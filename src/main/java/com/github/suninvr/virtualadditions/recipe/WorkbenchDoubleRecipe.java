package com.github.suninvr.virtualadditions.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public class WorkbenchDoubleRecipe implements Recipe<SimpleInventory> {
    private final Ingredient tool;
    private final Ingredient inputA;
    private final Ingredient inputB;
    private final int damage;
    private final Identifier sound;
    private final ItemStack result;
    private final Identifier id;

    public WorkbenchDoubleRecipe(Ingredient tool, Ingredient inputA, Ingredient inputB, int damage, Identifier sound, ItemStack outputStack, Identifier id) {
        this.tool = tool;
        this.inputA = inputA;
        this.inputB = inputB;
        this.damage = damage;
        this.sound = sound;
        this.result = outputStack;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (inventory.size() < 3) return false;
        return tool.test(inventory.getStack(0)) && (
                (inputA.test(inventory.getStack(1)) && inputB.test(inventory.getStack(2))) ||
                (inputA.test(inventory.getStack(2)) && inputB.test(inventory.getStack(1)))
        );
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 3;
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

    public Ingredient getInputA() {
        return inputA;
    }

    public Ingredient getInputB() {return inputB;}

    public int getDamage() {
        return damage;
    }

    public Identifier getSound() {
        return sound;
    }

    @Override
    public ItemStack createIcon() { return new ItemStack(VABlocks.STEEL_BLOCK); }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WorkbenchRecipeSerializer.INSTANCE;
    }

    public boolean testTool(ItemStack stack) {
        return this.tool.test(stack);
    }

    public static class Type implements RecipeType<WorkbenchDoubleRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static String ID = "workbench_double";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    static class WorkbenchRecipeJsonFormat {
        JsonObject tool;
        JsonObject inputA;
        JsonObject inputB;
        int damage;
        String sound;
        String output;
        int count;
    }

    public static class WorkbenchRecipeSerializer implements RecipeSerializer<WorkbenchDoubleRecipe>{

        @Override
        public WorkbenchDoubleRecipe read(Identifier id, JsonObject json) {
            WorkbenchRecipeJsonFormat recipeJson = new Gson().fromJson(json, WorkbenchRecipeJsonFormat.class);

            if (recipeJson.inputA == null || recipeJson.inputB == null || recipeJson.tool == null || recipeJson.output == null) {
                throw new JsonSyntaxException("A required attribute is missing!");
            }

            if (recipeJson.sound == null) recipeJson.sound = "";
            if (recipeJson.count == 0) recipeJson.count = 1;

            Ingredient tool = Ingredient.fromJson(recipeJson.tool);
            Ingredient inputA = Ingredient.fromJson(recipeJson.inputA);
            Ingredient inputB = Ingredient.fromJson(recipeJson.inputB);
            int damage = recipeJson.damage;

            Identifier sound = new Identifier(recipeJson.sound);

            Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.output))
                    .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.output));
            ItemStack output = new ItemStack(outputItem, recipeJson.count);


            return new WorkbenchDoubleRecipe(tool, inputA, inputB, damage, sound, output, id);
        }

        @Override
        public WorkbenchDoubleRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient tool = Ingredient.fromPacket(buf);
            Ingredient inputA = Ingredient.fromPacket(buf);
            Ingredient inputB = Ingredient.fromPacket(buf);
            int damage = buf.readInt();
            Identifier sound = buf.readIdentifier();
            ItemStack output = buf.readItemStack();
            return new WorkbenchDoubleRecipe(tool, inputA, inputB, damage, sound, output, id);
        }

        @Override
        public void write(PacketByteBuf buf, WorkbenchDoubleRecipe recipe) {
            recipe.tool.write(buf);
            recipe.inputA.write(buf);
            recipe.inputB.write(buf);
            buf.writeInt(recipe.damage);
            buf.writeIdentifier(recipe.sound);
            buf.writeItemStack(recipe.getOutput());
        }

        private WorkbenchRecipeSerializer() {}
        public static final WorkbenchRecipeSerializer INSTANCE = new WorkbenchRecipeSerializer();
        public static final Identifier ID = VirtualAdditions.idOf("workbench_double");


    }
}
