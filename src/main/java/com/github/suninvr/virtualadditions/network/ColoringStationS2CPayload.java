package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import com.github.suninvr.virtualadditions.screen.ColoringStationScreenHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;

public record ColoringStationS2CPayload(List<ColoringStationScreenHandler.ColoringRecipeData> list) implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, ColoringStationS2CPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.<RegistryFriendlyByteBuf, ColoringStationScreenHandler.ColoringRecipeData>list().apply(ColoringStationScreenHandler.ColoringRecipeData.CODEC), ColoringStationS2CPayload::list,
            ColoringStationS2CPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return VAPackets.COLORING_STATION_S2C_ID;
    }
}
