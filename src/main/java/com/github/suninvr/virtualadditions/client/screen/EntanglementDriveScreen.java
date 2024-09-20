package com.github.suninvr.virtualadditions.client.screen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.network.EntanglementDriveC2SPayload;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
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
    private float mouseX, mouseY;
    private final UUID playerId;

    public EntanglementDriveScreen(EntanglementDriveScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.playerId = inventory.player.getUuid();
    }

    @Override
    protected void init() {
        super.init();
        ConfirmButtonWidget confirm = new ConfirmButtonWidget(this.x + 141, this.y + 29);
        this.addDrawableChild(confirm);
        this.titleX = 80;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = this.x;
        int j = this.y;
        context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight, 256, 256);
        if (this.handler.isSelectingSlot() && this.handler.isSlotSelected()) {
            Slot slot = this.handler.getSelectedSlot();
            context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, i + slot.x - 1, j + slot.y - 1, 196, 0, 18, 18, 256, 256);
        }
        if (this.handler.isActive() && this.handler.isSamePlayer()) {
            Slot slot = this.handler.getActiveSlot();
            context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, i + slot.x - 1, j + slot.y - 1, 178, 0, 18, 18, 256, 256);
        }
        if (this.client != null && this.client.player != null) {
            InventoryScreen.drawEntity(context, i + 26, j + 8, i + 75, j + 78, 30, 0.0625F, this.mouseX, this.mouseY, this.client.player);
        }
    }

    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 4210752, false);
    }

    @Override
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
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

        public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);

            if (this.isDisabled()) this.setTooltip(null);
            else this.setTooltip(CONFIRM_BUTTON_TOOLTIP);

            int j = 178;
            if (this.isDisabled()) {
                j += this.width * 2;
            } else if (this.isSelected()) {
                j += this.width;
            }

            context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, this.getX(), this.getY(), j, 18, this.width, this.height, 256, 256);
        }

        @Override
        public void onPress() {
            ClientPlayNetworking.send(new EntanglementDriveC2SPayload(EntanglementDriveScreen.this.handler.getSelectedSlotIndex(), EntanglementDriveScreen.this.playerId));
            EntanglementDriveScreen.this.handler.decrementPaymentSlot();
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
