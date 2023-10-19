package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.item.GildType;
import com.github.suninvr.virtualadditions.item.GildTypes;
import com.github.suninvr.virtualadditions.item.GildedToolUtil;
import com.github.suninvr.virtualadditions.registry.RegistryHelper;
import com.github.suninvr.virtualadditions.registry.VABlockFamilies;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
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

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public final class VAItemTagProvider {

    public static final VAItemTagProvider INSTANCE = new VAItemTagProvider();

    public FabricDataGenerator.Pack.RegistryDependentFactory<?> base() {
        return Base::new;
    }

    public FabricDataGenerator.Pack.RegistryDependentFactory<?> preview() {
        return Preview::new;
    }

    private static class Base extends FabricTagProvider.ItemTagProvider {
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

        public Base(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
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

    private static class Preview extends Base {
        public Preview(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
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
            getOrCreateTagBuilder(ItemTags.FENCES).add(VAItems.STEEL_FENCE).add(VAItems.AEROBLOOM_FENCE);
            getOrCreateTagBuilder(ItemTags.DOORS).add(VAItems.AEROBLOOM_DOOR);
            getOrCreateTagBuilder(ItemTags.TRAPDOORS).add(VAItems.AEROBLOOM_TRAPDOOR);
            getOrCreateTagBuilder(ItemTags.LEAVES).add(VAItems.AEROBLOOM_LEAVES);
            getOrCreateTagBuilder(ItemTags.SAPLINGS).add(VAItems.AEROBLOOM_SAPLING);

            getOrCreateTagBuilder(FOODS).add(VAItems.BALLOON_FRUIT);


            getOrCreateTagBuilder(VAItemTags.LUMWASP_LARVAE_FOOD).add(
                    VAItems.FLOATROCK,
                    VAItems.GRASSY_FLOATROCK,
                    VAItems.RED_GLIMMER_CRYSTAL,
                    VAItems.GREEN_GLIMMER_CRYSTAL,
                    VAItems.BLUE_GLIMMER_CRYSTAL
            );
            getOrCreateTagBuilder(VAItemTags.AEROBLOOM_LOGS).add(
                    VAItems.AEROBLOOM_LOG,
                    VAItems.AEROBLOOM_WOOD,
                    VAItems.STRIPPED_AEROBLOOM_LOG,
                    VAItems.STRIPPED_AEROBLOOM_WOOD
            );
            configureWoodenBlockFamilies(
                    VABlockFamilies.AEROBLOOM
            );
            configureBlockFamilies(
                    VABlockFamilies.FLOATROCK
            );

            getOrCreateTagBuilder(CRYSTALS).add(VAItems.RED_GLIMMER_CRYSTAL, VAItems.GREEN_GLIMMER_CRYSTAL, VAItems.BLUE_GLIMMER_CRYSTAL);
        }
    }
}
