package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.client.toast.RemoteNotifierToast;
import com.github.suninvr.virtualadditions.network.RemoteNotifierS2CPayload;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VAPackets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class RemoteNotifierBlock extends Block {
    public static final BooleanProperty POWERED = Properties.POWERED;

    public RemoteNotifierBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && player instanceof ServerPlayerEntity serverPlayer) {
            ItemStack stack = player.getMainHandStack();
            ServerPlayNetworking.send(serverPlayer, new RemoteNotifierS2CPayload(stack.getName().getString(), stack));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (state.isOf(this) && !world.isClient) {
            boolean gettingPower = world.isReceivingRedstonePower(pos);
            boolean isPowered = state.get(POWERED);
            if (gettingPower && !isPowered) {
                world.setBlockState(pos, state.with(POWERED, true));
                ServerPlayerEntity player = (ServerPlayerEntity) world.getPlayers().getFirst();
                if (player != null) ServerPlayNetworking.send(player, new RemoteNotifierS2CPayload("Warning! Rocket stock is low!", Items.FIREWORK_ROCKET.getDefaultStack()));
            }
            if (!gettingPower && isPowered) {
                world.setBlockState(pos, state.with(POWERED, false));
            }

        }
    }
}
