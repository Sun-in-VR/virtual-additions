package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.component.GildTypeComponent;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
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

import java.util.Comparator;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAItemGroups {
    public static final RegistryKey<ItemGroup> VIRTUAL_ADDITIONS_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), idOf("virtual_additions"));
    public static final ItemGroup VIRTUAL_ADDITIONS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(VAItems.IOLITE))
            .displayName(Text.of("Virtual Additions"))
            .build();
    public static final RegistryKey<ItemGroup> GILDED_TOOLS_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), idOf("gilded_tools"));
    public static final ItemGroup GILDED_TOOLS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(VAItems.TOOL_GILD_SMITHING_TEMPLATE))
            .displayName(Text.of("Gilded Tools"))
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
            Registry.register(Registries.ITEM_GROUP, GILDED_TOOLS_KEY, GILDED_TOOLS);
            populateGildedToolsGroup();
            Registry.register(Registries.ITEM_GROUP, LOOT_TABLES_KEY, LOOT_TABLES);
            populateLootTablesGroup();
        }

    }

    private static void populateGildedToolsGroup() {
        ItemGroupEvents.modifyEntriesEvent(GILDED_TOOLS_KEY).register(group -> {
            group.add(Items.SMITHING_TABLE);
            group.add(VAItems.TOOL_GILD_SMITHING_TEMPLATE);
            group.add(Items.AMETHYST_SHARD);
            group.add(Items.COPPER_INGOT);
            group.add(Items.EMERALD);
            group.add(Items.QUARTZ);
            group.add(Items.ECHO_SHARD);
            group.add(VAItems.IOLITE);
            addGildedTools(VAItems.COPPER_TOOL_SET, group);
            addGildedTools(VAItems.IRON_TOOL_SET, group);
            addGildedTools(VAItems.GOLDEN_TOOL_SET, group);
            addGildedTools(VAItems.STEEL_TOOL_SET, group);
            addGildedTools(VAItems.DIAMOND_TOOL_SET, group);
            addGildedTools(VAItems.NETHERITE_TOOL_SET, group);
        });
    }

    private static void addGildedTools(RegistryHelper.ItemRegistryHelper.ToolSet set, FabricItemGroupEntries group) {
        VARegistries.GILD_TYPE.stream().forEach(type -> {
            set.forEach(item -> {
                ItemStack stack = new ItemStack(item);
                stack.set(VADataComponentTypes.GILD_TYPE_COMPONENT, new GildTypeComponent(VARegistries.GILD_TYPE.getEntry(type)));
                type.modifyStackOnCrafted(stack);
                group.add(stack);
            });
        });

    }

    private static void populateLootTablesGroup() {
        ItemGroupEvents.modifyEntriesEvent(LOOT_TABLES_KEY).register(group -> {
            LootTables.getAll().stream().sorted(Comparator.comparing(registryKey -> registryKey.getValue().toString())).forEach(lootTableKey -> {
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
