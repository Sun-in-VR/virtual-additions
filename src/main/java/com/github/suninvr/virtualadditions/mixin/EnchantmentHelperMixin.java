package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAGildTypes;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "processBlockExperience", at = @At(value = "RETURN"), cancellable = true)
    private static void virtualAdditions$getBlockExperienceForGildedTool(ServerLevel world, ItemStack stack, int baseBlockExperience, CallbackInfoReturnable<Integer> cir, @Local MutableFloat mutableFloat) {
        GildType type = stack.get(VADataComponentTypes.GILD_TYPE);
        if (!VAGildTypes.EMERALD.equals(type)) return;
        float f = mutableFloat.floatValue();
        cir.setReturnValue((int) Math.ceil(new MutableFloat(f * 1.6F).floatValue()));
    }

    @Inject(method = "doPostAttackEffectsWithItemSourceOnBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;runIterationOnItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/enchantment/EnchantmentHelper$EnchantmentInSlotVisitor;)V"))
    private static void virtualAdditions$doPostAttackEffects(ServerLevel serverLevel, Entity entity, DamageSource damageSource, @Nullable ItemStack itemStack, @Nullable Consumer<Item> consumer, CallbackInfo ci, @Local LivingEntity livingEntity) {
        if (entity instanceof LivingEntity livingEntity1) {
            GildType gildType;
            if ((gildType = itemStack.get(VADataComponentTypes.GILD_TYPE)) != null && gildType.hasHitEffects()) {
                gildType.applyEffectsOnHit(serverLevel, livingEntity1, livingEntity);
            }
            if (itemStack.has(VADataComponentTypes.EFFECTS_ON_HIT)) {
             itemStack.get(VADataComponentTypes.EFFECTS_ON_HIT).applyEffectsTo(itemStack, livingEntity1, livingEntity);
            }
        }
    }
}
