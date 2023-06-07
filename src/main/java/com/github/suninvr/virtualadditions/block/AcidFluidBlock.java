package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAFluids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class AcidFluidBlock extends FluidBlock {
    public AcidFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }


    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, Blocks.WATER.getDefaultState().with(LEVEL, world.getBlockState(pos).get(LEVEL)));
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (this.receiveNeighborFluids(world, pos)) {
            world.scheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }

    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (this.receiveNeighborFluids(world, pos)) {
            world.scheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
    }

    private boolean receiveNeighborFluids(World world, BlockPos pos) {
        for (Direction direction : FLOW_DIRECTIONS) {
            BlockPos blockPos = pos.offset(direction.getOpposite());
            FluidState state = world.getFluidState(blockPos);
            if (state.isIn(FluidTags.WATER) && !state.isIn(VAFluids.ACID_TAG)) {
                world.scheduleBlockTick(pos, VABlocks.ACID, Fluids.WATER.getTickRate(world));
                return false;
            }
        }
        return true;
    }
}
