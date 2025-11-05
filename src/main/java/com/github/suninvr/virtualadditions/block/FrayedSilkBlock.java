package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FrayedSilkBlock extends VegetationBlock {
    public static final MapCodec<FrayedSilkBlock> CODEC = simpleCodec(FrayedSilkBlock::new);
    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
    public static final EnumProperty<Direction> VERTICAL_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public FrayedSilkBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any()
                .setValue(VERTICAL_DIRECTION, Direction.UP)
        );
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL_DIRECTION);
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction[] directions = ctx.getNearestLookingDirections();
        for (Direction direction : directions) {
            BlockState state = this.defaultBlockState().setValue(VERTICAL_DIRECTION, direction == Direction.DOWN ? Direction.UP : Direction.DOWN);
            if (canSurvive(state, ctx.getLevel(), ctx.getClickedPos())) return state;
        }
        return null;

    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.isFaceSturdy(world, pos, Direction.UP);
    }

    protected boolean canPlantOnBottom(BlockState ceiling, BlockGetter world, BlockPos pos) {
        return ceiling.isFaceSturdy(world, pos, Direction.DOWN);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = state.getValue(VERTICAL_DIRECTION);
        BlockPos placeOnPos = pos.relative(direction, -1);
        return direction == Direction.UP ? this.mayPlaceOn(world.getBlockState(placeOnPos), world, placeOnPos) : this.canPlantOnBottom(world.getBlockState(placeOnPos), world, placeOnPos);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(500) <= 1 && world.getBlockState(pos.relative(state.getValue(VERTICAL_DIRECTION).getOpposite())).is(VABlocks.SILK_BLOCK)) {
            world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), VASoundEvents.BLOCK_FRAYED_SILK_IDLE, SoundSource.BLOCKS, 1.0F, 0.7F, true);
        }
    }
}
