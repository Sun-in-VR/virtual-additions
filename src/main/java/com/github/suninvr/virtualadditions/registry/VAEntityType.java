package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.Heightmap;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEntityType {

    public static final EntityType<ClimbingRopeEntity> CLIMBING_ROPE;
    public static final EntityType<SteelBombEntity> STEEL_BOMB;
    public static final EntityType<AcidSpitEntity> ACID_SPIT;
    public static final EntityType<LumwaspEntity> LUMWASP;
    public static final EntityType<LyftEntity> LYFT;
    public static final EntityType<SalineEntity> SALINE;

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
                .build());

        LUMWASP = Registry.register(Registries.ENTITY_TYPE, idOf("lumwasp"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, LumwaspEntity::new)
                .dimensions(EntityDimensions.fixed(1.5F, 0.75F))
                .build());

        LYFT = Registry.register(Registries.ENTITY_TYPE, idOf("lyft"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, LyftEntity::new)
                .dimensions(EntityDimensions.fixed(1.0F, 0.7F).scaled(0.68F))
                .requires(VirtualAdditions.PREVIEW)
                .build());

        SALINE = Registry.register(Registries.ENTITY_TYPE, idOf("saline"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SalineEntity::new)
                .dimensions(EntityDimensions.fixed(0.6F, 1.95F)).trackRangeBlocks(8)
                .build());
    }

    public static void init(){
        FabricDefaultAttributeRegistry.register(SALINE, SalineEntity.createSalineAttributes());
        FabricDefaultAttributeRegistry.register(LUMWASP, LumwaspEntity.createLumwaspAttributes());
        FabricDefaultAttributeRegistry.register(LYFT, LyftEntity.createLyftAttributes());
        SpawnRestriction.register(SALINE, SpawnRestriction.getLocation(SALINE), Heightmap.Type.MOTION_BLOCKING, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(LUMWASP, SpawnRestriction.getLocation(LUMWASP), Heightmap.Type.MOTION_BLOCKING, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(LYFT, SpawnRestriction.getLocation(LYFT), Heightmap.Type.MOTION_BLOCKING, ((type, world, spawnReason, pos, random) -> SpawnReason.isTrialSpawner(spawnReason)||world.getBlockState(pos.down()).isOf(VABlocks.GRASSY_FLOATROCK)  ));
    }
}
