package com.github.suninvr.virtualadditions.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VASoundEvents {
    public static final SoundEvent BLOCK_ROPE_BREAK ;
    public static final SoundEvent BLOCK_ROPE_EXTEND;
    public static final SoundEvent BLOCK_ROPE_FALL ;
    public static final SoundEvent BLOCK_ROPE_HIT;
    public static final SoundEvent BLOCK_ROPE_PLACE;
    public static final SoundEvent BLOCK_ROPE_STEP;
    public static final SoundEvent ENTITY_STEEL_BOMB_THROW;
    public static final SoundEvent ENTITY_LUMWASP_HURT;
    public static final SoundEvent ENTITY_LUMWASP_DEATH;
    public static final SoundEvent BUCKET_FILL_ACID;
    public static final SoundEvent BUCKET_EMPTY_ACID;
    public static final SoundEvent ACID_SIZZLE;
    public static final SoundEvent ACID_AMBIENT;
    public static final SoundEvent BLOCK_WARP_TETHER_WARP;
    public static final SoundEvent BLOCK_WARP_TETHER_FAIL;
    public static final SoundEvent BLOCK_WARP_TETHER_RECHARGE;
    public static final SoundEvent BLOCK_WARP_ANCHOR_WARP;
    public static final SoundEvent BLOCK_WARP_ANCHOR_USE;
    public static final SoundEvent BLOCK_ENTANGLEMENT_DRIVE_USE;
    public static final SoundEvent BLOCK_STEEL_DOOR_SHUTTER_OPEN;
    public static final SoundEvent BLOCK_STEEL_DOOR_SHUTTER_CLOSE;

    static {
        BLOCK_ROPE_BREAK = register("block.rope.break");
        BLOCK_ROPE_EXTEND = register("block.rope.extend");
        BLOCK_ROPE_FALL = register("block.rope.fall");
        BLOCK_ROPE_HIT = register("block.rope.hit");
        BLOCK_ROPE_PLACE = register("block.rope.place");
        BLOCK_ROPE_STEP = register("block.rope.step");
        ENTITY_STEEL_BOMB_THROW = register("entity.steel_bomb.throw");
        ENTITY_LUMWASP_HURT = register("entity.lumwasp.hurt");
        ENTITY_LUMWASP_DEATH = register("entity.lumwasp.death");
        BUCKET_FILL_ACID = register("item.bucket.fill_acid");
        BUCKET_EMPTY_ACID = register("item.bucket.empty_acid");
        ACID_SIZZLE = register("block.acid.sizzle");
        ACID_AMBIENT = register("block.acid.ambient");
        BLOCK_WARP_TETHER_WARP = register("block.warp_tether.warp");
        BLOCK_WARP_TETHER_FAIL = register("block.warp_tether.fail");
        BLOCK_WARP_TETHER_RECHARGE = register("block.warp_tether.recharge");
        BLOCK_WARP_ANCHOR_WARP = register("block.warp_anchor.warp");
        BLOCK_WARP_ANCHOR_USE = register("block.warp_anchor.use");
        BLOCK_ENTANGLEMENT_DRIVE_USE = register("block.entanglement_drive.use");
        BLOCK_STEEL_DOOR_SHUTTER_OPEN = register("block.steel_door.shutter_open");
        BLOCK_STEEL_DOOR_SHUTTER_CLOSE = register("block.steel_door.shutter_close");
    }

    public static void init(){}

    private static SoundEvent register(String id) {
        Identifier identifier = idOf(id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier) );
    }
}
