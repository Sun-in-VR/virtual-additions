package com.github.suninvr.virtualadditions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FrayedSilkBlock extends PlantBlock {
    public static final MapCodec<FrayedSilkBlock> CODEC = createCodec(FrayedSilkBlock::new);
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
    public static final EnumProperty<Direction> VERTICAL_DIRECTION = Properties.VERTICAL_DIRECTION;
    public FrayedSilkBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState()
                .with(VERTICAL_DIRECTION, Direction.UP)
        );
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
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
        return floor.isSideSolidFullSquare(world, pos, Direction.UP);
    }

    protected boolean canPlantOnBottom(BlockState ceiling, BlockView world, BlockPos pos) {
        return ceiling.isSideSolidFullSquare(world, pos, Direction.DOWN);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(VERTICAL_DIRECTION);
        BlockPos placeOnPos = pos.offset(direction, -1);
        return direction == Direction.UP ? this.canPlantOnTop(world.getBlockState(placeOnPos), world, placeOnPos) : this.canPlantOnBottom(world.getBlockState(placeOnPos), world, placeOnPos);
    }
}
