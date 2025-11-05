package com.github.suninvr.virtualadditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public class OxidizableClimbingRopeAnchor extends ClimbingRopeAnchorBlock implements WeatheringCopper {
    private final WeatheringCopper.WeatherState oxidationLevel;

    public OxidizableClimbingRopeAnchor(Properties settings, WeatheringCopper.WeatherState oxidationLevel) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        this.changeOverTime(state, world, pos, random);
    }

    protected boolean isRandomlyTicking(BlockState state) {
        return !this.oxidationLevel.equals(WeatherState.OXIDIZED);
    }

    @Override
    public WeatherState getAge() {
        return this.oxidationLevel;
    }
}
