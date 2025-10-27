package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.item.gild.AttributeStackModifier;
import com.github.suninvr.virtualadditions.item.gild.DurabilityStackModifier;
import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.item.gild.SculkGildType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("unchecked")
public class VAGildTypes {

    public static final GildType AMETHYST = register(idOf("amethyst"), new GildType(0x9A5CC6, new AttributeStackModifier(EntityAttributes.ATTACK_SPEED, -0.125F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE, ItemTags.SWORDS, ItemTags.AXES, ItemTags.SPEARS), new AttributeStackModifier(EntityAttributes.ATTACK_SPEED, -0.05F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE, VAItemTags.HALBERDS)));
    public static final GildType COPPER = register(idOf("copper"), new GildType(0xB4684D, new DurabilityStackModifier(1.5F)));
    public static final GildType EMERALD = register(idOf("emerald"), new GildType(0x11A036));
    public static final GildType IOLITE = register(idOf("iolite"), new GildType(0xc24ed7, new AttributeStackModifier(EntityAttributes.BLOCK_INTERACTION_RANGE, 3.0F, EntityAttributeModifier.Operation.ADD_VALUE), new AttributeStackModifier(EntityAttributes.ENTITY_INTERACTION_RANGE, 0.5F, EntityAttributeModifier.Operation.ADD_VALUE)));
    public static final GildType QUARTZ = register(idOf("quartz"), new GildType(0xE3D4C4, new AttributeStackModifier(EntityAttributes.ATTACK_DAMAGE, 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)));
    public static final GildType SCULK = register(idOf("sculk"), new SculkGildType(
            new AttributeStackModifier(EntityAttributes.ATTACK_SPEED, 0.2F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE, ItemTags.SWORDS),
            new AttributeStackModifier(EntityAttributes.ATTACK_SPEED, 0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE, ItemTags.SPEARS, ItemTags.AXES, VAItemTags.HALBERDS),
            new AttributeStackModifier(EntityAttributes.BLOCK_BREAK_SPEED, -0.6F, EntityAttributeModifier.Operation.ADD_VALUE)
    ));

    public static void init(){};

    private static GildType register(Identifier id, GildType type) {
        return Registry.register(VARegistries.GILD_TYPE, id, type);
    }

    public static Object registerAndGetDefault(Registry<GildType> gildTypes) {
        return AMETHYST;
    }
}
