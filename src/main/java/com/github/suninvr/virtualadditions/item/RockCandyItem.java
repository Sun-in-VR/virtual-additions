package com.github.suninvr.virtualadditions.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class RockCandyItem extends Item {
    public RockCandyItem(Settings settings) { super(settings); }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack stack1 = super.finishUsing(stack, world, user);
        if (stack1.isEmpty()) return Items.STICK.getDefaultStack();
        if (user instanceof PlayerEntity player) {
            if (player.getAbilities().creativeMode) return stack;
            if (!(player.getInventory().insertStack(Items.STICK.getDefaultStack()))) {
                player.dropItem(Items.STICK);
            }
        }
        return stack;
    }

}
