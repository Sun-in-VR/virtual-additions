package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AbstractFilmBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
    public static final VoxelShape X_SHAPE;
    public static final VoxelShape Y_SHAPE;
    public static final VoxelShape Z_SHAPE;

    public AbstractFilmBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.X));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AXIS)) {
            case X -> X_SHAPE;
            case Y -> Y_SHAPE;
            case Z -> Z_SHAPE;
        };
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (world.isClient) return;
        if (fallDistance > 3.33F) {
            int rand = world.getRandom().nextInt(20) + 4;
            System.out.println(rand);
            if (fallDistance > rand) {
                breakFilm(world, pos, state, entity);
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_HONEY_BLOCK_BREAK, SoundCategory.BLOCKS, 1.5F, 0.6F);
            } else {
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_HONEY_BLOCK_FALL, SoundCategory.BLOCKS, 0.8F, 0.8F);
            }
        }
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        breakFilm(world, hit.getBlockPos(), state, projectile.getOwner());
        world.playSound(null, hit.getBlockPos(), SoundEvents.BLOCK_HONEY_BLOCK_BREAK, SoundCategory.BLOCKS, 1.5F, 0.6F);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean supportingBlock = switch (state.get(AXIS)) {
            case X -> sourcePos.getX() == pos.getX();
            case Y -> sourcePos.getY() == pos.getY();
            case Z -> sourcePos.getZ() == pos.getZ();
        };
        BlockState updatedSourceState = world.getBlockState(sourcePos);
        if (supportingBlock && !(updatedSourceState.isFullCube(world, sourcePos) || isFilm(updatedSourceState))) {
            world.createAndScheduleBlockTick(pos, this, world.getRandom().nextInt(3)+2);
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        breakFilm(world, pos, state);
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return isFilm(stateFrom) || super.isSideInvisible(state, stateFrom, direction);
    }

    public void breakFilm(World world, BlockPos pos, BlockState state) {
        breakFilm(world, pos, state, null);
    }

    public void breakFilm(World world, BlockPos pos, BlockState state, @Nullable Entity entity) {
        if (entity == null) {
            world.breakBlock(pos, true);
        } else {
            world.breakBlock(pos, true, entity);
        }
    }

    public static boolean isFilm(BlockState state) {
        if (state.isOf(VABlocks.VISCOUS_FILM)) {
            return true;
        } else if (state.isOf(VABlocks.VISCOUS_POD)) {
            return state.get(ViscousPodBlock.FILM);
        }
        return false;
    }

    static {
        X_SHAPE = Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
        Y_SHAPE = Block.createCuboidShape(0.0, 6.0, 0.0, 16.0, 10.0, 16.0);
        Z_SHAPE = Block.createCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    }
}
