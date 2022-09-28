package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.IoliteTetherBlock;
import com.mojang.serialization.DataResult;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;

public class IoliteTetherBlockEntity extends BlockEntity {
    private NbtElement destination;
    private static final ParticleEffect particle;

    public IoliteTetherBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(VABlockEntities.IOLITE_TETHER_BLOCK_ENTITY, blockPos, blockState);
        RegistryKey<World> worldKey = ServerWorld.OVERWORLD;

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
        if (world.getTime() % 4L == 0L) {
            double cpos = ((world.getTime() % 20) / 20.0F) * Math.PI;
            double xp = (Math.cos(cpos) * 0.25);
            double zp = (Math.sin(cpos) * 0.25);
            world.addParticle(particle, i + xp, j + 0.4, k + zp, 0.0D, 0.1D, 0.0D);
            world.addParticle(particle, i - xp, j + 0.4, k - zp, 0.0D, 0.1D, 0.0D);
        }
    }

    static {
        particle = new DustColorTransitionParticleEffect(new Vec3f(Vec3d.unpackRgb(15321342)), new Vec3f(Vec3d.unpackRgb(16777215)), 0.75F);
    }

}
