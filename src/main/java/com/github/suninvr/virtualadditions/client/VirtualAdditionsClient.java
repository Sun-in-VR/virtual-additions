package com.github.suninvr.virtualadditions.client;

import com.github.suninvr.virtualadditions.block.RedstoneBridgeBlock;
import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.block.entity.CustomBedBlockEntity;
import com.github.suninvr.virtualadditions.client.particle.AcidSplashEmitterParticle;
import com.github.suninvr.virtualadditions.client.particle.GreencapSporeParticle;
import com.github.suninvr.virtualadditions.client.particle.IoliteRingParticle;
import com.github.suninvr.virtualadditions.client.render.block.CustomBedBlockEntityRenderer;
import com.github.suninvr.virtualadditions.client.render.block.CustomShulkerBoxBlockEntityRenderer;
import com.github.suninvr.virtualadditions.client.render.entity.*;
import com.github.suninvr.virtualadditions.client.render.item.CustomBedItemRenderer;
import com.github.suninvr.virtualadditions.client.render.item.CustomShulkerBoxItemRenderer;
import com.github.suninvr.virtualadditions.client.screen.ColoringStationScreen;
import com.github.suninvr.virtualadditions.client.screen.EntanglementDriveScreen;
import com.github.suninvr.virtualadditions.registry.*;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.particle.WaterSplashParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.Entity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;

import java.util.UUID;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VirtualAdditionsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        VARenderers.init();

        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.ACID_SPLASH_EMITTER, new AcidSplashEmitterParticle.Factory());
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.ACID_SPLASH, WaterSplashParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.GREENCAP_SPORE, GreencapSporeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.IOLITE_ANCHOR_RING, IoliteRingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.IOLITE_TETHER_RING, IoliteRingParticle.Factory::new);

        HandledScreens.register(VAScreenHandler.ENTANGLEMENT_DRIVE, EntanglementDriveScreen::new);
        HandledScreens.register(VAScreenHandler.COLORING_STATION, ColoringStationScreen::new);
    }

}
