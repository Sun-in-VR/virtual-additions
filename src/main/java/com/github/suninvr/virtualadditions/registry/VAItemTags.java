package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public class VAItemTags {
    public static final TagKey<Item> LUMWASP_LARVAE_FOOD = register("lumwasp_larvae_food");
    public static final TagKey<Item> ACID_RESISTANT = register("acid_resistant");
    public static final TagKey<Item> GILDED_TOOLS = TagKey.create(Registries.ITEM, idOf("gilded_tools"));
    public static final TagKey<Item> SOULBLOOM_LOGS = TagKey.create(Registries.ITEM, idOf("soulbloom_logs"));
    public static final TagKey<Item> WITHERED_LOGS = TagKey.create(Registries.ITEM, idOf("withered_logs"));
    public static final TagKey<Item> SILKBULBS = TagKey.create(Registries.ITEM, idOf("silkbulbs"));
    public static final TagKey<Item> BASE_DYE = TagKey.create(Registries.ITEM, idOf("base_dye"));
    public static final TagKey<Item> COLORABLE_GLASS = TagKey.create(Registries.ITEM, idOf("colorable_glass"));
    public static final TagKey<Item> COLORABLE_GLASS_PANE = TagKey.create(Registries.ITEM, idOf("colorable_glass_pane"));
    public static final TagKey<Item> CLIMBING_ROPES = TagKey.create(Registries.ITEM, idOf("climbing_ropes"));
    public static final TagKey<Item> REPAIRS_STEEL_ARMOR = TagKey.create(Registries.ITEM, idOf("repairs_steel_armor"));
    public static final TagKey<Item> ROCK_SALT_ORES = TagKey.create(Registries.ITEM, idOf("rock_salt_ores"));
    public static final TagKey<Item> ACCEPTS_APPLIED_EFFECTS = TagKey.create(Registries.ITEM, idOf("accepts_applied_effects"));
    public static final TagKey<Item> ACCEPTS_TOOL_GILDS = TagKey.create(Registries.ITEM, idOf("accepts_tool_gilds"));
    public static final TagKey<Item> HALBERDS = TagKey.create(Registries.ITEM, idOf("halberds"));
    public static final TagKey<Item> HALBERD_ENCHANTABLE = TagKey.create(Registries.ITEM, idOf("enchantable/halberd"));
    public static final TagKey<Item> PUMMELING_ENCHANTABLE = TagKey.create(Registries.ITEM, idOf("enchantable/pummeling"));

    private static TagKey<Item> register(String id) {
        return TagKey.create(BuiltInRegistries.ITEM.key(), idOf(id));
    }

    public static void init(){}

}
