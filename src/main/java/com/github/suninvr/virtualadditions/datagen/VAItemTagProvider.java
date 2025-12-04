package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.RegistryHelper;
import com.github.suninvr.virtualadditions.registry.VACollections;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("SameParameterValue")
public final class VAItemTagProvider {

    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> base() {
        return BaseProvider::new;
    }

    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> preview() {
        return PreviewProvider::new;
    }

    private static class BaseProvider extends Provider {

        public BaseProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            addTo(ItemTags.BEACON_PAYMENT_ITEMS, VAItems.STEEL_INGOT, VAItems.IOLITE);
            addTo(ItemTags.STONE_CRAFTING_MATERIALS, VAItems.COBBLED_HORNFELS, VAItems.COBBLED_BLUESCHIST, VAItems.COBBLED_SYENITE);
            addTo(ItemTags.STONE_TOOL_MATERIALS, VAItems.COBBLED_HORNFELS, VAItems.COBBLED_BLUESCHIST, VAItems.COBBLED_SYENITE);
            addTo(ItemTags.TRIM_MATERIALS, VAItems.STEEL_INGOT, VAItems.IOLITE, VAItems.ROCK_SALT);
            addTo(ItemTags.TRIMMABLE_ARMOR, VAItems.STEEL_HELMET, VAItems.STEEL_CHESTPLATE, VAItems.STEEL_LEGGINGS, VAItems.STEEL_BOOTS);
            addTo(ItemTags.HEAD_ARMOR_ENCHANTABLE, VAItems.STEEL_HELMET);
            addTo(ItemTags.CHEST_ARMOR_ENCHANTABLE, VAItems.STEEL_CHESTPLATE);
            addTo(ItemTags.LEG_ARMOR_ENCHANTABLE, VAItems.STEEL_LEGGINGS);
            addTo(ItemTags.FOOT_ARMOR_ENCHANTABLE, VAItems.STEEL_BOOTS);
            addTo(ItemTags.HEAD_ARMOR, VAItems.STEEL_HELMET);
            addTo(ItemTags.CHEST_ARMOR, VAItems.STEEL_CHESTPLATE);
            addTo(ItemTags.LEG_ARMOR, VAItems.STEEL_LEGGINGS);
            addTo(ItemTags.FOOT_ARMOR, VAItems.STEEL_BOOTS);
            addTo(ItemTags.SPEARS, VAItems.STEEL_SPEAR);
            addTo(ItemTags.VILLAGER_PLANTABLE_SEEDS, VAItems.CORN_SEEDS, VAItems.TOMATO_SEEDS, VAItems.CABBAGE_SEEDS, VAItems.WISDOM_BERRY_SEEDS);
            addTo(ItemTags.DOORS, VAItems.STEEL_DOOR);
            addTo(ItemTags.TRAPDOORS, VAItems.STEEL_TRAPDOOR);
            addTo(ItemTags.DYEABLE, VAItems.ICE_CREAM, VAItems.ENGRAVING_CHISEL);
            addTo(ItemTags.DURABILITY_ENCHANTABLE, VAItems.ENGRAVING_CHISEL);
            addTo(ItemTags.PIG_FOOD, VAItems.TOMATO, VAItems.CORN, VAItems.CABBAGE);
            addTo(ItemTags.VILLAGER_PICKS_UP, VAItems.TOMATO, VAItems.CORN, VAItems.CABBAGE, VAItems.WISDOM_BERRY, VAItems.CORN_SEEDS, VAItems.TOMATO_SEEDS, VAItems.CABBAGE_SEEDS, VAItems.WISDOM_BERRY_SEEDS);
            addTo(ItemTags.CHICKEN_FOOD, VAItems.CORN_SEEDS, VAItems.TOMATO_SEEDS, VAItems.CABBAGE_SEEDS, VAItems.COTTON_SEEDS, VAItems.WISDOM_BERRY_SEEDS);
            addTo(ItemTags.BOATS, VAItems.SOULBLOOM_BOAT);
            addTo(ItemTags.CHEST_BOATS, VAItems.SOULBLOOM_CHEST_BOAT);
            addTo(ItemTags.FLOWERS, VAItems.BLUE_PETALS, VAItems.SPRING_LOTUS, VAItems.SMALL_SPRING_LOTUS, VAItems.SOUL_SPROUT);
            addTo(ItemTags.SMALL_FLOWERS, VAItems.SMALL_SPRING_LOTUS, VAItems.SOUL_SPROUT);
            addTo(ItemTags.BEE_FOOD, VAItems.BLUE_PETALS, VAItems.SPRING_LOTUS, VAItems.SMALL_SPRING_LOTUS, VAItems.SOUL_SPROUT);

            addTo(INGOTS, VAItems.STEEL_INGOT);
            addTo(STEEL_INGOTS, VAItems.STEEL_INGOT);
            addTo(RAW_ORES, VAItems.RAW_STEEL);
            addTo(IOLITE, VAItems.IOLITE);
            addTo(IOLITE_ORES, VAItems.IOLITE_ORE);
            addTo(VAItemTags.ROCK_SALT_ORES, VAItems.ROCK_SALT_ORE, VAItems.DEEPSLATE_ROCK_SALT_ORE);
            addTo(GEMS).addOptionalTag(IOLITE.location());
            addTo(ORES).addOptionalTag(IOLITE_ORES.location()).addOptionalTag(VAItemTags.ROCK_SALT_ORES.location());
            addTo(FOODS, VAItems.FRIED_EGG, VAItems.CORN, VAItems.ROASTED_CORN, VAItems.TORTILLA, VAItems.ICE_CREAM, VAItems.SWEET_BERRY_PIE);
            addTo(POTIONS, VAItems.APPLICABLE_POTION);

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

            addTo(ItemTags.BUNDLES, 
                    VAItems.CHARTREUSE_BUNDLE,
                    VAItems.MAROON_BUNDLE,
                    VAItems.INDIGO_BUNDLE,
                    VAItems.PLUM_BUNDLE,
                    VAItems.VIRIDIAN_BUNDLE,
                    VAItems.TAN_BUNDLE,
                    VAItems.SINOPIA_BUNDLE,
                    VAItems.LILAC_BUNDLE
            );

            addTo(ItemTags.HARNESSES, 
                    VAItems.CHARTREUSE_HARNESS,
                    VAItems.MAROON_HARNESS,
                    VAItems.INDIGO_HARNESS,
                    VAItems.PLUM_HARNESS,
                    VAItems.VIRIDIAN_HARNESS,
                    VAItems.TAN_HARNESS,
                    VAItems.SINOPIA_HARNESS,
                    VAItems.LILAC_HARNESS
            );

            addTo(VAItemTags.SILKBULBS, 
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

            addTo(VAItemTags.LUMWASP_LARVAE_FOOD, 
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

            addTo(VAItemTags.ACID_RESISTANT, 
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

            addTo(VAItemTags.ACCEPTS_APPLIED_EFFECTS, Items.TRIDENT, Items.MACE)
                    .addOptionalTag(ItemTags.SWORDS.location())
                    .addOptionalTag(ItemTags.SHOVELS.location())
                    .addOptionalTag(ItemTags.PICKAXES.location())
                    .addOptionalTag(ItemTags.AXES.location())
                    .addOptionalTag(ItemTags.HOES.location())
                    .addOptionalTag(ItemTags.SPEARS.location())
                    .addOptionalTag(VAItemTags.HALBERDS.location());

            addTo(VAItemTags.ACCEPTS_TOOL_GILDS,
                    Items.COPPER_SWORD, Items.COPPER_SHOVEL, Items.COPPER_PICKAXE, Items.COPPER_AXE, Items.COPPER_HOE, Items.COPPER_SPEAR, VAItems.COPPER_HALBERD,
                    Items.IRON_SWORD, Items.IRON_SHOVEL, Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SPEAR, VAItems.IRON_HALBERD,
                    Items.GOLDEN_SWORD, Items.GOLDEN_SHOVEL, Items.GOLDEN_PICKAXE, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SPEAR, VAItems.GOLDEN_HALBERD,
                    VAItems.STEEL_SWORD, VAItems.STEEL_SHOVEL, VAItems.STEEL_PICKAXE, VAItems.STEEL_AXE, VAItems.STEEL_HOE, VAItems.STEEL_SPEAR, VAItems.STEEL_HALBERD,
                    Items.DIAMOND_SWORD, Items.DIAMOND_SHOVEL, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_HOE, Items.DIAMOND_SPEAR, VAItems.DIAMOND_HALBERD,
                    Items.NETHERITE_SWORD, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_HOE, Items.NETHERITE_SPEAR, VAItems.NETHERITE_HALBERD
            );

            addTo(VAItemTags.BASE_DYE, 
                    Items.RED_DYE,
                    Items.GREEN_DYE,
                    Items.BLUE_DYE,
                    Items.YELLOW_DYE,
                    Items.BLACK_DYE,
                    Items.WHITE_DYE
            );

            addTo(VAItemTags.COLORABLE_GLASS, 
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

            addTo(VAItemTags.COLORABLE_GLASS_PANE, 
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

            addTo(ItemTags.SHULKER_BOXES, 
                    VAItems.LILAC_SHULKER_BOX,
                    VAItems.MAROON_SHULKER_BOX,
                    VAItems.SINOPIA_SHULKER_BOX,
                    VAItems.TAN_SHULKER_BOX,
                    VAItems.CHARTREUSE_SHULKER_BOX,
                    VAItems.VIRIDIAN_SHULKER_BOX,
                    VAItems.INDIGO_SHULKER_BOX,
                    VAItems.PLUM_SHULKER_BOX
            );

            addTo(VAItemTags.CLIMBING_ROPES, 
                    VAItems.CLIMBING_ROPE,
                    VAItems.EXPOSED_CLIMBING_ROPE,
                    VAItems.WEATHERED_CLIMBING_ROPE,
                    VAItems.OXIDIZED_CLIMBING_ROPE,
                    VAItems.WAXED_CLIMBING_ROPE,
                    VAItems.WAXED_EXPOSED_CLIMBING_ROPE,
                    VAItems.WAXED_WEATHERED_CLIMBING_ROPE,
                    VAItems.WAXED_OXIDIZED_CLIMBING_ROPE
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
                    VACollections.SYENITE_BRICKS,
                    VACollections.ROCK_SALT_BRICKS
            );

            configureToolSet(VAItems.STEEL_TOOL_SET);

            addTo(VAItemTags.HALBERDS, VAItems.WOODEN_HALBERD, VAItems.STONE_HALBERD, VAItems.COPPER_HALBERD, VAItems.IRON_HALBERD, VAItems.GOLDEN_HALBERD, VAItems.STEEL_HALBERD, VAItems.DIAMOND_HALBERD, VAItems.NETHERITE_HALBERD);
            getOrCreateRawBuilder(VAItemTags.HALBERD_ENCHANTABLE).addOptionalTag(VAItemTags.HALBERDS.location());
            getOrCreateRawBuilder(VAItemTags.PUMMELING_ENCHANTABLE)
                    .addOptionalTag(VAItemTags.HALBERDS.location())
                    .addOptionalTag(ItemTags.AXES.location())
                    .addOptionalTag(ItemTags.SWORDS.location());
            getOrCreateRawBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE).addOptionalTag(VAItemTags.HALBERDS.location());
            getOrCreateRawBuilder(ItemTags.MELEE_WEAPON_ENCHANTABLE).addOptionalTag(VAItemTags.HALBERDS.location());
            getOrCreateRawBuilder(ItemTags.DURABILITY_ENCHANTABLE).addOptionalTag(VAItemTags.HALBERDS.location());
            getOrCreateRawBuilder(ItemTags.VANISHING_ENCHANTABLE).addOptionalTag(VAItemTags.HALBERDS.location());
            getOrCreateRawBuilder(ItemTags.BREAKS_DECORATED_POTS).addOptionalTag(VAItemTags.HALBERDS.location());

            addTo(ItemTags.STONE_CRAFTING_MATERIALS, VAItems.PORPHYRY);
            addTo(ItemTags.STONE_TOOL_MATERIALS, VAItems.PORPHYRY);
            addTo(ItemTags.LOGS_THAT_BURN, VAItems.SOULBLOOM_LOG, VAItems.SOULBLOOM_WOOD, VAItems.STRIPPED_SOULBLOOM_LOG, VAItems.STRIPPED_SOULBLOOM_WOOD);
            addTo(ItemTags.PLANKS, VAItems.SOULBLOOM_PLANKS);
            addTo(ItemTags.SIGNS, VAItems.SOULBLOOM_SIGN);
            addTo(ItemTags.HANGING_SIGNS, VAItems.SOULBLOOM_HANGING_SIGN);
            addTo(ItemTags.FENCES, VAItems.SOULBLOOM_FENCE);
            addTo(ItemTags.DOORS, VAItems.SOULBLOOM_DOOR);
            addTo(ItemTags.TRAPDOORS, VAItems.SOULBLOOM_TRAPDOOR);
            addTo(ItemTags.WOODEN_SHELVES, VAItems.SOULBLOOM_SHELF);
            addTo(ItemTags.LEAVES, VAItems.SOULBLOOM_LEAVES);
            addTo(ItemTags.SAPLINGS, VAItems.SOULBLOOM_SAPLING);
            
            addTo(ItemTags.LOGS, VAItems.WITHERED_LOG, VAItems.WITHERED_WOOD);//, VAItems.STRIPPED_WITHERED_LOG, VAItems.STRIPPED_WITHERED_WOOD);
            addTo(ItemTags.PLANKS, VAItems.WITHERED_PLANKS);
            addTo(ItemTags.SIGNS, VAItems.WITHERED_SIGN);
            addTo(ItemTags.HANGING_SIGNS, VAItems.WITHERED_HANGING_SIGN);
            addTo(ItemTags.FENCES, VAItems.WITHERED_FENCE);
            addTo(ItemTags.DOORS, VAItems.WITHERED_DOOR);
            addTo(ItemTags.TRAPDOORS, VAItems.WITHERED_TRAPDOOR);
            addTo(ItemTags.WOODEN_SHELVES, VAItems.WITHERED_SHELF);
            addTo(ItemTags.LEAVES, VAItems.WITHERED_LEAVES);
            addTo(ItemTags.SAPLINGS, VAItems.WITHERED_SAPLING);

            addTo(VAItemTags.LUMWASP_LARVAE_FOOD, 
                    VAItems.PORPHYRY
            );
            addTo(VAItemTags.SOULBLOOM_LOGS, 
                    VAItems.SOULBLOOM_LOG,
                    VAItems.SOULBLOOM_WOOD,
                    VAItems.STRIPPED_SOULBLOOM_LOG,
                    VAItems.STRIPPED_SOULBLOOM_WOOD
            );
            addTo(VAItemTags.WITHERED_LOGS, 
                    VAItems.WITHERED_LOG,
                    VAItems.WITHERED_WOOD,
                    VAItems.STRIPPED_WITHERED_LOG,
                    VAItems.STRIPPED_WITHERED_WOOD
            );
            configureWoodenBlockFamilies(
                    VACollections.SOULBLOOM,
                    VACollections.WITHERED
            );
            configureBlockFamilies(
                    VACollections.PORPHYRY,
                    VACollections.POLISHED_PORPHYRY,
                    VACollections.PORPHYRY_BRICKS
            );
        }
    }

    private static class PreviewProvider extends Provider {
        public PreviewProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            addTo(FOODS,VAItems.BALLOON_FRUIT);
        }
    }

    private abstract static class Provider extends FabricTagProvider.ItemTagProvider {
        protected static final TagKey<Item> INGOTS = TagKey.create(Registries.ITEM, Identifier.parse("c:ingots"));
        protected static final TagKey<Item> STEEL_INGOTS = TagKey.create(Registries.ITEM, Identifier.parse("c:steel_ingots"));
        protected static final TagKey<Item> RAW_ORES = TagKey.create(Registries.ITEM, Identifier.parse("c:raw_materials"));
        protected static final TagKey<Item> GEMS = TagKey.create(Registries.ITEM, Identifier.parse("c:gems"));
        protected static final TagKey<Item> FOODS = TagKey.create(Registries.ITEM, Identifier.parse("c:foods"));
        protected static final TagKey<Item> IOLITE = TagKey.create(Registries.ITEM, Identifier.parse("c:iolite"));
        protected static final TagKey<Item> IOLITE_ORES = TagKey.create(Registries.ITEM, Identifier.parse("c:iolite_ores"));
        protected static final TagKey<Item> ORES = TagKey.create(Registries.ITEM, Identifier.parse("c:ores"));
        protected static final TagKey<Item> POTIONS = TagKey.create(Registries.ITEM, Identifier.parse("c:potions"));

        public Provider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }
        
        protected TagBuilder addTo(TagKey<Item> tag, ItemLike... items) {
            TagBuilder builder = getOrCreateRawBuilder(tag);
            for (ItemLike item : items) {
                builder.addElement(BuiltInRegistries.ITEM.getKey(item.asItem()));
            }
            return builder;
        }

        protected void configureBlockFamilies(BlockFamily... families) {
            for (BlockFamily family : families) {
                family.getVariants().forEach((variant, block) -> {
                    switch (variant) {
                        case STAIRS -> addTo(ItemTags.STAIRS, block.asItem());
                        case SLAB -> addTo(ItemTags.SLABS, block.asItem());
                        case WALL -> addTo(ItemTags.WALLS, block.asItem());
                        case FENCE -> addTo(ItemTags.FENCES, block.asItem());
                        case FENCE_GATE -> addTo(ItemTags.FENCE_GATES, block.asItem());
                        case DOOR -> addTo(ItemTags.DOORS, block.asItem());
                        case TRAPDOOR -> addTo(ItemTags.TRAPDOORS, block.asItem());
                        case BUTTON -> addTo(ItemTags.BUTTONS, block.asItem());
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
                        case STAIRS -> addTo(ItemTags.WOODEN_STAIRS, block.asItem());
                        case SLAB -> addTo(ItemTags.WOODEN_SLABS, block.asItem());
                        case FENCE -> addTo(ItemTags.WOODEN_FENCES, block.asItem());
                        case DOOR -> addTo(ItemTags.WOODEN_DOORS, block.asItem());
                        case TRAPDOOR -> addTo(ItemTags.WOODEN_TRAPDOORS, block.asItem());
                        case BUTTON -> addTo(ItemTags.WOODEN_BUTTONS, block.asItem());
                        case PRESSURE_PLATE -> addTo(ItemTags.WOODEN_PRESSURE_PLATES, block.asItem());
                    }
                });
            }
        }

        protected void configureToolSet(RegistryHelper.ItemRegistryHelper.ToolSet set) {
            addTo(ItemTags.AXES, set.AXE());
            addTo(ItemTags.HOES, set.HOE());
            addTo(ItemTags.PICKAXES, set.PICKAXE());
            addTo(ItemTags.SHOVELS, set.SHOVEL());
            addTo(ItemTags.SWORDS, set.SWORD());
            addTo(VAItemTags.HALBERDS, set.HALBERD());

            addTo(ItemTags.CLUSTER_MAX_HARVESTABLES, set.PICKAXE());
        }

        protected void configureToolSets(TagKey<Item> tag, RegistryHelper.ItemRegistryHelper.ToolSet... sets) {
            TagBuilder builder = getOrCreateRawBuilder(tag);
            Arrays.stream(sets).iterator().forEachRemaining(set -> set.forEach(item -> builder.addElement(BuiltInRegistries.ITEM.getKey(item))));
        }
    }
}
