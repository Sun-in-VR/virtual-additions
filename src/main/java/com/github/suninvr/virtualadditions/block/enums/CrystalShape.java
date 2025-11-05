package com.github.suninvr.virtualadditions.block.enums;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum CrystalShape implements StringRepresentable {
    TIP,
    BODY;

    private final String name;

    @Override
    public String getSerializedName() {
        return this.name;
    }

    CrystalShape() {
        this.name = this.name().toLowerCase(Locale.ROOT);
    }
}
