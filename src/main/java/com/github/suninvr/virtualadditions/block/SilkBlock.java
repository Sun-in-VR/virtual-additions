package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
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

@SuppressWarnings("deprecation")
public class SilkBlock extends Block {
    public static final MapCodec<SilkBlock> CODEC = createCodec(SilkBlock::new);
    public SilkBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(player.preferredHand);
        if (stack.isOf(Items.SHEARS) && world.getBlockState(pos.up()).isAir()) {
            world.playSound(player, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos.up(), VABlocks.FRAYED_SILK.getDefaultState());
            stack.damage(1, player, playerEntity -> playerEntity.sendToolBreakStatus(player.preferredHand));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
