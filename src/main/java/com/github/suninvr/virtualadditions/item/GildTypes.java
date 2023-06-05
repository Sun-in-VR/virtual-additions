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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.function.BiFunction;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class GildTypes {
    private static final BiFunction<Float, Float, Float> ADD = Float::sum;
    private static final BiFunction<Float, Float, Float> MULTIPLY = (attribute, modifier) -> attribute * modifier;

    public static final GildType AMETHYST = new GildType(idOf("amethyst"), 0x9A5CC6, new GildType.Modifier(GildType.ModifierType.MINING_SPEED, 1, ADD)){
        @Override
        public void emitBlockBreakingEffects(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 0.6F, world.getRandom().nextFloat() + 0.5F);
        }
    };
    public static final GildType COPPER = new GildType(idOf("copper"), 0xB4684D, new GildType.Modifier(GildType.ModifierType.DURABILITY, 1.5F, MULTIPLY)){};
    public static final GildType EMERALD = new GildType(idOf("emerald"), 0x11A036, new GildType.Modifier(GildType.ModifierType.ENCHANTABILITY, 1.5F, MULTIPLY)){
        @Override
        public boolean isGildEffective(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            return super.isGildEffective(world, player, pos, state, tool) && state.getBlock() instanceof ExperienceDroppingBlock;
        }

        @Override
        public void emitBlockBreakingEffects(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 0.02F, world.getRandom().nextFloat() * 0.5F + 1.5F);
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.25, 0.25, 0.25, 0.02);
            }
        }
    };
    public static final GildType QUARTZ = new GildType(idOf("quartz"), 0xE3D4C4, new GildType.Modifier(GildType.ModifierType.ATTACK_DAMAGE, 2, ADD)){};
    public static final GildType SCULK = new GildType(idOf("sculk"), 0x009295, new GildType.Modifier(GildType.ModifierType.MINING_SPEED, 0.4F, MULTIPLY)) {
        @Override
        public boolean isGildEffective(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            return !state.isOf(VABlocks.DESTRUCTIVE_SCULK) && state.isIn(VABlockTags.SCULK_GILD_EFFECTIVE) && super.isGildEffective(world, player, pos, state, tool);
        }

        @Override
        public void emitBlockBreakingEffects(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.SCULK_CHARGE_POP, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 50, 0.4, 0.4, 0.4, 0.02);
            }
        }

        @Override
        public boolean onBlockBroken(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
            world.setBlockState(pos, VABlocks.DESTRUCTIVE_SCULK.getDefaultState().with(DestructiveSculkBlock.ORIGIN, true));
            world.scheduleBlockTick(pos, VABlocks.DESTRUCTIVE_SCULK, 2);
            boolean stronglyEffective = state.isIn(VABlockTags.SCULK_GILD_STRONGLY_EFFECTIVE);
            int hardnessMul = stronglyEffective ? 3 : 6;
            int potency = (int) Math.floor(MathHelper.clamp(30 - (state.getHardness(world, pos) * hardnessMul + 1), 0, 30));
            DestructiveSculkBlock.setData(world, pos, state, player.getUuid(), tool, potency);
            player.incrementStat(Stats.USED.getOrCreateStat(tool.getItem()));
            tool.damage( stronglyEffective ? 5 : 15, player, player1 -> player1.sendToolBreakStatus(Hand.MAIN_HAND));
            return false;
        }
    };
    public static final GildType NONE = new GildType(idOf("none"), 0xFFFFFF){};
    
}
