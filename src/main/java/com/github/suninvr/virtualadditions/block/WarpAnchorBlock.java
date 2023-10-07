package com.github.suninvr.virtualadditions.block;

import com.github.suninvr.virtualadditions.block.entity.WarpAnchorBlockEntity;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class WarpAnchorBlock extends BlockWithEntity implements Waterloggable {
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.VERTICAL_DIRECTION;
    private static final VoxelShape HITBOX;
    private static final VoxelShape HITBOX_CEILING;

    public WarpAnchorBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(POWERED, false)
                .with(WATERLOGGED, false)
                .with(FACING, Direction.UP)
        );
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED).add(WATERLOGGED).add(FACING);
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(VAItems.WARP_TETHER)) {
            stack.getOrCreateNbt().put("destination", NbtHelper.fromBlockPos(pos));
            world.playSound(null, pos, VASoundEvents.BLOCK_WARP_ANCHOR_USE, SoundCategory.BLOCKS, 1.0F, 0.6F);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        if (!world.isClient) {
            if(world.isReceivingRedstonePower(pos)) world.setBlockState(pos, state.with(POWERED, true));
            else world.setBlockState(pos, state.with(POWERED, false));
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return state.get(FACING).equals(Direction.DOWN) ? HITBOX_CEILING : HITBOX;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState()
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER)
                .with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()))
                .with(FACING, ctx.getVerticalPlayerLookDirection().getOpposite());
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("block.virtual_additions.warp_anchor.desc1").formatted(Formatting.GRAY));
        tooltip.add(ScreenTexts.space().append(Text.translatable("block.virtual_additions.warp_anchor.desc2").formatted(Formatting.BLUE)));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WarpAnchorBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {return BlockRenderType.MODEL;}

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {return validateTicker(type, VABlockEntities.IOLITE_ANCHOR_BLOCK_ENTITY, WarpAnchorBlockEntity::tick);}

    static {
        HITBOX = VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.375, 1f);
        HITBOX_CEILING = VoxelShapes.cuboid(0f, 0.625f, 0f, 1f, 1f, 1f);
    }
}
