package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Animal.class)
public abstract class AnimalEntityMixin extends AgeableMob {

    @Shadow public abstract void setInLove(@Nullable Player player);

    @Shadow private int inLove;

    protected AnimalEntityMixin(EntityType<? extends AgeableMob> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void virtualAdditions$tickLovePotion(CallbackInfo ci) {
        if (this.hasEffect(VAStatusEffects.LOVE)) {
            if (this.age != 0 || this.inLove > 0) {
                this.removeEffect(VAStatusEffects.LOVE);
                return;
            }
            int i = 500 / (Math.max(1, this.getEffect(VAStatusEffects.LOVE).getAmplifier() + 1));
            if (this.level().getRandom().nextInt(i) < 1) {
                this.setInLove(null);
                this.removeEffect(VAStatusEffects.LOVE);
            }
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return null;
    }
}
