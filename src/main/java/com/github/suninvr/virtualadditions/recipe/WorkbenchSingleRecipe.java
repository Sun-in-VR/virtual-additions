package com.github.suninvr.virtualadditions.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VABlocks;

public class WorkbenchSingleRecipe implements Recipe<SimpleInventory> {
    private final Ingredient tool;
    private final Ingredient input;
    private final int damage;
    private final Identifier sound;
    private final ItemStack result;
    private final Identifier id;
    private final boolean persistent;

    public WorkbenchSingleRecipe(Ingredient tool, Ingredient input, int damage, Identifier sound, ItemStack outputStack, Identifier id, boolean persistent) {
        this.tool = tool;
        this.input = input;
        this.damage = damage;
        this.sound = sound;
        this.result = outputStack;
        this.id = id;
        this.persistent = persistent;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (inventory.size() < 3) return false;
        //return tool.test(inventory.getStack(0)) && (input.test(inventory.getStack(1)) || input.test(inventory.getStack(2)));
        if (input.test(inventory.getStack(1))) {
            return tool.test(inventory.getStack(0));
        } else if (input.test(inventory.getStack(2))) {
            return tool.test(inventory.getStack(0));
        }
        return false;
    }

    public int getWorkingSlot(SimpleInventory inventory) {
        if (inventory.size() < 3) return 0;
        if (input.test(inventory.getStack(1))) {
            return 1;
        } else if (input.test(inventory.getStack(2))) {
            return 2;
        }
        return 0;

    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        ItemStack itemStack = this.result.copy();
        if (this.persistent) {
            NbtCompound nbt = inventory.getStack(1).getNbt();
            if (nbt != null) itemStack.setNbt(nbt.copy());
        }

        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 2;
    }

    @Override
    public ItemStack getOutput() {
        return result;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    public Ingredient getTool() {
        return tool;
    }

    public Ingredient getInput() {
        return input;
    }

    public int getDamage() {
        return damage;
    }

    public Identifier getSound() {
        return sound;
    }

    public boolean isPersistent() {
        return persistent;
    }

    @Override
    public ItemStack createIcon() {return new ItemStack(VABlocks.STEEL_BLOCK);}

    public boolean testTool(ItemStack stack) {
        return this.tool.test(stack);
    };

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WorkbenchRecipeSerializer.INSTANCE;
    }


    public static class Type implements RecipeType<WorkbenchSingleRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static String ID = "workbench_single";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    static class WorkbenchRecipeJsonFormat {
        JsonObject tool;
        JsonObject input;
        int damage;
        String sound;
        String output;
        int count;
        boolean persistent;
    }

    public static class WorkbenchRecipeSerializer implements RecipeSerializer<WorkbenchSingleRecipe>{

        @Override
        public WorkbenchSingleRecipe read(Identifier id, JsonObject json) {
            WorkbenchRecipeJsonFormat recipeJson = new Gson().fromJson(json, WorkbenchRecipeJsonFormat.class);

            if (recipeJson.input == null || recipeJson.tool == null || recipeJson.output == null) {
                throw new JsonSyntaxException("A required attribute is missing!");
            }

            if(recipeJson.sound == null) {
                recipeJson.sound = "";
            }

            if (recipeJson.count == 0) recipeJson.count = 1;

            Ingredient tool = Ingredient.fromJson(recipeJson.tool);
            Ingredient input = Ingredient.fromJson(recipeJson.input);
            int damage = recipeJson.damage;

            Identifier sound = new Identifier(recipeJson.sound);

            Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.output))
                    .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.output));
            ItemStack output = new ItemStack(outputItem, recipeJson.count);

            boolean persistent = recipeJson.persistent;


            return new WorkbenchSingleRecipe(tool, input, damage, sound, output, id, persistent);
        }

        @Override
        public WorkbenchSingleRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient tool = Ingredient.fromPacket(buf);
            Ingredient input = Ingredient.fromPacket(buf);
            int damage = buf.readInt();
            Identifier sound = buf.readIdentifier();
            ItemStack output = buf.readItemStack();
            boolean persistent = buf.readBoolean();
            return new WorkbenchSingleRecipe(tool, input, damage, sound, output, id, persistent);
        }

        @Override
        public void write(PacketByteBuf buf, WorkbenchSingleRecipe recipe) {
            recipe.tool.write(buf);
            recipe.input.write(buf);
            buf.writeInt(recipe.damage);
            buf.writeIdentifier(recipe.sound);
            buf.writeItemStack(recipe.getOutput());
            buf.writeBoolean(recipe.persistent);
        }

        private WorkbenchRecipeSerializer() {}
        public static final WorkbenchRecipeSerializer INSTANCE = new WorkbenchRecipeSerializer();
        public static final Identifier ID = VirtualAdditions.idOf("workbench_single");


    }
}
