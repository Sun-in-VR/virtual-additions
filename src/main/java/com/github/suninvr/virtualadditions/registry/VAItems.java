package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.component.ExplosiveContentComponent;
import com.github.suninvr.virtualadditions.component.IncenseEffectsComponent;
import com.github.suninvr.virtualadditions.item.*;
import com.github.suninvr.virtualadditions.item.materials.SteelToolMaterial;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.util.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.FuelValues;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;
import static com.github.suninvr.virtualadditions.registry.RegistryHelper.ItemRegistryHelper.*;

public class VAItems {
    //region Declarations

    public static final ToolSet COPPER_TOOL_SET;
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
    public static final Item POLISHED_BLUESCHIST_WALL;
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
    public static final Item POLISHED_SYENITE_WALL;
    public static final Item SYENITE_BRICKS;
    public static final Item CRACKED_SYENITE_BRICKS;
    public static final Item SYENITE_BRICK_STAIRS;
    public static final Item SYENITE_BRICK_SLAB;
    public static final Item SYENITE_BRICK_WALL;
    public static final Item CHISELED_SYENITE;
    public static final Item PORPHYRY;
    public static final Item PORPHYRY_STAIRS;
    public static final Item PORPHYRY_SLAB;
    public static final Item PORPHYRY_WALL;
    public static final Item PORPHYRY_BRICKS;
    public static final Item PORPHYRY_BRICK_STAIRS;
    public static final Item PORPHYRY_BRICK_SLAB;
    public static final Item PORPHYRY_BRICK_WALL;
    public static final Item POLISHED_PORPHYRY;
    public static final Item POLISHED_PORPHYRY_STAIRS;
    public static final Item POLISHED_PORPHYRY_SLAB;
    public static final Item POLISHED_PORPHYRY_WALL;
    public static final Item SMALL_SPRING_LOTUS;
    public static final Item SPRING_LOTUS;
    public static final Item SOULBLOOM_LOG;
    public static final Item SOULBLOOM_WOOD;
    public static final Item STRIPPED_SOULBLOOM_LOG;
    public static final Item STRIPPED_SOULBLOOM_WOOD;
    public static final Item SOULBLOOM_PLANKS;
    public static final Item SOULBLOOM_STAIRS;
    public static final Item SOULBLOOM_SLAB;
    public static final Item SOULBLOOM_FENCE;
    public static final Item SOULBLOOM_FENCE_GATE;
    public static final Item SOULBLOOM_DOOR;
    public static final Item SOULBLOOM_TRAPDOOR;
    public static final Item SOULBLOOM_PRESSURE_PLATE;
    public static final Item SOULBLOOM_BUTTON;
    public static final Item SOULBLOOM_SIGN;
    public static final Item SOULBLOOM_HANGING_SIGN;
    public static final Item SOULBLOOM_SHELF;
    public static final Item SOULBLOOM_LEAVES;
    public static final Item SOULBLOOM_HEDGE;
    public static final Item SOULBLOOM_SAPLING;
    public static final Item SOULBLOOM_BOAT;
    public static final Item SOULBLOOM_CHEST_BOAT;
    public static final Item BLUE_PETALS;
    public static final Item SOUL_SPROUT;
    public static final Item BALLOON_FRUIT;
    public static final Item NECROTIC_NYLIUM;
    public static final Item NECROTIC_ROOTS;
    public static final Item BONE_LITTER;
    public static final Item BONE_PILE;
    public static final Item WITHERED_LOG;
    public static final Item WITHERED_WOOD;
    public static final Item STRIPPED_WITHERED_LOG;
    public static final Item STRIPPED_WITHERED_WOOD;
    public static final Item WITHERED_PLANKS;
    public static final Item WITHERED_STAIRS;
    public static final Item WITHERED_SLAB;
    public static final Item WITHERED_FENCE;
    public static final Item WITHERED_FENCE_GATE;
    public static final Item WITHERED_DOOR;
    public static final Item WITHERED_TRAPDOOR;
    public static final Item WITHERED_PRESSURE_PLATE;
    public static final Item WITHERED_BUTTON;
    public static final Item WITHERED_SIGN;
    public static final Item WITHERED_HANGING_SIGN;
    public static final Item WITHERED_SHELF;
    public static final Item WITHERED_LEAVES;
    public static final Item WITHERED_HEDGE;
    public static final Item WITHERED_SAPLING;
    public static final Item SPECTRAL_TORCH;
    public static final Item SPECTRAL_LANTERN;
    public static final Item SPECTRAL_POWDER;
    public static final Item SPECTRAL_SAND;
    public static final Item SPECTRAL_SPYGLASS;
    public static final Item ZEBRANO_LOG;
    public static final Item ZEBRANO_WOOD;
    public static final Item STRIPPED_ZEBRANO_LOG;
    public static final Item STRIPPED_ZEBRANO_WOOD;
    public static final Item ZEBRANO_PLANKS;
    public static final Item ZEBRANO_STAIRS;
    public static final Item ZEBRANO_SLAB;
    public static final Item ZEBRANO_FENCE;
    public static final Item ZEBRANO_FENCE_GATE;
    public static final Item ZEBRANO_DOOR;
    public static final Item ZEBRANO_TRAPDOOR;
    public static final Item ZEBRANO_PRESSURE_PLATE;
    public static final Item ZEBRANO_BUTTON;
    public static final Item ZEBRANO_SIGN;
    public static final Item ZEBRANO_HANGING_SIGN;
    public static final Item ZEBRANO_SHELF;
    public static final Item ZEBRANO_LEAVES;
    public static final Item ZEBRANO_HEDGE;
    public static final Item ZEBRANO_SAPLING;
    public static final Item ZEBRANO_BOAT;
    public static final Item ZEBRANO_CHEST_BOAT;
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
    public static final Item ROCK_SALT_BRICKS;
    public static final Item ROCK_SALT_BRICK_STAIRS;
    public static final Item ROCK_SALT_BRICK_SLAB;
    public static final Item ROCK_SALT_BRICK_WALL;
    public static final Item CHISELED_ROCK_SALT_BRICKS;
    public static final Item ROCK_SALT_CRYSTAL;
    public static final Item ROCK_SALT_ORE;
    public static final Item DEEPSLATE_ROCK_SALT_ORE;
    public static final Item ROCK_SALT;
    public static final Item SPOTLIGHT;
    public static final Item RAW_STEEL;
    public static final Item RAW_STEEL_BLOCK;
    public static final Item STEEL_INGOT;
    public static final Item STEEL_NUGGET;
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
    public static final Item STEEL_SPEAR;
    public static final Item STEEL_HALBERD;
    public static final Item STEEL_HELMET;
    public static final Item STEEL_CHESTPLATE;
    public static final Item STEEL_LEGGINGS;
    public static final Item STEEL_BOOTS;
    public static final Item STEEL_HORSE_ARMOR;
    public static final Item STEEL_NAUTILUS_ARMOR;
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
    public static final Item CORN_SEEDS;
    public static final Item CORN;
    public static final Item ROASTED_CORN;
    public static final Item TORTILLA;
    public static final Item TOMATO_SEEDS;
    public static final Item TOMATO;
    public static final Item TOMATO_SOUP;
    public static final Item CABBAGE_SEEDS;
    public static final Item CABBAGE;
    public static final Item SALAD;
    public static final Item TACO;
    public static final Item COTTON_SEEDS;
    public static final Item COTTON;
    public static final Item SOY_BEANS;
    public static final Item WISDOM_BERRY_SEEDS;
    public static final Item WISDOM_BERRY;
    public static final Item FRIED_EGG;
    public static final Item ICE_CREAM;
    public static final Item CHEESE_WEDGE;
    public static final Item SWEET_BERRY_PIE;
    public static final Item BEEF_JERKY;
    public static final Item PORK_JERKY;
    public static final Item CHICKEN_JERKY;
    public static final Item MUTTON_JERKY;
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
    public static final Item SOY_OIL_BUCKET;
    public static final Item APPLICABLE_POTION;
    public static final Item LIGHTNING_BOTTLE;
    public static final Item PURPLE_EGG;
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
    public static final Item CHARTREUSE_GLAZED_TERRACOTTA;
    public static final Item CHARTREUSE_BUNDLE;
    public static final Item CHARTREUSE_HARNESS;
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
    public static final Item MAROON_GLAZED_TERRACOTTA;
    public static final Item MAROON_BUNDLE;
    public static final Item MAROON_HARNESS;
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
    public static final Item INDIGO_GLAZED_TERRACOTTA;
    public static final Item INDIGO_BUNDLE;
    public static final Item INDIGO_HARNESS;
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
    public static final Item PLUM_GLAZED_TERRACOTTA;
    public static final Item PLUM_BUNDLE;
    public static final Item PLUM_HARNESS;
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
    public static final Item VIRIDIAN_GLAZED_TERRACOTTA;
    public static final Item VIRIDIAN_BUNDLE;
    public static final Item VIRIDIAN_HARNESS;
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
    public static final Item TAN_GLAZED_TERRACOTTA;
    public static final Item TAN_BUNDLE;
    public static final Item TAN_HARNESS;
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
    public static final Item SINOPIA_GLAZED_TERRACOTTA;
    public static final Item SINOPIA_BUNDLE;
    public static final Item SINOPIA_HARNESS;
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
    public static final Item LILAC_GLAZED_TERRACOTTA;
    public static final Item LILAC_BUNDLE;
    public static final Item LILAC_HARNESS;
    public static final Item COLORING_STATION;
    public static final Item INCENSE;
    public static final Item TOOL_GILD_SMITHING_TEMPLATE;
    public static final Item EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE;
    public static final Item ROBE_ARMOR_TRIM_SMITHING_TEMPLATE;
    public static final Item IOLITE;
    public static final Item IOLITE_ORE;
    public static final Item IOLITE_BLOCK;
    public static final Item PORTAL_CORE;
    public static final Item DRAINED_PORTAL_CORE;
    public static final Item ENTANGLEMENT_DRIVE;
    public static final Item REMOTE_NOTIFIER;
    public static final Item WOODEN_HALBERD;
    public static final Item STONE_HALBERD;
    public static final Item COPPER_HALBERD;
    public static final Item IRON_HALBERD;
    public static final Item GOLDEN_HALBERD;
    public static final Item DIAMOND_HALBERD;
    public static final Item NETHERITE_HALBERD;
    public static final Item SLINGSHOT;
    public static final Item SALINE_SPAWN_EGG;
    public static final Item LUMWASP_SPAWN_EGG;
    public static final Item SPECTRE_SPAWN_EGG;

    private static final Component TOOL_GILD_APPLIES_TO_TEXT;
    private static final Component TOOL_GILD_INGREDIENTS_TEXT;
    private static final Component TOOL_GILD_BASE_SLOT_DESCRIPTION_TEXT;
    private static final Component TOOL_GILD_ADDITIONS_SLOT_DESCRIPTION_TEXT;
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

    //endregion

    static {
        //region Identifiers

        TOOL_GILD_APPLIES_TO_TEXT = Component.translatable(Util.makeDescriptionId("item", idOf("smithing_template.tool_gild.applies_to"))).withStyle(ChatFormatting.BLUE);
        TOOL_GILD_INGREDIENTS_TEXT = Component.translatable(Util.makeDescriptionId("item", idOf("smithing_template.tool_gild.ingredients"))).withStyle(ChatFormatting.BLUE);
        TOOL_GILD_BASE_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", idOf("smithing_template.tool_gild.base_slot_description")));
        TOOL_GILD_ADDITIONS_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", idOf("smithing_template.tool_gild.additions_slot_description")));

        EMPTY_SLOT_HOE_TEXTURE = Identifier.withDefaultNamespace("container/slot/hoe");
        EMPTY_SLOT_AXE_TEXTURE = Identifier.withDefaultNamespace("container/slot/axe");
        EMPTY_SLOT_SWORD_TEXTURE = Identifier.withDefaultNamespace("container/slot/sword");
        EMPTY_SLOT_SHOVEL_TEXTURE = Identifier.withDefaultNamespace("container/slot/shovel");
        EMPTY_SLOT_PICKAXE_TEXTURE = Identifier.withDefaultNamespace("container/slot/pickaxe");
        EMPTY_SLOT_INGOT_TEXTURE = Identifier.withDefaultNamespace("container/slot/ingot");
        EMPTY_SLOT_QUARTZ_TEXTURE = Identifier.withDefaultNamespace("container/slot/quartz");
        EMPTY_SLOT_EMERALD_TEXTURE = Identifier.withDefaultNamespace("container/slot/emerald");
        EMPTY_SLOT_AMETHYST_TEXTURE = Identifier.withDefaultNamespace("container/slot/amethyst_shard");
        EMPTY_SLOT_ECHO_SHARD_TEXTURE = idOf("container/slot/echo_shard");

        //endregion

        //region Deep Stone Variants

        //region Hornfels

        HORNFELS = registerBlockItem("hornfels", VABlocks.HORNFELS, at(CreativeModeTabs.NATURAL_BLOCKS, Items.DEEPSLATE), at(CreativeModeTabs.BUILDING_BLOCKS, Items.DEEPSLATE_TILE_WALL));
        COBBLED_HORNFELS = registerBlockItem("cobbled_hornfels", VABlocks.COBBLED_HORNFELS, CreativeModeTabs.BUILDING_BLOCKS, HORNFELS);
        COBBLED_HORNFELS_STAIRS = registerBlockItem("cobbled_hornfels_stairs", VABlocks.COBBLED_HORNFELS_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_HORNFELS);
        COBBLED_HORNFELS_SLAB = registerBlockItem("cobbled_hornfels_slab", VABlocks.COBBLED_HORNFELS_SLAB, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_HORNFELS_STAIRS);
        COBBLED_HORNFELS_WALL = registerBlockItem("cobbled_hornfels_wall", VABlocks.COBBLED_HORNFELS_WALL, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_HORNFELS_SLAB);
        POLISHED_HORNFELS = registerBlockItem("polished_hornfels", VABlocks.POLISHED_HORNFELS, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_HORNFELS_WALL);
        POLISHED_HORNFELS_STAIRS = registerBlockItem("polished_hornfels_stairs", VABlocks.POLISHED_HORNFELS_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, POLISHED_HORNFELS);
        POLISHED_HORNFELS_SLAB = registerBlockItem("polished_hornfels_slab", VABlocks.POLISHED_HORNFELS_SLAB, CreativeModeTabs.BUILDING_BLOCKS, POLISHED_HORNFELS_STAIRS);
        HORNFELS_TILES = registerBlockItem("hornfels_tiles", VABlocks.HORNFELS_TILES, CreativeModeTabs.BUILDING_BLOCKS, POLISHED_HORNFELS_SLAB);
        CRACKED_HORNFELS_TILES = registerBlockItem("cracked_hornfels_tiles", VABlocks.CRACKED_HORNFELS_TILES, CreativeModeTabs.BUILDING_BLOCKS, HORNFELS_TILES);
        HORNFELS_TILE_STAIRS = registerBlockItem("hornfels_tile_stairs", VABlocks.HORNFELS_TILE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, CRACKED_HORNFELS_TILES);
        HORNFELS_TILE_SLAB = registerBlockItem("hornfels_tile_slab", VABlocks.HORNFELS_TILE_SLAB, CreativeModeTabs.BUILDING_BLOCKS, HORNFELS_TILE_STAIRS);
        CHISELED_HORNFELS = registerBlockItem("chiseled_hornfels", VABlocks.CHISELED_HORNFELS, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_HORNFELS_WALL);
        CHISELED_HORNFELS_TILES = registerBlockItem("chiseled_hornfels_tiles", VABlocks.CHISELED_HORNFELS_TILES, CreativeModeTabs.BUILDING_BLOCKS, HORNFELS_TILE_SLAB);

        //endregion

        //region Blueschist

        BLUESCHIST = registerBlockItem("blueschist", VABlocks.BLUESCHIST, at(CreativeModeTabs.NATURAL_BLOCKS, HORNFELS), at(CreativeModeTabs.BUILDING_BLOCKS, prev));
        COBBLED_BLUESCHIST = registerBlockItem("cobbled_blueschist", VABlocks.COBBLED_BLUESCHIST, CreativeModeTabs.BUILDING_BLOCKS, BLUESCHIST);
        COBBLED_BLUESCHIST_STAIRS = registerBlockItem("cobbled_blueschist_stairs", VABlocks.COBBLED_BLUESCHIST_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_BLUESCHIST);
        COBBLED_BLUESCHIST_SLAB = registerBlockItem("cobbled_blueschist_slab", VABlocks.COBBLED_BLUESCHIST_SLAB, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_BLUESCHIST_STAIRS);
        COBBLED_BLUESCHIST_WALL = registerBlockItem("cobbled_blueschist_wall", VABlocks.COBBLED_BLUESCHIST_WALL, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_BLUESCHIST_SLAB);
        POLISHED_BLUESCHIST = registerBlockItem("polished_blueschist", VABlocks.POLISHED_BLUESCHIST, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_BLUESCHIST_WALL);
        POLISHED_BLUESCHIST_STAIRS = registerBlockItem("polished_blueschist_stairs", VABlocks.POLISHED_BLUESCHIST_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, POLISHED_BLUESCHIST);
        POLISHED_BLUESCHIST_SLAB = registerBlockItem("polished_blueschist_slab", VABlocks.POLISHED_BLUESCHIST_SLAB, CreativeModeTabs.BUILDING_BLOCKS, POLISHED_BLUESCHIST_STAIRS);
        POLISHED_BLUESCHIST_WALL = registerBlockItem("polished_blueschist_wall", VABlocks.POLISHED_BLUESCHIST_WALL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        BLUESCHIST_BRICKS = registerBlockItem("blueschist_bricks", VABlocks.BLUESCHIST_BRICKS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        CRACKED_BLUESCHIST_BRICKS = registerBlockItem("cracked_blueschist_bricks", VABlocks.CRACKED_BLUESCHIST_BRICKS, CreativeModeTabs.BUILDING_BLOCKS, BLUESCHIST_BRICKS);
        BLUESCHIST_BRICK_STAIRS = registerBlockItem("blueschist_brick_stairs", VABlocks.BLUESCHIST_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, CRACKED_BLUESCHIST_BRICKS);
        BLUESCHIST_BRICK_SLAB = registerBlockItem("blueschist_brick_slab", VABlocks.BLUESCHIST_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS, BLUESCHIST_BRICK_STAIRS);
        BLUESCHIST_BRICK_WALL = registerBlockItem("blueschist_brick_wall", VABlocks.BLUESCHIST_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS, BLUESCHIST_BRICK_SLAB);
        CHISELED_BLUESCHIST = registerBlockItem("chiseled_blueschist", VABlocks.CHISELED_BLUESCHIST, CreativeModeTabs.BUILDING_BLOCKS, prev);

        //endregion

        //region Syenite

        SYENITE = registerBlockItem("syenite", VABlocks.SYENITE, at(CreativeModeTabs.NATURAL_BLOCKS, BLUESCHIST), at(CreativeModeTabs.BUILDING_BLOCKS, prev));
        COBBLED_SYENITE = registerBlockItem("cobbled_syenite", VABlocks.COBBLED_SYENITE, CreativeModeTabs.BUILDING_BLOCKS, SYENITE);
        COBBLED_SYENITE_STAIRS = registerBlockItem("cobbled_syenite_stairs", VABlocks.COBBLED_SYENITE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_SYENITE);
        COBBLED_SYENITE_SLAB = registerBlockItem("cobbled_syenite_slab", VABlocks.COBBLED_SYENITE_SLAB, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_SYENITE_STAIRS);
        COBBLED_SYENITE_WALL = registerBlockItem("cobbled_syenite_wall", VABlocks.COBBLED_SYENITE_WALL, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_SYENITE_SLAB);
        POLISHED_SYENITE = registerBlockItem("polished_syenite", VABlocks.POLISHED_SYENITE, CreativeModeTabs.BUILDING_BLOCKS, COBBLED_SYENITE_WALL);
        POLISHED_SYENITE_STAIRS = registerBlockItem("polished_syenite_stairs", VABlocks.POLISHED_SYENITE_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, POLISHED_SYENITE);
        POLISHED_SYENITE_SLAB = registerBlockItem("polished_syenite_slab", VABlocks.POLISHED_SYENITE_SLAB, CreativeModeTabs.BUILDING_BLOCKS, POLISHED_SYENITE_STAIRS);
        POLISHED_SYENITE_WALL = registerBlockItem("polished_syenite_wall", VABlocks.POLISHED_SYENITE_WALL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        SYENITE_BRICKS = registerBlockItem("syenite_bricks", VABlocks.SYENITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        CRACKED_SYENITE_BRICKS = registerBlockItem("cracked_syenite_bricks", VABlocks.CRACKED_SYENITE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS, SYENITE_BRICKS);
        SYENITE_BRICK_STAIRS = registerBlockItem("syenite_brick_stairs", VABlocks.SYENITE_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, CRACKED_SYENITE_BRICKS);
        SYENITE_BRICK_SLAB = registerBlockItem("syenite_brick_slab", VABlocks.SYENITE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS, SYENITE_BRICK_STAIRS);
        SYENITE_BRICK_WALL = registerBlockItem("syenite_brick_wall", VABlocks.SYENITE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS, SYENITE_BRICK_SLAB);
        CHISELED_SYENITE = registerBlockItem("chiseled_syenite", VABlocks.CHISELED_SYENITE, CreativeModeTabs.BUILDING_BLOCKS, prev);

        //endregion

        //endregion

        //region Porphyry

        PORPHYRY = registerBlockItem("porphyry", VABlocks.PORPHYRY, at(CreativeModeTabs.BUILDING_BLOCKS, Items.CHISELED_TUFF_BRICKS), at(CreativeModeTabs.NATURAL_BLOCKS, Items.TUFF));
        PORPHYRY_STAIRS = registerBlockItem("porphyry_stairs", VABlocks.PORPHYRY_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        PORPHYRY_SLAB = registerBlockItem("porphyry_slab", VABlocks.PORPHYRY_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        PORPHYRY_WALL = registerBlockItem("porphyry_wall", VABlocks.PORPHYRY_WALL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        PORPHYRY_BRICKS = registerBlockItem("porphyry_bricks", VABlocks.PORPHYRY_BRICKS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        PORPHYRY_BRICK_STAIRS = registerBlockItem("porphyry_brick_stairs", VABlocks.PORPHYRY_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        PORPHYRY_BRICK_SLAB = registerBlockItem("porphyry_brick_slab", VABlocks.PORPHYRY_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        PORPHYRY_BRICK_WALL = registerBlockItem("porphyry_brick_wall", VABlocks.PORPHYRY_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        POLISHED_PORPHYRY = registerBlockItem("polished_porphyry", VABlocks.POLISHED_PORPHYRY, CreativeModeTabs.BUILDING_BLOCKS, prev);
        POLISHED_PORPHYRY_STAIRS = registerBlockItem("polished_porphyry_stairs", VABlocks.POLISHED_PORPHYRY_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        POLISHED_PORPHYRY_SLAB = registerBlockItem("polished_porphyry_slab", VABlocks.POLISHED_PORPHYRY_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        POLISHED_PORPHYRY_WALL = registerBlockItem("polished_porphyry_wall", VABlocks.POLISHED_PORPHYRY_WALL, CreativeModeTabs.BUILDING_BLOCKS, prev);

        //endregion

        //region Soulbloom

        SOULBLOOM_LOG = registerBlockItem("soulbloom_log", VABlocks.SOULBLOOM_LOG, at(CreativeModeTabs.BUILDING_BLOCKS, Items.CHERRY_BUTTON), at(CreativeModeTabs.NATURAL_BLOCKS, Items.CHERRY_LOG));
        SOULBLOOM_WOOD = registerBlockItem("soulbloom_wood", VABlocks.SOULBLOOM_WOOD, CreativeModeTabs.BUILDING_BLOCKS, prev);
        STRIPPED_SOULBLOOM_LOG = registerBlockItem("stripped_soulbloom_log", VABlocks.STRIPPED_SOULBLOOM_LOG, CreativeModeTabs.BUILDING_BLOCKS, prev);
        STRIPPED_SOULBLOOM_WOOD = registerBlockItem("stripped_soulbloom_wood", VABlocks.STRIPPED_SOULBLOOM_WOOD, CreativeModeTabs.BUILDING_BLOCKS, prev);
        SOULBLOOM_PLANKS = registerBlockItem("soulbloom_planks", VABlocks.SOULBLOOM_PLANKS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        SOULBLOOM_STAIRS = registerBlockItem("soulbloom_stairs", VABlocks.SOULBLOOM_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        SOULBLOOM_SLAB = registerBlockItem("soulbloom_slab", VABlocks.SOULBLOOM_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        SOULBLOOM_FENCE = registerBlockItem("soulbloom_fence", VABlocks.SOULBLOOM_FENCE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        SOULBLOOM_FENCE_GATE = registerBlockItem("soulbloom_fence_gate", VABlocks.SOULBLOOM_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        SOULBLOOM_DOOR = registerBlockItem("soulbloom_door", VABlocks.SOULBLOOM_DOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        SOULBLOOM_TRAPDOOR = registerBlockItem("soulbloom_trapdoor", VABlocks.SOULBLOOM_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        SOULBLOOM_PRESSURE_PLATE = registerBlockItem("soulbloom_pressure_plate", VABlocks.SOULBLOOM_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        SOULBLOOM_BUTTON = registerBlockItem("soulbloom_button", VABlocks.SOULBLOOM_BUTTON, CreativeModeTabs.BUILDING_BLOCKS, prev);
        SOULBLOOM_SIGN = register("soulbloom_sign", settings ->  new SignItem(VABlocks.SOULBLOOM_SIGN, VABlocks.SOULBLOOM_WALL_SIGN, settings), new Item.Properties().overrideDescription(VABlocks.SOULBLOOM_SIGN.getDescriptionId()).stacksTo(16), CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.CHERRY_HANGING_SIGN);
        SOULBLOOM_HANGING_SIGN = register("soulbloom_hanging_sign", settings ->  new HangingSignItem(VABlocks.SOULBLOOM_HANGING_SIGN, VABlocks.SOULBLOOM_WALL_HANGING_SIGN, settings), new Item.Properties().overrideDescription(VABlocks.SOULBLOOM_HANGING_SIGN.getDescriptionId()).stacksTo(16), CreativeModeTabs.FUNCTIONAL_BLOCKS, prev);
        SOULBLOOM_SHELF = registerBlockItem("soulbloom_shelf", VABlocks.SOULBLOOM_SHELF, at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.CHERRY_SHELF));
        SOULBLOOM_LEAVES = registerBlockItem("soulbloom_leaves", VABlocks.SOULBLOOM_LEAVES, CreativeModeTabs.NATURAL_BLOCKS, Items.CHERRY_LEAVES);
        SOULBLOOM_SAPLING = registerBlockItem("soulbloom_sapling", VABlocks.SOULBLOOM_SAPLING, CreativeModeTabs.NATURAL_BLOCKS, Items.CHERRY_SAPLING);
        SOULBLOOM_BOAT = register("soulbloom_boat", settings -> new BoatItem(VAEntityType.SOULBLOOM_BOAT, settings), new Item.Properties().stacksTo(1), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.CHERRY_CHEST_BOAT);
        SOULBLOOM_CHEST_BOAT = register("soulbloom_chest_boat", settings -> new BoatItem(VAEntityType.SOULBLOOM_CHEST_BOAT, settings), new Item.Properties().stacksTo(1), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);

        BLUE_PETALS = registerBlockItem("blue_petals", VABlocks.BLUE_PETALS, CreativeModeTabs.NATURAL_BLOCKS, Items.PINK_PETALS);
        SOUL_SPROUT = registerBlockItem("soul_sprout", VABlocks.SOUL_SPROUT, CreativeModeTabs.NATURAL_BLOCKS, Items.TORCHFLOWER);

        SMALL_SPRING_LOTUS = registerBlockItem("small_spring_lotus", VABlocks.SMALL_SPRING_LOTUS, CreativeModeTabs.NATURAL_BLOCKS, Items.SMALL_DRIPLEAF);
        SPRING_LOTUS = registerBlockItem("spring_lotus", VABlocks.SPRING_LOTUS, CreativeModeTabs.NATURAL_BLOCKS, prev);

        BALLOON_FRUIT = register("balloon_fruit", settings -> new BalloonFruitItem(VABlocks.BALLOON_BULB_BUD, settings), new Item.Properties().food(VAFoodComponents.BALLOON_FRUIT, VAFoodComponents.BALLOON_FRUIT_CONSUMABLE), CreativeModeTabs.FOOD_AND_DRINKS, Items.CHORUS_FRUIT);

        //endregion

        //region Wither Woods

        NECROTIC_NYLIUM = registerBlockItem("necrotic_nylium", VABlocks.NECROTIC_NYLIUM, CreativeModeTabs.NATURAL_BLOCKS, Items.WARPED_NYLIUM);
        NECROTIC_ROOTS = registerBlockItem("necrotic_roots", VABlocks.NECROTIC_ROOTS, CreativeModeTabs.NATURAL_BLOCKS, Items.WARPED_ROOTS);
        
        BONE_LITTER = registerBlockItem("bone_litter", VABlocks.BONE_LITTER, CreativeModeTabs.NATURAL_BLOCKS, prev);
        BONE_PILE = registerBlockItem("bone_pile", VABlocks.BONE_PILE, CreativeModeTabs.NATURAL_BLOCKS, prev);

        WITHERED_LOG = registerBlockItem("withered_log", VABlocks.WITHERED_LOG, at(CreativeModeTabs.BUILDING_BLOCKS, Items.WARPED_BUTTON), at(CreativeModeTabs.NATURAL_BLOCKS, Items.WARPED_STEM));
        WITHERED_WOOD = registerBlockItem("withered_wood", VABlocks.WITHERED_WOOD, CreativeModeTabs.BUILDING_BLOCKS, prev);
        STRIPPED_WITHERED_LOG = registerBlockItem("stripped_withered_log", VABlocks.STRIPPED_WITHERED_LOG, CreativeModeTabs.BUILDING_BLOCKS, prev);
        STRIPPED_WITHERED_WOOD = registerBlockItem("stripped_withered_wood", VABlocks.STRIPPED_WITHERED_WOOD, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WITHERED_PLANKS = registerBlockItem("withered_planks", VABlocks.WITHERED_PLANKS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WITHERED_STAIRS = registerBlockItem("withered_stairs", VABlocks.WITHERED_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WITHERED_SLAB = registerBlockItem("withered_slab", VABlocks.WITHERED_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WITHERED_FENCE = registerBlockItem("withered_fence", VABlocks.WITHERED_FENCE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WITHERED_FENCE_GATE = registerBlockItem("withered_fence_gate", VABlocks.WITHERED_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WITHERED_DOOR = registerBlockItem("withered_door", VABlocks.WITHERED_DOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WITHERED_TRAPDOOR = registerBlockItem("withered_trapdoor", VABlocks.WITHERED_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WITHERED_PRESSURE_PLATE = registerBlockItem("withered_pressure_plate", VABlocks.WITHERED_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WITHERED_BUTTON = registerBlockItem("withered_button", VABlocks.WITHERED_BUTTON, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WITHERED_SIGN = register("withered_sign", settings ->  new SignItem(VABlocks.WITHERED_SIGN, VABlocks.WITHERED_WALL_SIGN, settings), new Item.Properties().overrideDescription(VABlocks.WITHERED_SIGN.getDescriptionId()).stacksTo(16), CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.WARPED_HANGING_SIGN);
        WITHERED_HANGING_SIGN = register("withered_hanging_sign", settings ->  new HangingSignItem(VABlocks.WITHERED_HANGING_SIGN, VABlocks.WITHERED_WALL_HANGING_SIGN, settings), new Item.Properties().overrideDescription(VABlocks.WITHERED_HANGING_SIGN.getDescriptionId()).stacksTo(16), CreativeModeTabs.FUNCTIONAL_BLOCKS, prev);
        WITHERED_SHELF = registerBlockItem("withered_shelf", VABlocks.WITHERED_SHELF, at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.WARPED_SHELF));
        WITHERED_LEAVES = registerBlockItem("withered_leaves", VABlocks.WITHERED_LEAVES, CreativeModeTabs.NATURAL_BLOCKS, Items.FLOWERING_AZALEA_LEAVES);
        WITHERED_SAPLING = registerBlockItem("withered_sapling", VABlocks.WITHERED_SAPLING, CreativeModeTabs.NATURAL_BLOCKS, Items.FLOWERING_AZALEA);

        //endregion

        //region Spectral Items

        SPECTRAL_TORCH = register("spectral_torch", settings -> new StandingAndWallBlockItem(VABlocks.SPECTRAL_TORCH, VABlocks.SPECTRAL_WALL_TORCH, Direction.DOWN, settings), new Item.Properties(), CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.SOUL_TORCH);
        SPECTRAL_LANTERN = registerBlockItem("spectral_lantern", VABlocks.SPECTRAL_LANTERN, CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.SOUL_LANTERN);
        SPECTRAL_POWDER = register("spectral_powder", CreativeModeTabs.INGREDIENTS, Items.BLAZE_POWDER);
        SPECTRAL_SAND = registerBlockItem("spectral_sand", VABlocks.SPECTRAL_SAND, CreativeModeTabs.NATURAL_BLOCKS, Items.RED_SANDSTONE);

        SPECTRAL_SPYGLASS = register("spectral_spyglass", ProjectionSpyglassItem::new, new Item.Properties().stacksTo(1), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.SPYGLASS);

        //endregion

        ZEBRANO_LOG = registerBlockItem("zebrano_log", VABlocks.ZEBRANO_LOG, at(CreativeModeTabs.BUILDING_BLOCKS, Items.MANGROVE_BUTTON), at(CreativeModeTabs.NATURAL_BLOCKS, Items.WARPED_STEM));
        ZEBRANO_WOOD = registerBlockItem("zebrano_wood", VABlocks.ZEBRANO_WOOD, CreativeModeTabs.BUILDING_BLOCKS, prev);
        STRIPPED_ZEBRANO_LOG = registerBlockItem("stripped_zebrano_log", VABlocks.STRIPPED_ZEBRANO_LOG, CreativeModeTabs.BUILDING_BLOCKS, prev);
        STRIPPED_ZEBRANO_WOOD = registerBlockItem("stripped_zebrano_wood", VABlocks.STRIPPED_ZEBRANO_WOOD, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ZEBRANO_PLANKS = registerBlockItem("zebrano_planks", VABlocks.ZEBRANO_PLANKS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ZEBRANO_STAIRS = registerBlockItem("zebrano_stairs", VABlocks.ZEBRANO_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ZEBRANO_SLAB = registerBlockItem("zebrano_slab", VABlocks.ZEBRANO_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ZEBRANO_FENCE = registerBlockItem("zebrano_fence", VABlocks.ZEBRANO_FENCE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ZEBRANO_FENCE_GATE = registerBlockItem("zebrano_fence_gate", VABlocks.ZEBRANO_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ZEBRANO_DOOR = registerBlockItem("zebrano_door", VABlocks.ZEBRANO_DOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ZEBRANO_TRAPDOOR = registerBlockItem("zebrano_trapdoor", VABlocks.ZEBRANO_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ZEBRANO_PRESSURE_PLATE = registerBlockItem("zebrano_pressure_plate", VABlocks.ZEBRANO_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ZEBRANO_BUTTON = registerBlockItem("zebrano_button", VABlocks.ZEBRANO_BUTTON, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ZEBRANO_SIGN = register("zebrano_sign", settings ->  new SignItem(VABlocks.ZEBRANO_SIGN, VABlocks.ZEBRANO_WALL_SIGN, settings), new Item.Properties().overrideDescription(VABlocks.ZEBRANO_SIGN.getDescriptionId()).stacksTo(16), CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.MANGROVE_HANGING_SIGN);
        ZEBRANO_HANGING_SIGN = register("zebrano_hanging_sign", settings ->  new HangingSignItem(VABlocks.ZEBRANO_HANGING_SIGN, VABlocks.ZEBRANO_WALL_HANGING_SIGN, settings), new Item.Properties().overrideDescription(VABlocks.ZEBRANO_HANGING_SIGN.getDescriptionId()).stacksTo(16), CreativeModeTabs.FUNCTIONAL_BLOCKS, prev);
        ZEBRANO_SHELF = registerBlockItem("zebrano_shelf", VABlocks.ZEBRANO_SHELF, at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.WARPED_SHELF));
        ZEBRANO_LEAVES = registerBlockItem("zebrano_leaves", VABlocks.ZEBRANO_LEAVES, CreativeModeTabs.NATURAL_BLOCKS, Items.MANGROVE_LEAVES);
        ZEBRANO_SAPLING = registerBlockItem("zebrano_sapling", VABlocks.ZEBRANO_SAPLING, CreativeModeTabs.NATURAL_BLOCKS, Items.MANGROVE_PROPAGULE);
        ZEBRANO_BOAT = register("zebrano_boat", settings -> new BoatItem(VAEntityType.ZEBRANO_BOAT, settings), new Item.Properties().stacksTo(1), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.MANGROVE_CHEST_BOAT);
        ZEBRANO_CHEST_BOAT = register("zebrano_chest_boat", settings -> new BoatItem(VAEntityType.ZEBRANO_CHEST_BOAT, settings), new Item.Properties().stacksTo(1), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);

        //region Salty Caves

        ROCK_SALT_BLOCK = registerBlockItem("rock_salt_block", VABlocks.ROCK_SALT_BLOCK, at(CreativeModeTabs.NATURAL_BLOCKS, Items.AMETHYST_CLUSTER), at(CreativeModeTabs.BUILDING_BLOCKS, Items.MUD_BRICK_WALL));
        ROCK_SALT_BRICKS = registerBlockItem("rock_salt_bricks", VABlocks.ROCK_SALT_BRICKS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ROCK_SALT_BRICK_STAIRS = registerBlockItem("rock_salt_brick_stairs", VABlocks.ROCK_SALT_BRICK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ROCK_SALT_BRICK_SLAB = registerBlockItem("rock_salt_brick_slab", VABlocks.ROCK_SALT_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ROCK_SALT_BRICK_WALL = registerBlockItem("rock_salt_brick_wall", VABlocks.ROCK_SALT_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        CHISELED_ROCK_SALT_BRICKS = registerBlockItem("chiseled_rock_salt_bricks", VABlocks.CHISELED_ROCK_SALT_BRICKS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        ROCK_SALT_CRYSTAL = registerBlockItem("rock_salt_crystal", VABlocks.ROCK_SALT_CRYSTAL, CreativeModeTabs.NATURAL_BLOCKS, ROCK_SALT_BLOCK);
        ROCK_SALT_ORE = registerBlockItem("rock_salt_ore", VABlocks.ROCK_SALT_ORE, CreativeModeTabs.NATURAL_BLOCKS, Items.DEEPSLATE_COAL_ORE);
        DEEPSLATE_ROCK_SALT_ORE = registerBlockItem("deepslate_rock_salt_ore", VABlocks.DEEPSLATE_ROCK_SALT_ORE, CreativeModeTabs.NATURAL_BLOCKS, prev);
        ROCK_SALT = register("rock_salt", new Item.Properties().food(VAFoodComponents.ROCK_SALT, VAFoodComponents.ROCK_SALT_CONSUMABLE).trimMaterial(VAArmorTrimMaterials.ROCK_SALT), at(CreativeModeTabs.INGREDIENTS, Items.SUGAR), at(CreativeModeTabs.FOOD_AND_DRINKS, Items.SPIDER_EYE));

        //endregion

        //region Halberd

        WOODEN_HALBERD = register("wooden_halberd", HalberdItem::new, VAToolUtil.halberdSettings(new Item.Properties(), ToolMaterial.WOOD, 7.0F, -3.35F), CreativeModeTabs.COMBAT, Items.NETHERITE_AXE);
        STONE_HALBERD = register("stone_halberd", HalberdItem::new, VAToolUtil.halberdSettings(new Item.Properties(), ToolMaterial.STONE, 8.0F, -3.35F), CreativeModeTabs.COMBAT, prev);
        COPPER_HALBERD = register("copper_halberd", HalberdItem::new, VAToolUtil.halberdSettings(new Item.Properties(), ToolMaterial.COPPER, 8.0F, -3.35F), CreativeModeTabs.COMBAT, prev);
        IRON_HALBERD = register("iron_halberd", HalberdItem::new, VAToolUtil.halberdSettings(new Item.Properties(), ToolMaterial.IRON, 9.0F, -3.35F), CreativeModeTabs.COMBAT, prev);
        GOLDEN_HALBERD = register("golden_halberd", HalberdItem::new, VAToolUtil.halberdSettings(new Item.Properties(), ToolMaterial.GOLD, 7.0F, -3.35F), CreativeModeTabs.COMBAT, prev);
        DIAMOND_HALBERD = register("diamond_halberd", HalberdItem::new, VAToolUtil.halberdSettings(new Item.Properties(), ToolMaterial.DIAMOND, 10.0F, -3.35F), CreativeModeTabs.COMBAT, prev);
        NETHERITE_HALBERD = register("netherite_halberd", HalberdItem::new, VAToolUtil.halberdSettings(new Item.Properties().fireResistant(), ToolMaterial.NETHERITE, 11.0F, -3.35F), CreativeModeTabs.COMBAT, prev);

        //endregion

        //region Steel

        RAW_STEEL_BLOCK = registerBlockItem("raw_steel_block", VABlocks.RAW_STEEL_BLOCK, CreativeModeTabs.NATURAL_BLOCKS, Items.RAW_GOLD_BLOCK);
        STEEL_BLOCK = registerBlockItem("steel_block", VABlocks.STEEL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS, Items.GOLD_BLOCK);
        CUT_STEEL = registerBlockItem("cut_steel", VABlocks.CUT_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        CUT_STEEL_STAIRS = registerBlockItem("cut_steel_stairs", VABlocks.CUT_STEEL_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        CUT_STEEL_SLAB = registerBlockItem("cut_steel_slab", VABlocks.CUT_STEEL_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        STEEL_GRATE = registerBlockItem("steel_grate", VABlocks.STEEL_GRATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        CHISELED_STEEL = registerBlockItem("chiseled_steel", VABlocks.CHISELED_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        STEEL_FENCE = registerBlockItem("steel_fence", VABlocks.STEEL_FENCE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        STEEL_DOOR = registerBlockItem("steel_door", VABlocks.STEEL_DOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        STEEL_TRAPDOOR = registerBlockItem("steel_trapdoor", VABlocks.STEEL_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        REDSTONE_BRIDGE = registerBlockItem("redstone_bridge", VABlocks.REDSTONE_BRIDGE, CreativeModeTabs.REDSTONE_BLOCKS, Items.COMPARATOR);
        CAGELIGHT = registerBlockItem("cagelight", VABlocks.CAGELIGHT, CreativeModeTabs.BUILDING_BLOCKS, STEEL_TRAPDOOR);
        SPOTLIGHT = registerBlockItem("spotlight", VABlocks.SPOTLIGHT, CreativeModeTabs.REDSTONE_BLOCKS, Items.REDSTONE_LAMP);
        RAW_STEEL = register("raw_steel", CreativeModeTabs.INGREDIENTS, Items.RAW_GOLD);
        STEEL_INGOT = register("steel_ingot", new Item.Properties().trimMaterial(VAArmorTrimMaterials.STEEL), CreativeModeTabs.INGREDIENTS, Items.GOLD_INGOT);
        STEEL_NUGGET = register("steel_nugget", CreativeModeTabs.INGREDIENTS, Items.GOLD_NUGGET);
        STEEL_BOMB = register("steel_bomb", SteelBombItem::new, new Item.Properties().stacksTo(16).component(VADataComponentTypes.EXPLOSIVE_CONTENTS, ExplosiveContentComponent.DEFAULT), at(CreativeModeTabs.COMBAT, Items.SNOWBALL), at(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.LEAD));
        STEEL_SWORD = register("steel_sword", settings -> new Item(settings.sword(SteelToolMaterial.INSTANCE, 3, -2.4F)), new Item.Properties(), CreativeModeTabs.COMBAT, Items.GOLDEN_SWORD);
        STEEL_SHOVEL = register("steel_shovel", settings -> new ShovelItem(SteelToolMaterial.INSTANCE, 1.5F, -3.0F, settings), new Item.Properties(), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.GOLDEN_HOE);
        STEEL_PICKAXE = register("steel_pickaxe", settings -> new Item(settings.pickaxe(SteelToolMaterial.INSTANCE, 1, -2.8F)), new Item.Properties(), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);
        STEEL_AXE = register("steel_axe", settings -> new AxeItem(SteelToolMaterial.INSTANCE, 5.5F, -3.1F, settings), new Item.Properties(), at(CreativeModeTabs.TOOLS_AND_UTILITIES, prev), at(CreativeModeTabs.COMBAT, Items.GOLDEN_AXE));
        STEEL_HOE = register("steel_hoe", settings -> new HoeItem(SteelToolMaterial.INSTANCE, -2, -1.0F, settings), new Item.Properties(), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);
        STEEL_SPEAR = register("steel_spear", new Item.Properties().spear(SteelToolMaterial.INSTANCE, 1.00F, 1.00F, 0.55F, 2.75F, 7.75F, 4.25F, 5.1F, 10.625F, 4.6F), CreativeModeTabs.COMBAT, Items.GOLDEN_SPEAR);
        STEEL_HALBERD = register("steel_halberd", HalberdItem::new, VAToolUtil.halberdSettings(new Item.Properties(), SteelToolMaterial.INSTANCE, 9.5F, -3.35F), CreativeModeTabs.COMBAT, GOLDEN_HALBERD);
        STEEL_HELMET = register("steel_helmet", settings -> new Item(settings.humanoidArmor(VAArmorMaterial.STEEL, ArmorType.HELMET)), new Item.Properties().stacksTo(1).durability(ArmorType.HELMET.getDurability(24)), CreativeModeTabs.COMBAT, Items.GOLDEN_BOOTS);
        STEEL_CHESTPLATE = register("steel_chestplate", settings -> new Item(settings.humanoidArmor(VAArmorMaterial.STEEL, ArmorType.CHESTPLATE)), new Item.Properties().stacksTo(1).durability(ArmorType.CHESTPLATE.getDurability(24)), CreativeModeTabs.COMBAT, prev);
        STEEL_LEGGINGS = register("steel_leggings", settings -> new Item(settings.humanoidArmor(VAArmorMaterial.STEEL, ArmorType.LEGGINGS)), new Item.Properties().stacksTo(1).durability(ArmorType.LEGGINGS.getDurability(24)), CreativeModeTabs.COMBAT, prev);
        STEEL_BOOTS = register("steel_boots", settings -> new Item(settings.humanoidArmor(VAArmorMaterial.STEEL, ArmorType.BOOTS)), new Item.Properties().stacksTo(1).durability(ArmorType.BOOTS.getDurability(24)), CreativeModeTabs.COMBAT, prev);
        STEEL_HORSE_ARMOR = register("steel_horse_armor", new Item.Properties().horseArmor(VAArmorMaterial.STEEL), at(CreativeModeTabs.COMBAT, Items.GOLDEN_HORSE_ARMOR));
        STEEL_NAUTILUS_ARMOR = register("steel_nautilus_armor", new Item.Properties().nautilusArmor(VAArmorMaterial.STEEL), at(CreativeModeTabs.COMBAT, Items.GOLDEN_NAUTILUS_ARMOR));

        //region Exposed Blocks

        EXPOSED_STEEL_BLOCK = registerBlockItem("exposed_steel_block", VABlocks.EXPOSED_STEEL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS, CAGELIGHT);
        EXPOSED_CUT_STEEL = registerBlockItem("exposed_cut_steel", VABlocks.EXPOSED_CUT_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        EXPOSED_CUT_STEEL_STAIRS = registerBlockItem("exposed_cut_steel_stairs", VABlocks.EXPOSED_CUT_STEEL_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        EXPOSED_CUT_STEEL_SLAB = registerBlockItem("exposed_cut_steel_slab", VABlocks.EXPOSED_CUT_STEEL_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        EXPOSED_STEEL_GRATE = registerBlockItem("exposed_steel_grate", VABlocks.EXPOSED_STEEL_GRATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        EXPOSED_CHISELED_STEEL = registerBlockItem("exposed_chiseled_steel", VABlocks.EXPOSED_CHISELED_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        EXPOSED_STEEL_FENCE = registerBlockItem("exposed_steel_fence", VABlocks.EXPOSED_STEEL_FENCE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        EXPOSED_STEEL_DOOR = registerBlockItem("exposed_steel_door", VABlocks.EXPOSED_STEEL_DOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        EXPOSED_STEEL_TRAPDOOR = registerBlockItem("exposed_steel_trapdoor", VABlocks.EXPOSED_STEEL_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);

        //endregion

        //region Weathered Blocks

        WEATHERED_STEEL_BLOCK = registerBlockItem("weathered_steel_block", VABlocks.WEATHERED_STEEL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WEATHERED_CUT_STEEL = registerBlockItem("weathered_cut_steel", VABlocks.WEATHERED_CUT_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WEATHERED_CUT_STEEL_STAIRS = registerBlockItem("weathered_cut_steel_stairs", VABlocks.WEATHERED_CUT_STEEL_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WEATHERED_CUT_STEEL_SLAB = registerBlockItem("weathered_cut_steel_slab", VABlocks.WEATHERED_CUT_STEEL_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WEATHERED_STEEL_GRATE = registerBlockItem("weathered_steel_grate", VABlocks.WEATHERED_STEEL_GRATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WEATHERED_CHISELED_STEEL = registerBlockItem("weathered_chiseled_steel", VABlocks.WEATHERED_CHISELED_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WEATHERED_STEEL_FENCE = registerBlockItem("weathered_steel_fence", VABlocks.WEATHERED_STEEL_FENCE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WEATHERED_STEEL_DOOR = registerBlockItem("weathered_steel_door", VABlocks.WEATHERED_STEEL_DOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WEATHERED_STEEL_TRAPDOOR = registerBlockItem("weathered_steel_trapdoor", VABlocks.WEATHERED_STEEL_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);

        //endregion

        //region Oxidized Blocks

        OXIDIZED_STEEL_BLOCK = registerBlockItem("oxidized_steel_block", VABlocks.OXIDIZED_STEEL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS, prev);
        OXIDIZED_CUT_STEEL = registerBlockItem("oxidized_cut_steel", VABlocks.OXIDIZED_CUT_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        OXIDIZED_CUT_STEEL_STAIRS = registerBlockItem("oxidized_cut_steel_stairs", VABlocks.OXIDIZED_CUT_STEEL_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        OXIDIZED_CUT_STEEL_SLAB = registerBlockItem("oxidized_cut_steel_slab", VABlocks.OXIDIZED_CUT_STEEL_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        OXIDIZED_STEEL_GRATE = registerBlockItem("oxidized_steel_grate", VABlocks.OXIDIZED_STEEL_GRATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        OXIDIZED_CHISELED_STEEL = registerBlockItem("oxidized_chiseled_steel", VABlocks.OXIDIZED_CHISELED_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        OXIDIZED_STEEL_FENCE = registerBlockItem("oxidized_steel_fence", VABlocks.OXIDIZED_STEEL_FENCE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        OXIDIZED_STEEL_DOOR = registerBlockItem("oxidized_steel_door", VABlocks.OXIDIZED_STEEL_DOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        OXIDIZED_STEEL_TRAPDOOR = registerBlockItem("oxidized_steel_trapdoor", VABlocks.OXIDIZED_STEEL_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);

        //endregion

        //region Waxed Blocks

        WAXED_STEEL_BLOCK = registerBlockItem("waxed_steel_block", VABlocks.WAXED_STEEL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_CUT_STEEL = registerBlockItem("waxed_cut_steel", VABlocks.WAXED_CUT_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_CUT_STEEL_STAIRS = registerBlockItem("waxed_cut_steel_stairs", VABlocks.WAXED_CUT_STEEL_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_CUT_STEEL_SLAB = registerBlockItem("waxed_cut_steel_slab", VABlocks.WAXED_CUT_STEEL_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_STEEL_GRATE = registerBlockItem("waxed_steel_grate", VABlocks.WAXED_STEEL_GRATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_CHISELED_STEEL = registerBlockItem("waxed_chiseled_steel", VABlocks.WAXED_CHISELED_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_STEEL_FENCE = registerBlockItem("waxed_steel_fence", VABlocks.WAXED_STEEL_FENCE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_STEEL_DOOR = registerBlockItem("waxed_steel_door", VABlocks.WAXED_STEEL_DOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_STEEL_TRAPDOOR = registerBlockItem("waxed_steel_trapdoor", VABlocks.WAXED_STEEL_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);

        //endregion

        //region Waxed Exposed Blocks

        WAXED_EXPOSED_STEEL_BLOCK = registerBlockItem("waxed_exposed_steel_block", VABlocks.WAXED_EXPOSED_STEEL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_CUT_STEEL = registerBlockItem("waxed_exposed_cut_steel", VABlocks.WAXED_EXPOSED_CUT_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_CUT_STEEL_STAIRS = registerBlockItem("waxed_exposed_cut_steel_stairs", VABlocks.WAXED_EXPOSED_CUT_STEEL_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_CUT_STEEL_SLAB = registerBlockItem("waxed_exposed_cut_steel_slab", VABlocks.WAXED_EXPOSED_CUT_STEEL_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_STEEL_GRATE = registerBlockItem("waxed_exposed_steel_grate", VABlocks.WAXED_EXPOSED_STEEL_GRATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_CHISELED_STEEL = registerBlockItem("waxed_exposed_chiseled_steel", VABlocks.WAXED_EXPOSED_CHISELED_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_STEEL_FENCE = registerBlockItem("waxed_exposed_steel_fence", VABlocks.WAXED_EXPOSED_STEEL_FENCE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_STEEL_DOOR = registerBlockItem("waxed_exposed_steel_door", VABlocks.WAXED_EXPOSED_STEEL_DOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_EXPOSED_STEEL_TRAPDOOR = registerBlockItem("waxed_exposed_steel_trapdoor", VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);

        //endregion

        //region Waxed Weathered Blocks

        WAXED_WEATHERED_STEEL_BLOCK = registerBlockItem("waxed_weathered_steel_block", VABlocks.WAXED_WEATHERED_STEEL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_CUT_STEEL = registerBlockItem("waxed_weathered_cut_steel", VABlocks.WAXED_WEATHERED_CUT_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_CUT_STEEL_STAIRS = registerBlockItem("waxed_weathered_cut_steel_stairs", VABlocks.WAXED_WEATHERED_CUT_STEEL_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_CUT_STEEL_SLAB = registerBlockItem("waxed_weathered_cut_steel_slab", VABlocks.WAXED_WEATHERED_CUT_STEEL_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_STEEL_GRATE = registerBlockItem("waxed_weathered_steel_grate", VABlocks.WAXED_WEATHERED_STEEL_GRATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_CHISELED_STEEL = registerBlockItem("waxed_weathered_chiseled_steel", VABlocks.WAXED_WEATHERED_CHISELED_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_STEEL_FENCE = registerBlockItem("waxed_weathered_steel_fence", VABlocks.WAXED_WEATHERED_STEEL_FENCE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_STEEL_DOOR = registerBlockItem("waxed_weathered_steel_door", VABlocks.WAXED_WEATHERED_STEEL_DOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_WEATHERED_STEEL_TRAPDOOR = registerBlockItem("waxed_weathered_steel_trapdoor", VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);

        //endregion

        //region Waxed Oxidized Blocks

        WAXED_OXIDIZED_STEEL_BLOCK = registerBlockItem("waxed_oxidized_steel_block", VABlocks.WAXED_OXIDIZED_STEEL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_CUT_STEEL = registerBlockItem("waxed_oxidized_cut_steel", VABlocks.WAXED_OXIDIZED_CUT_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_CUT_STEEL_STAIRS = registerBlockItem("waxed_oxidized_cut_steel_stairs", VABlocks.WAXED_OXIDIZED_CUT_STEEL_STAIRS, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_CUT_STEEL_SLAB = registerBlockItem("waxed_oxidized_cut_steel_slab", VABlocks.WAXED_OXIDIZED_CUT_STEEL_SLAB, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_STEEL_GRATE = registerBlockItem("waxed_oxidized_steel_grate", VABlocks.WAXED_OXIDIZED_STEEL_GRATE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_CHISELED_STEEL = registerBlockItem("waxed_oxidized_chiseled_steel", VABlocks.WAXED_OXIDIZED_CHISELED_STEEL, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_STEEL_FENCE = registerBlockItem("waxed_oxidized_steel_fence", VABlocks.WAXED_OXIDIZED_STEEL_FENCE, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_STEEL_DOOR = registerBlockItem("waxed_oxidized_steel_door", VABlocks.WAXED_OXIDIZED_STEEL_DOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);
        WAXED_OXIDIZED_STEEL_TRAPDOOR = registerBlockItem("waxed_oxidized_steel_trapdoor", VABlocks.WAXED_OXIDIZED_STEEL_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS, prev);

        //endregion

        //endregion

        //region Wasp Den

        SILK_BLOCK = registerBlockItem("silk_block", VABlocks.SILK_BLOCK, CreativeModeTabs.NATURAL_BLOCKS, Items.SHROOMLIGHT);
        LUMWASP_NEST = registerBlockItem("lumwasp_nest", VABlocks.LUMWASP_NEST, CreativeModeTabs.NATURAL_BLOCKS, prev);
        WEBBED_SILK = registerBlockItem("webbed_silk", VABlocks.WEBBED_SILK, CreativeModeTabs.NATURAL_BLOCKS, prev);
        FRAYED_SILK = registerBlockItem("frayed_silk", VABlocks.FRAYED_SILK, CreativeModeTabs.NATURAL_BLOCKS, Items.DEAD_BUSH);
        GREENCAP_MUSHROOM = registerBlockItem("greencap_mushroom", VABlocks.GREENCAP_MUSHROOM, CreativeModeTabs.NATURAL_BLOCKS, Items.RED_MUSHROOM);
        TALL_GREENCAP_MUSHROOMS = registerBlockItem("tall_greencap_mushrooms", VABlocks.TALL_GREENCAP_MUSHROOMS, CreativeModeTabs.NATURAL_BLOCKS, Items.PITCHER_PLANT);
        GLOWING_SILK = registerBlockItem("glowing_silk", VABlocks.GLOWING_SILK, CreativeModeTabs.NATURAL_BLOCKS, Items.HANGING_ROOTS);
        SILK_THREAD = register("silk_thread", CreativeModeTabs.INGREDIENTS, Items.STRING);
        LUMWASP_MANDIBLE = register("lumwasp_mandible", CreativeModeTabs.INGREDIENTS, Items.FERMENTED_SPIDER_EYE);
        SILKBULB = registerBlockItem("silkbulb", VABlocks.SILKBULB, at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.GLOWSTONE), at(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_BANNER), at(CreativeModeTabs.NATURAL_BLOCKS, WEBBED_SILK));
        ACID_BLOCK = registerBlockItem("acid_block", VABlocks.ACID_BLOCK, CreativeModeTabs.NATURAL_BLOCKS, prev);

        //region Colorful Silkbulbs

        WHITE_SILKBULB = registerBlockItem("white_silkbulb", VABlocks.WHITE_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        LIGHT_GRAY_SILKBULB = registerBlockItem("light_gray_silkbulb", VABlocks.LIGHT_GRAY_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        GRAY_SILKBULB = registerBlockItem("gray_silkbulb", VABlocks.GRAY_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        BLACK_SILKBULB = registerBlockItem("black_silkbulb", VABlocks.BLACK_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        BROWN_SILKBULB = registerBlockItem("brown_silkbulb", VABlocks.BROWN_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        RED_SILKBULB = registerBlockItem("red_silkbulb", VABlocks.RED_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        ORANGE_SILKBULB = registerBlockItem("orange_silkbulb", VABlocks.ORANGE_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        YELLOW_SILKBULB = registerBlockItem("yellow_silkbulb", VABlocks.YELLOW_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        LIME_SILKBULB = registerBlockItem("lime_silkbulb", VABlocks.LIME_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        GREEN_SILKBULB = registerBlockItem("green_silkbulb", VABlocks.GREEN_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        CYAN_SILKBULB = registerBlockItem("cyan_silkbulb", VABlocks.CYAN_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        LIGHT_BLUE_SILKBULB = registerBlockItem("light_blue_silkbulb", VABlocks.LIGHT_BLUE_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        BLUE_SILKBULB = registerBlockItem("blue_silkbulb", VABlocks.BLUE_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        PURPLE_SILKBULB = registerBlockItem("purple_silkbulb", VABlocks.PURPLE_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        MAGENTA_SILKBULB = registerBlockItem("magenta_silkbulb", VABlocks.MAGENTA_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);
        PINK_SILKBULB = registerBlockItem("pink_silkbulb", VABlocks.PINK_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, prev);

        //endregion

        ACID_BUCKET = register("acid_bucket", AcidBucketItem::new, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.LAVA_BUCKET);
        APPLICABLE_POTION = register("applicable_potion", ApplicablePotionItem::new, new Item.Properties().stacksTo(16).component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY));


        //endregion

        //region Dyes

        //region Chartreuse Dyables

        CHARTREUSE_DYE = register("chartreuse_dye", settings ->  new DyeItem(VADyeColors.CHARTREUSE, settings), new Item.Properties(), CreativeModeTabs.INGREDIENTS, Items.YELLOW_DYE);
        CHARTREUSE_WOOL = registerBlockItem("chartreuse_wool", VABlocks.CHARTREUSE_WOOL, CreativeModeTabs.COLORED_BLOCKS, Blocks.YELLOW_WOOL.asItem());
        CHARTREUSE_CARPET = registerBlockItem("chartreuse_carpet", VABlocks.CHARTREUSE_CARPET, CreativeModeTabs.COLORED_BLOCKS, Blocks.YELLOW_CARPET.asItem());
        CHARTREUSE_TERRACOTTA = registerBlockItem("chartreuse_terracotta", VABlocks.CHARTREUSE_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Blocks.YELLOW_TERRACOTTA.asItem());
        CHARTREUSE_GLAZED_TERRACOTTA = registerBlockItem("chartreuse_glazed_terracotta", VABlocks.CHARTREUSE_GLAZED_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Items.YELLOW_GLAZED_TERRACOTTA);
        CHARTREUSE_CONCRETE = registerBlockItem("chartreuse_concrete", VABlocks.CHARTREUSE_CONCRETE, CreativeModeTabs.COLORED_BLOCKS, Blocks.YELLOW_CONCRETE.asItem());
        CHARTREUSE_CONCRETE_POWDER = registerBlockItem("chartreuse_concrete_powder", VABlocks.CHARTREUSE_CONCRETE_POWDER, CreativeModeTabs.COLORED_BLOCKS, Blocks.YELLOW_CONCRETE_POWDER.asItem());
        CHARTREUSE_STAINED_GLASS = registerBlockItem("chartreuse_stained_glass", VABlocks.CHARTREUSE_STAINED_GLASS, CreativeModeTabs.COLORED_BLOCKS, Blocks.YELLOW_STAINED_GLASS.asItem());
        CHARTREUSE_STAINED_GLASS_PANE = registerBlockItem("chartreuse_stained_glass_pane", VABlocks.CHARTREUSE_STAINED_GLASS_PANE, CreativeModeTabs.COLORED_BLOCKS, Blocks.YELLOW_STAINED_GLASS_PANE.asItem());
        CHARTREUSE_CANDLE = registerBlockItem("chartreuse_candle", VABlocks.CHARTREUSE_CANDLE, at(CreativeModeTabs.COLORED_BLOCKS, Blocks.YELLOW_CANDLE.asItem()), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Blocks.YELLOW_CANDLE.asItem()));
        CHARTREUSE_SILKBULB = registerBlockItem("chartreuse_silkbulb", VABlocks.CHARTREUSE_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, VAItems.YELLOW_SILKBULB);
        CHARTREUSE_BED = register("chartreuse_bed", settings -> new BedItem(VABlocks.CHARTREUSE_BED, settings), new Item.Properties().overrideDescription(VABlocks.CHARTREUSE_BED.getDescriptionId()).stacksTo(1), at(CreativeModeTabs.COLORED_BLOCKS, Items.YELLOW_BED), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.YELLOW_BED));
        CHARTREUSE_SHULKER_BOX = register("chartreuse_shulker_box", settings ->  new BlockItem(VABlocks.CHARTREUSE_SHULKER_BOX, settings), new Item.Properties().overrideDescription(VABlocks.CHARTREUSE_SHULKER_BOX.getDescriptionId()).stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.YELLOW_SHULKER_BOX), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.YELLOW_SHULKER_BOX));
        CHARTREUSE_BANNER = register("chartreuse_banner", settings ->  new BannerItem(VABlocks.CHARTREUSE_BANNER, VABlocks.CHARTREUSE_WALL_BANNER, settings), new Item.Properties().overrideDescription(VABlocks.CHARTREUSE_BANNER.getDescriptionId()).stacksTo(16).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.YELLOW_BANNER), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.YELLOW_BANNER));
        CHARTREUSE_BUNDLE = register("chartreuse_bundle", BundleItem::new, new Item.Properties().stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.YELLOW_BUNDLE);
        CHARTREUSE_HARNESS = register("chartreuse_harness", Item::new, new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, Equippable.harness(VADyeColors.CHARTREUSE)), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.YELLOW_HARNESS);

        //endregion

        //region Maroon Dyables

        MAROON_DYE = register("maroon_dye", settings ->  new DyeItem(VADyeColors.MAROON, settings), new Item.Properties(), CreativeModeTabs.INGREDIENTS, Items.BROWN_DYE);
        MAROON_WOOL = registerBlockItem("maroon_wool", VABlocks.MAROON_WOOL, CreativeModeTabs.COLORED_BLOCKS, Blocks.BROWN_WOOL.asItem());
        MAROON_CARPET = registerBlockItem("maroon_carpet", VABlocks.MAROON_CARPET, CreativeModeTabs.COLORED_BLOCKS, Blocks.BROWN_CARPET.asItem());
        MAROON_TERRACOTTA = registerBlockItem("maroon_terracotta", VABlocks.MAROON_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Blocks.BROWN_TERRACOTTA.asItem());
        MAROON_GLAZED_TERRACOTTA = registerBlockItem("maroon_glazed_terracotta", VABlocks.MAROON_GLAZED_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Items.BROWN_GLAZED_TERRACOTTA);
        MAROON_CONCRETE = registerBlockItem("maroon_concrete", VABlocks.MAROON_CONCRETE, CreativeModeTabs.COLORED_BLOCKS, Blocks.BROWN_CONCRETE.asItem());
        MAROON_CONCRETE_POWDER = registerBlockItem("maroon_concrete_powder", VABlocks.MAROON_CONCRETE_POWDER, CreativeModeTabs.COLORED_BLOCKS, Blocks.BROWN_CONCRETE_POWDER.asItem());
        MAROON_STAINED_GLASS = registerBlockItem("maroon_stained_glass", VABlocks.MAROON_STAINED_GLASS, CreativeModeTabs.COLORED_BLOCKS, Blocks.BROWN_STAINED_GLASS.asItem());
        MAROON_STAINED_GLASS_PANE = registerBlockItem("maroon_stained_glass_pane", VABlocks.MAROON_STAINED_GLASS_PANE, CreativeModeTabs.COLORED_BLOCKS, Blocks.BROWN_STAINED_GLASS_PANE.asItem());
        MAROON_CANDLE = registerBlockItem("maroon_candle", VABlocks.MAROON_CANDLE, at(CreativeModeTabs.COLORED_BLOCKS, Blocks.BROWN_CANDLE.asItem()), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Blocks.BROWN_CANDLE.asItem()));
        MAROON_SILKBULB = registerBlockItem("maroon_silkbulb", VABlocks.MAROON_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, VAItems.BROWN_SILKBULB);
        MAROON_BED = register("maroon_bed", settings -> new BedItem(VABlocks.MAROON_BED, settings), new Item.Properties().overrideDescription(VABlocks.MAROON_BED.getDescriptionId()).stacksTo(1), at(CreativeModeTabs.COLORED_BLOCKS, Items.BROWN_BED), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BROWN_BED));
        MAROON_SHULKER_BOX = register("maroon_shulker_box", settings ->  new BlockItem(VABlocks.MAROON_SHULKER_BOX, settings), new Item.Properties().overrideDescription(VABlocks.MAROON_SHULKER_BOX.getDescriptionId()).stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.BROWN_SHULKER_BOX), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BROWN_SHULKER_BOX));
        MAROON_BANNER = register("maroon_banner", settings ->  new BannerItem(VABlocks.MAROON_BANNER, VABlocks.MAROON_WALL_BANNER, settings), new Item.Properties().overrideDescription(VABlocks.MAROON_BANNER.getDescriptionId()).stacksTo(16).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.BROWN_BANNER), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BROWN_BANNER));
        MAROON_BUNDLE = register("maroon_bundle", BundleItem::new, new Item.Properties().stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.BROWN_BUNDLE);
        MAROON_HARNESS = register("maroon_harness", Item::new, new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, Equippable.harness(VADyeColors.MAROON)), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.BROWN_HARNESS);

        //endregion

        //region Indigo Dyables

        INDIGO_DYE = register("indigo_dye", settings ->  new DyeItem(VADyeColors.INDIGO, settings), new Item.Properties(), CreativeModeTabs.INGREDIENTS, Items.BLUE_DYE);
        INDIGO_WOOL = registerBlockItem("indigo_wool", VABlocks.INDIGO_WOOL, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLUE_WOOL.asItem());
        INDIGO_CARPET = registerBlockItem("indigo_carpet", VABlocks.INDIGO_CARPET, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLUE_CARPET.asItem());
        INDIGO_TERRACOTTA = registerBlockItem("indigo_terracotta", VABlocks.INDIGO_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLUE_TERRACOTTA.asItem());
        INDIGO_GLAZED_TERRACOTTA = registerBlockItem("indigo_glazed_terracotta", VABlocks.INDIGO_GLAZED_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Items.BLUE_GLAZED_TERRACOTTA);
        INDIGO_CONCRETE = registerBlockItem("indigo_concrete", VABlocks.INDIGO_CONCRETE, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLUE_CONCRETE.asItem());
        INDIGO_CONCRETE_POWDER = registerBlockItem("indigo_concrete_powder", VABlocks.INDIGO_CONCRETE_POWDER, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLUE_CONCRETE_POWDER.asItem());
        INDIGO_STAINED_GLASS = registerBlockItem("indigo_stained_glass", VABlocks.INDIGO_STAINED_GLASS, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLUE_STAINED_GLASS.asItem());
        INDIGO_STAINED_GLASS_PANE = registerBlockItem("indigo_stained_glass_pane", VABlocks.INDIGO_STAINED_GLASS_PANE, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLUE_STAINED_GLASS_PANE.asItem());
        INDIGO_CANDLE = registerBlockItem("indigo_candle", VABlocks.INDIGO_CANDLE, at(CreativeModeTabs.COLORED_BLOCKS, Blocks.BLUE_CANDLE.asItem()), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Blocks.BLUE_CANDLE.asItem()));
        INDIGO_SILKBULB = registerBlockItem("indigo_silkbulb", VABlocks.INDIGO_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, VAItems.BLUE_SILKBULB);
        INDIGO_BED = register("indigo_bed", settings -> new BedItem(VABlocks.INDIGO_BED, settings), new Item.Properties().overrideDescription(VABlocks.INDIGO_BED.getDescriptionId()).stacksTo(1), at(CreativeModeTabs.COLORED_BLOCKS, Items.BLUE_BED), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BLUE_BED));
        INDIGO_SHULKER_BOX = register("indigo_shulker_box", settings ->  new BlockItem(VABlocks.INDIGO_SHULKER_BOX, settings), new Item.Properties().overrideDescription(VABlocks.INDIGO_SHULKER_BOX.getDescriptionId()).stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.BLUE_SHULKER_BOX), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BLUE_SHULKER_BOX));
        INDIGO_BANNER = register("indigo_banner", settings ->  new BannerItem(VABlocks.INDIGO_BANNER, VABlocks.INDIGO_WALL_BANNER, settings), new Item.Properties().overrideDescription(VABlocks.INDIGO_BANNER.getDescriptionId()).stacksTo(16).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.BLUE_BANNER), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BLUE_BANNER));
        INDIGO_BUNDLE = register("indigo_bundle", BundleItem::new, new Item.Properties().stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.BLUE_BUNDLE);
        INDIGO_HARNESS = register("indigo_harness", Item::new, new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, Equippable.harness(VADyeColors.INDIGO)), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.BLUE_HARNESS);

        //endregion

        //region Plum Dyables

        PLUM_DYE = register("plum_dye", settings ->  new DyeItem(VADyeColors.PLUM, settings), new Item.Properties(), CreativeModeTabs.INGREDIENTS, Items.MAGENTA_DYE);
        PLUM_WOOL = registerBlockItem("plum_wool", VABlocks.PLUM_WOOL, CreativeModeTabs.COLORED_BLOCKS, Blocks.MAGENTA_WOOL.asItem());
        PLUM_CARPET = registerBlockItem("plum_carpet", VABlocks.PLUM_CARPET, CreativeModeTabs.COLORED_BLOCKS, Blocks.MAGENTA_CARPET.asItem());
        PLUM_TERRACOTTA = registerBlockItem("plum_terracotta", VABlocks.PLUM_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Blocks.MAGENTA_TERRACOTTA.asItem());
        PLUM_GLAZED_TERRACOTTA = registerBlockItem("plum_glazed_terracotta", VABlocks.PLUM_GLAZED_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Items.MAGENTA_GLAZED_TERRACOTTA);
        PLUM_CONCRETE = registerBlockItem("plum_concrete", VABlocks.PLUM_CONCRETE, CreativeModeTabs.COLORED_BLOCKS, Blocks.MAGENTA_CONCRETE.asItem());
        PLUM_CONCRETE_POWDER = registerBlockItem("plum_concrete_powder", VABlocks.PLUM_CONCRETE_POWDER, CreativeModeTabs.COLORED_BLOCKS, Blocks.MAGENTA_CONCRETE_POWDER.asItem());
        PLUM_STAINED_GLASS = registerBlockItem("plum_stained_glass", VABlocks.PLUM_STAINED_GLASS, CreativeModeTabs.COLORED_BLOCKS, Blocks.MAGENTA_STAINED_GLASS.asItem());
        PLUM_STAINED_GLASS_PANE = registerBlockItem("plum_stained_glass_pane", VABlocks.PLUM_STAINED_GLASS_PANE, CreativeModeTabs.COLORED_BLOCKS, Blocks.MAGENTA_STAINED_GLASS_PANE.asItem());
        PLUM_CANDLE = registerBlockItem("plum_candle", VABlocks.PLUM_CANDLE, at(CreativeModeTabs.COLORED_BLOCKS, Blocks.MAGENTA_CANDLE.asItem()), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Blocks.MAGENTA_CANDLE.asItem()));
        PLUM_SILKBULB = registerBlockItem("plum_silkbulb", VABlocks.PLUM_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, VAItems.MAGENTA_SILKBULB);
        PLUM_BED = register("plum_bed", settings -> new BedItem(VABlocks.PLUM_BED, settings), new Item.Properties().overrideDescription(VABlocks.PLUM_BED.getDescriptionId()).stacksTo(1), at(CreativeModeTabs.COLORED_BLOCKS, Items.MAGENTA_BED), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.MAGENTA_BED));
        PLUM_SHULKER_BOX = register("plum_shulker_box", settings ->  new BlockItem(VABlocks.PLUM_SHULKER_BOX, settings), new Item.Properties().overrideDescription(VABlocks.PLUM_SHULKER_BOX.getDescriptionId()).stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.MAGENTA_SHULKER_BOX), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.MAGENTA_SHULKER_BOX));
        PLUM_BANNER = register("plum_banner", settings ->  new BannerItem(VABlocks.PLUM_BANNER, VABlocks.PLUM_WALL_BANNER, settings), new Item.Properties().overrideDescription(VABlocks.PLUM_BANNER.getDescriptionId()).stacksTo(16).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.MAGENTA_BANNER), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.MAGENTA_BANNER));
        PLUM_BUNDLE = register("plum_bundle", BundleItem::new, new Item.Properties().stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.MAGENTA_BUNDLE);
        PLUM_HARNESS = register("plum_harness", Item::new, new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, Equippable.harness(VADyeColors.PLUM)), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.MAGENTA_HARNESS);

        //endregion

        //region Viridian Dyables

        VIRIDIAN_DYE = register("viridian_dye", settings ->  new DyeItem(VADyeColors.VIRIDIAN, settings), new Item.Properties(), CreativeModeTabs.INGREDIENTS, Items.GREEN_DYE);
        VIRIDIAN_WOOL = registerBlockItem("viridian_wool", VABlocks.VIRIDIAN_WOOL, CreativeModeTabs.COLORED_BLOCKS, Blocks.GREEN_WOOL.asItem());
        VIRIDIAN_CARPET = registerBlockItem("viridian_carpet", VABlocks.VIRIDIAN_CARPET, CreativeModeTabs.COLORED_BLOCKS, Blocks.GREEN_CARPET.asItem());
        VIRIDIAN_TERRACOTTA = registerBlockItem("viridian_terracotta", VABlocks.VIRIDIAN_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Blocks.GREEN_TERRACOTTA.asItem());
        VIRIDIAN_GLAZED_TERRACOTTA = registerBlockItem("viridian_glazed_terracotta", VABlocks.VIRIDIAN_GLAZED_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Items.GREEN_GLAZED_TERRACOTTA);
        VIRIDIAN_CONCRETE = registerBlockItem("viridian_concrete", VABlocks.VIRIDIAN_CONCRETE, CreativeModeTabs.COLORED_BLOCKS, Blocks.GREEN_CONCRETE.asItem());
        VIRIDIAN_CONCRETE_POWDER = registerBlockItem("viridian_concrete_powder", VABlocks.VIRIDIAN_CONCRETE_POWDER, CreativeModeTabs.COLORED_BLOCKS, Blocks.GREEN_CONCRETE_POWDER.asItem());
        VIRIDIAN_STAINED_GLASS = registerBlockItem("viridian_stained_glass", VABlocks.VIRIDIAN_STAINED_GLASS, CreativeModeTabs.COLORED_BLOCKS, Blocks.GREEN_STAINED_GLASS.asItem());
        VIRIDIAN_STAINED_GLASS_PANE = registerBlockItem("viridian_stained_glass_pane", VABlocks.VIRIDIAN_STAINED_GLASS_PANE, CreativeModeTabs.COLORED_BLOCKS, Blocks.GREEN_STAINED_GLASS_PANE.asItem());
        VIRIDIAN_CANDLE = registerBlockItem("viridian_candle", VABlocks.VIRIDIAN_CANDLE, at(CreativeModeTabs.COLORED_BLOCKS, Blocks.GREEN_CANDLE.asItem()), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Blocks.GREEN_CANDLE.asItem()));
        VIRIDIAN_SILKBULB = registerBlockItem("viridian_silkbulb", VABlocks.VIRIDIAN_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, VAItems.GREEN_SILKBULB);
        VIRIDIAN_BED = register("viridian_bed", settings -> new BedItem(VABlocks.VIRIDIAN_BED, settings), new Item.Properties().overrideDescription(VABlocks.VIRIDIAN_BED.getDescriptionId()).stacksTo(1), at(CreativeModeTabs.COLORED_BLOCKS, Items.GREEN_BED), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.GREEN_BED));
        VIRIDIAN_SHULKER_BOX = register("viridian_shulker_box", settings ->  new BlockItem(VABlocks.VIRIDIAN_SHULKER_BOX, settings), new Item.Properties().overrideDescription(VABlocks.VIRIDIAN_SHULKER_BOX.getDescriptionId()).stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.GREEN_SHULKER_BOX), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.GREEN_SHULKER_BOX));
        VIRIDIAN_BANNER = register("viridian_banner", settings ->  new BannerItem(VABlocks.VIRIDIAN_BANNER, VABlocks.VIRIDIAN_WALL_BANNER, settings), new Item.Properties().overrideDescription(VABlocks.VIRIDIAN_BANNER.getDescriptionId()).stacksTo(16).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.GREEN_BANNER), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.GREEN_BANNER));
        VIRIDIAN_BUNDLE = register("viridian_bundle", BundleItem::new, new Item.Properties().stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.GREEN_BUNDLE);
        VIRIDIAN_HARNESS = register("viridian_harness", Item::new, new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, Equippable.harness(VADyeColors.VIRIDIAN)), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.GREEN_HARNESS);

        //endregion

        //region Tan Dyables

        TAN_DYE = register("tan_dye", settings ->  new DyeItem(VADyeColors.TAN, settings), new Item.Properties(), CreativeModeTabs.INGREDIENTS, Items.BLACK_DYE);
        TAN_WOOL = registerBlockItem("tan_wool", VABlocks.TAN_WOOL, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLACK_WOOL.asItem());
        TAN_CARPET = registerBlockItem("tan_carpet", VABlocks.TAN_CARPET, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLACK_CARPET.asItem());
        TAN_TERRACOTTA = registerBlockItem("tan_terracotta", VABlocks.TAN_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLACK_TERRACOTTA.asItem());
        TAN_GLAZED_TERRACOTTA = registerBlockItem("tan_glazed_terracotta", VABlocks.TAN_GLAZED_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Items.BLACK_GLAZED_TERRACOTTA);
        TAN_CONCRETE = registerBlockItem("tan_concrete", VABlocks.TAN_CONCRETE, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLACK_CONCRETE.asItem());
        TAN_CONCRETE_POWDER = registerBlockItem("tan_concrete_powder", VABlocks.TAN_CONCRETE_POWDER, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLACK_CONCRETE_POWDER.asItem());
        TAN_STAINED_GLASS = registerBlockItem("tan_stained_glass", VABlocks.TAN_STAINED_GLASS, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLACK_STAINED_GLASS.asItem());
        TAN_STAINED_GLASS_PANE = registerBlockItem("tan_stained_glass_pane", VABlocks.TAN_STAINED_GLASS_PANE, CreativeModeTabs.COLORED_BLOCKS, Blocks.BLACK_STAINED_GLASS_PANE.asItem());
        TAN_CANDLE = registerBlockItem("tan_candle", VABlocks.TAN_CANDLE, at(CreativeModeTabs.COLORED_BLOCKS, Blocks.BLACK_CANDLE.asItem()), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Blocks.BLACK_CANDLE.asItem()));
        TAN_SILKBULB = registerBlockItem("tan_silkbulb", VABlocks.TAN_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, VAItems.BLACK_SILKBULB);
        TAN_BED = register("tan_bed", settings -> new BedItem(VABlocks.TAN_BED, settings), new Item.Properties().overrideDescription(VABlocks.TAN_BED.getDescriptionId()).stacksTo(1), at(CreativeModeTabs.COLORED_BLOCKS, Items.BLACK_BED), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BLACK_BED));
        TAN_SHULKER_BOX = register("tan_shulker_box", settings ->  new BlockItem(VABlocks.TAN_SHULKER_BOX, settings), new Item.Properties().overrideDescription(VABlocks.TAN_SHULKER_BOX.getDescriptionId()).stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.BLACK_SHULKER_BOX), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BLACK_SHULKER_BOX));
        TAN_BANNER = register("tan_banner", settings ->  new BannerItem(VABlocks.TAN_BANNER, VABlocks.TAN_WALL_BANNER, settings), new Item.Properties().overrideDescription(VABlocks.TAN_BANNER.getDescriptionId()).stacksTo(16).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.BLACK_BANNER), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BLACK_BANNER));
        TAN_BUNDLE = register("tan_bundle", BundleItem::new, new Item.Properties().stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.BLACK_BUNDLE);
        TAN_HARNESS = register("tan_harness", Item::new, new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, Equippable.harness(VADyeColors.TAN)), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.BLACK_HARNESS);

        //endregion

        //region Sinopia Dyables

        SINOPIA_DYE = register("sinopia_dye", settings ->  new DyeItem(VADyeColors.SINOPIA, settings), new Item.Properties(), CreativeModeTabs.INGREDIENTS, Items.RED_DYE);
        SINOPIA_WOOL = registerBlockItem("sinopia_wool", VABlocks.SINOPIA_WOOL, CreativeModeTabs.COLORED_BLOCKS, Blocks.RED_WOOL.asItem());
        SINOPIA_CARPET = registerBlockItem("sinopia_carpet", VABlocks.SINOPIA_CARPET, CreativeModeTabs.COLORED_BLOCKS, Blocks.RED_CARPET.asItem());
        SINOPIA_TERRACOTTA = registerBlockItem("sinopia_terracotta", VABlocks.SINOPIA_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Blocks.RED_TERRACOTTA.asItem());
        SINOPIA_GLAZED_TERRACOTTA = registerBlockItem("sinopia_glazed_terracotta", VABlocks.SINOPIA_GLAZED_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Items.RED_GLAZED_TERRACOTTA);
        SINOPIA_CONCRETE = registerBlockItem("sinopia_concrete", VABlocks.SINOPIA_CONCRETE, CreativeModeTabs.COLORED_BLOCKS, Blocks.RED_CONCRETE.asItem());
        SINOPIA_CONCRETE_POWDER = registerBlockItem("sinopia_concrete_powder", VABlocks.SINOPIA_CONCRETE_POWDER, CreativeModeTabs.COLORED_BLOCKS, Blocks.RED_CONCRETE_POWDER.asItem());
        SINOPIA_STAINED_GLASS = registerBlockItem("sinopia_stained_glass", VABlocks.SINOPIA_STAINED_GLASS, CreativeModeTabs.COLORED_BLOCKS, Blocks.RED_STAINED_GLASS.asItem());
        SINOPIA_STAINED_GLASS_PANE = registerBlockItem("sinopia_stained_glass_pane", VABlocks.SINOPIA_STAINED_GLASS_PANE, CreativeModeTabs.COLORED_BLOCKS, Blocks.RED_STAINED_GLASS_PANE.asItem());
        SINOPIA_CANDLE = registerBlockItem("sinopia_candle", VABlocks.SINOPIA_CANDLE, at(CreativeModeTabs.COLORED_BLOCKS, Blocks.RED_CANDLE.asItem()), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Blocks.RED_CANDLE.asItem()));
        SINOPIA_SILKBULB = registerBlockItem("sinopia_silkbulb", VABlocks.SINOPIA_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, VAItems.RED_SILKBULB);
        SINOPIA_BED = register("sinopia_bed", settings -> new BedItem(VABlocks.SINOPIA_BED, settings), new Item.Properties().overrideDescription(VABlocks.SINOPIA_BED.getDescriptionId()).stacksTo(1), at(CreativeModeTabs.COLORED_BLOCKS, Items.RED_BED), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.RED_BED));
        SINOPIA_SHULKER_BOX = register("sinopia_shulker_box", settings ->  new BlockItem(VABlocks.SINOPIA_SHULKER_BOX, settings), new Item.Properties().overrideDescription(VABlocks.SINOPIA_SHULKER_BOX.getDescriptionId()).stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.RED_SHULKER_BOX), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.RED_SHULKER_BOX));
        SINOPIA_BANNER = register("sinopia_banner", settings ->  new BannerItem(VABlocks.SINOPIA_BANNER, VABlocks.SINOPIA_WALL_BANNER, settings), new Item.Properties().overrideDescription(VABlocks.SINOPIA_BANNER.getDescriptionId()).stacksTo(16).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.RED_BANNER), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.RED_BANNER));
        SINOPIA_BUNDLE = register("sinopia_bundle", BundleItem::new, new Item.Properties().stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.RED_BUNDLE);
        SINOPIA_HARNESS = register("sinopia_harness", Item::new, new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, Equippable.harness(VADyeColors.SINOPIA)), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.RED_HARNESS);

        //endregion

        //region Lilac Dyables

        LILAC_DYE = register("lilac_dye", settings ->  new DyeItem(VADyeColors.LILAC, settings), new Item.Properties(), CreativeModeTabs.INGREDIENTS, Items.PINK_DYE);
        LILAC_WOOL = registerBlockItem("lilac_wool", VABlocks.LILAC_WOOL, CreativeModeTabs.COLORED_BLOCKS, Blocks.PINK_WOOL.asItem());
        LILAC_CARPET = registerBlockItem("lilac_carpet", VABlocks.LILAC_CARPET, CreativeModeTabs.COLORED_BLOCKS, Blocks.PINK_CARPET.asItem());
        LILAC_TERRACOTTA = registerBlockItem("lilac_terracotta", VABlocks.LILAC_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Blocks.PINK_TERRACOTTA.asItem());
        LILAC_GLAZED_TERRACOTTA = registerBlockItem("lilac_glazed_terracotta", VABlocks.LILAC_GLAZED_TERRACOTTA, CreativeModeTabs.COLORED_BLOCKS, Items.PINK_GLAZED_TERRACOTTA);
        LILAC_CONCRETE = registerBlockItem("lilac_concrete", VABlocks.LILAC_CONCRETE, CreativeModeTabs.COLORED_BLOCKS, Blocks.PINK_CONCRETE.asItem());
        LILAC_CONCRETE_POWDER = registerBlockItem("lilac_concrete_powder", VABlocks.LILAC_CONCRETE_POWDER, CreativeModeTabs.COLORED_BLOCKS, Blocks.PINK_CONCRETE_POWDER.asItem());
        LILAC_STAINED_GLASS = registerBlockItem("lilac_stained_glass", VABlocks.LILAC_STAINED_GLASS, CreativeModeTabs.COLORED_BLOCKS, Blocks.PINK_STAINED_GLASS.asItem());
        LILAC_STAINED_GLASS_PANE = registerBlockItem("lilac_stained_glass_pane", VABlocks.LILAC_STAINED_GLASS_PANE, CreativeModeTabs.COLORED_BLOCKS, Blocks.PINK_STAINED_GLASS_PANE.asItem());
        LILAC_CANDLE = registerBlockItem("lilac_candle", VABlocks.LILAC_CANDLE, at(CreativeModeTabs.COLORED_BLOCKS, Blocks.PINK_CANDLE.asItem()), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Blocks.PINK_CANDLE.asItem()));
        LILAC_SILKBULB = registerBlockItem("lilac_silkbulb", VABlocks.LILAC_SILKBULB, CreativeModeTabs.COLORED_BLOCKS, VAItems.PINK_SILKBULB);
        LILAC_BED = register("lilac_bed", settings -> new BedItem(VABlocks.LILAC_BED, settings), new Item.Properties().overrideDescription(VABlocks.LILAC_BED.getDescriptionId()).stacksTo(1), at(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_BED), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.PINK_BED));
        LILAC_SHULKER_BOX = register("lilac_shulker_box", settings ->  new BlockItem(VABlocks.LILAC_SHULKER_BOX, settings), new Item.Properties().overrideDescription(VABlocks.LILAC_SHULKER_BOX.getDescriptionId()).stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_SHULKER_BOX), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.PINK_SHULKER_BOX));
        LILAC_BANNER = register("lilac_banner", settings ->  new BannerItem(VABlocks.LILAC_BANNER, VABlocks.LILAC_WALL_BANNER, settings), new Item.Properties().overrideDescription(VABlocks.LILAC_BANNER.getDescriptionId()).stacksTo(16).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY), at(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_BANNER), at(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.PINK_BANNER));
        LILAC_BUNDLE = register("lilac_bundle", BundleItem::new, new Item.Properties().stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.PINK_BUNDLE);
        LILAC_HARNESS = register("lilac_harness", Item::new, new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, Equippable.harness(VADyeColors.LILAC)), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.PINK_HARNESS);

        //endregion

        COLORING_STATION = registerBlockItem("coloring_station", VABlocks.COLORING_STATION, CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.LOOM);

        //endregion

        //region Iolite

        IOLITE = register("iolite", new Item.Properties().trimMaterial(VAArmorTrimMaterials.IOLITE), CreativeModeTabs.INGREDIENTS, Items.ANCIENT_DEBRIS);
        IOLITE_ORE = registerBlockItem("iolite_ore", VABlocks.IOLITE_ORE, CreativeModeTabs.NATURAL_BLOCKS, Items.ANCIENT_DEBRIS);
        IOLITE_BLOCK = registerBlockItem("iolite_block", VABlocks.IOLITE_BLOCK, CreativeModeTabs.BUILDING_BLOCKS, Items.NETHERITE_BLOCK);
        PORTAL_CORE = register("portal_core", PortalCoreItem::new, new Item.Properties().stacksTo(16), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.TNT_MINECART);
        DRAINED_PORTAL_CORE = register("drained_portal_core", new Item.Properties().stacksTo(16), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);
        ENTANGLEMENT_DRIVE = register("entanglement_drive", settings ->  new BlockItem(VABlocks.ENTANGLEMENT_DRIVE, settings), new Item.Properties().rarity(Rarity.RARE).overrideDescription(VABlocks.ENTANGLEMENT_DRIVE.getDescriptionId()), CreativeModeTabs.REDSTONE_BLOCKS, Items.DECORATED_POT);
        REMOTE_NOTIFIER = register("remote_notifier", settings ->  new BlockItem(VABlocks.REMOTE_NOTIFIER, settings), new Item.Properties().overrideDescription(VABlocks.REMOTE_NOTIFIER.getDescriptionId()).requiredFeatures(VirtualAdditions.PREVIEW), CreativeModeTabs.REDSTONE_BLOCKS, prev);

        //endregion

        //region Base Tool Sets

        COPPER_TOOL_SET = new ToolSet(Items.COPPER_SWORD, Items.COPPER_SHOVEL, Items.COPPER_PICKAXE, Items.COPPER_AXE, Items.COPPER_HOE, VAItems.COPPER_HALBERD, Items.COPPER_SPEAR, ToolMaterial.COPPER, "copper");
        DIAMOND_TOOL_SET = new ToolSet(Items.DIAMOND_SWORD, Items.DIAMOND_SHOVEL, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_HOE, VAItems.DIAMOND_HALBERD, Items.DIAMOND_SPEAR, ToolMaterial.DIAMOND, "diamond");
        GOLDEN_TOOL_SET = new ToolSet(Items.GOLDEN_SWORD, Items.GOLDEN_SHOVEL, Items.GOLDEN_PICKAXE, Items.GOLDEN_AXE, Items.GOLDEN_HOE, VAItems.GOLDEN_HALBERD, Items.GOLDEN_SPEAR, ToolMaterial.GOLD, "golden");
        IRON_TOOL_SET = new ToolSet(Items.IRON_SWORD, Items.IRON_SHOVEL, Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_HOE, VAItems.IRON_HALBERD, Items.IRON_SPEAR, ToolMaterial.IRON, "iron");
        NETHERITE_TOOL_SET = new ToolSet(Items.NETHERITE_SWORD, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_HOE, VAItems.NETHERITE_HALBERD, Items.NETHERITE_SPEAR, ToolMaterial.NETHERITE, "netherite");
        STEEL_TOOL_SET = new ToolSet(STEEL_SWORD, STEEL_SHOVEL, STEEL_PICKAXE, STEEL_AXE, STEEL_HOE, VAItems.STEEL_HALBERD, VAItems.STEEL_SPEAR, SteelToolMaterial.INSTANCE, "steel");

        //endregion

        //region Misc

        //region Climbing Ropes

        CLIMBING_ROPE = register("climbing_rope", settings ->  new ClimbingRopeItem(VABlocks.CLIMBING_ROPE_ANCHOR, settings), new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.LEAD);
        EXPOSED_CLIMBING_ROPE = register("exposed_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR, settings), new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);
        WEATHERED_CLIMBING_ROPE = register("weathered_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR, settings), new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);
        OXIDIZED_CLIMBING_ROPE = register("oxidized_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR, settings), new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);
        WAXED_CLIMBING_ROPE = register("waxed_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.WAXED_CLIMBING_ROPE_ANCHOR, settings), new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);
        WAXED_EXPOSED_CLIMBING_ROPE = register("waxed_exposed_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR, settings), new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);
        WAXED_WEATHERED_CLIMBING_ROPE = register("waxed_weathered_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR, settings), new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);
        WAXED_OXIDIZED_CLIMBING_ROPE = register("waxed_oxidized_climbing_rope", settings -> new ClimbingRopeItem(VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR, settings), new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.TOOLS_AND_UTILITIES, prev);

        //endregion

        //region Hedges

        OAK_HEDGE = registerBlockItem("oak_hedge", VABlocks.OAK_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, Items.OAK_LEAVES);
        SPRUCE_HEDGE = registerBlockItem("spruce_hedge", VABlocks.SPRUCE_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, Items.SPRUCE_LEAVES);
        BIRCH_HEDGE = registerBlockItem("birch_hedge", VABlocks.BIRCH_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, Items.BIRCH_LEAVES);
        JUNGLE_HEDGE = registerBlockItem("jungle_hedge", VABlocks.JUNGLE_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, Items.JUNGLE_LEAVES);
        ACACIA_HEDGE = registerBlockItem("acacia_hedge", VABlocks.ACACIA_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, Items.ACACIA_LEAVES);
        DARK_OAK_HEDGE = registerBlockItem("dark_oak_hedge", VABlocks.DARK_OAK_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, Items.DARK_OAK_LEAVES);
        PALE_OAK_HEDGE = registerBlockItem("pale_oak_hedge", VABlocks.PALE_OAK_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, Items.PALE_OAK_LEAVES);
        MANGROVE_HEDGE = registerBlockItem("mangrove_hedge", VABlocks.MANGROVE_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, Items.MANGROVE_LEAVES);
        ZEBRANO_HEDGE = registerBlockItem("zebrano_hedge", VABlocks.ZEBRANO_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, VAItems.ZEBRANO_LEAVES);
        AZALEA_HEDGE = registerBlockItem("azalea_hedge", VABlocks.AZALEA_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, Items.AZALEA_LEAVES);
        FLOWERING_AZALEA_HEDGE = registerBlockItem("flowering_azalea_hedge", VABlocks.FLOWERING_AZALEA_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, Items.FLOWERING_AZALEA_LEAVES);
        CHERRY_HEDGE = registerBlockItem("cherry_hedge", VABlocks.CHERRY_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, Items.CHERRY_LEAVES);
        SOULBLOOM_HEDGE = registerBlockItem("soulbloom_hedge", VABlocks.SOULBLOOM_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, VAItems.SOULBLOOM_LEAVES);
        WITHERED_HEDGE = registerBlockItem("withered_hedge", VABlocks.WITHERED_HEDGE, CreativeModeTabs.NATURAL_BLOCKS, VAItems.WITHERED_LEAVES);

        //endregion

        //region Smithing Templates

        TOOL_GILD_SMITHING_TEMPLATE = register("tool_gild_smithing_template", settings -> new SmithingTemplateItem(TOOL_GILD_APPLIES_TO_TEXT, TOOL_GILD_INGREDIENTS_TEXT, TOOL_GILD_BASE_SLOT_DESCRIPTION_TEXT, TOOL_GILD_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                List.of(EMPTY_SLOT_SWORD_TEXTURE, EMPTY_SLOT_SHOVEL_TEXTURE, EMPTY_SLOT_PICKAXE_TEXTURE, EMPTY_SLOT_AXE_TEXTURE, EMPTY_SLOT_HOE_TEXTURE),
                List.of(EMPTY_SLOT_AMETHYST_TEXTURE, EMPTY_SLOT_INGOT_TEXTURE, EMPTY_SLOT_EMERALD_TEXTURE, VATextureIdentifiers.EMPTY_SLOT_IOLITE_TEXTURE, EMPTY_SLOT_QUARTZ_TEXTURE, EMPTY_SLOT_ECHO_SHARD_TEXTURE),
                settings
        ),new Item.Properties(), CreativeModeTabs.INGREDIENTS, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE);

        EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE = register("exoskeleton_armor_trim_smithing_template", SmithingTemplateItem::createArmorTrimTemplate, new Item.Properties().rarity(Rarity.UNCOMMON), CreativeModeTabs.INGREDIENTS, Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE);
        ROBE_ARMOR_TRIM_SMITHING_TEMPLATE = register("robe_armor_trim_smithing_template", SmithingTemplateItem::createArmorTrimTemplate, new Item.Properties().rarity(Rarity.RARE), CreativeModeTabs.INGREDIENTS, prev);

        //endregion

        //region Food & Crops

        CORN_SEEDS = register("corn_seeds", settings -> new BlockItem(VABlocks.CORN_CROP, settings),new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.NATURAL_BLOCKS, Items.BEETROOT_SEEDS);
        CORN = register("corn", new Item.Properties().food(VAFoodComponents.CORN), CreativeModeTabs.FOOD_AND_DRINKS, Items.BEETROOT);
        ROASTED_CORN = register("roasted_corn", new Item.Properties().food(VAFoodComponents.ROASTED_CORN), CreativeModeTabs.FOOD_AND_DRINKS, prev);
        TORTILLA = register("tortilla", new Item.Properties().food(VAFoodComponents.TORTILLA), CreativeModeTabs.FOOD_AND_DRINKS, Items.BREAD);
        TOMATO_SEEDS = register("tomato_seeds", settings -> new BlockItem(VABlocks.TOMATO, settings),new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.NATURAL_BLOCKS, CORN_SEEDS);
        TOMATO = register("tomato", TomatoItem::new, new Item.Properties().food(VAFoodComponents.TOMATO), CreativeModeTabs.FOOD_AND_DRINKS, Items.BEETROOT);
        TOMATO_SOUP = register("tomato_soup", new Item.Properties().food(VAFoodComponents.TOMATO_SOUP).stacksTo(1).usingConvertsTo(Items.BOWL), CreativeModeTabs.FOOD_AND_DRINKS, Items.BEETROOT_SOUP);
        CABBAGE_SEEDS = register("cabbage_seeds", settings -> new BlockItem(VABlocks.CABBAGE, settings),new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.NATURAL_BLOCKS, CORN_SEEDS);
        CABBAGE = register("cabbage", new Item.Properties().food(VAFoodComponents.CABBAGE),CreativeModeTabs.FOOD_AND_DRINKS, Items.BEETROOT);
        WISDOM_BERRY_SEEDS = register("wisdom_berry_seeds", settings -> new BlockItem(VABlocks.WISDOM_BERRY, settings),new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.NATURAL_BLOCKS, Items.PITCHER_POD);
        WISDOM_BERRY = register("wisdom_berry", new Item.Properties().food(VAFoodComponents.WISDOM_BERRY), CreativeModeTabs.FOOD_AND_DRINKS, CABBAGE);
        COTTON_SEEDS = register("cotton_seeds", settings -> new BlockItem(VABlocks.COTTON, settings),new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.NATURAL_BLOCKS, Items.BEETROOT_SEEDS);
        COTTON = register("cotton", CreativeModeTabs.INGREDIENTS, Items.WHEAT);
        SOY_BEANS = register("soy_beans", settings -> new BlockItem(VABlocks.SOY_CROP, settings),new Item.Properties().useItemDescriptionPrefix(), CreativeModeTabs.NATURAL_BLOCKS, COTTON_SEEDS);
        SOY_OIL_BUCKET = register("soy_oil_bucket", new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.MILK_BUCKET);

        SALAD = register("salad", new Item.Properties().food(VAFoodComponents.SALAD).stacksTo(1).usingConvertsTo(Items.BOWL), CreativeModeTabs.FOOD_AND_DRINKS, Items.BEETROOT_SOUP);
        TACO = register("taco", new Item.Properties().food(VAFoodComponents.TACO), CreativeModeTabs.FOOD_AND_DRINKS, prev);

        FRIED_EGG = register("fried_egg", new Item.Properties().food(VAFoodComponents.FRIED_EGG), CreativeModeTabs.FOOD_AND_DRINKS, Items.COOKED_CHICKEN);
        ICE_CREAM = register("ice_cream", new Item.Properties().food(VAFoodComponents.ICE_CREAM).stacksTo(1).usingConvertsTo(Items.BOWL), CreativeModeTabs.FOOD_AND_DRINKS, Items.COOKIE);
        CHEESE_WEDGE = register("cheese_wedge", new Item.Properties().food(VAFoodComponents.CHEESE_WEDGE), CreativeModeTabs.FOOD_AND_DRINKS, Items.MILK_BUCKET);
        SWEET_BERRY_PIE = register("sweet_berry_pie", new Item.Properties().food(VAFoodComponents.SWEET_BERRY_PIE), CreativeModeTabs.FOOD_AND_DRINKS, Items.PUMPKIN_PIE);

        BEEF_JERKY = register("beef_jerky", new Item.Properties().food(VAFoodComponents.BEEF_JERKY, VAFoodComponents.JERKY_CONSUMABLE).stacksTo(96), CreativeModeTabs.FOOD_AND_DRINKS, Items.COOKED_BEEF);
        PORK_JERKY = register("pork_jerky", new Item.Properties().food(VAFoodComponents.PORK_JERKY, VAFoodComponents.JERKY_CONSUMABLE).stacksTo(96), CreativeModeTabs.FOOD_AND_DRINKS, Items.COOKED_PORKCHOP);
        CHICKEN_JERKY = register("chicken_jerky", new Item.Properties().food(VAFoodComponents.CHICKEN_JERKY, VAFoodComponents.JERKY_CONSUMABLE).stacksTo(96), CreativeModeTabs.FOOD_AND_DRINKS, Items.COOKED_CHICKEN);
        MUTTON_JERKY = register("mutton_jerky", new Item.Properties().food(VAFoodComponents.MUTTON_JERKY, VAFoodComponents.JERKY_CONSUMABLE).stacksTo(96), CreativeModeTabs.FOOD_AND_DRINKS, Items.COOKED_MUTTON);

        //endregion

        ENGRAVING_CHISEL = register("engraving_chisel", new Item.Properties().stacksTo(1).durability(64), CreativeModeTabs.TOOLS_AND_UTILITIES, Items.NAME_TAG);
        LIGHTNING_BOTTLE = register("lightning_bottle", LightningBottleItem::new, new Item.Properties().component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).rarity(Rarity.RARE).stacksTo(16), CreativeModeTabs.INGREDIENTS, Items.EXPERIENCE_BOTTLE);

        INCENSE = registerBlockItem("incense", VABlocks.INCENSE, CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BELL);

        SLINGSHOT = register("slingshot", SlingshotItem::new, new Item.Properties().stacksTo(1).durability(250), CreativeModeTabs.COMBAT, Items.WIND_CHARGE);

        PURPLE_EGG = register("purple_egg", EggItem::new, new Item.Properties().stacksTo(16).component(DataComponents.CHICKEN_VARIANT, new EitherHolder<>(ResourceKey.create(Registries.CHICKEN_VARIANT, idOf("enchanted")))), at(CreativeModeTabs.COMBAT, Items.BLUE_EGG), at(CreativeModeTabs.INGREDIENTS, Items.BLUE_EGG));

        //endregion

        //region Spawn Eggs

        SALINE_SPAWN_EGG = register("saline_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(VAEntityType.SALINE), CreativeModeTabs.SPAWN_EGGS, Items.RAVAGER_SPAWN_EGG);
        LUMWASP_SPAWN_EGG = register("lumwasp_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(VAEntityType.LUMWASP), CreativeModeTabs.SPAWN_EGGS, Items.LLAMA_SPAWN_EGG);
        SPECTRE_SPAWN_EGG = register("spectre_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(VAEntityType.SPECTRE), CreativeModeTabs.SPAWN_EGGS, Items.SNOW_GOLEM_SPAWN_EGG);

        //endregion
    }

    public static void init(){
        VADispenserBehavior.init();
        VACompostables.init();
        VACauldronBehaviors.init();
        VALootTableModifiers.init();
        SlingshotBehaviors.initDefaultBehaviors();

        FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(VAItems.SOY_OIL_BUCKET, 6400);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register( (content) -> {
            if (CreativeModeTabs.CACHED_PARAMETERS == null) return;
            CreativeModeTabs.CACHED_PARAMETERS.holders().lookup(Registries.POTION).ifPresent((wrapper) -> CreativeModeTabs.generatePotionEffectTypes(content, wrapper, VAItems.APPLICABLE_POTION, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.CACHED_PARAMETERS.enabledFeatures()));
        } );

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(content -> {
            if (CreativeModeTabs.CACHED_PARAMETERS == null) return;
            List<Pair<MobEffectInstance, Integer>> componentData = new ArrayList<>();
            componentData.add(Pair.of(new MobEffectInstance(MobEffects.HASTE, 60, 0, true, true), 3600));
            componentData.add(Pair.of(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0, true, true), 0));
            componentData.add(Pair.of(new MobEffectInstance(MobEffects.NAUSEA, 60, 0, true, true), 3600));
            componentData.add(Pair.of(new MobEffectInstance(MobEffects.NIGHT_VISION, 250, 0, true, true), 3600));
            componentData.add(Pair.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 0, true, true), 3600));
            componentData.add(Pair.of(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, true, true), 3600));
            componentData.add(Pair.of(new MobEffectInstance(MobEffects.REGENERATION, 60, 0, true, true), 3600));
            componentData.add(Pair.of(new MobEffectInstance(MobEffects.JUMP_BOOST, 60, 0, true, true), 3600));
            componentData.add(Pair.of(new MobEffectInstance(MobEffects.POISON, 60, 0, true, true), 3600));
            componentData.add(Pair.of(new MobEffectInstance(MobEffects.WITHER, 60, 0, true, true), 3600));
            componentData.add(Pair.of(new MobEffectInstance(VAStatusEffects.AURA, 60, 0, true, true), 3600));
            componentData.add(Pair.of(new MobEffectInstance(VAStatusEffects.FRAILTY, 60, 0, true, true), 3600));

            componentData.forEach(pair -> {
                ItemStack stack = INCENSE.getDefaultInstance();
                stack.set(VADataComponentTypes.INCENSE_EFFECTS, new IncenseEffectsComponent(
                        Optional.of(new PotionContents(Optional.empty(), Optional.empty(), List.of(pair.getLeft()), Optional.empty())),
                        Optional.of(pair.getRight())
                ));
                content.addBefore(Items.BEACON, stack);

            });
        });
    }

    //region Initializers

    //endregion
}