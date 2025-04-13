package com.github.suninvr.virtualadditions.worldgen.feature;

import com.github.suninvr.virtualadditions.registry.VAFeatures;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class WitheredTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<WitheredTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance -> WitheredTrunkPlacer.fillTrunkPlacerFields(instance).apply(instance, WitheredTrunkPlacer::new));

    public WitheredTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return VAFeatures.TrunkPlacerTypes.WITHERED_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        ArrayList<FoliagePlacer.TreeNode> nodes = new ArrayList<>();
        int[][] branches = getBranches(random, height);
        Direction direction = Direction.Type.HORIZONTAL.random(random);
        boolean bl = false;
        for (int i = 0; i < height; ++i) {
            BlockPos pos = startPos.up(i);
            this.getAndSetState(world, replacer, random, pos, config);
            for (int[] branch : branches) {
                if (branch[0] == i) {
                    FoliagePlacer.TreeNode node = placeBranch(world, replacer, random, pos, branch, direction, config);
                    if (node != null) {
                        nodes.add(node);
                    }
                    direction = direction.rotateYClockwise();
                    if (!bl && random.nextInt(2) == 1) {
                        direction = direction.rotateYClockwise();
                        bl = true;
                    }
                }
            }
        }
        nodes.add(new FoliagePlacer.TreeNode(startPos.up(height), 1, false));
        return List.copyOf(nodes);
    }

    private FoliagePlacer.TreeNode placeBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos branchPos, int[] branch, Direction direction, TreeFeatureConfig config) {

        this.getAndSetState(world, replacer, random, branchPos.offset(direction), config, state -> state.with(Properties.AXIS, direction.getAxis()));
        for (int i = 0; i < branch[1]; i++) {
            this.getAndSetState(world, replacer, random, branchPos.offset(direction, 2).up(i+1), config);
        }
        return new FoliagePlacer.TreeNode(branchPos.offset(direction, 2).up(1 + branch[1]), 1, false);
    }

    private int[][] getBranches(Random random, int height) {
        ArrayList<int[]> branches = new ArrayList<>();
        branches.add(getRandomBranchHeight(random, height));
        branches.add(getRandomBranchHeight(random, height));
        if (random.nextInt(2) == 1) branches.add(getRandomBranchHeight(random, height));
        return branches.toArray(new int[][]{});
    }

    private int[] getRandomBranchHeight(Random random, int height) {
        int min = height / 4;
        int max = height - min;
        if (max < min) return new int[]{-1};
        return new int[]{random.nextInt(max) + min, random.nextInt(2) + 1};
    }
}
