package com.github.suninvr.virtualadditions.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentType;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
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

public record EffectsOnHitComponent(Optional<PotionContentsComponent> potionContents, Optional<Integer> uses, Optional<Integer> remaining){
    public static final EffectsOnHitComponent DEFAULT = new EffectsOnHitComponent(Optional.empty(), Optional.empty(), Optional.empty());
    public static final Codec<EffectsOnHitComponent> CODEC;
    public static final PacketCodec<RegistryByteBuf, EffectsOnHitComponent> PACKET_CODEC;

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

    public void buildTooltip(Consumer<Text> textConsumer, float tickRate, boolean advanced) {
        if (this.hasEffectsComponent()) {
            MutableText text = Text.translatable("item.virtual_additions.applied_effect_tooltip").formatted(Formatting.DARK_PURPLE);
            this.potionContents.get().buildTooltip(textConsumer, 0.125F, tickRate);
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

    public static DataComponentType.Builder<EffectsOnHitComponent> setCodecs(DataComponentType.Builder<EffectsOnHitComponent> builder) {
        return builder.codec(CODEC).packetCodec(PACKET_CODEC);
    }

    static {
        CODEC = RecordCodecBuilder.create( instance -> {
            return instance.group(
                    Codecs.createStrictOptionalFieldCodec(PotionContentsComponent.CODEC, "potion").forGetter(EffectsOnHitComponent::potionContents),
                    Codecs.createStrictOptionalFieldCodec(Codecs.NONNEGATIVE_INT, "total_uses").forGetter(EffectsOnHitComponent::uses),
                    Codecs.createStrictOptionalFieldCodec(Codecs.NONNEGATIVE_INT, "remaining_uses").forGetter(EffectsOnHitComponent::remaining)
            ).apply(instance, EffectsOnHitComponent::new); //return instance.group(Codecs.createStrictOptionalFieldCodec(Registries.POTION.getEntryCodec(), "potion").forGetter(EffectsOnHitComponent::potion), Codecs.createStrictOptionalFieldCodec(Codec.INT, "custom_color").forGetter(EffectsOnHitComponent::customColor), Codecs.createStrictOptionalFieldCodec(StatusEffectInstance.CODEC.listOf(), "custom_effects", List.of()).forGetter(EffectsOnHitComponent::customEffects), Codecs.createStrictOptionalFieldCodec()).apply(instance, PotionContentsComponent::new);
        });
        PACKET_CODEC = PacketCodec.tuple(PacketCodecs.optional(PotionContentsComponent.PACKET_CODEC), EffectsOnHitComponent::potionContents, PacketCodecs.INTEGER.collect(PacketCodecs::optional), EffectsOnHitComponent::uses, PacketCodecs.INTEGER.collect(PacketCodecs::optional), EffectsOnHitComponent::remaining, EffectsOnHitComponent::new);
    }
}
