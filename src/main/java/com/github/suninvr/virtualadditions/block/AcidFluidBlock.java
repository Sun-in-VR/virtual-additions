package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

@SuppressWarnings("deprecation")
public class AcidFluidBlock extends LiquidBlock {
    public AcidFluidBlock(FlowingFluid fluid, Properties settings) {
        super(fluid, settings);
    }


    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState().setValue(LEVEL, world.getBlockState(pos).getValue(LEVEL)));
    }

    @Override
    public void handlePrecipitation(BlockState state, Level world, BlockPos pos, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            world.setBlockAndUpdate(pos, Blocks.WATER.withPropertiesOf(state));
        }
    }

    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (this.receiveNeighborFluids(world, pos)) {
            world.scheduleTick(pos, state.getFluidState().getType(), this.fluid.getTickDelay(world));
        }

    }

    public void neighborUpdate(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (this.receiveNeighborFluids(world, pos)) {
            world.scheduleTick(pos, state.getFluidState().getType(), this.fluid.getTickDelay(world));
        }
    }

    private boolean receiveNeighborFluids(Level world, BlockPos pos) {
        for (Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
            BlockPos blockPos = pos.relative(direction.getOpposite());
            FluidState state = world.getFluidState(blockPos);
            if (state.is(FluidTags.WATER) && !state.is(VAFluids.ACID_TAG)) {
                world.scheduleTick(pos, VABlocks.ACID, Fluids.WATER.getTickDelay(world));
                return false;
            }
        }
        return true;
    }
}
