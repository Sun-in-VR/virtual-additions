package com.github.suninvr.virtualadditions.registry;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.*;

import java.util.function.ToIntFunction;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAGameRules {
    public static final GameRule<Boolean> IOLITE_INTERFERENCE;
    public static final GameRule<Integer> MINI_PORTAL_MAX_CREATION_RANGE;
    public static final GameRule<Integer> SCULK_GILD_BLOCK_SELECTION_MAXIMUM;

    static {
        IOLITE_INTERFERENCE = booleanRule("iolite_interfrence", GameRuleCategory.PLAYER, true);
        MINI_PORTAL_MAX_CREATION_RANGE = intRule("maximum_mini_portal_creation_range", GameRuleCategory.MISC, 128, 0, Integer.MAX_VALUE);
        SCULK_GILD_BLOCK_SELECTION_MAXIMUM = intRule("maximum_sculk_gild_destroy_size", GameRuleCategory.PLAYER, 255, 0, 10000);
    }

    private static GameRule<Integer> intRule(String id, GameRuleCategory category, int defValue, int minValue, int maxValue) {
        return register(id, category, GameRuleType.INT, IntegerArgumentType.integer(minValue, maxValue), Codec.intRange(minValue, maxValue), defValue, FeatureFlagSet.of(), GameRuleTypeVisitor::visitInteger, (integer) -> integer);
    }

    private static GameRule<Boolean> booleanRule(String id, GameRuleCategory category, boolean defValue) {
        return register(id, category, GameRuleType.BOOL, BoolArgumentType.bool(), Codec.BOOL, defValue, FeatureFlagSet.of(), GameRuleTypeVisitor::visitBoolean, (boolean_) -> boolean_ ? 1 : 0);
    }

    public static <T> GameRule<T> register(String string, GameRuleCategory gameRuleCategory, GameRuleType gameRuleType, ArgumentType<T> argumentType, Codec<T> codec, T object, FeatureFlagSet featureFlagSet, GameRules.VisitorCaller<T> visitorCaller, ToIntFunction<T> toIntFunction) {
        return (GameRule) Registry.register(BuiltInRegistries.GAME_RULE, idOf(string), new GameRule(gameRuleCategory, gameRuleType, argumentType, visitorCaller, codec, toIntFunction, object, featureFlagSet));
    }

    public static void init(){}
}
