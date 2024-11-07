package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.component.type.DeathProtectionComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(DeathProtectionComponent.class)
public class DeathProtectionComponentMixin {
    @Shadow @Final @Mutable
    public static DeathProtectionComponent TOTEM_OF_UNDYING;

    static {
        List<ConsumeEffect> list = new ArrayList<>(TOTEM_OF_UNDYING.deathEffects());
        list.add(new ApplyEffectsConsumeEffect(new StatusEffectInstance(VAStatusEffects.IOLITE_INTERFERENCE, 3600, 1)));
        TOTEM_OF_UNDYING = new DeathProtectionComponent(list);
    }
}
