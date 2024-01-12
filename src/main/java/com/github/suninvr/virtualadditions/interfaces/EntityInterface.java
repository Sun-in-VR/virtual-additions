package com.github.suninvr.virtualadditions.interfaces;

import net.minecraft.util.math.Vec3d;

public interface EntityInterface {
    boolean virtualAdditions$isInAcid();
    
    void virtualAdditions$setInWind(boolean bl);

    void virtualAdditions$setWindVelocity(Vec3d vel);
}
