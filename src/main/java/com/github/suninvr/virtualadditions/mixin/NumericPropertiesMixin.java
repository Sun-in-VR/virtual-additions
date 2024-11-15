package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.client.render.item.CrossbowProjectileTypeProperty;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.numeric.NumericProperties;
import net.minecraft.client.render.item.property.numeric.NumericProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@Mixin(NumericProperties.class)
public class NumericPropertiesMixin {
    @Shadow @Final private static Codecs.IdMapper<Identifier, MapCodec<? extends NumericProperty>> ID_MAPPER;

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void virtualAdditions$bootstrap(CallbackInfo ci) {
        ID_MAPPER.put(idOf("crossbow/projectile_type"), CrossbowProjectileTypeProperty.CODEC);
    }
}
