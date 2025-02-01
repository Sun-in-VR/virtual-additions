package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.function.Consumer;

public class GildedHoeItem extends Item implements GildedToolItem {
    private final GildType gildType;
    private final Item baseItem;
    private static final Text descriptionHeader = Text.translatable("item.minecraft.smithing_template.upgrade").formatted(Formatting.GRAY);
    private final Text descriptionText;

    public GildedHoeItem(GildType gildType, ToolMaterial baseMaterial, HoeItem baseItem, Settings settings) {
        super(GildedToolUtil.settingsOf(gildType.getModifiedMaterial(baseMaterial).asToolMaterial().applyToolSettings(settings, BlockTags.HOE_MINEABLE, baseMaterial.attackDamageBonus(), baseMaterial.speed(), 0.0F), baseItem, gildType));
        this.gildType = gildType;
        this.baseItem = baseItem;
        this.descriptionText = ScreenTexts.space().append(Text.translatable(this.gildType.buildTooltipTranslationKey()).setStyle(Style.EMPTY.withColor(this.gildType.getColor())));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return this.baseItem.useOnBlock(context);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(2, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public GildType getGildType() {
        return this.gildType;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(descriptionHeader);
        textConsumer.accept(this.descriptionText);
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }

    @Override
    public Item getBaseItem() {
        return baseItem;
    }
}
