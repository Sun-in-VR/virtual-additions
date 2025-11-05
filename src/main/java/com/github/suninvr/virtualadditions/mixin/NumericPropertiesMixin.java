package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.client.render.item.CrossbowProjectileTypeProperty;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@Mixin(RangeSelectItemModelProperties.class)
public class NumericPropertiesMixin {
    @Shadow @Final private static ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends RangeSelectItemModelProperty>> ID_MAPPER;

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void virtualAdditions$bootstrap(CallbackInfo ci) {
        ID_MAPPER.put(idOf("crossbow/projectile_type"), CrossbowProjectileTypeProperty.CODEC);
    }
}
