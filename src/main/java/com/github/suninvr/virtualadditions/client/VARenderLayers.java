package com.github.suninvr.virtualadditions.client;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class VARenderLayers {
    public static void init() {

    }

    private static final Function<Identifier, RenderType> MINI_PORTAL = Util.memoize(
            texture -> {
                RenderSetup setup = RenderSetup.builder(RenderPipelines.OPAQUE_PARTICLE)
                        .withTexture("Sampler0", texture)
                        .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING_FORWARD)
                        .createRenderSetup();
                return RenderType.create("virtual_additions_mini_portal", setup);
            }
    );

    private static final Function<Identifier, RenderType> MINI_PORTAL_TRANSLUCENT = Util.memoize(
            texture -> {
                RenderSetup setup = RenderSetup.builder(RenderPipelines.TRANSLUCENT_PARTICLE)
                        .withTexture("Sampler0", texture)
                        .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING_FORWARD)
                        .createRenderSetup();
                return RenderType.create("virtual_additions_mini_portal", setup);
            }
    );


    public static RenderType getMiniPortal(Identifier texture, boolean translucent) {
        return translucent ? MINI_PORTAL_TRANSLUCENT.apply(texture) : MINI_PORTAL.apply(texture);
    }
}
