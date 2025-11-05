package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.AcidSpitEntity;
import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.interfaces.DamageSourcesInterface;
import com.github.suninvr.virtualadditions.registry.VADamageTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DamageSources.class)
public abstract class DamageSourcesMixin implements DamageSourcesInterface {

    @Shadow public abstract DamageSource source(ResourceKey<DamageType> resourceKey);

    @Shadow public abstract DamageSource source(ResourceKey<DamageType> resourceKey, @Nullable Entity entity, @Nullable Entity entity2);

    @Shadow public abstract DamageSource source(ResourceKey<DamageType> resourceKey, @Nullable Entity entity);

    private DamageSource acid;

    @Inject(method = "<init>", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    void virtualAdditions$customDamageSources(RegistryAccess registryManager, CallbackInfo ci) {
        this.acid = this.source(VADamageTypes.ACID);
    }

    @Override
    public DamageSource virtualAdditions$acid() {
        return this.acid;
    }

    public DamageSource virtualAdditions$acidSpit(AcidSpitEntity source, @Nullable Entity attacker) {
        return this.source(VADamageTypes.ACID_SPIT, source, attacker);
    }

    public DamageSource virtualAdditions$soulDestroyed(@Nullable PlayerProjectionEntity source, @Nullable Entity attacker) {
        return this.source(VADamageTypes.SOUL_DESTROYED, source, attacker);
    }
}
