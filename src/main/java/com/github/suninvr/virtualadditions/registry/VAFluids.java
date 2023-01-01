package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.fluid.AcidFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAFluids {
    public static final TagKey<Fluid> ACID_TAG = tag("acid");

    public static void init(){}

    public static final FlowableFluid FLOWING_ACID = Registry.register(Registries.FLUID, idOf("flowing_acid"), new AcidFluid.Flowing());
    public static final FlowableFluid ACID = Registry.register(Registries.FLUID, idOf("acid"), new AcidFluid.Still());

    private static TagKey<Fluid> tag(String id) {
        return TagKey.of(RegistryKeys.FLUID, idOf(id));
    }
}
