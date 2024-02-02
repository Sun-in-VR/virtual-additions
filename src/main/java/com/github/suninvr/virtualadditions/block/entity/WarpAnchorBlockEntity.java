package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.WarpAnchorBlock;
import com.github.suninvr.virtualadditions.particle.IoliteRingParticleEffect;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WarpAnchorBlockEntity extends BlockEntity {
    private static final ParticleEffect particle;
    private static final ParticleEffect particleCeiling;

    public WarpAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.WARP_ANCHOR, pos, state);
    }

    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, WarpAnchorBlockEntity blockEntity) {
        if (state.get(WarpAnchorBlock.POWERED)) return;
        double i = pos.getX() + 0.5;
        double j = pos.getY();
        double k = pos.getZ() + 0.5;
        if (world.getTime() % 4L == 0L) {
            if (state.get(WarpAnchorBlock.FACING) == Direction.DOWN) world.addParticle(particleCeiling, i, j + 0.624, k, 0.0D, 0.0D, 0.0D);
            else world.addParticle(particle, i, j + 0.376, k, 0.0D, 0.0D, 0.0D);
        }
    }

    static {
        particle = new IoliteRingParticleEffect(false, 0.025, VAParticleTypes.IOLITE_ANCHOR_RING);
        particleCeiling = new IoliteRingParticleEffect(false, -0.025, VAParticleTypes.IOLITE_ANCHOR_RING);
    }

}
