package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.ComposterBlock;

public class VACompostables {
    protected static void init() {
        ComposterBlock.registerCompostableItem(0.65F, VAItems.COTTON_SEEDS);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.COTTON);
        ComposterBlock.registerCompostableItem(0.65F, VAItems.CORN);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.CORN_SEEDS);
        ComposterBlock.registerCompostableItem(0.65F, VAItems.TOMATO);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.TOMATO_SEEDS);
        ComposterBlock.registerCompostableItem(0.65F, VAItems.CABBAGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.CABBAGE_SEEDS);
        ComposterBlock.registerCompostableItem(0.85F, VAItems.GREENCAP_MUSHROOM);
        ComposterBlock.registerCompostableItem(0.85F, VAItems.TALL_GREENCAP_MUSHROOMS);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.BLUE_PETALS);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.SOULBLOOM_SAPLING);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.SOULBLOOM_LEAVES);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.OAK_HEDGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.SPRUCE_HEDGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.BIRCH_HEDGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.JUNGLE_HEDGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.ACACIA_HEDGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.DARK_OAK_HEDGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.PALE_OAK_HEDGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.MANGROVE_HEDGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.CHERRY_HEDGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.AZALEA_HEDGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.FLOWERING_AZALEA_HEDGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.SOULBLOOM_HEDGE);
    }
}
