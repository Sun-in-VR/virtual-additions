package com.github.suninvr.virtualadditions.client;

import com.github.suninvr.virtualadditions.block.RedstoneBridgeBlock;
import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.client.particle.AcidSplashEmitterParticle;
import com.github.suninvr.virtualadditions.client.particle.GreencapSporeParticle;
import com.github.suninvr.virtualadditions.client.particle.IoliteRingParticle;
import com.github.suninvr.virtualadditions.client.render.entity.*;
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

    private static final UUID nullId = UUID.fromString("0-0-0-0-0");

    public static EntityModelLayer LUMWASP_ENTITY_LAYER = new EntityModelLayer(new Identifier("virtual_additions", "lumwasp"), "main");
    public static EntityModelLayer LYFT_ENTITY_LAYER = new EntityModelLayer(new Identifier("virtual_additions", "lyft"), "main");
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                VAFluids.ACID,
                VAFluids.FLOWING_ACID
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                VABlocks.WEBBED_SILK,
                VABlocks.ACID_BLOCK,
                VABlocks.CHARTREUSE_STAINED_GLASS,
                VABlocks.CHARTREUSE_STAINED_GLASS_PANE,
                VABlocks.MAROON_STAINED_GLASS,
                VABlocks.MAROON_STAINED_GLASS_PANE,
                VABlocks.INDIGO_STAINED_GLASS,
                VABlocks.INDIGO_STAINED_GLASS_PANE,
                VABlocks.PLUM_STAINED_GLASS,
                VABlocks.PLUM_STAINED_GLASS_PANE,
                VABlocks.COLD_GREEN_STAINED_GLASS,
                VABlocks.COLD_GREEN_STAINED_GLASS_PANE,
                VABlocks.TAN_STAINED_GLASS,
                VABlocks.TAN_STAINED_GLASS_PANE
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                VABlocks.CLIMBING_ROPE_ANCHOR,
                VABlocks.CLIMBING_ROPE,
                VABlocks.AEROBLOOM_DOOR,
                VABlocks.AEROBLOOM_TRAPDOOR,
                VABlocks.AEROBLOOM_SAPLING,
                VABlocks.BALLOON_BULB,
                VABlocks.BALLOON_BULB_PLANT,
                VABlocks.BALLOON_BULB_BUD,
                VABlocks.RED_GLIMMER_CRYSTAL,
                VABlocks.GREEN_GLIMMER_CRYSTAL,
                VABlocks.BLUE_GLIMMER_CRYSTAL,
                VABlocks.COTTON,
                VABlocks.CORN_CROP,
                VABlocks.GLOWING_SILK,
                VABlocks.FRAYED_SILK,
                VABlocks.TALL_GREENCAP_MUSHROOMS,
                VABlocks.GREENCAP_MUSHROOM
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                VABlocks.GRASSY_FLOATROCK,
                VABlocks.AEROBLOOM_LEAVES,
                VABlocks.AEROBLOOM_HEDGE,
                VABlocks.OAK_HEDGE,
                VABlocks.SPRUCE_HEDGE,
                VABlocks.BIRCH_HEDGE,
                VABlocks.JUNGLE_HEDGE,
                VABlocks.ACACIA_HEDGE,
                VABlocks.DARK_OAK_HEDGE,
                VABlocks.MANGROVE_HEDGE,
                VABlocks.CHERRY_HEDGE,
                VABlocks.AZALEA_HEDGE,
                VABlocks.FLOWERING_AZALEA_HEDGE,
                VABlocks.STEEL_DOOR,
                VABlocks.STEEL_TRAPDOOR,
                VABlocks.REDSTONE_BRIDGE
        );

        EntityRendererRegistry.register(VAEntityType.CLIMBING_ROPE, ClimbingRopeEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.STEEL_BOMB, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.ACID_SPIT, AcidSpitEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.LUMWASP, LumwaspEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.LYFT, LyftEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(LUMWASP_ENTITY_LAYER, LumwaspEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(LYFT_ENTITY_LAYER, LyftEntityModel::getTexturedModelData);

        ColorProviderRegistry.BLOCK.register( ((state, world, pos, tintIndex) -> world != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor()),
                VABlocks.OAK_HEDGE,
                VABlocks.JUNGLE_HEDGE,
                VABlocks.ACACIA_HEDGE,
                VABlocks.DARK_OAK_HEDGE,
                VABlocks.MANGROVE_HEDGE
                );
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> FoliageColors.getSpruceColor(), VABlocks.SPRUCE_HEDGE);
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> FoliageColors.getBirchColor(), VABlocks.BIRCH_HEDGE);
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> 0x00e076, VABlocks.ACID);
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> tintIndex <= 0 ? -1 : RedstoneWireBlock.getWireColor(state.get(RedstoneBridgeBlock.POWER)), VABlocks.REDSTONE_BRIDGE);
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> tintIndex <= 0 ? -1 : world != null ? BiomeColors.getGrassColor(world, pos) : 5353656, VABlocks.GRASSY_FLOATROCK);

        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtil.getColor(stack), VAItems.APPLICABLE_POTION);
        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> FoliageColors.getDefaultColor(), VAItems.OAK_HEDGE, VAItems.JUNGLE_HEDGE, VAItems.ACACIA_HEDGE, VAItems.DARK_OAK_HEDGE);
        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> tintIndex <= 0 ? -1 : 5353656, VAItems.GRASSY_FLOATROCK);
        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> FoliageColors.getBirchColor(), VAItems.BIRCH_HEDGE);
        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> FoliageColors.getSpruceColor(), VAItems.SPRUCE_HEDGE);
        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> FoliageColors.getMangroveColor(), VAItems.MANGROVE_HEDGE);

        FluidRenderHandlerRegistry.INSTANCE.register(VAFluids.ACID, VAFluids.FLOWING_ACID, new SimpleFluidRenderHandler(
                new Identifier("minecraft:block/water_still"),
                new Identifier("minecraft:block/water_flow"),
                0x00e076
        ));

        ModelPredicateProviderRegistry.register(Items.CROSSBOW, idOf("climbing_rope"), (itemStack, clientWorld, livingEntity, a) -> {
            if(!itemStack.isOf(Items.CROSSBOW)) return 0.0F;
            return CrossbowItem.hasProjectile(itemStack, VAItems.CLIMBING_ROPE) ? 1.0F : 0.0F;
        });

        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.ACID_SPLASH_EMITTER, new AcidSplashEmitterParticle.Factory());
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.ACID_SPLASH, WaterSplashParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.GREENCAP_SPORE, GreencapSporeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.IOLITE_ANCHOR_RING, IoliteRingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VAParticleTypes.IOLITE_TETHER_RING, IoliteRingParticle.Factory::new);

        HandledScreens.register(VAScreenHandler.ENTANGLEMENT_DRIVE, EntanglementDriveScreen::new);
        HandledScreens.register(VAScreenHandler.COLORING_STATION, ColoringStationScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(VAPackets.ENTANGLEMENT_DRIVE_ACTIVE_SLOT_SYNC_ID, ((client, handler, buf, responseSender) -> {
            if (client.currentScreen instanceof EntanglementDriveScreen entanglementDriveScreen) {
                EntanglementDriveScreenHandler screenHandler = entanglementDriveScreen.getScreenHandler();
                screenHandler.setActiveSlotId(buf.readInt());
                screenHandler.setActiveSlotIndex(buf.readInt());
                screenHandler.setPlayerId( buf.readOptional((PacketByteBuf::readUuid)).orElse(nullId) );
                entanglementDriveScreen.updateActiveSlot();
            }
        }));

        ClientPlayNetworking.registerGlobalReceiver(VAPackets.ENTANGLEMENT_DRIVE_SELECTED_SLOT_SYNC_ID, ((client, handler, buf, responseSender) -> {
            if (client.currentScreen instanceof EntanglementDriveScreen entanglementDriveScreen) {
                entanglementDriveScreen.setSelectingSlotPos(buf.readInt(), buf.readInt());
            }
        }));

        ClientPlayNetworking.registerGlobalReceiver(VAPackets.WIND_UPDATE_VELOCITY,  ((client, handler, buf, responseSender) -> {
            if (client.world != null) {
                int id = buf.readInt();
                Entity entity = client.world.getEntityById(id);
                if (entity != null) {
                    double x = buf.readDouble() + entity.getVelocity().x;
                    double y = buf.readDouble() + entity.getVelocity().y;
                    double z = buf.readDouble() + entity.getVelocity().z;
                    entity.setVelocityClient(x, y, z);
                }
            }
        }));

        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_SIGN, SignBlockEntityRenderer::new );
        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_HANGING_SIGN, HangingSignBlockEntityRenderer::new );

        TexturedRenderLayers.SIGN_TYPE_TEXTURES.put(VABlocks.AEROBLOOM_WOODTYPE, new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, idOf("entity/signs/aerobloom")));
        TexturedRenderLayers.HANGING_SIGN_TYPE_TEXTURES.put(VABlocks.AEROBLOOM_WOODTYPE, new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, idOf("entity/signs/hanging/aerobloom")));
    }

}
