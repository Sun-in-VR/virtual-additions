package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlockMixin extends Block {

    protected SculkShriekerBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if(state.get(Properties.CAN_SUMMON) && stack.isOf(Items.GLASS_BOTTLE)) {
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, VAItems.BOTTLED_SOULS.getDefaultStack()));
            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(player, GameEvent.ITEM_INTERACT_START, pos);
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.getBlockEntity(pos, BlockEntityType.SCULK_SHRIEKER).ifPresent((blockEntity) -> blockEntity.shriek(serverWorld, (ServerPlayerEntity) player));
            }
            return ActionResult.SUCCESS;
        }
        if (!state.get(Properties.CAN_SUMMON) && stack.isOf(VAItems.BOTTLED_SOULS)) {
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, Items.GLASS_BOTTLE.getDefaultStack()));
            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(player, GameEvent.ITEM_INTERACT_START, pos);
            world.setBlockState(pos, state.with(Properties.CAN_SUMMON, true));
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
