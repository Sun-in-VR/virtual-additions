package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.registry.VARecipeType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

public class ColoringRecipe implements Recipe<Inventory> {
    public final ColoringStationBlockEntity.DyeContents cost;
    public final int index;
    protected final Ingredient ingredient;
    protected final ItemStack result;

    public ColoringRecipe(Ingredient ingredient, ItemStack result, ColoringStationBlockEntity.DyeContents cost, int index) {
        this.ingredient = ingredient;
        this.result = result;
        this.cost = cost;
        this.index = index;
    }

    public ColoringRecipe(Ingredient ingredient, ItemStack result, int r, int g, int b, int y, int k, int w, int index) {
        this.ingredient = ingredient;
        this.result = result;
        this.cost = new ColoringStationBlockEntity.DyeContents(r, g, b, y, k, w);
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
        if (contents.getR() - this.cost.getR() >= 0 && contents.getG() - this.cost.getG() >= 0 && contents.getB() - this.cost.getB() >= 0 && contents.getY() - this.cost.getY() >= 0 && contents.getK() - this.cost.getK() >= 0 && contents.getW() - this.cost.getW() >= 0) {
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
        return inverted ? this.cost : this.cost.copyAndMultiply(-1);
    }

    public ColoringStationBlockEntity.DyeContents getDyeCost() {
        return getDyeCost(false);
    }

    public static class Serializer implements RecipeSerializer<ColoringRecipe> {
        private final Codec<ColoringRecipe> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Ingredient.ALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
                        ItemStack.CUTTING_RECIPE_RESULT_CODEC.forGetter(recipe -> recipe.result),
                        Codecs.createStrictOptionalFieldCodec(Codec.INT, "red_cost", 0).forGetter(recipe -> recipe.cost.getR()),
                        Codecs.createStrictOptionalFieldCodec(Codec.INT, "green_cost", 0).forGetter(recipe -> recipe.cost.getG()),
                        Codecs.createStrictOptionalFieldCodec(Codec.INT, "blue_cost", 0).forGetter(recipe -> recipe.cost.getB()),
                        Codecs.createStrictOptionalFieldCodec(Codec.INT, "yellow_cost", 0).forGetter(recipe -> recipe.cost.getY()),
                        Codecs.createStrictOptionalFieldCodec(Codec.INT, "black_cost", 0).forGetter(recipe -> recipe.cost.getK()),
                        Codecs.createStrictOptionalFieldCodec(Codec.INT, "white_cost", 0).forGetter(recipe -> recipe.cost.getW()),
                        Codecs.createStrictOptionalFieldCodec(Codec.INT, "index", 0).forGetter(recipe -> recipe.index)
                ).apply(instance, ColoringRecipe::new)
        );
        private static final PacketCodec<RegistryByteBuf, ColoringRecipe> PACKET_CODEC = PacketCodec.ofStatic(Serializer::write, Serializer::read);

        @Override
        public Codec<ColoringRecipe> codec() {
            return this.CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ColoringRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static ColoringRecipe read(RegistryByteBuf buf) {
            Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
            ItemStack stack = ItemStack.PACKET_CODEC.decode(buf);
            ColoringStationBlockEntity.DyeContents cost = ColoringStationBlockEntity.DyeContents.PACKET_CODEC.decode(buf);
            int index = buf.readInt();
            return new ColoringRecipe(ingredient, stack, cost, index);
        }

        private static void write(RegistryByteBuf buf, ColoringRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.ingredient);
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
            ColoringStationBlockEntity.DyeContents.PACKET_CODEC.encode(buf, recipe.cost);
            buf.writeInt(recipe.index);
        }
    }
}
