package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.block.SpotlightBlock;
import com.github.suninvr.virtualadditions.client.render.item.CrossbowProjectileTypeProperty;
import com.github.suninvr.virtualadditions.datagen.registry.VAModels;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.*;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.bool.HasComponentProperty;
import net.minecraft.client.render.item.property.numeric.CrossbowPullProperty;
import net.minecraft.client.render.item.property.select.ChargeTypeProperty;
import net.minecraft.client.render.item.property.select.TrimMaterialProperty;
import net.minecraft.client.render.item.tint.ConstantTintSource;
import net.minecraft.client.render.item.tint.DyeTintSource;
import net.minecraft.client.render.item.tint.GrassTintSource;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.trim.ArmorTrimAssets;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.FoliageColors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;
import static net.minecraft.client.data.ItemModelGenerator.*;

public class VAModelProvider {

    public static FabricDataGenerator.Pack.Factory<?> base() {
        return BaseProvider::new;
    }

    public static FabricDataGenerator.Pack.Factory<?> enhancements() {
        return EnhancementsProvider::new;
    }

    static class BaseProvider extends Provider {
        public BaseProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.FLOATROCK).family(VACollections.FLOATROCK);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.AEROBLOOM_PLANKS).family(VACollections.AEROBLOOM);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.POLISHED_FLOATROCK).family(VACollections.POLISHED_FLOATROCK);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.FLOATROCK_BRICKS).family(VACollections.FLOATROCK_BRICKS);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.CUT_STEEL).family(VACollections.CUT_STEEL).parented(VABlocks.CUT_STEEL, VABlocks.WAXED_CUT_STEEL).parented(VABlocks.CHISELED_STEEL, VABlocks.WAXED_CHISELED_STEEL).family(VACollections.WAXED_CUT_STEEL);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.EXPOSED_CUT_STEEL).family(VACollections.EXPOSED_CUT_STEEL).parented(VABlocks.EXPOSED_CUT_STEEL, VABlocks.WAXED_EXPOSED_CUT_STEEL).parented(VABlocks.EXPOSED_CHISELED_STEEL, VABlocks.WAXED_EXPOSED_CHISELED_STEEL).family(VACollections.WAXED_EXPOSED_CUT_STEEL);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.WEATHERED_CUT_STEEL).family(VACollections.WEATHERED_CUT_STEEL).parented(VABlocks.WEATHERED_CUT_STEEL, VABlocks.WAXED_WEATHERED_CUT_STEEL).parented(VABlocks.WEATHERED_CHISELED_STEEL, VABlocks.WAXED_WEATHERED_CHISELED_STEEL).family(VACollections.WAXED_WEATHERED_CUT_STEEL);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.OXIDIZED_CUT_STEEL).family(VACollections.OXIDIZED_CUT_STEEL).parented(VABlocks.OXIDIZED_CUT_STEEL, VABlocks.WAXED_OXIDIZED_CUT_STEEL).parented(VABlocks.OXIDIZED_CHISELED_STEEL, VABlocks.WAXED_OXIDIZED_CHISELED_STEEL).family(VACollections.WAXED_OXIDIZED_CUT_STEEL);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.ROCK_SALT_BRICKS).family(VACollections.ROCK_SALT_BRICKS);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.COBBLED_HORNFELS).family(VACollections.COBBLED_HORNFELS);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.COBBLED_BLUESCHIST).family(VACollections.COBBLED_BLUESCHIST);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.BLUESCHIST_BRICKS).family(VACollections.BLUESCHIST_BRICKS);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.POLISHED_BLUESCHIST).family(VACollections.POLISHED_BLUESCHIST);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.COBBLED_SYENITE).family(VACollections.COBBLED_SYENITE);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.SYENITE_BRICKS).family(VACollections.SYENITE_BRICKS);
            blockStateModelGenerator.registerCubeAllModelTexturePool(VABlocks.POLISHED_SYENITE).family(VACollections.POLISHED_SYENITE);
            blockStateModelGenerator.registerTintedItemModel(VABlocks.GRASSY_FLOATROCK, ModelIds.getBlockModelId(VABlocks.GRASSY_FLOATROCK), new GrassTintSource());

            blockStateModelGenerator.registerItemModel(VAItems.CABBAGE_SEEDS);
            blockStateModelGenerator.registerItemModel(VAItems.CORN_SEEDS);
            blockStateModelGenerator.registerItemModel(VAItems.COTTON_SEEDS);
            blockStateModelGenerator.registerItemModel(VAItems.BALLOON_FRUIT);

            blockStateModelGenerator.registerItemModel(VAItems.CLIMBING_ROPE);
            blockStateModelGenerator.registerItemModel(VAItems.EXPOSED_CLIMBING_ROPE);
            blockStateModelGenerator.registerItemModel(VAItems.WEATHERED_CLIMBING_ROPE);
            blockStateModelGenerator.registerItemModel(VAItems.OXIDIZED_CLIMBING_ROPE);
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_CLIMBING_ROPE_ANCHOR, Registries.ITEM.getId(VAItems.CLIMBING_ROPE).withPrefixedPath("item/"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR, Registries.ITEM.getId(VAItems.EXPOSED_CLIMBING_ROPE).withPrefixedPath("item/"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR, Registries.ITEM.getId(VAItems.WEATHERED_CLIMBING_ROPE).withPrefixedPath("item/"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR, Registries.ITEM.getId(VAItems.OXIDIZED_CLIMBING_ROPE).withPrefixedPath("item/"));
            blockStateModelGenerator.registerItemModel(VAItems.STEEL_DOOR);
            blockStateModelGenerator.registerItemModel(VAItems.EXPOSED_STEEL_DOOR);
            blockStateModelGenerator.registerItemModel(VAItems.WEATHERED_STEEL_DOOR);
            blockStateModelGenerator.registerItemModel(VAItems.OXIDIZED_STEEL_DOOR);
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_STEEL_DOOR, Registries.ITEM.getId(VAItems.STEEL_DOOR).withPrefixedPath("item/"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_EXPOSED_STEEL_DOOR, Registries.ITEM.getId(VAItems.EXPOSED_STEEL_DOOR).withPrefixedPath("item/"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_WEATHERED_STEEL_DOOR, Registries.ITEM.getId(VAItems.WEATHERED_STEEL_DOOR).withPrefixedPath("item/"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_OXIDIZED_STEEL_DOOR, Registries.ITEM.getId(VAItems.OXIDIZED_STEEL_DOOR).withPrefixedPath("item/"));

            blockStateModelGenerator.registerParentedItemModel(VABlocks.STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.EXPOSED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.EXPOSED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WEATHERED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.WEATHERED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.OXIDIZED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.OXIDIZED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.EXPOSED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.WEATHERED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.WAXED_OXIDIZED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.OXIDIZED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));

            blockStateModelGenerator.registerParentedItemModel(VABlocks.REDSTONE_BRIDGE, Registries.BLOCK.getId(VABlocks.REDSTONE_BRIDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"));

            blockStateModelGenerator.registerItemModel(VABlocks.ROCK_SALT_CRYSTAL, "_tip");

            blockStateModelGenerator.registerItemModel(VABlocks.FRAYED_SILK);
            blockStateModelGenerator.registerItemModel(VABlocks.GREENCAP_MUSHROOM);
            blockStateModelGenerator.registerItemModel(VABlocks.TALL_GREENCAP_MUSHROOMS, "_top");
            blockStateModelGenerator.registerItemModel(VABlocks.GLOWING_SILK);

            blockStateModelGenerator.registerItemModel(VAItems.AEROBLOOM_HANGING_SIGN);
            blockStateModelGenerator.registerItemModel(VAItems.AEROBLOOM_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED.registerItemModel(blockStateModelGenerator, VABlocks.AEROBLOOM_SAPLING));

            blockStateModelGenerator.registerMirrorable(VABlocks.ROCK_SALT_BLOCK);
            blockStateModelGenerator.registerSimpleCubeAll(VABlocks.ROCK_SALT_ORE);
            blockStateModelGenerator.registerSimpleCubeAll(VABlocks.DEEPSLATE_ROCK_SALT_ORE);
            blockStateModelGenerator.registerSingleton(VABlocks.CHISELED_ROCK_SALT_BRICKS, TexturedModel.CUBE_COLUMN);

            blockStateModelGenerator.registerCrop(VABlocks.TOMATO, CropBlock.AGE, 0, 1, 2, 3, 4, 5, 6, 7);

            registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.CHARTREUSE);
            registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.MAROON);
            registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.INDIGO);
            registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.PLUM);
            registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.VIRIDIAN);
            registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.TAN);
            registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.SINOPIA);
            registerColorfulBlockSetModels( blockStateModelGenerator,VACollections.LILAC);

            registerParentedTintedItemModel(blockStateModelGenerator, VABlocks.OAK_HEDGE, Registries.BLOCK.getId(VABlocks.OAK_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"), new ConstantTintSource(FoliageColors.DEFAULT));
            registerParentedTintedItemModel(blockStateModelGenerator, VABlocks.SPRUCE_HEDGE, Registries.BLOCK.getId(VABlocks.SPRUCE_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"), new ConstantTintSource(FoliageColors.SPRUCE));
            registerParentedTintedItemModel(blockStateModelGenerator, VABlocks.BIRCH_HEDGE, Registries.BLOCK.getId(VABlocks.BIRCH_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"), new ConstantTintSource(FoliageColors.BIRCH));
            registerParentedTintedItemModel(blockStateModelGenerator, VABlocks.JUNGLE_HEDGE, Registries.BLOCK.getId(VABlocks.JUNGLE_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"), new ConstantTintSource(FoliageColors.DEFAULT));
            registerParentedTintedItemModel(blockStateModelGenerator, VABlocks.ACACIA_HEDGE, Registries.BLOCK.getId(VABlocks.ACACIA_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"), new ConstantTintSource(FoliageColors.DEFAULT));
            registerParentedTintedItemModel(blockStateModelGenerator, VABlocks.DARK_OAK_HEDGE, Registries.BLOCK.getId(VABlocks.DARK_OAK_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"), new ConstantTintSource(FoliageColors.DEFAULT));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.PALE_OAK_HEDGE, Registries.BLOCK.getId(VABlocks.PALE_OAK_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"));
            registerParentedTintedItemModel(blockStateModelGenerator, VABlocks.MANGROVE_HEDGE, Registries.BLOCK.getId(VABlocks.MANGROVE_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"), new ConstantTintSource(FoliageColors.MANGROVE));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.CHERRY_HEDGE, Registries.BLOCK.getId(VABlocks.CHERRY_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.AEROBLOOM_HEDGE, Registries.BLOCK.getId(VABlocks.AEROBLOOM_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.AZALEA_HEDGE, Registries.BLOCK.getId(VABlocks.AZALEA_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"));
            blockStateModelGenerator.registerParentedItemModel(VABlocks.FLOWERING_AZALEA_HEDGE, Registries.BLOCK.getId(VABlocks.FLOWERING_AZALEA_HEDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"));

            registerColoringStation(blockStateModelGenerator);

            registerSpotlight(blockStateModelGenerator);

            blockStateModelGenerator.registerFlowerPotPlant(VABlocks.AEROBLOOM_SAPLING, VABlocks.POTTED_AEROBLOOM_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
            blockStateModelGenerator.registerFlowerPotPlant(VABlocks.GREENCAP_MUSHROOM, VABlocks.POTTED_GREENCAP_MUSHROOM, BlockStateModelGenerator.CrossType.NOT_TINTED);

            blockStateModelGenerator.registerSimpleCubeAll(VABlocks.STEEL_GRATE);
            blockStateModelGenerator.registerSimpleCubeAll(VABlocks.EXPOSED_STEEL_GRATE);
            blockStateModelGenerator.registerSimpleCubeAll(VABlocks.WEATHERED_STEEL_GRATE);
            blockStateModelGenerator.registerSimpleCubeAll(VABlocks.OXIDIZED_STEEL_GRATE);
            blockStateModelGenerator.registerParented(VABlocks.STEEL_GRATE, VABlocks.WAXED_STEEL_GRATE);
            blockStateModelGenerator.registerParented(VABlocks.EXPOSED_STEEL_GRATE, VABlocks.WAXED_EXPOSED_STEEL_GRATE);
            blockStateModelGenerator.registerParented(VABlocks.WEATHERED_STEEL_GRATE, VABlocks.WAXED_WEATHERED_STEEL_GRATE);
            blockStateModelGenerator.registerParented(VABlocks.OXIDIZED_STEEL_GRATE, VABlocks.WAXED_OXIDIZED_STEEL_GRATE);
            blockStateModelGenerator.registerSimpleCubeAll(VABlocks.EXPOSED_STEEL_BLOCK);
            blockStateModelGenerator.registerSimpleCubeAll(VABlocks.WEATHERED_STEEL_BLOCK);
            blockStateModelGenerator.registerSimpleCubeAll(VABlocks.OXIDIZED_STEEL_BLOCK);
            blockStateModelGenerator.registerParented(VABlocks.STEEL_BLOCK, VABlocks.WAXED_STEEL_BLOCK);
            blockStateModelGenerator.registerParented(VABlocks.EXPOSED_STEEL_BLOCK, VABlocks.WAXED_EXPOSED_STEEL_BLOCK);
            blockStateModelGenerator.registerParented(VABlocks.WEATHERED_STEEL_BLOCK, VABlocks.WAXED_WEATHERED_STEEL_BLOCK);
            blockStateModelGenerator.registerParented(VABlocks.OXIDIZED_STEEL_BLOCK, VABlocks.WAXED_OXIDIZED_STEEL_BLOCK);

            blockStateModelGenerator.registerParented(VABlocks.STEEL_BLOCK, VABlocks.REMOTE_NOTIFIER);
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            generateGildedToolItemModels(itemModelGenerator, VAItems.AMETHYST_TOOL_SETS);
            generateGildedToolItemModels(itemModelGenerator, VAItems.COPPER_TOOL_SETS);
            generateGildedToolItemModels(itemModelGenerator, VAItems.EMERALD_TOOL_SETS);
            generateGildedToolItemModels(itemModelGenerator, VAItems.IOLITE_TOOL_SETS);
            generateGildedToolItemModels(itemModelGenerator, VAItems.QUARTZ_TOOL_SETS);
            generateGildedToolItemModels(itemModelGenerator, VAItems.SCULK_TOOL_SETS);

            itemModelGenerator.register(VAItems.CHARTREUSE_DYE, Models.GENERATED);
            itemModelGenerator.register(VAItems.MAROON_DYE, Models.GENERATED);
            itemModelGenerator.register(VAItems.INDIGO_DYE, Models.GENERATED);
            itemModelGenerator.register(VAItems.PLUM_DYE, Models.GENERATED);
            itemModelGenerator.register(VAItems.VIRIDIAN_DYE, Models.GENERATED);
            itemModelGenerator.register(VAItems.TAN_DYE, Models.GENERATED);
            itemModelGenerator.register(VAItems.SINOPIA_DYE, Models.GENERATED);
            itemModelGenerator.register(VAItems.LILAC_DYE, Models.GENERATED);
            itemModelGenerator.register(VAItems.ROCK_SALT, Models.GENERATED);
            itemModelGenerator.register(VAItems.RAW_STEEL, Models.GENERATED);
            itemModelGenerator.register(VAItems.STEEL_INGOT, Models.GENERATED);
            itemModelGenerator.register(VAItems.TOOL_GILD_SMITHING_TEMPLATE, Models.GENERATED);
            itemModelGenerator.register(VAItems.STEEL_BOMB, Models.GENERATED);
            itemModelGenerator.register(VAItems.IOLITE, Models.GENERATED);
            itemModelGenerator.register(VAItems.TOMATO, Models.GENERATED);
            itemModelGenerator.register(VAItems.CABBAGE, Models.GENERATED);
            itemModelGenerator.register(VAItems.CORN, Models.GENERATED);
            itemModelGenerator.register(VAItems.ROASTED_CORN, Models.GENERATED);
            itemModelGenerator.register(VAItems.COTTON, Models.GENERATED);

            itemModelGenerator.register(VAItems.FRIED_EGG, Models.GENERATED);
            itemModelGenerator.register(VAItems.CHEESE_WEDGE, Models.GENERATED);
            itemModelGenerator.register(VAItems.BEEF_JERKY, Models.GENERATED);
            itemModelGenerator.register(VAItems.PORK_JERKY, Models.GENERATED);
            itemModelGenerator.register(VAItems.CHICKEN_JERKY, Models.GENERATED);
            itemModelGenerator.register(VAItems.MUTTON_JERKY, Models.GENERATED);
            itemModelGenerator.register(VAItems.SWEET_BERRY_PIE, Models.GENERATED);
            itemModelGenerator.register(VAItems.ACID_BUCKET, Models.GENERATED);
            itemModelGenerator.register(VAItems.SILK_THREAD, Models.GENERATED);
            itemModelGenerator.register(VAItems.LUMWASP_MANDIBLE, Models.GENERATED);

            itemModelGenerator.register(VAItems.LIGHTNING_BOTTLE, Models.GENERATED);

            itemModelGenerator.register(VAItems.SALINE_SPAWN_EGG, Models.GENERATED);
            itemModelGenerator.register(VAItems.LUMWASP_SPAWN_EGG, Models.GENERATED);

            itemModelGenerator.register(VAItems.STEEL_SWORD, Models.HANDHELD);
            itemModelGenerator.register(VAItems.STEEL_SHOVEL, Models.HANDHELD);
            itemModelGenerator.register(VAItems.STEEL_PICKAXE, Models.HANDHELD);
            itemModelGenerator.register(VAItems.STEEL_AXE, Models.HANDHELD);
            itemModelGenerator.register(VAItems.STEEL_HOE, Models.HANDHELD);

            itemModelGenerator.registerArmor(VAItems.STEEL_HELMET, VAArmorMaterial.STEEL.assetId(), HELMET_TRIM_ID_PREFIX, false);
            itemModelGenerator.registerArmor(VAItems.STEEL_CHESTPLATE, VAArmorMaterial.STEEL.assetId(), CHESTPLATE_TRIM_ID_PREFIX, false);
            itemModelGenerator.registerArmor(VAItems.STEEL_LEGGINGS, VAArmorMaterial.STEEL.assetId(), LEGGINGS_TRIM_ID_PREFIX, false);
            itemModelGenerator.registerArmor(VAItems.STEEL_BOOTS, VAArmorMaterial.STEEL.assetId(), BOOTS_TRIM_ID_PREFIX, false);
            itemModelGenerator.register(VAItems.STEEL_HORSE_ARMOR, Models.GENERATED);

            itemModelGenerator.registerBundle(VAItems.CHARTREUSE_BUNDLE);
            itemModelGenerator.registerBundle(VAItems.MAROON_BUNDLE);
            itemModelGenerator.registerBundle(VAItems.INDIGO_BUNDLE);
            itemModelGenerator.registerBundle(VAItems.PLUM_BUNDLE);
            itemModelGenerator.registerBundle(VAItems.VIRIDIAN_BUNDLE);
            itemModelGenerator.registerBundle(VAItems.TAN_BUNDLE);
            itemModelGenerator.registerBundle(VAItems.SINOPIA_BUNDLE);
            itemModelGenerator.registerBundle(VAItems.LILAC_BUNDLE);
            itemModelGenerator.register(VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE, Models.GENERATED);

            registerTintableWithDefaultTexture(itemModelGenerator, VAItems.ENGRAVING_CHISEL);
            registerTintableWithDefaultTexture(itemModelGenerator, VAItems.ICE_CREAM);

            registerApplicablePotion(itemModelGenerator, VAItems.APPLICABLE_POTION);

        }
    }

    static class EnhancementsProvider extends Provider {
        public EnhancementsProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            haltModelGeneration = true;
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    VAItems.STEEL_HELMET,
                    VAItems.STEEL_CHESTPLATE,
                    VAItems.STEEL_LEGGINGS,
                    VAItems.STEEL_BOOTS,
                    VAArmorMaterial.STEEL.assetId(), false);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.IRON_HELMET,
                    Items.IRON_CHESTPLATE,
                    Items.IRON_LEGGINGS,
                    Items.IRON_BOOTS,
                    EquipmentAssetKeys.IRON, false);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.GOLDEN_HELMET,
                    Items.GOLDEN_CHESTPLATE,
                    Items.GOLDEN_LEGGINGS,
                    Items.GOLDEN_BOOTS,
                    EquipmentAssetKeys.GOLD, false);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.DIAMOND_HELMET,
                    Items.DIAMOND_CHESTPLATE,
                    Items.DIAMOND_LEGGINGS,
                    Items.DIAMOND_BOOTS,
                    EquipmentAssetKeys.DIAMOND, false);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.NETHERITE_HELMET,
                    Items.NETHERITE_CHESTPLATE,
                    Items.NETHERITE_LEGGINGS,
                    Items.NETHERITE_BOOTS,
                    EquipmentAssetKeys.NETHERITE, false);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.CHAINMAIL_HELMET,
                    Items.CHAINMAIL_CHESTPLATE,
                    Items.CHAINMAIL_LEGGINGS,
                    Items.CHAINMAIL_BOOTS,
                    EquipmentAssetKeys.CHAINMAIL, false);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.LEATHER_HELMET,
                    Items.LEATHER_CHESTPLATE,
                    Items.LEATHER_LEGGINGS,
                    Items.LEATHER_BOOTS,
                    EquipmentAssetKeys.LEATHER, true);

            registerCrossbow(itemModelGenerator, Items.CROSSBOW);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private abstract static class Provider extends FabricModelProvider {
        private static final List<ItemModelGenerator.TrimMaterial> TRIM_MATERIALS_EXTENDED =
                List.of(
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.QUARTZ, ArmorTrimMaterials.QUARTZ),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.IRON, ArmorTrimMaterials.IRON),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.NETHERITE, ArmorTrimMaterials.NETHERITE),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.REDSTONE, ArmorTrimMaterials.REDSTONE),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.COPPER, ArmorTrimMaterials.COPPER),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.GOLD, ArmorTrimMaterials.GOLD),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.EMERALD, ArmorTrimMaterials.EMERALD),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.DIAMOND, ArmorTrimMaterials.DIAMOND),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.LAPIS, ArmorTrimMaterials.LAPIS),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.AMETHYST, ArmorTrimMaterials.AMETHYST),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.RESIN, ArmorTrimMaterials.RESIN),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("virtual_additions_steel", Map.of(VAArmorMaterial.STEEL_ASSET_KEY, "virtual_additions_steel_darker")), VAArmorTrimMaterials.STEEL),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("virtual_additions_rock_salt"), VAArmorTrimMaterials.ROCK_SALT),
                        new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("virtual_additions_iolite"), VAArmorTrimMaterials.IOLITE)
                );

        public Provider(FabricDataOutput output) {
            super(output);
        }

        protected void registerColoringStation(BlockStateModelGenerator blockStateModelGenerator) {
            TextureMap textureMap = new TextureMap()
                    .put(TextureKey.PARTICLE, TextureMap.getSubId(VABlocks.COLORING_STATION, "_front"))
                    .put(TextureKey.DOWN, TextureMap.getSubId(VABlocks.COLORING_STATION, "_bottom"))
                    .put(TextureKey.UP, TextureMap.getSubId(VABlocks.COLORING_STATION, "_top"))
                    .put(TextureKey.NORTH, TextureMap.getSubId(VABlocks.COLORING_STATION, "_front"))
                    .put(TextureKey.SOUTH, TextureMap.getSubId(VABlocks.COLORING_STATION, "_front"))
                    .put(TextureKey.EAST, TextureMap.getSubId(VABlocks.COLORING_STATION, "_side"))
                    .put(TextureKey.WEST, TextureMap.getSubId(VABlocks.COLORING_STATION, "_side"));
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(VABlocks.COLORING_STATION, BlockStateModelGenerator.createWeightedVariant(Models.CUBE.upload(VABlocks.COLORING_STATION, textureMap, blockStateModelGenerator.modelCollector))));
        }

        protected void registerColorfulBlockSetModels(BlockStateModelGenerator g, ColorfulBlockSet s) {
            s.ifWool(wool -> {
                if (s.carpet() != null) g.registerWoolAndCarpet(wool, s.carpet());
                else g.registerSimpleCubeAll(s.wool());
                s.ifBed(bed -> g.registerBed(bed, wool, s.dye().getColor()));
            });
            if (s.terracotta() != null) g.registerSimpleCubeAll(s.terracotta());
            if (s.concrete() != null) g.registerSimpleCubeAll(s.concrete());
            if (s.concretePowder() != null) g.registerRandomHorizontalRotations(TexturedModel.CUBE_ALL,s.concretePowder());
            if (s.stainedGlass() != null) {
                if (s.stainedGlassPane() != null) g.registerGlassAndPane(s.stainedGlass(), s.stainedGlassPane());
                else g.registerSimpleCubeAll(s.stainedGlass());
            }
            if (s.silkbulb() != null) g.registerSimpleCubeAll(s.silkbulb());
            if (s.candle() != null && s.candleCake() != null) g.registerCandle(s.candle(), s.candleCake());
            s.ifShulkerBox(shulkerbox -> g.registerShulkerBox(shulkerbox, s.dye().getColor()));
            s.ifGlazedTerracotta(block -> g.registerSouthDefaultHorizontalFacing(TexturedModel.TEMPLATE_GLAZED_TERRACOTTA, block));
            s.ifBanner(banner -> s.ifWallBanner(wallBanner -> g.registerBanner(banner, wallBanner, s.dye().getColor())));
        }

        protected void generateGildedToolItemModels(ItemModelGenerator itemModelGenerator, RegistryHelper.ItemRegistryHelper.ToolSet... toolSets) {
            for (RegistryHelper.ItemRegistryHelper.ToolSet set : toolSets) {
                uploadGildedToolModels(itemModelGenerator, set);
            }
        }

        public static void uploadGildedToolModels(ItemModelGenerator itemModelGenerator, RegistryHelper.ItemRegistryHelper.ToolSet set) {
            uploadGildedToolModel(itemModelGenerator, set.SWORD(), "_sword");
            uploadGildedToolModel(itemModelGenerator, set.SHOVEL(), "_shovel");
            uploadGildedToolModel(itemModelGenerator, set.PICKAXE(), "_pickaxe");
            uploadGildedToolModel(itemModelGenerator, set.AXE(), "_axe");
            uploadGildedToolModel(itemModelGenerator, set.HOE(), "_hoe");
        }

        public static void uploadGildedToolModel(ItemModelGenerator itemModelGenerator, Item item, String suffix) {
            GildedToolItem gildedToolItem = item instanceof GildedToolItem ? (GildedToolItem) item : null;
            if (gildedToolItem == null) return;
            Item baseItem = gildedToolItem.getBaseItem();
            Identifier base = ModelIds.getItemModelId(baseItem);
            Identifier gild = gildedToolItem.getGildType().getId().withSuffixedPath(suffix).withPrefixedPath("item/gilded_tools/");
            Identifier id = ModelIds.getItemModelId(item);
            VAModels.HANDHELD_TWO_LAYERS.upload(id, TextureMap.layered(base, gild), itemModelGenerator.modelCollector);
            itemModelGenerator.output.accept(item, ItemModels.basic(id));
        }

        protected void registerSpotlight(BlockStateModelGenerator generator) {
            WeightedVariant spotlight = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockModelId(VABlocks.SPOTLIGHT));
            WeightedVariant spotlightActive = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockSubModelId(VABlocks.SPOTLIGHT, "_active"));
            generator.blockStateCollector
                    .accept(
                            VariantsBlockModelDefinitionCreator.of(VABlocks.SPOTLIGHT)
                                    .with(
                                            BlockStateVariantMap.models(SpotlightBlock.POWERED)
                                                    .register(false, spotlight)
                                                    .register(true, spotlightActive)
                                    )
                                    .coordinate(BlockStateVariantMap.operations(Properties.ORIENTATION).generate(BlockStateModelGenerator::addJigsawOrientationToVariant))
                    );
        }

        public final void registerApplicablePotion(ItemModelGenerator generator, Item item) {
            Identifier identifier = generator.uploadTwoLayers(item, idOf("item/applicable_potion_overlay"), ModelIds.getItemModelId(item));
            generator.registerPotionTinted(item, identifier);
        }

        public final void registerTintableWithDefaultTexture(ItemModelGenerator generator, Item item) {
            Identifier undyedItemIdentifier = generator.upload(item, Models.GENERATED);
            Identifier itemIdentifier = Registries.ITEM.getId(item).withPrefixedPath("item/");
            Identifier dyedItemIdentifier = Models.GENERATED_TWO_LAYERS.upload(ModelIds.getItemSubModelId(item, "_dyed"), TextureMap.layered(itemIdentifier.withSuffixedPath("_layer"), itemIdentifier.withSuffixedPath("_base")), generator.modelCollector);
            generator.output.accept(item, ItemModels.condition(new HasComponentProperty(DataComponentTypes.DYED_COLOR, true), ItemModels.tinted(dyedItemIdentifier, new DyeTintSource(0xFFFFFF)), ItemModels.basic(undyedItemIdentifier)));
        }

        public void registerParentedTintedItemModel(BlockStateModelGenerator generator, Block block, Identifier parentModelId, TintSource... sources) {
            generator.itemModelOutput.accept(block.asItem(), ItemModels.tinted(parentModelId, sources));
        }

        public static void registerArmorSetWithExtendedTrimMaterials(ItemModelGenerator generator, Item helmet, Item chestplate, Item leggings, Item boots, RegistryKey<EquipmentAsset> equipmentKey, boolean dyeable) {
            registerArmorWithExtendedTrimMaterials(generator, helmet, equipmentKey, HELMET_TRIM_ID_PREFIX, dyeable);
            registerArmorWithExtendedTrimMaterials(generator, chestplate, equipmentKey, CHESTPLATE_TRIM_ID_PREFIX, dyeable);
            registerArmorWithExtendedTrimMaterials(generator, leggings, equipmentKey, LEGGINGS_TRIM_ID_PREFIX, dyeable);
            registerArmorWithExtendedTrimMaterials(generator, boots, equipmentKey, BOOTS_TRIM_ID_PREFIX, dyeable);
        }

        public static void registerArmorWithExtendedTrimMaterials(ItemModelGenerator generator, Item item, RegistryKey<EquipmentAsset> equipmentKey, Identifier trimIdPrefix, boolean dyeable) {
            Identifier identifier = ModelIds.getItemModelId(item);
            Identifier identifier2 = TextureMap.getId(item);
            Identifier identifier3 = TextureMap.getSubId(item, "_overlay");
            List<SelectItemModel.SwitchCase<RegistryKey<ArmorTrimMaterial>>> list = new ArrayList<>(TRIM_MATERIALS_EXTENDED.size());

            ItemModelGenerator.TrimMaterial trimMaterial;
            ItemModel.Unbaked unbaked;
            for(Iterator<ItemModelGenerator.TrimMaterial> iterator = TRIM_MATERIALS_EXTENDED.iterator(); iterator.hasNext(); list.add(ItemModels.switchCase(trimMaterial.materialKey, unbaked))) {
                trimMaterial = iterator.next();
                Identifier identifier4 = identifier.withSuffixedPath("_" + trimMaterial.assets().base().suffix() + "_trim");
                String var10001 = trimMaterial.assets().getAssetId(equipmentKey).suffix();
                Identifier identifier5 = trimIdPrefix.withSuffixedPath("_" + var10001);
                if (dyeable) {
                    generator.uploadArmor(identifier4, identifier2, identifier3, identifier5);
                    unbaked = ItemModels.tinted(identifier4, new DyeTintSource(-6265536));
                } else {
                    generator.uploadArmor(identifier4, identifier2, identifier5);
                    unbaked = ItemModels.basic(identifier4);
                }
            }

            ItemModel.Unbaked unbaked2;
            if (dyeable) {
                Models.GENERATED_TWO_LAYERS.upload(identifier, TextureMap.layered(identifier2, identifier3), generator.modelCollector);
                unbaked2 = ItemModels.tinted(identifier, new DyeTintSource(-6265536));
            } else {
                Models.GENERATED.upload(identifier, TextureMap.layer0(identifier2), generator.modelCollector);
                unbaked2 = ItemModels.basic(identifier);
            }

            generator.output.accept(item, ItemModels.select(new TrimMaterialProperty(), unbaked2, list));
        }

        public final void registerCrossbow(ItemModelGenerator generator, Item item) {
            ItemModel.Unbaked unbaked = ItemModels.basic(ModelIds.getItemModelId(item));
            ItemModel.Unbaked unbaked2 = ItemModels.basic(generator.registerSubModel(item, "_pulling_0", Models.CROSSBOW));
            ItemModel.Unbaked unbaked3 = ItemModels.basic(generator.registerSubModel(item, "_pulling_1", Models.CROSSBOW));
            ItemModel.Unbaked unbaked4 = ItemModels.basic(generator.registerSubModel(item, "_pulling_2", Models.CROSSBOW));
            ItemModel.Unbaked unbaked5 = ItemModels.basic(generator.registerSubModel(item, "_arrow", Models.CROSSBOW));
            ItemModel.Unbaked unbaked6 = ItemModels.basic(generator.registerSubModel(item, "_firework", Models.CROSSBOW));
            ItemModel.Unbaked unbaked7 = ItemModels.basic(generator.registerSubModel(item, "_climbing_rope", Models.CROSSBOW));
            ItemModel.Unbaked unbaked8 = ItemModels.basic(generator.registerSubModel(item, "_exposed_climbing_rope", Models.CROSSBOW));
            ItemModel.Unbaked unbaked9 = ItemModels.basic(generator.registerSubModel(item, "_weathered_climbing_rope", Models.CROSSBOW));
            ItemModel.Unbaked unbaked10 = ItemModels.basic(generator.registerSubModel(item, "_oxidized_climbing_rope", Models.CROSSBOW));
            generator.output.accept(item, ItemModels.condition(
                    ItemModels.usingItemProperty(), ItemModels.rangeDispatch(new CrossbowPullProperty(), unbaked2, ItemModels.rangeDispatchEntry(unbaked3, 0.58F), ItemModels.rangeDispatchEntry(unbaked4, 1.0F)),
                    ItemModels.select(new ChargeTypeProperty(), unbaked,
                            ItemModels.switchCase(CrossbowItem.ChargeType.ARROW, ItemModels.rangeDispatch(new CrossbowProjectileTypeProperty(), unbaked5,
                                    ItemModels.rangeDispatchEntry(unbaked7, 0.25F),
                                    ItemModels.rangeDispatchEntry(unbaked8, 0.5F),
                                    ItemModels.rangeDispatchEntry(unbaked9, 0.75F),
                                    ItemModels.rangeDispatchEntry(unbaked10, 1.0F)
                                    )),
                            ItemModels.switchCase(CrossbowItem.ChargeType.ROCKET, unbaked6))));
        }
    }

    private static boolean haltModelGeneration = false;

    public static boolean isModelGenerationHalted() {
        return haltModelGeneration;
    }

    public static void unhaltModelGeneration() {
        haltModelGeneration = false;
    }
}
