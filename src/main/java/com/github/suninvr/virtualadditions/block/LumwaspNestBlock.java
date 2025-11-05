package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAFluids;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

@SuppressWarnings("deprecation")
public class LumwaspNestBlock extends TransparentBlock {
    public static final MapCodec<LumwaspNestBlock> CODEC = simpleCodec(LumwaspNestBlock::new);
    public static final BooleanProperty LARVAE = BooleanProperty.create("larvae");
    public LumwaspNestBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any().setValue(LARVAE, false));
    }

    @Override
    protected MapCodec<? extends TransparentBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LARVAE);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(LARVAE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!world.getFluidState(pos.above()).is(VAFluids.ACID) && !world.getBlockState(pos.above()).is(VABlocks.ACID_BLOCK)) {
            world.setBlockAndUpdate(pos, state.setValue(LARVAE, false));
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(250) == 0 && state.getValue(LARVAE)) {
            world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), VASoundEvents.BLOCK_LUMWASP_NEST_IDLE, SoundSource.BLOCKS, 1.0F, 1.2F, true);
        }
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
            if (world.isClientSide()) return;
            if (world.getFluidState(pos.above()).is(VAFluids.ACID) && world.getBlockState(pos.below()).isAir() && entity instanceof ItemEntity itemEntity) {
            ItemStack stack = itemEntity.getItem();
            int i = 0;
            if (stack.is(VAItemTags.LUMWASP_LARVAE_FOOD)) i += stack.getCount();
            else return;

            boolean larvae = state.getValue(LARVAE);
            while (i > 0) {
                stack.shrink(1);
                if (larvae) {
                    world.setBlockAndUpdate(pos.below(), VABlocks.GLOWING_SILK.defaultBlockState());
                    return;
                } else {
                    world.setBlockAndUpdate(pos, state.setValue(LARVAE, true));
                }
                i--;
            }
        }
    }
}
