package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loottable.EntityLootTableGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.DyeColor;
import net.minecraft.util.context.ContextType;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class VASimpleLootTableProvider {

    public static FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider> enhancementsShearing() {return EnhancementsShearingProvider::new;}
    public static FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider> enhancementsEntities() {return EnhancementsEntitiesProvider::new;}

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

    protected static abstract class Provider extends SimpleFabricLootTableProvider {

        public Provider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, ContextType contextType) {
            super(output, registryLookup, contextType);
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
