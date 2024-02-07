package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.block.BalloonBulbPlantBlock;
import com.github.suninvr.virtualadditions.registry.VACollections;
import com.github.suninvr.virtualadditions.registry.VABlocks;
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
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.state.property.Properties;

@SuppressWarnings("SameParameterValue")
public final class VABlockLootTableProvider {
    public static final VABlockLootTableProvider INSTANCE = new VABlockLootTableProvider();

    public FabricDataGenerator.Pack.Factory<?> base() {
        return Base::new;
    }
    public FabricDataGenerator.Pack.Factory<?> preview() {
        return Preview::new;
    }

    private static class Base extends FabricBlockLootTableProvider {

        protected Base(FabricDataOutput dataOutput) {
            super(dataOutput);
        }

        @Override
        public void generate() {
            addFamilyDrops(
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

            addSimpleDrops(
                    VABlocks.CLIMBING_ROPE_ANCHOR,
                    VABlocks.RAW_STEEL_BLOCK,
                    VABlocks.STEEL_BLOCK,
                    VABlocks.STEEL_FENCE,
                    VABlocks.STEEL_TRAPDOOR,
                    VABlocks.REDSTONE_BRIDGE,
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
                    VABlocks.ENTANGLEMENT_DRIVE
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
            this.addDrop(VABlocks.HORNFELS, block -> this.drops(block, VABlocks.COBBLED_HORNFELS));
            this.addDrop(VABlocks.BLUESCHIST, block -> this.drops(block, VABlocks.COBBLED_BLUESCHIST));
            this.addDrop(VABlocks.SYENITE, block -> this.drops(block, VABlocks.COBBLED_SYENITE));
            this.addDrop(VABlocks.LUMWASP_NEST, block -> this.drops(block, VABlocks.SILK_BLOCK));
            this.addDrop(VABlocks.GLOWING_SILK, block -> this.drops(block, VAItems.SILK_THREAD));
            this.addDrop(VABlocks.IOLITE_ORE, block -> this.oreDrops(block, VAItems.IOLITE));
            this.addDrop(VABlocks.TALL_GREENCAP_MUSHROOMS, (Block block) -> this.dropsWithProperty(block, TallPlantBlock.HALF, DoubleBlockHalf.LOWER));

            this.addDrop(VABlocks.REDSTONE_BRIDGE);
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
            set.ifCandle(block -> this.lootTables.put(block.getLootTableId(), this.candleDrops(block)));
            set.ifCandleCake(block -> this.lootTables.put(block.getLootTableId(), BlockLootTableGenerator.candleCakeDrops(block)));
            set.ifBed(block -> this.lootTables.put(block.getLootTableId(), this.dropsWithProperty(block, BedBlock.PART, BedPart.HEAD)));
            set.ifShulkerBox(block -> this.lootTables.put(block.getLootTableId(), this.shulkerBoxDrops(block)));
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
            this.lootTables.put(block.getLootTableId(), lootTable.randomSequenceId(block.getLootTableId()));
        }

        @Override
        public LootTable.Builder oreDrops(Block dropWithSilkTouch, Item drop) {
            return BlockLootTableGenerator
                    .dropsWithSilkTouch(dropWithSilkTouch, this.applyExplosionDecay(dropWithSilkTouch, ItemEntry.builder(drop).apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))))
                    .randomSequenceId(dropWithSilkTouch.getLootTableId());
        }
    }

    private static class Preview extends Base {
        public Preview(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generate() {
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
                    VABlocks.SPRINGSOIL,
                    VABlocks.AEROBLOOM_HEDGE,
                    VABlocks.RED_GLIMMER_CRYSTAL,
                    VABlocks.GREEN_GLIMMER_CRYSTAL,
                    VABlocks.BLUE_GLIMMER_CRYSTAL,
                    VABlocks.SPOTLIGHT
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

            this.addDrop(VABlocks.BALLOON_BULB, block -> this.drops(VAItems.BALLOON_FRUIT));
            this.addDrop(VABlocks.BALLOON_BULB_PLANT, block -> new LootTable.Builder().pool(LootPool.builder().with(ItemEntry.builder(VAItems.BALLOON_FRUIT).conditionally(BlockStatePropertyLootCondition.builder(VABlocks.BALLOON_BULB_PLANT).properties(StatePredicate.Builder.create().exactMatch(BalloonBulbPlantBlock.AGE, 3))).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 3))))));

            this.addDrop(VABlocks.AEROBLOOM_LEAVES, block ->  leavesDrops(VABlocks.AEROBLOOM_LEAVES, VABlocks.AEROBLOOM_SAPLING, SAPLING_DROP_CHANCE));
        }
    }
}
