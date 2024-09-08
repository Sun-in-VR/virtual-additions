package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.EntanglementDriveBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.screen.EntanglementDriveScreenHandler;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class EntanglementDriveBlock extends BlockWithEntity implements InventoryProvider {
    public static final MapCodec<EntanglementDriveBlock> CODEC = createCodec(EntanglementDriveBlock::new);
    public static final BooleanProperty POWERED = Properties.POWERED;
    private static final Text TITLE;
    private static final VoxelShape SHAPE;

    public EntanglementDriveBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState()
                .with(POWERED, false)
        );
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (!world.isClient) {
            if(world.isReceivingRedstonePower(pos)) world.setBlockState(pos, state.with(POWERED, true));
            else world.setBlockState(pos, state.with(POWERED, false));
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = this.getDefaultState();
        if (ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos())) state = state.with(POWERED, true);
        return state;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EntanglementDriveBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            EntanglementDriveBlockEntity entity = world.getBlockEntity(pos) instanceof EntanglementDriveBlockEntity entanglementDriveBlockEntity ? entanglementDriveBlockEntity : null;
            if (entity == null) return ActionResult.SUCCESS;
            player.openHandledScreen(entity);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput( world.getBlockEntity(pos) );
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null ? blockEntity instanceof EntanglementDriveBlockEntity entanglementDriveBlockEntity ? entanglementDriveBlockEntity.getInventory() : null : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, VABlockEntityType.ENTANGLEMENT_DRIVE, (EntanglementDriveBlockEntity::tick));
    }

    @Nullable
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(((syncId, inv, player) -> new EntanglementDriveScreenHandler(syncId, inv, ScreenHandlerContext.create(world, pos), new ArrayPropertyDelegate(2))), TITLE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    static {
        TITLE = Text.translatable("container.virtual_additions.entanglement_drive");
        SHAPE = VoxelShapes.union(
                Block.createCuboidShape(2, 0,2, 14, 16, 14),
                Block.createCuboidShape(1, 3,1, 15, 13, 15)
        );
    }

}
