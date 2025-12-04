package com.github.suninvr.virtualadditions.client.toast;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class RemoteNotifierToast implements Toast {
    private static final Identifier TEXTURE = VirtualAdditions.idOf("toast/remote_notifier");
    public static final int DEFAULT_DURATION_MS = 5000;
    private final Component text;
    private final ItemStack stack;
    private int height;
    private Toast.Visibility visibility = Toast.Visibility.HIDE;

    public RemoteNotifierToast(ItemStack displayStack, Component text) {
        this.stack = displayStack;
        this.text = text;
        this.height = 32;
    }

    @Override
    public Visibility getWantedVisibility() {
        return this.visibility;
    }

    @Override
    public void update(ToastManager manager, long time) {
        this.visibility = (double)time >= 5000.0 * manager.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    @Override
    public void render(GuiGraphics context, Font textRenderer, long startTime) {
        context.blitSprite(RenderPipelines.GUI_TEXTURED, TEXTURE, 0, 0, this.width(), this.height());
        if (this.text != null) {
            List<FormattedCharSequence> lines = textRenderer.split(text, this.stack.isEmpty() ? 146 : 125);
            int textX = this.stack.isEmpty() ? 8 : 30;
            if (!this.stack.isEmpty()) {
                context.renderFakeItem(this.stack, 8, 8);
            }
            if (lines.size() > 2) {
                this.height = 32 + (12 * (lines.size() - 2));
            }
            int offset = 0;
            for (FormattedCharSequence line : lines) {
                context.drawString(textRenderer, line, textX, lines.size() > 1 ? 6 + offset : 12, 0xFFFFFF33, true);
                offset += 12;
            }
        }

    }

    @Override
    public int height() {
        return this.height;
    }
}
