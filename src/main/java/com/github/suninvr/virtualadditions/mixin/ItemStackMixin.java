package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.component.DataComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TooltipAppender;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow protected abstract <T extends TooltipAppender> void appendTooltip(DataComponentType<EffectsOnHitComponent> componentType, Consumer<Text> textConsumer, TooltipContext context);

    @Inject(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendTooltip(Lnet/minecraft/component/DataComponentType;Ljava/util/function/Consumer;Lnet/minecraft/client/item/TooltipContext;)V", ordinal = 5, shift = At.Shift.AFTER))
    void virtualAdditions$getTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir, @Local LocalRef<List<Text>> list) {
        List<Text> texts = list.get();
        this.appendTooltip(VADataComponentTypes.EFFECTS_ON_HIT, texts::add, context);
        list.set(texts);
    }
}
