package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.DestructiveSculkBlockEntity;
import com.github.suninvr.virtualadditions.interfaces.ExperienceDroppingBlockInterface;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
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
    public static final BooleanProperty ORIGIN = BooleanProperty.of("origin");
    public DestructiveSculkBlock(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(SPREADING, true).with(ORIGIN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SPREADING).add(ORIGIN);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DestructiveSculkBlockEntity(pos, state);
    }

    public static void setData(World world, BlockPos pos, BlockState replacedState, UUID playerId, ItemStack tool, int potency, BlockPos originPos) {
        if(world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity) {
            destructiveSculkBlockEntity.setReplacedState(replacedState);
            destructiveSculkBlockEntity.setPlayerId(playerId);
            destructiveSculkBlockEntity.setTool(tool);
            destructiveSculkBlockEntity.setPotency(potency);
            destructiveSculkBlockEntity.setOriginPos(originPos);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean bl = state.get(SPREADING);
        if (bl) {
            world.setBlockState(pos, state.with(SPREADING, false));
            trySpread(state, world, pos);
        }
        else {
            if (world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity) {
                if (destructiveSculkBlockEntity.isOrigin()) {
                    destructiveSculkBlockEntity.getAffectedPos().forEach( (blockPos) -> world.scheduleBlockTick(blockPos, this, random.nextBetween(1, 5)));
                }
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
        DestructiveSculkBlockEntity originEntity = getBlockEntity(world, blockEntity.getOriginPos());
        if (originEntity == null) return;
        if (originEntity.getPotency() <= 0) return;
        ArrayList<BlockPos> validPos = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            BlockState stateToReplace = world.getBlockState(blockPos);
            if (stateToReplace.isOf(blockEntity.getReplacedState().getBlock())) {
                validPos.add(blockPos);
            }
        }
        int potency = originEntity.getPotency();
        for (BlockPos blockPos : validPos) {
            if (potency <= 0) {
                break;
            }
            potency -= 1;
            BlockState replacedState = world.getBlockState(blockPos);
            world.setBlockState(blockPos, VABlocks.DESTRUCTIVE_SCULK.getDefaultState());
            setData(world, blockPos, replacedState, blockEntity.getPlayerId(), blockEntity.getTool(), 0, blockEntity.getOriginPos());
            world.scheduleBlockTick(blockPos, this, 2);
            originEntity.addAffectedPos(blockPos);
            originEntity.setPotency(potency);
        }
        world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity blockEntity) {
            if (blockEntity.isOrigin()) {
                blockEntity.getAffectedPos().forEach( (blockPos) -> {
                    world.setBlockState(blockPos, VABlocks.DESTRUCTIVE_SCULK.getDefaultState().with(SPREADING, false));
                    world.scheduleBlockTick(blockPos, this, world.getRandom().nextBetween(1, 5));
                } );
            }
        }
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!state.get(ORIGIN)) return null;
        return checkType(type, VABlockEntities.DESTRUCTIVE_SCULK_BLOCK_ENTITY, (DestructiveSculkBlockEntity::tick));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
