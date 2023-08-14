package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.block.DestructiveSculkBlock;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.BiFunction;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("unused")
public class GildTypes {
    private static final BiFunction<Float, Float, Float> ADD = Float::sum;
    private static final BiFunction<Float, Float, Float> MULTIPLY = (attribute, modifier) -> attribute * modifier;
    private static final BiFunction<Float, Float, Float> MULTIPLY_AND_ROUND = (attribute, modifier) -> Math.round(10 * (attribute * modifier)) / 10.0F;

    public static final GildType AMETHYST = new GildType(idOf("amethyst"), 0x9A5CC6, miningSpeedModifier(1, ADD), attackSpeedModifier(0.8F, MULTIPLY_AND_ROUND));
    public static final GildType COPPER = new GildType(idOf("copper"), 0xB4684D, durabilityModifier(1.5F, MULTIPLY));
    public static final GildType EMERALD = new GildType(idOf("emerald"), 0x11A036, enchantabilityModifier(1.5F, MULTIPLY)){
        @Override
        public boolean isGildEffective(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            return super.isGildEffective(world, player, pos, state, tool) && state.getBlock() instanceof ExperienceDroppingBlock;
        }

        @Override
        public void emitBlockBreakingEffects(World world, BlockPos pos, ItemStack tool) {
            world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 0.02F, world.getRandom().nextFloat() * 0.5F + 1.5F);
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.25, 0.25, 0.25, 0.02);
            }
        }
    };
    public static final GildType QUARTZ = new GildType(idOf("quartz"), 0xE3D4C4, attackDamageModifier(2, ADD));
    public static final GildType SCULK = new GildType(idOf("sculk"), 0x009295, miningSpeedModifier(0.4F, MULTIPLY), attackSpeedModifier(1.2F, MULTIPLY_AND_ROUND, GildType.ModifierType.ToolType.SWORD)) {
        @Override
        public boolean isGildEffective(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            return !state.isOf(VABlocks.DESTRUCTIVE_SCULK) && state.isIn(VABlockTags.SCULK_GILD_EFFECTIVE) && super.isGildEffective(world, player, pos, state, tool);
        }

        @Override
        public void emitBlockBreakingEffects(World world, BlockPos pos, ItemStack tool) {
            world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.SCULK_CHARGE_POP, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 50, 0.4, 0.4, 0.4, 0.02);
            }
        }

        @Override
        public boolean onBlockBroken(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            boolean stronglyEffective = state.isIn(VABlockTags.SCULK_GILD_STRONGLY_EFFECTIVE);
            int potency = (int) Math.floor( Math.max(30 - (state.getHardness(world, pos) * (stronglyEffective ? 3 : 6) + 1), 0) );
            DestructiveSculkBlock.placeState(world, pos, state, player.getUuid(), tool, potency);
            player.incrementStat(Stats.USED.getOrCreateStat(tool.getItem()));
            tool.damage( potency, player, player1 -> player1.sendToolBreakStatus(Hand.MAIN_HAND));
            return false;
        }
    };
    public static final GildType NONE = new GildType(idOf("none"), 0xFFFFFF);



    public static GildType.Modifier durabilityModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(GildType.ModifierType.DURABILITY, value, function, appliesTo);
    }
    public static GildType.Modifier miningSpeedModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(GildType.ModifierType.MINING_SPEED, value, function, appliesTo);
    }
    public static GildType.Modifier attackDamageModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(GildType.ModifierType.ATTACK_DAMAGE, value, function, appliesTo);
    }
    public static GildType.Modifier miningLevelModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(GildType.ModifierType.MINING_LEVEL, value, function, appliesTo);
    }
    public static GildType.Modifier enchantabilityModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(GildType.ModifierType.ENCHANTABILITY, value, function, appliesTo);
    }
    public static GildType.Modifier attackSpeedModifier(float value, BiFunction<Float, Float, Float> function, GildType.ModifierType.ToolType... appliesTo) {
        return new GildType.Modifier(GildType.ModifierType.ATTACK_SPEED, value, function, appliesTo);
    }

}
