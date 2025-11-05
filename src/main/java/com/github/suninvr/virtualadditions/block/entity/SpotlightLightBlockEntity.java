package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.SpotlightLightBlock;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VAGameEventTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("ClassEscapesDefinedScope")
public class SpotlightLightBlockEntity extends BlockEntity implements GameEventListener.Provider<SpotlightLightBlockEntity.Listener> {
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
    public void preRemoveSideEffects(BlockPos pos, BlockState oldState) {
        SpotlightLightBlock.updateSources(this.level, pos, oldState);
        super.preRemoveSideEffects(pos, oldState);
    }

    @Override
    public Listener getListener() {
        return this.listener;
    }

    protected class Listener implements GameEventListener {
        private final PositionSource source;

        public Listener(BlockPos pos) {
            this.source = new BlockPositionSource(pos);
        }

        @Override
        public PositionSource getListenerSource() {
            return this.source;
        }

        @Override
        public int getListenerRadius() {
            return 16;
        }

        @Override
        public boolean handleGameEvent(ServerLevel world, Holder<GameEvent> event, GameEvent.Context emitter, Vec3 emitterPos) {
            if (!event.is(VAGameEventTags.NOTIFIES_SPOTLIGHT)) return false;
            if (!SpotlightLightBlockEntity.this.canUpdate(world.getGameTime())) return false;
            Vec3 pos = this.getListenerSource().getPosition(world).get();
            BlockPos blockPos = BlockPos.containing(pos);
            SpotlightLightBlock.updateSources(world, blockPos, world.getBlockState(blockPos));
            return true;
        }
    }
}
