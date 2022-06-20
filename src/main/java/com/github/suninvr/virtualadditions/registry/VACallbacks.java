package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.ActionResult;

import java.util.List;

import static com.github.suninvr.virtualadditions.util.AppliedPotionHelper.*;

public class VACallbacks{
    public static void init() {

        //Applied Potion hit effects callback
        AttackEntityCallback.EVENT.register( ((player, world, hand, entity, hitResult) -> {
            if (player.isSpectator()) return ActionResult.PASS;
            if(!entity.isAlive()) return ActionResult.PASS;
            ItemStack stack = player.getStackInHand(hand);
            if (stack.isEmpty()) return ActionResult.PASS;
            if (!stack.hasNbt()) return ActionResult.PASS;
            NbtCompound appliedPotionData = stack.getNbt().getCompound("AppliedPotion");

            if (getAppliedPotion(stack) != Potions.EMPTY || appliedPotionData.contains("CustomPotionEffects")) {
                int remainingUses = getAppliedPotionUses(stack);

                List<StatusEffectInstance> potionEffects = getAppliedPotion(stack).getEffects();
                for (StatusEffectInstance effect: potionEffects) {
                    ((LivingEntity)entity).addStatusEffect(new StatusEffectInstance(effect.getEffectType(), Math.max(effect.getDuration() / 8, 1), effect.getAmplifier(), effect.isAmbient(), effect.shouldShowParticles(), effect.shouldShowIcon()), player);
                }
                List<StatusEffectInstance> customEffects = PotionUtil.getCustomPotionEffects(appliedPotionData);
                for (StatusEffectInstance effect : customEffects) {
                    ((LivingEntity)entity).addStatusEffect(effect, player);
                }

                remainingUses -= 1;
                if (remainingUses == 0) {
                    stack.removeSubNbt("AppliedPotion");
                } else if (remainingUses > 0) {
                    decrementAppliedPotionUses(stack);
                }
            }
            return ActionResult.PASS;
        } ) );

    }
}
