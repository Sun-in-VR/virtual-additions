package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.DyeContents;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import com.github.suninvr.virtualadditions.registry.VARecipeType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class ArmorColoringRecipe implements Recipe<RecipeInput>, ColoringStationRecipe {
    public final Ingredient ingredient;
    public final int index;
    public final DyeItem dyeItem;
    private PlacementInfo ingredientPlacement;

    public ArmorColoringRecipe(Ingredient ingredient, int index) {
        this.ingredient = ingredient;
        this.index = index;
        ResourceKey<Item>[] key = new ResourceKey[1];
        ingredient.items().findFirst().ifPresent(item -> key[0] = item.unwrapKey().get());
        this.dyeItem = key[0] == null ? null : BuiltInRegistries.ITEM.getValue(key[0]) instanceof DyeItem dyeItem ? dyeItem : null;
    }

    @Override
    public boolean matches(RecipeInput inventory, Level world) {
        return inventory.getItem(0).is(ItemTags.DYEABLE);
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public Optional<Ingredient> getIngredient() {
        return Optional.of(this.ingredient);
    }

    @Override
    public SlotDisplay.ItemStackSlotDisplay getStackSlotDisplay() {
        return new SlotDisplay.ItemStackSlotDisplay(this.dyeItem.getDefaultInstance());
    }

    @Override
    public ItemStack craftWithDye(RecipeInput input, RegistryAccess registryManager, DyeContents dyeContents) {
        DyeContents cost = this.getDyeCost(true);
        if (dyeContents.getR() - cost.getR() >= 0 && dyeContents.getG() - cost.getG() >= 0 && dyeContents.getB() - cost.getB() >= 0 && dyeContents.getY() - cost.getY() >= 0 && dyeContents.getK() - cost.getK() >= 0 && dyeContents.getW() - cost.getW() >= 0) {
            return assemble(input, registryManager);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public DyeContents getDyeCost(boolean inverted) {
        DyeContents contents = this.dyeItem != null ? VADyeColors.getContents(this.dyeItem, 4) : DyeContents.empty();
        if (!inverted) contents.multiply(-1);
        return contents;
    }

    @Override
    public ItemStack getResultStack(RegistryAccess registryManager, ItemStack input) {
        return getResultStack(input);
    }

    public ItemStack getResultStack(ItemStack input) {
        if (!input.isEmpty() && this.dyeItem != null) {
            ItemStack result = input.copy();
            return DyedItemColor.applyDyes(result, List.of(this.dyeItem));
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack assemble(RecipeInput inventory, HolderLookup.Provider wrapperLookup) {
        ItemStack stack = inventory.getItem(1);
        return this.getResultStack( stack);
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return VARecipeType.ARMOR_COLORING_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return VARecipeType.COLORING;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = PlacementInfo.createFromOptionals(List.of(Optional.of(this.ingredient)));
        }

        return this.ingredientPlacement;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.STONECUTTER;
    }

    public static class Serializer implements RecipeSerializer<ArmorColoringRecipe> {
        private final MapCodec<ArmorColoringRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Ingredient.CODEC.fieldOf("dye").forGetter(armorColoringRecipe -> armorColoringRecipe.ingredient),
                        Codec.INT.optionalFieldOf("index", 0).forGetter(recipe -> recipe.index)
                ).apply(instance, ArmorColoringRecipe::new)
        );
        private static final StreamCodec<RegistryFriendlyByteBuf, ArmorColoringRecipe> PACKET_CODEC = StreamCodec.of(Serializer::write, Serializer::read);

        @Override
        public MapCodec<ArmorColoringRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ArmorColoringRecipe> streamCodec() {
            return PACKET_CODEC;
        }

        private static ArmorColoringRecipe read(RegistryFriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            int index = buf.readInt();
            return new ArmorColoringRecipe(ingredient, index);
        }

        private static void write(RegistryFriendlyByteBuf buf, ArmorColoringRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient);
            buf.writeInt(recipe.index);
        }
    }
}
