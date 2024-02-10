package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class VAEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public VAEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(EntityTypeTags.IMPACT_PROJECTILES).add(VAEntityType.STEEL_BOMB, VAEntityType.CLIMBING_ROPE, VAEntityType.ACID_SPIT);
        getOrCreateTagBuilder(EntityTypeTags.ARTHROPOD).add(VAEntityType.LUMWASP);
    }
}
