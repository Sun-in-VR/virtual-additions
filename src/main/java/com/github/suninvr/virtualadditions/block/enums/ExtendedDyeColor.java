package com.github.suninvr.virtualadditions.block.enums;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.StringIdentifiable;

public enum ExtendedDyeColor implements StringIdentifiable {
    CHARTREUSE
    ;

    final SpriteIdentifier CHARTREUSE_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/shulker"));

    @Override
    public String asString() {
        return null;
    }

    public SpriteIdentifier getShulkerBoxTexture() {
        return switch (this) {
            case CHARTREUSE -> CHARTREUSE_SHULKER_BOX;
        };
    }
}
