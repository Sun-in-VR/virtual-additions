package com.github.suninvr.virtualadditions.worldgen.feature;

import com.github.suninvr.virtualadditions.block.BalloonBulbBlock;
import com.github.suninvr.virtualadditions.block.BalloonBulbPlantBlock;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BalloonBulbFeature extends Feature<NoneFeatureConfiguration> {
    private static final BlockState BULB_STATE = VABlocks.BALLOON_BULB.defaultBlockState();
    private static final BlockState PLANT_STATE = VABlocks.BALLOON_BULB_PLANT.defaultBlockState();

    public BalloonBulbFeature(Codec<NoneFeatureConfiguration> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();

        if (!world.getBlockState(pos).isAir()) return false;
        if (!world.getBlockState(pos.below()).isAir()) {
            if (!world.getBlockState(pos.above()).isAir()) return false;
            pos.relative(Direction.UP);
        }

        setBlock(world, pos, BULB_STATE.setValue(BalloonBulbBlock.HEIGHT, random.nextIntBetweenInclusive(18, 25)));
        setBlock(world, pos.below(), PLANT_STATE.setValue(BalloonBulbPlantBlock.AGE, random.nextIntBetweenInclusive(2, 3)));

        return true;
    }
}
