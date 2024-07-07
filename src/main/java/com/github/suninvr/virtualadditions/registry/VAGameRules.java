package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class VAGameRules {
    public static final GameRules.Key<GameRules.BooleanRule> IOLITE_INTERFERENCE;
    public static final GameRules.Key<GameRules.IntRule> SCULK_GILD_BLOCK_SELECTION_MAXIMUM;

    static {
        IOLITE_INTERFERENCE = GameRuleRegistry.register("ioliteInterference", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
        SCULK_GILD_BLOCK_SELECTION_MAXIMUM = GameRuleRegistry.register("sculkGildBlockSelectionMaximum", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(255, 0, 10000));
    }

    public static void init(){}
}
