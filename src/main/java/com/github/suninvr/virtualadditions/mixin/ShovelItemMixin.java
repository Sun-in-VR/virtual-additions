package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$undoPathBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = context.getPlayer();
        if (!(context.getClickedFace() == Direction.DOWN) && player != null && player.isShiftKeyDown()) {
            Level world = context.getLevel();
            BlockPos blockPos = context.getClickedPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.is(Blocks.DIRT_PATH)) {
                world.setBlockAndUpdate(blockPos, Blocks.DIRT.defaultBlockState());
                world.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, Blocks.DIRT.defaultBlockState()));
                context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
                world.playSound(player, blockPos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}
