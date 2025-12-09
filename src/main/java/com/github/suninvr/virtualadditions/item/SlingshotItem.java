package com.github.suninvr.virtualadditions.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SlingshotItem extends Item {

    public SlingshotItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        if (level.isClientSide()) return false;
        InteractionHand oppositeHand = livingEntity.getUsedItemHand().equals(InteractionHand.MAIN_HAND) ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        int useTicks = 72000 - i;
        double power = Math.min(useTicks / 20.0, 1);
        boolean bl = shootProjectile(level, livingEntity, livingEntity.getItemInHand(oppositeHand), power);
        if (bl) {
            itemStack.hurtAndBreak(1, livingEntity, livingEntity.getUsedItemHand().asEquipmentSlot());
            if (livingEntity instanceof Player player) {
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            }
        }
        return bl;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        InteractionHand oppositeHand = interactionHand.equals(InteractionHand.MAIN_HAND) ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack stack = player.getItemInHand(oppositeHand);
        if (!stack.isEmpty()) {
            player.startUsingItem(interactionHand);
            level.playSound(player, player.position().x, player.getEyeY(), player.position().z, SoundEvents.CROSSBOW_QUICK_CHARGE_3.value(), SoundSource.PLAYERS, 0.3F, 1.2F);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return 72000;
    }

    protected static boolean shootProjectile(Level level, LivingEntity shooter, ItemStack stack, double power) {
        Vec3 firePos = shooter.getEyePosition().add(shooter.getLookAngle().normalize());
        Vec3 shooterMovement = shooter.getDeltaMovement();
        Vec3 vel = shooter.getLookAngle().add(shooterMovement.x, shooter.onGround() ? 0.0 : shooterMovement.y, shooterMovement.z).normalize();
        Vec3 playSoundPos = firePos.add(shooter.getLookAngle().normalize().scale(3.0));
        level.playSound(null, playSoundPos.x, playSoundPos.y, playSoundPos.z, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS);
        return SlingshotBehaviors.runOnItem(stack, level, shooter, firePos, vel, power);
    }
}