package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

@SuppressWarnings("deprecation")
public class GreencapMushroomBlock extends PlantBlock implements Fertilizable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);
    public GreencapMushroomBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos) || floor.isOf(VABlocks.SILK_BLOCK);
    }


    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(3) != 1) return;
        Vec3d modelOffset = state.getModelOffset(world, pos);
        double x = pos.getX() + modelOffset.x;
        double y = pos.getY() + modelOffset.y;
        double z = pos.getZ() + modelOffset.z;
        for(int l = 0; l < 2; ++l) {
            world.addParticle(VAParticleTypes.GREENCAP_SPORE, x + 0.25 + random.nextDouble() / 2, y + 0.25  + random.nextDouble() / 3, z + 0.25 + random.nextDouble() / 2, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.up()).isReplaceable();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.up()).isReplaceable();
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, VABlocks.TALL_GREENCAP_MUSHROOMS.getDefaultState());
        world.setBlockState(pos.up(), VABlocks.TALL_GREENCAP_MUSHROOMS.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
    }
}
