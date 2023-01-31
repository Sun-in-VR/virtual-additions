package com.github.suninvr.virtualadditions.client.screen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.UUID;

public class EntanglementDriveScreen extends HandledScreen<EntanglementDriveScreenHandler> {
    public static final Identifier BACKGROUND_TEXTURE = VirtualAdditions.idOf("textures/gui/container/inventory.png");
    private float mouseX;
    private float mouseY;
    private boolean samePlayer;
    private final UUID playerId;
    int selectedSlotX;
    int selectedSlotY;

    public EntanglementDriveScreen(EntanglementDriveScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.playerId = inventory.player.getUuid();
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = 80;
        Slot slot = this.handler.getSlot();
        this.setSlotPos(slot, slot.getIndex());
        this.samePlayer = this.playerId.equals(this.handler.getPlayerId());
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = this.x;
        int j = this.y;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (samePlayer) this.drawTexture(matrices, i + selectedSlotX, j + selectedSlotY, 178, 0, 18, 18);
        InventoryScreen.drawEntity(i + 51, j + 75, 30, (float)(i + 51) - this.mouseX, (float)(j + 75 - 50) - this.mouseY, this.client.player);
    }

    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
    }

    @Override
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
        setSlotPos(slot, slotId);
    }

    protected void setSlotPos(Slot slot, int slotId) {
        if (MathHelper.clamp(slotId, 0, 40) == slotId) {
            selectedSlotX = slot.x - 1;
            selectedSlotY = slot.y - 1;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
        this.mouseX = (float)mouseX;
        this.mouseY = (float)mouseY;
    }

    public void updateParams() {
        this.samePlayer = this.playerId.equals(this.handler.getPlayerId());
    }
}
