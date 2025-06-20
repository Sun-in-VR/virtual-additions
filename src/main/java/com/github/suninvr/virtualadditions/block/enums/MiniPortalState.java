package com.github.suninvr.virtualadditions.block.enums;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum MiniPortalState implements StringIdentifiable {
    OPEN(true, true),
    BLOCKED(false, true),
    COOLDOWN(false, true),
    POWERED(false, false);
    
    public final boolean canDepart;
    public final boolean canArrive;

    MiniPortalState(boolean canDepart, boolean canArrive) {
        this.canDepart = canDepart;
        this.canArrive = canArrive;
    }

    @Override
    public String asString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
