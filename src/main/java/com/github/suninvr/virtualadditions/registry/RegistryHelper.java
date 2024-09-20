package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.item.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlag;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings({"unused"})
public class RegistryHelper {

    public static class BlockRegistryHelper {

        public static <T extends Block> Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
            RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, idOf(id));
            Block block = (Block)factory.apply(settings.registryKey(key));
            return (Block)Registry.register(Registries.BLOCK, key, block);
        }

    }

    public static class ItemRegistryHelper {

        public record ToolSet(Item SWORD, Item SHOVEL, Item PICKAXE, Item AXE, Item HOE, ToolMaterial MATERIAL, String NAME){
            public Item[] getItems() {
                return new Item[]{AXE, HOE, PICKAXE, SHOVEL, SWORD};
            }

            public void forEach(Consumer<Item> consumer) {
                consumer.accept(AXE);
                consumer.accept(HOE);
                consumer.accept(PICKAXE);
                consumer.accept(SHOVEL);
                consumer.accept(SWORD);
            }
        }
        public record ItemGroupLocation(RegistryKey<ItemGroup> GROUP, Item AFTER){}
        public static Item prev;

        private static <T extends net.minecraft.item.Item> net.minecraft.item.Item register(String id, T item) { // Register a given item
            net.minecraft.item.Item item1 = Registry.register(Registries.ITEM, idOf(id), item);
            prev = item1;
            return item1;
        }

        private static <T extends net.minecraft.item.Item> net.minecraft.item.Item register(String id, T item, RegistryKey<ItemGroup> itemGroup) { // Register an item, add to a group
            net.minecraft.item.Item item1 = register(id, item);
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register( (content) -> content.add(item1));
            prev = item1;
            return item1;
        }

        private static <T extends net.minecraft.item.Item> net.minecraft.item.Item register(String id, T item, RegistryKey<ItemGroup> itemGroup, net.minecraft.item.Item itemAfter) { // Register an item, add to a specific location in a group
            net.minecraft.item.Item item1 = register(id, item);
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register( (content) -> content.addAfter(itemAfter, item1));
            prev = item1;
            return item1;
        }

        private static <T extends net.minecraft.item.Item> net.minecraft.item.Item register(String id, T item, ItemGroupLocation... locations) { // Register an item, add to several locations
            net.minecraft.item.Item item1 = register(id, item);
            for (ItemGroupLocation location : locations) {
                ItemGroupEvents.modifyEntriesEvent(location.GROUP).register( (content) -> content.addAfter(location.AFTER, item1));
            }
            prev = item1;
            return item1;
        }

        public static Item register(String id, Item.Settings settings, RegistryKey<ItemGroup> itemGroup, net.minecraft.item.Item itemAfter) {
            settings = settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)));
            return register(id, new Item(settings), itemGroup, itemAfter);
        }

        public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
            settings = settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)));
            return register(id, factory.apply(settings));
        }

        public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings, RegistryKey<ItemGroup> itemGroup, net.minecraft.item.Item itemAfter) {
            settings = settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)));
            return register(id, factory.apply(settings), itemGroup, itemAfter);
        }

        public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings, ItemGroupLocation... itemGroupLocations) {
            settings = settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)));
            return register(id, factory.apply(settings), itemGroupLocations);
        }


        /**
         * Registers an item without applying settings
         *
         * @param id The in-game ID. This will be "modid:id"
         * **/
        public static net.minecraft.item.Item register(String id) { // Create and register an item
            Item.Settings settings = new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)));
            return register(id, new net.minecraft.item.Item(settings));
        }

        /**
         * Registers an item without applying settings
         *
         * @param id The in-game ID. This will be "modid:id"
         * @param itemGroup The group to place the item into
         * **/
        public static net.minecraft.item.Item register(String id, RegistryKey<ItemGroup> itemGroup) { // Create and register an item, give a group
            Item.Settings settings = new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)));
            return register(id, new net.minecraft.item.Item(settings), itemGroup);
        }

        /**
         * Registers an item without applying settings
         *
         * @param id The in-game ID. This will be "modid:id"
         * @param itemGroup The group to place the item into
         * @param itemAfter The item to place the registered item after
         * **/
        public static net.minecraft.item.Item register(String id, RegistryKey<ItemGroup> itemGroup, net.minecraft.item.Item itemAfter) { // Create and register an item, give a location in a group
            Item.Settings settings = new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)));
            return register(id, new net.minecraft.item.Item(settings), itemGroup, itemAfter);
        }

        /**
         * Registers an item without applying settings
         *
         * @param id The in-game ID. This will be "modid:id"
         * @param itemGroup The group to place the item into
         * @param itemAfter The item to place the registered item after
         * @param features What feature flags need to be enabled in-game for this item to appear
         * **/
        public static net.minecraft.item.Item register(String id, RegistryKey<ItemGroup> itemGroup, net.minecraft.item.Item itemAfter, FeatureFlag... features) { // Create and register an item, give a location in a group
            Item.Settings settings = new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id))).requires(features);
            return register(id, new net.minecraft.item.Item(settings), itemGroup, itemAfter);
        }


        /**
         * Registers an item without applying settings
         *
         * @param id The in-game ID. This will be "modid:id"
         * @param locations A location to place the item into
         * @see ItemGroupLocation
         * **/
        public static net.minecraft.item.Item register(String id, ItemGroupLocation... locations) { // Create and register an item, give several locations
            Item.Settings settings = new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)));
            return register(id, new net.minecraft.item.Item(settings), locations);
        }

        /**
         * Registers an item for a block. No settings are applied
         *
         * @param id The in-game ID. This will be "modid:id"
         * @param block The block to create an item for.
         * **/
        public static net.minecraft.item.Item registerBlockItem(String id, Block block) { // Create and register a block item
            return register(id, new BlockItem(block, new Item.Settings().translationKey(block.getTranslationKey()).registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)))));
        }

        public static net.minecraft.item.Item registerBlockItem(String id, Block block, RegistryKey<ItemGroup> itemGroup) { // Create and register a block item, give a group
            return register(id, new BlockItem(block, new Item.Settings().translationKey(block.getTranslationKey()).registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)))), itemGroup);
        }

        public static net.minecraft.item.Item registerBlockItem(String id, Block block, RegistryKey<ItemGroup> itemGroup, net.minecraft.item.Item itemAfter) { // Create and register a block item, give a location in a group
            return register(id, new BlockItem(block, new Item.Settings().translationKey(block.getTranslationKey()).registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)))), itemGroup, itemAfter);
        }

        public static net.minecraft.item.Item registerBlockItem(String id, Block block, ItemGroupLocation... locations) { // Create and register a block item, give several locations
            return register(id, new BlockItem(block, new Item.Settings().translationKey(block.getTranslationKey()).registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id)))), locations);
        }

        public static net.minecraft.item.Item registerBlockItem(String id, Block block, RegistryKey<ItemGroup> itemGroup, net.minecraft.item.Item itemAfter, FeatureFlag... features) { // Create and register a block item, give a location in a group, assign required feature flags
            return register(id, new BlockItem(block, new Item.Settings().translationKey(block.getTranslationKey()).registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id))).requires(features)), itemGroup, itemAfter);
        }

        public static net.minecraft.item.Item registerBlockItem(String id, Block block, ItemGroupLocation[] locations, FeatureFlag... features) { // Create and register a block item, give several locations, assign required feature flags
            return register(id, new BlockItem(block, new Item.Settings().translationKey(block.getTranslationKey()).registryKey(RegistryKey.of(RegistryKeys.ITEM, idOf(id))).requires(features)), locations);
        }

        public static ToolSet registerGildedToolSet(ToolSet baseSet, GildType type) {
            String newName = type.getId().getPath() +"_"+ baseSet.NAME;
            return new ToolSet(
                    register(newName +"_sword", settings -> new GildedSwordItem(type, baseSet.MATERIAL, (SwordItem) baseSet.SWORD, settings), GildedToolUtil.settingsOf(baseSet.SWORD, type)),
                    register(newName +"_shovel", settings -> new GildedShovelItem(type, baseSet.MATERIAL, (ShovelItem) baseSet.SHOVEL, settings), GildedToolUtil.settingsOf(baseSet.SHOVEL, type)),
                    register(newName +"_pickaxe", settings -> new GildedPickaxeItem(type, baseSet.MATERIAL, (PickaxeItem) baseSet.PICKAXE, settings), GildedToolUtil.settingsOf(baseSet.PICKAXE, type)),
                    register(newName +"_axe", settings -> new GildedAxeItem(type, baseSet.MATERIAL, (AxeItem) baseSet.AXE, settings), GildedToolUtil.settingsOf(baseSet.AXE, type)),
                    register(newName +"_hoe", settings -> new GildedHoeItem(type, baseSet.MATERIAL, (HoeItem) baseSet.HOE, settings), GildedToolUtil.settingsOf(baseSet.HOE, type)),
                    type.getModifiedMaterial(baseSet.MATERIAL).asToolMaterial(),
                    newName
            );
        }
    }
}
