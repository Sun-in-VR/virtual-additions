package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.DestructiveSculkBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DestructiveSculkBlock extends BaseEntityBlock {
    public static final MapCodec<DestructiveSculkBlock> CODEC = simpleCodec(DestructiveSculkBlock::new);
    public static final BooleanProperty SPREADING = BooleanProperty.create("spreading");
    public static final BooleanProperty ORIGIN = BooleanProperty.create("origin");
    public DestructiveSculkBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(stateDefinition.any().setValue(SPREADING, true).setValue(ORIGIN, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SPREADING).add(ORIGIN);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DestructiveSculkBlockEntity(pos, state);
    }

    public static void setData(Level world, BlockPos pos, BlockState replacedState, UUID playerId, ItemStack tool, int potency) {
        if(world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity) {
            destructiveSculkBlockEntity.setPlayerId(playerId);
            destructiveSculkBlockEntity.setTool(tool);
            destructiveSculkBlockEntity.setPotency(potency);
            destructiveSculkBlockEntity.setReplacedState(replacedState);
        }
    }

    public static void setOriginData(Level world, BlockPos pos, BlockState replacedState, UUID playerId, ItemStack tool, int potency) {
        if(world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity) {
            destructiveSculkBlockEntity.setPlayerId(playerId);
            destructiveSculkBlockEntity.setTool(tool);
            destructiveSculkBlockEntity.setPotency(potency);
            destructiveSculkBlockEntity.setReplacedState(replacedState);
            destructiveSculkBlockEntity.setOrigin();
        }
    }

    public static void placeState(Level world, BlockPos pos, BlockState replacedState, UUID playerId, ItemStack tool, int potency) {
        boolean spreading = potency > 0;
        world.setBlockAndUpdate(pos, VABlocks.DESTRUCTIVE_SCULK.defaultBlockState().setValue(DestructiveSculkBlock.ORIGIN, true).setValue(SPREADING, spreading));
        world.scheduleTick(pos, VABlocks.DESTRUCTIVE_SCULK, spreading ? 2 : 10);
        DestructiveSculkBlock.setOriginData(world, pos, replacedState, playerId, tool, potency);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.getValue(SPREADING)) {
            if (world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity) {
                Player player = world.getPlayerByUUID(destructiveSculkBlockEntity.getPlayerId());
                if (player != null) player.awardStat(Stats.BLOCK_MINED.get(destructiveSculkBlockEntity.getReplacedState().getBlock()));
                destructiveSculkBlockEntity.destroyAll();
            }
        }
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        DestructiveSculkBlockEntity blockEntity = builder.getParameter(LootContextParams.BLOCK_ENTITY) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity ? destructiveSculkBlockEntity : null;
        if (blockEntity != null && blockEntity.getLevel() instanceof ServerLevel serverWorld) {
            return blockEntity.getDroppedStacks(serverWorld);
        }
        return Collections.emptyList();
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.spawnAfterBreak(state, world, pos, tool, dropExperience);
        DestructiveSculkBlockEntity blockEntity = getBlockEntity(world, pos);
        if (blockEntity != null) {
            Block block = blockEntity.getReplacedState().getBlock();
            if (block instanceof DropExperienceBlock experienceDroppingBlock) {
                tryDropExperience(world, pos, blockEntity.getTool(), experienceDroppingBlock.xpRange);
            }
        }
    }

    protected static DestructiveSculkBlockEntity getBlockEntity(Level world, BlockPos pos) {
        return world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity ? destructiveSculkBlockEntity : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (!state.getValue(ORIGIN)) return null;
        return createTickerHelper(type, VABlockEntityType.DESTRUCTIVE_SCULK, (DestructiveSculkBlockEntity::tick));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
