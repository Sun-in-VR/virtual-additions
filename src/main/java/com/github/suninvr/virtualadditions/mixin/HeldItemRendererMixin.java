package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.PlayerEntityInterface;
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
            boolean bl = arm == Arm.RIGHT;
            int armMul = bl ? 1 : -1;
            matrices.translate(0, 0.2F, 0);

            float fx = HalberdItem.getSwingReadiness(item, player, item.getMaxUseTime(player) - player.getItemUseTime(), tickProgress);
            if (fx > 1.0F) {
                fx = 1.0F;
            }
            float efx = (float) Math.sin(Math.PI * (fx / 2))/2 + 0.5F;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(armMul * (60 + -40 * efx)));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(armMul * (-5 * efx)));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(armMul * (-18.05F + -60*efx)));

            float m = (item.getMaxUseTime(player) - (player.getItemUseTimeLeft() - tickProgress + 1.0F)) * fx;

            if (fx > 0.1F) {
                float gx = MathHelper.sin((m - 0.1F) * 1.3F);
                float h = fx - 0.1F;
                float j = gx * h;
                matrices.translate(j * 0.0F, j * 0.004F, j * 0.0F);
            }

            matrices.translate(-0.3F * efx + 0.2, efx * 2.0F - 1.5F, 0.8F * efx - 0.7F);
        }
    }

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V", ordinal = 4))
    void virtualAdditions$swingHalberd(AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, CallbackInfo ci, @Local Arm arm) {
        long l;
        if (player.getActiveHand() == hand && (l = (player.getEntityWorld().getTime() - ((PlayerEntityInterface)(player)).virtualAdditions$lastSwungHalberd())) < 30) {
            boolean bl = arm == Arm.RIGHT;
            int armMul = bl ? 1 : -1;

            float f = Math.min((l + tickProgress) / 6.0F, 1.0F);
            float g = Math.min((l + tickProgress) / 25.0F, 1.0F);
            g = (float) Math.cos(Math.PI * g * g) / 2 + 0.5F;
            float efx = (float) Math.sin(Math.PI * (f / 2));

            matrices.translate(0, 0.2F*g, 0);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(g * armMul * (20 + 130 * efx)));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * armMul * (-5 + -10 * efx)));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(g * armMul * (-78.05F + -5 * efx)));
            matrices.translate((-0.1F + 0.2*efx)*g, (0.5F + 0.3*efx)*g, (0.1F + 0.5F*efx)*g);

        }
    }
}
