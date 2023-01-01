package com.github.suninvr.virtualadditions.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

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
    public static final SoundEvent ENTITY_LUMWASP_HURT;
    public static final SoundEvent ACID_FILL;
    public static final SoundEvent ACID_EMPTY;

    static {
        BLOCK_ROPE_BREAK = register("block.rope.break");
        BLOCK_ROPE_EXTEND = register("block.rope.extend");
        BLOCK_ROPE_FALL = register("block.rope.fall");
        BLOCK_ROPE_HIT = register("block.rope.hit");
        BLOCK_ROPE_PLACE = register("block.rope.place");
        BLOCK_ROPE_STEP = register("block.rope.step");
        ENTITY_STEEL_BOMB_THROW = register("entity.steel_bomb.throw");
        ENTITY_LUMWASP_HURT = register("entity.lumwasp.hurt");
        ACID_FILL = register("item.bucket.fill_acid");
        ACID_EMPTY = register("item.bucket.empty_acid");
    }

    private static SoundEvent register(String id) {
        Identifier identifier = idOf(id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier) );
    }
}
