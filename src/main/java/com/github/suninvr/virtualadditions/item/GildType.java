package com.github.suninvr.virtualadditions.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;

public class GildType {
    private final ArrayList<Modifier> modifiers = new ArrayList<>();
    private final Identifier id;
    private final int color;
    private TagKey<Item> tag;
    private TagKey<Item> axesTag;
    private TagKey<Item> hoesTag;
    private TagKey<Item> pickaxesTag;
    private TagKey<Item> shovelsTag;
    private TagKey<Item> swordsTag;
    protected record Modifier(ModifierType type, float value, BiFunction<Float, Float, Float> function){
        public float apply(float f) {
            return this.function.apply(f, this.value);
        }

        public int apply(int i) {
            return Math.round(this.function.apply((float) i, this.value));
        }
    }

    public GildType(Identifier id, int color, Modifier... modifiers) {
        this.id = id;
        this.color = color;
        this.modifiers.addAll(Arrays.asList(modifiers));
    }

    /**
     * Gets whether the gild should play effects or affect the world after breaking a specific block.
     *
     * @return <code>true</code> if the gild type is effective, <code>false</code> if it is not effective.
     *
     * @param world the world in which the block was broken
     * @param pos the position of the block that was broken
     * @param player the player that broke the block
     * @param state the state of the broken block
     * @param tool the tool used to break the block
     *
     * **/
    public boolean isGildEffective(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
        return tool.isSuitableFor(state);
    }

    /**
     * Plays effects in the world after a tool with this gild type breaks a block.
     *
     * @param world the world in which the block was broken
     * @param pos the position of the block that was broken
     * @param tool the tool used to break the block
     * **/
    public void emitBlockBreakingEffects(World world, BlockPos pos, ItemStack tool) {}

    /**
     * Affects the world after a tool with this gild type breaks a block.
     *
     * @return <code>true</code> if the block should still break normally, <code>false</code> if it should not.
     *
     * @param world the world in which the block was broken
     * @param pos the position of the block that was broken
     * @param player the player that broke the block
     * @param state the state of the broken block
     * @param tool the tool used to break the block
     *
     * @implNote Returning false will disable <b>all effects</b> of breaking a block, such damaging the tool and increasing the player's relevant stats.
     *
     * **/
    public boolean onBlockBroken(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
        return true;
    }

    /**
     * Modifies a given tool material with the modifications provided at the gild type's creation.
     *
     * @return a new tool material with modifiers applied
     *
     * @param baseMaterial the tool material to apply this gild type's modifications to
     *
     * **/
    public final ToolMaterial getModifiedMaterial(ToolMaterial baseMaterial) {
        if (this.modifiers.isEmpty() || !this.shouldModifyBaseMaterial()) return baseMaterial;
        
        int durability = baseMaterial.getDurability();
        float miningSpeed = baseMaterial.getMiningSpeedMultiplier();
        float attackDamage = baseMaterial.getAttackDamage();
        int miningLevel = baseMaterial.getMiningLevel();
        int enchantability = baseMaterial.getEnchantability();

        for (Modifier modifier : this.modifiers) {
            switch (modifier.type) {
                case DURABILITY -> durability = modifier.apply(durability);
                case MINING_SPEED -> miningSpeed = modifier.apply(miningSpeed);
                case ATTACK_DAMAGE -> attackDamage = modifier.apply(attackDamage);
                case MINING_LEVEL -> miningLevel = modifier.apply(miningLevel);
                case ENCHANTABILITY -> enchantability = modifier.apply(enchantability);
            }
        }

        return new ModifiedToolMaterial(miningLevel, durability, miningSpeed, attackDamage, enchantability, baseMaterial.getRepairIngredient());
    }

    /**
     * Get a value for attack speed from a base item.
     *
     * @return the modified attack speed value
     *
     * @param baseItem the item from which the base attack speed is obtained
     *
     * **/
    public double getModifiedAttackSpeed(ToolItem baseItem) {
        double attackSpeed = baseItem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(EntityAttributes.GENERIC_ATTACK_SPEED).stream().mapToDouble(EntityAttributeModifier::getValue).sum();
        for (Modifier modifier : this.modifiers) {
            if (modifier.type.equals(ModifierType.ATTACK_SPEED)) attackSpeed = modifier.apply((float) attackSpeed);
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
    
    public TagKey<Item> getTag() {
        if (this.tag == null) {
            Identifier id = this.id.withSuffixedPath("_gilded_tools");
            this.tag = TagKey.of(RegistryKeys.ITEM, id);
        }
        return this.tag;
    }
    
    public TagKey<Item> getAxesTag() {
        if (this.axesTag == null) {
            Identifier id = this.id.withSuffixedPath("_gilded_axes");
            this.axesTag = TagKey.of(RegistryKeys.ITEM, id);
        }
        return this.axesTag;
    }
    
    public TagKey<Item> getHoesTag() {
        if (this.hoesTag == null) {
            Identifier id = this.id.withSuffixedPath("_gilded_hoes");
            this.hoesTag = TagKey.of(RegistryKeys.ITEM, id);
        }
        return this.hoesTag;
    }
    
    public TagKey<Item> getPickaxesTag() {
        if (this.pickaxesTag == null) {
            Identifier id = this.id.withSuffixedPath("_gilded_pickaxes");
            this.pickaxesTag = TagKey.of(RegistryKeys.ITEM, id);
        }
        return this.pickaxesTag;
    }

    public TagKey<Item> getShovelsTag() {
        if (this.shovelsTag == null) {
            Identifier id = this.id.withSuffixedPath("_gilded_shovels");
            this.shovelsTag = TagKey.of(RegistryKeys.ITEM, id);
        }
        return this.shovelsTag;
    }

    public TagKey<Item> getSwordsTag() {
        if (this.swordsTag == null) {
            Identifier id = this.id.withSuffixedPath("_gilded_swords");
            this.swordsTag = TagKey.of(RegistryKeys.ITEM, id);
        }
        return this.swordsTag;
    }

    @Override
    public final boolean equals(Object obj) {
        return (obj instanceof GildType gildType && gildType.getId().equals(this.id)) || (obj instanceof Identifier identifier && identifier.equals(this.id));
    }

    protected enum ModifierType {
        DURABILITY,
        MINING_SPEED,
        ATTACK_DAMAGE,
        MINING_LEVEL,
        ENCHANTABILITY,
        ATTACK_SPEED(false);

        private final boolean modifiesBaseMaterial;

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
