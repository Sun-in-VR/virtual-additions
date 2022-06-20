package com.github.suninvr.virtualadditions.block.entity;

import com.mojang.serialization.DataResult;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;

public class IoliteTetherBlockEntity extends BlockEntity {
    private NbtElement destination;
    private NbtElement destinationDim;

    public IoliteTetherBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(VABlockEntities.IOLITE_TETHER_BLOCK_ENTITY, blockPos, blockState);
        RegistryKey<World> worldKey = ServerWorld.OVERWORLD;
        DataResult<NbtElement> worldDim = World.CODEC.encodeStart(NbtOps.INSTANCE, worldKey);

        NbtCompound defaultTag = new NbtCompound();
        defaultTag.put("destination", NbtHelper.fromBlockPos(blockPos));
        worldDim.resultOrPartial(VirtualAdditions.LOGGER::error).ifPresent((dimName) -> defaultTag.put("destinationDim", dimName));
        readNbt(defaultTag);
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        if (this.destination != null) {
            tag.put("destination", destination);
        }
        if (this.destinationDim != null) {
            tag.put("destinationDim", destinationDim);
        }
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        if (tag.contains("destination")) {
            destination = tag.get("destination");
        }
        if (tag.contains("destinationDim")) {
            destinationDim = tag.get("destinationDim");
        }
    }

}
