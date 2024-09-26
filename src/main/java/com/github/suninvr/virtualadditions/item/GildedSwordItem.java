package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.interfaces.MiningToolItemInterface;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class GildedSwordItem extends Item implements GildedToolItem {
    private final GildType gildType;
    private final Item baseItem;
    private static final Text descriptionHeader = Text.translatable("item.minecraft.smithing_template.upgrade").formatted(Formatting.GRAY);
    private final Text descriptionText;

    public GildedSwordItem(GildType gildType, ToolMaterial baseMaterial, SwordItem baseItem, Settings settings) {
        super(GildedToolUtil.settingsOf(gildType.getModifiedMaterial(baseMaterial).asToolMaterial().applySwordSettings(settings, ((MiningToolItemInterface)baseItem).getAttackDamage(), ((MiningToolItemInterface)baseItem).getAttackSpeed()), baseItem, gildType));
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
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public Item getBaseItem() {
        return baseItem;
    }
}
