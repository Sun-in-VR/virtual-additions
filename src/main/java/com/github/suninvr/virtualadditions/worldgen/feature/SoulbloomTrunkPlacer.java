package com.github.suninvr.virtualadditions.worldgen.feature;

import com.github.suninvr.virtualadditions.registry.VAFeatures;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class SoulbloomTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<SoulbloomTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(soulbloomTrunkPlacerInstance -> SoulbloomTrunkPlacer.trunkPlacerParts(soulbloomTrunkPlacerInstance).apply(soulbloomTrunkPlacerInstance, SoulbloomTrunkPlacer::new));


    public SoulbloomTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return VAFeatures.TrunkPlacerTypes.SOULBLOOM_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        int lowerTrunkHeight = Math.round(height * ((random.nextFloat() * 0.3F) + 0.3F));
        BlockPos dirtPos = startPos.below();
        SoulbloomTrunkPlacer.setDirtAt(world, replacer, random, dirtPos, config);
        if (lowerTrunkHeight > 0) {
            SoulbloomTrunkPlacer.setDirtAt(world, replacer, random, dirtPos.north(), config);
            SoulbloomTrunkPlacer.setDirtAt(world, replacer, random, dirtPos.east(), config);
            SoulbloomTrunkPlacer.setDirtAt(world, replacer, random, dirtPos.south(), config);
            SoulbloomTrunkPlacer.setDirtAt(world, replacer, random, dirtPos.west(), config);
        }
        int selectedHeight = 0;
        BlockPos selectedPos = startPos;
        while (selectedHeight < height) {
            selectedPos = startPos.above(selectedHeight);
            this.placeLog(world, replacer, random, selectedPos, config);
            BlockPos finalSelectedPos = selectedPos;
            if (selectedHeight < lowerTrunkHeight) {
                Direction.stream().filter(direction -> direction.getAxis().isHorizontal()).forEach(direction -> this.placeLog(world, replacer, random, finalSelectedPos.relative(direction), config));
            }
            if (selectedHeight == lowerTrunkHeight) {
                Direction.stream().filter(direction -> direction.getAxis().isHorizontal()).forEach(direction -> {
                    if (random.nextBoolean()) this.placeLog(world, replacer, random, finalSelectedPos.relative(direction), config);
                });
            }
            selectedHeight++;
        }
        return List.of(new FoliagePlacer.FoliageAttachment(selectedPos, 0, false));
    }
}
