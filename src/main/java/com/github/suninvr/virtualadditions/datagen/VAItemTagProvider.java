package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.item.GildType;
import com.github.suninvr.virtualadditions.item.GildTypes;
import com.github.suninvr.virtualadditions.item.GildedToolUtil;
import com.github.suninvr.virtualadditions.registry.RegistryHelper;
import com.github.suninvr.virtualadditions.registry.VABlockFamilies;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("SameParameterValue")
class VAItemTagProvider extends FabricTagProvider.ItemTagProvider {
    private static final TagKey<Item> SWORDS = TagKey.of(RegistryKeys.ITEM, new Identifier("c:swords"));
    private static final TagKey<Item> SHOVELS = TagKey.of(RegistryKeys.ITEM, new Identifier("c:shovels"));
    private static final TagKey<Item> PICKAXES = TagKey.of(RegistryKeys.ITEM, new Identifier("c:pickaxes"));
    private static final TagKey<Item> AXES = TagKey.of(RegistryKeys.ITEM, new Identifier("c:axes"));
    private static final TagKey<Item> HOES = TagKey.of(RegistryKeys.ITEM, new Identifier("c:hoes"));


    public VAItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.BEACON_PAYMENT_ITEMS).add(VAItems.STEEL_INGOT, VAItems.IOLITE);
        getOrCreateTagBuilder(ItemTags.CLUSTER_MAX_HARVESTABLES).addOptionalTag(PICKAXES);
        getOrCreateTagBuilder(ItemTags.STONE_CRAFTING_MATERIALS).add(VAItems.COBBLED_HORNFELS, VAItems.COBBLED_BLUESCHIST, VAItems.COBBLED_SYENITE);
        getOrCreateTagBuilder(ItemTags.STONE_TOOL_MATERIALS).add(VAItems.COBBLED_HORNFELS, VAItems.COBBLED_BLUESCHIST, VAItems.COBBLED_SYENITE);
        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS).add(VAItems.STEEL_INGOT, VAItems.IOLITE);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR).add(VAItems.STEEL_HELMET, VAItems.STEEL_CHESTPLATE, VAItems.STEEL_LEGGINGS, VAItems.STEEL_BOOTS);
        getOrCreateTagBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(VAItems.CORN, VAItems.COTTON_SEEDS);
        getOrCreateTagBuilder(ItemTags.FENCES).add(VAItems.STEEL_FENCE);
        getOrCreateTagBuilder(ItemTags.DOORS).add(VAItems.STEEL_DOOR);
        getOrCreateTagBuilder(ItemTags.TRAPDOORS).add(VAItems.STEEL_TRAPDOOR);

        getOrCreateTagBuilder(VAItemTags.LUMWASP_LARVAE_FOOD).add(
                Items.STONE,
                Items.COBBLESTONE,
                Items.GRANITE,
                Items.DIORITE,
                Items.ANDESITE,
                Items.TUFF,
                Items.DEEPSLATE,
                Items.COBBLED_DEEPSLATE,
                VAItems.HORNFELS,
                VAItems.BLUESCHIST,
                VAItems.SYENITE,
                VAItems.COBBLED_HORNFELS,
                VAItems.COBBLED_BLUESCHIST,
                VAItems.COBBLED_SYENITE
        );

        configureBlockFamilies(
                VABlockFamilies.CUT_STEEL,
                VABlockFamilies.COBBLED_HORNFELS,
                VABlockFamilies.COBBLED_BLUESCHIST,
                VABlockFamilies.COBBLED_SYENITE,
                VABlockFamilies.POLISHED_HORNFELS,
                VABlockFamilies.POLISHED_BLUESCHIST,
                VABlockFamilies.POLISHED_SYENITE,
                VABlockFamilies.HORNFELS_TILES,
                VABlockFamilies.BLUESCHIST_BRICKS,
                VABlockFamilies.SYENITE_BRICKS
        );

        configureToolSet(VAItems.STEEL_TOOL_SET);
        configureGildedToolSet(VAItems.AMETHYST_TOOL_SETS);
        configureGildedToolSet(VAItems.COPPER_TOOL_SETS);
        configureGildedToolSet(VAItems.EMERALD_TOOL_SETS);
        configureGildedToolSet(VAItems.QUARTZ_TOOL_SETS);
        configureGildedToolSet(VAItems.SCULK_TOOL_SETS);
    }

    private void configureBlockFamilies(BlockFamily... families) {
        for (BlockFamily family : families) {
            family.getVariants().forEach((variant, block) -> {
                switch (variant) {
                    case STAIRS -> getOrCreateTagBuilder(ItemTags.STAIRS).add(block.asItem());
                    case SLAB -> getOrCreateTagBuilder(ItemTags.SLABS).add(block.asItem());
                    case WALL -> getOrCreateTagBuilder(ItemTags.WALLS).add(block.asItem());
                    case FENCE -> getOrCreateTagBuilder(ItemTags.FENCES).add(block.asItem());
                    case DOOR -> getOrCreateTagBuilder(ItemTags.DOORS).add(block.asItem());
                    case TRAPDOOR -> getOrCreateTagBuilder(ItemTags.TRAPDOORS).add(block.asItem());
                }
            });
        }
    }

    private void configureGildedToolSet(RegistryHelper.ItemRegistryHelper.ToolSet... sets) {
        for (RegistryHelper.ItemRegistryHelper.ToolSet set : sets) {
            configureToolSet(set);
            Item[] items = set.getItems();
            GildType type = GildedToolUtil.getGildType(set.AXE());
            if (type != GildTypes.NONE) {
                getOrCreateTagBuilder(VAItemTags.GILDED_TOOLS).add(items);
                getOrCreateTagBuilder(type.getTag()).add(items);
                getOrCreateTagBuilder(type.getAxesTag()).add(set.AXE());
                getOrCreateTagBuilder(type.getHoesTag()).add(set.HOE());
                getOrCreateTagBuilder(type.getPickaxesTag()).add(set.PICKAXE());
                getOrCreateTagBuilder(type.getShovelsTag()).add(set.SHOVEL());
                getOrCreateTagBuilder(type.getSwordsTag()).add(set.SWORD());
            }
            if (set.NAME().contains("golden")) {
                getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED).add(items);
            }
        }
    }

    private void configureToolSet(RegistryHelper.ItemRegistryHelper.ToolSet set) {
        getOrCreateTagBuilder(AXES).add(set.AXE());
        getOrCreateTagBuilder(HOES).add(set.HOE());
        getOrCreateTagBuilder(PICKAXES).add(set.PICKAXE());
        getOrCreateTagBuilder(SHOVELS).add(set.SHOVEL());
        getOrCreateTagBuilder(SWORDS).add(set.SWORD());
    }
}
