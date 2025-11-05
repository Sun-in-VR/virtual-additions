package com.github.suninvr.virtualadditions.item.gild;

import com.github.suninvr.virtualadditions.block.DestructiveSculkBlock;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAGameRules;
import com.github.suninvr.virtualadditions.registry.VAStatusEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class SculkGildType extends GildType{
    public SculkGildType(StackModifier<?>... modifiers) {
        super(0x009295, modifiers);
    }

    @Override
    public boolean isGildEffective(Level world, Player player, BlockPos pos, BlockState state, ItemStack tool) {
        return !player.getCooldowns().isOnCooldown(tool) && !state.is(VABlocks.DESTRUCTIVE_SCULK) && state.getDestroySpeed(world, pos) > 0 && world.getBlockEntity(pos) == null && super.isGildEffective(world, player, pos, state, tool);
    }

    @Override
    public void emitBlockBreakingEffects(Level world, Player player, BlockPos pos, ItemStack tool) {
        world.playSound(null, pos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (world instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.SCULK_CHARGE_POP, false, false, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 50, 0.4, 0.4, 0.4, 0.02);
        }
    }

    @Override
    public boolean onBlockBroken(Level world, Player player, BlockPos pos, BlockState state, ItemStack tool) {
        if (world instanceof ServerLevel serverWorld) {
            double miningEfficiency = player.getAttributeValue(Attributes.MINING_EFFICIENCY);
            int potency = (int) (48 / state.getDestroySpeed(world, pos));
            potency += (int) (potency * (miningEfficiency / 13.0));
            potency = Math.min(Math.min(tool.getMaxDamage() == 0 ? 10000 : (tool.getMaxDamage() - tool.getDamageValue()), potency) - 1, serverWorld.getGameRules().getInt(VAGameRules.SCULK_GILD_BLOCK_SELECTION_MAXIMUM));
            if (potency <= 0) return true;

            List<BlockPos> posList = selectPositions(world, pos, state, potency);
            DestructiveSculkBlock.placeState(world, pos, state, player.getUUID(), tool, potency);
            int i = posList.size();
            tool.hurtAndBreak(i, player, EquipmentSlot.MAINHAND);
            player.getCooldowns().addCooldown(tool, (int) ((i) / ((miningEfficiency / 20.0) + 1)));
        }
        return false;
    }

    @Override
    public boolean hasHitEffects() {
        return true;
    }

    @Override
    public void applyEffectsOnHit(Level world, LivingEntity target, LivingEntity attacker) {
        if (!target.hasEffect(VAStatusEffects.FESTERING_WOUNDS)) world.playSound(target, target.blockPosition(), SoundEvents.SCULK_BLOCK_SPREAD, target.getSoundSource(), 1.5F, 1.0F);
        target.addEffect(new MobEffectInstance(VAStatusEffects.FESTERING_WOUNDS, 400), attacker);
    }

    private static List<BlockPos> selectPositions(Level world, BlockPos pos, BlockState state, int i){
        ArrayList<BlockPos> activeList = new ArrayList<>();
        ArrayList<BlockPos> finalList = new ArrayList<>();
        activeList.add(pos);
        finalList.add(pos);
        while (i > 0 && !activeList.isEmpty()) {
            BlockPos checkAroundPos = activeList.getFirst();
            for (Direction dir : Direction.values()) {
                BlockPos checkAtPos = checkAroundPos.relative(dir);
                if (!finalList.contains(checkAtPos) && world.getBlockState(checkAtPos).getBlock().equals(state.getBlock())) {
                    i -= 1;
                    activeList.add(checkAtPos);
                    finalList.add(checkAtPos);
                }
                for (Direction dirEdge : Direction.values()){
                    if (dirEdge.getAxis().equals(dir.getAxis())) continue;
                    checkAtPos = checkAroundPos.relative(dir).relative(dirEdge);
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
