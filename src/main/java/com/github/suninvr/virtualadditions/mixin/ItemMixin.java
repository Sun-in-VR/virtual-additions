package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("DataFlowIssue")
@Mixin(Item.class)
public class ItemMixin {

    @Inject(at = @At("HEAD"), method = "getItemBarColor", cancellable = true)
    private void virtualAdditions$getItemBarColorForAppliedEffect(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        EffectsOnHitComponent component;
        if ((component = stack.get(VADataComponentTypes.EFFECTS_ON_HIT)) != null && component.hasEffectsComponent() && component.getRemainingUses() > 0) cir.setReturnValue(component.getColor());
    }

    @Inject(at = @At("HEAD"), method = "getItemBarStep", cancellable = true)
    void virtualAdditions$getItemBarStepForAppliedPotion(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        EffectsOnHitComponent component;
        if ((component = stack.get(VADataComponentTypes.EFFECTS_ON_HIT)) != null && component.getRemainingUses() > 0) {
            cir.setReturnValue(component.getItemBarAmount());
        }
    }

    @Inject(at = @At("HEAD"), method = "isItemBarVisible", cancellable = true)
    void virtualAdditions$isAppliedPotionBarVisible(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        EffectsOnHitComponent component = stack.get(VADataComponentTypes.EFFECTS_ON_HIT);
        if (component != null && component.getRemainingUses() > 0) cir.setReturnValue(true);
    }

}
