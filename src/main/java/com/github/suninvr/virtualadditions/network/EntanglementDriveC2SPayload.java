package com.github.suninvr.virtualadditions.network;

import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.UUID;

public class EntanglementDriveC2SPayload implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, EntanglementDriveC2SPayload> CODEC = PacketCodec.of(EntanglementDriveC2SPayload::write, EntanglementDriveC2SPayload::new);
    private final int slotIndex;
    private final UUID playerId;

    private EntanglementDriveC2SPayload(PacketByteBuf buf) {
        this.slotIndex = buf.readInt();
        this.playerId = buf.readUuid();
    }

    private void write(PacketByteBuf buf) {
        buf.writeInt(this.slotIndex);
        buf.writeUuid(this.playerId);
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
    public Id<? extends CustomPayload> getId() {
        return VAPackets.ENTANGLEMENT_DRIVE_C2S_ID;
    }
}
