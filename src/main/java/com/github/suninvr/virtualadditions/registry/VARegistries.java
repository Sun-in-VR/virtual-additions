package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARegistries {
    public static final RegistryKey<Registry<GildType>> GILD_TYPE_REGISTRY_KEY = RegistryKey.ofRegistry(idOf("gild_type"));

    public static final Registry<GildType> GILD_TYPE = Registries.create(GILD_TYPE_REGISTRY_KEY, "virtual_additions:amethyst", VAGildTypes::registerAndGetDefault);

    public static void init() {
    }
}
