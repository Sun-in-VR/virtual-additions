package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;

import java.util.Map;

public class VACauldronBehaviors {
    protected static void init() {
        Map<Item, CauldronBehavior> waterBehaviors = CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map();
        waterBehaviors.put(VAItems.ENGRAVING_CHISEL, CauldronBehavior::cleanArmor);
        waterBehaviors.put(VAItems.CHARTREUSE_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.MAROON_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.INDIGO_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.PLUM_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.VIRIDIAN_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.TAN_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.SINOPIA_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.LILAC_SHULKER_BOX, CauldronBehavior::cleanShulkerBox);
        waterBehaviors.put(VAItems.CHARTREUSE_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.MAROON_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.INDIGO_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.PLUM_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.VIRIDIAN_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.TAN_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.SINOPIA_BANNER, CauldronBehavior::cleanBanner);
        waterBehaviors.put(VAItems.LILAC_BANNER, CauldronBehavior::cleanBanner);
    }
}
