package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.block.ClimbingRopeAnchorBlock;
import com.github.suninvr.virtualadditions.item.VAToolUtil;
import com.github.suninvr.virtualadditions.registry.VAGildTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Shadow
    public static List<ItemStack> getDrops(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack itemStack) {
        return null;
    }

    @Inject(method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$dropAndPickUpStacks(BlockState state, Level world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfo ci) {
        if ((state.getBlock() instanceof ClimbingRopeAnchorBlock || VAGildTypes.IOLITE.equals(VAToolUtil.getGildType(tool))) && world instanceof ServerLevel && entity instanceof Player player) {
            List<ItemStack> stacks = getDrops(state, (ServerLevel) world, pos, blockEntity, entity, tool);
            if (stacks != null) stacks.forEach( stack -> dropStackIntoInventory(world, pos, stack, player));
            state.spawnAfterBreak((ServerLevel)world, pos, tool, true);
            ci.cancel();
        }
    }

    @Unique
    private static void dropStackIntoInventory(Level world, BlockPos pos, ItemStack stack, Player player) {
        double d = (double) EntityType.ITEM.getHeight() / 2.0;
        double e = (double)pos.getX() + 0.5 + Mth.nextDouble(world.random, -0.25, 0.25);
        double f = (double)pos.getY() + 0.5 + Mth.nextDouble(world.random, -0.25, 0.25) - d;
        double g = (double)pos.getZ() + 0.5 + Mth.nextDouble(world.random, -0.25, 0.25);
        ItemEntity item = new ItemEntity(world, e, f, g, stack);
        world.addFreshEntity(item);
        item.playerTouch(player);
    }
}
