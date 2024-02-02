package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.registry.VARecipeType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

public class ColoringRecipe implements Recipe<Inventory> {
    public final int r, g, b, y, k, w, index;
    protected final Ingredient ingredient;
    protected final ItemStack result;

    public ColoringRecipe(Ingredient ingredient, ItemStack result, ColoringStationBlockEntity.DyeContents cost, int index) {
        this(ingredient, result, cost.getR(), cost.getG(), cost.getB(), cost.getY(), cost.getK(), cost.getW(), index);
    }

    public ColoringRecipe(Ingredient ingredient, ItemStack result, int r, int g, int b, int y, int k, int w, int index) {
        this.ingredient = ingredient;
        this.result = result;
        this.r = r;
        this.g = g;
        this.b = b;
        this.y = y;
        this.k = k;
        this.w = w;
        this.index = index;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.ingredient.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return this.result.copy();
    }

    public ItemStack craftWithDye(Inventory inventory, DynamicRegistryManager registryManager, ColoringStationBlockEntity.DyeContents contents) {
        if (contents.getR() - this.r >= 0 && contents.getG() - this.g >= 0 && contents.getB() - this.b >= 0 && contents.getY() - this.y >= 0 && contents.getK() - this.k >= 0 && contents.getW() - this.w >= 0) {
            return craft(inventory, registryManager);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VARecipeType.COLORING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return VARecipeType.COLORING;
    }

    public int getIndex() {
        return this.index;
    }

    public ColoringStationBlockEntity.DyeContents getDyeCost(boolean inverted) {
        return inverted ? new ColoringStationBlockEntity.DyeContents(this.r, this.g, this.b, this.y, this.k, this.w) : new ColoringStationBlockEntity.DyeContents(this.r * -1, this.g * -1, this.b * -1, this.y * -1, this.k * -1, this.w * -1);
    }

    public ColoringStationBlockEntity.DyeContents getDyeCost() {
        return getDyeCost(false);
    }

    public static class Serializer implements RecipeSerializer<ColoringRecipe> {
        private final Codec<ColoringRecipe> codec;

        public Serializer() {
            this.codec = RecordCodecBuilder.create(
                    instance -> instance.group(
                            Ingredient.ALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
                            ItemStack.CUTTING_RECIPE_RESULT_CODEC.forGetter(recipe -> recipe.result),
                            Codecs.createStrictOptionalFieldCodec(Codec.INT, "red_cost", 0).forGetter(recipe -> recipe.r),
                            Codecs.createStrictOptionalFieldCodec(Codec.INT, "green_cost", 0).forGetter(recipe -> recipe.g),
                            Codecs.createStrictOptionalFieldCodec(Codec.INT, "blue_cost", 0).forGetter(recipe -> recipe.b),
                            Codecs.createStrictOptionalFieldCodec(Codec.INT, "yellow_cost", 0).forGetter(recipe -> recipe.y),
                            Codecs.createStrictOptionalFieldCodec(Codec.INT, "black_cost", 0).forGetter(recipe -> recipe.k),
                            Codecs.createStrictOptionalFieldCodec(Codec.INT, "white_cost", 0).forGetter(recipe -> recipe.w),
                            Codecs.createStrictOptionalFieldCodec(Codec.INT, "index", 0).forGetter(recipe -> recipe.index)
                    ).apply(instance, ColoringRecipe::new)
            );
        }

        @Override
        public Codec<ColoringRecipe> codec() {
            return this.codec;
        }

        @Override
        public ColoringRecipe read(PacketByteBuf buf) {
            Ingredient ingredient = Ingredient.fromPacket(buf);
            ItemStack stack = buf.readItemStack();
            int r = buf.readInt();
            int g = buf.readInt();
            int b = buf.readInt();
            int y = buf.readInt();
            int k = buf.readInt();
            int w = buf.readInt();
            int index = buf.readInt();
            return new ColoringRecipe(ingredient, stack, r, g, b, y, k, w, index);
        }

        @Override
        public void write(PacketByteBuf buf, ColoringRecipe recipe) {
            recipe.ingredient.write(buf);
            buf.writeItemStack(recipe.result);
            buf.writeInt(recipe.r);
            buf.writeInt(recipe.g);
            buf.writeInt(recipe.b);
            buf.writeInt(recipe.y);
            buf.writeInt(recipe.k);
            buf.writeInt(recipe.w);
            buf.writeInt(recipe.index);
        }
    }
}
