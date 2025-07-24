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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class DestructiveSculkBlockEntity extends BlockEntity {
    private BlockState replacedState;
    private boolean isOrigin;
    private UUID playerId;
    private static final UUID nullId = UUID.fromString("0-0-0-0-0");
    private ItemStack tool;
    private int potency;
    private final ArrayList<BlockPos> affectedPos;
    private int activePosIndex;
    private boolean firstTick;

    private static final Direction[] HORIZONTAL_DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public DestructiveSculkBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.DESTRUCTIVE_SCULK, pos, state);
        this.replacedState = Blocks.AIR.getDefaultState();
        this.playerId = nullId;
        this.tool = ItemStack.EMPTY;
        this.potency = 0;
        this.affectedPos = new ArrayList<>();
        this.activePosIndex = -1;
        this.firstTick = true;
        this.isOrigin = false;
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
        return this.isOrigin;
    }

    private boolean stateMatches(BlockPos pos) {
        return this.world != null && this.world.getBlockState(pos).isOf(this.replacedState.getBlock());
    }

    public BlockPos getFirstMatch() {
        for (Direction direction : Direction.values()) {
            BlockPos pos1 = this.pos.offset(direction);
            if (this.stateMatches(pos1)) return pos1;

        }

        for (Direction direction : HORIZONTAL_DIRECTIONS) {
            BlockPos pos1 = this.pos.offset(direction).offset(Direction.UP);
            BlockPos pos2 = this.pos.offset(direction).offset(direction.rotateYClockwise());
            BlockPos pos3 = this.pos.offset(direction).offset(Direction.DOWN);
            if (this.stateMatches(pos1)) return pos1;
            if (this.stateMatches(pos2)) return pos2;
            if (this.stateMatches(pos3)) return pos3;

        }

        return null;
    }

    public List<BlockPos> getAdjacentMatches() {
        ArrayList<BlockPos> posList = new ArrayList<>();
        Direction.stream().forEach(direction -> {
            BlockPos pos1 = this.pos.offset(direction);
            if (this.stateMatches(pos1)) posList.add(pos1);
        });
        return posList;
    }

    public List<BlockPos> getEdgeAdjacentMatches() {
        ArrayList<BlockPos> posList = new ArrayList<>();
        Direction.Type.HORIZONTAL.stream().forEach(direction -> {
            BlockPos pos1 = this.pos.offset(direction).offset(Direction.UP);
            BlockPos pos2 = this.pos.offset(direction).offset(direction.rotateYClockwise());
            BlockPos pos3 = this.pos.offset(direction).offset(Direction.DOWN);
            if (this.stateMatches(pos1)) posList.add(pos1);
            if (this.stateMatches(pos2)) posList.add(pos2);
            if (this.stateMatches(pos3)) posList.add(pos3);
        });
        return posList;
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
        this.destroyAll();
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

    public void setOrigin() {
        this.isOrigin = true;
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
        if (!blockEntity.isOrigin) return;
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
            BlockEntity activeEntity = world.getBlockEntity(activePos);

            bl = activeEntity instanceof DestructiveSculkBlockEntity spreadFrom && spreadFrom.trySpread(blockEntity);
            if (!bl) {
                blockEntity.setActivePosIndex(blockEntity.getActivePosIndex() + 1);
            }
        }
    }

    public boolean trySpread(DestructiveSculkBlockEntity originEntity) {
        if (this.world == null) return false;
        if (this.world.isClient()) return false;
        if (originEntity.getPotency() <= 0) return false;
        BlockPos blockPos = this.getFirstMatch();

        boolean bl = false;
        if (blockPos != null) {
            BlockState stateToReplace = this.world.getBlockState(blockPos);
            this.world.setBlockState(blockPos, VABlocks.DESTRUCTIVE_SCULK.getDefaultState());
            DestructiveSculkBlock.setData(this.world, blockPos, stateToReplace, this.getPlayerId(), this.getTool(), 0);
            originEntity.addAffectedPos(blockPos);
            originEntity.setPotency(originEntity.getPotency() - 1);
            this.world.playSound(null, blockPos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 0.5F, 1.0F);
            if (this.world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.SCULK_CHARGE_POP, false, false, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 20, 0.4, 0.4, 0.4, 0.02);
            }
            bl = true;
        }
        if (blockPos == null || originEntity.getPotency() < 1) {
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(DestructiveSculkBlock.SPREADING, false));
        }
        return bl;
    }

    public void destroyAll() {
        if (this.world == null || this.world.isClient()) return;
        if (this.isOrigin()) {
            this.getAffectedPos().forEach( (blockPos) -> {
                if (this.world.getBlockState(blockPos).isOf(VABlocks.DESTRUCTIVE_SCULK)) this.world.setBlockState(blockPos, this.world.getBlockState(blockPos).with(DestructiveSculkBlock.SPREADING, false));
                int i = this.getAffectedPos().indexOf(blockPos) / 10;
                this.world.scheduleBlockTick(blockPos, VABlocks.DESTRUCTIVE_SCULK, world.getRandom().nextBetween(1, 4) + i);
            });
        }
        this.world.breakBlock(this.pos, true, this.world.getPlayerByUuid(this.playerId));
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.potency = view.getInt("potency", 0);
        view.read("player_id", Uuids.CODEC).ifPresentOrElse(this::setPlayerId, () -> this.setPlayerId(nullId));
        view.read("state", BlockState.CODEC).ifPresent(state -> this.replacedState = state);
        view.read("tool", ItemStack.CODEC).ifPresent(itemStack -> this.tool = itemStack);
        this.isOrigin = view.getBoolean("origin", this.isOrigin);
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
        view.putBoolean("origin", this.isOrigin);
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
