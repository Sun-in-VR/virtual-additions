package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAToolUtil {

    public static Item.Properties halberdSettings(Item.Properties settings, ToolMaterial material, float attackDamage, float attackSpeed) {
        return material
                .applySwordProperties(settings, attackDamage, attackSpeed)
                .attributes(halberdAttributes(attackDamage, attackSpeed))
                .component(DataComponents.BLOCKS_ATTACKS, new BlocksAttacks(0.0F, 0.0F, List.of(), BlocksAttacks.ItemDamageFunction.DEFAULT, Optional.empty(), Optional.empty(), Optional.empty()))
				.component(DataComponents.MINIMUM_ATTACK_CHARGE, 0.25F);
    }

    public static ItemAttributeModifiers halberdAttributes(float attackDamage, float attackSpeed) {
        AttributeModifier reach = new AttributeModifier(idOf("halberd_reach"), 1, AttributeModifier.Operation.ADD_VALUE);
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE, reach, EquipmentSlotGroup.MAINHAND,
                        new ItemAttributeModifiers.Display.OverrideText(CommonComponents.space().append(
                                        Component.translatable("attribute.modifier.equals." + reach.operation().id(),
                                                ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(reach.amount()),
                                                Component.translatable(Attributes.ENTITY_INTERACTION_RANGE.value().getDescriptionId())
                                        ).withStyle(ChatFormatting.DARK_GREEN)
                        )))
                .build();
    }

    @Nullable
    public static GildType getGildType(ItemStack itemStack) {
        return itemStack.has(VADataComponentTypes.GILD_TYPE) ? itemStack.get(VADataComponentTypes.GILD_TYPE) : null;
    }
}
