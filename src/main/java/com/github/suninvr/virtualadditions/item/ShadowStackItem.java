package com.github.suninvr.virtualadditions.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

public class ShadowStackItem extends Item {
    public ShadowStackItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType.equals(ClickType.RIGHT)) {
            player.getInventory().setStack(0, slot.getStack());
            return true;
        }
        return super.onStackClicked(stack, slot, clickType, player);
    }
}
