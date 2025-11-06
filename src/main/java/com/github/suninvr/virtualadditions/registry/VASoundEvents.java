package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VASoundEvents {
    public static final SoundEvent BLOCK_ROPE_BREAK ;
    public static final SoundEvent BLOCK_ROPE_EXTEND;
    public static final SoundEvent BLOCK_ROPE_FALL ;
    public static final SoundEvent BLOCK_ROPE_HIT;
    public static final SoundEvent BLOCK_ROPE_PLACE;
    public static final SoundEvent BLOCK_ROPE_STEP;
    public static final SoundEvent BLOCK_ROPE_HIT_GROUND;
    public static final SoundEvent ENTITY_STEEL_BOMB_THROW;
    public static final SoundEvent ENTITY_TOMATO_THROW;
    public static final SoundEvent ENTITY_TOMATO_HIT;
    public static final SoundEvent ENTITY_SALINE_AMBIENT;
    public static final SoundEvent ENTITY_SALINE_HURT;
    public static final SoundEvent ENTITY_SALINE_DEATH;
    public static final SoundEvent ENTITY_LUMWASP_HURT;
    public static final SoundEvent ENTITY_LUMWASP_DEATH;
    public static final SoundEvent ENTITY_SPECTRE_EMPOWER_START;
    public static final SoundEvent ENTITY_SPECTRE_EMPOWER_AMBIENT;
    public static final SoundEvent ENTITY_SPECTRE_AMBIENT;
    public static final SoundEvent ENTITY_SPECTRE_HURT;
    public static final SoundEvent ENTITY_SPECTRE_DEATH;
    public static final SoundEvent BUCKET_FILL_ACID;
    public static final SoundEvent BUCKET_EMPTY_ACID;
    public static final SoundEvent ACID_SIZZLE;
    public static final SoundEvent ACID_AMBIENT;
    public static final SoundEvent BLOCK_MINI_PORTAL_DEPART;
    public static final SoundEvent BLOCK_MINI_PORTAL_ARRIVE;
    public static final SoundEvent BLOCK_MINI_PORTAL_FAIL;
    public static final SoundEvent BLOCK_MINI_PORTAL_RECHARGE;
    public static final SoundEvent BLOCK_MINI_PORTAL_OPEN;
    public static final SoundEvent ITEM_PORTAL_CORE_USE;
    public static final SoundEvent ITEM_HALBERD_SWING;
    public static final SoundEvent ITEM_SPECTRAL_SPYGLASS_START;
    public static final SoundEvent ITEM_SPECTRAL_SPYGLASS_AMBIENT;
    public static final SoundEvent BLOCK_ENTANGLEMENT_DRIVE_USE;
    public static final SoundEvent BLOCK_STEEL_DOOR_SHUTTER_OPEN;
    public static final SoundEvent BLOCK_STEEL_DOOR_SHUTTER_CLOSE;
    public static final SoundEvent BLOCK_FRAYED_SILK_IDLE;
    public static final SoundEvent BLOCK_LUMWASP_NEST_IDLE;

    static {
        BLOCK_ROPE_BREAK = register("block.rope.break");
        BLOCK_ROPE_EXTEND = register("block.rope.extend");
        BLOCK_ROPE_FALL = register("block.rope.fall");
        BLOCK_ROPE_HIT = register("block.rope.hit");
        BLOCK_ROPE_PLACE = register("block.rope.place");
        BLOCK_ROPE_STEP = register("block.rope.step");
        BLOCK_ROPE_HIT_GROUND = register("block.rope.hit_ground");
        ENTITY_STEEL_BOMB_THROW = register("entity.steel_bomb.throw");
        ENTITY_TOMATO_THROW = register("entity.tomato.throw");
        ENTITY_TOMATO_HIT = register("entity.tomato.hit");
        ENTITY_SALINE_AMBIENT = register("entity.saline.ambient");
        ENTITY_SALINE_HURT = register("entity.saline.hurt");
        ENTITY_SALINE_DEATH = register("entity.saline.death");
        ENTITY_LUMWASP_HURT = register("entity.lumwasp.hurt");
        ENTITY_LUMWASP_DEATH = register("entity.lumwasp.death");
        ENTITY_SPECTRE_EMPOWER_START = register("entity.spectre.empower_start");
        ENTITY_SPECTRE_EMPOWER_AMBIENT = register("entity.spectre.empower_ambient");
        ENTITY_SPECTRE_AMBIENT = register("entity.spectre.ambient");
        ENTITY_SPECTRE_HURT = register("entity.spectre.hurt");
        ENTITY_SPECTRE_DEATH = register("entity.spectre.death");
        BUCKET_FILL_ACID = register("item.bucket.fill_acid");
        BUCKET_EMPTY_ACID = register("item.bucket.empty_acid");
        ACID_SIZZLE = register("block.acid.sizzle");
        ACID_AMBIENT = register("block.acid.ambient");
        BLOCK_MINI_PORTAL_DEPART = register("block.mini_portal.depart");
        BLOCK_MINI_PORTAL_ARRIVE = register("block.mini_portal.arrive");
        BLOCK_MINI_PORTAL_FAIL = register("block.mini_portal.fail");
        BLOCK_MINI_PORTAL_RECHARGE = register("block.mini_portal.recharge");
        BLOCK_MINI_PORTAL_OPEN = register("block.mini_portal.open");
        ITEM_PORTAL_CORE_USE = register("item.portal_core.use");
        ITEM_HALBERD_SWING = register("item.halberd.swing");
        ITEM_SPECTRAL_SPYGLASS_START = register("item.spectral_spyglass.start");
        ITEM_SPECTRAL_SPYGLASS_AMBIENT = register("item.spectral_spyglass.ambient");
        BLOCK_ENTANGLEMENT_DRIVE_USE = register("block.entanglement_drive.use");
        BLOCK_STEEL_DOOR_SHUTTER_OPEN = register("block.steel_door.shutter_open");
        BLOCK_STEEL_DOOR_SHUTTER_CLOSE = register("block.steel_door.shutter_close");
        BLOCK_FRAYED_SILK_IDLE = register("block.frayed_silk.idle");
        BLOCK_LUMWASP_NEST_IDLE = register("block.lumwasp_nest.idle");
    }

    public static void init(){}

    private static SoundEvent register(String id) {
        Identifier identifier = idOf(id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier) );
    }
}
