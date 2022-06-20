package com.github.suninvr.virtualadditions.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record LargeCrystalFeatureConfig(IntProvider length, BlockStateProvider innerState, BlockStateProvider outerState, boolean ceiling) implements FeatureConfig {
    public static final Codec<LargeCrystalFeatureConfig> CODEC = RecordCodecBuilder.create( (instance) -> {
        return instance.group(
                IntProvider.NON_NEGATIVE_CODEC.fieldOf("length").forGetter(LargeCrystalFeatureConfig::length),
                BlockStateProvider.TYPE_CODEC.fieldOf("inner_state").forGetter(LargeCrystalFeatureConfig::innerState),
                BlockStateProvider.TYPE_CODEC.fieldOf("outer_state").forGetter(LargeCrystalFeatureConfig::outerState),
                Codec.BOOL.fieldOf("ceiling").forGetter(LargeCrystalFeatureConfig::ceiling)

        ).apply(instance, LargeCrystalFeatureConfig::new);
    } );

    public static LargeCrystalFeatureConfig create(IntProvider height, BlockStateProvider innerState, BlockStateProvider outerState, boolean ceiling) {
        return new LargeCrystalFeatureConfig(height, innerState, outerState, ceiling);
    }
}
