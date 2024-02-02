package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.RegistryHelper;
import com.github.suninvr.virtualadditions.registry.VACollections;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;

import java.util.Map;

@SuppressWarnings("SameParameterValue")
class VAModelProvider extends FabricModelProvider {

    public VAModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.POLISHED_FLOATROCK).family(VACollections.POLISHED_FLOATROCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.FLOATROCK_BRICKS).family(VACollections.FLOATROCK_BRICKS);

        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.CHARTREUSE);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.MAROON);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.INDIGO);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.PLUM);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.COLD_GREEN);
        registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.TAN);
        registerColoringStation(blockStateModelGenerator);
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
        itemModelGenerator.register(VAItems.COLD_GREEN_DYE, Models.GENERATED);
        itemModelGenerator.register(VAItems.TAN_DYE, Models.GENERATED);

    }

    private void registerColorfulBlockSetModels(BlockStateModelGenerator g, ColorfulBlockSet s) {
        if (s.wool() != null) {
            if (s.carpet() != null) g.registerWoolAndCarpet(s.wool(), s.carpet());
            else g.registerSimpleCubeAll(s.wool());
        }
        if (s.terracotta() != null) g.registerSimpleCubeAll(s.terracotta());
        if (s.concrete() != null) g.registerSimpleCubeAll(s.concrete());
        if (s.concretePowder() != null) g.registerSimpleCubeAll(s.concretePowder());
        if (s.stainedGlass() != null) {
            if (s.stainedGlassPane() != null) g.registerGlassPane(s.stainedGlass(), s.stainedGlassPane());
            else g.registerSimpleCubeAll(s.stainedGlass());
        }
        if (s.silkbulb() != null) g.registerSimpleCubeAll(s.silkbulb());
        if (s.candle() != null && s.candleCake() != null) g.registerCandle(s.candle(), s.candleCake());
    }

    private void generateGildedToolItemModels(ItemModelGenerator itemModelGenerator, RegistryHelper.ItemRegistryHelper.ToolSet... toolSets) {
        for (RegistryHelper.ItemRegistryHelper.ToolSet set : toolSets) {
            for (Item item : set.getItems()) {
                if (item instanceof GildedToolItem gildedToolItem) {
                    uploadGildedToolModel(itemModelGenerator, item, gildedToolItem);
                }
            }
        }
    }

    public static void uploadGildedToolModel(ItemModelGenerator itemModelGenerator, Item item, GildedToolItem gildedToolItem) {
        Identifier base = ModelIds.getItemModelId(gildedToolItem.getBaseItem());
        String TOOL_TYPE_SUFFIX = "";
        if (item instanceof AxeItem) TOOL_TYPE_SUFFIX = "_axe";
        else if (item instanceof HoeItem) TOOL_TYPE_SUFFIX = "_hoe";
        else if (item instanceof PickaxeItem) TOOL_TYPE_SUFFIX = "_pickaxe";
        else if (item instanceof ShovelItem) TOOL_TYPE_SUFFIX = "_shovel";
        else if (item instanceof SwordItem) TOOL_TYPE_SUFFIX = "_sword";
        Identifier gild = gildedToolItem.getGildType().getId().withSuffixedPath(TOOL_TYPE_SUFFIX).withPrefixedPath("item/gilded_tools/");
        Models.HANDHELD.upload(ModelIds.getItemModelId(item), TextureMap.layered(base, base), itemModelGenerator.writer, (id, textures) -> Models.HANDHELD.createJson(ModelIds.getItemModelId(item), Map.of(TextureKey.LAYER0, base, TextureKey.LAYER1, gild)));
    }
}
