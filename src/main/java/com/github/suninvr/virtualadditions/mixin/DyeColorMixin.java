package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.block.MapColor;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(DyeColor.class)
public class DyeColorMixin {

    @Unique
    private static DyeColor[] allValues;

    @Shadow @Final private int id;

    //private static final DyeColor CHARTREUSE = virtualAdditions$addVariant("CHARTREUSE", "chartreuse", 0xA3C115, MapColor.LIME, 0xA3C115, 0xA3C115);

    @Inject(method = "values", at = @At("RETURN"), cancellable = true)
    private static void virtualAdditions$values(CallbackInfoReturnable<DyeColor[]> cir) {
        if (allValues == null) {
            allValues = cir.getReturnValue();
            virtualAdditions$addVariant("CHARTREUSE", "chartreuse", 0xA3C115, MapColor.LIME, 0xA3C115, 0xA3C115);
            virtualAdditions$addVariant("MAROON", "maroon", 0x641003, MapColor.DARK_RED, 0x641003, 0x641003);
            virtualAdditions$addVariant("INDIGO", "indigo", 0x320091, MapColor.BLUE, 0x320091, 0x320091);
            virtualAdditions$addVariant("PLUM", "plum", 0x792D56, MapColor.DULL_PINK, 0x792D56, 0x792D56);
            virtualAdditions$addVariant("VIRIDIAN", "viridian", 0x406C5F, MapColor.PALE_GREEN, 0x406C5F, 0x406C5F);
            virtualAdditions$addVariant("TAN", "tan", 0xC1906F, MapColor.TERRACOTTA_WHITE, 0xC1906F, 0xC1906F);
            virtualAdditions$addVariant("SINOPIA", "sinopia", 0xB1390A, MapColor.ORANGE, 0xB1390A, 0xB1390A);
            virtualAdditions$addVariant("LILAC", "lilac", 0xB290B3, MapColor.PALE_PURPLE, 0xB290B3, 0xB290B3);
        }
        cir.setReturnValue(allValues);
    }

    @Invoker("<init>")
    public static DyeColor virtualAdditions$invokeInit(String internalName, int internalId, int id, String name, int color, MapColor mapColor, int fireworkColor, int dyeColor) {
        throw new AssertionError();
    }

    @Unique
    private static DyeColor virtualAdditions$addVariant(String internalName, String name, int color, MapColor mapColor, int fireworkColor, int dyeColor) {
        ArrayList<DyeColor> newList = new ArrayList<>(Arrays.asList(DyeColorMixin.allValues));
        int i = newList.get(newList.size() - 1).ordinal() + 1;
        DyeColor newColor = DyeColorMixin.virtualAdditions$invokeInit(internalName, i, i, name, color, mapColor, fireworkColor, dyeColor);
        newList.add(newColor);
        DyeColorMixin.allValues = newList.toArray(new DyeColor[0]);
        return newColor;
    }
}
