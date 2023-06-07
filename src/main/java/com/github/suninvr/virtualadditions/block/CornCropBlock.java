package com.github.suninvr.virtualadditions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class CornCropBlock extends CropBlock {
    public static final EnumProperty<CornCropSegment> SEGMENT = EnumProperty.of("segment", CornCropSegment.class);
    private static final VoxelShape SHAPE_AGE_1;
    private static final VoxelShape SHAPE_AGE_2;
    private static final VoxelShape SHAPE_AGE_3;

    public CornCropBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(SEGMENT, CornCropSegment.BOTTOM));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SEGMENT);
        super.appendProperties(builder);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d offset = state.getModelOffset(world, pos);
        return state.get(SEGMENT).getShape(state.get(AGE)).offset(offset.x, offset.y, offset.z);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getAvailableMoisture(this, world, pos);
                if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                    grow(world, state, pos);
                }
            }
        }
    }

    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + this.getGrowthAmount(world);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        setAge(world, state, pos, i);
    }

    private void grow(World world, BlockState state, BlockPos pos) {
        if (!state.isOf(this)) return;
        int age = state.get(AGE) + 1;
        setAge(world, state, pos, age);
    }
    private void setAge(World world, BlockState state, BlockPos pos, int age) {
        boolean growMiddle = age > 2;
        boolean growTop = age > 5;
        switch (state.get(SEGMENT)) {
            case BOTTOM -> {
                boolean canGrow = true;
                if (growMiddle) canGrow = canReplaceBlockState(world, pos.up());
                if (growTop) canGrow = canGrow && canReplaceBlockState(world, pos.up(2));
                if (canGrow) {
                    world.setBlockState(pos, this.bottomSegment(age), Block.NOTIFY_LISTENERS);
                    if (growMiddle) world.setBlockState(pos.up(), this.middleSegment(age), Block.NOTIFY_LISTENERS);
                    if (growTop) world.setBlockState(pos.up(2), this.topSegment(age), Block.NOTIFY_LISTENERS);
                }
            }
            case MIDDLE -> {
                boolean canGrow = true;
                if (growTop) canGrow = canReplaceBlockState(world, pos.up());
                if (canGrow) {
                    world.setBlockState(pos.down(), this.bottomSegment(age), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos, this.middleSegment(age), Block.NOTIFY_LISTENERS);
                    if (growTop) world.setBlockState(pos.up(), this.topSegment(age), Block.NOTIFY_LISTENERS);
                }
            }
            case TOP -> {
                world.setBlockState(pos.down(2), this.bottomSegment(age), Block.NOTIFY_LISTENERS);
                world.setBlockState(pos.down(), this.middleSegment(age), Block.NOTIFY_LISTENERS);
                world.setBlockState(pos, this.topSegment(age), Block.NOTIFY_LISTENERS);
            }
        }
    }


    public BlockState topSegment(int age) {
        return this.stateOf(age, CornCropSegment.TOP);
    }
    public BlockState middleSegment(int age) {
        return this.stateOf(age, CornCropSegment.MIDDLE);
    }
    public BlockState bottomSegment(int age) {
        return this.stateOf(age, CornCropSegment.BOTTOM);
    }

    public BlockState stateOf(int age, CornCropSegment segment) {
        return this.withAge(age).with(SEGMENT, segment);
    }
    
    private boolean canReplaceBlockState(World world, BlockPos pos) {
        return world.getBlockState(pos).isAir() || world.getBlockState(pos).isOf(this);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return switch (state.get(SEGMENT)) {
            case TOP -> checkSegment(world, pos, CornCropSegment.TOP, Direction.DOWN);
            case MIDDLE -> checkSegment(world, pos, CornCropSegment.MIDDLE, Direction.DOWN) && (state.get(AGE) <= 5 || checkSegment(world, pos, CornCropSegment.MIDDLE, Direction.UP));
            case BOTTOM -> ((world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos)) && super.canPlaceAt(state, world, pos)) && (state.get(AGE) <= 2 || checkSegment(world, pos, CornCropSegment.BOTTOM, Direction.UP));
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(SEGMENT, CornCropSegment.BOTTOM);
    }

    private boolean checkSegment(WorldView world, BlockPos pos, CornCropSegment segment, Direction direction) {
        CornCropSegment expectedSegment = direction.equals(Direction.UP) ? segment.aboveSegment() : segment.belowSegment();
        if (expectedSegment == null) return false;
        return world.getBlockState(pos.offset(direction)).isOf(this) && world.getBlockState(pos.offset(direction)).get(SEGMENT).equals(expectedSegment);
    }

    public enum CornCropSegment implements StringIdentifiable {
        TOP,
        MIDDLE,
        BOTTOM;

        @Override
        public String asString() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public VoxelShape getShape(int age) {
            if (this.equals(MIDDLE)) age -= 3;
            if (this.equals(TOP)) age -= 6;
            if (age < 1) return SHAPE_AGE_1;
            if (age > 1) return SHAPE_AGE_3;
            return SHAPE_AGE_2;
        }

        @Nullable
        public CornCropSegment aboveSegment() {
            return switch (this) {
                case TOP -> null;
                case MIDDLE -> TOP;
                case BOTTOM -> MIDDLE;
            };
        }

        @Nullable
        public CornCropSegment belowSegment() {
            return switch (this) {
                case TOP -> MIDDLE;
                case MIDDLE -> BOTTOM;
                case BOTTOM -> null;
            };
        }
    }

    static {
        SHAPE_AGE_1 = Block.createCuboidShape(4, 0, 4, 12, 4, 12);
        SHAPE_AGE_2 = Block.createCuboidShape(3, 0, 3, 13, 8, 13);
        SHAPE_AGE_3 = Block.createCuboidShape(3, 0, 3, 13, 16, 13);
    }
}
