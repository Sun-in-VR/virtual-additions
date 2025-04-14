package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
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
        getOrCreateTagBuilder(EntityTypeTags.UNDEAD).add(VAEntityType.SALINE);
        getOrCreateTagBuilder(VAEntityTypeTags.SPECTRE_BUFF_TARGETS)
                .add(
                        EntityType.SPIDER,
                        VAEntityType.LUMWASP,
                        EntityType.ZOMBIE,
                        EntityType.DROWNED,
                        EntityType.HUSK,
                        VAEntityType.SALINE,
                        EntityType.SKELETON,
                        EntityType.WITHER_SKELETON,
                        EntityType.STRAY,
                        EntityType.BOGGED,
                        EntityType.BLAZE,
                        EntityType.BREEZE,
                        EntityType.GHAST
                ).addOptionalTag(EntityTypeTags.ILLAGER);
        getOrCreateTagBuilder(VAEntityTypeTags.PASSES_THROUGH_WEBBED_SILK)
                .add(EntityType.ITEM, EntityType.EXPERIENCE_ORB)
                .addOptionalTag(EntityTypeTags.IMPACT_PROJECTILES);
    }
}
