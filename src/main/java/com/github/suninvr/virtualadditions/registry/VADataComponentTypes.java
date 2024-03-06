package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.component.ExplosiveContentComponent;
import com.github.suninvr.virtualadditions.component.WarpTetherLocationComponent;
import net.minecraft.component.DataComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VADataComponentTypes {

    public static final DataComponentType<EffectsOnHitComponent> EFFECTS_ON_HIT = register("effects_on_hit", EffectsOnHitComponent::setCodecs);
    public static final DataComponentType<ExplosiveContentComponent> EXPLOSIVE_CONTENTS = register("explosive_contents", ExplosiveContentComponent::setCodecs);
    public static final DataComponentType<WarpTetherLocationComponent> WARP_TETHER_LOCATION = register("warp_tether_location", WarpTetherLocationComponent::setCodecs);

    public static void init(){}

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, idOf(id), builderOperator.apply(DataComponentType.builder()).build());
    }
}