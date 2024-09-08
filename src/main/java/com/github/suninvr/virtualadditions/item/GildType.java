package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

public class GildType {
    public static final Identifier GILDED_TOOL_BLOCK_INTERACTION_RANGE_MODIFIER_ID = VirtualAdditions.idOf("gilded_tool_block_interaction_range");
    public static final Identifier GILDED_TOOL_ENTITY_INTERACTION_RANGE_MODIFIER_ID = VirtualAdditions.idOf("gilded_tool_entity_interaction_range");
    private final ArrayList<Modifier> modifiers = new ArrayList<>();
    private final Identifier id;
    private final int color;
    private TagKey<Item> tag;
    private TagKey<Item> axesTag;
    private TagKey<Item> hoesTag;
    private TagKey<Item> pickaxesTag;
    private TagKey<Item> shovelsTag;
    private TagKey<Item> swordsTag;
    public record Modifier(Identifier id, ModifierType type, float value, BiFunction<Float, Float, Float> function, ModifierType.ToolType... appliesTo){

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
     * @param baseMaterial the tool material to apply this gild type's modifications to
     * @return a new tool material with modifiers applied
     **/
    public final ModifiedToolMaterial getModifiedMaterial(ToolMaterial baseMaterial) {
        return new ModifiedToolMaterial(baseMaterial, this.modifiers);
    }

    public AttributeModifiersComponent createAttributeModifiers(Item baseItem) {
        AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();

        baseItem.getComponents().get(DataComponentTypes.ATTRIBUTE_MODIFIERS).modifiers().forEach(entry -> {
            EntityAttributeModifier modifier = entry.modifier();
            if (entry.attribute().equals(EntityAttributes.ATTACK_SPEED) || entry.attribute().equals(EntityAttributes.ATTACK_DAMAGE)) {
                double[] value = {modifier.value()};
                this.modifiers.forEach(gildModifier -> {
                    if (gildModifier.modifiesAttribute(entry.attribute()) && gildModifier.shouldApplyToTool(baseItem)) {
                        value[0] = gildModifier.apply((float) value[0]);
                    }
                });
                modifier = new EntityAttributeModifier(entry.modifier().id(), value[0], modifier.operation());
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
                builder.add(attribute, new EntityAttributeModifier(modifier.id, modifier.value, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND);
            }
        } );
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
                case BLOCK_INTERACTION_RANGE -> EntityAttributes.BLOCK_INTERACTION_RANGE;
                case ENTITY_INTERACTION_RANGE -> EntityAttributes.ENTITY_INTERACTION_RANGE;
                case ATTACK_SPEED -> EntityAttributes.ATTACK_SPEED;
                case ATTACK_DAMAGE -> EntityAttributes.ATTACK_DAMAGE;
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

    public static class ModifiedToolMaterial {
        private final ToolMaterial baseMaterial;
        private final TagKey<Block> inverseTag;
        private int itemDurability;
        private float miningSpeed;
        private float attackDamageBonus;
        private int enchantability;
        private float blockInteractionRange = 0.0f;
        private float entityInteractionRange = 0.0f;
        private float attackSpeed = 0.0f;
        private final TagKey<Item> repairItems;
        
        public ModifiedToolMaterial(ToolMaterial baseMaterial, List<Modifier> modifiers) {
            this.baseMaterial = baseMaterial;
            this.inverseTag = baseMaterial.incorrectBlocksForDrops();
            this.itemDurability = baseMaterial.durability();
            this.miningSpeed = baseMaterial.speed();
            this.attackDamageBonus = baseMaterial.attackDamageBonus();
            this.enchantability = baseMaterial.enchantmentValue();
            this.repairItems = baseMaterial.repairItems();
            this.applyModifiers(modifiers);
        }

        private void applyModifiers(List<Modifier> modifiers) {
            modifiers.forEach(modifier -> {
                switch (modifier.type()) {
                    case DURABILITY -> itemDurability = modifier.apply(itemDurability);
                    case MINING_SPEED -> miningSpeed = modifier.apply(miningSpeed);
                    case ATTACK_DAMAGE -> attackDamageBonus = modifier.apply(attackDamageBonus);
                    case ENCHANTABILITY -> enchantability = modifier.apply(enchantability);
                    case BLOCK_INTERACTION_RANGE -> blockInteractionRange = modifier.apply(blockInteractionRange);
                    case ENTITY_INTERACTION_RANGE -> entityInteractionRange = modifier.apply(entityInteractionRange);
                    case ATTACK_SPEED -> attackSpeed = modifier.apply(attackSpeed);
                }
            });
        }

        public ToolMaterial asToolMaterial() {
            return new ToolMaterial(this.inverseTag, this.itemDurability, this.miningSpeed, this.attackDamageBonus, this.enchantability, this.repairItems);
        }

        public ToolMaterial getBaseMaterial() {
            return baseMaterial;
        }

        private Item.Settings applyBaseSettings(Item.Settings settings) {
            return settings.maxDamage(this.itemDurability).repairable(this.repairItems).enchantable(this.enchantability);
        }

        public Item.Settings applyToolSettings(Item.Settings settings, TagKey<Block> effectiveBlocks, float attackDamage, float attackSpeed) {
            RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
            return this.applyBaseSettings(settings).component(DataComponentTypes.TOOL, new ToolComponent(List.of(ToolComponent.Rule.ofNeverDropping(registryEntryLookup.getOrThrow(baseMaterial.incorrectBlocksForDrops())), ToolComponent.Rule.ofAlwaysDropping(registryEntryLookup.getOrThrow(effectiveBlocks), this.miningSpeed)), 1.0F, 1)).attributeModifiers(this.createToolAttributeModifiers(attackDamage, attackSpeed));
        }

        private AttributeModifiersComponent createToolAttributeModifiers(float attackDamage, float attackSpeed) {
            AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
            builder.add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage + this.attackDamageBonus, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND);
            builder.add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed + this.attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND);
            if (!(this.blockInteractionRange == 0)) builder.add(EntityAttributes.BLOCK_INTERACTION_RANGE, new EntityAttributeModifier(GILDED_TOOL_BLOCK_INTERACTION_RANGE_MODIFIER_ID, this.blockInteractionRange, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND);
            if (!(this.entityInteractionRange == 0)) builder.add(EntityAttributes.ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(GILDED_TOOL_ENTITY_INTERACTION_RANGE_MODIFIER_ID, this.entityInteractionRange, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND);
            return builder.build();
        }
    }
}
