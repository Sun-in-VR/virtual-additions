package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.Uuids;

import java.util.UUID;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VATrackedDataHandlerRegistry {
    public static final TrackedDataHandler<UUID> UUID = TrackedDataHandler.create(Uuids.PACKET_CODEC);

    public static void init(){
        FabricTrackedDataRegistry.register(idOf("uuid"), UUID);
    }
}
