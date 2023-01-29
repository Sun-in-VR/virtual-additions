package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.AcidSpitEntity;
import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import com.github.suninvr.virtualadditions.entity.SteelBombEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.world.Heightmap;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEntityType {
    public static void init(){
        FabricDefaultAttributeRegistry.register(LUMWASP, LumwaspEntity.createLumwaspAttributes());
        SpawnRestriction.register(LUMWASP, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
    }

    public static final EntityType<ClimbingRopeEntity> CLIMBING_ROPE;
    public static final EntityType<SteelBombEntity> STEEL_BOMB;
    public static final EntityType<AcidSpitEntity> ACID_SPIT;
    public static final EntityType<LumwaspEntity> LUMWASP;

    static {
        CLIMBING_ROPE = Registry.register(Registries.ENTITY_TYPE, VirtualAdditions.idOf("climbing_rope"), FabricEntityTypeBuilder.<ClimbingRopeEntity>create(SpawnGroup.MISC, ClimbingRopeEntity::new)
                .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                .trackRangeBlocks(4).trackedUpdateRate(10)
                .build());

        STEEL_BOMB = Registry.register(Registries.ENTITY_TYPE, idOf("steel_bomb"), FabricEntityTypeBuilder.<SteelBombEntity>create(SpawnGroup.MISC, SteelBombEntity::new)
                .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                .trackRangeBlocks(4).trackedUpdateRate(10)
                .build());

        ACID_SPIT = Registry.register(Registries.ENTITY_TYPE, idOf("acid_spit"), FabricEntityTypeBuilder.<AcidSpitEntity>create(SpawnGroup.MISC, AcidSpitEntity::new)
                .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                .trackRangeBlocks(4).trackedUpdateRate(10)
                .requires(FeatureFlags.UPDATE_1_20)
                .build());

        LUMWASP = Registry.register(Registries.ENTITY_TYPE, idOf("lumwasp"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, LumwaspEntity::new)
                .dimensions(EntityDimensions.fixed(1.5F, 0.75F))
                .requires(FeatureFlags.UPDATE_1_20)
                .build());
    }
}
