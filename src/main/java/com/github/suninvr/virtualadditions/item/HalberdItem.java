package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.registry.VAGildTypes;
import com.github.suninvr.virtualadditions.registry.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;

public class HalberdItem extends Item {
    public HalberdItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            float readiness = getSwingReadiness(stack, user, remainingUseTicks);
            if (readiness >= 0.25) {
                swingAttack(stack, world, player, readiness);
            }
        }
        return false;
    }

    private void swingAttack(ItemStack stack, World world, PlayerEntity player, float readiness) {{
            if (world instanceof ServerWorld serverWorld) {
                //Variable definitions
                boolean vertical = player.getPitch() >= 60 || player.getPitch() <= -60;
                List<Entity> playerMounts = player.getRootVehicle().getPassengerList();
                MutableInt entitiesHit = new MutableInt(0);
                Vec3d playerRotation = player.getRotationVector();
                Vec3d center = player.getEyePos().add(playerRotation.multiply(2.0));
                Box box = !vertical ? new Box(center.add(-2, -1, -2), center.add(2, 1, 2)) : new Box(center.add(-1, -2, -1), center.add(1, 2, 1));
                MutableFloat lungePower = new MutableFloat(0.0F);

                EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
                    enchantment.value().modifyValue(VAEnchantmentEffects.HALBERD_LUNGE_COMPONENT, player.getRandom(), level, lungePower);
                });

                // Damaging mobs found in the hitbox
                serverWorld.getNonSpectatingEntities(LivingEntity.class, box).stream().filter(livingEntity ->
                                livingEntity != player.getRootVehicle() && !playerMounts.contains(livingEntity))
                        .forEach(target -> {
                            //Damaging a bob
                            float startingHealth = target.getHealth();
                            DamageSource source = player.getDamageSources().playerAttack(player);
                            float damage = EnchantmentHelper.getDamage(serverWorld, stack, target, source, (float) (player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE) * readiness * 0.75F));
                            target.damage(serverWorld, source, damage);

                            // Additional events on damage
                            float knockback = player.getAttackKnockbackAgainst(target, source) + 0.5F * readiness;
                            if (knockback > 0.0F) {
                                double velocity = player.getVelocity().horizontalLength() + 1.0;
                                target.takeKnockback(knockback + (lungePower.floatValue() + (velocity * velocity) - 1), player.getX() - target.getX(), player.getZ() - target.getZ());
                                target.velocityModified = true;
                            }
                            stack.postDamageEntity(target, player);
                            entitiesHit.increment();
                            player.increaseStat(Stats.DAMAGE_DEALT, Math.round((startingHealth - target.getHealth()) * 10.0F));
                        });

                // Destroying blocks in a smaller hitbox
                BlockPos.stream(vertical ? box.expand(0, -1, 0) : box.expand(-1, 0, -1)).forEach(pos -> {
                    if (tryBreakState(serverWorld, pos, player)) stack.damage(1, player, player.getActiveHand());
                });

                // Events when at least one mob was hit
                if (entitiesHit.getValue() > 0) {
                    if (readiness >= 1) serverWorld.playSoundFromEntity(null, player, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    else serverWorld.playSoundFromEntity(null, player, SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }

                // Events when a swing occurs
                world.emitGameEvent(player, GameEvent.ITEM_INTERACT_FINISH, player.getEntityPos());
                player.addExhaustion(0.1F + 0.05F * entitiesHit.getValue());
                serverWorld.playSoundFromEntity(null, player, VASoundEvents.ITEM_HALBERD_SWING, SoundCategory.PLAYERS, 1.0F, 1.0F);
                Vec3d vec3d = player.getEyePos().add(playerRotation);
                applyPlayerMovement(player, stack, entitiesHit.getValue(), lungePower.floatValue());
                serverWorld.spawnParticles(ParticleTypes.SWEEP_ATTACK, false, true, vec3d.x, vec3d.y, vec3d.z, 1, 0, 0, 0, 0);
                if (readiness >= 1) {
                    if (vertical) serverWorld.spawnParticles(ParticleTypes.CRIT, false, true, center.x, center.y, center.z, 25, 0.25, 1.25, 0.25, 0.25);
                    else serverWorld.spawnParticles(ParticleTypes.CRIT, false, true, center.x, center.y, center.z, 25, 1.25, 0.125, 1.25, 0.25);
                }
            }

            // Client and Server events
            player.getItemCooldownManager().set(stack, (int) getSwingCooldown(stack, player));
            player.swingHand(player.getActiveHand(), true);
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            player.resetTicksSinceLastAttack();
        }
    }

    private static void applyPlayerMovement(PlayerEntity player, ItemStack stack, int entitiesHit, float lungePower) {
        if (player.hasVehicle() || player.getAbilities().flying) return;
        if (lungePower > 0 && !player.isGliding()) {
            player.setVelocity(
                    new Vec3d(
                            MathHelper.sin((player.getYaw() + 180) * (float) (Math.PI / 180.0)),
                            Math.min(0.4, player.getVelocity().y * 0.75 + lungePower),
                            -MathHelper.cos((player.getYaw() + 180) * (float) (Math.PI / 180.0)))
                    .multiply(entitiesHit > 0 ? lungePower * 0.5 : lungePower));
            player.velocityModified = true;
            stack.damage(1, player, player.getActiveHand());
        } else if (entitiesHit > 0) {
            if (player.isGliding()) player.setVelocity(player.getVelocity().multiply(0.25F));
            player.takeKnockback(player.isGliding() ? 1.0F : 0.333F, MathHelper.sin((player.getYaw() - 180) * (float) (Math.PI / 180.0)), -MathHelper.cos((player.getYaw() - 180) * (float) (Math.PI / 180.0)));
            player.velocityModified = true;
        }

    }

    public static float getSwingReadiness(ItemStack stack, LivingEntity user, int remainingUseTicks) {
        return getSwingReadiness(stack, user, remainingUseTicks, 0);
    }

    public static float getSwingReadiness(ItemStack stack, LivingEntity user, int remainingUseTicks, float tickProgress) {
        if (!(stack.getItem() instanceof HalberdItem)) return 0;
        float f = getMaxReadiness(stack, user);
        if (f <= 0) return 1;
        float g = Math.min( (float)stack.getItem().getMaxUseTime(stack, user) - ((float) remainingUseTicks - tickProgress), (int) f);
        return g / f;
    }

    public static float getMaxReadiness(ItemStack stack, LivingEntity user) {
        MutableFloat mutableFloat = new MutableFloat(30.0F);
        EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
            enchantment.value().modifyValue(VAEnchantmentEffects.HALBERD_READINESS_TIME_COMPONENT, user.getRandom(), level, mutableFloat);
        });
        return mutableFloat.floatValue();
    }

    private static boolean tryBreakState(ServerWorld world, BlockPos pos, PlayerEntity player) {
        if (player.isBlockBreakingRestricted(world, pos, player.getGameMode())) return false;
        BlockState state = world.getBlockState(pos);
        if (!state.isIn(VABlockTags.HALBERD_SWING_BREAKABLES)) return false;
        if (state.getBlock() instanceof LeavesBlock && state.get(LeavesBlock.PERSISTENT)) return false;
        if (state.isOf(Blocks.DECORATED_POT)) {
            world.setBlockState(pos, state.with(DecoratedPotBlock.CRACKED, true), DecoratedPotBlock.SKIP_REDRAW_AND_BLOCK_ENTITY_REPLACED_CALLBACK);
        }
        world.breakBlock(pos, !player.shouldSkipBlockDrops(), player);
        return state.getHardness(world, pos) > 0;
    }

    private static float getSwingCooldown(ItemStack stack, LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(40.0F);
        EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
            enchantment.value().modifyValue(VAEnchantmentEffects.HALBERD_SWING_COOLDOWN_COMPONENT, entity.getRandom(), level, mutableFloat);
        });
        if (VAGildTypes.AMETHYST.equals(VAToolUtil.getGildType(stack))) mutableFloat.add(-10.0F);
        if (mutableFloat.floatValue() <= 0) return 0;
        return mutableFloat.floatValue();
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        //if (hand == Hand.OFF_HAND) return ActionResult.PASS;
        ItemStack stack = user.getStackInHand(hand);
        if (getMaxReadiness(stack, user) <= 0) {
            swingAttack(stack, world, user, 1);
            return ActionResult.SUCCESS;
        }
        user.setCurrentHand(hand);
        return ActionResult.CONSUME;
    }
}
