package com.github.suninvr.virtualadditions.client.toast;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class RemoteNotifierToast implements Toast {
    private static final Identifier TEXTURE = VirtualAdditions.idOf("toast/remote_notifier");
    public static final int DEFAULT_DURATION_MS = 5000;
    private final Text text;
    private final ItemStack stack;
    private int height;
    private Toast.Visibility visibility = Toast.Visibility.HIDE;

    public RemoteNotifierToast(ItemStack displayStack, Text text) {
        this.stack = displayStack;
        this.text = text;
        this.height = 32;
    }

    @Override
    public Visibility getVisibility() {
        return this.visibility;
    }

    @Override
    public void update(ToastManager manager, long time) {
        this.visibility = (double)time >= 5000.0 * manager.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    @Override
    public void draw(DrawContext context, TextRenderer textRenderer, long startTime) {
        context.drawGuiTexture(RenderLayer::getGuiTextured, TEXTURE, 0, 0, this.getWidth(), this.getHeight());
        if (this.text != null) {
            List<OrderedText> lines = textRenderer.wrapLines(text, this.stack.isEmpty() ? 146 : 125);
            int textX = this.stack.isEmpty() ? 8 : 30;
            if (!this.stack.isEmpty()) {
                context.drawItemWithoutEntity(this.stack, 8, 8);
            }
            if (lines.size() > 2) {
                this.height = 32 + (12 * (lines.size() - 2));
            }
            int offset = 0;
            for (OrderedText line : lines) {
                context.drawText(textRenderer, line, textX, lines.size() > 1 ? 6 + offset : 12, 0xFFFF33, true);
                offset += 12;
            }
        }

    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
