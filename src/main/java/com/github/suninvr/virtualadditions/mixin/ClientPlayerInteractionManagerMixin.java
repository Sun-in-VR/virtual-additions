package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAGildTypes;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Shadow private int destroyDelay;

    @Shadow public abstract GameType getPlayerMode();

    @Unique private BlockState brokenState;
    @Unique private final Minecraft client = Minecraft.getInstance();

    @Inject(method = "destroyBlock", at = @At("HEAD"))
    private void virtualAdditions$setBrokenBlockState(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (client.level != null) this.brokenState = client.level.getBlockState(pos);
    }

    @WrapOperation(method = "continueDestroyBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;destroyDelay:I", opcode = Opcodes.PUTFIELD, ordinal = 2))
    private void virtualAdditions$changeBlockBreakingCooldown2(MultiPlayerGameMode instance, int value, Operation<Void> original) {
        original.call(instance, value);
        value = virtualAdditions$getBlockBreakingCooldown(this.destroyDelay);
        this.destroyDelay = value;
    }

    @Unique
    int virtualAdditions$getBlockBreakingCooldown(int original) {
        int cooldown = original;
        if (client.player == null && !getPlayerMode().equals(GameType.CREATIVE)) return 5;
        ItemStack heldStack = client.player.getMainHandItem();
        GildType gild = heldStack.get(VADataComponentTypes.GILD_TYPE);
        if (heldStack.isCorrectToolForDrops(this.brokenState) && gild != null) {
            double d = Math.max(0, (client.player.getDestroySpeed(this.brokenState) / 10.0) - 1);
            int y = (VAGildTypes.AMETHYST.equals(gild) ? 3 : 0) + (int) Math.floor(d);
            cooldown = Math.max(0, cooldown - y);
        }
        return cooldown;
    }
}
