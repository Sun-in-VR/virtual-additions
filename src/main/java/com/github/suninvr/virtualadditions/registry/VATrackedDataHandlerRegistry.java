package com.github.suninvr.virtualadditions.registry;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.Uuids;

import java.util.UUID;

public class VATrackedDataHandlerRegistry {
    public static final TrackedDataHandler<UUID> UUID = TrackedDataHandler.create(Uuids.PACKET_CODEC);

    public static void init(){
        TrackedDataHandlerRegistry.register(UUID);
    }
}
