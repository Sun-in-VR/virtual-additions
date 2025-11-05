package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABiomeKeys;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public abstract class VanillaBiomeParametersMixin {

    @Shadow @Final private Climate.Parameter nearInlandContinentalness;

    @Shadow @Final private Climate.Parameter midInlandContinentalness;

    @Shadow @Final private Climate.Parameter farInlandContinentalness;

    @Shadow @Final private Climate.Parameter FULL_RANGE;
    @Shadow @Final private Climate.Parameter[] temperatures;
    @Shadow @Final private Climate.Parameter[] humidities;
    @Shadow @Final private Climate.Parameter[] erosions;

    @Shadow protected abstract void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter parameter, Climate.Parameter parameter2, Climate.Parameter parameter3, Climate.Parameter parameter4, Climate.Parameter parameter5, float f, ResourceKey<Biome> resourceKey);

    @Shadow protected abstract void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter parameter, Climate.Parameter parameter2, Climate.Parameter parameter3, Climate.Parameter parameter4, Climate.Parameter parameter5, float f, ResourceKey<Biome> resourceKey);

    @Unique private final Climate.Parameter farInlandContinentalnessUpper = Climate.Parameter.span(0.63333F, 1.0F);

    @Inject(method = "addUndergroundBiomes", at = @At("TAIL"))
    void virtualAdditions$writeCaveBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, CallbackInfo ci) {
        this.addUndergroundBiome(
                parameters,
                Climate.Parameter.span(0.7F, 1.0F),
                Climate.Parameter.span(-1.0F, -0.5F),
                this.FULL_RANGE,
                this.FULL_RANGE,
                this.FULL_RANGE,
                0.0F,
                VABiomeKeys.WASP_DEN
        );
        this.addUndergroundBiome(
                parameters,
                this.FULL_RANGE,
                this.FULL_RANGE,
                Climate.Parameter.span(-0.9F, -0.44F),
                Climate.Parameter.span(0.6F, 1.0F),
                this.FULL_RANGE,
                0.0F,
                VABiomeKeys.SALTY_CAVES
        );
    }

    @Inject(method = "addLowSlice", at = @At("TAIL"))
    void virtualAdditions$writeLowBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        if (weirdness.max() <= 0.0) return;
        virtualAdditions$writeSoulGrove(parameters, weirdness);
    }

    @Inject(method = "addMidSlice", at = @At("TAIL"))
    void virtualAdditions$writeMidBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        if (weirdness.max() <= 0.0) return;
        virtualAdditions$writeSoulGrove(parameters, weirdness);
    }

    @Inject(method = "addHighSlice", at = @At("TAIL"))
    void virtualAdditions$writeHighBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        if (weirdness.max() <= 0.0) return;
        virtualAdditions$writeSoulGrove(parameters, weirdness);
    }

    @Unique
    void virtualAdditions$writeSoulGrove(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness) {
        this.addSurfaceBiome(
                parameters,
                this.temperatures[2],
                Climate.Parameter.span(this.humidities[3], this.humidities[4]),
                Climate.Parameter.span(0.46667F, 1.0F),
                Climate.Parameter.span(this.erosions[0], this.erosions[4]),
                weirdness,
                0.0F,
                VABiomeKeys.SOUL_GROVE
        );
    }
}
