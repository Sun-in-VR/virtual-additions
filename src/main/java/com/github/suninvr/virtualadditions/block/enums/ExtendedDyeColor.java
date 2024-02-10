package com.github.suninvr.virtualadditions.block.enums;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.client.VARenderers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.StringIdentifiable;

public enum ExtendedDyeColor implements StringIdentifiable {
    CHARTREUSE,
    MAROON,
    INDIGO,
    PLUM,
    VIRIDIAN,
    TAN,
    SINOPIA,
    LILAC;

    @Override
    public String asString() {
        return null;
    }

    @Environment(EnvType.CLIENT)
    public SpriteIdentifier getBedTexture() {
        return switch (this) {
            case CHARTREUSE -> VARenderers.CHARTREUSE_BED_TEXTURE;
            case MAROON -> VARenderers.MAROON_BED_TEXTURE;
            case INDIGO -> VARenderers.INDIGO_BED_TEXTURE;
            case PLUM -> VARenderers.PLUM_BED_TEXTURE;
            case VIRIDIAN -> VARenderers.VIRIDIAN_BED_TEXTURE;
            case TAN -> VARenderers.TAN_BED_TEXTURE;
            case SINOPIA -> VARenderers.SINOPIA_BED_TEXTURE;
            case LILAC -> VARenderers.LILAC_BED_TEXTURE;
        };
    }

    @Environment(EnvType.CLIENT)
    public SpriteIdentifier getShulkerBoxTexture() {
        return switch (this) {
            case CHARTREUSE -> VARenderers.CHARTREUSE_SHULKER_BOX;
            case MAROON -> VARenderers.MAROON_SHULKER_BOX;
            case INDIGO -> VARenderers.INDIGO_SHULKER_BOX;
            case PLUM -> VARenderers.PLUM_SHULKER_BOX;
            case VIRIDIAN -> VARenderers.VIRIDIAN_SHULKER_BOX;
            case TAN -> VARenderers.TAN_SHULKER_BOX;
            case SINOPIA -> VARenderers.SINOPIA_SHULKER_BOX;
            case LILAC -> VARenderers.LILAC_SHULKER_BOX;
        };
    }
}
