package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.CustomShulkerBoxBlockEntity;
import com.github.suninvr.virtualadditions.block.enums.ExtendedDyeColor;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAItems;
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
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class CustomShulkerBoxBlock extends ShulkerBoxBlock {
    private final ExtendedDyeColor color;

    public CustomShulkerBoxBlock(ExtendedDyeColor color, Settings settings) {
        super(null, settings);
        this.color = color;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CustomShulkerBoxBlockEntity(this.color, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return CustomShulkerBoxBlock.validateTicker(type, VABlockEntityType.CUSTOM_SHULKER_BOX, CustomShulkerBoxBlockEntity::tick);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CustomShulkerBoxBlockEntity shulkerBoxBlockEntity) {
            if (!world.isClient && player.isCreative() && !shulkerBoxBlockEntity.isEmpty()) {
                ItemStack itemStack = CustomShulkerBoxBlock.getItemStack(this.getExtendedDyeColor());
                itemStack.copyComponentsFrom(blockEntity.createComponentMap());
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            } else {
                shulkerBoxBlockEntity.generateLoot(player);
            }
        }
        return this.finishBreak(world, pos, state, player);
    }

    public BlockState finishBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        this.spawnBreakParticles(world, player, pos, state);
        if (state.isIn(BlockTags.GUARDED_BY_PIGLINS)) {
            PiglinBrain.onGuardedBlockInteracted(player, false);
        }
        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(player, state));
        return state;
    }

    public static ItemStack getItemStack(ExtendedDyeColor color) {
        return new ItemStack(CustomShulkerBoxBlock.get(color));
    }

    public static Block get(ExtendedDyeColor color) {
        return switch (color) {
            case CHARTREUSE -> VABlocks.CHARTREUSE_SHULKER_BOX;
            case MAROON -> VABlocks.MAROON_SHULKER_BOX;
            case INDIGO -> VABlocks.INDIGO_SHULKER_BOX;
            case PLUM -> VABlocks.PLUM_SHULKER_BOX;
            case VIRIDIAN -> VABlocks.VIRIDIAN_SHULKER_BOX;
            case TAN -> VABlocks.TAN_SHULKER_BOX;
            case SINOPIA -> VABlocks.SINOPIA_SHULKER_BOX;
            case LILAC -> VABlocks.LILAC_SHULKER_BOX;
        };
    }

    public ExtendedDyeColor getExtendedDyeColor() {
        return this.color;
    }
}
