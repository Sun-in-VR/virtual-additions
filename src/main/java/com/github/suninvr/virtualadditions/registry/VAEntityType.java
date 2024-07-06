package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.Heightmap;

import java.util.Map;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEntityType {

    public static final EntityType<ClimbingRopeEntity> CLIMBING_ROPE;
    public static final EntityType<SteelBombEntity> STEEL_BOMB;
    public static final EntityType<AcidSpitEntity> ACID_SPIT;
    public static final EntityType<LumwaspEntity> LUMWASP;
    public static final EntityType<LyftEntity> LYFT;
    public static final EntityType<SalineEntity> SALINE;

    public static final Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> ENTITY_ATTRIBUTES = new java.util.HashMap<>();

    static {
        CLIMBING_ROPE = Registry.register(Registries.ENTITY_TYPE, VirtualAdditions.idOf("climbing_rope"), EntityType.Builder.<ClimbingRopeEntity>create(ClimbingRopeEntity::new, SpawnGroup.MISC)
                .dimensions(0.25F, 0.25F)
                .maxTrackingRange(4).trackingTickInterval(10)
                .build());

        STEEL_BOMB = Registry.register(Registries.ENTITY_TYPE, idOf("steel_bomb"), EntityType.Builder.<SteelBombEntity>create(SteelBombEntity::new, SpawnGroup.MISC)
                .dimensions(0.25F, 0.25F)
                .maxTrackingRange(4).trackingTickInterval(10)
                .build());

        ACID_SPIT = Registry.register(Registries.ENTITY_TYPE, idOf("acid_spit"), EntityType.Builder.<AcidSpitEntity>create(AcidSpitEntity::new, SpawnGroup.MISC)
                .dimensions(0.25F, 0.25F)
                .maxTrackingRange(4).trackingTickInterval(10)
                .build());

        LUMWASP = Registry.register(Registries.ENTITY_TYPE, idOf("lumwasp"), EntityType.Builder.create(LumwaspEntity::new, SpawnGroup.MONSTER)
                .dimensions(1.5F, 0.75F)
                .build());

        LYFT = Registry.register(Registries.ENTITY_TYPE, idOf("lyft"), EntityType.Builder.create(LyftEntity::new, SpawnGroup.CREATURE)
                .dimensions(1.0F, 0.7F)
                .requires(VirtualAdditions.PREVIEW)
                .build());

        SALINE = Registry.register(Registries.ENTITY_TYPE, idOf("saline"), EntityType.Builder.create(SalineEntity::new, SpawnGroup.MONSTER)
                .dimensions(0.6F, 1.95F).maxTrackingRange(8)
                .build());

        ENTITY_ATTRIBUTES.put(LUMWASP, LumwaspEntity.createLumwaspAttributes());
        ENTITY_ATTRIBUTES.put(LYFT, LyftEntity.createLyftAttributes());
        ENTITY_ATTRIBUTES.put(SALINE, SalineEntity.createSalineAttributes());
    }

    public static void init(){
        SpawnRestriction.register(SALINE, SpawnRestriction.getLocation(SALINE), Heightmap.Type.MOTION_BLOCKING, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(LUMWASP, SpawnRestriction.getLocation(LUMWASP), Heightmap.Type.MOTION_BLOCKING, LumwaspEntity::canSpawnInDark);
        SpawnRestriction.register(LYFT, SpawnRestriction.getLocation(LYFT), Heightmap.Type.MOTION_BLOCKING, ((type, world, spawnReason, pos, random) -> SpawnReason.isTrialSpawner(spawnReason)||world.getBlockState(pos.down()).isOf(VABlocks.GRASSY_FLOATROCK)  ));
    }
}
