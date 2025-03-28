package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceWithEnchantedBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
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

    protected static abstract class Provider extends FabricEntityLootTableProvider {
        protected Provider(FabricDataOutput output, @NotNull CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }
    }

}
