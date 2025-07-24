package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.DestructiveSculkBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DestructiveSculkBlock extends BlockWithEntity {
    public static final MapCodec<DestructiveSculkBlock> CODEC = createCodec(DestructiveSculkBlock::new);
    public static final BooleanProperty SPREADING = BooleanProperty.of("spreading");
    public static final BooleanProperty ORIGIN = BooleanProperty.of("origin");
    public DestructiveSculkBlock(Settings settings) {
        super(settings);
        this.setDefaultState(stateManager.getDefaultState().with(SPREADING, true).with(ORIGIN, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
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

    public static void setData(World world, BlockPos pos, BlockState replacedState, UUID playerId, ItemStack tool, int potency) {
        if(world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity) {
            destructiveSculkBlockEntity.setPlayerId(playerId);
            destructiveSculkBlockEntity.setTool(tool);
            destructiveSculkBlockEntity.setPotency(potency);
            destructiveSculkBlockEntity.setReplacedState(replacedState);
        }
    }

    public static void setOriginData(World world, BlockPos pos, BlockState replacedState, UUID playerId, ItemStack tool, int potency) {
        if(world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity) {
            destructiveSculkBlockEntity.setPlayerId(playerId);
            destructiveSculkBlockEntity.setTool(tool);
            destructiveSculkBlockEntity.setPotency(potency);
            destructiveSculkBlockEntity.setReplacedState(replacedState);
            destructiveSculkBlockEntity.setOrigin();
        }
    }

    public static void placeState(World world, BlockPos pos, BlockState replacedState, UUID playerId, ItemStack tool, int potency) {
        boolean spreading = potency > 0;
        world.setBlockState(pos, VABlocks.DESTRUCTIVE_SCULK.getDefaultState().with(DestructiveSculkBlock.ORIGIN, true).with(SPREADING, spreading));
        world.scheduleBlockTick(pos, VABlocks.DESTRUCTIVE_SCULK, spreading ? 2 : 10);
        DestructiveSculkBlock.setOriginData(world, pos, replacedState, playerId, tool, potency);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(SPREADING)) {
            if (world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity) {
                PlayerEntity player = world.getPlayerByUuid(destructiveSculkBlockEntity.getPlayerId());
                if (player != null) player.incrementStat(Stats.MINED.getOrCreateStat(destructiveSculkBlockEntity.getReplacedState().getBlock()));
                destructiveSculkBlockEntity.destroyAll();
            }
        }
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        DestructiveSculkBlockEntity blockEntity = builder.get(LootContextParameters.BLOCK_ENTITY) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity ? destructiveSculkBlockEntity : null;
        if (blockEntity != null && blockEntity.getWorld() instanceof ServerWorld serverWorld) {
            return blockEntity.getDroppedStacks(serverWorld);
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
                dropExperienceWhenMined(world, pos, blockEntity.getTool(), experienceDroppingBlock.experienceDropped);
            }
        }
    }

    protected static DestructiveSculkBlockEntity getBlockEntity(World world, BlockPos pos) {
        return world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity ? destructiveSculkBlockEntity : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!state.get(ORIGIN)) return null;
        return validateTicker(type, VABlockEntityType.DESTRUCTIVE_SCULK, (DestructiveSculkBlockEntity::tick));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
