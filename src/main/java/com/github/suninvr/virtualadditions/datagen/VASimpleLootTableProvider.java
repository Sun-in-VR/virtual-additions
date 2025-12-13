package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VALootTables;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.entity.animal.chicken.ChickenVariants;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VASimpleLootTableProvider {

    public static FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider> enhancementsShearing() {return EnhancementsShearingProvider::new;}
    public static FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider> enhancementsEntities() {return EnhancementsEntitiesProvider::new;}
    public static FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider> enhancementsGift() {return EnhancementsGiftProvider::new;}
    public static FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider> base() {return BaseProvider::new;}

    public static class BaseProvider extends Provider {
        public BaseProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup, LootContextParamSets.CHEST);
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            lootTableBiConsumer.accept(VALootTables.CEMETERY_GRAVES, LootTable.lootTable()
                    .withPool(LootPool.lootPool().when(LootItemRandomChanceCondition.randomChance(0.6F))
                            .add(item(Items.IRON_HELMET).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(7))
                            .add(item(Items.IRON_CHESTPLATE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(7))
                            .add(item(Items.IRON_LEGGINGS).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(7))
                            .add(item(Items.IRON_BOOTS).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(7))
                            .add(item(Items.IRON_SWORD).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(7))
                            .add(item(Items.IRON_SHOVEL).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(7))
                            .add(item(Items.IRON_PICKAXE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(7))
                            .add(item(Items.IRON_AXE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(7))
                            .add(item(Items.IRON_HOE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(7))
                            .add(item(VAItems.STEEL_HELMET).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(3))
                            .add(item(VAItems.STEEL_CHESTPLATE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(3))
                            .add(item(VAItems.STEEL_LEGGINGS).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(3))
                            .add(item(VAItems.STEEL_BOOTS).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(3))
                            .add(item(VAItems.STEEL_SWORD).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(3))
                            .add(item(VAItems.STEEL_SHOVEL).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(3))
                            .add(item(VAItems.STEEL_PICKAXE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(3))
                            .add(item(VAItems.STEEL_AXE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(3))
                            .add(item(VAItems.STEEL_HOE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).setWeight(3))
                    )
                    .withPool(LootPool.lootPool().setRolls(uniform(2.0F, 5.0F))
                            .add(item(Items.BONE, 3, 10).setWeight(12))
                            .add(item(Items.ROTTEN_FLESH, 3, 10).setWeight(8))
                            .add(item(Items.EMERALD, 2, 8).setWeight(3))
                            .add(item(Items.LEATHER, 1, 5).setWeight(3))
                    )
            );

            lootTableBiConsumer.accept(VALootTables.CEMETERY, LootTable.lootTable(
                    ).withPool(LootPool.lootPool().when(chance(0.25F))
                            .add(item(Items.DIAMOND, 1, 3))
                            .add(item(VAItems.ROBE_ARMOR_TRIM_SMITHING_TEMPLATE))
                    ).withPool(LootPool.lootPool().setRolls(uniform(2.0F, 4.0F))
                            .add(item(VAItems.SPECTRAL_POWDER, 2, 8).setWeight(10))
                            .add(item(VAItems.SPECTRAL_TORCH, 2, 8).setWeight(8))
                            .add(item(VAItems.SPECTRAL_SAND, 2, 8).setWeight(5))
                            .add(item(VAItems.STEEL_INGOT, 2, 4).setWeight(2))
                    ).withPool(LootPool.lootPool().setRolls(uniform(2.0F, 5.0f))
                            .add(item(Items.LEATHER, 1, 5))
                            .add(item(Items.BONE, 1, 5))
                            .add(item(VAItems.BONE_LITTER, 2, 10))
                    )
            );
        }
    }

    public static class EnhancementsShearingProvider extends Provider {
        public EnhancementsShearingProvider(FabricDataOutput fabricDataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(fabricDataOutput, registryLookup, LootContextParamSets.SHEARING);
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            lootTableBiConsumer.accept(
                BuiltInLootTables.SHEAR_SHEEP,
                LootTable.lootTable()
                        .withPool(EntityLootSubProvider.createSheepDispatchPool(BuiltInLootTables.SHEAR_SHEEP_BY_DYE))
            );

            WOOL_FROM_DYE_COLOR
                    .forEach(
                            (color, wool) -> lootTableBiConsumer.accept(
                                    BuiltInLootTables.SHEAR_SHEEP_BY_DYE.get(color),
                                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(wool)))
                            )
                    );
        }
    }

    public static class EnhancementsEntitiesProvider extends Provider {
        CompletableFuture<HolderLookup.Provider> registryLookup;

        public EnhancementsEntitiesProvider(FabricDataOutput fabricDataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(fabricDataOutput, registryLookup, LootContextParamSets.ENTITY);
            this.registryLookup = registryLookup;
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            WOOL_FROM_DYE_COLOR
                    .forEach(
                            (color, wool) -> lootTableBiConsumer.accept(
                                    BuiltInLootTables.SHEEP_BY_DYE.get(color),
                                    LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(wool)))
                            )
                    );


        }
    }

    public static class EnhancementsGiftProvider extends Provider {
        CompletableFuture<HolderLookup.Provider> registryLookup;

        public EnhancementsGiftProvider(FabricDataOutput fabricDataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(fabricDataOutput, registryLookup, LootContextParamSets.GIFT);
            this.registryLookup = registryLookup;
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            lootTableBiConsumer.accept(BuiltInLootTables.CHICKEN_LAY,
                    LootTable.lootTable().withPool(LootPool.lootPool().add(
                            AlternativesEntry.alternatives(
                                    LootItem.lootTableItem(Items.EGG).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().components(DataComponentMatchers.Builder.components().exact(DataComponentExactPredicate.expect(DataComponents.CHICKEN_VARIANT, new EitherHolder<>(ChickenVariants.TEMPERATE))).build()))),
                                    LootItem.lootTableItem(Items.BROWN_EGG).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().components(DataComponentMatchers.Builder.components().exact(DataComponentExactPredicate.expect(DataComponents.CHICKEN_VARIANT, new EitherHolder<>(ChickenVariants.WARM))).build()))),
                                    LootItem.lootTableItem(Items.BLUE_EGG).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().components(DataComponentMatchers.Builder.components().exact(DataComponentExactPredicate.expect(DataComponents.CHICKEN_VARIANT, new EitherHolder<>(ChickenVariants.COLD))).build()))),
                                    LootItem.lootTableItem(VAItems.PURPLE_EGG).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().components(DataComponentMatchers.Builder.components().exact(DataComponentExactPredicate.expect(DataComponents.CHICKEN_VARIANT, new EitherHolder<>(ResourceKey.create(Registries.CHICKEN_VARIANT, idOf("enchanted"))))).build())))
                            )
                    ))
                    );


        }
    }

    protected static abstract class Provider extends SimpleFabricLootTableProvider {
        protected final HolderLookup.Provider registryLookup;

        public Provider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup, ContextKeySet contextType) {
            super(output, registryLookup, contextType);
            HolderLookup.Provider[] lookup = {null};
            registryLookup.thenAccept(wrapperLookup -> lookup[0] = wrapperLookup);
            this.registryLookup = lookup[0];
        }

        protected static LootPoolSingletonContainer.Builder<?> item(Item item) {
            return LootItem.lootTableItem(item);
        }

        protected static LootPoolSingletonContainer.Builder<?> item(Item item, NumberProvider count) {
            return LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(count));
        }

        protected static LootPoolSingletonContainer.Builder<?> item(Item item, int min, int max) {
            return LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(uniform(min, max)));
        }

        protected static LootPoolSingletonContainer.Builder<?> item(Item item, int count) {
            return LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(constant(count)));
        }

        protected static NumberProvider uniform(float min, float max) {
            return UniformGenerator.between(min, max);
        }

        protected static NumberProvider constant(float count) {
            return ConstantValue.exactly(count);
        }

        protected static LootItemFunction.Builder damage(float min, float max) {
            return SetItemDamageFunction.setDamage(uniform(min, max));
        }

        protected LootItemFunction.Builder enchant() {
            return EnchantRandomlyFunction.randomApplicableEnchantment(this.registryLookup);
        }

        protected LootItemFunction.Builder enchant(NumberProvider levels) {
            return EnchantWithLevelsFunction.enchantWithLevels(this.registryLookup, levels);
        }

        protected LootItemFunction.Builder enchant(int levels) {
            return EnchantWithLevelsFunction.enchantWithLevels(this.registryLookup, constant(levels));
        }

        protected LootItemFunction.Builder enchant(int levels, float probability) {
            return EnchantWithLevelsFunction.enchantWithLevels(this.registryLookup, constant(levels))
                    .when(LootItemRandomChanceCondition.randomChance(probability));
        }

        protected LootItemCondition.Builder chance(float probability) {
            return LootItemRandomChanceCondition.randomChance(probability);
        }

        protected static Map<DyeColor, ItemLike> WOOL_FROM_DYE_COLOR = Maps.newEnumMap(
                Map.ofEntries(
                        Map.entry(VADyeColors.CHARTREUSE, VABlocks.CHARTREUSE_WOOL),
                        Map.entry(VADyeColors.MAROON, VABlocks.MAROON_WOOL),
                        Map.entry(VADyeColors.INDIGO, VABlocks.INDIGO_WOOL),
                        Map.entry(VADyeColors.PLUM, VABlocks.PLUM_WOOL),
                        Map.entry(VADyeColors.VIRIDIAN, VABlocks.VIRIDIAN_WOOL),
                        Map.entry(VADyeColors.TAN, VABlocks.TAN_WOOL),
                        Map.entry(VADyeColors.SINOPIA, VABlocks.SINOPIA_WOOL),
                        Map.entry(VADyeColors.LILAC, VABlocks.LILAC_WOOL)
                )
        );
    }


}
