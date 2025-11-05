package com.github.suninvr.virtualadditions.client.screen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.network.EntanglementDriveC2SPayload;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

import java.util.UUID;

public class EntanglementDriveScreen extends AbstractContainerScreen<EntanglementDriveScreenHandler> {
    public static final ResourceLocation BACKGROUND_TEXTURE = VirtualAdditions.idOf("textures/gui/container/entanglement_drive.png");
    private static final Component SLOT_HINT = Component.translatable("container.virtual_additions.entanglement_drive.select_slot_hint");
    private static final Component PAYMENT_SLOT_HINT = Component.translatable("container.virtual_additions.entanglement_drive.payment_slot_hint");
    private float mouseX, mouseY;
    private final UUID playerId;

    public EntanglementDriveScreen(EntanglementDriveScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.playerId = inventory.player.getUUID();
    }

    @Override
    protected void init() {
        super.init();
        ConfirmButtonWidget confirm = new ConfirmButtonWidget(this.leftPos + 141, this.topPos + 29);
        this.addRenderableWidget(confirm);
        this.titleLabelX = 80;
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        //RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = this.leftPos;
        int j = this.topPos;
        context.blit(RenderPipelines.GUI_TEXTURED, INVENTORY_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        if (this.menu.isSelectingSlot() && this.menu.isSlotSelected()) {
            Slot slot = this.menu.getSelectedSlot();
            context.blit(RenderPipelines.GUI_TEXTURED, INVENTORY_LOCATION, i + slot.x - 1, j + slot.y - 1, 196, 0, 18, 18, 256, 256);
        }
        if (this.menu.isActive() && this.menu.isSamePlayer()) {
            Slot slot = this.menu.getActiveSlot();
            context.blit(RenderPipelines.GUI_TEXTURED, INVENTORY_LOCATION, i + slot.x - 1, j + slot.y - 1, 178, 0, 18, 18, 256, 256);
        }
        if (this.minecraft != null && this.minecraft.player != null) {
            InventoryScreen.renderEntityInInventoryFollowsMouse(context, i + 26, j + 8, i + 75, j + 78, 30, 0.0625F, this.mouseX, this.mouseY, this.minecraft.player);
        }
    }

    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int button, ClickType actionType) {
        super.slotClicked(slot, slotId, button, actionType);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.renderTooltip(context, mouseX, mouseY);
        this.mouseX = (float)mouseX;
        this.mouseY = (float)mouseY;
    }

    @Override
    protected void renderTooltip(GuiGraphics context, int x, int y) {
        int i = 0;
        boolean bl = this.hoveredSlot != null;
        if (bl) i = this.hoveredSlot.index;

        if ( bl && i == 41 && !this.menu.isSelectingSlot() ) {
            context.setTooltipForNextFrame(this.font, PAYMENT_SLOT_HINT, x, y);
        } else if ( bl && i != 41 && this.menu.getCarried().isEmpty() && this.menu.isSelectingSlot() ) {
            context.setTooltipForNextFrame(this.font, SLOT_HINT, x, y);
        } else {
            super.renderTooltip(context, x, y);
        }
    }

    private class ConfirmButtonWidget extends AbstractButton {
        private static final Tooltip CONFIRM_BUTTON_TOOLTIP = Tooltip.create(Component.translatable("container.virtual_additions.entanglement_drive.confirm_button"));


        protected ConfirmButtonWidget(int x, int y) {
            super(x, y, 18, 18, Component.empty());
        }

        @Override
        public void onPress(InputWithModifiers input) {
            ClientPlayNetworking.send(new EntanglementDriveC2SPayload(EntanglementDriveScreen.this.menu.getSelectedSlotIndex(), EntanglementDriveScreen.this.playerId));
            EntanglementDriveScreen.this.menu.decrementPaymentSlot();
        }

        public void renderContents(GuiGraphics context, int mouseX, int mouseY, float delta) {
            //RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);

            if (this.isDisabled()) this.setTooltip(null);
            else this.setTooltip(CONFIRM_BUTTON_TOOLTIP);

            int j = 178;
            if (this.isDisabled()) {
                j += this.width * 2;
            } else if (this.isHoveredOrFocused()) {
                j += this.width;
            }

            context.blit(RenderPipelines.GUI_TEXTURED, INVENTORY_LOCATION, this.getX(), this.getY(), j, 18, this.width, this.height, 256, 256);
        }

        @Override
        protected boolean isValidClickButton(MouseButtonInfo input) {
            return super.isValidClickButton(input) && !this.isDisabled() ;
        }

        public boolean isDisabled() {
            return !(EntanglementDriveScreen.this.menu.isSelectingSlot() && EntanglementDriveScreen.this.menu.isSlotSelected());
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput builder) {

        }
    }
}
