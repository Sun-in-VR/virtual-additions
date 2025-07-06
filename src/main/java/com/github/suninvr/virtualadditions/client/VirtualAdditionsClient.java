package com.github.suninvr.virtualadditions.client;

import com.github.suninvr.virtualadditions.client.particle.*;
import com.github.suninvr.virtualadditions.client.screen.ColoringStationScreen;
import com.github.suninvr.virtualadditions.client.screen.EntanglementDriveScreen;
import com.github.suninvr.virtualadditions.client.toast.RemoteNotifierToast;
import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.registry.VAPackets;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.github.suninvr.virtualadditions.registry.VAScreenHandler;
import com.github.suninvr.virtualadditions.screen.ColoringStationScreenHandler;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.particle.FireflyParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.LeavesParticle;
import net.minecraft.client.particle.WaterSplashParticle;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.io.File;

public class VirtualAdditionsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        VARenderers.init();

        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.ACID_SPLASH_EMITTER, AcidSplashEmitterParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.ACID_SPLASH, WaterSplashParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.GREENCAP_SPORE, GreencapSporeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SCRAPE_STEEL, SteelScrapeFactory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SOULBLOOM_LEAVES, LeavesParticle.CherryLeavesFactory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.INTERFERENCE, PowerParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SPECTRAL_POWER, PowerParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.IOLITE_ANCHOR_RING, IoliteRingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.IOLITE_TETHER_RING, IoliteRingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SPECTRAL_FLAME, FlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SMALL_SPECTRAL_FLAME, FlameParticle.SmallFactory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SPRING_LOTUS_POLLEN, SpringLotusPollenParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SOUL_FIREFLY, FireflyParticle.Factory::new);

        HandledScreens.register(VAScreenHandler.ENTANGLEMENT_DRIVE, EntanglementDriveScreen::new);
        HandledScreens.register(VAScreenHandler.COLORING_STATION, ColoringStationScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(VAPackets.REMOTE_NOTIFIER_S2C_ID, ((payload, context) -> {
            if (context != null) context.client().getToastManager().add(new RemoteNotifierToast(payload.STACK(), Text.of(payload.TEXT())));
        }));

        ClientPlayNetworking.registerGlobalReceiver(VAPackets.COLORING_STATION_S2C_ID, (payload, context) -> {
            if (context != null) {
                if (context.client().currentScreen instanceof ColoringStationScreen coloringStationScreen) {
                    coloringStationScreen.getScreenHandler().setRecipeData(payload.list());
                } else {
                    ColoringStationScreenHandler.recipeDataOnLoad = payload.list();
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(VAPackets.PLAYER_PROJECTION_S2C_ID, (payload, context) -> {
            World world = context.player().getWorld();
            if (world != null) {
                Entity entity = world.getEntity(payload.getEntityId());
                if (entity instanceof PlayerProjectionEntity playerProjectionEntity) {
                    if (payload.isRemoved()) {
                        playerProjectionEntity.remove(Entity.RemovalReason.DISCARDED);
                        context.client().setCameraEntity(context.client().player);
                    } else {
                        playerProjectionEntity.setClientPlayer(context.client().player);
                        context.client().setCameraEntity(playerProjectionEntity);
                    }
                } else {
                    context.client().setCameraEntity(context.client().player);
                }
            }
        });
    }

}
