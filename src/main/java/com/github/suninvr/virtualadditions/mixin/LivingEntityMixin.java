package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.LivingEntityInterface;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import com.github.suninvr.virtualadditions.interfaces.EntityInterface;
import com.github.suninvr.virtualadditions.item.enums.GildType;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.VAFluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityInterface {
    @Shadow protected abstract boolean shouldSwimInFluids();

    @Shadow public abstract boolean canWalkOnFluid(FluidState state);

    @Shadow public abstract boolean canMoveVoluntarily();

    @Shadow public abstract Vec3d applyFluidMovingSpeed(double gravity, boolean falling, Vec3d motion);

    @Shadow public abstract void updateLimbs(LivingEntity entity, boolean flutter);

    @Shadow protected boolean dead;

    @Shadow public abstract boolean isExperienceDroppingDisabled();

    @Shadow protected abstract boolean shouldAlwaysDropXp();

    @Shadow protected int playerHitTimer;

    @Shadow public abstract boolean shouldDropXp();

    @Shadow public abstract int getXpToDrop();

    private float experienceMultiplier = 1.0F;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$travelInAcid(Vec3d movementInput, CallbackInfo ci) {
        if (this.canMoveVoluntarily() || this.isLogicalSideForUpdatingMovement()) {
            double d = 0.08D;
            boolean bl = this.getVelocity().y <= 0.0D;

            FluidState fluidState = this.world.getFluidState(this.getBlockPos());
            double e;
            if (((EntityInterface) (this)).isInAcid() && this.shouldSwimInFluids() && !this.canWalkOnFluid(fluidState)) {
                e = this.getY();
                this.updateVelocity(0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                Vec3d vec3d3;
                if (this.getFluidHeight(VAFluidTags.ACID) <= this.getSwimHeight()) {
                    this.setVelocity(this.getVelocity().multiply(0.5D, 0.800000011920929D, 0.5D));
                    vec3d3 = this.applyFluidMovingSpeed(d, bl, this.getVelocity());
                    this.setVelocity(vec3d3);
                } else {
                    this.setVelocity(this.getVelocity().multiply(0.5D));
                }

                if (!this.hasNoGravity()) {
                    this.setVelocity(this.getVelocity().add(0.0D, -d / 4.0D, 0.0D));
                }

                vec3d3 = this.getVelocity();
                if (this.horizontalCollision && this.doesNotCollide(vec3d3.x, vec3d3.y + 0.6000000238418579D - this.getY() + e, vec3d3.z)) {
                    this.setVelocity(vec3d3.x, 0.30000001192092896D, vec3d3.z);
                }

                this.updateLimbs((LivingEntity)(Object)this, this instanceof Flutterer);
                ci.cancel();
            }
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    void virtualAdditions$setExperienceMultiplier(DamageSource source, CallbackInfo ci) {
        if (!this.isRemoved() && !this.dead && !((LivingEntity)(Object)(this) instanceof PlayerEntity)) {
            Entity entity = source.getAttacker();
            if (entity instanceof PlayerEntity playerEntity) {
                if (GildedToolItem.getGildType(playerEntity.getMainHandStack()).equals(GildType.EMERALD)) {
                    this.experienceMultiplier = 1.6F;
                }
            }
        }
    }

    @Override
    public float getXpModifier() {
        return this.experienceMultiplier;
    }

    @Inject(method = "dropXp", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$dropModifiedXp(CallbackInfo ci) {
        if (this.experienceMultiplier > 1) {
            //if (this.world instanceof ServerWorld && !this.isExperienceDroppingDisabled() && (this.shouldAlwaysDropXp() || this.playerHitTimer > 0 && this.shouldDropXp() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
            //    ExperienceOrbEntity.spawn((ServerWorld)this.world, this.getPos(), (int) Math.ceil(this.getXpToDrop() * experienceMultiplier));
            //}
            //ci.cancel();
        }
    }
}
