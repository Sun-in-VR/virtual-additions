package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.DestructiveSculkBlock;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        this.replacedState = Blocks.AIR.defaultBlockState();
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
        return this.level != null && this.level.getBlockState(pos).is(this.replacedState.getBlock());
    }

    public BlockPos getFirstMatch() {
        for (Direction direction : Direction.values()) {
            BlockPos pos1 = this.worldPosition.relative(direction);
            if (this.stateMatches(pos1)) return pos1;

        }

        for (Direction direction : HORIZONTAL_DIRECTIONS) {
            BlockPos pos1 = this.worldPosition.relative(direction).relative(Direction.UP);
            BlockPos pos2 = this.worldPosition.relative(direction).relative(direction.getClockWise());
            BlockPos pos3 = this.worldPosition.relative(direction).relative(Direction.DOWN);
            if (this.stateMatches(pos1)) return pos1;
            if (this.stateMatches(pos2)) return pos2;
            if (this.stateMatches(pos3)) return pos3;

        }

        return null;
    }

    public List<BlockPos> getAdjacentMatches() {
        ArrayList<BlockPos> posList = new ArrayList<>();
        Direction.stream().forEach(direction -> {
            BlockPos pos1 = this.worldPosition.relative(direction);
            if (this.stateMatches(pos1)) posList.add(pos1);
        });
        return posList;
    }

    public List<BlockPos> getEdgeAdjacentMatches() {
        ArrayList<BlockPos> posList = new ArrayList<>();
        Direction.Plane.HORIZONTAL.stream().forEach(direction -> {
            BlockPos pos1 = this.worldPosition.relative(direction).relative(Direction.UP);
            BlockPos pos2 = this.worldPosition.relative(direction).relative(direction.getClockWise());
            BlockPos pos3 = this.worldPosition.relative(direction).relative(Direction.DOWN);
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
        return this.activePosIndex >= 0 ? this.activePosIndex < this.affectedPos.size() ? this.affectedPos.get(this.activePosIndex) : null : this.getBlockPos();
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState oldState) {
        this.destroyAll();
        super.preRemoveSideEffects(pos, oldState);
    }

    public void setReplacedState(BlockState state) {
        if (this.level instanceof ServerLevel serverWorld) {
            this.replacedState = state;
            this.setChanged();
        }
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
        this.setChanged();
    }

    public void setTool(ItemStack tool) {
        this.tool = tool;
        this.setChanged();
    }

    public void setPotency(int potency) {
        this.potency = potency;
        this.setChanged();
    }

    public void addAffectedPos(BlockPos pos) {
        this.affectedPos.add(pos);
        this.setChanged();
    }

    public void setActivePosIndex(int index) {
        this.activePosIndex = index;
        this.setChanged();
    }

    public void setOrigin() {
        this.isOrigin = true;
        this.setChanged();
    }

    public LootParams.Builder modifyLootContext(LootParams.Builder builder) {
        if (this.getLevel() instanceof ServerLevel serverWorld) {
            builder.withParameter(LootContextParams.ORIGIN, new Vec3(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ()));
            Player player = serverWorld.getPlayerByUUID(this.getPlayerId());
            if (player != null) builder.withParameter(LootContextParams.THIS_ENTITY, player);
            builder.withParameter(LootContextParams.TOOL, this.tool);
            builder.withParameter(LootContextParams.BLOCK_ENTITY, this);
        }
        return builder;
    }

    public static void tick(Level world, BlockPos pos, BlockState state, DestructiveSculkBlockEntity blockEntity) {
        if (world.isClientSide()) return;
        if (!blockEntity.isOrigin) return;
        if (blockEntity.firstTick) {
            blockEntity.firstTick = false;
            return;
        }
        boolean bl = false;
        while (!bl) {
            BlockPos activePos = blockEntity.getActivePos();
            if (activePos == null) {
                world.scheduleTick(pos, VABlocks.DESTRUCTIVE_SCULK, 6);
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
        if (this.level == null) return false;
        if (this.level.isClientSide()) return false;
        if (originEntity.getPotency() <= 0) return false;
        BlockPos blockPos = this.getFirstMatch();

        boolean bl = false;
        if (blockPos != null) {
            BlockState stateToReplace = this.level.getBlockState(blockPos);
            this.level.setBlockAndUpdate(blockPos, VABlocks.DESTRUCTIVE_SCULK.defaultBlockState());
            DestructiveSculkBlock.setData(this.level, blockPos, stateToReplace, this.getPlayerId(), this.getTool(), 0);
            originEntity.addAffectedPos(blockPos);
            originEntity.setPotency(originEntity.getPotency() - 1);
            this.level.playSound(null, blockPos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 0.5F, 1.0F);
            if (this.level instanceof ServerLevel serverWorld) {
                serverWorld.sendParticles(ParticleTypes.SCULK_CHARGE_POP, false, false, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 20, 0.4, 0.4, 0.4, 0.02);
            }
            bl = true;
        }
        if (blockPos == null || originEntity.getPotency() < 1) {
            this.level.setBlockAndUpdate(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(DestructiveSculkBlock.SPREADING, false));
        }
        return bl;
    }

    public void destroyAll() {
        if (this.level == null || this.level.isClientSide()) return;
        if (this.isOrigin()) {
            this.getAffectedPos().forEach( (blockPos) -> {
                if (this.level.getBlockState(blockPos).is(VABlocks.DESTRUCTIVE_SCULK)) this.level.setBlockAndUpdate(blockPos, this.level.getBlockState(blockPos).setValue(DestructiveSculkBlock.SPREADING, false));
                int i = this.getAffectedPos().indexOf(blockPos) / 10;
                this.level.scheduleTick(blockPos, VABlocks.DESTRUCTIVE_SCULK, level.getRandom().nextIntBetweenInclusive(1, 4) + i);
            });
        }
        this.level.destroyBlock(this.worldPosition, true, this.level.getPlayerByUUID(this.playerId));
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.potency = view.getIntOr("potency", 0);
        view.read("player_id", UUIDUtil.AUTHLIB_CODEC).ifPresentOrElse(this::setPlayerId, () -> this.setPlayerId(nullId));
        view.read("state", BlockState.CODEC).ifPresent(state -> this.replacedState = state);
        view.read("tool", ItemStack.CODEC).ifPresent(itemStack -> this.tool = itemStack);
        this.isOrigin = view.getBooleanOr("origin", this.isOrigin);
        this.activePosIndex = view.getIntOr("active_pos_index", -1);
        this.affectedPos.clear();
        Optional<ValueInput.TypedInputList<BlockPos>> affectedPos = view.list("affected_pos", BlockPos.CODEC);
        affectedPos.ifPresent(blockPos -> blockPos.forEach(this.affectedPos::add));
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        view.putInt("potency", this.potency);
        view.store("player_id", UUIDUtil.AUTHLIB_CODEC, this.playerId);
        view.store("state", BlockState.CODEC, this.replacedState);
        view.store("tool", ItemStack.CODEC, this.tool);
        view.putBoolean("origin", this.isOrigin);
        if (!this.affectedPos.isEmpty()) {
            view.putInt("active_pos_index", this.activePosIndex);
            ValueOutput.TypedOutputList<BlockPos> affectedPos = view.list("affected_pos", BlockPos.CODEC);
            this.affectedPos.forEach(affectedPos::add);
        }
    }

    public List<ItemStack> getDroppedStacks(ServerLevel serverWorld) {
        return this.replacedState.getDrops(
                this.modifyLootContext(new LootParams.Builder(serverWorld))
        );
    }
}
