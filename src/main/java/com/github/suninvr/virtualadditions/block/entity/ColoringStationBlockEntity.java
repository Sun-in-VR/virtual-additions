package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.screen.ColoringStationScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ColoringStationBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
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
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.dyeContents = DyeContents.from(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.dyeContents.to(nbt);
    }

    public void setDyeContent(DyeContents contents) {
        this.dyeContents = contents;
        this.markDirty();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        this.dyeContents.to(buf);
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

    public static class DyeContents {
        private int r;
        private int g;
        private int b;
        private int y;
        private int k;
        private int w;

        public DyeContents() {
            this(0, 0, 0, 0, 0, 0);
        }

        public DyeContents(int r, int g, int b, int y, int k, int w) {
            this.setR(r);
            this.setG(g);
            this.setB(b);
            this.setY(y);
            this.setK(k);
            this.setW(w);
        }

        public DyeContents(PropertyDelegate propertyDelegate) {
            this(propertyDelegate.get(0), propertyDelegate.get(1), propertyDelegate.get(2), propertyDelegate.get(3), propertyDelegate.get(4), propertyDelegate.get(5));
        }

        public static DyeContents from(NbtCompound nbt) {
            if (nbt.contains("dye_contents")) {
                NbtCompound dyeContents = nbt.getCompound("dye_contents");
                return new DyeContents(
                        dyeContents.contains("red") ? dyeContents.getInt("red") : 0,
                        dyeContents.contains("green") ? dyeContents.getInt("green") : 0,
                        dyeContents.contains("blue") ? dyeContents.getInt("blue") : 0,
                        dyeContents.contains("yellow") ? dyeContents.getInt("yellow") : 0,
                        dyeContents.contains("black") ? dyeContents.getInt("black") : 0,
                        dyeContents.contains("white") ? dyeContents.getInt("white") : 0
                );
            }
            return new DyeContents();
        }

        public static DyeContents from(PacketByteBuf buf) {
            return new DyeContents(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
        }

        public void to(NbtCompound nbt) {
            NbtCompound dyeContents = new NbtCompound();
            dyeContents.putInt("red", this.getR());
            dyeContents.putInt("green", this.getG());
            dyeContents.putInt("blue", this.getB());
            dyeContents.putInt("yellow", this.getY());
            dyeContents.putInt("black", this.getK());
            dyeContents.putInt("white", this.getW());
            nbt.put("dye_contents", dyeContents);
        }

        public void to(PacketByteBuf buf) {
            buf.writeInt(this.getR());
            buf.writeInt(this.getG());
            buf.writeInt(this.getB());
            buf.writeInt(this.getY());
            buf.writeInt(this.getK());
            buf.writeInt(this.getW());
        }

        public void set(int r, int g, int b, int y, int k, int w) {
            this.setR(r);
            this.setG(g);
            this.setB(b);
            this.setY(y);
            this.setK(k);
            this.setW(w);
        }

        public void add(DyeContents contents) {
            this.setR(this.getR() + contents.getR());
            this.setG(this.getG() + contents.getG());
            this.setB(this.getB() + contents.getB());
            this.setY(this.getY() + contents.getY());
            this.setK(this.getK() + contents.getK());
            this.setW(this.getW() + contents.getW());
        }

        public void add(int r, int g, int b, int y, int k, int w) {
            this.setR(this.getR() + r);
            this.setG(this.getG() + g);
            this.setB(this.getB() + b);
            this.setY(this.getY() + y);
            this.setK(this.getK() + k);
            this.setW(this.getW() + w);
        }

        public void multiply(int x) {
            this.setR(this.getR() * x);
            this.setG(this.getG() * x);
            this.setB(this.getB() * x);
            this.setY(this.getY() * x);
            this.setK(this.getK() * x);
            this.setW(this.getW() * x);
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getK() {
            return k;
        }

        public void setK(int k) {
            this.k = k;
        }

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
        }

        public DyeContents copy() {
            return new DyeContents(this.getR(), this.getG(), this.getB(), this.getY(), this.getK(), this.getW());
        }

        public DyeContents copyAndMultiply(int x) {
            DyeContents contents = new DyeContents(this.getR(), this.getG(), this.getB(), this.getY(), this.getK(), this.getW());
            contents.multiply(x);
            return contents;
        }

        public boolean addDye(ItemStack itemStack, int max) {
            boolean bl = false;
            if (itemStack.isOf(Items.RED_DYE)) {
                int diff = max - this.getR();
                while (diff > 0 && itemStack.getCount() > 0) {
                    diff -= 16;
                    this.setR(Math.min(max, this.getR() + 16));
                    itemStack.decrement(1);
                    bl = true;
                }
            }
            if (itemStack.isOf(Items.GREEN_DYE)) {
                int diff = max - this.getG();
                while (diff > 0 && itemStack.getCount() > 0) {
                    diff -= 16;
                    this.setG(Math.min(max, this.getG() + 16));
                    itemStack.decrement(1);
                    bl = true;
                }
            }
            if (itemStack.isOf(Items.BLUE_DYE)) {
                int diff = max - this.getB();
                while (diff > 0 && itemStack.getCount() > 0) {
                    diff -= 16;
                    this.setB(Math.min(max, this.getB() + 16));
                    itemStack.decrement(1);
                    bl = true;
                }
            }
            if (itemStack.isOf(Items.YELLOW_DYE)) {
                int diff = max - this.getY();
                while (diff > 0 && itemStack.getCount() > 0) {
                    diff -= 16;
                    this.setY(Math.min(max, this.getY() + 16));
                    itemStack.decrement(1);
                    bl = true;
                }
            }
            if (itemStack.isOf(Items.BLACK_DYE)) {
                int diff = max - this.getK();
                while (diff > 0 && itemStack.getCount() > 0) {
                    diff -= 16;
                    this.setK(Math.min(max, this.getK() + 16));
                    itemStack.decrement(1);
                    bl = true;
                }
            }
            if (itemStack.isOf(Items.WHITE_DYE)) {
                int diff = max - this.getW();
                while (diff > 0 && itemStack.getCount() > 0) {
                    diff -= 16;
                    this.setW(Math.min(max, this.getW() + 16));
                    itemStack.decrement(1);
                    bl = true;
                }
            }
            return bl;
        }
    }
}
