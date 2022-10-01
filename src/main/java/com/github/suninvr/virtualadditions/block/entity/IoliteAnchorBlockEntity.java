package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.IoliteAnchorBlock;
import com.github.suninvr.virtualadditions.particle.IoliteRingParticleEffect;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IoliteAnchorBlockEntity extends BlockEntity {
    public IoliteAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntities.IOLITE_ANCHOR_BLOCK_ENTITY, pos, state);
    }
    private BlockState blockState;
    private static final ParticleEffect particle;
    private static final ParticleEffect particleCeiling;

    public static void tick(World world, BlockPos pos, BlockState state, IoliteAnchorBlockEntity blockEntity) {
        if (state.get(IoliteAnchorBlock.POWERED)) return;
        float i = pos.getX() + 0.5F;
        float j = pos.getY();
        float k = pos.getZ() + 0.5F;
        if (world.getTime() % 4L - 2 == 0L) {
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

        return this.blockState.get(IoliteAnchorBlock.CEILING);
    }

    static {
        particle = new IoliteRingParticleEffect(false, 0.05, VAParticleTypes.IOLITE_ANCHOR_RING);
        particleCeiling = new IoliteRingParticleEffect(false, -0.05, VAParticleTypes.IOLITE_ANCHOR_RING);
    }

}
