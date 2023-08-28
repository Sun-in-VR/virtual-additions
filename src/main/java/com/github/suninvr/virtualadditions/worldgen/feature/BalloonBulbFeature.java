package com.github.suninvr.virtualadditions.worldgen.feature;

import com.github.suninvr.virtualadditions.block.BalloonBulbBlock;
import com.github.suninvr.virtualadditions.block.BalloonBulbPlantBlock;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BalloonBulbFeature extends Feature<DefaultFeatureConfig> {
    private static final BlockState BULB_STATE = VABlocks.BALLOON_BULB.getDefaultState();
    private static final BlockState PLANT_STATE = VABlocks.BALLOON_BULB_PLANT.getDefaultState();

    public BalloonBulbFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {

        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();

        if (!world.getBlockState(pos).isAir()) return false;
        if (!world.getBlockState(pos.down()).isAir()) {
            if (!world.getBlockState(pos.up()).isAir()) return false;
            pos.offset(Direction.UP);
        }

        setBlockState(world, pos, BULB_STATE.with(BalloonBulbBlock.HEIGHT, random.nextBetween(18, 25)));
        setBlockState(world, pos.down(), PLANT_STATE.with(BalloonBulbPlantBlock.AGE, random.nextBetween(2, 3)));

        return true;
    }
}
