package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAEntityType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(DefaultAttributeRegistry.class)
public class DefaultAttributeRegistryMixin {
    @Shadow @Final @Mutable
    private static Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> DEFAULT_ATTRIBUTE_REGISTRY;

    static {
        Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> newMap = new java.util.HashMap<>(Map.copyOf(DEFAULT_ATTRIBUTE_REGISTRY));
        newMap.putAll(VAEntityType.ENTITY_ATTRIBUTES);
        DEFAULT_ATTRIBUTE_REGISTRY = ImmutableMap.copyOf(newMap);
    }
}
