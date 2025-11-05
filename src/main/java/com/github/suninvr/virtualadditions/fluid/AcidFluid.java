package com.github.suninvr.virtualadditions.fluid;

import com.github.suninvr.virtualadditions.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.Optional;

public abstract class AcidFluid extends FlowingFluid {
    @Override
    public Fluid getFlowing() {
        return VAFluids.FLOWING_ACID;
    }

    @Override
    public Fluid getSource() {
        return VAFluids.ACID;
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == getSource() || fluid == getFlowing() || fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER;
    }

    @Override
    protected boolean canConvertToSource(ServerLevel serverWorld) {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropResources(state, world, pos, blockEntity);
    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    @Override
    protected void randomTick(ServerLevel serverWorld, BlockPos pos, FluidState state, RandomSource random) {
        if (state.getAmount() > 6 && serverWorld.getBlockState(pos.below()).is(Blocks.MAGMA_BLOCK)) {
            serverWorld.setBlockAndUpdate(pos, VABlocks.ACID_BLOCK.defaultBlockState());
        }

        BlockPos offsetPos = pos.relative(Direction.getRandom(random));
        BlockState offsetState = serverWorld.getBlockState(offsetPos);
        if (offsetState.getBlock() instanceof WeatheringCopper) {
            serverWorld.setBlockAndUpdate(offsetPos, WeatheringCopper.getPrevious(offsetState).orElse(offsetState));
        }
    }

    public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {
        if (!state.isSource() && !(Boolean)state.getValue(FALLING)) {
            if (random.nextInt(64) == 0) {
                world.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.1F, true);
            }
        } else if ( world.getBlockState(pos.above()).isAir()) {
            if (random.nextInt(200) == 0) {
                world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), VASoundEvents.ACID_AMBIENT, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, true);
            } else if (random.nextInt(100) == 0) {
                world.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, VASoundEvents.ACID_SIZZLE, SoundSource.BLOCKS, 0.6F + random.nextFloat() * 0.2F, 0.8F + random.nextFloat() * 0.3F, true);
                world.addParticle(VAParticleTypes.ACID_SPLASH_EMITTER, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
            }
        }

    }

    @Override
    protected int getSlopeFindDistance(LevelReader world) {
        return 7;
    }

    @Override
    protected int getDropOff(LevelReader world) {
        return 1;
    }

    @Override
    public Item getBucket() {
        return VAItems.ACID_BUCKET;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockGetter world, BlockPos pos, Fluid fluid, Direction direction) {
        return !state.is(VAFluids.ACID_TAG);
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(VASoundEvents.BUCKET_FILL_ACID);
    }

    @Override
    public int getTickDelay(LevelReader world) {
        return 10;
    }

    @Override
    protected float getExplosionResistance() {
        return 100;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return VABlocks.ACID.defaultBlockState().setValue(BlockStateProperties.LEVEL, getLegacyLevel(state));
    }


    public static class Flowing extends AcidFluid {
        public Flowing() {
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Still extends AcidFluid {
        public Still() {
        }

        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
