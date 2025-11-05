package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;

public class TallGreencapMushroomBlock extends DoublePlantBlock implements BonemealableBlock {
    public static final MapCodec<TallGreencapMushroomBlock> CODEC = simpleCodec(TallGreencapMushroomBlock::new);

    public TallGreencapMushroomBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends DoublePlantBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(BlockTags.DIRT) || floor.is(Blocks.FARMLAND) || floor.is(VABlocks.SILK_BLOCK);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) return;
        if (random.nextInt(2) == 1) return;
        Vec3 modelOffset = state.getOffset(pos);
        double x = pos.getX() + modelOffset.x;
        double y = pos.getY() + modelOffset.y;
        double z = pos.getZ() + modelOffset.z;
        for(int l = 0; l < 4; ++l) {
            world.addParticle(VAParticleTypes.GREENCAP_SPORE, x + 0.25 + random.nextDouble() / 2, y + 0.25  + random.nextDouble() / 2, z + 0.25 + random.nextDouble() / 2, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        popResource(world, pos, new ItemStack(VABlocks.GREENCAP_MUSHROOM));
    }
}
