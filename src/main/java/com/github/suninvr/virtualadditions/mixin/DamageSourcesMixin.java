package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.entity.AcidSpitEntity;
import com.github.suninvr.virtualadditions.interfaces.DamageSourcesInterface;
import com.github.suninvr.virtualadditions.registry.VADamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DamageSources.class)
public abstract class DamageSourcesMixin implements DamageSourcesInterface {

    @Shadow protected abstract DamageSource create(RegistryKey<DamageType> key);

    @Shadow protected abstract DamageSource create(RegistryKey<DamageType> key, @Nullable Entity attacker);

    @Shadow protected abstract DamageSource create(RegistryKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker);

    private DamageSource acid;

    @Inject(method = "<init>", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    void virtualAdditions$customDamageSources(DynamicRegistryManager registryManager, CallbackInfo ci) {
        this.acid = this.create(VADamageTypes.ACID);
    }

    @Override
    public DamageSource acid() {
        return this.acid;
    }

    public DamageSource acidSpit(AcidSpitEntity source, @Nullable Entity attacker) {
        return this.create(VADamageTypes.ACID_SPIT, source, attacker);
    }
}
