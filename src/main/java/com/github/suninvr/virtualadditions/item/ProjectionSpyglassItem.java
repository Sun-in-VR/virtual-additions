package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class ProjectionSpyglassItem extends SpyglassItem {
    public ProjectionSpyglassItem(Properties settings) {
        super(settings);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 12000;
    }

    public static boolean isInUseBy(@Nullable Entity entity) {
        return entity instanceof Player player && player.isUsingItem() && player.getUseItem().is(VAItems.SPECTRAL_SPYGLASS);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide()) {
            PlayerProjectionEntity entity = PlayerProjectionEntity.createForPlayer(user);
            if (entity != null) world.gameEvent(entity, GameEvent.ENTITY_PLACE, entity.position());
        }
        user.playSound(VASoundEvents.ITEM_SPECTRAL_SPYGLASS_START, 1.0F, 1.0F);
        user.awardStat(Stats.ITEM_USED.get(this));
        user.setSprinting(false);
        return ItemUtils.startUsingInstantly(world, user, hand);
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        return super.releaseUsing(stack, world, user, remainingUseTicks);
    }
}
