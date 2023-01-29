package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.enums.HangingGlowsilkShape;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TransparentBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class LumwaspNestBlock extends TransparentBlock {
    public static final BooleanProperty LARVAE = BooleanProperty.of("larvae");
    public LumwaspNestBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LARVAE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LARVAE);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.BLOCK, pos.up()) >= 15 && random.nextInt(5) == 0) {
            if (state.get(LARVAE)) {
                growSilk(world, pos);
            } else {
                world.setBlockState(pos, state.with(LARVAE, true));
            }
        }
    }

    private void growSilk(World world, BlockPos pos) {
        int i = 0;
        BlockState checkState;
        BlockPos checkPos;
        BlockPos checkPosDown;
        while (i <= 7) {
            checkPos = pos.down(i);
            checkPosDown = checkPos.down();
            checkState = world.getBlockState(checkPos);

            if (checkPos == pos) {
                if (world.getBlockState(checkPosDown).isAir()) {
                    world.setBlockState(checkPosDown, VABlocks.HANGING_GLOWSILK.getDefaultState());
                    return;
                }
            } else if (checkState.isOf(VABlocks.HANGING_GLOWSILK)) {
                if (checkState.get(HangingGlowsilkBlock.SHAPE).isEnd() && world.getBlockState(checkPosDown).isAir()) {
                    world.setBlockState(checkPos, checkState.with(HangingGlowsilkBlock.SHAPE, HangingGlowsilkShape.STRAIGHT));
                    world.setBlockState(checkPosDown, checkState.with(HangingGlowsilkBlock.SHAPE, HangingGlowsilkShape.END));
                    return;
                }
            } else {
                return;
            }
            i += 1;
        }
        if (world.getBlockState(pos.down()).isAir()) world.setBlockState(pos.down(), VABlocks.HANGING_GLOWSILK.getDefaultState());
    }
}
