package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.SpotlightBlock;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAGameEventTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;

public class SpotlightBlockEntity extends BlockEntity implements GameEventListener.Holder<SpotlightBlockEntity.Listener> {
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
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.writeNbt(nbt, lookup);
        nbt.put("light_pos", NbtHelper.fromBlockPos(this.lightPos));
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.readNbt(nbt, lookup);
        NbtHelper.toBlockPos(nbt, "light_pos").ifPresent(pos -> this.lightPos = pos);
    }

    private boolean canUpdate(long time) {
        if (this.world == null) return false;
        boolean bl = (time != this.lastUpdated);
        if (bl) this.lastUpdated = time;
        BlockState state = this.world.getBlockState(this.pos);
        return bl && state.isOf(VABlocks.SPOTLIGHT) && state.get(SpotlightBlock.POWERED);
    }

    public BlockPos getLightLocation() {
        return this.lightPos;
    }

    public void setLightLocation(BlockPos pos) {
        this.lightPos = pos;
        this.markDirty();
    }

    @SuppressWarnings("ClassEscapesDefinedScope")
    @Override
    public Listener getEventListener() {
        return this.listener;
    }

    protected class Listener implements GameEventListener {
        private final PositionSource positionSource;

        public Listener(BlockPos pos) {
            this.positionSource = new BlockPositionSource(pos);
        }

        @Override
        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public int getRange() {
            return 16;
        }

        @Override
        public boolean listen(ServerWorld world, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter, Vec3d emitterPos) {
            if (!event.isIn(VAGameEventTags.NOTIFIES_SPOTLIGHT)) return false;
            if (!SpotlightBlockEntity.this.canUpdate(world.getTime())) return false;
            Vec3d pos = this.getPositionSource().getPos(world).get();
            world.scheduleBlockTick(BlockPos.ofFloored(pos), VABlocks.SPOTLIGHT, 1);
            return true;
        }
    }
}
