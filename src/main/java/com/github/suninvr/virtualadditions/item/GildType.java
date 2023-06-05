package com.github.suninvr.virtualadditions.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;

public abstract class GildType {
    private final ArrayList<Modifier> modifiers = new ArrayList<>();
    private final Identifier id;
    private final int color;
    protected record Modifier(ModifierType type, float modifier, BiFunction<Float, Float, Float> function){}

    public GildType(Identifier id, int color, Modifier... modifiers) {
        this.id = id;
        this.color = color;
        this.modifiers.addAll(Arrays.asList(modifiers));
    }

    public boolean isGildEffective(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
        return tool.isSuitableFor(state);
    }

    public void emitBlockBreakingEffects(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {}

    public boolean onBlockBroken(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
        return true;
    }

    public ToolMaterial getModifiedMaterial(ToolMaterial baseMaterial) {
        if (this.modifiers.isEmpty()) return baseMaterial;
        
        int modifiedDurability = baseMaterial.getDurability();
        float modifiedMiningSpeedMultiplier = baseMaterial.getMiningSpeedMultiplier();
        float modifiedAttackDamage = baseMaterial.getAttackDamage();
        int modifiedMiningLevel = baseMaterial.getMiningLevel();
        int modifiedEnchantability = baseMaterial.getEnchantability();

        for (Modifier modifier : this.modifiers) {
            BiFunction<Float, Float, Float> function = modifier.function();
            switch (modifier.type) {
                case DURABILITY -> modifiedDurability = Math.round(function.apply((float)modifiedDurability, modifier.modifier));
                case MINING_SPEED -> modifiedMiningSpeedMultiplier = function.apply(modifiedMiningSpeedMultiplier, modifier.modifier);
                case ATTACK_DAMAGE -> modifiedAttackDamage = function.apply(modifiedAttackDamage, modifier.modifier);
                case MINING_LEVEL -> modifiedMiningLevel = Math.round(function.apply((float)modifiedMiningLevel, modifier.modifier));
                case ENCHANTABILITY -> modifiedEnchantability = Math.round(function.apply((float)modifiedEnchantability, modifier.modifier));
            }
        }

        return new ModifiedToolMaterial(modifiedMiningLevel, modifiedDurability, modifiedMiningSpeedMultiplier, modifiedAttackDamage, modifiedEnchantability, baseMaterial.getRepairIngredient());
    }

    public ToolMaterial getModifiedMaterial(ToolItem item) {
        return getModifiedMaterial(item.getMaterial());
    }

    public final String buildTooltipTranslationKey() {
        return "item.virtual_additions.gilded_tool_tooltip." + this.id.getPath();
    }

    public int getColor() {
        return color;
    }

    public Identifier getId() {
        return this.id;
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof GildType gildType && gildType.getId().equals(id);
    }

    protected enum ModifierType {
        DURABILITY,
        MINING_SPEED,
        ATTACK_DAMAGE,
        MINING_LEVEL,
        ENCHANTABILITY
    }

    public static class ModifiedToolMaterial implements ToolMaterial {
        private final int miningLevel;
        private final int itemDurability;
        private final float miningSpeed;
        private final float attackDamage;
        private final int enchantability;
        private final Ingredient repairIngredient;
        
        public ModifiedToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Ingredient repairIngredient) {
            this.miningLevel = miningLevel;
            this.itemDurability = itemDurability;
            this.miningSpeed = miningSpeed;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
            this.repairIngredient = repairIngredient;
        }

        @Override
        public int getDurability() {
            return this.itemDurability;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return this.miningSpeed;
        }

        @Override
        public float getAttackDamage() {
            return this.attackDamage;
        }

        @Override
        public int getMiningLevel() {
            return this.miningLevel;
        }

        @Override
        public int getEnchantability() {
            return this.enchantability;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return this.repairIngredient;
        }
    }
}
