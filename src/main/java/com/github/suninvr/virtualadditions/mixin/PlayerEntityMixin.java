package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.interfaces.PlayerEntityInterface;
import com.github.suninvr.virtualadditions.item.VAToolUtil;
import com.github.suninvr.virtualadditions.registry.VADamageTypes;
import com.github.suninvr.virtualadditions.registry.VAEntityAttributes;
import com.github.suninvr.virtualadditions.registry.VAGildTypes;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityInterface {
    @Shadow public abstract boolean isSwimming();

    @Shadow public abstract ItemCooldowns getCooldowns();

    @Unique private PlayerProjectionEntity projection = null;

    @Unique long lastSwungHalberd;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public PlayerProjectionEntity virtualAdditions$getProjectionEntity() {
        return this.projection;
    }

    @Override
    public void virtualAdditions$setProjectionEntity(PlayerProjectionEntity projection) {
        this.projection = projection;
    }

    @Override
    public boolean virtualAdditions$hasProjectionEntity() {
        return this.projection != null;
    }

    @Override
    public void virtualAdditions$onHalberdSwing() {
        this.lastSwungHalberd = this.level().getGameTime();
    }

    @Override
    public long virtualAdditions$lastSwungHalberd() {
        return this.lastSwungHalberd;
    }

    @ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 3)
     float virtualAdditions$setCriticalHitDamage(float value, @Local(ordinal = 2) boolean bl3, @Local(ordinal = 0) float f, @Local(ordinal = 1) float g) {
        if (bl3) {
            float h = f + (f * 0.333333333F * ((float) this.getAttributeValue(VAEntityAttributes.CRITICAL_HIT_FACTOR) - 1));
            return h + g;
        } else return value;
    }

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void virtualAdditions$createPlayerAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue().add(VAEntityAttributes.CRITICAL_HIT_FACTOR));
    }

    @Inject(method = "getDestroySpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectUtil;hasDigSpeed(Lnet/minecraft/world/entity/LivingEntity;)Z", shift = At.Shift.BEFORE))
    void virtualAdditions$getBlockBreakingSpeedForSculkGildedTools(BlockState block, CallbackInfoReturnable<Float> cir, @Local LocalFloatRef f) {
        if (f.get() <= 1.0F) return;
        Player player = ((Player)(Object)this);
        ItemStack stack = player.getMainHandItem();
        if (VAGildTypes.SCULK.equals(VAToolUtil.getGildType(stack))) {
            f.set((float) (f.get() - player.getAttributeValue(Attributes.MINING_EFFICIENCY)));
        }
    }

    @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    public void virtualAdditions$getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        float r = cir.getReturnValue();
        Player player = ((Player)(Object)this);

        Entity entity = player.getRootVehicle();
        boolean bl = entity.onGround() || (entity instanceof LivingEntity livingEntity && livingEntity.onClimbable());
        if (bl && !player.onGround()) r *= 5.0F;

        cir.setReturnValue(r);
    }

    @Inject(method = "hurtArmor", at = @At("HEAD"))
    void virtualAdditions$damageArmorFromAcid(DamageSource source, float amount, CallbackInfo ci, @Local(argsOnly = true) LocalFloatRef amountRef) {
        if (source.is(VADamageTypes.INCREASED_ARMOR_DAMAGE)) amountRef.set(amountRef.get() * 3.0F);
    }

    @Inject(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;removeEntitiesOnShoulder()V", shift = At.Shift.AFTER))
    void virtualAdditions$cancelSpectralSpyglassUsage(ServerLevel world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.isUsingItem() && this.getUseItem().is(VAItems.SPECTRAL_SPYGLASS)) {
            this.getCooldowns().addCooldown(this.getUseItem(), 100);
            this.stopUsingItem();
        }
    }
}
