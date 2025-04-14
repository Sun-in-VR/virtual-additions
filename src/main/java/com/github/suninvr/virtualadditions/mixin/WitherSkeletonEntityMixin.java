package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABiomeKeys;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentDropChances;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeletonEntity.class)
public class WitherSkeletonEntityMixin extends MobEntity {
    protected WitherSkeletonEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initEquipment", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$initWitherSkeletonArmor(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        if (this.getType().equals(EntityType.WITHER_SKELETON) && this.getWorld().getBiome(this.getBlockPos()).matchesKey(VABiomeKeys.WITHERED_WOODS)) {
            float f = random.nextFloat();
            float d = localDifficulty.getLocalDifficulty() * 0.05F;
            boolean bow = random.nextFloat() > 0.85F;
            if (f > (1.0F - d)) {
                if (!bow) this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_SWORD));
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.NETHERITE_HELMET));
                this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.NETHERITE_CHESTPLATE));
                this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.NETHERITE_LEGGINGS));
                this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.NETHERITE_BOOTS));
                if (!bow) this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0.001F);
                this.setEquipmentDropChance(EquipmentSlot.HEAD, 0.001F);
                this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.001F);
                this.setEquipmentDropChance(EquipmentSlot.LEGS, 0.001F);
                this.setEquipmentDropChance(EquipmentSlot.FEET, 0.001F);
                ci.cancel();
            }
            if (bow) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                ci.cancel();
            }
        }
    }
}
