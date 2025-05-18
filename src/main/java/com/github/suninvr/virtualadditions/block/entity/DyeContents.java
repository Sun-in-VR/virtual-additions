package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VADyeColors;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DyeContents {
    public static final PacketCodec<RegistryByteBuf, DyeContents> PACKET_CODEC = new PacketCodec<>() {
        @Override
        public DyeContents decode(RegistryByteBuf buf) {
            return new DyeContents(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
        }

        @Override
        public void encode(RegistryByteBuf buf, DyeContents contents) {
            buf.writeInt(contents.getR());
            buf.writeInt(contents.getG());
            buf.writeInt(contents.getB());
            buf.writeInt(contents.getY());
            buf.writeInt(contents.getK());
            buf.writeInt(contents.getW());
        }
    };
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

    public static DyeContents from(ReadView view) {
        Optional<int[]> contents = view.getOptionalIntArray("dye_contents");
        return contents.map(ints -> new DyeContents(
                ints.length > 0 ? ints[0] : 0,
                ints.length > 1 ? ints[1] : 0,
                ints.length > 2 ? ints[2] : 0,
                ints.length > 3 ? ints[3] : 0,
                ints.length > 4 ? ints[4] : 0,
                ints.length > 5 ? ints[5] : 0
        )).orElseGet(DyeContents::new);
    }

    public static DyeContents from(PacketByteBuf buf) {
        return new DyeContents(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static DyeContents empty() {
        return new DyeContents(0, 0, 0, 0, 0, 0);
    }

    public void to(WriteView view) {
        view.putIntArray("dye_contents", this.asIntArray());
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

    public DyeContents add(DyeContents contents) {
        this.setR(this.getR() + contents.getR());
        this.setG(this.getG() + contents.getG());
        this.setB(this.getB() + contents.getB());
        this.setY(this.getY() + contents.getY());
        this.setK(this.getK() + contents.getK());
        this.setW(this.getW() + contents.getW());
        return this;
    }

    public int[] asIntArray() {
        return new int[]{this.getR(), this.getG(), this.getB(), this.getY(), this.getK(), this.getW()};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DyeContents contents = (DyeContents) o;
        return r == contents.r && g == contents.g && b == contents.b && y == contents.y && k == contents.k && w == contents.w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b, y, k, w);
    }

    public boolean canAdd(DyeContents contents) {
        int[] thisContents = this.asIntArray();
        int[] thatContents = contents.asIntArray();
        int i;
        for (i = 0; i <= 5; i++) {
            int c = thisContents[i] + thatContents[i];
            if (0 > c || c > 8192) return false;
        }
        return true;
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

    public List<ItemStack> getDyeStacks() {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        int[] ints = this.asIntArray();
        int i = 0;
        for (int j : ints) {
            int c = Math.floorDiv(j, 32);
            while (c > 0) {
                stacks.add(new ItemStack(getDyeFromIndex(i), Math.min(c, 64)));
                c -= 64;
            }
            i++;
        }
        return stacks;
    }

    private static Item getDyeFromIndex(int index) {
        return switch (index) {
            case 0 -> Items.RED_DYE;
            case 1 -> Items.GREEN_DYE;
            case 2 -> Items.BLUE_DYE;
            case 3 -> Items.YELLOW_DYE;
            case 4 -> Items.BLACK_DYE;
            case 5 -> Items.WHITE_DYE;
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }

    public DyeContents copy() {
        return new DyeContents(this.getR(), this.getG(), this.getB(), this.getY(), this.getK(), this.getW());
    }

    public DyeContents copyAndMultiply(int x) {
        DyeContents contents = this.copy();
        contents.multiply(x);
        return contents;
    }

    public void addDye(ItemStack itemStack) {
        if (itemStack.getItem() instanceof DyeItem dyeItem) {
            DyeContents contents = VADyeColors.getContents(dyeItem, 8);
            while (!itemStack.isEmpty() && this.canAdd(contents)) {
                this.add(contents);
                itemStack.decrement(1);
            }
        }
    }
}
