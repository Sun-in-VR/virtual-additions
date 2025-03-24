package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VABiomeKeys;
import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(VanillaBiomeParameters.class)
public abstract class VanillaBiomeParametersMixin {
    @Shadow protected abstract void writeCaveBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome);

    @Shadow @Final private MultiNoiseUtil.ParameterRange defaultParameter;

    @Inject(method = "writeCaveBiomes", at = @At("TAIL"))
    void virtualAdditions$writeCaveBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, CallbackInfo ci) {
        this.writeCaveBiomeParameters(
                parameters,
                MultiNoiseUtil.ParameterRange.of(0.7F, 1.0F),
                MultiNoiseUtil.ParameterRange.of(-1.0F, -0.5F),
                this.defaultParameter,
                this.defaultParameter,
                this.defaultParameter,
                0.0F,
                VABiomeKeys.WASP_DEN
        );
        this.writeCaveBiomeParameters(
                parameters,
                this.defaultParameter,
                this.defaultParameter,
                MultiNoiseUtil.ParameterRange.of(-0.9F, -0.44F),
                MultiNoiseUtil.ParameterRange.of(0.6F, 1.0F),
                this.defaultParameter,
                0.0F,
                VABiomeKeys.SALTY_CAVES
        );
    }
}
