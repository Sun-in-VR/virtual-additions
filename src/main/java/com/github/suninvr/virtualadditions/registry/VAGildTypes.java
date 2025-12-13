package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.item.gild.*;
import com.github.suninvr.virtualadditions.item.gild.modifier.AttackRangeStackModifier;
import com.github.suninvr.virtualadditions.item.gild.modifier.AttributeStackModifier;
import com.github.suninvr.virtualadditions.item.gild.modifier.DurabilityStackModifier;
import com.github.suninvr.virtualadditions.item.gild.modifier.KineticWeaponStackModifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("unchecked")
public class VAGildTypes {
    private static final Predicate<ItemStack> BASE_WEAPONS = stack -> stack.is(ItemTags.SWORDS) || stack.is(ItemTags.AXES) || stack.is(ItemTags.SPEARS);
    private static final Predicate<ItemStack> SWORDS = stack -> stack.is(ItemTags.SWORDS);
    private static final Predicate<ItemStack> HALBERDS = stack -> stack.is(VAItemTags.HALBERDS);
    private static final Predicate<ItemStack> SPECIAL_WEAPONS = stack -> stack.is(VAItemTags.HALBERDS) || stack.is(ItemTags.AXES) || stack.is(ItemTags.SPEARS);

    public static final GildType AMETHYST = register(idOf("amethyst"), new GildType(0x9A5CC6, new KineticWeaponStackModifier(0.0F, 1.1F)));
    public static final GildType AMETHYST_WEAPON = register(idOf("amethyst_weapon"), new GildType(AMETHYST, BASE_WEAPONS, new AttributeStackModifier(Attributes.ATTACK_SPEED, -0.125F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final GildType AMETHYST_HALBERD = register(idOf("amethyst_halberd"), new GildType(AMETHYST, HALBERDS, new AttributeStackModifier(Attributes.ATTACK_SPEED, -0.05F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
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
    public static final GildType SCULK = register(idOf("sculk"), new SculkGildType(new AttributeStackModifier(Attributes.BLOCK_BREAK_SPEED, -0.6F, AttributeModifier.Operation.ADD_VALUE)));
    public static final GildType SCULK_SWORD = register(idOf("sculk_sword"), new SculkGildType(SCULK, SWORDS, new AttributeStackModifier(Attributes.ATTACK_SPEED, 0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final GildType SCULK_WEAPON = register(idOf("sculk_weapon"), new SculkGildType(SCULK, SPECIAL_WEAPONS, new AttributeStackModifier(Attributes.ATTACK_SPEED, 0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));

    public static void init(){};

    private static GildType register(Identifier id, GildType type) {
        return Registry.register(VARegistries.GILD_TYPE, id, type);
    }

    public static Object registerAndGetDefault(Registry<GildType> gildTypes) {
        return AMETHYST;
    }
}
