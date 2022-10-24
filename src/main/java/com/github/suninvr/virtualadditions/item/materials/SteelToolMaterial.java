package com.github.suninvr.virtualadditions.item.materials;

import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class SteelToolMaterial implements ToolMaterial {
    public static final SteelToolMaterial INSTANCE = new SteelToolMaterial();

    @Override
    public int getDurability() {
        return 768;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 7.0F;
    }

    @Override
    public float getAttackDamage() {
        return 2.5F;
    }

    @Override
    public int getMiningLevel() {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 14;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(VAItems.STEEL_INGOT);
    }
}
