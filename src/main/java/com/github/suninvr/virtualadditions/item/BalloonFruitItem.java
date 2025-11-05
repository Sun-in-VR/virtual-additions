package com.github.suninvr.virtualadditions.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class BalloonFruitItem extends BlockItem {
    public BalloonFruitItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (user instanceof Player player) {
            player.getCooldowns().addCooldown(stack.getItem().getDefaultInstance(), 30);
        }
        return super.finishUsingItem(stack, world, user);
    }
}
