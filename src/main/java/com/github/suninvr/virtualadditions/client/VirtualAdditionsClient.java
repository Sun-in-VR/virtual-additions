package com.github.suninvr.virtualadditions.client;

import com.github.suninvr.virtualadditions.client.particle.*;
import com.github.suninvr.virtualadditions.client.screen.ColoringStationScreen;
import com.github.suninvr.virtualadditions.client.screen.EntanglementDriveScreen;
import com.github.suninvr.virtualadditions.client.screen.RemoteNotifierScreen;
import com.github.suninvr.virtualadditions.client.toast.RemoteNotifierToast;
import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.registry.VAPackets;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.github.suninvr.virtualadditions.registry.VAMenus;
import com.github.suninvr.virtualadditions.screen.ColoringStationMenu;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.FallingLeavesParticle;
import net.minecraft.client.particle.FireflyParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class VirtualAdditionsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        VARenderLayers.init();
        VARenderers.init();

        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.ACID_SPLASH_EMITTER, AcidSplashEmitterParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.ACID_SPLASH, SplashParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.GREENCAP_SPORE, GreencapSporeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SCRAPE_STEEL, SteelScrapeFactory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SOULBLOOM_LEAVES, FallingLeavesParticle.CherryProvider::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.INTERFERENCE, PowerParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SPECTRAL_POWER, PowerParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.COLORFUL_POWER, PowerParticleFactory.Color::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SPECTRAL_FLAME, FlameParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SMALL_SPECTRAL_FLAME, FlameParticle.SmallFlameProvider::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SPRING_LOTUS_POLLEN, SpringLotusPollenParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SOUL_FIREFLY, FireflyParticle.FireflyProvider::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.STATIC_SCULK_CHARGE_POP, StaticSculkChargePopParticleFactory::new);

        MenuScreens.register(VAMenus.ENTANGLEMENT_DRIVE, EntanglementDriveScreen::new);
        MenuScreens.register(VAMenus.COLORING_STATION, ColoringStationScreen::new);
        MenuScreens.register(VAMenus.REMOTE_NOTIFIER, RemoteNotifierScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(VAPackets.REMOTE_NOTIFIER_S2C_ID, ((payload, context) -> {
            if (context != null) context.client().getToastManager().addToast(new RemoteNotifierToast(payload.STACK(), Component.nullToEmpty(payload.TEXT())));
        }));

        ClientPlayNetworking.registerGlobalReceiver(VAPackets.SET_REMOTE_NOTIFIER_MESSAGE_ID, (payload, context) -> {
           RemoteNotifierScreen.initializerText = payload.TEXT();
        });

        ClientPlayNetworking.registerGlobalReceiver(VAPackets.COLORING_STATION_S2C_ID, (payload, context) -> {
            if (context != null) {
                if (context.client().screen instanceof ColoringStationScreen coloringStationScreen) {
                    coloringStationScreen.getMenu().setRecipeData(payload.list());
                } else {
                    ColoringStationMenu.recipeDataOnLoad = payload.list();
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(VAPackets.PLAYER_PROJECTION_S2C_ID, (payload, context) -> {
            Level world = context.player().level();
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
