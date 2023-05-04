package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class FrayedSilkBlock extends PlantBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
    public static final DirectionProperty VERTICAL_DIRECTION = Properties.VERTICAL_DIRECTION;
    public FrayedSilkBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(VERTICAL_DIRECTION, Direction.UP)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL_DIRECTION);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction[] directions = ctx.getPlacementDirections();
        for (Direction direction : directions) {
            BlockState state = this.getDefaultState().with(VERTICAL_DIRECTION, direction == Direction.DOWN ? Direction.UP : Direction.DOWN);
            if (canPlaceAt(state, ctx.getWorld(), ctx.getBlockPos())) return state;
        }
        return null;

    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos) || floor.isOf(VABlocks.SILK_BLOCK);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(VERTICAL_DIRECTION);
        BlockPos placeOnPos = pos.offset(direction, -1);
        return this.canPlantOnTop(world.getBlockState(placeOnPos), world, placeOnPos);
    }
}
