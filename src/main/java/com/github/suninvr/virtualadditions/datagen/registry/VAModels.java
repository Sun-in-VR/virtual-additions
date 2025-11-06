package com.github.suninvr.virtualadditions.datagen.registry;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;

import java.util.Optional;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAModels {
    public static final ModelTemplate HANDHELD_TWO_LAYERS = ModelTemplates.createItem("handheld", TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate HALBERD_IN_HAND = item("halberd_in_hand", TextureSlot.LAYER0);
    public static final ModelTemplate HALBERD_IN_HAND_TWO_LAYERS = item("halberd_in_hand", TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate SPEAR_IN_HAND_TWO_LAYERS = item(Identifier.withDefaultNamespace("spear_in_hand"), TextureSlot.LAYER0, TextureSlot.LAYER1);

    private static ModelTemplate item(String parent, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(Optional.of(idOf(parent).withPrefix("item/")), Optional.empty(), requiredTextureKeys);
    }
    private static ModelTemplate item(Identifier parent, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(Optional.of(parent.withPrefix("item/")), Optional.empty(), requiredTextureKeys);
    }
}
