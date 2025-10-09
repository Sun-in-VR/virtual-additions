package com.github.suninvr.virtualadditions.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public class VAItemTags {
    public static final TagKey<Item> LUMWASP_LARVAE_FOOD = register("lumwasp_larvae_food");
    public static final TagKey<Item> ACID_RESISTANT = register("acid_resistant");
    public static final TagKey<Item> GILDED_TOOLS = TagKey.of(RegistryKeys.ITEM, idOf("gilded_tools"));
    public static final TagKey<Item> SOULBLOOM_LOGS = TagKey.of(RegistryKeys.ITEM, idOf("soulbloom_logs"));
    public static final TagKey<Item> WITHERED_LOGS = TagKey.of(RegistryKeys.ITEM, idOf("withered_logs"));
    public static final TagKey<Item> SILKBULBS = TagKey.of(RegistryKeys.ITEM, idOf("silkbulbs"));
    public static final TagKey<Item> BASE_DYE = TagKey.of(RegistryKeys.ITEM, idOf("base_dye"));
    public static final TagKey<Item> COLORABLE_GLASS = TagKey.of(RegistryKeys.ITEM, idOf("colorable_glass"));
    public static final TagKey<Item> COLORABLE_GLASS_PANE = TagKey.of(RegistryKeys.ITEM, idOf("colorable_glass_pane"));
    public static final TagKey<Item> CLIMBING_ROPES = TagKey.of(RegistryKeys.ITEM, idOf("climbing_ropes"));
    public static final TagKey<Item> REPAIRS_STEEL_ARMOR = TagKey.of(RegistryKeys.ITEM, idOf("repairs_steel_armor"));
    public static final TagKey<Item> ROCK_SALT_ORES = TagKey.of(RegistryKeys.ITEM, idOf("rock_salt_ores"));
    public static final TagKey<Item> ACCEPTS_APPLIED_EFFECTS = TagKey.of(RegistryKeys.ITEM, idOf("accepts_applied_effects"));
    public static final TagKey<Item> HALBERDS = TagKey.of(RegistryKeys.ITEM, idOf("halberds"));
    public static final TagKey<Item> HALBERD_ENCHANTABLE = TagKey.of(RegistryKeys.ITEM, idOf("enchantable/halberd"));
    public static final TagKey<Item> PUMMELING_ENCHANTABLE = TagKey.of(RegistryKeys.ITEM, idOf("enchantable/pummeling"));

    private static TagKey<Item> register(String id) {
        return TagKey.of(Registries.ITEM.getKey(), idOf(id));
    }

    public static void init(){}

}
