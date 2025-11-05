package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.fluid.AcidFluid;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public class VAFluids {
    public static final TagKey<Fluid> ACID_TAG = tag("acid");

    public static final FlowingFluid FLOWING_ACID = Registry.register(BuiltInRegistries.FLUID, idOf("flowing_acid"), new AcidFluid.Flowing());
    public static final FlowingFluid ACID = Registry.register(BuiltInRegistries.FLUID, idOf("acid"), new AcidFluid.Still());

    public static void init(){}

    private static TagKey<Fluid> tag(String id) {
        return TagKey.create(Registries.FLUID, idOf(id));
    }
}
