package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.component.GildTypeComponent;
import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VARecipeType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;
import java.util.Optional;

public class SmithingGildRecipe implements SmithingRecipe {
    private static final Ingredient TEMPLATE = Ingredient.ofItem(VAItems.TOOL_GILD_SMITHING_TEMPLATE);
    private IngredientPlacement ingredientPlacement;
    final Ingredient base;
    final Ingredient addition;
    final RegistryEntry<GildType> type;

    public SmithingGildRecipe(Ingredient base, Ingredient addition, RegistryEntry<GildType> type) {
        this.base = base;
        this.addition = addition;
        this.type = type;
    }

    @Override
    public ItemStack craft(SmithingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        if (input.base().contains(VADataComponentTypes.GILD_TYPE_COMPONENT)) return null;
        ItemStack result = input.base().copyWithCount(1);
        result.set(VADataComponentTypes.GILD_TYPE_COMPONENT, new GildTypeComponent(this.type));
        this.type.value().modifyStackOnCrafted(result);
        return result;
    }

    @Override
    public RecipeSerializer<? extends SmithingRecipe> getSerializer() {
        return VARecipeType.SMITHING_GILD_SERIALIZER;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forShapeless(List.of(TEMPLATE, this.base, this.addition));
        }

        return this.ingredientPlacement;
    }

    @Override
    public Optional<Ingredient> template() {
        return Optional.of(TEMPLATE);
    }

    @Override
    public Ingredient base() {
        return this.base;
    }

    @Override
    public Optional<Ingredient> addition() {
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
        public static final PacketCodec<RegistryByteBuf, SmithingGildRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC,
                recipe -> recipe.base,
                Ingredient.PACKET_CODEC,
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
        public PacketCodec<RegistryByteBuf, SmithingGildRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
