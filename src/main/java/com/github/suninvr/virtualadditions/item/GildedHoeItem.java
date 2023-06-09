package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GildedHoeItem extends HoeItem implements GildedToolItem {
    private final GildType gildType;
    private final ToolMaterial toolMaterial;
    private final ToolMaterial baseMaterial;
    private final Item baseItem;
    private static final Text descriptionHeader = Text.translatable("item.minecraft.smithing_template.upgrade").formatted(Formatting.GRAY);
    private final Text descriptionText;

    public GildedHoeItem(GildType gildType, HoeItem baseItem, Settings settings) {
        super(gildType.getModifiedMaterial(baseItem), (int) (baseItem.getAttackDamage() - baseItem.getMaterial().getAttackDamage()), (float) getAttackSpeed(baseItem, gildType), settings);
        this.gildType = gildType;
        this.toolMaterial = gildType.getModifiedMaterial(baseItem);
        this.baseMaterial = baseItem.getMaterial();
        this.baseItem = baseItem;
        this.descriptionText = ScreenTexts.space().append(Text.translatable(this.gildType.buildTooltipTranslationKey()).setStyle(Style.EMPTY.withColor(this.gildType.getColor())));
    }

    private static double getAttackSpeed(ToolItem baseItem, GildType gildType) {
        return gildType.getModifiedAttackSpeed(baseItem);
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
