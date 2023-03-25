package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.Block;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public interface VADamageTypes {
    RegistryKey<DamageType> ACID = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, idOf("acid"));
    public static final TagKey<DamageType> INCREASED_ARMOR_DAMAGE = TagKey.of(RegistryKeys.DAMAGE_TYPE, idOf("increased_armor_damage"));

    public static void init(){};

}
