package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$undoPathBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        PlayerEntity player = context.getPlayer();
        if (!(context.getSide() == Direction.DOWN) && player != null && player.isSneaking()) {
            World world = context.getWorld();
            BlockPos blockPos = context.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(Blocks.DIRT_PATH)) {
                world.setBlockState(blockPos, Blocks.DIRT.getDefaultState());
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(player, Blocks.DIRT.getDefaultState()));
                context.getStack().damage(1, player, context.getHand().method_73186());
                world.playSound(player, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
