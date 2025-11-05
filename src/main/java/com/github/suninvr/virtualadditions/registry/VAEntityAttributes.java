package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEntityAttributes {
    public static final Holder<Attribute> CRITICAL_HIT_FACTOR = register(
            "critical_hit_factor", new RangedAttribute("attribute.virtual_additions.critical_hit_factor", 1.0, 0.0, 2048)
    );

    public static void init() {};

    private static Holder<Attribute> register(String id, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, idOf(id), attribute);
    }
}
