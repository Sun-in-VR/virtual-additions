package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.EntanglementDriveBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveMenu;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class EntanglementDriveBlock extends BaseEntityBlock implements WorldlyContainerHolder {
    public static final MapCodec<EntanglementDriveBlock> CODEC = simpleCodec(EntanglementDriveBlock::new);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final Component TITLE;
    private static final VoxelShape SHAPE;

    public EntanglementDriveBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any()
                .setValue(POWERED, false)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (!world.isClientSide()) {
            if(world.hasNeighborSignal(pos)) world.setBlockAndUpdate(pos, state.setValue(POWERED, true));
            else world.setBlockAndUpdate(pos, state.setValue(POWERED, false));
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState state = this.defaultBlockState();
        if (ctx.getLevel().hasNeighborSignal(ctx.getClickedPos())) state = state.setValue(POWERED, true);
        return state;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EntanglementDriveBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide()) {
            EntanglementDriveBlockEntity entity = world.getBlockEntity(pos) instanceof EntanglementDriveBlockEntity entanglementDriveBlockEntity ? entanglementDriveBlockEntity : null;
            if (entity == null) return InteractionResult.SUCCESS;
            player.openMenu(entity);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos, Direction direction) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity( world.getBlockEntity(pos) );
    }

    @Override
    public WorldlyContainer getContainer(BlockState state, LevelAccessor world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null ? blockEntity instanceof EntanglementDriveBlockEntity entanglementDriveBlockEntity ? entanglementDriveBlockEntity.getInventory() : null : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, VABlockEntityType.ENTANGLEMENT_DRIVE, (EntanglementDriveBlockEntity::tick));
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider(((syncId, inv, player) -> new EntanglementDriveMenu(syncId, inv, ContainerLevelAccess.create(world, pos), new SimpleContainerData(2))), TITLE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    static {
        TITLE = Component.translatable("container.virtual_additions.entanglement_drive");
        SHAPE = Shapes.or(
                Block.box(2, 0,2, 14, 16, 14),
                Block.box(1, 3,1, 15, 13, 15)
        );
    }

}
