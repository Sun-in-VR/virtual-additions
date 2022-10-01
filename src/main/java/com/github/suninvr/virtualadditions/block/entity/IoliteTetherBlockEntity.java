package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.IoliteTetherBlock;
import com.github.suninvr.virtualadditions.particle.IoliteRingParticleEffect;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IoliteTetherBlockEntity extends BlockEntity {
    private NbtElement destination;
    private static final ParticleEffect particle;

    public IoliteTetherBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(VABlockEntities.IOLITE_TETHER_BLOCK_ENTITY, blockPos, blockState);

        NbtCompound defaultTag = new NbtCompound();
        defaultTag.put("destination", NbtHelper.fromBlockPos(blockPos));
        readNbt(defaultTag);
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        if (this.destination != null) {
            tag.put("destination", destination);
        }
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        if (tag.contains("destination")) {
            destination = tag.get("destination");
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, IoliteTetherBlockEntity blockEntity) {
        if (state.get(IoliteTetherBlock.COOLDOWN)) return;
        float i = pos.getX() + 0.5F;
        float j = pos.getY();
        float k = pos.getZ() + 0.5F;
        if (world.getTime() % 2L == 0L) {
            world.addParticle(particle, i, j + 0.525, k, 0.0D, 0.1D, 0.0D);
        }
    }

    static {
        particle = new IoliteRingParticleEffect(true, -0.05, VAParticleTypes.IOLITE_TETHER_RING);
    }

}
