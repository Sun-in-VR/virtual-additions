package com.github.suninvr.virtualadditions.block.enums;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum MembraneStatus implements StringIdentifiable {
    STARTING,
    EXPANDING,
    STOPPED,
    DYING;

    private final String name;

    @Override
    public String asString() {
        return this.name;
    }

    MembraneStatus() {
        this.name = this.name().toLowerCase(Locale.ROOT);
    }
}
