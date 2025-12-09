package com.github.suninvr.virtualadditions.client;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.RedstoneBridgeBlock;
import com.github.suninvr.virtualadditions.client.render.block.MiniPortalBlockEntityRenderer;
import com.github.suninvr.virtualadditions.client.render.entity.*;
import com.github.suninvr.virtualadditions.client.render.fog.PlayerProjectionPhasingFogModifier;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAFluids;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.RedStoneWireBlock;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARenderers {
    public static ModelLayerLocation LUMWASP_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("virtual_additions", "lumwasp"), "main");
    public static ModelLayerLocation SPECTRE_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("virtual_additions", "spectre"), "main");
    public static ModelLayerLocation PLAYER_PROJECTION_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("virtual_additions", "player_projection"), "main");
    public static ModelLayerLocation SOULBLOOM_BOAT = new ModelLayerLocation(Identifier.fromNamespaceAndPath("virtual_additions", "boat/soulbloom"), "main");
    public static ModelLayerLocation SOULBLOOM_CHEST_BOAT = new ModelLayerLocation(Identifier.fromNamespaceAndPath("virtual_additions", "chest_boat/soulbloom"), "main");
    public static ModelLayerLocation ZEBRANO_BOAT = new ModelLayerLocation(Identifier.fromNamespaceAndPath("virtual_additions", "boat/zebrano"), "main");
    public static ModelLayerLocation ZEBRANO_CHEST_BOAT = new ModelLayerLocation(Identifier.fromNamespaceAndPath("virtual_additions", "chest_boat/zebrano"), "main");
    public static final Material CHARTREUSE_SHULKER_BOX = new Material(Sheets.SHULKER_SHEET, VirtualAdditions.idOf("entity/shulker/shulker_chartreuse"));
    public static final Material MAROON_SHULKER_BOX = new Material(Sheets.SHULKER_SHEET, VirtualAdditions.idOf("entity/shulker/shulker_maroon"));
    public static final Material INDIGO_SHULKER_BOX = new Material(Sheets.SHULKER_SHEET, VirtualAdditions.idOf("entity/shulker/shulker_indigo"));
    public static final Material PLUM_SHULKER_BOX = new Material(Sheets.SHULKER_SHEET, VirtualAdditions.idOf("entity/shulker/shulker_plum"));
    public static final Material VIRIDIAN_SHULKER_BOX = new Material(Sheets.SHULKER_SHEET, VirtualAdditions.idOf("entity/shulker/shulker_viridian"));
    public static final Material TAN_SHULKER_BOX = new Material(Sheets.SHULKER_SHEET, VirtualAdditions.idOf("entity/shulker/shulker_tan"));
    public static final Material SINOPIA_SHULKER_BOX = new Material(Sheets.SHULKER_SHEET, VirtualAdditions.idOf("entity/shulker/shulker_sinopia"));
    public static final Material LILAC_SHULKER_BOX = new Material(Sheets.SHULKER_SHEET, VirtualAdditions.idOf("entity/shulker/shulker_lilac"));
    public static final Material CHARTREUSE_BED_TEXTURE = new Material(Sheets.BED_SHEET, VirtualAdditions.idOf("entity/bed/chartreuse"));
    public static final Material MAROON_BED_TEXTURE = new Material(Sheets.BED_SHEET, VirtualAdditions.idOf("entity/bed/maroon"));
    public static final Material INDIGO_BED_TEXTURE = new Material(Sheets.BED_SHEET, VirtualAdditions.idOf("entity/bed/indigo"));
    public static final Material PLUM_BED_TEXTURE = new Material(Sheets.BED_SHEET, VirtualAdditions.idOf("entity/bed/plum"));
    public static final Material VIRIDIAN_BED_TEXTURE = new Material(Sheets.BED_SHEET, VirtualAdditions.idOf("entity/bed/viridian"));
    public static final Material TAN_BED_TEXTURE = new Material(Sheets.BED_SHEET, VirtualAdditions.idOf("entity/bed/tan"));
    public static final Material SINOPIA_BED_TEXTURE = new Material(Sheets.BED_SHEET, VirtualAdditions.idOf("entity/bed/sinopia"));
    public static final Material LILAC_BED_TEXTURE = new Material(Sheets.BED_SHEET, VirtualAdditions.idOf("entity/bed/lilac"));
    public static final RenderStateDataKey<Boolean> IS_HOLDING_HALBERD = RenderStateDataKey.create(() -> "virtual_additions:is_using_halberd");
    public static final RenderStateDataKey<HumanoidArm> HOLDING_HALBERD_IN = RenderStateDataKey.create(() -> "virtual_additions:holding_halberd_in");
    public static final RenderStateDataKey<Float> HALBERD_READINESS = RenderStateDataKey.create(() -> "virtual_additions:halberd_readiness");
    public static final RenderStateDataKey<Float> TICKS_SINCE_HALBERD_USED = RenderStateDataKey.create(() -> "virtual_additions:ticks_since_halberd_used");
    public static void init() {
        initBlockRenderLayers();
        initEntityRenderers();
        initBlockEntityRenderers();
        initFluidRenderers();
        initColorProviders();
        initFogModifiers();
    }

    private static void initBlockRenderLayers() {
        BlockRenderLayerMap.putFluids(ChunkSectionLayer.TRANSLUCENT,
                VAFluids.ACID,
                VAFluids.FLOWING_ACID
        );

        BlockRenderLayerMap.putBlocks(ChunkSectionLayer.TRANSLUCENT,
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

        BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT,
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
                VABlocks.ZEBRANO_DOOR,
                VABlocks.ZEBRANO_TRAPDOOR,
                VABlocks.ZEBRANO_LEAVES,
                VABlocks.ZEBRANO_HEDGE,
                VABlocks.ZEBRANO_SAPLING,
                VABlocks.POTTED_ZEBRANO_SAPLING,
                VABlocks.ROCK_SALT_CRYSTAL,
                VABlocks.COTTON,
                VABlocks.SOY_CROP,
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
                VABlocks.WISDOM_BERRY,
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
                VABlocks.ZEBRANO_HEDGE,
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
        EntityModelLayerRegistry.registerModelLayer(PLAYER_PROJECTION_LAYER, PlayerProjectionEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SOULBLOOM_BOAT, BoatModel::createBoatModel);
        EntityModelLayerRegistry.registerModelLayer(SOULBLOOM_CHEST_BOAT, BoatModel::createChestBoatModel);
        EntityModelLayerRegistry.registerModelLayer(ZEBRANO_BOAT, BoatModel::createBoatModel);
        EntityModelLayerRegistry.registerModelLayer(ZEBRANO_CHEST_BOAT, BoatModel::createChestBoatModel);
        EntityRendererRegistry.register(VAEntityType.CLIMBING_ROPE, ClimbingRopeEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.STEEL_BOMB, ThrownItemRenderer::new);
        EntityRendererRegistry.register(VAEntityType.TOMATO, ThrownItemRenderer::new);
        EntityRendererRegistry.register(VAEntityType.ACID_SPIT, AcidSpitEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.SPECTRAL_BOLT, SpectralBoltEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.SLUNG_ITEM, ThrownItemRenderer::new);
        EntityRendererRegistry.register(VAEntityType.LUMWASP, LumwaspEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.SPECTRE, SpectreEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.SALINE, SalineEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.PLAYER_PROJECTION, PlayerProjectionEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.LIGHTNING_BOTTLE, ThrownItemRenderer::new);
        EntityRendererRegistry.register(VAEntityType.SOULBLOOM_BOAT, context -> new BoatRenderer(context, SOULBLOOM_BOAT));
        EntityRendererRegistry.register(VAEntityType.SOULBLOOM_CHEST_BOAT, context -> new BoatRenderer(context, SOULBLOOM_CHEST_BOAT));
        EntityRendererRegistry.register(VAEntityType.ZEBRANO_BOAT, context -> new BoatRenderer(context, ZEBRANO_BOAT));
        EntityRendererRegistry.register(VAEntityType.ZEBRANO_CHEST_BOAT, context -> new BoatRenderer(context, ZEBRANO_CHEST_BOAT));
    }

    private static void initBlockEntityRenderers() {

        BlockEntityRenderers.register( VABlockEntityType.MINI_PORTAL, MiniPortalBlockEntityRenderer::new );

        BlockEntityRenderers.register( VABlockEntityType.CUSTOM_SIGN, SignRenderer::new );
        BlockEntityRenderers.register( VABlockEntityType.CUSTOM_HANGING_SIGN, HangingSignRenderer::new );
        BlockEntityRenderers.register( VABlockEntityType.CUSTOM_BED, BedRenderer::new );
        BlockEntityRenderers.register( VABlockEntityType.CUSTOM_SHULKER_BOX, ShulkerBoxRenderer::new );
        BlockEntityRenderers.register( VABlockEntityType.CUSTOM_BANNER, BannerRenderer::new );

        Sheets.SIGN_MATERIALS.put(VABlocks.SOULBLOOM_WOODTYPE, new Material(Sheets.SIGN_SHEET, idOf("entity/signs/soulbloom")));
        Sheets.HANGING_SIGN_MATERIALS.put(VABlocks.SOULBLOOM_WOODTYPE, new Material(Sheets.SIGN_SHEET, idOf("entity/signs/hanging/soulbloom")));
        Sheets.SIGN_MATERIALS.put(VABlocks.WITHERED_WOODTYPE, new Material(Sheets.SIGN_SHEET, idOf("entity/signs/withered")));
        Sheets.HANGING_SIGN_MATERIALS.put(VABlocks.WITHERED_WOODTYPE, new Material(Sheets.SIGN_SHEET, idOf("entity/signs/hanging/withered")));

    }

    private static void  initFluidRenderers() {
        FluidRenderHandlerRegistry.INSTANCE.register(VAFluids.ACID, VAFluids.FLOWING_ACID, new SimpleFluidRenderHandler(
                Identifier.parse("minecraft:block/water_still"),
                Identifier.parse("minecraft:block/water_flow"),
                0x00e076
        ));
    }

    private static void initColorProviders() {
        ColorProviderRegistry.BLOCK.register( ((state, world, pos, tintIndex) -> world != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.FOLIAGE_DEFAULT),
                VABlocks.OAK_HEDGE,
                VABlocks.JUNGLE_HEDGE,
                VABlocks.ACACIA_HEDGE,
                VABlocks.DARK_OAK_HEDGE,
                VABlocks.MANGROVE_HEDGE
        );
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> FoliageColor.FOLIAGE_EVERGREEN, VABlocks.SPRUCE_HEDGE);
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> FoliageColor.FOLIAGE_BIRCH, VABlocks.BIRCH_HEDGE);
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> 0x00e076, VABlocks.ACID);
        ColorProviderRegistry.BLOCK.register( (state, world, pos, tintIndex) -> tintIndex <= 0 ? -1 : RedStoneWireBlock.getColorForPower(state.getValue(RedstoneBridgeBlock.POWER)), VABlocks.REDSTONE_BRIDGE);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (tintIndex != 0) {
                return world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.getDefaultColor();
            } else {
                return -1;
            }
        }, VABlocks.BLUE_PETALS);
    }

    private static void initFogModifiers() {
        FogRenderer.FOG_ENVIRONMENTS.addFirst(new PlayerProjectionPhasingFogModifier());
    }
}
