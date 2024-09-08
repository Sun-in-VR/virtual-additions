package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class BalloonBulbPlantBlock extends Block implements Fertilizable, LandingBlock {
    public static final MapCodec<BalloonBulbPlantBlock> CODEC = createCodec(BalloonBulbPlantBlock::new);
    public static final IntProperty AGE = Properties.AGE_3;
    private static final VoxelShape SHAPE = Block.createCuboidShape(3.0F, 4.0F, 3.0F, 13.0F, 16.0F, 13.0F);
    public BalloonBulbPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState().with(AGE, 0));
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        world.scheduleBlockTick(pos, this, 2);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        BlockState aboveState = world.getBlockState(pos.up());
        int a = state.get(AGE);
        return a < 3 && aboveState.isOf(VABlocks.BALLOON_BULB) && aboveState.get(BalloonBulbBlock.HEIGHT) > a;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.isOf(this)) {
            int a = state.get(AGE);
            if (a < 3) {
                BlockState aboveState = world.getBlockState(pos.up());
                if (aboveState.isOf(VABlocks.BALLOON_BULB) && aboveState.get(BalloonBulbBlock.HEIGHT) > a) {
                    world.setBlockState(pos, state.with(AGE, a + 1));
                }
            }
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(2) == 1) grow(world, random, pos, state);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.up()).isOf(VABlocks.BALLOON_BULB);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.getBlockState(pos.up()).isOf(VABlocks.BALLOON_BULB)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
        }
    }

    @Override
    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        BlockState state = fallingBlockEntity.getBlockState();
        this.onBreak(world, pos, state, null);
        if (world instanceof ServerWorld serverWorld) Block.dropStacks(state, serverWorld, pos, null);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d offset = state.getModelOffset(pos);
        return SHAPE.offset(offset.x, 0, offset.z);
    }
}
