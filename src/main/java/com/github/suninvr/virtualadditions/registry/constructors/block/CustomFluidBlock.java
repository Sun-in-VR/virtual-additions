package com.github.suninvr.virtualadditions.registry.constructors.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class CustomFluidBlock extends FluidBlock {
    private final boolean canPathThrough;
    public CustomFluidBlock(FlowableFluid fluid, boolean canPathThrough, Settings settings) {
        super(fluid, settings);
        this.canPathThrough = canPathThrough;
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return canPathThrough;
    }
}
