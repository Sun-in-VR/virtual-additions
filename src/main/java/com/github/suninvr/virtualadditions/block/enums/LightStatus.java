package com.github.suninvr.virtualadditions.block.enums;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum LightStatus implements StringIdentifiable {
    LIT,
    UNLIT,
    NONE
    ;

    @Override
    public String asString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public boolean hasLight() {
        return !this.equals(NONE);
    }
}
