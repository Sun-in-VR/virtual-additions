package com.github.suninvr.virtualadditions.command;

import com.github.suninvr.virtualadditions.item.enums.GildType;
import com.mojang.serialization.Codec;
import net.minecraft.command.argument.EnumArgumentType;

import java.util.function.Supplier;

public class GildTypeArgumentType extends EnumArgumentType<GildType> {
    public GildTypeArgumentType() {
        super(GildType.CODEC, GildType::values);
    }
}
