package com.github.suninvr.virtualadditions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SpringSoilBlock extends Block {
    public SpringSoilBlock(Settings settings) {
        super(settings);
    }

    @Override
    public float getJumpVelocityMultiplier() {
        return 2.2F;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.0f, world.getDamageSources().fall());
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        super.onEntityLand(world, entity);
    }
}