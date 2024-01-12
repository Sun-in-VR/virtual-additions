package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.interfaces.EntityInterface;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class WindBlockEntity extends BlockEntity {
    Vec3d windVector;

    public WindBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntities.WIND_BLOCK_ENTITY, pos, state);
        this.windVector = new Vec3d(0, 0, 0);
        this.markDirty();
    }

    public WindBlockEntity(BlockPos pos, BlockState state, Vec3d vec) {
        super(VABlockEntities.WIND_BLOCK_ENTITY, pos, state);
        this.windVector = vec;
        this.markDirty();
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("wind_vector")) {
            NbtCompound vecNbt = nbt.getCompound("wind_vector");
            this.windVector = new Vec3d(vecNbt.getDouble("x"), vecNbt.getDouble("y") ,vecNbt.getDouble("z"));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.windVector != null) {
            NbtCompound vecNbt = new NbtCompound();
            vecNbt.putDouble("x", this.windVector.x);
            vecNbt.putDouble("y", this.windVector.y);
            vecNbt.putDouble("z", this.windVector.z);
            nbt.put("wind_vector", vecNbt);
        }
    }

    public static void tick(World world, BlockPos pos, BlockState blockState, WindBlockEntity windBlockEntity) {
        if (windBlockEntity.windVector == null) return;
        if (windBlockEntity.windVector.length() == 0) return;
        Box box = new Box(new Vec3d(pos.getX() - 5, pos.getY() - 5, pos.getZ() - 5), new Vec3d(pos.getX() + 5, pos.getY() + 5, pos.getZ() + 5));
        List<Entity> entities = world.getEntitiesByType(
                TypeFilter.instanceOf(Entity.class),
                box,
                (entity -> true)
        );
        entities.forEach(entity -> {
            ((EntityInterface)(entity)).virtualAdditions$setWindVelocity(windBlockEntity.windVector);
            ((EntityInterface)(entity)).virtualAdditions$setInWind(true);
        });
    }

    public Vec3d getWindVector() {
        return this.windVector;
    }
}
