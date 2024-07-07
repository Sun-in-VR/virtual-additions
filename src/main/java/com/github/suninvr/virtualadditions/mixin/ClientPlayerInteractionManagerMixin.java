package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.GildType;
import com.github.suninvr.virtualadditions.item.GildTypes;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.VAEnchantmentTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Shadow
    private int blockBreakingCooldown;
    @Shadow public abstract GameMode getCurrentGameMode();
    @Unique private BlockState brokenState;
    @Unique private final MinecraftClient client = MinecraftClient.getInstance();

    @Inject(method = "breakBlock", at = @At("HEAD"))
    private void virtualAdditions$setBrokenBlockState(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (client.world != null) this.brokenState = client.world.getBlockState(pos);
    }

    @Redirect(method = "updateBlockBreakingProgress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode = Opcodes.PUTFIELD))
    private void virtualAdditions$changeBlockBreakingCooldown(ClientPlayerInteractionManager interactionManager, int x) {
        if (x == 5 && !(client.player == null) && !(client.world == null) && !getCurrentGameMode().equals(GameMode.CREATIVE)) {
            ItemStack heldStack = client.player.getMainHandStack();
            GildType gild = GildedToolItem.getGildType(heldStack);
            if (heldStack.isSuitableFor(this.brokenState) && !gild.equals(GildTypes.SCULK)) {
                int[] efficiency = {0};
                EnchantmentHelper.forEachEnchantment(heldStack, (enchantment, level) -> {
                    if (enchantment.isIn(VAEnchantmentTags.EFFICIENCY)) efficiency[0] = level;
                });
                int y = (gild.equals(GildTypes.AMETHYST) ? 2 : 0) + Math.max(0, efficiency[0] - 3) + (client.player.hasStatusEffect(StatusEffects.HASTE) ? 1 : 0);
                x = Math.max(0, x - y);
            }
        }
        blockBreakingCooldown = x;
    }
}
