package com.github.suninvr.virtualadditions.recipe;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import com.github.suninvr.virtualadditions.registry.VARecipeType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class ArmorColoringRecipe implements Recipe<RecipeInput>, ColoringStationRecipe {
    public final Ingredient item;
    public final int index;
    public final DyeItem dyeItem;
    private IngredientPlacement ingredientPlacement;

    public ArmorColoringRecipe(Ingredient item, int index) {
        this.item = item;
        this.index = index;
        RegistryKey<Item> key = item.getMatchingStacks().getFirst().getKey().orElse(null);
        this.dyeItem = key == null ? null : Registries.ITEM.get(key) instanceof DyeItem dyeItem1 ? dyeItem1 : null;
    }

    @Override
    public boolean matches(RecipeInput inventory, World world) {
        return inventory.getStackInSlot(0).isIn(ItemTags.DYEABLE);
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public ItemStack craftWithDye(RecipeInput input, DynamicRegistryManager registryManager, ColoringStationBlockEntity.DyeContents dyeContents) {
        ColoringStationBlockEntity.DyeContents cost = this.getDyeCost(true);
        if (dyeContents.getR() - cost.getR() >= 0 && dyeContents.getG() - cost.getG() >= 0 && dyeContents.getB() - cost.getB() >= 0 && dyeContents.getY() - cost.getY() >= 0 && dyeContents.getK() - cost.getK() >= 0 && dyeContents.getW() - cost.getW() >= 0) {
            return craft(input, registryManager);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ColoringStationBlockEntity.DyeContents getDyeCost(boolean inverted) {
        ColoringStationBlockEntity.DyeContents contents = this.dyeItem != null ? VADyeColors.getContents(this.dyeItem, 4) : ColoringStationBlockEntity.DyeContents.empty();
        if (!inverted) contents.multiply(-1);
        return contents;
    }

    @Override
    public ItemStack getResultStack(DynamicRegistryManager registryManager, ItemStack input) {
        return getResultStack(input);
    }

    public ItemStack getResultStack(ItemStack input) {
        if (!input.isEmpty() && this.dyeItem != null) {
            ItemStack result = input.copy();
            return DyedColorComponent.setColor(result, List.of(this.dyeItem));
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack craft(RecipeInput inventory, RegistryWrapper.WrapperLookup wrapperLookup) {
        ItemStack stack = inventory.getStackInSlot(1);
        return this.getResultStack( stack);
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup wrapperLookup) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VARecipeType.ARMOR_COLORING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return VARecipeType.COLORING;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forMultipleSlots(List.of(Optional.of(this.item)));
        }

        return this.ingredientPlacement;
    }

    public static class Serializer implements RecipeSerializer<ArmorColoringRecipe> {
        private final MapCodec<ArmorColoringRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Ingredient.CODEC.fieldOf("dye").forGetter(armorColoringRecipe -> armorColoringRecipe.item),
                        Codec.INT.optionalFieldOf("index", 0).forGetter(recipe -> recipe.index)
                ).apply(instance, ArmorColoringRecipe::new)
        );
        private static final PacketCodec<RegistryByteBuf, ArmorColoringRecipe> PACKET_CODEC = PacketCodec.ofStatic(Serializer::write, Serializer::read);

        @Override
        public MapCodec<ArmorColoringRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ArmorColoringRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static ArmorColoringRecipe read(RegistryByteBuf buf) {
            Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
            int index = buf.readInt();
            return new ArmorColoringRecipe(ingredient, index);
        }

        private static void write(RegistryByteBuf buf, ArmorColoringRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.item);
            buf.writeInt(recipe.index);
        }
    }
}
