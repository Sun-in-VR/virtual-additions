package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.WarpTetherBlock;
import com.github.suninvr.virtualadditions.particle.IoliteRingParticleEffect;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WarpAnchorBlockEntity extends BlockEntity {
    public WarpAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntities.IOLITE_ANCHOR_BLOCK_ENTITY, pos, state);
    }
    private BlockState blockState;
    private static final ParticleEffect particle;
    private static final ParticleEffect particleCeiling;

    public static void tick(World world, BlockPos pos, BlockState state, WarpAnchorBlockEntity blockEntity) {
        if (state.get(WarpTetherBlock.POWERED)) return;
        float i = pos.getX() + 0.5F;
        float j = pos.getY();
        float k = pos.getZ() + 0.5F;
        if (world.getTime() % 4L == 0L) {
            if (blockEntity.isUpsideDown()) world.addParticle(particleCeiling, i, j + 0.624, k, 0.0D, 0.0D, 0.0D);
            else world.addParticle(particle, i, j + 0.376, k, 0.0D, 0.0D, 0.0D);
        }
    }

    public boolean isUpsideDown() {
        if (this.blockState == null) {
            if (this.world != null) {
                this.blockState = this.getWorld().getBlockState(this.pos);
            } else return false;
        }

        return this.blockState.get(WarpTetherBlock.FACING).equals(Direction.DOWN);
    }

    static {
        particle = new IoliteRingParticleEffect(false, 0.025, VAParticleTypes.IOLITE_ANCHOR_RING);
        particleCeiling = new IoliteRingParticleEffect(false, -0.025, VAParticleTypes.IOLITE_ANCHOR_RING);
    }

}
