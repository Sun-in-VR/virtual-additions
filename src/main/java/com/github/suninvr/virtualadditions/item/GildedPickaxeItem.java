package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GildedPickaxeItem extends PickaxeItem implements GildedToolItem {
    private final GildType gildType;
    private final ToolMaterial toolMaterial;
    private final ToolMaterial baseMaterial;
    private final Item baseItem;
    private static final Text descriptionHeader = Text.translatable("item.minecraft.smithing_template.upgrade").formatted(Formatting.GRAY);
    private final Text descriptionText;
    private final Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributeModifiers;

    public GildedPickaxeItem(GildType gildType, PickaxeItem baseItem, Settings settings) {
        super(gildType.getModifiedMaterial(baseItem), settings);
        this.gildType = gildType;
        this.toolMaterial = gildType.getModifiedMaterial(baseItem);
        this.baseMaterial = baseItem.getMaterial();
        this.baseItem = baseItem;
        this.descriptionText = ScreenTexts.space().append(Text.translatable(this.gildType.buildTooltipTranslationKey()).setStyle(Style.EMPTY.withColor(this.gildType.getColor())));
        ImmutableMultimap.Builder<RegistryEntry<EntityAttribute>, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        super.getAttributeModifiers(EquipmentSlot.MAINHAND).get(EntityAttributes.GENERIC_ATTACK_DAMAGE).forEach(entityAttributeModifier -> builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, entityAttributeModifier));
        super.getAttributeModifiers(EquipmentSlot.MAINHAND).get(EntityAttributes.GENERIC_ATTACK_SPEED).forEach(entityAttributeModifier -> builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, entityAttributeModifier));
        gildType.appendAttributeModifiers(builder);
        attributeModifiers = builder.build();
    }

    private static double getAttackSpeed(ToolItem baseItem, GildType gildType) {
        return gildType.getModifiedAttackSpeed(baseItem);
    }

    @Override
    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
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
        tooltip.add(descriptionHeader);
        tooltip.add(this.descriptionText);
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public String getTranslationKey() {
        return baseItem.getTranslationKey();
    }

    @Override
    public Item getBaseItem() {
        return baseItem;
    }
}
