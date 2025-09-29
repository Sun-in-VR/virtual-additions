package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.client.VARenderers;
import com.github.suninvr.virtualadditions.item.HalberdItem;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin{

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    boolean virtualAdditions$isOfAnySpyglass(boolean original, @Local ItemStack stack) {
        return original || stack.isOf(VAItems.SPECTRAL_SPYGLASS);
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("TAIL"))
    void virtualAdditions$updateHalberdRenderState(PlayerLikeEntity playerLikeEntity, PlayerEntityRenderState playerEntityRenderState, float f, CallbackInfo ci) {
        ItemStack stack = playerLikeEntity.getStackInHand(playerLikeEntity.getActiveHand());
        playerEntityRenderState.setData(VARenderers.IS_HOLDING_HALBERD, stack.getItem() instanceof HalberdItem);
        playerEntityRenderState.setData(VARenderers.HALBERD_READINESS, HalberdItem.getSwingReadiness(stack, playerLikeEntity, playerLikeEntity.getItemUseTimeLeft(), f));
    }
}
