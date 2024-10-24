package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.component.ExplosiveContentComponent;
import com.github.suninvr.virtualadditions.component.WarpTetherLocationComponent;
import com.github.suninvr.virtualadditions.item.*;
import com.github.suninvr.virtualadditions.item.materials.SteelToolMaterial;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.dispenser.BlockPlacementDispenserBehavior;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;
import static com.github.suninvr.virtualadditions.registry.RegistryHelper.ItemRegistryHelper.*;

public class VAItems {
    //region Declarations

    public static final ToolSet DIAMOND_TOOL_SET;
    public static final ToolSet GOLDEN_TOOL_SET;
    public static final ToolSet IRON_TOOL_SET;
    public static final ToolSet NETHERITE_TOOL_SET;
    public static final ToolSet STEEL_TOOL_SET;
    public static final Item CLIMBING_ROPE;
    public static final Item EXPOSED_CLIMBING_ROPE;
    public static final Item WEATHERED_CLIMBING_ROPE;
    public static final Item OXIDIZED_CLIMBING_ROPE;
    public static final Item WAXED_CLIMBING_ROPE;
    public static final Item WAXED_EXPOSED_CLIMBING_ROPE;
    public static final Item WAXED_WEATHERED_CLIMBING_ROPE;
    public static final Item WAXED_OXIDIZED_CLIMBING_ROPE;
    public static final Item ENGRAVING_CHISEL;
    public static final Item HORNFELS;
    public static final Item COBBLED_HORNFELS;
    public static final Item COBBLED_HORNFELS_STAIRS;
    public static final Item COBBLED_HORNFELS_SLAB;
    public static final Item COBBLED_HORNFELS_WALL;
    public static final Item POLISHED_HORNFELS;
    public static final Item POLISHED_HORNFELS_STAIRS;
    public static final Item POLISHED_HORNFELS_SLAB;
    public static final Item HORNFELS_TILES;
    public static final Item CRACKED_HORNFELS_TILES;
    public static final Item HORNFELS_TILE_STAIRS;
    public static final Item HORNFELS_TILE_SLAB;
    public static final Item CHISELED_HORNFELS;
    public static final Item CHISELED_HORNFELS_TILES;
    public static final Item BLUESCHIST;
    public static final Item COBBLED_BLUESCHIST;
    public static final Item COBBLED_BLUESCHIST_STAIRS;
    public static final Item COBBLED_BLUESCHIST_SLAB;
    public static final Item COBBLED_BLUESCHIST_WALL;
    public static final Item POLISHED_BLUESCHIST;
    public static final Item POLISHED_BLUESCHIST_STAIRS;
    public static final Item POLISHED_BLUESCHIST_SLAB;
    public static final Item BLUESCHIST_BRICKS;
    public static final Item CRACKED_BLUESCHIST_BRICKS;
    public static final Item BLUESCHIST_BRICK_STAIRS;
    public static final Item BLUESCHIST_BRICK_SLAB;
    public static final Item BLUESCHIST_BRICK_WALL;
    public static final Item CHISELED_BLUESCHIST;
    public static final Item SYENITE;
    public static final Item COBBLED_SYENITE;
    public static final Item COBBLED_SYENITE_STAIRS;
    public static final Item COBBLED_SYENITE_SLAB;
    public static final Item COBBLED_SYENITE_WALL;
    public static final Item POLISHED_SYENITE;
    public static final Item POLISHED_SYENITE_STAIRS;
    public static final Item POLISHED_SYENITE_SLAB;
    public static final Item SYENITE_BRICKS;
    public static final Item CRACKED_SYENITE_BRICKS;
    public static final Item SYENITE_BRICK_STAIRS;
    public static final Item SYENITE_BRICK_SLAB;
    public static final Item SYENITE_BRICK_WALL;
    public static final Item CHISELED_SYENITE;
    public static final Item FLOATROCK;
    public static final Item GRASSY_FLOATROCK;
    public static final Item FLOATROCK_STAIRS;
    public static final Item FLOATROCK_SLAB;
    public static final Item FLOATROCK_WALL;
    public static final Item FLOATROCK_BRICKS;
    public static final Item FLOATROCK_BRICK_STAIRS;
    public static final Item FLOATROCK_BRICK_SLAB;
    public static final Item FLOATROCK_BRICK_WALL;
    public static final Item POLISHED_FLOATROCK;
    public static final Item POLISHED_FLOATROCK_STAIRS;
    public static final Item POLISHED_FLOATROCK_SLAB;
    public static final Item POLISHED_FLOATROCK_WALL;
    public static final Item FLOATROCK_COAL_ORE;
    public static final Item FLOATROCK_IRON_ORE;
    public static final Item FLOATROCK_COPPER_ORE;
    public static final Item FLOATROCK_GOLD_ORE;
    public static final Item FLOATROCK_REDSTONE_ORE;
    public static final Item FLOATROCK_EMERALD_ORE;
    public static final Item FLOATROCK_LAPIS_ORE;
    public static final Item FLOATROCK_DIAMOND_ORE;
    public static final Item SPRINGSOIL;
    public static final Item AEROBLOOM_LOG;
    public static final Item AEROBLOOM_WOOD;
    public static final Item STRIPPED_AEROBLOOM_LOG;
    public static final Item STRIPPED_AEROBLOOM_WOOD;
    public static final Item AEROBLOOM_PLANKS;
    public static final Item AEROBLOOM_STAIRS;
    public static final Item AEROBLOOM_SLAB;
    public static final Item AEROBLOOM_FENCE;
    public static final Item AEROBLOOM_FENCE_GATE;
    public static final Item AEROBLOOM_DOOR;
    public static final Item AEROBLOOM_TRAPDOOR;
    public static final Item AEROBLOOM_PRESSURE_PLATE;
    public static final Item AEROBLOOM_BUTTON;
    public static final Item AEROBLOOM_SIGN;
    public static final Item AEROBLOOM_HANGING_SIGN;
    public static final Item AEROBLOOM_LEAVES;
    public static final Item AEROBLOOM_HEDGE;
    public static final Item AEROBLOOM_SAPLING;
    public static final Item BALLOON_FRUIT;
    public static final Item OAK_HEDGE;
    public static final Item SPRUCE_HEDGE;
    public static final Item BIRCH_HEDGE;
    public static final Item JUNGLE_HEDGE;
    public static final Item ACACIA_HEDGE;
    public static final Item DARK_OAK_HEDGE;
    public static final Item PALE_OAK_HEDGE;
    public static final Item MANGROVE_HEDGE;
    public static final Item CHERRY_HEDGE;
    public static final Item AZALEA_HEDGE;
    public static final Item FLOWERING_AZALEA_HEDGE;
    public static final Item ROCK_SALT_BLOCK;
    public static final Item ROCK_SALT_CRYSTAL;
    public static final Item ROCK_SALT;
    public static final Item SPOTLIGHT;
    public static final Item RAW_STEEL;
    public static final Item RAW_STEEL_BLOCK;
    public static final Item STEEL_INGOT;
    public static final Item STEEL_BLOCK;
    public static final Item CUT_STEEL;
    public static final Item CUT_STEEL_STAIRS;
    public static final Item CUT_STEEL_SLAB;
    public static final Item STEEL_GRATE;
    public static final Item CHISELED_STEEL;
    public static final Item EXPOSED_CHISELED_STEEL;
    public static final Item WEATHERED_CHISELED_STEEL;
    public static final Item OXIDIZED_CHISELED_STEEL;
    public static final Item WAXED_CHISELED_STEEL;
    public static final Item WAXED_EXPOSED_CHISELED_STEEL;
    public static final Item WAXED_WEATHERED_CHISELED_STEEL;
    public static final Item WAXED_OXIDIZED_CHISELED_STEEL;
    public static final Item STEEL_FENCE;
    public static final Item EXPOSED_STEEL_FENCE;
    public static final Item WEATHERED_STEEL_FENCE;
    public static final Item OXIDIZED_STEEL_FENCE;
    public static final Item WAXED_STEEL_FENCE;
    public static final Item WAXED_EXPOSED_STEEL_FENCE;
    public static final Item WAXED_WEATHERED_STEEL_FENCE;
    public static final Item WAXED_OXIDIZED_STEEL_FENCE;
    public static final Item STEEL_DOOR;
    public static final Item EXPOSED_STEEL_DOOR;
    public static final Item WEATHERED_STEEL_DOOR;
    public static final Item OXIDIZED_STEEL_DOOR;
    public static final Item WAXED_STEEL_DOOR;
    public static final Item WAXED_EXPOSED_STEEL_DOOR;
    public static final Item WAXED_WEATHERED_STEEL_DOOR;
    public static final Item WAXED_OXIDIZED_STEEL_DOOR;
    public static final Item STEEL_TRAPDOOR;
    public static final Item EXPOSED_STEEL_TRAPDOOR;
    public static final Item WEATHERED_STEEL_TRAPDOOR;
    public static final Item OXIDIZED_STEEL_TRAPDOOR;
    public static final Item WAXED_STEEL_TRAPDOOR;
    public static final Item WAXED_EXPOSED_STEEL_TRAPDOOR;
    public static final Item WAXED_WEATHERED_STEEL_TRAPDOOR;
    public static final Item WAXED_OXIDIZED_STEEL_TRAPDOOR;
    public static final Item REDSTONE_BRIDGE;
    public static final Item CAGELIGHT;
    public static final Item STEEL_BOMB;
    public static final Item STEEL_SWORD;
    public static final Item STEEL_SHOVEL;
    public static final Item STEEL_PICKAXE;
    public static final Item STEEL_AXE;
    public static final Item STEEL_HOE;
    public static final Item STEEL_HELMET;
    public static final Item STEEL_CHESTPLATE;
    public static final Item STEEL_LEGGINGS;
    public static final Item STEEL_BOOTS;
    public static final Item EXPOSED_STEEL_BLOCK;
    public static final Item EXPOSED_CUT_STEEL;
    public static final Item EXPOSED_CUT_STEEL_STAIRS;
    public static final Item EXPOSED_CUT_STEEL_SLAB;
    public static final Item EXPOSED_STEEL_GRATE;
    public static final Item WEATHERED_STEEL_BLOCK;
    public static final Item WEATHERED_CUT_STEEL;
    public static final Item WEATHERED_CUT_STEEL_STAIRS;
    public static final Item WEATHERED_CUT_STEEL_SLAB;
    public static final Item WEATHERED_STEEL_GRATE;
    public static final Item OXIDIZED_STEEL_BLOCK;
    public static final Item OXIDIZED_CUT_STEEL;
    public static final Item OXIDIZED_CUT_STEEL_STAIRS;
    public static final Item OXIDIZED_CUT_STEEL_SLAB;
    public static final Item OXIDIZED_STEEL_GRATE;
    public static final Item WAXED_STEEL_BLOCK;
    public static final Item WAXED_CUT_STEEL;
    public static final Item WAXED_CUT_STEEL_STAIRS;
    public static final Item WAXED_CUT_STEEL_SLAB;
    public static final Item WAXED_STEEL_GRATE;
    public static final Item WAXED_EXPOSED_STEEL_BLOCK;
    public static final Item WAXED_EXPOSED_CUT_STEEL;
    public static final Item WAXED_EXPOSED_CUT_STEEL_STAIRS;
    public static final Item WAXED_EXPOSED_CUT_STEEL_SLAB;
    public static final Item WAXED_EXPOSED_STEEL_GRATE;
    public static final Item WAXED_WEATHERED_STEEL_BLOCK;
    public static final Item WAXED_WEATHERED_CUT_STEEL;
    public static final Item WAXED_WEATHERED_CUT_STEEL_STAIRS;
    public static final Item WAXED_WEATHERED_CUT_STEEL_SLAB;
    public static final Item WAXED_WEATHERED_STEEL_GRATE;
    public static final Item WAXED_OXIDIZED_STEEL_BLOCK;
    public static final Item WAXED_OXIDIZED_CUT_STEEL;
    public static final Item WAXED_OXIDIZED_CUT_STEEL_STAIRS;
    public static final Item WAXED_OXIDIZED_CUT_STEEL_SLAB;
    public static final Item WAXED_OXIDIZED_STEEL_GRATE;
    public static final Item COTTON_SEEDS;
    public static final Item COTTON;
    public static final Item CORN;
    public static final Item CORN_SEEDS;
    public static final Item ROASTED_CORN;
    public static final Item FRIED_EGG;
    public static final Item ICE_CREAM;
    public static final Item SWEET_BERRY_PIE;
    public static final Item GLOWING_SILK;
    public static final Item FRAYED_SILK;
    public static final Item TALL_GREENCAP_MUSHROOMS;
    public static final Item SILK_THREAD;
    public static final Item LUMWASP_MANDIBLE;
    public static final Item LUMWASP_NEST;
    public static final Item SILK_BLOCK;
    public static final Item GREENCAP_MUSHROOM;
    public static final Item WEBBED_SILK;
    public static final Item SILKBULB;
    public static final Item WHITE_SILKBULB;
    public static final Item LIGHT_GRAY_SILKBULB;
    public static final Item GRAY_SILKBULB;
    public static final Item BLACK_SILKBULB;
    public static final Item BROWN_SILKBULB;
    public static final Item RED_SILKBULB;
    public static final Item ORANGE_SILKBULB;
    public static final Item YELLOW_SILKBULB;
    public static final Item LIME_SILKBULB;
    public static final Item GREEN_SILKBULB;
    public static final Item CYAN_SILKBULB;
    public static final Item LIGHT_BLUE_SILKBULB;
    public static final Item BLUE_SILKBULB;
    public static final Item PURPLE_SILKBULB;
    public static final Item MAGENTA_SILKBULB;
    public static final Item PINK_SILKBULB;
    public static final Item ACID_BUCKET;
    public static final Item ACID_BLOCK;
    public static final Item APPLICABLE_POTION;
    public static final Item CHARTREUSE_DYE;
    public static final Item CHARTREUSE_WOOL;
    public static final Item CHARTREUSE_CARPET;
    public static final Item CHARTREUSE_TERRACOTTA;
    public static final Item CHARTREUSE_CONCRETE;
    public static final Item CHARTREUSE_CONCRETE_POWDER;
    public static final Item CHARTREUSE_STAINED_GLASS;
    public static final Item CHARTREUSE_STAINED_GLASS_PANE;
    public static final Item CHARTREUSE_CANDLE;
    public static final Item CHARTREUSE_SILKBULB;
    public static final Item CHARTREUSE_BED;
    public static final Item CHARTREUSE_SHULKER_BOX;
    public static final Item CHARTREUSE_BANNER;
    public static final Item CHARTREUSE_BUNDLE;
    public static final Item CHARTREUSE_GLAZED_TERRACOTTA;
    public static final Item MAROON_DYE;
    public static final Item MAROON_WOOL;
    public static final Item MAROON_CARPET;
    public static final Item MAROON_TERRACOTTA;
    public static final Item MAROON_CONCRETE;
    public static final Item MAROON_CONCRETE_POWDER;
    public static final Item MAROON_STAINED_GLASS;
    public static final Item MAROON_STAINED_GLASS_PANE;
    public static final Item MAROON_CANDLE;
    public static final Item MAROON_SILKBULB;
    public static final Item MAROON_BED;
    public static final Item MAROON_SHULKER_BOX;
    public static final Item MAROON_BANNER;
    public static final Item MAROON_BUNDLE;
    public static final Item MAROON_GLAZED_TERRACOTTA;
    public static final Item INDIGO_DYE;
    public static final Item INDIGO_WOOL;
    public static final Item INDIGO_CARPET;
    public static final Item INDIGO_TERRACOTTA;
    public static final Item INDIGO_CONCRETE;
    public static final Item INDIGO_CONCRETE_POWDER;
    public static final Item INDIGO_STAINED_GLASS;
    public static final Item INDIGO_STAINED_GLASS_PANE;
    public static final Item INDIGO_CANDLE;
    public static final Item INDIGO_SILKBULB;
    public static final Item INDIGO_BED;
    public static final Item INDIGO_SHULKER_BOX;
    public static final Item INDIGO_BANNER;
    public static final Item INDIGO_BUNDLE;
    public static final Item INDIGO_GLAZED_TERRACOTTA;
    public static final Item PLUM_DYE;
    public static final Item PLUM_WOOL;
    public static final Item PLUM_CARPET;
    public static final Item PLUM_TERRACOTTA;
    public static final Item PLUM_CONCRETE;
    public static final Item PLUM_CONCRETE_POWDER;
    public static final Item PLUM_STAINED_GLASS;
    public static final Item PLUM_STAINED_GLASS_PANE;
    public static final Item PLUM_CANDLE;
    public static final Item PLUM_SILKBULB;
    public static final Item PLUM_BED;
    public static final Item PLUM_SHULKER_BOX;
    public static final Item PLUM_BANNER;
    public static final Item PLUM_BUNDLE;
    public static final Item PLUM_GLAZED_TERRACOTTA;
    public static final Item VIRIDIAN_DYE;
    public static final Item VIRIDIAN_WOOL;
    public static final Item VIRIDIAN_CARPET;
    public static final Item VIRIDIAN_TERRACOTTA;
    public static final Item VIRIDIAN_CONCRETE;
    public static final Item VIRIDIAN_CONCRETE_POWDER;
    public static final Item VIRIDIAN_STAINED_GLASS;
    public static final Item VIRIDIAN_STAINED_GLASS_PANE;
    public static final Item VIRIDIAN_CANDLE;
    public static final Item VIRIDIAN_SILKBULB;
    public static final Item VIRIDIAN_BED;
    public static final Item VIRIDIAN_SHULKER_BOX;
    public static final Item VIRIDIAN_BANNER;
    public static final Item VIRIDIAN_BUNDLE;
    public static final Item VIRIDIAN_GLAZED_TERRACOTTA;
    public static final Item TAN_DYE;
    public static final Item TAN_WOOL;
    public static final Item TAN_CARPET;
    public static final Item TAN_TERRACOTTA;
    public static final Item TAN_CONCRETE;
    public static final Item TAN_CONCRETE_POWDER;
    public static final Item TAN_STAINED_GLASS;
    public static final Item TAN_STAINED_GLASS_PANE;
    public static final Item TAN_CANDLE;
    public static final Item TAN_SILKBULB;
    public static final Item TAN_BED;
    public static final Item TAN_SHULKER_BOX;
    public static final Item TAN_BANNER;
    public static final Item TAN_BUNDLE;
    public static final Item TAN_GLAZED_TERRACOTTA;
    public static final Item SINOPIA_DYE;
    public static final Item SINOPIA_WOOL;
    public static final Item SINOPIA_CARPET;
    public static final Item SINOPIA_TERRACOTTA;
    public static final Item SINOPIA_CONCRETE;
    public static final Item SINOPIA_CONCRETE_POWDER;
    public static final Item SINOPIA_STAINED_GLASS;
    public static final Item SINOPIA_STAINED_GLASS_PANE;
    public static final Item SINOPIA_CANDLE;
    public static final Item SINOPIA_SILKBULB;
    public static final Item SINOPIA_BED;
    public static final Item SINOPIA_SHULKER_BOX;
    public static final Item SINOPIA_BANNER;
    public static final Item SINOPIA_BUNDLE;
    public static final Item SINOPIA_GLAZED_TERRACOTTA;
    public static final Item LILAC_DYE;
    public static final Item LILAC_WOOL;
    public static final Item LILAC_CARPET;
    public static final Item LILAC_TERRACOTTA;
    public static final Item LILAC_CONCRETE;
    public static final Item LILAC_CONCRETE_POWDER;
    public static final Item LILAC_STAINED_GLASS;
    public static final Item LILAC_STAINED_GLASS_PANE;
    public static final Item LILAC_CANDLE;
    public static final Item LILAC_SILKBULB;
    public static final Item LILAC_BED;
    public static final Item LILAC_SHULKER_BOX;
    public static final Item LILAC_BANNER;
    public static final Item LILAC_BUNDLE;
    public static final Item LILAC_GLAZED_TERRACOTTA;
    public static final Item COLORING_STATION;
    public static final Item TOOL_GILD_SMITHING_TEMPLATE;
    public static final Item EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE;
    public static final Item IOLITE;
    public static final Item IOLITE_ORE;
    public static final Item IOLITE_BLOCK;
    public static final Item WARP_ANCHOR;
    public static final Item WARP_TETHER;
    public static final Item ENTANGLEMENT_DRIVE;
    public static final Item REMOTE_NOTIFIER;
    public static final Item SALINE_SPAWN_EGG;
    public static final Item LUMWASP_SPAWN_EGG;
    public static final Item LYFT_SPAWN_EGG;

    public static final FoodComponent FRIED_EGG_FOOD = (new FoodComponent.Builder().nutrition(4).saturationModifier(0.4F).build());
    public static final FoodComponent CORN_FOOD = (new FoodComponent.Builder()).nutrition(1).saturationModifier(0.3F).build();
    public static final FoodComponent ROASTED_CORN_FOOD = (new FoodComponent.Builder()).nutrition(5).saturationModifier(0.6F).build();
    public static final FoodComponent ICE_CREAM_FOOD = new FoodComponent.Builder().nutrition(7).saturationModifier(0.1F).build();
    public static final FoodComponent BALLOON_FRUIT_FOOD = (new FoodComponent.Builder().nutrition(2).saturationModifier(0.1F).alwaysEdible().build());
    public static final FoodComponent SWEET_BERRY_PIE_FOOD = (new FoodComponent.Builder().nutrition(8).saturationModifier(0.3F).build());

    public static final ToolSet AMETHYST_DIAMOND_TOOL_SET;
    public static final ToolSet COPPER_DIAMOND_TOOL_SET;
    public static final ToolSet EMERALD_DIAMOND_TOOL_SET;
    public static final ToolSet IOLITE_DIAMOND_TOOL_SET;
    public static final ToolSet QUARTZ_DIAMOND_TOOL_SET;
    public static final ToolSet SCULK_DIAMOND_TOOL_SET;
    public static final ToolSet AMETHYST_IRON_TOOL_SET;
    public static final ToolSet COPPER_IRON_TOOL_SET ;
    public static final ToolSet EMERALD_IRON_TOOL_SET;
    public static final ToolSet IOLITE_IRON_TOOL_SET;
    public static final ToolSet QUARTZ_IRON_TOOL_SET;
    public static final ToolSet SCULK_IRON_TOOL_SET;
    public static final ToolSet AMETHYST_GOLDEN_TOOL_SET;
    public static final ToolSet COPPER_GOLDEN_TOOL_SET;
    public static final ToolSet EMERALD_GOLDEN_TOOL_SET;
    public static final ToolSet IOLITE_GOLDEN_TOOL_SET;
    public static final ToolSet QUARTZ_GOLDEN_TOOL_SET;
    public static final ToolSet SCULK_GOLDEN_TOOL_SET;
    public static final ToolSet AMETHYST_NETHERITE_TOOL_SET;
    public static final ToolSet COPPER_NETHERITE_TOOL_SET;
    public static final ToolSet EMERALD_NETHERITE_TOOL_SET;
    public static final ToolSet IOLITE_NETHERITE_TOOL_SET;
    public static final ToolSet QUARTZ_NETHERITE_TOOL_SET;
    public static final ToolSet SCULK_NETHERITE_TOOL_SET;
    public static final ToolSet AMETHYST_STEEL_TOOL_SET;
    public static final ToolSet COPPER_STEEL_TOOL_SET;
    public static final ToolSet EMERALD_STEEL_TOOL_SET;
    public static final ToolSet IOLITE_STEEL_TOOL_SET;
    public static final ToolSet QUARTZ_STEEL_TOOL_SET;
    public static final ToolSet SCULK_STEEL_TOOL_SET;
    
    public static final ToolSet[] AMETHYST_TOOL_SETS;
    public static final ToolSet[] COPPER_TOOL_SETS;
    public static final ToolSet[] EMERALD_TOOL_SETS;
    public static final ToolSet[] IOLITE_TOOL_SETS;
    public static final ToolSet[] QUARTZ_TOOL_SETS;
    public static final ToolSet[] SCULK_TOOL_SETS;

    private static final Text TOOL_GILD_APPLIES_TO_TEXT;
    private static final Text TOOL_GILD_INGREDIENTS_TEXT;
    private static final Text TOOL_GILD_BASE_SLOT_DESCRIPTION_TEXT;
    private static final Text TOOL_GILD_ADDITIONS_SLOT_DESCRIPTION_TEXT;
    private static final Identifier EMPTY_SLOT_HOE_TEXTURE;
    private static final Identifier EMPTY_SLOT_AXE_TEXTURE;
    private static final Identifier EMPTY_SLOT_SWORD_TEXTURE;
    private static final Identifier EMPTY_SLOT_SHOVEL_TEXTURE;
    private static final Identifier EMPTY_SLOT_PICKAXE_TEXTURE;
    private static final Identifier EMPTY_SLOT_INGOT_TEXTURE;
    private static final Identifier EMPTY_SLOT_QUARTZ_TEXTURE;
    private static final Identifier EMPTY_SLOT_EMERALD_TEXTURE;
    private static final Identifier EMPTY_SLOT_AMETHYST_TEXTURE;
    private static final Identifier EMPTY_SLOT_ECHO_SHARD_TEXTURE;
    private static final Identifier EMPTY_SLOT_IOLITE_TEXTURE;

    //endregion

    static {
        //region Identifiers

        TOOL_GILD_APPLIES_TO_TEXT = Text.translatable(Util.createTranslationKey("item", idOf("smithing_template.tool_gild.applies_to"))).formatted(Formatting.BLUE);
        TOOL_GILD_INGREDIENTS_TEXT = Text.translatable(Util.createTranslationKey("item", idOf("smithing_template.tool_gild.ingredients"))).formatted(Formatting.BLUE);
        TOOL_GILD_BASE_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", idOf("smithing_template.tool_gild.base_slot_description")));
        TOOL_GILD_ADDITIONS_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", idOf("smithing_template.tool_gild.additions_slot_description")));

        EMPTY_SLOT_HOE_TEXTURE = Identifier.ofVanilla("item/empty_slot_hoe");
        EMPTY_SLOT_AXE_TEXTURE = Identifier.ofVanilla("item/empty_slot_axe");
        EMPTY_SLOT_SWORD_TEXTURE = Identifier.ofVanilla("item/empty_slot_sword");
        EMPTY_SLOT_SHOVEL_TEXTURE = Identifier.ofVanilla("item/empty_slot_shovel");
        EMPTY_SLOT_PICKAXE_TEXTURE = Identifier.ofVanilla("item/empty_slot_pickaxe");
        EMPTY_SLOT_INGOT_TEXTURE = Identifier.ofVanilla("item/empty_slot_ingot");
        EMPTY_SLOT_QUARTZ_TEXTURE = Identifier.ofVanilla("item/empty_slot_quartz");
        EMPTY_SLOT_EMERALD_TEXTURE = Identifier.ofVanilla("item/empty_slot_emerald");
        EMPTY_SLOT_AMETHYST_TEXTURE = Identifier.ofVanilla("item/empty_slot_amethyst_shard");
        EMPTY_SLOT_ECHO_SHARD_TEXTURE = Identifier.of("virtual_additions:item/empty_slot_echo_shard");
        EMPTY_SLOT_IOLITE_TEXTURE = Identifier.of("virtual_additions:item/empty_slot_iolite");

        //endregion

        //region Deep Stone Variants

        //region Hornfels

        HORNFELS = registerBlockItem("hornfels", VABlocks.HORNFELS, new ItemGroupLocation(ItemGroups.NATURAL, Items.DEEPSLATE), new ItemGroupLocation(ItemGroups.BUILDING_BLOCKS, Items.DEEPSLATE_TILE_WALL));
        COBBLED_HORNFELS = registerBlockItem("cobbled_hornfels", VABlocks.COBBLED_HORNFELS, ItemGroups.BUILDING_BLOCKS, HORNFELS);
        COBBLED_HORNFELS_STAIRS = registerBlockItem("cobbled_hornfels_stairs", VABlocks.COBBLED_HORNFELS_STAIRS, ItemGroups.BUILDING_BLOCKS, COBBLED_HORNFELS);
        COBBLED_HORNFELS_SLAB = registerBlockItem("cobbled_hornfels_slab", VABlocks.COBBLED_HORNFELS_SLAB, ItemGroups.BUILDING_BLOCKS, COBBLED_HORNFELS_STAIRS);
        COBBLED_HORNFELS_WALL = registerBlockItem("cobbled_hornfels_wall", VABlocks.COBBLED_HORNFELS_WALL, ItemGroups.BUILDING_BLOCKS, COBBLED_HORNFELS_SLAB);
        POLISHED_HORNFELS = registerBlockItem("polished_hornfels", VABlocks.POLISHED_HORNFELS, ItemGroups.BUILDING_BLOCKS, COBBLED_HORNFELS_WALL);
        POLISHED_HORNFELS_STAIRS = registerBlockItem("polished_hornfels_stairs", VABlocks.POLISHED_HORNFELS_STAIRS, ItemGroups.BUILDING_BLOCKS, POLISHED_HORNFELS);
        POLISHED_HORNFELS_SLAB = registerBlockItem("polished_hornfels_slab", VABlocks.POLISHED_HORNFELS_SLAB, ItemGroups.BUILDING_BLOCKS, POLISHED_HORNFELS_STAIRS);
        HORNFELS_TILES = registerBlockItem("hornfels_tiles", VABlocks.HORNFELS_TILES, ItemGroups.BUILDING_BLOCKS, POLISHED_HORNFELS_SLAB);
        CRACKED_HORNFELS_TILES = registerBlockItem("cracked_hornfels_tiles", VABlocks.CRACKED_HORNFELS_TILES, ItemGroups.BUILDING_BLOCKS, HORNFELS_TILES);
        HORNFELS_TILE_STAIRS = registerBlockItem("hornfels_tile_stairs", VABlocks.HORNFELS_TILE_STAIRS, ItemGroups.BUILDING_BLOCKS, CRACKED_HORNFELS_TILES);
        HORNFELS_TILE_SLAB = registerBlockItem("hornfels_tile_slab", VABlocks.HORNFELS_TILE_SLAB, ItemGroups.BUILDING_BLOCKS, HORNFELS_TILE_STAIRS);
        CHISELED_HORNFELS = registerBlockItem("chiseled_hornfels", VABlocks.CHISELED_HORNFELS, ItemGroups.BUILDING_BLOCKS, COBBLED_HORNFELS_WALL);
        CHISELED_HORNFELS_TILES = registerBlockItem("chiseled_hornfels_tiles", VABlocks.CHISELED_HORNFELS_TILES, ItemGroups.BUILDING_BLOCKS, HORNFELS_TILE_SLAB);

        //endregion

        //region Blueschist

        BLUESCHIST = registerBlockItem("blueschist", VABlocks.BLUESCHIST, new ItemGroupLocation(ItemGroups.NATURAL, HORNFELS), new ItemGroupLocation(ItemGroups.BUILDING_BLOCKS, prev));
        COBBLED_BLUESCHIST = registerBlockItem("cobbled_blueschist", VABlocks.COBBLED_BLUESCHIST, ItemGroups.BUILDING_BLOCKS, BLUESCHIST);
        COBBLED_BLUESCHIST_STAIRS = registerBlockItem("cobbled_blueschist_stairs", VABlocks.COBBLED_BLUESCHIST_STAIRS, ItemGroups.BUILDING_BLOCKS, COBBLED_BLUESCHIST);
        COBBLED_BLUESCHIST_SLAB = registerBlockItem("cobbled_blueschist_slab", VABlocks.COBBLED_BLUESCHIST_SLAB, ItemGroups.BUILDING_BLOCKS, COBBLED_BLUESCHIST_STAIRS);
        COBBLED_BLUESCHIST_WALL = registerBlockItem("cobbled_blueschist_wall", VABlocks.COBBLED_BLUESCHIST_WALL, ItemGroups.BUILDING_BLOCKS, COBBLED_BLUESCHIST_SLAB);
        POLISHED_BLUESCHIST = registerBlockItem("polished_blueschist", VABlocks.POLISHED_BLUESCHIST, ItemGroups.BUILDING_BLOCKS, COBBLED_BLUESCHIST_WALL);
        POLISHED_BLUESCHIST_STAIRS = registerBlockItem("polished_blueschist_stairs", VABlocks.POLISHED_BLUESCHIST_STAIRS, ItemGroups.BUILDING_BLOCKS, POLISHED_BLUESCHIST);
        POLISHED_BLUESCHIST_SLAB = registerBlockItem("polished_blueschist_slab", VABlocks.POLISHED_BLUESCHIST_SLAB, ItemGroups.BUILDING_BLOCKS, POLISHED_BLUESCHIST_STAIRS);
        BLUESCHIST_BRICKS = registerBlockItem("blueschist_bricks", VABlocks.BLUESCHIST_BRICKS, ItemGroups.BUILDING_BLOCKS, POLISHED_BLUESCHIST_SLAB);
        CRACKED_BLUESCHIST_BRICKS = registerBlockItem("cracked_blueschist_bricks", VABlocks.CRACKED_BLUESCHIST_BRICKS, ItemGroups.BUILDING_BLOCKS, BLUESCHIST_BRICKS);
        BLUESCHIST_BRICK_STAIRS = registerBlockItem("blueschist_brick_stairs", VABlocks.BLUESCHIST_BRICK_STAIRS, ItemGroups.BUILDING_BLOCKS, CRACKED_BLUESCHIST_BRICKS);
        BLUESCHIST_BRICK_SLAB = registerBlockItem("blueschist_brick_slab", VABlocks.BLUESCHIST_BRICK_SLAB, ItemGroups.BUILDING_BLOCKS, BLUESCHIST_BRICK_STAIRS);
        BLUESCHIST_BRICK_WALL = registerBlockItem("blueschist_brick_wall", VABlocks.BLUESCHIST_BRICK_WALL, ItemGroups.BUILDING_BLOCKS, BLUESCHIST_BRICK_SLAB);
        CHISELED_BLUESCHIST = registerBlockItem("chiseled_blueschist", VABlocks.CHISELED_BLUESCHIST, ItemGroups.BUILDING_BLOCKS, prev);

        //endregion

        //region Syenite

        SYENITE = registerBlockItem("syenite", VABlocks.SYENITE, new ItemGroupLocation(ItemGroups.NATURAL, BLUESCHIST), new ItemGroupLocation(ItemGroups.BUILDING_BLOCKS, prev));
        COBBLED_SYENITE = registerBlockItem("cobbled_syenite", VABlocks.COBBLED_SYENITE, ItemGroups.BUILDING_BLOCKS, SYENITE);
        COBBLED_SYENITE_STAIRS = registerBlockItem("cobbled_syenite_stairs", VABlocks.COBBLED_SYENITE_STAIRS, ItemGroups.BUILDING_BLOCKS, COBBLED_SYENITE);
        COBBLED_SYENITE_SLAB = registerBlockItem("cobbled_syenite_slab", VABlocks.COBBLED_SYENITE_SLAB, ItemGroups.BUILDING_BLOCKS, COBBLED_SYENITE_STAIRS);
        COBBLED_SYENITE_WALL = registerBlockItem("cobbled_syenite_wall", VABlocks.COBBLED_SYENITE_WALL, ItemGroups.BUILDING_BLOCKS, COBBLED_SYENITE_SLAB);
        POLISHED_SYENITE = registerBlockItem("polished_syenite", VABlocks.POLISHED_SYENITE, ItemGroups.BUILDING_BLOCKS, COBBLED_SYENITE_WALL);
        POLISHED_SYENITE_STAIRS = registerBlockItem("polished_syenite_stairs", VABlocks.POLISHED_SYENITE_STAIRS, ItemGroups.BUILDING_BLOCKS, POLISHED_SYENITE);
        POLISHED_SYENITE_SLAB = registerBlockItem("polished_syenite_slab", VABlocks.POLISHED_SYENITE_SLAB, ItemGroups.BUILDING_BLOCKS, POLISHED_SYENITE_STAIRS);
        SYENITE_BRICKS = registerBlockItem("syenite_bricks", VABlocks.SYENITE_BRICKS, ItemGroups.BUILDING_BLOCKS, POLISHED_SYENITE_SLAB);
        CRACKED_SYENITE_BRICKS = registerBlockItem("cracked_syenite_bricks", VABlocks.CRACKED_SYENITE_BRICKS, ItemGroups.BUILDING_BLOCKS, SYENITE_BRICKS);
        SYENITE_BRICK_STAIRS = registerBlockItem("syenite_brick_stairs", VABlocks.SYENITE_BRICK_STAIRS, ItemGroups.BUILDING_BLOCKS, CRACKED_SYENITE_BRICKS);
        SYENITE_BRICK_SLAB = registerBlockItem("syenite_brick_slab", VABlocks.SYENITE_BRICK_SLAB, ItemGroups.BUILDING_BLOCKS, SYENITE_BRICK_STAIRS);
        SYENITE_BRICK_WALL = registerBlockItem("syenite_brick_wall", VABlocks.SYENITE_BRICK_WALL, ItemGroups.BUILDING_BLOCKS, SYENITE_BRICK_SLAB);
        CHISELED_SYENITE = registerBlockItem("chiseled_syenite", VABlocks.CHISELED_SYENITE, ItemGroups.BUILDING_BLOCKS, prev);

        //endregion

        //endregion

        //region Skylands

        //region Floatrock

        FLOATROCK = registerBlockItem("floatrock", VABlocks.FLOATROCK, new ItemGroupLocation(ItemGroups.BUILDING_BLOCKS, Items.DEEPSLATE_TILE_WALL), new ItemGroupLocation(ItemGroups.NATURAL, Items.DEEPSLATE));
        FLOATROCK_STAIRS = registerBlockItem("floatrock_stairs", VABlocks.FLOATROCK_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        FLOATROCK_SLAB = registerBlockItem("floatrock_slab", VABlocks.FLOATROCK_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        FLOATROCK_WALL = registerBlockItem("floatrock_wall", VABlocks.FLOATROCK_WALL, ItemGroups.BUILDING_BLOCKS, prev);
        FLOATROCK_BRICKS = registerBlockItem("floatrock_bricks", VABlocks.FLOATROCK_BRICKS, ItemGroups.BUILDING_BLOCKS, prev);
        FLOATROCK_BRICK_STAIRS = registerBlockItem("floatrock_brick_stairs", VABlocks.FLOATROCK_BRICK_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        FLOATROCK_BRICK_SLAB = registerBlockItem("floatrock_brick_slab", VABlocks.FLOATROCK_BRICK_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        FLOATROCK_BRICK_WALL = registerBlockItem("floatrock_brick_wall", VABlocks.FLOATROCK_BRICK_WALL, ItemGroups.BUILDING_BLOCKS, prev);
        POLISHED_FLOATROCK = registerBlockItem("polished_floatrock", VABlocks.POLISHED_FLOATROCK, ItemGroups.BUILDING_BLOCKS, prev);
        POLISHED_FLOATROCK_STAIRS = registerBlockItem("polished_floatrock_stairs", VABlocks.POLISHED_FLOATROCK_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        POLISHED_FLOATROCK_SLAB = registerBlockItem("polished_floatrock_slab", VABlocks.POLISHED_FLOATROCK_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        POLISHED_FLOATROCK_WALL = registerBlockItem("polished_floatrock_wall", VABlocks.POLISHED_FLOATROCK_WALL, ItemGroups.BUILDING_BLOCKS, prev);
        GRASSY_FLOATROCK = registerBlockItem("grassy_floatrock", VABlocks.GRASSY_FLOATROCK, ItemGroups.NATURAL, FLOATROCK);

        //region Floatrock Ores

        FLOATROCK_COAL_ORE = registerBlockItem("floatrock_coal_ore", VABlocks.FLOATROCK_COAL_ORE, ItemGroups.NATURAL, Items.DEEPSLATE_COAL_ORE);
        FLOATROCK_COPPER_ORE = registerBlockItem("floatrock_copper_ore", VABlocks.FLOATROCK_COPPER_ORE, ItemGroups.NATURAL, Items.DEEPSLATE_COPPER_ORE);
        FLOATROCK_IRON_ORE = registerBlockItem("floatrock_iron_ore", VABlocks.FLOATROCK_IRON_ORE, ItemGroups.NATURAL, Items.DEEPSLATE_IRON_ORE);
        FLOATROCK_GOLD_ORE = registerBlockItem("floatrock_gold_ore", VABlocks.FLOATROCK_GOLD_ORE, ItemGroups.NATURAL, Items.DEEPSLATE_GOLD_ORE);
        FLOATROCK_REDSTONE_ORE = registerBlockItem("floatrock_redstone_ore", VABlocks.FLOATROCK_REDSTONE_ORE,   ItemGroups.NATURAL, Items.DEEPSLATE_REDSTONE_ORE);
        FLOATROCK_EMERALD_ORE = registerBlockItem("floatrock_emerald_ore", VABlocks.FLOATROCK_EMERALD_ORE, ItemGroups.NATURAL, Items.DEEPSLATE_EMERALD_ORE);
        FLOATROCK_LAPIS_ORE = registerBlockItem("floatrock_lapis_ore", VABlocks.FLOATROCK_LAPIS_ORE, ItemGroups.NATURAL, Items.DEEPSLATE_LAPIS_ORE);
        FLOATROCK_DIAMOND_ORE = registerBlockItem("floatrock_diamond_ore", VABlocks.FLOATROCK_DIAMOND_ORE, ItemGroups.NATURAL, Items.DEEPSLATE_DIAMOND_ORE);

        //endregion

        //endregion

        //region Aerobloom

        AEROBLOOM_LOG = registerBlockItem("aerobloom_log", VABlocks.AEROBLOOM_LOG, new ItemGroupLocation(ItemGroups.BUILDING_BLOCKS, Items.CHERRY_BUTTON), new ItemGroupLocation(ItemGroups.NATURAL, Items.CHERRY_LOG));
        AEROBLOOM_WOOD = registerBlockItem("aerobloom_wood", VABlocks.AEROBLOOM_WOOD, ItemGroups.BUILDING_BLOCKS, prev);
        STRIPPED_AEROBLOOM_LOG = registerBlockItem("stripped_aerobloom_log", VABlocks.STRIPPED_AEROBLOOM_LOG, ItemGroups.BUILDING_BLOCKS, prev);
        STRIPPED_AEROBLOOM_WOOD = registerBlockItem("stripped_aerobloom_wood", VABlocks.STRIPPED_AEROBLOOM_WOOD, ItemGroups.BUILDING_BLOCKS, prev);
        AEROBLOOM_PLANKS = registerBlockItem("aerobloom_planks", VABlocks.AEROBLOOM_PLANKS, ItemGroups.BUILDING_BLOCKS, prev);
        AEROBLOOM_STAIRS = registerBlockItem("aerobloom_stairs", VABlocks.AEROBLOOM_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        AEROBLOOM_SLAB = registerBlockItem("aerobloom_slab", VABlocks.AEROBLOOM_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        AEROBLOOM_FENCE = registerBlockItem("aerobloom_fence", VABlocks.AEROBLOOM_FENCE, ItemGroups.BUILDING_BLOCKS, prev);
        AEROBLOOM_FENCE_GATE = registerBlockItem("aerobloom_fence_gate", VABlocks.AEROBLOOM_FENCE_GATE, ItemGroups.BUILDING_BLOCKS, prev);
        AEROBLOOM_DOOR = registerBlockItem("aerobloom_door", VABlocks.AEROBLOOM_DOOR, ItemGroups.BUILDING_BLOCKS, prev);
        AEROBLOOM_TRAPDOOR = registerBlockItem("aerobloom_trapdoor", VABlocks.AEROBLOOM_TRAPDOOR, ItemGroups.BUILDING_BLOCKS, prev);
        AEROBLOOM_PRESSURE_PLATE = registerBlockItem("aerobloom_pressure_plate", VABlocks.AEROBLOOM_PRESSURE_PLATE, ItemGroups.BUILDING_BLOCKS, prev);
        AEROBLOOM_BUTTON = registerBlockItem("aerobloom_button", VABlocks.AEROBLOOM_BUTTON, ItemGroups.BUILDING_BLOCKS, prev);
        AEROBLOOM_SIGN = register("aerobloom_sign", settings ->  new SignItem(VABlocks.AEROBLOOM_SIGN, VABlocks.AEROBLOOM_WALL_SIGN, settings), new Item.Settings().translationKey(VABlocks.AEROBLOOM_SIGN.getTranslationKey()).maxCount(16), ItemGroups.FUNCTIONAL, Items.CHERRY_HANGING_SIGN);
        AEROBLOOM_HANGING_SIGN = register("aerobloom_hanging_sign", settings ->  new HangingSignItem(VABlocks.AEROBLOOM_HANGING_SIGN, VABlocks.AEROBLOOM_WALL_HANGING_SIGN, settings), new Item.Settings().translationKey(VABlocks.AEROBLOOM_HANGING_SIGN.getTranslationKey()).maxCount(16), ItemGroups.FUNCTIONAL, prev);
        AEROBLOOM_LEAVES = registerBlockItem("aerobloom_leaves", VABlocks.AEROBLOOM_LEAVES, ItemGroups.NATURAL, Items.CHERRY_LEAVES);
        AEROBLOOM_SAPLING = registerBlockItem("aerobloom_sapling", VABlocks.AEROBLOOM_SAPLING, ItemGroups.NATURAL, Items.CHERRY_SAPLING);

        //endregion

        SPRINGSOIL = registerBlockItem("springsoil", VABlocks.SPRINGSOIL, ItemGroups.NATURAL, Items.RED_SANDSTONE);

        BALLOON_FRUIT = register("balloon_fruit", settings -> new BalloonFruitItem(VABlocks.BALLOON_BULB_BUD, settings), new Item.Settings().food(BALLOON_FRUIT_FOOD), ItemGroups.FOOD_AND_DRINK, Items.CHORUS_FRUIT);

        //endregion

        //region Salty Caves

        ROCK_SALT_BLOCK = registerBlockItem("rock_salt_block", VABlocks.ROCK_SALT_BLOCK, ItemGroups.NATURAL, Items.POINTED_DRIPSTONE);
        ROCK_SALT_CRYSTAL = registerBlockItem("rock_salt_crystal", VABlocks.ROCK_SALT_CRYSTAL, ItemGroups.NATURAL, prev);
        ROCK_SALT = register("rock_salt", ItemGroups.INGREDIENTS, Items.SUGAR);

        //endregion

        //region Steel

        RAW_STEEL_BLOCK = registerBlockItem("raw_steel_block", VABlocks.RAW_STEEL_BLOCK, ItemGroups.NATURAL, Items.RAW_GOLD_BLOCK);
        STEEL_BLOCK = registerBlockItem("steel_block", VABlocks.STEEL_BLOCK, ItemGroups.BUILDING_BLOCKS, Items.GOLD_BLOCK);
        CUT_STEEL = registerBlockItem("cut_steel", VABlocks.CUT_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        CUT_STEEL_STAIRS = registerBlockItem("cut_steel_stairs", VABlocks.CUT_STEEL_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        CUT_STEEL_SLAB = registerBlockItem("cut_steel_slab", VABlocks.CUT_STEEL_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        STEEL_GRATE = registerBlockItem("steel_grate", VABlocks.STEEL_GRATE, ItemGroups.BUILDING_BLOCKS, prev);
        CHISELED_STEEL = registerBlockItem("chiseled_steel", VABlocks.CHISELED_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        STEEL_FENCE = registerBlockItem("steel_fence", VABlocks.STEEL_FENCE, ItemGroups.BUILDING_BLOCKS, prev);
        STEEL_DOOR = registerBlockItem("steel_door", VABlocks.STEEL_DOOR, ItemGroups.BUILDING_BLOCKS, prev);
        STEEL_TRAPDOOR = registerBlockItem("steel_trapdoor", VABlocks.STEEL_TRAPDOOR, ItemGroups.BUILDING_BLOCKS, prev);
        REDSTONE_BRIDGE = registerBlockItem("redstone_bridge", VABlocks.REDSTONE_BRIDGE, ItemGroups.REDSTONE, Items.COMPARATOR);
        CAGELIGHT = registerBlockItem("cagelight", VABlocks.CAGELIGHT, ItemGroups.BUILDING_BLOCKS, STEEL_TRAPDOOR);
        SPOTLIGHT = registerBlockItem("spotlight", VABlocks.SPOTLIGHT, ItemGroups.REDSTONE, Items.REDSTONE_LAMP);
        RAW_STEEL = register("raw_steel", ItemGroups.INGREDIENTS, Items.RAW_GOLD);
        STEEL_INGOT = register("steel_ingot", ItemGroups.INGREDIENTS, Items.GOLD_INGOT);
        STEEL_BOMB = register("steel_bomb", SteelBombItem::new, new Item.Settings().maxCount(16).component(VADataComponentTypes.EXPLOSIVE_CONTENTS, ExplosiveContentComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COMBAT, Items.SNOWBALL), new ItemGroupLocation(ItemGroups.TOOLS, Items.LEAD));
        STEEL_SWORD = register("steel_sword", settings -> new SwordItem(SteelToolMaterial.INSTANCE, 3, -2.4F, settings), new Item.Settings(), ItemGroups.COMBAT, Items.GOLDEN_SWORD);
        STEEL_SHOVEL = register("steel_shovel", settings -> new ShovelItem(SteelToolMaterial.INSTANCE, 1.5F, -3.0F, settings), new Item.Settings(), ItemGroups.TOOLS, Items.GOLDEN_HOE);
        STEEL_PICKAXE = register("steel_pickaxe", settings -> new PickaxeItem(SteelToolMaterial.INSTANCE, 1, -2.8F, settings), new Item.Settings(), ItemGroups.TOOLS, prev);
        STEEL_AXE = register("steel_axe", settings -> new AxeItem(SteelToolMaterial.INSTANCE, 6.0F, -3.1F, settings), new Item.Settings(), new ItemGroupLocation(ItemGroups.TOOLS, prev), new ItemGroupLocation(ItemGroups.COMBAT, Items.GOLDEN_AXE));
        STEEL_HOE = register("steel_hoe", settings -> new HoeItem(SteelToolMaterial.INSTANCE, -2, -1.0F, settings), new Item.Settings(), ItemGroups.TOOLS, prev);
        STEEL_HELMET = register("steel_helmet", settings -> new ArmorItem(VAArmorMaterial.STEEL, EquipmentType.HELMET, settings), new Item.Settings().maxCount(1).maxDamage(EquipmentType.HELMET.getMaxDamage(24)), ItemGroups.COMBAT, Items.GOLDEN_BOOTS);
        STEEL_CHESTPLATE = register("steel_chestplate", settings -> new ArmorItem(VAArmorMaterial.STEEL, EquipmentType.CHESTPLATE, settings), new Item.Settings().maxCount(1).maxDamage(EquipmentType.CHESTPLATE.getMaxDamage(24)), ItemGroups.COMBAT, prev);
        STEEL_LEGGINGS = register("steel_leggings", settings -> new ArmorItem(VAArmorMaterial.STEEL, EquipmentType.LEGGINGS, settings), new Item.Settings().maxCount(1).maxDamage(EquipmentType.LEGGINGS.getMaxDamage(24)), ItemGroups.COMBAT, prev);
        STEEL_BOOTS = register("steel_boots", settings -> new ArmorItem(VAArmorMaterial.STEEL, EquipmentType.BOOTS, settings), new Item.Settings().maxCount(1).maxDamage(EquipmentType.BOOTS.getMaxDamage(24)), ItemGroups.COMBAT, prev);

        //region Exposed Blocks

        EXPOSED_STEEL_BLOCK = registerBlockItem("exposed_steel_block", VABlocks.EXPOSED_STEEL_BLOCK, ItemGroups.BUILDING_BLOCKS, CAGELIGHT);
        EXPOSED_CUT_STEEL = registerBlockItem("exposed_cut_steel", VABlocks.EXPOSED_CUT_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        EXPOSED_CUT_STEEL_STAIRS = registerBlockItem("exposed_cut_steel_stairs", VABlocks.EXPOSED_CUT_STEEL_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        EXPOSED_CUT_STEEL_SLAB = registerBlockItem("exposed_cut_steel_slab", VABlocks.EXPOSED_CUT_STEEL_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        EXPOSED_STEEL_GRATE = registerBlockItem("exposed_steel_grate", VABlocks.EXPOSED_STEEL_GRATE, ItemGroups.BUILDING_BLOCKS, prev);
        EXPOSED_CHISELED_STEEL = registerBlockItem("exposed_chiseled_steel", VABlocks.EXPOSED_CHISELED_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        EXPOSED_STEEL_FENCE = registerBlockItem("exposed_steel_fence", VABlocks.EXPOSED_STEEL_FENCE, ItemGroups.BUILDING_BLOCKS, prev);
        EXPOSED_STEEL_DOOR = registerBlockItem("exposed_steel_door", VABlocks.EXPOSED_STEEL_DOOR, ItemGroups.BUILDING_BLOCKS, prev);
        EXPOSED_STEEL_TRAPDOOR = registerBlockItem("exposed_steel_trapdoor", VABlocks.EXPOSED_STEEL_TRAPDOOR, ItemGroups.BUILDING_BLOCKS, prev);

        //endregion

        //region Weathered Blocks

        WEATHERED_STEEL_BLOCK = registerBlockItem("weathered_steel_block", VABlocks.WEATHERED_STEEL_BLOCK, ItemGroups.BUILDING_BLOCKS, prev);
        WEATHERED_CUT_STEEL = registerBlockItem("weathered_cut_steel", VABlocks.WEATHERED_CUT_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        WEATHERED_CUT_STEEL_STAIRS = registerBlockItem("weathered_cut_steel_stairs", VABlocks.WEATHERED_CUT_STEEL_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        WEATHERED_CUT_STEEL_SLAB = registerBlockItem("weathered_cut_steel_slab", VABlocks.WEATHERED_CUT_STEEL_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        WEATHERED_STEEL_GRATE = registerBlockItem("weathered_steel_grate", VABlocks.WEATHERED_STEEL_GRATE, ItemGroups.BUILDING_BLOCKS, prev);
        WEATHERED_CHISELED_STEEL = registerBlockItem("weathered_chiseled_steel", VABlocks.WEATHERED_CHISELED_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        WEATHERED_STEEL_FENCE = registerBlockItem("weathered_steel_fence", VABlocks.WEATHERED_STEEL_FENCE, ItemGroups.BUILDING_BLOCKS, prev);
        WEATHERED_STEEL_DOOR = registerBlockItem("weathered_steel_door", VABlocks.WEATHERED_STEEL_DOOR, ItemGroups.BUILDING_BLOCKS, prev);
        WEATHERED_STEEL_TRAPDOOR = registerBlockItem("weathered_steel_trapdoor", VABlocks.WEATHERED_STEEL_TRAPDOOR, ItemGroups.BUILDING_BLOCKS, prev);

        //endregion

        //region Oxidized Blocks

        OXIDIZED_STEEL_BLOCK = registerBlockItem("oxidized_steel_block", VABlocks.OXIDIZED_STEEL_BLOCK, ItemGroups.BUILDING_BLOCKS, prev);
        OXIDIZED_CUT_STEEL = registerBlockItem("oxidized_cut_steel", VABlocks.OXIDIZED_CUT_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        OXIDIZED_CUT_STEEL_STAIRS = registerBlockItem("oxidized_cut_steel_stairs", VABlocks.OXIDIZED_CUT_STEEL_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        OXIDIZED_CUT_STEEL_SLAB = registerBlockItem("oxidized_cut_steel_slab", VABlocks.OXIDIZED_CUT_STEEL_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        OXIDIZED_STEEL_GRATE = registerBlockItem("oxidized_steel_grate", VABlocks.OXIDIZED_STEEL_GRATE, ItemGroups.BUILDING_BLOCKS, prev);
        OXIDIZED_CHISELED_STEEL = registerBlockItem("oxidized_chiseled_steel", VABlocks.OXIDIZED_CHISELED_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        OXIDIZED_STEEL_FENCE = registerBlockItem("oxidized_steel_fence", VABlocks.OXIDIZED_STEEL_FENCE, ItemGroups.BUILDING_BLOCKS, prev);
        OXIDIZED_STEEL_DOOR = registerBlockItem("oxidized_steel_door", VABlocks.OXIDIZED_STEEL_DOOR, ItemGroups.BUILDING_BLOCKS, prev);
        OXIDIZED_STEEL_TRAPDOOR = registerBlockItem("oxidized_steel_trapdoor", VABlocks.OXIDIZED_STEEL_TRAPDOOR, ItemGroups.BUILDING_BLOCKS, prev);

        //endregion

        //region Waxed Blocks

        WAXED_STEEL_BLOCK = registerBlockItem("waxed_steel_block", VABlocks.WAXED_STEEL_BLOCK, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_CUT_STEEL = registerBlockItem("waxed_cut_steel", VABlocks.WAXED_CUT_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_CUT_STEEL_STAIRS = registerBlockItem("waxed_cut_steel_stairs", VABlocks.WAXED_CUT_STEEL_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_CUT_STEEL_SLAB = registerBlockItem("waxed_cut_steel_slab", VABlocks.WAXED_CUT_STEEL_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_STEEL_GRATE = registerBlockItem("waxed_steel_grate", VABlocks.WAXED_STEEL_GRATE, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_CHISELED_STEEL = registerBlockItem("waxed_chiseled_steel", VABlocks.WAXED_CHISELED_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_STEEL_FENCE = registerBlockItem("waxed_steel_fence", VABlocks.WAXED_STEEL_FENCE, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_STEEL_DOOR = registerBlockItem("waxed_steel_door", VABlocks.WAXED_STEEL_DOOR, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_STEEL_TRAPDOOR = registerBlockItem("waxed_steel_trapdoor", VABlocks.WAXED_STEEL_TRAPDOOR, ItemGroups.BUILDING_BLOCKS, prev);

        //endregion

        //region Waxed Exposed Blocks

        WAXED_EXPOSED_STEEL_BLOCK = registerBlockItem("waxed_exposed_steel_block", VABlocks.WAXED_EXPOSED_STEEL_BLOCK, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_CUT_STEEL = registerBlockItem("waxed_exposed_cut_steel", VABlocks.WAXED_EXPOSED_CUT_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_CUT_STEEL_STAIRS = registerBlockItem("waxed_exposed_cut_steel_stairs", VABlocks.WAXED_EXPOSED_CUT_STEEL_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_CUT_STEEL_SLAB = registerBlockItem("waxed_exposed_cut_steel_slab", VABlocks.WAXED_EXPOSED_CUT_STEEL_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_STEEL_GRATE = registerBlockItem("waxed_exposed_steel_grate", VABlocks.WAXED_EXPOSED_STEEL_GRATE, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_CHISELED_STEEL = registerBlockItem("waxed_exposed_chiseled_steel", VABlocks.WAXED_EXPOSED_CHISELED_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_STEEL_FENCE = registerBlockItem("waxed_exposed_steel_fence", VABlocks.WAXED_EXPOSED_STEEL_FENCE, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_STEEL_DOOR = registerBlockItem("waxed_exposed_steel_door", VABlocks.WAXED_EXPOSED_STEEL_DOOR, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_STEEL_TRAPDOOR = registerBlockItem("waxed_exposed_steel_trapdoor", VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR, ItemGroups.BUILDING_BLOCKS, prev);

        //endregion

        //region Waxed Weathered Blocks

        WAXED_WEATHERED_STEEL_BLOCK = registerBlockItem("waxed_weathered_steel_block", VABlocks.WAXED_WEATHERED_STEEL_BLOCK, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_CUT_STEEL = registerBlockItem("waxed_weathered_cut_steel", VABlocks.WAXED_WEATHERED_CUT_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_CUT_STEEL_STAIRS = registerBlockItem("waxed_weathered_cut_steel_stairs", VABlocks.WAXED_WEATHERED_CUT_STEEL_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_CUT_STEEL_SLAB = registerBlockItem("waxed_weathered_cut_steel_slab", VABlocks.WAXED_WEATHERED_CUT_STEEL_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_STEEL_GRATE = registerBlockItem("waxed_weathered_steel_grate", VABlocks.WAXED_WEATHERED_STEEL_GRATE, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_CHISELED_STEEL = registerBlockItem("waxed_weathered_chiseled_steel", VABlocks.WAXED_WEATHERED_CHISELED_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_STEEL_FENCE = registerBlockItem("waxed_weathered_steel_fence", VABlocks.WAXED_WEATHERED_STEEL_FENCE, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_STEEL_DOOR = registerBlockItem("waxed_weathered_steel_door", VABlocks.WAXED_WEATHERED_STEEL_DOOR, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_STEEL_TRAPDOOR = registerBlockItem("waxed_weathered_steel_trapdoor", VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR, ItemGroups.BUILDING_BLOCKS, prev);

        //endregion

        //region Waxed Oxidized Blocks

        WAXED_OXIDIZED_STEEL_BLOCK = registerBlockItem("waxed_oxidized_steel_block", VABlocks.WAXED_OXIDIZED_STEEL_BLOCK, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_CUT_STEEL = registerBlockItem("waxed_oxidized_cut_steel", VABlocks.WAXED_OXIDIZED_CUT_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_CUT_STEEL_STAIRS = registerBlockItem("waxed_oxidized_cut_steel_stairs", VABlocks.WAXED_OXIDIZED_CUT_STEEL_STAIRS, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_CUT_STEEL_SLAB = registerBlockItem("waxed_oxidized_cut_steel_slab", VABlocks.WAXED_OXIDIZED_CUT_STEEL_SLAB, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_STEEL_GRATE = registerBlockItem("waxed_oxidized_steel_grate", VABlocks.WAXED_OXIDIZED_STEEL_GRATE, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_CHISELED_STEEL = registerBlockItem("waxed_oxidized_chiseled_steel", VABlocks.WAXED_OXIDIZED_CHISELED_STEEL, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_STEEL_FENCE = registerBlockItem("waxed_oxidized_steel_fence", VABlocks.WAXED_OXIDIZED_STEEL_FENCE, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_STEEL_DOOR = registerBlockItem("waxed_oxidized_steel_door", VABlocks.WAXED_OXIDIZED_STEEL_DOOR, ItemGroups.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_STEEL_TRAPDOOR = registerBlockItem("waxed_oxidized_steel_trapdoor", VABlocks.WAXED_OXIDIZED_STEEL_TRAPDOOR, ItemGroups.BUILDING_BLOCKS, prev);

        //endregion

        //endregion

        //region Wasp Den

        SILK_BLOCK = registerBlockItem("silk_block", VABlocks.SILK_BLOCK, ItemGroups.NATURAL, Items.SHROOMLIGHT);
        LUMWASP_NEST = registerBlockItem("lumwasp_nest", VABlocks.LUMWASP_NEST, ItemGroups.NATURAL, prev);
        WEBBED_SILK = registerBlockItem("webbed_silk", VABlocks.WEBBED_SILK, ItemGroups.NATURAL, prev);
        FRAYED_SILK = registerBlockItem("frayed_silk", VABlocks.FRAYED_SILK, ItemGroups.NATURAL, Items.DEAD_BUSH);
        GREENCAP_MUSHROOM = registerBlockItem("greencap_mushroom", VABlocks.GREENCAP_MUSHROOM, ItemGroups.NATURAL, Items.RED_MUSHROOM);
        TALL_GREENCAP_MUSHROOMS = registerBlockItem("tall_greencap_mushrooms", VABlocks.TALL_GREENCAP_MUSHROOMS, ItemGroups.NATURAL, Items.PITCHER_PLANT);
        GLOWING_SILK = registerBlockItem("glowing_silk", VABlocks.GLOWING_SILK, ItemGroups.NATURAL, Items.HANGING_ROOTS);
        SILK_THREAD = register("silk_thread", ItemGroups.INGREDIENTS, Items.STRING);
        LUMWASP_MANDIBLE = register("lumwasp_mandible", ItemGroups.INGREDIENTS, Items.FERMENTED_SPIDER_EYE);
        SILKBULB = registerBlockItem("silkbulb", VABlocks.SILKBULB, new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.GLOWSTONE), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.PINK_BANNER), new ItemGroupLocation(ItemGroups.NATURAL, WEBBED_SILK));
        ACID_BLOCK = registerBlockItem("acid_block", VABlocks.ACID_BLOCK, ItemGroups.NATURAL, prev);

        //region Colorful Silkbulbs

        WHITE_SILKBULB = registerBlockItem("white_silkbulb", VABlocks.WHITE_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        LIGHT_GRAY_SILKBULB = registerBlockItem("light_gray_silkbulb", VABlocks.LIGHT_GRAY_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        GRAY_SILKBULB = registerBlockItem("gray_silkbulb", VABlocks.GRAY_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        BLACK_SILKBULB = registerBlockItem("black_silkbulb", VABlocks.BLACK_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        BROWN_SILKBULB = registerBlockItem("brown_silkbulb", VABlocks.BROWN_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        RED_SILKBULB = registerBlockItem("red_silkbulb", VABlocks.RED_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        ORANGE_SILKBULB = registerBlockItem("orange_silkbulb", VABlocks.ORANGE_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        YELLOW_SILKBULB = registerBlockItem("yellow_silkbulb", VABlocks.YELLOW_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        LIME_SILKBULB = registerBlockItem("lime_silkbulb", VABlocks.LIME_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        GREEN_SILKBULB = registerBlockItem("green_silkbulb", VABlocks.GREEN_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        CYAN_SILKBULB = registerBlockItem("cyan_silkbulb", VABlocks.CYAN_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        LIGHT_BLUE_SILKBULB = registerBlockItem("light_blue_silkbulb", VABlocks.LIGHT_BLUE_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        BLUE_SILKBULB = registerBlockItem("blue_silkbulb", VABlocks.BLUE_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        PURPLE_SILKBULB = registerBlockItem("purple_silkbulb", VABlocks.PURPLE_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        MAGENTA_SILKBULB = registerBlockItem("magenta_silkbulb", VABlocks.MAGENTA_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);
        PINK_SILKBULB = registerBlockItem("pink_silkbulb", VABlocks.PINK_SILKBULB, ItemGroups.COLORED_BLOCKS, prev);

        //endregion

        ACID_BUCKET = register("acid_bucket", AcidBucketItem::new, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1), ItemGroups.TOOLS, Items.LAVA_BUCKET);

        APPLICABLE_POTION = register("applicable_potion", ApplicablePotionItem::new, new Item.Settings().maxCount(16).component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT));

        //endregion

        //region Dyes

        //region Chartreuse Dyables

        CHARTREUSE_DYE = register("chartreuse_dye", settings ->  new DyeItem(VADyeColors.CHARTREUSE, settings), new Item.Settings(), ItemGroups.INGREDIENTS, Items.YELLOW_DYE);
        CHARTREUSE_WOOL = registerBlockItem("chartreuse_wool", VABlocks.CHARTREUSE_WOOL, ItemGroups.COLORED_BLOCKS, Blocks.YELLOW_WOOL.asItem());
        CHARTREUSE_CARPET = registerBlockItem("chartreuse_carpet", VABlocks.CHARTREUSE_CARPET, ItemGroups.COLORED_BLOCKS, Blocks.YELLOW_CARPET.asItem());
        CHARTREUSE_TERRACOTTA = registerBlockItem("chartreuse_terracotta", VABlocks.CHARTREUSE_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Blocks.YELLOW_TERRACOTTA.asItem());
        CHARTREUSE_GLAZED_TERRACOTTA = registerBlockItem("chartreuse_glazed_terracotta", VABlocks.CHARTREUSE_GLAZED_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Items.YELLOW_GLAZED_TERRACOTTA);
        CHARTREUSE_CONCRETE = registerBlockItem("chartreuse_concrete", VABlocks.CHARTREUSE_CONCRETE, ItemGroups.COLORED_BLOCKS, Blocks.YELLOW_CONCRETE.asItem());
        CHARTREUSE_CONCRETE_POWDER = registerBlockItem("chartreuse_concrete_powder", VABlocks.CHARTREUSE_CONCRETE_POWDER, ItemGroups.COLORED_BLOCKS, Blocks.YELLOW_CONCRETE_POWDER.asItem());
        CHARTREUSE_STAINED_GLASS = registerBlockItem("chartreuse_stained_glass", VABlocks.CHARTREUSE_STAINED_GLASS, ItemGroups.COLORED_BLOCKS, Blocks.YELLOW_STAINED_GLASS.asItem());
        CHARTREUSE_STAINED_GLASS_PANE = registerBlockItem("chartreuse_stained_glass_pane", VABlocks.CHARTREUSE_STAINED_GLASS_PANE, ItemGroups.COLORED_BLOCKS, Blocks.YELLOW_STAINED_GLASS_PANE.asItem());
        CHARTREUSE_CANDLE = registerBlockItem("chartreuse_candle", VABlocks.CHARTREUSE_CANDLE, new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Blocks.YELLOW_CANDLE.asItem()), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Blocks.YELLOW_CANDLE.asItem()));
        CHARTREUSE_SILKBULB = registerBlockItem("chartreuse_silkbulb", VABlocks.CHARTREUSE_SILKBULB, ItemGroups.COLORED_BLOCKS, VAItems.YELLOW_SILKBULB);
        CHARTREUSE_BED = register("chartreuse_bed", settings -> new BedItem(VABlocks.CHARTREUSE_BED, settings), new Item.Settings().translationKey(VABlocks.CHARTREUSE_BED.getTranslationKey()).maxCount(1), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.YELLOW_BED), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.YELLOW_BED));
        CHARTREUSE_SHULKER_BOX = register("chartreuse_shulker_box", settings ->  new BlockItem(VABlocks.CHARTREUSE_SHULKER_BOX, settings), new Item.Settings().translationKey(VABlocks.CHARTREUSE_SHULKER_BOX.getTranslationKey()).maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.YELLOW_SHULKER_BOX), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.YELLOW_SHULKER_BOX));
        CHARTREUSE_BANNER = register("chartreuse_banner", settings ->  new BannerItem(VABlocks.CHARTREUSE_BANNER, VABlocks.CHARTREUSE_WALL_BANNER, settings), new Item.Settings().translationKey(VABlocks.CHARTREUSE_BANNER.getTranslationKey()).maxCount(16).component(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.YELLOW_BANNER), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.YELLOW_BANNER));
        CHARTREUSE_BUNDLE = register("chartreuse_bundle", settings -> new BundleItem(idOf("chartreuse_bundle_open_front"), idOf("chartreuse_bundle_open_back"), settings), new Item.Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT), ItemGroups.TOOLS, Items.YELLOW_BUNDLE);

        //endregion

        //region Maroon Dyables

        MAROON_DYE = register("maroon_dye", settings ->  new DyeItem(VADyeColors.MAROON, settings), new Item.Settings(), ItemGroups.INGREDIENTS, Items.BROWN_DYE);
        MAROON_WOOL = registerBlockItem("maroon_wool", VABlocks.MAROON_WOOL, ItemGroups.COLORED_BLOCKS, Blocks.BROWN_WOOL.asItem());
        MAROON_CARPET = registerBlockItem("maroon_carpet", VABlocks.MAROON_CARPET, ItemGroups.COLORED_BLOCKS, Blocks.BROWN_CARPET.asItem());
        MAROON_TERRACOTTA = registerBlockItem("maroon_terracotta", VABlocks.MAROON_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Blocks.BROWN_TERRACOTTA.asItem());
        MAROON_GLAZED_TERRACOTTA = registerBlockItem("maroon_glazed_terracotta", VABlocks.MAROON_GLAZED_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Items.BROWN_GLAZED_TERRACOTTA);
        MAROON_CONCRETE = registerBlockItem("maroon_concrete", VABlocks.MAROON_CONCRETE, ItemGroups.COLORED_BLOCKS, Blocks.BROWN_CONCRETE.asItem());
        MAROON_CONCRETE_POWDER = registerBlockItem("maroon_concrete_powder", VABlocks.MAROON_CONCRETE_POWDER, ItemGroups.COLORED_BLOCKS, Blocks.BROWN_CONCRETE_POWDER.asItem());
        MAROON_STAINED_GLASS = registerBlockItem("maroon_stained_glass", VABlocks.MAROON_STAINED_GLASS, ItemGroups.COLORED_BLOCKS, Blocks.BROWN_STAINED_GLASS.asItem());
        MAROON_STAINED_GLASS_PANE = registerBlockItem("maroon_stained_glass_pane", VABlocks.MAROON_STAINED_GLASS_PANE, ItemGroups.COLORED_BLOCKS, Blocks.BROWN_STAINED_GLASS_PANE.asItem());
        MAROON_CANDLE = registerBlockItem("maroon_candle", VABlocks.MAROON_CANDLE, new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Blocks.BROWN_CANDLE.asItem()), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Blocks.BROWN_CANDLE.asItem()));
        MAROON_SILKBULB = registerBlockItem("maroon_silkbulb", VABlocks.MAROON_SILKBULB, ItemGroups.COLORED_BLOCKS, VAItems.BROWN_SILKBULB);
        MAROON_BED = register("maroon_bed", settings -> new BedItem(VABlocks.MAROON_BED, settings), new Item.Settings().translationKey(VABlocks.MAROON_BED.getTranslationKey()).maxCount(1), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.BROWN_BED), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.BROWN_BED));
        MAROON_SHULKER_BOX = register("maroon_shulker_box", settings ->  new BlockItem(VABlocks.MAROON_SHULKER_BOX, settings), new Item.Settings().translationKey(VABlocks.MAROON_SHULKER_BOX.getTranslationKey()).maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.BROWN_SHULKER_BOX), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.BROWN_SHULKER_BOX));
        MAROON_BANNER = register("maroon_banner", settings ->  new BannerItem(VABlocks.MAROON_BANNER, VABlocks.MAROON_WALL_BANNER, settings), new Item.Settings().translationKey(VABlocks.MAROON_BANNER.getTranslationKey()).maxCount(16).component(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.BROWN_BANNER), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.BROWN_BANNER));
        MAROON_BUNDLE = register("maroon_bundle", settings -> new BundleItem(idOf("maroon_bundle_open_front"), idOf("maroon_bundle_open_back"), settings), new Item.Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT), ItemGroups.TOOLS, Items.BROWN_BUNDLE);

        //endregion

        //region Indigo Dyables

        INDIGO_DYE = register("indigo_dye", settings ->  new DyeItem(VADyeColors.INDIGO, settings), new Item.Settings(), ItemGroups.INGREDIENTS, Items.BLUE_DYE);
        INDIGO_WOOL = registerBlockItem("indigo_wool", VABlocks.INDIGO_WOOL, ItemGroups.COLORED_BLOCKS, Blocks.BLUE_WOOL.asItem());
        INDIGO_CARPET = registerBlockItem("indigo_carpet", VABlocks.INDIGO_CARPET, ItemGroups.COLORED_BLOCKS, Blocks.BLUE_CARPET.asItem());
        INDIGO_TERRACOTTA = registerBlockItem("indigo_terracotta", VABlocks.INDIGO_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Blocks.BLUE_TERRACOTTA.asItem());
        INDIGO_GLAZED_TERRACOTTA = registerBlockItem("indigo_glazed_terracotta", VABlocks.INDIGO_GLAZED_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Items.BLUE_GLAZED_TERRACOTTA);
        INDIGO_CONCRETE = registerBlockItem("indigo_concrete", VABlocks.INDIGO_CONCRETE, ItemGroups.COLORED_BLOCKS, Blocks.BLUE_CONCRETE.asItem());
        INDIGO_CONCRETE_POWDER = registerBlockItem("indigo_concrete_powder", VABlocks.INDIGO_CONCRETE_POWDER, ItemGroups.COLORED_BLOCKS, Blocks.BLUE_CONCRETE_POWDER.asItem());
        INDIGO_STAINED_GLASS = registerBlockItem("indigo_stained_glass", VABlocks.INDIGO_STAINED_GLASS, ItemGroups.COLORED_BLOCKS, Blocks.BLUE_STAINED_GLASS.asItem());
        INDIGO_STAINED_GLASS_PANE = registerBlockItem("indigo_stained_glass_pane", VABlocks.INDIGO_STAINED_GLASS_PANE, ItemGroups.COLORED_BLOCKS, Blocks.BLUE_STAINED_GLASS_PANE.asItem());
        INDIGO_CANDLE = registerBlockItem("indigo_candle", VABlocks.INDIGO_CANDLE, new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Blocks.BLUE_CANDLE.asItem()), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Blocks.BLUE_CANDLE.asItem()));
        INDIGO_SILKBULB = registerBlockItem("indigo_silkbulb", VABlocks.INDIGO_SILKBULB, ItemGroups.COLORED_BLOCKS, VAItems.BLUE_SILKBULB);
        INDIGO_BED = register("indigo_bed", settings -> new BedItem(VABlocks.INDIGO_BED, settings), new Item.Settings().translationKey(VABlocks.INDIGO_BED.getTranslationKey()).maxCount(1), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.BLUE_BED), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.BLUE_BED));
        INDIGO_SHULKER_BOX = register("indigo_shulker_box", settings ->  new BlockItem(VABlocks.INDIGO_SHULKER_BOX, settings), new Item.Settings().translationKey(VABlocks.INDIGO_SHULKER_BOX.getTranslationKey()).maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.BLUE_SHULKER_BOX), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.BLUE_SHULKER_BOX));
        INDIGO_BANNER = register("indigo_banner", settings ->  new BannerItem(VABlocks.INDIGO_BANNER, VABlocks.INDIGO_WALL_BANNER, settings), new Item.Settings().translationKey(VABlocks.INDIGO_BANNER.getTranslationKey()).maxCount(16).component(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.BLUE_BANNER), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.BLUE_BANNER));
        INDIGO_BUNDLE = register("indigo_bundle", settings -> new BundleItem(idOf("indigo_bundle_open_front"), idOf("indigo_bundle_open_back"), settings), new Item.Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT), ItemGroups.TOOLS, Items.BLUE_BUNDLE);

        //endregion

        //region Plum Dyables

        PLUM_DYE = register("plum_dye", settings ->  new DyeItem(VADyeColors.PLUM, settings), new Item.Settings(), ItemGroups.INGREDIENTS, Items.MAGENTA_DYE);
        PLUM_WOOL = registerBlockItem("plum_wool", VABlocks.PLUM_WOOL, ItemGroups.COLORED_BLOCKS, Blocks.MAGENTA_WOOL.asItem());
        PLUM_CARPET = registerBlockItem("plum_carpet", VABlocks.PLUM_CARPET, ItemGroups.COLORED_BLOCKS, Blocks.MAGENTA_CARPET.asItem());
        PLUM_TERRACOTTA = registerBlockItem("plum_terracotta", VABlocks.PLUM_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Blocks.MAGENTA_TERRACOTTA.asItem());
        PLUM_GLAZED_TERRACOTTA = registerBlockItem("plum_glazed_terracotta", VABlocks.PLUM_GLAZED_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Items.MAGENTA_GLAZED_TERRACOTTA);
        PLUM_CONCRETE = registerBlockItem("plum_concrete", VABlocks.PLUM_CONCRETE, ItemGroups.COLORED_BLOCKS, Blocks.MAGENTA_CONCRETE.asItem());
        PLUM_CONCRETE_POWDER = registerBlockItem("plum_concrete_powder", VABlocks.PLUM_CONCRETE_POWDER, ItemGroups.COLORED_BLOCKS, Blocks.MAGENTA_CONCRETE_POWDER.asItem());
        PLUM_STAINED_GLASS = registerBlockItem("plum_stained_glass", VABlocks.PLUM_STAINED_GLASS, ItemGroups.COLORED_BLOCKS, Blocks.MAGENTA_STAINED_GLASS.asItem());
        PLUM_STAINED_GLASS_PANE = registerBlockItem("plum_stained_glass_pane", VABlocks.PLUM_STAINED_GLASS_PANE, ItemGroups.COLORED_BLOCKS, Blocks.MAGENTA_STAINED_GLASS_PANE.asItem());
        PLUM_CANDLE = registerBlockItem("plum_candle", VABlocks.PLUM_CANDLE, new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Blocks.MAGENTA_CANDLE.asItem()), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Blocks.MAGENTA_CANDLE.asItem()));
        PLUM_SILKBULB = registerBlockItem("plum_silkbulb", VABlocks.PLUM_SILKBULB, ItemGroups.COLORED_BLOCKS, VAItems.MAGENTA_SILKBULB);
        PLUM_BED = register("plum_bed", settings -> new BedItem(VABlocks.PLUM_BED, settings), new Item.Settings().translationKey(VABlocks.PLUM_BED.getTranslationKey()).maxCount(1), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.MAGENTA_BED), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.MAGENTA_BED));
        PLUM_SHULKER_BOX = register("plum_shulker_box", settings ->  new BlockItem(VABlocks.PLUM_SHULKER_BOX, settings), new Item.Settings().translationKey(VABlocks.PLUM_SHULKER_BOX.getTranslationKey()).maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.MAGENTA_SHULKER_BOX), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.MAGENTA_SHULKER_BOX));
        PLUM_BANNER = register("plum_banner", settings ->  new BannerItem(VABlocks.PLUM_BANNER, VABlocks.PLUM_WALL_BANNER, settings), new Item.Settings().translationKey(VABlocks.PLUM_BANNER.getTranslationKey()).maxCount(16).component(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.MAGENTA_BANNER), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.MAGENTA_BANNER));
        PLUM_BUNDLE = register("plum_bundle", settings -> new BundleItem(idOf("plum_bundle_open_front"), idOf("plum_bundle_open_back"), settings), new Item.Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT), ItemGroups.TOOLS, Items.MAGENTA_BUNDLE);

        //endregion

        //region Viridian Dyables

        VIRIDIAN_DYE = register("viridian_dye", settings ->  new DyeItem(VADyeColors.VIRIDIAN, settings), new Item.Settings(), ItemGroups.INGREDIENTS, Items.GREEN_DYE);
        VIRIDIAN_WOOL = registerBlockItem("viridian_wool", VABlocks.VIRIDIAN_WOOL, ItemGroups.COLORED_BLOCKS, Blocks.GREEN_WOOL.asItem());
        VIRIDIAN_CARPET = registerBlockItem("viridian_carpet", VABlocks.VIRIDIAN_CARPET, ItemGroups.COLORED_BLOCKS, Blocks.GREEN_CARPET.asItem());
        VIRIDIAN_TERRACOTTA = registerBlockItem("viridian_terracotta", VABlocks.VIRIDIAN_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Blocks.GREEN_TERRACOTTA.asItem());
        VIRIDIAN_GLAZED_TERRACOTTA = registerBlockItem("viridian_glazed_terracotta", VABlocks.VIRIDIAN_GLAZED_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Items.GREEN_GLAZED_TERRACOTTA);
        VIRIDIAN_CONCRETE = registerBlockItem("viridian_concrete", VABlocks.VIRIDIAN_CONCRETE, ItemGroups.COLORED_BLOCKS, Blocks.GREEN_CONCRETE.asItem());
        VIRIDIAN_CONCRETE_POWDER = registerBlockItem("viridian_concrete_powder", VABlocks.VIRIDIAN_CONCRETE_POWDER, ItemGroups.COLORED_BLOCKS, Blocks.GREEN_CONCRETE_POWDER.asItem());
        VIRIDIAN_STAINED_GLASS = registerBlockItem("viridian_stained_glass", VABlocks.VIRIDIAN_STAINED_GLASS, ItemGroups.COLORED_BLOCKS, Blocks.GREEN_STAINED_GLASS.asItem());
        VIRIDIAN_STAINED_GLASS_PANE = registerBlockItem("viridian_stained_glass_pane", VABlocks.VIRIDIAN_STAINED_GLASS_PANE, ItemGroups.COLORED_BLOCKS, Blocks.GREEN_STAINED_GLASS_PANE.asItem());
        VIRIDIAN_CANDLE = registerBlockItem("viridian_candle", VABlocks.VIRIDIAN_CANDLE, new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Blocks.GREEN_CANDLE.asItem()), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Blocks.GREEN_CANDLE.asItem()));
        VIRIDIAN_SILKBULB = registerBlockItem("viridian_silkbulb", VABlocks.VIRIDIAN_SILKBULB, ItemGroups.COLORED_BLOCKS, VAItems.GREEN_SILKBULB);
        VIRIDIAN_BED = register("viridian_bed", settings -> new BedItem(VABlocks.VIRIDIAN_BED, settings), new Item.Settings().translationKey(VABlocks.VIRIDIAN_BED.getTranslationKey()).maxCount(1), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.GREEN_BED), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.GREEN_BED));
        VIRIDIAN_SHULKER_BOX = register("viridian_shulker_box", settings ->  new BlockItem(VABlocks.VIRIDIAN_SHULKER_BOX, settings), new Item.Settings().translationKey(VABlocks.VIRIDIAN_SHULKER_BOX.getTranslationKey()).maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.GREEN_SHULKER_BOX), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.GREEN_SHULKER_BOX));
        VIRIDIAN_BANNER = register("viridian_banner", settings ->  new BannerItem(VABlocks.VIRIDIAN_BANNER, VABlocks.VIRIDIAN_WALL_BANNER, settings), new Item.Settings().translationKey(VABlocks.VIRIDIAN_BANNER.getTranslationKey()).maxCount(16).component(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.GREEN_BANNER), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.GREEN_BANNER));
        VIRIDIAN_BUNDLE = register("viridian_bundle", settings -> new BundleItem(idOf("viridian_bundle_open_front"), idOf("viridian_bundle_open_back"), settings), new Item.Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT), ItemGroups.TOOLS, Items.GREEN_BUNDLE);

        //endregion

        //region Tan Dyables

        TAN_DYE = register("tan_dye", settings ->  new DyeItem(VADyeColors.TAN, settings), new Item.Settings(), ItemGroups.INGREDIENTS, Items.BLACK_DYE);
        TAN_WOOL = registerBlockItem("tan_wool", VABlocks.TAN_WOOL, ItemGroups.COLORED_BLOCKS, Blocks.BLACK_WOOL.asItem());
        TAN_CARPET = registerBlockItem("tan_carpet", VABlocks.TAN_CARPET, ItemGroups.COLORED_BLOCKS, Blocks.BLACK_CARPET.asItem());
        TAN_TERRACOTTA = registerBlockItem("tan_terracotta", VABlocks.TAN_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Blocks.BLACK_TERRACOTTA.asItem());
        TAN_GLAZED_TERRACOTTA = registerBlockItem("tan_glazed_terracotta", VABlocks.TAN_GLAZED_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Items.BLACK_GLAZED_TERRACOTTA);
        TAN_CONCRETE = registerBlockItem("tan_concrete", VABlocks.TAN_CONCRETE, ItemGroups.COLORED_BLOCKS, Blocks.BLACK_CONCRETE.asItem());
        TAN_CONCRETE_POWDER = registerBlockItem("tan_concrete_powder", VABlocks.TAN_CONCRETE_POWDER, ItemGroups.COLORED_BLOCKS, Blocks.BLACK_CONCRETE_POWDER.asItem());
        TAN_STAINED_GLASS = registerBlockItem("tan_stained_glass", VABlocks.TAN_STAINED_GLASS, ItemGroups.COLORED_BLOCKS, Blocks.BLACK_STAINED_GLASS.asItem());
        TAN_STAINED_GLASS_PANE = registerBlockItem("tan_stained_glass_pane", VABlocks.TAN_STAINED_GLASS_PANE, ItemGroups.COLORED_BLOCKS, Blocks.BLACK_STAINED_GLASS_PANE.asItem());
        TAN_CANDLE = registerBlockItem("tan_candle", VABlocks.TAN_CANDLE, new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Blocks.BLACK_CANDLE.asItem()), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Blocks.BLACK_CANDLE.asItem()));
        TAN_SILKBULB = registerBlockItem("tan_silkbulb", VABlocks.TAN_SILKBULB, ItemGroups.COLORED_BLOCKS, VAItems.BLACK_SILKBULB);
        TAN_BED = register("tan_bed", settings -> new BedItem(VABlocks.TAN_BED, settings), new Item.Settings().translationKey(VABlocks.TAN_BED.getTranslationKey()).maxCount(1), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.BLACK_BED), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.BLACK_BED));
        TAN_SHULKER_BOX = register("tan_shulker_box", settings ->  new BlockItem(VABlocks.TAN_SHULKER_BOX, settings), new Item.Settings().translationKey(VABlocks.TAN_SHULKER_BOX.getTranslationKey()).maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.BLACK_SHULKER_BOX), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.BLACK_SHULKER_BOX));
        TAN_BANNER = register("tan_banner", settings ->  new BannerItem(VABlocks.TAN_BANNER, VABlocks.TAN_WALL_BANNER, settings), new Item.Settings().translationKey(VABlocks.TAN_BANNER.getTranslationKey()).maxCount(16).component(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.BLACK_BANNER), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.BLACK_BANNER));
        TAN_BUNDLE = register("tan_bundle", settings -> new BundleItem(idOf("tan_bundle_open_front"), idOf("tan_bundle_open_back"), settings), new Item.Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT), ItemGroups.TOOLS, Items.BLACK_BUNDLE);

        //endregion

        //region Sinopia Dyables

        SINOPIA_DYE = register("sinopia_dye", settings ->  new DyeItem(VADyeColors.SINOPIA, settings), new Item.Settings(), ItemGroups.INGREDIENTS, Items.RED_DYE);
        SINOPIA_WOOL = registerBlockItem("sinopia_wool", VABlocks.SINOPIA_WOOL, ItemGroups.COLORED_BLOCKS, Blocks.RED_WOOL.asItem());
        SINOPIA_CARPET = registerBlockItem("sinopia_carpet", VABlocks.SINOPIA_CARPET, ItemGroups.COLORED_BLOCKS, Blocks.RED_CARPET.asItem());
        SINOPIA_TERRACOTTA = registerBlockItem("sinopia_terracotta", VABlocks.SINOPIA_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Blocks.RED_TERRACOTTA.asItem());
        SINOPIA_GLAZED_TERRACOTTA = registerBlockItem("sinopia_glazed_terracotta", VABlocks.SINOPIA_GLAZED_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Items.RED_GLAZED_TERRACOTTA);
        SINOPIA_CONCRETE = registerBlockItem("sinopia_concrete", VABlocks.SINOPIA_CONCRETE, ItemGroups.COLORED_BLOCKS, Blocks.RED_CONCRETE.asItem());
        SINOPIA_CONCRETE_POWDER = registerBlockItem("sinopia_concrete_powder", VABlocks.SINOPIA_CONCRETE_POWDER, ItemGroups.COLORED_BLOCKS, Blocks.RED_CONCRETE_POWDER.asItem());
        SINOPIA_STAINED_GLASS = registerBlockItem("sinopia_stained_glass", VABlocks.SINOPIA_STAINED_GLASS, ItemGroups.COLORED_BLOCKS, Blocks.RED_STAINED_GLASS.asItem());
        SINOPIA_STAINED_GLASS_PANE = registerBlockItem("sinopia_stained_glass_pane", VABlocks.SINOPIA_STAINED_GLASS_PANE, ItemGroups.COLORED_BLOCKS, Blocks.RED_STAINED_GLASS_PANE.asItem());
        SINOPIA_CANDLE = registerBlockItem("sinopia_candle", VABlocks.SINOPIA_CANDLE, new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Blocks.RED_CANDLE.asItem()), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Blocks.RED_CANDLE.asItem()));
        SINOPIA_SILKBULB = registerBlockItem("sinopia_silkbulb", VABlocks.SINOPIA_SILKBULB, ItemGroups.COLORED_BLOCKS, VAItems.RED_SILKBULB);
        SINOPIA_BED = register("sinopia_bed", settings -> new BedItem(VABlocks.SINOPIA_BED, settings), new Item.Settings().translationKey(VABlocks.SINOPIA_BED.getTranslationKey()).maxCount(1), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.RED_BED), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.RED_BED));
        SINOPIA_SHULKER_BOX = register("sinopia_shulker_box", settings ->  new BlockItem(VABlocks.SINOPIA_SHULKER_BOX, settings), new Item.Settings().translationKey(VABlocks.SINOPIA_SHULKER_BOX.getTranslationKey()).maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.RED_SHULKER_BOX), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.RED_SHULKER_BOX));
        SINOPIA_BANNER = register("sinopia_banner", settings ->  new BannerItem(VABlocks.SINOPIA_BANNER, VABlocks.SINOPIA_WALL_BANNER, settings), new Item.Settings().translationKey(VABlocks.SINOPIA_BANNER.getTranslationKey()).maxCount(16).component(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.RED_BANNER), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.RED_BANNER));
        SINOPIA_BUNDLE = register("sinopia_bundle", settings -> new BundleItem(idOf("sinopia_bundle_open_front"), idOf("sinopia_bundle_open_back"), settings), new Item.Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT), ItemGroups.TOOLS, Items.RED_BUNDLE);

        //endregion

        //region Lilac Dyables

        LILAC_DYE = register("lilac_dye", settings ->  new DyeItem(VADyeColors.LILAC, settings), new Item.Settings(), ItemGroups.INGREDIENTS, Items.PINK_DYE);
        LILAC_WOOL = registerBlockItem("lilac_wool", VABlocks.LILAC_WOOL, ItemGroups.COLORED_BLOCKS, Blocks.PINK_WOOL.asItem());
        LILAC_CARPET = registerBlockItem("lilac_carpet", VABlocks.LILAC_CARPET, ItemGroups.COLORED_BLOCKS, Blocks.PINK_CARPET.asItem());
        LILAC_TERRACOTTA = registerBlockItem("lilac_terracotta", VABlocks.LILAC_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Blocks.PINK_TERRACOTTA.asItem());
        LILAC_GLAZED_TERRACOTTA = registerBlockItem("lilac_glazed_terracotta", VABlocks.LILAC_GLAZED_TERRACOTTA, ItemGroups.COLORED_BLOCKS, Items.PINK_GLAZED_TERRACOTTA);
        LILAC_CONCRETE = registerBlockItem("lilac_concrete", VABlocks.LILAC_CONCRETE, ItemGroups.COLORED_BLOCKS, Blocks.PINK_CONCRETE.asItem());
        LILAC_CONCRETE_POWDER = registerBlockItem("lilac_concrete_powder", VABlocks.LILAC_CONCRETE_POWDER, ItemGroups.COLORED_BLOCKS, Blocks.PINK_CONCRETE_POWDER.asItem());
        LILAC_STAINED_GLASS = registerBlockItem("lilac_stained_glass", VABlocks.LILAC_STAINED_GLASS, ItemGroups.COLORED_BLOCKS, Blocks.PINK_STAINED_GLASS.asItem());
        LILAC_STAINED_GLASS_PANE = registerBlockItem("lilac_stained_glass_pane", VABlocks.LILAC_STAINED_GLASS_PANE, ItemGroups.COLORED_BLOCKS, Blocks.PINK_STAINED_GLASS_PANE.asItem());
        LILAC_CANDLE = registerBlockItem("lilac_candle", VABlocks.LILAC_CANDLE, new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Blocks.PINK_CANDLE.asItem()), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Blocks.PINK_CANDLE.asItem()));
        LILAC_SILKBULB = registerBlockItem("lilac_silkbulb", VABlocks.LILAC_SILKBULB, ItemGroups.COLORED_BLOCKS, VAItems.PINK_SILKBULB);
        LILAC_BED = register("lilac_bed", settings -> new BedItem(VABlocks.LILAC_BED, settings), new Item.Settings().translationKey(VABlocks.LILAC_BED.getTranslationKey()).maxCount(1), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.PINK_BED), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.PINK_BED));
        LILAC_SHULKER_BOX = register("lilac_shulker_box", settings ->  new BlockItem(VABlocks.LILAC_SHULKER_BOX, settings), new Item.Settings().translationKey(VABlocks.LILAC_SHULKER_BOX.getTranslationKey()).maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.PINK_SHULKER_BOX), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.PINK_SHULKER_BOX));
        LILAC_BANNER = register("lilac_banner", settings ->  new BannerItem(VABlocks.LILAC_BANNER, VABlocks.LILAC_WALL_BANNER, settings), new Item.Settings().translationKey(VABlocks.LILAC_BANNER.getTranslationKey()).maxCount(16).component(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT), new ItemGroupLocation(ItemGroups.COLORED_BLOCKS, Items.PINK_BANNER), new ItemGroupLocation(ItemGroups.FUNCTIONAL, Items.PINK_BANNER));
        LILAC_BUNDLE = register("lilac_bundle", settings -> new BundleItem(idOf("lilac_bundle_open_front"), idOf("lilac_bundle_open_back"), settings), new Item.Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT), ItemGroups.TOOLS, Items.PINK_BUNDLE);

        //endregion

        COLORING_STATION = registerBlockItem("coloring_station", VABlocks.COLORING_STATION, ItemGroups.FUNCTIONAL, Items.LOOM);

        //endregion

        //region Iolite

        IOLITE = register("iolite", ItemGroups.INGREDIENTS, Items.ANCIENT_DEBRIS);
        IOLITE_ORE = registerBlockItem("iolite_ore", VABlocks.IOLITE_ORE, ItemGroups.NATURAL, Items.ANCIENT_DEBRIS);
        IOLITE_BLOCK = registerBlockItem("iolite_block", VABlocks.IOLITE_BLOCK, ItemGroups.BUILDING_BLOCKS, Items.NETHERITE_BLOCK);
        WARP_ANCHOR = register("warp_anchor", settings ->  new BlockItem(VABlocks.WARP_ANCHOR, settings), new Item.Settings().rarity(Rarity.RARE).translationKey(VABlocks.WARP_TETHER.getTranslationKey()), ItemGroups.REDSTONE, Items.CAULDRON);
        WARP_TETHER = register("warp_tether", settings -> new BlockItem(VABlocks.WARP_TETHER, settings), new Item.Settings().rarity(Rarity.RARE).translationKey(VABlocks.WARP_ANCHOR.getTranslationKey()).component(VADataComponentTypes.WARP_TETHER_LOCATION, WarpTetherLocationComponent.DEFAULT), ItemGroups.REDSTONE, prev);
        ENTANGLEMENT_DRIVE = register("entanglement_drive", settings ->  new BlockItem(VABlocks.ENTANGLEMENT_DRIVE, settings), new Item.Settings().rarity(Rarity.RARE).translationKey(VABlocks.ENTANGLEMENT_DRIVE.getTranslationKey()), ItemGroups.REDSTONE, prev);
        REMOTE_NOTIFIER = register("remote_notifier", settings ->  new BlockItem(VABlocks.REMOTE_NOTIFIER, settings), new Item.Settings().translationKey(VABlocks.REMOTE_NOTIFIER.getTranslationKey()).requires(VirtualAdditions.PREVIEW), ItemGroups.REDSTONE, prev);

        //endregion

        //region Base Tool Sets

        DIAMOND_TOOL_SET = new ToolSet(Items.DIAMOND_SWORD, Items.DIAMOND_SHOVEL, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_HOE, ToolMaterial.DIAMOND, "diamond");
        GOLDEN_TOOL_SET = new ToolSet(Items.GOLDEN_SWORD, Items.GOLDEN_SHOVEL, Items.GOLDEN_PICKAXE, Items.GOLDEN_AXE, Items.GOLDEN_HOE, ToolMaterial.GOLD, "golden");
        IRON_TOOL_SET = new ToolSet(Items.IRON_SWORD, Items.IRON_SHOVEL, Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_HOE, ToolMaterial.IRON, "iron");
        NETHERITE_TOOL_SET = new ToolSet(Items.NETHERITE_SWORD, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_HOE, ToolMaterial.NETHERITE, "netherite");
        STEEL_TOOL_SET = new ToolSet(STEEL_SWORD, STEEL_SHOVEL, STEEL_PICKAXE, STEEL_AXE, STEEL_HOE, SteelToolMaterial.INSTANCE, "steel");

        //endregion

        //region Gilded Tool Sets

        AMETHYST_DIAMOND_TOOL_SET = registerGildedToolSet(DIAMOND_TOOL_SET, GildTypes.AMETHYST);
        COPPER_DIAMOND_TOOL_SET = registerGildedToolSet(DIAMOND_TOOL_SET, GildTypes.COPPER);
        EMERALD_DIAMOND_TOOL_SET = registerGildedToolSet(DIAMOND_TOOL_SET, GildTypes.EMERALD);
        IOLITE_DIAMOND_TOOL_SET = registerGildedToolSet(DIAMOND_TOOL_SET, GildTypes.IOLITE);
        QUARTZ_DIAMOND_TOOL_SET = registerGildedToolSet(DIAMOND_TOOL_SET, GildTypes.QUARTZ);
        SCULK_DIAMOND_TOOL_SET = registerGildedToolSet(DIAMOND_TOOL_SET, GildTypes.SCULK);
        AMETHYST_IRON_TOOL_SET = registerGildedToolSet(IRON_TOOL_SET, GildTypes.AMETHYST);
        COPPER_IRON_TOOL_SET = registerGildedToolSet(IRON_TOOL_SET, GildTypes.COPPER);
        EMERALD_IRON_TOOL_SET = registerGildedToolSet(IRON_TOOL_SET, GildTypes.EMERALD);
        IOLITE_IRON_TOOL_SET = registerGildedToolSet(IRON_TOOL_SET, GildTypes.IOLITE);
        QUARTZ_IRON_TOOL_SET = registerGildedToolSet(IRON_TOOL_SET, GildTypes.QUARTZ);
        SCULK_IRON_TOOL_SET = registerGildedToolSet(IRON_TOOL_SET, GildTypes.SCULK);
        AMETHYST_GOLDEN_TOOL_SET = registerGildedToolSet(GOLDEN_TOOL_SET, GildTypes.AMETHYST);
        COPPER_GOLDEN_TOOL_SET = registerGildedToolSet(GOLDEN_TOOL_SET, GildTypes.COPPER);
        EMERALD_GOLDEN_TOOL_SET = registerGildedToolSet(GOLDEN_TOOL_SET, GildTypes.EMERALD);
        IOLITE_GOLDEN_TOOL_SET = registerGildedToolSet(GOLDEN_TOOL_SET, GildTypes.IOLITE);
        QUARTZ_GOLDEN_TOOL_SET = registerGildedToolSet(GOLDEN_TOOL_SET, GildTypes.QUARTZ);
        SCULK_GOLDEN_TOOL_SET = registerGildedToolSet(GOLDEN_TOOL_SET, GildTypes.SCULK);
        AMETHYST_NETHERITE_TOOL_SET = registerGildedToolSet(NETHERITE_TOOL_SET, GildTypes.AMETHYST, new Item.Settings().fireproof());
        COPPER_NETHERITE_TOOL_SET = registerGildedToolSet(NETHERITE_TOOL_SET, GildTypes.COPPER, new Item.Settings().fireproof());
        EMERALD_NETHERITE_TOOL_SET = registerGildedToolSet(NETHERITE_TOOL_SET, GildTypes.EMERALD, new Item.Settings().fireproof());
        IOLITE_NETHERITE_TOOL_SET = registerGildedToolSet(NETHERITE_TOOL_SET, GildTypes.IOLITE, new Item.Settings().fireproof());
        QUARTZ_NETHERITE_TOOL_SET = registerGildedToolSet(NETHERITE_TOOL_SET, GildTypes.QUARTZ, new Item.Settings().fireproof());
        SCULK_NETHERITE_TOOL_SET = registerGildedToolSet(NETHERITE_TOOL_SET, GildTypes.SCULK, new Item.Settings().fireproof());
        AMETHYST_STEEL_TOOL_SET = registerGildedToolSet(STEEL_TOOL_SET, GildTypes.AMETHYST, new Item.Settings().fireproof());
        COPPER_STEEL_TOOL_SET = registerGildedToolSet(STEEL_TOOL_SET, GildTypes.COPPER);
        EMERALD_STEEL_TOOL_SET = registerGildedToolSet(STEEL_TOOL_SET, GildTypes.EMERALD);
        IOLITE_STEEL_TOOL_SET = registerGildedToolSet(STEEL_TOOL_SET, GildTypes.IOLITE);
        QUARTZ_STEEL_TOOL_SET = registerGildedToolSet(STEEL_TOOL_SET, GildTypes.QUARTZ);
        SCULK_STEEL_TOOL_SET = registerGildedToolSet(STEEL_TOOL_SET, GildTypes.SCULK);
        
        AMETHYST_TOOL_SETS = new ToolSet[]{AMETHYST_DIAMOND_TOOL_SET, AMETHYST_IRON_TOOL_SET, AMETHYST_GOLDEN_TOOL_SET, AMETHYST_STEEL_TOOL_SET, AMETHYST_NETHERITE_TOOL_SET};
        COPPER_TOOL_SETS = new ToolSet[]{COPPER_DIAMOND_TOOL_SET, COPPER_IRON_TOOL_SET, COPPER_GOLDEN_TOOL_SET, COPPER_STEEL_TOOL_SET, COPPER_NETHERITE_TOOL_SET};
        EMERALD_TOOL_SETS = new ToolSet[]{EMERALD_DIAMOND_TOOL_SET, EMERALD_IRON_TOOL_SET, EMERALD_GOLDEN_TOOL_SET, EMERALD_STEEL_TOOL_SET, EMERALD_NETHERITE_TOOL_SET};
        IOLITE_TOOL_SETS = new ToolSet[]{IOLITE_DIAMOND_TOOL_SET, IOLITE_IRON_TOOL_SET, IOLITE_GOLDEN_TOOL_SET, IOLITE_STEEL_TOOL_SET, IOLITE_NETHERITE_TOOL_SET};
        QUARTZ_TOOL_SETS = new ToolSet[]{QUARTZ_DIAMOND_TOOL_SET, QUARTZ_IRON_TOOL_SET, QUARTZ_GOLDEN_TOOL_SET, QUARTZ_STEEL_TOOL_SET, QUARTZ_NETHERITE_TOOL_SET};
        SCULK_TOOL_SETS = new ToolSet[]{SCULK_DIAMOND_TOOL_SET, SCULK_IRON_TOOL_SET, SCULK_GOLDEN_TOOL_SET, SCULK_STEEL_TOOL_SET, SCULK_NETHERITE_TOOL_SET};

        //endregion

        //region Misc

        //region Climbing Ropes

        CLIMBING_ROPE = register("climbing_rope", settings ->  new ClimbingRopeItem(VABlocks.CLIMBING_ROPE_ANCHOR, settings), new Item.Settings().maxCount(16).useItemPrefixedTranslationKey(), ItemGroups.TOOLS, Items.LEAD);
        EXPOSED_CLIMBING_ROPE = register("exposed_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR, settings), new Item.Settings().maxCount(16).useItemPrefixedTranslationKey(), ItemGroups.TOOLS, prev);
        WEATHERED_CLIMBING_ROPE = register("weathered_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR, settings), new Item.Settings().maxCount(16).useItemPrefixedTranslationKey(), ItemGroups.TOOLS, prev);
        OXIDIZED_CLIMBING_ROPE = register("oxidized_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR, settings), new Item.Settings().maxCount(16).useItemPrefixedTranslationKey(), ItemGroups.TOOLS, prev);
        WAXED_CLIMBING_ROPE = register("waxed_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.WAXED_CLIMBING_ROPE_ANCHOR, settings), new Item.Settings().maxCount(16).useItemPrefixedTranslationKey(), ItemGroups.TOOLS, prev);
        WAXED_EXPOSED_CLIMBING_ROPE = register("waxed_exposed_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR, settings), new Item.Settings().maxCount(16).useItemPrefixedTranslationKey(), ItemGroups.TOOLS, prev);
        WAXED_WEATHERED_CLIMBING_ROPE = register("waxed_weathered_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR, settings), new Item.Settings().maxCount(16).useItemPrefixedTranslationKey(), ItemGroups.TOOLS, prev);
        WAXED_OXIDIZED_CLIMBING_ROPE = register("waxed_oxidized_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR, settings), new Item.Settings().maxCount(16).useItemPrefixedTranslationKey(), ItemGroups.TOOLS, prev);

        //endregion

        //region Hedges

        OAK_HEDGE = registerBlockItem("oak_hedge", VABlocks.OAK_HEDGE, ItemGroups.NATURAL, Items.OAK_LEAVES);
        SPRUCE_HEDGE = registerBlockItem("spruce_hedge", VABlocks.SPRUCE_HEDGE, ItemGroups.NATURAL, Items.SPRUCE_LEAVES);
        BIRCH_HEDGE = registerBlockItem("birch_hedge", VABlocks.BIRCH_HEDGE, ItemGroups.NATURAL, Items.BIRCH_LEAVES);
        JUNGLE_HEDGE = registerBlockItem("jungle_hedge", VABlocks.JUNGLE_HEDGE, ItemGroups.NATURAL, Items.JUNGLE_LEAVES);
        ACACIA_HEDGE = registerBlockItem("acacia_hedge", VABlocks.ACACIA_HEDGE, ItemGroups.NATURAL, Items.ACACIA_LEAVES);
        DARK_OAK_HEDGE = registerBlockItem("dark_oak_hedge", VABlocks.DARK_OAK_HEDGE, ItemGroups.NATURAL, Items.DARK_OAK_LEAVES);
        PALE_OAK_HEDGE = registerBlockItem("pale_oak_hedge", VABlocks.PALE_OAK_HEDGE, ItemGroups.NATURAL, Items.PALE_OAK_LEAVES);
        MANGROVE_HEDGE = registerBlockItem("mangrove_hedge", VABlocks.MANGROVE_HEDGE, ItemGroups.NATURAL, Items.MANGROVE_LEAVES);
        AZALEA_HEDGE = registerBlockItem("azalea_hedge", VABlocks.AZALEA_HEDGE, ItemGroups.NATURAL, Items.AZALEA_LEAVES);
        FLOWERING_AZALEA_HEDGE = registerBlockItem("flowering_azalea_hedge", VABlocks.FLOWERING_AZALEA_HEDGE, ItemGroups.NATURAL, Items.FLOWERING_AZALEA_LEAVES);
        CHERRY_HEDGE = registerBlockItem("cherry_hedge", VABlocks.CHERRY_HEDGE, ItemGroups.NATURAL, Items.CHERRY_LEAVES);
        AEROBLOOM_HEDGE = registerBlockItem("aerobloom_hedge", VABlocks.AEROBLOOM_HEDGE, ItemGroups.NATURAL, VAItems.AEROBLOOM_LEAVES);

        //endregion

        //region Smithing Templates

        TOOL_GILD_SMITHING_TEMPLATE = register("tool_gild_smithing_template", settings -> new SmithingTemplateItem(TOOL_GILD_APPLIES_TO_TEXT, TOOL_GILD_INGREDIENTS_TEXT, TOOL_GILD_BASE_SLOT_DESCRIPTION_TEXT, TOOL_GILD_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                List.of(EMPTY_SLOT_SWORD_TEXTURE, EMPTY_SLOT_SHOVEL_TEXTURE, EMPTY_SLOT_PICKAXE_TEXTURE, EMPTY_SLOT_AXE_TEXTURE, EMPTY_SLOT_HOE_TEXTURE),
                List.of(EMPTY_SLOT_AMETHYST_TEXTURE, EMPTY_SLOT_INGOT_TEXTURE, EMPTY_SLOT_EMERALD_TEXTURE, EMPTY_SLOT_IOLITE_TEXTURE, EMPTY_SLOT_QUARTZ_TEXTURE, EMPTY_SLOT_ECHO_SHARD_TEXTURE),
                settings
        ),new Item.Settings(), ItemGroups.INGREDIENTS, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE);

        EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE = register("exoskeleton_armor_trim_smithing_template", SmithingTemplateItem::of, new Item.Settings().rarity(Rarity.UNCOMMON), ItemGroups.INGREDIENTS, Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE);

        //endregion

        //region Food & Crops

        COTTON_SEEDS = register("cotton_seeds", settings -> new BlockItem(VABlocks.COTTON, settings),new Item.Settings().useItemPrefixedTranslationKey(), ItemGroups.NATURAL, Items.BEETROOT_SEEDS);
        COTTON = register("cotton", ItemGroups.INGREDIENTS, Items.WHEAT);
        CORN = register("corn", new Item.Settings().food(CORN_FOOD), ItemGroups.FOOD_AND_DRINK, Items.BEETROOT);
        ROASTED_CORN = register("roasted_corn", new Item.Settings().food(ROASTED_CORN_FOOD), ItemGroups.FOOD_AND_DRINK, prev);
        CORN_SEEDS = register("corn_seeds", settings -> new BlockItem(VABlocks.CORN_CROP, settings),new Item.Settings().useItemPrefixedTranslationKey(), ItemGroups.NATURAL, COTTON_SEEDS);
        FRIED_EGG = register("fried_egg", new Item.Settings().food(FRIED_EGG_FOOD), ItemGroups.FOOD_AND_DRINK, Items.COOKED_CHICKEN);
        ICE_CREAM = register("ice_cream", new Item.Settings().food(ICE_CREAM_FOOD).maxCount(1), ItemGroups.FOOD_AND_DRINK, Items.COOKIE);
        SWEET_BERRY_PIE = register("sweet_berry_pie", new Item.Settings().food(SWEET_BERRY_PIE_FOOD), ItemGroups.FOOD_AND_DRINK, Items.PUMPKIN_PIE);

        //endregion

        ENGRAVING_CHISEL = register("engraving_chisel", new Item.Settings().maxCount(1).maxDamage(64), ItemGroups.TOOLS, Items.NAME_TAG);


        //endregion

        //region Spawn Eggs

        SALINE_SPAWN_EGG = register("saline_spawn_egg", settings -> new SpawnEggItem(VAEntityType.SALINE, 0x924C2E, 0xE49A6C, settings), new Item.Settings(), ItemGroups.SPAWN_EGGS, Items.RAVAGER_SPAWN_EGG);
        LUMWASP_SPAWN_EGG = register("lumwasp_spawn_egg", settings -> new SpawnEggItem(VAEntityType.LUMWASP, 0x00d67a, 0x214132, settings), new Item.Settings(), ItemGroups.SPAWN_EGGS, Items.LLAMA_SPAWN_EGG);
        LYFT_SPAWN_EGG = register("lyft_spawn_egg", settings -> new SpawnEggItem(VAEntityType.LYFT, 0xB1C1DC, 0x88A1C0, settings), new Item.Settings(), ItemGroups.SPAWN_EGGS, prev);

        //endregion
    }

    public static void init(){
        initDispenserBehaviors();
        initCompostables();
        initLootTableModifiers();
        initCauldronBehaviors();


        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register( (content) -> {
            if (ItemGroups.displayContext == null) return;
            ItemGroups.displayContext.lookup().getOptional(RegistryKeys.POTION).ifPresent((wrapper) -> ItemGroups.addPotions(content, wrapper, VAItems.APPLICABLE_POTION, ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS, ItemGroups.displayContext.enabledFeatures()));
        } );
    }

    //region Initializers

    protected static void initDispenserBehaviors() {
        DispenserBlock.registerBehavior(STEEL_BOMB, new ProjectileDispenserBehavior(STEEL_BOMB));

        Item[] climbingRopes = {VAItems.CLIMBING_ROPE, VAItems.WAXED_CLIMBING_ROPE, VAItems.EXPOSED_CLIMBING_ROPE, VAItems.WAXED_EXPOSED_CLIMBING_ROPE, VAItems.WEATHERED_CLIMBING_ROPE, VAItems.WAXED_WEATHERED_CLIMBING_ROPE, VAItems.OXIDIZED_CLIMBING_ROPE, VAItems.WAXED_OXIDIZED_CLIMBING_ROPE};

        for (Item item : climbingRopes) {
            DispenserBehavior climbingRopeBehavior = new ProjectileDispenserBehavior(item);
            DispenserBlock.registerBehavior(item, climbingRopeBehavior);
        }

        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.CHARTREUSE).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.MAROON).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.INDIGO).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.PLUM).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.VIRIDIAN).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.TAN).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.SINOPIA).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.LILAC).asItem(), new BlockPlacementDispenserBehavior());

        DispenserBlock.registerBehavior(ACID_BUCKET, new ItemDispenserBehavior(){
            private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                FluidModificationItem fluidModificationItem = (FluidModificationItem)stack.getItem();
                BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
                World world = pointer.world();
                if (fluidModificationItem.placeFluid(null, world, blockPos, null)) {
                    fluidModificationItem.onEmptied(null, world, stack, blockPos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return this.fallbackBehavior.dispense(pointer, stack);
                }
            }
        });
    }

    protected static void initCompostables() {
        ComposterBlock.registerCompostableItem(0.3F, COTTON_SEEDS);
        ComposterBlock.registerCompostableItem(0.3F, COTTON);
        ComposterBlock.registerCompostableItem(0.65F, CORN);
        ComposterBlock.registerCompostableItem(0.3F, CORN_SEEDS);
    }

    protected static void initLootTableModifiers() {
        LootTableEvents.MODIFY.register( ((key, tableBuilder, source, registries) -> {

            if (!source.isBuiltin()) return;

            if (LootTables.SPAWN_BONUS_CHEST.equals(key)) {
                LootPool.Builder bundleBuilder = LootPool.builder()
                        .with(ItemEntry.builder(Items.BUNDLE));
                tableBuilder.pool(bundleBuilder);
            }

            // Ominous Trial Spawner Throwables
            if (LootTables.TRIAL_CHAMBER_ITEMS_TO_DROP_WHEN_OMINOUS_SPAWNER.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.with(ItemEntry.builder(Items.LINGERING_POTION).apply(SetPotionLootFunction.builder(VAPotions.STRONG_FRAILTY)));
                    }
                    if (i[0] == 1) {
                        builder.with(ItemEntry.builder(STEEL_BOMB).apply(SetComponentsLootFunction.builder(VADataComponentTypes.EXPLOSIVE_CONTENTS, ExplosiveContentComponent.KEEP_BLOCKS)));
                    }
                    i[0]++;
                });
            }

            // Grass Drop
            if (Blocks.SHORT_GRASS.getLootTableKey().isPresent() && Blocks.SHORT_GRASS.getLootTableKey().get().equals(key)) {
                RegistryEntryLookup<Enchantment> enchantmentLookup = registries.getOrThrow(RegistryKeys.ENCHANTMENT);
                RegistryEntryLookup<Item> itemRegistryEntryLookup = registries.getOrThrow(RegistryKeys.ITEM);
                LootPool.Builder cottonBuilder = LootPool.builder()
                        .with(ItemEntry.builder(COTTON_SEEDS)
                                .apply(ApplyBonusLootFunction.uniformBonusCount(enchantmentLookup.getOrThrow(Enchantments.FORTUNE), 2))
                                .apply(ExplosionDecayLootFunction.builder())
                                .conditionally(RandomChanceLootCondition.builder(0.125F))
                                .conditionally(InvertedLootCondition.builder(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(itemRegistryEntryLookup, Items.SHEARS))))
                        );
                LootPool.Builder cornBuilder = LootPool.builder()
                        .with(ItemEntry.builder(CORN_SEEDS)
                                .apply(ApplyBonusLootFunction.uniformBonusCount(enchantmentLookup.getOrThrow(Enchantments.FORTUNE), 2))
                                .apply(ExplosionDecayLootFunction.builder())
                                .conditionally(RandomChanceLootCondition.builder(0.125F))
                                .conditionally(InvertedLootCondition.builder(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(itemRegistryEntryLookup, Items.SHEARS))))
                        );
                tableBuilder.pool(cottonBuilder).pool(cornBuilder);
            }

            // Abandoned Mineshaft Chest
            if (LootTables.ABANDONED_MINESHAFT_CHEST.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 2) {
                        builder
                                .with(ItemEntry.builder(CLIMBING_ROPE).weight(7).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 16))))
                                .with(ItemEntry.builder(EXPOSED_CLIMBING_ROPE).weight(7).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 16))))
                                .with(ItemEntry.builder(WEATHERED_CLIMBING_ROPE).weight(7).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 16))))
                                .with(ItemEntry.builder(OXIDIZED_CLIMBING_ROPE).weight(7).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 16))));
                    }
                    i[0]++;
                });
            }

            // Village Toolsmith and Weaponsmith Chests
            if (LootTables.VILLAGE_TOOLSMITH_CHEST.equals(key) || LootTables.VILLAGE_WEAPONSMITH_CHEST.equals(key)) {
                tableBuilder.modifyPools( builder -> builder
                        .with(ItemEntry.builder(STEEL_INGOT).weight(3).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4))))
                );
                LootPool.Builder smithingTemplateBuilder = LootPool.builder()
                        .with(ItemEntry.builder(TOOL_GILD_SMITHING_TEMPLATE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 3))));
                tableBuilder.pool(smithingTemplateBuilder);
                LootPool.Builder gildMaterialBuilder = LootPool.builder()
                        .with(ItemEntry.builder(Items.COPPER_INGOT).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4))))
                        .with(ItemEntry.builder(Items.AMETHYST_SHARD).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4))))
                        .with(ItemEntry.builder(Items.EMERALD).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4))));
                tableBuilder.pool(gildMaterialBuilder);

            }

            // Savannah and Desert Village House Chests
            if (LootTables.VILLAGE_SAVANNA_HOUSE_CHEST.equals(key) || LootTables.VILLAGE_DESERT_HOUSE_CHEST.equals(key)) {
                tableBuilder.modifyPools( builder -> builder.with(ItemEntry.builder(CORN).weight(5).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4)))));
            }

            // Jungle Temple Chest
            if (LootTables.JUNGLE_TEMPLE_CHEST.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.with(ItemEntry.builder(STEEL_INGOT).weight(5).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 5))));
                    }
                    i[0]++;
                });
            }

            // End City Chest
            if (LootTables.END_CITY_TREASURE_CHEST.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.with(ItemEntry.builder(IOLITE).weight(5).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4))))
                                .with(ItemEntry.builder(STEEL_INGOT).weight(10).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 6))));
                    }
                    i[0]++;
                });
            }

            // Ancient City Loot
            if (LootTables.ANCIENT_CITY_CHEST.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.with(ItemEntry.builder(EMERALD_DIAMOND_TOOL_SET.HOE())
                                .weight(2)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.8F, 1), false))
                        );
                    }
                    i[0]++;
                });
            }

            // Zombie Loot
            if (EntityType.ZOMBIE.getLootTableKey().get().equals(key) || EntityType.HUSK.getLootTableKey().get().equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 1) {
                        builder.with(ItemEntry.builder(CORN).apply(
                                FurnaceSmeltLootFunction.builder().conditionally(
                                        EntityPropertiesLootCondition.builder(
                                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onFire(true))
                                        )
                                )
                        ));
                    }
                    i[0]++;
                });
            }
        } ));
    }

    protected static void initCauldronBehaviors() {
        Map<Item, CauldronBehavior> waterBehaviors = CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map();
        waterBehaviors.put(VAItems.ENGRAVING_CHISEL, CauldronBehavior::cleanArmor);
        waterBehaviors.put(VAItems.CHARTREUSE_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.MAROON_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.INDIGO_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.PLUM_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.VIRIDIAN_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.TAN_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.SINOPIA_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.LILAC_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.CHARTREUSE_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.MAROON_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.INDIGO_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.PLUM_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.VIRIDIAN_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.TAN_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.SINOPIA_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.LILAC_BANNER, CauldronBehavior::cleanBanner);
    }

    //endregion
}