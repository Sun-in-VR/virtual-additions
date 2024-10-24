package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.block.entity.DyeContents;
import com.github.suninvr.virtualadditions.client.VARenderers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.DyeItem;
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

    public static final DyeContents WHITE_CONTENT = new DyeContents(0, 0, 0, 0, 0, 4);
    public static final DyeContents LIGHT_GRAY_CONTENT = new DyeContents(0, 0, 0, 0, 1, 3); // 3rd's
    public static final DyeContents GRAY_CONTENT = new DyeContents(0, 0, 0, 0, 2, 2);
    public static final DyeContents BLACK_CONTENT = new DyeContents(0, 0, 0, 0, 4, 0);
    public static final DyeContents TAN_CONTENT = new DyeContents(1, 0, 0, 1, 1, 1);
    public static final DyeContents BROWN_CONTENT = new DyeContents(1, 0, 0, 1, 2, 0);
    public static final DyeContents MAROON_CONTENT = new DyeContents(2, 0, 0, 0, 2, 0);
    public static final DyeContents RED_CONTENT = new DyeContents(4, 0, 0, 0, 0, 0);
    public static final DyeContents SINOPIA_CONTENT = new DyeContents(3, 0, 0, 1, 0, 0); // 3rd's
    public static final DyeContents ORANGE_CONTENT = new DyeContents(2, 0, 0, 2, 0, 0);
    public static final DyeContents YELLOW_CONTENT = new DyeContents(0, 0, 0, 4, 0, 0);
    public static final DyeContents CHARTREUSE_CONTENT = new DyeContents(0, 2, 0, 1, 0, 1);
    public static final DyeContents LIME_CONTENT = new DyeContents(0, 2, 0, 0, 0, 2);
    public static final DyeContents GREEN_CONTENT = new DyeContents(0, 4, 0, 0, 0, 0);
    public static final DyeContents VIRIDIAN_CONTENT = new DyeContents(0, 3, 1, 0, 0, 0); // 3rd's
    public static final DyeContents CYAN_CONTENT = new DyeContents(0, 2, 2, 0, 0, 0);
    public static final DyeContents LIGHT_BLUE_CONTENT = new DyeContents(0, 0, 2, 0, 0, 2);
    public static final DyeContents BLUE_CONTENT = new DyeContents(0, 0, 4, 0, 0, 0);
    public static final DyeContents INDIGO_CONTENT = new DyeContents(1, 0, 3, 0, 0, 0); // 3rd's
    public static final DyeContents PURPLE_CONTENT = new DyeContents(2, 0, 2, 0, 0, 0);
    public static final DyeContents PLUM_CONTENT = new DyeContents(2, 0, 1, 0, 1, 0);
    public static final DyeContents MAGENTA_CONTENT = new DyeContents(2, 0, 1, 0, 0, 1);
    public static final DyeContents PINK_CONTENT = new DyeContents(2, 0, 0, 0, 0, 2);
    public static final DyeContents LILAC_CONTENT = new DyeContents(1, 0, 1, 0, 0, 2);


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

    public static DyeContents getContents(DyeItem item, int multiplier) {
        return getContents(item.getColor(), multiplier);
    }

    public static DyeContents getContents(DyeColor color, int multiplier) {
        if (color == DyeColor.WHITE) return WHITE_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.LIGHT_GRAY) return LIGHT_GRAY_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.GRAY) return GRAY_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.BLACK) return BLACK_CONTENT.copyAndMultiply(multiplier);
        if (color == TAN) return TAN_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.BROWN) return BROWN_CONTENT.copyAndMultiply(multiplier);
        if (color == MAROON) return MAROON_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.RED) return RED_CONTENT.copyAndMultiply(multiplier);
        if (color == SINOPIA) return SINOPIA_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.ORANGE) return ORANGE_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.YELLOW) return YELLOW_CONTENT.copyAndMultiply(multiplier);
        if (color == CHARTREUSE) return CHARTREUSE_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.LIME) return LIME_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.GREEN) return GREEN_CONTENT.copyAndMultiply(multiplier);
        if (color == VIRIDIAN) return VIRIDIAN_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.CYAN) return CYAN_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.LIGHT_BLUE) return LIGHT_BLUE_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.BLUE) return BLUE_CONTENT.copyAndMultiply(multiplier);
        if (color == INDIGO) return INDIGO_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.PURPLE) return PURPLE_CONTENT.copyAndMultiply(multiplier);
        if (color == PLUM) return PLUM_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.MAGENTA) return MAGENTA_CONTENT.copyAndMultiply(multiplier);
        if (color == DyeColor.PINK) return PINK_CONTENT.copyAndMultiply(multiplier);
        if (color == LILAC) return LILAC_CONTENT.copyAndMultiply(multiplier);
        throw new IncompatibleClassChangeError();
    }

    public static void init(){}
}
