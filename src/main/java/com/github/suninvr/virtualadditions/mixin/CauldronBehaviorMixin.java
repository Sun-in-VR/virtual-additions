package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {

    @Inject(method = "registerBehavior", at = @At("TAIL"))
    private static void virtualAdditions$registerExtraBehavior(CallbackInfo ci, @Local(ordinal = 1) Map<Item, CauldronBehavior> map2) {
        //if (!VirtualAdditions.areBlocksInitialized()) return;
        //map2.put(VAItems.ENGRAVING_CHISEL, CauldronBehavior.CLEAN_DYEABLE_ITEM);
        //map2.put(VAItems.CHARTREUSE_SHULKER_BOX, CauldronBehavior.CLEAN_SHULKER_BOX);
        //map2.put(VAItems.MAROON_SHULKER_BOX, CauldronBehavior.CLEAN_SHULKER_BOX);
        //map2.put(VAItems.INDIGO_SHULKER_BOX, CauldronBehavior.CLEAN_SHULKER_BOX);
        //map2.put(VAItems.PLUM_SHULKER_BOX, CauldronBehavior.CLEAN_SHULKER_BOX);
        //map2.put(VAItems.VIRIDIAN_SHULKER_BOX, CauldronBehavior.CLEAN_SHULKER_BOX);
        //map2.put(VAItems.TAN_SHULKER_BOX, CauldronBehavior.CLEAN_SHULKER_BOX);
        //map2.put(VAItems.SINOPIA_SHULKER_BOX, CauldronBehavior.CLEAN_SHULKER_BOX);
        //map2.put(VAItems.LILAC_SHULKER_BOX, CauldronBehavior.CLEAN_SHULKER_BOX);
        //map2.put(VAItems.CHARTREUSE_BANNER, CauldronBehavior.CLEAN_BANNER);
        //map2.put(VAItems.MAROON_BANNER, CauldronBehavior.CLEAN_BANNER);
        //map2.put(VAItems.INDIGO_BANNER, CauldronBehavior.CLEAN_BANNER);
        //map2.put(VAItems.PLUM_BANNER, CauldronBehavior.CLEAN_BANNER);
        //map2.put(VAItems.VIRIDIAN_BANNER, CauldronBehavior.CLEAN_BANNER);
        //map2.put(VAItems.TAN_BANNER, CauldronBehavior.CLEAN_BANNER);
        //map2.put(VAItems.SINOPIA_BANNER, CauldronBehavior.CLEAN_BANNER);
        //map2.put(VAItems.LILAC_BANNER, CauldronBehavior.CLEAN_BANNER);
    }
}
