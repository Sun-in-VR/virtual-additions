package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.entity.LightningBottleEntity;
import com.github.suninvr.virtualadditions.entity.SteelBombEntity;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
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

public class LightningBottleItem extends Item implements ProjectileItem {

    public LightningBottleItem(Item.Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), VASoundEvents.ENTITY_STEEL_BOMB_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getItemCooldownManager().set(itemStack, 10);
        if (world instanceof ServerWorld serverWorld) {
            ProjectileEntity.spawnWithVelocity(LightningBottleEntity::new, serverWorld, itemStack, user, -20.0F, 0.7F, 1.0F);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrementUnlessCreative(1, user);
        return ActionResult.SUCCESS;
    }

    @Override
    public ProjectileEntity createEntity(World world, Position position, ItemStack itemStack, Direction direction) {
        return new LightningBottleEntity(world, position.getX(), position.getY(), position.getZ(), itemStack);
    }
}
