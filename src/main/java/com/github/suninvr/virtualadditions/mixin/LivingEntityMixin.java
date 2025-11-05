package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.SpectreEntity;
import com.github.suninvr.virtualadditions.item.VAToolUtil;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VAGildTypes;
import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@SuppressWarnings("ConstantValue")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow protected boolean dead;

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> holder);

    @Shadow @Nullable public abstract MobEffectInstance getEffect(Holder<MobEffect> holder);

    @Shadow public abstract Collection<MobEffectInstance> getActiveEffects();

    @Unique private long lastHurtByFesteringWounds;

    @Unique private float experienceMultiplier = 1.0F;

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "die", at = @At("HEAD"))
    void virtualAdditions$setExperienceMultiplier(DamageSource source, CallbackInfo ci) {
        if (!this.isRemoved() && !this.dead && !((LivingEntity)(Object)(this) instanceof Player)) {
            Entity entity = source.getEntity();
            if (entity instanceof Player playerEntity) {
                ItemStack stack = playerEntity.getMainHandItem();
                if (VAGildTypes.EMERALD.equals(VAToolUtil.getGildType(stack))) {
                    this.experienceMultiplier = 1.6F;
                }
            }
        }
    }

    @Inject(method = "remove", at = @At("HEAD"))
    void virtualAdditions$onRemoveSpectre(RemovalReason reason, CallbackInfo ci) {
        if ((Object)(this) instanceof SpectreEntity spectre) {
            spectre.setBuffTarget(null);
        }
    }

    @Inject(method = "getDamageAfterMagicAbsorb", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$modifyAppliedDamageForFrailty(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (this.hasEffect(VAStatusEffects.FRAILTY)) cir.setReturnValue(cir.getReturnValueF() * (1.0F + (0.2F * (this.getEffect(VAStatusEffects.FRAILTY).getAmplifier() + 1))));
    }

    @Inject(method = "hurtServer", at = @At("TAIL"))
    void virtualAdditions$spreadDamageForFesteringWoundsEffect(ServerLevel world, DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasEffect(VAStatusEffects.FESTERING_WOUNDS) && this.lastHurtByFesteringWounds != world.getGameTime()) {
            this.lastHurtByFesteringWounds = world.getGameTime();
            world.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(12, 12, 12)).stream().filter(entity -> entity != (Object)this).forEach(entity -> {
                if (entity.hasEffect(VAStatusEffects.FESTERING_WOUNDS)) {
                    if (entity.hurtServer(world, damageSource, f)) {
                        this.getActiveEffects().forEach(statusEffectInstance -> {
                            Holder<MobEffect> effect = statusEffectInstance.getEffect();
                            if (!entity.hasEffect(effect) || entity.getEffect(effect).compareTo(statusEffectInstance) < 0) {
                                entity.addEffect(statusEffectInstance);
                            }
                        });
                        world.sendParticles(new VibrationParticleOption(new EntityPositionSource(entity, entity.getEyeHeight(entity.getPose())), 8), this.getX(), this.getEyeY(), this.getZ(), 1, 0, 0, 0, 0);
                        world.playSound(entity, entity.blockPosition(), SoundEvents.SCULK_BLOCK_CHARGE, entity.getSoundSource(), 1.0F, 0.5F);
                    }
                };
            });
        }
    }

    @Inject(method = "onClimbable", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$isClimbingRope(CallbackInfoReturnable<Boolean> cir) {
        if (!this.isSpectator() && !(this.level() == null)) {
            if (this.level().getBlockState(this.blockPosition()).getCollisionShape(this.level(), this.blockPosition()).equals(Shapes.empty())) {
                BlockPos blockPos = this.blockPosition().below();
                BlockState state = this.level().getBlockState(blockPos);
                if (state.is(VABlockTags.CLIMBING_ROPES)) cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "getBaseExperienceReward", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$getModifiedXpToDrop(ServerLevel serverLevel, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) (cir.getReturnValueI() * this.experienceMultiplier));
    }
}
