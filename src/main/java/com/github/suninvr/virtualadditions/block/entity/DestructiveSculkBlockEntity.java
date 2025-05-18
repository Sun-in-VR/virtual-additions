package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.DestructiveSculkBlock;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DestructiveSculkBlockEntity extends BlockEntity {
    private BlockState replacedState;
    private UUID playerId;
    private static final UUID nullId = UUID.fromString("0-0-0-0-0");
    private ItemStack tool;
    private int potency;
    private final ArrayList<BlockPos> affectedPos;
    private int activePosIndex;
    private boolean firstTick;

    public DestructiveSculkBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.DESTRUCTIVE_SCULK, pos, state);
        this.replacedState = Blocks.AIR.getDefaultState();
        this.playerId = nullId;
        this.tool = ItemStack.EMPTY;
        this.potency = 0;
        this.affectedPos = new ArrayList<>();
        this.activePosIndex = -1;
        this.firstTick = true;
    }

    public BlockState getReplacedState() {
        return replacedState;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public ItemStack getTool() {
        return tool;
    }

    public int getPotency() {
        return potency;
    }

    public boolean isOrigin() {
        return this.world != null && (this.world.getBlockState(this.getPos()).isOf(VABlocks.DESTRUCTIVE_SCULK) ? this.world.getBlockState(this.getPos()).get(DestructiveSculkBlock.ORIGIN) : false);
    }

    public ArrayList<BlockPos> getAffectedPos() {
        return affectedPos;
    }

    public int getActivePosIndex() {
        return activePosIndex;
    }

    @Nullable
    public BlockPos getActivePos() {
        return this.activePosIndex >= 0 ? this.activePosIndex < this.affectedPos.size() ? this.affectedPos.get(this.activePosIndex) : null : this.getPos();
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        if (oldState.isOf(VABlocks.DESTRUCTIVE_SCULK) && oldState.get(DestructiveSculkBlock.ORIGIN)) this.destroyAll(true);
        super.onBlockReplaced(pos, oldState);
    }

    public void setReplacedState(BlockState state) {
        if (this.world instanceof ServerWorld serverWorld) {
            this.replacedState = state;
            this.markDirty();
        }
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
        this.markDirty();
    }

    public void setTool(ItemStack tool) {
        this.tool = tool;
        this.markDirty();
    }

    public void setPotency(int potency) {
        this.potency = potency;
        this.markDirty();
    }

    public void addAffectedPos(BlockPos pos) {
        this.affectedPos.add(pos);
        this.markDirty();
    }

    public void setActivePosIndex(int index) {
        this.activePosIndex = index;
        this.markDirty();
    }

    public LootWorldContext.Builder modifyLootContext(LootWorldContext.Builder builder) {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            builder.add(LootContextParameters.ORIGIN, new Vec3d(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ()));
            PlayerEntity player = serverWorld.getPlayerByUuid(this.getPlayerId());
            if (player != null) builder.add(LootContextParameters.THIS_ENTITY, player);
            builder.add(LootContextParameters.TOOL, this.tool);
            builder.add(LootContextParameters.BLOCK_ENTITY, this);
        }
        return builder;
    }

    public static void tick(World world, BlockPos pos, BlockState state, DestructiveSculkBlockEntity blockEntity) {
        if (world.isClient()) return;
        if (!state.get(DestructiveSculkBlock.ORIGIN)) return;
        if (blockEntity.firstTick) {
            blockEntity.firstTick = false;
            return;
        }
        boolean bl = false;
        while (!bl) {
            BlockPos activePos = blockEntity.getActivePos();
            if (activePos == null) {
                world.scheduleBlockTick(pos, VABlocks.DESTRUCTIVE_SCULK, 6);
                return;
            }
            BlockState activeState = world.getBlockState(activePos);

            bl = DestructiveSculkBlock.trySpread(activeState, world, activePos, blockEntity);
            if (!bl) {
                blockEntity.setActivePosIndex(blockEntity.getActivePosIndex() + 1);
            }
        }
    }

    public void destroyAll(boolean force) {
        if (this.world == null || this.world.isClient()) return;
        if (force || this.isOrigin()) {
            this.getAffectedPos().forEach( (blockPos) -> {
                if (this.world.getBlockState(blockPos).isOf(VABlocks.DESTRUCTIVE_SCULK)) this.world.setBlockState(blockPos, this.world.getBlockState(blockPos).with(DestructiveSculkBlock.SPREADING, false));
                int i = this.getAffectedPos().indexOf(blockPos) / 10;
                this.world.scheduleBlockTick(blockPos, VABlocks.DESTRUCTIVE_SCULK, world.getRandom().nextBetween(1, 4) + i);
            });
        }
        this.world.breakBlock(this.pos, true);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.potency = view.getInt("potency", 0);
        view.read("player_id", Uuids.CODEC).ifPresentOrElse(this::setPlayerId, () -> this.setPlayerId(nullId));
        view.read("state", BlockState.CODEC).ifPresent(state -> this.replacedState = state);
        view.read("tool", ItemStack.CODEC).ifPresent(itemStack -> this.tool = itemStack);
        this.activePosIndex = view.getInt("active_pos_index", -1);
        this.affectedPos.clear();
        Optional<ReadView.TypedListReadView<BlockPos>> affectedPos = view.getOptionalTypedListView("affected_pos", BlockPos.CODEC);
        affectedPos.ifPresent(blockPos -> blockPos.forEach(this.affectedPos::add));
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.putInt("potency", this.potency);
        view.put("player_id", Uuids.CODEC, this.playerId);
        view.put("state", BlockState.CODEC, this.replacedState);
        view.put("tool", ItemStack.CODEC, this.tool);
        if (!this.affectedPos.isEmpty()) {
            view.putInt("active_pos_index", this.activePosIndex);
            WriteView.ListAppender<BlockPos> affectedPos = view.getListAppender("affected_pos", BlockPos.CODEC);
            this.affectedPos.forEach(affectedPos::add);
        }
    }

    public List<ItemStack> getDroppedStacks(ServerWorld serverWorld) {
        return this.replacedState.getDroppedStacks(
                this.modifyLootContext(new LootWorldContext.Builder(serverWorld))
        );
    }
}
