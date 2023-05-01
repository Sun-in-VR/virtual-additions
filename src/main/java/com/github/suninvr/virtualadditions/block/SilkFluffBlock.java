package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAEntityTypeTags;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class SilkFluffBlock extends TransparentBlock {
    private static final Vec3d movement = new Vec3d(0.85, 0.85, 0.85);
    public SilkFluffBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity.getType() == VAEntityType.LUMWASP) return;
        entity.slowMovement(state, movement);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityContext) {
            if (entityContext.getEntity() != null) {
                if (!(entityContext.getEntity().getType().isIn(VAEntityTypeTags.COLLIDES_WITH_SILK_FLUFF) || entityContext.getEntity().isSneaking())) return VoxelShapes.empty();
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
