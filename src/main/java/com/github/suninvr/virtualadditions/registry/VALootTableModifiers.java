package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.component.ExplosiveContentComponent;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import static com.github.suninvr.virtualadditions.registry.VAItems.*;

public class VALootTableModifiers {

    public static void init() {
        LootTableEvents.MODIFY.register( ((key, tableBuilder, source, registries) -> {

            if (!source.isBuiltin()) return;

            if (LootTables.SPAWN_BONUS_CHEST.equals(key)) {
                LootPool.Builder bundleBuilder = LootPool.builder()
                        .with(ItemEntry.builder(Items.BUNDLE));
                tableBuilder.pool(bundleBuilder);
            }

            // Ominous Trial Spawner Throwables
            if (LootTables.TRIAL_CHAMBER_ITEMS_TO_DROP_WHEN_OMINOUS_SPAWNER.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.with(ItemEntry.builder(Items.LINGERING_POTION).apply(SetPotionLootFunction.builder(VAPotions.STRONG_FRAILTY)));
                    }
                    if (i[0] == 1) {
                        builder.with(ItemEntry.builder(STEEL_BOMB).apply(SetComponentsLootFunction.builder(VADataComponentTypes.EXPLOSIVE_CONTENTS, ExplosiveContentComponent.KEEP_BLOCKS)));
                    }
                    i[0]++;
                });
            }

            // Grass Drop
            if (lootTableKeyMatches(key, Blocks.SHORT_GRASS, Blocks.FERN, Blocks.TALL_GRASS, Blocks.LARGE_FERN)) {
                boolean largeFern = lootTableKeyMatches(key, Blocks.LARGE_FERN);
                boolean tallGrass = lootTableKeyMatches(key, Blocks.TALL_GRASS);
                RegistryWrapper.Impl<Biome> impl = registries.getOrThrow(RegistryKeys.BIOME);
                RegistryEntryLookup<Enchantment> enchantmentLookup = registries.getOrThrow(RegistryKeys.ENCHANTMENT);
                RegistryEntryLookup<Item> itemRegistryEntryLookup = registries.getOrThrow(RegistryKeys.ITEM);
                if (largeFern || tallGrass) {
                    Block block = largeFern ? Blocks.LARGE_FERN : Blocks.TALL_GRASS;
                    LootPool.Builder lowerFruitCropBuilder = createFruitCropSeedsLoot(impl, enchantmentLookup, itemRegistryEntryLookup)
                            .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(TallPlantBlock.HALF, DoubleBlockHalf.LOWER)))
                            .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().block(BlockPredicate.Builder.create().state(StatePredicate.Builder.create().exactMatch(TallPlantBlock.HALF, DoubleBlockHalf.UPPER))), new BlockPos(0, 1, 0)));
                    LootPool.Builder upperFruitCropBuilder = createFruitCropSeedsLoot(impl, enchantmentLookup, itemRegistryEntryLookup)
                            .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(TallPlantBlock.HALF, DoubleBlockHalf.UPPER)))
                            .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().block(BlockPredicate.Builder.create().state(StatePredicate.Builder.create().exactMatch(TallPlantBlock.HALF, DoubleBlockHalf.LOWER))), new BlockPos(0, -1, 0)));
                    LootPool.Builder lowerCottonBuilder = createCottonSeedsLoot(enchantmentLookup, itemRegistryEntryLookup)
                            .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(TallPlantBlock.HALF, DoubleBlockHalf.LOWER)))
                            .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().block(BlockPredicate.Builder.create().state(StatePredicate.Builder.create().exactMatch(TallPlantBlock.HALF, DoubleBlockHalf.UPPER))), new BlockPos(0, 1, 0)));
                    LootPool.Builder upperCottonBuilder = createCottonSeedsLoot(enchantmentLookup, itemRegistryEntryLookup)
                            .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(TallPlantBlock.HALF, DoubleBlockHalf.UPPER)))
                            .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().block(BlockPredicate.Builder.create().state(StatePredicate.Builder.create().exactMatch(TallPlantBlock.HALF, DoubleBlockHalf.LOWER))), new BlockPos(0, -1, 0)));

                    tableBuilder.pool(lowerCottonBuilder).pool(upperCottonBuilder).pool(lowerFruitCropBuilder).pool(upperFruitCropBuilder);
                } else {
                    tableBuilder.pool(createCottonSeedsLoot(enchantmentLookup, itemRegistryEntryLookup)).pool(createFruitCropSeedsLoot(impl, enchantmentLookup, itemRegistryEntryLookup));
                }
            }

            // Abandoned Mineshaft Chest
            if (LootTables.ABANDONED_MINESHAFT_CHEST.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 2) {
                        builder
                                .with(ItemEntry.builder(CLIMBING_ROPE).weight(7).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 16))))
                                .with(ItemEntry.builder(EXPOSED_CLIMBING_ROPE).weight(7).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 16))))
                                .with(ItemEntry.builder(WEATHERED_CLIMBING_ROPE).weight(7).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 16))))
                                .with(ItemEntry.builder(OXIDIZED_CLIMBING_ROPE).weight(7).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 16))));
                    }
                    i[0]++;
                });
            }

            // Village Toolsmith and Weaponsmith Chests
            if (LootTables.VILLAGE_TOOLSMITH_CHEST.equals(key) || LootTables.VILLAGE_WEAPONSMITH_CHEST.equals(key)) {
                tableBuilder.modifyPools( builder -> builder
                        .with(ItemEntry.builder(STEEL_INGOT).weight(3).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4))))
                );
                LootPool.Builder smithingTemplateBuilder = LootPool.builder()
                        .with(ItemEntry.builder(TOOL_GILD_SMITHING_TEMPLATE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 3))));
                tableBuilder.pool(smithingTemplateBuilder);
                LootPool.Builder gildMaterialBuilder = LootPool.builder()
                        .with(ItemEntry.builder(Items.COPPER_INGOT).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4))))
                        .with(ItemEntry.builder(Items.AMETHYST_SHARD).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4))))
                        .with(ItemEntry.builder(Items.EMERALD).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4))));
                tableBuilder.pool(gildMaterialBuilder);

            }

            // Savannah and Desert Village House Chests
            if (LootTables.VILLAGE_SAVANNA_HOUSE_CHEST.equals(key) || LootTables.VILLAGE_DESERT_HOUSE_CHEST.equals(key)) {
                tableBuilder.modifyPools( builder -> builder.with(ItemEntry.builder(CORN).weight(5).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4)))));
            }

            // Jungle Temple Chest
            if (LootTables.JUNGLE_TEMPLE_CHEST.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.with(ItemEntry.builder(STEEL_INGOT).weight(5).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 5))));
                    }
                    i[0]++;
                });
            }

            // End City Chest
            if (LootTables.END_CITY_TREASURE_CHEST.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.with(ItemEntry.builder(IOLITE).weight(5).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4))))
                                .with(ItemEntry.builder(STEEL_INGOT).weight(10).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 6))));
                    }
                    i[0]++;
                });
            }

            // Ancient City Loot
            if (LootTables.ANCIENT_CITY_CHEST.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.with(ItemEntry.builder(EMERALD_DIAMOND_TOOL_SET.HOE())
                                .weight(2)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.8F, 1), false))
                        );
                    }
                    i[0]++;
                });
            }

            // Ancient City Ice Box Loot
            if (LootTables.ANCIENT_CITY_ICE_BOX_CHEST.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.with(ItemEntry.builder(TOMATO)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 12.0F)))
                        );
                    }
                    i[0]++;
                });
            }

            if (LootTables.SHIPWRECK_SUPPLY_CHEST.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.with(ItemEntry.builder(ROCK_SALT)
                                .weight(5)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(7, 24))));
                        builder.with(ItemEntry.builder(CHEESE_WEDGE)
                                .weight(5)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 12))));
                    }
                    i[0]++;
                });
            }

            // Zombie Loot
            if (lootTableKeyMatches(key, EntityType.ZOMBIE, EntityType.HUSK)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 1) {
                        builder.with(ItemEntry.builder(CORN).apply(
                                FurnaceSmeltLootFunction.builder().conditionally(
                                        EntityPropertiesLootCondition.builder(
                                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onFire(true))
                                        )
                                )
                        ));
                    }
                    i[0]++;
                });
            }
        } ));
    }

    private static LootPool.Builder createFruitCropSeedsLoot(RegistryWrapper.Impl<Biome> impl, RegistryEntryLookup<Enchantment> enchantmentLookup, RegistryEntryLookup<Item> itemRegistryEntryLookup) {
        return LootPool.builder()
                .with(AlternativeEntry.builder(
                                        ItemEntry.builder(CORN_SEEDS)
                                                .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(
                                                        impl.getOrThrow(BiomeTags.SPAWNS_WARM_VARIANT_FARM_ANIMALS)
                                                ))),
                                        ItemEntry.builder(CABBAGE_SEEDS)
                                                .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(
                                                        impl.getOrThrow(BiomeTags.SPAWNS_COLD_VARIANT_FARM_ANIMALS)
                                                ))),
                                        ItemEntry.builder(TOMATO_SEEDS)
                                )
                                .conditionally(InvertedLootCondition.builder(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(itemRegistryEntryLookup, Items.SHEARS))))
                                .conditionally(RandomChanceLootCondition.builder(0.125F))
                )
                .apply(ApplyBonusLootFunction.uniformBonusCount(enchantmentLookup.getOrThrow(Enchantments.FORTUNE), 2))
                .apply(ExplosionDecayLootFunction.builder());
    }

    private static LootPool.Builder createCottonSeedsLoot(RegistryEntryLookup<Enchantment> enchantmentLookup, RegistryEntryLookup<Item> itemRegistryEntryLookup) {
        return LootPool.builder()
                .with(ItemEntry.builder(COTTON_SEEDS)
                        .apply(ApplyBonusLootFunction.uniformBonusCount(enchantmentLookup.getOrThrow(Enchantments.FORTUNE), 2))
                        .apply(ExplosionDecayLootFunction.builder())
                        .conditionally(RandomChanceLootCondition.builder(0.125F))
                        .conditionally(InvertedLootCondition.builder(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(itemRegistryEntryLookup, Items.SHEARS))))
                );
    }
}
