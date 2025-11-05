package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings({"unused"})
public class RegistryHelper {

    public static class BlockRegistryHelper {

        public static <T extends Block> Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
            ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, idOf(id));
            Block block = (Block)factory.apply(settings.setId(key));
            return (Block)Registry.register(BuiltInRegistries.BLOCK, key, block);
        }

    }

    public static class ItemRegistryHelper {

        public record ToolSet(Item SWORD, Item SHOVEL, Item PICKAXE, Item AXE, Item HOE, Item HALBERD, Item SPEAR, ToolMaterial MATERIAL, String NAME){
            public Item[] getItems() {
                return new Item[]{AXE, HOE, PICKAXE, SHOVEL, SWORD, HALBERD, SPEAR};
            }

            public void forEach(Consumer<Item> consumer) {
                consumer.accept(AXE);
                consumer.accept(HOE);
                consumer.accept(PICKAXE);
                consumer.accept(SHOVEL);
                consumer.accept(SWORD);
                consumer.accept(HALBERD);
                consumer.accept(SPEAR);
            }
        }

        public record ItemGroupLocation(ResourceKey<CreativeModeTab> GROUP, Item AFTER){ }
        public static Item prev;

        private static <T extends net.minecraft.world.item.Item> net.minecraft.world.item.Item register(String id, T item) { // Register a given item
            net.minecraft.world.item.Item item1 = Registry.register(BuiltInRegistries.ITEM, idOf(id), item);
            prev = item1;
            return item1;
        }

        private static <T extends net.minecraft.world.item.Item> net.minecraft.world.item.Item register(String id, T item, ResourceKey<CreativeModeTab> itemGroup) { // Register an item, add to a group
            net.minecraft.world.item.Item item1 = register(id, item);
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register( (content) -> content.accept(item1));
            prev = item1;
            return item1;
        }

        private static <T extends net.minecraft.world.item.Item> net.minecraft.world.item.Item register(String id, T item, ResourceKey<CreativeModeTab> itemGroup, net.minecraft.world.item.Item itemAfter) { // Register an item, add to a specific location in a group
            net.minecraft.world.item.Item item1 = register(id, item);
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register( (content) -> content.addAfter(itemAfter, item1));
            prev = item1;
            return item1;
        }

        private static <T extends net.minecraft.world.item.Item> net.minecraft.world.item.Item register(String id, T item, ItemGroupLocation... locations) { // Register an item, add to several locations
            net.minecraft.world.item.Item item1 = register(id, item);
            for (ItemGroupLocation location : locations) {
                ItemGroupEvents.modifyEntriesEvent(location.GROUP).register( (content) -> content.addAfter(location.AFTER, item1));
            }
            prev = item1;
            return item1;
        }

        public static Item register(String id, Item.Properties settings, ResourceKey<CreativeModeTab> itemGroup, net.minecraft.world.item.Item itemAfter) {
            settings = settings.setId(ResourceKey.create(Registries.ITEM, idOf(id)));
            return register(id, new Item(settings), itemGroup, itemAfter);
        }

        public static Item register(String id, Item.Properties settings, ItemGroupLocation... locations) {
            settings = settings.setId(ResourceKey.create(Registries.ITEM, idOf(id)));
            return register(id, new Item(settings), locations);
        }

        public static Item register(String id, Function<Item.Properties, Item> factory, Item.Properties settings) {
            settings = settings.setId(ResourceKey.create(Registries.ITEM, idOf(id)));
            return register(id, factory.apply(settings));
        }

        public static Item register(String id, Function<Item.Properties, Item> factory, Item.Properties settings, ResourceKey<CreativeModeTab> itemGroup, net.minecraft.world.item.Item itemAfter) {
            settings = settings.setId(ResourceKey.create(Registries.ITEM, idOf(id)));
            return register(id, factory.apply(settings), itemGroup, itemAfter);
        }

        public static Item register(String id, Function<Item.Properties, Item> factory, Item.Properties settings, ItemGroupLocation... itemGroupLocations) {
            settings = settings.setId(ResourceKey.create(Registries.ITEM, idOf(id)));
            return register(id, factory.apply(settings), itemGroupLocations);
        }

        public static ItemGroupLocation at(ResourceKey<CreativeModeTab> group, Item after) {
            return new ItemGroupLocation(group, after);
        };


        /**
         * Registers an item without applying settings
         *
         * @param id The in-game ID. This will be "modid:id"
         * **/
        public static net.minecraft.world.item.Item register(String id) { // Create and register an item
            Item.Properties settings = new Item.Properties().setId(ResourceKey.create(Registries.ITEM, idOf(id)));
            return register(id, new net.minecraft.world.item.Item(settings));
        }

        /**
         * Registers an item without applying settings
         *
         * @param id The in-game ID. This will be "modid:id"
         * @param itemGroup The group to place the item into
         * **/
        public static net.minecraft.world.item.Item register(String id, ResourceKey<CreativeModeTab> itemGroup) { // Create and register an item, give a group
            Item.Properties settings = new Item.Properties().setId(ResourceKey.create(Registries.ITEM, idOf(id)));
            return register(id, new net.minecraft.world.item.Item(settings), itemGroup);
        }

        /**
         * Registers an item without applying settings
         *
         * @param id The in-game ID. This will be "modid:id"
         * @param itemGroup The group to place the item into
         * @param itemAfter The item to place the registered item after
         * **/
        public static net.minecraft.world.item.Item register(String id, ResourceKey<CreativeModeTab> itemGroup, net.minecraft.world.item.Item itemAfter) { // Create and register an item, give a location in a group
            Item.Properties settings = new Item.Properties().setId(ResourceKey.create(Registries.ITEM, idOf(id)));
            return register(id, new net.minecraft.world.item.Item(settings), itemGroup, itemAfter);
        }

        /**
         * Registers an item without applying settings
         *
         * @param id The in-game ID. This will be "modid:id"
         * @param itemGroup The group to place the item into
         * @param itemAfter The item to place the registered item after
         * @param features What feature flags need to be enabled in-game for this item to appear
         * **/
        public static net.minecraft.world.item.Item register(String id, ResourceKey<CreativeModeTab> itemGroup, net.minecraft.world.item.Item itemAfter, FeatureFlag... features) { // Create and register an item, give a location in a group
            Item.Properties settings = new Item.Properties().setId(ResourceKey.create(Registries.ITEM, idOf(id))).requiredFeatures(features);
            return register(id, new net.minecraft.world.item.Item(settings), itemGroup, itemAfter);
        }


        /**
         * Registers an item without applying settings
         *
         * @param id The in-game ID. This will be "modid:id"
         * @param locations A location to place the item into
         * @see ItemGroupLocation
         * **/
        public static net.minecraft.world.item.Item register(String id, ItemGroupLocation... locations) { // Create and register an item, give several locations
            Item.Properties settings = new Item.Properties().setId(ResourceKey.create(Registries.ITEM, idOf(id)));
            return register(id, new net.minecraft.world.item.Item(settings), locations);
        }

        /**
         * Registers an item for a block. No settings are applied
         *
         * @param id The in-game ID. This will be "modid:id"
         * @param block The block to create an item for.
         * **/
        public static net.minecraft.world.item.Item registerBlockItem(String id, Block block) { // Create and register a block item
            return register(id, new BlockItem(block, new Item.Properties().overrideDescription(block.getDescriptionId()).setId(ResourceKey.create(Registries.ITEM, idOf(id)))));
        }

        public static net.minecraft.world.item.Item registerBlockItem(String id, Block block, ResourceKey<CreativeModeTab> itemGroup) { // Create and register a block item, give a group
            return register(id, new BlockItem(block, new Item.Properties().overrideDescription(block.getDescriptionId()).setId(ResourceKey.create(Registries.ITEM, idOf(id)))), itemGroup);
        }

        public static net.minecraft.world.item.Item registerBlockItem(String id, Block block, ResourceKey<CreativeModeTab> itemGroup, net.minecraft.world.item.Item itemAfter) { // Create and register a block item, give a location in a group
            return register(id, new BlockItem(block, new Item.Properties().overrideDescription(block.getDescriptionId()).setId(ResourceKey.create(Registries.ITEM, idOf(id)))), itemGroup, itemAfter);
        }

        public static net.minecraft.world.item.Item registerBlockItem(String id, Block block, ItemGroupLocation... locations) { // Create and register a block item, give several locations
            return register(id, new BlockItem(block, new Item.Properties().overrideDescription(block.getDescriptionId()).setId(ResourceKey.create(Registries.ITEM, idOf(id)))), locations);
        }

        public static net.minecraft.world.item.Item registerBlockItem(String id, Block block, ResourceKey<CreativeModeTab> itemGroup, net.minecraft.world.item.Item itemAfter, FeatureFlag... features) { // Create and register a block item, give a location in a group, assign required feature flags
            return register(id, new BlockItem(block, new Item.Properties().overrideDescription(block.getDescriptionId()).setId(ResourceKey.create(Registries.ITEM, idOf(id))).requiredFeatures(features)), itemGroup, itemAfter);
        }

        public static net.minecraft.world.item.Item registerBlockItem(String id, Block block, ItemGroupLocation[] locations, FeatureFlag... features) { // Create and register a block item, give several locations, assign required feature flags
            return register(id, new BlockItem(block, new Item.Properties().overrideDescription(block.getDescriptionId()).setId(ResourceKey.create(Registries.ITEM, idOf(id))).requiredFeatures(features)), locations);
        }
    }
}
