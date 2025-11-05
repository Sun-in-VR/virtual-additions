package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.DyeContents;
import com.github.suninvr.virtualadditions.registry.VARecipeType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;

import java.util.List;
import java.util.Optional;

public class ColoringRecipe implements Recipe<RecipeInput>, ColoringStationRecipe {
    public final DyeContents cost;
    public final int index;
    protected final Optional<Ingredient> ingredient;
    protected final ItemStack result;
    private PlacementInfo ingredientPlacement;

    public ColoringRecipe(Optional<Ingredient> ingredient, ItemStack result, DyeContents cost, int index) {
        this.ingredient = ingredient;
        this.result = result;
        this.cost = cost;
        this.index = index;
    }

    public ColoringRecipe(Optional<Ingredient> ingredient, ItemStack result, int r, int g, int b, int y, int k, int w, int index) {
        this.ingredient = ingredient;
        this.result = result;
        this.cost = new DyeContents(r, g, b, y, k, w);
        this.index = index;
    }

    @Override
    public boolean matches(RecipeInput inventory, Level world) {
        return this.ingredient.map(value -> value.test(inventory.getItem(0))).orElseGet(() -> inventory.getItem(0).isEmpty());
    }

    @Override
    public ItemStack assemble(RecipeInput inventory, HolderLookup.Provider wrapperLookup) {
        ItemStack stack = inventory.getItem(1);
        if ((stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock) || stack.getItem() instanceof BundleItem) {
            return stack.transmuteCopy(this.result.getItem(), this.result.getCount());
        }
        return this.result.copy();
    }

    public ItemStack craftWithDye(RecipeInput inventory, RegistryAccess registryManager, DyeContents contents) {
        if (contents.canAdd(this.cost.copyAndMultiply(-1)) && !inventory.getItem(0).is(this.result.getItem())) {
            return assemble(inventory, registryManager);
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
    public PlacementInfo placementInfo() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = PlacementInfo.createFromOptionals(List.of(this.ingredient));
        }

        return this.ingredientPlacement;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.STONECUTTER;
    }

    public int getIndex() {
        return this.index;
    }

    public DyeContents getDyeCost(boolean inverted) {
        return inverted ? this.cost : this.cost.copyAndMultiply(-1);
    }

    public Optional<Ingredient> getIngredient() {
        return ingredient;
    }

    public SlotDisplay.ItemStackSlotDisplay getStackSlotDisplay() {
        return new SlotDisplay.ItemStackSlotDisplay(this.result);
    };

    @Override
    public ItemStack getResultStack(RegistryAccess registryManager, ItemStack input) {
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
        private static final StreamCodec<RegistryFriendlyByteBuf, ColoringRecipe> PACKET_CODEC = StreamCodec.of(Serializer::write, Serializer::read);

        @Override
        public MapCodec<ColoringRecipe> codec() {
            return this.CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ColoringRecipe> streamCodec() {
            return PACKET_CODEC;
        }

        private static ColoringRecipe read(RegistryFriendlyByteBuf buf) {
            Optional<Ingredient> ingredient = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buf);
            ItemStack stack = ItemStack.STREAM_CODEC.decode(buf);
            DyeContents cost = DyeContents.PACKET_CODEC.decode(buf);
            int index = buf.readInt();
            return new ColoringRecipe(ingredient, stack, cost, index);
        }

        private static void write(RegistryFriendlyByteBuf buf, ColoringRecipe recipe) {
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient);
            ItemStack.STREAM_CODEC.encode(buf, recipe.result);
            DyeContents.PACKET_CODEC.encode(buf, recipe.cost);
            buf.writeInt(recipe.index);
        }
    }
}
