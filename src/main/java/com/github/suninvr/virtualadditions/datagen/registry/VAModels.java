package com.github.suninvr.virtualadditions.datagen.registry;

import net.minecraft.client.data.Model;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAModels {
    public static final Model HANDHELD_TWO_LAYERS = Models.item("handheld", TextureKey.LAYER0, TextureKey.LAYER1);
    public static final Model HALBERD_IN_HAND = item("halberd_in_hand", TextureKey.LAYER0);
    public static final Model HALBERD_IN_USE = item("halberd_in_use", TextureKey.LAYER0);
    public static final Model HALBERD_IN_HAND_TWO_LAYERS = item("halberd_in_hand", TextureKey.LAYER0, TextureKey.LAYER1);
    public static final Model HALBERD_IN_USE_TWO_LAYERS = item("halberd_in_use", TextureKey.LAYER0, TextureKey.LAYER1);
    public static final Model SPEAR_IN_HAND_TWO_LAYERS = item(Identifier.ofVanilla("spear_in_hand"), TextureKey.LAYER0, TextureKey.LAYER1);

    private static Model item(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(idOf(parent).withPrefixedPath("item/")), Optional.empty(), requiredTextureKeys);
    }
    private static Model item(Identifier parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(parent.withPrefixedPath("item/")), Optional.empty(), requiredTextureKeys);
    }
}
