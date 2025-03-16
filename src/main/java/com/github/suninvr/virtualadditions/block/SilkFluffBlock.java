package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAEntityTypeTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class SilkFluffBlock extends Block {
    public static final MapCodec<SilkFluffBlock> CODEC = createCodec(SilkFluffBlock::new);
    private static final Vec3d movement = new Vec3d(0.85, 1.0, 0.85);

    public SilkFluffBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return true;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        if (entity.getType().isIn(VAEntityTypeTags.PASSES_THROUGH_WEBBED_SILK)) entity.slowMovement(state, movement);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityContext) {
            if (entityContext.getEntity() != null) {
                Entity entity = entityContext.getEntity();
                if (entity.getType().isIn(VAEntityTypeTags.PASSES_THROUGH_WEBBED_SILK)) return VoxelShapes.empty();
            }
        }
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }
}
