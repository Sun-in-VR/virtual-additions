package com.github.suninvr.virtualadditions.worldgen.feature;

import com.github.suninvr.virtualadditions.registry.VAFeatures;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class DivergingTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<DivergingTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance -> DivergingTrunkPlacer.trunkPlacerParts(instance).apply(instance, DivergingTrunkPlacer::new));
    private static final Logger log = LoggerFactory.getLogger(DivergingTrunkPlacer.class);

    public DivergingTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return VAFeatures.TrunkPlacerTypes.DIVERGING_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        ArrayList<FoliagePlacer.FoliageAttachment> nodes = new ArrayList<>();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        for (int i = 0; i < height; ++i) {
            BlockPos pos = startPos.above(i);
            this.placeLog(levelReader, replacer, random, pos, config);
            if (i == height / 2) {
                nodes.addAll(this.diverge(levelReader, replacer, random, pos, direction, config));
            } else if (i == (height / 3) * 2 && random.nextIntBetweenInclusive(10, 30) < height) {
                nodes.addAll(this.diverge(levelReader, replacer, random, pos, direction.getClockWise(), config));
            }
        }
        nodes.add(new FoliagePlacer.FoliageAttachment(startPos.above(height), 1, false));
        return List.copyOf(nodes);
    }

    private List<FoliagePlacer.FoliageAttachment> diverge(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos branchPos, Direction direction, TreeConfiguration config) {
        ArrayList<BlockPos> logs = new ArrayList<>();
        ArrayList<FoliagePlacer.FoliageAttachment> foliagePlacers = new ArrayList<>();
        int leftHeightOffset = random.nextIntBetweenInclusive(0, 3) - 2;
        int rightHeightOffset = random.nextIntBetweenInclusive(0, 3) - 2;
        int leftLean = random.nextIntBetweenInclusive(2, 3);
        int rightLean = random.nextIntBetweenInclusive(2, 3);
        for (int i = 0; i < 9; i++) {
            int j = (int) Math.max(1, Math.ceil((double) (i) / leftLean));
            int k = (int) Math.max(1, Math.ceil((double) (i) / rightLean)) * -1;
            logs.add(branchPos.above(i + leftHeightOffset).relative(direction, j));
            logs.add(branchPos.above(i + rightHeightOffset).relative(direction, k));
            if (i == 8) {
                foliagePlacers.add(new FoliagePlacer.FoliageAttachment(branchPos.above(i + 2 + leftHeightOffset).relative(direction, j), -1, false));
                foliagePlacers.add(new FoliagePlacer.FoliageAttachment(branchPos.above(i + 2 + rightHeightOffset).relative(direction, k), -1, false));
            }
        }
        logs.forEach(pos -> this.placeLog(levelReader, replacer, random, pos, config, state -> state.setValue(BlockStateProperties.AXIS, Direction.Axis.Y)));
        return foliagePlacers;
    }
}
