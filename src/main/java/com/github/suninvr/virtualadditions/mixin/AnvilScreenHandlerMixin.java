package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AnvilMenu.class)
public abstract class AnvilScreenHandlerMixin extends ItemCombinerMenu {

    @Shadow @Nullable private String itemName;

    @Shadow @Final private DataSlot cost;

    public AnvilScreenHandlerMixin(@Nullable MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess context, ItemCombinerMenuSlotDefinition forgingSlotsManager) {
        super(type, syncId, playerInventory, context, forgingSlotsManager);
    }

    @WrapOperation(method = "onTake", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Container;setItem(ILnet/minecraft/world/item/ItemStack;)V"))
    void virtualAdditions$setEngravingChiselStack(Container instance, int i, ItemStack stack, Operation<Void> original) {
        ItemStack stack1 = instance.getItem(1);
        ServerLevel world = this.player.level().isClientSide() ? null : (ServerLevel) this.player.level();
        if (!(world == null) && i == 1 && stack1.is(VAItems.ENGRAVING_CHISEL) && stack1.getDamageValue() < 63) {
            int j = player.isCreative() ? 0 : EnchantmentHelper.processDurabilityChange(world, stack1, 1);
            stack1.setDamageValue(stack1.getDamageValue() + j);
            instance.setItem(1, stack1);
        }
        else original.call(instance, i, stack);
    }

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$updateResultForInscryptionChisel(CallbackInfo ci) {
        ItemStack baseStack = this.getSlot(0).getItem();
        ItemStack additionsStack = this.getSlot(1).getItem();

        if (additionsStack.is(VAItems.ENGRAVING_CHISEL) && this.itemName != null) {
            ItemStack resultStack = baseStack.copy();
            ItemLore lore = resultStack.has(DataComponents.LORE) ? resultStack.get(DataComponents.LORE) : new ItemLore(List.of());
            int color = additionsStack.has(DataComponents.DYED_COLOR) ? additionsStack.get(DataComponents.DYED_COLOR).rgb() : 0x757D97;
            if (lore.lines().size() >= 10) return;
            lore = lore.withLineAdded(MutableComponent.create(PlainTextContents.create(this.itemName)).withStyle(Style.EMPTY.withColor(color).withItalic(false)));
            resultStack.set(DataComponents.LORE, lore);
            this.cost.set(1);
            this.resultSlots.setItem(0, resultStack);
            ci.cancel();
        }

    }
}
