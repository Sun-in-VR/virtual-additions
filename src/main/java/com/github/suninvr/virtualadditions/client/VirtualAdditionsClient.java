package com.github.suninvr.virtualadditions.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.resource.ResourceManager;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import com.github.suninvr.virtualadditions.client.render.entity.ClimbingRopeEntityRenderer;
import com.github.suninvr.virtualadditions.registry.*;
import com.github.suninvr.virtualadditions.screen.WorkbenchScreen;

import java.util.function.Function;

import static net.minecraft.resource.ResourceType.CLIENT_RESOURCES;
import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VirtualAdditionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                VABlocks.CLIMBING_ROPE_ANCHOR,
                VABlocks.VENOMOUS_BOIL,
                VABlocks.TOXIC_ROOTS,
                VABlocks.LUMINOUS_CRYSTAL
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                VABlocks.ICE_SHEET,
                VABlocks.LUMINOUS_CRYSTAL_BLOCK
                );
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                    VAFluids.STILL_ACID,
                    VAFluids.FLOWING_ACID
                );

        EntityRendererRegistry.register(VAEntityType.CLIMBING_ROPE, ClimbingRopeEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.STEEL_BOMB, FlyingItemEntityRenderer::new);

        ColorProviderRegistry.ITEM.register( ((stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtil.getColor(stack)), VAItems.APPLICABLE_POTION_ITEM );

        FabricModelPredicateProviderRegistry.register(Items.CROSSBOW, idOf("climbing_rope"), (itemStack, clientWorld, livingEntity, a) -> {
            if(!itemStack.isOf(Items.CROSSBOW)) return 0.0F;
            return CrossbowItem.hasProjectile(itemStack, VAItems.CLIMBING_ROPE) ? 1.0F : 0.0F;
        });

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> registry.register(idOf("particle/icy_fog"))));
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.ICY_FOG, WaterSuspendParticle.UnderwaterFactory::new);

        ScreenRegistry.register(VAScreenHandlers.WORKBENCH_SCREEN_HANDLER, WorkbenchScreen::new);

        setupFluidRendering(VAFluids.STILL_ACID, VAFluids.FLOWING_ACID, idOf("acid"), 0x95be21);//0x549300);
    }

    public static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId, final int color) {
        final Identifier stillSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_still");
        final Identifier flowingSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_flow");

        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(stillSpriteId);
            registry.register(flowingSpriteId);
        });

        final Identifier fluidId = Registry.FLUID.getId(still);
        final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

        final Sprite[] fluidSprites = { null, null };

        ResourceManagerHelper.get(CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return listenerId;
            }

            /**
             * Get the sprites from the block atlas when resources are reloaded
             */
            @Override
            public void reload(ResourceManager resourceManager) {
                final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
                fluidSprites[0] = atlas.apply(stillSpriteId);
                fluidSprites[1] = atlas.apply(flowingSpriteId);
            }
        });

        // The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
        final FluidRenderHandler renderHandler = new FluidRenderHandler()
        {
            @Override
            public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
                return color;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);

    }

}
