package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class NecroticNyliumBlock extends NyliumBlock {
    public NecroticNyliumBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockState blockState = world.getBlockState(pos);
        BlockPos blockPos = pos.above();
        ChunkGenerator chunkGenerator = world.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry = world.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE);
        if (blockState.is(VABlocks.NECROTIC_NYLIUM)) {
            this.place(registry, VAFeatures.Configured.NECROTIC_ROOTS_BONEMEAL, world, chunkGenerator, random, blockPos);
        }
    }

    private void place(Registry<ConfiguredFeature<?, ?>> registry, ResourceKey<ConfiguredFeature<?, ?>> key, ServerLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        registry.get(key).ifPresent((entry) -> entry.value().place(world, chunkGenerator, random, pos));
    }
}
