package com.github.suninvr.virtualadditions.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
            if(clickedStack.getItem() instanceof SwordItem || clickedStack.getItem() instanceof AxeItem) {

                NbtCompound clickedStacknbt = clickedStack.getOrCreateNbt();
                NbtCompound stackNbt = stack.getOrCreateNbt();
                NbtCompound appliedPotionNbt = new NbtCompound();
                Potion potion = PotionUtil.getPotion(stack).value();
                player.playSound(SoundEvents.ITEM_BOTTLE_EMPTY, 0.3F, 1.4F);

                if (stackNbt.contains("Potion")) appliedPotionNbt.putString("Potion", stackNbt.getString("Potion"));
                if (stackNbt.contains("CustomPotionEffects")) appliedPotionNbt.put("CustomPotionEffects", stackNbt.get("CustomPotionEffects"));
                if (stackNbt.contains("CustomPotionColor")) appliedPotionNbt.put("CustomPotionColor", stackNbt.get("CustomPotionColor"));
                int uses = stack.getOrCreateNbt().getInt("Uses");
                if (uses == 0) uses = potion.hasInstantEffect() ? 10 : 30;
                appliedPotionNbt.putInt("Uses", uses);
                appliedPotionNbt.putInt("MaxUses", uses);

                clickedStacknbt.put("AppliedPotion", appliedPotionNbt);
                stack.decrement(1);
                return true;

            } else if (clickedStack.isOf(Items.ARROW)) {
                ItemStack tippedArrowStack = Items.TIPPED_ARROW.getDefaultStack();
                player.playSound(SoundEvents.ITEM_BOTTLE_EMPTY, 0.3F, 1.4F);
                PotionUtil.setPotion(tippedArrowStack, PotionUtil.getPotion(stack));
                if(clickedStack.getCount() <= 10) {
                    tippedArrowStack.setCount(clickedStack.getCount());
                    slot.setStackNoCallbacks(tippedArrowStack);
                } else {
                    tippedArrowStack.setCount(10);
                    clickedStack.decrement(10);
                    player.getInventory().offerOrDrop(tippedArrowStack);
                }
                stack.decrement(1);
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        PotionUtil.buildTooltip(stack, tooltip, 0.125F, world == null ? 1.0F : world.getTickManager().getTickRate());
    }
}
