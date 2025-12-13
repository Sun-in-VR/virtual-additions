package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.item.gild.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("unchecked")
public class VAGildTypes {

    public static final GildType AMETHYST = register(idOf("amethyst"), new GildType(0x9A5CC6,
            new AttributeStackModifier(Attributes.ATTACK_SPEED, -0.125F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, ItemTags.SWORDS, ItemTags.AXES, ItemTags.SPEARS),
            new AttributeStackModifier(Attributes.ATTACK_SPEED, -0.05F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, VAItemTags.HALBERDS),
            new KineticWeaponStackModifier(0.0F, 1.1F)
    ));
    public static final GildType COPPER = register(idOf("copper"), new GildType(0xB4684D, new DurabilityStackModifier(1.5F)));
    public static final GildType EMERALD = register(idOf("emerald"), new GildType(0x11A036));
    public static final GildType IOLITE = register(idOf("iolite"), new GildType(0xc24ed7,
            new AttributeStackModifier(Attributes.BLOCK_INTERACTION_RANGE, 3.0F, AttributeModifier.Operation.ADD_VALUE),
            new AttributeStackModifier(Attributes.ENTITY_INTERACTION_RANGE, 0.5F, AttributeModifier.Operation.ADD_VALUE),
            new AttackRangeStackModifier(1.0F)
    ));
    public static final GildType QUARTZ = register(idOf("quartz"), new GildType(0xE3D4C4,
            new AttributeStackModifier(Attributes.ATTACK_DAMAGE, 2.0F, AttributeModifier.Operation.ADD_VALUE),
            new KineticWeaponStackModifier(0.15F, 1.0F)
    ));
    public static final GildType SCULK = register(idOf("sculk"), new SculkGildType(
            new AttributeStackModifier(Attributes.ATTACK_SPEED, 0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, ItemTags.SWORDS),
            new AttributeStackModifier(Attributes.ATTACK_SPEED, 0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, ItemTags.SPEARS, ItemTags.AXES, VAItemTags.HALBERDS),
            new AttributeStackModifier(Attributes.BLOCK_BREAK_SPEED, -0.6F, AttributeModifier.Operation.ADD_VALUE)
    ));

    public static void init(){};

    private static GildType register(Identifier id, GildType type) {
        return Registry.register(VARegistries.GILD_TYPE, id, type);
    }

    public static Object registerAndGetDefault(Registry<GildType> gildTypes) {
        return AMETHYST;
    }
}
