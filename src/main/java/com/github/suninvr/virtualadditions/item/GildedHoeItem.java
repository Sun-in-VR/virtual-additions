package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.interfaces.MiningToolItemInterface;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class GildedHoeItem extends HoeItem implements GildedToolItem {
    private final GildType gildType;
    private final Item baseItem;
    private static final Text descriptionHeader = Text.translatable("item.minecraft.smithing_template.upgrade").formatted(Formatting.GRAY);
    private final Text descriptionText;

    public GildedHoeItem(GildType gildType, ToolMaterial baseMaterial, HoeItem baseItem, Settings settings) {
        super(gildType.getModifiedMaterial(baseMaterial).asToolMaterial(), ((MiningToolItemInterface)baseItem).getAttackDamage(), ((MiningToolItemInterface)baseItem).getAttackSpeed(), settings);
        this.gildType = gildType;
        this.baseItem = baseItem;
        this.descriptionText = ScreenTexts.space().append(Text.translatable(this.gildType.buildTooltipTranslationKey()).setStyle(Style.EMPTY.withColor(this.gildType.getColor())));
    }

    @Override
    public GildType getGildType() {
        return this.gildType;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(descriptionHeader);
        tooltip.add(this.descriptionText);
        super.appendTooltip(stack, context, tooltip, type);
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
