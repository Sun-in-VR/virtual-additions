package com.github.suninvr.virtualadditions.client;

import com.github.suninvr.virtualadditions.client.render.entity.ClimbingRopeEntityRenderer;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VirtualAdditionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                VABlocks.CLIMBING_ROPE_ANCHOR,
                VABlocks.RED_GLIMMER_CRYSTAL,
                VABlocks.GREEN_GLIMMER_CRYSTAL,
                VABlocks.BLUE_GLIMMER_CRYSTAL,
                VABlocks.COTTON,
                VABlocks.OAK_HEDGE,
                VABlocks.SPRUCE_HEDGE,
                VABlocks.BIRCH_HEDGE,
                VABlocks.JUNGLE_HEDGE,
                VABlocks.ACACIA_HEDGE,
                VABlocks.DARK_OAK_HEDGE,
                VABlocks.MANGROVE_HEDGE,
                VABlocks.AZALEA_HEDGE,
                VABlocks.FLOWERING_AZALEA_HEDGE,
                VABlocks.HANGING_GLOWSILK
        );

        EntityRendererRegistry.register(VAEntityType.CLIMBING_ROPE, ClimbingRopeEntityRenderer::new);
        EntityRendererRegistry.register(VAEntityType.STEEL_BOMB, FlyingItemEntityRenderer::new);

        ColorProviderRegistry.BLOCK.register( ((state, world, pos, tintIndex) -> world != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor()),
                VABlocks.OAK_HEDGE,
                VABlocks.JUNGLE_HEDGE,
                VABlocks.ACACIA_HEDGE,
                VABlocks.DARK_OAK_HEDGE,
                VABlocks.MANGROVE_HEDGE
                );
        ColorProviderRegistry.BLOCK.register( ((state, world, pos, tintIndex) -> FoliageColors.getSpruceColor()), VABlocks.SPRUCE_HEDGE);
        ColorProviderRegistry.BLOCK.register( ((state, world, pos, tintIndex) -> FoliageColors.getBirchColor()), VABlocks.BIRCH_HEDGE);

        ColorProviderRegistry.ITEM.register( ((stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtil.getColor(stack)), VAItems.APPLICABLE_POTION);
        ColorProviderRegistry.ITEM.register( ((stack, tintIndex) -> FoliageColors.getDefaultColor()),
                VAItems.OAK_HEDGE,
                VAItems.JUNGLE_HEDGE,
                VAItems.ACACIA_HEDGE,
                VAItems.DARK_OAK_HEDGE
        );
        ColorProviderRegistry.ITEM.register( ((stack, tintIndex) -> FoliageColors.getBirchColor()), VAItems.BIRCH_HEDGE );
        ColorProviderRegistry.ITEM.register( ((stack, tintIndex) -> FoliageColors.getSpruceColor()), VAItems.SPRUCE_HEDGE );
        ColorProviderRegistry.ITEM.register( ((stack, tintIndex) -> FoliageColors.getMangroveColor()), VAItems.MANGROVE_HEDGE );

        ModelPredicateProviderRegistry.register(Items.CROSSBOW, idOf("climbing_rope"), (itemStack, clientWorld, livingEntity, a) -> {
            if(!itemStack.isOf(Items.CROSSBOW)) return 0.0F;
            return CrossbowItem.hasProjectile(itemStack, VAItems.CLIMBING_ROPE) ? 1.0F : 0.0F;
        });
    }

}
