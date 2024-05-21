package com.github.suninvr.virtualadditions.screen;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.recipe.ColoringStationRecipe;
import com.github.suninvr.virtualadditions.registry.VARecipeType;
import com.github.suninvr.virtualadditions.registry.VAScreenHandler;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;

public class ColoringStationScreenHandler extends ScreenHandler {
    private final ColoringStationBlockEntity.DyeContents dyeContents;
    private ColoringStationBlockEntity.DyeContents dyeContentsAdder;
    private final World world;
    private List<RecipeEntry<ColoringStationRecipe>> availableRecipes = Lists.newArrayList();
    private ItemStack inputStack = ItemStack.EMPTY;
    private final Property selectedRecipe = Property.create();
    private final PropertyDelegate propertyDelegate;
    private final ScreenHandlerContext context;
    long lastTakeTime = 0;
    final Slot dyeSlot;
    final Slot inputSlot;
    final Slot outputSlot;
    Runnable contentsChangedListener = () -> {};
    public final Inventory input = new SimpleInventory(2){

        @Override
        public void markDirty() {
            super.markDirty();
            ColoringStationScreenHandler.this.onContentChanged(this);
            ColoringStationScreenHandler.this.contentsChangedListener.run();
        }
    };
    public final RecipeInput recipeInput = new RecipeInput() {
        @Override
        public ItemStack getStackInSlot(int slot) {
            return ColoringStationScreenHandler.this.input.getStack(1);
        }

        @Override
        public int getSize() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return this.getStackInSlot(0).isEmpty();
        }
    };
    final CraftingResultInventory output = new CraftingResultInventory();

    public ColoringStationScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, new ArrayPropertyDelegate(6));
    }

    public ColoringStationScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context, PropertyDelegate propertyDelegate) {
        super(VAScreenHandler.COLORING_STATION, syncId);
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        this.dyeContents = new ColoringStationBlockEntity.DyeContents(propertyDelegate){
            @Override
            public int getR() {
                return ColoringStationScreenHandler.this.propertyDelegate.get(0);
            }
            @Override
            public int getG() {
                return ColoringStationScreenHandler.this.propertyDelegate.get(1);
            }
            @Override
            public int getB() {
                return ColoringStationScreenHandler.this.propertyDelegate.get(2);
            }
            @Override
            public int getY() {
                return ColoringStationScreenHandler.this.propertyDelegate.get(3);
            }
            @Override
            public int getK() {
                return ColoringStationScreenHandler.this.propertyDelegate.get(4);
            }
            @Override
            public int getW() {
                return ColoringStationScreenHandler.this.propertyDelegate.get(5);
            }

            @Override
            public void setR(int r) {
                ColoringStationScreenHandler.this.propertyDelegate.set(0, r);
            }

            @Override
            public void setG(int r) {
                ColoringStationScreenHandler.this.propertyDelegate.set(1, r);
            }

            @Override
            public void setB(int r) {
                ColoringStationScreenHandler.this.propertyDelegate.set(2, r);
            }

            @Override
            public void setY(int r) {
                ColoringStationScreenHandler.this.propertyDelegate.set(3, r);
            }

            @Override
            public void setK(int r) {
                ColoringStationScreenHandler.this.propertyDelegate.set(4, r);
            }

            @Override
            public void setW(int r) {
                ColoringStationScreenHandler.this.propertyDelegate.set(5, r);
            }
        };
        this.dyeContentsAdder = new ColoringStationBlockEntity.DyeContents();
        this.world = playerInventory.player.getWorld();
        this.context = context;

        int i;
        this.dyeSlot = this.addSlot(new Slot(this.input, 0,10, 15));
        this.inputSlot = this.addSlot(new Slot(this.input, 1,28, 15));
        this.outputSlot = this.addSlot(new Slot(this.output, 2,143, 33){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                stack.onCraftByPlayer(player.getWorld(), player, stack.getCount());
                ColoringStationScreenHandler.this.inputSlot.takeStack(1);
                ColoringStationScreenHandler.this.addDyeContents();
                ColoringStationScreenHandler.this.updateDyeInput();
                ColoringStationScreenHandler.this.populateResult();
                context.run((world, pos) -> {
                    long l = world.getTime();
                    if (ColoringStationScreenHandler.this.lastTakeTime != l) {
                        world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ColoringStationScreenHandler.this.lastTakeTime = l;
                    }
                });
                super.onTakeItem(player, stack);
            }

        });
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.updateInput();
        this.populateResult();
    }

    private void addDyeContents() {
        this.dyeContents.add(this.dyeContentsAdder);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }

    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    public List<RecipeEntry<ColoringStationRecipe>> getAvailableRecipes() {
        return this.availableRecipes;
    }

    public int getAvailableRecipeCount() {
        return this.availableRecipes.size();
    }

    public void setContentsChangedListener(Runnable contentsChangedListener) {
        this.contentsChangedListener = contentsChangedListener;
    }

    public boolean canCraft() {
        return !this.availableRecipes.isEmpty();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot clickedSlot = this.slots.get(slot);
        if (clickedSlot != null && clickedSlot.hasStack()) {
            ItemStack itemStack2 = clickedSlot.getStack();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (slot == 2) {
                item.onCraftByPlayer(itemStack2, player.getWorld(), player);
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                clickedSlot.onQuickTransfer(itemStack2, itemStack);
            } else if (
                    slot <= 1
                            ? !this.insertItem(itemStack2, 3, 39, false)
                            : (this.world.getRecipeManager().getFirstMatch(VARecipeType.COLORING, new SingleStackRecipeInput(itemStack2), this.world).isPresent()
                                ? !this.insertItem(itemStack2, 1, 2, false)
                                : itemStack2.getItem() instanceof DyeItem ? !this.insertItem(itemStack2, 0, 1, false) : (slot >= 3 && slot < 30
                                    ? !this.insertItem(itemStack2, 30, 39, false)
                                    : slot >= 30 && slot < 39 && !this.insertItem(itemStack2, 3, 30, false)))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                clickedSlot.setStack(ItemStack.EMPTY);
            }
            clickedSlot.markDirty();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            clickedSlot.onTakeItem(player, itemStack2);
            this.sendContentUpdates();
        }
        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (this.isInBounds(id)) {
            this.selectedRecipe.set(id);
            this.populateResult();
        }
        return true;
    }

    private boolean isInBounds(int id) {
        return id >= 0 && id < this.availableRecipes.size();
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = this.inputSlot.getStack();
        if (!itemStack.isOf(this.inputStack.getItem())) {
            this.inputStack = itemStack.copy();
            this.updateInput();
        }
        this.updateDyeInput();
    }

    private void updateDyeInput() {
        ItemStack dyeStack = this.dyeSlot.getStack();
        if (this.dyeSlot.hasStack()) {
            this.dyeContents.addDye(dyeStack);
            this.populateResult();
        }
    }

    private void updateInput() {
        this.availableRecipes.clear();
        this.selectedRecipe.set(-1);
        this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
        this.availableRecipes = this.world.getRecipeManager().getAllMatches(VARecipeType.COLORING, this.recipeInput, this.world);
        this.availableRecipes.sort(Comparator.comparingInt(o -> o.value().getIndex()));
    }

    void populateResult() {
        if (!this.availableRecipes.isEmpty() && this.isInBounds(this.selectedRecipe.get())) {
            RecipeEntry<ColoringStationRecipe> recipeEntry = this.availableRecipes.get(this.selectedRecipe.get());
            ItemStack itemStack = recipeEntry.value().craftWithDye(this.recipeInput, this.world.getRegistryManager(), this.dyeContents);
            this.dyeContentsAdder = recipeEntry.value().getDyeCost();
            if (itemStack.isItemEnabled(this.world.getEnabledFeatures())) {
                this.output.setLastRecipe(recipeEntry);
                this.outputSlot.setStackNoCallbacks(itemStack);
            } else {
                this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
            }
        } else {
            this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
        }
        this.sendContentUpdates();
    }

    public ColoringStationBlockEntity.DyeContents getDyeContents() {
        return this.dyeContents;
    }
}
