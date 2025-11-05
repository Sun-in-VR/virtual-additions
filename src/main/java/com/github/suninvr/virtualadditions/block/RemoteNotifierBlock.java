package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.network.RemoteNotifierS2CPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class RemoteNotifierBlock extends Block {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RemoteNotifierBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ItemStack stack = player.getMainHandItem();
            ServerPlayNetworking.send(serverPlayer, new RemoteNotifierS2CPayload(stack.getHoverName().getString(), stack));
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
                ServerPlayer player = (ServerPlayer) world.players().getFirst();
                if (player != null) ServerPlayNetworking.send(player, new RemoteNotifierS2CPayload("Warning! Rocket stock is low!", Items.FIREWORK_ROCKET.getDefaultInstance()));
            }
            if (!gettingPower && isPowered) {
                world.setBlockAndUpdate(pos, state.setValue(POWERED, false));
            }

        }
    }
}
