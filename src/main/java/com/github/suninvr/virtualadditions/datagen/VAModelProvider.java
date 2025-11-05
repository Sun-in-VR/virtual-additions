package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.block.SpotlightBlock;
import com.github.suninvr.virtualadditions.client.render.item.CrossbowProjectileTypeProperty;
import com.github.suninvr.virtualadditions.client.render.item.GildTypeProperty;
import com.github.suninvr.virtualadditions.datagen.registry.VAModels;
import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.*;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.conditional.HasComponent;
import net.minecraft.client.renderer.item.properties.numeric.CrossbowPull;
import net.minecraft.client.renderer.item.properties.select.Charge;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;
import static net.minecraft.client.data.models.ItemModelGenerators.*;

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
        public void generateBlockStateModels(BlockModelGenerators generator) {

            generator.family(VABlocks.PORPHYRY).generateFor(VACollections.PORPHYRY);
            generator.family(VABlocks.SOULBLOOM_PLANKS).generateFor(VACollections.SOULBLOOM);
            generator.family(VABlocks.WITHERED_PLANKS).generateFor(VACollections.WITHERED);
            generator.family(VABlocks.POLISHED_PORPHYRY).generateFor(VACollections.POLISHED_PORPHYRY);
            generator.family(VABlocks.PORPHYRY_BRICKS).generateFor(VACollections.PORPHYRY_BRICKS);
            generator.family(VABlocks.CUT_STEEL).generateFor(VACollections.CUT_STEEL).donateModelTo(VABlocks.CUT_STEEL, VABlocks.WAXED_CUT_STEEL).donateModelTo(VABlocks.CHISELED_STEEL, VABlocks.WAXED_CHISELED_STEEL).generateFor(VACollections.WAXED_CUT_STEEL);
            generator.family(VABlocks.EXPOSED_CUT_STEEL).generateFor(VACollections.EXPOSED_CUT_STEEL).donateModelTo(VABlocks.EXPOSED_CUT_STEEL, VABlocks.WAXED_EXPOSED_CUT_STEEL).donateModelTo(VABlocks.EXPOSED_CHISELED_STEEL, VABlocks.WAXED_EXPOSED_CHISELED_STEEL).generateFor(VACollections.WAXED_EXPOSED_CUT_STEEL);
            generator.family(VABlocks.WEATHERED_CUT_STEEL).generateFor(VACollections.WEATHERED_CUT_STEEL).donateModelTo(VABlocks.WEATHERED_CUT_STEEL, VABlocks.WAXED_WEATHERED_CUT_STEEL).donateModelTo(VABlocks.WEATHERED_CHISELED_STEEL, VABlocks.WAXED_WEATHERED_CHISELED_STEEL).generateFor(VACollections.WAXED_WEATHERED_CUT_STEEL);
            generator.family(VABlocks.OXIDIZED_CUT_STEEL).generateFor(VACollections.OXIDIZED_CUT_STEEL).donateModelTo(VABlocks.OXIDIZED_CUT_STEEL, VABlocks.WAXED_OXIDIZED_CUT_STEEL).donateModelTo(VABlocks.OXIDIZED_CHISELED_STEEL, VABlocks.WAXED_OXIDIZED_CHISELED_STEEL).generateFor(VACollections.WAXED_OXIDIZED_CUT_STEEL);
            generator.family(VABlocks.ROCK_SALT_BRICKS).generateFor(VACollections.ROCK_SALT_BRICKS);
            generator.family(VABlocks.COBBLED_HORNFELS).generateFor(VACollections.COBBLED_HORNFELS);
            generator.family(VABlocks.COBBLED_BLUESCHIST).generateFor(VACollections.COBBLED_BLUESCHIST);
            generator.family(VABlocks.BLUESCHIST_BRICKS).generateFor(VACollections.BLUESCHIST_BRICKS);
            generator.family(VABlocks.POLISHED_BLUESCHIST).generateFor(VACollections.POLISHED_BLUESCHIST);
            generator.family(VABlocks.COBBLED_SYENITE).generateFor(VACollections.COBBLED_SYENITE);
            generator.family(VABlocks.SYENITE_BRICKS).generateFor(VACollections.SYENITE_BRICKS);
            generator.family(VABlocks.POLISHED_SYENITE).generateFor(VACollections.POLISHED_SYENITE);

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

            generator.woodProvider(VABlocks.SOULBLOOM_LOG).logWithHorizontal(VABlocks.SOULBLOOM_LOG).wood(VABlocks.SOULBLOOM_WOOD);
            generator.woodProvider(VABlocks.STRIPPED_SOULBLOOM_LOG).logWithHorizontal(VABlocks.STRIPPED_SOULBLOOM_LOG).wood(VABlocks.STRIPPED_SOULBLOOM_WOOD);
            generator.createHangingSign(VABlocks.STRIPPED_SOULBLOOM_LOG, VABlocks.SOULBLOOM_HANGING_SIGN, VABlocks.SOULBLOOM_WALL_HANGING_SIGN);
            generator.createShelf(VABlocks.SOULBLOOM_SHELF, VABlocks.STRIPPED_SOULBLOOM_LOG);

            generator.createNyliumBlock(VABlocks.NECROTIC_NYLIUM);
            generator.woodProvider(VABlocks.WITHERED_LOG).logWithHorizontal(VABlocks.WITHERED_LOG).wood(VABlocks.WITHERED_WOOD);
            generator.woodProvider(VABlocks.STRIPPED_WITHERED_LOG).logWithHorizontal(VABlocks.STRIPPED_WITHERED_LOG).wood(VABlocks.STRIPPED_WITHERED_WOOD);
            generator.createHangingSign(VABlocks.STRIPPED_WITHERED_LOG, VABlocks.WITHERED_HANGING_SIGN, VABlocks.WITHERED_WALL_HANGING_SIGN);
            generator.createShelf(VABlocks.WITHERED_SHELF, VABlocks.STRIPPED_WITHERED_LOG);

            registerSpectralFire(generator);
            generator.createNormalTorch(VABlocks.SPECTRAL_TORCH, VABlocks.SPECTRAL_WALL_TORCH);
            generator.createLantern(VABlocks.SPECTRAL_LANTERN);

            generator.createFlowerBed(VABlocks.BLUE_PETALS);

            generator.registerSimpleFlatItemModel(VAItems.CABBAGE_SEEDS);
            generator.registerSimpleFlatItemModel(VAItems.CORN_SEEDS);
            generator.registerSimpleFlatItemModel(VAItems.COTTON_SEEDS);
            generator.registerSimpleFlatItemModel(VAItems.BALLOON_FRUIT);

            generator.registerSimpleFlatItemModel(VAItems.CLIMBING_ROPE);
            generator.registerSimpleFlatItemModel(VAItems.EXPOSED_CLIMBING_ROPE);
            generator.registerSimpleFlatItemModel(VAItems.WEATHERED_CLIMBING_ROPE);
            generator.registerSimpleFlatItemModel(VAItems.OXIDIZED_CLIMBING_ROPE);
            generator.registerSimpleItemModel(VABlocks.WAXED_CLIMBING_ROPE_ANCHOR, BuiltInRegistries.ITEM.getKey(VAItems.CLIMBING_ROPE).withPrefix("item/"));
            generator.registerSimpleItemModel(VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR, BuiltInRegistries.ITEM.getKey(VAItems.EXPOSED_CLIMBING_ROPE).withPrefix("item/"));
            generator.registerSimpleItemModel(VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR, BuiltInRegistries.ITEM.getKey(VAItems.WEATHERED_CLIMBING_ROPE).withPrefix("item/"));
            generator.registerSimpleItemModel(VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR, BuiltInRegistries.ITEM.getKey(VAItems.OXIDIZED_CLIMBING_ROPE).withPrefix("item/"));
            generator.registerSimpleFlatItemModel(VAItems.STEEL_DOOR);
            generator.registerSimpleFlatItemModel(VAItems.EXPOSED_STEEL_DOOR);
            generator.registerSimpleFlatItemModel(VAItems.WEATHERED_STEEL_DOOR);
            generator.registerSimpleFlatItemModel(VAItems.OXIDIZED_STEEL_DOOR);
            generator.registerSimpleItemModel(VABlocks.WAXED_STEEL_DOOR, BuiltInRegistries.ITEM.getKey(VAItems.STEEL_DOOR).withPrefix("item/"));
            generator.registerSimpleItemModel(VABlocks.WAXED_EXPOSED_STEEL_DOOR, BuiltInRegistries.ITEM.getKey(VAItems.EXPOSED_STEEL_DOOR).withPrefix("item/"));
            generator.registerSimpleItemModel(VABlocks.WAXED_WEATHERED_STEEL_DOOR, BuiltInRegistries.ITEM.getKey(VAItems.WEATHERED_STEEL_DOOR).withPrefix("item/"));
            generator.registerSimpleItemModel(VABlocks.WAXED_OXIDIZED_STEEL_DOOR, BuiltInRegistries.ITEM.getKey(VAItems.OXIDIZED_STEEL_DOOR).withPrefix("item/"));

            generator.registerSimpleItemModel(VABlocks.STEEL_TRAPDOOR, BuiltInRegistries.BLOCK.getKey(VABlocks.STEEL_TRAPDOOR).withPrefix("block/").withSuffix("_bottom"));
            generator.registerSimpleItemModel(VABlocks.EXPOSED_STEEL_TRAPDOOR, BuiltInRegistries.BLOCK.getKey(VABlocks.EXPOSED_STEEL_TRAPDOOR).withPrefix("block/").withSuffix("_bottom"));
            generator.registerSimpleItemModel(VABlocks.WEATHERED_STEEL_TRAPDOOR, BuiltInRegistries.BLOCK.getKey(VABlocks.WEATHERED_STEEL_TRAPDOOR).withPrefix("block/").withSuffix("_bottom"));
            generator.registerSimpleItemModel(VABlocks.OXIDIZED_STEEL_TRAPDOOR, BuiltInRegistries.BLOCK.getKey(VABlocks.OXIDIZED_STEEL_TRAPDOOR).withPrefix("block/").withSuffix("_bottom"));
            generator.registerSimpleItemModel(VABlocks.WAXED_STEEL_TRAPDOOR, BuiltInRegistries.BLOCK.getKey(VABlocks.STEEL_TRAPDOOR).withPrefix("block/").withSuffix("_bottom"));
            generator.registerSimpleItemModel(VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR, BuiltInRegistries.BLOCK.getKey(VABlocks.EXPOSED_STEEL_TRAPDOOR).withPrefix("block/").withSuffix("_bottom"));
            generator.registerSimpleItemModel(VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR, BuiltInRegistries.BLOCK.getKey(VABlocks.WEATHERED_STEEL_TRAPDOOR).withPrefix("block/").withSuffix("_bottom"));
            generator.registerSimpleItemModel(VABlocks.WAXED_OXIDIZED_STEEL_TRAPDOOR, BuiltInRegistries.BLOCK.getKey(VABlocks.OXIDIZED_STEEL_TRAPDOOR).withPrefix("block/").withSuffix("_bottom"));

            generator.registerSimpleItemModel(VABlocks.REDSTONE_BRIDGE, BuiltInRegistries.BLOCK.getKey(VABlocks.REDSTONE_BRIDGE).withPrefix("block/").withSuffix("_inventory"));

            generator.registerSimpleFlatItemModel(VABlocks.ROCK_SALT_CRYSTAL, "_tip");

            generator.registerSimpleItemModel(VABlocks.SPRING_LOTUS, BuiltInRegistries.BLOCK.getKey(VABlocks.SPRING_LOTUS).withPrefix("block/").withSuffix("_3"));

            generator.registerSimpleFlatItemModel(VABlocks.FRAYED_SILK);
            generator.registerSimpleFlatItemModel(VABlocks.GREENCAP_MUSHROOM);
            generator.registerSimpleFlatItemModel(VABlocks.TALL_GREENCAP_MUSHROOMS, "_top");
            generator.registerSimpleFlatItemModel(VABlocks.GLOWING_SILK);

            generator.registerSimpleItemModel(VAItems.SOULBLOOM_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED.createItemModel(generator, VABlocks.SOULBLOOM_SAPLING));
            generator.registerSimpleItemModel(VAItems.WITHERED_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED.createItemModel(generator, VABlocks.WITHERED_SAPLING));

            generator.registerSimpleItemModel(VAItems.NECROTIC_ROOTS, BlockModelGenerators.PlantType.NOT_TINTED.createItemModel(generator, VABlocks.NECROTIC_ROOTS));

            generator.registerSimpleItemModel(VAItems.SMALL_SPRING_LOTUS, BlockModelGenerators.PlantType.NOT_TINTED.createItemModel(generator, VABlocks.SMALL_SPRING_LOTUS));
            generator.registerSimpleItemModel(VAItems.SOUL_SPROUT, BlockModelGenerators.PlantType.EMISSIVE_NOT_TINTED.createItemModel(generator, VABlocks.SOUL_SPROUT));

            generator.createRotatedMirroredVariantBlock(VABlocks.ROCK_SALT_BLOCK);
            generator.createTrivialBlock(VABlocks.CHISELED_ROCK_SALT_BRICKS, TexturedModel.COLUMN);

            generator.createCropBlock(VABlocks.TOMATO, CropBlock.AGE, 0, 1, 2, 3, 4, 5, 6, 7);
            generator.createCropBlock(VABlocks.WISDOM_BERRY, CropBlock.AGE, 0, 1, 1, 1, 2, 2, 2, 3);

            generator.createLeafLitter(VABlocks.BONE_LITTER);
            generator.createCrossBlockWithDefaultItem(VABlocks.BONE_PILE, BlockModelGenerators.PlantType.NOT_TINTED);

            registerColorfulBlockSetModels( generator,VACollections.CHARTREUSE);
            registerColorfulBlockSetModels( generator,VACollections.MAROON);
            registerColorfulBlockSetModels( generator,VACollections.INDIGO);
            registerColorfulBlockSetModels( generator,VACollections.PLUM);
            registerColorfulBlockSetModels( generator,VACollections.VIRIDIAN);
            registerColorfulBlockSetModels( generator,VACollections.TAN);
            registerColorfulBlockSetModels( generator,VACollections.SINOPIA);
            registerColorfulBlockSetModels( generator,VACollections.LILAC);

            registerInventoryBlockItem(generator, VABlocks.OAK_HEDGE, new Constant(FoliageColor.FOLIAGE_DEFAULT));
            registerInventoryBlockItem(generator, VABlocks.SPRUCE_HEDGE, new Constant(FoliageColor.FOLIAGE_EVERGREEN));
            registerInventoryBlockItem(generator, VABlocks.BIRCH_HEDGE, new Constant(FoliageColor.FOLIAGE_BIRCH));
            registerInventoryBlockItem(generator, VABlocks.JUNGLE_HEDGE, new Constant(FoliageColor.FOLIAGE_DEFAULT));
            registerInventoryBlockItem(generator, VABlocks.ACACIA_HEDGE, new Constant(FoliageColor.FOLIAGE_DEFAULT));
            registerInventoryBlockItem(generator, VABlocks.DARK_OAK_HEDGE, new Constant(FoliageColor.FOLIAGE_DEFAULT));
            registerInventoryBlockItem(generator, VABlocks.PALE_OAK_HEDGE);
            registerInventoryBlockItem(generator, VABlocks.MANGROVE_HEDGE, new Constant(FoliageColor.FOLIAGE_MANGROVE));
            registerInventoryBlockItem(generator, VABlocks.CHERRY_HEDGE);
            registerInventoryBlockItem(generator, VABlocks.SOULBLOOM_HEDGE);
            registerInventoryBlockItem(generator, VABlocks.WITHERED_HEDGE);
            registerInventoryBlockItem(generator, VABlocks.AZALEA_HEDGE);
            registerInventoryBlockItem(generator, VABlocks.FLOWERING_AZALEA_HEDGE);

            registerColoringStation(generator);
            registerSpotlight(generator);

            generator.createPlant(VABlocks.SOULBLOOM_SAPLING, VABlocks.POTTED_SOULBLOOM_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
            generator.createPlant(VABlocks.WITHERED_SAPLING, VABlocks.POTTED_WITHERED_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
            generator.createPlant(VABlocks.GREENCAP_MUSHROOM, VABlocks.POTTED_GREENCAP_MUSHROOM, BlockModelGenerators.PlantType.NOT_TINTED);
            generator.createPlant(VABlocks.NECROTIC_ROOTS, VABlocks.POTTED_NECROTIC_ROOTS, BlockModelGenerators.PlantType.NOT_TINTED);
            generator.createPlant(VABlocks.SMALL_SPRING_LOTUS, VABlocks.POTTED_SMALL_SPRING_LOTUS, BlockModelGenerators.PlantType.NOT_TINTED);
            generator.createPlant(VABlocks.SOUL_SPROUT, VABlocks.POTTED_SOUL_SPROUT, BlockModelGenerators.PlantType.EMISSIVE_NOT_TINTED);

            generator.copyModel(VABlocks.STEEL_GRATE, VABlocks.WAXED_STEEL_GRATE);
            generator.copyModel(VABlocks.EXPOSED_STEEL_GRATE, VABlocks.WAXED_EXPOSED_STEEL_GRATE);
            generator.copyModel(VABlocks.WEATHERED_STEEL_GRATE, VABlocks.WAXED_WEATHERED_STEEL_GRATE);
            generator.copyModel(VABlocks.OXIDIZED_STEEL_GRATE, VABlocks.WAXED_OXIDIZED_STEEL_GRATE);
            generator.copyModel(VABlocks.STEEL_BLOCK, VABlocks.WAXED_STEEL_BLOCK);
            generator.copyModel(VABlocks.EXPOSED_STEEL_BLOCK, VABlocks.WAXED_EXPOSED_STEEL_BLOCK);
            generator.copyModel(VABlocks.WEATHERED_STEEL_BLOCK, VABlocks.WAXED_WEATHERED_STEEL_BLOCK);
            generator.copyModel(VABlocks.OXIDIZED_STEEL_BLOCK, VABlocks.WAXED_OXIDIZED_STEEL_BLOCK);

            generator.copyModel(VABlocks.STEEL_BLOCK, VABlocks.REMOTE_NOTIFIER);

            generator.createParticleOnlyBlock(VABlocks.MINI_PORTAL, VABlocks.IOLITE_BLOCK);
        }

        @Override
        public void generateItemModels(ItemModelGenerators generator) {
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
                    VAItems.STEEL_NAUTILUS_ARMOR,
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

            registerItems(generator, ModelTemplates.FLAT_HANDHELD_ITEM,
                    VAItems.STEEL_SWORD,
                    VAItems.STEEL_SHOVEL,
                    VAItems.STEEL_PICKAXE,
                    VAItems.STEEL_AXE,
                    VAItems.STEEL_HOE
            );

            generator.generateSpear(VAItems.STEEL_SPEAR);

            registerHalberd(generator, VAItems.WOODEN_HALBERD);
            registerHalberd(generator, VAItems.STONE_HALBERD);
            registerHalberd(generator, VAItems.COPPER_HALBERD);
            registerHalberd(generator, VAItems.IRON_HALBERD);
            registerHalberd(generator, VAItems.GOLDEN_HALBERD);
            registerHalberd(generator, VAItems.STEEL_HALBERD);
            registerHalberd(generator, VAItems.DIAMOND_HALBERD);
            registerHalberd(generator, VAItems.NETHERITE_HALBERD);

            registerPortalCore(generator, VAItems.PORTAL_CORE);

            generator.generateTrimmableItem(VAItems.STEEL_HELMET, VAArmorMaterial.STEEL.assetId(), TRIM_PREFIX_HELMET, false);
            generator.generateTrimmableItem(VAItems.STEEL_CHESTPLATE, VAArmorMaterial.STEEL.assetId(), TRIM_PREFIX_CHESTPLATE, false);
            generator.generateTrimmableItem(VAItems.STEEL_LEGGINGS, VAArmorMaterial.STEEL.assetId(), TRIM_PREFIX_LEGGINGS, false);
            generator.generateTrimmableItem(VAItems.STEEL_BOOTS, VAArmorMaterial.STEEL.assetId(), TRIM_PREFIX_BOOTS, false);

            generator.generateBundleModels(VAItems.CHARTREUSE_BUNDLE);
            generator.generateBundleModels(VAItems.MAROON_BUNDLE);
            generator.generateBundleModels(VAItems.INDIGO_BUNDLE);
            generator.generateBundleModels(VAItems.PLUM_BUNDLE);
            generator.generateBundleModels(VAItems.VIRIDIAN_BUNDLE);
            generator.generateBundleModels(VAItems.TAN_BUNDLE);
            generator.generateBundleModels(VAItems.SINOPIA_BUNDLE);
            generator.generateBundleModels(VAItems.LILAC_BUNDLE);

            registerTintableWithDefaultTexture(generator, VAItems.ENGRAVING_CHISEL);
            registerTintableWithDefaultTexture(generator, VAItems.ICE_CREAM);

            registerApplicablePotion(generator, VAItems.APPLICABLE_POTION);

            generator.generateSpyglass(VAItems.SPECTRAL_SPYGLASS);
        }
    }

    static class EnhancementsProvider extends Provider {
        public EnhancementsProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
            haltModelGeneration = true;
        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerator) {
            uploadGildedToolModels(itemModelGenerator, VAItems.COPPER_TOOL_SET, VAItems.IRON_TOOL_SET, VAItems.GOLDEN_TOOL_SET, VAItems.STEEL_TOOL_SET, VAItems.DIAMOND_TOOL_SET, VAItems.NETHERITE_TOOL_SET);

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
                    EquipmentAssets.IRON, false);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.GOLDEN_HELMET,
                    Items.GOLDEN_CHESTPLATE,
                    Items.GOLDEN_LEGGINGS,
                    Items.GOLDEN_BOOTS,
                    EquipmentAssets.GOLD, false);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.DIAMOND_HELMET,
                    Items.DIAMOND_CHESTPLATE,
                    Items.DIAMOND_LEGGINGS,
                    Items.DIAMOND_BOOTS,
                    EquipmentAssets.DIAMOND, false);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.NETHERITE_HELMET,
                    Items.NETHERITE_CHESTPLATE,
                    Items.NETHERITE_LEGGINGS,
                    Items.NETHERITE_BOOTS,
                    EquipmentAssets.NETHERITE, false);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.CHAINMAIL_HELMET,
                    Items.CHAINMAIL_CHESTPLATE,
                    Items.CHAINMAIL_LEGGINGS,
                    Items.CHAINMAIL_BOOTS,
                    EquipmentAssets.CHAINMAIL, false);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.LEATHER_HELMET,
                    Items.LEATHER_CHESTPLATE,
                    Items.LEATHER_LEGGINGS,
                    Items.LEATHER_BOOTS,
                    EquipmentAssets.LEATHER, true);
            registerArmorSetWithExtendedTrimMaterials(itemModelGenerator,
                    Items.COPPER_HELMET,
                    Items.COPPER_CHESTPLATE,
                    Items.COPPER_LEGGINGS,
                    Items.COPPER_BOOTS,
                    EquipmentAssets.COPPER, false);

            registerCrossbow(itemModelGenerator, Items.CROSSBOW);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private abstract static class Provider extends FabricModelProvider {
        protected static final ClientItem.Properties HALBERD_PROPERTIES = new ClientItem.Properties(true, false, 1.7F);
        protected static final ClientItem.Properties SPEAR_PROPERTIES = new ClientItem.Properties(true, false, 1.9F);

        private static final List<ItemModelGenerators.TrimMaterialData> TRIM_MATERIALS_EXTENDED =
                List.of(
                        new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.QUARTZ, TrimMaterials.QUARTZ),
                        new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.IRON, TrimMaterials.IRON),
                        new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.NETHERITE, TrimMaterials.NETHERITE),
                        new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.REDSTONE, TrimMaterials.REDSTONE),
                        new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.COPPER, TrimMaterials.COPPER),
                        new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.GOLD, TrimMaterials.GOLD),
                        new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.EMERALD, TrimMaterials.EMERALD),
                        new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.DIAMOND, TrimMaterials.DIAMOND),
                        new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.LAPIS, TrimMaterials.LAPIS),
                        new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.AMETHYST, TrimMaterials.AMETHYST),
                        new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.RESIN, TrimMaterials.RESIN),
                        new ItemModelGenerators.TrimMaterialData(VAArmorTrimAssets.STEEL, VAArmorTrimMaterials.STEEL),
                        new ItemModelGenerators.TrimMaterialData(VAArmorTrimAssets.ROCK_SALT, VAArmorTrimMaterials.ROCK_SALT),
                        new ItemModelGenerators.TrimMaterialData(VAArmorTrimAssets.IOLITE, VAArmorTrimMaterials.IOLITE)
                );

        private static final List<GildType> GILD_TYPES = VARegistries.GILD_TYPE.stream().toList();

        public Provider(FabricDataOutput output) {
            super(output);
        }

        protected void registerColoringStation(BlockModelGenerators blockStateModelGenerator) {
            TextureMapping textureMap = new TextureMapping()
                    .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(VABlocks.COLORING_STATION, "_front"))
                    .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(VABlocks.COLORING_STATION, "_bottom"))
                    .put(TextureSlot.UP, TextureMapping.getBlockTexture(VABlocks.COLORING_STATION, "_top"))
                    .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(VABlocks.COLORING_STATION, "_front"))
                    .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(VABlocks.COLORING_STATION, "_front"))
                    .put(TextureSlot.EAST, TextureMapping.getBlockTexture(VABlocks.COLORING_STATION, "_side"))
                    .put(TextureSlot.WEST, TextureMapping.getBlockTexture(VABlocks.COLORING_STATION, "_side"));
            blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(VABlocks.COLORING_STATION, BlockModelGenerators.plainVariant(ModelTemplates.CUBE.create(VABlocks.COLORING_STATION, textureMap, blockStateModelGenerator.modelOutput))));
        }

        protected void registerColorfulBlockSetModels(BlockModelGenerators g, ColorfulBlockSet s) {
            s.ifWool(wool -> {
                if (s.carpet() != null) g.createFullAndCarpetBlocks(wool, s.carpet());
                else g.createTrivialCube(s.wool());
                s.ifBed(bed -> g.createBed(bed, wool, s.dye().getDyeColor()));
            });
            if (s.terracotta() != null) g.createTrivialCube(s.terracotta());
            if (s.concrete() != null) g.createTrivialCube(s.concrete());
            if (s.concretePowder() != null) g.createColoredBlockWithRandomRotations(TexturedModel.CUBE,s.concretePowder());
            if (s.stainedGlass() != null) {
                if (s.stainedGlassPane() != null) g.createGlassBlocks(s.stainedGlass(), s.stainedGlassPane());
                else g.createTrivialCube(s.stainedGlass());
            }
            if (s.silkbulb() != null) {
                g.createTrivialCube(s.silkbulb());
            };
            if (s.candle() != null && s.candleCake() != null) g.createCandleAndCandleCake(s.candle(), s.candleCake());
            s.ifShulkerBox(shulkerbox -> g.createShulkerBox(shulkerbox, s.dye().getDyeColor()));
            s.ifGlazedTerracotta(block -> g.createColoredBlockWithStateRotations(TexturedModel.GLAZED_TERRACOTTA, block));
            s.ifBanner(banner -> s.ifWallBanner(wallBanner -> g.createBanner(banner, wallBanner, s.dye().getDyeColor())));
        }

        public static void uploadGildedToolModels(ItemModelGenerators itemModelGenerator, RegistryHelper.ItemRegistryHelper.ToolSet... sets) {
            for (RegistryHelper.ItemRegistryHelper.ToolSet set : sets) {
                uploadGildedToolModel(itemModelGenerator, VAModels.HANDHELD_TWO_LAYERS, set.SWORD(), "_sword");
                uploadGildedToolModel(itemModelGenerator, VAModels.HANDHELD_TWO_LAYERS, set.SHOVEL(), "_shovel");
                uploadGildedToolModel(itemModelGenerator, VAModels.HANDHELD_TWO_LAYERS, set.PICKAXE(), "_pickaxe");
                uploadGildedToolModel(itemModelGenerator, VAModels.HANDHELD_TWO_LAYERS, set.AXE(), "_axe");
                uploadGildedToolModel(itemModelGenerator, VAModels.HANDHELD_TWO_LAYERS, set.HOE(), "_hoe");
                uploadGildedHalberdModel(itemModelGenerator, set.HALBERD(), "_halberd");
                uploadGildedSpearModel(itemModelGenerator, set.SPEAR(), "_spear");
            }
        }

        public static void uploadGildedToolModel(ItemModelGenerators generator, ModelTemplate layered, Item item, String suffix) {
            ResourceLocation itemModelId = ModelLocationUtils.getModelLocation(item);
            List<SelectItemModel.SwitchCase<ResourceKey<GildType>>> list = new ArrayList<>(GILD_TYPES.size());

            ItemModel.Unbaked baseModel = ItemModelUtils.plainModel(itemModelId);

            ItemModel.Unbaked gilded;
            for (GildType type : GILD_TYPES) {
                ResourceLocation gildType = VARegistries.GILD_TYPE.getKey(type);
                ResourceLocation gildLayer = gildType.withSuffix(suffix).withPrefix("item/gilded_tools/");
                ResourceLocation gildedItem = itemModelId.withSuffix("_with_" + gildType.getPath() + "_gild");
                gilded = ItemModelUtils.plainModel(gildedItem);
                list.add(ItemModelUtils.when(VARegistries.GILD_TYPE.getResourceKey(type).get(), gilded));
                layered.create(gildedItem, TextureMapping.layered(itemModelId, gildLayer), generator.modelOutput);
            }

            generator.itemModelOutput.accept(item, ItemModelUtils.select(new GildTypeProperty(), baseModel, list), ClientItem.Properties.DEFAULT);
        }

        public static void uploadGildedHalberdModel(ItemModelGenerators generator, Item item, String suffix) {
            ResourceLocation itemModelId = ModelLocationUtils.getModelLocation(item);
            List<SelectItemModel.SwitchCase<ResourceKey<GildType>>> list = new ArrayList<>(GILD_TYPES.size());

            ItemModel.Unbaked baseModel = ItemModelUtils.plainModel(itemModelId);
            ItemModel.Unbaked inHandModel = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item, "_in_hand"));

            ItemModel.Unbaked gilded;
            for (GildType type : GILD_TYPES) {
                ResourceLocation gildType = VARegistries.GILD_TYPE.getKey(type);
                ResourceLocation gildLayer = gildType.withSuffix(suffix).withPrefix("item/gilded_tools/");
                ResourceLocation gildedItem = itemModelId.withSuffix("_with_" + gildType.getPath() + "_gild");
                gilded = createFlatModelDispatch(ItemModelUtils.plainModel(gildedItem), ItemModelUtils.plainModel(gildedItem.withSuffix("_in_hand")));
                list.add(ItemModelUtils.when(VARegistries.GILD_TYPE.getResourceKey(type).get(), gilded));
                VAModels.HANDHELD_TWO_LAYERS.create(gildedItem, TextureMapping.layered(itemModelId, gildLayer), generator.modelOutput);
                VAModels.HALBERD_IN_HAND_TWO_LAYERS.create(gildedItem.withSuffix("_in_hand"), TextureMapping.layered(itemModelId.withSuffix("_in_hand"), gildLayer.withSuffix("_in_hand")), generator.modelOutput);
            }

            generator.itemModelOutput.accept(item, ItemModelUtils.select(new GildTypeProperty(), createFlatModelDispatch(baseModel, inHandModel), list), HALBERD_PROPERTIES);
        }

        public static void uploadGildedSpearModel(ItemModelGenerators generator, Item item, String suffix) {
            ResourceLocation itemModelId = ModelLocationUtils.getModelLocation(item);
            List<SelectItemModel.SwitchCase<ResourceKey<GildType>>> list = new ArrayList<>(GILD_TYPES.size());

            ItemModel.Unbaked baseModel = ItemModelUtils.plainModel(itemModelId);
            ItemModel.Unbaked inHandModel = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item, "_in_hand"));

            ItemModel.Unbaked gilded;
            for (GildType type : GILD_TYPES) {
                ResourceLocation gildType = VARegistries.GILD_TYPE.getKey(type);
                ResourceLocation gildLayer = gildType.withSuffix(suffix).withPrefix("item/gilded_tools/");
                ResourceLocation gildedItem = itemModelId.withSuffix("_with_" + gildType.getPath() + "_gild");
                gilded = createFlatModelDispatch(ItemModelUtils.plainModel(gildedItem), ItemModelUtils.plainModel(gildedItem.withSuffix("_in_hand")));
                list.add(ItemModelUtils.when(VARegistries.GILD_TYPE.getResourceKey(type).get(), gilded));
                ModelTemplates.TWO_LAYERED_ITEM.create(gildedItem, TextureMapping.layered(itemModelId, gildLayer), generator.modelOutput);
                VAModels.SPEAR_IN_HAND_TWO_LAYERS.create(gildedItem.withSuffix("_in_hand"), TextureMapping.layered(itemModelId.withSuffix("_in_hand"), gildLayer.withSuffix("_in_hand")), generator.modelOutput);
            }

            generator.itemModelOutput.accept(item, ItemModelUtils.select(new GildTypeProperty(), createFlatModelDispatch(baseModel, inHandModel), list), SPEAR_PROPERTIES);
        }

        protected void registerSpotlight(BlockModelGenerators generator) {
            MultiVariant spotlight = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(VABlocks.SPOTLIGHT));
            MultiVariant spotlightActive = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(VABlocks.SPOTLIGHT, "_active"));
            generator.blockStateOutput
                    .accept(
                            MultiVariantGenerator.dispatch(VABlocks.SPOTLIGHT)
                                    .with(
                                            PropertyDispatch.initial(SpotlightBlock.POWERED)
                                                    .select(false, spotlight)
                                                    .select(true, spotlightActive)
                                    )
                                    .with(PropertyDispatch.modify(BlockStateProperties.ORIENTATION).generate(BlockModelGenerators::applyRotation))
                    );
        }

        public final void registerApplicablePotion(ItemModelGenerators generator, Item item) {
            ResourceLocation identifier = generator.generateLayeredItem(item, idOf("item/applicable_potion_overlay"), ModelLocationUtils.getModelLocation(item));
            generator.addPotionTint(item, identifier);
        }

        public final void registerTintableWithDefaultTexture(ItemModelGenerators generator, Item item) {
            ResourceLocation undyedItemIdentifier = generator.createFlatItemModel(item, ModelTemplates.FLAT_ITEM);
            ResourceLocation itemIdentifier = BuiltInRegistries.ITEM.getKey(item).withPrefix("item/");
            ResourceLocation dyedItemIdentifier = ModelTemplates.TWO_LAYERED_ITEM.create(ModelLocationUtils.getModelLocation(item, "_dyed"), TextureMapping.layered(itemIdentifier.withSuffix("_layer"), itemIdentifier.withSuffix("_base")), generator.modelOutput);
            generator.itemModelOutput.accept(item, ItemModelUtils.conditional(new HasComponent(DataComponents.DYED_COLOR, true), ItemModelUtils.tintedModel(dyedItemIdentifier, new Dye(0xFFFFFF)), ItemModelUtils.plainModel(undyedItemIdentifier)), ClientItem.Properties.DEFAULT);
        }

        public static void registerParentedTintedItemModel(BlockModelGenerators generator, Block block, ResourceLocation parentModelId, ItemTintSource... sources) {
            generator.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(parentModelId, sources), ClientItem.Properties.DEFAULT);
        }

        public static void registerArmorSetWithExtendedTrimMaterials(ItemModelGenerators generator, Item helmet, Item chestplate, Item leggings, Item boots, ResourceKey<EquipmentAsset> equipmentKey, boolean dyeable) {
            registerArmorWithExtendedTrimMaterials(generator, helmet, equipmentKey, TRIM_PREFIX_HELMET, dyeable);
            registerArmorWithExtendedTrimMaterials(generator, chestplate, equipmentKey, TRIM_PREFIX_CHESTPLATE, dyeable);
            registerArmorWithExtendedTrimMaterials(generator, leggings, equipmentKey, TRIM_PREFIX_LEGGINGS, dyeable);
            registerArmorWithExtendedTrimMaterials(generator, boots, equipmentKey, TRIM_PREFIX_BOOTS, dyeable);
        }

        public static void registerArmorWithExtendedTrimMaterials(ItemModelGenerators generator, Item item, ResourceKey<EquipmentAsset> equipmentKey, ResourceLocation trimIdPrefix, boolean dyeable) {
            ResourceLocation identifier = ModelLocationUtils.getModelLocation(item);
            ResourceLocation identifier2 = TextureMapping.getItemTexture(item);
            ResourceLocation identifier3 = TextureMapping.getItemTexture(item, "_overlay");
            List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> list = new ArrayList<>(TRIM_MATERIALS_EXTENDED.size());

            ItemModelGenerators.TrimMaterialData trimMaterial;
            ItemModel.Unbaked unbaked;
            for(Iterator<ItemModelGenerators.TrimMaterialData> iterator = TRIM_MATERIALS_EXTENDED.iterator(); iterator.hasNext(); list.add(ItemModelUtils.when(trimMaterial.materialKey, unbaked))) {
                trimMaterial = iterator.next();
                ResourceLocation identifier4 = identifier.withSuffix("_" + trimMaterial.assets().base().suffix() + "_trim");
                String var10001 = trimMaterial.assets().assetId(equipmentKey).suffix();
                ResourceLocation identifier5 = trimIdPrefix.withSuffix("_" + var10001);
                if (dyeable) {
                    generator.generateLayeredItem(identifier4, identifier2, identifier3, identifier5);
                    unbaked = ItemModelUtils.tintedModel(identifier4, new Dye(-6265536));
                } else {
                    generator.generateLayeredItem(identifier4, identifier2, identifier5);
                    unbaked = ItemModelUtils.plainModel(identifier4);
                }
            }

            ItemModel.Unbaked unbaked2;
            if (dyeable) {
                ModelTemplates.TWO_LAYERED_ITEM.create(identifier, TextureMapping.layered(identifier2, identifier3), generator.modelOutput);
                unbaked2 = ItemModelUtils.tintedModel(identifier, new Dye(-6265536));
            } else {
                ModelTemplates.FLAT_ITEM.create(identifier, TextureMapping.layer0(identifier2), generator.modelOutput);
                unbaked2 = ItemModelUtils.plainModel(identifier);
            }

            generator.itemModelOutput.accept(item, ItemModelUtils.select(new TrimMaterialProperty(), unbaked2, list), ClientItem.Properties.DEFAULT);
        }

        public final void registerCrossbow(ItemModelGenerators generator, Item item) {
            ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
            ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(generator.createFlatItemModel(item, "_pulling_0", ModelTemplates.CROSSBOW));
            ItemModel.Unbaked unbaked3 = ItemModelUtils.plainModel(generator.createFlatItemModel(item, "_pulling_1", ModelTemplates.CROSSBOW));
            ItemModel.Unbaked unbaked4 = ItemModelUtils.plainModel(generator.createFlatItemModel(item, "_pulling_2", ModelTemplates.CROSSBOW));
            ItemModel.Unbaked unbaked5 = ItemModelUtils.plainModel(generator.createFlatItemModel(item, "_arrow", ModelTemplates.CROSSBOW));
            ItemModel.Unbaked unbaked6 = ItemModelUtils.plainModel(generator.createFlatItemModel(item, "_firework", ModelTemplates.CROSSBOW));
            ItemModel.Unbaked unbaked7 = ItemModelUtils.plainModel(generator.createFlatItemModel(item, "_climbing_rope", ModelTemplates.CROSSBOW));
            ItemModel.Unbaked unbaked8 = ItemModelUtils.plainModel(generator.createFlatItemModel(item, "_exposed_climbing_rope", ModelTemplates.CROSSBOW));
            ItemModel.Unbaked unbaked9 = ItemModelUtils.plainModel(generator.createFlatItemModel(item, "_weathered_climbing_rope", ModelTemplates.CROSSBOW));
            ItemModel.Unbaked unbaked10 = ItemModelUtils.plainModel(generator.createFlatItemModel(item, "_oxidized_climbing_rope", ModelTemplates.CROSSBOW));
            generator.itemModelOutput.accept(item, ItemModelUtils.conditional(
                    ItemModelUtils.isUsingItem(), ItemModelUtils.rangeSelect(new CrossbowPull(), unbaked2, ItemModelUtils.override(unbaked3, 0.58F), ItemModelUtils.override(unbaked4, 1.0F)),
                    ItemModelUtils.select(new Charge(), unbaked,
                            ItemModelUtils.when(CrossbowItem.ChargeType.ARROW, ItemModelUtils.rangeSelect(new CrossbowProjectileTypeProperty(), unbaked5,
                                    ItemModelUtils.override(unbaked7, 0.25F),
                                    ItemModelUtils.override(unbaked8, 0.5F),
                                    ItemModelUtils.override(unbaked9, 0.75F),
                                    ItemModelUtils.override(unbaked10, 1.0F)
                                    )),
                            ItemModelUtils.when(CrossbowItem.ChargeType.ROCKET, unbaked6))), ClientItem.Properties.DEFAULT);
        }

        public final void registerPortalCore(ItemModelGenerators generator, Item item) {
            ItemModel.Unbaked base = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
            ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(TextureMapping.getItemTexture(item)), generator.modelOutput);
            ItemModel.Unbaked active = ItemModelUtils.plainModel(generator.createFlatItemModel(item, "_active", ModelTemplates.FLAT_ITEM));
            generator.itemModelOutput.accept(item, ItemModelUtils.conditional(
                    ItemModelUtils.hasComponent(VADataComponentTypes.PORTAL_CORE_LOCATION), active, base
            ), ClientItem.Properties.DEFAULT);
        }

        public static void registerHalberd(ItemModelGenerators generator, Item item) {
            ItemModel.Unbaked base = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
            ItemModel.Unbaked inHand = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item, "_in_hand"));
            ModelTemplates.FLAT_HANDHELD_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(TextureMapping.getItemTexture(item)), generator.modelOutput);
            VAModels.HALBERD_IN_HAND.create(ModelLocationUtils.getModelLocation(item, "_in_hand"), TextureMapping.layer0(TextureMapping.getItemTexture(item, "_in_hand")), generator.modelOutput);
            generator.itemModelOutput.accept(item, createFlatModelDispatch(base, inHand), HALBERD_PROPERTIES);
        }

        protected void registerSpectralFire(BlockModelGenerators generator) {
            MultiVariant weightedVariant = generator.createFloorFireModels(VABlocks.SPECTRAL_FIRE);
            MultiVariant weightedVariant2 = generator.createSideFireModels(VABlocks.SPECTRAL_FIRE);
            generator.blockStateOutput
                    .accept(
                            MultiPartGenerator.multiPart(VABlocks.SPECTRAL_FIRE)
                                    .with(weightedVariant)
                                    .with(weightedVariant2)
                                    .with(weightedVariant2.with(BlockModelGenerators.Y_ROT_90))
                                    .with(weightedVariant2.with(BlockModelGenerators.Y_ROT_180))
                                    .with(weightedVariant2.with(BlockModelGenerators.Y_ROT_270))
                    );
        }

        protected static void registerSimpleBlockItem(BlockModelGenerators generator, Block block) {
            generator.registerSimpleItemModel(block, BuiltInRegistries.BLOCK.getKey(block).withPrefix("block/"));
        }

        protected static void registerInventoryBlockItem(BlockModelGenerators generator, Block block) {
            generator.registerSimpleItemModel(block, BuiltInRegistries.BLOCK.getKey(block).withPrefix("block/").withSuffix("_inventory"));
        }

        protected static void registerInventoryBlockItem(BlockModelGenerators generator, Block block, ItemTintSource... sources) {
            registerParentedTintedItemModel(generator, block, BuiltInRegistries.BLOCK.getKey(block).withPrefix("block/").withSuffix("_inventory"), sources);
        }
        
        protected static void registerSimpleBlockItems(BlockModelGenerators generator, Block... blocks) {
            for (Block block : blocks) {
                registerSimpleBlockItem(generator, block);
            }
        }
        
        protected static void registerSimpleCubeAll(BlockModelGenerators generator, Block... blocks) {
            for (Block block : blocks) {
                generator.createTrivialCube(block);
            }
        }

        protected static void registerSimpleItems(ItemModelGenerators generator, Item... items) {
            for (Item item : items) {
                generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
            }
        }

        protected static void registerItems(ItemModelGenerators generator, ModelTemplate model, Item... items) {
            for (Item item : items) {
                generator.generateFlatItem(item, model);
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
