package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.DestructiveSculkBlock;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAGameRules;
import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
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
            double miningEfficiency = player.getAttributeValue(EntityAttributes.PLAYER_MINING_EFFICIENCY) / 10.0;
            potency += (int) (potency * miningEfficiency);
            potency = Math.min(Math.min((tool.getMaxDamage() - tool.getDamage()), potency) -1, world.getGameRules().getInt(VAGameRules.SCULK_GILD_BLOCK_SELECTION_MAXIMUM));
            List<BlockPos> posList = selectPositions(world, pos, state, potency);
            //TODO: Rewrite DestructiveSculkBlock.placeState to use posList.
            DestructiveSculkBlock.placeState(world, pos, state, player.getUuid(), tool, potency);
            player.increaseStat(Stats.USED.getOrCreateStat(tool.getItem()), posList.size());
            tool.damage( posList.size(), player, EquipmentSlot.MAINHAND);
            player.getItemCooldownManager().set(tool.getItem(), posList.size() * 2);
            return false;
        }

        private static List<BlockPos> selectPositions(World world, BlockPos pos, BlockState state, int i){
            ArrayList<BlockPos> activeList = new ArrayList<>();
            ArrayList<BlockPos> finalList = new ArrayList<>();
            activeList.add(pos);
            finalList.add(pos);
            while (i > 0 && !activeList.isEmpty()) {
                BlockPos checkAroundPos = activeList.getFirst();
                for (Direction dir : Direction.values()) {
                    BlockPos checkAtPos = checkAroundPos.offset(dir);
                    if (!finalList.contains(checkAtPos) && world.getBlockState(checkAtPos).getBlock().equals(state.getBlock())) {
                        i -= 1;
                        activeList.add(checkAtPos);
                        finalList.add(checkAtPos);
                    }
                    for (Direction dirEdge : Direction.values()){
                        if (dirEdge.getAxis().equals(dir.getAxis())) continue;
                        checkAtPos = checkAroundPos.offset(dir).offset(dirEdge);
                        if (!finalList.contains(checkAtPos) && world.getBlockState(checkAtPos).getBlock().equals(state.getBlock())) {
                            i -= 1;
                            activeList.add(checkAtPos);
                            finalList.add(checkAtPos);
                        }
                    }
                }
                activeList.removeFirst();
            }
            return finalList;
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
