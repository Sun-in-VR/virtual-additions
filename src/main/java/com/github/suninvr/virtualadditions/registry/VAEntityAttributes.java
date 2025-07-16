package com.github.suninvr.virtualadditions.registry;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEntityAttributes {
    public static final RegistryEntry<EntityAttribute> CRITICAL_HIT_FACTOR = register(
            "critical_hit_factor", new ClampedEntityAttribute("attribute.virtual_additions.critical_hit_factor", 1.0, 0.0, 2048)
    );

    public static void init() {};

    private static RegistryEntry<EntityAttribute> register(String id, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, idOf(id), attribute);
    }
}
