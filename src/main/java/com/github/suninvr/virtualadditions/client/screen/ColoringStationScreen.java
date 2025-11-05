package com.github.suninvr.virtualadditions.client.screen;

import com.github.suninvr.virtualadditions.block.entity.DyeContents;
import com.github.suninvr.virtualadditions.screen.ColoringStationScreenHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class ColoringStationScreen extends AbstractContainerScreen<ColoringStationScreenHandler> {
    private static final ResourceLocation SCROLLER_TEXTURE = ResourceLocation.parse("container/stonecutter/scroller");
    private static final ResourceLocation SCROLLER_DISABLED_TEXTURE = ResourceLocation.parse("container/stonecutter/scroller_disabled");
    private static final ResourceLocation RECIPE_SELECTED_TEXTURE = ResourceLocation.parse("container/stonecutter/recipe_selected");
    private static final ResourceLocation RECIPE_HIGHLIGHTED_TEXTURE = ResourceLocation.parse("container/stonecutter/recipe_highlighted");
    private static final ResourceLocation RECIPE_UNCRAFTABLE_TEXTURE = idOf("container/coloring_station/recipe_uncraftable");
    private static final ResourceLocation RECIPE_TEXTURE = ResourceLocation.parse("container/stonecutter/recipe");
    private static final ResourceLocation DYE_SLOT_TEXTURE = ResourceLocation.parse("container/slot/dye");
    private static final ResourceLocation TEXTURE = idOf("textures/gui/container/coloring_station.png");
    private static final Component NOT_ENOUGH_DYE_WARNING = Component.translatable("container.virtual_additions.coloring_station.not_enough_dye_warning").withStyle(ChatFormatting.RED);
    private static final Component CANT_CRAFT_SAME_ITEM_WARNING = Component.translatable("container.virtual_additions.coloring_station.cant_craft_same_item_warning").withStyle(ChatFormatting.RED);
    private static final Component DYE_SLOT_HINT = Component.translatable("container.virtual_additions.coloring_station.dye_slot_hint");
    private static final Component ITEM_SLOT_HINT = Component.translatable("container.virtual_additions.coloring_station.item_slot_hint");
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
    private DyeContents dyeContents;
    private DyeContents cachedDyeContents;
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

    public ColoringStationScreen(ColoringStationScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        handler.setContentsChangedListener(this::onInventoryChange);
        this.dyeContents = handler.getDyeContents();
    }

    @Override
    protected void init() {
        super.init();
        this.updateCaches();
        --this.titleLabelY;
    }

    private void updateCaches() {
        int[] dyes = this.dyeContents.asIntArray();
        for (int i = 0; i < 6; i++) {
            this.caches[i] = new ColorCache(dyes[i]);
        }
        this.cachedDyeContents = this.dyeContents.copy();
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        if (!this.cachedDyeContents.equals(this.dyeContents)) this.updateCaches();
        super.render(context, mouseX, mouseY, delta);
        this.renderTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        this.drawDyeStatusBar(context, 0, this.caches[4].percent, i + 10, j + 35);
        this.drawDyeStatusBar(context, 1, this.caches[5].percent, i + 10, j + 41);
        this.drawDyeStatusBar(context, 2, this.caches[0].percent, i + 10, j + 47);
        this.drawDyeStatusBar(context, 3, this.caches[1].percent, i + 10, j + 53);
        this.drawDyeStatusBar(context, 4, this.caches[2].percent, i + 10, j + 59);
        this.drawDyeStatusBar(context, 5, this.caches[3].percent, i + 10, j + 65);

        Slot slot = this.menu.getSlot(0);
        if (!slot.hasItem()) {
            context.blitSprite(RenderPipelines.GUI_TEXTURED, DYE_SLOT_TEXTURE, i + slot.x, j + slot.y, 16, 16);
        }
        int k = (int)(41.0f * this.scrollAmount);
        ResourceLocation identifier = this.shouldScroll() ? SCROLLER_TEXTURE : SCROLLER_DISABLED_TEXTURE;
        context.blitSprite(RenderPipelines.GUI_TEXTURED, identifier, i + 119, j + 15 + k, 12, 15);
        int l = this.leftPos + 52;
        int m = this.topPos + 14;
        int n = this.scrollOffset + 12;

        this.renderRecipeBackground(context, mouseX, mouseY, l, m, n);
        this.renderRecipeIcons(context, l, m, n);
    }

    private void drawDyeStatusBar(GuiGraphics context, int index, float percent, int x, int y) {
        int v = index * 4;
        int max = (int)Math.ceil(34 * percent);
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 176, v, max, 4, 256, 256);
    }

    @Override
    protected void renderTooltip(GuiGraphics context, int x, int y) {
        super.renderTooltip(context, x, y);
        if (this.minecraft == null) return;
        boolean advanced = this.minecraft.options.advancedItemTooltips;
        if (this.menu.canCraft() && this.minecraft.level != null) {
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.scrollOffset + 12;
            for (int l = this.scrollOffset; l < k && l < this.menu.getAvailableRecipeCount(); ++l) {
                if (this.menu.getRecipeData(l) == null) continue;
                ColoringStationScreenHandler.ColoringRecipeData data = this.menu.getRecipeData(l);
                int m = l - this.scrollOffset;
                int n = i + m % 4 * 16;
                int o = j + m / 4 * 18 + 2;
                if (x < n || x >= n + 16 || y < o || y >= o + 18) continue;
                ItemStack stack = data.stack();
                ArrayList<Component> tooltip = new ArrayList<>(Screen.getTooltipFromItem(this.minecraft, stack));
                if (!data.isInputValid(this.menu.input.getItem(1))) {
                    tooltip.add(CANT_CRAFT_SAME_ITEM_WARNING);
                } else if (!data.isDyeContentSufficient(this.dyeContents)) {
                    tooltip.add(NOT_ENOUGH_DYE_WARNING);
                }
                if (advanced) {
                    DyeContents cost = data.dyeCost().copyAndMultiply(-1);
                    int K = cost.getK();
                    int W = cost.getW();
                    int R = cost.getR();
                    int G = cost.getG();
                    int B = cost.getB();
                    int Y = cost.getY();
                    if (K > 0) tooltip.add(Component.nullToEmpty("Black Cost: " + K).copy().withStyle(K > this.dyeContents.getK() ? ChatFormatting.RED : ChatFormatting.GRAY));
                    if (W > 0) tooltip.add(Component.nullToEmpty("White Cost: " + W).copy().withStyle(W > this.dyeContents.getW() ? ChatFormatting.RED : ChatFormatting.GRAY));
                    if (R > 0) tooltip.add(Component.nullToEmpty("Red Cost: " + R).copy().withStyle(R > this.dyeContents.getR() ? ChatFormatting.RED : ChatFormatting.GRAY));
                    if (G > 0) tooltip.add(Component.nullToEmpty("Green Cost: " + G).copy().withStyle(G > this.dyeContents.getG() ? ChatFormatting.RED : ChatFormatting.GRAY));
                    if (B > 0) tooltip.add(Component.nullToEmpty("Blue Cost: " + B).copy().withStyle(B > this.dyeContents.getB() ? ChatFormatting.RED : ChatFormatting.GRAY));
                    if (Y > 0) tooltip.add(Component.nullToEmpty("Yellow Cost: " + Y).copy().withStyle(Y > this.dyeContents.getY() ? ChatFormatting.RED : ChatFormatting.GRAY));
                }
                context.setTooltipForNextFrame(font, tooltip, stack.getTooltipImage(), x, y);
            }
        }
        int i = this.leftPos;
        int j = this.topPos;
        if (x < i + 45 && x >= i + 9 && y < j + 40 && y >= j + 34) {
            K = !advanced ? this.caches[4].dye : this.dyeContents.getK();
            context.setTooltipForNextFrame(font, Component.translatable(advanced ? INDICATOR_ADVANCED_BLACK : INDICATOR_BLACK, K), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 46 && y >= j + 40) {
            W = !advanced ? this.caches[5].dye : this.dyeContents.getW();
            context.setTooltipForNextFrame(font, Component.translatable(advanced ? INDICATOR_ADVANCED_WHITE : INDICATOR_WHITE, W), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 52 && y >= j + 46) {
            R = !advanced ? this.caches[0].dye : this.dyeContents.getR();
            context.setTooltipForNextFrame(font, Component.translatable(advanced ? INDICATOR_ADVANCED_RED : INDICATOR_RED, R), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 58 && y >= j + 52) {
            G = !advanced ? this.caches[1].dye : this.dyeContents.getG();
            context.setTooltipForNextFrame(font, Component.translatable(advanced ? INDICATOR_ADVANCED_GREEN : INDICATOR_GREEN, G), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 64 && y >= j + 58) {
            B = !advanced ? this.caches[2].dye : this.dyeContents.getB();
            context.setTooltipForNextFrame(font, Component.translatable(advanced ? INDICATOR_ADVANCED_BLUE : INDICATOR_BLUE, B), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 70 && y >= j + 64) {
            Y = !advanced ? this.caches[3].dye : this.dyeContents.getY();
            context.setTooltipForNextFrame(font, Component.translatable(advanced ? INDICATOR_ADVANCED_YELLOW : INDICATOR_YELLOW, Y), x, y);
        }
        int k = 0;
        boolean bl = this.hoveredSlot != null;
        if (bl) k = this.hoveredSlot.index;
        if (bl && k == 0 && !this.hoveredSlot.hasItem()) {
            context.setTooltipForNextFrame(font, DYE_SLOT_HINT, x, y);
        }
        if (bl && k == 1 && !this.hoveredSlot.hasItem()) {
            context.setTooltipForNextFrame(font, ITEM_SLOT_HINT, x, y);
        }
    }

    private void renderRecipeBackground(GuiGraphics context, int mouseX, int mouseY, int x, int y, int scrollOffset) {
        for (int i = this.scrollOffset; i < scrollOffset && i < (this.menu).getAvailableRecipeCount(); ++i) {
            if (this.menu.getRecipeData(i) == null) continue;
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            ColoringStationScreenHandler.ColoringRecipeData data = this.menu.getRecipeData(i);
            ItemStack stack = this.menu.input.getItem(1);
            ResourceLocation identifier = data.isDyeContentSufficient(this.dyeContents) && data.isInputValid(stack) ? (i == (this.menu).getSelectedRecipe() ? RECIPE_SELECTED_TEXTURE : mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18 ? RECIPE_HIGHLIGHTED_TEXTURE : RECIPE_TEXTURE) : RECIPE_UNCRAFTABLE_TEXTURE;
            context.blitSprite(RenderPipelines.GUI_TEXTURED, identifier, k, m - 1, 16, 18);
        }
    }

    private void renderRecipeIcons(GuiGraphics context, int x, int y, int scrollOffset) {
        for (int i = this.scrollOffset; i < scrollOffset && i < (this.menu).getAvailableRecipeCount(); ++i) {
            if (this.menu.getRecipeData(i) == null) continue;
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            ColoringStationScreenHandler.ColoringRecipeData data = this.menu.getRecipeData(i);
            context.renderItem(data.stack(), k, m);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        this.mouseClicked = false;
        if (this.minecraft != null && this.menu.canCraft()) {
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.scrollOffset + 12;
            for (int l = this.scrollOffset; l < k; ++l) {
                int m = l - this.scrollOffset;
                double d = click.x() - (double)(i + m % 4 * 16);
                double e = click.y() - (double)(j + m / 4 * 18);
                if (!(d >= 0.0) || !(e >= 0.0) || !(d < 16.0) || !(e < 18.0) || !this.menu.clickMenuButton(this.minecraft.player, l)) continue;
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0f));
                if (this.minecraft.gameMode != null) this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, l);
                return true;
            }
            i = this.leftPos + 119;
            j = this.topPos + 9;
            if (click.x() >= (double)i && click.x() < (double)(i + 12) && click.y() >= (double)j && click.y() < (double)(j + 54)) {
                this.mouseClicked = true;
            }
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent click, double offsetX, double offsetY) {
        if (this.mouseClicked && this.shouldScroll()) {
            int i = this.topPos + 14;
            int j = i + 54;
            this.scrollAmount = (float) ((click.y() - (float)i - 7.5f) / ((float)(j - i) - 15.0f));
            this.scrollAmount = Mth.clamp(this.scrollAmount, 0.0f, 1.0f);
            this.scrollOffset = (int)((double)(this.scrollAmount * (float)this.getMaxScroll()) + 0.5) * 4;
            return true;
        }
        return super.mouseDragged(click, offsetX, offsetY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
            return true;
        } else {
            if (this.shouldScroll()) {
                int i = this.getMaxScroll();
                float f = (float)verticalAmount / (float)i;
                this.scrollAmount = Mth.clamp(this.scrollAmount - f, 0.0F, 1.0F);
                this.scrollOffset = (int)((double)(this.scrollAmount * (float)i) + 0.5) * 4;
            }

            return true;
        }
    }

    private boolean shouldScroll() {
        return this.menu.canCraft() && (this.menu).getAvailableRecipeCount() > 12;
    }

    protected int getMaxScroll() {
        return ((this.menu).getAvailableRecipeCount() + 4 - 1) / 4 - 3;
    }

    private void onInventoryChange() {
        this.canCraft = this.menu.canCraft();
        if (!this.canCraft) {
            this.scrollAmount = 0.0f;
            this.scrollOffset = 0;
        }
    }

}
