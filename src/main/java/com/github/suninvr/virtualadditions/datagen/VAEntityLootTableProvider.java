package com.github.suninvr.virtualadditions.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.data.loottable.EntityLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.util.context.ContextType;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class VAEntityLootTableProvider {
    protected static class Generator extends SimpleFabricLootTableProvider {

        public Generator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, ContextType contextType) {
            super(output, registryLookup, contextType);
        }

        @Override
        public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            lootTableBiConsumer.accept(
                    LootTables.SHEEP_SHEARING,
                    LootTable.builder()
                            .pool(EntityLootTableGenerator.createForSheep(LootTables.SHEEP_SHEARING_FROM_DYE_COLOR))
            );
        }
    }


}
