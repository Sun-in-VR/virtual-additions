package com.github.suninvr.virtualadditions.item.gild;

import com.github.suninvr.virtualadditions.block.DestructiveSculkBlock;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAGameRules;
import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class SculkGildType extends GildType{
    public SculkGildType(StackModifier<?>... modifiers) {
        super(0x009295, modifiers);
    }

    @Override
    public boolean isGildEffective(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
        return !player.getItemCooldownManager().isCoolingDown(tool) && !state.isOf(VABlocks.DESTRUCTIVE_SCULK) && state.getHardness(world, pos) > 0 && world.getBlockEntity(pos) == null && super.isGildEffective(world, player, pos, state, tool);
    }

    @Override
    public void emitBlockBreakingEffects(World world, PlayerEntity player, BlockPos pos, ItemStack tool) {
        world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 1.0F, 1.0F);
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.SCULK_CHARGE_POP, false, false, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 50, 0.4, 0.4, 0.4, 0.02);
        }
    }

    @Override
    public boolean onBlockBroken(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
        if (world instanceof ServerWorld serverWorld) {
            double miningEfficiency = player.getAttributeValue(EntityAttributes.MINING_EFFICIENCY);
            int potency = (int) (48 / state.getHardness(world, pos));
            potency += (int) (potency * (miningEfficiency / 13.0));
            potency = Math.min(Math.min(tool.getMaxDamage() == 0 ? 10000 : (tool.getMaxDamage() - tool.getDamage()), potency) - 1, serverWorld.getGameRules().getInt(VAGameRules.SCULK_GILD_BLOCK_SELECTION_MAXIMUM));
            if (potency <= 0) return true;

            List<BlockPos> posList = selectPositions(world, pos, state, potency);
            DestructiveSculkBlock.placeState(world, pos, state, player.getUuid(), tool, potency);
            int i = posList.size();
            tool.damage(i, player, EquipmentSlot.MAINHAND);
            player.getItemCooldownManager().set(tool, (int) ((i) / ((miningEfficiency / 20.0) + 1)));
        }
        return false;
    }

    @Override
    public boolean hasHitEffects() {
        return true;
    }

    @Override
    public void applyEffectsOnHit(World world, LivingEntity target, LivingEntity attacker) {
        if (!target.hasStatusEffect(VAStatusEffects.FESTERING_WOUNDS)) world.playSound(target, target.getBlockPos(), SoundEvents.BLOCK_SCULK_SPREAD, target.getSoundCategory(), 1.5F, 1.0F);
        target.addStatusEffect(new StatusEffectInstance(VAStatusEffects.FESTERING_WOUNDS, 400), attacker);
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
}
