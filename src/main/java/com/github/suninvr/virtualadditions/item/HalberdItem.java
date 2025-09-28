package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.registry.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DecoratedPotBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
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
import net.minecraft.world.RaycastContext;
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
            int i = Math.min(this.getMaxUseTime(stack, user) - remainingUseTicks, 20);
            if (i >= 5) {
                float f = MathHelper.lerp(i / 20.0F, 0, 1);
                boolean isLookingUp = player.getPitch() <= -45;
                boolean isLookingDown = player.getPitch() >= 45;
                if (world instanceof ServerWorld serverWorld) {
                    boolean hasAppliedPotion = stack.contains(VADataComponentTypes.EFFECTS_ON_HIT);
                    List<Entity> playerMounts = player.getRootVehicle().getPassengerList();
                    MutableInt entitiesHit = new MutableInt(0);
                    Vec3d playerRotation = player.getRotationVector();
                    Box box = isLookingUp || isLookingDown
                            ? player.getBoundingBox().offset(playerRotation.multiply( isLookingUp ? 1.5 : 2.5)).offset(new Vec3d(0, player.getEyeHeight(player.getPose()) / 2, 0)).expand(0.5, 2.0, 0.5)
                            : player.getBoundingBox().offset(playerRotation.multiply(2.5)).offset(new Vec3d(0, player.getEyeHeight(player.getPose()) / 2, 0)).expand(2.5, 0.5, 2.5);
                    serverWorld.getNonSpectatingEntities(LivingEntity.class, box).stream().filter(livingEntity ->
                            livingEntity != player.getRootVehicle() && !playerMounts.contains(livingEntity))
                            .forEach(target -> {
                                float startingHealth = target.getHealth();
                                DamageSource source = player.getDamageSources().playerAttack(player);
                                float damage = EnchantmentHelper.getDamage(serverWorld, stack, target, source, (float) (player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE) * f * 0.75F));
                                target.damage(serverWorld, source, damage);
                                float knockback = player.getAttackKnockbackAgainst(target, source) + 0.5F;
                                if (knockback > 0.0F) {
                                    target.takeKnockback(
                                            knockback * 0.5F, player.getX() - target.getX(), player.getZ() - target.getZ()
                                    );
                                    player.setVelocity(player.getVelocity().multiply(0.6, 1.0, 0.6));
                                    player.setSprinting(false);
                                }
                                if (hasAppliedPotion) {
                                    stack.get(VADataComponentTypes.EFFECTS_ON_HIT).forEachEffect(statusEffectInstance -> target.addStatusEffect(statusEffectInstance, player));
                                }
                                if (GildedToolUtil.getGildType(stack).equals(GildTypes.SCULK)) {
                                    target.addStatusEffect(new StatusEffectInstance(VAStatusEffects.FESTERING_WOUNDS, 400));
                                }
                                entitiesHit.increment();
                                player.increaseStat(Stats.DAMAGE_DEALT, Math.round((startingHealth - target.getHealth()) * 10.0F));
                    });
                    BlockPos.stream(box.expand(-1.5, -0.5, -1.5)).forEach(pos -> {
                        if (tryBreakState(serverWorld, pos, player)) stack.damage(1, player, player.getActiveHand());
                    });
                    stack.damage(entitiesHit.getValue() / 2, player, player.getActiveHand());
                    if (entitiesHit.getValue() > 0) {

                        if (hasAppliedPotion) stack.set(VADataComponentTypes.EFFECTS_ON_HIT, stack.get(VADataComponentTypes.EFFECTS_ON_HIT).decrementRemainingUses());

                        if (i == 20) serverWorld.playSoundFromEntity(null, player, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        else serverWorld.playSoundFromEntity(null, player, SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    } else {
                        serverWorld.playSoundFromEntity(null, player, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1.0F, 0.8F);
                    }
                    Vec3d center = box.getCenter();
                    Vec3d vec3d = player.getEyePos().add(playerRotation);
                    serverWorld.spawnParticles(ParticleTypes.SWEEP_ATTACK, false, true, vec3d.x, vec3d.y, vec3d.z, 1, 0, 0, 0, 0);
                    if (i == 20) {
                        if (isLookingUp || isLookingDown) serverWorld.spawnParticles(ParticleTypes.CRIT, false, true, center.x, center.y, center.z, 25, 0.25, 1.25, 0.25, 0.25);
                        else serverWorld.spawnParticles(ParticleTypes.CRIT, false, true, center.x, center.y, center.z, 25, 1.25, 0.125, 1.25, 0.25);
                    }
                    world.emitGameEvent(player, GameEvent.ITEM_INTERACT_FINISH, player.getEntityPos());
                    player.addExhaustion(0.1F + 0.05F * entitiesHit.getValue());
                }
                player.getItemCooldownManager().set(stack, (int) getSwingCooldown(stack, player));
                player.swingHand(player.getActiveHand(), true);
                player.resetLastAttackedTicks();
                return true;
            }
        }
        return false;
    }

    private static boolean tryBreakState(ServerWorld world, BlockPos pos, PlayerEntity player) {
        if (player.isBlockBreakingRestricted(world, pos, player.getGameMode())) return false;
        BlockState state = world.getBlockState(pos);
        if (!state.isIn(VABlockTags.HALBERD_SWING_BREAKABLES)) return false;
        if (state.getBlock() instanceof LeavesBlock && state.get(LeavesBlock.PERSISTENT)) return false;
        if (state.isOf(Blocks.DECORATED_POT)) {
            world.setBlockState(pos, state.with(DecoratedPotBlock.CRACKED, true), DecoratedPotBlock.SKIP_REDRAW_AND_BLOCK_ENTITY_REPLACED_CALLBACK);
        }
        if (state.isOf(Blocks.COBWEB) && player.getEyePos().squaredDistanceTo(pos.toCenterPos()) > 6.25) return false;
        world.breakBlock(pos, !player.shouldSkipBlockDrops(), player);
        return state.getHardness(world, pos) > 0;
    }

    private static float getSwingCooldown(ItemStack stack, LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(60.0F);
        EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
            enchantment.value().modifyValue(VAEnchantmentEffects.HALBERD_SWING_COOLDOWN_COMPONENT, entity.getRandom(), level, mutableFloat);
        });
        return mutableFloat.floatValue();
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (hand == Hand.OFF_HAND) return ActionResult.PASS;
        user.setCurrentHand(hand);
        return ActionResult.CONSUME;
    }
}
