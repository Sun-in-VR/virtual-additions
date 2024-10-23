package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.interfaces.MiningToolItemInterface;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
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

public class GildedShovelItem extends Item implements GildedToolItem {
    private final GildType gildType;
    private final Item baseItem;
    private static final Text descriptionHeader = Text.translatable("item.minecraft.smithing_template.upgrade").formatted(Formatting.GRAY);
    private final Text descriptionText;

    public GildedShovelItem(GildType gildType, ToolMaterial baseMaterial, ShovelItem baseItem, Settings settings) {
        super(GildedToolUtil.settingsOf(gildType.getModifiedMaterial(baseMaterial).asToolMaterial().applyToolSettings(settings, BlockTags.SHOVEL_MINEABLE, ((MiningToolItemInterface)baseItem).getAttackDamage(), ((MiningToolItemInterface)baseItem).getAttackSpeed()), baseItem, gildType));
        this.gildType = gildType;
        this.baseItem = baseItem;
        this.descriptionText = ScreenTexts.space().append(Text.translatable(this.gildType.buildTooltipTranslationKey()).setStyle(Style.EMPTY.withColor(this.gildType.getColor())));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return this.baseItem.useOnBlock(context);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
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
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(descriptionHeader);
        tooltip.add(this.descriptionText);
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public Item getBaseItem() {
        return baseItem;
    }
}
