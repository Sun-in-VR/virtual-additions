package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.screen.ColoringStationScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ColoringStationBlockEntity extends BlockEntity implements MenuProvider {
    private static final Component displayName = Component.translatable("container.virtual_additions.coloring_station");
    public DyeContents dyeContents;
    protected final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> ColoringStationBlockEntity.this.dyeContents.getR();
                case 1 -> ColoringStationBlockEntity.this.dyeContents.getG();
                case 2 -> ColoringStationBlockEntity.this.dyeContents.getB();
                case 3 -> ColoringStationBlockEntity.this.dyeContents.getY();
                case 4 -> ColoringStationBlockEntity.this.dyeContents.getK();
                case 5 -> ColoringStationBlockEntity.this.dyeContents.getW();
                default -> throw new IllegalStateException("Unexpected value: " + index);
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> ColoringStationBlockEntity.this.dyeContents.setR(value);
                case 1 -> ColoringStationBlockEntity.this.dyeContents.setG(value);
                case 2 -> ColoringStationBlockEntity.this.dyeContents.setB(value);
                case 3 -> ColoringStationBlockEntity.this.dyeContents.setY(value);
                case 4 -> ColoringStationBlockEntity.this.dyeContents.setK(value);
                case 5 -> ColoringStationBlockEntity.this.dyeContents.setW(value);
                default -> throw new IllegalStateException("Unexpected value: " + index);
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    };

    public ColoringStationBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.COLORING_STATION, pos, state);
        this.dyeContents = new DyeContents();
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.dyeContents = DyeContents.from(view);
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        this.dyeContents.to(view);

    }

    public List<ItemStack> getDroppedStacks() {
        return this.dyeContents.getDyeStacks();
    }

    @Override
    public Component getDisplayName() {
        return displayName;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new ColoringStationScreenHandler(syncId, playerInventory, ContainerLevelAccess.create(this.level, this.worldPosition), this.propertyDelegate);
    }

}
