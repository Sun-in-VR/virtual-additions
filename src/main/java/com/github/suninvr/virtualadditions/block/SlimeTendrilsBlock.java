package com.github.suninvr.virtualadditions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.StructureWorldAccess;
import org.jetbrains.annotations.Nullable;

public class SlimeTendrilsBlock extends Block {
    public static final BooleanProperty SLIMY = BooleanProperty.of("slimy");

    public SlimeTendrilsBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(SLIMY, false)
        );
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        ChunkPos chunkPos = new ChunkPos(ctx.getBlockPos());
        if(ctx.getWorld() instanceof ServerWorld) {
            boolean bl = ChunkRandom.getSlimeRandom(chunkPos.x, chunkPos.z, ((ServerWorld)ctx.getWorld()).getSeed(), 987234911L).nextInt(10) == 0;
            return this.getDefaultState().with(SLIMY, bl);
        }
        return super.getPlacementState(ctx);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SLIMY);
    }
}
