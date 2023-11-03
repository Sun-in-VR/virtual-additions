package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.GildTypes;
import com.github.suninvr.virtualadditions.item.GildedToolUtil;
import com.github.suninvr.virtualadditions.registry.VADamageTypes;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Unique
    private static boolean ioliteGildExtendedReach;

    @Shadow public abstract boolean isSwimming();

    @Shadow @Final private PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;updateItems()V"))
    private void virtualAdditions$updateIoliteGildType(CallbackInfo ci) {
        TagKey<Item> tag = GildTypes.IOLITE.getTag();
        ioliteGildExtendedReach = this.getMainHandStack().isIn(tag) || this.getOffHandStack().isIn(tag);
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    public void virtualAdditions$getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        float r = cir.getReturnValue();
        PlayerEntity player = ((PlayerEntity)(Object)this);

        ItemStack stack = player.getMainHandStack();
        if (GildedToolUtil.getGildType(stack).equals(GildTypes.SCULK)) {
            float e = 0.12F * EnchantmentHelper.getEfficiency(player);
            r *= 1 - e;
        }

        Entity entity = player.getRootVehicle();
        boolean bl = entity.isOnGround() || (entity instanceof LivingEntity livingEntity && livingEntity.isClimbing());
        if (bl && !player.isOnGround()) r *= 5.0F;

        cir.setReturnValue(r);
    }

    @Inject(method = "damageArmor", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$damageArmorFromAcid(DamageSource source, float amount, CallbackInfo ci) {
        if (source.isIn(VADamageTypes.INCREASED_ARMOR_DAMAGE)) {
            if (amount > 1) amount *= 3;
            this.inventory.damageArmor(source, amount, PlayerInventory.ARMOR_SLOTS);
            ci.cancel();
        }
    }

    @Inject(method = "getReachDistance", at = @At("RETURN"), cancellable = true)
    private static void virtualAdditions$getExtendedReachDistance(boolean creative, CallbackInfoReturnable<Float> cir) {
        if (ioliteGildExtendedReach) cir.setReturnValue(cir.getReturnValue() + 3.5F);
    }
}
