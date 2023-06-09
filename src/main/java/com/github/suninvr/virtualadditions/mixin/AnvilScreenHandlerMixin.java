package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.GildedToolUtil;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

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
}
