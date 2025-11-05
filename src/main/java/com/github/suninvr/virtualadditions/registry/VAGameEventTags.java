package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.gameevent.GameEvent;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAGameEventTags {
    public static final TagKey<GameEvent> NOTIFIES_SPOTLIGHT = register("notifies_spotlight");

    private static TagKey<GameEvent> register(String id) {
        return TagKey.create(BuiltInRegistries.GAME_EVENT.key(), idOf(id));
    }

    public static void init(){}
}
