package com.github.suninvr.virtualadditions.client.render.entity;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.world.entity.player.PlayerSkin;

public class PlayerProjectionEntityRenderState extends LivingEntityRenderState {
    public PlayerSkin skinTextures = DefaultPlayerSkin.getDefaultSkin();
    public boolean hatVisible = false;
}
