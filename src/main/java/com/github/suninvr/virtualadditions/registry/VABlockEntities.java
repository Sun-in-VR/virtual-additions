package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class VABlockEntities {
    public static final BlockEntityType<SpotlightBlockEntity> SPOTLIGHT_BLOCK_ENTITY;
    public static final BlockEntityType<WarpTetherBlockEntity> IOLITE_TETHER_BLOCK_ENTITY;
    public static final BlockEntityType<WarpAnchorBlockEntity> IOLITE_ANCHOR_BLOCK_ENTITY;
    public static final BlockEntityType<EntanglementDriveBlockEntity> ENTANGLEMENT_DRIVE_BLOCK_ENTITY;
    public static final BlockEntityType<DestructiveSculkBlockEntity> DESTRUCTIVE_SCULK_BLOCK_ENTITY;
    public static final BlockEntityType<SignBlockEntity> CUSTOM_SIGN_BLOCK_ENTITY;
    public static final BlockEntityType<SignBlockEntity> CUSTOM_HANGING_SIGN_BLOCK_ENTITY;

    static {
        SPOTLIGHT_BLOCK_ENTITY = register("spotlight", SpotlightBlockEntity::new, VABlocks.SPOTLIGHT);
        IOLITE_TETHER_BLOCK_ENTITY = register("iolite_tether", WarpTetherBlockEntity::new, VABlocks.WARP_TETHER);
        IOLITE_ANCHOR_BLOCK_ENTITY = register("iolite_anchor", WarpAnchorBlockEntity::new, VABlocks.WARP_ANCHOR);
        ENTANGLEMENT_DRIVE_BLOCK_ENTITY = register("entanglement_drive", EntanglementDriveBlockEntity::new, VABlocks.ENTANGLEMENT_DRIVE);
        DESTRUCTIVE_SCULK_BLOCK_ENTITY = register("destructive_sculk", DestructiveSculkBlockEntity::new, VABlocks.DESTRUCTIVE_SCULK);
        CUSTOM_SIGN_BLOCK_ENTITY = register("custom_sign", CustomSignBlockEntity::new, VABlocks.AEROBLOOM_SIGN, VABlocks.AEROBLOOM_WALL_SIGN);
        CUSTOM_HANGING_SIGN_BLOCK_ENTITY = register("custom_hanging_sign", CustomHangingSignBlockEntity::new, VABlocks.AEROBLOOM_HANGING_SIGN, VABlocks.AEROBLOOM_WALL_HANGING_SIGN);
    }

    public static void init(){}

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, VirtualAdditions.idOf(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }
}
