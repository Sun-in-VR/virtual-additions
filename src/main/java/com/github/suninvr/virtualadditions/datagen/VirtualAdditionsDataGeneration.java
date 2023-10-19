package com.github.suninvr.virtualadditions.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VirtualAdditionsDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(VAItemTagProvider.INSTANCE.base());
        pack.addProvider(VABlockTagProvider.INSTANCE.base());
        pack.addProvider(VAEntityTypeTagProvider::new);
        pack.addProvider(VARecipeProvider.INSTANCE.base());
        pack.addProvider(VABlockLootTableProvider.INSTANCE.base());
        pack.addProvider(VAModelProvider::new);

        FabricDataGenerator.Pack preview = fabricDataGenerator.createBuiltinResourcePack(idOf("preview"));
        preview.addProvider(VAItemTagProvider.INSTANCE.preview());
        preview.addProvider(VABlockTagProvider.INSTANCE.preview());
        preview.addProvider(VARecipeProvider.INSTANCE.preview());
        preview.addProvider(VABlockLootTableProvider.INSTANCE.preview());
    }

}
