package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABiomeTags;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import net.minecraft.core.Holder;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.animal.sheep.SheepColorSpawnRules;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepColorSpawnRules.class)
public abstract class SheepColorsMixin {

    @Shadow
    protected static SheepColorSpawnRules.SheepColorProvider weighted(WeightedList<SheepColorSpawnRules.SheepColorProvider> weightedList) {
        return null;
    }

    @Shadow
    protected static WeightedList.Builder<SheepColorSpawnRules.SheepColorProvider> builder() {
        return null;
    }

    @Shadow
    protected static SheepColorSpawnRules.SheepColorProvider single(DyeColor dyeColor) {
        return null;
    }

    @Unique private static final SheepColorSpawnRules.SheepColorSpawnConfiguration VIRTUAL_ADDITIONS_ENCHANTED = new SheepColorSpawnRules.SheepColorSpawnConfiguration(weighted(builder().add(single(DyeColor.LIGHT_BLUE), 5).add(single(VADyeColors.VIRIDIAN), 5).add(single(DyeColor.BLUE), 5).add(single(VADyeColors.INDIGO), 3).add(single(DyeColor.CYAN), 82).build()));


    @Inject(method = "getSheepColorConfiguration", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$getSpawnConfig(Holder<Biome> biome, CallbackInfoReturnable<SheepColorSpawnRules.SheepColorSpawnConfiguration> cir) {
        if (biome.is(VABiomeTags.SPAWNS_ENCHANTED_VARIANT_FARM_ANIMALS)) cir.setReturnValue(VIRTUAL_ADDITIONS_ENCHANTED);
    }
}
