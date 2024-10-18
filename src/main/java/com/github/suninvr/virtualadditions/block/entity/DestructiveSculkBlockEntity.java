package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.DestructiveSculkBlock;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.UUID;

public class DestructiveSculkBlockEntity extends BlockEntity {
    private BlockState replacedState;
    private UUID playerId;
    private ItemStack tool;
    private int potency;
    private int age;
    private final ArrayList<BlockPos> affectedPos;
    private int activePosIndex;

    public DestructiveSculkBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.DESTRUCTIVE_SCULK, pos, state);
        this.replacedState = Blocks.AIR.getDefaultState();
        this.playerId = UUID.fromString("0-0-0-0-0");
        this.tool = ItemStack.EMPTY;
        this.potency = 0;
        this.age = 0;
        this.affectedPos = new ArrayList<>();
        this.activePosIndex = -1;
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

    public void setReplacedState(BlockState state) {
        this.replacedState = state;
        this.markDirty();
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

    public void modifyLootContext(LootWorldContext.Builder builder) {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            PlayerEntity player = serverWorld.getPlayerByUuid(this.getPlayerId());
            if (player != null) builder.add(LootContextParameters.THIS_ENTITY, player);
            builder.add(LootContextParameters.TOOL, getTool());
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, DestructiveSculkBlockEntity blockEntity) {
        if (world.isClient()) return;
        if (!state.get(DestructiveSculkBlock.ORIGIN)) return;
        blockEntity.age += 1;
        if (blockEntity.age % 2 == 0) {
            boolean bl = false;
            while (!bl) {
                BlockPos activePos = blockEntity.getActivePos();
                if (activePos == null) {
                    world.scheduleBlockTick(pos, VABlocks.DESTRUCTIVE_SCULK, 6);
                    //blockEntity.destroyAll(false);
                    return;
                }
                BlockState activeState = world.getBlockState(activePos);

                bl = DestructiveSculkBlock.trySpread(activeState, world, activePos, blockEntity);
                if (!bl) {
                    blockEntity.setActivePosIndex(blockEntity.getActivePosIndex() + 1);
                }
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
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.readNbt(nbt, lookup);
        RegistryEntryLookup<Block> registryEntryLookup = this.world != null ? this.world.createCommandRegistryWrapper(RegistryKeys.BLOCK) : Registries.createEntryLookup(Registries.BLOCK);
        this.replacedState = NbtHelper.toBlockState(registryEntryLookup, nbt.getCompound("blockState"));
        this.playerId = nbt.getUuid("playerId");
        if (nbt.contains("tool")) this.tool = ItemStack.fromNbt(lookup, nbt.getCompound("tool")).orElse(ItemStack.EMPTY);
        this.potency = nbt.getInt("potency");
        this.age = nbt.getInt("age");
        this.activePosIndex = nbt.getInt("activePosIndex");
        NbtList affectedPos = nbt.getList("affectedPos", NbtElement.COMPOUND_TYPE);
        this.affectedPos.clear();
        affectedPos.forEach( (nbtElement -> NbtHelper.toBlockPos((NbtCompound)nbtElement, "pos").ifPresent(this.affectedPos::add)));
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.writeNbt(nbt, lookup);
        nbt.put("blockState", NbtHelper.fromBlockState(this.replacedState));
        nbt.putUuid("playerId", playerId);
        NbtCompound tool = new NbtCompound();
        if (!this.tool.isEmpty()) this.tool.toNbt(lookup, tool);
        nbt.put("tool", tool);
        nbt.putInt("potency", this.potency);
        nbt.putInt("age", this.age);
        nbt.putInt("activePosIndex", this.activePosIndex);
        NbtList affectedPos = new NbtList();
        for (BlockPos pos : this.affectedPos) {
            NbtCompound posNbt = new NbtCompound();
            posNbt.put("pos", NbtHelper.fromBlockPos(pos));
            affectedPos.add(posNbt);
        }
        nbt.put("affectedPos", affectedPos);
    }
}
