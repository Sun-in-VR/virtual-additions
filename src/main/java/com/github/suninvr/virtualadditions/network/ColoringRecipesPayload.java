package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import com.github.suninvr.virtualadditions.screen.ColoringStationMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;

public record ColoringRecipesPayload(List<ColoringStationMenu.ColoringRecipeData> list) implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, ColoringRecipesPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.<RegistryFriendlyByteBuf, ColoringStationMenu.ColoringRecipeData>list().apply(ColoringStationMenu.ColoringRecipeData.CODEC), ColoringRecipesPayload::list,
            ColoringRecipesPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return VAPackets.COLORING_STATION_S2C_ID;
    }
}
