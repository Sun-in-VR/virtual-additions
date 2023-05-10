package com.github.suninvr.virtualadditions.registry;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public interface VADamageTypes {
    RegistryKey<DamageType> ACID = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, idOf("acid"));
    RegistryKey<DamageType> ACID_SPIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, idOf("acid_spit"));

    TagKey<DamageType> INCREASED_ARMOR_DAMAGE = TagKey.of(RegistryKeys.DAMAGE_TYPE, idOf("increased_armor_damage"));

    static void init(){}

}
