package com.github.suninvr.virtualadditions.interfaces;

import com.github.suninvr.virtualadditions.entity.PlayerProjectionEntity;

public interface PlayerEntityInterface {

    void virtualAdditions$setProjectionEntity(PlayerProjectionEntity projection);

    PlayerProjectionEntity virtualAdditions$getProjectionEntity();

    boolean virtualAdditions$hasProjectionEntity();

    void virtualAdditions$onHalberdSwing();

    long virtualAdditions$lastSwungHalberd();
}
