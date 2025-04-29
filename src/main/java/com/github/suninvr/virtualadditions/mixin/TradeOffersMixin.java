package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.List;

@Mixin(TradeOffers.class)
public abstract class TradeOffersMixin {
    @Shadow @Final @Mutable
    public static List<Pair<TradeOffers.Factory[], Integer>> WANDERING_TRADER_TRADES;

    @Unique private static final TradeOffers.Factory SOULBLOOM_SAPLING_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.SOULBLOOM_SAPLING, 5, 1, 8, 1);
    @Unique private static final TradeOffers.Factory SOULBLOOM_LOG_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.SOULBLOOM_LOG, 1, 8, 4, 1);
    @Unique private static final TradeOffers.Factory CHARTREUSE_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.CHARTREUSE_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory MAROON_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.MAROON_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory INDIGO_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.INDIGO_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory PLUM_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.PLUM_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory VIRIDIAN_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.VIRIDIAN_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory TAN_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.TAN_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory SINOPIA_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.SINOPIA_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory LILAC_DYE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.LILAC_DYE, 1, 3, 12, 1);
    @Unique private static final TradeOffers.Factory LIGHTNING_BOTTLE_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.LIGHTNING_BOTTLE, 15, 1, 12, 1);
    @Unique private static final TradeOffers.Factory SPRING_LOTUS_TRADE_OFFER = new TradeOffers.SellItemFactory(VAItems.SMALL_SPRING_LOTUS, 1, 1, 12, 1);

    static {
        ArrayList<Pair<TradeOffers.Factory[], Integer>> TRADES = new ArrayList<>();
        ArrayList<TradeOffers.Factory> BUY_ITEM_TRADES = new ArrayList<>(List.of(WANDERING_TRADER_TRADES.getFirst().getLeft()));
        ArrayList<TradeOffers.Factory> SELL_ITEM_TRADES = new ArrayList<>(List.of(WANDERING_TRADER_TRADES.get(1).getLeft()));
        ArrayList<TradeOffers.Factory> SELL_ITEM_TRADES_2 = new ArrayList<>(List.of(WANDERING_TRADER_TRADES.get(2).getLeft()));

        SELL_ITEM_TRADES.add(SOULBLOOM_LOG_TRADE_OFFER);

        SELL_ITEM_TRADES_2.add(SOULBLOOM_SAPLING_TRADE_OFFER);
        SELL_ITEM_TRADES_2.add(CHARTREUSE_DYE_TRADE_OFFER);
        SELL_ITEM_TRADES_2.add(MAROON_DYE_TRADE_OFFER);
        SELL_ITEM_TRADES_2.add(INDIGO_DYE_TRADE_OFFER);
        SELL_ITEM_TRADES_2.add(PLUM_DYE_TRADE_OFFER);
        SELL_ITEM_TRADES_2.add(VIRIDIAN_DYE_TRADE_OFFER);
        SELL_ITEM_TRADES_2.add(TAN_DYE_TRADE_OFFER);
        SELL_ITEM_TRADES_2.add(SINOPIA_DYE_TRADE_OFFER);
        SELL_ITEM_TRADES_2.add(LILAC_DYE_TRADE_OFFER);
        SELL_ITEM_TRADES_2.add(LIGHTNING_BOTTLE_TRADE_OFFER);
        SELL_ITEM_TRADES_2.add(SPRING_LOTUS_TRADE_OFFER);

        TRADES.add(Pair.of(BUY_ITEM_TRADES.toArray(new TradeOffers.Factory[]{}), WANDERING_TRADER_TRADES.getFirst().getRight()));
        TRADES.add(Pair.of(SELL_ITEM_TRADES.toArray(new TradeOffers.Factory[]{}), WANDERING_TRADER_TRADES.get(1).getRight()));
        TRADES.add(Pair.of(SELL_ITEM_TRADES_2.toArray(new TradeOffers.Factory[]{}), WANDERING_TRADER_TRADES.get(2).getRight()));

        //TRADES.add(Pair.of(new TradeOffers.Factory[]{
        //        LIGHTNING_BOTTLE_TRADE_OFFER
        //        }, 1
        //));

        WANDERING_TRADER_TRADES = TRADES;
    }
}
