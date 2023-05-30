package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.DestructiveSculkBlock;
import com.github.suninvr.virtualadditions.block.SpotlightBlock;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.UUID;

public class DestructiveSculkBlockEntity extends BlockEntity {
    private BlockState replacedState;
    private UUID playerId;
    private ItemStack tool;
    private int potency;
    private int age;
    private ArrayList<BlockPos> affectedPos;
    private BlockPos originPos;

    public DestructiveSculkBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntities.DESTRUCTIVE_SCULK_BLOCK_ENTITY, pos, state);
        this.replacedState = Blocks.AIR.getDefaultState();
        this.playerId = UUID.fromString("0-0-0-0-0");
        this.tool = ItemStack.EMPTY;
        this.potency = 0;
        this.age = 0;
        this.affectedPos = new ArrayList<>();
        this.originPos = pos;
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

    public BlockPos getOriginPos() {
        return originPos;
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
        //if (this.isOrigin() && potency <= 0 && this.world != null) this.world.scheduleBlockTick(this.pos, VABlocks.DESTRUCTIVE_SCULK, 5);
        this.markDirty();
    }

    public void addAffectedPos(BlockPos pos) {
        this.affectedPos.add(pos);
        this.markDirty();
    }

    public void setOriginPos(BlockPos originPos) {
        this.originPos = originPos;
    }

    public void modifyLootContext(LootContextParameterSet.Builder builder) {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            PlayerEntity player = serverWorld.getPlayerByUuid(this.getPlayerId());
            if (player != null) builder.add(LootContextParameters.THIS_ENTITY, player);
            builder.add(LootContextParameters.TOOL, getTool());
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, DestructiveSculkBlockEntity blockEntity) {
        if (!state.get(DestructiveSculkBlock.ORIGIN)) return;
        blockEntity.age += 1;
        if (blockEntity.age % 8 == 0) {
            boolean isSpreading = false;
            for (BlockPos blockPos : blockEntity.getAffectedPos()) {
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isOf(VABlocks.DESTRUCTIVE_SCULK) && blockState.get(DestructiveSculkBlock.SPREADING)) {
                    isSpreading = true;
                    break;
                }
            }
            if (!isSpreading) {
                world.scheduleBlockTick(pos, VABlocks.DESTRUCTIVE_SCULK, 1);
                blockEntity.getAffectedPos().forEach( (blockPos) -> world.scheduleBlockTick(blockPos, VABlocks.DESTRUCTIVE_SCULK, world.getRandom().nextBetween(1, 5)));
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        RegistryEntryLookup<Block> registryEntryLookup = this.world != null ? this.world.createCommandRegistryWrapper(RegistryKeys.BLOCK) : Registries.BLOCK.getReadOnlyWrapper();
        this.replacedState = NbtHelper.toBlockState(registryEntryLookup, nbt.getCompound("blockState"));
        this.playerId = nbt.getUuid("playerId");
        this.tool = ItemStack.fromNbt(nbt.getCompound("tool"));
        if (this.isOrigin()) {
            this.potency = nbt.getInt("potency");
            this.age = nbt.getInt("age");
            NbtList affectedPos = nbt.getList("affectedPos", NbtElement.COMPOUND_TYPE);
            this.affectedPos.clear();
            affectedPos.forEach( (nbtElement -> this.affectedPos.add(NbtHelper.toBlockPos((NbtCompound)nbtElement))));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("blockState", NbtHelper.fromBlockState(this.replacedState));
        nbt.putUuid("playerId", playerId);
        NbtCompound tool = new NbtCompound();
        this.tool.writeNbt(tool);
        nbt.put("tool", tool);
        if (this.isOrigin()) {
            nbt.putInt("potency", this.potency);
            nbt.putInt("age", this.age);
            NbtList affectedPos = new NbtList();
            for (BlockPos pos : this.affectedPos) {
                affectedPos.add(NbtHelper.fromBlockPos(pos));
            }
            nbt.put("affectedPos", affectedPos);
        }
    }
}
