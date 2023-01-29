package com.github.suninvr.virtualadditions.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import com.github.suninvr.virtualadditions.block.entity.EntanglementDriveBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import org.jetbrains.annotations.Nullable;

public class EntanglementDriveBlock extends BlockWithEntity implements InventoryProvider {
    private static final VoxelShape SHAPE;

    public EntanglementDriveBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EntanglementDriveBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof EntanglementDriveBlockEntity blockEntity) {
                if (player.isSneaking()) {
                    blockEntity.setPlayer(null);
                }
                blockEntity.setPlayer(player);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(getInventory(state, world, pos));
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof EntanglementDriveBlockEntity driveBlockEntity) {
            PlayerEntity player = ((EntanglementDriveBlockEntity)blockEntity).getPlayer();
            int slot = ((EntanglementDriveBlockEntity)blockEntity).getSlot();
            if (player != null) {
                driveBlockEntity.setInventory(new PlayerProxyInventory(player, slot));
                return driveBlockEntity.getInventory();
            }
        }
        return new DummyInventory();
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, VABlockEntities.ENTANGLEMENT_DRIVE_BLOCK_ENTITY, (EntanglementDriveBlockEntity::tick));
    }

    static class PlayerProxyInventory extends SimpleInventory implements SidedInventory {
        private final PlayerEntity player;
        private final int playerSlot;

        public PlayerProxyInventory(PlayerEntity player, int slot) {
            super(player.getInventory().getStack(Math.min(40, Math.max(0, slot))));
            this.player = player;
            this.playerSlot = Math.min(40, Math.max(0, slot));
        }

        public int getMaxCountPerStack() {
            return 64;
        }

        public int[] getAvailableSlots(Direction side) {
            return new int[]{0};
        }

        public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
            return dir == Direction.UP && this.canModifyPlayerInventory();
        }

        public boolean canExtract(int slot, ItemStack stack, Direction dir) {
            return dir == Direction.DOWN && this.canModifyPlayerInventory();
        }

        private boolean canModifyPlayerInventory() {
            return !this.player.isDead() && !this.player.isSpectator();
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            this.player.getInventory().insertStack(this.playerSlot, stack);
        }

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    static class DummyInventory extends SimpleInventory implements SidedInventory {
        public DummyInventory() {
            super(0);
        }

        public int[] getAvailableSlots(Direction side) {
            return new int[0];
        }

        public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
            return false;
        }

        public boolean canExtract(int slot, ItemStack stack, Direction dir) {
            return false;
        }
    }

    static {
        SHAPE = VoxelShapes.union(
                Block.createCuboidShape(2, 0,2, 14, 16, 14),
                Block.createCuboidShape(1, 3,1, 15, 13, 15)
        );
    }

}
