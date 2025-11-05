package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

public class EntanglementDriveC2SPayload implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, EntanglementDriveC2SPayload> CODEC = StreamCodec.ofMember(EntanglementDriveC2SPayload::write, EntanglementDriveC2SPayload::new);
    private final int slotIndex;
    private final UUID playerId;

    private EntanglementDriveC2SPayload(FriendlyByteBuf buf) {
        this.slotIndex = buf.readInt();
        this.playerId = buf.readUUID();
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(this.slotIndex);
        buf.writeUUID(this.playerId);
    }

    public EntanglementDriveC2SPayload(int slotIndex, UUID playerId) {
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
