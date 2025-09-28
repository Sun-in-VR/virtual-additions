package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.SpectreEntity;
import com.github.suninvr.virtualadditions.item.GildTypes;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
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

    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow public abstract Collection<StatusEffectInstance> getStatusEffects();

    @Unique private long lastHurtByFesteringWounds;

    @Unique private float experienceMultiplier = 1.0F;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    void virtualAdditions$setExperienceMultiplier(DamageSource source, CallbackInfo ci) {
        if (!this.isRemoved() && !this.dead && !((LivingEntity)(Object)(this) instanceof PlayerEntity)) {
            Entity entity = source.getAttacker();
            if (entity instanceof PlayerEntity playerEntity) {
                ItemStack stack = playerEntity.getMainHandStack();
                if (GildedToolItem.getGildType(stack).equals(GildTypes.EMERALD)) {
                    this.experienceMultiplier = 1.6F;
                }
            }
        }
    }

    @Inject(method = "onRemove", at = @At("HEAD"))
    void virtualAdditions$onRemoveSpectre(RemovalReason reason, CallbackInfo ci) {
        if ((Object)(this) instanceof SpectreEntity spectre) {
            spectre.setBuffTarget(null);
        }
    }

    @Inject(method = "modifyAppliedDamage", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$modifyAppliedDamageForFrailty(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (this.hasStatusEffect(VAStatusEffects.FRAILTY)) cir.setReturnValue(cir.getReturnValueF() * (1.0F + (0.2F * (this.getStatusEffect(VAStatusEffects.FRAILTY).getAmplifier() + 1))));
    }

    @Inject(method = "applyDamage", at = @At("TAIL"))
    void virtualAdditions$spreadDamageForFesteringWoundsEffect(ServerWorld world, DamageSource source, float amount, CallbackInfo ci) {
        if (this.hasStatusEffect(VAStatusEffects.FESTERING_WOUNDS) && this.lastHurtByFesteringWounds != world.getTime()) {
            this.lastHurtByFesteringWounds = world.getTime();
            world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(12, 12, 12)).stream().filter(entity -> entity != (Object)this).forEach(entity -> {
                if (entity.hasStatusEffect(VAStatusEffects.FESTERING_WOUNDS)) {
                    if (entity.damage(world, source, amount)) {
                        this.getStatusEffects().forEach(statusEffectInstance -> {
                            RegistryEntry<StatusEffect> effect = statusEffectInstance.getEffectType();
                            if (!entity.hasStatusEffect(effect) || entity.getStatusEffect(effect).compareTo(statusEffectInstance) < 0) {
                                entity.addStatusEffect(statusEffectInstance);
                            }
                        });
                        world.spawnParticles(new VibrationParticleEffect(new EntityPositionSource(entity, entity.getEyeHeight(entity.getPose())), 8), this.getX(), this.getEyeY(), this.getZ(), 1, 0, 0, 0, 0);
                        world.playSound(entity, entity.getBlockPos(), SoundEvents.BLOCK_SCULK_CHARGE, entity.getSoundCategory(), 1.0F, 0.5F);
                    }
                };
            });
        }
    }

    @Inject(method = "isClimbing", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$isClimbingRope(CallbackInfoReturnable<Boolean> cir) {
        if (!this.isSpectator() && !(this.getEntityWorld() == null)) {
            if (this.getEntityWorld().getBlockState(this.getBlockPos()).getCollisionShape(this.getEntityWorld(), this.getBlockPos()).equals(VoxelShapes.empty())) {
                BlockPos blockPos = this.getBlockPos().down();
                BlockState state = this.getEntityWorld().getBlockState(blockPos);
                if (state.isIn(VABlockTags.CLIMBING_ROPES)) cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "getExperienceToDrop(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;)I", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$getModifiedXpToDrop(ServerWorld world, Entity attacker, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) (cir.getReturnValueI() * this.experienceMultiplier));
    }

    @Inject(method = "travelControlled", at = @At("HEAD"))
    void virtualAdditions$travelConteolled(PlayerEntity controllingPlayer, Vec3d movementInput, CallbackInfo ci) {
    }
}
