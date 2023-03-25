package com.github.suninvr.virtualadditions.client.sound;

import com.github.suninvr.virtualadditions.entity.LumwaspEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class FlyingLumwaspSoundInstance extends MovingSoundInstance {
    private final LumwaspEntity lumwasp;
    public FlyingLumwaspSoundInstance(LumwaspEntity entity) {
        super(SoundEvents.ENTITY_BEE_LOOP, SoundCategory.HOSTILE, SoundInstance.createRandom());
        this.lumwasp = entity;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.0F;
        this.x = lumwasp.getX();
        this.y = lumwasp.getY();
        this.z = lumwasp.getZ();

    }

    public boolean shouldAlwaysPlay() {
        return true;
    }

    @Override
    public boolean canPlay() {
        return !this.lumwasp.isSilent();
    }

    @Override
    public void tick() {

        if (!this.lumwasp.isRemoved()) {
            if (this.lumwasp.isInAir()) {
                this.x = (float)this.lumwasp.getX();
                this.y = (float)this.lumwasp.getY();
                this.z = (float)this.lumwasp.getZ();
                float f = (float)this.lumwasp.getVelocity().horizontalLength();
                if (f >= 0.01F) {
                    this.pitch = MathHelper.lerp(MathHelper.clamp(f, 0.6F, 1.0F), 0.6F, 1.0F);
                    this.volume = MathHelper.lerp(MathHelper.clamp(f, 0.0F, 0.5F), 0F, 3.6F);
                }
            } else {
                this.volume = 0.0F;
            }

        } else {
            this.setDone();
        }
    }
}
