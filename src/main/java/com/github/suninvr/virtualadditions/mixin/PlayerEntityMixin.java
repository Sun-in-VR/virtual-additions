package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.GildTypes;
import com.github.suninvr.virtualadditions.item.GildedToolUtil;
import com.github.suninvr.virtualadditions.registry.VADamageTypes;
import com.github.suninvr.virtualadditions.registry.VAEnchantmentTags;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract boolean isSwimming();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    public void virtualAdditions$getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        float r = cir.getReturnValue();
        PlayerEntity player = ((PlayerEntity)(Object)this);

        ItemStack stack = player.getMainHandStack();
        if (GildedToolUtil.getGildType(stack).equals(GildTypes.SCULK)) {
            float[] e = {0.0F};
            EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
                if (enchantment.isIn(VAEnchantmentTags.REDUCES_SCULK_GILD_MINING_SPEED)) e[0] += 0.12F * level;
            });
            r *= Math.max(1 - e[0], 0.4F);
        }

        Entity entity = player.getRootVehicle();
        boolean bl = entity.isOnGround() || (entity instanceof LivingEntity livingEntity && livingEntity.isClimbing());
        if (bl && !player.isOnGround()) r *= 5.0F;

        cir.setReturnValue(r);
    }

    @Inject(method = "damageArmor", at = @At("HEAD"))
    void virtualAdditions$damageArmorFromAcid(DamageSource source, float amount, CallbackInfo ci, @Local(argsOnly = true) LocalFloatRef amountRef) {
        if (source.isIn(VADamageTypes.INCREASED_ARMOR_DAMAGE)) amountRef.set(amountRef.get() * 3.0F);
    }
}
