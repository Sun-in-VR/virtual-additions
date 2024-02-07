package com.github.suninvr.virtualadditions.block.enums;

import com.github.suninvr.virtualadditions.VirtualAdditions;
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

    final SpriteIdentifier CHARTREUSE_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/chartreuse"));
    final SpriteIdentifier MAROON_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/maroon"));
    final SpriteIdentifier INDIGO_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/indigo"));
    final SpriteIdentifier PLUM_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/plum"));
    final SpriteIdentifier VIRIDIAN_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/viridian"));
    final SpriteIdentifier TAN_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/tan"));
    final SpriteIdentifier SINOPIA_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/sinopia"));
    final SpriteIdentifier LILAC_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/lilac"));

    final SpriteIdentifier CHARTREUSE_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/chartreuse"));
    final SpriteIdentifier MAROON_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/maroon"));
    final SpriteIdentifier INDIGO_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/indigo"));
    final SpriteIdentifier PLUM_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/plum"));
    final SpriteIdentifier VIRIDIAN_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/viridian"));
    final SpriteIdentifier TAN_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/tan"));
    final SpriteIdentifier SINOPIA_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/sinopia"));
    final SpriteIdentifier LILAC_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/lilac"));

    @Override
    public String asString() {
        return null;
    }

    public SpriteIdentifier getBedTexture() {
        return switch (this) {
            case CHARTREUSE -> CHARTREUSE_BED_TEXTURE;
            case MAROON -> MAROON_BED_TEXTURE;
            case INDIGO -> INDIGO_BED_TEXTURE;
            case PLUM -> PLUM_BED_TEXTURE;
            case VIRIDIAN -> VIRIDIAN_BED_TEXTURE;
            case TAN -> TAN_BED_TEXTURE;
            case SINOPIA -> SINOPIA_BED_TEXTURE;
            case LILAC -> LILAC_BED_TEXTURE;
        };
    }

    public SpriteIdentifier getShulkerBoxTexture() {
        return switch (this) {
            case CHARTREUSE -> CHARTREUSE_SHULKER_BOX;
            case MAROON -> MAROON_SHULKER_BOX;
            case INDIGO -> INDIGO_SHULKER_BOX;
            case PLUM -> PLUM_SHULKER_BOX;
            case VIRIDIAN -> VIRIDIAN_SHULKER_BOX;
            case TAN -> TAN_SHULKER_BOX;
            case SINOPIA -> SINOPIA_SHULKER_BOX;
            case LILAC -> LILAC_SHULKER_BOX;
        };
    }
}
