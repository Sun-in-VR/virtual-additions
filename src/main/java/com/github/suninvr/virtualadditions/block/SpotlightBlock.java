package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.SpotlightBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.LightStatus;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class SpotlightBlock extends BlockWithEntity {
    public static final MapCodec<SpotlightBlock> CODEC = createCodec(SpotlightBlock::new);
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty POWERED = Properties.POWERED;

    public SpotlightBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState()
                .with(FACING, Direction.EAST)
                .with(POWERED, false)
        );
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(POWERED);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClient && blockEntity instanceof SpotlightBlockEntity) {
            ((SpotlightBlockEntity)blockEntity).updateLightLocation(world, pos, state);
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockState state1 = super.onBreak(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClient() && blockEntity instanceof SpotlightBlockEntity spotlightBlockEntity) {
            BlockPos lightPos = spotlightBlockEntity.getLightLocation();
            BlockState lightState = world.getBlockState(lightPos);
            if (lightState.isOf(VABlocks.SPOTLIGHT_LIGHT)) SpotlightLightBlock.setStatus(world, lightState, lightPos, state.get(FACING), LightStatus.NONE);
        }
        return state1;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        boolean redstonePower = world.isReceivingRedstonePower(pos);
        boolean isPowered = state.get(POWERED);
        if (redstonePower && !isPowered) {
            world.setBlockState(pos, state.with(POWERED, true));
            setLightState(world, state, pos, LightStatus.LIT);
        } else if (!redstonePower && isPowered) {
            world.setBlockState(pos, state.with(POWERED, false));
            setLightState(world, state, pos, LightStatus.UNLIT);
        }
    }

    private void setLightState(World world, BlockState state, BlockPos pos, LightStatus status) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClient() && blockEntity instanceof SpotlightBlockEntity spotlightBlockEntity) {
            Direction direction = state.get(FACING);
            BlockPos lightPos = spotlightBlockEntity.getLightLocation();
            BlockState lightState = world.getBlockState(lightPos);
            if (world.getBlockState(lightPos).isOf(VABlocks.SPOTLIGHT_LIGHT)) SpotlightLightBlock.setStatus(world, lightState, lightPos, direction, status);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpotlightBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!state.get(POWERED)) return null;
        return validateTicker(type, VABlockEntities.SPOTLIGHT_BLOCK_ENTITY, (SpotlightBlockEntity::tick));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite()).with(POWERED, world.isReceivingRedstonePower(pos));
    }
}
