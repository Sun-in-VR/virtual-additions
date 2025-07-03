package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABiomeTags;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import net.minecraft.entity.passive.SheepColors;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.collection.Pool;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepColors.class)
public abstract class SheepColorsMixin {

    @Shadow
    protected static SheepColors.ColorSelector createCombinedSelector(Pool<SheepColors.ColorSelector> pool) {
        return null;
    }

    @Shadow
    protected static Pool.Builder<SheepColors.ColorSelector> poolBuilder() {
        return null;
    }

    @Shadow
    protected static SheepColors.ColorSelector createSingleSelector(DyeColor color) {
        return null;
    }

    @Shadow
    protected static SheepColors.ColorSelector createDefaultSelector(DyeColor color) {
        return null;
    }

    @Unique private static final SheepColors.SpawnConfig VIRTUAL_ADDITIONS_ENCHANTED = new SheepColors.SpawnConfig(createCombinedSelector(poolBuilder().add(createSingleSelector(DyeColor.LIGHT_BLUE), 5).add(createSingleSelector(VADyeColors.VIRIDIAN), 5).add(createSingleSelector(DyeColor.BLUE), 5).add(createSingleSelector(VADyeColors.INDIGO), 3).add(createDefaultSelector(DyeColor.CYAN), 82).build()));


    @Inject(method = "getSpawnConfig", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$getSpawnConfig(RegistryEntry<Biome> biome, CallbackInfoReturnable<SheepColors.SpawnConfig> cir) {
        if (biome.isIn(VABiomeTags.SPAWNS_ENCHANTED_VARIANT_FARM_ANIMALS)) cir.setReturnValue(VIRTUAL_ADDITIONS_ENCHANTED);
    }
}
