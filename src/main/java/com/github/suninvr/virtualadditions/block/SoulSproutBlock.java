package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SoulSproutBlock extends FlowerBlock implements BonemealableBlock {
    public SoulSproutBlock(Properties settings) {
        super(VAStatusEffects.AURA, 4, settings);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextDouble() <= 0.003 && world.getBlockState(pos.above()).isAir()) {
            Vec3 centerPos = pos.getCenter();
            world.playLocalSound(centerPos.x, centerPos.y, centerPos.z, SoundEvents.EYEBLOSSOM_IDLE, SoundSource.AMBIENT, 1, 1, true);
        }
        if (random.nextDouble() <= 0.7) {
            double d = pos.getX() + random.nextDouble() * 10.0 - 5.0;
            double e = pos.getY() + random.nextDouble() * 5.0;
            double f = pos.getZ() + random.nextDouble() * 10.0 - 5.0;
            world.addParticle(VAParticleTypes.SOUL_FIREFLY, d, e, f, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return BonemealableBlock.hasSpreadableNeighbourPos(world, pos, state);
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BonemealableBlock.findSpreadableNeighbourPos(world, pos, state).ifPresent(posx -> world.setBlockAndUpdate(posx, VABlocks.SOUL_SPROUT.defaultBlockState()));
    }
}
