package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.entity.TomatoEntity;
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

public class TomatoItem extends Item implements ProjectileItem {
    public TomatoItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if (!user.isShiftKeyDown()) {
            return super.use(world, user, hand);
        }

        ItemStack itemStack = user.getItemInHand(hand);

        world.playSound(null, user.getX(), user.getY(), user.getZ(), VASoundEvents.ENTITY_TOMATO_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide()) {
            Projectile.spawnProjectileFromRotation(TomatoEntity::new, (ServerLevel) world, itemStack, user, 0.0F, 1.5F, 1.0F);
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        if (!user.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public Projectile asProjectile(Level world, Position pos, ItemStack stack, Direction direction) {
        return new TomatoEntity(world, pos.x(), pos.y(), pos.z(), stack);
    }
}
