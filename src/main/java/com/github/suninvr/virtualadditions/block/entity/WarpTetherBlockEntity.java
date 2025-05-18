package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.WarpTetherBlock;
import com.github.suninvr.virtualadditions.particle.IoliteRingParticleEffect;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WarpTetherBlockEntity extends BlockEntity {
    private BlockPos destination;
    private static final ParticleEffect particle;

    public WarpTetherBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(VABlockEntityType.WARP_TETHER, blockPos, blockState);
        this.destination = blockPos;
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if (this.destination != null) view.put("destination", BlockPos.CODEC, this.destination);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        view.read("destination", BlockPos.CODEC).ifPresent(blockPos -> this.destination = blockPos);
    }

    public static void tick(World world, BlockPos pos, BlockState state, WarpTetherBlockEntity blockEntity) {
        if (state.get(WarpTetherBlock.COOLDOWN)) return;
        double i = pos.getX() + 0.5;
        double j = pos.getY();
        double k = pos.getZ() + 0.5;
        if (world.getTime() % 4L == 0L) {
            world.addParticleClient(particle, i, j + 0.576, k, 0.0D, 0.0D, 0.0D);
        }
    }

    public BlockPos getDestination() {
        return this.destination;
    }

    public void setDestination(BlockPos destination) {
        this.destination = destination;
        this.markDirty();
    }

    static {
        particle = new IoliteRingParticleEffect(true, -0.025, VAParticleTypes.IOLITE_TETHER_RING);
    }
}
