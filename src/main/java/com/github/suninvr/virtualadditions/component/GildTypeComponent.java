package com.github.suninvr.virtualadditions.component;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentType;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public record GildTypeComponent(RegistryEntry<GildType> type, Text descriptionText) implements TooltipAppender {
    private static final Text descriptionHeader = Text.translatable("item.minecraft.smithing_template.upgrade").formatted(Formatting.GRAY);
    public static final Codec<GildTypeComponent> CODEC;
    public static final PacketCodec<RegistryByteBuf, GildTypeComponent> PACKET_CODEC;

    public GildTypeComponent(RegistryEntry<GildType> type) {
        this(type, ScreenTexts.space().append(type.value().getTranslationKey()).withColor(type.value().getColor()));
    }

    public static ComponentType.Builder<GildTypeComponent> setCodecs(ComponentType.Builder<GildTypeComponent> builder) {
        return builder.codec(CODEC).packetCodec(PACKET_CODEC);
    }

    static {
        CODEC = RecordCodecBuilder.create(instance -> instance.group(
                GildType.ENTRY_CODEC.fieldOf("type").forGetter(GildTypeComponent::type)
        ).apply(instance, GildTypeComponent::new));
        PACKET_CODEC = PacketCodec.tuple(
                GildType.ENTRY_PACKET_CODEC, component -> component.type, GildTypeComponent::new
        );
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(descriptionHeader);
        textConsumer.accept(this.descriptionText);
    }
}
