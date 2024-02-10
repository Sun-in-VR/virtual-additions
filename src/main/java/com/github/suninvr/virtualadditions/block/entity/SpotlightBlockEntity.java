package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.SpotlightBlock;
import com.github.suninvr.virtualadditions.block.SpotlightLightBlock;
import com.github.suninvr.virtualadditions.block.enums.LightStatus;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAGameEventTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.GameEventTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;

public class SpotlightBlockEntity extends BlockEntity implements GameEventListener.Holder<SpotlightBlockEntity.Listener> {
    private BlockPos lightPos;
    private final Listener listener;

    public SpotlightBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.SPOTLIGHT, pos, state);
        this.lightPos = pos;
        this.listener = new Listener(pos);
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

    public BlockPos getLightLocation() {
        return this.lightPos;
    }

    public void setLightLocation(BlockPos pos) {
        this.lightPos = pos;
        this.markDirty();
    }

    @Override
    public Listener getEventListener() {
        return this.listener;
    }

    protected static class Listener implements GameEventListener {
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
            Vec3d pos = this.getPositionSource().getPos(world).get();
            BlockPos blockPos = new BlockPos((int)Math.floor(pos.x), (int)Math.floor(pos.y), (int)Math.floor(pos.z));
            BlockState state = world.getBlockState(blockPos);
            if (!state.isOf(VABlocks.SPOTLIGHT) || !state.get(SpotlightBlock.POWERED)) return false;
            boolean bl = posIsInDirection(pos, emitterPos, world.getBlockState(blockPos).get(Properties.ORIENTATION).getFacing());
            if (bl) {
                world.scheduleBlockTick(new BlockPos((int)Math.floor(pos.x), (int)Math.floor(pos.y), (int)Math.floor(pos.z)), VABlocks.SPOTLIGHT, 1);
            }
            return bl;
        }

        private static boolean posIsInDirection(Vec3d from, Vec3d to, Direction dir) {
            return switch (dir) {
                case UP -> from.getX() == to.getX() && from.getY() < to.getY() && from.getZ() == to.getZ();
                case DOWN -> from.getX() == to.getX() && from.getY() > to.getY() && from.getZ() == to.getZ();
                case EAST -> from.getX() < to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ();
                case WEST -> from.getX() > to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ();
                case NORTH -> from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() > to.getZ();
                case SOUTH -> from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() < to.getZ();
            };
        }
    }
}
