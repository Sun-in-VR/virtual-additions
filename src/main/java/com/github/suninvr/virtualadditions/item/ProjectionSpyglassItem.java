package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.SpyglassItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ProjectionSpyglassItem extends SpyglassItem {
    public ProjectionSpyglassItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 12000;
    }

    public static boolean isInUseBy(@Nullable PlayerEntity player) {
        return player != null && player.isUsingItem() && player.getActiveItem().isOf(VAItems.SPECTRAL_SPYGLASS);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            PlayerProjectionEntity.createForPlayer(user);
        }
        user.playSound(VASoundEvents.ENTITY_SPECTRE_EMPOWER_START, 1.0F, 1.0F);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        user.setSprinting(false);
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        return super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }
}
