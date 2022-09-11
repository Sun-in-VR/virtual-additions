package com.github.suninvr.virtualadditions.registry;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.registry.Registry;
import com.github.suninvr.virtualadditions.fluid.AcidFluid;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAFluids {
    public static final FlowableFluid ACID;
    public static final FlowableFluid FLOWING_ACID;

    static {
        ACID = Registry.register(Registry.FLUID, idOf("acid"), new AcidFluid.Still());
        FLOWING_ACID = Registry.register(Registry.FLUID, idOf("flowing_acid"), new AcidFluid.Flowing());
    }
    public static void init(){

    }
}
