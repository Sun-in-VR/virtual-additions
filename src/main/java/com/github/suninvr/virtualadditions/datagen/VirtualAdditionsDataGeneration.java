package com.github.suninvr.virtualadditions.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VirtualAdditionsDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(VAItemTagProvider.base());
        pack.addProvider(VABlockTagProvider.base());
        pack.addProvider(VARecipeProvider.base());
        pack.addProvider(VABlockLootTableProvider.base());
        pack.addProvider(VAEntityTypeTagProvider::new);
        pack.addProvider(VAModelProvider::new);

        FabricDataGenerator.Pack preview = fabricDataGenerator.createBuiltinResourcePack(idOf("preview"));
        preview.addProvider(VAItemTagProvider.preview());
        preview.addProvider(VABlockTagProvider.preview());
        preview.addProvider(VARecipeProvider.preview());
        preview.addProvider(VABlockLootTableProvider.preview());
    }

}
