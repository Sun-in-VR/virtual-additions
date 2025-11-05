package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARegistries {
    public static final ResourceKey<Registry<GildType>> GILD_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(idOf("gild_type"));

    public static final Registry<GildType> GILD_TYPE = BuiltInRegistries.registerDefaulted(GILD_TYPE_REGISTRY_KEY, "virtual_additions:amethyst", VAGildTypes::registerAndGetDefault);

    public static void init() {
    }
}
