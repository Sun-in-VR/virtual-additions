package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(DefaultAttributes.class)
public class DefaultAttributeRegistryMixin {
    @Shadow @Final @Mutable
    private static Map<EntityType<? extends LivingEntity>, AttributeSupplier> SUPPLIERS;

    static {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier> newMap = new java.util.HashMap<>(Map.copyOf(SUPPLIERS));
        newMap.putAll(VAEntityType.ENTITY_ATTRIBUTES);
        SUPPLIERS = ImmutableMap.copyOf(newMap);
    }
}
