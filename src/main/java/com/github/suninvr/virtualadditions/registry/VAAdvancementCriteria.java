package com.github.suninvr.virtualadditions.registry;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.TickCriterion;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAAdvancementCriteria {
    public static final TickCriterion USE_TELEPORTER;
    public static final TickCriterion USE_ENTANGLEMENT_DRIVE;
    public static final TickCriterion FIRE_CLIMBING_ROPE_FROM_CROSSBOW;

    static {
        USE_TELEPORTER = Criteria.register(idOf("use_teleporter").toString(), new TickCriterion());
        USE_ENTANGLEMENT_DRIVE = Criteria.register(idOf("use_entanglement_drive").toString(), new TickCriterion());
        FIRE_CLIMBING_ROPE_FROM_CROSSBOW = Criteria.register(idOf("fire_climbing_rope_with_crossbow").toString(), new TickCriterion());
    }

    public static void init(){}
}
