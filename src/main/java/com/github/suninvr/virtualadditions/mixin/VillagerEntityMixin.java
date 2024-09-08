package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {

    @Shadow @Final @Mutable
    private static Map<Item, Integer> ITEM_FOOD_VALUES;

    @Unique
    private static final Map<Item, Integer> virtualAddition$ITEM_FOOD_VALUES = ImmutableMap.of(VAItems.CORN, 1);

    static {
        ArrayList<Item> newItemFoodKeys = new ArrayList<>(ITEM_FOOD_VALUES.keySet());
        ArrayList<Integer> newItemFoodValues = new ArrayList<>(ITEM_FOOD_VALUES.values());
        newItemFoodKeys.addAll(virtualAddition$ITEM_FOOD_VALUES.keySet());
        newItemFoodValues.addAll(virtualAddition$ITEM_FOOD_VALUES.values());
        ArrayList<Map.Entry<Item, Integer>> entries = new ArrayList<>();
        int i = 0;
        while (i < newItemFoodKeys.size()) {
            entries.add(Map.entry(newItemFoodKeys.get(i), newItemFoodValues.get(i)));
            i++;
        }
        ITEM_FOOD_VALUES = ImmutableMap.ofEntries(entries.toArray(new Map.Entry[]{}));
    }
}
