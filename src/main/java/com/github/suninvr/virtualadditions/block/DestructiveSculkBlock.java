package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.DestructiveSculkBlockEntity;
import com.github.suninvr.virtualadditions.interfaces.ExperienceDroppingBlockInterface;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DestructiveSculkBlock extends BlockWithEntity {
    public static final BooleanProperty SPREADING = BooleanProperty.of("spreading");
    public DestructiveSculkBlock(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(SPREADING, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SPREADING);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DestructiveSculkBlockEntity(pos, state);
    }

    public static void setData(World world, BlockPos pos, BlockState replacedState, PlayerEntity player, ItemStack tool, int potency) {
        setData(world, pos, replacedState, player.getUuid(), tool, potency);
    }

    public static void setData(World world, BlockPos pos, BlockState replacedState, UUID playerId, ItemStack tool, int potency) {
        if(world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity) {
            destructiveSculkBlockEntity.setReplacedState(replacedState);
            destructiveSculkBlockEntity.setPlayerId(playerId);
            destructiveSculkBlockEntity.setTool(tool);
            destructiveSculkBlockEntity.setPotency(potency);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean bl = state.get(SPREADING);
        if (bl) {
            world.setBlockState(pos, state.with(SPREADING, false));
            trySpread(state, world, pos);
            world.scheduleBlockTick(pos, this, random.nextBetween(5, 20));
        }
        else {
            if (world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity) {
                PlayerEntity player = world.getPlayerByUuid(destructiveSculkBlockEntity.getPlayerId());
                if (player != null) player.incrementStat(Stats.MINED.getOrCreateStat(destructiveSculkBlockEntity.getReplacedState().getBlock()));
            }
            world.breakBlock(pos, true);
        }
    }

    private void trySpread(BlockState state, World world, BlockPos pos) {
        if (!state.isOf(this)) return;
        DestructiveSculkBlockEntity blockEntity = getBlockEntity(world, pos);
        if (blockEntity == null) return;
        if (blockEntity.getPotency() <= 0) return;
        ArrayList<BlockPos> validPos = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            BlockState state1 = world.getBlockState(blockPos);
            if (state1.isOf(blockEntity.getReplacedState().getBlock())) {
                validPos.add(blockPos);
            }
        }
        for (BlockPos blockPos : validPos) {
            BlockState state1 = world.getBlockState(blockPos);
            float hardness = Math.max(state1.getHardness(world, blockPos), 2.0F);
            int size = validPos.size();
            int nextPotency = Math.round(blockEntity.getPotency() - (1.33F * size * size + hardness * hardness ));
            world.setBlockState(blockPos, VABlocks.DESTRUCTIVE_SCULK.getDefaultState());
            setData(world, blockPos, state1, blockEntity.getPlayerId(), blockEntity.getTool(), nextPotency);
            world.scheduleBlockTick(blockPos, this, 2);
        }
        world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        DestructiveSculkBlockEntity blockEntity = builder.get(LootContextParameters.BLOCK_ENTITY) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity ? destructiveSculkBlockEntity : null;
        if (blockEntity != null) {
            blockEntity.modifyLootContext(builder);
            return blockEntity.getReplacedState().getDroppedStacks(builder);
        }
        return Collections.emptyList();
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        DestructiveSculkBlockEntity blockEntity = getBlockEntity(world, pos);
        if (blockEntity != null) {
            Block block = blockEntity.getReplacedState().getBlock();
            if (block instanceof ExperienceDroppingBlock experienceDroppingBlock) {
                dropExperienceWhenMined(world, pos, blockEntity.getTool(), ((ExperienceDroppingBlockInterface)experienceDroppingBlock).getExperienceDropped() );
            }
        }
    }

    protected DestructiveSculkBlockEntity getBlockEntity(World world, BlockPos pos) {
        return world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity ? destructiveSculkBlockEntity : null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
