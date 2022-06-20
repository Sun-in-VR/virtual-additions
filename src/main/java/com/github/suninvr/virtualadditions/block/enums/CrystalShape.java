package com.github.suninvr.virtualadditions.block.enums;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum CrystalShape implements StringIdentifiable {
    TIP,
    BODY;

    private final String name;

    @Override
    public String asString() {
        return this.name;
    }

    CrystalShape() {
        this.name = this.name().toLowerCase(Locale.ROOT);
    }
}
