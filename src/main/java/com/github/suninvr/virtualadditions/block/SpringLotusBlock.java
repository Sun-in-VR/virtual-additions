package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.SpringLotusState;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAEntityTypeTags;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpringLotusBlock extends VegetationBlock implements BonemealableBlock {
    public static final MapCodec<SpringLotusBlock> CODEC = simpleCodec(SpringLotusBlock::new);
    public static final IntegerProperty COMPRESSION = IntegerProperty.create("compression", 0, 4);
    public static final EnumProperty<SpringLotusState> STATE = EnumProperty.create("state", SpringLotusState.class);
    public static final VoxelShape SHAPE_0, SHAPE_1, SHAPE_2, SHAPE_3, SHAPE_4;

    public SpringLotusBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(COMPRESSION, 3).setValue(STATE, SpringLotusState.IDLE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COMPRESSION, STATE);
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if (state.getValue(COMPRESSION) > 3) {
            super.fallOn(world, state, pos, entity, fallDistance);
            return;
        }
        double d = fallDistance - 15.0;
        super.fallOn(world, state, pos, entity, d);
        if (world.isClientSide()) return;
        if (d > entity.getMaxFallDistance() && entity instanceof LivingEntity) {
            world.setBlockAndUpdate(pos, state.setValue(COMPRESSION, 4).setValue(STATE, SpringLotusState.OVER_COMPRESSED));
            world.playSound(null, pos, SoundEvents.BIG_DRIPLEAF_TILT_DOWN, SoundSource.BLOCKS, 1, 0.8F);
            world.scheduleTick(pos, this, 1);
        } else if (state.getValue(COMPRESSION) == 3 && fallDistance >= 0.5 && !entity.getType().is(VAEntityTypeTags.IGNORES_SPRING_LOTUS) && !entity.isShiftKeyDown()) {
            world.scheduleTick(pos, this, 1);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(STATE).equals(SpringLotusState.OVER_COMPRESSED)) {
            world.setBlockAndUpdate(pos, state.setValue(STATE, SpringLotusState.IDLE));
            world.scheduleTick(pos, this, 99);
            return;
        }
        if (state.getValue(STATE).equals(SpringLotusState.PUSHING)) {
            world.setBlockAndUpdate(pos, state.setValue(STATE, SpringLotusState.IDLE));
            world.scheduleTick(pos, this, 35);
            return;
        }
        int compression = state.getValue(COMPRESSION);
        if (compression < 3) {
            world.setBlockAndUpdate(pos, state.setValue(COMPRESSION, compression + 1));
            if (compression < 2) world.scheduleTick(pos, this, 8);
            world.playSound(null, pos, SoundEvents.BIG_DRIPLEAF_TILT_UP, SoundSource.BLOCKS, 1, 1.2F);
        } else if (compression > 3) {
            world.setBlockAndUpdate(pos, state.setValue(COMPRESSION, compression - 1));
            world.playSound(null, pos, SoundEvents.BIG_DRIPLEAF_TILT_UP, SoundSource.BLOCKS, 1, 1.2F);
        } else {
            spring(world, pos, state);
        }
    }

    private static void spring(ServerLevel world, BlockPos pos, BlockState state) {
        Vec3 centerPos = pos.getCenter();
        world.getEntities(null, AABB.ofSize(centerPos, 1.0, 1.0, 1.0))
                .forEach(entity -> {
                    double d = getSpringPower(entity);
                    int r;
                    if ((r =world.getBestNeighborSignal(pos)) > 0) {
                        d *= (7.0 - Math.sqrt(r)) / 6.0;
                    }
                    Vec3 velocity = entity.getDeltaMovement();
                    if (d < velocity.y) return;
                    entity.setDeltaMovement(velocity.x, d, velocity.z);
                    entity.hurtMarked = true;
                    if (entity instanceof ServerPlayer serverPlayerEntity) {
                        serverPlayerEntity.currentImpulseImpactPos = serverPlayerEntity.position();
                        serverPlayerEntity.setIgnoreFallDamageFromCurrentImpulse(true);
                    }
                }
        );
        world.setBlockAndUpdate(pos, state.setValue(COMPRESSION, 0).setValue(STATE, SpringLotusState.PUSHING));
        world.scheduleTick(pos, state.getBlock(), 5);
        world.playSound(null, pos, SoundEvents.BIG_DRIPLEAF_TILT_DOWN, SoundSource.BLOCKS, 1, 1);
        world.sendParticles(VAParticleTypes.SPRING_LOTUS_POLLEN, false, false, centerPos.x(), centerPos.y() + 1.0, centerPos.z(), 25, 0.25, 0.25, 0.25, 0.25);
    }

    private static double getSpringPower(Entity entity) {
        double d = entity instanceof AbstractMinecart ? 2.8 : 1.6;
        if (entity.getType().is(VAEntityTypeTags.IGNORES_SPRING_LOTUS)) d *= 0.65;
        return d;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return BonemealableBlock.hasSpreadableNeighbourPos(world, pos, state);
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BonemealableBlock.findSpreadableNeighbourPos(world, pos, state).ifPresent(posx -> world.setBlockAndUpdate(posx, VABlocks.SMALL_SPRING_LOTUS.defaultBlockState()));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(STATE).equals(SpringLotusState.PUSHING) ? SHAPE_3 : super.getCollisionShape(state, world, pos, context);
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(COMPRESSION)) {
            case 0 -> SHAPE_0;
            case 1 -> SHAPE_1;
            case 2 -> SHAPE_2;
            case 3 -> SHAPE_3;
            case 4 -> SHAPE_4;
            default -> throw new IllegalStateException("Unexpected value: " + state.getValue(COMPRESSION));
        };
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return Shapes.empty();
    }

    static {
        SHAPE_4 = Block.column(16.0, 0.0, 4.0);
        SHAPE_3 = Shapes.or(Block.column(16.0, 4.0, 8.0), Block.column(14.0, 0.0, 8.0));
        SHAPE_2 = Shapes.or(Block.column(16.0, 8.0, 12.0), Block.column(12.0, 0.0, 12.0));
        SHAPE_1 = Shapes.or(Block.column(16.0, 10.0, 14.0), Block.column(10.0, 0.0, 14.0));
        SHAPE_0 = Shapes.or(Block.column(16.0, 12.0, 16.0), Block.column(8.0, 0.0, 16.0));
    }
}
