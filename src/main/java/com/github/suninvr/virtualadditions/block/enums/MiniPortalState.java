package com.github.suninvr.virtualadditions.block.enums;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum MiniPortalState implements StringRepresentable {
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
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
