package com.github.suninvr.virtualadditions.item.gild;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.registry.VARegistries;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;

public class GildType {
    public static final Codec<RegistryEntry<GildType>> ENTRY_CODEC = RegistryFixedCodec.of(VARegistries.GILD_TYPE_REGISTRY_KEY);
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<GildType>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(VARegistries.GILD_TYPE_REGISTRY_KEY);
    private Text translationKey;
    private final ArrayList<StackModifier<?>> modifiers = new ArrayList<>();
    private final int color;

    public Text getTranslationKey() {
        if (this.translationKey == null) {
            Identifier id = VARegistries.GILD_TYPE.getId(this);
            this.translationKey = Text.translatable("gild_type." + id.getNamespace() + "." + id.getPath());
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
    public boolean isGildEffective(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
        return tool.isSuitableFor(state);
    }

    /**
     * Plays effects in the world after a tool with this gild type breaks a block.
     *
     * @param world the world in which the block was broken
     * @param pos the position of the block that was broken
     * @param tool the tool used to break the block
     * **/
    public void emitBlockBreakingEffects(World world, PlayerEntity player, BlockPos pos, ItemStack tool) {}

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
    public boolean onBlockBroken(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
        return true;
    }

    public boolean hasHitEffects() {
        return false;
    }

    public void applyEffectsOnHit(World world, LivingEntity target, LivingEntity attacker) {

    }

    /**
     * Gets the color of the gild type's tooltip text
     *
     * @return An integer representing the color
     */
    public int getColor() {
        return color;
    }
}
