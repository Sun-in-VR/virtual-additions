package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.RemoteNotifierBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class RemoteNotifierBlock extends BaseEntityBlock {
    public static final MapCodec<RemoteNotifierBlock> CODEC = simpleCodec(RemoteNotifierBlock::new);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RemoteNotifierBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer && level.getBlockEntity(blockPos) instanceof RemoteNotifierBlockEntity entity) {
            serverPlayer.openMenu(entity);
            //boolean subscribeTo = entity.subscribe(player);
            //boolean unsubscribeTo = false;
            //if (!itemStack.isEmpty()) {
            //    entity.setString(itemStack.getHoverName().getString());
            //    entity.setStack(itemStack);
            //}
            //if (!subscribeTo && itemStack.isEmpty()) {
            //    unsubscribeTo = entity.unsubscribe(player);
            //}
            //if (subscribeTo) level.playSound(null, blockPos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            //else if (unsubscribeTo) level.playSound(null, blockPos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0F, 0.5F);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (state.is(this) && !world.isClientSide()) {
            boolean gettingPower = world.hasNeighborSignal(pos);
            boolean isPowered = state.getValue(POWERED);
            if (gettingPower && !isPowered) {
                world.setBlockAndUpdate(pos, state.setValue(POWERED, true));
                if (world.getBlockEntity(pos) instanceof RemoteNotifierBlockEntity entity) entity.broadcast();
            }
            if (!gettingPower && isPowered) {
                world.setBlockAndUpdate(pos, state.setValue(POWERED, false));
            }

        }
    }

    @Override
    public @org.jspecify.annotations.Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RemoteNotifierBlockEntity(blockPos, blockState);
    }
}
