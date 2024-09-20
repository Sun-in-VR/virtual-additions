package com.github.suninvr.virtualadditions.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BalloonFruitItem extends BlockItem {
    public BalloonFruitItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            player.getItemCooldownManager().set(stack.getItem().getDefaultStack(), 30);
        }
        return super.finishUsing(stack, world, user);
    }
}
