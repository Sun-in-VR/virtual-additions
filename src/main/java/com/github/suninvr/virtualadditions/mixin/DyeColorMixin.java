package com.github.suninvr.virtualadditions.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(DyeColor.class)
public class DyeColorMixin {

    @Unique
    private static DyeColor[] allValues;

    @ModifyReturnValue(method = "values", at = @At("RETURN"))
    private static DyeColor[] virtualAdditions$addValues(DyeColor[] original) {
        if (allValues == null) {
            allValues = original;
            virtualAdditions$addVariant("chartreuse", 0xA3C115, MapColor.COLOR_LIGHT_GREEN, 0xA3C115, 0xA3C115);
            virtualAdditions$addVariant("maroon", 0x641003, MapColor.NETHER, 0x641003, 0x641003);
            virtualAdditions$addVariant("indigo", 0x5C21CC, MapColor.COLOR_BLUE, 0x5C21CC, 0x5C21CC);
            virtualAdditions$addVariant("plum", 0xA24058, MapColor.CRIMSON_STEM, 0xA24058, 0xA24058);
            virtualAdditions$addVariant("viridian", 0x406C5F, MapColor.GRASS, 0x406C5F, 0x406C5F);
            virtualAdditions$addVariant("tan", 0xC1906F, MapColor.TERRACOTTA_WHITE, 0xC1906F, 0xC1906F);
            virtualAdditions$addVariant("sinopia", 0xB1390A, MapColor.COLOR_ORANGE, 0xB1390A, 0xB1390A);
            virtualAdditions$addVariant("lilac", 0xCF96D5, MapColor.ICE, 0xCF96D5, 0xCF96D5);
        }
        return allValues;
    }

    @Invoker("<init>")
    public static DyeColor virtualAdditions$invokeInit(String internalName, int internalId, int id, String name, int color, MapColor mapColor, int fireworkColor, int dyeColor) {
        throw new AssertionError();
    }

    @Unique
    private static DyeColor virtualAdditions$addVariant(String name, int color, MapColor mapColor, int fireworkColor, int dyeColor) {
        ArrayList<DyeColor> newList = new ArrayList<>(Arrays.asList(DyeColorMixin.allValues));
        int i = newList.get(newList.size() - 1).ordinal() + 1;
        DyeColor newColor = DyeColorMixin.virtualAdditions$invokeInit(name, i, i, name, color, mapColor, fireworkColor, dyeColor);
        newList.add(newColor);
        DyeColorMixin.allValues = newList.toArray(new DyeColor[0]);
        return newColor;
    }
}
