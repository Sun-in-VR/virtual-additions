package com.github.suninvr.virtualadditions.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.Optional;
import java.util.function.Consumer;

public record IncenseEffectsComponent(Optional<PotionContents> potionContents, Optional<Integer> burnTicks) implements TooltipProvider {
    public static final IncenseEffectsComponent DEFAULT = new IncenseEffectsComponent(Optional.empty(), Optional.empty());
    public static final Codec<IncenseEffectsComponent> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, IncenseEffectsComponent> STREAM_CODEC;

    static {
        CODEC = RecordCodecBuilder.create(instance -> instance.group(
                PotionContents.CODEC.optionalFieldOf("potion").forGetter(IncenseEffectsComponent::potionContents),
                ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("burn_ticks").forGetter(IncenseEffectsComponent::burnTicks)
        ).apply(instance, IncenseEffectsComponent::new));
        STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.optional(PotionContents.STREAM_CODEC), IncenseEffectsComponent::potionContents,
                ByteBufCodecs.INT.apply(ByteBufCodecs::optional), IncenseEffectsComponent::burnTicks,
                IncenseEffectsComponent::new);
    }

    public static DataComponentType.Builder<IncenseEffectsComponent> setCodecs(DataComponentType.Builder<IncenseEffectsComponent> builder) {
        return builder.persistent(CODEC).networkSynchronized(STREAM_CODEC);
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        if (this.potionContents.isPresent()) {
            this.potionContents.get().addToTooltip(tooltipContext, consumer, tooltipFlag, dataComponentGetter);
            if (tooltipFlag.isAdvanced() && this.burnTicks.isPresent() && this.burnTicks.get() > 0) consumer.accept(Component.literal("Burn Time: ").append(String.valueOf(this.burnTicks.get() / 20)).append(" seconds").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
