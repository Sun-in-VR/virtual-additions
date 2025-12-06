package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.block.BalloonBulbPlantBlock;
import com.github.suninvr.virtualadditions.block.CornCropBlock;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VACollections;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@SuppressWarnings("SameParameterValue")
public final class VABlockLootTableProvider {

    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> base() {
        return BaseProvider::new;
    }
    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> preview() {
        return PreviewProvider::new;
    }

    private static class BaseProvider extends Provider {

        protected BaseProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            addFamilyDrops(
                    VACollections.CUT_STEEL,
                    VACollections.EXPOSED_CUT_STEEL,
                    VACollections.WEATHERED_CUT_STEEL,
                    VACollections.OXIDIZED_CUT_STEEL,
                    VACollections.WAXED_CUT_STEEL,
                    VACollections.WAXED_EXPOSED_CUT_STEEL,
                    VACollections.WAXED_WEATHERED_CUT_STEEL,
                    VACollections.WAXED_OXIDIZED_CUT_STEEL,
                    VACollections.COBBLED_HORNFELS,
                    VACollections.COBBLED_BLUESCHIST,
                    VACollections.COBBLED_SYENITE,
                    VACollections.POLISHED_HORNFELS,
                    VACollections.POLISHED_BLUESCHIST,
                    VACollections.POLISHED_SYENITE,
                    VACollections.HORNFELS_TILES,
                    VACollections.BLUESCHIST_BRICKS,
                    VACollections.SYENITE_BRICKS,
                    VACollections.ROCK_SALT_BRICKS,
                    VACollections.SOULBLOOM,
                    VACollections.WITHERED,
                    VACollections.ZEBRANO,
                    VACollections.PORPHYRY,
                    VACollections.POLISHED_PORPHYRY,
                    VACollections.PORPHYRY_BRICKS
            );

            addSimpleDrops(
                    VABlocks.CLIMBING_ROPE_ANCHOR,
                    VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.RAW_STEEL_BLOCK,
                    VABlocks.STEEL_BLOCK,
                    VABlocks.EXPOSED_STEEL_BLOCK,
                    VABlocks.WEATHERED_STEEL_BLOCK,
                    VABlocks.OXIDIZED_STEEL_BLOCK,
                    VABlocks.WAXED_STEEL_BLOCK,
                    VABlocks.WAXED_EXPOSED_STEEL_BLOCK,
                    VABlocks.WAXED_WEATHERED_STEEL_BLOCK,
                    VABlocks.WAXED_OXIDIZED_STEEL_BLOCK,
                    VABlocks.STEEL_FENCE,
                    VABlocks.STEEL_TRAPDOOR,
                    VABlocks.EXPOSED_STEEL_TRAPDOOR,
                    VABlocks.WEATHERED_STEEL_TRAPDOOR,
                    VABlocks.OXIDIZED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_OXIDIZED_STEEL_TRAPDOOR,
                    VABlocks.STEEL_GRATE,
                    VABlocks.EXPOSED_STEEL_GRATE,
                    VABlocks.WEATHERED_STEEL_GRATE,
                    VABlocks.OXIDIZED_STEEL_GRATE,
                    VABlocks.WAXED_STEEL_GRATE,
                    VABlocks.WAXED_EXPOSED_STEEL_GRATE,
                    VABlocks.WAXED_WEATHERED_STEEL_GRATE,
                    VABlocks.WAXED_OXIDIZED_STEEL_GRATE,
                    VABlocks.REDSTONE_BRIDGE,
                    VABlocks.CAGELIGHT,
                    VABlocks.OAK_HEDGE,
                    VABlocks.SPRUCE_HEDGE,
                    VABlocks.BIRCH_HEDGE,
                    VABlocks.JUNGLE_HEDGE,
                    VABlocks.ACACIA_HEDGE,
                    VABlocks.DARK_OAK_HEDGE,
                    VABlocks.MANGROVE_HEDGE,
                    VABlocks.ZEBRANO_HEDGE,
                    VABlocks.CHERRY_HEDGE,
                    VABlocks.AZALEA_HEDGE,
                    VABlocks.FLOWERING_AZALEA_HEDGE,
                    VABlocks.SILK_BLOCK,
                    VABlocks.WEBBED_SILK,
                    VABlocks.FRAYED_SILK,
                    VABlocks.GREENCAP_MUSHROOM,
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
                    VABlocks.ACID_BLOCK,
                    VABlocks.ROCK_SALT_BLOCK,
                    VABlocks.CHISELED_ROCK_SALT_BRICKS,
                    VABlocks.SOULBLOOM_LOG,
                    VABlocks.SOULBLOOM_WOOD,
                    VABlocks.STRIPPED_SOULBLOOM_LOG,
                    VABlocks.STRIPPED_SOULBLOOM_WOOD,
                    VABlocks.SOULBLOOM_HANGING_SIGN,
                    VABlocks.SOULBLOOM_SHELF,
                    VABlocks.SOULBLOOM_SAPLING,
                    VABlocks.SOULBLOOM_HEDGE,
                    VABlocks.WITHERED_LOG,
                    VABlocks.WITHERED_WOOD,
                    VABlocks.STRIPPED_WITHERED_LOG,
                    VABlocks.STRIPPED_WITHERED_WOOD,
                    VABlocks.WITHERED_HANGING_SIGN,
                    VABlocks.WITHERED_SHELF,
                    VABlocks.WITHERED_SAPLING,
                    VABlocks.WITHERED_HEDGE,
                    VABlocks.NECROTIC_ROOTS,
                    VABlocks.SPECTRAL_TORCH,
                    VABlocks.SPECTRAL_LANTERN,
                    VABlocks.SPECTRAL_SAND,
                    VABlocks.ZEBRANO_LOG,
                    VABlocks.ZEBRANO_WOOD,
                    VABlocks.STRIPPED_ZEBRANO_LOG,
                    VABlocks.STRIPPED_ZEBRANO_WOOD,
                    VABlocks.ZEBRANO_HANGING_SIGN,
                    VABlocks.ZEBRANO_SHELF,
                    VABlocks.ZEBRANO_SAPLING,
                    VABlocks.ZEBRANO_HEDGE,
                    VABlocks.COLORING_STATION,
                    VABlocks.IOLITE_BLOCK,
                    VABlocks.ENTANGLEMENT_DRIVE,
                    VABlocks.SPOTLIGHT,
                    VABlocks.SMALL_SPRING_LOTUS,
                    VABlocks.SPRING_LOTUS,
                    VABlocks.SOUL_SPROUT
            );

            this.add(VABlocks.POTTED_GREENCAP_MUSHROOM, this.createPotFlowerItemTable(VABlocks.GREENCAP_MUSHROOM));
            this.add(VABlocks.POTTED_SOULBLOOM_SAPLING, this.createPotFlowerItemTable(VABlocks.SOULBLOOM_SAPLING));
            this.add(VABlocks.POTTED_WITHERED_SAPLING, this.createPotFlowerItemTable(VABlocks.WITHERED_SAPLING));
            this.add(VABlocks.POTTED_ZEBRANO_SAPLING, this.createPotFlowerItemTable(VABlocks.ZEBRANO_SAPLING));
            this.add(VABlocks.POTTED_NECROTIC_ROOTS, this.createPotFlowerItemTable(VABlocks.NECROTIC_ROOTS));
            this.add(VABlocks.POTTED_SMALL_SPRING_LOTUS, this.createPotFlowerItemTable(VABlocks.SMALL_SPRING_LOTUS));
            this.add(VABlocks.POTTED_SOUL_SPROUT, this.createPotFlowerItemTable(VABlocks.SOUL_SPROUT));

            this.add(VABlocks.BLUE_PETALS, this.createSegmentedBlockDrops(VABlocks.BLUE_PETALS));

            this.add(VABlocks.NECROTIC_NYLIUM, block -> this.createSingleItemTableWithSilkTouch(block, Blocks.NETHERRACK));

            this.add(VABlocks.BONE_LITTER, this.boneLitterDrops(VABlocks.BONE_LITTER));
            this.add(VABlocks.BONE_PILE, this.bonePileDrops(VABlocks.BONE_PILE));

            this.dropOther(VABlocks.MINI_PORTAL, VAItems.DRAINED_PORTAL_CORE);

            this.dropOther(VABlocks.INCENSE, Items.CHARCOAL);

            LootItemCondition.Builder tomatoBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(VABlocks.TOMATO)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
            this.add(VABlocks.TOMATO, this.cropDrops(VABlocks.TOMATO, VAItems.TOMATO, VAItems.TOMATO_SEEDS, 1, 3, tomatoBuilder));

            LootItemCondition.Builder cabbageBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(VABlocks.CABBAGE)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
            this.add(VABlocks.CABBAGE, this.createCropDrops(VABlocks.CABBAGE, VAItems.CABBAGE, VAItems.CABBAGE_SEEDS, cabbageBuilder));

            LootItemCondition.Builder cottonBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(VABlocks.COTTON)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
            this.add(VABlocks.COTTON, this.cropDrops(VABlocks.COTTON, VAItems.COTTON, VAItems.COTTON_SEEDS, 1, 2, cottonBuilder));

            LootItemCondition.Builder wisdomBerryCropBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(VABlocks.WISDOM_BERRY)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
            this.add(VABlocks.WISDOM_BERRY, this.plantCropDrops(VABlocks.WISDOM_BERRY, VAItems.WISDOM_BERRY, VAItems.WISDOM_BERRY_SEEDS, wisdomBerryCropBuilder));

            LootItemCondition.Builder soyBeansBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(VABlocks.SOY_CROP)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
            this.add(VABlocks.SOY_CROP, this.cropDrops(VABlocks.SOY_CROP, VAItems.SOY_BEANS, VAItems.SOY_BEANS, 2, 4, soyBeansBuilder));

            this.add(VABlocks.CORN_CROP, this.cornDrops());

            addColorfulBlockSetDrops(VACollections.CHARTREUSE);
            addColorfulBlockSetDrops(VACollections.MAROON);
            addColorfulBlockSetDrops(VACollections.INDIGO);
            addColorfulBlockSetDrops(VACollections.PLUM);
            addColorfulBlockSetDrops(VACollections.VIRIDIAN);
            addColorfulBlockSetDrops(VACollections.TAN);
            addColorfulBlockSetDrops(VACollections.SINOPIA);
            addColorfulBlockSetDrops(VACollections.LILAC);


            this.add(VABlocks.STEEL_DOOR, this::createDoorTable);
            this.add(VABlocks.EXPOSED_STEEL_DOOR, this::createDoorTable);
            this.add(VABlocks.WEATHERED_STEEL_DOOR, this::createDoorTable);
            this.add(VABlocks.OXIDIZED_STEEL_DOOR, this::createDoorTable);
            this.add(VABlocks.WAXED_STEEL_DOOR, this::createDoorTable);
            this.add(VABlocks.WAXED_EXPOSED_STEEL_DOOR, this::createDoorTable);
            this.add(VABlocks.WAXED_WEATHERED_STEEL_DOOR, this::createDoorTable);
            this.add(VABlocks.WAXED_OXIDIZED_STEEL_DOOR, this::createDoorTable);
            this.add(VABlocks.HORNFELS, block -> this.createSingleItemTableWithSilkTouch(block, VABlocks.COBBLED_HORNFELS));
            this.add(VABlocks.BLUESCHIST, block -> this.createSingleItemTableWithSilkTouch(block, VABlocks.COBBLED_BLUESCHIST));
            this.add(VABlocks.SYENITE, block -> this.createSingleItemTableWithSilkTouch(block, VABlocks.COBBLED_SYENITE));
            this.add(VABlocks.LUMWASP_NEST, block -> this.createSingleItemTableWithSilkTouch(block, VABlocks.SILK_BLOCK));
            this.add(VABlocks.GLOWING_SILK, block -> this.createSingleItemTableWithSilkTouch(block, VAItems.SILK_THREAD));
            this.add(VABlocks.IOLITE_ORE, block -> this.createOreDrop(block, VAItems.IOLITE));
            this.add(VABlocks.TALL_GREENCAP_MUSHROOMS, (Block block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

            this.add(VABlocks.ROCK_SALT_CRYSTAL, (block) -> this.createSingleItemTableWithSilkTouch(block, VAItems.ROCK_SALT, ConstantValue.exactly(2.0F)));
            this.add(VABlocks.ROCK_SALT_ORE, this::rockSaltOreDrops);
            this.add(VABlocks.DEEPSLATE_ROCK_SALT_ORE, this::rockSaltOreDrops);

            this.dropSelf(VABlocks.REDSTONE_BRIDGE);

            this.add(VABlocks.SOULBLOOM_LEAVES, block ->  createLeavesDrops(VABlocks.SOULBLOOM_LEAVES, VABlocks.SOULBLOOM_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
            this.add(VABlocks.WITHERED_LEAVES, block ->  createLeavesDrops(VABlocks.WITHERED_LEAVES, VABlocks.WITHERED_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
            this.add(VABlocks.ZEBRANO_LEAVES, block ->  createLeavesDrops(VABlocks.ZEBRANO_LEAVES, VABlocks.ZEBRANO_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));

        }
    }

    private static class PreviewProvider extends Provider {
        public PreviewProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generate() {
            this.add(VABlocks.BALLOON_BULB, block -> this.createSingleItemTable(VAItems.BALLOON_FRUIT));

            this.add(VABlocks.BALLOON_BULB_PLANT, block -> new LootTable.Builder().withPool(LootPool.lootPool().add(LootItem.lootTableItem(VAItems.BALLOON_FRUIT).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(VABlocks.BALLOON_BULB_PLANT).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BalloonBulbPlantBlock.AGE, 3))).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 3))))));
        }
    }

    private static abstract class Provider extends FabricBlockLootTableProvider {
        protected final HolderGetter<Enchantment> registryLookup;

        protected Provider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
            this.registryLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        }

        protected void addFamilyDrops(BlockFamily... blockFamilies) {
            for (BlockFamily family : blockFamilies) {
                dropSelf(family.getBaseBlock());
                family.getVariants().forEach(this::addDrop);
            }
        }

        protected void addColorfulBlockSetDrops(ColorfulBlockSet set) {
            set.ifWool(this::dropSelf);
            set.ifCarpet(this::dropSelf);
            set.ifTerracotta(this::dropSelf);
            set.ifConcrete(this::dropSelf);
            set.ifConcretePowder(this::dropSelf);
            set.ifStainedGlass(this::dropWhenSilkTouch);
            set.ifStainedGlassPane(this::dropWhenSilkTouch);
            set.ifSilkbulb(this::dropSelf);
            set.ifCandle(block -> {
                this.map.put(block.getLootTable().get(), this.createCandleDrops(block));
                set.ifCandleCake(candleCakeBlock -> this.map.put(candleCakeBlock.getLootTable().get(), BlockLootSubProvider.createCandleCakeDrops(block)));
            });
            set.ifBed(block -> this.map.put(block.getLootTable().get(), this.createSinglePropConditionTable(block, BedBlock.PART, BedPart.HEAD)));
            set.ifShulkerBox(block -> this.map.put(block.getLootTable().get(), this.createShulkerBoxDrop(block)));
            set.ifBanner(block -> this.map.put(block.getLootTable().get(), this.createBannerDrop(block)));
            set.ifGlazedTerracotta(this::dropSelf);
        }

        protected void addDrop(BlockFamily.Variant variant, Block block) {
            switch (variant) {
                case SLAB -> this.add(block, this::createSlabItemTable);
                case DOOR -> this.add(block, this::createDoorTable);
                default -> this.dropSelf(block);
            }
        }

        protected void addSimpleDrops(Block... blocks) {
            for (Block block : blocks) {
                this.dropSelf(block);
            }
        }

        @Override
        public void dropSelf(Block block) {
            this.dropOther(block, block);
        }

        @Override
        public void dropOther(Block block, ItemLike drop) {
            this.add(block, this.createSingleItemTable(drop));
        }

        @Override
        public void add(Block block, LootTable.Builder lootTable) {
            this.map.put(block.getLootTable().get(), lootTable.setRandomSequence(block.getLootTable().get().identifier()));
        }

        public LootTable.Builder cropDrops(Block crop, Item product, Item seeds, int minYield, int maxYield, LootItemCondition.Builder condition) {
            HolderLookup.RegistryLookup<Enchantment> impl = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
            return this.applyExplosionDecay(
                    crop,
                    LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                    .add(LootItem.lootTableItem(product)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(minYield, maxYield)))
                                            .when(condition)
                                            .otherwise(LootItem.lootTableItem(seeds))
                                    )
                            )
                            .withPool(
                                    LootPool.lootPool()
                                            .when(condition)
                                            .add(LootItem.lootTableItem(seeds).apply(ApplyBonusCount.addBonusBinomialDistributionCount(impl.getOrThrow(Enchantments.FORTUNE), 0.5714286F, 3)))
                            )
            );
        }

        public LootTable.Builder plantCropDrops(Block crop, Item product, Item seeds, LootItemCondition.Builder condition) {
            HolderLookup.RegistryLookup<Enchantment> impl = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
            return this.applyExplosionDecay(
                    crop,
                    LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                    .add(LootItem.lootTableItem(product)
                                            .when(condition)
                                            .otherwise(LootItem.lootTableItem(seeds))
                                    )
                            )
                            .withPool(
                                    LootPool.lootPool()
                                            .when(condition)
                                            .add(LootItem.lootTableItem(seeds).apply(ApplyBonusCount.addBonusBinomialDistributionCount(impl.getOrThrow(Enchantments.FORTUNE), 0.5714286F, 3)))
                            )
            );
        }

        @Override
        public LootTable.Builder createOreDrop(Block dropWithSilkTouch, Item drop) {
            return this.createSilkTouchDispatchTable(dropWithSilkTouch,
                            this.applyExplosionDecay(dropWithSilkTouch, LootItem.lootTableItem(drop)
                                    .apply(ApplyBonusCount.addOreBonusCount(this.registryLookup.getOrThrow(Enchantments.FORTUNE)))))
                    .setRandomSequence(dropWithSilkTouch.getLootTable().get().identifier());
        }


        public LootTable.Builder rockSaltOreDrops(Block drop) {
            HolderLookup.RegistryLookup<Enchantment> impl = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
            return this.createSilkTouchDispatchTable(
                    drop,
                    this.applyExplosionDecay(
                            drop,
                            LootItem.lootTableItem(VAItems.ROCK_SALT)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                    .apply(ApplyBonusCount.addOreBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
                    )
            );
        }


        public LootTable.Builder bonePileDrops(Block drop) {
            return this.createSilkTouchDispatchTable(drop,
                    this.applyExplosionDecay(drop,
                            LootItem.lootTableItem(Items.BONE_MEAL)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)))
                    )
            );
        }

        protected LootTable.Builder cornDrops() {
            HolderLookup.RegistryLookup<Enchantment> impl = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
            return LootTable.lootTable()
                    .withPool(createCornCropSeedsLoot(impl, CornCropBlock.Segment.TOP))
                    .withPool(createCornCropSeedsLoot(impl, CornCropBlock.Segment.MIDDLE))
                    .withPool(createCornCropSeedsLoot(impl, CornCropBlock.Segment.BOTTOM))
                    .withPool(createCornCropLoot(CornCropBlock.Segment.TOP))
                    .withPool(createCornCropLoot(CornCropBlock.Segment.MIDDLE))
                    .withPool(createCornCropLoot(CornCropBlock.Segment.BOTTOM))
                    .apply(ApplyExplosionDecay.explosionDecay());
        }

        private LootPool.Builder createCornCropSeedsLoot(HolderLookup.RegistryLookup<Enchantment> impl, CornCropBlock.Segment segment) {
            LootPool.Builder builder = LootPool.lootPool();
            builder.add(LootItem.lootTableItem(VAItems.CORN_SEEDS).apply(ApplyBonusCount.addBonusBinomialDistributionCount(impl.getOrThrow(Enchantments.FORTUNE), 0.5714286F, 3).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(VABlocks.CORN_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, 7)))))
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(VABlocks.CORN_CROP).setProperties(StatePropertiesPredicate.Builder.properties()
                    .hasProperty(CornCropBlock.SEGMENT, segment)
            ));
            if (segment.equals(CornCropBlock.Segment.BOTTOM)) {
                builder.when(createCornCropSeedsLootCondition(segment, CornCropBlock.Segment.MIDDLE));
                builder.when(createCornCropSeedsLootCondition(segment, CornCropBlock.Segment.TOP));
            }
            if (segment.equals(CornCropBlock.Segment.MIDDLE)) {
                builder.when(createCornCropSeedsLootCondition(segment, CornCropBlock.Segment.TOP));
                builder.when(createCornCropLootCondition(segment, CornCropBlock.Segment.BOTTOM));
            }
            if (segment.equals(CornCropBlock.Segment.TOP)) {
                builder.when(createCornCropLootCondition(segment, CornCropBlock.Segment.MIDDLE));
                builder.when(createCornCropLootCondition(segment, CornCropBlock.Segment.BOTTOM));
            }

            return builder;
        }

        private LootPool.Builder createCornCropLoot(CornCropBlock.Segment segment) {
            LootPool.Builder builder = LootPool.lootPool();
            builder.add(LootItem.lootTableItem(VAItems.CORN)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(VABlocks.CORN_CROP).setProperties(StatePropertiesPredicate.Builder.properties()
                    .hasProperty(CornCropBlock.SEGMENT, segment)
                    .hasProperty(CornCropBlock.AGE, 7)
            ));
            if (!(segment.equals(CornCropBlock.Segment.BOTTOM))) builder.when(createCornCropLootCondition(segment, CornCropBlock.Segment.BOTTOM, true));
            if (!(segment.equals(CornCropBlock.Segment.MIDDLE))) builder.when(createCornCropLootCondition(segment, CornCropBlock.Segment.MIDDLE, true));
            if (!(segment.equals(CornCropBlock.Segment.TOP))) builder.when(createCornCropLootCondition(segment, CornCropBlock.Segment.TOP, true));
            return builder.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)));
        }

        private LootItemCondition.Builder createCornCropSeedsLootCondition(CornCropBlock.Segment segment, CornCropBlock.Segment expectedSegment) {
            return AnyOfCondition.anyOf(
                    createCornCropLootCondition(segment, expectedSegment),
                    createCornCropSeedsAgeConditions(segment.minAge(), expectedSegment.minAge())
            );
        }

        private LootItemCondition.Builder createCornCropLootCondition(CornCropBlock.Segment segment, CornCropBlock.Segment expectedSegment) {
            int i = segment.getYOffset(expectedSegment);
            return AnyOfCondition.anyOf(createCornCropStateConditions(expectedSegment, i, segment.minAge()));
        }

        private LootItemCondition.Builder createCornCropLootCondition(CornCropBlock.Segment segment, CornCropBlock.Segment expectedSegment, boolean fullyGrown) {
            if (!fullyGrown) return createCornCropLootCondition(segment, expectedSegment);
            int i = segment.getYOffset(expectedSegment);
            return LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.SEGMENT, expectedSegment).hasProperty(CornCropBlock.AGE, 7))), new BlockPos(0, i, 0));
        }

        private LootItemCondition.Builder[] createCornCropStateConditions(CornCropBlock.Segment segment, int offset, int minAge) {
            LootItemCondition.Builder[] builders = {};
            ArrayList<LootItemCondition.Builder> buildersList = new ArrayList<>();
            int i = minAge;
            while (i < 8) {
                buildersList.add(
                        AllOfCondition.allOf(
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(VABlocks.CORN_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, i)),
                                LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.SEGMENT, segment).hasProperty(CornCropBlock.AGE, i))), new BlockPos(0, offset, 0))
                        )
                );

                i++;
            }
            return buildersList.toArray(builders);
        }

        private LootItemCondition.Builder createCornCropSeedsAgeConditions(int minAge, int maxAge) {
            LootItemCondition.Builder[] builders = {};
            ArrayList<LootItemCondition.Builder> buildersList = new ArrayList<>();
            int i = minAge;
            while (i < maxAge) {
                buildersList.add(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(VABlocks.CORN_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, i))
                );
                i++;
            }
            return AnyOfCondition.anyOf(buildersList.toArray(builders));
        }


        protected LootTable.Builder boneLitterDrops(Block segmented) {
            return segmented instanceof SegmentableBlock segmented2 ? LootTable.lootTable().withPool(
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                                    AlternativesEntry.alternatives(
                                            LootItem.lootTableItem(segmented)
                                                    .apply(
                                                            IntStream.rangeClosed(1, 4).boxed().toList(),
                                                            count -> SetItemCountFunction.setCount(ConstantValue.exactly(count))
                                                                    .when(
                                                                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(segmented).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(segmented2.getSegmentAmountProperty(), count))
                                                                    )
                                                    )
                                                    .when(hasSilkTouch()),
                                            LootItem.lootTableItem(Items.BONE_MEAL)
                                                    .apply(
                                                            IntStream.rangeClosed(1, 4).boxed().toList(),
                                                            count -> SetItemCountFunction.setCount(UniformGenerator.between(-1, count))
                                                                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(segmented).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(segmented2.getSegmentAmountProperty(), count)))
                                                    )
                                    )
                                    ).apply(ApplyExplosionDecay.explosionDecay())
                    )
                    : noDrop();
        }
    }
}
