package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.component.GildTypeComponent;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract <T extends TooltipAppender> void appendComponentTooltip(ComponentType<T> componentType, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type);

    @Inject(
            method = "appendTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;appendComponentTooltip(Lnet/minecraft/component/ComponentType;Lnet/minecraft/item/Item$TooltipContext;Lnet/minecraft/component/type/TooltipDisplayComponent;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V",
                    ordinal = 19,
                    shift = At.Shift.AFTER))
    void virtualAdditions$appendTooltip(Item.TooltipContext context, TooltipDisplayComponent displayComponent, PlayerEntity player, TooltipType type, Consumer<Text> textConsumer, CallbackInfo ci) {
        this.appendComponentTooltip(VADataComponentTypes.EFFECTS_ON_HIT, context, displayComponent, textConsumer, type);
        this.appendComponentTooltip(VADataComponentTypes.GILD_TYPE_COMPONENT, context, displayComponent, textConsumer, type);
    }

    @Inject(method = "postDamageEntity", at = @At("TAIL"))
    void virtualAdditions$postDamageEntity(LivingEntity target, LivingEntity user, CallbackInfo ci) {
        GildTypeComponent gildType = ((ItemStack)(Object)(this)).get(VADataComponentTypes.GILD_TYPE_COMPONENT);
        if (gildType != null) {
            gildType.type().value().applyEffectsOnHit(user.getEntityWorld(), target, user);
        }
        EffectsOnHitComponent effectsOnHit = ((ItemStack)(Object)(this)).get(VADataComponentTypes.EFFECTS_ON_HIT);
        if (effectsOnHit != null && effectsOnHit.getRemainingUses() > 0) {
            effectsOnHit.forEachEffect(target::addStatusEffect);
            ((ItemStack)(Object)(this)).set(VADataComponentTypes.EFFECTS_ON_HIT, effectsOnHit.decrementRemainingUses());
        }

    }

    //@Shadow protected abstract <T extends TooltipAppender> void appendTooltip(ComponentType<T> componentType, Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type);
//
    //@Inject(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendTooltip(Lnet/minecraft/component/ComponentType;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V", ordinal = 5, shift = At.Shift.AFTER))
    //void virtualAdditions$getTooltip(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir, @Local LocalRef<List<Text>> list) {
    //    List<Text> texts = list.get();
    //    this.appendTooltip(VADataComponentTypes.EFFECTS_ON_HIT, context, texts::add, type);
    //    list.set(texts);
    //}
}
