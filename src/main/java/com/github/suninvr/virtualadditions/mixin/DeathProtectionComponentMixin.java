package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(DeathProtection.class)
public class DeathProtectionComponentMixin {
    @Shadow @Final @Mutable
    public static DeathProtection TOTEM_OF_UNDYING;

    static {
        List<ConsumeEffect> list = new ArrayList<>(TOTEM_OF_UNDYING.deathEffects());
        list.add(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(VAStatusEffects.IOLITE_INTERFERENCE, 3600, 1)));
        TOTEM_OF_UNDYING = new DeathProtection(list);
    }
}
