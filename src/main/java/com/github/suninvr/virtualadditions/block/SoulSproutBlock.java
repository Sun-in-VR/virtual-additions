package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class SoulSproutBlock extends FlowerBlock {
    public SoulSproutBlock(Settings settings) {
        super(VAStatusEffects.AURA, 4, settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextDouble() <= 0.01 && world.getBlockState(pos.up()).isAir()) {
            Vec3d centerPos = pos.toCenterPos();
            world.playSoundClient(centerPos.x, centerPos.y, centerPos.z, SoundEvents.BLOCK_EYEBLOSSOM_IDLE, SoundCategory.AMBIENT, 1, 1, true);
        }
        if (random.nextDouble() <= 0.7) {
            double d = pos.getX() + random.nextDouble() * 10.0 - 5.0;
            double e = pos.getY() + random.nextDouble() * 5.0;
            double f = pos.getZ() + random.nextDouble() * 10.0 - 5.0;
            world.addParticleClient(VAParticleTypes.SOUL_FIREFLY, d, e, f, 0.0, 0.0, 0.0);
        }
    }
}
