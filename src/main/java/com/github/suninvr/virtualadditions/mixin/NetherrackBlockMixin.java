package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherrackBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(NetherrackBlock.class)
public class NetherrackBlockMixin {
    @Inject(method = "grow", at = @At("TAIL"))
    void virtualAdditions$growNecroticNylium(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci, @Local boolean bl, @Local boolean bl2) {
        boolean bl3 = false;

        Iterator var7 = BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1)).iterator();
        while(var7.hasNext()) {
            BlockPos blockPos = (BlockPos)var7.next();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(VABlocks.NECROTIC_NYLIUM)) {
                bl3 = true;
                break;
            }
        }

        if (bl3) {
            BlockState nyliumState;
            if (bl2 && bl) {
                int i = random.nextInt(3);
                nyliumState = switch (i) {
                    case 0 -> Blocks.WARPED_NYLIUM.getDefaultState();
                    case 1 -> Blocks.CRIMSON_NYLIUM.getDefaultState();
                    case 2 -> VABlocks.NECROTIC_NYLIUM.getDefaultState();
                    default -> Blocks.AIR.getDefaultState();
                };
            } else if (bl2) {
                nyliumState = random.nextBoolean() ? VABlocks.NECROTIC_NYLIUM.getDefaultState() : Blocks.WARPED_NYLIUM.getDefaultState();
            } else if (bl) {
                nyliumState = random.nextBoolean() ? VABlocks.NECROTIC_NYLIUM.getDefaultState() : Blocks.CRIMSON_NYLIUM.getDefaultState();
            } else {
                nyliumState = VABlocks.NECROTIC_NYLIUM.getDefaultState();
            }
            world.setBlockState(pos, nyliumState, 3);
        }
    }
}
