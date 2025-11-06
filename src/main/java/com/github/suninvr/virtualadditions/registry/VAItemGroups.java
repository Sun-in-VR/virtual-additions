package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.Comparator;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAItemGroups {
    public static final ResourceKey<CreativeModeTab> VIRTUAL_ADDITIONS_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), idOf("virtual_additions"));
    public static final CreativeModeTab VIRTUAL_ADDITIONS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(VAItems.IOLITE))
            .title(Component.nullToEmpty("Virtual Additions"))
            .build();
    public static final ResourceKey<CreativeModeTab> GILDED_TOOLS_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), idOf("gilded_tools"));
    public static final CreativeModeTab GILDED_TOOLS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(VAItems.TOOL_GILD_SMITHING_TEMPLATE))
            .title(Component.nullToEmpty("Gilded Tools"))
            .build();
    public static final ResourceKey<CreativeModeTab> LOOT_TABLES_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), idOf("loot_tables"));
    public static final CreativeModeTab LOOT_TABLES = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.CHEST))
            .title(Component.nullToEmpty("Loot Tables"))
            .build();

    public static void init() {
        if (VirtualAdditions.DEBUG) {
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, VIRTUAL_ADDITIONS_KEY, VIRTUAL_ADDITIONS);
            populateVirtualAdditionsGroup();
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, GILDED_TOOLS_KEY, GILDED_TOOLS);
            populateGildedToolsGroup();
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, LOOT_TABLES_KEY, LOOT_TABLES);
            populateLootTablesGroup();
        }

    }

    private static void populateGildedToolsGroup() {
        ItemGroupEvents.modifyEntriesEvent(GILDED_TOOLS_KEY).register(group -> {
            group.accept(Items.SMITHING_TABLE);
            group.accept(VAItems.TOOL_GILD_SMITHING_TEMPLATE);
            group.accept(Items.AMETHYST_SHARD);
            group.accept(Items.COPPER_INGOT);
            group.accept(Items.EMERALD);
            group.accept(Items.QUARTZ);
            group.accept(Items.ECHO_SHARD);
            group.accept(VAItems.IOLITE);
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
                stack.set(VADataComponentTypes.GILD_TYPE, type);
                type.modifyStackOnCrafted(stack);
                group.accept(stack);
            });
        });

    }

    private static void populateLootTablesGroup() {
        ItemGroupEvents.modifyEntriesEvent(LOOT_TABLES_KEY).register(group -> {
            BuiltInLootTables.all().stream().sorted(Comparator.comparing(registryKey -> registryKey.identifier().toString())).forEach(lootTableKey -> {
                ItemStack stack = Items.CHEST.getDefaultInstance();
                stack.set(DataComponents.CONTAINER_LOOT, new SeededContainerLoot(lootTableKey, 0L));
                stack.set(DataComponents.ITEM_NAME, Component.nullToEmpty(lootTableKey.identifier().toString()));
                group.accept(stack);
            });
        });
    }

    private static void populateVirtualAdditionsGroup() {
        ItemGroupEvents.modifyEntriesEvent(VIRTUAL_ADDITIONS_KEY).register(group -> {
            BuiltInRegistries.ITEM.forEach(item -> {
                if (BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(VirtualAdditions.NAMESPACE)) {
                    group.accept(item.getDefaultInstance());
                }
            });
        });
    }
}
