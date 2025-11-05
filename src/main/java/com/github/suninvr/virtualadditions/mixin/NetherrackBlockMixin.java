package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherrackBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(NetherrackBlock.class)
public class NetherrackBlockMixin {
    @Inject(method = "performBonemeal", at = @At("TAIL"))
    void virtualAdditions$growNecroticNylium(ServerLevel world, RandomSource random, BlockPos pos, BlockState state, CallbackInfo ci, @Local boolean bl, @Local boolean bl2) {
        boolean bl3 = false;

        Iterator var7 = BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1)).iterator();
        while(var7.hasNext()) {
            BlockPos blockPos = (BlockPos)var7.next();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.is(VABlocks.NECROTIC_NYLIUM)) {
                bl3 = true;
                break;
            }
        }

        if (bl3) {
            BlockState nyliumState;
            if (bl2 && bl) {
                int i = random.nextInt(3);
                nyliumState = switch (i) {
                    case 0 -> Blocks.WARPED_NYLIUM.defaultBlockState();
                    case 1 -> Blocks.CRIMSON_NYLIUM.defaultBlockState();
                    case 2 -> VABlocks.NECROTIC_NYLIUM.defaultBlockState();
                    default -> Blocks.AIR.defaultBlockState();
                };
            } else if (bl2) {
                nyliumState = random.nextBoolean() ? VABlocks.NECROTIC_NYLIUM.defaultBlockState() : Blocks.WARPED_NYLIUM.defaultBlockState();
            } else if (bl) {
                nyliumState = random.nextBoolean() ? VABlocks.NECROTIC_NYLIUM.defaultBlockState() : Blocks.CRIMSON_NYLIUM.defaultBlockState();
            } else {
                nyliumState = VABlocks.NECROTIC_NYLIUM.defaultBlockState();
            }
            world.setBlock(pos, nyliumState, 3);
        }
    }
}
