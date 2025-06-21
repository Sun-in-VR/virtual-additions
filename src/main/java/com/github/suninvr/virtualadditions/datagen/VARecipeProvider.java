package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.DyeContents;
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
import net.minecraft.data.recipe.*;
import net.minecraft.item.*;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

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
        }

        @Override
        public void generate() {
            offerBlasting(List.of(VAItems.RAW_STEEL), RecipeCategory.MISC, VAItems.STEEL_INGOT, 1.0F, 100, "steel_ingot");
            offerSmelting(List.of(VAItems.RAW_STEEL), RecipeCategory.MISC, VAItems.STEEL_INGOT, 1.0F, 200, "steel_ingot");
            offerBlasting(List.of(VAItems.IOLITE_ORE), RecipeCategory.MISC, VAItems.IOLITE, 1.0F, 100, "iolite");
            offerSmelting(List.of(VAItems.IOLITE_ORE), RecipeCategory.MISC, VAItems.IOLITE, 1.0F, 200, "iolite");
            offerBlasting(List.of(VAItems.ROCK_SALT_ORE), RecipeCategory.MISC, VAItems.ROCK_SALT, 1.0F, 100, "rock_salt");
            offerSmelting(List.of(VAItems.ROCK_SALT_ORE), RecipeCategory.MISC, VAItems.ROCK_SALT, 1.0F, 200, "rock_salt");
            offerBlasting(List.of(VAItems.DEEPSLATE_ROCK_SALT_ORE), RecipeCategory.MISC, VAItems.ROCK_SALT, 1.0F, 100, "rock_salt");
            offerSmelting(List.of(VAItems.DEEPSLATE_ROCK_SALT_ORE), RecipeCategory.MISC, VAItems.ROCK_SALT, 1.0F, 200, "rock_salt");

            offerHedgeRecipe(VABlocks.OAK_HEDGE, Blocks.OAK_LEAVES);
            offerHedgeRecipe(VABlocks.SPRUCE_HEDGE, Blocks.SPRUCE_LEAVES);
            offerHedgeRecipe(VABlocks.BIRCH_HEDGE, Blocks.BIRCH_LEAVES);
            offerHedgeRecipe(VABlocks.JUNGLE_HEDGE, Blocks.JUNGLE_LEAVES);
            offerHedgeRecipe(VABlocks.ACACIA_HEDGE, Blocks.ACACIA_LEAVES);
            offerHedgeRecipe(VABlocks.DARK_OAK_HEDGE, Blocks.DARK_OAK_LEAVES);
            offerHedgeRecipe(VABlocks.PALE_OAK_HEDGE, Blocks.PALE_OAK_LEAVES);
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

            offerStonecuttingRecipes(VABlocks.PORPHYRY, VACollections.POLISHED_PORPHYRY, VACollections.PORPHYRY_BRICKS);
            generateCuttableFamilyChain(VACollections.PORPHYRY);
            generateCuttableFamilyChain(VACollections.POLISHED_PORPHYRY);
            generateCuttableFamilyChain(VACollections.PORPHYRY_BRICKS);
            offer2x2ConversionChain(VABlocks.PORPHYRY, VABlocks.POLISHED_PORPHYRY, VABlocks.PORPHYRY_BRICKS);

            offer2x2FullRecipe(RecipeCategory.MISC, Items.STRING, VAItems.COTTON, 2);
            offerShapelessRecipe(RecipeCategory.MISC, VAItems.COTTON_SEEDS, 1, Pair.of(VAItems.COTTON, 1));
            offerShapelessRecipe(RecipeCategory.FOOD, VAItems.CORN_SEEDS, 1, Pair.of(VAItems.CORN, 1));
            offerShapelessRecipe(RecipeCategory.FOOD, VAItems.TOMATO_SEEDS, 1, Pair.of(VAItems.TOMATO, 1));
            offerShapelessRecipe(RecipeCategory.FOOD, VAItems.CABBAGE_SEEDS, 1, Pair.of(VAItems.CABBAGE, 1));

            offerCookingRecipes(VAItems.FRIED_EGG, List.of(Items.EGG, Items.BROWN_EGG, Items.BLUE_EGG), 0.35F, "fried_egg");
            offerCookingRecipes(VAItems.ROASTED_CORN, VAItems.CORN, 0.35F, "corn");

            offerJerkyFoodRecipe(Items.COOKED_BEEF, VAItems.BEEF_JERKY);
            offerJerkyFoodRecipe(Items.COOKED_PORKCHOP, VAItems.PORK_JERKY);
            offerJerkyFoodRecipe(Items.COOKED_CHICKEN, VAItems.CHICKEN_JERKY);
            offerJerkyFoodRecipe(Items.COOKED_MUTTON, VAItems.MUTTON_JERKY);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.FOOD, VAItems.CHEESE_WEDGE, 4)
                            .pattern(" # ")
                            .pattern("#M#")
                            .pattern(" # ")
                            .input('#', VAItems.ROCK_SALT).input('M', Items.MILK_BUCKET)
                                    .criterion("has_item", conditionsFromItem(Items.MILK_BUCKET)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.FOOD, Items.LEATHER)
                            .pattern("###")
                            .pattern("#F#")
                            .pattern("###")
                            .input('#', VAItems.ROCK_SALT).input('F', Items.ROTTEN_FLESH)
                                    .criterion("has_item", conditionsFromItem(Items.ROTTEN_FLESH)).offerTo(this.exporter);

            offerCompactingRecipe(RecipeCategory.BUILDING_BLOCKS, VAItems.ROCK_SALT_BLOCK, VAItems.ROCK_SALT);
            offerShapelessRecipe(VAItems.ROCK_SALT, VAItems.ROCK_SALT_BLOCK, "rock_salt", 9);

            offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.WEBBED_SILK, VAItems.SILK_THREAD);
            offerCompactingRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.SILK_BLOCK, VAItems.SILK_THREAD);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.TOOLS, VAItems.STEEL_AXE).input('#', Items.STICK).input('X', VAItems.STEEL_INGOT).pattern("XX").pattern("X#").pattern(" #").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_BOOTS).input('X', VAItems.STEEL_INGOT).pattern("X X").pattern("X X").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_CHESTPLATE).input('X', VAItems.STEEL_INGOT).pattern("X X").pattern("XXX").pattern("XXX").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_HELMET).input('X', VAItems.STEEL_INGOT).pattern("XXX").pattern("X X").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_HORSE_ARMOR).input('X', VAItems.STEEL_INGOT).pattern("X X").pattern("XXX").pattern("X X").criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);
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

            completeDyablesRecipes(beds, vanillaBeds, virtualAdditionsBeds, "bed", RecipeCategory.BUILDING_BLOCKS);
            completeDyablesRecipes(wool, vanillaWool, virtualAdditionsWool, "wool", RecipeCategory.BUILDING_BLOCKS);
            completeDyablesRecipes(carpets, vanillaCarpets, virtualAdditionsCarpets, "carpet", RecipeCategory.BUILDING_BLOCKS);
            completeDyablesRecipes(harnesses, vanillaHarnesses, virtualAdditionsHarnesses, "harness", RecipeCategory.BUILDING_BLOCKS);
            offerDyeablesRecipes(dyes, silkbulbs, VAItems.SILKBULB, "silkbulb", RecipeCategory.BUILDING_BLOCKS);

            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.CHARTREUSE_DYE), VAItems.CHARTREUSE_BUNDLE).criterion(hasItem(VAItems.CHARTREUSE_DYE), this.conditionsFromItem(VAItems.CHARTREUSE_DYE)).group("bundle_dye").offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.MAROON_DYE), VAItems.MAROON_BUNDLE).criterion(hasItem(VAItems.MAROON_DYE), this.conditionsFromItem(VAItems.MAROON_DYE)).group("bundle_dye").offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.INDIGO_DYE), VAItems.INDIGO_BUNDLE).criterion(hasItem(VAItems.INDIGO_DYE), this.conditionsFromItem(VAItems.INDIGO_DYE)).group("bundle_dye").offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.PLUM_DYE), VAItems.PLUM_BUNDLE).criterion(hasItem(VAItems.PLUM_DYE), this.conditionsFromItem(VAItems.PLUM_DYE)).group("bundle_dye").offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.VIRIDIAN_DYE), VAItems.VIRIDIAN_BUNDLE).criterion(hasItem(VAItems.VIRIDIAN_DYE), this.conditionsFromItem(VAItems.VIRIDIAN_DYE)).group("bundle_dye").offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.TAN_DYE), VAItems.TAN_BUNDLE).criterion(hasItem(VAItems.TAN_DYE), this.conditionsFromItem(VAItems.TAN_DYE)).group("bundle_dye").offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.SINOPIA_DYE), VAItems.SINOPIA_BUNDLE).criterion(hasItem(VAItems.SINOPIA_DYE), this.conditionsFromItem(VAItems.SINOPIA_DYE)).group("bundle_dye").offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofTag(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.ofItems(VAItems.LILAC_DYE), VAItems.LILAC_BUNDLE).criterion(hasItem(VAItems.LILAC_DYE), this.conditionsFromItem(VAItems.LILAC_DYE)).group("bundle_dye").offerTo(exporter);

            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.CHARTREUSE_DYE), VAItems.CHARTREUSE_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.MAROON_DYE), VAItems.MAROON_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.INDIGO_DYE), VAItems.INDIGO_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.PLUM_DYE), VAItems.PLUM_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.VIRIDIAN_DYE), VAItems.VIRIDIAN_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.TAN_DYE), VAItems.TAN_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.SINOPIA_DYE), VAItems.SINOPIA_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);
            TransmuteRecipeJsonBuilder.create(RecipeCategory.TOOLS, Ingredient.ofItems(Items.SHULKER_BOX), Ingredient.ofItems(VAItems.LILAC_DYE), VAItems.LILAC_SHULKER_BOX).criterion("has_shulker_box", this.conditionsFromItem(Items.SHULKER_BOX)).offerTo(exporter);

            createColoringRecipeSet(ItemTags.BUNDLES, bundles);
            createColoringRecipeSet(ItemTags.HARNESSES, harnesses);

            for(int i = 0; i < virtualAdditionsHarnesses.size(); ++i) {
                this.offerHarness(virtualAdditionsHarnesses.get(i), virtualAdditionsWool.get(i));
            }

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
                    VACollections.MAGENTA,
                    VACollections.PLUM,
                    VACollections.PINK,
                    VACollections.LILAC
            );

            offerSmithingTrimRecipe(VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE, VAArmorTrimPatterns.EXOSKELETON, RegistryKey.of(RegistryKeys.RECIPE, idOf("exoskeleton_armor_trim_smithing_template_smithing_trim")));
            offerSmithingTemplateCopyingRecipe(VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE, VAItems.SILK_BLOCK);

            offerSmithingTrimRecipe(VAItems.ROBE_ARMOR_TRIM_SMITHING_TEMPLATE, VAArmorTrimPatterns.ROBE, RegistryKey.of(RegistryKeys.RECIPE, idOf("robe_armor_trim_smithing_template_smithing_trim")));
            offerSmithingTemplateCopyingRecipe(VAItems.ROBE_ARMOR_TRIM_SMITHING_TEMPLATE, VAItems.SPECTRAL_SAND);

            offerStonecuttingRecipes(VABlocks.ROCK_SALT_BLOCK, VACollections.ROCK_SALT_BRICKS);
            generateCuttableFamilyChain(VACollections.ROCK_SALT_BRICKS);
            offer2x2ConversionChain(VABlocks.ROCK_SALT_BLOCK, VABlocks.ROCK_SALT_BRICKS);
            offerChiseledBlockRecipe(RecipeCategory.BUILDING_BLOCKS, VAItems.CHISELED_ROCK_SALT_BRICKS, VAItems.ROCK_SALT_BRICK_SLAB);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, VAItems.CHISELED_ROCK_SALT_BRICKS, VAItems.ROCK_SALT_BRICKS);
            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, VAItems.CHISELED_ROCK_SALT_BRICKS, VAItems.ROCK_SALT_BLOCK);

            generateFamily(VACollections.SOULBLOOM, FeatureFlags.VANILLA_FEATURES);
            offerBarkBlockRecipe(VAItems.SOULBLOOM_WOOD, VAItems.SOULBLOOM_LOG);
            offerBarkBlockRecipe(VAItems.STRIPPED_SOULBLOOM_WOOD, VAItems.STRIPPED_SOULBLOOM_LOG);
            offerPlanksRecipe2(VAItems.SOULBLOOM_PLANKS, VAItemTags.SOULBLOOM_LOGS, 4);
            offerHangingSignRecipe(VAItems.SOULBLOOM_HANGING_SIGN, VAItems.STRIPPED_SOULBLOOM_LOG);
            offerHedgeRecipe(VABlocks.SOULBLOOM_HEDGE, VABlocks.SOULBLOOM_LEAVES);
            offerBoatRecipe(VAItems.SOULBLOOM_BOAT, VABlocks.SOULBLOOM_PLANKS);
            offerChestBoatRecipe(VAItems.SOULBLOOM_CHEST_BOAT, VAItems.SOULBLOOM_BOAT);
            
            generateFamily(VACollections.WITHERED, FeatureFlags.VANILLA_FEATURES);
            offerBarkBlockRecipe(VAItems.WITHERED_WOOD, VAItems.WITHERED_LOG);
            offerBarkBlockRecipe(VAItems.STRIPPED_WITHERED_WOOD, VAItems.STRIPPED_WITHERED_LOG);
            offerPlanksRecipe2(VAItems.WITHERED_PLANKS, VAItemTags.WITHERED_LOGS, 4);
            offerHangingSignRecipe(VAItems.WITHERED_HANGING_SIGN, VAItems.STRIPPED_WITHERED_LOG);
            offerHedgeRecipe(VABlocks.WITHERED_HEDGE, VABlocks.WITHERED_LEAVES);


            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.BONE_PILE, 4)
                    .pattern("bBb")
                    .input('B', Items.BONE_BLOCK).input('b', Items.BONE)
                    .criterion("has_bone_block", conditionsFromItem(Items.BONE_BLOCK)).offerTo(exporter);
            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.BONE_LITTER, 2)
                            .input(VAItems.BONE_PILE).criterion("has_bone_pile", conditionsFromItem(VAItems.BONE_PILE)).offerTo(exporter);
            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, Items.BONE_MEAL)
                            .input(VAItems.BONE_LITTER).criterion("has_bone_litter", conditionsFromItem(VAItems.BONE_PILE)).offerTo(exporter);

            offerCompactingRecipe(RecipeCategory.BUILDING_BLOCKS, VABlocks.SPECTRAL_SAND, VAItems.SPECTRAL_POWDER);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, VAItems.SPECTRAL_TORCH, 4)
                            .pattern("c")
                            .pattern("/")
                            .pattern("p")
                            .input('c', Items.COAL).input('/', Items.STICK).input('p', VAItems.SPECTRAL_POWDER)
                    .criterion("has_spectral_powder", conditionsFromItem(VAItems.SPECTRAL_POWDER)).offerTo(exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, VAItems.SPECTRAL_LANTERN)
                            .pattern("iii")
                            .pattern("iti")
                            .pattern("iii")
                            .input('i', Items.IRON_NUGGET).input('t', VAItems.SPECTRAL_TORCH)
                    .criterion("has_spectral_torch", conditionsFromItem(VAItems.SPECTRAL_TORCH)).offerTo(exporter);


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
                    .criterion("has_dye", conditionsFromItem(Items.YELLOW_DYE)).offerTo(this.exporter, idOf("chartreuse_dye_from_green_white_yellow_dye").toString());

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.INDIGO_DYE, 2).group("indigo_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .input(Ingredient.ofItems(Items.PURPLE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.PURPLE_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.INDIGO_DYE, 3).group("indigo_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 2)
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.BLUE_DYE)).offerTo(this.exporter, idOf("indigo_dye_from_blue_blue_red_dye").toString());

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.PLUM_DYE, 2).group("plum_dye")
                    .input(Ingredient.ofItems(Items.PURPLE_DYE), 1)
                    .input(Ingredient.ofItems(VAItems.MAROON_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.PURPLE_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.PLUM_DYE, 3).group("plum_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(VAItems.MAROON_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(VAItems.MAROON_DYE)).offerTo(this.exporter, idOf("plum_from_blue_red_maroon_dye").toString());

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.PLUM_DYE, 4).group("plum_dye")
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .input(Ingredient.ofItems(Items.RED_DYE), 2)
                    .input(Ingredient.ofItems(Items.BLACK_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.RED_DYE)).offerTo(this.exporter, idOf("plum_from_blue_red_red_black_dye").toString());

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.VIRIDIAN_DYE, 2).group("viridian_dye")
                    .input(Ingredient.ofItems(Items.GREEN_DYE), 1)
                    .input(Ingredient.ofItems(Items.CYAN_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.CYAN_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.VIRIDIAN_DYE, 3).group("viridian_dye")
                    .input(Ingredient.ofItems(Items.GREEN_DYE), 2)
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.GREEN_DYE)).offerTo(this.exporter, idOf("viridian_from_green_green_blue_dye").toString());

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.TAN_DYE, 2).group("tan_dye")
                    .input(Ingredient.ofItems(Items.ORANGE_DYE), 1)
                    .input(Ingredient.ofItems(Items.GRAY_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.ORANGE_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.TAN_DYE, 3).group("tan_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .input(Ingredient.ofItems(Items.GRAY_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.GRAY_DYE)).offerTo(this.exporter, idOf("tan_from_red_yellow_gray_dye").toString());

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.TAN_DYE, 4).group("tan_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .input(Ingredient.ofItems(Items.WHITE_DYE), 1)
                    .input(Ingredient.ofItems(Items.BLACK_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.RED_DYE)).offerTo(this.exporter, idOf("tan_from_red_yellow_black_white_dye").toString());

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.SINOPIA_DYE, 2).group("sinopia_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.ORANGE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.ORANGE_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.SINOPIA_DYE, 3).group("sinopia_dye")
                    .input(Ingredient.ofItems(Items.RED_DYE), 2)
                    .input(Ingredient.ofItems(Items.YELLOW_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.RED_DYE)).offerTo(this.exporter, idOf("sinopia_from_red_red_yellow_dye").toString());

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.LILAC_DYE, 2).group("lilac_dye")
                    .input(Ingredient.ofItems(Items.WHITE_DYE), 1)
                    .input(Ingredient.ofItems(Items.PURPLE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.PURPLE_DYE)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.LILAC_DYE, 3).group("lilac_dye")
                    .input(Ingredient.ofItems(Items.WHITE_DYE), 1)
                    .input(Ingredient.ofItems(Items.RED_DYE), 1)
                    .input(Ingredient.ofItems(Items.BLUE_DYE), 1)
                    .criterion("has_dye", conditionsFromItem(Items.WHITE_DYE)).offerTo(this.exporter, idOf("lilac_from_white_red_blue_dye").toString());

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.FOOD, VAItems.SWEET_BERRY_PIE, 1)
                    .input(Items.SWEET_BERRIES, 3)
                    .input(Items.SUGAR)
                    .input(Items.EGG).criterion("has_sweet_berries", conditionsFromItem(Items.SWEET_BERRIES)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.FOOD, Items.EXPERIENCE_BOTTLE, 3)
                    .input(Items.GLASS_BOTTLE, 3)
                    .input(VAItems.SOUL_SPROUT).criterion("has_soul_sprout", conditionsFromItem(VAItems.SOUL_SPROUT)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.TOOLS, VAItems.CLIMBING_ROPE, 4)
                    .pattern(" # ")
                    .pattern(" # ")
                    .pattern(" l ")
                    .input('#', Items.COPPER_INGOT).input('l', Items.LEAD)
                    .criterion("has_copper_ingot", conditionsFromItem(Items.COPPER_INGOT)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, VABlocks.SILKBULB, 1)
                    .pattern("###")
                    .pattern("#b#")
                    .pattern("###")
                    .input('#', VAItems.SILK_THREAD).input('b', VAItems.ACID_BLOCK)
                    .criterion("has_acid_block", conditionsFromItem(VAItems.ACID_BLOCK)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.REDSTONE, VABlocks.ENTANGLEMENT_DRIVE, 1)
                    .pattern("s#s")
                    .pattern("sNs")
                    .pattern("s#s")
                    .input('#', VAItems.IOLITE).input('s', VAItems.STEEL_INGOT).input('N', Items.NETHER_STAR)
                    .criterion("has_iolite", conditionsFromItem(VAItems.IOLITE)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.TRANSPORTATION, VAItems.PORTAL_CORE, 1)
                    .pattern("eee")
                    .pattern("eye")
                    .pattern("eee")
                    .input('e', VAItems.IOLITE).input('y', Items.ENDER_PEARL)
                    .criterion("has_iolite", conditionsFromItem(VAItems.IOLITE)).group("portal_core").offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.TRANSPORTATION, VAItems.PORTAL_CORE, 1)
                     .input(VAItems.DRAINED_PORTAL_CORE)
                     .input(VAItems.IOLITE, 2)
                     .criterion("has_drained_portal_core", conditionsFromItem(VAItems.DRAINED_PORTAL_CORE)).group("portal_core").offerTo(this.exporter, "portal_core_restoration");

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_BOMB, 4)
                    .pattern(" s ")
                    .pattern("#g#")
                    .input('#', VAItems.STEEL_INGOT).input('s', Items.STRING).input('g', Items.GUNPOWDER)
                    .criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.REDSTONE, VAItems.REDSTONE_BRIDGE, 3)
                    .pattern("#")
                    .pattern("r")
                    .pattern("#")
                    .input('#', VAItems.STEEL_INGOT).input('r', Items.REDSTONE)
                    .criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.CAGELIGHT, 3)
                    .pattern("#")
                    .pattern("g")
                    .pattern("#")
                    .input('#', VAItems.STEEL_INGOT).input('g', Items.GLOWSTONE_DUST)
                    .criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.MISC, VAItems.COLORING_STATION, 1)
                    .pattern("BB")
                    .pattern("##")
                    .pattern("##")
                    .input('#', Ingredient.ofTag(this.registryLookup.getOrThrow(ItemTags.PLANKS))).input('B', Ingredient.ofTag(this.registryLookup.getOrThrow(ItemTags.WOOL)))
                    .criterion("has_wool", conditionsFromTag(ItemTags.WOOL)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.REDSTONE, VAItems.SPOTLIGHT, 1)
                    .pattern("ssa")
                    .pattern("rga")
                    .pattern("ssa")
                    .input('a', Items.AMETHYST_SHARD).input('g', Items.GLOWSTONE).input('s', VAItems.STEEL_INGOT).input('r', Items.REDSTONE)
                    .criterion("has_glowstone", conditionsFromItem(Items.GLOWSTONE)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.FOOD, VAItems.ICE_CREAM, 1)
                    .pattern(" s ")
                    .pattern("bmb")
                    .pattern(" w ")
                    .input('s', VAItems.ROCK_SALT).input('b', Items.SNOWBALL).input('m', Items.MILK_BUCKET).input('w', Items.BOWL)
                    .criterion("has_milk", conditionsFromItem(Items.MILK_BUCKET)).offerTo(this.exporter);

            ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.ENGRAVING_CHISEL, 1)
                    .pattern("c")
                    .pattern("s")
                    .pattern("c")
                    .input('c', ItemTags.STONE_CRAFTING_MATERIALS)
                    .input('s', VAItems.STEEL_INGOT)
                    .criterion("has_steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(this.exporter);

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.DECORATIONS, Items.LIGHT_BLUE_DYE)
                    .input(VAItems.BLUE_PETALS)
                    .criterion("has_blue_petals", conditionsFromItem(VAItems.BLUE_PETALS))
                            .offerTo(this.exporter, "virtual_additions:light_blue_dye_from_blue_petals");

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.PLUM_DYE)
                    .input(VAItems.SMALL_SPRING_LOTUS)
                    .criterion("has_small_spring_lotus", conditionsFromItem(VAItems.SMALL_SPRING_LOTUS))
                            .offerTo(this.exporter, "virtual_additions:orange_dye_from_small_spring_lotus");

            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.DECORATIONS, Items.LIGHT_BLUE_DYE)
                    .input(VAItems.SOUL_SPROUT)
                    .criterion("has_soul_sprout", conditionsFromItem(VAItems.SOUL_SPROUT))
                    .offerTo(this.exporter, "virtual_additions:orange_dye_from_soul_sprout");

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
        protected static final DyeContents WHITE_COST = VADyeColors.WHITE_CONTENT;
        protected static final DyeContents LIGHT_GRAY_COST = VADyeColors.LIGHT_GRAY_CONTENT;
        protected static final DyeContents GRAY_COST = VADyeColors.GRAY_CONTENT;
        protected static final DyeContents BLACK_COST = VADyeColors.BLACK_CONTENT;
        protected static final DyeContents TAN_COST = VADyeColors.TAN_CONTENT;
        protected static final DyeContents BROWN_COST = VADyeColors.BROWN_CONTENT;
        protected static final DyeContents MAROON_COST = VADyeColors.MAROON_CONTENT;
        protected static final DyeContents RED_COST = VADyeColors.RED_CONTENT;
        protected static final DyeContents SINOPIA_COST = VADyeColors.SINOPIA_CONTENT;
        protected static final DyeContents ORANGE_COST = VADyeColors.ORANGE_CONTENT;
        protected static final DyeContents YELLOW_COST = VADyeColors.YELLOW_CONTENT;
        protected static final DyeContents CHARTREUSE_COST = VADyeColors.CHARTREUSE_CONTENT;
        protected static final DyeContents LIME_COST = VADyeColors.LIME_CONTENT;
        protected static final DyeContents GREEN_COST = VADyeColors.GREEN_CONTENT;
        protected static final DyeContents VIRIDIAN_COST = VADyeColors.VIRIDIAN_CONTENT;
        protected static final DyeContents CYAN_COST = VADyeColors.CYAN_CONTENT;
        protected static final DyeContents LIGHT_BLUE_COST = VADyeColors.LIGHT_BLUE_CONTENT;
        protected static final DyeContents BLUE_COST = VADyeColors.BLUE_CONTENT;
        protected static final DyeContents INDIGO_COST = VADyeColors.INDIGO_CONTENT;
        protected static final DyeContents PURPLE_COST = VADyeColors.PURPLE_CONTENT;
        protected static final DyeContents PLUM_COST = VADyeColors.PLUM_CONTENT;
        protected static final DyeContents MAGENTA_COST = VADyeColors.MAGENTA_CONTENT;
        protected static final DyeContents PINK_COST = VADyeColors.PINK_CONTENT;
        protected static final DyeContents LILAC_COST = VADyeColors.LILAC_CONTENT;
        protected static final List<DyeContents> dyeCosts = List.of(BLACK_COST, BLUE_COST, BROWN_COST, CYAN_COST, GRAY_COST, GREEN_COST, LIGHT_BLUE_COST, LIGHT_GRAY_COST, LIME_COST, MAGENTA_COST, ORANGE_COST, PINK_COST, PURPLE_COST, RED_COST, YELLOW_COST, WHITE_COST, CHARTREUSE_COST, MAROON_COST, INDIGO_COST, PLUM_COST, VIRIDIAN_COST, TAN_COST, SINOPIA_COST, LILAC_COST);

        protected static final List<Item> dyes = List.of(Items.BLACK_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.CYAN_DYE, Items.GRAY_DYE, Items.GREEN_DYE, Items.LIGHT_BLUE_DYE, Items.LIGHT_GRAY_DYE, Items.LIME_DYE, Items.MAGENTA_DYE, Items.ORANGE_DYE, Items.PINK_DYE, Items.PURPLE_DYE, Items.RED_DYE, Items.YELLOW_DYE, Items.WHITE_DYE, VAItems.CHARTREUSE_DYE, VAItems.MAROON_DYE, VAItems.INDIGO_DYE, VAItems.PLUM_DYE, VAItems.VIRIDIAN_DYE, VAItems.TAN_DYE, VAItems.SINOPIA_DYE, VAItems.LILAC_DYE);
        protected static final List<Item> vanillaDyes = List.of(Items.BLACK_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.CYAN_DYE, Items.GRAY_DYE, Items.GREEN_DYE, Items.LIGHT_BLUE_DYE, Items.LIGHT_GRAY_DYE, Items.LIME_DYE, Items.MAGENTA_DYE, Items.ORANGE_DYE, Items.PINK_DYE, Items.PURPLE_DYE, Items.RED_DYE, Items.YELLOW_DYE, Items.WHITE_DYE);
        protected static final List<Item> virtualAdditionsDyes = List.of(VAItems.CHARTREUSE_DYE, VAItems.MAROON_DYE, VAItems.INDIGO_DYE, VAItems.PLUM_DYE, VAItems.VIRIDIAN_DYE, VAItems.TAN_DYE, VAItems.SINOPIA_DYE, VAItems.LILAC_DYE);

        protected static final List<Item> harnesses = List.of(Items.BLACK_HARNESS, Items.BLUE_HARNESS, Items.BROWN_HARNESS, Items.CYAN_HARNESS, Items.GRAY_HARNESS, Items.GREEN_HARNESS, Items.LIGHT_BLUE_HARNESS, Items.LIGHT_GRAY_HARNESS, Items.LIME_HARNESS, Items.MAGENTA_HARNESS, Items.ORANGE_HARNESS, Items.PINK_HARNESS, Items.PURPLE_HARNESS, Items.RED_HARNESS, Items.YELLOW_HARNESS, Items.WHITE_HARNESS, VAItems.CHARTREUSE_HARNESS, VAItems.MAROON_HARNESS, VAItems.INDIGO_HARNESS, VAItems.PLUM_HARNESS, VAItems.VIRIDIAN_HARNESS, VAItems.TAN_HARNESS, VAItems.SINOPIA_HARNESS, VAItems.LILAC_HARNESS);
        protected static final List<Item> vanillaHarnesses = List.of(Items.BLACK_HARNESS, Items.BLUE_HARNESS, Items.BROWN_HARNESS, Items.CYAN_HARNESS, Items.GRAY_HARNESS, Items.GREEN_HARNESS, Items.LIGHT_BLUE_HARNESS, Items.LIGHT_GRAY_HARNESS, Items.LIME_HARNESS, Items.MAGENTA_HARNESS, Items.ORANGE_HARNESS, Items.PINK_HARNESS, Items.PURPLE_HARNESS, Items.RED_HARNESS, Items.YELLOW_HARNESS, Items.WHITE_HARNESS);
        protected static final List<Item> virtualAdditionsHarnesses = List.of(VAItems.CHARTREUSE_HARNESS, VAItems.MAROON_HARNESS, VAItems.INDIGO_HARNESS, VAItems.PLUM_HARNESS, VAItems.VIRIDIAN_HARNESS, VAItems.TAN_HARNESS, VAItems.SINOPIA_HARNESS, VAItems.LILAC_HARNESS);

        protected static final List<Item> wool = List.of(Items.BLACK_WOOL, Items.BLUE_WOOL, Items.BROWN_WOOL, Items.CYAN_WOOL, Items.GRAY_WOOL, Items.GREEN_WOOL, Items.LIGHT_BLUE_WOOL, Items.LIGHT_GRAY_WOOL, Items.LIME_WOOL, Items.MAGENTA_WOOL, Items.ORANGE_WOOL, Items.PINK_WOOL, Items.PURPLE_WOOL, Items.RED_WOOL, Items.YELLOW_WOOL, Items.WHITE_WOOL, VAItems.CHARTREUSE_WOOL, VAItems.MAROON_WOOL, VAItems.INDIGO_WOOL, VAItems.PLUM_WOOL, VAItems.VIRIDIAN_WOOL, VAItems.TAN_WOOL, VAItems.SINOPIA_WOOL, VAItems.LILAC_WOOL);
        protected static final List<Item> vanillaWool = List.of(Items.BLACK_WOOL, Items.BLUE_WOOL, Items.BROWN_WOOL, Items.CYAN_WOOL, Items.GRAY_WOOL, Items.GREEN_WOOL, Items.LIGHT_BLUE_WOOL, Items.LIGHT_GRAY_WOOL, Items.LIME_WOOL, Items.MAGENTA_WOOL, Items.ORANGE_WOOL, Items.PINK_WOOL, Items.PURPLE_WOOL, Items.RED_WOOL, Items.YELLOW_WOOL, Items.WHITE_WOOL);
        protected static final List<Item> virtualAdditionsWool = List.of(VAItems.CHARTREUSE_WOOL, VAItems.MAROON_WOOL, VAItems.INDIGO_WOOL, VAItems.PLUM_WOOL, VAItems.VIRIDIAN_WOOL, VAItems.TAN_WOOL, VAItems.SINOPIA_WOOL, VAItems.LILAC_WOOL);

        protected static final List<Item> carpets = List.of(Items.BLACK_CARPET, Items.BLUE_CARPET, Items.BROWN_CARPET, Items.CYAN_CARPET, Items.GRAY_CARPET, Items.GREEN_CARPET, Items.LIGHT_BLUE_CARPET, Items.LIGHT_GRAY_CARPET, Items.LIME_CARPET, Items.MAGENTA_CARPET, Items.ORANGE_CARPET, Items.PINK_CARPET, Items.PURPLE_CARPET, Items.RED_CARPET, Items.YELLOW_CARPET, Items.WHITE_CARPET, VAItems.CHARTREUSE_CARPET, VAItems.MAROON_CARPET, VAItems.INDIGO_CARPET, VAItems.PLUM_CARPET, VAItems.VIRIDIAN_CARPET, VAItems.TAN_CARPET, VAItems.SINOPIA_CARPET, VAItems.LILAC_CARPET);
        protected static final List<Item> vanillaCarpets = List.of(Items.BLACK_CARPET, Items.BLUE_CARPET, Items.BROWN_CARPET, Items.CYAN_CARPET, Items.GRAY_CARPET, Items.GREEN_CARPET, Items.LIGHT_BLUE_CARPET, Items.LIGHT_GRAY_CARPET, Items.LIME_CARPET, Items.MAGENTA_CARPET, Items.ORANGE_CARPET, Items.PINK_CARPET, Items.PURPLE_CARPET, Items.RED_CARPET, Items.YELLOW_CARPET, Items.WHITE_CARPET);
        protected static final List<Item> virtualAdditionsCarpets = List.of(VAItems.CHARTREUSE_CARPET, VAItems.MAROON_CARPET, VAItems.INDIGO_CARPET, VAItems.PLUM_CARPET, VAItems.VIRIDIAN_CARPET, VAItems.TAN_CARPET, VAItems.SINOPIA_CARPET, VAItems.LILAC_CARPET);

        protected static final List<Item> beds = List.of(Items.BLACK_BED, Items.BLUE_BED, Items.BROWN_BED, Items.CYAN_BED, Items.GRAY_BED, Items.GREEN_BED, Items.LIGHT_BLUE_BED, Items.LIGHT_GRAY_BED, Items.LIME_BED, Items.MAGENTA_BED, Items.ORANGE_BED, Items.PINK_BED, Items.PURPLE_BED, Items.RED_BED, Items.YELLOW_BED, Items.WHITE_BED, VAItems.CHARTREUSE_BED, VAItems.MAROON_BED, VAItems.INDIGO_BED, VAItems.PLUM_BED, VAItems.VIRIDIAN_BED, VAItems.TAN_BED, VAItems.SINOPIA_BED, VAItems.LILAC_BED);
        protected static final List<Item> vanillaBeds = List.of(Items.BLACK_BED, Items.BLUE_BED, Items.BROWN_BED, Items.CYAN_BED, Items.GRAY_BED, Items.GREEN_BED, Items.LIGHT_BLUE_BED, Items.LIGHT_GRAY_BED, Items.LIME_BED, Items.MAGENTA_BED, Items.ORANGE_BED, Items.PINK_BED, Items.PURPLE_BED, Items.RED_BED, Items.YELLOW_BED, Items.WHITE_BED);
        protected static final List<Item> virtualAdditionsBeds = List.of(VAItems.CHARTREUSE_BED, VAItems.MAROON_BED, VAItems.INDIGO_BED, VAItems.PLUM_BED, VAItems.VIRIDIAN_BED, VAItems.TAN_BED, VAItems.SINOPIA_BED, VAItems.LILAC_BED);

        protected static final List<Item> silkbulbs = List.of(VAItems.BLACK_SILKBULB, VAItems.BLUE_SILKBULB, VAItems.BROWN_SILKBULB, VAItems.CYAN_SILKBULB, VAItems.GRAY_SILKBULB, VAItems.GREEN_SILKBULB, VAItems.LIGHT_BLUE_SILKBULB, VAItems.LIGHT_GRAY_SILKBULB, VAItems.LIME_SILKBULB, VAItems.MAGENTA_SILKBULB, VAItems.ORANGE_SILKBULB, VAItems.PINK_SILKBULB, VAItems.PURPLE_SILKBULB, VAItems.RED_SILKBULB, VAItems.YELLOW_SILKBULB, VAItems.WHITE_SILKBULB, VAItems.CHARTREUSE_SILKBULB, VAItems.MAROON_SILKBULB, VAItems.INDIGO_SILKBULB, VAItems.PLUM_SILKBULB, VAItems.VIRIDIAN_SILKBULB, VAItems.TAN_SILKBULB, VAItems.SINOPIA_SILKBULB, VAItems.LILAC_SILKBULB);
        protected static final List<Item> bundles = List.of(Items.BLACK_BUNDLE, Items.BLUE_BUNDLE, Items.BROWN_BUNDLE, Items.CYAN_BUNDLE, Items.GRAY_BUNDLE, Items.GREEN_BUNDLE, Items.LIGHT_BLUE_BUNDLE, Items.LIGHT_GRAY_BUNDLE, Items.LIME_BUNDLE, Items.MAGENTA_BUNDLE, Items.ORANGE_BUNDLE, Items.PINK_BUNDLE, Items.PURPLE_BUNDLE, Items.RED_BUNDLE, Items.YELLOW_BUNDLE, Items.WHITE_BUNDLE, VAItems.CHARTREUSE_BUNDLE, VAItems.MAROON_BUNDLE, VAItems.INDIGO_BUNDLE, VAItems.PLUM_BUNDLE, VAItems.VIRIDIAN_BUNDLE, VAItems.TAN_BUNDLE, VAItems.SINOPIA_BUNDLE, VAItems.LILAC_BUNDLE);


        protected final RegistryEntryLookup<Item> registryLookup;
        
        protected Generator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
            super(registryLookup, exporter);
            this.registryLookup = registries.getOrThrow(RegistryKeys.ITEM);
        }

        protected void offerCookingRecipes(ItemConvertible output, List<ItemConvertible> inputs, float experience, String group) {
            offerSmelting(inputs, RecipeCategory.FOOD, output, experience, 200, group);
            this.offerMultipleOptions(RecipeSerializer.SMOKING, SmokingRecipe::new, inputs, RecipeCategory.FOOD, output, experience, 100, group, "_from_smoking");
            this.offerMultipleOptions(RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, inputs, RecipeCategory.FOOD, output, experience, 600, group, "_from_campfire_cooking");
        }

        protected void offerCookingRecipes(ItemConvertible output, ItemConvertible input, float experience, String group) {
            offerSmelting(List.of(input), RecipeCategory.FOOD, output, experience, 200, group);
            offerFoodCookingRecipe("smoking", RecipeSerializer.SMOKING, SmokingRecipe::new, 100, input, output, experience);
            offerFoodCookingRecipe("campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 600, input, output, experience);
        }

        protected void offerColoringRecipe(ItemConvertible input, ItemConvertible output, DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(Ingredient.ofItems(input), cost, output, index).offerTo(this.exporter, idOf(getItemPath(output)).withSuffixedPath("_coloring"));
        }

        protected void offerColoringRecipe(ItemConvertible output, DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(null, cost, output, index).offerTo(this.exporter, idOf(getItemPath(output)).withSuffixedPath("_coloring"));
        }

        protected void offerColoringRecipe(TagKey<Item> input, ItemConvertible output, DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(Ingredient.ofTag(this.registryLookup.getOrThrow(input)), cost, output, index).offerTo(this.exporter, idOf(getItemPath(output)).withSuffixedPath("_coloring"));
        }

        protected void offerArmorColoringRecipe(DyeItem input, int i) {
            ArmorColoringRecipeJsonBuilder.create(Ingredient.ofItems(input), i).offerTo(this.exporter, idOf(input.getColor().asString()).withSuffixedPath("_armor_coloring"));
        }

        protected void offer2x2ConversionChain(Block... blocks) {
            for (int i = 0; i < blocks.length - 1; i++) {
                Block input = blocks[i];
                Block output = blocks[i + 1];
                String id = Registries.BLOCK.getId(output).getPath() + "_from_compacting_" + Registries.BLOCK.getId(input).getPath();
                ShapedRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("##")
                        .pattern("##")
                        .input('#', input).criterion(hasItem(input), conditionsFromItem(input)).offerTo(this.exporter, idOf(id).toString());
            }
        }

        protected void offerColoringRecipes(ColorfulBlockSet set, DyeContents cost, int index) {
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
                set.ifBed( bed -> offerBedRecipe(bed, wool));
                set.ifBanner(banner -> offerBannerRecipe(banner, wool));
                set.ifCarpet(carpet -> offerCarpetRecipe(carpet, wool));
            });
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
            ).criterion("has_smithing_template", conditionsFromItem(VAItems.TOOL_GILD_SMITHING_TEMPLATE)).offerTo(this.exporter, idOf("smithing/" + getItemPath(result)).toString());
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
                    .offerTo(this.exporter, idOf("smithing/" + getItemPath(result) + "_upgrade").toString());
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

        @SafeVarargs
        protected final void offerShapelessRecipe(RecipeCategory category, ItemConvertible output, int count, String group, Pair<ItemConvertible, Integer>... input) {
            ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(this.registryLookup, category, output, count);
            builder.criterion(hasItem(input[0].getLeft()), conditionsFromItem(input[0].getLeft()));
            for (Pair<ItemConvertible, Integer> itemProvider : input) {
                builder.input(itemProvider.getLeft(), itemProvider.getRight());
            }
            builder.group(group);
            builder.offerTo(this.exporter);
        }

        protected void offer2x2FullRecipe(RecipeCategory category, ItemConvertible output, ItemConvertible input, int count) {
            ShapedRecipeJsonBuilder.create(this.registryLookup, category, output, count).input('#', input).pattern("##").pattern("##").criterion(hasItem(input), conditionsFromItem(input)).offerTo(this.exporter, idOf(getItemPath(output) + "_from_" + getItemPath(input)).toString());
        }

        protected void offer2x2FullRecipe(RecipeCategory category, ItemConvertible output, ItemConvertible input, int count, String group) {
            ShapedRecipeJsonBuilder.create(this.registryLookup, category, output, count).input('#', input).pattern("##").pattern("##").group(group).criterion(hasItem(input), conditionsFromItem(input)).offerTo(this.exporter, idOf(getItemPath(output) + "_from_" + getItemPath(input)).toString());
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
                ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, waxed).input(unwaxed).input(Items.HONEYCOMB).group(RecipeGenerator.getItemPath(waxed)).criterion(RecipeGenerator.hasItem(unwaxed), this.conditionsFromItem(unwaxed)).offerTo(this.exporter, idOf(RecipeGenerator.convertBetween(waxed, Items.HONEYCOMB)).toString());
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

        public void offerJerkyFoodRecipe(Item input, Item jerky) {
            ShapelessRecipeJsonBuilder.create(this.registryLookup, RecipeCategory.FOOD, jerky, 2)
                    .input(input)
                    .input(VAItems.ROCK_SALT, 2)
                    .criterion("has_item", conditionsFromItem(input)).offerTo(exporter);
        }
        
        public void completeDyablesRecipes(List<Item> allItems, List<Item> vanillaItems, List<Item> moddedItems, String group, RecipeCategory recipeCategory) {
            offerDyeablesRecipes(vanillaDyes, vanillaItems, moddedItems, null, group, RecipeCategory.BUILDING_BLOCKS);
            offerDyeablesRecipes(virtualAdditionsDyes, moddedItems, allItems, null, group, RecipeCategory.BUILDING_BLOCKS);
        }

        public void offerDyeablesRecipes(List<Item> dyes, List<Item> dyeToItem, List<Item> dyeables, @Nullable Item undyed, String group, RecipeCategory category) {
            for(int i = 0; i < dyes.size(); ++i) {
                Item dye = dyes.get(i);
                Item dyedItem = dyeToItem.get(i);
                Stream<Item> stream = dyeables.stream().filter((itemx) -> !itemx.equals(dyedItem));
                if (undyed != null) {
                    stream = Stream.concat(stream, Stream.of(undyed));
                }

                this.createShapeless(category, dyedItem).input(dye).input(Ingredient.ofItems(stream)).group(group).criterion("has_needed_dye", this.conditionsFromItem(dye)).offerTo(this.exporter, "virtual_additions:dye_" + getItemPath(dyedItem));
            }

        }

        public void offerDyeablesRecipes(List<Item> dyes, List<Item> dyeables, @Nullable Item undyed, String group, RecipeCategory category) {
            for(int i = 0; i < dyes.size(); ++i) {
                Item item = dyes.get(i);
                Item item2 = dyeables.get(i);
                Stream<Item> stream = dyeables.stream().filter((itemx) -> !itemx.equals(item2));
                if (undyed != null) {
                    stream = Stream.concat(stream, Stream.of(undyed));
                }

                this.createShapeless(category, item2).input(item).input(Ingredient.ofItems(stream)).group(group).criterion("has_needed_dye", this.conditionsFromItem(item)).offerTo(this.exporter, "virtual_additions:dye_" + getItemPath(item2));
            }

        }
        
        protected void createColoringRecipeSet(TagKey<Item> inputTag, List<Item> items) {
            for(int i = 0; i < dyeCosts.size(); ++i) {
                DyeContents cost = dyeCosts.get(i);
                Item dyedItem = items.get(i);
                
                ColoringRecipeJsonBuilder.create(Ingredient.ofTag(this.registryLookup.getOrThrow(inputTag)), 
                        cost, dyedItem, VADyeColors.getIndex(cost))
                        .offerTo(exporter, idOf(getItemPath(dyedItem)).withSuffixedPath("_coloring"));

            }
            

        }
    }


}
