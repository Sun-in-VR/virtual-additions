package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.WindBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WindBlock extends BlockWithEntity {
    public static final MapCodec<WindBlock> WIND_BLOCK_MAP_CODEC = createCodec(WindBlock::new);

    public WindBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, VABlockEntities.WIND_BLOCK_ENTITY, WindBlockEntity::tick) ;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return WIND_BLOCK_MAP_CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WindBlockEntity(pos, state);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof WindBlockEntity windBlockEntity) {
            int i = random.nextInt(10);
            while (i < 20) {
                double x = (random.nextDouble() * 10) + pos.getX() - 5;
                double y = (random.nextDouble() * 10) + pos.getY() - 5;
                double z = (random.nextDouble() * 10) + pos.getZ() - 5;
                Vec3d v = windBlockEntity.getWindVector().multiply(5000);
                world.addParticle(ParticleTypes.CLOUD, x, y, z, v.x, v.y, v.z);
                i++;
            }
        }
    }
}
