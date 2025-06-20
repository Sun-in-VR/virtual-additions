package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.block.entity.MiniPortalBlockEntity;
import com.github.suninvr.virtualadditions.component.PortalCoreLocationComponent;
import com.github.suninvr.virtualadditions.registry.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class PortalCoreItem extends Item {
    public PortalCoreItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        if (entity.age % 4 != 0) return;
        if (entity instanceof ServerPlayerEntity player && (EquipmentSlot.MAINHAND.equals(slot) || EquipmentSlot.OFFHAND.equals(slot)) && stack.contains(VADataComponentTypes.PORTAL_CORE_LOCATION)) {
            stack.get(VADataComponentTypes.PORTAL_CORE_LOCATION).pos().ifPresent(pos1 -> {
                Vec3d centerPos = pos1.toCenterPos();
                world.spawnParticles(player, VAParticleTypes.INTERFERENCE, true, true, centerPos.getX(), centerPos.getY(), centerPos.getZ(), 1, 0.25F, 0.25F, 0.25F, 0.0);
            });
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos().offset(context.getSide());
        SoundEvent sound = VASoundEvents.ITEM_PORTAL_CORE_USE;
        float pitch = 0.8F;
        if (!context.getWorld().getBlockState(pos).isAir() && !context.getWorld().getBlockState(pos).isOf(Blocks.WATER)) return ActionResult.PASS;
        ItemStack stack = context.getStack();
        if (stack.contains(VADataComponentTypes.PORTAL_CORE_LOCATION) && !context.getWorld().isClient) {
            boolean[] bl = {false};
            stack.get(VADataComponentTypes.PORTAL_CORE_LOCATION).pos().ifPresent(pos1 -> {
                bl[0] = placePortalPair((ServerWorld) context.getWorld(), pos, pos1, context.getPlayer());
            });
            stack.remove(VADataComponentTypes.PORTAL_CORE_LOCATION);
            if (bl[0]) {
                sound = VASoundEvents.BLOCK_MINI_PORTAL_OPEN;
                pitch = 1.1F;
                stack.decrement(1);
            } else {
                pitch = 0.2F;
            }
        } else {
            stack.set(VADataComponentTypes.PORTAL_CORE_LOCATION, PortalCoreLocationComponent.of(pos));
        }
        context.getWorld().playSound(null, context.getBlockPos(), sound, SoundCategory.BLOCKS, 1.0F, pitch);
        return ActionResult.SUCCESS;
    }

    private boolean placePortalPair(ServerWorld world, BlockPos pos1, BlockPos pos2, PlayerEntity player) {
        if (pos1.equals(pos2)) return false;
        int range = world.getGameRules().getInt(VAGameRules.MINI_PORTAL_MAX_CREATION_RANGE);
        if (!player.isCreative() && Math.sqrt(pos1.toCenterPos().squaredDistanceTo(pos2.toCenterPos())) > range) return false;
        boolean bl1 = world.getBlockState(pos1).isAir() || world.getBlockState(pos1).isOf(Blocks.WATER);
        boolean bl2 = world.getBlockState(pos1).isAir() || world.getBlockState(pos1).isOf(Blocks.WATER);
        if (!bl1 || !bl2) return false;
        world.setBlockState(pos1, VABlocks.MINI_PORTAL.getDefaultState().with(Properties.WATERLOGGED, world.getFluidState(pos1).isOf(Fluids.WATER)));
        world.setBlockState(pos2, VABlocks.MINI_PORTAL.getDefaultState().with(Properties.WATERLOGGED, world.getFluidState(pos2).isOf(Fluids.WATER)));
        MiniPortalBlockEntity.setDestination(world, pos1, pos2);
        MiniPortalBlockEntity.setDestination(world, pos2, pos1);
        return true;
    }
}
