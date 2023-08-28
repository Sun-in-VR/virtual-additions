package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Consumer;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

class VARecipeProvider extends FabricRecipeProvider {
    public VARecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        offerBlasting(exporter, List.of(VAItems.RAW_STEEL), RecipeCategory.MISC, VAItems.STEEL_INGOT, 1.0F, 100, "steel_ingot");
        offerBlasting(exporter, List.of(VAItems.IOLITE_ORE), RecipeCategory.MISC, VAItems.IOLITE, 1.0F, 100, "iolite");
        offerSmelting(exporter, List.of(VAItems.IOLITE_ORE), RecipeCategory.MISC, VAItems.IOLITE, 1.0F, 200, "iolite");

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

        offerHedgeRecipe(exporter, VABlocks.OAK_HEDGE, Blocks.OAK_LEAVES);
        offerHedgeRecipe(exporter, VABlocks.SPRUCE_HEDGE, Blocks.SPRUCE_LEAVES);
        offerHedgeRecipe(exporter, VABlocks.BIRCH_HEDGE, Blocks.BIRCH_LEAVES);
        offerHedgeRecipe(exporter, VABlocks.JUNGLE_HEDGE, Blocks.JUNGLE_LEAVES);
        offerHedgeRecipe(exporter, VABlocks.ACACIA_HEDGE, Blocks.ACACIA_LEAVES);
        offerHedgeRecipe(exporter, VABlocks.DARK_OAK_HEDGE, Blocks.DARK_OAK_LEAVES);
        offerHedgeRecipe(exporter, VABlocks.MANGROVE_HEDGE, Blocks.MANGROVE_LEAVES);
        offerHedgeRecipe(exporter, VABlocks.CHERRY_HEDGE, Blocks.CHERRY_LEAVES);
        offerHedgeRecipe(exporter, VABlocks.AEROBLOOM_HEDGE, VABlocks.AEROBLOOM_LEAVES);
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, VABlocks.CUT_STEEL_STAIRS, 4).input('#', VABlocks.CUT_STEEL).pattern("#  ").pattern("## ").pattern("###").criterion("cut_steel", conditionsFromItem(VABlocks.CUT_STEEL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, VABlocks.CUT_STEEL_SLAB, 6).input('#', VABlocks.CUT_STEEL).pattern("###").criterion("cut_steel", conditionsFromItem(VABlocks.CUT_STEEL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, VABlocks.STEEL_FENCE, 6).input('W', VABlocks.CUT_STEEL).input('#', VAItems.STEEL_INGOT).pattern("W#W").pattern("W#W").criterion("cut_steel", conditionsFromItem(VABlocks.CUT_STEEL)).offerTo(exporter);
        offer2x2FullRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL, VAItems.STEEL_INGOT, 16);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.CUT_STEEL, VABlocks.STEEL_BLOCK, 36);
        createDoorRecipe(VABlocks.STEEL_DOOR, Ingredient.ofItems(VAItems.STEEL_INGOT)).criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);
        createTrapdoorRecipe(VABlocks.STEEL_TRAPDOOR, Ingredient.ofItems(VAItems.STEEL_INGOT)).criterion("steel_ingot", conditionsFromItem(VAItems.STEEL_INGOT)).offerTo(exporter);

        offerSmelting(exporter, List.of(VABlocks.COBBLED_HORNFELS), RecipeCategory.BUILDING_BLOCKS, VABlocks.HORNFELS, 0.1F, 200, "hornfels");
        offerStonecuttingRecipes(exporter, VABlocks.HORNFELS, VABlockFamilies.POLISHED_HORNFELS, VABlockFamilies.HORNFELS_TILES);
        generateCuttableFamilyChain(exporter, VABlockFamilies.COBBLED_HORNFELS);
        generateCuttableFamilyChain(exporter, VABlockFamilies.POLISHED_HORNFELS, VABlockFamilies.HORNFELS_TILES);
        generateCuttableFamilyChain(exporter, VABlockFamilies.HORNFELS_TILES);
        offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.HORNFELS, VABlocks.POLISHED_HORNFELS);
        offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.POLISHED_HORNFELS, VABlocks.HORNFELS_TILES);

        offerSmelting(exporter, List.of(VABlocks.COBBLED_BLUESCHIST), RecipeCategory.BUILDING_BLOCKS, VABlocks.BLUESCHIST, 0.1F, 200, "blueschist");
        offerStonecuttingRecipes(exporter, VABlocks.BLUESCHIST, VABlockFamilies.POLISHED_BLUESCHIST, VABlockFamilies.BLUESCHIST_BRICKS);
        generateCuttableFamilyChain(exporter, VABlockFamilies.COBBLED_BLUESCHIST);
        generateCuttableFamilyChain(exporter, VABlockFamilies.POLISHED_BLUESCHIST, VABlockFamilies.BLUESCHIST_BRICKS);
        generateCuttableFamilyChain(exporter, VABlockFamilies.BLUESCHIST_BRICKS);
        offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.BLUESCHIST, VABlocks.POLISHED_BLUESCHIST);
        offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.POLISHED_BLUESCHIST, VABlocks.BLUESCHIST_BRICKS);

        offerSmelting(exporter, List.of(VABlocks.COBBLED_SYENITE), RecipeCategory.BUILDING_BLOCKS, VABlocks.SYENITE, 0.1F, 200, "syenite");
        offerStonecuttingRecipes(exporter, VABlocks.SYENITE, VABlockFamilies.POLISHED_SYENITE, VABlockFamilies.SYENITE_BRICKS);
        generateCuttableFamilyChain(exporter, VABlockFamilies.COBBLED_SYENITE);
        generateCuttableFamilyChain(exporter, VABlockFamilies.POLISHED_SYENITE, VABlockFamilies.SYENITE_BRICKS);
        generateCuttableFamilyChain(exporter, VABlockFamilies.SYENITE_BRICKS);
        offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.SYENITE, VABlocks.POLISHED_SYENITE);
        offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, VABlocks.POLISHED_SYENITE, VABlocks.SYENITE_BRICKS);

        generateCuttableFamilyChain(exporter, VABlockFamilies.FLOATROCK);

        generateFamily(exporter, VABlockFamilies.AEROBLOOM);
        offerBarkBlockRecipe(exporter, VAItems.AEROBLOOM_WOOD, VAItems.AEROBLOOM_LOG);
        offerBarkBlockRecipe(exporter, VAItems.STRIPPED_AEROBLOOM_WOOD, VAItems.STRIPPED_AEROBLOOM_LOG);
        offerPlanksRecipe2(exporter, VAItems.AEROBLOOM_PLANKS, VAItemTags.AEROBLOOM_LOGS, 4);
        offerHangingSignRecipe(exporter, VAItems.AEROBLOOM_HANGING_SIGN, VAItems.STRIPPED_AEROBLOOM_LOG);

        offer2x2FullRecipe(exporter, RecipeCategory.MISC, Items.STRING, VAItems.COTTON, 2);
        offerShapelessRecipe(exporter, VAItems.COTTON_SEEDS, VAItems.COTTON, "cotton_seeds", 1);

        offerCookingRecipes(exporter, VAItems.FRIED_EGG, Items.EGG, 0.35F, "fried_egg");
        offerCookingRecipes(exporter, VAItems.ROASTED_CORN, VAItems.CORN, 0.35F, "corn");

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
        offerToolGildRecipes(exporter, Items.QUARTZ, VAItems.QUARTZ_TOOL_SETS);
        offerToolGildRecipes(exporter, Items.ECHO_SHARD, VAItems.SCULK_TOOL_SETS);
        offerToolUpgradeRecipes(exporter, VAItems.AMETHYST_DIAMOND_TOOL_SET, VAItems.AMETHYST_NETHERITE_TOOL_SET);
        offerToolUpgradeRecipes(exporter, VAItems.COPPER_DIAMOND_TOOL_SET, VAItems.COPPER_NETHERITE_TOOL_SET);
        offerToolUpgradeRecipes(exporter, VAItems.EMERALD_DIAMOND_TOOL_SET, VAItems.EMERALD_NETHERITE_TOOL_SET);
        offerToolUpgradeRecipes(exporter, VAItems.QUARTZ_DIAMOND_TOOL_SET, VAItems.QUARTZ_NETHERITE_TOOL_SET);
        offerToolUpgradeRecipes(exporter, VAItems.SCULK_DIAMOND_TOOL_SET, VAItems.SCULK_NETHERITE_TOOL_SET);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, VAItems.CLIMBING_ROPE, 4)
                .pattern("#  ")
                .pattern(" #s")
                .pattern(" ss")
                .input('#', Items.COPPER_INGOT).input('s', Items.STRING)
                .criterion("copper_ingot", conditionsFromItem(Items.COPPER_INGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, VABlocks.SILKBULB, 1)
                .pattern("###")
                .pattern("#b#")
                .pattern("###")
                .input('#', VAItems.SILK_THREAD).input('b', VAItems.ACID_BUCKET)
                .criterion("acid_bucket", conditionsFromItem(VAItems.ACID_BUCKET)).offerTo(exporter);

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

    }

    public static void offerCookingRecipes(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input, float experience, String group) {
        offerSmelting(exporter, List.of(input), RecipeCategory.FOOD, output, experience, 200, group);
        offerFoodCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, 100, input, output, experience);
        offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, 600, input, output, experience);
    }

    public static void generateCuttableFamilyChain(Consumer<RecipeJsonProvider> exporter, BlockFamily baseFamily, BlockFamily... subFamilies) {
        generateFamily(exporter, baseFamily);
        offerStonecuttingRecipes(exporter, baseFamily, baseFamily);
        for (BlockFamily subFamily : subFamilies) {
            offerStonecuttingRecipes(exporter, baseFamily, subFamily);
        }
    }

    public static void offerStonecuttingRecipes(Consumer<RecipeJsonProvider> exporter, BlockFamily baseFamily, BlockFamily... resultFamilies) {
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
        }
    }

    public static void offerStonecuttingRecipes(Consumer<RecipeJsonProvider> exporter, Block baseBlock, BlockFamily... resultFamilies) {
        Block block;
        for (BlockFamily resultFamily : resultFamilies) {
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, resultFamily.getBaseBlock(), baseBlock);
            if ((block = resultFamily.getVariant(BlockFamily.Variant.STAIRS)) != null)
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, block, baseBlock);
            if ((block = resultFamily.getVariant(BlockFamily.Variant.SLAB)) != null)
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, block, baseBlock, 2);
            if ((block = resultFamily.getVariant(BlockFamily.Variant.WALL)) != null)
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, block, baseBlock);
        }
    }

    public static void offerToolGildRecipe(Consumer<RecipeJsonProvider> exporter, GildedToolItem result, Item addition) {
        SmithingTransformRecipeJsonBuilder.create(
                Ingredient.ofItems(VAItems.TOOL_GILD_SMITHING_TEMPLATE),
                Ingredient.ofItems(result.getBaseItem()),
                Ingredient.ofItems(addition),
                RecipeCategory.TOOLS,
                result.asItem()
        ).criterion("has_smithing_template", conditionsFromItem(VAItems.TOOL_GILD_SMITHING_TEMPLATE)).offerTo(exporter, idOf("smithing/" + getItemPath(result)));
    }

    public static void offerToolGildRecipes(Consumer<RecipeJsonProvider> exporter, Item addition, RegistryHelper.ItemRegistryHelper.ToolSet... sets) {
        for (RegistryHelper.ItemRegistryHelper.ToolSet set : sets) {
            for (Item item : set.getItems())
                if (item instanceof GildedToolItem gildedToolItem)
                    offerToolGildRecipe(exporter, gildedToolItem, addition);
        }
    }

    private static void offerToolUpgradeRecipes(Consumer<RecipeJsonProvider> exporter, RegistryHelper.ItemRegistryHelper.ToolSet input, RegistryHelper.ItemRegistryHelper.ToolSet output) {
        offerNetheriteUpgradeRecipe(exporter, input.AXE(), output.AXE());
        offerNetheriteUpgradeRecipe(exporter, input.HOE(), output.HOE());
        offerNetheriteUpgradeRecipe(exporter, input.PICKAXE(), output.PICKAXE());
        offerNetheriteUpgradeRecipe(exporter, input.SHOVEL(), output.SHOVEL());
        offerNetheriteUpgradeRecipe(exporter, input.SWORD(), output.SWORD());
    }

    private static void offerNetheriteUpgradeRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Item result) {
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
    public static void offerShapelessRecipe(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, int count, Pair<ItemConvertible, Integer>... input) {
        ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(category, output, count);
        builder.criterion(hasItem(input[0].getLeft()), conditionsFromItem(input[0].getLeft()));
        for (Pair<ItemConvertible, Integer> itemProvider : input) {
            builder.input(itemProvider.getLeft(), itemProvider.getRight());
        }
        builder.offerTo(exporter);
    }

    public static void offer2x2FullRecipe(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input, int count) {
        ShapedRecipeJsonBuilder.create(category, output, count).input('#', input).pattern("##").pattern("##").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offer2x2FullRecipe(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input, int count, String group) {
        ShapedRecipeJsonBuilder.create(category, output, count).input('#', input).pattern("##").pattern("##").group(group).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    private static void offerHedgeRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("###")
                .pattern("###")
                .input('#', input).criterion("has_leaves", conditionsFromItem(input)).group("hedges").offerTo(exporter);
    }
}
