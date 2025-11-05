package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.entity.SteelBombEntity;
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

public class SteelBombItem extends Item implements ProjectileItem {

    public SteelBombItem(net.minecraft.world.item.Item.Properties settings) {
        super(settings);
    }

    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), VASoundEvents.ENTITY_STEEL_BOMB_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getCooldowns().addCooldown(itemStack, 30);
        if (!world.isClientSide()) {
            Projectile.spawnProjectileFromRotation(SteelBombEntity::new, (ServerLevel) world, itemStack, user, 0.0F, 1.5F, 1.0F);
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        if (!user.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public Projectile asProjectile(Level world, Position position, ItemStack itemStack, Direction direction) {
        SteelBombEntity steelBombEntity = new SteelBombEntity(world, position.x(), position.y(), position.z());
        steelBombEntity.setItem(itemStack);
        return steelBombEntity;
    }
}
