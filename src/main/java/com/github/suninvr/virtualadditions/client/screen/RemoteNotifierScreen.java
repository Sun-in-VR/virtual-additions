package com.github.suninvr.virtualadditions.client.screen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.network.SetRemoteNotifierMessagePayload;
import com.github.suninvr.virtualadditions.screen.RemoteNotifierMenu;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class RemoteNotifierScreen extends AbstractContainerScreen<RemoteNotifierMenu> {
    public static final Identifier INVENTORY_LOCATION = VirtualAdditions.idOf("textures/gui/container/remote_notifier.png");
    public static String initializerText = "";
    private EditBox message;
    private boolean sendNameChangePackets = false;

    public RemoteNotifierScreen(RemoteNotifierMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.message = new EditBox(this.font, i + 42, j + 30, 103, 12, Component.translatable("container.repair"));
        this.message.setCanLoseFocus(false);
        this.message.setBordered(false);
        this.message.setMaxLength(255);
        this.message.setResponder(this::onNameChanged);
        this.message.setWidth(110);
        this.addRenderableWidget(this.message);
        this.initText(initializerText);
    }

    public void initText(String text) {
        this.message.setEditable(true);
        this.message.setValue(text);
        this.sendNameChangePackets = true;
    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isEscape()) {
            this.minecraft.player.closeContainer();
            return true;
        } else {
            return !this.message.keyPressed(keyEvent) && !this.message.canConsumeInput() ? super.keyPressed(keyEvent) : true;
        }
    }

    private void onNameChanged(String string) {
        if (this.sendNameChangePackets && this.menu != null) {
            ClientPlayNetworking.send(new SetRemoteNotifierMessagePayload(this.message.getValue()));
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        this.renderTooltip(guiGraphics, i, j);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, INVENTORY_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
    }
}
