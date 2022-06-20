package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import com.github.suninvr.virtualadditions.item.TomeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbMixin {
    @Shadow private int amount;
    ExperienceOrbEntity instance = ((ExperienceOrbEntity)(Object)this);

    @Inject(method = "onPlayerCollision", at = @At("HEAD"))
    public void virtualAdditions$experienceOrbTomeEffects(PlayerEntity player, CallbackInfo ci){
        if (!instance.world.isClient() && player.experiencePickUpDelay == 0) {
            ItemStack stack = player.getOffHandStack();
            if (TomeItem.getType(stack).equals(TomeItem.TomeType.INTELLIGENCE)) {

                this.amount = (int)(this.amount * (TomeItem.TomeType.INTELLIGENCE.getModifierAmount(TomeItem.getLevel(stack)) + 1));

            } else if (TomeItem.getType(stack).equals(TomeItem.TomeType.SOUL_MENDING)) {
                int i = this.amount;
                while (player.getHealth() < 20 && i > 0) {
                    boolean bl = player.world.getRandom().nextInt(100) <= (TomeItem.TomeType.SOUL_MENDING.getModifierAmount(TomeItem.getLevel(stack)) * 100);
                    if (bl) {
                        player.heal(TomeItem.getLevel(player.getOffHandStack()));
                        this.amount -= 1;
                    }
                    i -= 1;
                }
            }
        }
    }
}
