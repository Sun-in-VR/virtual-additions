package com.github.suninvr.virtualadditions.client.render.block;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.util.DyeColor;

import java.util.Optional;

public class MiniPortalBlockEntityRenderState extends BlockEntityRenderState {
    public Optional<DyeColor> dyeColor;
    public boolean blocked;
}
