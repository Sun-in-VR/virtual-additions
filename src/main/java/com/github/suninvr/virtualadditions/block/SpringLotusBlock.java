package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.SpringLotusState;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAEntityTypeTags;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class SpringLotusBlock extends PlantBlock implements Fertilizable {
    public static final MapCodec<SpringLotusBlock> CODEC = createCodec(SpringLotusBlock::new);
    public static final IntProperty COMPRESSION = IntProperty.of("compression", 0, 4);
    public static final EnumProperty<SpringLotusState> STATE = EnumProperty.of("state", SpringLotusState.class);
    public static final VoxelShape SHAPE_0, SHAPE_1, SHAPE_2, SHAPE_3, SHAPE_4;

    public SpringLotusBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(COMPRESSION, 3).with(STATE, SpringLotusState.IDLE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COMPRESSION, STATE);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if (state.get(COMPRESSION) > 3) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
            return;
        }
        double d = fallDistance - 15.0;
        super.onLandedUpon(world, state, pos, entity, d);
        if (world.isClient()) return;
        if (d > entity.getSafeFallDistance() && entity instanceof LivingEntity) {
            world.setBlockState(pos, state.with(COMPRESSION, 4).with(STATE, SpringLotusState.OVER_COMPRESSED));
            world.playSound(null, pos, SoundEvents.BLOCK_BIG_DRIPLEAF_TILT_DOWN, SoundCategory.BLOCKS, 1, 0.8F);
            world.scheduleBlockTick(pos, this, 1);
        } else if (state.get(COMPRESSION) == 3 && fallDistance >= 0.5 && !entity.getType().isIn(VAEntityTypeTags.IGNORES_SPRING_LOTUS)) {
            world.scheduleBlockTick(pos, this, 1);
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(STATE).equals(SpringLotusState.OVER_COMPRESSED)) {
            world.setBlockState(pos, state.with(STATE, SpringLotusState.IDLE));
            world.scheduleBlockTick(pos, this, 99);
            return;
        }
        if (state.get(STATE).equals(SpringLotusState.PUSHING)) {
            world.setBlockState(pos, state.with(STATE, SpringLotusState.IDLE));
            world.scheduleBlockTick(pos, this, 35);
            return;
        }
        int compression = state.get(COMPRESSION);
        if (compression < 3) {
            world.setBlockState(pos, state.with(COMPRESSION, compression + 1));
            if (compression < 2) world.scheduleBlockTick(pos, this, 8);
            world.playSound(null, pos, SoundEvents.BLOCK_BIG_DRIPLEAF_TILT_UP, SoundCategory.BLOCKS, 1, 1.2F);
        } else if (compression > 3) {
            world.setBlockState(pos, state.with(COMPRESSION, compression - 1));
            world.playSound(null, pos, SoundEvents.BLOCK_BIG_DRIPLEAF_TILT_UP, SoundCategory.BLOCKS, 1, 1.2F);
        } else {
            spring(world, pos, state);
        }
    }

    private static void spring(ServerWorld world, BlockPos pos, BlockState state) {
        Vec3d centerPos = pos.toCenterPos();
        world.getOtherEntities(null, Box.of(centerPos, 1.0, 1.0, 1.0))
                .forEach(entity -> {
                    double d = getSpringPower(entity);
                    int r;
                    if ((r =world.getReceivedRedstonePower(pos)) > 0) {
                        d *= (7.0 - Math.sqrt(r)) / 6.0;
                    }
                    Vec3d velocity = entity.getVelocity();
                    if (d < velocity.y) return;
                    entity.setVelocity(velocity.x, d, velocity.z);
                    entity.velocityModified = true;
                }
        );
        world.setBlockState(pos, state.with(COMPRESSION, 0).with(STATE, SpringLotusState.PUSHING));
        world.scheduleBlockTick(pos, state.getBlock(), 5);
        world.playSound(null, pos, SoundEvents.BLOCK_BIG_DRIPLEAF_TILT_DOWN, SoundCategory.BLOCKS, 1, 1);
        world.spawnParticles(VAParticleTypes.SPRING_LOTUS_POLLEN, false, false, centerPos.getX(), centerPos.getY() + 1.0, centerPos.getZ(), 25, 0.25, 0.25, 0.25, 0.25);
    }

    private static double getSpringPower(Entity entity) {
        double d = entity instanceof AbstractMinecartEntity ? 2.8 : 1.5;
        if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
            if (serverPlayerEntity.getPlayerInput().sneak()) d = 1.05F;
            else if (serverPlayerEntity.getPlayerInput().jump()) {
                StatusEffectInstance instance = serverPlayerEntity.getStatusEffect(StatusEffects.JUMP_BOOST);
                double f = instance != null ? (instance.getAmplifier() + 1) * 0.15 : 0;
                d = 1.78F + f;
            }
        }
        if (entity.getType().isIn(VAEntityTypeTags.IGNORES_SPRING_LOTUS)) d *= 0.65;
        return d;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return Fertilizable.canSpread(world, pos, state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Fertilizable.findPosToSpreadTo(world, pos, state).ifPresent(posx -> world.setBlockState(posx, VABlocks.SMALL_SPRING_LOTUS.getDefaultState()));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(STATE).equals(SpringLotusState.PUSHING) ? SHAPE_3 : super.getCollisionShape(state, world, pos, context);
    }

    @Override
    protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(COMPRESSION)) {
            case 0 -> SHAPE_0;
            case 1 -> SHAPE_1;
            case 2 -> SHAPE_2;
            case 3 -> SHAPE_3;
            case 4 -> SHAPE_4;
            default -> throw new IllegalStateException("Unexpected value: " + state.get(COMPRESSION));
        };
    }

    @Override
    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected boolean isTransparent(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state) {
        return VoxelShapes.empty();
    }

    static {
        SHAPE_4 = Block.createColumnShape(16.0, 0.0, 4.0);
        SHAPE_3 = VoxelShapes.union(Block.createColumnShape(16.0, 4.0, 8.0), Block.createColumnShape(14.0, 0.0, 8.0));
        SHAPE_2 = VoxelShapes.union(Block.createColumnShape(16.0, 8.0, 12.0), Block.createColumnShape(12.0, 0.0, 12.0));
        SHAPE_1 = VoxelShapes.union(Block.createColumnShape(16.0, 10.0, 14.0), Block.createColumnShape(10.0, 0.0, 14.0));
        SHAPE_0 = VoxelShapes.union(Block.createColumnShape(16.0, 12.0, 16.0), Block.createColumnShape(8.0, 0.0, 16.0));
    }
}
