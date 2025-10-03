package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.SpyglassItem;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class ProjectionSpyglassItem extends SpyglassItem {
    public ProjectionSpyglassItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 12000;
    }

    public static boolean isInUseBy(@Nullable Entity entity) {
        return entity instanceof PlayerEntity player && player.isUsingItem() && player.getActiveItem().isOf(VAItems.SPECTRAL_SPYGLASS);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            PlayerProjectionEntity entity = PlayerProjectionEntity.createForPlayer(user);
            if (entity != null) world.emitGameEvent(entity, GameEvent.ENTITY_PLACE, entity.getEntityPos());
        }
        user.playSound(VASoundEvents.ITEM_SPECTRAL_SPYGLASS_START, 1.0F, 1.0F);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        user.setSprinting(false);
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        return super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }
}
