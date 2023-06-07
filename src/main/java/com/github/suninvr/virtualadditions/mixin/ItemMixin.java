package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static com.github.suninvr.virtualadditions.util.AppliedPotionHelper.*;

@SuppressWarnings("DataFlowIssue")
@Mixin(Item.class)
public class ItemMixin {

    @Inject(at = @At("TAIL"), method = "appendTooltip")
    private void virtualAdditions$addAppliedPotionTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (!stack.hasNbt()) return;
        NbtCompound appliedPotionData = stack.getNbt().getCompound("AppliedPotion");

        if (hasAppliedPotionEffects(stack)) {
            tooltip.add(Text.translatable("item.virtual_additions.applied_effect_tooltip", Text.of(((context.isAdvanced() && getMaxAppliedPotionUses(stack) > 0) ? (" (" + getAppliedPotionUses(stack) + "/" + getMaxAppliedPotionUses(stack) + ")") : ""))).formatted(Formatting.DARK_PURPLE));

            List<StatusEffectInstance> potionEffects = getAppliedPotion(stack).getEffects();
            List<StatusEffectInstance> customPotionEffects = PotionUtil.getCustomPotionEffects(appliedPotionData);
            List<StatusEffectInstance> allEffects = new java.util.ArrayList<>(List.of());
            allEffects.addAll(potionEffects);
            allEffects.addAll(customPotionEffects);

            PotionUtil.buildTooltip(allEffects, tooltip, 0.125F);
        }
    }

    @Inject(at = @At("HEAD"), method = "getItemBarColor", cancellable = true)
    private void virtualAdditions$getItemBarColorForAppliedEffect(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (hasAppliedPotionEffects(stack) && getMaxAppliedPotionUses(stack) > 0) {

            NbtCompound appliedPotionData = stack.getNbt().getCompound("AppliedPotion");
            if (appliedPotionData.contains("CustomPotionColor", 99)) {
                cir.setReturnValue( appliedPotionData.getInt("CustomPotionColor") );
            }

            Potion potion = PotionUtil.getPotion(appliedPotionData);
            if (!potion.getEffects().isEmpty()) {
                cir.setReturnValue( PotionUtil.getColor(potion) );
            }

        }
    }

    @Inject(at = @At("HEAD"), method = "getItemBarStep", cancellable = true)
    void virtualAdditions$getItemBarStepForAppliedPotion(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (hasAppliedPotionEffects(stack) && getMaxAppliedPotionUses(stack) > 0) {
            cir.setReturnValue(Math.round((float)getAppliedPotionUses(stack) * 13.0F / (float)getMaxAppliedPotionUses(stack)));
        }
    }

    @Inject(at = @At("HEAD"), method = "isItemBarVisible", cancellable = true)
    void virtualAdditions$isAppliedPotionBarVisible(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (hasAppliedPotionEffects(stack) && getMaxAppliedPotionUses(stack) > 0) cir.setReturnValue(true);
    }

}
