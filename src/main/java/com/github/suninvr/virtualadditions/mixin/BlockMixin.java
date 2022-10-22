package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.item.enums.GildType;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Shadow protected abstract void dropExperience(ServerWorld world, BlockPos pos, int size);

    @Inject(method = "dropExperienceWhenMined", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$dropExtraExperienceWhenMined(ServerWorld world, BlockPos pos, ItemStack tool, IntProvider experience, CallbackInfo ci) {
        if (!GildedToolItem.getGildType(tool).equals(GildType.EMERALD)) return;
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) == 0) {
            int i = (int)Math.ceil(experience.get(world.random) * 1.6);
            if (i > 0) {
                this.dropExperience(world, pos, i);
            }
        }
        ci.cancel();
    }
}
