package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BalloonBulbPlantBlock extends Block implements BonemealableBlock, Fallable {
    public static final MapCodec<BalloonBulbPlantBlock> CODEC = simpleCodec(BalloonBulbPlantBlock::new);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape SHAPE = Block.box(3.0F, 4.0F, 3.0F, 13.0F, 16.0F, 13.0F);
    public BalloonBulbPlantBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any().setValue(AGE, 0));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        world.scheduleTick(pos, this, 2);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        BlockState aboveState = world.getBlockState(pos.above());
        int a = state.getValue(AGE);
        return a < 3 && aboveState.is(VABlocks.BALLOON_BULB) && aboveState.getValue(BalloonBulbBlock.HEIGHT) > a;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        if (state.is(this)) {
            int a = state.getValue(AGE);
            if (a < 3) {
                BlockState aboveState = world.getBlockState(pos.above());
                if (aboveState.is(VABlocks.BALLOON_BULB) && aboveState.getValue(BalloonBulbBlock.HEIGHT) > a) {
                    world.setBlockAndUpdate(pos, state.setValue(AGE, a + 1));
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(2) == 1) performBonemeal(world, random, pos, state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.above()).is(VABlocks.BALLOON_BULB);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!world.getBlockState(pos.above()).is(VABlocks.BALLOON_BULB)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(world, pos, state);
        }
    }

    @Override
    public void onBrokenAfterFall(Level world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        BlockState state = fallingBlockEntity.getBlockState();
        this.playerWillDestroy(world, pos, state, null);
        if (world instanceof ServerLevel serverWorld) Block.dropResources(state, serverWorld, pos, null);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(pos);
        return SHAPE.move(offset.x, 0, offset.z);
    }
}
