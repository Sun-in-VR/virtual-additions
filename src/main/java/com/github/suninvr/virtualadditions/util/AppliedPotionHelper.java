package com.github.suninvr.virtualadditions.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;

@SuppressWarnings("DataFlowIssue")
public class AppliedPotionHelper {

    public static boolean hasAppliedPotionEffects(ItemStack stack) {
        if (!stack.hasNbt()) return false;
        NbtCompound nbt = stack.getNbt();
        return nbt.contains("AppliedPotion");
    }

    public static Potion getAppliedPotion (ItemStack stack) {
        if (hasAppliedPotionEffects(stack)) {
            NbtCompound nbt = stack.getNbt().getCompound("AppliedPotion");
            return PotionUtil.getPotion(nbt);
        } else {
            return Potions.EMPTY;
        }
    }

    public static int getAppliedPotionUses (ItemStack stack) {
        if (hasAppliedPotionEffects(stack)) {
            NbtCompound nbt = stack.getNbt().getCompound("AppliedPotion");
            return nbt.getInt("Uses");
        } else {
            return 0;
        }
    }

    public static int getMaxAppliedPotionUses (ItemStack stack) {
        if (hasAppliedPotionEffects(stack)) {
            NbtCompound nbt = stack.getNbt().getCompound("AppliedPotion");
            return nbt.getInt("MaxUses");
        } else {
            return 0;
        }
    }

    public static void decrementAppliedPotionUses (ItemStack stack) {
        if (hasAppliedPotionEffects(stack)) {
            NbtCompound nbt = stack.getNbt().getCompound("AppliedPotion");
            nbt.putInt("Uses", nbt.getInt("Uses") - 1);
            stack.getNbt().put("AppliedPotion", nbt);
        }
    }
}
