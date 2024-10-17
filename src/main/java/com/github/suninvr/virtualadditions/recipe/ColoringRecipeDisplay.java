package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.display.SlotDisplay;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record ColoringRecipeDisplay<T extends ColoringStationRecipe>(SlotDisplay optionDisplay, Optional<RecipeEntry<T>> recipeEntry) {

    public static <T extends ColoringStationRecipe> PacketCodec<RegistryByteBuf, ColoringRecipeDisplay<T>> codec() {
        return PacketCodec.tuple(SlotDisplay.PACKET_CODEC, ColoringRecipeDisplay::optionDisplay, display -> new ColoringRecipeDisplay<>(display, Optional.empty()));
    }

    public Optional<RecipeEntry<T>> recipe() {return this.recipeEntry;}

    public record Grouping<T extends ColoringStationRecipe>(List<ColoringRecipeDisplay.GroupEntry<T>> entries) {
        public static <T extends ColoringStationRecipe> ColoringRecipeDisplay.Grouping<T> empty() {
            return new ColoringRecipeDisplay.Grouping<T>(List.of());
        }

        public static <T extends ColoringStationRecipe> PacketCodec<RegistryByteBuf, ColoringRecipeDisplay.Grouping<T>> codec() {
            return PacketCodec.tuple(GroupEntry.<T>codec().collect(PacketCodecs.toList()), Grouping::entries, Grouping::new);
        }

        public boolean contains(ItemStack stack) {
            return this.entries.stream().anyMatch(entry -> entry.input.map(ingredient -> ingredient.test(stack)).orElseGet(stack::isEmpty));
        }

        public ColoringRecipeDisplay.Grouping<T> filter(ItemStack stack) {
            return new ColoringRecipeDisplay.Grouping<>(this.entries.stream().filter(tGroupEntry -> tGroupEntry.input.map(ingredient -> ingredient.test(stack)).orElseGet(stack::isEmpty)).toList());
        }

        public boolean isEmpty() {
            return this.entries.isEmpty();
        }

        public int size() {
            return this.entries.size();
        }
    }

    public record GroupEntry<T extends ColoringStationRecipe>(Optional<Ingredient> input, ColoringRecipeDisplay<T> recipe) {
        public static <T extends ColoringStationRecipe> PacketCodec<RegistryByteBuf, ColoringRecipeDisplay.GroupEntry<T>> codec() {
            return PacketCodec.tuple(Ingredient.OPTIONAL_PACKET_CODEC, ColoringRecipeDisplay.GroupEntry::input, ColoringRecipeDisplay.codec(), ColoringRecipeDisplay.GroupEntry::recipe, ColoringRecipeDisplay.GroupEntry::new);
        }

        public ColoringStationBlockEntity.DyeContents getDyeContents() {
            return this.recipe.recipeEntry.isPresent() && this.recipe.recipeEntry.get().value() instanceof ColoringRecipe coloringRecipe ? coloringRecipe.getDyeCost() : ColoringStationBlockEntity.DyeContents.empty();
        }
    }
}
