package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionContents;

public class ApplicablePotionItem extends PotionItem {
    public ApplicablePotionItem(net.minecraft.world.item.Item.Properties settings) {
        super(settings);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickType, Player player) {
        if(clickType == ClickAction.SECONDARY) {
            ItemStack clickedStack = slot.getItem();
            boolean bl = false;
            if(clickedStack.is(VAItemTags.ACCEPTS_APPLIED_EFFECTS)) {
                PotionContents potionComponent = stack.get(DataComponents.POTION_CONTENTS);
                if (potionComponent != null) {
                    int maxUses = 30;
                    clickedStack.set(VADataComponentTypes.EFFECTS_ON_HIT, new EffectsOnHitComponent(potionComponent, maxUses, maxUses));
                    bl = true;
                }

            } else if (clickedStack.is(Items.ARROW)) {
                ItemStack tippedArrowStack = Items.TIPPED_ARROW.getDefaultInstance();
                tippedArrowStack.set(DataComponents.POTION_CONTENTS, stack.get(DataComponents.POTION_CONTENTS));
                if(clickedStack.getCount() <= 10) {
                    tippedArrowStack.setCount(clickedStack.getCount());
                    slot.set(tippedArrowStack);
                } else {
                    tippedArrowStack.setCount(10);
                    clickedStack.shrink(10);
                    player.getInventory().placeItemBackInInventory(tippedArrowStack);
                }
                bl = true;
            }
            if (bl) {
                player.playSound(SoundEvents.BOTTLE_EMPTY, 0.3F, 1.4F);
                stack.shrink(1);
            }
            return bl;
        }
        return false;
    }

    //@Override
    //public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
    //    PotionContentsComponent potionContentsComponent = stack.get(DataComponentTypes.POTION_CONTENTS);
    //    if (potionContentsComponent != null) {
    //        Objects.requireNonNull(textConsumer);
    //        potionContentsComponent.appendTooltip(context, textConsumer, type, displayComponent);
    //    }
    //    super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    //}
}
