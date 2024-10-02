package com.github.suninvr.virtualadditions.client;

import com.github.suninvr.virtualadditions.client.particle.*;
import com.github.suninvr.virtualadditions.client.screen.ColoringStationScreen;
import com.github.suninvr.virtualadditions.client.screen.EntanglementDriveScreen;
import com.github.suninvr.virtualadditions.client.toast.RemoteNotifierToast;
import com.github.suninvr.virtualadditions.registry.VAPackets;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.github.suninvr.virtualadditions.registry.VAScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.particle.TrialSpawnerDetectionParticle;
import net.minecraft.client.particle.WaterSplashParticle;
import net.minecraft.text.Text;

public class VirtualAdditionsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        VARenderers.init();

        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.ACID_SPLASH_EMITTER, AcidSplashEmitterParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.ACID_SPLASH, WaterSplashParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.GREENCAP_SPORE, GreencapSporeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.SCRAPE_STEEL, SteelScrapeFactory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.INTERFERENCE, InterferenceFactory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.IOLITE_ANCHOR_RING, IoliteRingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.IOLITE_TETHER_RING, IoliteRingParticle.Factory::new);

        HandledScreens.register(VAScreenHandler.ENTANGLEMENT_DRIVE, EntanglementDriveScreen::new);
        HandledScreens.register(VAScreenHandler.COLORING_STATION, ColoringStationScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(VAPackets.REMOTE_NOTIFIER_S2C_ID, ((payload, context) -> {
            if (context != null) context.client().getToastManager().add(new RemoteNotifierToast(payload.STACK(), Text.of(payload.TEXT())));
        }));
    }

}
