package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class BalloonBulbBlock extends Block implements Fertilizable {
    public static final MapCodec<BalloonBulbBlock> CODEC = createCodec(BalloonBulbBlock::new);
    public static final IntProperty HEIGHT = IntProperty.of("height", 0, 25);
    private static final VoxelShape SHAPE = Block.createCuboidShape(3.0F, 0.0F, 3.0F, 13.0F, 14.0F, 13.0F);
    public BalloonBulbBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState().with(HEIGHT, 0));
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HEIGHT);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(HEIGHT) < 25;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 1) grow(world, random, pos, state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!canPlaceAt(state, world, pos)) world.breakBlock(pos, true);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState( pos.down()).isOf(VABlocks.BALLOON_BULB_PLANT);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(HEIGHT) < 25;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.up()).isAir();
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos abovePos = pos.up();
        BlockState aboveState = world.getBlockState(abovePos);
        if (aboveState.isAir()) {
            BlockPos belowPos = pos.down();
            BlockState belowState = world.getBlockState(belowPos);
            if (!belowState.isOf(VABlocks.BALLOON_BULB_PLANT)) return;
            world.setBlockState(abovePos, state.with(HEIGHT, state.get(HEIGHT) + 1));
            world.setBlockState(pos, belowState);
            world.setBlockState(belowPos, Blocks.AIR.getDefaultState());
        }

    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext && entityShapeContext.getEntity() != null) {
            if (entityShapeContext.getEntity().getType().isIn(EntityTypeTags.IMPACT_PROJECTILES)) return SHAPE;
        }
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d offset = state.getModelOffset(world, pos);
        return SHAPE.offset(offset.x, 0, offset.z);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        world.breakBlock(hit.getBlockPos(), true);
    }
}
