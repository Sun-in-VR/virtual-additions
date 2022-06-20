package com.github.suninvr.virtualadditions.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.NyliumBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import java.util.Random;

public class ToxicNyliumBlock extends NyliumBlock {

    public ToxicNyliumBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void grow(ServerWorld world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
        super.grow(world, random, pos, state);
    }
}
