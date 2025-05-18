package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.screen.ColoringStationScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ColoringStationBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    private static final Text displayName = Text.translatable("container.virtual_additions.coloring_station");
    public DyeContents dyeContents;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
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
        public int size() {
            return 6;
        }
    };

    public ColoringStationBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.COLORING_STATION, pos, state);
        this.dyeContents = new DyeContents();
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.dyeContents = DyeContents.from(view);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        this.dyeContents.to(view);

    }

    public List<ItemStack> getDroppedStacks() {
        return this.dyeContents.getDyeStacks();
    }

    @Override
    public Text getDisplayName() {
        return displayName;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ColoringStationScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(this.world, this.pos), this.propertyDelegate);
    }

}
