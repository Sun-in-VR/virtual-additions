package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.block.ClimbingRopeAnchorBlock;
import com.github.suninvr.virtualadditions.item.GildTypes;
import com.github.suninvr.virtualadditions.item.GildedToolUtil;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.World;
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
    @Shadow protected abstract void dropExperience(ServerWorld world, BlockPos pos, int size);

    @Shadow
    public static List<ItemStack> getDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack) {
        return null;
    }

    @Inject(method = "dropExperienceWhenMined", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$dropExtraExperienceWhenMined(ServerWorld world, BlockPos pos, ItemStack tool, IntProvider experience, CallbackInfo ci) {
        if (!GildedToolItem.getGildType(tool).equals(GildTypes.EMERALD)) return;
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) == 0) {
            int i = (int)Math.ceil(experience.get(world.random) * 1.6);
            if (i > 0) {
                this.dropExperience(world, pos, i);
            }
        }
        ci.cancel();
    }

    @Inject(method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private static void virtualAdditions$dropAndPickUpStacks(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfo ci) {
        if ((state.getBlock() instanceof ClimbingRopeAnchorBlock || GildedToolUtil.getGildType(tool).equals(GildTypes.IOLITE)) && world instanceof ServerWorld && entity instanceof PlayerEntity player) {
            List<ItemStack> stacks = getDroppedStacks(state, (ServerWorld)world, pos, blockEntity, entity, tool);
            if (stacks != null) stacks.forEach( stack -> dropStackIntoInventory(world, pos, stack, player));
            state.onStacksDropped((ServerWorld)world, pos, tool, true);
            ci.cancel();
        }
    }

    @Unique
    private static void dropStackIntoInventory(World world, BlockPos pos, ItemStack stack, PlayerEntity player) {
        double d = (double) EntityType.ITEM.getHeight() / 2.0;
        double e = (double)pos.getX() + 0.5 + MathHelper.nextDouble(world.random, -0.25, 0.25);
        double f = (double)pos.getY() + 0.5 + MathHelper.nextDouble(world.random, -0.25, 0.25) - d;
        double g = (double)pos.getZ() + 0.5 + MathHelper.nextDouble(world.random, -0.25, 0.25);
        ItemEntity item = new ItemEntity(world, e, f, g, stack);
        world.spawnEntity(item);
        item.onPlayerCollision(player);
    }
}
