package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAFluids;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class LumwaspNestBlock extends TransparentBlock {
    public static final MapCodec<LumwaspNestBlock> CODEC = createCodec(LumwaspNestBlock::new);
    public static final BooleanProperty LARVAE = BooleanProperty.of("larvae");
    public LumwaspNestBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState().with(LARVAE, false));
    }

    @Override
    protected MapCodec<? extends TransparentBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LARVAE);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(LARVAE);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.getFluidState(pos.up()).isOf(VAFluids.ACID)) {
            world.setBlockState(pos, state.with(LARVAE, false));
        }
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
            if (world.isClient()) return;
            if (world.getFluidState(pos.up()).isOf(VAFluids.ACID) && world.getBlockState(pos.down()).isAir() && entity instanceof ItemEntity itemEntity) {
            ItemStack stack = itemEntity.getStack();
            System.out.println(stack);
            int i = 0;
            if (stack.isIn(VAItemTags.LUMWASP_LARVAE_FOOD)) i += stack.getCount();
            else return;

            boolean larvae = state.get(LARVAE);
            while (i > 0) {
                stack.decrement(1);
                if (world.getRandom().nextInt(6) == 1) {
                    if (larvae) {
                        world.setBlockState(pos.down(), VABlocks.GLOWING_SILK.getDefaultState());
                        return;
                    } else {
                        world.setBlockState(pos, state.with(LARVAE, true));
                    }
                }
                i--;
            }
        }
    }
}
