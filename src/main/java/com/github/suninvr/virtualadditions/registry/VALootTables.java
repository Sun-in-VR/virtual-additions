package com.github.suninvr.virtualadditions.registry;

import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VALootTables {
    public static final RegistryKey<LootTable> CEMETERY_GRAVES = register("chests/cemetery/graves");
    public static final RegistryKey<LootTable> CEMETERY = register("chests/cemetery");

    public static void init(){}

    private static RegistryKey<LootTable> register(String id) {
        return LootTables.registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, idOf(id)));
    }
}
