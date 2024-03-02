package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.component.EffectsOnHitComponent;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ApplicablePotionItem extends PotionItem {
    public ApplicablePotionItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if(clickType == ClickType.RIGHT) {
            ItemStack clickedStack = slot.getStack();
            boolean bl = false;
            if(clickedStack.getItem() instanceof SwordItem || clickedStack.getItem() instanceof AxeItem) {
                PotionContentsComponent potionComponent = stack.get(DataComponentTypes.POTION_CONTENTS);
                if (potionComponent != null) {
                    int maxUses = 30; //potionComponent.potion().isPresent() ? potionComponent.potion().get().
                    clickedStack.set(VADataComponentTypes.EFFECTS_ON_HIT, new EffectsOnHitComponent(potionComponent, maxUses, maxUses));
                    bl = true;
                }

            } else if (clickedStack.isOf(Items.ARROW)) {
                ItemStack tippedArrowStack = Items.TIPPED_ARROW.getDefaultStack();
                tippedArrowStack.set(DataComponentTypes.POTION_CONTENTS, stack.get(DataComponentTypes.POTION_CONTENTS));
                if(clickedStack.getCount() <= 10) {
                    tippedArrowStack.setCount(clickedStack.getCount());
                    slot.setStackNoCallbacks(tippedArrowStack);
                } else {
                    tippedArrowStack.setCount(10);
                    clickedStack.decrement(10);
                    player.getInventory().offerOrDrop(tippedArrowStack);
                }
                bl = true;
            }
            if (bl) {
                player.playSound(SoundEvents.ITEM_BOTTLE_EMPTY, 0.3F, 1.4F);
                stack.decrement(1);
            }
            return bl;
        }
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        PotionContentsComponent potionContentsComponent = stack.get(DataComponentTypes.POTION_CONTENTS);
        if (potionContentsComponent != null) {
            Objects.requireNonNull(tooltip);
            potionContentsComponent.buildTooltip(tooltip::add, 0.125F, world == null ? 20.0F : world.getTickManager().getTickRate());
        }
    }
}
