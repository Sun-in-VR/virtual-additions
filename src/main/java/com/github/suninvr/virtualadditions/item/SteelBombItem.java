package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.entity.SteelBombEntity;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SteelBombItem extends Item {

    public SteelBombItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(user.getX(), user.getY(), user.getZ(), VASoundEvents.ENTITY_STEEL_BOMB_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F), true);
        user.getItemCooldownManager().set(this, 10);
        if (!world.isClient) {
            SteelBombEntity steelBombEntity = new SteelBombEntity(world, user);
            steelBombEntity.setItem(itemStack);
            steelBombEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(steelBombEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
