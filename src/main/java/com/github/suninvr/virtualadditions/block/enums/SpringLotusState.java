package com.github.suninvr.virtualadditions.block.enums;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum SpringLotusState implements StringIdentifiable {
    IDLE,
    PUSHING,
    OVER_COMPRESSED
    ;

    private final String name;

    @Override
    public String asString() {
        return this.name;
    }

    SpringLotusState() {
        this.name = this.name().toLowerCase(Locale.ROOT);
    }
}
