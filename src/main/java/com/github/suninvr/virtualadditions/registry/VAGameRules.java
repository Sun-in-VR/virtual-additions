package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.level.GameRules;

public class VAGameRules {
    public static final GameRules.Key<GameRules.BooleanValue> IOLITE_INTERFERENCE;
    public static final GameRules.Key<GameRules.IntegerValue> MINI_PORTAL_MAX_CREATION_RANGE;
    public static final GameRules.Key<GameRules.IntegerValue> SCULK_GILD_BLOCK_SELECTION_MAXIMUM;

    static {
        IOLITE_INTERFERENCE = GameRuleRegistry.register("ioliteInterference", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
        MINI_PORTAL_MAX_CREATION_RANGE = GameRuleRegistry.register("miniPortalMaxCreationRange", GameRules.Category.MISC, GameRuleFactory.createIntRule(128, 0, 60000000));
        SCULK_GILD_BLOCK_SELECTION_MAXIMUM = GameRuleRegistry.register("sculkGildBlockSelectionMaximum", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(255, 0, 10000));
    }

    public static void init(){}
}
