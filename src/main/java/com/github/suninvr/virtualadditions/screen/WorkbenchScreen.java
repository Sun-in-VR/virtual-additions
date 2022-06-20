package com.github.suninvr.virtualadditions.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.screenhandler.WorkbenchScreenHandler;

public class WorkbenchScreen extends HandledScreen<WorkbenchScreenHandler> {
    private String damageText;
    private int damageTextColor;
    private static final int damageTextColorNeutral;
    private static final int damageTextColorWillNotBreak;
    private static final int damageTextColorWillBreak;
    private final Identifier TEXTURE = VirtualAdditions.idOf("textures/gui/container/workbench.png");

    public WorkbenchScreen(WorkbenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.titleX = 60;
        this.damageText = "000";
        this.damageTextColor = damageTextColorNeutral;
        handler.addListener(new ScreenHandlerListener() {
            @Override
            public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {}

            @Override
            public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
                if (property == 0) WorkbenchScreen.this.damageText = getDamageText(value);
                if (property == 1) {WorkbenchScreen.this.damageTextColor = switch (value) {
                    case 1 -> damageTextColorWillNotBreak;
                    case 2 -> damageTextColorWillBreak;
                    default -> damageTextColorNeutral;
                };
                }
            }
        });
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);
        this.textRenderer.drawWithShadow(matrices, this.damageText, 17, 24, damageTextColor);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.TEXTURE);
        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    protected String getDamageText(int value) {
        if(value < 10) return "00" + value;
        if(value < 100) return "0" + value;
        return String.valueOf(value);
    }

    static {
        damageTextColorNeutral = MathHelper.packRgb(170, 170, 170);
        damageTextColorWillBreak = MathHelper.packRgb(255, 85, 85);
        damageTextColorWillNotBreak = MathHelper.packRgb(255, 255, 85);
    }
}
