package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.PlayerEntityInterface;
import com.github.suninvr.virtualadditions.item.HalberdItem;
import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class HeldItemRendererMixin {
    @WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isScoping()Z"))
    boolean virtualAdditions$isUsingSpectralSpyglass(AbstractClientPlayer instance, Operation<Boolean> original) {
        return original.call(instance) || ProjectionSpyglassItem.isInUseBy(instance);
    }

    @WrapOperation(method = "evaluateWhichHandsToRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 0))
    private static boolean virtualAdditions$setHalberdRenderType(ItemStack instance, Item item, Operation<Boolean> original) {
        return instance.getItem() instanceof HalberdItem || original.call(instance, item);
    }

    @WrapOperation(method = "selectionUsingItemWhileHoldingBowLike", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 0))
    private static boolean virtualAdditions$setUsingHalberdRenderType(ItemStack instance, Item item, Operation<Boolean> original) {
        return instance.getItem() instanceof HalberdItem || original.call(instance, item);
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 1))
    void virtualAdditions$applyHalberdOffset(AbstractClientPlayer player, float tickProgress, float pitch, InteractionHand hand, float swingProgress, ItemStack item, float equipProgress, PoseStack matrices, SubmitNodeCollector orderedRenderCommandQueue, int light, CallbackInfo ci, @Local HumanoidArm arm) {
        if (player.getUseItem().getItem() instanceof HalberdItem) {
            boolean bl = arm == HumanoidArm.RIGHT;
            int armMul = bl ? 1 : -1;
            matrices.translate(0, 0.2F, 0);

            float fx = HalberdItem.getSwingReadiness(item, player, item.getUseDuration(player) - player.getTicksUsingItem(), tickProgress);
            if (fx > 1.0F) {
                fx = 1.0F;
            }
            float efx = (float) Math.sin(Math.PI * (fx / 2))/2 + 0.5F;
            matrices.mulPose(Axis.YP.rotationDegrees(armMul * (60 + -40 * efx)));
            matrices.mulPose(Axis.XP.rotationDegrees(armMul * (-5 * efx)));
            matrices.mulPose(Axis.ZP.rotationDegrees(armMul * (-18.05F + -60*efx)));

            float m = (item.getUseDuration(player) - (player.getUseItemRemainingTicks() - tickProgress + 1.0F)) * fx;

            if (fx > 0.1F) {
                float gx = Mth.sin((m - 0.1F) * 1.3F);
                float h = fx - 0.1F;
                float j = gx * h;
                matrices.translate(j * 0.0F, j * 0.004F, j * 0.0F);
            }

            matrices.translate(-0.3F * efx + 0.2, efx * 2.0F - 1.5F, 0.8F * efx - 0.7F);
        }
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V"))
    void virtualAdditions$swingHalberd(AbstractClientPlayer player, float tickProgress, float pitch, InteractionHand hand, float swingProgress, ItemStack item, float equipProgress, PoseStack matrices, SubmitNodeCollector orderedRenderCommandQueue, int light, CallbackInfo ci, @Local HumanoidArm arm) {
        long l;
        if (player.getUsedItemHand() == hand && (l = (player.level().getGameTime() - ((PlayerEntityInterface)(player)).virtualAdditions$lastSwungHalberd())) < 30) {
            boolean bl = arm == HumanoidArm.RIGHT;
            int armMul = bl ? 1 : -1;

            float f = Math.min((l + tickProgress) / 6.0F, 1.0F);
            float g = Math.min((l + tickProgress) / 25.0F, 1.0F);
            g = (float) Math.cos(Math.PI * g * g) / 2 + 0.5F;
            float efx = (float) Math.sin(Math.PI * (f / 2));

            matrices.translate(0, 0.2F*g, 0);
            matrices.mulPose(Axis.YP.rotationDegrees(g * armMul * (20 + 130 * efx)));
            matrices.mulPose(Axis.XP.rotationDegrees(g * armMul * (-5 + -10 * efx)));
            matrices.mulPose(Axis.ZP.rotationDegrees(g * armMul * (-78.05F + -5 * efx)));
            matrices.translate((-0.1F + 0.2*efx)*g, (0.5F + 0.3*efx)*g, (0.1F + 0.5F*efx)*g);

        }
    }
}
