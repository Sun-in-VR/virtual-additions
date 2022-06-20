package com.github.suninvr.virtualadditions.item.enums;

import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.StringIdentifiable;

import java.util.Locale;
import java.util.function.BiFunction;

public enum GildType implements StringIdentifiable {
    AMETHYST(0, 1F, 0, 0, 0, Operation.ADDITION, "amethyst"),
    COPPER(1.5F, 1, 1, 1, 1, Operation.MULTIPLICATION, "copper"),
    EMERALD(0, 0, 0, 1, 6, Operation.ADDITION, "emerald"),
    QUARTZ(0, 0, 2, 0, 0, Operation.ADDITION, "quartz"),
    NONE(0, 0, 0, 0, 0, Operation.ADDITION, "none");

    public static final Codec<GildType> CODEC = StringIdentifiable.createCodec(GildType::values);
    private final float durabilityModifier;
    private final float miningSpeedMultiplierModifier;
    private final float attackDamageModifier;
    private final float miningLevelModifier;
    private final float enchantabilityModifier;
    private final Operation operation;
    private final String name;

    GildType(float durabilityModifier, float miningSpeedMultiplierModifier, float attackDamageModifier, float miningLevelModifier, float enchantabilityModifier, Operation operation, String name) {
        this.durabilityModifier = durabilityModifier;
        this.miningSpeedMultiplierModifier = miningSpeedMultiplierModifier;
        this.attackDamageModifier = attackDamageModifier;
        this.miningLevelModifier = miningLevelModifier;
        this.enchantabilityModifier = enchantabilityModifier;
        this.operation = operation;
        this.name = name;
    }

    public ToolMaterial getModifiedMaterial(ToolMaterial baseMaterial) {
        return new ToolMaterial() {
            final BiFunction<Float, Float, Float> function = GildType.this.operation.getFunction();
            final int modifiedDurability = Math.round(function.apply((float) baseMaterial.getDurability(), GildType.this.durabilityModifier));
            final float modifiedMiningSpeedMultiplier = function.apply(baseMaterial.getMiningSpeedMultiplier(), GildType.this.miningSpeedMultiplierModifier);
            final float modifiedAttackDamage = function.apply(baseMaterial.getAttackDamage(), GildType.this.attackDamageModifier);
            final int modifiedMiningLevel = Math.round(function.apply((float) baseMaterial.getMiningLevel(), GildType.this.miningLevelModifier));
            final int modifiedEnchantability = Math.round(function.apply((float) baseMaterial.getEnchantability(), GildType.this.enchantabilityModifier));

            @Override
            public int getDurability() {
                return modifiedDurability;
            }

            @Override
            public float getMiningSpeedMultiplier() {
                return modifiedMiningSpeedMultiplier;
            }

            @Override
            public float getAttackDamage() {
                return modifiedAttackDamage;
            }

            @Override
            public int getMiningLevel() {
                return modifiedMiningLevel;
            }

            @Override
            public int getEnchantability() {
                return modifiedEnchantability;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return baseMaterial.getRepairIngredient();
            }
        };
    }

    public ToolMaterial getModifiedMaterial(ToolItem item) {
        return getModifiedMaterial(item.getMaterial());
    }

    public String buildTooltipTranslationKey() {
        return "item.virtual_additions.gilded_tool_tooltip." + this.name;
    }

    @Override
    public String asString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    private enum Operation {
        ADDITION(Float::sum),
        MULTIPLICATION((attribute, modifier) -> attribute * modifier);

        private final BiFunction<Float, Float, Float> function;

        Operation(BiFunction<Float, Float, Float> function) {
            this.function = function;
        }

        BiFunction<Float, Float, Float> getFunction() {
            return this.function;
        }
    }
}
