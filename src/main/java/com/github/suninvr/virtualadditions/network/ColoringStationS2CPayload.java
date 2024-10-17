package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import com.github.suninvr.virtualadditions.screen.ColoringStationScreenHandler;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

import java.util.List;

public record ColoringStationS2CPayload(List<ColoringStationScreenHandler.ColoringRecipeData> list) implements CustomPayload {
    public static final PacketCodec<RegistryByteBuf, ColoringStationS2CPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.<RegistryByteBuf, ColoringStationScreenHandler.ColoringRecipeData>toList().apply(ColoringStationScreenHandler.ColoringRecipeData.CODEC), ColoringStationS2CPayload::list,
            ColoringStationS2CPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return VAPackets.COLORING_STATION_S2C_ID;
    }
}
