package com.github.suninvr.virtualadditions.worldgen.feature;

import com.github.suninvr.virtualadditions.registry.VAFeatures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class AerobloomTrunkPlacer extends TrunkPlacer {
    public static final Codec<AerobloomTrunkPlacer> CODEC =
            RecordCodecBuilder.create(instance -> AerobloomTrunkPlacer.fillTrunkPlacerFields(instance).apply(instance, AerobloomTrunkPlacer::new));


    public AerobloomTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return VAFeatures.TrunkPlacerTypes.AEROBLOOM_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        int lowerTrunkHeight = Math.round(height * ((random.nextFloat() * 0.3F) + 0.3F));
        BlockPos dirtPos = startPos.down();
        AerobloomTrunkPlacer.setToDirt(world, replacer, random, dirtPos, config);
        if (lowerTrunkHeight > 0) {
            AerobloomTrunkPlacer.setToDirt(world, replacer, random, dirtPos.north(), config);
            AerobloomTrunkPlacer.setToDirt(world, replacer, random, dirtPos.east(), config);
            AerobloomTrunkPlacer.setToDirt(world, replacer, random, dirtPos.south(), config);
            AerobloomTrunkPlacer.setToDirt(world, replacer, random, dirtPos.west(), config);
        }
        int selectedHeight = 0;
        BlockPos selectedPos = startPos;
        while (selectedHeight < height) {
            selectedPos = startPos.up(selectedHeight);
            this.getAndSetState(world, replacer, random, selectedPos, config);
            BlockPos finalSelectedPos = selectedPos;
            if (selectedHeight < lowerTrunkHeight) {
                Direction.stream().filter(direction -> direction.getAxis().isHorizontal()).forEach(direction -> this.getAndSetState(world, replacer, random, finalSelectedPos.offset(direction), config));
            }
            if (selectedHeight == lowerTrunkHeight) {
                Direction.stream().filter(direction -> direction.getAxis().isHorizontal()).forEach(direction -> {
                    if (random.nextBoolean()) this.getAndSetState(world, replacer, random, finalSelectedPos.offset(direction), config);
                });
            }
            selectedHeight++;
        }
        return List.of(new FoliagePlacer.TreeNode(selectedPos, 0, false));
    }
}
