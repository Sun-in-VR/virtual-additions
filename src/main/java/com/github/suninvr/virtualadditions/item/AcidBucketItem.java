package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VAFluids;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class AcidBucketItem extends BucketItem {
    private final Fluid fluid = VAFluids.ACID;
    public AcidBucketItem(Properties settings) {
        super(VAFluids.ACID, settings);
    }

    @Override
    public boolean emptyContents(@Nullable LivingEntity user, Level world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        BlockState blockState = world.getBlockState(pos);
        boolean canPlace = blockState.isAir();
        boolean destroyBlock = (blockState.canBeReplaced(this.fluid) || !blockState.is(VABlockTags.ACID_UNBREAKABLE)) && !canPlace;
        canPlace = destroyBlock || canPlace;
        if (!canPlace) {
            return hitResult != null && this.emptyContents(user, world, hitResult.getBlockPos().relative(hitResult.getDirection()), null);
        } else if (false) {
            if (!world.isClientSide() && !blockState.liquid() && destroyBlock) {
                world.destroyBlock(pos, true);
            }

            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            world.playSound(user, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

            for(int l = 0; l < 8; ++l) {
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0, 0.0, 0.0);
            }

            return true;
        } else {
            if (!world.isClientSide() && !blockState.liquid() && destroyBlock) {
                world.destroyBlock(pos, true);
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 1.0F);
                for(int l = 0; l < 8; ++l) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0, 0.0, 0.0);
                }
            }

            if (!world.setBlock(pos, this.fluid.defaultFluidState().createLegacyBlock(), 11) && !blockState.getFluidState().isSource()) {
                return false;
            } else {
                this.playEmptySound(user, world, pos);
                return true;
            }
        }
    }

    @Override
    protected void playEmptySound(@Nullable LivingEntity user, LevelAccessor world, BlockPos pos) {
        world.playSound(user, pos, VASoundEvents.BUCKET_EMPTY_ACID, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(user, GameEvent.FLUID_PLACE, pos);
    }
}
