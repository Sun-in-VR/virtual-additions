package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootTableProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class VAEntityLootTableProvider {

    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> base() {
        return BaseProvider::new;
    }
    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> preview() {
        return PreviewProvider::new;
    }
    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> enhancements() {
        return EnhancementsProvider::new;
    }

    protected static class BaseProvider extends Provider {

        protected BaseProvider(FabricDataOutput output, @NotNull CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generate() {
            LootTable.Builder lumwaspBuilder = LootTable.builder()
                    .pool(LootPool.builder().with(ItemEntry.builder(VAItems.LUMWASP_MANDIBLE)
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-1.0F, 1.0F)))
                            .apply(EnchantedCountIncreaseLootFunction.builder(this.registries, UniformLootNumberProvider.create(0.0F, 1.0F)))
                    ))
                    .pool(LootPool.builder().with(ItemEntry.builder(VAItems.SILK_THREAD)
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-1.0F, 2.0F)))
                            .apply(EnchantedCountIncreaseLootFunction.builder(this.registries, UniformLootNumberProvider.create(0.0F, 1.0F)))
                    ))
                    .pool(LootPool.builder().with(ItemEntry.builder(VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE)
                            .conditionally(RandomChanceWithEnchantedBonusLootCondition.builder(this.registries, 0.005F, 0.003F))
                    ));
            this.register(VAEntityType.LUMWASP, lumwaspBuilder);

            LootTable.Builder salineBuilder = LootTable.builder()
                    .pool(LootPool.builder()
                            .with(ItemEntry.builder(Items.ROTTEN_FLESH)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseLootFunction.builder(this.registries, UniformLootNumberProvider.create(0.0F, 1.0F))))
                            .with(ItemEntry.builder(VAItems.ROCK_SALT)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseLootFunction.builder(this.registries, UniformLootNumberProvider.create(0.0F, 1.0F))))
                    )
                    .pool(LootPool.builder()
                            .with(ItemEntry.builder(VAItems.STEEL_INGOT)
                                .conditionally(KilledByPlayerLootCondition.builder())
                                .conditionally(RandomChanceWithEnchantedBonusLootCondition.builder(this.registries, 0.025F, 0.01F))
                            )
                    );
            this.register(VAEntityType.SALINE, salineBuilder);

            LootTable.Builder spectreBuilder = LootTable.builder()
                    .pool(LootPool.builder()
                            .with(ItemEntry.builder(VAItems.SPECTRAL_POWDER)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-1.0F, 1.0F)))
                                .apply(EnchantedCountIncreaseLootFunction.builder(this.registries, UniformLootNumberProvider.create(0.0F, 1.0F))))
                    );
            this.register(VAEntityType.SPECTRE, spectreBuilder);
        }
    }

    protected static class PreviewProvider extends Provider {

        protected PreviewProvider(FabricDataOutput output, @NotNull CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generate() {

        }
    }

    protected static class EnhancementsProvider extends Provider {

        protected EnhancementsProvider(FabricDataOutput output, @NotNull CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generate() {
            RegistryEntryLookup<Item> registryEntryLookup = registries.getOrThrow(RegistryKeys.ITEM);

            LootTable.Builder witherSkeletonBuilder = LootTable.builder()
                    .pool(LootPool.builder().with(commonDrop(Items.COAL, -1, 1)))
                    .pool(LootPool.builder().with(commonDrop(Items.BONE, 0, 1)))
                    .pool(LootPool.builder().with(ItemEntry.builder(Items.WITHER_SKELETON_SKULL)
                            .conditionally(KilledByPlayerLootCondition.builder())
                            .conditionally(AnyOfLootCondition.builder(
                                    RandomChanceWithEnchantedBonusLootCondition.builder(registries, 0.025F, 0.01F),
                                    AllOfLootCondition.builder(
                                            RandomChanceWithEnchantedBonusLootCondition.builder(registries, 0.4F, 0.2F),
                                            EntityPropertiesLootCondition.builder(LootContext.EntityReference.THIS, EntityPredicate.Builder.create().equipment(EntityEquipmentPredicate.Builder.create().head(ItemPredicate.Builder.create().items(registryEntryLookup, Items.NETHERITE_HELMET))))
                                    )
                            ))

                    ));

            this.register(EntityType.WITHER_SKELETON, witherSkeletonBuilder);
        }
    }

    protected static abstract class Provider extends FabricEntityLootTableProvider {
        protected Provider(FabricDataOutput output, @NotNull CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }

        protected ItemEntry.Builder commonDrop(Item item, int baseMin, int baseMax, int enchantedMin, int enchantedMax) {
            return ItemEntry.builder(item)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(baseMin, baseMax)))
                    .apply(EnchantedCountIncreaseLootFunction.builder(registries, UniformLootNumberProvider.create(enchantedMin, enchantedMax)));
        }

        protected ItemEntry.Builder commonDrop(Item item, int baseMin, int baseMax) {
            return commonDrop(item, baseMin, baseMax, 0, 1);
        }
    }

}
