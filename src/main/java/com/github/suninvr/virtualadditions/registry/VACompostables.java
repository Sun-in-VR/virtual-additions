package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.ComposterBlock;

public class VACompostables {
    protected static void init() {
        ComposterBlock.registerCompostableItem(0.3F, VAItems.COTTON_SEEDS);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.COTTON);
        ComposterBlock.registerCompostableItem(0.65F, VAItems.CORN);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.CORN_SEEDS);
        ComposterBlock.registerCompostableItem(0.65F, VAItems.TOMATO);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.TOMATO_SEEDS);
        ComposterBlock.registerCompostableItem(0.65F, VAItems.CABBAGE);
        ComposterBlock.registerCompostableItem(0.3F, VAItems.CABBAGE_SEEDS);
    }
}
