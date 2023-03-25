package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.item.*;
import com.github.suninvr.virtualadditions.item.enums.GildType;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlag;

import java.util.Locale;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class RegistryHelper {

    public static class BlocKRegistryHelper {

        /**
         * Registers a new block
         *
         * @param id The in-game ID. This will be "modid:id"
         * @param block The block to be registered. Any class that extends {@link net.minecraft.block.AbstractBlock} will work.
         * **/
        public static <T extends Block> Block register(String id, T block) {
            return Registry.register(Registries.BLOCK, VirtualAdditions.idOf(id), block);
        }

    }

    public static class ItemHelper {

        public record ToolSet(Item SWORD, Item SHOVEL, Item PICKAXE, Item AXE, Item HOE, String NAME){}
        public record ItemGroupLocation(ItemGroup GROUP, Item AFTER){}
        public static Item prev;
        
        //Register an Item
        public static <T extends net.minecraft.item.Item> net.minecraft.item.Item register(String id, T item) { // Register a given item
            net.minecraft.item.Item item1 = Registry.register(Registries.ITEM, idOf(id), item);
            prev = item1;
            return item1;
        }
        public static <T extends net.minecraft.item.Item> net.minecraft.item.Item register(String id, T item, ItemGroup itemGroup) { // Register an item, add to a group
            net.minecraft.item.Item item1 = register(id, item);
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register( (content) -> content.add(item1));
            prev = item1;
            return item1;
        }
        public static <T extends net.minecraft.item.Item> net.minecraft.item.Item register(String id, T item, ItemGroup itemGroup, net.minecraft.item.Item itemAfter) { // Register an item, add to a specific location in a group
            net.minecraft.item.Item item1 = register(id, item);
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register( (content) -> content.addAfter(itemAfter, item1));
            prev = item1;
            return item1;
        }

        public static <T extends net.minecraft.item.Item> net.minecraft.item.Item register(String id, T item, ItemGroupLocation... locations) { // Register an item, add to several locations
            net.minecraft.item.Item item1 = register(id, item);
            for (ItemGroupLocation location : locations) {
                ItemGroupEvents.modifyEntriesEvent(location.GROUP).register( (content) -> content.addAfter(location.AFTER, item1));
            }
            prev = item1;
            return item1;
        }

        public static net.minecraft.item.Item register(String id) { // Create and register an item
            FabricItemSettings settings = new FabricItemSettings();
            return register(id, new net.minecraft.item.Item(settings));
        }

        public static net.minecraft.item.Item register(String id, ItemGroup itemGroup) { // Create and register an item, give a group
            FabricItemSettings settings = new FabricItemSettings();
            return register(id, new net.minecraft.item.Item(settings), itemGroup);
        }

        public static net.minecraft.item.Item register(String id, ItemGroup itemGroup, net.minecraft.item.Item itemAfter) { // Create and register an item, give a location in a group
            FabricItemSettings settings = new FabricItemSettings();
            return register(id, new net.minecraft.item.Item(settings), itemGroup, itemAfter);
        }

        public static net.minecraft.item.Item register(String id, ItemGroup itemGroup, net.minecraft.item.Item itemAfter, FeatureFlag... features) { // Create and register an item, give a location in a group
            FabricItemSettings settings = new FabricItemSettings().requires(features);
            return register(id, new net.minecraft.item.Item(settings), itemGroup, itemAfter);
        }

        public static net.minecraft.item.Item register(String id, ItemGroupLocation... locations) { // Create and register an item, give several locations
            FabricItemSettings settings = new FabricItemSettings();
            return register(id, new net.minecraft.item.Item(settings), locations);
        }

        public static net.minecraft.item.Item registerBlockItem(String id, Block block) { // Create and register a block item
            return register(id, new BlockItem(block, new FabricItemSettings()));
        }

        public static net.minecraft.item.Item registerBlockItem(String id, Block block, ItemGroup itemGroup) { // Create and register a block item, give a group
            return register(id, new BlockItem(block, new FabricItemSettings()), itemGroup);
        }

        public static net.minecraft.item.Item registerBlockItem(String id, Block block, ItemGroup itemGroup, net.minecraft.item.Item itemAfter) { // Create and register a block item, give a location in a group
            return register(id, new BlockItem(block, new FabricItemSettings()), itemGroup, itemAfter);
        }

        public static net.minecraft.item.Item registerBlockItem(String id, Block block, ItemGroupLocation... locations) { // Create and register a block item, give several locations
            return register(id, new BlockItem(block, new FabricItemSettings()), locations);
        }

        public static net.minecraft.item.Item registerBlockItem(String id, Block block, ItemGroup itemGroup, net.minecraft.item.Item itemAfter, FeatureFlag... features) { // Create and register a block item, give a location in a group, assign required feature flags
            return register(id, new BlockItem(block, new FabricItemSettings().requires(features)), itemGroup, itemAfter);
        }

        public static net.minecraft.item.Item registerBlockItem(String id, Block block, ItemGroupLocation[] locations, FeatureFlag... features) { // Create and register a block item, give several locations, assign required feature flags
            return register(id, new BlockItem(block, new FabricItemSettings().requires(features)), locations);
        }

        public static ToolSet registerGildedToolSet(ToolSet baseSet, GildType gildedToolMaterial) {
            String newName = gildedToolMaterial.name().toLowerCase(Locale.ROOT) +"_"+ baseSet.NAME;
            return new ToolSet(
                    register(newName +"_sword", new GildedSwordItem(gildedToolMaterial, (SwordItem) baseSet.SWORD, GildedToolUtil.settingsOf(baseSet.SWORD))),
                    register(newName +"_shovel", new GildedShovelItem(gildedToolMaterial, (ShovelItem) baseSet.SHOVEL, GildedToolUtil.settingsOf(baseSet.SHOVEL))),
                    register(newName +"_pickaxe", new GildedPickaxeItem(gildedToolMaterial, (PickaxeItem) baseSet.PICKAXE, GildedToolUtil.settingsOf(baseSet.PICKAXE))),
                    register(newName +"_axe", new GildedAxeItem(gildedToolMaterial, (AxeItem) baseSet.AXE, GildedToolUtil.settingsOf(baseSet.AXE))),
                    register(newName +"_hoe", new GildedHoeItem(gildedToolMaterial, (HoeItem) baseSet.HOE, GildedToolUtil.settingsOf(baseSet.HOE))),
                    newName
            );
        }
    }
}
