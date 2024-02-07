package com.github.suninvr.virtualadditions.client.screen;

import com.github.suninvr.virtualadditions.block.entity.ColoringStationBlockEntity;
import com.github.suninvr.virtualadditions.recipe.ColoringRecipe;
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
    private static final Identifier SCROLLER_TEXTURE = new Identifier("container/stonecutter/scroller");
    private static final Identifier SCROLLER_DISABLED_TEXTURE = new Identifier("container/stonecutter/scroller_disabled");
    private static final Identifier RECIPE_SELECTED_TEXTURE = new Identifier("container/stonecutter/recipe_selected");
    private static final Identifier RECIPE_HIGHLIGHTED_TEXTURE = new Identifier("container/stonecutter/recipe_highlighted");
    private static final Identifier RECIPE_TEXTURE = new Identifier("container/stonecutter/recipe");
    private static final Identifier DYE_SLOT_TEXTURE = new Identifier("container/loom/dye_slot");
    private static final Identifier TEXTURE = idOf("textures/gui/container/coloring_station.png");
    private ColoringStationBlockEntity.DyeContents dyeContents;
    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;
    private boolean canCraft;

    public ColoringStationScreen(ColoringStationScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        handler.setContentsChangedListener(this::onInventoryChange);
        this.dyeContents = handler.getDyeContents();
        --this.titleY;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = this.x;
        int j = this.y;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);

        this.drawDyeStatusBar(context, 0, this.dyeContents.getK() / 1024.0F, i + 10, j + 35);
        this.drawDyeStatusBar(context, 1, this.dyeContents.getW() / 1024.0F, i + 10, j + 41);
        this.drawDyeStatusBar(context, 2, this.dyeContents.getR() / 1024.0F, i + 10, j + 47);
        this.drawDyeStatusBar(context, 3, this.dyeContents.getG() / 1024.0F, i + 10, j + 53);
        this.drawDyeStatusBar(context, 4, this.dyeContents.getB() / 1024.0F, i + 10, j + 59);
        this.drawDyeStatusBar(context, 5, this.dyeContents.getY() / 1024.0F, i + 10, j + 65);

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
        int max = Math.round(34 * percent);
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
            List<RecipeEntry<ColoringRecipe>> list = (this.handler).getAvailableRecipes();
            for (int l = this.scrollOffset; l < k && l < (this.handler).getAvailableRecipeCount(); ++l) {
                int m = l - this.scrollOffset;
                int n = i + m % 4 * 16;
                int o = j + m / 4 * 18 + 2;
                if (x < n || x >= n + 16 || y < o || y >= o + 18) continue;
                ItemStack stack = list.get(l).value().getResult(this.client.world.getRegistryManager());
                ColoringStationBlockEntity.DyeContents cost = list.get(l).value().getDyeCost(true);
                ArrayList<Text> tooltip = new ArrayList<>(Screen.getTooltipFromItem(this.client, stack));
                if (advanced) {
                    int K = cost.getK();
                    int W = cost.getW();
                    int R = cost.getR();
                    int G = cost.getG();
                    int B = cost.getB();
                    int Y = cost.getY();
                    if (K > 0) tooltip.add(Text.of("Black Cost: " + K / 16.0F).copy().formatted(K > this.dyeContents.getK() ? Formatting.RED : Formatting.GRAY));
                    if (W > 0) tooltip.add(Text.of("White Cost: " + W / 16.0F).copy().formatted(W > this.dyeContents.getW() ? Formatting.RED : Formatting.GRAY));
                    if (R > 0) tooltip.add(Text.of("Red Cost: " + R / 16.0F).copy().formatted(R > this.dyeContents.getR() ? Formatting.RED : Formatting.GRAY));
                    if (G > 0) tooltip.add(Text.of("Green Cost: " + G / 16.0F).copy().formatted(G > this.dyeContents.getG() ? Formatting.RED : Formatting.GRAY));
                    if (B > 0) tooltip.add(Text.of("Blue Cost: " + B / 16.0F).copy().formatted(B > this.dyeContents.getB() ? Formatting.RED : Formatting.GRAY));
                    if (Y > 0) tooltip.add(Text.of("Yellow Cost: " + Y / 16.0F).copy().formatted(Y > this.dyeContents.getY() ? Formatting.RED : Formatting.GRAY));
                }
                context.drawTooltip(textRenderer, tooltip, stack.getTooltipData(), x, y);
            }
        }
        int i = this.x;
        int j = this.y;
        if (x < i + 45 && x >= i + 9 && y < j + 40 && y >= j + 34) {
            context.drawTooltip(textRenderer, Text.of("Black Dye: " + (advanced ? this.dyeContents.getK() / 16.0F : Math.round(this.dyeContents.getK() / 16.0F)) + " / 64"), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 46 && y >= j + 40) {
            context.drawTooltip(textRenderer, Text.of("White Dye: " + (advanced ? this.dyeContents.getW() / 16.0F : Math.round(this.dyeContents.getW() / 16.0F)) + " / 64"), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 52 && y >= j + 46) {
            context.drawTooltip(textRenderer, Text.of("Red Dye: " + (advanced ? this.dyeContents.getR() / 16.0F : Math.round(this.dyeContents.getR() / 16.0F)) + " / 64"), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 58 && y >= j + 52) {
            context.drawTooltip(textRenderer, Text.of("Green Dye: " + (advanced ? this.dyeContents.getG() / 16.0F : Math.round(this.dyeContents.getG() / 16.0F)) + " / 64"), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 64 && y >= j + 58) {
            context.drawTooltip(textRenderer, Text.of("Blue Dye: " + (advanced ? this.dyeContents.getB() / 16.0F : Math.round(this.dyeContents.getB() / 16.0F)) + " / 64"), x, y);
        }
        if (x < i + 45 && x >= i + 9 && y < j + 70 && y >= j + 64) {
            context.drawTooltip(textRenderer, Text.of("Yellow Dye: " + (advanced ? this.dyeContents.getY() / 16.0F : Math.round(this.dyeContents.getY() / 16.0F)) + " / 64"), x, y);
        }
    }

    private void renderRecipeBackground(DrawContext context, int mouseX, int mouseY, int x, int y, int scrollOffset) {
        for (int i = this.scrollOffset; i < scrollOffset && i < (this.handler).getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            Identifier identifier = i == (this.handler).getSelectedRecipe() ? RECIPE_SELECTED_TEXTURE : (mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18 ? RECIPE_HIGHLIGHTED_TEXTURE : RECIPE_TEXTURE);
            context.drawGuiTexture(identifier, k, m - 1, 16, 18);
        }
    }

    private void renderRecipeIcons(DrawContext context, int x, int y, int scrollOffset) {
        List<RecipeEntry<ColoringRecipe>> list = (this.handler).getAvailableRecipes();
        for (int i = this.scrollOffset; i < scrollOffset && i < (this.handler).getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            context.drawItem(list.get(i).value().getResult(this.client.world.getRegistryManager()), k, m);
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
