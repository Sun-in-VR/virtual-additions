package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class SteelDoorBlock extends DoorBlock {
    public static final BooleanProperty SHUTTER_OPEN = BooleanProperty.of("shutter_open");

    public SteelDoorBlock(Settings settings) {
        super(settings, BlockSetType.IRON);
        setDefaultState(getStateManager().getDefaultState().with(SHUTTER_OPEN, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            state = state.cycle(SHUTTER_OPEN);
            world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
            this.playOpenCloseShutterSound(player, world, pos, state.get(SHUTTER_OPEN));
            world.emitGameEvent(player, state.get(SHUTTER_OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    private void playOpenCloseShutterSound(@Nullable Entity entity, World world, BlockPos pos, boolean open) {
        world.playSound(entity, pos, open ? VASoundEvents.BLOCK_STEEL_DOOR_SHUTTER_OPEN : VASoundEvents.BLOCK_STEEL_DOOR_SHUTTER_CLOSE, SoundCategory.BLOCKS, 0.4F, world.getRandom().nextFloat() * 0.1F + 1.2F);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SHUTTER_OPEN);
    }
}
