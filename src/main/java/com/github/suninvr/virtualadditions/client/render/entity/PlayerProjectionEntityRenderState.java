package com.github.suninvr.virtualadditions.client.render.entity;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.player.SkinTextures;

public class PlayerProjectionEntityRenderState extends LivingEntityRenderState {
    public SkinTextures skinTextures = DefaultSkinHelper.getSteve();
    public boolean hatVisible = false;
}
