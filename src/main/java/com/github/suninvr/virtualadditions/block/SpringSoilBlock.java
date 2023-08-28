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
        return 1.75F;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.0f, world.getDamageSources().fall());
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if (!entity.bypassesLandingEffects() && entity.getVelocity().y < -0.75) {
            entity.setVelocity(entity.getVelocity().multiply(1.0, -1, 1.0));
        } else {
            super.onEntityLand(world, entity);
        }
    }
}
