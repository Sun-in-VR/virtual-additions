package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
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

    public static final ColoringStationBlockEntity.DyeContents WHITE_COST = new ColoringStationBlockEntity.DyeContents(0, 0, 0, 0, 0, 4);
    public static final ColoringStationBlockEntity.DyeContents LIGHT_GRAY_COST = new ColoringStationBlockEntity.DyeContents(0, 0, 0, 0, 1, 3);
    public static final ColoringStationBlockEntity.DyeContents GRAY_COST = new ColoringStationBlockEntity.DyeContents(0, 0, 0, 0, 2, 2);
    public static final ColoringStationBlockEntity.DyeContents BLACK_COST = new ColoringStationBlockEntity.DyeContents(0, 0, 0, 0, 4, 0);
    public static final ColoringStationBlockEntity.DyeContents TAN_COST = new ColoringStationBlockEntity.DyeContents(1, 0, 0, 1, 1, 1);
    public static final ColoringStationBlockEntity.DyeContents BROWN_COST = new ColoringStationBlockEntity.DyeContents(1, 0, 0, 1, 2, 0);
    public static final ColoringStationBlockEntity.DyeContents MAROON_COST = new ColoringStationBlockEntity.DyeContents(2, 0, 0, 0, 2, 0);
    public static final ColoringStationBlockEntity.DyeContents RED_COST = new ColoringStationBlockEntity.DyeContents(4, 0, 0, 0, 0, 0);
    public static final ColoringStationBlockEntity.DyeContents SINOPIA_COST = new ColoringStationBlockEntity.DyeContents(3, 0, 0, 1, 0, 0);
    public static final ColoringStationBlockEntity.DyeContents ORANGE_COST = new ColoringStationBlockEntity.DyeContents(2, 0, 0, 2, 0, 0);
    public static final ColoringStationBlockEntity.DyeContents YELLOW_COST = new ColoringStationBlockEntity.DyeContents(0, 0, 0, 4, 0, 0);
    public static final ColoringStationBlockEntity.DyeContents CHARTREUSE_COST = new ColoringStationBlockEntity.DyeContents(0, 2, 0, 1, 0, 1);
    public static final ColoringStationBlockEntity.DyeContents LIME_COST = new ColoringStationBlockEntity.DyeContents(0, 2, 0, 0, 0, 2);
    public static final ColoringStationBlockEntity.DyeContents GREEN_COST = new ColoringStationBlockEntity.DyeContents(0, 4, 0, 0, 0, 0);
    public static final ColoringStationBlockEntity.DyeContents VIRIDIAN_COST = new ColoringStationBlockEntity.DyeContents(0, 3, 1, 0, 0, 0);
    public static final ColoringStationBlockEntity.DyeContents CYAN_COST = new ColoringStationBlockEntity.DyeContents(0, 2, 2, 0, 0, 0);
    public static final ColoringStationBlockEntity.DyeContents LIGHT_BLUE_COST = new ColoringStationBlockEntity.DyeContents(0, 0, 2, 0, 0, 2);
    public static final ColoringStationBlockEntity.DyeContents BLUE_COST = new ColoringStationBlockEntity.DyeContents(0, 0, 4, 0, 0, 0);
    public static final ColoringStationBlockEntity.DyeContents INDIGO_COST = new ColoringStationBlockEntity.DyeContents(1, 0, 3, 0, 0, 0);
    public static final ColoringStationBlockEntity.DyeContents PURPLE_COST = new ColoringStationBlockEntity.DyeContents(2, 0, 2, 0, 0, 0);
    public static final ColoringStationBlockEntity.DyeContents PLUM_COST = new ColoringStationBlockEntity.DyeContents(2, 0, 1, 0, 1, 0);
    public static final ColoringStationBlockEntity.DyeContents MAGENTA_COST = new ColoringStationBlockEntity.DyeContents(2, 0, 1, 0, 0, 1);
    public static final ColoringStationBlockEntity.DyeContents PINK_COST = new ColoringStationBlockEntity.DyeContents(2, 0, 0, 0, 0, 2);
    public static final ColoringStationBlockEntity.DyeContents LILAC_COST = new ColoringStationBlockEntity.DyeContents(1, 0, 1, 0, 0, 2);


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

    public static ColoringStationBlockEntity.DyeContents getContents(DyeItem item, int multiplier) {
        return getContents(item.getColor(), multiplier);
    }

    public static ColoringStationBlockEntity.DyeContents getContents(DyeColor color, int multiplier) {
        if (color == DyeColor.WHITE) return WHITE_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.LIGHT_GRAY) return LIGHT_GRAY_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.GRAY) return GRAY_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.BLACK) return BLACK_COST.copyAndMultiply(multiplier);
        if (color == TAN) return TAN_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.BROWN) return BROWN_COST.copyAndMultiply(multiplier);
        if (color == MAROON) return MAROON_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.RED) return RED_COST.copyAndMultiply(multiplier);
        if (color == SINOPIA) return SINOPIA_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.ORANGE) return ORANGE_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.YELLOW) return YELLOW_COST.copyAndMultiply(multiplier);
        if (color == CHARTREUSE) return CHARTREUSE_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.LIME) return LIME_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.GREEN) return GREEN_COST.copyAndMultiply(multiplier);
        if (color == VIRIDIAN) return VIRIDIAN_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.CYAN) return CYAN_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.LIGHT_BLUE) return LIGHT_BLUE_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.BLUE) return BLUE_COST.copyAndMultiply(multiplier);
        if (color == INDIGO) return INDIGO_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.PURPLE) return PURPLE_COST.copyAndMultiply(multiplier);
        if (color == PLUM) return PLUM_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.MAGENTA) return MAGENTA_COST.copyAndMultiply(multiplier);
        if (color == DyeColor.PINK) return PINK_COST.copyAndMultiply(multiplier);
        if (color == LILAC) return LILAC_COST.copyAndMultiply(multiplier);
        throw new IncompatibleClassChangeError();
    }

    public static void init(){}
}
