package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.block.SpotlightBlock;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.RegistryHelper;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VACollections;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.item.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Map;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
class VAModelProvider extends FabricModelProvider {
    private static final Identifier OAK_PLANKS_TEXTURE = Identifier.of("block/oak_planks");

    public VAModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.POLISHED_FLOATROCK).family(VACollections.POLISHED_FLOATROCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.FLOATROCK_BRICKS).family(VACollections.FLOATROCK_BRICKS);
        blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.CUT_STEEL).family(VACollections.CUT_STEEL).parented(VABlocks.CUT_STEEL, VABlocks.WAXED_CUT_STEEL).parented(VABlocks.CHISELED_STEEL, VABlocks.WAXED_CHISELED_STEEL).family(VACollections.WAXED_CUT_STEEL);
        blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.EXPOSED_CUT_STEEL).family(VACollections.EXPOSED_CUT_STEEL).parented(VABlocks.EXPOSED_CUT_STEEL, VABlocks.WAXED_EXPOSED_CUT_STEEL).parented(VABlocks.EXPOSED_CHISELED_STEEL, VABlocks.WAXED_EXPOSED_CHISELED_STEEL).family(VACollections.WAXED_EXPOSED_CUT_STEEL);
        blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.WEATHERED_CUT_STEEL).family(VACollections.WEATHERED_CUT_STEEL).parented(VABlocks.WEATHERED_CUT_STEEL, VABlocks.WAXED_WEATHERED_CUT_STEEL).parented(VABlocks.WEATHERED_CHISELED_STEEL, VABlocks.WAXED_WEATHERED_CHISELED_STEEL).family(VACollections.WAXED_WEATHERED_CUT_STEEL);
        blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.OXIDIZED_CUT_STEEL).family(VACollections.OXIDIZED_CUT_STEEL).parented(VABlocks.OXIDIZED_CUT_STEEL, VABlocks.WAXED_OXIDIZED_CUT_STEEL).parented(VABlocks.OXIDIZED_CHISELED_STEEL, VABlocks.WAXED_OXIDIZED_CHISELED_STEEL).family(VACollections.WAXED_OXIDIZED_CUT_STEEL);

        blockStateModelGenerator.registerMirrorable(VABlocks.ROCK_SALT_BLOCK);

        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.CHARTREUSE);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.MAROON);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.INDIGO);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.PLUM);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.VIRIDIAN);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.TAN);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.SINOPIA);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.LILAC);

        registerBanners(blockStateModelGenerator, VACollections.CHARTREUSE, VACollections.MAROON, VACollections.INDIGO, VACollections.PLUM, VACollections.VIRIDIAN, VACollections.TAN, VACollections.SINOPIA, VACollections.LILAC);

        registerColoringStation(blockStateModelGenerator);

        registerSpotlight(blockStateModelGenerator);

        blockStateModelGenerator.registerFlowerPotPlant(VABlocks.AEROBLOOM_SAPLING, VABlocks.POTTED_AEROBLOOM_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(VABlocks.GREENCAP_MUSHROOM, VABlocks.POTTED_GREENCAP_MUSHROOM, BlockStateModelGenerator.TintType.NOT_TINTED);

        blockStateModelGenerator.registerSimpleCubeAll(VABlocks.STEEL_GRATE);
        blockStateModelGenerator.registerSimpleCubeAll(VABlocks.EXPOSED_STEEL_GRATE);
        blockStateModelGenerator.registerSimpleCubeAll(VABlocks.WEATHERED_STEEL_GRATE);
        blockStateModelGenerator.registerSimpleCubeAll(VABlocks.OXIDIZED_STEEL_GRATE);
        blockStateModelGenerator.registerParented(VABlocks.STEEL_GRATE, VABlocks.WAXED_STEEL_GRATE);
        blockStateModelGenerator.registerParented(VABlocks.EXPOSED_STEEL_GRATE, VABlocks.WAXED_EXPOSED_STEEL_GRATE);
        blockStateModelGenerator.registerParented(VABlocks.WEATHERED_STEEL_GRATE, VABlocks.WAXED_WEATHERED_STEEL_GRATE);
        blockStateModelGenerator.registerParented(VABlocks.OXIDIZED_STEEL_GRATE, VABlocks.WAXED_OXIDIZED_STEEL_GRATE);
        blockStateModelGenerator.registerSimpleCubeAll(VABlocks.EXPOSED_STEEL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(VABlocks.WEATHERED_STEEL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(VABlocks.OXIDIZED_STEEL_BLOCK);
        blockStateModelGenerator.registerParented(VABlocks.STEEL_BLOCK, VABlocks.WAXED_STEEL_BLOCK);
        blockStateModelGenerator.registerParented(VABlocks.EXPOSED_STEEL_BLOCK, VABlocks.WAXED_EXPOSED_STEEL_BLOCK);
        blockStateModelGenerator.registerParented(VABlocks.WEATHERED_STEEL_BLOCK, VABlocks.WAXED_WEATHERED_STEEL_BLOCK);
        blockStateModelGenerator.registerParented(VABlocks.OXIDIZED_STEEL_BLOCK, VABlocks.WAXED_OXIDIZED_STEEL_BLOCK);
    }

    private void registerColoringStation(BlockStateModelGenerator blockStateModelGenerator) {
        TextureMap textureMap = new TextureMap()
                .put(TextureKey.PARTICLE, TextureMap.getSubId(VABlocks.COLORING_STATION, "_front"))
                .put(TextureKey.DOWN, TextureMap.getSubId(VABlocks.COLORING_STATION, "_bottom"))
                .put(TextureKey.UP, TextureMap.getSubId(VABlocks.COLORING_STATION, "_top"))
                .put(TextureKey.NORTH, TextureMap.getSubId(VABlocks.COLORING_STATION, "_front"))
                .put(TextureKey.SOUTH, TextureMap.getSubId(VABlocks.COLORING_STATION, "_front"))
                .put(TextureKey.EAST, TextureMap.getSubId(VABlocks.COLORING_STATION, "_side"))
                .put(TextureKey.WEST, TextureMap.getSubId(VABlocks.COLORING_STATION, "_side"));
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(VABlocks.COLORING_STATION, Models.CUBE.upload(VABlocks.COLORING_STATION, textureMap, blockStateModelGenerator.modelCollector)));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        generateGildedToolItemModels(itemModelGenerator, VAItems.AMETHYST_TOOL_SETS);
        generateGildedToolItemModels(itemModelGenerator, VAItems.COPPER_TOOL_SETS);
        generateGildedToolItemModels(itemModelGenerator, VAItems.EMERALD_TOOL_SETS);
        generateGildedToolItemModels(itemModelGenerator, VAItems.IOLITE_TOOL_SETS);
        generateGildedToolItemModels(itemModelGenerator, VAItems.QUARTZ_TOOL_SETS);
        generateGildedToolItemModels(itemModelGenerator, VAItems.SCULK_TOOL_SETS);

        itemModelGenerator.register(VAItems.CHARTREUSE_DYE, Models.GENERATED);
        itemModelGenerator.register(VAItems.MAROON_DYE, Models.GENERATED);
        itemModelGenerator.register(VAItems.INDIGO_DYE, Models.GENERATED);
        itemModelGenerator.register(VAItems.PLUM_DYE, Models.GENERATED);
        itemModelGenerator.register(VAItems.VIRIDIAN_DYE, Models.GENERATED);
        itemModelGenerator.register(VAItems.TAN_DYE, Models.GENERATED);
        itemModelGenerator.register(VAItems.SINOPIA_DYE, Models.GENERATED);
        itemModelGenerator.register(VAItems.LILAC_DYE, Models.GENERATED);

        itemModelGenerator.register(VAItems.CHARTREUSE_BUNDLE, Models.GENERATED);
        itemModelGenerator.register(VAItems.MAROON_BUNDLE, Models.GENERATED);
        itemModelGenerator.register(VAItems.INDIGO_BUNDLE, Models.GENERATED);
        itemModelGenerator.register(VAItems.PLUM_BUNDLE, Models.GENERATED);
        itemModelGenerator.register(VAItems.VIRIDIAN_BUNDLE, Models.GENERATED);
        itemModelGenerator.register(VAItems.TAN_BUNDLE, Models.GENERATED);
        itemModelGenerator.register(VAItems.SINOPIA_BUNDLE, Models.GENERATED);
        itemModelGenerator.register(VAItems.LILAC_BUNDLE, Models.GENERATED);

        Models.GENERATED.upload(idOf("chartreuse_bundle_open_front"), TextureMap.layer0(idOf("chartreuse_bundle_open_front")), itemModelGenerator.writer);

        itemModelGenerator.register(VAItems.CORN_SEEDS, Models.GENERATED);
        itemModelGenerator.register(VAItems.SWEET_BERRY_PIE, Models.GENERATED);

        itemModelGenerator.register(VAItems.EXPOSED_CLIMBING_ROPE, Models.GENERATED);
        itemModelGenerator.register(VAItems.WEATHERED_CLIMBING_ROPE, Models.GENERATED);
        itemModelGenerator.register(VAItems.OXIDIZED_CLIMBING_ROPE, Models.GENERATED);
        itemModelGenerator.register(VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE, Models.GENERATED);

    }

    private void registerColorfulBlockSetModels(BlockStateModelGenerator g, ColorfulBlockSet s) {
        s.ifWool(wool -> {
            if (s.carpet() != null) g.registerWoolAndCarpet(wool, s.carpet());
            else g.registerSimpleCubeAll(s.wool());
            s.ifBed(bed -> g.registerBed(bed, wool));
        });
        if (s.terracotta() != null) g.registerSimpleCubeAll(s.terracotta());
        if (s.concrete() != null) g.registerSimpleCubeAll(s.concrete());
        if (s.concretePowder() != null) g.registerRandomHorizontalRotations(TexturedModel.CUBE_ALL,s.concretePowder());
        if (s.stainedGlass() != null) {
            if (s.stainedGlassPane() != null) g.registerGlassPane(s.stainedGlass(), s.stainedGlassPane());
            else g.registerSimpleCubeAll(s.stainedGlass());
        }
        if (s.silkbulb() != null) g.registerSimpleCubeAll(s.silkbulb());
        if (s.candle() != null && s.candleCake() != null) g.registerCandle(s.candle(), s.candleCake());
        s.ifBed(bed -> g.registerBuiltinWithParticle(bed, OAK_PLANKS_TEXTURE));
        s.ifShulkerBox(g::registerShulkerBox);
        s.ifGlazedTerracotta(block -> g.registerSouthDefaultHorizontalFacing(TexturedModel.TEMPLATE_GLAZED_TERRACOTTA, block));
    }

    private void registerBanners(BlockStateModelGenerator g, ColorfulBlockSet... s) {
        ArrayList<Block> banners = new ArrayList<>();
        ArrayList<Block> wallBanners = new ArrayList<>();
        for (ColorfulBlockSet set : s) {
            banners.add(set.banner());
            wallBanners.add(set.wallBanner());
        }
        g.registerBuiltin(idOf("banner"), Blocks.OAK_PLANKS).includeWithItem(Models.TEMPLATE_BANNER, banners.toArray(new Block[0])).includeWithoutItem(wallBanners.toArray(new Block[0]));
    }

    private void generateGildedToolItemModels(ItemModelGenerator itemModelGenerator, RegistryHelper.ItemRegistryHelper.ToolSet... toolSets) {
        for (RegistryHelper.ItemRegistryHelper.ToolSet set : toolSets) {
            uploadGildedToolModels(itemModelGenerator, set);
        }
    }

    public static void uploadGildedToolModels(ItemModelGenerator itemModelGenerator, RegistryHelper.ItemRegistryHelper.ToolSet set) {
        uploadGildedToolModel(itemModelGenerator, set.SWORD(), "_sword");
        uploadGildedToolModel(itemModelGenerator, set.SHOVEL(), "_shovel");
        uploadGildedToolModel(itemModelGenerator, set.PICKAXE(), "_pickaxe");
        uploadGildedToolModel(itemModelGenerator, set.AXE(), "_axe");
        uploadGildedToolModel(itemModelGenerator, set.HOE(), "_hoe");
    }

    public static void uploadGildedToolModel(ItemModelGenerator itemModelGenerator, Item item, String suffix) {
        GildedToolItem gildedToolItem = item instanceof GildedToolItem ? (GildedToolItem) item : null;
        if (gildedToolItem == null) return;
        Item baseItem = gildedToolItem.getBaseItem();
        Identifier base = ModelIds.getItemModelId(baseItem);
        Identifier gild = gildedToolItem.getGildType().getId().withSuffixedPath(suffix).withPrefixedPath("item/gilded_tools/");
        Models.HANDHELD.upload(ModelIds.getItemModelId(item), TextureMap.layered(base, base), itemModelGenerator.writer, (id, textures) -> Models.HANDHELD.createJson(ModelIds.getItemModelId(baseItem), Map.of(TextureKey.LAYER0, base, TextureKey.LAYER1, gild)));
    }

    private void registerSpotlight(BlockStateModelGenerator generator) {
        Identifier spotlight = ModelIds.getBlockModelId(VABlocks.SPOTLIGHT);
        Identifier spotlightActive = ModelIds.getBlockSubModelId(VABlocks.SPOTLIGHT, "_active");
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(VABlocks.SPOTLIGHT)
                .coordinate(BlockStateVariantMap.create(Properties.ORIENTATION)
                        .register(orientation -> generator.addJigsawOrientationToVariant(orientation, BlockStateVariant.create())))
                .coordinate(BlockStateVariantMap.create(SpotlightBlock.POWERED)
                        .register(powered -> powered ? BlockStateVariant.create().put(VariantSettings.MODEL, spotlightActive) : BlockStateVariant.create().put(VariantSettings.MODEL, spotlight))));
    }
}
