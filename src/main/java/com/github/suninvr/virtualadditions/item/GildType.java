package com.github.suninvr.virtualadditions.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
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
    public record Modifier(ModifierType type, float value, BiFunction<Float, Float, Float> function, ModifierType.ToolType... appliesTo){

        public float apply(float f) {
            return this.function.apply(f, this.value);
        }

        public int apply(int i) {
            return Math.round(this.function.apply((float) i, this.value));
        }

        public float apply(float f, Item baseItem) {
            return this.shouldApplyToTool(baseItem) ? this.apply(f) : f;
        }

        public int apply(int i, Item baseItem) {
            return this.shouldApplyToTool(baseItem) ? this.apply(i) : i;
        }

        public boolean shouldApplyToTool(Item item) {
            if (this.appliesTo.length == 0) return true;
            for (ModifierType.ToolType type : this.appliesTo) {
                if (type.matches(item)) return true;
            }
            return false;
        }

        public boolean shouldBeAppended() {
            return this.type.shouldBeAppended();
        }

        public boolean modifiesAttribute(RegistryEntry<EntityAttribute> attribute) {
            return this.type.getAttributeType() != null && this.type.getAttributeType().equals(attribute);
        }
    }

    /**
     * Constructor for gild types. Each gild type has a set of modifiers to apply to the base tool's attributes.
     * For extended functionality, some methods are provided for interacting with the world.
     *
     * @param id The identifier used by this gild
     * @param color The color of the gild type's tooltip
     * @param modifiers The attribute modifiers to apply to tools using this gild
     */
    public GildType(Identifier id, int color, Modifier... modifiers) {
        this.id = id;
        this.color = color;
        this.modifiers.addAll(Arrays.asList(modifiers));
    }

    /**
     * Gets whether the gild should play effects or interact with the world after breaking a specific block.
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
    public void emitBlockBreakingEffects(World world, PlayerEntity player, BlockPos pos, ItemStack tool) {}

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
     * @implNote Returning false will disable <b><i>all effects</i></b> of breaking a block, such damaging the tool and increasing the player's relevant stats.
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
    public final ToolMaterial getModifiedMaterial(ToolMaterial baseMaterial, ToolItem baseItem) {
        if (this.modifiers.isEmpty() || !this.shouldModifyBaseMaterial()) return baseMaterial;

        int durability = baseMaterial.getDurability();
        float miningSpeed = baseMaterial.getMiningSpeedMultiplier();
        float attackDamage = baseMaterial.getAttackDamage();
        int enchantability = baseMaterial.getEnchantability();

        for (Modifier modifier : this.modifiers) {
            switch (modifier.type) {
                case DURABILITY -> durability = modifier.apply(durability, baseItem);
                case MINING_SPEED -> miningSpeed = modifier.apply(miningSpeed, baseItem);
                case ATTACK_DAMAGE -> attackDamage = modifier.apply(attackDamage, baseItem);
                case ENCHANTABILITY -> enchantability = modifier.apply(enchantability, baseItem);
            }
        }

        return new ModifiedToolMaterial(baseMaterial.getInverseTag(), durability, miningSpeed, attackDamage, enchantability, baseMaterial.getRepairIngredient());
    }

    public AttributeModifiersComponent createAttributeModifiers(Item baseItem) {
        AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();

        baseItem.getComponents().get(DataComponentTypes.ATTRIBUTE_MODIFIERS).modifiers().forEach(entry -> {
            EntityAttributeModifier modifier = entry.modifier();
            if (entry.attribute().equals(EntityAttributes.GENERIC_ATTACK_SPEED) || entry.attribute().equals(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
                double[] value = {modifier.value()};
                this.modifiers.forEach(gildModifier -> {
                    if (gildModifier.modifiesAttribute(entry.attribute()) && gildModifier.shouldApplyToTool(baseItem)) {
                        value[0] = gildModifier.apply((float) value[0]);
                    }
                });
                modifier = new EntityAttributeModifier(modifier.uuid(), modifier.name(), value[0], modifier.operation());
            }
            builder.add(entry.attribute(), modifier, entry.slot());
        });

        this.appendAttributeModifiers(builder, baseItem);
        return builder.build();
    }

    public final void appendAttributeModifiers(AttributeModifiersComponent.Builder builder, Item baseItem) {
        this.modifiers.forEach( modifier -> {
            if (!modifier.shouldBeAppended() || !modifier.shouldApplyToTool(baseItem)) return;
            RegistryEntry<EntityAttribute> attribute = modifier.type.getAttributeType();
            if (attribute != null) {
                builder.add(attribute, new EntityAttributeModifier(modifier.type.getAttributeId(), "Tool Modifier", modifier.value, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND);
            }
        } );
    }

    public ToolMaterial getModifiedMaterial(ToolItem item) {
        return getModifiedMaterial(item.getMaterial(), item);
    }

    public final String buildTooltipTranslationKey() {
        return "gild_type." + this.id.getNamespace() + "." + this.id.getPath();
    }

    /**
     * Gets the color of the gild type's tooltip text
     *
     * @return An integer representing the color
     */
    public int getColor() {
        return color;
    }

    /**
     * Gets the gild type's id
     *
     * @return the identifier of the gild type
     */
    public Identifier getId() {
        return this.id;
    }

    /**
     * Determines if the gild type modifiers will modify the base material
     *
     * @return 'true' if the modifiers do modify the base material
     */
    private boolean shouldModifyBaseMaterial() {
        for (Modifier modifier : this.modifiers) {
            if (modifier.type.modifiesBaseMaterial) return true;
        }
        return false;
    }


    /**
     * Gets or creates a tag associated with this gild type
     */
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

    @Override
    public String toString() {
        return this.id.toString();
    }

    protected enum ModifierType {
        DURABILITY,
        MINING_SPEED,
        ATTACK_DAMAGE,
        ENCHANTABILITY,
        ATTACK_SPEED(false),
        BLOCK_INTERACTION_RANGE(false),
        ENTITY_INTERACTION_RANGE(false);

        protected enum ToolType {
            SWORD,
            SHOVEL,
            PICKAXE,
            AXE,
            HOE;

            public boolean matches(Item item) {
                return switch (this) {
                    case SWORD -> item instanceof SwordItem;
                    case SHOVEL -> item instanceof ShovelItem;
                    case PICKAXE -> item instanceof PickaxeItem;
                    case AXE -> item instanceof AxeItem;
                    case HOE -> item instanceof HoeItem;
                };
            }
        }

        private final boolean modifiesBaseMaterial;

        ModifierType(boolean modifiesBaseMaterial) {
            this.modifiesBaseMaterial = modifiesBaseMaterial;
        }

        @Nullable
        public RegistryEntry<EntityAttribute> getAttributeType() {
            return switch (this) {
                case BLOCK_INTERACTION_RANGE -> EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE;
                case ENTITY_INTERACTION_RANGE -> EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE;
                case ATTACK_SPEED -> EntityAttributes.GENERIC_ATTACK_SPEED;
                case ATTACK_DAMAGE -> EntityAttributes.GENERIC_ATTACK_DAMAGE;
                default -> null;
            };
        }

        public UUID getAttributeId() {
            return switch (this) {
                case BLOCK_INTERACTION_RANGE -> UUID.fromString("C39B0D39-EB64-45E9-A58E-52DA390DD1A2");
                case ENTITY_INTERACTION_RANGE -> UUID.fromString("D4786B0F-DF61-45D8-B77C-E5B55C4F4066");
                case ATTACK_SPEED -> UUID.fromString("68701EAF-5C43-42E8-95F0-BAA0E3E2438A");
                case ATTACK_DAMAGE -> UUID.fromString("F09A0E0B-32E7-407C-A4E6-17E9F5EB9C2B");
                default -> UUID.fromString("0-0-0-0-0");
            };
        }

        public boolean shouldBeAppended() {
            return !this.equals(ATTACK_SPEED) && !this.equals(ATTACK_DAMAGE);
        }

        ModifierType() {
            this.modifiesBaseMaterial = true;
        }
    }

    public static class ModifiedToolMaterial implements ToolMaterial {
        private final TagKey<Block> inverseTag;
        private final int itemDurability;
        private final float miningSpeed;
        private final float attackDamage;
        private final int enchantability;
        private final Ingredient repairIngredient;
        
        public ModifiedToolMaterial(TagKey<Block> inverseTag, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Ingredient repairIngredient) {
            this.inverseTag = inverseTag;
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
        public TagKey<Block> getInverseTag() {
            return this.inverseTag;
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
