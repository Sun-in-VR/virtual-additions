package com.github.suninvr.virtualadditions.screen;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.interfaces.RecipeManagerInterface;
import com.github.suninvr.virtualadditions.network.ColoringStationS2CPayload;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipeDisplay;
import com.github.suninvr.virtualadditions.recipe.ColoringStationRecipe;
import com.github.suninvr.virtualadditions.registry.VARecipeType;
import com.github.suninvr.virtualadditions.registry.VAScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ColoringStationScreenHandler extends ScreenHandler {
    private final ColoringStationBlockEntity.DyeContents dyeContents;
    private ColoringStationBlockEntity.DyeContents dyeContentsAdder;
    private final World world;
    private final PlayerInventory playerInventory;
    private ColoringRecipeDisplay.Grouping<ColoringStationRecipe> coloringRecipes = ColoringRecipeDisplay.Grouping.empty();
    private List<ColoringRecipeData> recipeData = new ArrayList<>();
    private List<RecipeEntry<ColoringStationRecipe>> recipeEntries = new ArrayList<>();
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
        public int size() {
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
        this.playerInventory = playerInventory;
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
        this.updateInput(ItemStack.EMPTY);
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

    public ColoringRecipeData getRecipeData(int i) {
      return this.recipeData.get(i);
    };

    public int getAvailableRecipeCount() {
        return this.recipeData.size();
    }

    public void setContentsChangedListener(Runnable contentsChangedListener) {
        this.contentsChangedListener = contentsChangedListener;
    }

    public boolean canCraft() {
        return this.recipeData != null && !this.recipeData.isEmpty();
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
            } else if (slot <= 1 ? !this.insertItem(itemStack2, 3, 39, false)
                            : !(itemStack2.getItem() instanceof DyeItem)
                                ? !this.insertItem(itemStack2, 1, 2, false)
                                : itemStack2.getItem() instanceof DyeItem ? !this.insertItem(itemStack2, 0, 1, false) : (slot >= 3 && slot < 30
                                    ? !this.insertItem(itemStack2, 30, 39, false)
                                    : slot >= 30 && slot < 39 && !this.insertItem(itemStack2, 3, 30, false))) {
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
        return id >= 0 && id < this.recipeData.size();
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = this.inputSlot.getStack();
        if (!itemStack.isOf(this.inputStack.getItem())) {
            this.inputStack = itemStack.copy();
            this.updateInput(itemStack);
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

    private void updateInput(ItemStack stack) {
        this.selectedRecipe.set(-1);
        this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
        if (!this.world.isClient) {
            List<ColoringRecipeData> recipeDataList = new ArrayList<>();
            List<RecipeEntry<ColoringStationRecipe>> recipeEntries = new ArrayList<>();
            this.coloringRecipes = ((RecipeManagerInterface) this.world.getRecipeManager()).virtualAdditions$getColoringRecipes().filter(stack);
            this.coloringRecipes.entries().forEach(entry -> {
                if (entry.recipe().recipeEntry().isPresent()) {
                    RecipeEntry<ColoringStationRecipe> recipe = entry.recipe().recipeEntry().get();
                    recipeDataList.add(new ColoringRecipeData(recipe.value().getIndex(), recipe.value().getResultStack(stack), recipe.value().getDyeCost()));
                    recipeEntries.add(recipe);
                }
            });
            this.setRecipeData(recipeDataList);
            this.setRecipeEntries(recipeEntries);
            ColoringStationS2CPayload payload = new ColoringStationS2CPayload(this.recipeData);
            ServerPlayNetworking.send((ServerPlayerEntity)playerInventory.player, payload);
        }
    }

    public void setRecipeData(List<ColoringRecipeData> data) {
        this.recipeData.clear();
        this.recipeData = new ArrayList<>(data);
        this.recipeData.sort(Comparator.comparingInt(o -> o.index));
    }

    private void setRecipeEntries(List<RecipeEntry<ColoringStationRecipe>> entries) {
        this.recipeEntries.clear();
        this.recipeEntries = new ArrayList<>(entries);
        this.recipeEntries.sort(Comparator.comparingInt(o -> o.value().getIndex()));
    }

    void populateResult() {
        if (this.world.isClient) return;
        Optional<RecipeEntry<ColoringStationRecipe>> optional;
        int i = this.selectedRecipe.get();
        if (!(this.recipeEntries == null) && !this.recipeEntries.isEmpty() && this.isInBounds(i)) {
            optional = Optional.of(this.recipeEntries.get(i));
        } else {
            optional = Optional.empty();
        }

        if (optional.isPresent()) {
            ColoringStationRecipe recipe = optional.get().value();
            ItemStack itemStack = recipe.craftWithDye(this.recipeInput, this.world.getRegistryManager(), this.dyeContents);
            this.dyeContentsAdder = recipe.getDyeCost();
            if (itemStack.isItemEnabled(this.world.getEnabledFeatures())) {
                this.output.setLastRecipe(optional.get());
                this.outputSlot.setStackNoCallbacks(itemStack);
            } else {
                this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
            }
        }
    }

    public ColoringStationBlockEntity.DyeContents getDyeContents() {
        return this.dyeContents;
    }

    public record ColoringRecipeData(int index, ItemStack stack, ColoringStationBlockEntity.DyeContents dyeCost) {
        public static final PacketCodec<RegistryByteBuf, ColoringRecipeData> CODEC = PacketCodec.tuple(
                PacketCodecs.INTEGER, ColoringRecipeData::index,
                ItemStack.OPTIONAL_PACKET_CODEC, ColoringRecipeData::stack,
                ColoringStationBlockEntity.DyeContents.PACKET_CODEC, ColoringRecipeData::dyeCost,
                ColoringRecipeData::new
        );
    }
}
