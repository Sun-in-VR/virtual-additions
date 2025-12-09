package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.DyeContents;
import com.github.suninvr.virtualadditions.datagen.recipe.ArmorColoringRecipeJsonBuilder;
import com.github.suninvr.virtualadditions.datagen.recipe.ColoringRecipeJsonBuilder;
import com.github.suninvr.virtualadditions.datagen.recipe.SmithingGildRecipeJsonBuilder;
import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.*;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
        public BaseProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
            return new BaseGenerator(registryLookup, exporter);
        }

        @Override
        public String getName() {
            return "Virtual Additions Recipes";
        }
    }

    private static class PreviewProvider extends FabricRecipeProvider {
        public PreviewProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
            return new PreviewGenerator(registryLookup, exporter);
        }

        @Override
        public String getName() {
            return "Virtual Additions Preview Recipes";
        }
    }

    private static class BaseGenerator extends Generator {

        protected BaseGenerator(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
            super(registryLookup, exporter);
        }

        @Override
        public void buildRecipes() {
            this.oreBlasting(List.of(VAItems.RAW_STEEL), RecipeCategory.MISC, VAItems.STEEL_INGOT, 1.0F, 100, "steel_ingot");
            this.oreSmelting(List.of(VAItems.RAW_STEEL), RecipeCategory.MISC, VAItems.STEEL_INGOT, 1.0F, 200, "steel_ingot");
            this.oreBlasting(List.of(VAItems.IOLITE_ORE), RecipeCategory.MISC, VAItems.IOLITE, 1.0F, 100, "iolite");
            this.oreSmelting(List.of(VAItems.IOLITE_ORE), RecipeCategory.MISC, VAItems.IOLITE, 1.0F, 200, "iolite");
            this.oreBlasting(List.of(VAItems.ROCK_SALT_ORE), RecipeCategory.MISC, VAItems.ROCK_SALT, 1.0F, 100, "rock_salt");
            this.oreSmelting(List.of(VAItems.ROCK_SALT_ORE), RecipeCategory.MISC, VAItems.ROCK_SALT, 1.0F, 200, "rock_salt");
            this.oreBlasting(List.of(VAItems.DEEPSLATE_ROCK_SALT_ORE), RecipeCategory.MISC, VAItems.ROCK_SALT, 1.0F, 100, "rock_salt");
            this.oreSmelting(List.of(VAItems.DEEPSLATE_ROCK_SALT_ORE), RecipeCategory.MISC, VAItems.ROCK_SALT, 1.0F, 200, "rock_salt");

            this.offerHedgeRecipe(VABlocks.OAK_HEDGE, Blocks.OAK_LEAVES);
            this.offerHedgeRecipe(VABlocks.SPRUCE_HEDGE, Blocks.SPRUCE_LEAVES);
            this.offerHedgeRecipe(VABlocks.BIRCH_HEDGE, Blocks.BIRCH_LEAVES);
            this.offerHedgeRecipe(VABlocks.JUNGLE_HEDGE, Blocks.JUNGLE_LEAVES);
            this.offerHedgeRecipe(VABlocks.ACACIA_HEDGE, Blocks.ACACIA_LEAVES);
            this.offerHedgeRecipe(VABlocks.DARK_OAK_HEDGE, Blocks.DARK_OAK_LEAVES);
            this.offerHedgeRecipe(VABlocks.PALE_OAK_HEDGE, Blocks.PALE_OAK_LEAVES);
            this.offerHedgeRecipe(VABlocks.MANGROVE_HEDGE, Blocks.MANGROVE_LEAVES);
            this.offerHedgeRecipe(VABlocks.ZEBRANO_HEDGE, VABlocks.ZEBRANO_LEAVES);
            this.offerHedgeRecipe(VABlocks.CHERRY_HEDGE, Blocks.CHERRY_LEAVES);
            this.offerHedgeRecipe(VABlocks.AZALEA_HEDGE, Blocks.AZALEA_LEAVES);
            this.offerHedgeRecipe(VABlocks.FLOWERING_AZALEA_HEDGE, Blocks.FLOWERING_AZALEA_LEAVES);

            this.createPlanterRecipe(Blocks.OAK_SLAB, VABlocks.OAK_PLANTER);
            this.createPlanterRecipe(Blocks.SPRUCE_SLAB, VABlocks.SPRUCE_PLANTER);
            this.createPlanterRecipe(Blocks.BIRCH_SLAB, VABlocks.BIRCH_PLANTER);
            this.createPlanterRecipe(Blocks.JUNGLE_SLAB, VABlocks.JUNGLE_PLANTER);
            this.createPlanterRecipe(Blocks.ACACIA_SLAB, VABlocks.ACACIA_PLANTER);
            this.createPlanterRecipe(Blocks.DARK_OAK_SLAB, VABlocks.DARK_OAK_PLANTER);
            this.createPlanterRecipe(Blocks.PALE_OAK_SLAB, VABlocks.PALE_OAK_PLANTER);
            this.createPlanterRecipe(Blocks.MANGROVE_SLAB, VABlocks.MANGROVE_PLANTER);
            this.createPlanterRecipe(VABlocks.ZEBRANO_SLAB, VABlocks.ZEBRANO_PLANTER);
            this.createPlanterRecipe(Blocks.CHERRY_SLAB, VABlocks.CHERRY_PLANTER);
            this.createPlanterRecipe(Blocks.BAMBOO_SLAB, VABlocks.BAMBOO_PLANTER);
            this.createPlanterRecipe(VABlocks.SOULBLOOM_SLAB, VABlocks.SOULBLOOM_PLANTER);
            this.createPlanterRecipe(Blocks.CRIMSON_SLAB, VABlocks.CRIMSON_PLANTER);
            this.createPlanterRecipe(Blocks.WARPED_SLAB, VABlocks.WARPED_PLANTER);
            this.createPlanterRecipe(VABlocks.WITHERED_SLAB, VABlocks.WITHERED_PLANTER);

            this.threeByThreePacker(RecipeCategory.MISC, VABlocks.STEEL_BLOCK, VAItems.STEEL_INGOT, "steel_ingot");
            this.oneToOneConversionRecipe(VAItems.STEEL_INGOT, VABlocks.STEEL_BLOCK, "steel", 9);
            this.oneToOneConversionRecipe(VAItems.STEEL_INGOT, VABlocks.WAXED_STEEL_BLOCK, "steel_ingot", 9);
            this.threeByThreePacker(RecipeCategory.MISC, VABlocks.RAW_STEEL_BLOCK, VAItems.RAW_STEEL, "raw_steel");
            this.oneToOneConversionRecipe(VAItems.RAW_STEEL, VABlocks.RAW_STEEL_BLOCK, "raw_steel", 9);
            this.threeByThreePacker(RecipeCategory.MISC, VAItems.STEEL_INGOT, VAItems.STEEL_NUGGET, "steel_nugget");
            this.oneToOneConversionRecipe(VAItems.STEEL_NUGGET, VAItems.STEEL_INGOT, "steel_nugget", 9);

            SimpleCookingRecipeBuilder.smelting(Ingredient.of(VAItems.STEEL_PICKAXE, VAItems.STEEL_SHOVEL, VAItems.STEEL_AXE, VAItems.STEEL_HOE, VAItems.STEEL_SWORD, VAItems.STEEL_SPEAR, VAItems.STEEL_HELMET, VAItems.STEEL_CHESTPLATE, VAItems.STEEL_LEGGINGS, VAItems.STEEL_BOOTS, VAItems.STEEL_HORSE_ARMOR, VAItems.STEEL_HALBERD), RecipeCategory.MISC, VAItems.STEEL_NUGGET, 0.1F, 200).unlockedBy("has_steel_pickaxe", this.has(VAItems.STEEL_PICKAXE)).unlockedBy("has_steel_shovel", this.has(VAItems.STEEL_SHOVEL)).unlockedBy("has_steel_axe", this.has(VAItems.STEEL_AXE)).unlockedBy("has_steel_hoe", this.has(VAItems.STEEL_HOE)).unlockedBy("has_steel_sword", this.has(VAItems.STEEL_SWORD)).unlockedBy("has_steel_helmet", this.has(VAItems.STEEL_HELMET)).unlockedBy("has_steel_chestplate", this.has(VAItems.STEEL_CHESTPLATE)).unlockedBy("has_steel_leggings", this.has(VAItems.STEEL_LEGGINGS)).unlockedBy("has_steel_boots", this.has(VAItems.STEEL_BOOTS)).unlockedBy("has_steel_horse_armor", this.has(VAItems.STEEL_HORSE_ARMOR)).save(this.output, getSmeltingRecipeName(VAItems.STEEL_NUGGET));
            SimpleCookingRecipeBuilder.blasting(Ingredient.of(VAItems.STEEL_PICKAXE, VAItems.STEEL_SHOVEL, VAItems.STEEL_AXE, VAItems.STEEL_HOE, VAItems.STEEL_SWORD, VAItems.STEEL_SPEAR, VAItems.STEEL_HELMET, VAItems.STEEL_CHESTPLATE, VAItems.STEEL_LEGGINGS, VAItems.STEEL_BOOTS, VAItems.STEEL_HORSE_ARMOR, VAItems.STEEL_HALBERD), RecipeCategory.MISC, VAItems.STEEL_NUGGET, 0.1F, 200).unlockedBy("has_steel_pickaxe", this.has(VAItems.STEEL_PICKAXE)).unlockedBy("has_steel_shovel", this.has(VAItems.STEEL_SHOVEL)).unlockedBy("has_steel_axe", this.has(VAItems.STEEL_AXE)).unlockedBy("has_steel_hoe", this.has(VAItems.STEEL_HOE)).unlockedBy("has_steel_sword", this.has(VAItems.STEEL_SWORD)).unlockedBy("has_steel_helmet", this.has(VAItems.STEEL_HELMET)).unlockedBy("has_steel_chestplate", this.has(VAItems.STEEL_CHESTPLATE)).unlockedBy("has_steel_leggings", this.has(VAItems.STEEL_LEGGINGS)).unlockedBy("has_steel_boots", this.has(VAItems.STEEL_BOOTS)).unlockedBy("has_steel_horse_armor", this.has(VAItems.STEEL_HORSE_ARMOR)).save(this.output, getBlastingRecipeName(VAItems.STEEL_NUGGET));
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(VAItems.IRON_HALBERD), RecipeCategory.MISC, Items.IRON_NUGGET, 0.1F, 200).unlockedBy("has_iron_pickaxe", this.has(Items.IRON_PICKAXE)).unlockedBy("has_iron_shovel", this.has(Items.IRON_SHOVEL)).unlockedBy("has_iron_axe", this.has(Items.IRON_AXE)).unlockedBy("has_iron_hoe", this.has(Items.IRON_HOE)).unlockedBy("has_iron_sword", this.has(Items.IRON_SWORD)).unlockedBy("has_iron_helmet", this.has(Items.IRON_HELMET)).unlockedBy("has_iron_chestplate", this.has(Items.IRON_CHESTPLATE)).unlockedBy("has_iron_leggings", this.has(Items.IRON_LEGGINGS)).unlockedBy("has_iron_boots", this.has(Items.IRON_BOOTS)).unlockedBy("has_iron_horse_armor", this.has(Items.IRON_HORSE_ARMOR)).unlockedBy("has_iron_halberd", this.has(VAItems.IRON_HALBERD)).save(this.output, idOf("iron_nugget_from_smelting_halberd").toString());
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(VAItems.GOLDEN_HALBERD), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.1F, 200).unlockedBy("has_golden_pickaxe", this.has(Items.GOLDEN_PICKAXE)).unlockedBy("has_golden_shovel", this.has(Items.GOLDEN_SHOVEL)).unlockedBy("has_golden_axe", this.has(Items.GOLDEN_AXE)).unlockedBy("has_golden_hoe", this.has(Items.GOLDEN_HOE)).unlockedBy("has_golden_sword", this.has(Items.GOLDEN_SWORD)).unlockedBy("has_golden_helmet", this.has(Items.GOLDEN_HELMET)).unlockedBy("has_golden_chestplate", this.has(Items.GOLDEN_CHESTPLATE)).unlockedBy("has_golden_leggings", this.has(Items.GOLDEN_LEGGINGS)).unlockedBy("has_golden_boots", this.has(Items.GOLDEN_BOOTS)).unlockedBy("has_golden_horse_armor", this.has(Items.GOLDEN_HORSE_ARMOR)).unlockedBy("has_golden_halberd", this.has(VAItems.GOLDEN_HALBERD)).save(this.output, idOf("gold_nugget_from_smelting_halberd").toString());
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(VAItems.COPPER_HALBERD), RecipeCategory.MISC, Items.COPPER_NUGGET, 0.1F, 200).unlockedBy("has_copper_pickaxe", this.has(Items.COPPER_PICKAXE)).unlockedBy("has_copper_shovel", this.has(Items.COPPER_SHOVEL)).unlockedBy("has_copper_axe", this.has(Items.COPPER_AXE)).unlockedBy("has_copper_hoe", this.has(Items.COPPER_HOE)).unlockedBy("has_copper_sword", this.has(Items.COPPER_SWORD)).unlockedBy("has_copper_helmet", this.has(Items.COPPER_HELMET)).unlockedBy("has_copper_chestplate", this.has(Items.COPPER_CHESTPLATE)).unlockedBy("has_copper_leggings", this.has(Items.COPPER_LEGGINGS)).unlockedBy("has_copper_boots", this.has(Items.COPPER_BOOTS)).unlockedBy("has_copper_horse_armor", this.has(Items.COPPER_HORSE_ARMOR)).unlockedBy("has_copper_halberd", this.has(VAItems.COPPER_HALBERD)).save(this.output, idOf("copper_nugget_from_smelting_halberd").toString());

            this.threeByThreePacker(RecipeCategory.MISC, VABlocks.IOLITE_BLOCK, VAItems.IOLITE, "iolite");
            this.oneToOneConversionRecipe(VAItems.IOLITE, VABlocks.IOLITE_BLOCK, "iolite", 9);

            this.offerShapelessRecipe(RecipeCategory.MISC, VAItems.RAW_STEEL, 1, Pair.of(Items.RAW_IRON, 3), Pair.of(Items.COAL, 1));

            this.offerShapelessRecipe(RecipeCategory.MISC, VAItems.TOOL_GILD_SMITHING_TEMPLATE, 1, Pair.of(VAItems.STEEL_INGOT, 1), Pair.of(Items.DIAMOND, 1));

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL, 4).pattern("###").pattern("# #").pattern("###").define('#', VAItems.STEEL_NUGGET).unlockedBy("steel_nugget", has(VAItems.STEEL_NUGGET)).save(this.output);
            this.doorBuilder(VABlocks.STEEL_DOOR, Ingredient.of(VAItems.STEEL_INGOT)).unlockedBy("steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            this.trapdoorBuilder(VABlocks.STEEL_TRAPDOOR, Ingredient.of(VAItems.STEEL_INGOT)).unlockedBy("steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.DECORATIONS, VABlocks.STEEL_FENCE, 6).define('W', VABlocks.CUT_STEEL).define('#', VAItems.STEEL_NUGGET).pattern("W#W").pattern("W#W").unlockedBy("cut_steel", has(VABlocks.CUT_STEEL)).save(this.output);

            this.offerSteelRecipeSet(VABlocks.STEEL_BLOCK, VABlocks.CUT_STEEL, VABlocks.CUT_STEEL_STAIRS, VABlocks.CUT_STEEL_SLAB, VABlocks.STEEL_GRATE, VABlocks.CHISELED_STEEL);
            this.offerSteelRecipeSet(VABlocks.EXPOSED_STEEL_BLOCK, VABlocks.EXPOSED_CUT_STEEL, VABlocks.EXPOSED_CUT_STEEL_STAIRS, VABlocks.EXPOSED_CUT_STEEL_SLAB, VABlocks.EXPOSED_STEEL_GRATE, VABlocks.EXPOSED_CHISELED_STEEL);
            this.offerSteelRecipeSet(VABlocks.WEATHERED_STEEL_BLOCK, VABlocks.WEATHERED_CUT_STEEL, VABlocks.WEATHERED_CUT_STEEL_STAIRS, VABlocks.WEATHERED_CUT_STEEL_SLAB, VABlocks.WEATHERED_STEEL_GRATE, VABlocks.WEATHERED_CHISELED_STEEL);
            this.offerSteelRecipeSet(VABlocks.OXIDIZED_STEEL_BLOCK, VABlocks.OXIDIZED_CUT_STEEL, VABlocks.OXIDIZED_CUT_STEEL_STAIRS, VABlocks.OXIDIZED_CUT_STEEL_SLAB, VABlocks.OXIDIZED_STEEL_GRATE, VABlocks.OXIDIZED_CHISELED_STEEL);
            this.offerSteelRecipeSet(VABlocks.WAXED_STEEL_BLOCK, VABlocks.WAXED_CUT_STEEL, VABlocks.WAXED_CUT_STEEL_STAIRS, VABlocks.WAXED_CUT_STEEL_SLAB, VABlocks.WAXED_STEEL_GRATE, VABlocks.WAXED_CHISELED_STEEL);
            this.offerSteelRecipeSet(VABlocks.WAXED_EXPOSED_STEEL_BLOCK, VABlocks.WAXED_EXPOSED_CUT_STEEL, VABlocks.WAXED_EXPOSED_CUT_STEEL_STAIRS, VABlocks.WAXED_EXPOSED_CUT_STEEL_SLAB, VABlocks.WAXED_EXPOSED_STEEL_GRATE, VABlocks.WAXED_EXPOSED_CHISELED_STEEL);
            this.offerSteelRecipeSet(VABlocks.WAXED_WEATHERED_STEEL_BLOCK, VABlocks.WAXED_WEATHERED_CUT_STEEL, VABlocks.WAXED_WEATHERED_CUT_STEEL_STAIRS, VABlocks.WAXED_WEATHERED_CUT_STEEL_SLAB, VABlocks.WAXED_WEATHERED_STEEL_GRATE, VABlocks.WAXED_WEATHERED_CHISELED_STEEL);
            this.offerSteelRecipeSet(VABlocks.WAXED_OXIDIZED_STEEL_BLOCK, VABlocks.WAXED_OXIDIZED_CUT_STEEL, VABlocks.WAXED_OXIDIZED_CUT_STEEL_STAIRS, VABlocks.WAXED_OXIDIZED_CUT_STEEL_SLAB, VABlocks.WAXED_OXIDIZED_STEEL_GRATE, VABlocks.WAXED_OXIDIZED_CHISELED_STEEL);

            this.oreSmelting(List.of(VABlocks.COBBLED_HORNFELS), RecipeCategory.BUILDING_BLOCKS, VABlocks.HORNFELS, 0.1F, 200, "hornfels");
            this.offerStonecuttingRecipes(VABlocks.HORNFELS, VACollections.POLISHED_HORNFELS, VACollections.HORNFELS_TILES);
            this.generateCuttableFamilyChain(VACollections.COBBLED_HORNFELS);
            this.generateCuttableFamilyChain(VACollections.POLISHED_HORNFELS, VACollections.HORNFELS_TILES);
            this.generateCuttableFamilyChain(VACollections.HORNFELS_TILES);
            this.offer2x2ConversionChain(VABlocks.HORNFELS, VABlocks.POLISHED_HORNFELS, VABlocks.HORNFELS_TILES);

            this.oreSmelting(List.of(VABlocks.COBBLED_BLUESCHIST), RecipeCategory.BUILDING_BLOCKS, VABlocks.BLUESCHIST, 0.1F, 200, "blueschist");
            this.offerStonecuttingRecipes(VABlocks.BLUESCHIST, VACollections.POLISHED_BLUESCHIST, VACollections.BLUESCHIST_BRICKS);
            this.generateCuttableFamilyChain(VACollections.COBBLED_BLUESCHIST);
            this.generateCuttableFamilyChain(VACollections.POLISHED_BLUESCHIST, VACollections.BLUESCHIST_BRICKS);
            this.generateCuttableFamilyChain(VACollections.BLUESCHIST_BRICKS);
            this.offer2x2ConversionChain(VABlocks.BLUESCHIST, VABlocks.POLISHED_BLUESCHIST, VABlocks.BLUESCHIST_BRICKS);

            this.oreSmelting(List.of(VABlocks.COBBLED_SYENITE), RecipeCategory.BUILDING_BLOCKS, VABlocks.SYENITE, 0.1F, 200, "syenite");
            this.offerStonecuttingRecipes(VABlocks.SYENITE, VACollections.POLISHED_SYENITE, VACollections.SYENITE_BRICKS);
            this.generateCuttableFamilyChain(VACollections.COBBLED_SYENITE);
            this.generateCuttableFamilyChain(VACollections.POLISHED_SYENITE, VACollections.SYENITE_BRICKS);
            this.generateCuttableFamilyChain(VACollections.SYENITE_BRICKS);
            this.offer2x2ConversionChain(VABlocks.SYENITE, VABlocks.POLISHED_SYENITE, VABlocks.SYENITE_BRICKS);

            this.offerStonecuttingRecipes(VABlocks.PORPHYRY, VACollections.POLISHED_PORPHYRY, VACollections.PORPHYRY_BRICKS);
            this.generateCuttableFamilyChain(VACollections.PORPHYRY);
            this.generateCuttableFamilyChain(VACollections.POLISHED_PORPHYRY);
            this.generateCuttableFamilyChain(VACollections.PORPHYRY_BRICKS);
            this.offer2x2ConversionChain(VABlocks.PORPHYRY, VABlocks.POLISHED_PORPHYRY, VABlocks.PORPHYRY_BRICKS);

            this.offer2x2FullRecipe(RecipeCategory.MISC, Items.STRING, VAItems.COTTON, 2);
            this.offerShapelessRecipe(RecipeCategory.MISC, VAItems.COTTON_SEEDS, 1, Pair.of(VAItems.COTTON, 1));
            this.offerShapelessRecipe(RecipeCategory.FOOD, VAItems.CORN_SEEDS, 1, Pair.of(VAItems.CORN, 1));
            this.offerShapelessRecipe(RecipeCategory.FOOD, VAItems.TOMATO_SEEDS, 1, Pair.of(VAItems.TOMATO, 1));
            this.offerShapelessRecipe(RecipeCategory.FOOD, VAItems.CABBAGE_SEEDS, 1, Pair.of(VAItems.CABBAGE, 1));
            this.offerShapelessRecipe(RecipeCategory.FOOD, VAItems.WISDOM_BERRY_SEEDS, 1, Pair.of(VAItems.WISDOM_BERRY, 1));

            this.offerCookingRecipes(VAItems.FRIED_EGG, List.of(Items.EGG, Items.BROWN_EGG, Items.BLUE_EGG, VAItems.PURPLE_EGG), 0.35F, "fried_egg");
            this.offerCookingRecipes(VAItems.ROASTED_CORN, VAItems.CORN, 0.35F, "corn");
            this.offerShapelessRecipe(RecipeCategory.FOOD, VAItems.TORTILLA, 2, Pair.of(VAItems.CORN, 3));

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.FOOD, VAItems.TOMATO_SOUP)
                            .requires(VAItems.TOMATO, 3).requires(Items.BOWL).unlockedBy("has_tomato", has(VAItems.TOMATO)).save(this.output);

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.FOOD, VAItems.SALAD)
                            .requires(VAItems.CABBAGE, 1).requires(VAItems.TOMATO, 1).requires(VAItems.ROASTED_CORN, 1).requires(Items.BOWL).unlockedBy("has_food", has(VAItems.TOMATO)).save(this.output);

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.FOOD, VAItems.TACO)
                    .requires(VAItems.CABBAGE, 1)
                    .requires(VAItems.TOMATO, 1)
                    .requires(VAItems.TORTILLA, 1)
                    .requires(VAItems.CHEESE_WEDGE)
                    .requires(Ingredient.of(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT))
                    .unlockedBy("has_food", has(VAItems.TORTILLA))
                    .save(this.output);

            this.offerJerkyFoodRecipe(Items.COOKED_BEEF, VAItems.BEEF_JERKY);
            this.offerJerkyFoodRecipe(Items.COOKED_PORKCHOP, VAItems.PORK_JERKY);
            this.offerJerkyFoodRecipe(Items.COOKED_CHICKEN, VAItems.CHICKEN_JERKY);
            this.offerJerkyFoodRecipe(Items.COOKED_MUTTON, VAItems.MUTTON_JERKY);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.FOOD, VAItems.CHEESE_WEDGE, 4)
                            .pattern(" # ")
                            .pattern("#M#")
                            .pattern(" # ")
                            .define('#', VAItems.ROCK_SALT).define('M', Items.MILK_BUCKET)
                                    .unlockedBy("has_item", has(Items.MILK_BUCKET)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.FOOD, Items.LEATHER)
                            .pattern("###")
                            .pattern("#F#")
                            .pattern("###")
                            .define('#', VAItems.ROCK_SALT).define('F', Items.ROTTEN_FLESH)
                                    .unlockedBy("has_item", has(Items.ROTTEN_FLESH)).save(this.output);

            this.threeByThreePacker(RecipeCategory.BUILDING_BLOCKS, VAItems.ROCK_SALT_BLOCK, VAItems.ROCK_SALT);
            this.oneToOneConversionRecipe(VAItems.ROCK_SALT, VAItems.ROCK_SALT_BLOCK, "rock_salt", 9);

            this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, VABlocks.WEBBED_SILK, VAItems.SILK_THREAD);
            this.threeByThreePacker(RecipeCategory.BUILDING_BLOCKS, VABlocks.SILK_BLOCK, VAItems.SILK_THREAD);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.TOOLS, VAItems.STEEL_AXE).define('#', Items.STICK).define('X', VAItems.STEEL_INGOT).pattern("XX").pattern("X#").pattern(" #").unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_BOOTS).define('X', VAItems.STEEL_INGOT).pattern("X X").pattern("X X").unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_CHESTPLATE).define('X', VAItems.STEEL_INGOT).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_HELMET).define('X', VAItems.STEEL_INGOT).pattern("XXX").pattern("X X").unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_HORSE_ARMOR).define('X', VAItems.STEEL_INGOT).pattern("X X").pattern("XXX").pattern("X X").unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.TOOLS, VAItems.STEEL_HOE).define('#', Items.STICK).define('X', VAItems.STEEL_INGOT).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_LEGGINGS).define('X', VAItems.STEEL_INGOT).pattern("XXX").pattern("X X").pattern("X X").unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.TOOLS, VAItems.STEEL_PICKAXE).define('#', Items.STICK).define('X', VAItems.STEEL_INGOT).pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.TOOLS, VAItems.STEEL_SHOVEL).define('#', Items.STICK).define('X', VAItems.STEEL_INGOT).pattern("X").pattern("#").pattern("#").unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_SWORD).define('#', Items.STICK).define('X', VAItems.STEEL_INGOT).pattern("X").pattern("X").pattern("#").unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_SPEAR).define('#', Items.STICK).define('X', VAItems.STEEL_INGOT).pattern("  X").pattern(" # ").pattern("#  ").unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);

            this.createHalberdRecipe(ItemTags.PLANKS, VAItems.WOODEN_HALBERD);
            this.createHalberdRecipe(ItemTags.STONE_TOOL_MATERIALS, VAItems.STONE_HALBERD);
            this.createHalberdRecipe(Items.COPPER_INGOT, VAItems.COPPER_HALBERD);
            this.createHalberdRecipe(Items.IRON_INGOT, VAItems.IRON_HALBERD);
            this.createHalberdRecipe(Items.GOLD_INGOT, VAItems.GOLDEN_HALBERD);
            this.createHalberdRecipe(VAItems.STEEL_INGOT, VAItems.STEEL_HALBERD);
            this.createHalberdRecipe(Items.DIAMOND, VAItems.DIAMOND_HALBERD);
            this.netheriteSmithing(VAItems.DIAMOND_HALBERD, RecipeCategory.COMBAT, VAItems.NETHERITE_HALBERD);

            this.offerToolGildRecipe(VAItemTags.ACCEPTS_TOOL_GILDS, Items.AMETHYST_SHARD, VAGildTypes.AMETHYST);
            this.offerToolGildRecipe(VAItemTags.ACCEPTS_TOOL_GILDS, Items.COPPER_INGOT, VAGildTypes.COPPER);
            this.offerToolGildRecipe(VAItemTags.ACCEPTS_TOOL_GILDS, Items.EMERALD, VAGildTypes.EMERALD);
            this.offerToolGildRecipe(VAItemTags.ACCEPTS_TOOL_GILDS, Items.QUARTZ, VAGildTypes.QUARTZ);
            this.offerToolGildRecipe(VAItemTags.ACCEPTS_TOOL_GILDS, Items.ECHO_SHARD, VAGildTypes.SCULK);
            this.offerToolGildRecipe(VAItemTags.ACCEPTS_TOOL_GILDS, VAItems.IOLITE, VAGildTypes.IOLITE);

            this.generateColorfulBlockSetRecipes(VACollections.CHARTREUSE, VAItems.CHARTREUSE_DYE);
            this.generateColorfulBlockSetRecipes(VACollections.MAROON, VAItems.MAROON_DYE);
            this.generateColorfulBlockSetRecipes(VACollections.INDIGO, VAItems.INDIGO_DYE);
            this.generateColorfulBlockSetRecipes(VACollections.PLUM, VAItems.PLUM_DYE);
            this.generateColorfulBlockSetRecipes(VACollections.VIRIDIAN, VAItems.VIRIDIAN_DYE);
            this.generateColorfulBlockSetRecipes(VACollections.TAN, VAItems.TAN_DYE);
            this.generateColorfulBlockSetRecipes(VACollections.SINOPIA, VAItems.SINOPIA_DYE);
            this.generateColorfulBlockSetRecipes(VACollections.LILAC, VAItems.LILAC_DYE);

            this.completeDyablesRecipes(beds, vanillaBeds, virtualAdditionsBeds, "bed", RecipeCategory.BUILDING_BLOCKS);
            this.completeDyablesRecipes(wool, vanillaWool, virtualAdditionsWool, "wool", RecipeCategory.BUILDING_BLOCKS);
            this.completeDyablesRecipes(carpets, vanillaCarpets, virtualAdditionsCarpets, "carpet", RecipeCategory.BUILDING_BLOCKS);
            this.completeDyablesRecipes(harnesses, vanillaHarnesses, virtualAdditionsHarnesses, "harness", RecipeCategory.BUILDING_BLOCKS);
            this.colorWithDye(dyes, silkbulbs, VAItems.SILKBULB, "silkbulb", RecipeCategory.BUILDING_BLOCKS);

            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.of(VAItems.CHARTREUSE_DYE), VAItems.CHARTREUSE_BUNDLE).unlockedBy(getHasName(VAItems.CHARTREUSE_DYE), this.has(VAItems.CHARTREUSE_DYE)).group("bundle_dye").save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.of(VAItems.MAROON_DYE), VAItems.MAROON_BUNDLE).unlockedBy(getHasName(VAItems.MAROON_DYE), this.has(VAItems.MAROON_DYE)).group("bundle_dye").save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.of(VAItems.INDIGO_DYE), VAItems.INDIGO_BUNDLE).unlockedBy(getHasName(VAItems.INDIGO_DYE), this.has(VAItems.INDIGO_DYE)).group("bundle_dye").save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.of(VAItems.PLUM_DYE), VAItems.PLUM_BUNDLE).unlockedBy(getHasName(VAItems.PLUM_DYE), this.has(VAItems.PLUM_DYE)).group("bundle_dye").save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.of(VAItems.VIRIDIAN_DYE), VAItems.VIRIDIAN_BUNDLE).unlockedBy(getHasName(VAItems.VIRIDIAN_DYE), this.has(VAItems.VIRIDIAN_DYE)).group("bundle_dye").save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.of(VAItems.TAN_DYE), VAItems.TAN_BUNDLE).unlockedBy(getHasName(VAItems.TAN_DYE), this.has(VAItems.TAN_DYE)).group("bundle_dye").save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.of(VAItems.SINOPIA_DYE), VAItems.SINOPIA_BUNDLE).unlockedBy(getHasName(VAItems.SINOPIA_DYE), this.has(VAItems.SINOPIA_DYE)).group("bundle_dye").save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(this.registryLookup.getOrThrow(ItemTags.BUNDLES)), Ingredient.of(VAItems.LILAC_DYE), VAItems.LILAC_BUNDLE).unlockedBy(getHasName(VAItems.LILAC_DYE), this.has(VAItems.LILAC_DYE)).group("bundle_dye").save(output);

            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(Items.SHULKER_BOX), Ingredient.of(VAItems.CHARTREUSE_DYE), VAItems.CHARTREUSE_SHULKER_BOX).unlockedBy("has_shulker_box", this.has(Items.SHULKER_BOX)).save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(Items.SHULKER_BOX), Ingredient.of(VAItems.MAROON_DYE), VAItems.MAROON_SHULKER_BOX).unlockedBy("has_shulker_box", this.has(Items.SHULKER_BOX)).save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(Items.SHULKER_BOX), Ingredient.of(VAItems.INDIGO_DYE), VAItems.INDIGO_SHULKER_BOX).unlockedBy("has_shulker_box", this.has(Items.SHULKER_BOX)).save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(Items.SHULKER_BOX), Ingredient.of(VAItems.PLUM_DYE), VAItems.PLUM_SHULKER_BOX).unlockedBy("has_shulker_box", this.has(Items.SHULKER_BOX)).save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(Items.SHULKER_BOX), Ingredient.of(VAItems.VIRIDIAN_DYE), VAItems.VIRIDIAN_SHULKER_BOX).unlockedBy("has_shulker_box", this.has(Items.SHULKER_BOX)).save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(Items.SHULKER_BOX), Ingredient.of(VAItems.TAN_DYE), VAItems.TAN_SHULKER_BOX).unlockedBy("has_shulker_box", this.has(Items.SHULKER_BOX)).save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(Items.SHULKER_BOX), Ingredient.of(VAItems.SINOPIA_DYE), VAItems.SINOPIA_SHULKER_BOX).unlockedBy("has_shulker_box", this.has(Items.SHULKER_BOX)).save(output);
            TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(Items.SHULKER_BOX), Ingredient.of(VAItems.LILAC_DYE), VAItems.LILAC_SHULKER_BOX).unlockedBy("has_shulker_box", this.has(Items.SHULKER_BOX)).save(output);

            this.createColoringRecipeSet(ItemTags.BUNDLES, bundles);
            this.createColoringRecipeSet(ItemTags.HARNESSES, harnesses);

            for(int i = 0; i < virtualAdditionsHarnesses.size(); ++i) {
                this.harness(virtualAdditionsHarnesses.get(i), virtualAdditionsWool.get(i));
            }

            this.offerColoringStationRecipes(
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

            this.trimSmithing(VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE, VAArmorTrimPatterns.EXOSKELETON, ResourceKey.create(Registries.RECIPE, idOf("exoskeleton_armor_trim_smithing_template_smithing_trim")));
            this.copySmithingTemplate(VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE, VAItems.SILK_BLOCK);

            this.trimSmithing(VAItems.ROBE_ARMOR_TRIM_SMITHING_TEMPLATE, VAArmorTrimPatterns.ROBE, ResourceKey.create(Registries.RECIPE, idOf("robe_armor_trim_smithing_template_smithing_trim")));
            this.copySmithingTemplate(VAItems.ROBE_ARMOR_TRIM_SMITHING_TEMPLATE, VAItems.SPECTRAL_SAND);

            this.offerStonecuttingRecipes(VABlocks.ROCK_SALT_BLOCK, VACollections.ROCK_SALT_BRICKS);
            this.generateCuttableFamilyChain(VACollections.ROCK_SALT_BRICKS);
            this.offer2x2ConversionChain(VABlocks.ROCK_SALT_BLOCK, VABlocks.ROCK_SALT_BRICKS);
            this.chiseled(RecipeCategory.BUILDING_BLOCKS, VAItems.CHISELED_ROCK_SALT_BRICKS, VAItems.ROCK_SALT_BRICK_SLAB);
            this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, VAItems.CHISELED_ROCK_SALT_BRICKS, VAItems.ROCK_SALT_BRICKS);
            this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, VAItems.CHISELED_ROCK_SALT_BRICKS, VAItems.ROCK_SALT_BLOCK);

            this.generateRecipes(VACollections.SOULBLOOM, FeatureFlags.VANILLA_SET);
            this.woodFromLogs(VAItems.SOULBLOOM_WOOD, VAItems.SOULBLOOM_LOG);
            this.woodFromLogs(VAItems.STRIPPED_SOULBLOOM_WOOD, VAItems.STRIPPED_SOULBLOOM_LOG);
            this.planksFromLog(VAItems.SOULBLOOM_PLANKS, VAItemTags.SOULBLOOM_LOGS, 4);
            this.hangingSign(VAItems.SOULBLOOM_HANGING_SIGN, VAItems.STRIPPED_SOULBLOOM_LOG);
            this.offerHedgeRecipe(VABlocks.SOULBLOOM_HEDGE, VABlocks.SOULBLOOM_LEAVES);
            this.woodenBoat(VAItems.SOULBLOOM_BOAT, VABlocks.SOULBLOOM_PLANKS);
            this.chestBoat(VAItems.SOULBLOOM_CHEST_BOAT, VAItems.SOULBLOOM_BOAT);
            this.shelf(VAItems.SOULBLOOM_SHELF, VAItems.STRIPPED_SOULBLOOM_LOG);

            this.generateRecipes(VACollections.WITHERED, FeatureFlags.VANILLA_SET);
            this.woodFromLogs(VAItems.WITHERED_WOOD, VAItems.WITHERED_LOG);
            this.woodFromLogs(VAItems.STRIPPED_WITHERED_WOOD, VAItems.STRIPPED_WITHERED_LOG);
            this.planksFromLog(VAItems.WITHERED_PLANKS, VAItemTags.WITHERED_LOGS, 4);
            this.hangingSign(VAItems.WITHERED_HANGING_SIGN, VAItems.STRIPPED_WITHERED_LOG);
            this.offerHedgeRecipe(VABlocks.WITHERED_HEDGE, VABlocks.WITHERED_LEAVES);
            this.shelf(VAItems.WITHERED_SHELF, VAItems.STRIPPED_WITHERED_LOG);

            this.generateRecipes(VACollections.ZEBRANO, FeatureFlags.VANILLA_SET);
            this.woodFromLogs(VAItems.ZEBRANO_WOOD, VAItems.ZEBRANO_LOG);
            this.woodFromLogs(VAItems.STRIPPED_ZEBRANO_WOOD, VAItems.STRIPPED_ZEBRANO_LOG);
            this.planksFromLog(VAItems.ZEBRANO_PLANKS, VAItemTags.ZEBRANO_LOGS, 4);
            this.hangingSign(VAItems.ZEBRANO_HANGING_SIGN, VAItems.STRIPPED_ZEBRANO_LOG);
            this.woodenBoat(VAItems.ZEBRANO_BOAT, VABlocks.ZEBRANO_PLANKS);
            this.chestBoat(VAItems.ZEBRANO_CHEST_BOAT, VAItems.ZEBRANO_BOAT);
            this.shelf(VAItems.ZEBRANO_SHELF, VAItems.STRIPPED_ZEBRANO_LOG);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.BONE_PILE, 4)
                    .pattern("bBb")
                    .define('B', Items.BONE_BLOCK).define('b', Items.BONE)
                    .unlockedBy("has_bone_block", has(Items.BONE_BLOCK)).save(output);
            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.BONE_LITTER, 2)
                            .requires(VAItems.BONE_PILE).unlockedBy("has_bone_pile", has(VAItems.BONE_PILE)).save(output);
            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, Items.BONE_MEAL)
                            .requires(VAItems.BONE_LITTER).unlockedBy("has_bone_litter", has(VAItems.BONE_PILE)).save(output);

            threeByThreePacker(RecipeCategory.BUILDING_BLOCKS, VABlocks.SPECTRAL_SAND, VAItems.SPECTRAL_POWDER);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, VAItems.SPECTRAL_TORCH, 4)
                            .pattern("c")
                            .pattern("/")
                            .pattern("p")
                            .define('c', Items.COAL).define('/', Items.STICK).define('p', VAItems.SPECTRAL_POWDER)
                    .unlockedBy("has_spectral_powder", has(VAItems.SPECTRAL_POWDER)).save(output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, VAItems.SPECTRAL_LANTERN)
                            .pattern("iii")
                            .pattern("iti")
                            .pattern("iii")
                            .define('i', Items.IRON_NUGGET).define('t', VAItems.SPECTRAL_TORCH)
                    .unlockedBy("has_spectral_torch", has(VAItems.SPECTRAL_TORCH)).save(output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.TOOLS, VAItems.SPECTRAL_SPYGLASS)
                            .pattern("sss")
                            .pattern("sps")
                            .pattern("sss")
                            .define('s', VAItems.SPECTRAL_POWDER).define('p', Items.SPYGLASS)
                    .unlockedBy("has_spectral_powder", has(VAItems.SPECTRAL_POWDER)).save(output);


            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.MAROON_DYE, 2)
                    .requires(Ingredient.of(Items.RED_DYE), 1)
                    .requires(Ingredient.of(Items.BLACK_DYE), 1)
                    .unlockedBy("has_dye", has(Items.RED_DYE)).save(this.output);


            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.CHARTREUSE_DYE, 2).group("chartreuse_dye")
                    .requires(Ingredient.of(Items.LIME_DYE), 1)
                    .requires(Ingredient.of(Items.YELLOW_DYE), 1)
                    .unlockedBy("has_dye", has(Items.LIME_DYE)).save(this.output);

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.CHARTREUSE_DYE, 3).group("chartreuse_dye")
                    .requires(Ingredient.of(Items.GREEN_DYE), 1)
                    .requires(Ingredient.of(Items.WHITE_DYE), 1)
                    .requires(Ingredient.of(Items.YELLOW_DYE), 1)
                    .unlockedBy("has_dye", has(Items.YELLOW_DYE)).save(this.output, idOf("chartreuse_dye_from_green_white_yellow_dye").toString());

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.INDIGO_DYE, 2).group("indigo_dye")
                    .requires(Ingredient.of(Items.BLUE_DYE), 1)
                    .requires(Ingredient.of(Items.PURPLE_DYE), 1)
                    .unlockedBy("has_dye", has(Items.PURPLE_DYE)).save(this.output);

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.INDIGO_DYE, 3).group("indigo_dye")
                    .requires(Ingredient.of(Items.BLUE_DYE), 2)
                    .requires(Ingredient.of(Items.RED_DYE), 1)
                    .unlockedBy("has_dye", has(Items.BLUE_DYE)).save(this.output, idOf("indigo_dye_from_blue_blue_red_dye").toString());

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.PLUM_DYE, 2).group("plum_dye")
                    .requires(Ingredient.of(Items.PURPLE_DYE), 1)
                    .requires(Ingredient.of(VAItems.MAROON_DYE), 1)
                    .unlockedBy("has_dye", has(Items.PURPLE_DYE)).save(this.output);

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.PLUM_DYE, 3).group("plum_dye")
                    .requires(Ingredient.of(Items.BLUE_DYE), 1)
                    .requires(Ingredient.of(Items.RED_DYE), 1)
                    .requires(Ingredient.of(VAItems.MAROON_DYE), 1)
                    .unlockedBy("has_dye", has(VAItems.MAROON_DYE)).save(this.output, idOf("plum_from_blue_red_maroon_dye").toString());

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.PLUM_DYE, 4).group("plum_dye")
                    .requires(Ingredient.of(Items.BLUE_DYE), 1)
                    .requires(Ingredient.of(Items.RED_DYE), 2)
                    .requires(Ingredient.of(Items.BLACK_DYE), 1)
                    .unlockedBy("has_dye", has(Items.RED_DYE)).save(this.output, idOf("plum_from_blue_red_red_black_dye").toString());

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.VIRIDIAN_DYE, 2).group("viridian_dye")
                    .requires(Ingredient.of(Items.GREEN_DYE), 1)
                    .requires(Ingredient.of(Items.CYAN_DYE), 1)
                    .unlockedBy("has_dye", has(Items.CYAN_DYE)).save(this.output);

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.VIRIDIAN_DYE, 3).group("viridian_dye")
                    .requires(Ingredient.of(Items.GREEN_DYE), 2)
                    .requires(Ingredient.of(Items.BLUE_DYE), 1)
                    .unlockedBy("has_dye", has(Items.GREEN_DYE)).save(this.output, idOf("viridian_from_green_green_blue_dye").toString());

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.TAN_DYE, 2).group("tan_dye")
                    .requires(Ingredient.of(Items.ORANGE_DYE), 1)
                    .requires(Ingredient.of(Items.GRAY_DYE), 1)
                    .unlockedBy("has_dye", has(Items.ORANGE_DYE)).save(this.output);

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.TAN_DYE, 3).group("tan_dye")
                    .requires(Ingredient.of(Items.RED_DYE), 1)
                    .requires(Ingredient.of(Items.YELLOW_DYE), 1)
                    .requires(Ingredient.of(Items.GRAY_DYE), 1)
                    .unlockedBy("has_dye", has(Items.GRAY_DYE)).save(this.output, idOf("tan_from_red_yellow_gray_dye").toString());

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.TAN_DYE, 4).group("tan_dye")
                    .requires(Ingredient.of(Items.RED_DYE), 1)
                    .requires(Ingredient.of(Items.YELLOW_DYE), 1)
                    .requires(Ingredient.of(Items.WHITE_DYE), 1)
                    .requires(Ingredient.of(Items.BLACK_DYE), 1)
                    .unlockedBy("has_dye", has(Items.RED_DYE)).save(this.output, idOf("tan_from_red_yellow_black_white_dye").toString());

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.SINOPIA_DYE, 2).group("sinopia_dye")
                    .requires(Ingredient.of(Items.RED_DYE), 1)
                    .requires(Ingredient.of(Items.ORANGE_DYE), 1)
                    .unlockedBy("has_dye", has(Items.ORANGE_DYE)).save(this.output);

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.SINOPIA_DYE, 3).group("sinopia_dye")
                    .requires(Ingredient.of(Items.RED_DYE), 2)
                    .requires(Ingredient.of(Items.YELLOW_DYE), 1)
                    .unlockedBy("has_dye", has(Items.RED_DYE)).save(this.output, idOf("sinopia_from_red_red_yellow_dye").toString());

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.LILAC_DYE, 2).group("lilac_dye")
                    .requires(Ingredient.of(Items.WHITE_DYE), 1)
                    .requires(Ingredient.of(Items.PURPLE_DYE), 1)
                    .unlockedBy("has_dye", has(Items.PURPLE_DYE)).save(this.output);

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.MISC, VAItems.LILAC_DYE, 3).group("lilac_dye")
                    .requires(Ingredient.of(Items.WHITE_DYE), 1)
                    .requires(Ingredient.of(Items.RED_DYE), 1)
                    .requires(Ingredient.of(Items.BLUE_DYE), 1)
                    .unlockedBy("has_dye", has(Items.WHITE_DYE)).save(this.output, idOf("lilac_from_white_red_blue_dye").toString());

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.FOOD, VAItems.SWEET_BERRY_PIE, 1)
                    .requires(Items.SWEET_BERRIES, 3)
                    .requires(Items.SUGAR)
                    .requires(Items.EGG).unlockedBy("has_sweet_berries", has(Items.SWEET_BERRIES)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.TOOLS, VAItems.CLIMBING_ROPE, 4)
                    .pattern("#")
                    .pattern("#")
                    .pattern("l")
                    .define('#', Items.COPPER_INGOT).define('l', Items.LEAD)
                    .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, VABlocks.SILKBULB, 1)
                    .pattern("###")
                    .pattern("#b#")
                    .pattern("###")
                    .define('#', VAItems.SILK_THREAD).define('b', VAItems.ACID_BLOCK)
                    .unlockedBy("has_acid_block", has(VAItems.ACID_BLOCK)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.REDSTONE, VABlocks.ENTANGLEMENT_DRIVE, 1)
                    .pattern("s#s")
                    .pattern("sNs")
                    .pattern("s#s")
                    .define('#', VAItems.IOLITE).define('s', VAItems.STEEL_INGOT).define('N', Items.NETHER_STAR)
                    .unlockedBy("has_iolite", has(VAItems.IOLITE)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.TRANSPORTATION, VAItems.PORTAL_CORE, 1)
                    .pattern("eee")
                    .pattern("eye")
                    .pattern("eee")
                    .define('e', VAItems.IOLITE).define('y', Items.ENDER_PEARL)
                    .unlockedBy("has_iolite", has(VAItems.IOLITE)).group("portal_core").save(this.output);

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.TRANSPORTATION, VAItems.PORTAL_CORE, 1)
                     .requires(VAItems.DRAINED_PORTAL_CORE)
                     .requires(VAItems.IOLITE, 2)
                     .unlockedBy("has_drained_portal_core", has(VAItems.DRAINED_PORTAL_CORE)).group("portal_core").save(this.output, "portal_core_restoration");

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.COMBAT, VAItems.STEEL_BOMB, 4)
                    .pattern(" s ")
                    .pattern("#g#")
                    .define('#', VAItems.STEEL_INGOT).define('s', Items.STRING).define('g', Items.GUNPOWDER)
                    .unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.REDSTONE, VAItems.REDSTONE_BRIDGE, 1)
                    .pattern("#")
                    .pattern("r")
                    .pattern("#")
                    .define('#', VAItems.STEEL_NUGGET).define('r', Items.REDSTONE)
                    .unlockedBy("has_steel_nugget", has(VAItems.STEEL_NUGGET)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.CAGELIGHT, 1)
                    .pattern("#")
                    .pattern("g")
                    .pattern("#")
                    .define('#', VAItems.STEEL_NUGGET).define('g', Items.GLOWSTONE_DUST)
                    .unlockedBy("has_steel_nugget", has(VAItems.STEEL_NUGGET)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.MISC, VAItems.COLORING_STATION, 1)
                    .pattern("BB")
                    .pattern("##")
                    .pattern("##")
                    .define('#', Ingredient.of(this.registryLookup.getOrThrow(ItemTags.PLANKS))).define('B', Ingredient.of(this.registryLookup.getOrThrow(ItemTags.WOOL)))
                    .unlockedBy("has_wool", has(ItemTags.WOOL)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.REDSTONE, VAItems.SPOTLIGHT, 1)
                    .pattern("ssa")
                    .pattern("rga")
                    .pattern("ssa")
                    .define('a', Items.AMETHYST_SHARD).define('g', Items.GLOWSTONE).define('s', VAItems.STEEL_INGOT).define('r', Items.REDSTONE)
                    .unlockedBy("has_glowstone", has(Items.GLOWSTONE)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.FOOD, VAItems.ICE_CREAM, 1)
                    .pattern(" s ")
                    .pattern("bmb")
                    .pattern(" w ")
                    .define('s', VAItems.ROCK_SALT).define('b', Items.SNOWBALL).define('m', Items.MILK_BUCKET).define('w', Items.BOWL)
                    .unlockedBy("has_milk", has(Items.MILK_BUCKET)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.ENGRAVING_CHISEL, 1)
                    .pattern("c")
                    .pattern("s")
                    .pattern("c")
                    .define('c', ItemTags.STONE_CRAFTING_MATERIALS)
                    .define('s', VAItems.STEEL_INGOT)
                    .unlockedBy("has_steel_ingot", has(VAItems.STEEL_INGOT)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.MISC, VAItems.SOY_OIL_BUCKET, 1)
                    .pattern("sss")
                    .pattern("sus")
                    .pattern("sss")
                    .define('s', VAItems.SOY_BEANS)
                    .define('u', Items.BUCKET)
                    .unlockedBy("has_soy_beans", has(VAItems.SOY_BEANS)).save(this.output);

            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.MISC, VAItems.SLINGSHOT, 1)
                    .pattern("L")
                    .pattern("/")
                    .define('L', Items.LEATHER)
                    .define('/', Items.STICK)
                    .unlockedBy("has_leather", has(Items.LEATHER)).save(this.output);

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.DECORATIONS, Items.LIGHT_BLUE_DYE)
                    .requires(VAItems.BLUE_PETALS)
                    .unlockedBy("has_blue_petals", has(VAItems.BLUE_PETALS))
                            .save(this.output, "virtual_additions:light_blue_dye_from_blue_petals");

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.DECORATIONS, VAItems.PLUM_DYE)
                    .requires(VAItems.SMALL_SPRING_LOTUS)
                    .unlockedBy("has_small_spring_lotus", has(VAItems.SMALL_SPRING_LOTUS))
                            .save(this.output, "virtual_additions:orange_dye_from_small_spring_lotus");

            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.DECORATIONS, Items.LIGHT_BLUE_DYE)
                    .requires(VAItems.SOUL_SPROUT)
                    .unlockedBy("has_soul_sprout", has(VAItems.SOUL_SPROUT))
                    .save(this.output, "virtual_additions:orange_dye_from_soul_sprout");

            this.offerIncenseRecipes(output);
            this.offerWaxingRecipes(output);
        }
    }

    private static class PreviewGenerator extends Generator {

        protected PreviewGenerator(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
            super(registryLookup, exporter);
        }

        @Override
        public void buildRecipes() {

        }
    }
    private abstract static class Generator extends RecipeProvider {
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


        protected final HolderGetter<Item> registryLookup;
        
        protected Generator(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
            super(registryLookup, exporter);
            this.registryLookup = registries.lookupOrThrow(Registries.ITEM);
        }

        protected void offerCookingRecipes(ItemLike output, List<ItemLike> inputs, float experience, String group) {
            oreSmelting(inputs, RecipeCategory.FOOD, output, experience, 200, group);
            this.oreCooking(RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, inputs, RecipeCategory.FOOD, output, experience, 100, group, "_from_smoking");
            this.oreCooking(RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, inputs, RecipeCategory.FOOD, output, experience, 600, group, "_from_campfire_cooking");
        }

        protected void offerCookingRecipes(ItemLike output, ItemLike input, float experience, String group) {
            oreSmelting(List.of(input), RecipeCategory.FOOD, output, experience, 200, group);
            simpleCookingRecipe("smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100, input, output, experience);
            simpleCookingRecipe("campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600, input, output, experience);
        }

        protected void offerColoringRecipe(ItemLike input, ItemLike output, DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(Ingredient.of(input), cost, output, index).offerTo(this.output, idOf(getItemName(output)).withSuffix("_coloring"));
        }

        protected void offerColoringRecipe(ItemLike output, DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(null, cost, output, index).offerTo(this.output, idOf(getItemName(output)).withSuffix("_coloring"));
        }

        protected void offerColoringRecipe(TagKey<Item> input, ItemLike output, DyeContents cost, int index) {
            ColoringRecipeJsonBuilder.create(Ingredient.of(this.registryLookup.getOrThrow(input)), cost, output, index).offerTo(this.output, idOf(getItemName(output)).withSuffix("_coloring"));
        }

        protected void offerArmorColoringRecipe(DyeItem input, int i) {
            ArmorColoringRecipeJsonBuilder.create(Ingredient.of(input), i).offerTo(this.output, idOf(input.getDyeColor().getSerializedName()).withSuffix("_armor_coloring"));
        }

        protected void offer2x2ConversionChain(Block... blocks) {
            for (int i = 0; i < blocks.length - 1; i++) {
                Block input = blocks[i];
                Block output = blocks[i + 1];
                String id = BuiltInRegistries.BLOCK.getKey(output).getPath() + "_from_compacting_" + BuiltInRegistries.BLOCK.getKey(input).getPath();
                ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', input).unlockedBy(getHasName(input), has(input)).save(this.output, idOf(id).toString());
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
            generateRecipes(baseFamily, FeatureFlags.VANILLA_SET);
            offerStonecuttingRecipes(baseFamily, baseFamily);
            for (BlockFamily subFamily : subFamilies) {
                offerStonecuttingRecipes(baseFamily, subFamily);
            }
        }

        protected void generateColorfulBlockSetRecipes(ColorfulBlockSet set, Item dye) {
            set.ifWool(wool -> {
                set.ifBed( bed -> bedFromPlanksAndWool(bed, wool));
                set.ifBanner(banner -> banner(banner, wool));
                set.ifCarpet(carpet -> carpet(carpet, wool));
            });
            set.ifConcretePowder( block -> concretePowder(block, dye));
            set.ifTerracotta(block -> coloredTerracottaFromTerracottaAndDye(block, dye));
            set.ifStainedGlass(block -> {
                stainedGlassFromGlassAndDye(block, dye);
                set.ifStainedGlassPane(pane -> {
                    stainedGlassPaneFromStainedGlass(pane, block);
                    stainedGlassPaneFromGlassPaneAndDye(pane, dye);
                });
            });
            set.ifCandle(block -> candle(block, dye));
        }

        protected void offerStonecuttingRecipes(BlockFamily baseFamily, BlockFamily... resultFamilies) {
            Block block;
            for (BlockFamily resultFamily : resultFamilies) {
                if (!baseFamily.getBaseBlock().equals(resultFamily.getBaseBlock()))
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, resultFamily.getBaseBlock(), baseFamily.getBaseBlock());
                if ((block = resultFamily.get(BlockFamily.Variant.STAIRS)) != null)
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock());
                if ((block = resultFamily.get(BlockFamily.Variant.SLAB)) != null)
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock(), 2);
                if ((block = resultFamily.get(BlockFamily.Variant.WALL)) != null)
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock());
                if ((block = resultFamily.get(BlockFamily.Variant.CHISELED)) != null)
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, block, baseFamily.getBaseBlock());
            }
        }

        protected void offerStonecuttingRecipes(Block baseBlock, BlockFamily... resultFamilies) {
            Block block;
            for (BlockFamily resultFamily : resultFamilies) {
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, resultFamily.getBaseBlock(), baseBlock);
                if ((block = resultFamily.get(BlockFamily.Variant.STAIRS)) != null)
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, block, baseBlock);
                if ((block = resultFamily.get(BlockFamily.Variant.SLAB)) != null)
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, block, baseBlock, 2);
                if ((block = resultFamily.get(BlockFamily.Variant.WALL)) != null)
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, block, baseBlock);
                if ((block = resultFamily.get(BlockFamily.Variant.CHISELED)) != null)
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, block, baseBlock);
            }
        }

        protected void offerToolGildRecipe(TagKey<Item> tag, Item addition, GildType type) {
            offerToolGildRecipe(tag, Ingredient.of(addition), type);
        }
        protected void offerToolGildRecipe(TagKey<Item> tag, Ingredient addition, GildType type) {
            SmithingGildRecipeJsonBuilder.create(
                    Ingredient.of(this.registryLookup.getOrThrow(tag)),
                    addition,
                    VARegistries.GILD_TYPE.wrapAsHolder(type)
            )
                    .criterion("has_smithing_template", has(VAItems.TOOL_GILD_SMITHING_TEMPLATE))
                    .offerTo(this.output, idOf("smithing/" + VARegistries.GILD_TYPE.getKey(type).getPath() + "_gild"));
        }

        protected void offerNetheriteUpgradeRecipe(Item input, Item result) {
            SmithingTransformRecipeBuilder.smithing(
                            Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                            Ingredient.of(input),
                            Ingredient.of(Items.NETHERITE_INGOT),
                            RecipeCategory.TOOLS,
                            result)
                    .unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                    .save(this.output, idOf("smithing/" + getItemName(result) + "_upgrade").toString());
        }

        @SafeVarargs
        protected final void offerShapelessRecipe(RecipeCategory category, ItemLike output, int count, Pair<ItemLike, Integer>... input) {
            ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(this.registryLookup, category, output, count);
            builder.unlockedBy(getHasName(input[0].getLeft()), has(input[0].getLeft()));
            for (Pair<ItemLike, Integer> itemProvider : input) {
                builder.requires(itemProvider.getLeft(), itemProvider.getRight());
            }
            builder.save(this.output);
        }

        @SafeVarargs
        protected final void offerShapelessRecipe(RecipeCategory category, ItemLike output, int count, String group, Pair<ItemLike, Integer>... input) {
            ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(this.registryLookup, category, output, count);
            builder.unlockedBy(getHasName(input[0].getLeft()), has(input[0].getLeft()));
            for (Pair<ItemLike, Integer> itemProvider : input) {
                builder.requires(itemProvider.getLeft(), itemProvider.getRight());
            }
            builder.group(group);
            builder.save(this.output);
        }

        protected void offer2x2FullRecipe(RecipeCategory category, ItemLike output, ItemLike input, int count) {
            ShapedRecipeBuilder.shaped(this.registryLookup, category, output, count).define('#', input).pattern("##").pattern("##").unlockedBy(getHasName(input), has(input)).save(this.output, idOf(getItemName(output) + "_from_" + getItemName(input)).toString());
        }

        protected void offer2x2FullRecipe(RecipeCategory category, ItemLike output, ItemLike input, int count, String group) {
            ShapedRecipeBuilder.shaped(this.registryLookup, category, output, count).define('#', input).pattern("##").pattern("##").group(group).unlockedBy(getHasName(input), has(input)).save(this.output, idOf(getItemName(output) + "_from_" + getItemName(input)).toString());
        }

        protected void offerHedgeRecipe(ItemLike output, ItemLike input) {
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, output, 6)
                    .pattern("###")
                    .pattern("###")
                    .define('#', input).unlockedBy("has_leaves", has(input)).group("hedges").save(this.output);
        }

        public void offerWaxingRecipes(RecipeOutput exporter) {
            HoneycombItem.WAXABLES.get().forEach((unwaxed, waxed) -> {
                if (!VirtualAdditions.isFromMod(BuiltInRegistries.BLOCK.getKey(unwaxed))) return;
                ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.BUILDING_BLOCKS, waxed).requires(unwaxed).requires(Items.HONEYCOMB).group(RecipeProvider.getItemName(waxed)).unlockedBy(RecipeProvider.getHasName(unwaxed), this.has(unwaxed)).save(this.output, idOf(RecipeProvider.getConversionRecipeName(waxed, Items.HONEYCOMB)).toString());
            });
        }

        public void offerSteelRecipeSet(Block block, Block cut, Block cutStairs, Block cutSlab, Block grate, Block chiseled){
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, cutStairs, cut);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, cutSlab, cut, 2);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, grate, cut, 1);
            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, chiseled, cut, 1);
            ShapedRecipeBuilder.shaped(this.registryLookup,RecipeCategory.DECORATIONS, cutStairs, 4).define('#', cut).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_item", has(cut)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup,RecipeCategory.DECORATIONS, cutSlab, 6).define('#', cut).pattern("###").unlockedBy("has_item", has(cut)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup,RecipeCategory.DECORATIONS, chiseled, 1).define('#', cutSlab).pattern("#").pattern("#").unlockedBy("has_item", has(cutSlab)).save(this.output);
            ShapedRecipeBuilder.shaped(this.registryLookup,RecipeCategory.DECORATIONS, grate, 4).pattern(" # ").pattern("# #").pattern(" # ").define('#', cut).unlockedBy("has_item", has(cut)).save(this.output);
        }

        public void offerJerkyFoodRecipe(Item input, Item jerky) {
            ShapelessRecipeBuilder.shapeless(this.registryLookup, RecipeCategory.FOOD, jerky, 3)
                    .requires(input)
                    .requires(VAItems.ROCK_SALT, 2)
                    .unlockedBy("has_item", has(input)).save(output);
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

                this.shapeless(category, dyedItem).requires(dye).requires(Ingredient.of(stream)).group(group).unlockedBy("has_needed_dye", this.has(dye)).save(this.output, "virtual_additions:dye_" + getItemName(dyedItem));
            }

        }

        public void colorWithDye(List<Item> dyes, List<Item> dyeables, @Nullable Item undyed, String group, RecipeCategory category) {
            for(int i = 0; i < dyes.size(); ++i) {
                Item item = dyes.get(i);
                Item item2 = dyeables.get(i);
                Stream<Item> stream = dyeables.stream().filter((itemx) -> !itemx.equals(item2));
                if (undyed != null) {
                    stream = Stream.concat(stream, Stream.of(undyed));
                }

                this.shapeless(category, item2).requires(item).requires(Ingredient.of(stream)).group(group).unlockedBy("has_needed_dye", this.has(item)).save(this.output, "virtual_additions:dye_" + getItemName(item2));
            }

        }
        
        protected void createColoringRecipeSet(TagKey<Item> inputTag, List<Item> items) {
            for(int i = 0; i < dyeCosts.size(); ++i) {
                DyeContents cost = dyeCosts.get(i);
                Item dyedItem = items.get(i);
                
                ColoringRecipeJsonBuilder.create(Ingredient.of(this.registryLookup.getOrThrow(inputTag)), 
                        cost, dyedItem, VADyeColors.getIndex(cost))
                        .offerTo(this.output, idOf(getItemName(dyedItem)).withSuffix("_coloring"));

            }
            

        }

        protected void createPlanterRecipe(ItemLike woodenSlab, ItemLike output) {
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.DECORATIONS, output, 4)
                    .pattern("#D#")
                    .pattern("###")
                    .define('#', woodenSlab).define('D', Items.DIRT).unlockedBy("has_material", has(woodenSlab)).save(this.output);
        }
        
        protected void createHalberdRecipe(ItemLike material, Item output) {
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.COMBAT, output)
                    .pattern("## ")
                    .pattern("#/#")
                    .pattern(" / ")
                    .define('#', material).define('/', Items.STICK).unlockedBy("has_material", has(material)).save(this.output);
        }

        protected void createHalberdRecipe(TagKey<Item> material, Item output) {
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.COMBAT, output)
                    .pattern("## ")
                    .pattern("#/#")
                    .pattern(" / ")
                    .define('#', material).define('/', Items.STICK).unlockedBy("has_material", has(material)).save(this.output);
        }

        protected void offerIncenseRecipes(RecipeOutput output) {

        };

        protected void offerIncenseRecipe(Ingredient ingredient) {
            ShapedRecipeBuilder.shaped(this.registryLookup, RecipeCategory.COMBAT, VAItems.INCENSE)
                    .pattern("C")
                    .pattern("#")
                    .pattern("/")
                    .define('C', ingredient).define('/', Items.STICK).define('#', ItemTags.COALS)
                    .save(this.output);
        };
    }


}
