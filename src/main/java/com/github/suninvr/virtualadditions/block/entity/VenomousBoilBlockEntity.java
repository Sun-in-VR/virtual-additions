package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.VenomousBoilBlock;
import com.github.suninvr.virtualadditions.registry.VABlockEntities;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.GameEventTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;

public class VenomousBoilBlockEntity extends BlockEntity implements GameEventListener {
    private final BlockPositionSource positionSource;
    private int range;

    public VenomousBoilBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntities.VENOMOUS_BOIL, pos, state);
        this.positionSource = new BlockPositionSource(this.pos);
    }

    @Override
    public PositionSource getPositionSource() {
        return this.positionSource;
    }

    @Override
    public int getRange() {
        return 3;
        //if (this.world != null) return this.world.getBlockState(this.pos).get(VenomousBoilBlock.AGE) * 2;
        //return 0;
    }

    private boolean shouldListen() {
        if (this.world != null && this.world.getBlockState(this.pos).isOf(VABlocks.VENOMOUS_BOIL)) return this.world.getBlockState(this.pos).get(VenomousBoilBlock.AGE) > 0;
        return false;
    }

    @Override
    public boolean listen(ServerWorld world, GameEvent.Message event) {
        if (this.isRemoved()) {
            return false;
        } else {
            if (event.getEvent().isIn(GameEventTags.VIBRATIONS) && this.shouldListen()) {
                GameEvent.Emitter emitter = event.getEmitter();
                Entity heardEntity = emitter.sourceEntity();
                if (heardEntity instanceof LivingEntity livingEntity) {
                    if ((livingEntity.isSneaking() && event.getEvent().isIn(GameEventTags.IGNORE_VIBRATIONS_SNEAKING))) return false;
                    VenomousBoilBlock.explode(world, this.pos, world.getBlockState(this.pos));
                    return true;
                }
            }
        }
        return false;
    }
}
