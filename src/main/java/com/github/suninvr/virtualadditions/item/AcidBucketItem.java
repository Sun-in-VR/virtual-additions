package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VAFluids;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class AcidBucketItem extends BucketItem {
    private final Fluid fluid = VAFluids.ACID;
    public AcidBucketItem(Fluid fluid, Settings settings) {
        super(fluid, settings);
    }

    @Override
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        Material material = blockState.getMaterial();
        boolean bl = blockState.canBucketPlace(this.fluid);
        boolean canPlace = blockState.isAir() || bl || block instanceof FluidFillable && ((FluidFillable)block).canFillWithFluid(world, pos, blockState, this.fluid);
        boolean destroyBlock = !blockState.isIn(VABlockTags.ACID_UNBREAKABLE) && !canPlace;
        canPlace = destroyBlock || canPlace;
        bl = destroyBlock || bl;
        if (!canPlace) {
            return hitResult != null && this.placeFluid(player, world, hitResult.getBlockPos().offset(hitResult.getSide()), null);
        } else if (world.getDimension().ultrawarm()) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

            for(int l = 0; l < 8; ++l) {
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0, 0.0, 0.0);
            }

            return true;
        } else if (block instanceof FluidFillable && this.fluid == Fluids.WATER) {
            ((FluidFillable)block).tryFillWithFluid(world, pos, blockState, ((FlowableFluid)this.fluid).getStill(false));
            this.playEmptyingSound(player, world, pos);
            return true;
        } else {
            if (!world.isClient && bl && !blockState.isLiquid()) {
                world.breakBlock(pos, true);
                if (destroyBlock) {
                    int i = pos.getX();
                    int j = pos.getY();
                    int k = pos.getZ();
                    world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 1.0F);
                    for(int l = 0; l < 8; ++l) {
                        world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0, 0.0, 0.0);
                    }
                }
            }

            if (!world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(), 11) && !blockState.getFluidState().isStill()) {
                return false;
            } else {
                this.playEmptyingSound(player, world, pos);
                return true;
            }
        }
    }

    @Override
    protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
        world.playSound(player, pos, VASoundEvents.BUCKET_EMPTY_ACID, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
    }
}
