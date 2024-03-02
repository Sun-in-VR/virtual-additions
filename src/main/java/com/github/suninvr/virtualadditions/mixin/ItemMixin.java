package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@SuppressWarnings("DataFlowIssue")
@Mixin(Item.class)
public class ItemMixin {

    @Shadow @Final private ComponentMap components;

    @Inject(at = @At("TAIL"), method = "appendTooltip")
    private void virtualAdditions$addAppliedPotionTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        EffectsOnHitComponent component;
        if ((component = stack.get(VADataComponentTypes.EFFECTS_ON_HIT)) != null) component.buildTooltip(tooltip::add, world == null ? 20.0F : world.getTickManager().getTickRate(), context.isAdvanced());
    }

    @Inject(at = @At("HEAD"), method = "getItemBarColor", cancellable = true)
    private void virtualAdditions$getItemBarColorForAppliedEffect(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        EffectsOnHitComponent component;
        if ((component = stack.get(VADataComponentTypes.EFFECTS_ON_HIT)) != null && component.hasEffectsComponent() && component.getRemainingUses() > 0) cir.setReturnValue(component.getColor());
    }

    @Inject(at = @At("HEAD"), method = "getItemBarStep", cancellable = true)
    void virtualAdditions$getItemBarStepForAppliedPotion(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        EffectsOnHitComponent component;
        if ((component = stack.get(VADataComponentTypes.EFFECTS_ON_HIT)) != null) {
            cir.setReturnValue(component.getItemBarAmount());
            //cir.setReturnValue(Math.round((float)getAppliedPotionUses(stack) * 13.0F / (float)getMaxAppliedPotionUses(stack)));
        }
    }

    @Inject(at = @At("HEAD"), method = "isItemBarVisible", cancellable = true)
    void virtualAdditions$isAppliedPotionBarVisible(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        EffectsOnHitComponent component = stack.get(VADataComponentTypes.EFFECTS_ON_HIT);
        if (component != null && component.getRemainingUses() > 0) cir.setReturnValue(true);
    }

}
