package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class VACallbacks{

    public static void init() {

        PlayerBlockBreakEvents.BEFORE.register( (world, player, pos, state, blockEntity) -> {
            if (player.isSpectator()) return true;
            if (player.isCreative()) return true;
            ItemStack tool = player.getStackInHand(Hand.MAIN_HAND);
            GildType gild;
            if (tool.contains(VADataComponentTypes.GILD_TYPE) && (gild = tool.get(VADataComponentTypes.GILD_TYPE)).isGildEffective(world, player, pos, state, tool)) {
                gild.emitBlockBreakingEffects(world, player, pos, tool);
                return gild.onBlockBroken(world, player, pos, state, tool);
            }
            return true;
        } );





    }
}
