package com.github.suninvr.virtualadditions.block.enums;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum LightStatus implements StringRepresentable {
    LIT,
    UNLIT,
    NONE
    ;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public boolean hasLight() {
        return !this.equals(NONE);
    }
}
