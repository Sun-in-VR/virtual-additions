package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.entity.LightningBottleEntity;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class LightningBottleItem extends Item implements ProjectileItem {

    public LightningBottleItem(Item.Properties settings) {
        super(settings);
    }

    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), VASoundEvents.ENTITY_STEEL_BOMB_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getCooldowns().addCooldown(itemStack, 10);
        if (world instanceof ServerLevel serverWorld) {
            Projectile.spawnProjectileFromRotation(LightningBottleEntity::new, serverWorld, itemStack, user, -20.0F, 0.7F, 1.0F);
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, user);
        return InteractionResult.SUCCESS;
    }

    @Override
    public Projectile asProjectile(Level world, Position position, ItemStack itemStack, Direction direction) {
        return new LightningBottleEntity(world, position.x(), position.y(), position.z(), itemStack);
    }
}
