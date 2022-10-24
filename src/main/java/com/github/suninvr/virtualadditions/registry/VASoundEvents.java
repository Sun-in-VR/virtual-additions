package com.github.suninvr.virtualadditions.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VASoundEvents {
    public static void init(){}
    public static final SoundEvent BLOCK_ROPE_BREAK ;
    public static final SoundEvent BLOCK_ROPE_EXTEND;
    public static final SoundEvent BLOCK_ROPE_FALL ;
    public static final SoundEvent BLOCK_ROPE_HIT;
    public static final SoundEvent BLOCK_ROPE_PLACE ;
    public static final SoundEvent BLOCK_ROPE_STEP ;
    public static final SoundEvent ENTITY_STEEL_BOMB_THROW;

    static {
        BLOCK_ROPE_BREAK = register("block.rope.break");
        BLOCK_ROPE_EXTEND = register("block.rope.extend");
        BLOCK_ROPE_FALL = register("block.rope.fall");
        BLOCK_ROPE_HIT = register("block.rope.hit");
        BLOCK_ROPE_PLACE = register("block.rope.place");
        BLOCK_ROPE_STEP = register("block.rope.step");
        ENTITY_STEEL_BOMB_THROW = register("entity.steel_bomb.throw");
    }

    private static SoundEvent register(String id) {
        return Registry.register(Registry.SOUND_EVENT, idOf(id), new SoundEvent(idOf(id)));
    }
}
