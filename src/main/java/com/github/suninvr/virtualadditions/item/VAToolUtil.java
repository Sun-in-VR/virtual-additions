package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAToolUtil {

    public static Item.Settings halberdSettings(Item.Settings settings, ToolMaterial material, float attackDamage, float attackSpeed) {
        return material
                .applySwordSettings(settings, attackDamage, attackSpeed)
                .attributeModifiers(halberdAttributes(attackDamage, attackSpeed))
                .component(DataComponentTypes.BLOCKS_ATTACKS, new BlocksAttacksComponent(0.0F, 0.0F, List.of(), BlocksAttacksComponent.ItemDamage.DEFAULT, Optional.empty(), Optional.empty(), Optional.empty()))
				.component(DataComponentTypes.MINIMUM_ATTACK_CHARGE, 0.25F);
    }

    public static AttributeModifiersComponent halberdAttributes(float attackDamage, float attackSpeed) {
        EntityAttributeModifier reach = new EntityAttributeModifier(idOf("halberd_reach"), 1, EntityAttributeModifier.Operation.ADD_VALUE);
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, reach, AttributeModifierSlot.MAINHAND,
                        new AttributeModifiersComponent.Display.Override(ScreenTexts.space().append(
                                        Text.translatable("attribute.modifier.equals." + reach.operation().getId(),
                                                AttributeModifiersComponent.DECIMAL_FORMAT.format(reach.value()),
                                                Text.translatable(EntityAttributes.ENTITY_INTERACTION_RANGE.value().getTranslationKey())
                                        ).formatted(Formatting.DARK_GREEN)
                        )))
                .build();
    }

    @Nullable
    public static GildType getGildType(ItemStack itemStack) {
        return itemStack.contains(VADataComponentTypes.GILD_TYPE) ? itemStack.get(VADataComponentTypes.GILD_TYPE) : null;
    }
}
