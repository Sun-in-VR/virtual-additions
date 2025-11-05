package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.client.VARenderers;
import com.github.suninvr.virtualadditions.interfaces.PlayerEntityInterface;
import com.github.suninvr.virtualadditions.item.HalberdItem;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class PlayerEntityRendererMixin{

    @ModifyExpressionValue(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    boolean virtualAdditions$isOfAnySpyglass(boolean original, @Local ItemStack stack) {
        return original || stack.is(VAItems.SPECTRAL_SPYGLASS);
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    void virtualAdditions$updateHalberdRenderState(Avatar playerLikeEntity, AvatarRenderState playerEntityRenderState, float f, CallbackInfo ci) {
        ItemStack stack = playerLikeEntity.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack stack2 = playerLikeEntity.getItemInHand(InteractionHand.OFF_HAND);
        if (stack.getItem() instanceof HalberdItem) {
            playerEntityRenderState.setData(VARenderers.HOLDING_HALBERD_IN, playerLikeEntity.getMainArm());
            playerEntityRenderState.setData(VARenderers.IS_HOLDING_HALBERD, true);
        } else if (stack2.getItem() instanceof HalberdItem) {
            playerEntityRenderState.setData(VARenderers.HOLDING_HALBERD_IN, playerLikeEntity.getMainArm().getOpposite());
            playerEntityRenderState.setData(VARenderers.IS_HOLDING_HALBERD, true);
        } else {
            playerEntityRenderState.setData(VARenderers.HOLDING_HALBERD_IN, null);
            playerEntityRenderState.setData(VARenderers.IS_HOLDING_HALBERD, false);
        }

        playerEntityRenderState.setData(VARenderers.HALBERD_READINESS, HalberdItem.getSwingReadiness(stack, playerLikeEntity, playerLikeEntity.getUseItemRemainingTicks(), f));
        if (playerLikeEntity instanceof PlayerEntityInterface playerEntityInterface) playerEntityRenderState.setData(VARenderers.TICKS_SINCE_HALBERD_USED, (playerLikeEntity.level().getGameTime() - playerEntityInterface.virtualAdditions$lastSwungHalberd()) + f);
    }
}
