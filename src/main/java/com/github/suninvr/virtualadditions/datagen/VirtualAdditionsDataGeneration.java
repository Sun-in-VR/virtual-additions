package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VirtualAdditionsDataGeneration implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        VirtualAdditions.isDataGenerationActive = true;

        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(VAItemTagProvider.base());
        pack.addProvider(VABlockTagProvider.base());
        pack.addProvider(VARecipeProvider.base());
        pack.addProvider(VABlockLootTableProvider.base());
        pack.addProvider(VAEntityLootTableProvider.base());
        pack.addProvider(VAEntityTypeTagProvider::new);
        pack.addProvider(VAModelProvider.base());
        pack.addProvider(VASimpleLootTableProvider.base());
        pack.addProvider(VAAdvancementProvider.base());

        FabricDataGenerator.Pack preview = fabricDataGenerator.createBuiltinResourcePack(idOf("preview"));
        preview.addProvider(VAItemTagProvider.preview());
        preview.addProvider(VABlockTagProvider.preview());
        preview.addProvider(VARecipeProvider.preview());
        preview.addProvider(VABlockLootTableProvider.preview());

        FabricDataGenerator.Pack enhancementsResources = fabricDataGenerator.createBuiltinResourcePack(idOf("enhancements_resources"));
        enhancementsResources.addProvider(VAModelProvider.enhancements());

        FabricDataGenerator.Pack enhancementsData = fabricDataGenerator.createBuiltinResourcePack(idOf("enhancements_data"));
        enhancementsData.addProvider(VASimpleLootTableProvider.enhancementsEntities());
        enhancementsData.addProvider(VAEntityLootTableProvider.enhancements());
        enhancementsData.addProvider(VASimpleLootTableProvider.enhancementsShearing());
        enhancementsData.addProvider(VASimpleLootTableProvider.enhancementsGift());
    }

}
