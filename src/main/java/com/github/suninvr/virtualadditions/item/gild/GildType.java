package com.github.suninvr.virtualadditions.item.gild;

import com.github.suninvr.virtualadditions.registry.VARegistries;
import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class GildType implements TooltipProvider {
    private static final Component descriptionHeader = Component.translatable("item.minecraft.smithing_template.upgrade").withStyle(ChatFormatting.GRAY);
    private Component descriptionText;
    public static final Codec<Holder<GildType>> ENTRY_CODEC = RegistryFixedCodec.create(VARegistries.GILD_TYPE_REGISTRY_KEY);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GildType>> ENTRY_PACKET_CODEC = ByteBufCodecs.holderRegistry(VARegistries.GILD_TYPE_REGISTRY_KEY);
    public static final Codec<GildType> CODEC = RegistryFixedCodec.create(VARegistries.GILD_TYPE_REGISTRY_KEY).xmap(Holder::value, VARegistries.GILD_TYPE::wrapAsHolder);
    public static final StreamCodec<RegistryFriendlyByteBuf, GildType> PACKET_CODEC = ByteBufCodecs.holderRegistry(VARegistries.GILD_TYPE_REGISTRY_KEY).map(Holder::value, VARegistries.GILD_TYPE::wrapAsHolder);
    private Component translationKey;
    private final ArrayList<StackModifier<?>> modifiers = new ArrayList<>();
    private final int color;

    public Component getTranslationKey() {
        if (this.translationKey == null) {
            Identifier id = VARegistries.GILD_TYPE.getKey(this);
            this.translationKey = Component.translatable("gild_type." + id.getNamespace() + "." + id.getPath());
        }
        return this.translationKey;
    }

    public void modifyStackOnCrafted(ItemStack result) {
        this.modifiers.forEach(stackModifier -> stackModifier.modify(result));
    }

    public GildType(int color, StackModifier<?>... modifiers) {
        this.color = color;
        this.modifiers.addAll(Arrays.asList(modifiers));
    }

    /**
     * Gets whether the gild should play effects or interact with the world after breaking a specific block.
     *
     * @return <code>true</code> if the gild type is effective, <code>false</code> if it is not effective.
     *
     * @param world the world in which the block was broken
     * @param pos the position of the block that was broken
     * @param player the player that broke the block
     * @param state the state of the broken block
     * @param tool the tool used to break the block
     *
     * **/
    public boolean isGildEffective(Level world, Player player, BlockPos pos, BlockState state, ItemStack tool) {
        return tool.isCorrectToolForDrops(state);
    }

    /**
     * Plays effects in the world after a tool with this gild type breaks a block.
     *
     * @param world the world in which the block was broken
     * @param pos the position of the block that was broken
     * @param tool the tool used to break the block
     * **/
    public void emitBlockBreakingEffects(Level world, Player player, BlockPos pos, ItemStack tool) {}

    /**
     * Affects the world after a tool with this gild type breaks a block.
     *
     * @return <code>true</code> if the block should still break normally, <code>false</code> if it should not.
     *
     * @param world the world in which the block was broken
     * @param pos the position of the block that was broken
     * @param player the player that broke the block
     * @param state the state of the broken block
     * @param tool the tool used to break the block
     *
     * @implNote Returning false will disable <b><i>all effects</i></b> of breaking a block, such damaging the tool and increasing the player's relevant stats.
     *
     * **/
    public boolean onBlockBroken(Level world, Player player, BlockPos pos, BlockState state, ItemStack tool) {
        return true;
    }

    public boolean hasHitEffects() {
        return false;
    }

    public void applyEffectsOnHit(Level world, LivingEntity target, LivingEntity attacker) {

    }

    /**
     * Gets the color of the gild type's tooltip text
     *
     * @return An integer representing the color
     */
    public int getColor() {
        return color;
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type, DataComponentGetter components) {
        textConsumer.accept(descriptionHeader);
        if (this.descriptionText == null) {
            this.descriptionText = CommonComponents.space().append(this.getTranslationKey()).withColor(color);
        }
        textConsumer.accept(this.descriptionText);
    }
}
