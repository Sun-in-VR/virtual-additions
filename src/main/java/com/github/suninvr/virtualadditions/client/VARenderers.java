package com.github.suninvr.virtualadditions.client;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.RedstoneBridgeBlock;
import com.github.suninvr.virtualadditions.client.render.block.CustomBedBlockEntityRenderer;
import com.github.suninvr.virtualadditions.client.render.block.CustomShulkerBoxBlockEntityRenderer;
import com.github.suninvr.virtualadditions.client.render.block.MiniPortalBlockEntityRenderer;
import com.github.suninvr.virtualadditions.client.render.entity.*;
import com.github.suninvr.virtualadditions.client.render.fog.PlayerProjectionPhasingFogModifier;
import com.github.suninvr.virtualadditions.registry.*;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.fog.FogModifier;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;

import java.util.ArrayList;
import java.util.List;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARenderers {
    public static EntityModelLayer LUMWASP_LAYER = new EntityModelLayer(Identifier.of("virtual_additions", "lumwasp"), "main");
    public static EntityModelLayer SPECTRE_LAYER = new EntityModelLayer(Identifier.of("virtual_additions", "spectre"), "main");
    public static EntityModelLayer SPECTRE_OUTER_LAYER = new EntityModelLayer(Identifier.of("virtual_additions", "spectre_outer"), "main");
    public static EntityModelLayer SOULBLOOM_BOAT = new EntityModelLayer(Identifier.of("virtual_additions", "boat/soulbloom"), "main");
    public static EntityModelLayer SOULBLOOM_CHEST_BOAT = new EntityModelLayer(Identifier.of("virtual_additions", "chest_boat/soulbloom"), "main");
    public static EntityModelLayer CUSTOM_BED_FOOT_LAYER = new EntityModelLayer(idOf("bed_foot"), "main");
    public static EntityModelLayer CUSTOM_BED_HEAD_LAYER = new EntityModelLayer(idOf("bed_head"), "main");
    public static final SpriteIdentifier CHARTREUSE_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/shulker_chartreuse"));
    public static final SpriteIdentifier MAROON_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/shulker_maroon"));
    public static final SpriteIdentifier INDIGO_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/shulker_indigo"));
    public static final SpriteIdentifier PLUM_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/shulker_plum"));
    public static final SpriteIdentifier VIRIDIAN_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/shulker_viridian"));
    public static final SpriteIdentifier TAN_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/shulker_tan"));
    public static final SpriteIdentifier SINOPIA_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/shulker_sinopia"));
    public static final SpriteIdentifier LILAC_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/shulker_lilac"));
    public static final SpriteIdentifier CHARTREUSE_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/chartreuse"));
    public static final SpriteIdentifier MAROON_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/maroon"));
    public static final SpriteIdentifier INDIGO_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/indigo"));
    public static final SpriteIdentifier PLUM_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/plum"));
    public static final SpriteIdentifier VIRIDIAN_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/viridian"));
    public static final SpriteIdentifier TAN_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/tan"));
    public static final SpriteIdentifier SINOPIA_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/sinopia"));
    public static final SpriteIdentifier LILAC_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/lilac"));

    public static void init() {
        initBlockRenderLayers();
        initEntityRenderers();
        initBlockEntityRenderers();
        initFluidRenderers();
        initColorProviders();
        initFogModifiers();
    }

    private static void initBlockRenderLayers() {
        BlockRenderLayerMap.putFluids(BlockRenderLayer.TRANSLUCENT,
                VAFluids.ACID,
                VAFluids.FLOWING_ACID
        );

        BlockRenderLayerMap.putBlocks(BlockRenderLayer.TRANSLUCENT,
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
                VABlocks.VIRIDIAN_STAINED_GLASS,
                VABlocks.VIRIDIAN_STAINED_GLASS_PANE,
                VABlocks.TAN_STAINED_GLASS,
                VABlocks.TAN_STAINED_GLASS_PANE,
                VABlocks.SINOPIA_STAINED_GLASS,
                VABlocks.SINOPIA_STAINED_GLASS_PANE,
                VABlocks.LILAC_STAINED_GLASS,
                VABlocks.LILAC_STAINED_GLASS_PANE
        );

        BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                VABlocks.CLIMBING_ROPE_ANCHOR,
                VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR,
                VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR,
                VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR,
                VABlocks.WAXED_CLIMBING_ROPE_ANCHOR,
                VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR,
                VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR,
                VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR,
                VABlocks.CLIMBING_ROPE,
                VABlocks.SOULBLOOM_DOOR,
                VABlocks.SOULBLOOM_TRAPDOOR,
                VABlocks.SOULBLOOM_SAPLING,
                VABlocks.POTTED_SOULBLOOM_SAPLING,
                VABlocks.NECROTIC_ROOTS,
                VABlocks.POTTED_NECROTIC_ROOTS,
                VABlocks.BONE_LITTER,
                VABlocks.BONE_PILE,
                VABlocks.WITHERED_DOOR,
                VABlocks.WITHERED_TRAPDOOR,
                VABlocks.WITHERED_SAPLING,
                VABlocks.POTTED_WITHERED_SAPLING,
                VABlocks.SPECTRAL_FIRE,
                VABlocks.SPECTRAL_LANTERN,
                VABlocks.BLUE_PETALS,
                VABlocks.BALLOON_BULB,
                VABlocks.BALLOON_BULB_PLANT,
                VABlocks.BALLOON_BULB_BUD,
                VABlocks.ROCK_SALT_CRYSTAL,
                VABlocks.COTTON,
                VABlocks.CORN_CROP,
                VABlocks.TOMATO,
                VABlocks.CABBAGE,
                VABlocks.GLOWING_SILK,
                VABlocks.FRAYED_SILK,
                VABlocks.TALL_GREENCAP_MUSHROOMS,
                VABlocks.GREENCAP_MUSHROOM,
                VABlocks.POTTED_GREENCAP_MUSHROOM,
                VABlocks.SMALL_SPRING_LOTUS,
                VABlocks.POTTED_SMALL_SPRING_LOTUS,
                VABlocks.SPRING_LOTUS,
                VABlocks.SOUL_SPROUT,
                VABlocks.POTTED_SOUL_SPROUT,
                VABlocks.WISDOM_BERRY
        );

        BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT_MIPPED,
                VABlocks.SOULBLOOM_LEAVES,
                VABlocks.SOULBLOOM_HEDGE,
                VABlocks.WITHERED_LEAVES,
                VABlocks.WITHERED_HEDGE,
                VABlocks.OAK_HEDGE,
                VABlocks.SPRUCE_HEDGE,
                VABlocks.BIRCH_HEDGE,
                VABlocks.JUNGLE_HEDGE,
                VABlocks.ACACIA_HEDGE,
                VABlocks.DARK_OAK_HEDGE,
                VABlocks.PALE_OAK_HEDGE,
                VABlocks.MANGROVE_HEDGE,
                VABlocks.CHERRY_HEDGE,
                VABlocks.AZALEA_HEDGE,
                VABlocks.FLOWERING_AZALEA_HEDGE,
                VABlocks.STEEL_DOOR,
                VABlocks.EXPOSED_STEEL_DOOR,
                VABlocks.WEATHERED_STEEL_DOOR,
                VABlocks.OXIDIZED_STEEL_DOOR,
                VABlocks.WAXED_STEEL_DOOR,
                VABlocks.WAXED_EXPOSED_STEEL_DOOR,
                VABlocks.WAXED_WEATHERED_STEEL_DOOR,
                VABlocks.WAXED_OXIDIZED_STEEL_DOOR,
                VABlocks.STEEL_TRAPDOOR,
                VABlocks.EXPOSED_STEEL_TRAPDOOR,
                VABlocks.WEATHERED_STEEL_TRAPDOOR,
                VABlocks.OXIDIZED_STEEL_TRAPDOOR,
                VABlocks.WAXED_STEEL_TRAPDOOR,
                VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR,
                VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR,
                VABlocks.WAXED_OXIDIZED_STEEL_TRAPDOOR,
                VABlocks.STEEL_TRAPDOOR,
                VABlocks.REDSTONE_BRIDGE,
                VABlocks.STEEL_GRATE,
                VABlocks.EXPOSED_STEEL_GRATE,
                VABlocks.WEATHERED_STEEL_GRATE,
                VABlocks.OXIDIZED_STEEL_GRATE,
                VABlocks.WAXED_STEEL_GRATE,
                VABlocks.WAXED_EXPOSED_STEEL_GRATE,
                VABlocks.WAXED_WEATHERED_STEEL_GRATE,
                VABlocks.WAXED_OXIDIZED_STEEL_GRATE
        );
    }

    private static void initEntityRenderers() {
        EntityModelLayerRegistry.registerModelLayer(LUMWASP_LAYER, LumwaspEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SPECTRE_LAYER, SpectreEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SOULBLOOM_BOAT, BoatEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SOULBLOOM_CHEST_BOAT, BoatEntityModel::getChestTexturedModelData);
        EntityRendererRegistry.register(VAEntityType.CLIMBING_ROPE, ClimbingRopeEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.STEEL_BOMB, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.TOMATO, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.ACID_SPIT, AcidSpitEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.SPECTRAL_BOLT, SpectralBoltEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.LUMWASP, LumwaspEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.SPECTRE, SpectreEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.SALINE, SalineEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.PLAYER_PROJECTION, PlayerProjectionEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.LIGHTNING_BOTTLE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.SOULBLOOM_BOAT, context -> new BoatEntityRenderer(context, SOULBLOOM_BOAT));
        EntityRendererRegistry.register(VAEntityType.SOULBLOOM_CHEST_BOAT, context -> new BoatEntityRenderer(context, SOULBLOOM_CHEST_BOAT));
    }

    private static void initBlockEntityRenderers() {

        EntityModelLayerRegistry.registerModelLayer(CUSTOM_BED_FOOT_LAYER, CustomBedBlockEntityRenderer.FOOT_MODEL_PROVIDER);
        EntityModelLayerRegistry.registerModelLayer(CUSTOM_BED_HEAD_LAYER, CustomBedBlockEntityRenderer.HEAD_MODEL_PROVIDER);

        BlockEntityRendererFactories.register( VABlockEntityType.MINI_PORTAL, MiniPortalBlockEntityRenderer::new );

        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_SIGN, SignBlockEntityRenderer::new );
        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_HANGING_SIGN, HangingSignBlockEntityRenderer::new );
        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_BED, CustomBedBlockEntityRenderer::new );
        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_SHULKER_BOX, CustomShulkerBoxBlockEntityRenderer::new );
        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_BANNER, BannerBlockEntityRenderer::new );

        TexturedRenderLayers.SIGN_TYPE_TEXTURES.put(VABlocks.SOULBLOOM_WOODTYPE, new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, idOf("entity/signs/soulbloom")));
        TexturedRenderLayers.HANGING_SIGN_TYPE_TEXTURES.put(VABlocks.SOULBLOOM_WOODTYPE, new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, idOf("entity/signs/hanging/soulbloom")));
        TexturedRenderLayers.SIGN_TYPE_TEXTURES.put(VABlocks.WITHERED_WOODTYPE, new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, idOf("entity/signs/withered")));
        TexturedRenderLayers.HANGING_SIGN_TYPE_TEXTURES.put(VABlocks.WITHERED_WOODTYPE, new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, idOf("entity/signs/hanging/withered")));

    }

    private static void  initFluidRenderers() {
        FluidRenderHandlerRegistry.INSTANCE.register(VAFluids.ACID, VAFluids.FLOWING_ACID, new SimpleFluidRenderHandler(
                Identifier.of("minecraft:block/water_still"),
                Identifier.of("minecraft:block/water_flow"),
                0x00e076
        ));
    }

    private static void initColorProviders() {
        ColorProviderRegistry.BLOCK.register( ((state, world, pos, tintIndex) -> world != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.DEFAULT),
                VABlocks.OAK_HEDGE,
                VABlocks.JUNGLE_HEDGE,
                VABlocks.ACACIA_HEDGE,
                VABlocks.DARK_OAK_HEDGE,
                VABlocks.MANGROVE_HEDGE
        );
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> FoliageColors.SPRUCE, VABlocks.SPRUCE_HEDGE);
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> FoliageColors.BIRCH, VABlocks.BIRCH_HEDGE);
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> 0x00e076, VABlocks.ACID);
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> tintIndex <= 0 ? -1 : RedstoneWireBlock.getWireColor(state.get(RedstoneBridgeBlock.POWER)), VABlocks.REDSTONE_BRIDGE);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (tintIndex != 0) {
                return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getDefaultColor();
            } else {
                return -1;
            }
        }, VABlocks.BLUE_PETALS);
    }

    private static void initFogModifiers() {
        FogRenderer.FOG_MODIFIERS.addFirst(new PlayerProjectionPhasingFogModifier());
    }
}
