package com.github.suninvr.virtualadditions.registry;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.TickCriterion;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAAdvancementCriteria {
    public static final TickCriterion USE_TELEPORTER;
    public static final TickCriterion USE_ENTANGLEMENT_DRIVE;

    public static void init(){};

    static {
        USE_TELEPORTER = Criteria.register(new TickCriterion(idOf("use_teleporter")));
        USE_ENTANGLEMENT_DRIVE = Criteria.register(new TickCriterion(idOf("use_entanglement_drive")));
    }
}
