package com.github.suninvr.virtualadditions.screenhandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import com.github.suninvr.virtualadditions.recipe.WorkbenchDoubleRecipe;
import com.github.suninvr.virtualadditions.recipe.WorkbenchSingleRecipe;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VARecipeTypes;
import com.github.suninvr.virtualadditions.registry.VAScreenHandlers;

import java.util.List;
import java.util.Optional;

public class WorkbenchScreenHandler extends AbstractRecipeScreenHandler<CraftingInventory> {

    protected final CraftingResultInventory result;
    protected final CraftingInventory input;

    protected final ScreenHandlerContext context;
    protected final PlayerEntity player;

    protected int workingSlot;
    public int damage;
    public Property damageProperty;
    public Property damageColorProperty;
    protected boolean isDoubleRecipe;
    protected Identifier sound;
    protected long lastTakeTime;
    private static List<WorkbenchDoubleRecipe> doubleRecipes;
    private static List<WorkbenchSingleRecipe> singleRecipes;

    public WorkbenchScreenHandler( int syncId, PlayerInventory playerInventory ) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public WorkbenchScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(VAScreenHandlers.WORKBENCH_SCREEN_HANDLER, syncId);
        this.context = context;
        this.player = playerInventory.player;
        this.input = new CraftingInventory(this, 3, 1);
        this.result = new CraftingResultInventory();
        doubleRecipes = playerInventory.player.world.getRecipeManager().listAllOfType(VARecipeTypes.WORKBENCH_DOUBLE_RECIPE);
        singleRecipes = playerInventory.player.world.getRecipeManager().listAllOfType(VARecipeTypes.WORKBENCH_SINGLE_RECIPE);

        this.damageProperty = this.addProperty(new Property() {
            private int damage;
            @Override
            public int get() {
                return this.damage;
            }
            @Override
            public void set(int value) {
                this.damage = value;
            }
        });
        this.damageColorProperty = this.addProperty(new Property() {
            private int damageColor;
            @Override
            public int get() {
                return this.damageColor;
            }
            @Override
            public void set(int value) {
                this.damageColor = value;
            }
        });

        this.setProperty(0, 0);
        this.setProperty(1, 0);
        this.addSlot(new Slot(this.input, 0, 18, 47));
        this.addSlot(new Slot(this.input, 1, 67, 47));
        this.addSlot(new Slot(this.input, 2, 85, 47));
        this.addSlot(new Slot(this.result, 3, 143, 47) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                return true;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                super.onTakeItem(player, stack);
                WorkbenchScreenHandler.this.onTakeOutput(player);
            }
        });

        //Player Inventory Slots
        int k;
        for(k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        //Player Hotbar Slots
        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public void setProperty(int id, int value) {
        super.setProperty(id, value);
        this.sendContentUpdates();
    }

    protected void updateResult(ScreenHandler handler, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (!world.isClient) {

            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
            ItemStack resultStack = ItemStack.EMPTY;
            SimpleInventory inventory = new SimpleInventory(
                    craftingInventory.getStack(0),
                    craftingInventory.getStack(1),
                    craftingInventory.getStack(2)
            );

            //Double Item Recipe
            Optional<WorkbenchDoubleRecipe> matchDouble = world.getRecipeManager().getFirstMatch(WorkbenchDoubleRecipe.Type.INSTANCE, inventory, world);
            if (matchDouble.isPresent()) {
                WorkbenchDoubleRecipe recipe = matchDouble.get();
                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, recipe)) {
                    this.isDoubleRecipe = true;
                    this.updateDamage(recipe.getDamage());
                    this.sound = recipe.getSound();
                    resultStack = recipe.craft(inventory);
                }
            } else {
                //Single Item Recipe
                Optional<WorkbenchSingleRecipe> matchSingle = world.getRecipeManager().getFirstMatch(WorkbenchSingleRecipe.Type.INSTANCE, inventory, world);
                if (matchSingle.isPresent()) {
                    WorkbenchSingleRecipe recipe;
                    recipe = matchSingle.get();
                    if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, recipe)) {
                        resultStack = recipe.craft(inventory);
                        this.isDoubleRecipe = false;
                        this.updateDamage(recipe.getDamage());
                        this.sound = recipe.getSound();
                        this.workingSlot = recipe.getWorkingSlot(inventory);
                    }

                } else {
                    this.updateDamage(0);
                }
            }
            resultInventory.setStack(0, resultStack);
            handler.setPreviousTrackedSlot(0, resultStack);
            serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 3, resultStack));
        }
    }

    protected void onTakeOutput(PlayerEntity player) {
        if(!this.isDoubleRecipe) {
            this.input.getStack(workingSlot).decrement(1);
        } else {
            this.input.getStack(1).decrement(1);
            this.input.getStack(2).decrement(1);
        }
        if (this.input.getStack(0).isDamageable()) {
            this.input.getStack(0).damage(this.damage, player, (p) -> {});
        }

        this.context.run((world, pos) -> {
            long currentTime = world.getTime();
            if(Registry.SOUND_EVENT.containsId(sound) && currentTime != this.lastTakeTime) {
                world.playSound(null, pos, Registry.SOUND_EVENT.get(sound), SoundCategory.BLOCKS, 1.0F, 1.0F);
                this.lastTakeTime = currentTime;
            }
        });

        this.result.unlockLastRecipe(player);

        this.updateResult(this, player.world, player, this.input, this.result);
    }

    protected void updateDamage(int damage) {
        this.damage = damage;
        this.setProperty(0, damage);
        this.setDamageColorProperty(damage, this.slots.get(0).getStack().getMaxDamage() - this.slots.get(0).getStack().getDamage());
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == this.input) {
            this.context.run((world, pos) -> updateResult(this, world, this.player, this.input, this.result));
        }
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> {
            this.dropInventory(player, this.input);
        });
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false; //canUse(this.context, player, VABlocks.WORKBENCH);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();

            //From Result Slot
            if (index == 3) {
                if (this.lastTakeTime != player.getWorld().getTime() || this.damage < this.getSlot(0).getStack().getMaxDamage() - this.getSlot(0).getStack().getDamage()) {
                    if (!this.insertItem(itemStack2, 4, 40, true)) {
                        return ItemStack.EMPTY;
                    }
                    slot.onQuickTransfer(itemStack2, itemStack);
                }
            //From Player Inventory
            } else if (index > 3 && index < 40) {
                if (!slots.get(0).hasStack() && isUsableAsTool(itemStack2)) {
                    if (!this.insertItem(itemStack2, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                    slot.onQuickTransfer(itemStack2, itemStack);
                } else {
                    if (!this.insertItem(itemStack2, 1, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                    slot.onQuickTransfer(itemStack2, itemStack);
                }

            //From Crafting Slots
            } else if (!this.insertItem(itemStack2, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    public int getDamage() {
        return this.damage;
    }

    private void setDamageColorProperty(int damage, int durability) {
        if (damage == 0) {
            this.setProperty(1, 0);
            return;
        }
        if (durability > damage) {
            this.setProperty(1, 1);
        } else {
            this.setProperty(1, 2);
        }
    }

    protected boolean isUsableAsTool(ItemStack stack) {
        boolean bl = doubleRecipes.stream().anyMatch( (recipe) -> recipe.testTool(stack) );
        return bl ? bl : singleRecipes.stream().anyMatch((recipe) -> recipe.testTool(stack));
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        if (this.input != null) {
            ((RecipeInputProvider)this.input).provideRecipeInputs(finder);
        }
    }

    @Override
    public void clearCraftingSlots() {
        this.getSlot(0).setStack(ItemStack.EMPTY);
        this.getSlot(1).setStack(ItemStack.EMPTY);
        this.getSlot(2).setStack(ItemStack.EMPTY);
    }

    @Override
    public boolean matches(Recipe<? super CraftingInventory> recipe) {
        return recipe.matches(this.input, this.player.getWorld());
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 3;
    }

    @Override
    public int getCraftingWidth() {
        return 3;
    }

    @Override
    public int getCraftingHeight() {
        return 1;
    }

    @Override
    public int getCraftingSlotCount() {
        return 4;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return VARecipeTypes.WORKBENCH_CATEGORY;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != 3;
    }
}
