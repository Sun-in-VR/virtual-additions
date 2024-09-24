package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
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
import net.minecraft.item.*;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
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

    private static class BaseProvider extends FabricRecipeProvider {
        public BaseProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
            System.out.println("Base Provider");
        }

        @Override
        protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
            return new BaseGenerator(registryLookup, exporter);
        }

        @Override
        public String getName() {
            return "Virtual Additions Recipes";
        }
    }

    private static class PreviewProvider extends FabricRecipeProvider {
        public PreviewProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
            return new PreviewGenerator(registryLookup, exporter);
        }

        @Override
        public String getName() {
            return "Virtual Additions Preview Recipes";
        }
    }

    private static class BaseGenerator extends Generator {

        protected BaseGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
            super(registryLookup, exporter);
            System.out.println("Base Generator");
        }

        @Override
        public void generate() {
            System.out.println("Base Generator is Generating");
            offerBlasting(List.of(VAItems.RAW_STEEL), RecipeCategory.MISC, VAItems.STEEL_INGOT, 1.0F, 100, "steel_ingot");
            offerBlasting(List.of(VAItems.IOLITE_ORE), RecipeCategory.MISC, VAItems.IOLITE, 1.0F, 100, "iolite");
            offerSmelting(List.of(VAItems.IOLITE_ORE), RecipeCategory.MISC, VAItems.IOLITE, 1.0F, 200, "iolite");

            offerHedgeRecipe(VABlocks.OAK_HEDGE, Blocks.OAK_LEAVES);
            offerHedgeRecipe(VABlocks.SPRUCE_HEDGE, Blocks.SPRUCE_LEAVES);
            offerHedgeRecipe(VABlocks.BIRCH_HEDGE, Blocks.BIRCH_LEAVES);
            offerHedgeRecipe(VABlocks.JUNGLE_HEDGE, Blocks.JUNGLE_LEAVES);
            offerHedgeRecipe(VABlocks.ACACIA_HEDGE, Blocks.ACACIA_LEAVES);
            offerHedgeRecipe(VABlocks.DARK_OAK_HEDGE, Blocks.DARK_OAK_LEAVES);
            offerHedgeRecipe(VABlocks.MANGROVE_HEDGE, Blocks.MANGROVE_LEAVES);
            offerHedgeRecipe(VABlocks.CHERRY_HEDGE, Blocks.CHERRY_LEAVES);
            offerHedgeRecipe(VABlocks.AZALEA_HEDGE, Blocks.AZALEA_LEAVES);
            offerHedgeRecipe(VABlocks.FLOWERING_AZALEA_HEDGE, Blocks.FLOWERING_AZALEA_LEAVES);

            offerCompactingRecipe(RecipeCategory.MISC, VABlocks.STEEL_BLOCK, VAItems.STEEL_INGOT, "steel_ingot");
            offerShapelessRecipe(VAItems.STEEL_INGOT, VABlocks.STEEL_BLOCK, "steel", 9);
            offerShapelessRecipe(VAItems.STEEL_INGOT, VABlocks.WAXED_STEEL_BLOCK, "steel", 9);
            offerCompactingRecipe(RecipeCategory.MISC, VABlocks.RAW_STEEL_BLOCK, VAItems.RAW_STEEL, "steel_ingot");
            offerShapelessRecipe(VAItems.RAW_STEEL, VABlocks.RAW_STEEL_BLOCK, "raw_steel", 9);
            offerCompactingRecipe(RecipeCategory.MISC, VABlocks.IOLITE_BLOCK, VAItems.IOLITE, "iolite");
            offerShapelessRecipe(VAItems.IOLITE, VABlocks.IOLITE_BLOCK, "iolite", 9);

            offerShapelessRecipe(RecipeCategory.MISC, VAItems.RAW_STEEL, 1, Pair.of(Items.RAW_IRON, 3), Pair.of(Items.COAL, 1));

            offerShapelessRecipe(RecipeCategory.MISC, VAItems.TOOL_GILD_SMITHING_TEMPLATE, 1, Pair.of(VAItems.STEEL_INGOT, 1), Pair.of(Items.DIAMOND, 1));

            offer2x2FullRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL, VAItems.STEEL_INGOT, 16);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL, VAItems.STEEL_INGOT, 4);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL_STAIRS, VAItems.STEEL_INGOT, 4);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL_SLAB, VAItems.STEEL_INGOT, 8);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.STEEL_GRATE, VAItems.STEEL_INGOT, 4);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.CHISELED_STEEL, VAItems.STEEL_INGOT, 4);
            createDoorRecipe(VABlocks.STEEL_DOOR, Ingredient.ofItems(VAItems.STEEL_INGOT)).criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            createTrapdoorRecipe(VABlocks.STEEL_TRAPDOOR, Ingredient.ofItems(VAItems.STEEL_INGOT)).criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.DECORATIONS, VABlocks.STEEL_FENCE, 6).input('W', VABlocks.CUT_STEEL).input('#', VAItems.STEEL_INGOT).pattern("W#W").pattern("W#W").criterion("cut_steel", conditionsFromItem(VABlocks.CUT_STEEL)).offerTo(this.exporter);

            offerSteelRecipeSet(VABlocks.STEEL_BLOCK, VABlocks.CUT_STEEL, VABlocks.CUT_STEEL_STAIRS, VABlocks.CUT_STEEL_SLAB, VABlocks.STEEL_GRATE, VABlocks.CHISELED_STEEL);
            offerSteelRecipeSet(VABlocks.EXPOSED_STEEL_BLOCK, VABlocks.EXPOSED_CUT_STEEL, VABlocks.EXPOSED_CUT_STEEL_STAIRS, VABlocks.EXPOSED_CUT_STEEL_SLAB, VABlocks.EXPOSED_STEEL_GRATE, VABlocks.EXPOSED_CHISELED_STEEL);
            offerSteelRecipeSet(VABlocks.WEATHERED_STEEL_BLOCK, VABlocks.WEATHERED_CUT_STEEL, VABlocks.WEATHERED_CUT_STEEL_STAIRS, VABlocks.WEATHERED_CUT_STEEL_SLAB, VABlocks.WEATHERED_STEEL_GRATE, VABlocks.WEATHERED_CHISELED_STEEL);
            offerSteelRecipeSet(VABlocks.OXIDIZED_STEEL_BLOCK, VABlocks.OXIDIZED_CUT_STEEL, VABlocks.OXIDIZED_CUT_STEEL_STAIRS, VABlocks.OXIDIZED_CUT_STEEL_SLAB, VABlocks.OXIDIZED_STEEL_GRATE, VABlocks.OXIDIZED_CHISELED_STEEL);
            offerSteelRecipeSet(VABlocks.WAXED_STEEL_BLOCK, VABlocks.WAXED_CUT_STEEL, VABlocks.WAXED_CUT_STEEL_STAIRS, VABlocks.WAXED_CUT_STEEL_SLAB, VABlocks.WAXED_STEEL_GRATE, VABlocks.WAXED_CHISELED_STEEL);
            offerSteelRecipeSet(VABlocks.WAXED_EXPOSED_STEEL_BLOCK, VABlocks.WAXED_EXPOSED_CUT_STEEL, VABlocks.WAXED_EXPOSED_CUT_STEEL_STAIRS, VABlocks.WAXED_EXPOSED_CUT_STEEL_SLAB, VABlocks.WAXED_EXPOSED_STEEL_GRATE, VABlocks.WAXED_EXPOSED_CHISELED_STEEL);
            offerSteelRecipeSet(VABlocks.WAXED_WEATHERED_STEEL_BLOCK, VABlocks.WAXED_WEATHERED_CUT_STEEL, VABlocks.WAXED_WEATHERED_CUT_STEEL_STAIRS, VABlocks.WAXED_WEATHERED_CUT_STEEL_SLAB, VABlocks.WAXED_WEATHERED_STEEL_GRATE, VABlocks.WAXED_WEATHERED_CHISELED_STEEL);
            offerSteelRecipeSet(VABlocks.WAXED_OXIDIZED_STEEL_BLOCK, VABlocks.WAXED_OXIDIZED_CUT_STEEL, VABlocks.WAXED_OXIDIZED_CUT_STEEL_STAIRS, VABlocks.WAXED_OXIDIZED_CUT_STEEL_SLAB, VABlocks.WAXED_OXIDIZED_STEEL_GRATE, VABlocks.WAXED_OXIDIZED_CHISELED_STEEL);

            offerSmelting(List.of(VABlocks.COBBLED_HORNFELS), RecipeCategory.BUILDING_BLOCKS, VABlocks.HORNFELS, 0.1F, 200, "hornfels");
            offerStonecuttingRecipes(VABlocks.HORNFELS, VACollections.POLISHED_HORNFELS, VACollections.HORNFELS_TILES);
            generateCuttableFamilyChain(VACollections.COBBLED_HORNFELS);
            generateCuttableFamilyChain(VACollections.POLISHED_HORNFELS, VACollections.HORNFELS_TILES);
            generateCuttableFamilyChain(VACollections.HORNFELS_TILES);
            offer2x2ConversionChain(VABlocks.HORNFELS, VABlocks.POLISHED_HORNFELS, VABlocks.HORNFELS_TILES);

            offerSmelting(List.of(VABlocks.COBBLED_BLUESCHIST), RecipeCategory.BUILDING_BLOCKS, VABlocks.BLUESCHIST, 0.1F, 200, "blueschist");
            offerStonecuttingRecipes(VABlocks.BLUESCHIST, VACollections.POLISHED_BLUESCHIST, VACollections.BLUESCHIST_BRICKS);
            generateCuttableFamilyChain(VACollections.COBBLED_BLUESCHIST);
            generateCuttableFamilyChain(VACollections.POLISHED_BLUESCHIST, VACollections.BLUESCHIST_BRICKS);
            generateCuttableFamilyChain(VACollections.BLUESCHIST_BRICKS);
            offer2x2ConversionChain(VABlocks.BLUESCHIST, VABlocks.POLISHED_BLUESCHIST, VABlocks.BLUESCHIST_BRICKS);

            offerSmelting(List.of(VABlocks.COBBLED_SYENITE), RecipeCategory.BUILDING_BLOCKS, VABlocks.SYENITE, 0.1F, 200, "syenite");
            offerStonecuttingRecipes(VABlocks.SYENITE, VACollections.POLISHED_SYENITE, VACollections.SYENITE_BRICKS);
            generateCuttableFamilyChain(VACollections.COBBLED_SYENITE);
            generateCuttableFamilyChain(VACollections.POLISHED_SYENITE, VACollections.SYENITE_BRICKS);
            generateCuttableFamilyChain(VACollections.SYENITE_BRICKS);
            offer2x2ConversionChain(VABlocks.SYENITE, VABlocks.POLISHED_SYENITE, VABlocks.SYENITE_BRICKS);

            offer2x2FullRecipe(RecipeCategory.MISC, Items.STRING, VAItems.COTTON, 2);
            offerShapelessRecipe(VAItems.COTTON_SEEDS, VAItems.COTTON, "cotton_seeds", 1);

            offerShapelessRecipe(VAItems.CORN_SEEDS, VAItems.CORN, "corn_seeds", 1);

            offerCookingRecipes(VAItems.FRIED_EGG, Items.EGG, 0.35F, "fried_egg");
            offerCookingRecipes(VAItems.ROASTED_CORN, VAItems.CORN, 0.35F, "corn");

            offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, VAItems.ROCK_SALT_BLOCK, VAItems.ROCK_SALT);

            offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.WEBBED_SILK, VAItems.SILK_THREAD);
            offerCompactingRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.SILK_BLOCK, VAItems.SILK_THREAD);
            List<Item> dyes = List.of(Items.BLACK_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.CYAN_DYE, Items.GRAY_DYE, Items.GREEN_DYE, Items.LIGHT_BLUE_DYE, Items.LIGHT_GRAY_DYE, Items.LIME_DYE, Items.MAGENTA_DYE, Items.ORANGE_DYE, Items.PINK_DYE, Items.PURPLE_DYE, Items.RED_DYE, Items.YELLOW_DYE, Items.WHITE_DYE);
            List<Item> silkbulbs = List.of(VABlocks.BLACK_SILKBULB.asItem(), VABlocks.BLUE_SILKBULB.asItem(), VABlocks.BROWN_SILKBULB.asItem(), VABlocks.CYAN_SILKBULB.asItem(), VABlocks.GRAY_SILKBULB.asItem(), VABlocks.GREEN_SILKBULB.asItem(), VABlocks.LIGHT_BLUE_SILKBULB.asItem(), VABlocks.LIGHT_GRAY_SILKBULB.asItem(), VABlocks.LIME_SILKBULB.asItem(), VABlocks.MAGENTA_SILKBULB.asItem(), VABlocks.ORANGE_SILKBULB.asItem(), VABlocks.PINK_SILKBULB.asItem(), VABlocks.PURPLE_SILKBULB.asItem(), VABlocks.RED_SILKBULB.asItem(), VABlocks.YELLOW_SILKBULB.asItem(), VABlocks.WHITE_SILKBULB.asItem(), VABlocks.SILKBULB.asItem());
            offerDyeableRecipes(dyes, silkbulbs, "silkbulbs");

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.TOOLS, VAItems.STEEL_AXE).input('#', Items.STICK).input('X', VAItems.STEEL_INGOT).pattern("XX").pattern("X#").pattern(" #").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_BOOTS).input('X', VAItems.STEEL_INGOT).pattern("X X").pattern("X X").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_CHESTPLATE).input('X', VAItems.STEEL_INGOT).pattern("X X").pattern("XXX").pattern("XXX").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_HELMET).input('X', VAItems.STEEL_INGOT).pattern("XXX").pattern("X X").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.TOOLS, VAItems.STEEL_HOE).input('#', Items.STICK).input('X', VAItems.STEEL_INGOT).pattern("XX").pattern(" #").pattern(" #").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_LEGGINGS).input('X', VAItems.STEEL_INGOT).pattern("XXX").pattern("X X").pattern("X X").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.TOOLS, VAItems.STEEL_PICKAXE).input('#', Items.STICK).input('X', VAItems.STEEL_INGOT).pattern("XXX").pattern(" # ").pattern(" # ").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.TOOLS, VAItems.STEEL_SHOVEL).input('#', Items.STICK).input('X', VAItems.STEEL_INGOT).pattern("X").pattern("#").pattern("#").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_SWORD).input('#', Items.STICK).input('X', VAItems.STEEL_INGOT).pattern("X").pattern("X").pattern("#").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);


            offerToolGildRecipes(Items.AMETHYST_SHARD, VAItems.AMETHYST_TOOL_SETS);
            offerToolGildRecipes(Items.COPPER_INGOT, VAItems.COPPER_TOOL_SETS);
            offerToolGildRecipes(Items.EMERALD, VAItems.EMERALD_TOOL_SETS);
            offerToolGildRecipes(VAItems.IOLITE, VAItems.IOLITE_TOOL_SETS);
            offerToolGildRecipes(Items.QUARTZ, VAItems.QUARTZ_TOOL_SETS);
            offerToolGildRecipes(Items.ECHO_SHARD, VAItems.SCULK_TOOL_SETS);
            offerToolUpgradeRecipes(VAItems.AMETHYST_DIAMOND_TOOL_SET, VAItems.AMETHYST_NETHERITE_TOOL_SET);
            offerToolUpgradeRecipes(VAItems.COPPER_DIAMOND_TOOL_SET, VAItems.COPPER_NETHERITE_TOOL_SET);
            offerToolUpgradeRecipes(VAItems.EMERALD_DIAMOND_TOOL_SET, VAItems.EMERALD_NETHERITE_TOOL_SET);
            offerToolUpgradeRecipes(VAItems.IOLITE_DIAMOND_TOOL_SET, VAItems.IOLITE_NETHERITE_TOOL_SET);
            offerToolUpgradeRecipes(VAItems.QUARTZ_DIAMOND_TOOL_SET, VAItems.QUARTZ_NETHERITE_TOOL_SET);
            offerToolUpgradeRecipes(VAItems.SCULK_DIAMOND_TOOL_SET, VAItems.SCULK_NETHERITE_TOOL_SET);

            generateColorfulBlockSetRecipes(VACollections.CHARTREUSE, VAItems.CHARTREUSE_DYE);
            generateColorfulBlockSetRecipes(VACollections.MAROON, VAItems.MAROON_DYE);
            generateColorfulBlockSetRecipes(VACollections.INDIGO, VAItems.INDIGO_DYE);
            generateColorfulBlockSetRecipes(VACollections.PLUM, VAItems.PLUM_DYE);
            generateColorfulBlockSetRecipes(VACollections.VIRIDIAN, VAItems.VIRIDIAN_DYE);
            generateColorfulBlockSetRecipes(VACollections.TAN, VAItems.TAN_DYE);
            generateColorfulBlockSetRecipes(VACollections.SINOPIA, VAItems.SINOPIA_DYE);
            generateColorfulBlockSetRecipes(VACollections.LILAC, VAItems.LILAC_DYE);

            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.CHARTREUSE_DYE), VAItems.CHARTREUSE_BUNDLE).criterion("has_bundle", this.conditionsFromItem(Items.BUNDLE)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.MAROON_DYE), VAItems.MAROON_BUNDLE).criterion("has_bundle", this.conditionsFromItem(Items.BUNDLE)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.INDIGO_DYE), VAItems.INDIGO_BUNDLE).criterion("has_bundle", this.conditionsFromItem(Items.BUNDLE)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.PLUM_DYE), VAItems.PLUM_BUNDLE).criterion("has_bundle", this.conditionsFromItem(Items.BUNDLE)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.VIRIDIAN_DYE), VAItems.VIRIDIAN_BUNDLE).criterion("has_bundle", this.conditionsFromItem(Items.BUNDLE)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.TAN_DYE), VAItems.TAN_BUNDLE).criterion("has_bundle", this.conditionsFromItem(Items.BUNDLE)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.SINOPIA_DYE), VAItems.SINOPIA_BUNDLE).criterion("has_bundle", this.conditionsFromItem(Items.BUNDLE)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.LILAC_DYE), VAItems.LILAC_BUNDLE).criterion("has_bundle", this.conditionsFromItem(Items.BUNDLE)).offerTo(exporter);

            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.CHARTREUSE_DYE), VAItems.CHARTREUSE_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.MAROON_DYE), VAItems.MAROON_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.INDIGO_DYE), VAItems.INDIGO_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.PLUM_DYE), VAItems.PLUM_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.VIRIDIAN_DYE), VAItems.VIRIDIAN_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.TAN_DYE), VAItems.TAN_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.SINOPIA_DYE), VAItems.SINOPIA_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.LILAC_DYE), VAItems.LILAC_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);

            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), WHITE_COST, Items.WHITE_BUNDLE, 0).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), LIGHT_GRAY_COST, Items.LIGHT_GRAY_BUNDLE, 1).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), GRAY_COST, Items.GRAY_BUNDLE, 2).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), BLACK_COST, Items.BLACK_BUNDLE, 3).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), TAN_COST, VAItems.TAN_BUNDLE, 4).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), BROWN_COST, Items.BROWN_BUNDLE, 5).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), MAROON_COST, VAItems.MAROON_BUNDLE, 6).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), RED_COST, Items.RED_BUNDLE, 7).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), SINOPIA_COST, VAItems.SINOPIA_BUNDLE, 8).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), ORANGE_COST, Items.ORANGE_BUNDLE, 9).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), YELLOW_COST, Items.YELLOW_BUNDLE, 10).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), CHARTREUSE_COST, VAItems.CHARTREUSE_BUNDLE, 11).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), LIME_COST, Items.LIME_BUNDLE, 12).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), GREEN_COST, Items.GREEN_BUNDLE, 13).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), VIRIDIAN_COST, VAItems.VIRIDIAN_BUNDLE, 14).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), CYAN_COST, Items.CYAN_BUNDLE, 15).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), LIGHT_BLUE_COST, Items.LIGHT_BLUE_BUNDLE, 16).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), BLUE_COST, Items.BLUE_BUNDLE, 17).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), INDIGO_COST, VAItems.INDIGO_BUNDLE, 18).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), PURPLE_COST, Items.PURPLE_BUNDLE, 19).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), PLUM_COST, VAItems.PLUM_BUNDLE, 20).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), MAGENTA_COST, Items.MAGENTA_BUNDLE, 21).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), PINK_COST, Items.PINK_BUNDLE, 22).offerTo(exporter);
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), LILAC_COST, VAItems.LILAC_BUNDLE, 23).offerTo(exporter);

            offerColoringStationRecipes(
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

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.MAROON_DYE, 2)
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.BLACK_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.RED_DYE)).offerTo(this.exporter);


            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.CHARTREUSE_DYE, 2).group("chartreuse_dye")
                    .input(Ingredient.ofItems(Items.LIME_DYE), 1)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.LIME_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.CHARTREUSE_DYE, 3).group("chartreuse_dye")
                    .input(Ingredient.ofItems(Items.GREEN_DYE), 1)
                    .input(Ingredient.ofItems(Items.WHITE_DYE), 1)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.YELLOW_DYE)).offerTo(this.exporter, idOf("chartreuse_dye_from_green_white_yellow_dye"));


            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.INDIGO_DYE, 2).group("indigo_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .input(Ingredient.ofItems(Items.PURPLE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.PURPLE_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.INDIGO_DYE, 3).group("indigo_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 2)
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.BLUE_DYE)).offerTo(this.exporter, idOf("indigo_dye_from_blue_blue_red_dye"));


            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.PLUM_DYE, 2).group("plum_dye")
                    .input(Ingredient.ofItems(Items.PURPLE_DYE), 1)
                    .input(Ingredient.ofItems(VAItems.MAROON_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.PURPLE_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.PLUM_DYE, 3).group("plum_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(VAItems.MAROON_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(VAItems.MAROON_DYE)).offerTo(this.exporter, idOf("plum_from_blue_red_maroon_dye"));

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.PLUM_DYE, 4).group("plum_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .input(Ingredient.ofItems(Items.RED_DYE), 2)
                    .input(Ingredient.ofItems(Items.BLACK_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.RED_DYE)).offerTo(this.exporter, idOf("plum_from_blue_red_red_black_dye"));


            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.VIRIDIAN_DYE, 2).group("viridian_dye")
                    .input(Ingredient.ofItems(Items.GREEN_DYE), 1)
                    .input(Ingredient.ofItems(Items.CYAN_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.CYAN_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.VIRIDIAN_DYE, 3).group("viridian_dye")
                    .input(Ingredient.ofItems(Items.GREEN_DYE), 2)
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.GREEN_DYE)).offerTo(this.exporter, idOf("viridian_from_green_green_blue_dye"));


            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.TAN_DYE, 2).group("tan_dye")
                    .input(Ingredient.ofItems(Items.ORANGE_DYE), 1)
                    .input(Ingredient.ofItems(Items.GRAY_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.ORANGE_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.TAN_DYE, 3).group("tan_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .input(Ingredient.ofItems(Items.GRAY_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.GRAY_DYE)).offerTo(this.exporter, idOf("tan_from_red_yellow_gray_dye"));

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.TAN_DYE, 4).group("tan_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .input(Ingredient.ofItems(Items.WHITE_DYE), 1)
                    .input(Ingredient.ofItems(Items.BLACK_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.RED_DYE)).offerTo(this.exporter, idOf("tan_from_red_yellow_black_white_dye"));


            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.SINOPIA_DYE, 2).group("sinopia_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.ORANGE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.ORANGE_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.SINOPIA_DYE, 3).group("sinopia_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 2)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.RED_DYE)).offerTo(this.exporter, idOf("sinopia_from_red_red_yellow_dye"));


            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.LILAC_DYE, 2).group("lilac_dye")
                    .input(Ingredient.ofItems(Items.WHITE_DYE), 1)
                    .input(Ingredient.ofItems(Items.PURPLE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.PURPLE_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.LILAC_DYE, 3).group("lilac_dye")
                    .input(Ingredient.ofItems(Items.WHITE_DYE), 1)
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.WHITE_DYE)).offerTo(this.exporter, idOf("lilac_from_white_red_blue_dye"));

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.FOOD, VAItems.SWEET_BERRY_PIE, 1)
                    .input(Items.SWEET_BERRIES, 3)
                    .input(Items.SUGAR)
                    .input(Items.EGG).criterion("has_sweet_berries", conditionsFromItem(Items.SWEET_BERRIES)).offerTo(this.exporter);



            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.TOOLS, VAItems.CLIMBING_ROPE, 4)
                    .pattern("#  ")
                    .pattern(" #s")
                    .pattern(" ss")
                    .input('#', Items.COPPER_INGOT).input('s', Items.STRING)
                    .criterion("copper_ingot", conditionsFromItem(Items.COPPER_INGOT)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, VABlocks.SILKBULB, 1)
                    .pattern("###")
                    .pattern("#b#")
                    .pattern("###")
                    .input('#', VAItems.SILK_THREAD).input('b', VAItems.ACID_BLOCK)
                    .criterion("acid_block", conditionsFromItem(VAItems.ACID_BLOCK)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.REDSTONE, VABlocks.ENTANGLEMENT_DRIVE, 1)
                    .pattern("s#s")
                    .pattern("sNs")
                    .pattern("s#s")
                    .input('#', VAItems.IOLITE).input('s', VAItems.STEEL_INGOT).input('N', Items.NETHER_STAR)
                    .criterion("iolite", conditionsFromItem(VAItems.IOLITE)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.TRANSPORTATION, VABlocks.WARP_TETHER, 1)
                    .pattern("s#s")
                    .pattern("epe")
                    .input('#', VAItems.IOLITE).input('s', VAItems.STEEL_INGOT).input('e', Blocks.END_STONE).input('p', Items.ENDER_PEARL)
                    .criterion("iolite", conditionsFromItem(VAItems.IOLITE)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.TRANSPORTATION, VABlocks.WARP_ANCHOR, 1)
                    .pattern("s#s")
                    .pattern("eye")
                    .input('#', VAItems.IOLITE).input('s', VAItems.STEEL_INGOT).input('e', Blocks.END_STONE).input('y', Items.ENDER_EYE)
                    .criterion("iolite", conditionsFromItem(VAItems.IOLITE)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_BOMB, 8)
                    .pattern(" s ")
                    .pattern("gag")
                    .pattern(" # ")
                    .input('#', VAItems.STEEL_INGOT).input('s', Items.STRING).input('g', Items.GUNPOWDER).input('a', Items.SAND)
                    .criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.REDSTONE, VAItems.REDSTONE_BRIDGE, 3)
                    .pattern("#")
                    .pattern("r")
                    .pattern("#")
                    .input('#', VAItems.STEEL_INGOT).input('r', Items.REDSTONE)
                    .criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.CAGELIGHT, 3)
                    .pattern("#")
                    .pattern("g")
                    .pattern("#")
                    .input('#', VAItems.STEEL_INGOT).input('g', Items.GLOWSTONE_DUST)
                    .criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.COLORING_STATION, 1)
                    .pattern("BB")
                    .pattern("##")
                    .pattern("##")
                    .input('#', Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.PLANKS))).input('B', Ingredient.fromTag(this.registryLookup.getOrThrow(ItemTags.WOOL)))
                    .criterion("wool", conditionsFromTag(ItemTags.WOOL)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.REDSTONE, VAItems.SPOTLIGHT, 1)
                    .pattern("ssa")
                    .pattern("rga")
                    .pattern("ssa")
                    .input('a', Items.AMETHYST_SHARD).input('g', Items.GLOWSTONE).input('s', VAItems.STEEL_INGOT).input('r', Items.REDSTONE)
                    .criterion("glowstone", conditionsFromItem(Items.GLOWSTONE)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.FOOD, VAItems.ICE_CREAM, 1)
                    .pattern(" s ")
                    .pattern("bmb")
                    .pattern(" w ")
                    .input('s', VAItems.ROCK_SALT).input('b', Items.SNOWBALL).input('m', Items.MILK_BUCKET).input('w', Items.BOWL)
                    .criterion("milk", conditionsFromItem(Items.MILK_BUCKET)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.ENGRAVING_CHISEL, 1)
                    .pattern("c")
                    .pattern("s")
                    .pattern("c")
                    .input('c', ItemTags.STONE_CRAFTING_MATERIALS)
                    .input('s', VAItems.STEEL_INGOT)
                    .criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, VAItems.FLOATROCK, 9)
                    .pattern("ccc")
                    .pattern("cfc")
                    .pattern("ccc")
                    .input('c', Items.COBBLESTONE)
                    .input('f', VAItems.FLOATROCK)
                    .criterion("floatrock", conditionsFromItem(VAItems.FLOATROCK)).offerTo(this.exporter);


            offerSmithingTrimRecipe(VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE, idOf("exoskeleton_armor_trim_smithing_template_smithing_trim"));
            offerSmithingTemplateCopyingRecipe(VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE, VAItems.SILK_BLOCK);

            offerSmelting(List.of(VAItems.FLOATROCK_COAL_ORE), RecipeCategory.MISC, Items.COAL, 0.1F, 200, "coal");
            offerSmelting(List.of(VAItems.FLOATROCK_COPPER_ORE), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7F, 200, "copper_ingot");
            offerSmelting(List.of(VAItems.FLOATROCK_IRON_ORE), RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 200, "iron_ingot");
            offerSmelting(List.of(VAItems.FLOATROCK_GOLD_ORE), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 200, "gold_ingot");
            offerSmelting(List.of(VAItems.FLOATROCK_REDSTONE_ORE), RecipeCategory.MISC, Items.REDSTONE, 0.7F, 200, "redstone");
            offerSmelting(List.of(VAItems.FLOATROCK_EMERALD_ORE), RecipeCategory.MISC, Items.EMERALD, 1.0F, 200, "emerald");
            offerSmelting(List.of(VAItems.FLOATROCK_LAPIS_ORE), RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2F, 200, "lapis_lazuli");
            offerSmelting(List.of(VAItems.FLOATROCK_DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 200, "diamond");

            offerBlasting(List.of(VAItems.FLOATROCK_COAL_ORE), RecipeCategory.MISC, Items.COAL, 0.1F, 100, "coal");
            offerBlasting(List.of(VAItems.FLOATROCK_COPPER_ORE), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7F, 100, "copper_ingot");
            offerBlasting(List.of(VAItems.FLOATROCK_IRON_ORE), RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 100, "iron_ingot");
            offerBlasting(List.of(VAItems.FLOATROCK_GOLD_ORE), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 100, "gold_ingot");
            offerBlasting(List.of(VAItems.FLOATROCK_REDSTONE_ORE), RecipeCategory.MISC, Items.REDSTONE, 0.7F, 100, "redstone");
            offerBlasting(List.of(VAItems.FLOATROCK_EMERALD_ORE), RecipeCategory.MISC, Items.EMERALD, 1.0F, 100, "emerald");
            offerBlasting(List.of(VAItems.FLOATROCK_LAPIS_ORE), RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2F, 100, "lapis_lazuli");
            offerBlasting(List.of(VAItems.FLOATROCK_DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 100, "diamond");

            offerHedgeRecipe(VABlocks.AEROBLOOM_HEDGE, VABlocks.AEROBLOOM_LEAVES);

            offerStonecuttingRecipes(VABlocks.FLOATROCK, VACollections.POLISHED_FLOATROCK, VACollections.FLOATROCK_BRICKS);
            generateCuttableFamilyChain(VACollections.FLOATROCK);
            generateCuttableFamilyChain(VACollections.POLISHED_FLOATROCK);
            generateCuttableFamilyChain(VACollections.FLOATROCK_BRICKS);
            offer2x2ConversionChain(VABlocks.FLOATROCK, VABlocks.POLISHED_FLOATROCK, VABlocks.FLOATROCK_BRICKS);

            generateFamily(VACollections.AEROBLOOM, FeatureFlags.VANILLA_FEATURES);
            offerBarkBlockRecipe(VAItems.AEROBLOOM_WOOD, VAItems.AEROBLOOM_LOG);
            offerBarkBlockRecipe(VAItems.STRIPPED_AEROBLOOM_WOOD, VAItems.STRIPPED_AEROBLOOM_LOG);
            offerPlanksRecipe2(VAItems.AEROBLOOM_PLANKS, VAItemTags.AEROBLOOM_LOGS, 4);
            offerHangingSignRecipe(VAItems.AEROBLOOM_HANGING_SIGN, VAItems.STRIPPED_AEROBLOOM_LOG);

            offerWaxingRecipes(exporter);
        }
    }

    private static class PreviewGenerator extends Generator {

        protected PreviewGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
            super(registryLookup, exporter);
        }

        @Override
        public void generate() {

        }
    }
    private abstract static class Generator extends RecipeGenerator {
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

        protected final RegistryEntryLookup<Item> registryLookup;
        
        protected Generator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
            super(registryLookup, exporter);
            this.registryLookup = registries.getOrThrow(RegistryKeys.ITEM);
        }

        protected void offerCookingRecipes(ItemConvertible output, ItemConvertible input, float experience, String group) {
            offerSmelting(List.of(input), RecipeCategory.FOOD, output, experience, 200, group);
            offerFoodCookingRecipe("smoking", RecipeSerializer.SMOKING, SmokingRecipe::new, 100, input, output, experience);
            offerFoodCookingRecipe("campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 600, input, output, experience);
        }

        protected void offerColoringRecipe(ItemConvertible input, ItemConvertible output, ColoringStationBlockEntity.DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(Ingredient.ofItems(input), cost, output, index).offerTo(this.exporter);
        }

        protected void offerColoringRecipe(ItemConvertible output, ColoringStationBlockEntity.DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(null, cost, output, index).offerTo(this.exporter);
        }

        protected void offerColoringRecipe(TagKey<Item> input, ItemConvertible output, ColoringStationBlockEntity.DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(Ingredient.fromTag(this.registryLookup.getOrThrow(input)), cost, output, index).offerTo(this.exporter);
        }

        protected void offerArmorColoringRecipe(DyeItem input, int i) {
            ArmorColoringRecipeJsonBuilder.create(Ingredient.ofItems(input), i).offerTo(this.exporter);
        }

        protected void offer2x2ConversionChain(Block... blocks) {
            for (int i = 0; i < blocks.length - 1; i++) {
                Block input = blocks[i];
                Block output = blocks[i + 1];
                String id = Registries.BLOCK.getId(output).getPath() + "_from_compacting_" + Registries.BLOCK.getId(input).getPath();
                ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("##")
                        .pattern("##")
                        .input('#', input).criterion(hasItem(input), conditionsFromItem(input)).offerTo(this.exporter, id);
            }
        }

        protected void offerColoringRecipes(ColorfulBlockSet set, ColoringStationBlockEntity.DyeContents cost, int index) {
            offerColoringRecipe(set.dye(), cost.copyAndMultiply(8), index);
            offerArmorColoringRecipe(set.dye(), index);
            set.ifWool(block -> offerColoringRecipe(ItemTags.WOOL, block, cost, index));
            set.ifCarpet(block -> offerColoringRecipe(ItemTags.WOOL_CARPETS, block, cost, index));
            set.ifStainedGlass(block -> offerColoringRecipe(VAItemTags.COLORABLE_GLASS, block, cost, index));
            set.ifStainedGlassPane(block -> offerColoringRecipe(VAItemTags.COLORABLE_GLASS_PANE, block, cost, index));
            set.ifTerracotta(block -> offerColoringRecipe(ItemTags.TERRACOTTA, block, cost, index));
            set.ifCandle(block -> offerColoringRecipe(ItemTags.CANDLES, block, cost, index));
            set.ifSilkbulb(block -> offerColoringRecipe(VAItemTags.SILKBULBS, block, cost, index));
            set.ifBed(block -> offerColoringRecipe(ItemTags.BEDS, block, cost, index));
            set.ifShulkerBox(block -> offerColoringRecipe(Items.SHULKER_BOX, block, cost, index));
        }

        protected void offerColoringStationRecipes(ColorfulBlockSet... sets) {
            int i = 0;
            for (ColorfulBlockSet set : sets) {
                offerColoringRecipes(set, VADyeColors.getContents(set.dye(), 1), i);
                i += 1;
            }
        }

        protected void generateCuttableFamilyChain(BlockFamily baseFamily, BlockFamily... subFamilies) {
            generateFamily(baseFamily, FeatureFlags.VANILLA_FEATURES);
            offerStonecuttingRecipes(baseFamily, baseFamily);
            for (BlockFamily subFamily : subFamilies) {
                offerStonecuttingRecipes(baseFamily, subFamily);
            }
        }

        protected void generateColorfulBlockSetRecipes(ColorfulBlockSet set, Item dye) {
            set.ifWool(wool -> {
                ShapelessRecipeJsonBuilder.create(this.registryLookup,RecipeCategory.BUILDING_BLOCKS, wool).input(ItemTags.WOOL).input(dye).criterion("has_dye", conditionsFromItem(dye)).offerTo(this.exporter);
                set.ifBed( bed -> offerBedRecipe(bed, wool));
                set.ifBanner(banner -> offerBannerRecipe(banner, wool));
                set.ifCarpet(carpet -> offerCarpetRecipe(carpet, wool));
            });
            set.ifCarpet(block -> ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, block).input(ItemTags.WOOL_CARPETS).input(dye).criterion("has_dye", conditionsFromItem(dye)).offerTo(this.exporter, CraftingRecipeJsonBuilder.getItemId(block).withSuffixedPath("_dyeing")));
            set.ifConcretePowder( block -> offerConcretePowderDyeingRecipe(block, dye));
            set.ifTerracotta(block -> offerTerracottaDyeingRecipe(block, dye));
            set.ifStainedGlass(block -> {
                offerStainedGlassDyeingRecipe(block, dye);
                set.ifStainedGlassPane(pane -> {
                    offerStainedGlassPaneRecipe(pane, block);
                    offerStainedGlassPaneDyeingRecipe(pane, dye);
                });
            });
            set.ifCandle(block -> offerCandleDyeingRecipe(block, dye));
            set.ifSilkbulb(block -> ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, block).input(VAItemTags.SILKBULBS).input(dye).criterion("has_dye", conditionsFromItem(dye)).offerTo(this.exporter));
        }

        protected void offerStonecuttingRecipes(BlockFamily baseFamily, BlockFamily... resultFamilies) {
            Block block;
            for (BlockFamily resultFamily : resultFamilies) {
                if (!baseFamily.getBaseBlock().equals(resultFamily.getBaseBlock()))
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, resultFamily.getBaseBlock(), baseFamily.getBaseBlock());
                if ((block = resultFamily.getVariant(BlockFamily.Variant.STAIRS)) != null)
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock());
                if ((block = resultFamily.getVariant(BlockFamily.Variant.SLAB)) != null)
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock(), 2);
                if ((block = resultFamily.getVariant(BlockFamily.Variant.WALL)) != null)
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock());
                if ((block = resultFamily.getVariant(BlockFamily.Variant.CHISELED)) != null)
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock());
            }
        }

        protected void offerStonecuttingRecipes(Block baseBlock, BlockFamily... resultFamilies) {
            Block block;
            for (BlockFamily resultFamily : resultFamilies) {
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, resultFamily.getBaseBlock(), baseBlock);
                if ((block = resultFamily.getVariant(BlockFamily.Variant.STAIRS)) != null)
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, block, baseBlock);
                if ((block = resultFamily.getVariant(BlockFamily.Variant.SLAB)) != null)
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, block, baseBlock, 2);
                if ((block = resultFamily.getVariant(BlockFamily.Variant.WALL)) != null)
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, block, baseBlock);
                if ((block = resultFamily.getVariant(BlockFamily.Variant.CHISELED)) != null)
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, block, baseBlock);
            }
        }

        protected void offerToolGildRecipe(GildedToolItem result, Item addition) {
            SmithingTransformRecipeJsonBuilder.create(
                    Ingredient.ofItems(VAItems.TOOL_GILD_SMITHING_TEMPLATE),
                    Ingredient.ofItems(result.getBaseItem()),
                    Ingredient.ofItems(addition),
                    RecipeCategory.TOOLS,
                    result.asItem()
            ).criterion("has_smithing_template", conditionsFromItem(VAItems.TOOL_GILD_SMITHING_TEMPLATE)).offerTo(this.exporter, idOf("smithing/" + getItemPath(result)));
        }

        protected void offerToolGildRecipes(Item addition, RegistryHelper.ItemRegistryHelper.ToolSet... sets) {
            for (RegistryHelper.ItemRegistryHelper.ToolSet set : sets) {
                for (Item item : set.getItems())
                    if (item instanceof GildedToolItem gildedToolItem)
                        offerToolGildRecipe(gildedToolItem, addition);
            }
        }

        protected void offerToolUpgradeRecipes(RegistryHelper.ItemRegistryHelper.ToolSet input, RegistryHelper.ItemRegistryHelper.ToolSet output) {
            offerNetheriteUpgradeRecipe(input.AXE(), output.AXE());
            offerNetheriteUpgradeRecipe(input.HOE(), output.HOE());
            offerNetheriteUpgradeRecipe(input.PICKAXE(), output.PICKAXE());
            offerNetheriteUpgradeRecipe(input.SHOVEL(), output.SHOVEL());
            offerNetheriteUpgradeRecipe(input.SWORD(), output.SWORD());
        }

        protected void offerNetheriteUpgradeRecipe(Item input, Item result) {
            SmithingTransformRecipeJsonBuilder.create(
                            Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                            Ingredient.ofItems(input),
                            Ingredient.ofItems(Items.NETHERITE_INGOT),
                            RecipeCategory.TOOLS,
                            result)
                    .criterion("has_netherite_ingot", conditionsFromItem(Items.NETHERITE_INGOT))
                    .offerTo(this.exporter, idOf("smithing/" + getItemPath(result) + "_upgrade"));
        }

        @SafeVarargs
        protected final void offerShapelessRecipe(RecipeCategory category, ItemConvertible output, int count, Pair<ItemConvertible, Integer>... input) {
            ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(this.registryLookup, category, output, count);
            builder.criterion(hasItem(input[0].getLeft()), conditionsFromItem(input[0].getLeft()));
            for (Pair<ItemConvertible, Integer> itemProvider : input) {
                builder.input(itemProvider.getLeft(), itemProvider.getRight());
            }
            builder.offerTo(this.exporter);
        }

        protected void offer2x2FullRecipe(RecipeCategory category, ItemConvertible output, ItemConvertible input, int count) {
            ShapedRecipeJsonBuilder.create(this.registryLookup, category, output, count).input('#', input).pattern("##").pattern("##").criterion(hasItem(input), conditionsFromItem(input)).offerTo(this.exporter);
        }

        protected void offer2x2FullRecipe(RecipeCategory category, ItemConvertible output, ItemConvertible input, int count, String group) {
            ShapedRecipeJsonBuilder.create(this.registryLookup, category, output, count).input('#', input).pattern("##").pattern("##").group(group).criterion(hasItem(input), conditionsFromItem(input)).offerTo(this.exporter);
        }

        protected void offerHedgeRecipe(ItemConvertible output, ItemConvertible input) {
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, output, 6)
                    .pattern("###")
                    .pattern("###")
                    .input('#', input).criterion("has_leaves", conditionsFromItem(input)).group("hedges").offerTo(this.exporter);
        }

        public void offerWaxingRecipes(RecipeExporter exporter) {
            HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get().forEach((unwaxed, waxed) -> {
                if (!VirtualAdditions.isFromMod(Registries.BLOCK.getId(unwaxed))) return;
                ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, waxed).input(unwaxed).input(Items.HONEYCOMB).group(RecipeGenerator.getItemPath(waxed)).criterion(RecipeGenerator.hasItem(unwaxed), this.conditionsFromItem(unwaxed)).offerTo(this.exporter, RecipeGenerator.convertBetween(waxed, Items.HONEYCOMB));
            });
        }

        public void offerSteelRecipeSet(Block block, Block cut, Block cutStairs, Block cutSlab, Block grate, Block chiseled){
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, cutStairs, cut);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, cutSlab, cut, 2);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, grate, cut, 1);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, chiseled, cut, 1);
            ShapedRecipeJsonBuilder.create(this.registryLookup,RecipeCategory.DECORATIONS, cutStairs, 4).input('#', cut).pattern("#  ").pattern("## ").pattern("###").criterion("has_item", conditionsFromItem(cut)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup,RecipeCategory.DECORATIONS, cutSlab, 6).input('#', cut).pattern("###").criterion("has_item", conditionsFromItem(cut)).offerTo(this.exporter);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, cut, block, 36);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, grate, block, 36);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, chiseled, block, 36);
            ShapedRecipeJsonBuilder.create(this.registryLookup,RecipeCategory.DECORATIONS, chiseled, 1).input('#', cutSlab).pattern("#").pattern("#").criterion("has_item", conditionsFromItem(cutSlab)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup,RecipeCategory.DECORATIONS, grate, 4).pattern(" # ").pattern("# #").pattern(" # ").input('#', cut).criterion("has_item", conditionsFromItem(cut)).offerTo(this.exporter);
        }
    }


}
