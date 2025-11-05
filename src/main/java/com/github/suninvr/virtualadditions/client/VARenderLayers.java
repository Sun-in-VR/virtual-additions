package com.github.suninvr.virtualadditions.client;

import net.minecraft.Util;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class VARenderLayers {
    public static void init() {

    }

    private static final Function<ResourceLocation, RenderType> MINI_PORTAL = Util.memoize(
            texture -> {
                RenderType.CompositeState multiPhaseParameters = RenderType.CompositeState.builder()
                        .setTextureState(new RenderStateShard.TextureStateShard(texture))
                        .setLightmapState(RenderStateShard.LIGHTMAP)
                        .createCompositeState(true);
                return RenderType.create("virtual_additions_mini_portal", 1536, false, false, RenderPipelines.OPAQUE_PARTICLE, multiPhaseParameters);
            }
    );

    private static final Function<ResourceLocation, RenderType> MINI_PORTAL_TRANSLUCENT = Util.memoize(
            texture -> {
                RenderType.CompositeState multiPhaseParameters = RenderType.CompositeState.builder()
                        .setTextureState(new RenderStateShard.TextureStateShard(texture))
                        .setLightmapState(RenderStateShard.LIGHTMAP)
                        .createCompositeState(true);
                return RenderType.create("virtual_additions_mini_portal_translucent", 1536, false, true, RenderPipelines.TRANSLUCENT_PARTICLE, multiPhaseParameters);
            }
    );


    public static RenderType getMiniPortal(ResourceLocation texture, boolean translucent) {
        return translucent ? MINI_PORTAL_TRANSLUCENT.apply(texture) : MINI_PORTAL.apply(texture);
    }
}
