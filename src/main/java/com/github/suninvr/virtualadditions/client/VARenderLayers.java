package com.github.suninvr.virtualadditions.client;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class VARenderLayers {
    public static void init() {

    }

    private static final Function<Identifier, RenderLayer> MINI_PORTAL = Util.memoize(
            texture -> {
                RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                        .texture(new RenderPhase.Texture(texture))
                        .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                        .build(true);
                return RenderLayer.of("virtual_additions_mini_portal", 1536, false, false, RenderPipelines.OPAQUE_PARTICLE, multiPhaseParameters);
            }
    );

    private static final Function<Identifier, RenderLayer> MINI_PORTAL_TRANSLUCENT = Util.memoize(
            texture -> {
                RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                        .texture(new RenderPhase.Texture(texture))
                        .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                        .build(true);
                return RenderLayer.of("virtual_additions_mini_portal_translucent", 1536, false, true, RenderPipelines.TRANSLUCENT_PARTICLE, multiPhaseParameters);
            }
    );


    public static RenderLayer getMiniPortal(Identifier texture, boolean translucent) {
        return translucent ? MINI_PORTAL_TRANSLUCENT.apply(texture) : MINI_PORTAL.apply(texture);
    }
}
