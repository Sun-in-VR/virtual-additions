package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VALootTables {
    public static final ResourceKey<LootTable> CEMETERY_GRAVES = register("chests/cemetery/graves");
    public static final ResourceKey<LootTable> CEMETERY = register("chests/cemetery");

    public static void init(){}

    private static ResourceKey<LootTable> register(String id) {
        return BuiltInLootTables.register(ResourceKey.create(Registries.LOOT_TABLE, idOf(id)));
    }
}
