package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class VAEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public VAEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        addTo(EntityTypeTags.IMPACT_PROJECTILES, VAEntityType.STEEL_BOMB, VAEntityType.CLIMBING_ROPE, VAEntityType.ACID_SPIT);
        addTo(EntityTypeTags.ARTHROPOD, VAEntityType.LUMWASP);
        addTo(EntityTypeTags.UNDEAD, VAEntityType.SALINE);
        addTo(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES, VAEntityType.SPECTRE);
        addTo(VAEntityTypeTags.IGNORES_SPRING_LOTUS,
                        EntityType.BEE,
                        EntityType.FROG
        );
        addTo(VAEntityTypeTags.SPECTRE_BUFF_TARGETS,
                        EntityType.BLAZE,
                        EntityType.BOGGED,
                        EntityType.BREEZE,
                        EntityType.CAVE_SPIDER,
                        EntityType.CREAKING,
                        EntityType.DROWNED,
                        EntityType.HUSK,
                        VAEntityType.LUMWASP,
                        EntityType.MAGMA_CUBE,
                        EntityType.RAVAGER,
                        VAEntityType.SALINE,
                        EntityType.SKELETON,
                        EntityType.SLIME,
                        EntityType.SPIDER,
                        EntityType.STRAY,
                        EntityType.WITCH,
                        EntityType.WITHER_SKELETON,
                        EntityType.ZOMBIE
                ).addOptionalTag(EntityTypeTags.ILLAGER.location());
        addTo(VAEntityTypeTags.PASSES_THROUGH_WEBBED_SILK,
                EntityType.ITEM, EntityType.EXPERIENCE_ORB)
                .addOptionalTag(EntityTypeTags.IMPACT_PROJECTILES.location());
    }

    protected TagBuilder addTo(TagKey<EntityType<?>> tag, EntityType<?>... types) {
        TagBuilder builder = getOrCreateRawBuilder(tag);
        for (EntityType<?> type : types) {
            builder.addElement(BuiltInRegistries.ENTITY_TYPE.getKey(type));
        }
        return builder;
    }
}
