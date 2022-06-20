package com.github.suninvr.virtualadditions.item.materials;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import com.github.suninvr.virtualadditions.registry.VAItems;

public class IoliteToolMaterial implements ToolMaterial {
    public static final IoliteToolMaterial INSTANCE = new IoliteToolMaterial();

    @Override
    public int getDurability() {
        return 3120;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 15.0F;
    }

    @Override
    public float getAttackDamage() {
        return 5.0F;
    }

    @Override
    public int getMiningLevel() {
        return 5;
    }

    @Override
    public int getEnchantability() {
        return 12;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(VAItems.STEEL_INGOT);
    }
}
