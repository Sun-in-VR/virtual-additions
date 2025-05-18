package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.TagBuilder;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class VAEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public VAEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
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
                ).addOptionalTag(EntityTypeTags.ILLAGER.id());
        addTo(VAEntityTypeTags.PASSES_THROUGH_WEBBED_SILK,
                EntityType.ITEM, EntityType.EXPERIENCE_ORB)
                .addOptionalTag(EntityTypeTags.IMPACT_PROJECTILES.id());
    }

    protected TagBuilder addTo(TagKey<EntityType<?>> tag, EntityType<?>... types) {
        TagBuilder builder = getTagBuilder(tag);
        for (EntityType<?> type : types) {
            builder.add(Registries.ENTITY_TYPE.getId(type));
        }
        return builder;
    }
}
