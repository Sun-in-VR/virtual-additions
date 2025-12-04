package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.component.ExplosiveContentComponent;
import com.github.suninvr.virtualadditions.component.IncenseEffectsComponent;
import com.github.suninvr.virtualadditions.component.PortalCoreLocationComponent;
import com.github.suninvr.virtualadditions.item.gild.GildType;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.UnaryOperator;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VADataComponentTypes {

    public static final DataComponentType<EffectsOnHitComponent> EFFECTS_ON_HIT = register("effects_on_hit", EffectsOnHitComponent::setCodecs);
    public static final DataComponentType<ExplosiveContentComponent> EXPLOSIVE_CONTENTS = register("explosive_contents", ExplosiveContentComponent::setCodecs);
    public static final DataComponentType<PortalCoreLocationComponent> PORTAL_CORE_LOCATION = register("portal_core_location", PortalCoreLocationComponent::setCodecs);
    public static final DataComponentType<IncenseEffectsComponent> INCENSE_EFFECTS = register("incense_effects", IncenseEffectsComponent::setCodecs);
    public static final DataComponentType<GildType> GILD_TYPE = register("gild_type", builder -> builder.persistent(GildType.CODEC).networkSynchronized(GildType.PACKET_CODEC));

    public static void init(){}

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, idOf(id), builderOperator.apply(DataComponentType.builder()).build());
    }
}
