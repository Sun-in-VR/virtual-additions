package com.github.suninvr.virtualadditions.registry.constructors.block;

import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;

public class CustomFluidBlock extends FluidBlock {
    public CustomFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }
}
