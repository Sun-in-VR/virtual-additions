package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VALootTables;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loottable.EntityLootTableGenerator;
import net.minecraft.entity.passive.ChickenVariants;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.component.ComponentMapPredicate;
import net.minecraft.predicate.component.ComponentsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.LazyRegistryEntryReference;
import net.minecraft.util.DyeColor;
import net.minecraft.util.context.ContextType;

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
        public BaseProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup, LootContextTypes.CHEST);
        }

        @Override
        public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            lootTableBiConsumer.accept(VALootTables.CEMETERY_GRAVES, LootTable.builder()
                    .pool(LootPool.builder().conditionally(RandomChanceLootCondition.builder(0.6F))
                            .with(item(Items.IRON_HELMET).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(7))
                            .with(item(Items.IRON_CHESTPLATE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(7))
                            .with(item(Items.IRON_LEGGINGS).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(7))
                            .with(item(Items.IRON_BOOTS).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(7))
                            .with(item(Items.IRON_SWORD).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(7))
                            .with(item(Items.IRON_SHOVEL).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(7))
                            .with(item(Items.IRON_PICKAXE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(7))
                            .with(item(Items.IRON_AXE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(7))
                            .with(item(Items.IRON_HOE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(7))
                            .with(item(VAItems.STEEL_HELMET).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(3))
                            .with(item(VAItems.STEEL_CHESTPLATE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(3))
                            .with(item(VAItems.STEEL_LEGGINGS).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(3))
                            .with(item(VAItems.STEEL_BOOTS).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(3))
                            .with(item(VAItems.STEEL_SWORD).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(3))
                            .with(item(VAItems.STEEL_SHOVEL).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(3))
                            .with(item(VAItems.STEEL_PICKAXE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(3))
                            .with(item(VAItems.STEEL_AXE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(3))
                            .with(item(VAItems.STEEL_HOE).apply(damage(0.15F, 0.8F)).apply(enchant(3, 0.4F)).weight(3))
                    )
                    .pool(LootPool.builder().rolls(uniform(2.0F, 5.0F))
                            .with(item(Items.BONE, 3, 10).weight(12))
                            .with(item(Items.ROTTEN_FLESH, 3, 10).weight(8))
                            .with(item(Items.EMERALD, 2, 8).weight(3))
                            .with(item(Items.LEATHER, 1, 5).weight(3))
                    )
            );

            lootTableBiConsumer.accept(VALootTables.CEMETERY, LootTable.builder(
                    ).pool(LootPool.builder().conditionally(chance(0.25F))
                            .with(item(Items.DIAMOND, 1, 3))
                            .with(item(VAItems.ROBE_ARMOR_TRIM_SMITHING_TEMPLATE))
                    ).pool(LootPool.builder().rolls(uniform(2.0F, 4.0F))
                            .with(item(VAItems.SPECTRAL_POWDER, 2, 8).weight(10))
                            .with(item(VAItems.SPECTRAL_TORCH, 2, 8).weight(8))
                            .with(item(VAItems.SPECTRAL_SAND, 2, 8).weight(5))
                            .with(item(VAItems.STEEL_INGOT, 2, 4).weight(2))
                    ).pool(LootPool.builder().rolls(uniform(2.0F, 5.0f))
                            .with(item(Items.LEATHER, 1, 5))
                            .with(item(Items.BONE, 1, 5))
                            .with(item(VAItems.BONE_LITTER, 2, 10))
                    )
            );
        }
    }

    public static class EnhancementsShearingProvider extends Provider {
        public EnhancementsShearingProvider(FabricDataOutput fabricDataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(fabricDataOutput, registryLookup, LootContextTypes.SHEARING);
        }

        @Override
        public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            lootTableBiConsumer.accept(
                LootTables.SHEEP_SHEARING,
                LootTable.builder()
                        .pool(EntityLootTableGenerator.createForSheep(LootTables.SHEEP_SHEARING_FROM_DYE_COLOR))
            );

            WOOL_FROM_DYE_COLOR
                    .forEach(
                            (color, wool) -> lootTableBiConsumer.accept(
                                    LootTables.SHEEP_SHEARING_FROM_DYE_COLOR.get(color),
                                    LootTable.builder().pool(LootPool.builder().rolls(UniformLootNumberProvider.create(1.0F, 3.0F)).with(ItemEntry.builder(wool)))
                            )
                    );
        }
    }

    public static class EnhancementsEntitiesProvider extends Provider {
        CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

        public EnhancementsEntitiesProvider(FabricDataOutput fabricDataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(fabricDataOutput, registryLookup, LootContextTypes.ENTITY);
            this.registryLookup = registryLookup;
        }

        @Override
        public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            WOOL_FROM_DYE_COLOR
                    .forEach(
                            (color, wool) -> lootTableBiConsumer.accept(
                                    LootTables.SHEEP_DROPS_FROM_DYE_COLOR.get(color),
                                    LootTable.builder().pool(LootPool.builder().with(ItemEntry.builder(wool)))
                            )
                    );


        }
    }

    public static class EnhancementsGiftProvider extends Provider {
        CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

        public EnhancementsGiftProvider(FabricDataOutput fabricDataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(fabricDataOutput, registryLookup, LootContextTypes.GIFT);
            this.registryLookup = registryLookup;
        }

        @Override
        public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            lootTableBiConsumer.accept(LootTables.CHICKEN_LAY_GAMEPLAY,
                    LootTable.builder().pool(LootPool.builder().with(
                            AlternativeEntry.builder(
                                    ItemEntry.builder(Items.EGG).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().components(ComponentsPredicate.Builder.create().exact(ComponentMapPredicate.of(DataComponentTypes.CHICKEN_VARIANT, new LazyRegistryEntryReference<>(ChickenVariants.TEMPERATE))).build()))),
                                    ItemEntry.builder(Items.BROWN_EGG).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().components(ComponentsPredicate.Builder.create().exact(ComponentMapPredicate.of(DataComponentTypes.CHICKEN_VARIANT, new LazyRegistryEntryReference<>(ChickenVariants.WARM))).build()))),
                                    ItemEntry.builder(Items.BLUE_EGG).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().components(ComponentsPredicate.Builder.create().exact(ComponentMapPredicate.of(DataComponentTypes.CHICKEN_VARIANT, new LazyRegistryEntryReference<>(ChickenVariants.COLD))).build()))),
                                    ItemEntry.builder(VAItems.PURPLE_EGG).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().components(ComponentsPredicate.Builder.create().exact(ComponentMapPredicate.of(DataComponentTypes.CHICKEN_VARIANT, new LazyRegistryEntryReference<>(RegistryKey.of(RegistryKeys.CHICKEN_VARIANT, idOf("enchanted"))))).build())))
                            )
                    ))
                    );


        }
    }

    protected static abstract class Provider extends SimpleFabricLootTableProvider {
        protected final RegistryWrapper.WrapperLookup registryLookup;

        public Provider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, ContextType contextType) {
            super(output, registryLookup, contextType);
            RegistryWrapper.WrapperLookup[] lookup = {null};
            registryLookup.thenAccept(wrapperLookup -> lookup[0] = wrapperLookup);
            this.registryLookup = lookup[0];
        }

        protected static LeafEntry.Builder<?> item(Item item) {
            return ItemEntry.builder(item);
        }

        protected static LeafEntry.Builder<?> item(Item item, LootNumberProvider count) {
            return ItemEntry.builder(item).apply(SetCountLootFunction.builder(count));
        }

        protected static LeafEntry.Builder<?> item(Item item, int min, int max) {
            return ItemEntry.builder(item).apply(SetCountLootFunction.builder(uniform(min, max)));
        }

        protected static LeafEntry.Builder<?> item(Item item, int count) {
            return ItemEntry.builder(item).apply(SetCountLootFunction.builder(constant(count)));
        }

        protected static LootNumberProvider uniform(float min, float max) {
            return UniformLootNumberProvider.create(min, max);
        }

        protected static LootNumberProvider constant(float count) {
            return ConstantLootNumberProvider.create(count);
        }

        protected static LootFunction.Builder damage(float min, float max) {
            return SetDamageLootFunction.builder(uniform(min, max));
        }

        protected LootFunction.Builder enchant() {
            return EnchantRandomlyLootFunction.builder(this.registryLookup);
        }

        protected LootFunction.Builder enchant(LootNumberProvider levels) {
            return EnchantWithLevelsLootFunction.builder(this.registryLookup, levels);
        }

        protected LootFunction.Builder enchant(int levels) {
            return EnchantWithLevelsLootFunction.builder(this.registryLookup, constant(levels));
        }

        protected LootFunction.Builder enchant(int levels, float probability) {
            return EnchantWithLevelsLootFunction.builder(this.registryLookup, constant(levels))
                    .conditionally(RandomChanceLootCondition.builder(probability));
        }

        protected LootCondition.Builder chance(float probability) {
            return RandomChanceLootCondition.builder(probability);
        }

        protected static Map<DyeColor, ItemConvertible> WOOL_FROM_DYE_COLOR = Maps.newEnumMap(
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
