package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VATextureIdentifiers;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.SmithingTemplateItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(SmithingTemplateItem.class)
public class SmithingTemplateItemMixin {
    @Inject(method = "getAdditionalSlotEmptyIcons", at = @At("RETURN"), cancellable = true)
    private static void virtualAdditions$getArmorTrimEmptyAdditionsSlotTextures(CallbackInfoReturnable<List<Identifier>> cir) {
        ArrayList<Identifier> list = new ArrayList<>(cir.getReturnValue());
        list.add(VATextureIdentifiers.EMPTY_SLOT_IOLITE_TEXTURE);
        list.add(VATextureIdentifiers.EMPTY_SLOT_ROCK_SALT_TEXTURE);
        cir.setReturnValue(list);
    }
}
