package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.IncenseBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class IncenseBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<IncenseBlock> CODEC = simpleCodec(IncenseBlock::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape X_AXIS_SHAPE = Block.box(5.0, 0.0, 3.0, 11.0, 7.0, 13.0);
    private static final VoxelShape Z_AXIS_SHAPE = Block.box(3.0, 0.0, 5.0, 13.0, 7.0, 11.0);
    public IncenseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.EAST)
                .setValue(LIT, false)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public static boolean canLight(BlockState blockState) {
        return blockState.is(VABlocks.INCENSE) && !blockState.getValue(LIT) && !blockState.getValue(WATERLOGGED);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (itemStack.isEmpty() && player.getAbilities().mayBuild && blockState.getValue(LIT)) {
            extinguish(player, blockState, level, blockPos);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);

    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(LIT)) {
            Optional<IncenseBlockEntity> optionalEntity = level.getBlockEntity(blockPos, VABlockEntityType.INCENSE);
            Vec3 vec3 = blockPos.getCenter().add(getCandleOffset(blockState));
            if (randomSource.nextFloat() < 0.333F && optionalEntity.isPresent() && optionalEntity.get().hasEffects()) {
                ParticleOptions options = optionalEntity.get().getFirstEffectParticle();
                if (options != null) level.addParticle(optionalEntity.get().getFirstEffectParticle(), vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
            }
            boolean b = randomSource.nextBoolean();
            if (b) level.addParticle(ParticleTypes.WHITE_SMOKE, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
            else level.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
            if (randomSource.nextFloat() < 0.2F) level.addParticle(ParticleTypes.ASH, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);

        }
    }

    private static Vec3 getCandleOffset(BlockState state) {
        return switch (state.getValue(FACING)) {
            case NORTH -> new Vec3(-0.3, 0.0, 0.0);
            case SOUTH -> new Vec3(0.3, 0.0, 0.0);
            case WEST -> new Vec3(0.0, 0.0, 0.3);
            case EAST -> new Vec3(0.0, 0.0, -0.3);
            default -> new Vec3(0.0, 0.0, 0.0);
        };
    }

    private static void extinguish(Player player, BlockState state, LevelAccessor level, BlockPos pos) {
        if (state.hasProperty(LIT)) {
            level.setBlock(pos, state.setValue(LIT, false), 3);
            level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return blockState.getValue(FACING).getAxis().equals(Direction.Axis.X) ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return Block.canSupportCenter(levelReader, blockPos.below(), Direction.UP);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(LIT).add(WATERLOGGED);
    }

    @Override
    protected BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    protected FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new IncenseBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, VABlockEntityType.INCENSE, IncenseBlockEntity::tick);
    }

    @Override
    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        if (!(Boolean)blockState.getValue(BlockStateProperties.WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
            if (!levelAccessor.isClientSide()) {
                if (blockState.getValue(LIT)) extinguish(null, blockState.setValue(BlockStateProperties.WATERLOGGED, true), levelAccessor, blockPos);
                else levelAccessor.setBlock(blockPos, blockState.setValue(BlockStateProperties.WATERLOGGED, true), 3);
                levelAccessor.scheduleTick(blockPos, fluidState.getType(), fluidState.getType().getTickDelay(levelAccessor));
            }

            return true;
        } else {
            return false;
        }
    }
}
