package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.List;

@Mixin(VillagerTrades.class)
public abstract class TradeOffersMixin {
    @Shadow @Final @Mutable
    public static List<Pair<VillagerTrades.ItemListing[], Integer>> WANDERING_TRADER_TRADES;

    @Unique private static final VillagerTrades.ItemListing SOULBLOOM_SAPLING_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.SOULBLOOM_SAPLING, 5, 1, 8, 1);
    @Unique private static final VillagerTrades.ItemListing SOULBLOOM_LOG_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.SOULBLOOM_LOG, 1, 8, 4, 1);
    @Unique private static final VillagerTrades.ItemListing ZEBRANO_SAPLING_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.ZEBRANO_SAPLING, 5, 1, 8, 1);
    @Unique private static final VillagerTrades.ItemListing ZEBRANO_LOG_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.ZEBRANO_LOG, 1, 8, 4, 1);
    @Unique private static final VillagerTrades.ItemListing CHARTREUSE_DYE_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.CHARTREUSE_DYE, 1, 3, 12, 1);
    @Unique private static final VillagerTrades.ItemListing MAROON_DYE_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.MAROON_DYE, 1, 3, 12, 1);
    @Unique private static final VillagerTrades.ItemListing INDIGO_DYE_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.INDIGO_DYE, 1, 3, 12, 1);
    @Unique private static final VillagerTrades.ItemListing PLUM_DYE_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.PLUM_DYE, 1, 3, 12, 1);
    @Unique private static final VillagerTrades.ItemListing VIRIDIAN_DYE_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.VIRIDIAN_DYE, 1, 3, 12, 1);
    @Unique private static final VillagerTrades.ItemListing TAN_DYE_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.TAN_DYE, 1, 3, 12, 1);
    @Unique private static final VillagerTrades.ItemListing SINOPIA_DYE_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.SINOPIA_DYE, 1, 3, 12, 1);
    @Unique private static final VillagerTrades.ItemListing LILAC_DYE_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.LILAC_DYE, 1, 3, 12, 1);
    @Unique private static final VillagerTrades.ItemListing LIGHTNING_BOTTLE_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.LIGHTNING_BOTTLE, 15, 1, 12, 1);
    @Unique private static final VillagerTrades.ItemListing SPRING_LOTUS_TRADE_OFFER = new VillagerTrades.ItemsForEmeralds(VAItems.SMALL_SPRING_LOTUS, 1, 1, 12, 1);

    static {
        ArrayList<Pair<VillagerTrades.ItemListing[], Integer>> TRADES = new ArrayList<>();
        ArrayList<VillagerTrades.ItemListing> BUY_ITEM_TRADES = new ArrayList<>(List.of(WANDERING_TRADER_TRADES.getFirst().getLeft()));
        ArrayList<VillagerTrades.ItemListing> SELL_ITEM_TRADES = new ArrayList<>(List.of(WANDERING_TRADER_TRADES.get(1).getLeft()));
        ArrayList<VillagerTrades.ItemListing> SELL_ITEM_TRADES_2 = new ArrayList<>(List.of(WANDERING_TRADER_TRADES.get(2).getLeft()));

        SELL_ITEM_TRADES.add(SOULBLOOM_LOG_TRADE_OFFER);
        SELL_ITEM_TRADES.add(ZEBRANO_LOG_TRADE_OFFER);

        SELL_ITEM_TRADES_2.add(SOULBLOOM_SAPLING_TRADE_OFFER);
        SELL_ITEM_TRADES_2.add(ZEBRANO_SAPLING_TRADE_OFFER);
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

        TRADES.add(Pair.of(BUY_ITEM_TRADES.toArray(new VillagerTrades.ItemListing[]{}), WANDERING_TRADER_TRADES.getFirst().getRight()));
        TRADES.add(Pair.of(SELL_ITEM_TRADES.toArray(new VillagerTrades.ItemListing[]{}), WANDERING_TRADER_TRADES.get(1).getRight()));
        TRADES.add(Pair.of(SELL_ITEM_TRADES_2.toArray(new VillagerTrades.ItemListing[]{}), WANDERING_TRADER_TRADES.get(2).getRight()));

        //TRADES.add(Pair.of(new TradeOffers.Factory[]{
        //        LIGHTNING_BOTTLE_TRADE_OFFER
        //        }, 1
        //));

        WANDERING_TRADER_TRADES = TRADES;
    }
}
