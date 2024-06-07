package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.DestructiveSculkBlock;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAEnchantmentTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.BiFunction;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings({"unused", "ClassEscapesDefinedScope"})
public class GildTypes {
    private static final BiFunction<Float, Float, Float> ADD = Float::sum;
    private static final BiFunction<Float, Float, Float> MULTIPLY = (attribute, modifier) -> attribute * modifier;
    private static final BiFunction<Float, Float, Float> MULTIPLY_ROUNDED_TENTHS = (attribute, modifier) -> Math.round(10 * (attribute * modifier)) / 10.0F;

    public static final GildType AMETHYST = new GildType(idOf("amethyst"), 0x9A5CC6, miningSpeedModifier(1, ADD), attackSpeedModifier(0.8F, MULTIPLY_ROUNDED_TENTHS));
    public static final GildType COPPER = new GildType(idOf("copper"), 0xB4684D, durabilityModifier(1.5F, MULTIPLY));
    public static final GildType EMERALD = new GildType(idOf("emerald"), 0x11A036, enchantabilityModifier(1.5F, MULTIPLY)){
        @Override
        public boolean isGildEffective(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            return super.isGildEffective(world, player, pos, state, tool) && state.getBlock() instanceof ExperienceDroppingBlock;
        }
    };
    public static final GildType IOLITE = new GildType(idOf("iolite"), 0x702bff, blockInteractionRangeModifier(4, ADD), entityInteractionRangeModifier(2, ADD));
    public static final GildType QUARTZ = new GildType(idOf("quartz"), 0xE3D4C4, attackDamageModifier(2, ADD));
    public static final GildType SCULK = new GildType(idOf("sculk"), 0x009295, miningSpeedModifier(0.4F, MULTIPLY), attackSpeedModifier(1.2F, MULTIPLY_ROUNDED_TENTHS, GildType.ModifierType.ToolType.SWORD)) {
        @Override
        public boolean isGildEffective(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            return !state.isOf(VABlocks.DESTRUCTIVE_SCULK) && state.isIn(VABlockTags.SCULK_GILD_EFFECTIVE) && super.isGildEffective(world, player, pos, state, tool);
        }

        @Override
        public void emitBlockBreakingEffects(World world, PlayerEntity player, BlockPos pos, ItemStack tool) {
            if (player.getItemCooldownManager().isCoolingDown(tool.getItem())) return;
            world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.SCULK_CHARGE_POP, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 50, 0.4, 0.4, 0.4, 0.02);
            }
        }

        @Override
        public boolean onBlockBroken(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            if (player.getItemCooldownManager().isCoolingDown(tool.getItem())) return true;
            boolean stronglyEffective = state.isIn(VABlockTags.SCULK_GILD_STRONGLY_EFFECTIVE);
            int potency = (int) Math.floor( Math.max(30 - (state.getHardness(world, pos) * (stronglyEffective ? 3 : 6) + 1), 0) );
            int[] corruptionLevel = {0};
            EnchantmentHelper.forEachEnchantment(tool, (enchantment, level) -> {
                if (enchantment.isIn(VAEnchantmentTags.CORRUPTION)) corruptionLevel[0] = level;
            });
            player.getItemCooldownManager().set(tool.getItem(), potency);
            potency += (potency * corruptionLevel[0]) / 3;
            DestructiveSculkBlock.placeState(world, pos, state, player.getUuid(), tool, potency);
            player.incrementStat(Stats.USED.getOrCreateStat(tool.getItem()));
            tool.damage( potency, player, EquipmentSlot.MAINHAND);
            return false;
        }
    };
    public static final GildType NONE = new GildType(idOf("none"), 0xFFFFFF);

    public static GildType.Modifier durabilityModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(VirtualAdditions.idOf("gild_durability_modifier"), GildType.ModifierType.DURABILITY, value, function, appliesTo);
    }
    public static GildType.Modifier miningSpeedModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(VirtualAdditions.idOf("gild_mining_speed_modifier"), GildType.ModifierType.MINING_SPEED, value, function, appliesTo);
    }
    public static GildType.Modifier attackDamageModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(VirtualAdditions.idOf("gild_attack_damage_modifier"), GildType.ModifierType.ATTACK_DAMAGE, value, function, appliesTo);
    }
    public static GildType.Modifier enchantabilityModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(VirtualAdditions.idOf("gild_enchantability_modifier"), GildType.ModifierType.ENCHANTABILITY, value, function, appliesTo);
    }
    public static GildType.Modifier attackSpeedModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(VirtualAdditions.idOf("gild_attack_speed_modifier"), GildType.ModifierType.ATTACK_SPEED, value, function, appliesTo);
    }
    public static GildType.Modifier blockInteractionRangeModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(VirtualAdditions.idOf("gild_block_interaction_range_modifier"), GildType.ModifierType.BLOCK_INTERACTION_RANGE, value, function, appliesTo);
    }
    public static GildType.Modifier entityInteractionRangeModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(VirtualAdditions.idOf("gild_entity_interaction_range_modifier"), GildType.ModifierType.ENTITY_INTERACTION_RANGE, value, function, appliesTo);
    }

}
