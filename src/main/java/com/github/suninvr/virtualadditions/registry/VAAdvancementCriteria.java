package com.github.suninvr.virtualadditions.registry;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.TickCriterion;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAAdvancementCriteria {
    public static final TickCriterion USE_TELEPORTER;
    public static final TickCriterion USE_ENTANGLEMENT_DRIVE;

    static {
        USE_TELEPORTER = Criteria.register(idOf("use_teleporter").toString(), new TickCriterion());
        USE_ENTANGLEMENT_DRIVE = Criteria.register(idOf("use_entanglement_drive").toString(), new TickCriterion());
    }

    public static void init(){}
}
