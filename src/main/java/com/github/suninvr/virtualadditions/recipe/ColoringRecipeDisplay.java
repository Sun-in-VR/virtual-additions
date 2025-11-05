package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.DyeContents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;
import java.util.Optional;

public record ColoringRecipeDisplay<T extends ColoringStationRecipe>(SlotDisplay optionDisplay, Optional<RecipeHolder<T>> recipeEntry) {

    public static <T extends ColoringStationRecipe> StreamCodec<RegistryFriendlyByteBuf, ColoringRecipeDisplay<T>> codec() {
        return StreamCodec.composite(SlotDisplay.STREAM_CODEC, ColoringRecipeDisplay::optionDisplay, display -> new ColoringRecipeDisplay<>(display, Optional.empty()));
    }

    public Optional<RecipeHolder<T>> recipe() {return this.recipeEntry;}

    public record Grouping<T extends ColoringStationRecipe>(List<ColoringRecipeDisplay.GroupEntry<T>> entries) {
        public static <T extends ColoringStationRecipe> ColoringRecipeDisplay.Grouping<T> empty() {
            return new ColoringRecipeDisplay.Grouping<T>(List.of());
        }

        public static <T extends ColoringStationRecipe> StreamCodec<RegistryFriendlyByteBuf, ColoringRecipeDisplay.Grouping<T>> codec() {
            return StreamCodec.composite(GroupEntry.<T>codec().apply(ByteBufCodecs.list()), Grouping::entries, Grouping::new);
        }

        public boolean contains(ItemStack stack) {
            return this.entries.stream().anyMatch(entry -> entry.input.map(ingredient -> ingredient.test(stack)).orElseGet(stack::isEmpty));
        }

        public ColoringRecipeDisplay.Grouping<T> filter(ItemStack stack) {
            Grouping<T> grouping = new ColoringRecipeDisplay.Grouping<>(this.entries.stream().filter(tGroupEntry -> tGroupEntry.input.map(ingredient -> ingredient.test(stack)).orElseGet(stack::isEmpty)).toList());
            if (grouping.isEmpty()) grouping = new ColoringRecipeDisplay.Grouping<>(this.entries.stream().filter(tGroupEntry -> tGroupEntry.recipe.recipeEntry.isPresent() && tGroupEntry.recipe.recipeEntry.get().value() instanceof ArmorColoringRecipe && stack.is(ItemTags.DYEABLE)).toList());
            return grouping;
        }

        public boolean isEmpty() {
            return this.entries.isEmpty();
        }

        public int size() {
            return this.entries.size();
        }
    }

    public record GroupEntry<T extends ColoringStationRecipe>(Optional<Ingredient> input, ColoringRecipeDisplay<T> recipe) {
        public static <T extends ColoringStationRecipe> StreamCodec<RegistryFriendlyByteBuf, ColoringRecipeDisplay.GroupEntry<T>> codec() {
            return StreamCodec.composite(Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC, ColoringRecipeDisplay.GroupEntry::input, ColoringRecipeDisplay.codec(), ColoringRecipeDisplay.GroupEntry::recipe, ColoringRecipeDisplay.GroupEntry::new);
        }

        public DyeContents getDyeContents() {
            return this.recipe.recipeEntry.isPresent() && this.recipe.recipeEntry.get().value() instanceof ColoringRecipe coloringRecipe ? coloringRecipe.getDyeCost() : DyeContents.empty();
        }
    }
}
