package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.component.PortalCoreLocationComponent;
import com.github.suninvr.virtualadditions.entity.SlungItemProjectile;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.mojang.datafixers.util.Function6;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class SlingshotBehaviors {
    private static final Map<ItemLike, Function6<Level, LivingEntity, ItemStack, Vec3, Vec3, Double, Boolean>> PROJECTILE_BEHAVIORS = new HashMap<>();
    private static final Function6<Level, LivingEntity, ItemStack, Vec3, Vec3, Double, Boolean> THROW_ITEM;
    private static final Function6<Level, LivingEntity, ItemStack, Vec3, Vec3, Double, Boolean> THROW_FIRE_CHARGE;
    private static final Function6<Level, LivingEntity, ItemStack, Vec3, Vec3, Double, Boolean> PORTAL_CORE;

    public static Function6<Level, LivingEntity, ItemStack, Vec3, Vec3, Double, Boolean> getBehavior(ItemLike item) {
        return PROJECTILE_BEHAVIORS.getOrDefault(item.asItem(), THROW_ITEM);
    }

    public static boolean runOnItem(ItemStack stack, Level level, LivingEntity entity, Vec3 pos, Vec3 initVel, double power) {
        return getBehavior(stack.getItem()).apply(level, entity, stack, pos, initVel, power);
    }

    public static void addBehavior(ItemLike item, Function6<Level, LivingEntity, ItemStack, Vec3, Vec3, Double, Boolean> behavior) {
        PROJECTILE_BEHAVIORS.put(item, behavior);
    }

    public static void initDefaultBehaviors() {
        addBehavior(Items.FIRE_CHARGE, THROW_FIRE_CHARGE);
        addBehavior(Items.FLINT, throwDamaging(5.0F, new Vec3(4.0, 4.0, 4.0)));
        addBehavior(Items.COPPER_NUGGET, throwDamaging(2.0F, new Vec3(20.0, 4.0, 20.0), 8));
        addBehavior(Items.GOLD_NUGGET, throwDamaging(3.0F, new Vec3(20.0, 4.0, 20.0), 8));
        addBehavior(Items.IRON_NUGGET, throwDamaging(4.0F, new Vec3(20.0, 4.0, 20.0), 8));
        addBehavior(VAItems.STEEL_NUGGET, throwDamaging(5.0F, new Vec3(20.0, 4.0, 20.0), 8));
        addBehavior(VAItems.PORTAL_CORE, PORTAL_CORE);
    }

    public static Function6<Level, LivingEntity, ItemStack, Vec3, Vec3, Double, Boolean> throwDamaging(float damage, Vec3 spread) {
        return (level, livingEntity, stack, firePos, initVel, power) -> {
            Vec3 vel = initVel.add(level.random.triangle(0.0, 0.0172275 * spread.x), level.random.triangle(0.0, 0.0172275 * spread.y), level.random.triangle(0.0, 0.0172275 * spread.z)).scale(1.25 * power + 0.25);
            SlungItemProjectile itemEntity = new SlungItemProjectile(firePos.x, firePos.y, firePos.z, level, stack.copyWithCount(1));
            itemEntity.setOwner(livingEntity);
            itemEntity.setDamage(damage);
            itemEntity.setDeltaMovement(vel);
            level.addFreshEntity(itemEntity);
            stack.consume(1, livingEntity);
            return true;
        };
    }

    public static Function6<Level, LivingEntity, ItemStack, Vec3, Vec3, Double, Boolean> throwDamaging(float damage, Vec3 spread, int count) {
        return (level, livingEntity, stack, firePos, initVel, power) -> {
            int i;
            for (i = 0; i < count; i++) {
                Vec3 vel = initVel.add(level.random.triangle(0.0, 0.0172275 * spread.x), level.random.triangle(0.0, 0.0172275 * spread.y), level.random.triangle(0.0, 0.0172275 * spread.z)).scale(1.25 * power + 0.25);
                SlungItemProjectile itemEntity = new SlungItemProjectile(firePos.x, firePos.y, firePos.z, level, stack.copyWithCount(1));
                itemEntity.setOwner(livingEntity);
                itemEntity.setDamage(damage);
                itemEntity.setDeltaMovement(vel);
                level.addFreshEntity(itemEntity);
                stack.consume(1, livingEntity);
                if (stack.isEmpty()) break;
            }
            return true;
        };
    }

    static {

        THROW_ITEM = (level, livingEntity, stack, firePos, initVel, power) -> {
            Vec3 vel = initVel.scale(1.25 * power + 0.25);
            ItemEntity itemEntity = new ItemEntity(level, firePos.x, firePos.y, firePos.z, stack.copyWithCount(1));
            itemEntity.setPickUpDelay(5);
            itemEntity.setDeltaMovement(vel);
            level.addFreshEntity(itemEntity);
            stack.consume(1, livingEntity);
            return true;
        };

        THROW_FIRE_CHARGE = (level, livingEntity, stack, firePos, initVel, power) -> {
            Vec3 vel = initVel.scale(2.0 * power + 0.5);
            SmallFireball itemEntity = new SmallFireball(level, firePos.x, firePos.y, firePos.z, vel);
            itemEntity.setOwner(livingEntity);
            itemEntity.setDeltaMovement(vel);
            level.addFreshEntity(itemEntity);
            firePos = firePos.add(livingEntity.getLookAngle().normalize().scale(3.0));
            level.playSound(null, firePos.x, firePos.y, firePos.z, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8F, level.random.nextIntBetweenInclusive(0, 2) * 0.1F + 0.9F);
            stack.consume(1, livingEntity);
            return true;
        };

        PORTAL_CORE = (level, entity, stack, vec3, vec4, aDouble) -> {
            if (stack.getItem() instanceof PortalCoreItem portalCore && entity instanceof Player player) {
                HitResult result = entity.pick(8.0 + 24.0 * aDouble, 0, false);
                if (result instanceof BlockHitResult blockHitResult) {
                    if (level instanceof ServerLevel serverLevel) {
                        Vec3 relative = vec3.subtract(blockHitResult.getBlockPos().relative(blockHitResult.getDirection()).getCenter());
                        for (int i = 1; i < relative.length(); i++) {
                            Vec3 particlePos = vec3.subtract(relative.normalize().scale(i));
                            serverLevel.sendParticles(VAParticleTypes.INTERFERENCE, particlePos.x(), particlePos.y, particlePos.z, 1, 0, 0, 0, 0);
                        }
                    }
                    InteractionHand oppositeHand = player.getUsedItemHand().equals(InteractionHand.MAIN_HAND) ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
                    portalCore.useOn(new UseOnContext(player, oppositeHand, blockHitResult));
                    level.playSound(null, vec3.x, vec3.y, vec3.z, SoundEvents.ENDER_EYE_LAUNCH, SoundSource.PLAYERS, 1.0F, 1.6F);
                }
            }
            return false;
        };


    }
}
