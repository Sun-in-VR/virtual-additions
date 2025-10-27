package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.item.gild.GildType;
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
        this.appendComponentTooltip(VADataComponentTypes.GILD_TYPE, context, displayComponent, textConsumer, type);
    }

    @Inject(method = "postDamageEntity", at = @At("TAIL"))
    void virtualAdditions$postDamageEntity(LivingEntity target, LivingEntity user, CallbackInfo ci) {
        GildType gildType = ((ItemStack)(Object)(this)).get(VADataComponentTypes.GILD_TYPE);
        if (gildType != null) {
            gildType.applyEffectsOnHit(user.getEntityWorld(), target, user);
        }
        EffectsOnHitComponent effectsOnHit = ((ItemStack)(Object)(this)).get(VADataComponentTypes.EFFECTS_ON_HIT);
        if (effectsOnHit != null && effectsOnHit.getRemainingUses() > 0) {
            effectsOnHit.forEachEffect(target::addStatusEffect);
            ((ItemStack)(Object)(this)).set(VADataComponentTypes.EFFECTS_ON_HIT, effectsOnHit.decrementRemainingUses());
        }

    }
}
