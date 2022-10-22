package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import com.github.suninvr.virtualadditions.entity.SteelBombEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEntityType {
    public static void init(){}

    public static final EntityType<ClimbingRopeEntity> CLIMBING_ROPE;

    public static final EntityType<SteelBombEntity> STEEL_BOMB;

    static {
        CLIMBING_ROPE = Registry.register(Registry.ENTITY_TYPE, VirtualAdditions.idOf("climbing_rope"), FabricEntityTypeBuilder.<ClimbingRopeEntity>create(SpawnGroup.MISC, ClimbingRopeEntity::new)
                        .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                        .trackRangeBlocks(4).trackedUpdateRate(10)
                        .build());

        STEEL_BOMB = Registry.register(Registry.ENTITY_TYPE, idOf("steel_bomb"), FabricEntityTypeBuilder.<SteelBombEntity>create(SpawnGroup.MISC, SteelBombEntity::new)
                        .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                        .trackRangeBlocks(4).trackedUpdateRate(10)
                        .build());
    }
}
