package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public interface VADamageTypes {
    ResourceKey<DamageType> ACID = ResourceKey.create(Registries.DAMAGE_TYPE, idOf("acid"));
    ResourceKey<DamageType> ACID_SPIT = ResourceKey.create(Registries.DAMAGE_TYPE, idOf("acid_spit"));
    ResourceKey<DamageType> SOUL_DESTROYED = ResourceKey.create(Registries.DAMAGE_TYPE, idOf("soul_destroyed"));

    TagKey<DamageType> INCREASED_ARMOR_DAMAGE = TagKey.create(Registries.DAMAGE_TYPE, idOf("increased_armor_damage"));
    TagKey<DamageType> ACID_TAG = TagKey.create(Registries.DAMAGE_TYPE, idOf("acid"));

    static void init(){}

}
