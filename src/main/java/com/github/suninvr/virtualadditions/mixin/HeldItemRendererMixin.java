package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.HalberdItem;
import com.github.suninvr.virtualadditions.item.ProjectionSpyglassItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @WrapOperation(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingSpyglass()Z"))
    boolean virtualAdditions$isUsingSpectralSpyglass(AbstractClientPlayerEntity instance, Operation<Boolean> original) {
        return original.call(instance) || ProjectionSpyglassItem.isInUseBy(instance);
    }

    @WrapOperation(method = "getHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private static boolean virtualAdditions$setHalberdRenderType(ItemStack instance, Item item, Operation<Boolean> original) {
        return instance.getItem() instanceof HalberdItem || original.call(instance, item);
    }

    @WrapOperation(method = "getUsingItemHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private static boolean virtualAdditions$setUsingHalberdRenderType(ItemStack instance, Item item, Operation<Boolean> original) {
        return instance.getItem() instanceof HalberdItem || original.call(instance, item);
    }

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V", ordinal = 1))
    void virtualAdditions$applyHalberdOffset(AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, CallbackInfo ci, @Local Arm arm) {
        if (player.getActiveItem().getItem() instanceof HalberdItem) {
            int l = arm == Arm.RIGHT ? 1 : -1;
            matrices.translate(l*0.25, 0.2F, 0);
            //matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-10.25F));

            float m = item.getMaxUseTime(player) - (player.getItemUseTimeLeft() - tickProgress + 1.0F);
            float fx = m / 20.0F;
            if (fx > 1.0F) {
                fx = 1.0F;
            }
            float efx = MathHelper.easeInOutSine((fx / 2) + 0.5F);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(l * (90 + -70 * efx)));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(l * -58.05F));

            if (fx > 0.1F) {
                float gx = MathHelper.sin((m - 0.1F) * 1.3F);
                float h = fx - 0.1F;
                float j = gx * h;
                matrices.translate(j * 0.0F, j * 0.004F, j * 0.0F);
            }

            matrices.translate(0.0F, efx * 2F - 1.5F, efx * 0.2F);
            matrices.scale(1.0F, 1.0F, 1.0F + fx * 0.2F);
        }
    }
}
