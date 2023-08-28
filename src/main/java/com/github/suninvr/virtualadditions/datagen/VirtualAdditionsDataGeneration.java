package com.github.suninvr.virtualadditions.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class VirtualAdditionsDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(VAItemTagProvider::new);
        pack.addProvider(VABlockTagProvider::new);
        pack.addProvider(VAEntityTypeTagProvider::new);
        pack.addProvider(VAModelProvider::new);
        pack.addProvider(VARecipeProvider::new);
        pack.addProvider(VABlockLootTableProvider::new);
    }

}
