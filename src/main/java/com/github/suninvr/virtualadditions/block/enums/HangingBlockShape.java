package com.github.suninvr.virtualadditions.block.enums;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum HangingBlockShape implements StringIdentifiable {
    SINGLE(true),
    BASE(false),
    STRAIGHT(false),
    END(true);

    private final String name;
    private final boolean end;


    @Override
    public String asString() {
        return this.name;
    }

    HangingBlockShape(boolean b) {
        this.name = this.name().toLowerCase(Locale.ROOT);
        this.end = b;
    }

    public boolean isEnd() {
        return this.end;
    }
}
