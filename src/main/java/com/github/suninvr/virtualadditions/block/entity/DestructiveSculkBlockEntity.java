package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class DestructiveSculkBlockEntity extends BlockEntity {
    private BlockState replacedState;
    private UUID playerId;
    private ItemStack tool;
    private int potency;

    public DestructiveSculkBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntities.DESTRUCTIVE_SCULK_BLOCK_ENTITY, pos, state);
        this.replacedState = Blocks.AIR.getDefaultState();
        this.playerId = UUID.fromString("0-0-0-0-0");
        this.tool = ItemStack.EMPTY;
        this.potency = 0;
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

    public void modifyLootContext(LootContextParameterSet.Builder builder) {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            PlayerEntity player = serverWorld.getPlayerByUuid(this.getPlayerId());
            if (player != null) builder.add(LootContextParameters.THIS_ENTITY, player);
            builder.add(LootContextParameters.TOOL, getTool());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        RegistryEntryLookup<Block> registryEntryLookup = this.world != null ? this.world.createCommandRegistryWrapper(RegistryKeys.BLOCK) : Registries.BLOCK.getReadOnlyWrapper();
        this.replacedState = NbtHelper.toBlockState(registryEntryLookup, nbt.getCompound("blockState"));
        this.playerId = nbt.getUuid("playerId");
        this.tool = ItemStack.fromNbt(nbt.getCompound("tool"));
        this.potency = nbt.getInt("spreadChance");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("blockState", NbtHelper.fromBlockState(this.replacedState));
        nbt.putUuid("playerId", playerId);
        NbtCompound tool = new NbtCompound();
        this.tool.writeNbt(tool);
        nbt.put("tool", tool);
        nbt.putInt("spreadChance", this.potency);
    }
}
