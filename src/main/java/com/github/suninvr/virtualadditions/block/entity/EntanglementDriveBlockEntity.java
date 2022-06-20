package com.github.suninvr.virtualadditions.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EntanglementDriveBlockEntity extends BlockEntity {
    private int slot;
    private UUID playerId;
    private Inventory inventory;

    public EntanglementDriveBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntities.ENTANGLEMENT_DRIVE_BLOCK_ENTITY, pos, state);

        NbtCompound defaultTag = new NbtCompound();
        defaultTag.putInt("Slot", 0);
        this.readNbt(defaultTag);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("Slot")) this.slot = nbt.getInt("Slot");
        if (nbt.contains("UUID")) this.playerId = nbt.getUuid("UUID"); else this.playerId = null;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("Slot", this.slot);
        if (this.playerId != null) nbt.putUuid("UUID", this.playerId);
    }

    @Nullable
    public PlayerEntity getPlayer() {
        if (this.getWorld() == null) return null;
        if (this.playerId != null) return this.getWorld().getPlayerByUuid(this.playerId);
        return null;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setPlayer(@Nullable PlayerEntity player) {
        NbtCompound nbt = new NbtCompound();
        if (player != null) {
            nbt.putUuid("UUID", player.getUuid());
            nbt.putInt("Slot", player.getInventory().selectedSlot);
        }
        this.readNbt(nbt);
        this.markDirty();
    }

    public static <E extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, EntanglementDriveBlockEntity blockEntity) {
        world.updateComparators(pos, VABlocks.ENTANGLEMENT_DRIVE);
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public SidedInventory getInventory() {
        return (SidedInventory) this.inventory;
    }
}
