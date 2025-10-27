package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.component.ExplosiveContentComponent;
import com.github.suninvr.virtualadditions.component.PortalCoreLocationComponent;
import com.github.suninvr.virtualadditions.item.gild.GildType;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.function.UnaryOperator;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VADataComponentTypes {

    public static final ComponentType<EffectsOnHitComponent> EFFECTS_ON_HIT = register("effects_on_hit", EffectsOnHitComponent::setCodecs);
    public static final ComponentType<ExplosiveContentComponent> EXPLOSIVE_CONTENTS = register("explosive_contents", ExplosiveContentComponent::setCodecs);
    public static final ComponentType<PortalCoreLocationComponent> PORTAL_CORE_LOCATION = register("portal_core_location", PortalCoreLocationComponent::setCodecs);
    public static final ComponentType<GildType> GILD_TYPE = register("gild_type", builder -> builder.codec(GildType.CODEC).packetCodec(GildType.PACKET_CODEC));

    public static void init(){}

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, idOf(id), builderOperator.apply(ComponentType.builder()).build());
    }
}
