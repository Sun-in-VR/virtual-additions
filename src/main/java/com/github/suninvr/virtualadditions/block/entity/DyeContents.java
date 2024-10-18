package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.registry.VADyeColors;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.screen.PropertyDelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static DyeContents empty() {
        return new DyeContents(0, 0, 0, 0, 0, 0);
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
        return new int[]{this.getR(), this.getG(), this.getB(), this.getY(), this.getW(), this.getK()};
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
        int R, G, B, Y, K, W;
        if ((R = this.getR() + contents.getR()) > 8192 || R < 0) return false;
        if ((G = this.getG() + contents.getG()) > 8192 || G < 0) return false;
        if ((B = this.getB() + contents.getB()) > 8192 || B < 0) return false;
        if ((Y = this.getY() + contents.getY()) > 8192 || Y < 0) return false;
        if ((K = this.getK() + contents.getK()) > 8192 || K < 0) return false;
        if ((W = this.getW() + contents.getW()) > 8192 || W < 0) return false;
        return true;
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

    public List<ItemStack> getDyeStacks() {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        int r = Math.floorDiv(this.getR(), 32);
        while (r > 0) {
            stacks.add(new ItemStack(Items.RED_DYE, Math.min(r, 64)));
            r -= 64;
        }
        int g = Math.floorDiv(this.getG(), 32);
        while (g > 0) {
            stacks.add(new ItemStack(Items.GREEN_DYE, Math.min(g, 64)));
            g -= 64;
        }
        int b = Math.floorDiv(this.getB(), 32);
        while (b > 0) {
            stacks.add(new ItemStack(Items.BLUE_DYE, Math.min(b, 64)));
            b -= 64;
        }
        int y = Math.floorDiv(this.getY(), 32);
        while (y > 0) {
            stacks.add(new ItemStack(Items.YELLOW_DYE, Math.min(y, 64)));
            y -= 64;
        }
        int w = Math.floorDiv(this.getW(), 32);
        while (w > 0) {
            stacks.add(new ItemStack(Items.WHITE_DYE, Math.min(w, 64)));
            w -= 64;
        }
        int k = Math.floorDiv(this.getK(), 32);
        while (k > 0) {
            stacks.add(new ItemStack(Items.BLACK_DYE, Math.min(k, 64)));
            k -= 64;
        }
        return stacks;
    }

    public DyeContents copy() {
        return new DyeContents(this.getR(), this.getG(), this.getB(), this.getY(), this.getK(), this.getW());
    }

    public DyeContents copyAndMultiply(int x) {
        DyeContents contents = new DyeContents(this.getR(), this.getG(), this.getB(), this.getY(), this.getK(), this.getW());
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
