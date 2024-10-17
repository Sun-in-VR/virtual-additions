package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.registry.VARecipeType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.class_10355;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookGroup;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class ColoringRecipe implements Recipe<RecipeInput>, ColoringStationRecipe {
    public final ColoringStationBlockEntity.DyeContents cost;
    public final int index;
    protected final Optional<Ingredient> ingredient;
    protected final ItemStack result;
    private IngredientPlacement ingredientPlacement;

    public ColoringRecipe(Optional<Ingredient> ingredient, ItemStack result, ColoringStationBlockEntity.DyeContents cost, int index) {
        this.ingredient = ingredient;
        this.result = result;
        this.cost = cost;
        this.index = index;
    }

    public ColoringRecipe(Optional<Ingredient> ingredient, ItemStack result, int r, int g, int b, int y, int k, int w, int index) {
        this.ingredient = ingredient;
        this.result = result;
        this.cost = new ColoringStationBlockEntity.DyeContents(r, g, b, y, k, w);
        this.index = index;
    }

    @Override
    public boolean matches(RecipeInput inventory, World world) {
        return this.ingredient.map(value -> value.test(inventory.getStackInSlot(0))).orElseGet(() -> inventory.getStackInSlot(0).isEmpty());
    }

    @Override
    public ItemStack craft(RecipeInput inventory, RegistryWrapper.WrapperLookup wrapperLookup) {
        ItemStack stack = inventory.getStackInSlot(1);
        if ((stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock) || stack.getItem() instanceof BundleItem) {
            return stack.copyComponentsToNewStack(this.result.getItem(), this.result.getCount());
        }
        return this.result.copy();
    }

    public ItemStack craftWithDye(RecipeInput inventory, DynamicRegistryManager registryManager, ColoringStationBlockEntity.DyeContents contents) {
        if (contents.getR() - this.cost.getR() >= 0 && contents.getG() - this.cost.getG() >= 0 && contents.getB() - this.cost.getB() >= 0 && contents.getY() - this.cost.getY() >= 0 && contents.getK() - this.cost.getK() >= 0 && contents.getW() - this.cost.getW() >= 0) {
            return craft(inventory, registryManager);
        }
        return ItemStack.EMPTY;
    }

    public ItemStack getResult() {
        return this.result;
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return VARecipeType.COLORING_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return VARecipeType.COLORING;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forMultipleSlots(List.of(this.ingredient));
        }

        return this.ingredientPlacement;
    }

    @Override
    public class_10355 getRecipeBookTab() {
        return RecipeBookGroup.STONECUTTER;
    }

    public int getIndex() {
        return this.index;
    }

    public ColoringStationBlockEntity.DyeContents getDyeCost(boolean inverted) {
        return inverted ? this.cost : this.cost.copyAndMultiply(-1);
    }

    public Optional<Ingredient> getIngredient() {
        return ingredient;
    }

    public SlotDisplay.StackSlotDisplay getStackSlotDisplay() {
        return new SlotDisplay.StackSlotDisplay(this.result);
    };

    @Override
    public ItemStack getResultStack(DynamicRegistryManager registryManager, ItemStack input) {
        return this.getResult();
    }

    @Override
    public ItemStack getResultStack(ItemStack input) {
        return this.getResult();
    }

    public static class Serializer implements RecipeSerializer<ColoringRecipe> {
        private final MapCodec<ColoringRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Ingredient.CODEC.optionalFieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
                        ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                        Codec.INT.optionalFieldOf("red_cost", 0).forGetter(recipe -> recipe.cost.getR()),
                        Codec.INT.optionalFieldOf("green_cost", 0).forGetter(recipe -> recipe.cost.getG()),
                        Codec.INT.optionalFieldOf("blue_cost", 0).forGetter(recipe -> recipe.cost.getB()),
                        Codec.INT.optionalFieldOf("yellow_cost", 0).forGetter(recipe -> recipe.cost.getY()),
                        Codec.INT.optionalFieldOf("black_cost", 0).forGetter(recipe -> recipe.cost.getK()),
                        Codec.INT.optionalFieldOf("white_cost", 0).forGetter(recipe -> recipe.cost.getW()),
                        Codec.INT.optionalFieldOf("index", 0).forGetter(recipe -> recipe.index)
                ).apply(instance, ColoringRecipe::new)
        );
        private static final PacketCodec<RegistryByteBuf, ColoringRecipe> PACKET_CODEC = PacketCodec.ofStatic(Serializer::write, Serializer::read);

        @Override
        public MapCodec<ColoringRecipe> codec() {
            return this.CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ColoringRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static ColoringRecipe read(RegistryByteBuf buf) {
            Optional<Ingredient> ingredient = Ingredient.OPTIONAL_PACKET_CODEC.decode(buf);
            ItemStack stack = ItemStack.PACKET_CODEC.decode(buf);
            ColoringStationBlockEntity.DyeContents cost = ColoringStationBlockEntity.DyeContents.PACKET_CODEC.decode(buf);
            int index = buf.readInt();
            return new ColoringRecipe(ingredient, stack, cost, index);
        }

        private static void write(RegistryByteBuf buf, ColoringRecipe recipe) {
            Ingredient.OPTIONAL_PACKET_CODEC.encode(buf, recipe.ingredient);
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
            ColoringStationBlockEntity.DyeContents.PACKET_CODEC.encode(buf, recipe.cost);
            buf.writeInt(recipe.index);
        }
    }
}
