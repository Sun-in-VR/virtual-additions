package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VABlockFamilies;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

@SuppressWarnings("SameParameterValue")
class VABlockLootTableProvider extends FabricBlockLootTableProvider {

    protected VABlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
        addFamilyDrops(
                VABlockFamilies.CUT_STEEL,
                VABlockFamilies.COBBLED_HORNFELS,
                VABlockFamilies.COBBLED_BLUESCHIST,
                VABlockFamilies.COBBLED_SYENITE,
                VABlockFamilies.POLISHED_HORNFELS,
                VABlockFamilies.POLISHED_BLUESCHIST,
                VABlockFamilies.POLISHED_SYENITE,
                VABlockFamilies.HORNFELS_TILES,
                VABlockFamilies.BLUESCHIST_BRICKS,
                VABlockFamilies.SYENITE_BRICKS,
                VABlockFamilies.AEROBLOOM,
                VABlockFamilies.FLOATSTONE
        );

        addSimpleDrops(
                VABlocks.CLIMBING_ROPE_ANCHOR,
                VABlocks.RAW_STEEL_BLOCK,
                VABlocks.STEEL_BLOCK,
                VABlocks.STEEL_FENCE,
                VABlocks.STEEL_TRAPDOOR,
                VABlocks.REDSTONE_BRIDGE,
                VABlocks.AEROBLOOM_LOG,
                VABlocks.AEROBLOOM_WOOD,
                VABlocks.STRIPPED_AEROBLOOM_LOG,
                VABlocks.STRIPPED_AEROBLOOM_WOOD,
                VABlocks.AEROBLOOM_HANGING_SIGN,
                VABlocks.AEROBLOOM_SAPLING,
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
                VABlocks.IOLITE_BLOCK,
                VABlocks.WARP_ANCHOR,
                VABlocks.ENTANGLEMENT_DRIVE
        );

        this.addDrop(VABlocks.STEEL_DOOR, this::doorDrops);
        this.addDrop(VABlocks.HORNFELS, block -> this.drops(block, VABlocks.COBBLED_HORNFELS));
        this.addDrop(VABlocks.BLUESCHIST, block -> this.drops(block, VABlocks.COBBLED_BLUESCHIST));
        this.addDrop(VABlocks.SYENITE, block -> this.drops(block, VABlocks.COBBLED_SYENITE));
        this.addDrop(VABlocks.LUMWASP_NEST, block -> this.drops(block, VABlocks.SILK_BLOCK));
        this.addDrop(VABlocks.GLOWING_SILK, block -> this.drops(block, VAItems.SILK_THREAD));
        this.addDrop(VABlocks.IOLITE_ORE, block -> this.oreDrops(block, VAItems.IOLITE));
        this.addDrop(VABlocks.TALL_GREENCAP_MUSHROOMS, (Block block) -> this.dropsWithProperty(block, TallPlantBlock.HALF, DoubleBlockHalf.LOWER));
        this.addDrop(VABlocks.AEROBLOOM_LEAVES, block ->  aerobloomLeavesDrops(VABlocks.AEROBLOOM_LEAVES, VABlocks.AEROBLOOM_SAPLING, SAPLING_DROP_CHANCE));
    }

    @Override
    public void generate() {
        addDrop(VABlocks.REDSTONE_BRIDGE);
    }

    private void addFamilyDrops(BlockFamily... blockFamilies) {
        for (BlockFamily family : blockFamilies) {
            addDrop(family.getBaseBlock());
            family.getVariants().forEach(this::addDrop);
        }
    }

    public void addDrop(BlockFamily.Variant variant, Block block) {
        switch (variant) {
            case SLAB -> this.addDrop(block, this::slabDrops);
            case DOOR -> this.addDrop(block, this::doorDrops);
            default -> this.addDrop(block);
        }
    }

    private void addSimpleDrops(Block... blocks) {
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

    public LootTable.Builder aerobloomLeavesDrops(Block leaves, Block drop, float ... chance) {
        return this.leavesDrops(leaves, drop, chance).pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f)).conditionally(WITHOUT_SILK_TOUCH_NOR_SHEARS).with(((LeafEntry.Builder<?>)this.addSurvivesExplosionCondition(leaves, ItemEntry.builder(VAItems.BALLOON_FRUIT))).conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.005f, 0.0055555557f, 0.00625f, 0.008333334f, 0.025f))));
    }
}
