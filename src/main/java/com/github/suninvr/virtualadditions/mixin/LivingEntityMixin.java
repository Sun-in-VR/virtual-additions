package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.LivingEntityInterface;
import com.github.suninvr.virtualadditions.item.enums.GildType;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityInterface {

    @Shadow protected boolean dead;

    private float experienceMultiplier = 1.0F;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
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
}
