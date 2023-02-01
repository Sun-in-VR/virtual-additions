package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class EntanglementDriveBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory {
    private static final Text TITLE = Text.translatable("container.virtual_additions.entanglement_drive");
    private int slotId;
    private int slotIndex;
    private UUID playerId;
    private final Inventory dummyInventory = new DummyInventory();
    private static final UUID nullId = UUID.fromString("0-0-0-0-0");

    public EntanglementDriveBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntities.ENTANGLEMENT_DRIVE_BLOCK_ENTITY, pos, state);
        NbtCompound defaultTag = new NbtCompound();
        defaultTag.putInt("SlotId", 0);
        defaultTag.putInt("SlotIndex", 0);
        defaultTag.putUuid("UUID", nullId);
        this.readNbt(defaultTag);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("SlotId")) this.slotId = nbt.getInt("SlotId");
        if (nbt.contains("SlotIndex")) this.slotIndex = nbt.getInt("SlotIndex");
        if (nbt.contains("UUID")) this.playerId = nbt.getUuid("UUID"); else this.playerId = null;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("SlotId", this.slotId);
        nbt.putInt("SlotIndex", this.slotIndex);
        nbt.putUuid("UUID", this.playerId);
    }

    @Nullable
    public PlayerEntity getPlayer() {
        if (this.getWorld() == null || this.getWorld().isClient()) return null;
        if (this.playerId != null) return this.getWorld().getServer().getPlayerManager().getPlayer(this.playerId);
        return null;
    }

    @Nullable
    private PlayerInventory getPlayerInventory() {
        if (this.getPlayer() == null) return null;
        return this.getPlayer().getInventory();
    }

    public int getSlotId() {
        return this.slotId;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public void setPlayerSlot(@Nullable PlayerEntity player, int slotIndex, int slotId) {
        if (player != null) {
            if (this.world != null && this.world.isClient()) return;
            NbtCompound nbt = new NbtCompound();
            UUID uuid = player.getUuid();
            nbt.putUuid("UUID", uuid);
            nbt.putInt("SlotId", slotId);
            nbt.putInt("SlotIndex", slotIndex);
            this.readNbt(nbt);
            this.markDirty();
        }
    }

    public static <E extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, EntanglementDriveBlockEntity blockEntity) {
        world.updateComparators(pos, VABlocks.ENTANGLEMENT_DRIVE);
    }

    public SidedInventory getInventory() {
        return this;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new EntanglementDriveScreenHandler(syncId, inv, ScreenHandlerContext.create(this.world, this.pos));
    }

    public void writeScreenData(PacketByteBuf buf) {
        buf.writeInt(this.slotId);
        buf.writeInt(this.slotIndex);
        buf.writeOptional(Optional.ofNullable(this.playerId), PacketByteBuf::writeUuid);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        writeScreenData(buf);
    }

    @Override
    public Text getDisplayName() {
        return TITLE;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[]{0};
    }

    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return dir == Direction.UP && this.canModifyPlayerInventory();
    }

    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return dir == Direction.DOWN && this.canModifyPlayerInventory();
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        if (this.getPlayerInventory() == null) return dummyInventory.isEmpty();
        return this.getPlayerInventory().getStack(this.getSlotIndex()) == ItemStack.EMPTY;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (this.getPlayerInventory() == null) return dummyInventory.getStack(slot);
        return this.getPlayerInventory().getStack(this.getSlotIndex());
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (this.getPlayerInventory() == null) return dummyInventory.removeStack(slot);
        this.markDirty();
        return this.getPlayerInventory().removeStack(this.getSlotIndex(), amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (this.getPlayerInventory() == null) return ItemStack.EMPTY;
        this.markDirty();
        return this.getPlayerInventory().removeStack(this.getSlotIndex());
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (this.getPlayerInventory() == null) return;
        this.getPlayerInventory().setStack(this.getSlotIndex(), stack);
        this.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.getPlayerInventory() == null) return dummyInventory.canPlayerUse(player);
        return this.getPlayerInventory().canPlayerUse(player);
    }

    @Override
    public void clear() {
        if (this.getPlayerInventory() == null) return;
        this.getPlayerInventory().removeStack(this.getSlotIndex());
        this.markDirty();
    }

    private boolean canModifyPlayerInventory() {
        return this.getPlayer() != null && !this.getPlayer().isDead() && !this.getPlayer().isSpectator();
    }

    static class DummyInventory extends SimpleInventory implements SidedInventory {
        public DummyInventory() {
            super(0);
        }

        public int[] getAvailableSlots(Direction side) {
            return new int[0];
        }

        public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
            return false;
        }

        public boolean canExtract(int slot, ItemStack stack, Direction dir) {
            return false;
        }
    }
}
