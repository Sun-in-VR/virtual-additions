package com.github.suninvr.virtualadditions.client.sound;

import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class PlayerProjectionLoopSoundInstance extends MovingSoundInstance {
    private final PlayerProjectionEntity playerProjection;

    public PlayerProjectionLoopSoundInstance(PlayerProjectionEntity entity) {
        super(VASoundEvents.ITEM_SPECTRAL_SPYGLASS_AMBIENT, SoundCategory.PLAYERS, SoundInstance.createRandom());
        this.playerProjection = entity;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.25F;
        this.x = playerProjection.getX();
        this.y = playerProjection.getY();
        this.z = playerProjection.getZ();

    }

    public boolean shouldAlwaysPlay() {
        return true;
    }

    @Override
    public boolean canPlay() {
        return !this.playerProjection.isSilent();
    }

    @Override
    public void tick() {

        if (!this.playerProjection.isRemoved() && !this.playerProjection.isDead()) {
            this.x = (float)this.playerProjection.getX();
            this.y = (float)this.playerProjection.getY();
            this.z = (float)this.playerProjection.getZ();
            if (this.playerProjection.isPhasingThroughWall()) {
                this.volume = 1.0F;
            } else {
                this.volume = 0.25F;
            }
            float f = Math.min((float) (0.5 + this.playerProjection.getVelocity().length() * 2), 1);
            this.pitch = f;
        } else {
            this.setDone();
        }
    }
}
