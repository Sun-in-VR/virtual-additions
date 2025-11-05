package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VARecipeType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import java.util.List;
import java.util.Optional;

public class SmithingGildRecipe implements SmithingRecipe {
    private static final Ingredient TEMPLATE = Ingredient.of(VAItems.TOOL_GILD_SMITHING_TEMPLATE);
    private PlacementInfo ingredientPlacement;
    final Ingredient base;
    final Ingredient addition;
    final Holder<GildType> type;

    public SmithingGildRecipe(Ingredient base, Ingredient addition, Holder<GildType> type) {
        this.base = base;
        this.addition = addition;
        this.type = type;
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries) {
        if (input.base().has(VADataComponentTypes.GILD_TYPE)) return null;
        ItemStack result = input.base().copyWithCount(1);
        result.set(VADataComponentTypes.GILD_TYPE, this.type.value());
        this.type.value().modifyStackOnCrafted(result);
        return result;
    }

    @Override
    public RecipeSerializer<? extends SmithingRecipe> getSerializer() {
        return VARecipeType.SMITHING_GILD_SERIALIZER;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = PlacementInfo.create(List.of(TEMPLATE, this.base, this.addition));
        }

        return this.ingredientPlacement;
    }

    @Override
    public Optional<Ingredient> templateIngredient() {
        return Optional.of(TEMPLATE);
    }

    @Override
    public Ingredient baseIngredient() {
        return this.base;
    }

    @Override
    public Optional<Ingredient> additionIngredient() {
        return Optional.of(this.addition);
    }

    public static class Serializer implements RecipeSerializer<SmithingGildRecipe> {
        private static final MapCodec<SmithingGildRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                                Ingredient.CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
                                GildType.ENTRY_CODEC.fieldOf("gild_type").forGetter(recipe -> recipe.type)
                        )
                        .apply(instance, SmithingGildRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, SmithingGildRecipe> PACKET_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC,
                recipe -> recipe.base,
                Ingredient.CONTENTS_STREAM_CODEC,
                recipe -> recipe.addition,
                GildType.ENTRY_PACKET_CODEC,
                recipe -> recipe.type,
                SmithingGildRecipe::new
        );

        @Override
        public MapCodec<SmithingGildRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SmithingGildRecipe> streamCodec() {
            return PACKET_CODEC;
        }
    }
}
