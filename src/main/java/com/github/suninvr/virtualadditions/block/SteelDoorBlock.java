package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class SteelDoorBlock extends DoorBlock {
    public static final MapCodec<SteelDoorBlock> CODEC = createCodec(SteelDoorBlock::new);
    public static final BooleanProperty SHUTTER_OPEN = BooleanProperty.of("shutter_open");

    public SteelDoorBlock(Settings settings) {
        super(VABlocks.STEEL_BLOCK_SET_TYPE, settings);
        this.setDefaultState(getStateManager().getDefaultState().with(SHUTTER_OPEN, false));
    }

    @Override
    public MapCodec<? extends DoorBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void onExploded(BlockState state, World world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
        if (explosion.getDestructionType() == Explosion.DestructionType.TRIGGER_BLOCK && state.get(HALF) == DoubleBlockHalf.UPPER && !world.isClient) {
            toggleShutter(world, state, pos, null);
        }
        super.onExploded(state, world, pos, explosion, stackMerger);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        return toggleShutter(world, state, pos, player) ? ActionResult.SUCCESS : toggleShutter(world, world.getBlockState(pos.up()), pos.up(), player) ? ActionResult.SUCCESS : ActionResult.PASS;
    }

    private static boolean toggleShutter(World world, BlockState state, BlockPos pos, @Nullable Entity source) {
        if (state.isOf(VABlocks.STEEL_DOOR) && state.get(HALF) == DoubleBlockHalf.UPPER) {
            state = state.cycle(SHUTTER_OPEN);
            world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
            playOpenCloseShutterSound(source, world, pos, state.get(SHUTTER_OPEN));
            world.emitGameEvent(source, state.get(SHUTTER_OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            return true;
        }
        return false;
    }

    private static void playOpenCloseShutterSound(@Nullable Entity entity, World world, BlockPos pos, boolean open) {
        world.playSound(entity, pos, open ? VASoundEvents.BLOCK_STEEL_DOOR_SHUTTER_OPEN : VASoundEvents.BLOCK_STEEL_DOOR_SHUTTER_CLOSE, SoundCategory.BLOCKS, 0.4F, world.getRandom().nextFloat() * 0.1F + 1.2F);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SHUTTER_OPEN);
    }
}
