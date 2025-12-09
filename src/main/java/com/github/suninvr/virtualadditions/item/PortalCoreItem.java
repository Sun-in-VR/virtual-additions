package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.block.entity.MiniPortalBlockEntity;
import com.github.suninvr.virtualadditions.component.PortalCoreLocationComponent;
import com.github.suninvr.virtualadditions.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class PortalCoreItem extends Item {
    public PortalCoreItem(Properties settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        if (entity.tickCount % 4 != 0) return;
        if (entity instanceof ServerPlayer player && (EquipmentSlot.MAINHAND.equals(slot) || EquipmentSlot.OFFHAND.equals(slot)) && stack.has(VADataComponentTypes.PORTAL_CORE_LOCATION)) {
            stack.get(VADataComponentTypes.PORTAL_CORE_LOCATION).pos().ifPresent(pos1 -> {
                Vec3 centerPos = pos1.getCenter();
                world.sendParticles(player, VAParticleTypes.INTERFERENCE, true, true, centerPos.x(), centerPos.y(), centerPos.z(), 1, 0.25F, 0.25F, 0.25F, 0.0);
            });
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        if (player.isCrouching()) {
            ItemStack stack = player.getItemInHand(interactionHand);
            level.playSound(null, player.blockPosition(), VASoundEvents.ITEM_PORTAL_CORE_USE, SoundSource.PLAYERS, 1.0F, 0.2F);
            stack.remove(VADataComponentTypes.PORTAL_CORE_LOCATION);
            return InteractionResult.SUCCESS;
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        SoundEvent sound = VASoundEvents.ITEM_PORTAL_CORE_USE;
        float pitch = 0.8F;
        if (!context.getLevel().getBlockState(pos).isAir() && !context.getLevel().getBlockState(pos).is(Blocks.WATER)) return InteractionResult.PASS;
        ItemStack stack = context.getItemInHand();
        if (stack.has(VADataComponentTypes.PORTAL_CORE_LOCATION) && !context.getLevel().isClientSide()) {
            boolean[] bl = {false};
            BlockPos[] destPos = {null};
            stack.get(VADataComponentTypes.PORTAL_CORE_LOCATION).pos().ifPresent(pos1 -> {
                destPos[0] = pos1;
                bl[0] = placePortalPair((ServerLevel) context.getLevel(), pos, pos1, context.getPlayer());
            });
            if (bl[0] || pos.equals(destPos[0]) || (context.getPlayer() != null && context.getPlayer().getUseItem().is(VAItems.SLINGSHOT))) stack.remove(VADataComponentTypes.PORTAL_CORE_LOCATION);
            if (bl[0]) {
                sound = VASoundEvents.BLOCK_MINI_PORTAL_OPEN;
                pitch = 1.1F;
                stack.consume(1, context.getPlayer());
            } else {
                pitch = 0.2F;
            }
        } else {
            stack.set(VADataComponentTypes.PORTAL_CORE_LOCATION, PortalCoreLocationComponent.of(pos));
        }
        context.getLevel().playSound(null, context.getClickedPos(), sound, SoundSource.BLOCKS, 1.0F, pitch);
        return InteractionResult.SUCCESS;
    }

    private boolean placePortalPair(ServerLevel world, BlockPos pos1, BlockPos pos2, Player player) {
        if (pos1.equals(pos2)) return false;
        int range = world.getGameRules().get(VAGameRules.MINI_PORTAL_MAX_CREATION_RANGE);
        if (!player.isCreative() && Math.sqrt(pos1.getCenter().distanceToSqr(pos2.getCenter())) > range) return false;
        boolean bl1 = world.getBlockState(pos1).isAir() || world.getBlockState(pos1).is(Blocks.WATER);
        boolean bl2 = world.getBlockState(pos2).isAir() || world.getBlockState(pos2).is(Blocks.WATER);
        if (!bl1 || !bl2) return false;
        world.setBlockAndUpdate(pos1, VABlocks.MINI_PORTAL.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, world.getFluidState(pos1).is(Fluids.WATER)));
        world.setBlockAndUpdate(pos2, VABlocks.MINI_PORTAL.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, world.getFluidState(pos2).is(Fluids.WATER)));
        MiniPortalBlockEntity.setDestination(world, pos1, pos2);
        MiniPortalBlockEntity.setDestination(world, pos2, pos1);
        return true;
    }
}
