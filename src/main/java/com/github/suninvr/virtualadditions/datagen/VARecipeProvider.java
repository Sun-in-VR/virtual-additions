package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.datagen.recipe.ArmorColoringRecipeJsonBuilder;
import com.github.suninvr.virtualadditions.datagen.recipe.ColoringRecipeJsonBuilder;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.*;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class VARecipeProvider {

    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> base() {
        return BaseProvider::new;
    }

    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> preview() {
        return PreviewProvider::new;
    }

    private static class BaseProvider extends Provider {

        public BaseProvider(FabricDataOutput fabricDataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> wrapperLookupCompletableFuture) {
            super(fabricDataOutput, wrapperLookupCompletableFuture);
        }

        @Override
        public void generate(RecipeExporter exporter) {

            offerBlasting(exporter, List.of(VAItems.RAW_STEEL), RecipeCategory.MISC, VAItems.STEEL_INGOT, 1.0F, 100, "steel_ingot");
            offerBlasting(exporter, List.of(VAItems.IOLITE_ORE), RecipeCategory.MISC, VAItems.IOLITE, 1.0F, 100, "iolite");
            offerSmelting(exporter, List.of(VAItems.IOLITE_ORE), RecipeCategory.MISC, VAItems.IOLITE, 1.0F, 200, "iolite");

            offerHedgeRecipe(exporter, VABlocks.OAK_HEDGE, Blocks.OAK_LEAVES);
            offerHedgeRecipe(exporter, VABlocks.SPRUCE_HEDGE, Blocks.SPRUCE_LEAVES);
            offerHedgeRecipe(exporter, VABlocks.BIRCH_HEDGE, Blocks.BIRCH_LEAVES);
            offerHedgeRecipe(exporter, VABlocks.JUNGLE_HEDGE, Blocks.JUNGLE_LEAVES);
            offerHedgeRecipe(exporter, VABlocks.ACACIA_HEDGE, Blocks.ACACIA_LEAVES);
            offerHedgeRecipe(exporter, VABlocks.DARK_OAK_HEDGE, Blocks.DARK_OAK_LEAVES);
            offerHedgeRecipe(exporter, VABlocks.MANGROVE_HEDGE, Blocks.MANGROVE_LEAVES);
            offerHedgeRecipe(exporter, VABlocks.CHERRY_HEDGE, Blocks.CHERRY_LEAVES);
            offerHedgeRecipe(exporter, VABlocks.AZALEA_HEDGE, Blocks.AZALEA_LEAVES);
            offerHedgeRecipe(exporter, VABlocks.FLOWERING_AZALEA_HEDGE, Blocks.FLOWERING_AZALEA_LEAVES);

            offerCompactingRecipe(exporter, RecipeCategory.MISC, VABlocks.STEEL_BLOCK, VAItems.STEEL_INGOT, "steel_ingot");
            offerShapelessRecipe(exporter, VAItems.STEEL_INGOT, VABlocks.STEEL_BLOCK, "steel", 9);
            offerCompactingRecipe(exporter, RecipeCategory.MISC, VABlocks.RAW_STEEL_BLOCK, VAItems.RAW_STEEL, "steel_ingot");
            offerShapelessRecipe(exporter, VAItems.RAW_STEEL, VABlocks.RAW_STEEL_BLOCK, "raw_steel", 9);
            offerCompactingRecipe(exporter, RecipeCategory.MISC, VABlocks.IOLITE_BLOCK, VAItems.IOLITE, "iolite");
            offerShapelessRecipe(exporter, VAItems.IOLITE, VABlocks.IOLITE_BLOCK, "iolite", 9);

            offerShapelessRecipe(exporter, RecipeCategory.MISC, VAItems.RAW_STEEL, 1, Pair.of(Items.RAW_IRON, 3), Pair.of(Items.COAL, 1));
            offerShapelessRecipe(exporter, RecipeCategory.MISC, VAItems.TOOL_GILD_SMITHING_TEMPLATE, 1, Pair.of(VAItems.STEEL_INGOT, 1), Pair.of(Items.DIAMOND, 1));
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL_STAIRS, VABlocks.CUT_STEEL);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL_SLAB, VABlocks.CUT_STEEL, 2);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.STEEL_GRATE, VABlocks.CUT_STEEL, 1);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CHISELED_STEEL, VABlocks.CUT_STEEL, 1);
            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, VABlocks.CUT_STEEL_STAIRS, 4).input('#', VABlocks.CUT_STEEL).pattern("#  ").pattern("## ").pattern("###").criterion("cut_steel", conditionsFromItem(VABlocks.CUT_STEEL)).offerTo(exporter);
            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, VABlocks.CUT_STEEL_SLAB, 6).input('#', VABlocks.CUT_STEEL).pattern("###").criterion("cut_steel", conditionsFromItem(VABlocks.CUT_STEEL)).offerTo(exporter);
            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, VABlocks.STEEL_FENCE, 6).input('W', VABlocks.CUT_STEEL).input('#', VAItems.STEEL_INGOT).pattern("W#W").pattern("W#W").criterion("cut_steel", conditionsFromItem(VABlocks.CUT_STEEL)).offerTo(exporter);
            offer2x2FullRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL, VAItems.STEEL_INGOT, 16);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL, VABlocks.STEEL_BLOCK, 36);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.STEEL_GRATE, VABlocks.STEEL_BLOCK, 36);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CHISELED_STEEL, VABlocks.STEEL_BLOCK, 36);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL, VAItems.STEEL_INGOT, 4);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL_STAIRS, VAItems.STEEL_INGOT, 4);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL_SLAB, VAItems.STEEL_INGOT, 8);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.STEEL_GRATE, VAItems.STEEL_INGOT, 4);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CHISELED_STEEL, VAItems.STEEL_INGOT, 4);
            createDoorRecipe(VABlocks.STEEL_DOOR, Ingredient.ofItems(VAItems.STEEL_INGOT)).criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);
            createTrapdoorRecipe(VABlocks.STEEL_TRAPDOOR, Ingredient.ofItems(VAItems.STEEL_INGOT)).criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);
            createChiseledBlockRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.CHISELED_STEEL, Ingredient.ofItems(VABlocks.CUT_STEEL_SLAB));

            offerSmelting(exporter, List.of(VABlocks.COBBLED_HORNFELS), RecipeCategory.BUILDING_BLOCKS, VABlocks.HORNFELS, 0.1F, 200, "hornfels");
            offerStonecuttingRecipes(exporter, VABlocks.HORNFELS, VACollections.POLISHED_HORNFELS, VACollections.HORNFELS_TILES);
            generateCuttableFamilyChain(exporter, VACollections.COBBLED_HORNFELS);
            generateCuttableFamilyChain(exporter, VACollections.POLISHED_HORNFELS, VACollections.HORNFELS_TILES);
            generateCuttableFamilyChain(exporter, VACollections.HORNFELS_TILES);
            offer2x2ConversionChain(exporter, VABlocks.HORNFELS, VABlocks.POLISHED_HORNFELS, VABlocks.HORNFELS_TILES);

            offerSmelting(exporter, List.of(VABlocks.COBBLED_BLUESCHIST), RecipeCategory.BUILDING_BLOCKS, VABlocks.BLUESCHIST, 0.1F, 200, "blueschist");
            offerStonecuttingRecipes(exporter, VABlocks.BLUESCHIST, VACollections.POLISHED_BLUESCHIST, VACollections.BLUESCHIST_BRICKS);
            generateCuttableFamilyChain(exporter, VACollections.COBBLED_BLUESCHIST);
            generateCuttableFamilyChain(exporter, VACollections.POLISHED_BLUESCHIST, VACollections.BLUESCHIST_BRICKS);
            generateCuttableFamilyChain(exporter, VACollections.BLUESCHIST_BRICKS);
            offer2x2ConversionChain(exporter, VABlocks.BLUESCHIST, VABlocks.POLISHED_BLUESCHIST, VABlocks.BLUESCHIST_BRICKS);

            offerSmelting(exporter, List.of(VABlocks.COBBLED_SYENITE), RecipeCategory.BUILDING_BLOCKS, VABlocks.SYENITE, 0.1F, 200, "syenite");
            offerStonecuttingRecipes(exporter, VABlocks.SYENITE, VACollections.POLISHED_SYENITE, VACollections.SYENITE_BRICKS);
            generateCuttableFamilyChain(exporter, VACollections.COBBLED_SYENITE);
            generateCuttableFamilyChain(exporter, VACollections.POLISHED_SYENITE, VACollections.SYENITE_BRICKS);
            generateCuttableFamilyChain(exporter, VACollections.SYENITE_BRICKS);
            offer2x2ConversionChain(exporter, VABlocks.SYENITE, VABlocks.POLISHED_SYENITE, VABlocks.SYENITE_BRICKS);

            offer2x2FullRecipe(exporter, RecipeCategory.MISC, Items.STRING, VAItems.COTTON, 2);
            offerShapelessRecipe(exporter, VAItems.COTTON_SEEDS, VAItems.COTTON, "cotton_seeds", 1);

            offerShapelessRecipe(exporter, VAItems.CORN_SEEDS, VAItems.CORN, "corn_seeds", 1);

            offerCookingRecipes(exporter, VAItems.FRIED_EGG, Items.EGG, 0.35F, "fried_egg");
            offerCookingRecipes(exporter, VAItems.ROASTED_CORN, VAItems.CORN, 0.35F, "corn");

            offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VAItems.ROCK_SALT_BLOCK, VAItems.ROCK_SALT);

            offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.WEBBED_SILK, VAItems.SILK_THREAD);
            offerCompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.SILK_BLOCK, VAItems.SILK_THREAD);
            List<Item> dyes = List.of(Items.BLACK_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.CYAN_DYE, Items.GRAY_DYE, Items.GREEN_DYE, Items.LIGHT_BLUE_DYE, Items.LIGHT_GRAY_DYE, Items.LIME_DYE, Items.MAGENTA_DYE, Items.ORANGE_DYE, Items.PINK_DYE, Items.PURPLE_DYE, Items.RED_DYE, Items.YELLOW_DYE, Items.WHITE_DYE);
            List<Item> silkbulbs = List.of(VABlocks.BLACK_SILKBULB.asItem(), VABlocks.BLUE_SILKBULB.asItem(), VABlocks.BROWN_SILKBULB.asItem(), VABlocks.CYAN_SILKBULB.asItem(), VABlocks.GRAY_SILKBULB.asItem(), VABlocks.GREEN_SILKBULB.asItem(), VABlocks.LIGHT_BLUE_SILKBULB.asItem(), VABlocks.LIGHT_GRAY_SILKBULB.asItem(), VABlocks.LIME_SILKBULB.asItem(), VABlocks.MAGENTA_SILKBULB.asItem(), VABlocks.ORANGE_SILKBULB.asItem(), VABlocks.PINK_SILKBULB.asItem(), VABlocks.PURPLE_SILKBULB.asItem(), VABlocks.RED_SILKBULB.asItem(), VABlocks.YELLOW_SILKBULB.asItem(), VABlocks.WHITE_SILKBULB.asItem(), VABlocks.SILKBULB.asItem());
            offerDyeableRecipes(exporter, dyes, silkbulbs, "silkbulbs");

            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, VAItems.STEEL_AXE).input('#', Items.STICK).input('X', VAItems.STEEL_INGOT).pattern("XX").pattern("X#").pattern(" #").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, VAItems.STEEL_BOOTS).input('X', VAItems.STEEL_INGOT).pattern("X X").pattern("X X").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, VAItems.STEEL_CHESTPLATE).input('X', VAItems.STEEL_INGOT).pattern("X X").pattern("XXX").pattern("XXX").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, VAItems.STEEL_HELMET).input('X', VAItems.STEEL_INGOT).pattern("XXX").pattern("X X").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, VAItems.STEEL_HOE).input('#', Items.STICK).input('X', VAItems.STEEL_INGOT).pattern("XX").pattern(" #").pattern(" #").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, VAItems.STEEL_LEGGINGS).input('X', VAItems.STEEL_INGOT).pattern("XXX").pattern("X X").pattern("X X").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, VAItems.STEEL_PICKAXE).input('#', Items.STICK).input('X', VAItems.STEEL_INGOT).pattern("XXX").pattern(" # ").pattern(" # ").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, VAItems.STEEL_SHOVEL).input('#', Items.STICK).input('X', VAItems.STEEL_INGOT).pattern("X").pattern("#").pattern("#").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, VAItems.STEEL_SWORD).input('#', Items.STICK).input('X', VAItems.STEEL_INGOT).pattern("X").pattern("X").pattern("#").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);


            offerToolGildRecipes(exporter, Items.AMETHYST_SHARD, VAItems.AMETHYST_TOOL_SETS);
            offerToolGildRecipes(exporter, Items.COPPER_INGOT, VAItems.COPPER_TOOL_SETS);
            offerToolGildRecipes(exporter, Items.EMERALD, VAItems.EMERALD_TOOL_SETS);
            offerToolGildRecipes(exporter, VAItems.IOLITE, VAItems.IOLITE_TOOL_SETS);
            offerToolGildRecipes(exporter, Items.QUARTZ, VAItems.QUARTZ_TOOL_SETS);
            offerToolGildRecipes(exporter, Items.ECHO_SHARD, VAItems.SCULK_TOOL_SETS);
            offerToolUpgradeRecipes(exporter, VAItems.AMETHYST_DIAMOND_TOOL_SET, VAItems.AMETHYST_NETHERITE_TOOL_SET);
            offerToolUpgradeRecipes(exporter, VAItems.COPPER_DIAMOND_TOOL_SET, VAItems.COPPER_NETHERITE_TOOL_SET);
            offerToolUpgradeRecipes(exporter, VAItems.EMERALD_DIAMOND_TOOL_SET, VAItems.EMERALD_NETHERITE_TOOL_SET);
            offerToolUpgradeRecipes(exporter, VAItems.IOLITE_DIAMOND_TOOL_SET, VAItems.IOLITE_NETHERITE_TOOL_SET);
            offerToolUpgradeRecipes(exporter, VAItems.QUARTZ_DIAMOND_TOOL_SET, VAItems.QUARTZ_NETHERITE_TOOL_SET);
            offerToolUpgradeRecipes(exporter, VAItems.SCULK_DIAMOND_TOOL_SET, VAItems.SCULK_NETHERITE_TOOL_SET);

            generateColorfulBlockSetRecipes(exporter, VACollections.CHARTREUSE, VAItems.CHARTREUSE_DYE);
            generateColorfulBlockSetRecipes(exporter, VACollections.MAROON, VAItems.MAROON_DYE);
            generateColorfulBlockSetRecipes(exporter, VACollections.INDIGO, VAItems.INDIGO_DYE);
            generateColorfulBlockSetRecipes(exporter, VACollections.PLUM, VAItems.PLUM_DYE);
            generateColorfulBlockSetRecipes(exporter, VACollections.VIRIDIAN, VAItems.VIRIDIAN_DYE);
            generateColorfulBlockSetRecipes(exporter, VACollections.TAN, VAItems.TAN_DYE);
            generateColorfulBlockSetRecipes(exporter, VACollections.SINOPIA, VAItems.SINOPIA_DYE);
            generateColorfulBlockSetRecipes(exporter, VACollections.LILAC, VAItems.LILAC_DYE);

            offerColoringStationRecipes(exporter,
                    VACollections.WHITE,
                    VACollections.LIGHT_GRAY,
                    VACollections.GRAY,
                    VACollections.BLACK,
                    VACollections.TAN,
                    VACollections.BROWN,
                    VACollections.MAROON,
                    VACollections.RED,
                    VACollections.SINOPIA,
                    VACollections.ORANGE,
                    VACollections.YELLOW,
                    VACollections.CHARTREUSE,
                    VACollections.LIME,
                    VACollections.GREEN,
                    VACollections.VIRIDIAN,
                    VACollections.CYAN,
                    VACollections.LIGHT_BLUE,
                    VACollections.BLUE,
                    VACollections.INDIGO,
                    VACollections.PURPLE,
                    VACollections.PLUM,
                    VACollections.MAGENTA,
                    VACollections.PINK,
                    VACollections.LILAC
            );

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.MAROON_DYE, 2)
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.BLACK_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.RED_DYE)).offerTo(exporter);

            
            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.CHARTREUSE_DYE, 2).group("chartreuse_dye")
                    .input(Ingredient.ofItems(Items.LIME_DYE), 1)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.LIME_DYE)).offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.CHARTREUSE_DYE, 3).group("chartreuse_dye")
                    .input(Ingredient.ofItems(Items.GREEN_DYE), 1)
                    .input(Ingredient.ofItems(Items.WHITE_DYE), 1)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.YELLOW_DYE)).offerTo(exporter, idOf("chartreuse_dye_from_green_white_yellow_dye"));


            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.INDIGO_DYE, 2).group("indigo_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .input(Ingredient.ofItems(Items.PURPLE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.PURPLE_DYE)).offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.INDIGO_DYE, 3).group("indigo_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 2)
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.BLUE_DYE)).offerTo(exporter, idOf("indigo_dye_from_blue_blue_red_dye"));


            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.PLUM_DYE, 2).group("plum_dye")
                    .input(Ingredient.ofItems(Items.PURPLE_DYE), 1)
                    .input(Ingredient.ofItems(VAItems.MAROON_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.PURPLE_DYE)).offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.PLUM_DYE, 3).group("plum_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(VAItems.MAROON_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(VAItems.MAROON_DYE)).offerTo(exporter, idOf("plum_from_blue_red_maroon_dye"));

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.PLUM_DYE, 4).group("plum_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .input(Ingredient.ofItems(Items.RED_DYE), 2)
                    .input(Ingredient.ofItems(Items.BLACK_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.RED_DYE)).offerTo(exporter, idOf("plum_from_blue_red_red_black_dye"));


            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.VIRIDIAN_DYE, 2).group("viridian_dye")
                    .input(Ingredient.ofItems(Items.GREEN_DYE), 1)
                    .input(Ingredient.ofItems(Items.CYAN_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.CYAN_DYE)).offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.VIRIDIAN_DYE, 3).group("viridian_dye")
                    .input(Ingredient.ofItems(Items.GREEN_DYE), 2)
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.GREEN_DYE)).offerTo(exporter, idOf("viridian_from_green_green_blue_dye"));

            
            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.TAN_DYE, 2).group("tan_dye")
                    .input(Ingredient.ofItems(Items.ORANGE_DYE), 1)
                    .input(Ingredient.ofItems(Items.GRAY_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.ORANGE_DYE)).offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.TAN_DYE, 3).group("tan_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .input(Ingredient.ofItems(Items.GRAY_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.GRAY_DYE)).offerTo(exporter, idOf("tan_from_red_yellow_gray_dye"));

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.TAN_DYE, 4).group("tan_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .input(Ingredient.ofItems(Items.WHITE_DYE), 1)
                    .input(Ingredient.ofItems(Items.BLACK_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.RED_DYE)).offerTo(exporter, idOf("tan_from_red_yellow_black_white_dye"));


            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.SINOPIA_DYE, 2).group("sinopia_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.ORANGE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.ORANGE_DYE)).offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.SINOPIA_DYE, 3).group("sinopia_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 2)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.RED_DYE)).offerTo(exporter, idOf("sinopia_from_red_red_yellow_dye"));

            
            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.LILAC_DYE, 2).group("lilac_dye")
                    .input(Ingredient.ofItems(Items.WHITE_DYE), 1)
                    .input(Ingredient.ofItems(Items.PURPLE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.PURPLE_DYE)).offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.LILAC_DYE, 3).group("lilac_dye")
                    .input(Ingredient.ofItems(Items.WHITE_DYE), 1)
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.WHITE_DYE)).offerTo(exporter, idOf("lilac_from_white_red_blue_dye"));




            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, VAItems.CLIMBING_ROPE, 4)
                    .pattern("#  ")
                    .pattern(" #s")
                    .pattern(" ss")
                    .input('#', Items.COPPER_INGOT).input('s', Items.STRING)
                    .criterion("copper_ingot", conditionsFromItem(Items.COPPER_INGOT)).offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, VAItems.WAXED_CLIMBING_ROPE, 1).input(VAItems.CLIMBING_ROPE).input(Items.HONEYCOMB).criterion("has_rope", conditionsFromItem(VAItems.CLIMBING_ROPE)).offerTo(exporter);
            ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, VAItems.WAXED_EXPOSED_CLIMBING_ROPE, 1).input(VAItems.EXPOSED_CLIMBING_ROPE).input(Items.HONEYCOMB).criterion("has_rope", conditionsFromItem(VAItems.EXPOSED_CLIMBING_ROPE)).offerTo(exporter);
            ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, VAItems.WAXED_WEATHERED_CLIMBING_ROPE, 1).input(VAItems.WEATHERED_CLIMBING_ROPE).input(Items.HONEYCOMB).criterion("has_rope", conditionsFromItem(VAItems.WEATHERED_CLIMBING_ROPE)).offerTo(exporter);
            ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, VAItems.WAXED_OXIDIZED_CLIMBING_ROPE, 1).input(VAItems.OXIDIZED_CLIMBING_ROPE).input(Items.HONEYCOMB).criterion("has_rope", conditionsFromItem(VAItems.OXIDIZED_CLIMBING_ROPE)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, VABlocks.SILKBULB, 1)
                    .pattern("###")
                    .pattern("#b#")
                    .pattern("###")
                    .input('#', VAItems.SILK_THREAD).input('b', VAItems.ACID_BLOCK)
                    .criterion("acid_block", conditionsFromItem(VAItems.ACID_BLOCK)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, VABlocks.ENTANGLEMENT_DRIVE, 1)
                    .pattern("s#s")
                    .pattern("sNs")
                    .pattern("s#s")
                    .input('#', VAItems.IOLITE).input('s', VAItems.STEEL_INGOT).input('N', Items.NETHER_STAR)
                    .criterion("iolite", conditionsFromItem(VAItems.IOLITE)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, VABlocks.WARP_TETHER, 1)
                    .pattern("s#s")
                    .pattern("epe")
                    .input('#', VAItems.IOLITE).input('s', VAItems.STEEL_INGOT).input('e', Blocks.END_STONE).input('p', Items.ENDER_PEARL)
                    .criterion("iolite", conditionsFromItem(VAItems.IOLITE)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, VABlocks.WARP_ANCHOR, 1)
                    .pattern("s#s")
                    .pattern("eye")
                    .input('#', VAItems.IOLITE).input('s', VAItems.STEEL_INGOT).input('e', Blocks.END_STONE).input('y', Items.ENDER_EYE)
                    .criterion("iolite", conditionsFromItem(VAItems.IOLITE)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, VAItems.STEEL_BOMB, 8)
                    .pattern(" s ")
                    .pattern("gag")
                    .pattern(" # ")
                    .input('#', VAItems.STEEL_INGOT).input('s', Items.STRING).input('g', Items.GUNPOWDER).input('a', Items.SAND)
                    .criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, VAItems.REDSTONE_BRIDGE, 3)
                    .pattern("#")
                    .pattern("r")
                    .pattern("#")
                    .input('#', VAItems.STEEL_INGOT).input('r', Items.REDSTONE)
                    .criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, VAItems.CAGELIGHT, 3)
                    .pattern("#")
                    .pattern("g")
                    .pattern("#")
                    .input('#', VAItems.STEEL_INGOT).input('g', Items.GLOWSTONE_DUST)
                    .criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, VAItems.COLORING_STATION, 1)
                    .pattern("BB")
                    .pattern("##")
                    .pattern("##")
                    .input('#', Ingredient.fromTag(ItemTags.PLANKS)).input('B', Ingredient.fromTag(ItemTags.WOOL))
                    .criterion("wool", conditionsFromTag(ItemTags.WOOL)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, VAItems.SPOTLIGHT, 1)
                    .pattern("ssa")
                    .pattern("rga")
                    .pattern("ssa")
                    .input('a', Items.AMETHYST_SHARD).input('g', Items.GLOWSTONE).input('s', VAItems.STEEL_INGOT).input('r', Items.REDSTONE)
                    .criterion("glowstone", conditionsFromItem(Items.GLOWSTONE)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, VAItems.ICE_CREAM, 1)
                    .pattern(" s ")
                    .pattern("bmb")
                    .pattern(" w ")
                    .input('s', VAItems.ROCK_SALT).input('b', Items.SNOWBALL).input('m', Items.MILK_BUCKET).input('w', Items.BOWL)
                    .criterion("milk", conditionsFromItem(Items.MILK_BUCKET)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, VAItems.STEEL_GRATE, 4)
                    .pattern(" c ")
                    .pattern("c c")
                    .pattern(" c ")
                    .input('c', VAItems.CUT_STEEL)
                    .criterion("cut_steel", conditionsFromItem(VAItems.CUT_STEEL)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, VAItems.ENGRAVING_CHISEL, 1)
                    .pattern("c")
                    .pattern("s")
                    .pattern("c")
                    .input('c', ItemTags.STONE_CRAFTING_MATERIALS)
                    .input('s', VAItems.STEEL_INGOT)
                    .criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, VAItems.FLOATROCK, 9)
                    .pattern("ccc")
                    .pattern("cfc")
                    .pattern("ccc")
                    .input('c', Items.COBBLESTONE)
                    .input('f', VAItems.FLOATROCK)
                    .criterion("floatrock", conditionsFromItem(VAItems.FLOATROCK)).offerTo(exporter);


            offerSmithingTrimRecipe(exporter, VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE, idOf("exoskeleton_armor_trim_smithing_template_smithing_trim"));
            offerSmithingTemplateCopyingRecipe(exporter, VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE, VAItems.SILK_BLOCK);

            offerSmelting(exporter, List.of(VAItems.FLOATROCK_COAL_ORE), RecipeCategory.MISC, Items.COAL, 0.1F, 200, "coal");
            offerSmelting(exporter, List.of(VAItems.FLOATROCK_COPPER_ORE), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7F, 200, "copper_ingot");
            offerSmelting(exporter, List.of(VAItems.FLOATROCK_IRON_ORE), RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 200, "iron_ingot");
            offerSmelting(exporter, List.of(VAItems.FLOATROCK_GOLD_ORE), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 200, "gold_ingot");
            offerSmelting(exporter, List.of(VAItems.FLOATROCK_REDSTONE_ORE), RecipeCategory.MISC, Items.REDSTONE, 0.7F, 200, "redstone");
            offerSmelting(exporter, List.of(VAItems.FLOATROCK_EMERALD_ORE), RecipeCategory.MISC, Items.EMERALD, 1.0F, 200, "emerald");
            offerSmelting(exporter, List.of(VAItems.FLOATROCK_LAPIS_ORE), RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2F, 200, "lapis_lazuli");
            offerSmelting(exporter, List.of(VAItems.FLOATROCK_DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 200, "diamond");

            offerBlasting(exporter, List.of(VAItems.FLOATROCK_COAL_ORE), RecipeCategory.MISC, Items.COAL, 0.1F, 100, "coal");
            offerBlasting(exporter, List.of(VAItems.FLOATROCK_COPPER_ORE), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7F, 100, "copper_ingot");
            offerBlasting(exporter, List.of(VAItems.FLOATROCK_IRON_ORE), RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 100, "iron_ingot");
            offerBlasting(exporter, List.of(VAItems.FLOATROCK_GOLD_ORE), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 100, "gold_ingot");
            offerBlasting(exporter, List.of(VAItems.FLOATROCK_REDSTONE_ORE), RecipeCategory.MISC, Items.REDSTONE, 0.7F, 100, "redstone");
            offerBlasting(exporter, List.of(VAItems.FLOATROCK_EMERALD_ORE), RecipeCategory.MISC, Items.EMERALD, 1.0F, 100, "emerald");
            offerBlasting(exporter, List.of(VAItems.FLOATROCK_LAPIS_ORE), RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2F, 100, "lapis_lazuli");
            offerBlasting(exporter, List.of(VAItems.FLOATROCK_DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 100, "diamond");

            offerHedgeRecipe(exporter, VABlocks.AEROBLOOM_HEDGE, VABlocks.AEROBLOOM_LEAVES);

            offerStonecuttingRecipes(exporter, VABlocks.FLOATROCK, VACollections.POLISHED_FLOATROCK, VACollections.FLOATROCK_BRICKS);
            generateCuttableFamilyChain(exporter, VACollections.FLOATROCK);
            generateCuttableFamilyChain(exporter, VACollections.POLISHED_FLOATROCK);
            generateCuttableFamilyChain(exporter, VACollections.FLOATROCK_BRICKS);
            offer2x2ConversionChain(exporter, VABlocks.FLOATROCK, VABlocks.POLISHED_FLOATROCK, VABlocks.FLOATROCK_BRICKS);

            generateFamily(exporter, VACollections.AEROBLOOM, FeatureFlags.VANILLA_FEATURES);
            offerBarkBlockRecipe(exporter, VAItems.AEROBLOOM_WOOD, VAItems.AEROBLOOM_LOG);
            offerBarkBlockRecipe(exporter, VAItems.STRIPPED_AEROBLOOM_WOOD, VAItems.STRIPPED_AEROBLOOM_LOG);
            offerPlanksRecipe2(exporter, VAItems.AEROBLOOM_PLANKS, VAItemTags.AEROBLOOM_LOGS, 4);
            offerHangingSignRecipe(exporter, VAItems.AEROBLOOM_HANGING_SIGN, VAItems.STRIPPED_AEROBLOOM_LOG);
        }
    }

    private static class PreviewProvider extends Provider {
        public PreviewProvider(FabricDataOutput fabricDataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> wrapperLookupCompletableFuture) {
            super(fabricDataOutput, wrapperLookupCompletableFuture);
        }

        @Override
        public void generate(RecipeExporter exporter) {
        }
    }

    private abstract static class Provider extends FabricRecipeProvider {
        protected static final ColoringStationBlockEntity.DyeContents WHITE_COST = VADyeColors.WHITE_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents LIGHT_GRAY_COST = VADyeColors.LIGHT_GRAY_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents GRAY_COST = VADyeColors.GRAY_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents BLACK_COST = VADyeColors.BLACK_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents TAN_COST = VADyeColors.TAN_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents BROWN_COST = VADyeColors.BROWN_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents MAROON_COST = VADyeColors.MAROON_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents RED_COST = VADyeColors.RED_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents SINOPIA_COST = VADyeColors.SINOPIA_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents ORANGE_COST = VADyeColors.ORANGE_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents YELLOW_COST = VADyeColors.YELLOW_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents CHARTREUSE_COST = VADyeColors.CHARTREUSE_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents LIME_COST = VADyeColors.LIME_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents GREEN_COST = VADyeColors.GREEN_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents VIRIDIAN_COST = VADyeColors.VIRIDIAN_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents CYAN_COST = VADyeColors.CYAN_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents LIGHT_BLUE_COST = VADyeColors.LIGHT_BLUE_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents BLUE_COST = VADyeColors.BLUE_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents INDIGO_COST = VADyeColors.INDIGO_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents PURPLE_COST = VADyeColors.PURPLE_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents PLUM_COST = VADyeColors.PLUM_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents MAGENTA_COST = VADyeColors.MAGENTA_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents PINK_COST = VADyeColors.PINK_CONTENT;
        protected static final ColoringStationBlockEntity.DyeContents LILAC_COST = VADyeColors.LILAC_CONTENT;

        public Provider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        protected static void offerCookingRecipes(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, float experience, String group) {
            offerSmelting(exporter, List.of(input), RecipeCategory.FOOD, output, experience, 200, group);
            offerFoodCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, SmokingRecipe::new, 100, input, output, experience);
            offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 600, input, output, experience);
        }

        protected static void offerColoringRecipe(RecipeExporter exporter, ItemConvertible input, ItemConvertible output, ColoringStationBlockEntity.DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(Ingredient.ofItems(input), cost, output, index).offerTo(exporter);
        }

        protected static void offerColoringRecipe(RecipeExporter exporter, ItemConvertible output, ColoringStationBlockEntity.DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(null, cost, output, index).offerTo(exporter);
        }

        protected static void offerColoringRecipe(RecipeExporter exporter, TagKey<Item> input, ItemConvertible output, ColoringStationBlockEntity.DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(input), cost, output, index).offerTo(exporter);
        }

        protected static void offerArmorColoringRecipe(RecipeExporter exporter, DyeItem input, int i) {
            ArmorColoringRecipeJsonBuilder.create(Ingredient.ofItems(input), i).offerTo(exporter);
        }

        protected static void offer2x2ConversionChain(RecipeExporter exporter, Block... blocks) {
            for (int i = 0; i < blocks.length - 1; i++) {
                Block input = blocks[i];
                Block output = blocks[i + 1];
                String id = Registries.BLOCK.getId(output).getPath() + "_from_compacting_" + Registries.BLOCK.getId(input).getPath();
                ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("##")
                        .pattern("##")
                        .input('#', input).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter, id);
            }
        }

        protected static void offerColoringRecipes(RecipeExporter exporter, ColorfulBlockSet set, ColoringStationBlockEntity.DyeContents cost, int index) {
            offerColoringRecipe(exporter, set.dye(), cost.copyAndMultiply(8), index);
            offerArmorColoringRecipe(exporter, set.dye(), index);
            set.ifWool(block -> offerColoringRecipe(exporter, ItemTags.WOOL, block, cost, index));
            set.ifCarpet(block -> offerColoringRecipe(exporter, ItemTags.WOOL_CARPETS, block, cost, index));
            set.ifStainedGlass(block -> offerColoringRecipe(exporter, VAItemTags.COLORABLE_GLASS, block, cost, index));
            set.ifStainedGlassPane(block -> offerColoringRecipe(exporter, VAItemTags.COLORABLE_GLASS_PANE, block, cost, index));
            set.ifTerracotta(block -> offerColoringRecipe(exporter, ItemTags.TERRACOTTA, block, cost, index));
            set.ifCandle(block -> offerColoringRecipe(exporter, ItemTags.CANDLES, block, cost, index));
            set.ifSilkbulb(block -> offerColoringRecipe(exporter, VAItemTags.SILKBULBS, block, cost, index));
            set.ifBed(block -> offerColoringRecipe(exporter, ItemTags.BEDS, block, cost, index));
            set.ifShulkerBox(block -> offerColoringRecipe(exporter, VAItemTags.SHULKER_BOXES, block, cost, index));
        }

        @SafeVarargs
        protected static void offerColoringStationRecipes(RecipeExporter exporter, ColorfulBlockSet... sets) {
            int i = 0;
            for (ColorfulBlockSet set : sets) {
                offerColoringRecipes(exporter, set, VADyeColors.getContents(set.dye(), 1), i);
                i += 1;
            }
        }

        protected static void generateCuttableFamilyChain(RecipeExporter exporter, BlockFamily baseFamily, BlockFamily... subFamilies) {
            generateFamily(exporter, baseFamily, FeatureFlags.VANILLA_FEATURES);
            offerStonecuttingRecipes(exporter, baseFamily, baseFamily);
            for (BlockFamily subFamily : subFamilies) {
                offerStonecuttingRecipes(exporter, baseFamily, subFamily);
            }
        }

        protected static void generateColorfulBlockSetRecipes(RecipeExporter exporter, ColorfulBlockSet set, Item dye) {
            set.ifWool(wool -> {
                ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, wool).input(ItemTags.WOOL).input(dye).criterion("has_dye", conditionsFromItem(dye)).offerTo(exporter);
                set.ifBed( bed -> offerBedRecipe(exporter, bed, wool));
                set.ifBanner(banner -> offerBannerRecipe(exporter, banner, wool));
                set.ifCarpet(carpet -> offerCarpetRecipe(exporter, carpet, wool));
            });
            set.ifCarpet(block -> ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, block).input(ItemTags.WOOL_CARPETS).input(dye).criterion("has_dye", conditionsFromItem(dye)).offerTo(exporter, CraftingRecipeJsonBuilder.getItemId(block).withSuffixedPath("_dyeing")));
            set.ifConcretePowder( block -> offerConcretePowderDyeingRecipe(exporter, block, dye));
            set.ifTerracotta(block -> offerTerracottaDyeingRecipe(exporter, block, dye));
            set.ifStainedGlass(block -> {
                offerStainedGlassDyeingRecipe(exporter, block, dye);
                set.ifStainedGlassPane(pane -> {
                    offerStainedGlassPaneRecipe(exporter, pane, block);
                    offerStainedGlassPaneDyeingRecipe(exporter, pane, dye);
                });
            });
            set.ifCandle(block -> offerCandleDyeingRecipe(exporter, block, dye));
            set.ifSilkbulb(block -> ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, block).input(VAItemTags.SILKBULBS).input(dye).criterion("has_dye", conditionsFromItem(dye)).offerTo(exporter));
        }

        protected static void offerStonecuttingRecipes(RecipeExporter exporter, BlockFamily baseFamily, BlockFamily... resultFamilies) {
            Block block;
            for (BlockFamily resultFamily : resultFamilies) {
                if (!baseFamily.getBaseBlock().equals(resultFamily.getBaseBlock()))
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, resultFamily.getBaseBlock(), baseFamily.getBaseBlock());
                if ((block = resultFamily.getVariant(BlockFamily.Variant.STAIRS)) != null)
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock());
                if ((block = resultFamily.getVariant(BlockFamily.Variant.SLAB)) != null)
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock(), 2);
                if ((block = resultFamily.getVariant(BlockFamily.Variant.WALL)) != null)
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock());
                if ((block = resultFamily.getVariant(BlockFamily.Variant.CHISELED)) != null)
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock());
            }
        }

        protected static void offerStonecuttingRecipes(RecipeExporter exporter, Block baseBlock, BlockFamily... resultFamilies) {
            Block block;
            for (BlockFamily resultFamily : resultFamilies) {
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, resultFamily.getBaseBlock(), baseBlock);
                if ((block = resultFamily.getVariant(BlockFamily.Variant.STAIRS)) != null)
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, block, baseBlock);
                if ((block = resultFamily.getVariant(BlockFamily.Variant.SLAB)) != null)
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, block, baseBlock, 2);
                if ((block = resultFamily.getVariant(BlockFamily.Variant.WALL)) != null)
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, block, baseBlock);
                if ((block = resultFamily.getVariant(BlockFamily.Variant.CHISELED)) != null)
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, block, baseBlock);
            }
        }

        protected static void offerToolGildRecipe(RecipeExporter exporter, GildedToolItem result, Item addition) {
            SmithingTransformRecipeJsonBuilder.create(
                    Ingredient.ofItems(VAItems.TOOL_GILD_SMITHING_TEMPLATE),
                    Ingredient.ofItems(result.getBaseItem()),
                    Ingredient.ofItems(addition),
                    RecipeCategory.TOOLS,
                    result.asItem()
            ).criterion("has_smithing_template", conditionsFromItem(VAItems.TOOL_GILD_SMITHING_TEMPLATE)).offerTo(exporter, idOf("smithing/" + getItemPath(result)));
        }

        protected static void offerToolGildRecipes(RecipeExporter exporter, Item addition, RegistryHelper.ItemRegistryHelper.ToolSet... sets) {
            for (RegistryHelper.ItemRegistryHelper.ToolSet set : sets) {
                for (Item item : set.getItems())
                    if (item instanceof GildedToolItem gildedToolItem)
                        offerToolGildRecipe(exporter, gildedToolItem, addition);
            }
        }

        protected static void offerToolUpgradeRecipes(RecipeExporter exporter, RegistryHelper.ItemRegistryHelper.ToolSet input, RegistryHelper.ItemRegistryHelper.ToolSet output) {
            offerNetheriteUpgradeRecipe(exporter, input.AXE(), output.AXE());
            offerNetheriteUpgradeRecipe(exporter, input.HOE(), output.HOE());
            offerNetheriteUpgradeRecipe(exporter, input.PICKAXE(), output.PICKAXE());
            offerNetheriteUpgradeRecipe(exporter, input.SHOVEL(), output.SHOVEL());
            offerNetheriteUpgradeRecipe(exporter, input.SWORD(), output.SWORD());
        }

        protected static void offerNetheriteUpgradeRecipe(RecipeExporter exporter, Item input, Item result) {
            SmithingTransformRecipeJsonBuilder.create(
                            Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                            Ingredient.ofItems(input),
                            Ingredient.ofItems(Items.NETHERITE_INGOT),
                            RecipeCategory.TOOLS,
                            result)
                    .criterion("has_netherite_ingot", conditionsFromItem(Items.NETHERITE_INGOT))
                    .offerTo(exporter, idOf("smithing/" + getItemPath(result) + "_upgrade"));
        }

        @SafeVarargs
        protected static void offerShapelessRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, int count, Pair<ItemConvertible, Integer>... input) {
            ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(category, output, count);
            builder.criterion(hasItem(input[0].getLeft()), conditionsFromItem(input[0].getLeft()));
            for (Pair<ItemConvertible, Integer> itemProvider : input) {
                builder.input(itemProvider.getLeft(), itemProvider.getRight());
            }
            builder.offerTo(exporter);
        }

        protected static void offer2x2FullRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input, int count) {
            ShapedRecipeJsonBuilder.create(category, output, count).input('#', input).pattern("##").pattern("##").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
        }

        protected static void offer2x2FullRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input, int count, String group) {
            ShapedRecipeJsonBuilder.create(category, output, count).input('#', input).pattern("##").pattern("##").group(group).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
        }

        protected static void offerHedgeRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                    .pattern("###")
                    .pattern("###")
                    .input('#', input).criterion("has_leaves", conditionsFromItem(input)).group("hedges").offerTo(exporter);
        }
    }
}
