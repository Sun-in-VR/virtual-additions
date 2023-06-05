package com.github.suninvr.virtualadditions.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
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
    protected record Modifier(ModifierType type, float modifier, BiFunction<Float, Float, Float> function){
        public float apply(float f) {
            return this.function.apply(f, this.modifier);
        }

        public int apply(int i) {
            return Math.round(this.function.apply((float) i, this.modifier));
        }
    }

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
        if (this.modifiers.isEmpty() || !this.shouldModifyBaseMaterial()) return baseMaterial;
        
        int modifiedDurability = baseMaterial.getDurability();
        float modifiedMiningSpeedMultiplier = baseMaterial.getMiningSpeedMultiplier();
        float modifiedAttackDamage = baseMaterial.getAttackDamage();
        int modifiedMiningLevel = baseMaterial.getMiningLevel();
        int modifiedEnchantability = baseMaterial.getEnchantability();

        for (Modifier modifier : this.modifiers) {
            switch (modifier.type) {
                case DURABILITY -> modifiedDurability = modifier.apply(modifiedDurability);
                case MINING_SPEED -> modifiedMiningSpeedMultiplier = modifier.apply(modifiedMiningSpeedMultiplier);
                case ATTACK_DAMAGE -> modifiedAttackDamage = modifier.apply(modifiedAttackDamage);
                case MINING_LEVEL -> modifiedMiningLevel = modifier.apply(modifiedMiningLevel);
                case ENCHANTABILITY -> modifiedEnchantability = modifier.apply(modifiedEnchantability);
            }
        }

        return new ModifiedToolMaterial(modifiedMiningLevel, modifiedDurability, modifiedMiningSpeedMultiplier, modifiedAttackDamage, modifiedEnchantability, baseMaterial.getRepairIngredient());
    }

    public double getModifiedAttackSpeed(ToolItem baseItem) {
        double attackSpeed = baseItem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(EntityAttributes.GENERIC_ATTACK_SPEED).stream().mapToDouble(EntityAttributeModifier::getValue).sum();
        for (Modifier modifier : this.modifiers) {
            if (modifier.type.equals(ModifierType.ATTACK_SPEED)) attackSpeed = modifier.apply((float) attackSpeed);
            attackSpeed = Math.round(attackSpeed * 10) / 10.0;
        }
        return attackSpeed;
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

    private boolean shouldModifyBaseMaterial() {
        for (Modifier modifier : this.modifiers) {
            if (modifier.type.modifiesBaseMaterial) return true;
        }
        return false;
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
        ENCHANTABILITY,
        ATTACK_SPEED(false);

        private boolean modifiesBaseMaterial;

        ModifierType(boolean modifiesBaseMaterial) {
            this.modifiesBaseMaterial = modifiesBaseMaterial;
        }

        ModifierType() {
            this.modifiesBaseMaterial = true;
        }
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
