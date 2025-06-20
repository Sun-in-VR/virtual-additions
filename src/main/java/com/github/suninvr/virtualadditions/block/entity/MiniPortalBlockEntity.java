package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.MiniPortalBlock;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class MiniPortalBlockEntity extends BlockEntity {
    private BlockPos destination;

    public MiniPortalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        super.onBlockReplaced(pos, oldState);
        if (this.destination != null && this.world.getBlockState(this.destination).isOf(VABlocks.MINI_PORTAL)) this.world.breakBlock(this.destination, false);
    }

    public static void setDestination(World world, BlockPos pos, BlockPos destination) {
        if(world.getBlockEntity(pos) instanceof MiniPortalBlockEntity destructiveSculkBlockEntity) {
            destructiveSculkBlockEntity.setDestination(destination);
        }
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if (this.destination != null) view.put("destination", BlockPos.CODEC, this.destination);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        view.read("destination", BlockPos.CODEC).ifPresent(blockPos -> this.destination = blockPos);
    }

    public BlockPos getDestination() {
        return this.destination;
    }

    public void setDestination(BlockPos destination) {
        this.destination = destination;
        this.markDirty();
    }

    public MiniPortalBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.MINI_PORTAL, pos, state);
    }

    public boolean isBlocked() {
        return this.getCachedState().isOf(VABlocks.MINI_PORTAL) && !this.getCachedState().get(MiniPortalBlock.STATE).allowsTeleporting;
    }


}
