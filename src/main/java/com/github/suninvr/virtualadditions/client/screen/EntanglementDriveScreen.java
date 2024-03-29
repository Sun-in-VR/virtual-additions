package com.github.suninvr.virtualadditions.client.screen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VAPackets;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class EntanglementDriveScreen extends HandledScreen<EntanglementDriveScreenHandler> {
    public static final Identifier BACKGROUND_TEXTURE = VirtualAdditions.idOf("textures/gui/container/entanglement_drive.png");
    private static final Text SLOT_HINT = Text.translatable("container.virtual_additions.entanglement_drive.select_slot_hint");
    private static final Text PAYMENT_SLOT_HINT = Text.translatable("container.virtual_additions.entanglement_drive.payment_slot_hint");
    private float mouseX;
    private float mouseY;
    private boolean samePlayer;
    private final UUID playerId;
    int activeSlotX;
    int activeSlotY;
    int selectedSlotX;
    int selectedSlotY;

    public EntanglementDriveScreen(EntanglementDriveScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        PlayerEntity player = inventory.player;
        this.playerId = player.getUuid();
    }

    @Override
    protected void init() {
        super.init();
        ConfirmButtonWidget confirm = new ConfirmButtonWidget(this.x + 141, this.y + 29);
        this.addDrawableChild(confirm);

        this.titleX = 80;
        this.updateActiveSlot();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = this.x;
        int j = this.y;
        context.drawTexture(BACKGROUND_TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (this.handler.isSelectingSlot() && this.handler.isSlotSelected()) context.drawTexture(BACKGROUND_TEXTURE, i + selectedSlotX - 1, j + selectedSlotY - 1, 196, 0, 18, 18);
        if (samePlayer) context.drawTexture(BACKGROUND_TEXTURE, i + activeSlotX, j + activeSlotY, 178, 0, 18, 18);
        if (this.client != null && this.client.player != null) {
            InventoryScreen.drawEntity(context, i + 51, j + 75, 30, (float)(i + 51) - this.mouseX, (float)(j + 75 - 50) - this.mouseY, this.client.player);
        }
    }

    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 4210752, false);
    }

    @Override
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
    }

    protected void setActiveSlotPos(Slot slot) {
        this.activeSlotX = slot.x - 1;
        this.activeSlotY = slot.y - 1;
    }

    public void setSelectingSlotPos(int x, int y) {
        this.selectedSlotX = x;
        this.selectedSlotY = y;
    }

    public void updateActiveSlot() {
        this.samePlayer = this.playerId.equals(this.handler.getPlayerId());
        Slot slot = this.handler.getActiveSlot();
        this.setActiveSlotPos(slot);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
        this.mouseX = (float)mouseX;
        this.mouseY = (float)mouseY;
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {

        int i = 0;
        boolean bl = this.focusedSlot != null;
        if (bl) i = this.focusedSlot.id;

        if ( bl && i == 41 && !this.handler.isSelectingSlot() ) {
            context.drawTooltip(this.textRenderer, PAYMENT_SLOT_HINT, x, y);
        } else if ( bl && i != 41 && this.handler.getCursorStack().isEmpty() && this.handler.isSelectingSlot() ) {
            context.drawTooltip(this.textRenderer, SLOT_HINT, x, y);
        } else {
            super.drawMouseoverTooltip(context, x, y);
        }
    }

    private class ConfirmButtonWidget extends PressableWidget {
        private static final Tooltip CONFIRM_BUTTON_TOOLTIP = Tooltip.of(Text.translatable("container.virtual_additions.entanglement_drive.confirm_button"));


        protected ConfirmButtonWidget(int x, int y) {
            super(x, y, 18, 18, Text.empty());
        }

        public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);

            if (this.isDisabled()) this.setTooltip(null);
            else this.setTooltip(CONFIRM_BUTTON_TOOLTIP);

            int j = 178;
            if (this.isDisabled()) {
                j += this.width * 2;
            } else if (this.isSelected()) {
                j += this.width;
            }

            context.drawTexture(BACKGROUND_TEXTURE, this.getX(), this.getY(), j, 18, this.width, this.height);
        }

        @Override
        public void onPress() {
            if (!this.isDisabled()) ClientPlayNetworking.send(VAPackets.ENTANGLEMENT_DRIVE_SET_ACTIVE_SLOT_ID, PacketByteBufs.empty());
        }

        @Override
        protected boolean isValidClickButton(int button) {
            return super.isValidClickButton(button) && !this.isDisabled() ;
        }

        public boolean isDisabled() {
            return !(EntanglementDriveScreen.this.handler.isSelectingSlot() && EntanglementDriveScreen.this.handler.isSlotSelected());
        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {

        }
    }
}
