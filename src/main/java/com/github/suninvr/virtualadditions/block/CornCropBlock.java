package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class CornCropBlock extends CropBlock {
    public static final MapCodec<CornCropBlock> CODEC = simpleCodec(CornCropBlock::new);
    public static final EnumProperty<Segment> SEGMENT = EnumProperty.create("segment", Segment.class);
    private static final VoxelShape SHAPE_AGE_1;
    private static final VoxelShape SHAPE_AGE_2;
    private static final VoxelShape SHAPE_AGE_3;

    public CornCropBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any().setValue(SEGMENT, Segment.BOTTOM));
    }

    @Override
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SEGMENT);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(pos);
        return state.getValue(SEGMENT).getShape(state.getValue(AGE)).move(offset.x, offset.y, offset.z);
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) world.destroyBlock(pos, true);
        super.tick(state, world, pos, random);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        tickView.scheduleTick(pos, this, 1);
        return state;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.getRawBrightness(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed(this, world, pos);
                if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                    grow(world, state, pos);
                }
            }
        }
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return VAItems.CORN_SEEDS;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return isSpaceToGrow(world, state, pos) && super.isValidBonemealTarget(world, pos, state);
    }

    public void growCrops(Level world, BlockPos pos, BlockState state) {
        int i = Math.min(this.getAge(state) + this.getBonemealAgeIncrease(world), this.getMaxAgeForSpace(world, pos, state));
        setAge(world, state, pos, i);
    }

    private int getMaxAgeForSpace(Level world, BlockPos pos, BlockState state) {
        if (!state.is(VABlocks.CORN_CROP)) return 0;
        Segment segment = state.getValue(SEGMENT);
        if (!(segment.equals(Segment.BOTTOM))) {
            BlockPos offsetPos = pos.above(segment.getYOffset(Segment.BOTTOM));
            return getMaxAgeForSpace(world, offsetPos, world.getBlockState(offsetPos));
        }
        if (canReplaceBlockState(world, pos.above().above())) return 7;
        if (canReplaceBlockState(world, pos.above())) return 5;
        return 2;
    }

    private void grow(Level world, BlockState state, BlockPos pos) {
        if (!state.is(this)) return;
        int age = state.getValue(AGE) + 1;
        setAge(world, state, pos, age);
    }

    private boolean isSpaceToGrow(LevelReader world, BlockState state, BlockPos pos) {
        boolean bl = true;
        int age = state.getValue(AGE);
        Segment segment = state.getValue(SEGMENT);
        if (segment.equals(Segment.BOTTOM) && age >= 2) bl = checkForState(world, pos, state, Segment.MIDDLE, true);
        if (!segment.equals(Segment.TOP) && age >= 5) bl = bl && checkForState(world, pos, state, Segment.TOP, true);
        return bl;
    }

    private void setAge(Level world, BlockState state, BlockPos pos, int age) {
        boolean growMiddle = age > 2;
        boolean growTop = age > 5;
        switch (state.getValue(SEGMENT)) {
            case BOTTOM -> {
                boolean canGrow = true;
                if (growMiddle) canGrow = canReplaceBlockState(world, pos.above());
                if (growTop) canGrow = canGrow && canReplaceBlockState(world, pos.above(2));
                if (canGrow) {
                    world.setBlock(pos, this.bottomSegment(age), Block.UPDATE_CLIENTS);
                    if (growMiddle) world.setBlock(pos.above(), this.middleSegment(age), Block.UPDATE_CLIENTS);
                    if (growTop) world.setBlock(pos.above(2), this.topSegment(age), Block.UPDATE_CLIENTS);
                }
            }
            case MIDDLE -> {
                boolean canGrow = true;
                if (growTop) canGrow = canReplaceBlockState(world, pos.above());
                if (canGrow) {
                    world.setBlock(pos.below(), this.bottomSegment(age), Block.UPDATE_CLIENTS);
                    world.setBlock(pos, this.middleSegment(age), Block.UPDATE_CLIENTS);
                    if (growTop) world.setBlock(pos.above(), this.topSegment(age), Block.UPDATE_CLIENTS);
                }
            }
            case TOP -> {
                world.setBlock(pos.below(2), this.bottomSegment(age), Block.UPDATE_CLIENTS);
                world.setBlock(pos.below(), this.middleSegment(age), Block.UPDATE_CLIENTS);
                world.setBlock(pos, this.topSegment(age), Block.UPDATE_CLIENTS);
            }
        }
    }

    private boolean checkForState(LevelReader world, BlockPos pos, BlockState state, Segment checkSegment, boolean allowAir) {
        Segment segment = state.getValue(SEGMENT);
        int offset = segment.getYOffset(checkSegment);
        BlockState checkState = world.getBlockState(pos.relative(Direction.UP, offset));
        if (checkState.is(VABlocks.CORN_CROP)) return checkState.getValue(SEGMENT).equals(checkSegment) && checkState.getValue(AGE).equals(state.getValue(AGE));
        return allowAir && world.getBlockState(pos.relative(Direction.UP, offset)).isAir();
    }


    public BlockState topSegment(int age) {
        return this.stateOf(age, Segment.TOP);
    }
    public BlockState middleSegment(int age) {
        return this.stateOf(age, Segment.MIDDLE);
    }
    public BlockState bottomSegment(int age) {
        return this.stateOf(age, Segment.BOTTOM);
    }

    public BlockState stateOf(int age, Segment segment) {
        return this.getStateForAge(age).setValue(SEGMENT, segment);
    }
    
    private boolean canReplaceBlockState(Level world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isAir() || state.is(VABlocks.SPOTLIGHT_LIGHT) || state.is(this);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return switch (state.getValue(SEGMENT)) {
            case TOP -> checkSegment(world, pos, state, Direction.DOWN);
            case MIDDLE -> checkSegment(world, pos, state, Direction.DOWN) && (state.getValue(AGE) <= 5 || checkSegment(world, pos, state, Direction.UP));
            case BOTTOM -> ((world.getRawBrightness(pos, 0) >= 8 || world.canSeeSky(pos)) && super.canSurvive(state, world, pos)) && (state.getValue(AGE) <= 2 || checkSegment(world, pos, state, Direction.UP));
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(SEGMENT, Segment.BOTTOM);
    }

    private boolean checkSegment(LevelReader world, BlockPos pos, BlockState state, Direction direction) {
        Segment segment = state.getValue(SEGMENT);
        Segment expectedSegment = direction.equals(Direction.UP) ? segment.aboveSegment() : segment.belowSegment();
        if (expectedSegment == null) return false;
        BlockState offsetState = world.getBlockState(pos.relative(direction));
        return offsetState.is(this) && offsetState.getValue(SEGMENT).equals(expectedSegment) && offsetState.getValue(AGE).equals(state.getValue(AGE));
    }

    public enum Segment implements StringRepresentable {
        TOP,
        MIDDLE,
        BOTTOM;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public VoxelShape getShape(int age) {
            age -= this.minAge();
            if (age < 1) return SHAPE_AGE_1;
            if (age > 1) return SHAPE_AGE_3;
            return SHAPE_AGE_2;
        }

        @Nullable
        public CornCropBlock.Segment aboveSegment() {
            return switch (this) {
                case TOP -> null;
                case MIDDLE -> TOP;
                case BOTTOM -> MIDDLE;
            };
        }

        @Nullable
        public CornCropBlock.Segment belowSegment() {
            return switch (this) {
                case TOP -> MIDDLE;
                case MIDDLE -> BOTTOM;
                case BOTTOM -> null;
            };
        }

        public int getYOffset(Segment expectedSegment) {
            return this.ordinal() - expectedSegment.ordinal();
        }

        public int minAge() {
            return switch (this) {
                case TOP -> 6;
                case MIDDLE -> 3;
                case BOTTOM -> 0;
            };
        }
    }

    static {
        SHAPE_AGE_1 = Block.box(3, 0, 3, 13, 4, 13);
        SHAPE_AGE_2 = Block.box(3, 0, 3, 13, 8, 13);
        SHAPE_AGE_3 = Block.box(3, 0, 3, 13, 16, 13);
    }
}
