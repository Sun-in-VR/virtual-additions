package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.GildType;
import com.github.suninvr.virtualadditions.item.GildTypes;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.VAEnchantmentTags;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributes;
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

    @WrapOperation(method = "updateBlockBreakingProgress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode = Opcodes.PUTFIELD, ordinal = 2))
    private void virtualAdditions$changeBlockBreakingCooldown2(ClientPlayerInteractionManager instance, int value, Operation<Void> original) {
        original.call(instance, value);
        value = virtualAdditions$getBlockBreakingCooldown(this.blockBreakingCooldown);
        this.blockBreakingCooldown = value;
    }

    @Unique
    int virtualAdditions$getBlockBreakingCooldown(int original) {
        int cooldown = original;
        if (client.player == null && !getCurrentGameMode().equals(GameMode.CREATIVE)) return 5;
        ItemStack heldStack = client.player.getMainHandStack();
        GildType gild = GildedToolItem.getGildType(heldStack);
        if (heldStack.isSuitableFor(this.brokenState) && !gild.equals(GildTypes.SCULK)) {
            double d = Math.max(0, (client.player.getBlockBreakingSpeed(this.brokenState) / 10.0) - 1);
            int y = (gild.equals(GildTypes.AMETHYST) ? 3 : 0) + (int) Math.floor(d);
            cooldown = Math.max(0, cooldown - y);
        }
        return cooldown;
    }
}
