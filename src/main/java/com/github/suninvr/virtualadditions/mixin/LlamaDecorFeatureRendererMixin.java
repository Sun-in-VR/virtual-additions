package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.LlamaDecorFeatureRenderer;
import net.minecraft.client.render.entity.model.LlamaEntityModel;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Arrays;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@Mixin(LlamaDecorFeatureRenderer.class)
public abstract class LlamaDecorFeatureRendererMixin extends FeatureRenderer<LlamaEntity, LlamaEntityModel<LlamaEntity>> {
    @Final
    @Shadow @Mutable private static Identifier[] LLAMA_DECOR;

    public LlamaDecorFeatureRendererMixin(FeatureRendererContext<LlamaEntity, LlamaEntityModel<LlamaEntity>> context) {
        super(context);
    }

    private static final Identifier[] CUSTOM_LLAMA_DECOR = new Identifier[]{ idOf("textures/entity/llama/decor/chartreuse.png"), idOf("textures/entity/llama/decor/maroon.png"), idOf("textures/entity/llama/decor/indigo.png"), idOf("textures/entity/llama/decor/plum.png"), idOf("textures/entity/llama/decor/viridian.png"), idOf("textures/entity/llama/decor/tan.png"), idOf("textures/entity/llama/decor/sinopia.png"), idOf("textures/entity/llama/decor/lilac.png") };

    static {
        ArrayList<Identifier> newLlamaDecor = new ArrayList<>(Arrays.asList(LLAMA_DECOR));
        newLlamaDecor.addAll(Arrays.asList(CUSTOM_LLAMA_DECOR));
        LLAMA_DECOR = newLlamaDecor.toArray(new Identifier[0]);
    }

}
