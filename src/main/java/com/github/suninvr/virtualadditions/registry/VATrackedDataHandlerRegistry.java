package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.UUID;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VATrackedDataHandlerRegistry {
    public static final EntityDataSerializer<UUID> UUID = EntityDataSerializer.forValueType(UUIDUtil.STREAM_CODEC);

    public static void init(){
        FabricTrackedDataRegistry.register(idOf("uuid"), UUID);
    }
}
