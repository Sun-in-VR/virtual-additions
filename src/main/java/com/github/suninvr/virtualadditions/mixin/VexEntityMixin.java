package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VexEntity.class)
public class VexEntityMixin extends HostileEntity {
    protected VexEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.getStackInHand(hand).isOf(VAItems.BOTTLED_SOULS)) {
            if (!world.isClient()) {
                AllayEntity allay = EntityType.ALLAY.create(world);
                boolean bl = world.isSpaceEmpty(allay.getType().createSimpleBoundingBox(this.getX(), this.getY(), this.getZ()));
                if (bl) {
                    allay.setPosition(this.getPos());
                    world.spawnEntity(allay);
                    player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, Items.GLASS_BOTTLE.getDefaultStack()));
                    world.playSound(null, this.getBlockPos(), SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    world.playSound(null, this.getBlockPos(), SoundEvents.ENTITY_ALLAY_ITEM_GIVEN, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    world.emitGameEvent(player, GameEvent.ITEM_INTERACT_START, this.getBlockPos());
                    this.discard();
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.interactMob(player, hand);
    }
}
