package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAEntityTypeTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class SpringLotusBlock extends PlantBlock implements Fertilizable {
    public static final MapCodec<SpringLotusBlock> CODEC = createCodec(SpringLotusBlock::new);
    public static final IntProperty COMPRESSION = IntProperty.of("compression", 0, 3);
    public static final BooleanProperty IS_PUSHING = BooleanProperty.of("is_pushing");
    public static final VoxelShape SHAPE_0, SHAPE_1, SHAPE_2, SHAPE_3;

    public SpringLotusBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(COMPRESSION, 3).with(IS_PUSHING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COMPRESSION, IS_PUSHING);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        double d = fallDistance - 12.0;
        super.onLandedUpon(world, state, pos, entity, d);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(IS_PUSHING)) {
            world.setBlockState(pos, state.with(IS_PUSHING, false));
            world.scheduleBlockTick(pos, this, 30);
            return;
        }
        int compression = state.get(COMPRESSION);
        if (compression < 3) {
            world.setBlockState(pos, state.with(COMPRESSION, compression + 1));
            if (compression < 2) world.scheduleBlockTick(pos, this, 8);
            world.playSound(null, pos, SoundEvents.BLOCK_BIG_DRIPLEAF_TILT_UP, SoundCategory.BLOCKS, 1, 1.2F);
        } else {
            final boolean[] sprung = {false};
            world.getOtherEntities(null, Box.of(pos.toCenterPos(), 1.0, 1.0, 1.0)).forEach(
                    entity -> {
                        sprung[0] = true;
                        double d = entity instanceof MinecartEntity ? 2.8 : 1.6;
                        entity.addVelocity(0.0, d, 0.0);
                        entity.velocityModified = true;
                    }
            );
            if (sprung[0]) {
                world.setBlockState(pos, state.with(COMPRESSION, 0).with(IS_PUSHING, true));
                world.scheduleBlockTick(pos, this, 10);
                world.playSound(null, pos, SoundEvents.BLOCK_BIG_DRIPLEAF_TILT_DOWN, SoundCategory.BLOCKS, 1, 1);
            }
        }
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient() && state.get(COMPRESSION) == 3 && !entity.getType().isIn(VAEntityTypeTags.IGNORES_SPRING_LOTUS) && !entity.isSneaking()) {
            world.scheduleBlockTick(pos, this, 1);
        }
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
        Fertilizable.findPosToSpreadTo(world, pos, state).ifPresent(posx -> world.setBlockState(posx, this.getDefaultState()));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(IS_PUSHING) ? SHAPE_3 : super.getCollisionShape(state, world, pos, context);
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
            default -> throw new IllegalStateException("Unexpected value: " + state.get(COMPRESSION));
        };
    }

    static {
        SHAPE_3 = VoxelShapes.union(Block.createColumnShape(16.0, 4.0, 8.0), Block.createColumnShape(12.0, 0.0, 8.0));
        SHAPE_2 = VoxelShapes.union(Block.createColumnShape(16.0, 8.0, 12.0), Block.createColumnShape(10.0, 0.0, 12.0));
        SHAPE_1 = VoxelShapes.union(Block.createColumnShape(16.0, 10.0, 14.0), Block.createColumnShape(10.0, 0.0, 14.0));
        SHAPE_0 = VoxelShapes.union(Block.createColumnShape(16.0, 12.0, 16.0), Block.createColumnShape(10.0, 0.0, 16.0));
    }
}
