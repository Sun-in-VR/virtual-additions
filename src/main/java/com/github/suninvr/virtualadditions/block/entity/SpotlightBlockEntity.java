package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.SpotlightBlock;
import com.github.suninvr.virtualadditions.block.SpotlightLightBlock;
import com.github.suninvr.virtualadditions.block.enums.LightStatus;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SpotlightBlockEntity extends BlockEntity {
    private BlockPos lightPos;

    public SpotlightBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.SPOTLIGHT, pos, state);
        this.lightPos = pos;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.writeNbt(nbt, lookup);
        if (this.lightPos != null) nbt.put("light_pos", NbtHelper.fromBlockPos(pos));
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.readNbt(nbt, lookup);
        if (nbt.contains("light_pos")) this.lightPos = NbtHelper.toBlockPos(nbt.getCompound("light_pos"));
    }

    public static void tick(World world, BlockPos pos, BlockState state, SpotlightBlockEntity blockEntity) {
        if (world.getTime() % 20 == 0) {
            if (state.get(SpotlightBlock.POWERED)) blockEntity.updateLightLocation(world, pos, state);
        }
    }

    public void updateLightLocation(World world, BlockPos pos, BlockState state) {
        if (world.isClient) return;
        if (!state.isOf(VABlocks.SPOTLIGHT)) return;

        Direction direction = state.get(SpotlightBlock.FACING);
        BlockPos newPos = this.getUpdatedLightLocation(world, pos, direction);
        if (newPos != null) {
            boolean powered = state.get(SpotlightBlock.POWERED);
            //Initialize position and state variables.
            BlockState newState = world.getBlockState(newPos);
            BlockPos oldPos = this.getLightLocation();
            BlockState oldState = world.getBlockState(oldPos);

            if (newState.isOf(VABlocks.SPOTLIGHT_LIGHT)) {
                //Update the state if already a light block
                SpotlightLightBlock.setStatus(world, newState, newPos, direction, powered ? LightStatus.LIT : LightStatus.UNLIT);
                this.setLightLocation(newPos);
            } else {
                //Place the new light if the space is available
                boolean isWater = world.getFluidState(newPos).isEqualAndStill(Fluids.WATER);
                if (world.isAir(newPos) || newState.isOf(Blocks.WATER)) {
                    this.setLightLocation(newPos);
                    BlockState lightState = VABlocks.SPOTLIGHT_LIGHT.getDefaultState().with(SpotlightLightBlock.WATERLOGGED, isWater).with(SpotlightLightBlock.LIT, powered);
                    world.setBlockState(newPos, SpotlightLightBlock.getStateWithStatus(lightState, direction, powered ? LightStatus.LIT : LightStatus.UNLIT));
                }
            }

            //Update the old state
            if (!oldPos.equals(newPos) && oldState.isOf(VABlocks.SPOTLIGHT_LIGHT)) SpotlightLightBlock.setStatus(world, oldState, oldPos, direction, LightStatus.NONE);
        }

    }

    private BlockPos getUpdatedLightLocation(World world, BlockPos startPos, Direction direction) {
        BlockPos pos = new BlockPos(startPos.offset(direction));
        if (!world.isAir(pos) && !world.getBlockState(pos).isIn(VABlockTags.SPOTLIGHT_PERMEABLE)) return pos;
        int i = 0;

        BlockPos finalPos = pos;
        BlockPos offsetPos = new BlockPos(pos.offset(direction));
        while ((world.isAir(offsetPos) || world.getBlockState(offsetPos).isIn(VABlockTags.SPOTLIGHT_PERMEABLE)) && i < 31) {
            pos = new BlockPos(pos.offset(direction));
            offsetPos = new BlockPos(pos.offset(direction));
            i++;
            if (world.isAir(pos) || world.getBlockState(pos).isOf(Blocks.WATER) || world.getBlockState(pos).isOf(VABlocks.SPOTLIGHT_LIGHT)) finalPos = pos;
        }

        return finalPos;
    }

    public BlockPos getLightLocation() {
        return this.lightPos;
    }

    private void setLightLocation(BlockPos pos) {
        this.lightPos = pos;
        this.markDirty();
    }
}
