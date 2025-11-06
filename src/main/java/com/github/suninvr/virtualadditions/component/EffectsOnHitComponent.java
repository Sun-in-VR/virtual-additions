package com.github.suninvr.virtualadditions.component;

import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record EffectsOnHitComponent(Optional<PotionContents> potionContents, Optional<Integer> uses, Optional<Integer> remaining) implements TooltipProvider {
    public static final EffectsOnHitComponent DEFAULT = new EffectsOnHitComponent(Optional.empty(), Optional.empty(), Optional.empty());
    public static final Codec<EffectsOnHitComponent> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, EffectsOnHitComponent> PACKET_CODEC;
    private static final Component tooltipText = Component.translatable("item.virtual_additions.applied_effect_tooltip").withStyle(ChatFormatting.DARK_PURPLE);

    public EffectsOnHitComponent(Holder<Potion> potion) {
        this(Optional.of(new PotionContents(potion)), Optional.of(30), Optional.of(0));
    }

    public EffectsOnHitComponent(PotionContents component, int uses, int remaining) {
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
        return this.potionContents.map(PotionContents::getColor).orElse(16253176);
    }

    public boolean hasEffectsComponent() {
        return this.potionContents.isPresent();
    }

    public EffectsOnHitComponent decrementRemainingUses() {
        if (this.remaining.isEmpty()) return this;
        int remaining = this.remaining.get() - 1;
        return remaining <= 0 ? null : new EffectsOnHitComponent(this.potionContents, this.uses, Optional.of(this.remaining.get() - 1));
    }

    public Iterable<MobEffectInstance> getEffects() {
        if (this.potionContents.isPresent()) return this.potionContents.get().getAllEffects();
        return List.of();
    }

    public void forEachEffect(Consumer<MobEffectInstance> effectConsumer) {
        this.potionContents.ifPresent(component -> component.forEachEffect(effectConsumer, 1.0F));
    }

    public static DataComponentType.Builder<EffectsOnHitComponent> setCodecs(DataComponentType.Builder<EffectsOnHitComponent> builder) {
        return builder.persistent(CODEC).networkSynchronized(PACKET_CODEC);
    }

    static {
        CODEC = RecordCodecBuilder.create( instance -> instance.group(
                PotionContents.CODEC.optionalFieldOf("potion").forGetter(EffectsOnHitComponent::potionContents),
                ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("total_uses").forGetter(EffectsOnHitComponent::uses),
                ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("remaining_uses").forGetter(EffectsOnHitComponent::remaining)
        ).apply(instance, EffectsOnHitComponent::new));
        PACKET_CODEC = StreamCodec.composite(
                ByteBufCodecs.optional(PotionContents.STREAM_CODEC), EffectsOnHitComponent::potionContents,
                ByteBufCodecs.INT.apply(ByteBufCodecs::optional), EffectsOnHitComponent::uses,
                ByteBufCodecs.INT.apply(ByteBufCodecs::optional), EffectsOnHitComponent::remaining,
                EffectsOnHitComponent::new);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type, DataComponentGetter components) {
        if (this.hasEffectsComponent()) {
            if (type.isAdvanced() && this.getRemainingUses() > 0) {
                MutableComponent text = Component.translatable("item.virtual_additions.applied_effect_tooltip.advanced", this.getRemainingUses(), this.getTotalUses()).withStyle(ChatFormatting.DARK_PURPLE);
                textConsumer.accept(text);
            }
            textConsumer.accept(tooltipText);
            this.potionContents.get().addToTooltip(context, textConsumer, type, components);
        }
    }

    public void applyEffectsTo(ItemStack stack, LivingEntity livingEntity, LivingEntity livingEntity1) {
        this.potionContents.get().applyToLivingEntity(livingEntity, 1.0F);
        stack.set(VADataComponentTypes.EFFECTS_ON_HIT, this.decrementRemainingUses());
    }
}
