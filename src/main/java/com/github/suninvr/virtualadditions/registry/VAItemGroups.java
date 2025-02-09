package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerLootComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAItemGroups {
    public static final RegistryKey<ItemGroup> VIRTUAL_ADDITIONS_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), idOf("virtual_additions"));
    public static final ItemGroup VIRTUAL_ADDITIONS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(VAItems.IOLITE))
            .displayName(Text.of("Virtual Additions"))
            .build();
    public static final RegistryKey<ItemGroup> LOOT_TABLES_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), idOf("loot_tables"));
    public static final ItemGroup LOOT_TABLES = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.CHEST))
            .displayName(Text.of("Loot Tables"))
            .build();

    public static void init() {
        if (VirtualAdditions.DEBUG) {
            Registry.register(Registries.ITEM_GROUP, VIRTUAL_ADDITIONS_KEY, VIRTUAL_ADDITIONS);
            populateVirtualAdditionsGroup();
            Registry.register(Registries.ITEM_GROUP, LOOT_TABLES_KEY, LOOT_TABLES);
            populateLootTablesGroup();
        }

    }

    private static void populateLootTablesGroup() {
        ItemGroupEvents.modifyEntriesEvent(LOOT_TABLES_KEY).register(group -> {
            LootTables.getAll().forEach(lootTableKey -> {
                ItemStack stack = Items.CHEST.getDefaultStack();
                stack.set(DataComponentTypes.CONTAINER_LOOT, new ContainerLootComponent(lootTableKey, 0L));
                stack.set(DataComponentTypes.ITEM_NAME, Text.of(lootTableKey.getValue().toString()));
                group.add(stack);
            });
        });
    }

    private static void populateVirtualAdditionsGroup() {
        ItemGroupEvents.modifyEntriesEvent(VIRTUAL_ADDITIONS_KEY).register(group -> {
            Registries.ITEM.forEach(item -> {
                if (Registries.ITEM.getId(item).getNamespace().equals(VirtualAdditions.NAMESPACE)) {
                    group.add(item.getDefaultStack());
                }
            });
        });
    }
}
