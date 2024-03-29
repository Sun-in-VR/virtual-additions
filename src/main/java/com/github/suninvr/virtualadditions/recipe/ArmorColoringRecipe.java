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
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Function;

public class ArmorColoringRecipe implements Recipe<Inventory>, ColoringStationRecipe {
    public final Ingredient item;
    public final int index;
    public final DyeItem dyeItem;

    public ArmorColoringRecipe(Ingredient item, int index) {
        this.item = item;
        this.index = index;
        this.dyeItem = item.getMatchingStacks()[0].getItem() instanceof DyeItem dyeItem ? dyeItem : null;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return inventory.getStack(0).isIn(ItemTags.DYEABLE);
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public ItemStack craftWithDye(Inventory input, DynamicRegistryManager registryManager, ColoringStationBlockEntity.DyeContents dyeContents) {
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
    public ItemStack craft(Inventory inventory, RegistryWrapper.WrapperLookup wrapperLookup) {
        ItemStack stack = inventory.getStack(1);
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

    public static class Serializer implements RecipeSerializer<ArmorColoringRecipe> {
        private final MapCodec<ArmorColoringRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("dye").forGetter(armorColoringRecipe -> armorColoringRecipe.item),
                        Codec.INT.optionalFieldOf("index", 0).forGetter(recipe -> recipe.index)
                ).apply(instance, ArmorColoringRecipe::new)
        );
        private static final PacketCodec<RegistryByteBuf, ArmorColoringRecipe> PACKET_CODEC = PacketCodec.ofStatic(Serializer::write, Serializer::read);

        @Override
        public MapCodec<ArmorColoringRecipe> codec() {
            return (MapCodec<ArmorColoringRecipe>) CODEC;
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
