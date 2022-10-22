package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.enums.GildType;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GildedShovelItem extends ShovelItem implements GildedToolItem {
    private final GildType gildType;
    private final ToolMaterial toolMaterial;
    private final ToolMaterial baseMaterial;
    private final Item baseItem;

    public GildedShovelItem(GildType gildType, ShovelItem baseItem, Settings settings) {
        super(gildType.getModifiedMaterial(baseItem), (int) (baseItem.getAttackDamage() - baseItem.getMaterial().getAttackDamage()), (float) getAttackSpeed(baseItem, gildType), settings);
        this.gildType = gildType;
        this.toolMaterial = gildType.getModifiedMaterial(baseItem);
        this.baseMaterial = baseItem.getMaterial();
        this.baseItem = baseItem;
    }

    private static double getAttackSpeed(ToolItem baseItem, GildType gildType) {
        double attackSpeed = baseItem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(EntityAttributes.GENERIC_ATTACK_SPEED).stream().mapToDouble(EntityAttributeModifier::getValue).sum();
        if (gildType == GildType.AMETHYST) attackSpeed *= 0.8;
        return attackSpeed;
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    public ToolMaterial getBaseMaterial() {
        return baseMaterial;
    }

    @Override
    public GildType getGildType() {
        return this.gildType;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(this.gildType.buildTooltipTranslationKey()));
    }

    @Override
    public String getTranslationKey() {
        return baseItem.getTranslationKey();
    }
}
