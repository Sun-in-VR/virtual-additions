package com.github.suninvr.virtualadditions.interfaces;

import net.minecraft.util.math.Vec3d;

public interface EntityInterface {
    boolean virtualAdditions$isInAcid();

    boolean virtualAdditions$hasUsedMiniPortalThisTick();

    void virtualAdditions$setUsedMiniPortalThisTick(boolean bl);
}
