package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.block.BalloonBulbPlantBlock;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VACollections;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("SameParameterValue")
public final class VABlockLootTableProvider {

    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> base() {
        return BaseProvider::new;
    }
    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> preview() {
        return PreviewProvider::new;
    }

    private static class BaseProvider extends Provider {

        protected BaseProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            addFamilyDrops(
                    VACollections.CUT_STEEL,
                    VACollections.EXPOSED_CUT_STEEL,
                    VACollections.WEATHERED_CUT_STEEL,
                    VACollections.OXIDIZED_CUT_STEEL,
                    VACollections.WAXED_CUT_STEEL,
                    VACollections.WAXED_EXPOSED_CUT_STEEL,
                    VACollections.WAXED_WEATHERED_CUT_STEEL,
                    VACollections.WAXED_OXIDIZED_CUT_STEEL,
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

            addSimpleDrops(
                    VABlocks.CLIMBING_ROPE_ANCHOR,
                    VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.RAW_STEEL_BLOCK,
                    VABlocks.STEEL_BLOCK,
                    VABlocks.EXPOSED_STEEL_BLOCK,
                    VABlocks.WEATHERED_STEEL_BLOCK,
                    VABlocks.OXIDIZED_STEEL_BLOCK,
                    VABlocks.WAXED_STEEL_BLOCK,
                    VABlocks.WAXED_EXPOSED_STEEL_BLOCK,
                    VABlocks.WAXED_WEATHERED_STEEL_BLOCK,
                    VABlocks.WAXED_OXIDIZED_STEEL_BLOCK,
                    VABlocks.STEEL_FENCE,
                    VABlocks.STEEL_TRAPDOOR,
                    VABlocks.EXPOSED_STEEL_TRAPDOOR,
                    VABlocks.WEATHERED_STEEL_TRAPDOOR,
                    VABlocks.OXIDIZED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_OXIDIZED_STEEL_TRAPDOOR,
                    VABlocks.STEEL_GRATE,
                    VABlocks.REDSTONE_BRIDGE,
                    VABlocks.CAGELIGHT,
                    VABlocks.OAK_HEDGE,
                    VABlocks.SPRUCE_HEDGE,
                    VABlocks.BIRCH_HEDGE,
                    VABlocks.JUNGLE_HEDGE,
                    VABlocks.ACACIA_HEDGE,
                    VABlocks.DARK_OAK_HEDGE,
                    VABlocks.MANGROVE_HEDGE,
                    VABlocks.CHERRY_HEDGE,
                    VABlocks.AZALEA_HEDGE,
                    VABlocks.FLOWERING_AZALEA_HEDGE,
                    VABlocks.SILK_BLOCK,
                    VABlocks.WEBBED_SILK,
                    VABlocks.FRAYED_SILK,
                    VABlocks.GREENCAP_MUSHROOM,
                    VABlocks.SILKBULB,
                    VABlocks.WHITE_SILKBULB,
                    VABlocks.LIGHT_GRAY_SILKBULB,
                    VABlocks.GRAY_SILKBULB,
                    VABlocks.BLACK_SILKBULB,
                    VABlocks.BROWN_SILKBULB,
                    VABlocks.RED_SILKBULB,
                    VABlocks.ORANGE_SILKBULB,
                    VABlocks.YELLOW_SILKBULB,
                    VABlocks.LIME_SILKBULB,
                    VABlocks.GREEN_SILKBULB,
                    VABlocks.CYAN_SILKBULB,
                    VABlocks.LIGHT_BLUE_SILKBULB,
                    VABlocks.BLUE_SILKBULB,
                    VABlocks.PURPLE_SILKBULB,
                    VABlocks.MAGENTA_SILKBULB,
                    VABlocks.PINK_SILKBULB,
                    VABlocks.COLORING_STATION,
                    VABlocks.ACID_BLOCK,
                    VABlocks.IOLITE_BLOCK,
                    VABlocks.WARP_ANCHOR,
                    VABlocks.ENTANGLEMENT_DRIVE,
                    VABlocks.SPOTLIGHT
            );

            addColorfulBlockSetDrops(VACollections.CHARTREUSE);
            addColorfulBlockSetDrops(VACollections.MAROON);
            addColorfulBlockSetDrops(VACollections.INDIGO);
            addColorfulBlockSetDrops(VACollections.PLUM);
            addColorfulBlockSetDrops(VACollections.VIRIDIAN);
            addColorfulBlockSetDrops(VACollections.TAN);
            addColorfulBlockSetDrops(VACollections.SINOPIA);
            addColorfulBlockSetDrops(VACollections.LILAC);


            this.addDrop(VABlocks.STEEL_DOOR, this::doorDrops);
            this.addDrop(VABlocks.EXPOSED_STEEL_DOOR, this::doorDrops);
            this.addDrop(VABlocks.WEATHERED_STEEL_DOOR, this::doorDrops);
            this.addDrop(VABlocks.OXIDIZED_STEEL_DOOR, this::doorDrops);
            this.addDrop(VABlocks.WAXED_STEEL_DOOR, this::doorDrops);
            this.addDrop(VABlocks.WAXED_EXPOSED_STEEL_DOOR, this::doorDrops);
            this.addDrop(VABlocks.WAXED_WEATHERED_STEEL_DOOR, this::doorDrops);
            this.addDrop(VABlocks.WAXED_OXIDIZED_STEEL_DOOR, this::doorDrops);
            this.addDrop(VABlocks.HORNFELS, block -> this.drops(block, VABlocks.COBBLED_HORNFELS));
            this.addDrop(VABlocks.BLUESCHIST, block -> this.drops(block, VABlocks.COBBLED_BLUESCHIST));
            this.addDrop(VABlocks.SYENITE, block -> this.drops(block, VABlocks.COBBLED_SYENITE));
            this.addDrop(VABlocks.LUMWASP_NEST, block -> this.drops(block, VABlocks.SILK_BLOCK));
            this.addDrop(VABlocks.GLOWING_SILK, block -> this.drops(block, VAItems.SILK_THREAD));
            this.addDrop(VABlocks.IOLITE_ORE, block -> this.oreDrops(block, VAItems.IOLITE));
            this.addDrop(VABlocks.TALL_GREENCAP_MUSHROOMS, (Block block) -> this.dropsWithProperty(block, TallPlantBlock.HALF, DoubleBlockHalf.LOWER));

            this.addDrop(VABlocks.ROCK_SALT_BLOCK, (block) -> this.drops(block, VAItems.ROCK_SALT, ConstantLootNumberProvider.create(4.0F)));
            this.addDrop(VABlocks.ROCK_SALT_CRYSTAL, (block) -> this.drops(block, VAItems.ROCK_SALT, ConstantLootNumberProvider.create(2.0F)));

            this.addDrop(VABlocks.REDSTONE_BRIDGE);

            addFamilyDrops(
                    VACollections.AEROBLOOM,
                    VACollections.FLOATROCK,
                    VACollections.POLISHED_FLOATROCK,
                    VACollections.FLOATROCK_BRICKS
            );
            addSimpleDrops(
                    VABlocks.AEROBLOOM_LOG,
                    VABlocks.AEROBLOOM_WOOD,
                    VABlocks.STRIPPED_AEROBLOOM_LOG,
                    VABlocks.STRIPPED_AEROBLOOM_WOOD,
                    VABlocks.AEROBLOOM_HANGING_SIGN,
                    VABlocks.AEROBLOOM_SAPLING,
                    VABlocks.AEROBLOOM_HEDGE
            );

            this.addDrop(VABlocks.GRASSY_FLOATROCK, block -> this.drops(block, VABlocks.FLOATROCK));

            this.addDrop(VABlocks.FLOATROCK_COAL_ORE, block -> this.oreDrops(block, Items.COAL));
            this.addDrop(VABlocks.FLOATROCK_IRON_ORE, block -> this.oreDrops(block, Items.RAW_IRON));
            this.addDrop(VABlocks.FLOATROCK_COPPER_ORE, this::copperOreDrops);
            this.addDrop(VABlocks.FLOATROCK_GOLD_ORE, block -> this.oreDrops(block, Items.RAW_GOLD));
            this.addDrop(VABlocks.FLOATROCK_REDSTONE_ORE, this::redstoneOreDrops);
            this.addDrop(VABlocks.FLOATROCK_EMERALD_ORE, block -> this.oreDrops(block, Items.EMERALD));
            this.addDrop(VABlocks.FLOATROCK_LAPIS_ORE, this::lapisOreDrops);
            this.addDrop(VABlocks.FLOATROCK_DIAMOND_ORE, block -> this.oreDrops(block, Items.DIAMOND));

            this.addDrop(VABlocks.AEROBLOOM_LEAVES, block ->  leavesDrops(VABlocks.AEROBLOOM_LEAVES, VABlocks.AEROBLOOM_SAPLING, SAPLING_DROP_CHANCE));

        }
    }

    private static class PreviewProvider extends Provider {
        public PreviewProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generate() {
            this.addDrop(VABlocks.BALLOON_BULB, block -> this.drops(VAItems.BALLOON_FRUIT));

            this.addSimpleDrops(
                    VABlocks.SPRINGSOIL
                    );

            this.addDrop(VABlocks.BALLOON_BULB_PLANT, block -> new LootTable.Builder().pool(LootPool.builder().with(ItemEntry.builder(VAItems.BALLOON_FRUIT).conditionally(BlockStatePropertyLootCondition.builder(VABlocks.BALLOON_BULB_PLANT).properties(StatePredicate.Builder.create().exactMatch(BalloonBulbPlantBlock.AGE, 3))).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 3))))));
        }
    }

    private static abstract class Provider extends FabricBlockLootTableProvider {
        protected final RegistryEntryLookup<Enchantment> registryLookup;

        protected Provider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
            this.registryLookup = registries.getOrThrow(RegistryKeys.ENCHANTMENT);
        }

        protected void addFamilyDrops(BlockFamily... blockFamilies) {
            for (BlockFamily family : blockFamilies) {
                addDrop(family.getBaseBlock());
                family.getVariants().forEach(this::addDrop);
            }
        }

        protected void addColorfulBlockSetDrops(ColorfulBlockSet set) {
            set.ifWool(this::addDrop);
            set.ifCarpet(this::addDrop);
            set.ifTerracotta(this::addDrop);
            set.ifConcrete(this::addDrop);
            set.ifConcretePowder(this::addDrop);
            set.ifStainedGlass(this::addDropWithSilkTouch);
            set.ifStainedGlassPane(this::addDropWithSilkTouch);
            set.ifSilkbulb(this::addDrop);
            set.ifCandle(block -> this.lootTables.put(block.getLootTableKey().get(), this.candleDrops(block)));
            //set.ifCandleCake(block -> this.lootTables.put(block.getLootTableKey().get(), BlockLootTableGenerator.candleCakeDrops(block)));
            set.ifBed(block -> this.lootTables.put(block.getLootTableKey().get(), this.dropsWithProperty(block, BedBlock.PART, BedPart.HEAD)));
            set.ifShulkerBox(block -> this.lootTables.put(block.getLootTableKey().get(), this.shulkerBoxDrops(block)));
            set.ifBanner(block -> this.lootTables.put(block.getLootTableKey().get(), this.bannerDrops(block)));
            set.ifGlazedTerracotta(this::addDrop);
        }

        protected void addDrop(BlockFamily.Variant variant, Block block) {
            switch (variant) {
                case SLAB -> this.addDrop(block, this::slabDrops);
                case DOOR -> this.addDrop(block, this::doorDrops);
                default -> this.addDrop(block);
            }
        }

        protected void addSimpleDrops(Block... blocks) {
            for (Block block : blocks) {
                this.addDrop(block);
            }
        }

        @Override
        public void addDrop(Block block) {
            this.addDrop(block, block);
        }

        @Override
        public void addDrop(Block block, ItemConvertible drop) {
            this.addDrop(block, this.drops(drop));
        }

        @Override
        public void addDrop(Block block, LootTable.Builder lootTable) {
            this.lootTables.put(block.getLootTableKey().get(), lootTable.randomSequenceId(block.getLootTableKey().get().getValue()));
        }

        @Override
        public LootTable.Builder oreDrops(Block dropWithSilkTouch, Item drop) {
            return this.dropsWithSilkTouch(dropWithSilkTouch,
                            this.applyExplosionDecay(dropWithSilkTouch, ItemEntry.builder(drop)
                                    .apply(ApplyBonusLootFunction.oreDrops(this.registryLookup.getOrThrow(Enchantments.FORTUNE)))))
                    .randomSequenceId(dropWithSilkTouch.getLootTableKey().get().getValue());
        }
    }
}
