package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends PassiveEntity {

    @Shadow public abstract void lovePlayer(@Nullable PlayerEntity player);

    @Shadow private int loveTicks;

    protected AnimalEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "mobTick", at = @At("HEAD"))
    private void virtualAdditions$tickLovePotion(CallbackInfo ci) {
        if (this.hasStatusEffect(VAStatusEffects.LOVE)) {
            if (this.breedingAge != 0 || this.loveTicks > 0) {
                this.removeStatusEffect(VAStatusEffects.LOVE);
                return;
            }
            int i = 500 / (Math.max(1, this.getStatusEffect(VAStatusEffects.LOVE).getAmplifier() + 1));
            if (this.getWorld().getRandom().nextInt(i) < 1) {
                this.lovePlayer(null);
                this.removeStatusEffect(VAStatusEffects.LOVE);
            }
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
