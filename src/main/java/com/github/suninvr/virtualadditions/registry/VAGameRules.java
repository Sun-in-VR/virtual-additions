package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class VAGameRules {
    public static final GameRules.Key<GameRules.BooleanRule> IOLITE_INTERFERENCE;

    static {
        IOLITE_INTERFERENCE = GameRuleRegistry.register("ioliteInterference", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
    }

    public static void init(){}
}
