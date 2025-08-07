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
        public void generateBlockStateModels(BlockStateModelGenerator generator) {

            generator.registerCubeAllModelTexturePool(VABlocks.PORPHYRY).family(VACollections.PORPHYRY);
            generator.registerCubeAllModelTexturePool(VABlocks.SOULBLOOM_PLANKS).family(VACollections.SOULBLOOM);
            generator.registerCubeAllModelTexturePool(VABlocks.WITHERED_PLANKS).family(VACollections.WITHERED);
            generator.registerCubeAllModelTexturePool(VABlocks.POLISHED_PORPHYRY).family(VACollections.POLISHED_PORPHYRY);
            generator.registerCubeAllModelTexturePool(VABlocks.PORPHYRY_BRICKS).family(VACollections.PORPHYRY_BRICKS);
            generator.registerCubeAllModelTexturePool(VABlocks.CUT_STEEL).family(VACollections.CUT_STEEL).parented(VABlocks.CUT_STEEL, VABlocks.WAXED_CUT_STEEL).parented(VABlocks.CHISELED_STEEL, VABlocks.WAXED_CHISELED_STEEL).family(VACollections.WAXED_CUT_STEEL);
            generator.registerCubeAllModelTexturePool(VABlocks.EXPOSED_CUT_STEEL).family(VACollections.EXPOSED_CUT_STEEL).parented(VABlocks.EXPOSED_CUT_STEEL, VABlocks.WAXED_EXPOSED_CUT_STEEL).parented(VABlocks.EXPOSED_CHISELED_STEEL, VABlocks.WAXED_EXPOSED_CHISELED_STEEL).family(VACollections.WAXED_EXPOSED_CUT_STEEL);
            generator.registerCubeAllModelTexturePool(VABlocks.WEATHERED_CUT_STEEL).family(VACollections.WEATHERED_CUT_STEEL).parented(VABlocks.WEATHERED_CUT_STEEL, VABlocks.WAXED_WEATHERED_CUT_STEEL).parented(VABlocks.WEATHERED_CHISELED_STEEL, VABlocks.WAXED_WEATHERED_CHISELED_STEEL).family(VACollections.WAXED_WEATHERED_CUT_STEEL);
            generator.registerCubeAllModelTexturePool(VABlocks.OXIDIZED_CUT_STEEL).family(VACollections.OXIDIZED_CUT_STEEL).parented(VABlocks.OXIDIZED_CUT_STEEL, VABlocks.WAXED_OXIDIZED_CUT_STEEL).parented(VABlocks.OXIDIZED_CHISELED_STEEL, VABlocks.WAXED_OXIDIZED_CHISELED_STEEL).family(VACollections.WAXED_OXIDIZED_CUT_STEEL);
            generator.registerCubeAllModelTexturePool(VABlocks.ROCK_SALT_BRICKS).family(VACollections.ROCK_SALT_BRICKS);
            generator.registerCubeAllModelTexturePool(VABlocks.COBBLED_HORNFELS).family(VACollections.COBBLED_HORNFELS);
            generator.registerCubeAllModelTexturePool(VABlocks.COBBLED_BLUESCHIST).family(VACollections.COBBLED_BLUESCHIST);
            generator.registerCubeAllModelTexturePool(VABlocks.BLUESCHIST_BRICKS).family(VACollections.BLUESCHIST_BRICKS);
            generator.registerCubeAllModelTexturePool(VABlocks.POLISHED_BLUESCHIST).family(VACollections.POLISHED_BLUESCHIST);
            generator.registerCubeAllModelTexturePool(VABlocks.COBBLED_SYENITE).family(VACollections.COBBLED_SYENITE);
            generator.registerCubeAllModelTexturePool(VABlocks.SYENITE_BRICKS).family(VACollections.SYENITE_BRICKS);
            generator.registerCubeAllModelTexturePool(VABlocks.POLISHED_SYENITE).family(VACollections.POLISHED_SYENITE);

            registerSimpleBlockItems(generator,
                    VABlocks.HORNFELS,
                    VABlocks.POLISHED_HORNFELS,
                    VABlocks.POLISHED_HORNFELS_STAIRS,
                    VABlocks.POLISHED_HORNFELS_SLAB,
                    VABlocks.HORNFELS_TILES,
                    VABlocks.CRACKED_HORNFELS_TILES,
                    VABlocks.HORNFELS_TILE_STAIRS,
                    VABlocks.HORNFELS_TILE_SLAB,
                    VABlocks.CHISELED_HORNFELS_TILES,
                    VABlocks.SYENITE,
                    VABlocks.CAGELIGHT,
                    VABlocks.LUMWASP_NEST,
                    VABlocks.ACID_BLOCK,
                    VABlocks.ENTANGLEMENT_DRIVE
                    );
            
            registerSimpleCubeAll(generator,
                    VABlocks.CHISELED_HORNFELS,
                    VABlocks.BLUESCHIST,
                    VABlocks.RAW_STEEL_BLOCK,
                    VABlocks.SILK_BLOCK,
                    VABlocks.WEBBED_SILK,
                    VABlocks.IOLITE_ORE,
                    VABlocks.IOLITE_BLOCK,
                    VABlocks.SILKBULB,
                    VABlocks.WHITE_SILKBULB,
                    VABlocks.LIGHT_GRAY_SILKBULB,
                    VABlocks.GRAY_SILKBULB,
                    VABlocks.BLACK_SILKBULB,
                    VABlocks.BROWN_SILKBULB,
                    VABlocks.RED_SILKBULB,
                    VABlocks.ORANGE_SILKBULB,
                    VABlocks.YELLOW_SILKBULB,
                    VABlocks.LIME_SILKBULB,
                    VABlocks.GREEN_SILKBULB,
                    VABlocks.CYAN_SILKBULB,
                    VABlocks.LIGHT_BLUE_SILKBULB,
                    VABlocks.BLUE_SILKBULB,
                    VABlocks.PURPLE_SILKBULB,
                    VABlocks.MAGENTA_SILKBULB,
                    VABlocks.PINK_SILKBULB,
                    VABlocks.ROCK_SALT_ORE,
                    VABlocks.DEEPSLATE_ROCK_SALT_ORE,
                    VABlocks.SOULBLOOM_LEAVES,
                    VABlocks.WITHERED_LEAVES,
                    VABlocks.SPECTRAL_SAND,
                    VABlocks.STEEL_BLOCK,
                    VABlocks.EXPOSED_STEEL_BLOCK,
                    VABlocks.WEATHERED_STEEL_BLOCK,
                    VABlocks.OXIDIZED_STEEL_BLOCK,
                    VABlocks.STEEL_GRATE,
                    VABlocks.EXPOSED_STEEL_GRATE,
                    VABlocks.WEATHERED_STEEL_GRATE,
                    VABlocks.OXIDIZED_STEEL_GRATE
                    );

            generator.createLogTexturePool(VABlocks.SOULBLOOM_LOG).log(VABlocks.SOULBLOOM_LOG).wood(VABlocks.SOULBLOOM_WOOD);
            generator.createLogTexturePool(VABlocks.STRIPPED_SOULBLOOM_LOG).log(VABlocks.STRIPPED_SOULBLOOM_LOG).wood(VABlocks.STRIPPED_SOULBLOOM_WOOD);
            generator.registerHangingSign(VABlocks.STRIPPED_SOULBLOOM_LOG, VABlocks.SOULBLOOM_HANGING_SIGN, VABlocks.SOULBLOOM_WALL_HANGING_SIGN);
            generator.registerShelf(VABlocks.SOULBLOOM_SHELF);

            generator.registerNetherrackBottomCustomTop(VABlocks.NECROTIC_NYLIUM);
            generator.createLogTexturePool(VABlocks.WITHERED_LOG).log(VABlocks.WITHERED_LOG).wood(VABlocks.WITHERED_WOOD);
            generator.createLogTexturePool(VABlocks.STRIPPED_WITHERED_LOG).log(VABlocks.STRIPPED_WITHERED_LOG).wood(VABlocks.STRIPPED_WITHERED_WOOD);
            generator.registerHangingSign(VABlocks.STRIPPED_WITHERED_LOG, VABlocks.WITHERED_HANGING_SIGN, VABlocks.WITHERED_WALL_HANGING_SIGN);
            generator.registerShelf(VABlocks.WITHERED_SHELF);

            registerSpectralFire(generator);
            generator.registerTorch(VABlocks.SPECTRAL_TORCH, VABlocks.SPECTRAL_WALL_TORCH);
            generator.registerLantern(VABlocks.SPECTRAL_LANTERN);

            generator.registerFlowerbed(VABlocks.BLUE_PETALS);

            generator.registerItemModel(VAItems.CABBAGE_SEEDS);
            generator.registerItemModel(VAItems.CORN_SEEDS);
            generator.registerItemModel(VAItems.COTTON_SEEDS);
            generator.registerItemModel(VAItems.BALLOON_FRUIT);

            generator.registerItemModel(VAItems.CLIMBING_ROPE);
            generator.registerItemModel(VAItems.EXPOSED_CLIMBING_ROPE);
            generator.registerItemModel(VAItems.WEATHERED_CLIMBING_ROPE);
            generator.registerItemModel(VAItems.OXIDIZED_CLIMBING_ROPE);
            generator.registerParentedItemModel(VABlocks.WAXED_CLIMBING_ROPE_ANCHOR, Registries.ITEM.getId(VAItems.CLIMBING_ROPE).withPrefixedPath("item/"));
            generator.registerParentedItemModel(VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR, Registries.ITEM.getId(VAItems.EXPOSED_CLIMBING_ROPE).withPrefixedPath("item/"));
            generator.registerParentedItemModel(VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR, Registries.ITEM.getId(VAItems.WEATHERED_CLIMBING_ROPE).withPrefixedPath("item/"));
            generator.registerParentedItemModel(VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR, Registries.ITEM.getId(VAItems.OXIDIZED_CLIMBING_ROPE).withPrefixedPath("item/"));
            generator.registerItemModel(VAItems.STEEL_DOOR);
            generator.registerItemModel(VAItems.EXPOSED_STEEL_DOOR);
            generator.registerItemModel(VAItems.WEATHERED_STEEL_DOOR);
            generator.registerItemModel(VAItems.OXIDIZED_STEEL_DOOR);
            generator.registerParentedItemModel(VABlocks.WAXED_STEEL_DOOR, Registries.ITEM.getId(VAItems.STEEL_DOOR).withPrefixedPath("item/"));
            generator.registerParentedItemModel(VABlocks.WAXED_EXPOSED_STEEL_DOOR, Registries.ITEM.getId(VAItems.EXPOSED_STEEL_DOOR).withPrefixedPath("item/"));
            generator.registerParentedItemModel(VABlocks.WAXED_WEATHERED_STEEL_DOOR, Registries.ITEM.getId(VAItems.WEATHERED_STEEL_DOOR).withPrefixedPath("item/"));
            generator.registerParentedItemModel(VABlocks.WAXED_OXIDIZED_STEEL_DOOR, Registries.ITEM.getId(VAItems.OXIDIZED_STEEL_DOOR).withPrefixedPath("item/"));

            generator.registerParentedItemModel(VABlocks.STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            generator.registerParentedItemModel(VABlocks.EXPOSED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.EXPOSED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            generator.registerParentedItemModel(VABlocks.WEATHERED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.WEATHERED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            generator.registerParentedItemModel(VABlocks.OXIDIZED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.OXIDIZED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            generator.registerParentedItemModel(VABlocks.WAXED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            generator.registerParentedItemModel(VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.EXPOSED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            generator.registerParentedItemModel(VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.WEATHERED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));
            generator.registerParentedItemModel(VABlocks.WAXED_OXIDIZED_STEEL_TRAPDOOR, Registries.BLOCK.getId(VABlocks.OXIDIZED_STEEL_TRAPDOOR).withPrefixedPath("block/").withSuffixedPath("_bottom"));

            generator.registerParentedItemModel(VABlocks.REDSTONE_BRIDGE, Registries.BLOCK.getId(VABlocks.REDSTONE_BRIDGE).withPrefixedPath("block/").withSuffixedPath("_inventory"));

            generator.registerItemModel(VABlocks.ROCK_SALT_CRYSTAL, "_tip");

            generator.registerParentedItemModel(VABlocks.SPRING_LOTUS, Registries.BLOCK.getId(VABlocks.SPRING_LOTUS).withPrefixedPath("block/").withSuffixedPath("_3"));

            generator.registerItemModel(VABlocks.FRAYED_SILK);
            generator.registerItemModel(VABlocks.GREENCAP_MUSHROOM);
            generator.registerItemModel(VABlocks.TALL_GREENCAP_MUSHROOMS, "_top");
            generator.registerItemModel(VABlocks.GLOWING_SILK);

            generator.registerItemModel(VAItems.SOULBLOOM_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED.registerItemModel(generator, VABlocks.SOULBLOOM_SAPLING));
            generator.registerItemModel(VAItems.WITHERED_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED.registerItemModel(generator, VABlocks.WITHERED_SAPLING));

            generator.registerItemModel(VAItems.NECROTIC_ROOTS, BlockStateModelGenerator.CrossType.NOT_TINTED.registerItemModel(generator, VABlocks.NECROTIC_ROOTS));

            generator.registerItemModel(VAItems.SMALL_SPRING_LOTUS, BlockStateModelGenerator.CrossType.NOT_TINTED.registerItemModel(generator, VABlocks.SMALL_SPRING_LOTUS));
            generator.registerItemModel(VAItems.SOUL_SPROUT, BlockStateModelGenerator.CrossType.EMISSIVE_NOT_TINTED.registerItemModel(generator, VABlocks.SOUL_SPROUT));

            generator.registerMirrorable(VABlocks.ROCK_SALT_BLOCK);
            generator.registerSingleton(VABlocks.CHISELED_ROCK_SALT_BRICKS, TexturedModel.CUBE_COLUMN);

            generator.registerCrop(VABlocks.TOMATO, CropBlock.AGE, 0, 1, 2, 3, 4, 5, 6, 7);
            generator.registerCrop(VABlocks.WISDOM_BERRY, CropBlock.AGE, 0, 1, 1, 1, 2, 2, 2, 3);

            generator.registerLeafLitter(VABlocks.BONE_LITTER);
            generator.registerTintableCross(VABlocks.BONE_PILE, BlockStateModelGenerator.CrossType.NOT_TINTED);

            registerColorfulBlockSetModels( generator,VACollections.CHARTREUSE);
            registerColorfulBlockSetModels( generator,VACollections.MAROON);
            registerColorfulBlockSetModels( generator,VACollections.INDIGO);
            registerColorfulBlockSetModels( generator,VACollections.PLUM);
            registerColorfulBlockSetModels( generator,VACollections.VIRIDIAN);
            registerColorfulBlockSetModels( generator,VACollections.TAN);
            registerColorfulBlockSetModels( generator,VACollections.SINOPIA);
            registerColorfulBlockSetModels( generator,VACollections.LILAC);

            registerInventoryBlockItem(generator, VABlocks.OAK_HEDGE, new ConstantTintSource(FoliageColors.DEFAULT));
            registerInventoryBlockItem(generator, VABlocks.SPRUCE_HEDGE, new ConstantTintSource(FoliageColors.SPRUCE));
            registerInventoryBlockItem(generator, VABlocks.BIRCH_HEDGE, new ConstantTintSource(FoliageColors.BIRCH));
            registerInventoryBlockItem(generator, VABlocks.JUNGLE_HEDGE, new ConstantTintSource(FoliageColors.DEFAULT));
            registerInventoryBlockItem(generator, VABlocks.ACACIA_HEDGE, new ConstantTintSource(FoliageColors.DEFAULT));
            registerInventoryBlockItem(generator, VABlocks.DARK_OAK_HEDGE, new ConstantTintSource(FoliageColors.DEFAULT));
            registerInventoryBlockItem(generator, VABlocks.PALE_OAK_HEDGE);
            registerInventoryBlockItem(generator, VABlocks.MANGROVE_HEDGE, new ConstantTintSource(FoliageColors.MANGROVE));
            registerInventoryBlockItem(generator, VABlocks.CHERRY_HEDGE);
            registerInventoryBlockItem(generator, VABlocks.SOULBLOOM_HEDGE);
            registerInventoryBlockItem(generator, VABlocks.WITHERED_HEDGE);
            registerInventoryBlockItem(generator, VABlocks.AZALEA_HEDGE);
            registerInventoryBlockItem(generator, VABlocks.FLOWERING_AZALEA_HEDGE);

            registerColoringStation(generator);
            registerSpotlight(generator);

            generator.registerFlowerPotPlant(VABlocks.SOULBLOOM_SAPLING, VABlocks.POTTED_SOULBLOOM_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
            generator.registerFlowerPotPlant(VABlocks.WITHERED_SAPLING, VABlocks.POTTED_WITHERED_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
            generator.registerFlowerPotPlant(VABlocks.GREENCAP_MUSHROOM, VABlocks.POTTED_GREENCAP_MUSHROOM, BlockStateModelGenerator.CrossType.NOT_TINTED);
            generator.registerFlowerPotPlant(VABlocks.NECROTIC_ROOTS, VABlocks.POTTED_NECROTIC_ROOTS, BlockStateModelGenerator.CrossType.NOT_TINTED);
            generator.registerFlowerPotPlant(VABlocks.SMALL_SPRING_LOTUS, VABlocks.POTTED_SMALL_SPRING_LOTUS, BlockStateModelGenerator.CrossType.NOT_TINTED);
            generator.registerFlowerPotPlant(VABlocks.SOUL_SPROUT, VABlocks.POTTED_SOUL_SPROUT, BlockStateModelGenerator.CrossType.EMISSIVE_NOT_TINTED);

            generator.registerParented(VABlocks.STEEL_GRATE, VABlocks.WAXED_STEEL_GRATE);
            generator.registerParented(VABlocks.EXPOSED_STEEL_GRATE, VABlocks.WAXED_EXPOSED_STEEL_GRATE);
            generator.registerParented(VABlocks.WEATHERED_STEEL_GRATE, VABlocks.WAXED_WEATHERED_STEEL_GRATE);
            generator.registerParented(VABlocks.OXIDIZED_STEEL_GRATE, VABlocks.WAXED_OXIDIZED_STEEL_GRATE);
            generator.registerParented(VABlocks.STEEL_BLOCK, VABlocks.WAXED_STEEL_BLOCK);
            generator.registerParented(VABlocks.EXPOSED_STEEL_BLOCK, VABlocks.WAXED_EXPOSED_STEEL_BLOCK);
            generator.registerParented(VABlocks.WEATHERED_STEEL_BLOCK, VABlocks.WAXED_WEATHERED_STEEL_BLOCK);
            generator.registerParented(VABlocks.OXIDIZED_STEEL_BLOCK, VABlocks.WAXED_OXIDIZED_STEEL_BLOCK);

            generator.registerParented(VABlocks.STEEL_BLOCK, VABlocks.REMOTE_NOTIFIER);

            generator.registerBuiltinWithParticle(VABlocks.MINI_PORTAL, VABlocks.IOLITE_BLOCK);
        }

        @Override
        public void generateItemModels(ItemModelGenerator generator) {
            generateGildedToolItemModels(generator, VAItems.AMETHYST_TOOL_SETS);
            generateGildedToolItemModels(generator, VAItems.COPPER_TOOL_SETS);
            generateGildedToolItemModels(generator, VAItems.EMERALD_TOOL_SETS);
            generateGildedToolItemModels(generator, VAItems.IOLITE_TOOL_SETS);
            generateGildedToolItemModels(generator, VAItems.QUARTZ_TOOL_SETS);
            generateGildedToolItemModels(generator, VAItems.SCULK_TOOL_SETS);

            registerSimpleItems(generator,
                    VAItems.CHARTREUSE_DYE,
                    VAItems.MAROON_DYE,
                    VAItems.INDIGO_DYE,
                    VAItems.PLUM_DYE,
                    VAItems.VIRIDIAN_DYE,
                    VAItems.TAN_DYE,
                    VAItems.SINOPIA_DYE,
                    VAItems.LILAC_DYE,
                    VAItems.ROCK_SALT,
                    VAItems.RAW_STEEL,
                    VAItems.STEEL_INGOT,
                    VAItems.STEEL_NUGGET,
                    VAItems.TOOL_GILD_SMITHING_TEMPLATE,
                    VAItems.STEEL_BOMB,
                    VAItems.IOLITE,
                    VAItems.DRAINED_PORTAL_CORE,
                    VAItems.TOMATO,
                    VAItems.TOMATO_SOUP,
                    VAItems.CABBAGE,
                    VAItems.CORN,
                    VAItems.ROASTED_CORN,
                    VAItems.SALAD,
                    VAItems.WISDOM_BERRY,
                    VAItems.COTTON,
                    VAItems.FRIED_EGG,
                    VAItems.CHEESE_WEDGE,
                    VAItems.BEEF_JERKY,
                    VAItems.PORK_JERKY,
                    VAItems.CHICKEN_JERKY,
                    VAItems.MUTTON_JERKY,
                    VAItems.SWEET_BERRY_PIE,
                    VAItems.ACID_BUCKET,
                    VAItems.SILK_THREAD,
                    VAItems.LUMWASP_MANDIBLE,
                    VAItems.LIGHTNING_BOTTLE,
                    VAItems.SALINE_SPAWN_EGG,
                    VAItems.LUMWASP_SPAWN_EGG,
                    VAItems.SPECTRE_SPAWN_EGG,
                    VAItems.STEEL_HORSE_ARMOR,
                    VAItems.SOULBLOOM_BOAT,
                    VAItems.SOULBLOOM_CHEST_BOAT,
                    VAItems.SPECTRAL_POWDER,
                    VAItems.CHARTREUSE_HARNESS,
                    VAItems.MAROON_HARNESS,
                    VAItems.INDIGO_HARNESS,
                    VAItems.PLUM_HARNESS,
                    VAItems.VIRIDIAN_HARNESS,
                    VAItems.TAN_HARNESS,
                    VAItems.SINOPIA_HARNESS,
                    VAItems.LILAC_HARNESS,
                    VAItems.PURPLE_EGG,
                    VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE,
                    VAItems.ROBE_ARMOR_TRIM_SMITHING_TEMPLATE
            );

            registerItems(generator, Models.HANDHELD,
                    VAItems.STEEL_SWORD,
                    VAItems.STEEL_SHOVEL,
                    VAItems.STEEL_PICKAXE,
                    VAItems.STEEL_AXE,
                    VAItems.STEEL_HOE
            );

            registerHalberd(generator, VAItems.WOODEN_HALBERD);
            registerHalberd(generator, VAItems.STONE_HALBERD);
            registerHalberd(generator, VAItems.COPPER_HALBERD);
            registerHalberd(generator, VAItems.IRON_HALBERD);
            registerHalberd(generator, VAItems.GOLDEN_HALBERD);
            registerHalberd(generator, VAItems.STEEL_HALBERD);
            registerHalberd(generator, VAItems.DIAMOND_HALBERD);
            registerHalberd(generator, VAItems.NETHERITE_HALBERD);

            registerPortalCore(generator, VAItems.PORTAL_CORE);

            generator.registerArmor(VAItems.STEEL_HELMET, VAArmorMaterial.STEEL.assetId(), HELMET_TRIM_ID_PREFIX, false);
            generator.registerArmor(VAItems.STEEL_CHESTPLATE, VAArmorMaterial.STEEL.assetId(), CHESTPLATE_TRIM_ID_PREFIX, false);
            generator.registerArmor(VAItems.STEEL_LEGGINGS, VAArmorMaterial.STEEL.assetId(), LEGGINGS_TRIM_ID_PREFIX, false);
            generator.registerArmor(VAItems.STEEL_BOOTS, VAArmorMaterial.STEEL.assetId(), BOOTS_TRIM_ID_PREFIX, false);

            generator.registerBundle(VAItems.CHARTREUSE_BUNDLE);
            generator.registerBundle(VAItems.MAROON_BUNDLE);
            generator.registerBundle(VAItems.INDIGO_BUNDLE);
            generator.registerBundle(VAItems.PLUM_BUNDLE);
            generator.registerBundle(VAItems.VIRIDIAN_BUNDLE);
            generator.registerBundle(VAItems.TAN_BUNDLE);
            generator.registerBundle(VAItems.SINOPIA_BUNDLE);
            generator.registerBundle(VAItems.LILAC_BUNDLE);

            registerTintableWithDefaultTexture(generator, VAItems.ENGRAVING_CHISEL);
            registerTintableWithDefaultTexture(generator, VAItems.ICE_CREAM);

            registerApplicablePotion(generator, VAItems.APPLICABLE_POTION);

            generator.registerWithInHandModel(VAItems.SPECTRAL_SPYGLASS);
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
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.COPPER_HELMET,
                    Items.COPPER_CHESTPLATE,
                    Items.COPPER_LEGGINGS,
                    Items.COPPER_BOOTS,
                    EquipmentAssetKeys.COPPER, false);

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
                        new ItemModelGenerator.TrimMaterial(VAArmorTrimAssets.STEEL, VAArmorTrimMaterials.STEEL),
                        new ItemModelGenerator.TrimMaterial(VAArmorTrimAssets.ROCK_SALT, VAArmorTrimMaterials.ROCK_SALT),
                        new ItemModelGenerator.TrimMaterial(VAArmorTrimAssets.IOLITE, VAArmorTrimMaterials.IOLITE)
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
            if (s.silkbulb() != null) {
                g.registerSimpleCubeAll(s.silkbulb());
            };
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
            uploadGildedHalberdModels(itemModelGenerator, set.HALBERD());
        }

        public static void uploadGildedToolModel(ItemModelGenerator itemModelGenerator, Item item, String suffix) {
            if (item instanceof GildedToolItem gildedToolItem) {
            Item baseItem = gildedToolItem.getBaseItem();
            Identifier base = ModelIds.getItemModelId(baseItem);
            Identifier gild = gildedToolItem.getGildType().getId().withSuffixedPath(suffix).withPrefixedPath("item/gilded_tools/");
            Identifier id = ModelIds.getItemModelId(item);
            VAModels.HANDHELD_TWO_LAYERS.upload(id, TextureMap.layered(base, gild), itemModelGenerator.modelCollector);
            itemModelGenerator.output.accept(item, ItemModels.basic(id));
            }
        }

        public static void uploadGildedHalberdModels(ItemModelGenerator itemModelGenerator, Item item) {
            if (item instanceof GildedToolItem gildedToolItem) {
                Identifier gild = gildedToolItem.getGildType().getId().withSuffixedPath("_halberd").withPrefixedPath("item/gilded_tools/");
                registerLayeredHalberd(itemModelGenerator, item, gildedToolItem.getBaseItem(), gild);
            }
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

        public static void registerParentedTintedItemModel(BlockStateModelGenerator generator, Block block, Identifier parentModelId, TintSource... sources) {
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

        public final void registerPortalCore(ItemModelGenerator generator, Item item) {
            ItemModel.Unbaked base = ItemModels.basic(ModelIds.getItemModelId(item));
            Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(TextureMap.getId(item)), generator.modelCollector);
            ItemModel.Unbaked active = ItemModels.basic(generator.registerSubModel(item, "_active", Models.GENERATED));
            generator.output.accept(item, ItemModels.condition(
                    ItemModels.hasComponentProperty(VADataComponentTypes.PORTAL_CORE_LOCATION), active, base
            ));
        }

        public static void registerHalberd(ItemModelGenerator generator, Item item) {
            ItemModel.Unbaked base = ItemModels.basic(ModelIds.getItemModelId(item));
            ItemModel.Unbaked inHand = ItemModels.basic(ModelIds.getItemSubModelId(item, "_in_hand"));
            Models.HANDHELD.upload(ModelIds.getItemModelId(item), TextureMap.layer0(TextureMap.getId(item)), generator.modelCollector);
            VAModels.HALBERD_IN_HAND.upload(ModelIds.getItemSubModelId(item, "_in_hand"), TextureMap.layer0(TextureMap.getSubId(item, "_in_hand")), generator.modelCollector);
            generator.output.accept(item, createModelWithInHandVariant(base, inHand));
        }

        public static void registerLayeredHalberd(ItemModelGenerator generator, Item item, Item baseItem, Identifier layer) {
            ItemModel.Unbaked base = ItemModels.basic(ModelIds.getItemModelId(item));
            ItemModel.Unbaked inHand = ItemModels.basic(ModelIds.getItemSubModelId(item, "_in_hand"));
            VAModels.HANDHELD_TWO_LAYERS.upload(ModelIds.getItemModelId(item), TextureMap.layered(TextureMap.getId(baseItem), layer), generator.modelCollector);
            VAModels.HALBERD_IN_HAND_TWO_LAYERS.upload(ModelIds.getItemSubModelId(item, "_in_hand"), TextureMap.layered(TextureMap.getSubId(baseItem, "_in_hand"), layer.withSuffixedPath("_in_hand")), generator.modelCollector);
            generator.output.accept(item, createModelWithInHandVariant(base, inHand));
        }

        protected void registerSpectralFire(BlockStateModelGenerator generator) {
            WeightedVariant weightedVariant = generator.getFireFloorModels(VABlocks.SPECTRAL_FIRE);
            WeightedVariant weightedVariant2 = generator.getFireSideModels(VABlocks.SPECTRAL_FIRE);
            generator.blockStateCollector
                    .accept(
                            MultipartBlockModelDefinitionCreator.create(VABlocks.SPECTRAL_FIRE)
                                    .with(weightedVariant)
                                    .with(weightedVariant2)
                                    .with(weightedVariant2.apply(BlockStateModelGenerator.ROTATE_Y_90))
                                    .with(weightedVariant2.apply(BlockStateModelGenerator.ROTATE_Y_180))
                                    .with(weightedVariant2.apply(BlockStateModelGenerator.ROTATE_Y_270))
                    );
        }

        protected static void registerSimpleBlockItem(BlockStateModelGenerator generator, Block block) {
            generator.registerParentedItemModel(block, Registries.BLOCK.getId(block).withPrefixedPath("block/"));
        }

        protected static void registerInventoryBlockItem(BlockStateModelGenerator generator, Block block) {
            generator.registerParentedItemModel(block, Registries.BLOCK.getId(block).withPrefixedPath("block/").withSuffixedPath("_inventory"));
        }

        protected static void registerInventoryBlockItem(BlockStateModelGenerator generator, Block block, TintSource... sources) {
            registerParentedTintedItemModel(generator, block, Registries.BLOCK.getId(block).withPrefixedPath("block/").withSuffixedPath("_inventory"), sources);
        }
        
        protected static void registerSimpleBlockItems(BlockStateModelGenerator generator, Block... blocks) {
            for (Block block : blocks) {
                registerSimpleBlockItem(generator, block);
            }
        }
        
        protected static void registerSimpleCubeAll(BlockStateModelGenerator generator, Block... blocks) {
            for (Block block : blocks) {
                generator.registerSimpleCubeAll(block);
            }
        }

        protected static void registerSimpleItems(ItemModelGenerator generator, Item... items) {
            for (Item item : items) {
                generator.register(item, Models.GENERATED);
            }
        }

        protected static void registerItems(ItemModelGenerator generator, Model model, Item... items) {
            for (Item item : items) {
                generator.register(item, model);
            }
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
