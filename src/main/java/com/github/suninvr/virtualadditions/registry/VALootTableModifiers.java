package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.component.ExplosiveContentComponent;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import static com.github.suninvr.virtualadditions.registry.VAItems.*;

public class VALootTableModifiers {

    public static void init() {
        LootTableEvents.MODIFY.register( ((key, tableBuilder, source, registries) -> {

            if (!source.isBuiltin()) return;

            if (BuiltInLootTables.SPAWN_BONUS_CHEST.equals(key)) {
                LootPool.Builder bundleBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.BUNDLE))
                        .add(LootItem.lootTableItem(Items.WHITE_BUNDLE))
                        .add(LootItem.lootTableItem(Items.LIGHT_GRAY_BUNDLE))
                        .add(LootItem.lootTableItem(Items.GRAY_BUNDLE))
                        .add(LootItem.lootTableItem(Items.BLACK_BUNDLE))
                        .add(LootItem.lootTableItem(VAItems.TAN_BUNDLE))
                        .add(LootItem.lootTableItem(Items.BROWN_BUNDLE))
                        .add(LootItem.lootTableItem(VAItems.LILAC_BUNDLE))
                        .add(LootItem.lootTableItem(VAItems.MAROON_BUNDLE))
                        .add(LootItem.lootTableItem(Items.RED_BUNDLE))
                        .add(LootItem.lootTableItem(VAItems.SINOPIA_BUNDLE))
                        .add(LootItem.lootTableItem(Items.ORANGE_BUNDLE))
                        .add(LootItem.lootTableItem(Items.YELLOW_BUNDLE))
                        .add(LootItem.lootTableItem(VAItems.CHARTREUSE_BUNDLE))
                        .add(LootItem.lootTableItem(Items.LIME_BUNDLE))
                        .add(LootItem.lootTableItem(Items.GREEN_BUNDLE))
                        .add(LootItem.lootTableItem(VAItems.VIRIDIAN_BUNDLE))
                        .add(LootItem.lootTableItem(Items.CYAN_BUNDLE))
                        .add(LootItem.lootTableItem(Items.LIGHT_BLUE_BUNDLE))
                        .add(LootItem.lootTableItem(Items.BLUE_BUNDLE))
                        .add(LootItem.lootTableItem(VAItems.INDIGO_BUNDLE))
                        .add(LootItem.lootTableItem(Items.PURPLE_BUNDLE))
                        .add(LootItem.lootTableItem(VAItems.PLUM_BUNDLE))
                        .add(LootItem.lootTableItem(Items.MAGENTA_BUNDLE))
                        .add(LootItem.lootTableItem(Items.PINK_BUNDLE));
                tableBuilder.withPool(bundleBuilder);
            }

            // Ominous Trial Spawner Throwables
            if (BuiltInLootTables.SPAWNER_TRIAL_ITEMS_TO_DROP_WHEN_OMINOUS.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.add(LootItem.lootTableItem(Items.LINGERING_POTION).apply(SetPotionFunction.setPotion(VAPotions.STRONG_FRAILTY)));
                    }
                    if (i[0] == 1) {
                        builder.add(LootItem.lootTableItem(STEEL_BOMB).apply(SetComponentsFunction.setComponent(VADataComponentTypes.EXPLOSIVE_CONTENTS, ExplosiveContentComponent.KEEP_BLOCKS)));
                    }
                    i[0]++;
                });
            }

            if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_RARE.equals(key)) {
                tableBuilder.modifyPools(builder -> {
                    builder
                            .add(LootItem.lootTableItem(IRON_HALBERD).setWeight(2).apply(new EnchantWithLevelsFunction.Builder(UniformGenerator.between(0, 10)).fromOptions(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(EnchantmentTags.ON_RANDOM_LOOT))))
                            .add(LootItem.lootTableItem(DIAMOND_HALBERD).apply(new EnchantWithLevelsFunction.Builder(UniformGenerator.between(5, 15)).fromOptions(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(EnchantmentTags.ON_RANDOM_LOOT))))
                    ;
                });
            }

            // Grass Drop
            if (lootTableKeyMatches(key, Blocks.SHORT_GRASS, Blocks.FERN, Blocks.TALL_GRASS, Blocks.LARGE_FERN)) {
                boolean largeFern = lootTableKeyMatches(key, Blocks.LARGE_FERN);
                boolean tallGrass = lootTableKeyMatches(key, Blocks.TALL_GRASS);
                HolderLookup.RegistryLookup<Biome> impl = registries.lookupOrThrow(Registries.BIOME);
                HolderGetter<Enchantment> enchantmentLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
                HolderGetter<Item> itemRegistryEntryLookup = registries.lookupOrThrow(Registries.ITEM);
                if (largeFern || tallGrass) {
                    Block block = largeFern ? Blocks.LARGE_FERN : Blocks.TALL_GRASS;
                    LootPool.Builder lowerFruitCropBuilder = createFruitCropSeedsLoot(impl, enchantmentLookup, itemRegistryEntryLookup)
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                            .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))), new BlockPos(0, 1, 0)));
                    LootPool.Builder upperFruitCropBuilder = createFruitCropSeedsLoot(impl, enchantmentLookup, itemRegistryEntryLookup)
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)))
                            .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))), new BlockPos(0, -1, 0)));
                    LootPool.Builder lowerCottonBuilder = createCottonSeedsLoot(enchantmentLookup, itemRegistryEntryLookup)
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                            .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))), new BlockPos(0, 1, 0)));
                    LootPool.Builder upperCottonBuilder = createCottonSeedsLoot(enchantmentLookup, itemRegistryEntryLookup)
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)))
                            .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))), new BlockPos(0, -1, 0)));

                    tableBuilder.withPool(lowerCottonBuilder).withPool(upperCottonBuilder).withPool(lowerFruitCropBuilder).withPool(upperFruitCropBuilder);
                } else {
                    tableBuilder.withPool(createCottonSeedsLoot(enchantmentLookup, itemRegistryEntryLookup)).withPool(createFruitCropSeedsLoot(impl, enchantmentLookup, itemRegistryEntryLookup));
                }
            }

            // Abandoned Mineshaft Chest
            if (BuiltInLootTables.ABANDONED_MINESHAFT.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 2) {
                        builder
                                .add(LootItem.lootTableItem(CLIMBING_ROPE).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 16))))
                                .add(LootItem.lootTableItem(EXPOSED_CLIMBING_ROPE).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 16))))
                                .add(LootItem.lootTableItem(WEATHERED_CLIMBING_ROPE).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 16))))
                                .add(LootItem.lootTableItem(OXIDIZED_CLIMBING_ROPE).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 16))));
                    }
                    i[0]++;
                });
            }

            // Toolsmith and Weaponsmith Chests
            if (BuiltInLootTables.VILLAGE_TOOLSMITH.equals(key) || BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(key)) {
                tableBuilder.modifyPools( builder -> builder
                        .add(LootItem.lootTableItem(STEEL_INGOT).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                );
                LootPool.Builder smithingTemplateBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(TOOL_GILD_SMITHING_TEMPLATE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 3))));
                tableBuilder.withPool(smithingTemplateBuilder);
                LootPool.Builder gildMaterialBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.EMERALD).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))));
                tableBuilder.withPool(gildMaterialBuilder);

            }

            // Savannah and Desert Village House Chests
            if (BuiltInLootTables.VILLAGE_SAVANNA_HOUSE.equals(key) || BuiltInLootTables.VILLAGE_DESERT_HOUSE.equals(key)) {
                tableBuilder.modifyPools( builder -> builder.add(LootItem.lootTableItem(CORN).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))));
            }

            // Plains Village House Chests
            if (BuiltInLootTables.VILLAGE_PLAINS_HOUSE.equals(key)) {
                tableBuilder.modifyPools( builder -> builder.add(LootItem.lootTableItem(TOMATO).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))));
            }

            // Taiga and Snowy Village House Chests
            if (BuiltInLootTables.VILLAGE_TAIGA_HOUSE.equals(key) || BuiltInLootTables.VILLAGE_SNOWY_HOUSE.equals(key)) {
                tableBuilder.modifyPools( builder -> builder.add(LootItem.lootTableItem(CABBAGE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))));
            }

            // Jungle Temple Loot
            if (BuiltInLootTables.JUNGLE_TEMPLE.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.add(LootItem.lootTableItem(STEEL_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5))));
                    }
                    i[0]++;
                });
            }

            // End City Loot
            if (BuiltInLootTables.END_CITY_TREASURE.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.add(LootItem.lootTableItem(IOLITE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                                .add(LootItem.lootTableItem(STEEL_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))));
                    }
                    i[0]++;
                });
            }

            // Ancient City Loot
            if (BuiltInLootTables.ANCIENT_CITY.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.add(LootItem.lootTableItem(Items.DIAMOND_HOE)
                                .setWeight(2)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 1), false))
                                .apply(SetComponentsFunction.setComponent(VADataComponentTypes.GILD_TYPE, VAGildTypes.EMERALD))
                        );
                    }
                    i[0]++;
                });
            }

            // Ancient City Ice Box Loot
            if (BuiltInLootTables.ANCIENT_CITY_ICE_BOX.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.add(LootItem.lootTableItem(TOMATO)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 12.0F)))
                        );
                    }
                    i[0]++;
                });
            }

            // Shipwreck Supply Loot
            if (BuiltInLootTables.SHIPWRECK_SUPPLY.equals(key)) {
                final int[] i = {0};
                tableBuilder.modifyPools(builder -> {
                    if (i[0] == 0) {
                        builder.add(LootItem.lootTableItem(ROCK_SALT)
                                .setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(7, 24))));
                        builder.add(LootItem.lootTableItem(CHEESE_WEDGE)
                                .setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 12))));
                    }
                    i[0]++;
                });
            }

            // Zombie Loot
            //if (lootTableKeyMatches(key, EntityType.ZOMBIE, EntityType.HUSK)) {
            //
            //}
        } ));
    }

    private static LootPool.Builder createFruitCropSeedsLoot(HolderLookup.RegistryLookup<Biome> impl, HolderGetter<Enchantment> enchantmentLookup, HolderGetter<Item> itemRegistryEntryLookup) {
        return LootPool.lootPool()
                .add(AlternativesEntry.alternatives(
                                        LootItem.lootTableItem(WISDOM_BERRY_SEEDS)
                                                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(
                                                        impl.getOrThrow(VABiomeTags.SPAWNS_ENCHANTED_VARIANT_FARM_ANIMALS)
                                                ))),
                                        LootItem.lootTableItem(CORN_SEEDS)
                                                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(
                                                        impl.getOrThrow(BiomeTags.SPAWNS_WARM_VARIANT_FARM_ANIMALS)
                                                ))),
                                        LootItem.lootTableItem(CABBAGE_SEEDS)
                                                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(
                                                        impl.getOrThrow(BiomeTags.SPAWNS_COLD_VARIANT_FARM_ANIMALS)
                                                ))),
                                        LootItem.lootTableItem(TOMATO_SEEDS)
                                )
                                .when(InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(itemRegistryEntryLookup, Items.SHEARS))))
                                .when(LootItemRandomChanceCondition.randomChance(0.125F))
                )
                .apply(ApplyBonusCount.addUniformBonusCount(enchantmentLookup.getOrThrow(Enchantments.FORTUNE), 2))
                .apply(ApplyExplosionDecay.explosionDecay());
    }

    private static LootPool.Builder createCottonSeedsLoot(HolderGetter<Enchantment> enchantmentLookup, HolderGetter<Item> itemRegistryEntryLookup) {
        return LootPool.lootPool()
                .add(LootItem.lootTableItem(COTTON_SEEDS)
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantmentLookup.getOrThrow(Enchantments.FORTUNE), 2))
                        .apply(ApplyExplosionDecay.explosionDecay())
                        .when(LootItemRandomChanceCondition.randomChance(0.125F))
                        .when(InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(itemRegistryEntryLookup, Items.SHEARS))))
                );
    }

    protected static boolean lootTableKeyMatches(ResourceKey<LootTable> key, Block... blocks) {
        for (Block block : blocks) {
            if (block.getLootTable().isPresent() && block.getLootTable().get().equals(key)) return true;
        }
        return false;
    }

    protected static boolean lootTableKeyMatches(ResourceKey<LootTable> key, EntityType<?>... entities) {
        for (EntityType<?> type : entities) {
            if (type.getDefaultLootTable().isPresent() && type.getDefaultLootTable().get().equals(key)) return true;
        }
        return false;
    }
}
