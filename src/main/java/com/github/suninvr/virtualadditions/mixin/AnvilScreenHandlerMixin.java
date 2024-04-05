package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.GildType;
import com.github.suninvr.virtualadditions.item.GildedToolUtil;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Style;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @Shadow @Final private Property levelCost;

    @Shadow @Nullable private String newItemName;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @WrapOperation(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    boolean virtualAdditions$compareGildedToolItems(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || GildedToolUtil.getBaseStack(instance).isOf(item instanceof GildedToolItem gildedToolItem ? gildedToolItem.getBaseItem() : item);
    }

    @WrapOperation(method = "onTakeOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;setStack(ILnet/minecraft/item/ItemStack;)V"))
    void virtualAdditions$setEngravingChiselStack(Inventory instance, int i, ItemStack stack, Operation<Void> original) {
        ItemStack stack1 = instance.getStack(1);
        if (i == 1 && stack1.isOf(VAItems.ENGRAVING_CHISEL) && stack1.getDamage() < 63) {
            stack1.setDamage(stack1.getDamage() + 1);
            instance.setStack(1, stack1);
        }
        else original.call(instance, i, stack);
    }

    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 4))
    void virtualAdditions$setStack(CraftingResultInventory instance, int slot, ItemStack stack) {
        ItemStack baseStack = this.getSlot(0).getStack();
        ItemStack additionsStack = this.getSlot(1).getStack();

        ItemStack baseBaseStack = GildedToolUtil.getBaseStack(baseStack);
        ItemStack additionsBaseStack = GildedToolUtil.getBaseStack(additionsStack);
        GildType baseType = GildedToolItem.getGildType(baseStack);
        GildType additionsType = GildedToolItem.getGildType(additionsStack);

        if (baseBaseStack.isOf(additionsBaseStack.getItem()) && !(baseType.equals(additionsType))) {
            ItemStack resultStack = baseStack.copy();
            resultStack.applyComponentsFrom(stack.getComponents());
            this.levelCost.set(this.levelCost.get() + 3);
            instance.setStack(slot, resultStack);
        } else {
            instance.setStack(slot, stack);
        }
    }

    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$updateResultForInscryptionChisel(CallbackInfo ci) {
        ItemStack baseStack = this.getSlot(0).getStack();
        ItemStack additionsStack = this.getSlot(1).getStack();

        if (additionsStack.isOf(VAItems.ENGRAVING_CHISEL) && this.newItemName != null) {
            ItemStack resultStack = baseStack.copy();
            LoreComponent lore = resultStack.contains(DataComponentTypes.LORE) ? resultStack.get(DataComponentTypes.LORE) : new LoreComponent(List.of());
            int color = additionsStack.contains(DataComponentTypes.DYED_COLOR) ? additionsStack.get(DataComponentTypes.DYED_COLOR).rgb() : 0x757D97;
            if (lore.lines().size() >= 10) return;
            lore = lore.with(MutableText.of(PlainTextContent.of(this.newItemName)).fillStyle(Style.EMPTY.withColor(color).withItalic(false)));
            resultStack.set(DataComponentTypes.LORE, lore);
            this.levelCost.set(1);
            this.output.setStack(0, resultStack);
            ci.cancel();
        }

    }
}
