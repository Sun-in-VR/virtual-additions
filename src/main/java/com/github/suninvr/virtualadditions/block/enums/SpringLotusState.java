package com.github.suninvr.virtualadditions.block.enums;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum SpringLotusState implements StringRepresentable {
    IDLE,
    PUSHING,
    OVER_COMPRESSED
    ;

    private final String name;

    @Override
    public String getSerializedName() {
        return this.name;
    }

    SpringLotusState() {
        this.name = this.name().toLowerCase(Locale.ROOT);
    }
}
