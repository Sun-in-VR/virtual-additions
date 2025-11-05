package com.github.suninvr.virtualadditions.registry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.PlayerTrigger;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAAdvancementCriteria {
    public static final PlayerTrigger USE_TELEPORTER;
    public static final PlayerTrigger USE_ENTANGLEMENT_DRIVE;
    public static final PlayerTrigger FIRE_CLIMBING_ROPE_FROM_CROSSBOW;

    static {
        USE_TELEPORTER = CriteriaTriggers.register(idOf("use_teleporter").toString(), new PlayerTrigger());
        USE_ENTANGLEMENT_DRIVE = CriteriaTriggers.register(idOf("use_entanglement_drive").toString(), new PlayerTrigger());
        FIRE_CLIMBING_ROPE_FROM_CROSSBOW = CriteriaTriggers.register(idOf("fire_climbing_rope_with_crossbow").toString(), new PlayerTrigger());
    }

    public static void init(){}
}
