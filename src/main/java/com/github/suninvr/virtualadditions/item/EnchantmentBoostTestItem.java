package com.github.suninvr.virtualadditions.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EnchantmentBoostTestItem extends Item {
    public EnchantmentBoostTestItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if(clickType == ClickType.RIGHT) {

            if(slot.getStack().hasEnchantments()) {
                NbtList nbtList = slot.getStack().getEnchantments();
                Map<Enchantment, Integer> enchantements = EnchantmentHelper.fromNbt(nbtList);
                ItemStack stack1 = slot.getStack().copy();
                stack1.removeSubNbt("Enchantments");

                AtomicInteger cost = new AtomicInteger(50);

                enchantements.forEach( ((enchantment, integer) -> {
                    int integer2 = Math.round(integer * 1.61803F);
                    stack1.addEnchantment(enchantment, integer2);
                    cost.set(cost.get() + (integer2 - integer) * 10);
                } ));

                System.out.println(cost.get());
                if(player.experienceLevel >= cost.get() || player.getAbilities().creativeMode) {
                    stack.decrement(1);
                    player.experienceLevel = player.getAbilities().creativeMode ? player.experienceLevel : player.experienceLevel - cost.get();
                    player.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE, 1.0F, 0.6F);
                    slot.setStack(stack1);
                    return true;
                }

            }

        }
        return false;
    }

}
