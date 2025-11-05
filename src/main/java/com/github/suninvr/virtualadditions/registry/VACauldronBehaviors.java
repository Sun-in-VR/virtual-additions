package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;

import java.util.Map;

public class VACauldronBehaviors {
    protected static void init() {
        Map<Item, CauldronInteraction> waterBehaviors = CauldronInteraction.WATER.map();
        waterBehaviors.put(VAItems.ENGRAVING_CHISEL, CauldronInteraction::dyedItemIteration);
        waterBehaviors.put(VAItems.CHARTREUSE_SHULKER_BOX, CauldronInteraction::shulkerBoxInteraction);
        waterBehaviors.put(VAItems.MAROON_SHULKER_BOX, CauldronInteraction::shulkerBoxInteraction);
        waterBehaviors.put(VAItems.INDIGO_SHULKER_BOX, CauldronInteraction::shulkerBoxInteraction);
        waterBehaviors.put(VAItems.PLUM_SHULKER_BOX, CauldronInteraction::shulkerBoxInteraction);
        waterBehaviors.put(VAItems.VIRIDIAN_SHULKER_BOX, CauldronInteraction::shulkerBoxInteraction);
        waterBehaviors.put(VAItems.TAN_SHULKER_BOX, CauldronInteraction::shulkerBoxInteraction);
        waterBehaviors.put(VAItems.SINOPIA_SHULKER_BOX, CauldronInteraction::shulkerBoxInteraction);
        waterBehaviors.put(VAItems.LILAC_SHULKER_BOX, CauldronInteraction::shulkerBoxInteraction);
        waterBehaviors.put(VAItems.CHARTREUSE_BANNER, CauldronInteraction::bannerInteraction);
        waterBehaviors.put(VAItems.MAROON_BANNER, CauldronInteraction::bannerInteraction);
        waterBehaviors.put(VAItems.INDIGO_BANNER, CauldronInteraction::bannerInteraction);
        waterBehaviors.put(VAItems.PLUM_BANNER, CauldronInteraction::bannerInteraction);
        waterBehaviors.put(VAItems.VIRIDIAN_BANNER, CauldronInteraction::bannerInteraction);
        waterBehaviors.put(VAItems.TAN_BANNER, CauldronInteraction::bannerInteraction);
        waterBehaviors.put(VAItems.SINOPIA_BANNER, CauldronInteraction::bannerInteraction);
        waterBehaviors.put(VAItems.LILAC_BANNER, CauldronInteraction::bannerInteraction);
    }
}
