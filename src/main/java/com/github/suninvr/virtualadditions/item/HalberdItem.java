package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.interfaces.PlayerEntityInterface;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VAEnchantmentEffects;
import com.github.suninvr.virtualadditions.registry.VAGildTypes;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;

public class HalberdItem extends Item {
    public HalberdItem(Properties settings) {
        super(settings);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof Player player) {
            float readiness = getSwingReadiness(stack, user, remainingUseTicks);
            if (readiness >= 0.25) {
                swingAttack(stack, world, player, readiness);
            }
        }
        return false;
    }

    private void swingAttack(ItemStack stack, Level world, Player player, float readiness) {{
            if (world instanceof ServerLevel serverWorld) {
                //Variable definitions
                boolean vertical = player.getXRot() >= 60 || player.getXRot() <= -60;
                List<Entity> playerMounts = player.getRootVehicle().getPassengers();
                MutableInt entitiesHit = new MutableInt(0);
                Vec3 playerRotation = player.getLookAngle();
                Vec3 center = player.getEyePosition().add(playerRotation.scale(2.0));
                AABB box = !vertical ? new AABB(center.add(-2, -1, -2), center.add(2, 1, 2)) : new AABB(center.add(-1, -2, -1), center.add(1, 2, 1));
                MutableFloat lungePower = new MutableFloat(0.0F);

                EnchantmentHelper.runIterationOnItem(stack, (enchantment, level) -> {
                    enchantment.value().modifyUnfilteredValue(VAEnchantmentEffects.HALBERD_LUNGE_COMPONENT, player.getRandom(), level, lungePower);
                });

                // Damaging mobs found in the hitbox
                serverWorld.getEntitiesOfClass(LivingEntity.class, box).stream().filter(livingEntity ->
                                livingEntity != player.getRootVehicle() && !playerMounts.contains(livingEntity))
                        .forEach(target -> {
                            //Damaging a bob
                            float startingHealth = target.getHealth();
                            DamageSource source = player.damageSources().playerAttack(player);
                            float damage = EnchantmentHelper.modifyDamage(serverWorld, stack, target, source, (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) * readiness * 0.75F));
                            target.hurtServer(serverWorld, source, damage);

                            // Additional events on damage
                            float knockback = player.getKnockback(target, source) + 0.5F * readiness;
                            if (knockback > 0.0F) {
                                double velocity = player.getDeltaMovement().horizontalDistance() + 1.0;
                                target.knockback(knockback + (lungePower.floatValue() + (velocity * velocity) - 1), player.getX() - target.getX(), player.getZ() - target.getZ());
                                target.hurtMarked = true;
                            }
                            player.attackVisualEffects(target, false, true, false, false, 0.0F);
                            player.setLastHurtMob(target);
                            player.itemAttackInteraction(target, stack, source, true);
                            player.damageStatsAndHearts(target, startingHealth);
                            entitiesHit.increment();
                        });

                // Destroying blocks in a smaller hitbox
                BlockPos.betweenClosedStream(vertical ? box.inflate(0, -1, 0) : box.inflate(-1, 0, -1)).forEach(pos -> {
                    if (tryBreakState(serverWorld, pos, player)) stack.hurtAndBreak(1, player, player.getUsedItemHand());
                });

                // Events when at least one mob was hit
                if (entitiesHit.getValue() > 0) {
                    if (readiness >= 1) serverWorld.playSound(null, player, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    else serverWorld.playSound(null, player, SoundEvents.PLAYER_ATTACK_WEAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                }

                // Events when a swing occurs
                world.gameEvent(player, GameEvent.ITEM_INTERACT_FINISH, player.position());
                player.causeFoodExhaustion(0.1F + 0.05F * entitiesHit.getValue());
                serverWorld.playSound(null, player, VASoundEvents.ITEM_HALBERD_SWING, SoundSource.PLAYERS, 1.0F, 1.0F);
                Vec3 vec3d = player.getEyePosition().add(playerRotation);
                applyPlayerMovement(player, stack, entitiesHit.getValue(), lungePower.floatValue());
                serverWorld.sendParticles(ParticleTypes.SWEEP_ATTACK, false, true, vec3d.x, vec3d.y, vec3d.z, 1, 0, 0, 0, 0);
                if (readiness >= 1) {
                    if (vertical) serverWorld.sendParticles(ParticleTypes.CRIT, false, true, center.x, center.y, center.z, 25, 0.25, 1.25, 0.25, 0.25);
                    else serverWorld.sendParticles(ParticleTypes.CRIT, false, true, center.x, center.y, center.z, 25, 1.25, 0.125, 1.25, 0.25);
                }
            }

            // Client and Server events
            player.getCooldowns().addCooldown(stack, (int) getSwingCooldown(stack, player));
            ((PlayerEntityInterface)(player)).virtualAdditions$onHalberdSwing();
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            player.resetOnlyAttackStrengthTicker();
        }
    }

    private static void applyPlayerMovement(Player player, ItemStack stack, int entitiesHit, float lungePower) {
        if (player.isPassenger() || player.getAbilities().flying) return;
        if (lungePower > 0 && !player.isFallFlying()) {
            player.setDeltaMovement(
                    new Vec3(
                            Mth.sin((player.getYRot() + 180) * (float) (Math.PI / 180.0)),
                            Math.min(0.4, player.getDeltaMovement().y * 0.75 + lungePower),
                            -Mth.cos((player.getYRot() + 180) * (float) (Math.PI / 180.0)))
                    .scale(entitiesHit > 0 ? lungePower * 0.5 : lungePower));
            player.hurtMarked = true;
            stack.hurtAndBreak(1, player, player.getUsedItemHand());
        } else if (entitiesHit > 0) {
            if (player.isFallFlying()) player.setDeltaMovement(player.getDeltaMovement().scale(0.25F));
            player.knockback(player.isFallFlying() ? 1.0F : 0.333F, Mth.sin((player.getYRot() - 180) * (float) (Math.PI / 180.0)), -Mth.cos((player.getYRot() - 180) * (float) (Math.PI / 180.0)));
            player.hurtMarked = true;
        }

    }

    public static float getSwingReadiness(ItemStack stack, LivingEntity user, int remainingUseTicks) {
        return getSwingReadiness(stack, user, remainingUseTicks, 0);
    }

    public static float getSwingReadiness(ItemStack stack, LivingEntity user, int remainingUseTicks, float tickProgress) {
        if (!(stack.getItem() instanceof HalberdItem)) return 0;
        float f = getMaxReadiness(stack, user);
        if (f <= 0) return 1;
        float g = Math.min( (float)stack.getItem().getUseDuration(stack, user) - ((float) remainingUseTicks - tickProgress), (int) f);
        return g / f;
    }

    public static float getMaxReadiness(ItemStack stack, LivingEntity user) {
        MutableFloat mutableFloat = new MutableFloat(30.0F);
        EnchantmentHelper.runIterationOnItem(stack, (enchantment, level) -> {
            enchantment.value().modifyUnfilteredValue(VAEnchantmentEffects.HALBERD_READINESS_TIME_COMPONENT, user.getRandom(), level, mutableFloat);
        });
        return mutableFloat.floatValue();
    }

    private static boolean tryBreakState(ServerLevel world, BlockPos pos, Player player) {
        if (player.blockActionRestricted(world, pos, player.gameMode())) return false;
        BlockState state = world.getBlockState(pos);
        if (!state.is(VABlockTags.HALBERD_SWING_BREAKABLES)) return false;
        if (state.getBlock() instanceof LeavesBlock && state.getValue(LeavesBlock.PERSISTENT)) return false;
        if (state.is(Blocks.DECORATED_POT)) {
            world.setBlock(pos, state.setValue(DecoratedPotBlock.CRACKED, true), DecoratedPotBlock.UPDATE_NONE);
        }
        world.destroyBlock(pos, !player.preventsBlockDrops(), player);
        return state.getDestroySpeed(world, pos) > 0;
    }

    private static float getSwingCooldown(ItemStack stack, LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(40.0F);
        EnchantmentHelper.runIterationOnItem(stack, (enchantment, level) -> {
            enchantment.value().modifyUnfilteredValue(VAEnchantmentEffects.HALBERD_SWING_COOLDOWN_COMPONENT, entity.getRandom(), level, mutableFloat);
        });
        if (VAGildTypes.AMETHYST.equals(VAToolUtil.getGildType(stack))) mutableFloat.add(-10.0F);
        if (mutableFloat.floatValue() <= 0) return 0;
        return mutableFloat.floatValue();
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) return InteractionResult.PASS;
        ItemStack stack = user.getItemInHand(hand);
        if (getMaxReadiness(stack, user) <= 0) {
            swingAttack(stack, world, user, 1);
            return InteractionResult.PASS;
        }
        user.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }
}
