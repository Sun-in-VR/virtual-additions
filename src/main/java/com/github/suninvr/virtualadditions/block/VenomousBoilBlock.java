package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.VenomousBoilBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class VenomousBoilBlock extends BlockWithEntity {
    public static final IntProperty AGE = IntProperty.of("age", 0, 3);
    protected static final VoxelShape SOCKET_SHAPE;
    protected static final VoxelShape AGE_1_SHAPE;
    protected static final VoxelShape AGE_2_SHAPE;
    protected static final VoxelShape AGE_3_SHAPE;

    public VenomousBoilBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(AGE, 0)
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AGE)) {
            case 1 -> AGE_1_SHAPE;
            case 2 -> AGE_2_SHAPE;
            case 3 -> AGE_3_SHAPE;
            default -> SOCKET_SHAPE;
        };
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos, Direction.UP);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if(!world.isClient && state.get(AGE) > 0 && !player.getAbilities().creativeMode && !(player.getMainHandStack().getItem() instanceof HoeItem)) { explode(world, pos, state); }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if(!world.isClient && state.get(AGE) > 0) {
            explode(world, pos, state);
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        int age = state.get(AGE);
        if(age < 3 && random.nextInt(2) == 0) {
            world.setBlockState(pos, state.with(AGE, age + 1));
        }
    }

    public static void explode(World world, BlockPos pos, BlockState state) {
        if(!world.isClient && state.isOf(VABlocks.VENOMOUS_BOIL) && state.get(AGE) > 0) {
            AreaEffectCloudEntity cloudEntity = new AreaEffectCloudEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            cloudEntity.setRadius(2.0F * state.get(AGE));
            cloudEntity.setRadiusOnUse(-1.0F);
            cloudEntity.setWaitTime(5);
            cloudEntity.setRadiusGrowth(-cloudEntity.getRadius() / cloudEntity.getDuration());
            cloudEntity.setColor(MathHelper.packRgb(200, 237, 56));
            cloudEntity.setPotion(Potions.STRONG_POISON);
            world.playSound(null, pos, SoundEvents.BLOCK_HONEY_BLOCK_BREAK, SoundCategory.BLOCKS, 1.0F, 0.5F);
            world.spawnEntity(cloudEntity);
            world.setBlockState(pos, state.with(AGE, 0));
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    static {
        SOCKET_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);
        AGE_1_SHAPE = VoxelShapes.union(SOCKET_SHAPE, Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D));
        AGE_2_SHAPE = VoxelShapes.union(SOCKET_SHAPE, Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 7.0D, 12.0D));
        AGE_3_SHAPE = VoxelShapes.union(SOCKET_SHAPE, Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VenomousBoilBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {return BlockRenderType.MODEL;}

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
        if (blockEntity instanceof VenomousBoilBlockEntity venomousBoilBlockEntity) return venomousBoilBlockEntity;
        return null;
    }
}
