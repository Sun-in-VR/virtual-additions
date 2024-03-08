package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.item.GildType;
import com.github.suninvr.virtualadditions.item.GildTypes;
import com.github.suninvr.virtualadditions.item.GildedToolUtil;
import com.github.suninvr.virtualadditions.registry.RegistryHelper;
import com.github.suninvr.virtualadditions.registry.VACollections;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public final class VAItemTagProvider {

    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> base() {
        return BaseProvider::new;
    }

    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> preview() {
        return PreviewProvider::new;
    }

    private static class BaseProvider extends Provider {

        public BaseProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
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
            getOrCreateTagBuilder(ItemTags.DOORS).add(VAItems.STEEL_DOOR);
            getOrCreateTagBuilder(ItemTags.TRAPDOORS).add(VAItems.STEEL_TRAPDOOR);

            getOrCreateTagBuilder(INGOTS).add(VAItems.STEEL_INGOT);
            getOrCreateTagBuilder(STEEL_INGOTS).add(VAItems.STEEL_INGOT);
            getOrCreateTagBuilder(RAW_ORES).add(VAItems.RAW_STEEL);
            getOrCreateTagBuilder(IOLITE).add(VAItems.IOLITE);
            getOrCreateTagBuilder(IOLITE_ORES).add(VAItems.IOLITE_ORE);
            getOrCreateTagBuilder(GEMS).addOptionalTag(IOLITE);
            getOrCreateTagBuilder(ORES).addOptionalTag(IOLITE_ORES);
            getOrCreateTagBuilder(FOODS).add(VAItems.FRIED_EGG, VAItems.CORN, VAItems.ROASTED_CORN);
            getOrCreateTagBuilder(POTIONS).add(VAItems.APPLICABLE_POTION);

            configureColorfulBlockSet(
                    VACollections.CHARTREUSE,
                    VACollections.MAROON,
                    VACollections.INDIGO,
                    VACollections.PLUM,
                    VACollections.VIRIDIAN,
                    VACollections.TAN,
                    VACollections.SINOPIA,
                    VACollections.LILAC
            );

            getOrCreateTagBuilder(VAItemTags.SILKBULBS).add(
                    VAItems.SILKBULB,
                    VAItems.WHITE_SILKBULB,
                    VAItems.LIGHT_GRAY_SILKBULB,
                    VAItems.GRAY_SILKBULB,
                    VAItems.BLACK_SILKBULB,
                    VAItems.BROWN_SILKBULB,
                    VAItems.RED_SILKBULB,
                    VAItems.ORANGE_SILKBULB,
                    VAItems.YELLOW_SILKBULB,
                    VAItems.LIME_SILKBULB,
                    VAItems.GREEN_SILKBULB,
                    VAItems.CYAN_SILKBULB,
                    VAItems.LIGHT_BLUE_SILKBULB,
                    VAItems.BLUE_SILKBULB,
                    VAItems.PURPLE_SILKBULB,
                    VAItems.MAGENTA_SILKBULB,
                    VAItems.PINK_SILKBULB
            );

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

            getOrCreateTagBuilder(VAItemTags.ACID_RESISTANT).add(
                    VAItems.ACID_BLOCK,
                    Items.ANCIENT_DEBRIS,
                    Items.NETHERITE_SCRAP,
                    Items.NETHERITE_INGOT,
                    Items.NETHERITE_BLOCK,
                    Items.NETHERITE_SWORD,
                    Items.NETHERITE_SHOVEL,
                    Items.NETHERITE_PICKAXE,
                    Items.NETHERITE_AXE,
                    Items.NETHERITE_HOE,
                    Items.NETHERITE_HELMET,
                    Items.NETHERITE_CHESTPLATE,
                    Items.NETHERITE_LEGGINGS,
                    Items.NETHERITE_BOOTS,
                    Items.NETHER_STAR
            );

            configureToolSets(VAItemTags.ACID_RESISTANT,
                    VAItems.AMETHYST_NETHERITE_TOOL_SET,
                    VAItems.COPPER_NETHERITE_TOOL_SET,
                    VAItems.EMERALD_NETHERITE_TOOL_SET,
                    VAItems.QUARTZ_NETHERITE_TOOL_SET,
                    VAItems.SCULK_NETHERITE_TOOL_SET,
                    VAItems.IOLITE_NETHERITE_TOOL_SET
                    );

            getOrCreateTagBuilder(VAItemTags.BASE_DYE).add(
                    Items.RED_DYE,
                    Items.GREEN_DYE,
                    Items.BLUE_DYE,
                    Items.YELLOW_DYE,
                    Items.BLACK_DYE,
                    Items.WHITE_DYE
            );

            getOrCreateTagBuilder(VAItemTags.COLORABLE_GLASS).add(
                    Items.GLASS,
                    Items.WHITE_STAINED_GLASS,
                    Items.LIGHT_GRAY_STAINED_GLASS,
                    Items.GRAY_STAINED_GLASS,
                    Items.BLACK_STAINED_GLASS,
                    Items.BROWN_STAINED_GLASS,
                    VAItems.LILAC_STAINED_GLASS,
                    VAItems.MAROON_STAINED_GLASS,
                    Items.RED_STAINED_GLASS,
                    VAItems.SINOPIA_STAINED_GLASS,
                    Items.ORANGE_STAINED_GLASS,
                    VAItems.TAN_STAINED_GLASS,
                    Items.YELLOW_STAINED_GLASS,
                    VAItems.CHARTREUSE_STAINED_GLASS,
                    Items.LIME_STAINED_GLASS,
                    Items.GREEN_STAINED_GLASS,
                    VAItems.VIRIDIAN_STAINED_GLASS,
                    Items.CYAN_STAINED_GLASS,
                    Items.LIGHT_BLUE_STAINED_GLASS,
                    Items.BLUE_STAINED_GLASS,
                    VAItems.INDIGO_STAINED_GLASS,
                    Items.PURPLE_STAINED_GLASS,
                    VAItems.PLUM_STAINED_GLASS,
                    Items.MAGENTA_STAINED_GLASS,
                    Items.PINK_STAINED_GLASS
            );

            getOrCreateTagBuilder(VAItemTags.COLORABLE_GLASS_PANE).add(
                    Items.GLASS_PANE,
                    Items.WHITE_STAINED_GLASS_PANE,
                    Items.LIGHT_GRAY_STAINED_GLASS_PANE,
                    Items.GRAY_STAINED_GLASS_PANE,
                    Items.BLACK_STAINED_GLASS_PANE,
                    Items.BROWN_STAINED_GLASS_PANE,
                    VAItems.LILAC_STAINED_GLASS_PANE,
                    VAItems.MAROON_STAINED_GLASS_PANE,
                    Items.RED_STAINED_GLASS_PANE,
                    VAItems.SINOPIA_STAINED_GLASS_PANE,
                    Items.ORANGE_STAINED_GLASS_PANE,
                    VAItems.TAN_STAINED_GLASS_PANE,
                    Items.YELLOW_STAINED_GLASS_PANE,
                    VAItems.CHARTREUSE_STAINED_GLASS_PANE,
                    Items.LIME_STAINED_GLASS_PANE,
                    Items.GREEN_STAINED_GLASS_PANE,
                    VAItems.VIRIDIAN_STAINED_GLASS_PANE,
                    Items.CYAN_STAINED_GLASS_PANE,
                    Items.LIGHT_BLUE_STAINED_GLASS_PANE,
                    Items.BLUE_STAINED_GLASS_PANE,
                    VAItems.INDIGO_STAINED_GLASS_PANE,
                    Items.PURPLE_STAINED_GLASS_PANE,
                    VAItems.PLUM_STAINED_GLASS_PANE,
                    Items.MAGENTA_STAINED_GLASS_PANE,
                    Items.PINK_STAINED_GLASS_PANE
            );

            getOrCreateTagBuilder(VAItemTags.SHULKER_BOXES).add(
                    Items.SHULKER_BOX,
                    Items.WHITE_SHULKER_BOX,
                    Items.LIGHT_GRAY_SHULKER_BOX,
                    Items.GRAY_SHULKER_BOX,
                    Items.BLACK_SHULKER_BOX,
                    Items.BROWN_SHULKER_BOX,
                    VAItems.LILAC_SHULKER_BOX,
                    VAItems.MAROON_SHULKER_BOX,
                    Items.RED_SHULKER_BOX,
                    VAItems.SINOPIA_SHULKER_BOX,
                    Items.ORANGE_SHULKER_BOX,
                    VAItems.TAN_SHULKER_BOX,
                    Items.YELLOW_SHULKER_BOX,
                    VAItems.CHARTREUSE_SHULKER_BOX,
                    Items.LIME_SHULKER_BOX,
                    Items.GREEN_SHULKER_BOX,
                    VAItems.VIRIDIAN_SHULKER_BOX,
                    Items.CYAN_SHULKER_BOX,
                    Items.LIGHT_BLUE_SHULKER_BOX,
                    Items.BLUE_SHULKER_BOX,
                    VAItems.INDIGO_SHULKER_BOX,
                    Items.PURPLE_SHULKER_BOX,
                    VAItems.PLUM_SHULKER_BOX,
                    Items.MAGENTA_SHULKER_BOX,
                    Items.PINK_SHULKER_BOX
            );

            configureBlockFamilies(
                    VACollections.CUT_STEEL,
                    VACollections.COBBLED_HORNFELS,
                    VACollections.COBBLED_BLUESCHIST,
                    VACollections.COBBLED_SYENITE,
                    VACollections.POLISHED_HORNFELS,
                    VACollections.POLISHED_BLUESCHIST,
                    VACollections.POLISHED_SYENITE,
                    VACollections.HORNFELS_TILES,
                    VACollections.BLUESCHIST_BRICKS,
                    VACollections.SYENITE_BRICKS
            );

            configureToolSet(VAItems.STEEL_TOOL_SET);
            configureGildedToolSet(VAItems.AMETHYST_TOOL_SETS);
            configureGildedToolSet(VAItems.COPPER_TOOL_SETS);
            configureGildedToolSet(VAItems.EMERALD_TOOL_SETS);
            configureGildedToolSet(VAItems.IOLITE_TOOL_SETS);
            configureGildedToolSet(VAItems.QUARTZ_TOOL_SETS);
            configureGildedToolSet(VAItems.SCULK_TOOL_SETS);
        }
    }

    private static class PreviewProvider extends Provider {
        public PreviewProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(ItemTags.STONE_CRAFTING_MATERIALS).add(VAItems.FLOATROCK);
            getOrCreateTagBuilder(ItemTags.STONE_TOOL_MATERIALS).add(VAItems.FLOATROCK);
            getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).add(VAItems.AEROBLOOM_LOG, VAItems.AEROBLOOM_WOOD, VAItems.STRIPPED_AEROBLOOM_LOG, VAItems.STRIPPED_AEROBLOOM_WOOD);
            getOrCreateTagBuilder(ItemTags.PLANKS).add(VAItems.AEROBLOOM_PLANKS);
            getOrCreateTagBuilder(ItemTags.SIGNS).add(VAItems.AEROBLOOM_SIGN);
            getOrCreateTagBuilder(ItemTags.HANGING_SIGNS).add(VAItems.AEROBLOOM_HANGING_SIGN);
            getOrCreateTagBuilder(ItemTags.FENCES).add(VAItems.AEROBLOOM_FENCE);
            getOrCreateTagBuilder(ItemTags.DOORS).add(VAItems.AEROBLOOM_DOOR);
            getOrCreateTagBuilder(ItemTags.TRAPDOORS).add(VAItems.AEROBLOOM_TRAPDOOR);
            getOrCreateTagBuilder(ItemTags.LEAVES).add(VAItems.AEROBLOOM_LEAVES);
            getOrCreateTagBuilder(ItemTags.SAPLINGS).add(VAItems.AEROBLOOM_SAPLING);

            getOrCreateTagBuilder(FOODS).add(VAItems.BALLOON_FRUIT);


            getOrCreateTagBuilder(VAItemTags.LUMWASP_LARVAE_FOOD).add(
                    VAItems.FLOATROCK,
                    VAItems.GRASSY_FLOATROCK
            );
            getOrCreateTagBuilder(VAItemTags.AEROBLOOM_LOGS).add(
                    VAItems.AEROBLOOM_LOG,
                    VAItems.AEROBLOOM_WOOD,
                    VAItems.STRIPPED_AEROBLOOM_LOG,
                    VAItems.STRIPPED_AEROBLOOM_WOOD
            );
            configureWoodenBlockFamilies(
                    VACollections.AEROBLOOM
            );
            configureBlockFamilies(
                    VACollections.FLOATROCK,
                    VACollections.POLISHED_FLOATROCK,
                    VACollections.FLOATROCK_BRICKS
            );
        }
    }

    private abstract static class Provider extends FabricTagProvider.ItemTagProvider {
        protected static final TagKey<Item> SWORDS = TagKey.of(RegistryKeys.ITEM, new Identifier("c:swords"));
        protected static final TagKey<Item> SHOVELS = TagKey.of(RegistryKeys.ITEM, new Identifier("c:shovels"));
        protected static final TagKey<Item> PICKAXES = TagKey.of(RegistryKeys.ITEM, new Identifier("c:pickaxes"));
        protected static final TagKey<Item> AXES = TagKey.of(RegistryKeys.ITEM, new Identifier("c:axes"));
        protected static final TagKey<Item> HOES = TagKey.of(RegistryKeys.ITEM, new Identifier("c:hoes"));
        protected static final TagKey<Item> INGOTS = TagKey.of(RegistryKeys.ITEM, new Identifier("c:ingots"));
        protected static final TagKey<Item> STEEL_INGOTS = TagKey.of(RegistryKeys.ITEM, new Identifier("c:steel_ingots"));
        protected static final TagKey<Item> RAW_ORES = TagKey.of(RegistryKeys.ITEM, new Identifier("c:raw_ores"));
        protected static final TagKey<Item> GEMS = TagKey.of(RegistryKeys.ITEM, new Identifier("c:gems"));
        protected static final TagKey<Item> FOODS = TagKey.of(RegistryKeys.ITEM, new Identifier("c:foods"));
        protected static final TagKey<Item> IOLITE = TagKey.of(RegistryKeys.ITEM, new Identifier("c:iolite"));
        protected static final TagKey<Item> IOLITE_ORES = TagKey.of(RegistryKeys.ITEM, new Identifier("c:iolite_ores"));
        protected static final TagKey<Item> ORES = TagKey.of(RegistryKeys.ITEM, new Identifier("c:ores"));
        protected static final TagKey<Item> POTIONS = TagKey.of(RegistryKeys.ITEM, new Identifier("c:potions"));
        protected static final TagKey<Item> CRYSTALS = TagKey.of(RegistryKeys.ITEM, idOf("crystals"));

        public Provider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        protected void configureBlockFamilies(BlockFamily... families) {
            for (BlockFamily family : families) {
                family.getVariants().forEach((variant, block) -> {
                    switch (variant) {
                        case STAIRS -> getOrCreateTagBuilder(ItemTags.STAIRS).add(block.asItem());
                        case SLAB -> getOrCreateTagBuilder(ItemTags.SLABS).add(block.asItem());
                        case WALL -> getOrCreateTagBuilder(ItemTags.WALLS).add(block.asItem());
                        case FENCE -> getOrCreateTagBuilder(ItemTags.FENCES).add(block.asItem());
                        case FENCE_GATE -> getOrCreateTagBuilder(ItemTags.FENCE_GATES).add(block.asItem());
                        case DOOR -> getOrCreateTagBuilder(ItemTags.DOORS).add(block.asItem());
                        case TRAPDOOR -> getOrCreateTagBuilder(ItemTags.TRAPDOORS).add(block.asItem());
                        case BUTTON -> getOrCreateTagBuilder(ItemTags.BUTTONS).add(block.asItem());
                    }
                });
            }
        }

        protected void configureColorfulBlockSet(ColorfulBlockSet... sets) {
            for (ColorfulBlockSet set : sets) {
                set.ifWool(block -> addTo(ItemTags.WOOL, block));
                set.ifCarpet(block -> addTo(ItemTags.WOOL_CARPETS, block));
                set.ifTerracotta(block -> addTo(ItemTags.TERRACOTTA, block));
                set.ifCandle(block -> addTo(ItemTags.CANDLES, block));
                set.ifSilkbulb(block -> addTo(VAItemTags.SILKBULBS, block));
                set.ifBed(block -> addTo(ItemTags.BEDS, block));
                set.ifBanner(block -> addTo(ItemTags.BANNERS, block));
            }
        }

        protected void configureWoodenBlockFamilies(BlockFamily... families) {
            for (BlockFamily family : families) {
                family.getVariants().forEach((variant, block) -> {
                    switch (variant) {
                        case STAIRS -> getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS).add(block.asItem());
                        case SLAB -> getOrCreateTagBuilder(ItemTags.WOODEN_SLABS).add(block.asItem());
                        case FENCE -> getOrCreateTagBuilder(ItemTags.WOODEN_FENCES).add(block.asItem());
                        case DOOR -> getOrCreateTagBuilder(ItemTags.WOODEN_DOORS).add(block.asItem());
                        case TRAPDOOR -> getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS).add(block.asItem());
                        case BUTTON -> getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS).add(block.asItem());
                        case PRESSURE_PLATE -> getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(block.asItem());
                    }
                });
            }
        }

        protected void addTo(TagKey<Item> tag, ItemConvertible item) {
            getOrCreateTagBuilder(tag).add(item.asItem());
        }

        protected void configureGildedToolSet(RegistryHelper.ItemRegistryHelper.ToolSet... sets) {
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

        protected void configureToolSet(RegistryHelper.ItemRegistryHelper.ToolSet set) {
            getOrCreateTagBuilder(AXES).add(set.AXE());
            getOrCreateTagBuilder(HOES).add(set.HOE());
            getOrCreateTagBuilder(PICKAXES).add(set.PICKAXE());
            getOrCreateTagBuilder(SHOVELS).add(set.SHOVEL());
            getOrCreateTagBuilder(SWORDS).add(set.SWORD());
        }

        protected void configureToolSets(TagKey<Item> tag, RegistryHelper.ItemRegistryHelper.ToolSet... sets) {
            FabricTagBuilder builder = getOrCreateTagBuilder(tag);
            Arrays.stream(sets).iterator().forEachRemaining(set -> set.forEach(builder::add));
        }
    }
}
