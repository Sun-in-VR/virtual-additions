package com.github.suninvr.virtualadditions.client.screen;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.recipe.ColoringStationRecipe;
import com.github.suninvr.virtualadditions.screen.ColoringStationScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class ColoringStationScreen extends HandledScreen<ColoringStationScreenHandler> {
    private static final Identifier SCROLLER_TEXTURE = Identifier.of("container/stonecutter/scroller");
    private static final Identifier SCROLLER_DISABLED_TEXTURE = Identifier.of("container/stonecutter/scroller_disabled");
    private static final Identifier RECIPE_SELECTED_TEXTURE = Identifier.of("container/stonecutter/recipe_selected");
    private static final Identifier RECIPE_HIGHLIGHTED_TEXTURE = Identifier.of("container/stonecutter/recipe_highlighted");
    private static final Identifier RECIPE_UNCRAFTABLE_TEXTURE = idOf("container/coloring_station/recipe_uncraftable");
    private static final Identifier RECIPE_TEXTURE = Identifier.of("container/stonecutter/recipe");
    private static final Identifier DYE_SLOT_TEXTURE = Identifier.of("container/loom/dye_slot");
    private static final Identifier TEXTURE = idOf("textures/gui/container/coloring_station.png");
    private static final Text NOT_ENOUGH_DYE_WARNING = Text.translatable("container.virtual_additions.coloring_station.not_enough_dye_warning").formatted(Formatting.RED);
    private static final Text DYE_SLOT_HINT = Text.translatable("container.virtual_additions.coloring_station.dye_slot_hint");
    private static final Text ITEM_SLOT_HINT = Text.translatable("container.virtual_additions.coloring_station.item_slot_hint");
    private static int K = 0, W = 0, R = 0, G = 0, B = 0, Y = 0;
    private static final String INDICATOR_BLACK = "container.virtual_additions.coloring_station.indicator.black";
    private static final String INDICATOR_WHITE = "container.virtual_additions.coloring_station.indicator.white";
    private static final String INDICATOR_RED = "container.virtual_additions.coloring_station.indicator.red";
    private static final String INDICATOR_GREEN = "container.virtual_additions.coloring_station.indicator.green";
    private static final String INDICATOR_BLUE = "container.virtual_additions.coloring_station.indicator.blue";
    private static final String INDICATOR_YELLOW = "container.virtual_additions.coloring_station.indicator.yellow";
    private static final String INDICATOR_ADVANCED_BLACK = "container.virtual_additions.coloring_station.indicator.advanced.black";
    private static final String INDICATOR_ADVANCED_WHITE = "container.virtual_additions.coloring_station.indicator.advanced.white";
    private static final String INDICATOR_ADVANCED_RED = "container.virtual_additions.coloring_station.indicator.advanced.red";
    private static final String INDICATOR_ADVANCED_GREEN = "container.virtual_additions.coloring_station.indicator.advanced.green";
    private static final String INDICATOR_ADVANCED_BLUE = "container.virtual_additions.coloring_station.indicator.advanced.blue";
    private static final String INDICATOR_ADVANCED_YELLOW = "container.virtual_additions.coloring_station.indicator.advanced.yellow";
    private ColoringStationBlockEntity.DyeContents dyeContents;
    private ColoringStationBlockEntity.DyeContents cachedDyeContents;
    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;
    private boolean canCraft;
    private record ColorCache(int amount, int dye, float percent){
        private ColorCache(int amount) {
            this(amount, amount / 32, amount / 8192.0F);
        }
    }
    private ColorCache[] caches = {
            new ColorCache(0),
            new ColorCache(0),
            new ColorCache(0),
            new ColorCache(0),
            new ColorCache(0),
            new ColorCache(0)
    };

    public ColoringStationScreen(ColoringStationScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        handler.setContentsChangedListener(this::onInventoryChange);
        this.dyeContents = handler.getDyeContents();
        this.updateCaches();
        --this.titleY;
    }

    private void updateCaches() {
        int[] dyes = this.dyeContents.asIntArray();
        for (int i = 0; i < 6; i++) {
            this.caches[i] = new ColorCache(dyes[i]);
        }
        this.cachedDyeContents = this.dyeContents.copy();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!this.cachedDyeContents.equals(this.dyeContents)) this.updateCaches();
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = this.x;
        int j = this.y;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);

        this.drawDyeStatusBar(context, 0, this.caches[5].percent, i + 10, j + 35);
        this.drawDyeStatusBar(context, 1, this.caches[4].percent, i + 10, j + 41);
        this.drawDyeStatusBar(context, 2, this.caches[0].percent, i + 10, j + 47);
        this.drawDyeStatusBar(context, 3, this.caches[1].percent, i + 10, j + 53);
        this.drawDyeStatusBar(context, 4, this.caches[2].percent, i + 10, j + 59);
        this.drawDyeStatusBar(context, 5, this.caches[3].percent, i + 10, j + 65);

        Slot slot = this.handler.getSlot(0);
        if (!slot.hasStack()) {
            context.drawGuiTexture(DYE_SLOT_TEXTURE, i + slot.x, j + slot.y, 16, 16);
        }
        int k = (int)(41.0f * this.scrollAmount);
        Identifier identifier = this.shouldScroll() ? SCROLLER_TEXTURE : SCROLLER_DISABLED_TEXTURE;
        context.drawGuiTexture(identifier, i + 119, j + 15 + k, 12, 15);
        int l = this.x + 52;
        int m = this.y + 14;
        int n = this.scrollOffset + 12;

        this.renderRecipeBackground(context, mouseX, mouseY, l, m, n);
        this.renderRecipeIcons(context, l, m, n);
    }

    private void drawDyeStatusBar(DrawContext context, int index, float percent, int x, int y) {
        int v = index * 4;
        int max = (int)Math.ceil(34 * percent);
        context.drawTexture(TEXTURE, x, y, 176, v, max, 4);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
        if (this.client == null) return;
        boolean advanced = this.client.options.advancedItemTooltips;
        if (this.canCraft && this.client.world != null) {
            int i = this.x + 52;
            int j = this.y + 14;
            int k = this.scrollOffset + 12;
            List<RecipeEntry<ColoringStationRecipe>> list = (this.handler).getAvailableRecipes();
            for (int l = this.scrollOffset; l < k && l < (this.handler).getAvailableRecipeCount(); ++l) {
                int m = l - this.scrollOffset;
                int n = i + m % 4 * 16;
                int o = j + m / 4 * 18 + 2;
                if (x < n || x >= n + 16 || y < o || y >= o + 18) continue;
                ItemStack stack = list.get(l).value().getResultStack(this.client.world.getRegistryManager(), this.handler.getSlot(1).getStack());
                ColoringStationBlockEntity.DyeContents cost = list.get(l).value().getDyeCost(true);
                ArrayList<Text> tooltip = new ArrayList<>(Screen.getTooltipFromItem(this.client, stack));
                if (advanced) {
                    int K = cost.getK();
                    int W = cost.getW();
                    int R = cost.getR();
                    int G = cost.getG();
                    int B = cost.getB();
                    int Y = cost.getY();
                    if (K > 0) tooltip.add(Text.of("Black Cost: " + K).copy().formatted(K > this.dyeContents.getK() ? Formatting.RED : Formatting.GRAY));
                    if (W > 0) tooltip.add(Text.of("White Cost: " + W).copy().formatted(W > this.dyeContents.getW() ? Formatting.RED : Formatting.GRAY));
                    if (R > 0) tooltip.add(Text.of("Red Cost: " + R).copy().formatted(R > this.dyeContents.getR() ? Formatting.RED : Formatting.GRAY));
                    if (G > 0) tooltip.add(Text.of("Green Cost: " + G).copy().formatted(G > this.dyeContents.getG() ? Formatting.RED : Formatting.GRAY));
                    if (B > 0) tooltip.add(Text.of("Blue Cost: " + B).copy().formatted(B > this.dyeContents.getB() ? Formatting.RED : Formatting.GRAY));
                    if (Y > 0) tooltip.add(Text.of("Yellow Cost: " + Y).copy().formatted(Y > this.dyeContents.getY() ? Formatting.RED : Formatting.GRAY));
                }
                else if (!this.dyeContents.canAdd(cost.copyAndMultiply(-1))) {
                    tooltip.add(NOT_ENOUGH_DYE_WARNING);
                }
                context.drawTooltip(textRenderer, tooltip, stack.getTooltipData(), x, y);
            }
        }
        int i = this.x;
        int j = this.y;
        if (x < i + 45 && x >= i + 9 && y < j + 40 && y >= j + 34) {
            K = !advanced ? this.caches[5].dye : this.dyeContents.getK();
            context.drawTooltip(textRenderer, Text.translatable(advanced ? INDICATOR_ADVANCED_BLACK : INDICATOR_BLACK, K), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 46 && y >= j + 40) {
            W = !advanced ? this.caches[4].dye : this.dyeContents.getW();
            context.drawTooltip(textRenderer, Text.translatable(advanced ? INDICATOR_ADVANCED_WHITE : INDICATOR_WHITE, W), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 52 && y >= j + 46) {
            R = !advanced ? this.caches[0].dye : this.dyeContents.getR();
            context.drawTooltip(textRenderer, Text.translatable(advanced ? INDICATOR_ADVANCED_RED : INDICATOR_RED, R), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 58 && y >= j + 52) {
            G = !advanced ? this.caches[1].dye : this.dyeContents.getG();
            context.drawTooltip(textRenderer, Text.translatable(advanced ? INDICATOR_ADVANCED_GREEN : INDICATOR_GREEN, G), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 64 && y >= j + 58) {
            B = !advanced ? this.caches[2].dye : this.dyeContents.getB();
            context.drawTooltip(textRenderer, Text.translatable(advanced ? INDICATOR_ADVANCED_BLUE : INDICATOR_BLUE, B), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 70 && y >= j + 64) {
            Y = !advanced ? this.caches[3].dye : this.dyeContents.getY();
            context.drawTooltip(textRenderer, Text.translatable(advanced ? INDICATOR_ADVANCED_YELLOW : INDICATOR_YELLOW, Y), x, y);
        }
        int k = 0;
        boolean bl = this.focusedSlot != null;
        if (bl) k = this.focusedSlot.id;
        if (bl && k == 0 && !this.focusedSlot.hasStack()) {
            context.drawTooltip(textRenderer, DYE_SLOT_HINT, x, y);
        }
        if (bl && k == 1 && !this.focusedSlot.hasStack()) {
            context.drawTooltip(textRenderer, ITEM_SLOT_HINT, x, y);
        }
    }

    private void renderRecipeBackground(DrawContext context, int mouseX, int mouseY, int x, int y, int scrollOffset) {
        for (int i = this.scrollOffset; i < scrollOffset && i < (this.handler).getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            Identifier identifier = this.dyeContents.canAdd(this.handler.getAvailableRecipes().get(i).value().getDyeCost(false)) ? (i == (this.handler).getSelectedRecipe() ? RECIPE_SELECTED_TEXTURE : mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18 ? RECIPE_HIGHLIGHTED_TEXTURE : RECIPE_TEXTURE) : RECIPE_UNCRAFTABLE_TEXTURE;
            context.drawGuiTexture(identifier, k, m - 1, 16, 18);
        }
    }

    private void renderRecipeIcons(DrawContext context, int x, int y, int scrollOffset) {
        List<RecipeEntry<ColoringStationRecipe>> list = (this.handler).getAvailableRecipes();
        for (int i = this.scrollOffset; i < scrollOffset && i < (this.handler).getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            context.drawItem(list.get(i).value().getResultStack(this.client.world.getRegistryManager(), this.handler.getSlot(1).getStack()), k, m);
        }
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.mouseClicked = false;
        if (this.client != null && this.canCraft) {
            int i = this.x + 52;
            int j = this.y + 14;
            int k = this.scrollOffset + 12;
            for (int l = this.scrollOffset; l < k; ++l) {
                int m = l - this.scrollOffset;
                double d = mouseX - (double)(i + m % 4 * 16);
                double e = mouseY - (double)(j + m / 4 * 18);
                if (!(d >= 0.0) || !(e >= 0.0) || !(d < 16.0) || !(e < 18.0) || !this.handler.onButtonClick(this.client.player, l)) continue;
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0f));
                if (this.client.interactionManager != null) this.client.interactionManager.clickButton(this.handler.syncId, l);
                return true;
            }
            i = this.x + 119;
            j = this.y + 9;
            if (mouseX >= (double)i && mouseX < (double)(i + 12) && mouseY >= (double)j && mouseY < (double)(j + 54)) {
                this.mouseClicked = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.mouseClicked && this.shouldScroll()) {
            int i = this.y + 14;
            int j = i + 54;
            this.scrollAmount = ((float)mouseY - (float)i - 7.5f) / ((float)(j - i) - 15.0f);
            this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0f, 1.0f);
            this.scrollOffset = (int)((double)(this.scrollAmount * (float)this.getMaxScroll()) + 0.5) * 4;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (this.shouldScroll()) {
            int i = this.getMaxScroll();
            float f = (float)verticalAmount / (float)i;
            this.scrollAmount = MathHelper.clamp(this.scrollAmount - f, 0.0f, 1.0f);
            this.scrollOffset = (int)((double)(this.scrollAmount * (float)i) + 0.5) * 4;
        }
        return true;
    }

    private boolean shouldScroll() {
        return this.canCraft && (this.handler).getAvailableRecipeCount() > 12;
    }

    protected int getMaxScroll() {
        return ((this.handler).getAvailableRecipeCount() + 4 - 1) / 4 - 3;
    }

    private void onInventoryChange() {
        this.canCraft = this.handler.canCraft();
        if (!this.canCraft) {
            this.scrollAmount = 0.0f;
            this.scrollOffset = 0;
        }
    }

    public void setDyeContent(ColoringStationBlockEntity.DyeContents from) {
        this.dyeContents = from;
    }
}
