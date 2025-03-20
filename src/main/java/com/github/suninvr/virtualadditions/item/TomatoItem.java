package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.entity.SteelBombEntity;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class TomatoItem extends Item implements ProjectileItem {
    public TomatoItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!user.isSneaking()) {
            ActionResult useResult = super.use(world, user, hand);
            if (useResult == ActionResult.CONSUME) return useResult;
        }

        ItemStack itemStack = user.getStackInHand(hand);

        world.playSound(null, user.getX(), user.getY(), user.getZ(), VASoundEvents.ENTITY_STEEL_BOMB_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            ProjectileEntity.spawnWithVelocity(SnowballEntity::new, (ServerWorld) world, itemStack, user, 0.0F, 1.5F, 1.0F);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        return new SnowballEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
    }
}
