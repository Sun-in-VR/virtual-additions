package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.EntanglementDriveBlock;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings({"unused", "DataFlowIssue"})
public class EntanglementDriveBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
    private static final Component TITLE = Component.translatable("container.virtual_additions.entanglement_drive");
    private int slotIndex;
    private UUID playerId;
    private int[] playerIdInts;
    private ItemStack cachedStack;
    private final ContainerData properties = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> EntanglementDriveBlockEntity.this.slotIndex;
                case 1 -> EntanglementDriveBlockEntity.this.playerIdInts[0];
                case 2 -> EntanglementDriveBlockEntity.this.playerIdInts[1];
                case 3 -> EntanglementDriveBlockEntity.this.playerIdInts[2];
                case 4 -> EntanglementDriveBlockEntity.this.playerIdInts[3];
                default -> throw new IllegalStateException("Unexpected value: " + index);
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> EntanglementDriveBlockEntity.this.slotIndex = value;
                case 1 -> EntanglementDriveBlockEntity.this.playerIdInts[0] = value;
                case 2 -> EntanglementDriveBlockEntity.this.playerIdInts[1] = value;
                case 3 -> EntanglementDriveBlockEntity.this.playerIdInts[2] = value;
                case 4 -> EntanglementDriveBlockEntity.this.playerIdInts[3] = value;
                default -> throw new IllegalStateException("Unexpected value: " + index);
            }
            EntanglementDriveBlockEntity.this.setChanged();
        }

        @Override
        public int getCount() {
            return 5;
        }
    };
    private static final Container dummyInventory = new DummyInventory();
    private static final String nullIdString = "0-0-0-0-0";
    private static final UUID nullId = UUID.fromString(nullIdString);

    public EntanglementDriveBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.ENTANGLEMENT_DRIVE, pos, state);
        this.slotIndex = -1;
        this.setPlayerId(nullId);
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.slotIndex = view.getIntOr("slot_index",-1);
        UUID playerId = view.read("user_id", UUIDUtil.AUTHLIB_CODEC).orElse(nullId);
        this.setPlayerId(playerId);
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        view.putInt("slot_index", this.slotIndex);
        view.store("user_id", UUIDUtil.AUTHLIB_CODEC, this.playerId);
    }

    @NotNull
    public Optional<Player> getPlayer() {
        return Optional.ofNullable(this.getLevel().getServer().getPlayerList().getPlayer(this.playerId));
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
        this.playerIdInts = UUIDUtil.uuidToIntArray(playerId);
    }

    @Nullable
    private Inventory getPlayerInventory() {
        if (this.getPlayer().isEmpty()) return null;
        return this.getPlayer().get().getInventory();
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public void setPlayerSlot(@Nullable Player player, int slotIndex, int slotId) {
        if (player != null) {
            if (this.level != null || this.level.isClientSide()) return;
            this.setPlayerId(player.getUUID());
            this.slotIndex = slotIndex;
            this.setChanged();
        }
    }

    public static <E extends BlockEntity> void tick(Level world, BlockPos pos, BlockState state, EntanglementDriveBlockEntity blockEntity) {
        if (world.isClientSide()) {
            return;
        }
        world.updateNeighbourForOutputSignal(pos, VABlocks.ENTANGLEMENT_DRIVE);
    }

    public WorldlyContainer getInventory() {
        return this;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new EntanglementDriveScreenHandler(syncId, inv, ContainerLevelAccess.create(this.level, this.worldPosition), this.properties);
    }

    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{0};
    }

    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        return dir == Direction.UP && this.canModifyPlayerInventory();
    }

    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return dir == Direction.DOWN && this.canModifyPlayerInventory();
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        if (!this.canAccessPlayerInventory()) return dummyInventory.isEmpty();
        return this.getPlayerInventory().getItem(this.getSlotIndex()) == ItemStack.EMPTY;
    }

    public ItemStack getStack() {
        return this.getItem(0);
    }

    @Override
    public ItemStack getItem(int slot) {
        if (!this.canAccessPlayerInventory()) return dummyInventory.getItem(slot);
        return this.getPlayerInventory().getItem(this.getSlotIndex());
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (!this.canModifyPlayerInventory()) return dummyInventory.removeItem(slot, amount);
        ItemStack stack = this.getPlayerInventory().removeItem(this.getSlotIndex(), amount);
        this.setChanged();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if (!this.canModifyPlayerInventory()) return ItemStack.EMPTY;
        ItemStack stack = this.getPlayerInventory().removeItemNoUpdate(this.getSlotIndex());
        this.setChanged();
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (!this.canModifyPlayerInventory()) return;
        this.getPlayerInventory().setItem(this.getSlotIndex(), stack);
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        if (!this.canAccessPlayerInventory()) return dummyInventory.stillValid(player);
        return this.getPlayerInventory().stillValid(player);
    }

    @Override
    public void clearContent() {
        if (!this.canModifyPlayerInventory()) return;
        this.getPlayerInventory().removeItemNoUpdate(this.getSlotIndex());
        this.setChanged();
    }

    private boolean canModifyPlayerInventory() {
        if (!canAccessPlayerInventory()) return false;
        Player player = this.getPlayer().get();
        MobEffectInstance effect = player.getEffect(VAStatusEffects.IOLITE_INTERFERENCE);
        boolean bl = effect == null || effect.getAmplifier() < 1;
        return !player.isDeadOrDying() && !player.isSpectator() && bl;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean canAccessPlayerInventory() {
        if (this.getPlayer().isEmpty()) return false;
        if (this.slotIndex < 0) return false;
        BlockState state = this.getLevel() != null ? this.getLevel().getBlockState(this.getBlockPos()) : Blocks.AIR.defaultBlockState();
        return this.getPlayerInventory() != null && state.is(VABlocks.ENTANGLEMENT_DRIVE) && !state.getValue(EntanglementDriveBlock.POWERED);
    }

    static class DummyInventory extends SimpleContainer implements WorldlyContainer {
        public DummyInventory() {
            super(0);
        }

        public int[] getSlotsForFace(Direction side) {
            return new int[0];
        }

        public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
            return false;
        }

        public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
            return false;
        }
    }
}
