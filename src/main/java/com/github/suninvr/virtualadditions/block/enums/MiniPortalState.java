package com.github.suninvr.virtualadditions.block.enums;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum MiniPortalState implements StringIdentifiable {
    OPEN(true),
    BLOCKED,
    COOLDOWN;
    
    public final boolean allowsTeleporting;

    MiniPortalState(boolean allowsTeleporting) {
        this.allowsTeleporting = allowsTeleporting;
    }
    
    MiniPortalState() {
        this.allowsTeleporting = false;
    }

    @Override
    public String asString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
