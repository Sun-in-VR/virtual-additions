package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.event.GameEvent;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAGameEventTags {
    public static final TagKey<GameEvent> NOTIFIES_SPOTLIGHT = register("notifies_spotlight");

    private static TagKey<GameEvent> register(String id) {
        return TagKey.of(Registries.GAME_EVENT.getKey(), idOf(id));
    }

    public static void init(){}
}
