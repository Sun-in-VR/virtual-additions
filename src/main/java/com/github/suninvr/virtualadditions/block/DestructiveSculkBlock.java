package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.DestructiveSculkBlockEntity;
import com.github.suninvr.virtualadditions.interfaces.ExperienceDroppingBlockInterface;
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
            destructiveSculkBlockEntity.setReplacedState(replacedState);
            destructiveSculkBlockEntity.setPlayerId(playerId);
            destructiveSculkBlockEntity.setTool(tool);
            destructiveSculkBlockEntity.setPotency(potency);
        }
    }

    public static void placeState(World world, BlockPos pos, BlockState replacedState, UUID playerId, ItemStack tool, int potency) {
        boolean spreading = potency > 0;
        world.setBlockState(pos, VABlocks.DESTRUCTIVE_SCULK.getDefaultState().with(DestructiveSculkBlock.ORIGIN, true).with(SPREADING, spreading));
        world.scheduleBlockTick(pos, VABlocks.DESTRUCTIVE_SCULK, spreading ? 2 : 10);
        DestructiveSculkBlock.setData(world, pos, replacedState, playerId, tool, potency);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(SPREADING)) {
            if (world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity destructiveSculkBlockEntity) {
                PlayerEntity player = world.getPlayerByUuid(destructiveSculkBlockEntity.getPlayerId());
                if (player != null) player.incrementStat(Stats.MINED.getOrCreateStat(destructiveSculkBlockEntity.getReplacedState().getBlock()));
                destructiveSculkBlockEntity.destroyAll(false);
            }
        }
    }

    public static boolean trySpread(BlockState state, World world, BlockPos pos, DestructiveSculkBlockEntity originEntity) {
        if (world.isClient()) return false;
        if (!state.isOf(VABlocks.DESTRUCTIVE_SCULK)) return false;
        DestructiveSculkBlockEntity blockEntity = getBlockEntity(world, pos);
        if (blockEntity == null) return false;
        if (originEntity.getPotency() <= 0) return false;
        ArrayList<BlockPos> validPos = new ArrayList<>();
        ArrayList<BlockPos> validPosLater = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    BlockPos blockPos = pos.add(i, j, k);
                    BlockState stateToReplace = world.getBlockState(blockPos);
                    int ia = Math.abs(i);
                    int ja = Math.abs(j);
                    int ka = Math.abs(k);
                    if (stateToReplace.isOf(blockEntity.getReplacedState().getBlock()) && !(ia == ja && ja == ka)) {
                        if ( (ia + ja + ka) >= 2) {
                            validPosLater.add(blockPos);
                        } else {
                            validPos.add(blockPos);
                        }
                    }
                }
            }
        }

        validPos.addAll(validPosLater);
        validPosLater.clear();

        boolean bl = false;
        if (!validPos.isEmpty()) {
            BlockPos blockPos = validPos.getFirst();
            BlockState stateToReplace = world.getBlockState(blockPos);
            world.setBlockState(blockPos, VABlocks.DESTRUCTIVE_SCULK.getDefaultState());
            setData(world, blockPos, stateToReplace, blockEntity.getPlayerId(), blockEntity.getTool(), 0);
            originEntity.addAffectedPos(blockPos);
            originEntity.setPotency(originEntity.getPotency() - 1);
            world.playSound(null, blockPos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 0.5F, 1.0F);
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.SCULK_CHARGE_POP, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 20, 0.4, 0.4, 0.4, 0.02);
            }
            bl = true;
        }
        if (validPos.size() <= 1 || originEntity.getPotency() < 1) {
            world.setBlockState(pos, state.with(SPREADING, false));
        }
        return bl;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (world.isClient()) return;
        if (newState.isOf(state.getBlock())) return;
        if (world.getBlockEntity(pos) instanceof DestructiveSculkBlockEntity blockEntity) {
            if (state.get(ORIGIN)) blockEntity.destroyAll(true);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
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
                dropExperienceWhenMined(world, pos, blockEntity.getTool(), ((ExperienceDroppingBlockInterface)experienceDroppingBlock).virtualAdditions$getExperienceDropped() );
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
