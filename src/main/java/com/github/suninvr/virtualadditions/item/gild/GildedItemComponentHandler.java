package com.github.suninvr.virtualadditions.item.gild;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class GildedItemComponentHandler {
    private static Map<DataComponentMap, Map<GildType, DataComponentMap>> GILDED_TOOL_COMPONENT_MAPS = new HashMap<>();

    private static Map<GildType, DataComponentMap> getOrCreateItemMap(DataComponentMap itemMap) {
        Map<GildType, DataComponentMap> map;
        if (GILDED_TOOL_COMPONENT_MAPS.containsKey(itemMap)) map = GILDED_TOOL_COMPONENT_MAPS.get(itemMap);
        else {
            map = new HashMap<>();
            GILDED_TOOL_COMPONENT_MAPS.put(itemMap, map);
        }
        return map;
    }

    public static DataComponentMap getOrCompute(Item item, GildType type) {
        return getOrCompute(item.components(), type);
    }

    public static DataComponentMap getOrCompute(DataComponentMap map, GildType type) {
        Map<GildType, DataComponentMap> inner = getOrCreateItemMap(map);
        if (!inner.containsKey(type)) computeMap(map, type);
        return inner.get(type);
    }

    private static void computeMap(DataComponentMap itemMap, GildType type) {
        Map<GildType, DataComponentMap> map = getOrCreateItemMap(itemMap);
        DataComponentMap.Builder components = DataComponentMap.builder().addAll(itemMap);
        type.modifyDataComponents(components);
        map.put(type, components.build());
    }
}
