package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BoundSilkBlock extends Block {
    public BoundSilkBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.SHEARS) && world.getBlockState(pos.up()).isAir()) {
            world.playSound(player, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos.up(), VABlocks.FRAYED_SILK.getDefaultState());
            stack.damage(1, player, playerEntity -> playerEntity.sendToolBreakStatus(hand));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
