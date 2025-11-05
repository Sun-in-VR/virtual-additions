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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class WitheredTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<WitheredTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance -> WitheredTrunkPlacer.trunkPlacerParts(instance).apply(instance, WitheredTrunkPlacer::new));

    public WitheredTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return VAFeatures.TrunkPlacerTypes.WITHERED_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        ArrayList<FoliagePlacer.FoliageAttachment> nodes = new ArrayList<>();
        int[][] branches = getBranches(random, height);
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        boolean bl = false;
        for (int i = 0; i < height; ++i) {
            BlockPos pos = startPos.above(i);
            this.placeLog(world, replacer, random, pos, config);
            for (int[] branch : branches) {
                if (branch[0] == i) {
                    FoliagePlacer.FoliageAttachment node = placeBranch(world, replacer, random, pos, branch, direction, config);
                    if (node != null) {
                        nodes.add(node);
                    }
                    direction = direction.getClockWise();
                    if (!bl && random.nextInt(2) == 1) {
                        direction = direction.getClockWise();
                        bl = true;
                    }
                }
            }
        }
        nodes.add(new FoliagePlacer.FoliageAttachment(startPos.above(height), 1, false));
        return List.copyOf(nodes);
    }

    private FoliagePlacer.FoliageAttachment placeBranch(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos branchPos, int[] branch, Direction direction, TreeConfiguration config) {

        this.placeLog(world, replacer, random, branchPos.relative(direction), config, state -> state.setValue(BlockStateProperties.AXIS, direction.getAxis()));
        for (int i = 0; i < branch[1]; i++) {
            this.placeLog(world, replacer, random, branchPos.relative(direction, 2).above(i+1), config);
        }
        return new FoliagePlacer.FoliageAttachment(branchPos.relative(direction, 2).above(1 + branch[1]), 1, false);
    }

    private int[][] getBranches(RandomSource random, int height) {
        ArrayList<int[]> branches = new ArrayList<>();
        branches.add(getRandomBranchHeight(random, height));
        branches.add(getRandomBranchHeight(random, height));
        if (random.nextInt(2) == 1) branches.add(getRandomBranchHeight(random, height));
        return branches.toArray(new int[][]{});
    }

    private int[] getRandomBranchHeight(RandomSource random, int height) {
        int min = height / 4;
        int max = height - min;
        if (max < min) return new int[]{-1};
        return new int[]{random.nextInt(max) + min, random.nextInt(2) + 1};
    }
}
