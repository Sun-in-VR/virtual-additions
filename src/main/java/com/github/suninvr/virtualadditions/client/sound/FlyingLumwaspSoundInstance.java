package com.github.suninvr.virtualadditions.client.sound;

import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public class FlyingLumwaspSoundInstance extends AbstractTickableSoundInstance {
    private final LumwaspEntity lumwasp;
    public FlyingLumwaspSoundInstance(LumwaspEntity entity) {
        super(SoundEvents.BEE_LOOP, SoundSource.HOSTILE, SoundInstance.createUnseededRandom());
        this.lumwasp = entity;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0F;
        this.x = lumwasp.getX();
        this.y = lumwasp.getY();
        this.z = lumwasp.getZ();

    }

    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.lumwasp.isSilent();
    }

    @Override
    public void tick() {

        if (!this.lumwasp.isRemoved() && !this.lumwasp.isDeadOrDying()) {
            if (this.lumwasp.isFlying()) {
                this.x = (float)this.lumwasp.getX();
                this.y = (float)this.lumwasp.getY();
                this.z = (float)this.lumwasp.getZ();
                float f = (float)this.lumwasp.getDeltaMovement().horizontalDistance();
                if (f >= 0.01F) {
                    this.pitch = Mth.lerp(Mth.clamp(f, 0.6F, 1.0F), 0.6F, 1.0F);
                    this.volume = Mth.lerp(Mth.clamp(f, 0.0F, 0.5F), 0F, 3.6F);
                }
            } else {
                this.volume = 0.0F;
            }

        } else {
            this.stop();
        }
    }
}
