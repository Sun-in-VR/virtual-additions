package com.github.suninvr.virtualadditions.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.TooltipAppender;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.dynamic.Codecs;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record EffectsOnHitComponent(Optional<PotionContentsComponent> potionContents, Optional<Integer> uses, Optional<Integer> remaining) implements TooltipAppender {
    public static final EffectsOnHitComponent DEFAULT = new EffectsOnHitComponent(Optional.empty(), Optional.empty(), Optional.empty());
    public static final Codec<EffectsOnHitComponent> CODEC;
    public static final PacketCodec<RegistryByteBuf, EffectsOnHitComponent> PACKET_CODEC;
    private static final Text tooltipText = Text.translatable("item.virtual_additions.applied_effect_tooltip").formatted(Formatting.DARK_PURPLE);

    public EffectsOnHitComponent(RegistryEntry<Potion> potion) {
        this(Optional.of(new PotionContentsComponent(potion)), Optional.of(30), Optional.of(0));
    }

    public EffectsOnHitComponent(PotionContentsComponent component, int uses, int remaining) {
        this(Optional.of(component), Optional.of(uses), Optional.of(remaining));
    }

    public int getRemainingUses() {
        return this.remaining.orElse(0);
    }

    public int getTotalUses() {
        return this.uses.orElse(0);
    }

    public int getItemBarAmount() {
        int remaining = this.getRemainingUses();
        int total = this.getTotalUses();
        return total == 0 ? 0 : remaining * 13 / total;
    }

    public int getColor() {
        return this.potionContents.map(PotionContentsComponent::getColor).orElse(16253176);
    }

    public boolean hasEffectsComponent() {
        return this.potionContents.isPresent();
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if (this.hasEffectsComponent()) {
            if (type.isAdvanced() && this.getRemainingUses() > 0) {
                MutableText text = Text.translatable("item.virtual_additions.applied_effect_tooltip.advanced", this.getRemainingUses(), this.getTotalUses()).formatted(Formatting.DARK_PURPLE);
                tooltip.accept(text);
            }
            tooltip.accept(tooltipText);
            this.potionContents.get().buildTooltip(tooltip, 0.125F, 20);
        }
    }
    public EffectsOnHitComponent decrementRemainingUses() {
        if (this.remaining.isEmpty()) return this;
        int remaining = this.remaining.get() - 1;
        return remaining <= 0 ? null : new EffectsOnHitComponent(this.potionContents, this.uses, Optional.of(this.remaining.get() - 1));
    }

    public Iterable<StatusEffectInstance> getEffects() {
        if (this.potionContents.isPresent()) return this.potionContents.get().getEffects();
        return List.of();
    }

    public void forEachEffect(Consumer<StatusEffectInstance> effectConsumer) {
        this.potionContents.ifPresent(component -> component.forEachEffect(effectConsumer));
    }

    public static ComponentType.Builder<EffectsOnHitComponent> setCodecs(ComponentType.Builder<EffectsOnHitComponent> builder) {
        return builder.codec(CODEC).packetCodec(PACKET_CODEC);
    }

    static {
        CODEC = RecordCodecBuilder.create( instance -> {
            return instance.group(
                    PotionContentsComponent.CODEC.optionalFieldOf("potion").forGetter(EffectsOnHitComponent::potionContents),
                    Codecs.NONNEGATIVE_INT.optionalFieldOf("total_uses").forGetter(EffectsOnHitComponent::uses),
                    Codecs.NONNEGATIVE_INT.optionalFieldOf("remaining_uses").forGetter(EffectsOnHitComponent::remaining)
            ).apply(instance, EffectsOnHitComponent::new);
        });
        PACKET_CODEC = PacketCodec.tuple(PacketCodecs.optional(PotionContentsComponent.PACKET_CODEC), EffectsOnHitComponent::potionContents, PacketCodecs.INTEGER.collect(PacketCodecs::optional), EffectsOnHitComponent::uses, PacketCodecs.INTEGER.collect(PacketCodecs::optional), EffectsOnHitComponent::remaining, EffectsOnHitComponent::new);
    }
}
