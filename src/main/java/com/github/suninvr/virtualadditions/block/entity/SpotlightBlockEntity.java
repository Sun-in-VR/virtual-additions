package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.SpotlightBlock;
import com.github.suninvr.virtualadditions.block.enums.LightStatus;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class SpotlightBlockEntity extends BlockEntity implements GameEventListener.Provider<SpotlightBlockEntity.Listener> {
    private BlockPos lightPos;
    private final Listener listener;
    private long lastUpdated;

    public SpotlightBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.SPOTLIGHT, pos, state);
        this.lightPos = pos;
        this.listener = new Listener(pos);
        this.lastUpdated = -1;
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        view.store("light_pos", BlockPos.CODEC, this.lightPos);
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        view.read("light_pos", BlockPos.CODEC).ifPresent(pos -> this.lightPos = pos);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState oldState) {
        SpotlightBlock.setLightState(this.level, pos, oldState, LightStatus.NONE);
        super.preRemoveSideEffects(pos, oldState);
    }

    private boolean canUpdate(long time) {
        if (this.level == null) return false;
        boolean bl = (time != this.lastUpdated);
        if (bl) this.lastUpdated = time;
        BlockState state = this.level.getBlockState(this.worldPosition);
        return bl && state.is(VABlocks.SPOTLIGHT) && state.getValue(SpotlightBlock.POWERED);
    }

    public BlockPos getLightLocation() {
        return this.lightPos;
    }

    public void setLightLocation(BlockPos pos) {
        this.lightPos = pos;
        this.setChanged();
    }

    @SuppressWarnings("ClassEscapesDefinedScope")
    @Override
    public Listener getListener() {
        return this.listener;
    }

    protected class Listener implements GameEventListener {
        private final PositionSource positionSource;

        public Listener(BlockPos pos) {
            this.positionSource = new BlockPositionSource(pos);
        }

        @Override
        public PositionSource getListenerSource() {
            return this.positionSource;
        }

        @Override
        public int getListenerRadius() {
            return 16;
        }

        @Override
        public boolean handleGameEvent(ServerLevel world, Holder<GameEvent> event, GameEvent.Context emitter, Vec3 emitterPos) {
            if (!event.is(VAGameEventTags.NOTIFIES_SPOTLIGHT)) return false;
            if (!SpotlightBlockEntity.this.canUpdate(world.getGameTime())) return false;
            Vec3 pos = this.getListenerSource().getPosition(world).get();
            world.scheduleTick(BlockPos.containing(pos), VABlocks.SPOTLIGHT, 1);
            return true;
        }
    }
}
