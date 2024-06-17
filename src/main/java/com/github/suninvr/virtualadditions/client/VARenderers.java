package com.github.suninvr.virtualadditions.client;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.RedstoneBridgeBlock;
import com.github.suninvr.virtualadditions.client.render.block.CustomBedBlockEntityRenderer;
import com.github.suninvr.virtualadditions.client.render.block.CustomShulkerBoxBlockEntityRenderer;
import com.github.suninvr.virtualadditions.client.render.entity.*;
import com.github.suninvr.virtualadditions.client.render.item.CustomBedItemRenderer;
import com.github.suninvr.virtualadditions.client.render.item.CustomShulkerBoxItemRenderer;
import com.github.suninvr.virtualadditions.registry.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.biome.FoliageColors;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARenderers {
    public static EntityModelLayer LUMWASP_LAYER = new EntityModelLayer(Identifier.of("virtual_additions", "lumwasp"), "main");
    public static EntityModelLayer LYFT_LAYER = new EntityModelLayer(Identifier.of("virtual_additions", "lyft"), "main");
    public static EntityModelLayer CUSTOM_BED_FOOT_LAYER = new EntityModelLayer(idOf("bed_foot"), "main");
    public static EntityModelLayer CUSTOM_BED_HEAD_LAYER = new EntityModelLayer(idOf("bed_head"), "main");
    public static final SpriteIdentifier CHARTREUSE_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/chartreuse"));
    public static final SpriteIdentifier MAROON_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/maroon"));
    public static final SpriteIdentifier INDIGO_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/indigo"));
    public static final SpriteIdentifier PLUM_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/plum"));
    public static final SpriteIdentifier VIRIDIAN_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/viridian"));
    public static final SpriteIdentifier TAN_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/tan"));
    public static final SpriteIdentifier SINOPIA_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/sinopia"));
    public static final SpriteIdentifier LILAC_SHULKER_BOX = new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/shulker/lilac"));
    public static final SpriteIdentifier CHARTREUSE_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/chartreuse"));
    public static final SpriteIdentifier MAROON_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/maroon"));
    public static final SpriteIdentifier INDIGO_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/indigo"));
    public static final SpriteIdentifier PLUM_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/plum"));
    public static final SpriteIdentifier VIRIDIAN_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/viridian"));
    public static final SpriteIdentifier TAN_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/tan"));
    public static final SpriteIdentifier SINOPIA_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/sinopia"));
    public static final SpriteIdentifier LILAC_BED_TEXTURE = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, VirtualAdditions.idOf("entity/bed/lilac"));
    private static final CustomBedItemRenderer bedItemRenderer = new CustomBedItemRenderer();
    private static final CustomShulkerBoxItemRenderer shulkerBoxItemRenderer = new CustomShulkerBoxItemRenderer();

    public static void init() {
        initBlockRenderLayers();
        initEntityRenderers();
        initBlockEntityRenderers();
        initItemRenderers();
        initFluidRenderers();
        initColorProviders();
    }

    private static void initBlockRenderLayers() {
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
                VABlocks.VIRIDIAN_STAINED_GLASS,
                VABlocks.VIRIDIAN_STAINED_GLASS_PANE,
                VABlocks.TAN_STAINED_GLASS,
                VABlocks.TAN_STAINED_GLASS_PANE,
                VABlocks.SINOPIA_STAINED_GLASS,
                VABlocks.SINOPIA_STAINED_GLASS_PANE,
                VABlocks.LILAC_STAINED_GLASS,
                VABlocks.LILAC_STAINED_GLASS_PANE
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                VABlocks.CLIMBING_ROPE_ANCHOR,
                VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR,
                VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR,
                VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR,
                VABlocks.WAXED_CLIMBING_ROPE_ANCHOR,
                VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR,
                VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR,
                VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR,
                VABlocks.CLIMBING_ROPE,
                VABlocks.AEROBLOOM_DOOR,
                VABlocks.AEROBLOOM_TRAPDOOR,
                VABlocks.AEROBLOOM_SAPLING,
                VABlocks.POTTED_AEROBLOOM_SAPLING,
                VABlocks.BALLOON_BULB,
                VABlocks.BALLOON_BULB_PLANT,
                VABlocks.BALLOON_BULB_BUD,
                VABlocks.ROCK_SALT_CRYSTAL,
                VABlocks.COTTON,
                VABlocks.CORN_CROP,
                VABlocks.GLOWING_SILK,
                VABlocks.FRAYED_SILK,
                VABlocks.TALL_GREENCAP_MUSHROOMS,
                VABlocks.GREENCAP_MUSHROOM,
                VABlocks.POTTED_GREENCAP_MUSHROOM
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
        EntityModelLayerRegistry.registerModelLayer(LYFT_LAYER, LyftEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(VAEntityType.CLIMBING_ROPE, ClimbingRopeEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.STEEL_BOMB, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.ACID_SPIT, AcidSpitEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.LUMWASP, LumwaspEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.LYFT, LyftEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.SALINE, SalineEntityRenderer::new);
    }

    private static void initBlockEntityRenderers() {

        EntityModelLayerRegistry.registerModelLayer(CUSTOM_BED_FOOT_LAYER, CustomBedBlockEntityRenderer.FOOT_MODEL_PROVIDER);
        EntityModelLayerRegistry.registerModelLayer(CUSTOM_BED_HEAD_LAYER, CustomBedBlockEntityRenderer.HEAD_MODEL_PROVIDER);

        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_SIGN, SignBlockEntityRenderer::new );
        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_HANGING_SIGN, HangingSignBlockEntityRenderer::new );
        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_BED, CustomBedBlockEntityRenderer::new );
        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_SHULKER_BOX, CustomShulkerBoxBlockEntityRenderer::new );
        BlockEntityRendererFactories.register( VABlockEntityType.CUSTOM_BANNER, BannerBlockEntityRenderer::new );

        TexturedRenderLayers.SIGN_TYPE_TEXTURES.put(VABlocks.AEROBLOOM_WOODTYPE, new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, idOf("entity/signs/aerobloom")));
        TexturedRenderLayers.HANGING_SIGN_TYPE_TEXTURES.put(VABlocks.AEROBLOOM_WOODTYPE, new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, idOf("entity/signs/hanging/aerobloom")));

    }

    private static void initItemRenderers() {
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.CHARTREUSE_BED, bedItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.MAROON_BED, bedItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.INDIGO_BED, bedItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.PLUM_BED, bedItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.VIRIDIAN_BED, bedItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.TAN_BED, bedItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.SINOPIA_BED, bedItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.LILAC_BED, bedItemRenderer);

        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.CHARTREUSE_SHULKER_BOX, shulkerBoxItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.MAROON_SHULKER_BOX, shulkerBoxItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.INDIGO_SHULKER_BOX, shulkerBoxItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.PLUM_SHULKER_BOX, shulkerBoxItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.VIRIDIAN_SHULKER_BOX, shulkerBoxItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.TAN_SHULKER_BOX, shulkerBoxItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.SINOPIA_SHULKER_BOX, shulkerBoxItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(VAItems.LILAC_SHULKER_BOX, shulkerBoxItemRenderer);

        ModelPredicateProviderRegistry.register(Items.CROSSBOW, idOf("climbing_rope"), (itemStack, clientWorld, livingEntity, a) -> {
            if(!itemStack.isOf(Items.CROSSBOW)) return 0.0F;
            ChargedProjectilesComponent component = itemStack.get(DataComponentTypes.CHARGED_PROJECTILES);
            float f = 0.0F;
            if (component != null) {
                ItemStack stack = component.getProjectiles().isEmpty() ? ItemStack.EMPTY : component.getProjectiles().get(0);
                if (stack.isOf(VAItems.CLIMBING_ROPE) || stack.isOf(VAItems.WAXED_CLIMBING_ROPE)) f = 0.25F;
                else if (stack.isOf(VAItems.EXPOSED_CLIMBING_ROPE) || stack.isOf(VAItems.WAXED_EXPOSED_CLIMBING_ROPE)) f = 0.5F;
                else if (stack.isOf(VAItems.WEATHERED_CLIMBING_ROPE) || stack.isOf(VAItems.WAXED_WEATHERED_CLIMBING_ROPE)) f = 0.75F;
                else if (stack.isOf(VAItems.OXIDIZED_CLIMBING_ROPE) || stack.isOf(VAItems.WAXED_OXIDIZED_CLIMBING_ROPE)) f = 1.0F;
            }
            return f ;
        });

        ModelPredicateProviderRegistry.register(VAItems.ICE_CREAM, idOf("colorful"), (stack, world, entity, seed) -> {
            if (!stack.isOf(VAItems.ICE_CREAM)) return 0.0F;
            return stack.contains(DataComponentTypes.DYED_COLOR) ? 1.0F : 0.0F;
        });

        ModelPredicateProviderRegistry.register(VAItems.ENGRAVING_CHISEL, idOf("colorful"), (stack, world, entity, seed) -> {
            if (!stack.isOf(VAItems.ENGRAVING_CHISEL)) return 0.0F;
            return stack.contains(DataComponentTypes.DYED_COLOR) ? 1.0F : 0.0F;
        });
    }

    private static void  initFluidRenderers() {
        FluidRenderHandlerRegistry.INSTANCE.register(VAFluids.ACID, VAFluids.FLOWING_ACID, new SimpleFluidRenderHandler(
                Identifier.of("minecraft:block/water_still"),
                Identifier.of("minecraft:block/water_flow"),
                0x00e076
        ));
    }

    private static void initColorProviders() {
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

        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> {
            return tintIndex > 0 ? -1 : ColorHelper.Argb.fullAlpha(stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).getColor());
        }, VAItems.APPLICABLE_POTION);
        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> FoliageColors.getDefaultColor(), VAItems.OAK_HEDGE, VAItems.JUNGLE_HEDGE, VAItems.ACACIA_HEDGE, VAItems.DARK_OAK_HEDGE);
        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> tintIndex <= 0 ? -1 : 5353656, VAItems.GRASSY_FLOATROCK);
        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> FoliageColors.getBirchColor(), VAItems.BIRCH_HEDGE);
        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> FoliageColors.getSpruceColor(), VAItems.SPRUCE_HEDGE);
        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> FoliageColors.getMangroveColor(), VAItems.MANGROVE_HEDGE);
        ColorProviderRegistry.ITEM.register( (stack, tintIndex) -> {
            return tintIndex > 0 ? -1 : ColorHelper.Argb.fullAlpha(stack.get(DataComponentTypes.DYED_COLOR) != null ? stack.get(DataComponentTypes.DYED_COLOR).rgb() : 0xFFFFFF);
        }, VAItems.ICE_CREAM, VAItems.ENGRAVING_CHISEL);
    }
}
