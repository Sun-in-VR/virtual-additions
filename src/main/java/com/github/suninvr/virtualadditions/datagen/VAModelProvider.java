package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.RegistryHelper;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
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

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        generateGildedToolItemModels(itemModelGenerator, VAItems.AMETHYST_TOOL_SETS);
        generateGildedToolItemModels(itemModelGenerator, VAItems.COPPER_TOOL_SETS);
        generateGildedToolItemModels(itemModelGenerator, VAItems.EMERALD_TOOL_SETS);
        generateGildedToolItemModels(itemModelGenerator, VAItems.QUARTZ_TOOL_SETS);
        generateGildedToolItemModels(itemModelGenerator, VAItems.SCULK_TOOL_SETS);

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
        Models.HANDHELD.upload(ModelIds.getItemModelId(item), TextureMap.layered(base, base), itemModelGenerator.writer, new Model.JsonFactory() {
            @Override
            public JsonObject create(Identifier id, Map<TextureKey, Identifier> textures) {
                return Models.HANDHELD.createJson(ModelIds.getItemModelId(item), Map.of(TextureKey.LAYER0, base, TextureKey.LAYER1, gild));
            }
        });
    }
}
