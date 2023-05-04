package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TallGreencapMushroomBlock extends TallPlantBlock {
    public TallGreencapMushroomBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND) || floor.isOf(VABlocks.SILK_BLOCK);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(TallPlantBlock.HALF) == DoubleBlockHalf.LOWER) return;
        Vec3d modelOffset = state.getModelOffset(world, pos);
        double x = pos.getX() + modelOffset.x;
        double y = pos.getY() + modelOffset.y;
        double z = pos.getZ() + modelOffset.z;
        for(int l = 0; l < 6; ++l) {
            world.addParticle(VAParticleTypes.GREENCAP_SPORE, x + 0.25 + random.nextDouble() / 2, y + 0.25  + random.nextDouble() / 2, z + 0.25 + random.nextDouble() / 2, 0.0, 0.0, 0.0);
        }
    }
}
