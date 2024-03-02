package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.GildType;
import com.github.suninvr.virtualadditions.item.GildedToolUtil;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @Shadow @Final private Property levelCost;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    boolean virtualAdditions$compareGildedToolItems(ItemStack instance, Item item) {
        boolean bl = instance.isOf(item);
        if (bl) return true;
        ItemStack baseStack = GildedToolUtil.getBaseStack(instance);
        Item baseItem = item;
        if (item instanceof GildedToolItem gildedToolItem) {
            baseItem = gildedToolItem.getBaseItem();
        }

        return baseStack.isOf(baseItem);
    }

    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 4))
    void virtualAdditions$setStack(CraftingResultInventory instance, int slot, ItemStack stack) {
        ItemStack baseStack = this.getSlot(0).getStack();
        ItemStack additionsStack = this.getSlot(1).getStack();
        GildType baseType = GildedToolItem.getGildType(baseStack);
        GildType additionsType = GildedToolItem.getGildType(additionsStack);

        if (!(baseType.equals(additionsType))) {
            if (stack.isEmpty()) stack = baseStack.copy();
            ItemStack resultStack = additionsStack.copy();
            resultStack.copyComponentsFrom(stack.getComponents());

            int damage = stack.getDamage() - (baseStack.getDamage() - additionsStack.getDamage());

            resultStack.setDamage(damage);
            this.levelCost.set(this.levelCost.get() + 5);
            instance.setStack(slot, resultStack);
        } else {
            instance.setStack(slot, stack);
        }
    }
}
