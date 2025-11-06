package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootTableProvider;
import net.minecraft.advancements.criterion.EntityEquipmentPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
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

        protected BaseProvider(FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generate() {
            LootTable.Builder lumwaspBuilder = LootTable.lootTable()
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(VAItems.LUMWASP_MANDIBLE)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)))
                            .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                    ))
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(VAItems.SILK_THREAD)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 2.0F)))
                            .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                    ))
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(VAItems.EXOSKELETON_ARMOR_TRIM_SMITHING_TEMPLATE)
                            .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.005F, 0.003F))
                    ));
            this.add(VAEntityType.LUMWASP, lumwaspBuilder);

            LootTable.Builder salineBuilder = LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                            .add(LootItem.lootTableItem(VAItems.ROCK_SALT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                    )
                    .withPool(LootPool.lootPool()
                            .add(LootItem.lootTableItem(VAItems.STEEL_INGOT)
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.025F, 0.01F))
                            )
                    );
            this.add(VAEntityType.SALINE, salineBuilder);

            LootTable.Builder spectreBuilder = LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .add(LootItem.lootTableItem(VAItems.SPECTRAL_POWDER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                    );
            this.add(VAEntityType.SPECTRE, spectreBuilder);
        }
    }

    protected static class PreviewProvider extends Provider {

        protected PreviewProvider(FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generate() {

        }
    }

    protected static class EnhancementsProvider extends Provider {

        protected EnhancementsProvider(FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generate() {
            HolderGetter<Item> registryEntryLookup = registries.lookupOrThrow(Registries.ITEM);

            LootTable.Builder witherSkeletonBuilder = LootTable.lootTable()
                    .withPool(LootPool.lootPool().add(commonDrop(Items.COAL, -1, 1)))
                    .withPool(LootPool.lootPool().add(commonDrop(Items.BONE, 0, 1)))
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.WITHER_SKELETON_SKULL)
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(AnyOfCondition.anyOf(
                                    LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.025F, 0.01F),
                                    AllOfCondition.allOf(
                                            LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.4F, 0.2F),
                                            LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().head(ItemPredicate.Builder.item().of(registryEntryLookup, Items.NETHERITE_HELMET))))
                                    )
                            ))

                    ));

            this.add(EntityType.WITHER_SKELETON, witherSkeletonBuilder);
        }
    }

    protected static abstract class Provider extends FabricEntityLootTableProvider {
        protected Provider(FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup);
        }

        protected LootItem.Builder commonDrop(Item item, int baseMin, int baseMax, int enchantedMin, int enchantedMax) {
            return LootItem.lootTableItem(item)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(baseMin, baseMax)))
                    .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(enchantedMin, enchantedMax)));
        }

        protected LootItem.Builder commonDrop(Item item, int baseMin, int baseMax) {
            return commonDrop(item, baseMin, baseMax, 0, 1);
        }
    }

}
