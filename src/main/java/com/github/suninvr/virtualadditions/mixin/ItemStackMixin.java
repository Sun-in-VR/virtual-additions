package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.gild.GildedItemComponentHandler;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TooltipProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract <T extends TooltipProvider> void addToTooltip(DataComponentType<T> dataComponentType, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag);

    @Shadow @Final private PatchedDataComponentMap components;

    @Shadow public abstract Item getItem();

    @Inject(
            method = "addDetailsToTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V",
                    ordinal = 19,
                    shift = At.Shift.AFTER))
    void virtualAdditions$appendTooltip(Item.TooltipContext context, TooltipDisplay displayComponent, Player player, TooltipFlag type, Consumer<Component> textConsumer, CallbackInfo ci) {
        this.addToTooltip(VADataComponentTypes.INCENSE_EFFECTS, context, displayComponent, textConsumer, type);
        this.addToTooltip(VADataComponentTypes.EFFECTS_ON_HIT, context, displayComponent, textConsumer, type);
        this.addToTooltip(VADataComponentTypes.GILD_TYPE, context, displayComponent, textConsumer, type);
    }

    @Inject(method = "getPrototype", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$getGildedPrototype(CallbackInfoReturnable<DataComponentMap> cir) {
        if (this.components.has(VADataComponentTypes.GILD_TYPE)) cir.setReturnValue(GildedItemComponentHandler.getOrCompute(this.getItem(), this.components.get(VADataComponentTypes.GILD_TYPE)));
    }

    @Inject(method = "hurtEnemy", at = @At("TAIL"))
    void virtualAdditions$postDamageEntity(LivingEntity target, LivingEntity user, CallbackInfoReturnable<Boolean> cir) {
        //GildType gildType = ((ItemStack)(Object)(this)).get(VADataComponentTypes.GILD_TYPE);
        //if (gildType != null) {
        //    gildType.applyEffectsOnHit(user.level(), target, user);
        //}
        //EffectsOnHitComponent effectsOnHit = ((ItemStack)(Object)(this)).get(VADataComponentTypes.EFFECTS_ON_HIT);
        //if (effectsOnHit != null && effectsOnHit.getRemainingUses() > 0) {
        //    effectsOnHit.forEachEffect(target::addEffect);
        //    ((ItemStack)(Object)(this)).set(VADataComponentTypes.EFFECTS_ON_HIT, effectsOnHit.decrementRemainingUses());
        //}

    }
}
