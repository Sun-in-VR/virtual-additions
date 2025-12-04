package com.github.suninvr.virtualadditions.screen;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.block.entity.DyeContents;
import com.github.suninvr.virtualadditions.interfaces.RecipeManagerInterface;
import com.github.suninvr.virtualadditions.network.ColoringRecipesPayload;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipeDisplay;
import com.github.suninvr.virtualadditions.recipe.ColoringStationRecipe;
import com.github.suninvr.virtualadditions.registry.VAMenus;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ColoringStationMenu extends AbstractContainerMenu {
    private final DyeContents dyeContents;
    private DyeContents dyeContentsAdder;
    private final Level world;
    private final Inventory playerInventory;
    private ColoringRecipeDisplay.Grouping<ColoringStationRecipe> coloringRecipes = ColoringRecipeDisplay.Grouping.empty();
    private List<ColoringRecipeData> recipeData = new ArrayList<>();
    public static List<ColoringRecipeData> recipeDataOnLoad = new ArrayList<>();
    private List<RecipeHolder<ColoringStationRecipe>> recipeEntries = new ArrayList<>();
    private ItemStack inputStack = ItemStack.EMPTY;
    private final DataSlot selectedRecipe = DataSlot.standalone();
    private final ContainerData propertyDelegate;
    private final ContainerLevelAccess context;
    long lastTakeTime = 0;
    final Slot dyeSlot;
    final Slot inputSlot;
    final Slot outputSlot;
    Runnable contentsChangedListener = () -> {};
    public final Container input = new SimpleContainer(2){
        @Override
        public void setChanged() {
            super.setChanged();
            ColoringStationMenu.this.slotsChanged(this);
            ColoringStationMenu.this.contentsChangedListener.run();
        }
    };
    public final RecipeInput recipeInput = new RecipeInput() {
        @Override
        public ItemStack getItem(int slot) {
            return ColoringStationMenu.this.input.getItem(1);
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return this.getItem(0).isEmpty();
        }
    };
    final ResultContainer output = new ResultContainer();

    public ColoringStationMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL, new SimpleContainerData(6));
    }

    public ColoringStationMenu(int syncId, Inventory playerInventory, final ContainerLevelAccess context, ContainerData propertyDelegate) {
        super(VAMenus.COLORING_STATION, syncId);
        this.propertyDelegate = propertyDelegate;
        this.addDataSlots(propertyDelegate);
        this.dyeContents = new DyeContents(propertyDelegate){
            @Override
            public int getR() {
                return ColoringStationMenu.this.propertyDelegate.get(0);
            }
            @Override
            public int getG() {
                return ColoringStationMenu.this.propertyDelegate.get(1);
            }
            @Override
            public int getB() {
                return ColoringStationMenu.this.propertyDelegate.get(2);
            }
            @Override
            public int getY() {
                return ColoringStationMenu.this.propertyDelegate.get(3);
            }
            @Override
            public int getK() {
                return ColoringStationMenu.this.propertyDelegate.get(4);
            }
            @Override
            public int getW() {
                return ColoringStationMenu.this.propertyDelegate.get(5);
            }

            @Override
            public void setR(int r) {
                ColoringStationMenu.this.propertyDelegate.set(0, r);
            }

            @Override
            public void setG(int r) {
                ColoringStationMenu.this.propertyDelegate.set(1, r);
            }

            @Override
            public void setB(int r) {
                ColoringStationMenu.this.propertyDelegate.set(2, r);
            }

            @Override
            public void setY(int r) {
                ColoringStationMenu.this.propertyDelegate.set(3, r);
            }

            @Override
            public void setK(int r) {
                ColoringStationMenu.this.propertyDelegate.set(4, r);
            }

            @Override
            public void setW(int r) {
                ColoringStationMenu.this.propertyDelegate.set(5, r);
            }
        };
        this.dyeContentsAdder = new DyeContents();
        this.world = playerInventory.player.level();
        this.playerInventory = playerInventory;
        this.context = context;

        int i;
        this.dyeSlot = this.addSlot(new Slot(this.input, 0,10, 15));
        this.inputSlot = this.addSlot(new Slot(this.input, 1,28, 15));
        this.outputSlot = this.addSlot(new Slot(this.output, 2,143, 33){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                stack.onCraftedBy(player, stack.getCount());
                ColoringStationMenu.this.output.awardUsedRecipes(player, List.of(ColoringStationMenu.this.inputStack));
                ColoringStationMenu.this.inputSlot.remove(1);
                ColoringStationMenu.this.addDyeContents();
                ColoringStationMenu.this.updateDyeInput();
                ColoringStationMenu.this.populateResult();
                ColoringStationMenu.this.context.execute((world, pos) -> {
                    long l = world.getGameTime();
                    if (ColoringStationMenu.this.lastTakeTime != l) {
                        world.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ColoringStationMenu.this.lastTakeTime = l;
                    }
                    ColoringStationMenu.this.markBlockEntityDirty();
                });
                super.onTake(player, stack);
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
        if (this.recipeData.isEmpty() && !recipeDataOnLoad.isEmpty()) this.setRecipeData(recipeDataOnLoad);
        this.populateResult();
    }

    private void addDyeContents() {
        this.dyeContents.add(this.dyeContentsAdder);
        ColoringStationMenu.this.markBlockEntityDirty();
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.context.execute((world, pos) -> this.clearContainer(player, this.input));
    }

    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    public ColoringRecipeData getRecipeData(int i) {
      return this.recipeData.get(i);
    }

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
    public ItemStack quickMoveStack(Player player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot clickedSlot = this.slots.get(slot);
        if (clickedSlot.hasItem()) {
            ItemStack itemStack2 = clickedSlot.getItem();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (slot == 2) {
                item.onCraftedBy(itemStack2, player);
                if (!this.moveItemStackTo(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                clickedSlot.onQuickCraft(itemStack2, itemStack);
            } else if (slot <= 1 ? !this.moveItemStackTo(itemStack2, 3, 39, false)
                            : !(itemStack2.getItem() instanceof DyeItem)
                                ? !this.moveItemStackTo(itemStack2, 1, 2, false)
                                : itemStack2.getItem() instanceof DyeItem ? !this.moveItemStackTo(itemStack2, 0, 1, false) : (slot >= 3 && slot < 30
                                    ? !this.moveItemStackTo(itemStack2, 30, 39, false)
                                    : slot >= 30 && slot < 39 && !this.moveItemStackTo(itemStack2, 3, 30, false))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                clickedSlot.setByPlayer(ItemStack.EMPTY);
            }
            clickedSlot.setChanged();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            clickedSlot.onTake(player, itemStack2);
            this.broadcastChanges();
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
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
    public void slotsChanged(Container inventory) {
        ItemStack itemStack = this.inputSlot.getItem();
        if (!itemStack.equals(this.inputStack)) {
            if (!(itemStack.is(this.inputStack.getItem()))) {
                this.updateInput(itemStack);
            }
            this.populateResult();
            this.inputStack = itemStack.copy();
        }
        this.updateDyeInput();
    }

    private void updateDyeInput() {
        ItemStack dyeStack = this.dyeSlot.getItem();
        if (this.dyeSlot.hasItem()) {
            this.dyeContents.addDye(dyeStack);
            this.populateResult();
            ColoringStationMenu.this.markBlockEntityDirty();
        }
    }

    private void updateInput(ItemStack stack) {
        this.selectedRecipe.set(-1);
        this.outputSlot.set(ItemStack.EMPTY);
        if (!this.world.isClientSide()) {
            List<ColoringRecipeData> recipeDataList = new ArrayList<>();
            List<RecipeHolder<ColoringStationRecipe>> recipeEntries = new ArrayList<>();
            this.coloringRecipes = ((RecipeManagerInterface) this.world.recipeAccess()).virtualAdditions$getColoringRecipes().filter(stack);
            this.coloringRecipes.entries().forEach(entry -> {
                if (entry.recipe().recipeEntry().isPresent()) {
                    RecipeHolder<ColoringStationRecipe> recipe = entry.recipe().recipeEntry().get();
                    ItemStack resultStack;
                    if (!(resultStack = recipe.value().getResultStack(stack)).isEmpty()) {
                        recipeDataList.add(new ColoringRecipeData(recipe.value().getIndex(), resultStack, recipe.value().getDyeCost()));
                        recipeEntries.add(recipe);
                    }
                }
            });
            this.setRecipeData(recipeDataList);
            this.setRecipeEntries(recipeEntries);
            ColoringRecipesPayload payload = new ColoringRecipesPayload(this.recipeData);
            ServerPlayNetworking.send((ServerPlayer)playerInventory.player, payload);
        }
    }

    public void setRecipeData(List<ColoringRecipeData> data) {
        this.recipeData.clear();
        this.recipeData = new ArrayList<>(data);
        this.recipeData.sort(Comparator.comparingInt(o -> o.index));
    }

    private void setRecipeEntries(List<RecipeHolder<ColoringStationRecipe>> entries) {
        this.recipeEntries.clear();
        this.recipeEntries = new ArrayList<>(entries);
        this.recipeEntries.sort(Comparator.comparingInt(o -> o.value().getIndex()));
    }

    void populateResult() {
        if (this.world.isClientSide()) return;
        Optional<RecipeHolder<ColoringStationRecipe>> optional;
        int i = this.selectedRecipe.get();
        if (!(this.recipeEntries == null) && !this.recipeEntries.isEmpty() && this.isInBounds(i)) {
            optional = Optional.of(this.recipeEntries.get(i));
        } else {
            optional = Optional.empty();
        }

        if (optional.isPresent()) {
            ColoringStationRecipe recipe = optional.get().value();
            ItemStack itemStack = recipe.craftWithDye(this.recipeInput, this.world.registryAccess(), this.dyeContents);
            this.dyeContentsAdder = recipe.getDyeCost();
            if (itemStack.isItemEnabled(this.world.enabledFeatures())) {
                this.output.setRecipeUsed(optional.get());
                this.outputSlot.set(itemStack);
            } else {
                this.outputSlot.set(ItemStack.EMPTY);
            }
        }
    }

    private void markBlockEntityDirty() {
        this.context.execute((world1, pos) -> {
            if (world1.getBlockEntity(pos) instanceof ColoringStationBlockEntity blockEntity) blockEntity.setChanged();
        });
    }

    public DyeContents getDyeContents() {
        return this.dyeContents;
    }

    public record ColoringRecipeData(int index, ItemStack stack, DyeContents dyeCost) {
        public static final StreamCodec<RegistryFriendlyByteBuf, ColoringRecipeData> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, ColoringRecipeData::index,
                ItemStack.OPTIONAL_STREAM_CODEC, ColoringRecipeData::stack,
                DyeContents.PACKET_CODEC, ColoringRecipeData::dyeCost,
                ColoringRecipeData::new
        );

        public boolean isInputValid(ItemStack stack) {
            return !stack.is(this.stack().getItem()) || stack.is(ItemTags.DYEABLE);
        }

        public boolean isDyeContentSufficient(DyeContents contents) {
            return contents.canAdd(this.dyeCost());
        }
    }
}
