package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.client.VARenderers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.DyeColor;

public class VADyeColors {
    public static final DyeColor CHARTREUSE = DyeColor.byName("chartreuse", DyeColor.WHITE);
    public static final DyeColor MAROON = DyeColor.byName("maroon", DyeColor.WHITE);
    public static final DyeColor INDIGO = DyeColor.byName("indigo", DyeColor.WHITE);
    public static final DyeColor PLUM = DyeColor.byName("plum", DyeColor.WHITE);
    public static final DyeColor VIRIDIAN = DyeColor.byName("viridian", DyeColor.WHITE);
    public static final DyeColor TAN = DyeColor.byName("tan", DyeColor.WHITE);
    public static final DyeColor SINOPIA = DyeColor.byName("sinopia", DyeColor.WHITE);
    public static final DyeColor LILAC = DyeColor.byName("lilac", DyeColor.WHITE);


    @Environment(EnvType.CLIENT)
    public static SpriteIdentifier getShulkerBoxTexture(DyeColor color) {
        if (color == VADyeColors.CHARTREUSE) return VARenderers.CHARTREUSE_SHULKER_BOX;
        if (color == VADyeColors.MAROON) return VARenderers.MAROON_SHULKER_BOX;
        if (color == VADyeColors.INDIGO) return VARenderers.INDIGO_SHULKER_BOX;
        if (color == VADyeColors.PLUM) return VARenderers.PLUM_SHULKER_BOX;
        if (color == VADyeColors.VIRIDIAN) return VARenderers.VIRIDIAN_SHULKER_BOX;
        if (color == VADyeColors.TAN) return VARenderers.TAN_SHULKER_BOX;
        if (color == VADyeColors.SINOPIA) return VARenderers.SINOPIA_SHULKER_BOX;
        if (color == VADyeColors.LILAC) return VARenderers.LILAC_SHULKER_BOX;
        return VARenderers.CHARTREUSE_SHULKER_BOX;
    }

    @Environment(EnvType.CLIENT)
    public static SpriteIdentifier getBedTexture(DyeColor color) {
        if (color == VADyeColors.CHARTREUSE) return VARenderers.CHARTREUSE_BED_TEXTURE;
        if (color == VADyeColors.MAROON) return VARenderers.MAROON_BED_TEXTURE;
        if (color == VADyeColors.INDIGO) return VARenderers.INDIGO_BED_TEXTURE;
        if (color == VADyeColors.PLUM) return VARenderers.PLUM_BED_TEXTURE;
        if (color == VADyeColors.VIRIDIAN) return VARenderers.VIRIDIAN_BED_TEXTURE;
        if (color == VADyeColors.TAN) return VARenderers.TAN_BED_TEXTURE;
        if (color == VADyeColors.SINOPIA) return VARenderers.SINOPIA_BED_TEXTURE;
        if (color == VADyeColors.LILAC) return VARenderers.LILAC_BED_TEXTURE;
        return VARenderers.CHARTREUSE_BED_TEXTURE;
    }

    public static void init(){}
}
