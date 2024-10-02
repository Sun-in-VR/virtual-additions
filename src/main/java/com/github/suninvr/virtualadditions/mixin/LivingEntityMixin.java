package com.github.suninvr.virtualadditions.mixin;

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
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantValue")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow protected boolean dead;

    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    private float experienceMultiplier = 1.0F;

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

    @Inject(method = "modifyAppliedDamage", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$modifyAppliedDamageForFrailty(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (this.hasStatusEffect(VAStatusEffects.FRAILTY)) cir.setReturnValue(cir.getReturnValueF() * (1.0F + (0.2F * (this.getStatusEffect(VAStatusEffects.FRAILTY).getAmplifier() + 1))));
    }

    @Inject(method = "isClimbing", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$isClimbingRope(CallbackInfoReturnable<Boolean> cir) {
        if (!this.isSpectator() && !(this.getWorld() == null)) {
            if (this.getWorld().getBlockState(this.getBlockPos()).getCollisionShape(this.getWorld(), this.getBlockPos()).equals(VoxelShapes.empty())) {
                BlockPos blockPos = this.getBlockPos().down();
                BlockState state = this.getWorld().getBlockState(blockPos);
                if (state.isIn(VABlockTags.CLIMBING_ROPES)) cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "getXpToDrop(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;)I", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$getModifiedXpToDrop(ServerWorld world, Entity attacker, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) (cir.getReturnValueI() * this.experienceMultiplier));
    }
}
