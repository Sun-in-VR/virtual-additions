package com.github.suninvr.virtualadditions.client.render.block;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.item.DyeColor;

import java.util.Optional;

public class MiniPortalBlockEntityRenderState extends BlockEntityRenderState {
    public Optional<DyeColor> dyeColor;
    public boolean blocked;
}
