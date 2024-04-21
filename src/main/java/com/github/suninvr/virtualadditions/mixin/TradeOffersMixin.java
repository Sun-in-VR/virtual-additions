package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(TradeOffers.class)
public abstract class TradeOffersMixin {
    @Shadow @Final @Mutable
    public static Int2ObjectMap<TradeOffers.Factory[]> WANDERING_TRADER_TRADES;

    @Unique private static final TradeOffers.Factory AEROBLOOM_SAPLING_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.AEROBLOOM_SAPLING, 5, 1, 8, 1);
    @Unique private static final TradeOffers.Factory CHARTREUSE_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.CHARTREUSE_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory MAROON_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.MAROON_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory INDIGO_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.INDIGO_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory PLUM_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.PLUM_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory VIRIDIAN_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.VIRIDIAN_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory TAN_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.TAN_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory SINOPIA_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.SINOPIA_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory LILAC_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.LILAC_DYE, 1, 3, 12, 1);

    @Shadow
    private static Int2ObjectMap<TradeOffers.Factory[]> copyToFastUtilMap(ImmutableMap<Integer, TradeOffers.Factory[]> map) {
        return null;
    }

    static {
        ArrayList<TradeOffers.Factory> LEVEL_1_TRADES = new ArrayList<>(List.of(WANDERING_TRADER_TRADES.get(1)));
        ArrayList<TradeOffers.Factory> LEVEL_2_TRADES = new ArrayList<>(List.of(WANDERING_TRADER_TRADES.get(2)));
        LEVEL_1_TRADES.add(AEROBLOOM_SAPLING_TRADE_OFFER);
        LEVEL_1_TRADES.add(AEROBLOOM_SAPLING_TRADE_OFFER);
        LEVEL_1_TRADES.add(AEROBLOOM_SAPLING_TRADE_OFFER);
        LEVEL_1_TRADES.add(AEROBLOOM_SAPLING_TRADE_OFFER);
        LEVEL_1_TRADES.add(AEROBLOOM_SAPLING_TRADE_OFFER);
        LEVEL_1_TRADES.add(AEROBLOOM_SAPLING_TRADE_OFFER);
        LEVEL_1_TRADES.add(AEROBLOOM_SAPLING_TRADE_OFFER);
        LEVEL_1_TRADES.add(AEROBLOOM_SAPLING_TRADE_OFFER);
        LEVEL_1_TRADES.add(AEROBLOOM_SAPLING_TRADE_OFFER);
        LEVEL_1_TRADES.add(AEROBLOOM_SAPLING_TRADE_OFFER);
        LEVEL_1_TRADES.add(CHARTREUSE_DYE_TRADE_OFFER);
        LEVEL_1_TRADES.add(MAROON_DYE_TRADE_OFFER);
        LEVEL_1_TRADES.add(INDIGO_DYE_TRADE_OFFER);
        LEVEL_1_TRADES.add(PLUM_DYE_TRADE_OFFER);
        LEVEL_1_TRADES.add(VIRIDIAN_DYE_TRADE_OFFER);
        LEVEL_1_TRADES.add(TAN_DYE_TRADE_OFFER);
        LEVEL_1_TRADES.add(SINOPIA_DYE_TRADE_OFFER);
        LEVEL_1_TRADES.add(LILAC_DYE_TRADE_OFFER);

        WANDERING_TRADER_TRADES = copyToFastUtilMap(ImmutableMap.of(1, LEVEL_1_TRADES.toArray(new TradeOffers.Factory[]{}), 2, LEVEL_2_TRADES.toArray(new TradeOffers.Factory[]{})));
    }
}
