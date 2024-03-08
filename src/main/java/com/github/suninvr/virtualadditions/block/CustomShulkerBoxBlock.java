package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.CustomShulkerBoxBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VADyeColors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CustomShulkerBoxBlock extends ShulkerBoxBlock {
    private final DyeColor color;

    public CustomShulkerBoxBlock(DyeColor color, Settings settings) {
        super(null, settings);
        this.color = color;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CustomShulkerBoxBlockEntity(this.color, pos, state);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity) {
            if (!world.isClient && player.isCreative() && !shulkerBoxBlockEntity.isEmpty()) {
                ItemStack itemStack = getItemStack(this.color);
                itemStack.applyComponentsFrom(blockEntity.createComponentMap());
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            } else {
                shulkerBoxBlockEntity.generateLoot(player);
            }
        }
        return this.finishBreak(world, pos, state, player);
    }

    private BlockState finishBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        this.spawnBreakParticles(world, player, pos, state);
        if (state.isIn(BlockTags.GUARDED_BY_PIGLINS)) {
            PiglinBrain.onGuardedBlockInteracted(player, false);
        }
        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(player, state));
        return state;
    }

    public static Block get(@Nullable DyeColor dyeColor) {
        if (dyeColor == null) return ShulkerBoxBlock.get(null);
        if (dyeColor.equals(VADyeColors.CHARTREUSE)) return VABlocks.CHARTREUSE_SHULKER_BOX;
        if (dyeColor.equals(VADyeColors.MAROON)) return VABlocks.MAROON_SHULKER_BOX;
        if (dyeColor.equals(VADyeColors.INDIGO)) return VABlocks.INDIGO_SHULKER_BOX;
        if (dyeColor.equals(VADyeColors.PLUM)) return VABlocks.PLUM_SHULKER_BOX;
        if (dyeColor.equals(VADyeColors.VIRIDIAN)) return VABlocks.VIRIDIAN_SHULKER_BOX;
        if (dyeColor.equals(VADyeColors.TAN)) return VABlocks.TAN_SHULKER_BOX;
        if (dyeColor.equals(VADyeColors.SINOPIA)) return VABlocks.SINOPIA_SHULKER_BOX;
        if (dyeColor.equals(VADyeColors.LILAC)) return VABlocks.LILAC_SHULKER_BOX;
        return ShulkerBoxBlock.get(dyeColor);
    }

    public static ItemStack getItemStack(@Nullable DyeColor color) {
        return new ItemStack(get(color));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return CustomShulkerBoxBlock.validateTicker(type, VABlockEntityType.CUSTOM_SHULKER_BOX, CustomShulkerBoxBlockEntity::tick);
    }
}
