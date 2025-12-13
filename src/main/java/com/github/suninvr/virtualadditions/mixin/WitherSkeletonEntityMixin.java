package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABiomeKeys;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeleton.class)
public class WitherSkeletonEntityMixin extends Mob {
    protected WitherSkeletonEntityMixin(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$initWitherSkeletonArmor(RandomSource random, DifficultyInstance localDifficulty, CallbackInfo ci) {
        if (this.getType().equals(EntityType.WITHER_SKELETON) && this.level().getBiome(this.blockPosition()).is(VABiomeKeys.WITHERED_WOODS)) {
            float f = random.nextFloat();
            float d = localDifficulty.getEffectiveDifficulty() * 0.05F;
            boolean bow = random.nextFloat() > 0.85F;
            if (f > (1.0F - d)) {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(VAItems.STONE_HALBERD));
                this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.NETHERITE_HELMET));
                this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.NETHERITE_CHESTPLATE));
                this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.NETHERITE_LEGGINGS));
                this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.NETHERITE_BOOTS));
                this.setDropChance(EquipmentSlot.HEAD, 0.001F);
                this.setDropChance(EquipmentSlot.CHEST, 0.001F);
                this.setDropChance(EquipmentSlot.LEGS, 0.001F);
                this.setDropChance(EquipmentSlot.FEET, 0.001F);
                ci.cancel();
            } else if (bow) {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                ci.cancel();
            }
        }
    }
}
