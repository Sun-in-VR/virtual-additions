package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Style;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @Shadow @Final private Property levelCost;

    @Shadow @Nullable private String newItemName;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, ForgingSlotsManager forgingSlotsManager) {
        super(type, syncId, playerInventory, context, forgingSlotsManager);
    }

    @WrapOperation(method = "onTakeOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;setStack(ILnet/minecraft/item/ItemStack;)V"))
    void virtualAdditions$setEngravingChiselStack(Inventory instance, int i, ItemStack stack, Operation<Void> original) {
        ItemStack stack1 = instance.getStack(1);
        ServerWorld world = this.player.getEntityWorld().isClient() ? null : (ServerWorld) this.player.getEntityWorld();
        if (!(world == null) && i == 1 && stack1.isOf(VAItems.ENGRAVING_CHISEL) && stack1.getDamage() < 63) {
            int j = player.isCreative() ? 0 : EnchantmentHelper.getItemDamage(world, stack1, 1);
            stack1.setDamage(stack1.getDamage() + j);
            instance.setStack(1, stack1);
        }
        else original.call(instance, i, stack);
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
