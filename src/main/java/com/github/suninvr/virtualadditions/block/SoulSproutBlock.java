package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class SoulSproutBlock extends FlowerBlock implements Fertilizable {
    public SoulSproutBlock(Settings settings) {
        super(VAStatusEffects.AURA, 4, settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextDouble() <= 0.003 && world.getBlockState(pos.up()).isAir()) {
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

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return Fertilizable.canSpread(world, pos, state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Fertilizable.findPosToSpreadTo(world, pos, state).ifPresent(posx -> world.setBlockState(posx, VABlocks.SOUL_SPROUT.getDefaultState()));
    }
}
