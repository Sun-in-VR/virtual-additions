package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.item.GildType;
import com.github.suninvr.virtualadditions.item.GildTypes;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class VACallbacks{

    public static void init() {
        //Applied Potion hit effects callback
        AttackEntityCallback.EVENT.register( ((player, world, hand, entity, hitResult) -> {
            if (player.isSpectator()) return ActionResult.PASS;
            if (!entity.isAlive()) return ActionResult.PASS;
            ItemStack stack = player.getStackInHand(hand);
            if (stack.isEmpty()) return ActionResult.PASS;
            ComponentMap components = stack.getComponents();
            if (!components.contains(VADataComponentTypes.EFFECTS_ON_HIT)) return ActionResult.PASS;
            EffectsOnHitComponent component = components.get(VADataComponentTypes.EFFECTS_ON_HIT);
            if (entity instanceof LivingEntity livingEntity && component.getRemainingUses() > 0) {
                component.forEachEffect(livingEntity::addStatusEffect);
                stack.set(VADataComponentTypes.EFFECTS_ON_HIT, component.decrementRemainingUses());
            }

            return ActionResult.PASS;
        } ) );

        PlayerBlockBreakEvents.BEFORE.register( (world, player, pos, state, blockEntity) -> {
            if (player.isSpectator()) return true;
            if (player.isCreative()) return true;
            ItemStack tool = player.getStackInHand(Hand.MAIN_HAND);
            GildType gild = GildedToolItem.getGildType(tool);
            if (gild.equals(GildTypes.NONE)) return true;
            if (gild.isGildEffective(world, player, pos, state, tool)) {
                gild.emitBlockBreakingEffects(world, player, pos, tool);
                return gild.onBlockBroken(world, player, pos, state, tool);
            }
            return true;
        } );

    }
}
