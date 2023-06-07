package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("unused")
@Mixin(ComposterBlock.class)
public interface ComposterBlockAccessor {

    @Invoker("registerCompostableItem")
    static void virtualAdditions$registerCompostableItem(float levelIncreaseChance, ItemConvertible item) {
        throw new AssertionError();
    }
}
