package com.github.suninvr.virtualadditions.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import com.github.suninvr.virtualadditions.registry.VABlockTags;

public class LargeCrystalFeature extends Feature<LargeCrystalFeatureConfig> {
    static Direction[] horizontalDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

    public LargeCrystalFeature(Codec<LargeCrystalFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<LargeCrystalFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Direction offset = Util.getRandom(horizontalDirections, world.getRandom());
        Direction verticalOffset = context.getConfig().ceiling() ? Direction.DOWN : Direction.UP;
        BlockPos origin = context.getOrigin();
        LargeCrystalAngle angle = Util.getRandom(LargeCrystalAngle.angles, world.getRandom());

        if (!world.getBlockState(origin).isAir()) origin.offset(verticalOffset);
        int length = context.getConfig().length().get(world.getRandom());
        if (!angle.equals(LargeCrystalAngle.MEDIUM)) {length = (length / 2) + 1;}

        for (int s = 1; s <= length; s++) {
            origin = angle.fillStep(world, origin, context.getConfig(), offset, verticalOffset);
        }

        return true;
    }

    private enum LargeCrystalAngle{
        STEEP,
        MEDIUM,
        SHALLOW;
        static LargeCrystalAngle[] angles = {STEEP, MEDIUM, SHALLOW};

        public BlockPos fillStep(StructureWorldAccess world, BlockPos pos, LargeCrystalFeatureConfig config, Direction forward, Direction up) {
            BlockStateProvider innerState = config.innerState();
            BlockStateProvider outerState = config.outerState();

            //Relative directions
            Direction backward = forward.getOpposite();
            Direction left = forward.rotateYCounterclockwise();
            Direction right = forward.rotateYClockwise();
            Direction down = up.getOpposite();
            
            switch (this) {
                case STEEP -> {
                    for (int h = 0; h < 2; h++) {
                        BlockPos pos1 = pos.offset(up, h);
                        setState(world, pos1, innerState);
                        setState(world, pos1.offset(forward), outerState);
                        setState(world, pos1.offset(backward), outerState);
                        setState(world, pos1.offset(left), outerState);
                        setState(world, pos1.offset(right), outerState);
                    }
                    setState(world, pos.offset(up, 2), outerState);
                    setState(world, pos.offset(up, 2).offset(forward), outerState);

                    return pos.offset(forward).offset(up, 2);
                }


                case MEDIUM -> {
                    setState(world, pos, innerState);
                    setState(world, pos.offset(forward), outerState);
                    setState(world, pos.offset(backward), outerState);
                    setState(world, pos.offset(left), outerState);
                    setState(world, pos.offset(right), outerState);
                    setState(world, pos.offset(up), outerState);
                    setState(world, pos.offset(forward).offset(up), outerState);
                    return pos.offset(forward).offset(up);
                }


                case SHALLOW -> {
                    setState(world, pos, innerState);
                    setState(world, pos.offset(backward), innerState);
                    setState(world, pos.offset(forward), outerState);
                    setState(world, pos.offset(backward, 2), outerState);
                    setState(world, pos.offset(backward, 3), outerState);
                    setState(world, pos.offset(up), outerState);
                    setState(world, pos.offset(up).offset(forward), outerState);
                    setState(world, pos.offset(up).offset(backward), outerState);
                    setState(world, pos.offset(down), outerState);
                    setState(world, pos.offset(left), outerState);
                    setState(world, pos.offset(right), outerState);
                    setState(world, pos.offset(left).offset(backward), outerState);
                    setState(world, pos.offset(right).offset(backward), outerState);
                    return pos.offset(forward, 2).offset(up);
                }
                default -> {
                    return pos;
                }
            }
        }

        private void setState(StructureWorldAccess world, BlockPos pos, BlockStateProvider provider) {
            if (!world.isValidForSetBlock(pos)) return;
            if(world.getBlockState(pos).isIn(VABlockTags.LARGE_CRYSTAL_REPLACEABLE) || world.isAir(pos)) world.setBlockState(pos, provider.getBlockState(world.getRandom(), pos), 2);
        }
    }
}
