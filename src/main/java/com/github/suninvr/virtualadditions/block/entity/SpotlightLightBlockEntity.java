package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.SpotlightLightBlock;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VAGameEventTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;

@SuppressWarnings("ClassEscapesDefinedScope")
public class SpotlightLightBlockEntity extends BlockEntity implements GameEventListener.Holder<SpotlightLightBlockEntity.Listener> {
    private final Listener listener;
    private long lastUpdated;

    public SpotlightLightBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.SPOTLIGHT_LIGHT, pos, state);
        this.listener = new SpotlightLightBlockEntity.Listener(pos);
        this.lastUpdated = -1;
    }

    private boolean canUpdate(long time) {
        boolean bl = (time != this.lastUpdated);
        if (bl) this.lastUpdated = time;
        return bl;
    }

    @Override
    public Listener getEventListener() {
        return this.listener;
    }

    protected class Listener implements GameEventListener {
        private final PositionSource source;

        public Listener(BlockPos pos) {
            this.source = new BlockPositionSource(pos);
        }

        @Override
        public PositionSource getPositionSource() {
            return this.source;
        }

        @Override
        public int getRange() {
            return 16;
        }

        @Override
        public boolean listen(ServerWorld world, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter, Vec3d emitterPos) {
            if (!event.isIn(VAGameEventTags.NOTIFIES_SPOTLIGHT)) return false;
            if (!SpotlightLightBlockEntity.this.canUpdate(world.getTime())) return false;
            Vec3d pos = this.getPositionSource().getPos(world).get();
            BlockPos blockPos = BlockPos.ofFloored(pos);
            SpotlightLightBlock.updateSources(world, blockPos, world.getBlockState(blockPos));
            return true;
        }
    }
}
