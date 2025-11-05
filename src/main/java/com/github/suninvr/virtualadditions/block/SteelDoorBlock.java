package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class SteelDoorBlock extends DoorBlock {
    public static final MapCodec<SteelDoorBlock> CODEC = simpleCodec(SteelDoorBlock::new);
    public static final BooleanProperty SHUTTER_OPEN = BooleanProperty.create("shutter_open");

    public SteelDoorBlock(Properties settings) {
        super(VABlocks.STEEL_BLOCK_SET_TYPE, settings);
        this.registerDefaultState(getStateDefinition().any().setValue(SHUTTER_OPEN, false));
    }

    @Override
    public MapCodec<? extends DoorBlock> codec() {
        return CODEC;
    }

    @Override
    protected void onExplosionHit(BlockState state, ServerLevel world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
        if (explosion.getBlockInteraction() == Explosion.BlockInteraction.TRIGGER_BLOCK && state.getValue(HALF) == DoubleBlockHalf.UPPER && !world.isClientSide()) {
            toggleShutter(world, state, pos, null);
        }
        super.onExplosionHit(state, world, pos, explosion, stackMerger);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        return toggleShutter(world, state, pos, player) ? InteractionResult.SUCCESS : toggleShutter(world, world.getBlockState(pos.above()), pos.above(), player) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    private static boolean toggleShutter(Level world, BlockState state, BlockPos pos, @Nullable Entity source) {
        if (state.getBlock() instanceof SteelDoorBlock && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            state = state.cycle(SHUTTER_OPEN);
            world.setBlock(pos, state, Block.UPDATE_CLIENTS | Block.UPDATE_IMMEDIATE);
            playOpenCloseShutterSound(source, world, pos, state.getValue(SHUTTER_OPEN));
            world.gameEvent(source, state.getValue(SHUTTER_OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            return true;
        }
        return false;
    }

    private static void playOpenCloseShutterSound(@Nullable Entity entity, Level world, BlockPos pos, boolean open) {
        world.playSound(entity, pos, open ? VASoundEvents.BLOCK_STEEL_DOOR_SHUTTER_OPEN : VASoundEvents.BLOCK_STEEL_DOOR_SHUTTER_CLOSE, SoundSource.BLOCKS, 0.4F, world.getRandom().nextFloat() * 0.1F + 1.2F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SHUTTER_OPEN);
    }
}
