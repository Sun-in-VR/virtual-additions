package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

public class SetEntangledSlotPayload implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, SetEntangledSlotPayload> CODEC = StreamCodec.ofMember(SetEntangledSlotPayload::write, SetEntangledSlotPayload::new);
    private final int slotIndex;
    private final UUID playerId;

    private SetEntangledSlotPayload(FriendlyByteBuf buf) {
        this.slotIndex = buf.readInt();
        this.playerId = buf.readUUID();
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(this.slotIndex);
        buf.writeUUID(this.playerId);
    }

    public SetEntangledSlotPayload(int slotIndex, UUID playerId) {
        this.slotIndex = slotIndex;
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return this.playerId;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return VAPackets.ENTANGLEMENT_DRIVE_C2S_ID;
    }
}
