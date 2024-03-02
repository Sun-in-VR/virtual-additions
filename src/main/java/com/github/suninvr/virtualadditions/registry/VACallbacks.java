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

            //if (!world.isClient() && stack.getItem() instanceof SwordItem && GildedToolUtil.getGildType(stack).equals(GildTypes.SCULK) && player.getAttackCooldownProgress(0.0F) >= 1.0F && entity instanceof LivingEntity livingEntity && !hitEntities.contains(entity)) {
            //    TODO: Better Sculk Gilded Sword funcitonality
            //
            //    hitEntities.add(livingEntity);
            //    Box box = Box.of(entity.getPos(), 8, 8, 8);
            //    List<LivingEntity> entities = Lists.newArrayList();
            //    world.collectEntitiesByType(TypeFilter.instanceOf(livingEntity.getClass()), box,  entity1 -> !entity1.equals(entity), entities, 9);
            //    int lastAttackedTicks = ((PlayerEntityInterface)player).virtualAdditions$getLastAttackTicks();
            //    for (Object obj : entities) {
            //        if (obj instanceof LivingEntity nearbyEntity) {
            //            int remaining = entities.size() - entities.indexOf(nearbyEntity);
            //            int mul = entities.size() - remaining + 1;
            //            EntityAttributeInstance attributes = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            //            if (attributes != null) attributes.addTemporaryModifier(new EntityAttributeModifier(UUID.fromString("1630266f-00db-4198-bd70-840420f673a2"), "sculkgild", -2.0F * mul, EntityAttributeModifier.Operation.ADDITION));
            //            hitEntities.add(nearbyEntity);
            //            player.attack(nearbyEntity);
            //            ((PlayerEntityInterface)player).virtualAdditions$setLastAttackTicks(lastAttackedTicks);
            //            nearbyEntity.damage(player.getDamageSources().playerAttack(player), 5.0F);
            //            if (world instanceof ServerWorld serverWorld) {
            //                Vec3d pos = nearbyEntity.getBoundingBox().getCenter();
            //                float mul1 = nearbyEntity.getWidth();
            //                serverWorld.spawnParticles(ParticleTypes.SCULK_CHARGE_POP, pos.getX(), pos.getY(), pos.getZ(), 30, 0.33 * mul1, 0.33 * mul1, 0.33 * mul1, 0.02);
            //            }
            //            if (attributes != null) attributes.removeModifier(UUID.fromString("1630266f-00db-4198-bd70-840420f673a2"));
            //        }
            //    }
            //    hitEntities.clear();
            //}
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
