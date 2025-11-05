package com.github.suninvr.virtualadditions.client.sound;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

public class PlayerProjectionLoopSoundInstance extends AbstractTickableSoundInstance {
    private final PlayerProjectionEntity playerProjection;

    public PlayerProjectionLoopSoundInstance(PlayerProjectionEntity entity) {
        super(VASoundEvents.ITEM_SPECTRAL_SPYGLASS_AMBIENT, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.playerProjection = entity;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.25F;
        this.x = playerProjection.getX();
        this.y = playerProjection.getY();
        this.z = playerProjection.getZ();

    }

    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.playerProjection.isSilent();
    }

    @Override
    public void tick() {

        if (!this.playerProjection.isRemoved() && !this.playerProjection.isDeadOrDying()) {
            this.x = (float)this.playerProjection.getX();
            this.y = (float)this.playerProjection.getY();
            this.z = (float)this.playerProjection.getZ();
            if (this.playerProjection.isPhasingThroughWall()) {
                this.volume = 1.0F;
            } else {
                this.volume = 0.25F;
            }
            float f = Math.min((float) (0.5 + this.playerProjection.getDeltaMovement().length() * 2), 1);
            this.pitch = f;
        } else {
            this.stop();
        }
    }
}
