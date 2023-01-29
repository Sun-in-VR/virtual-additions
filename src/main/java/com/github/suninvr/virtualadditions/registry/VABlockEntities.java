package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.EntanglementDriveBlockEntity;
import com.github.suninvr.virtualadditions.block.entity.WarpAnchorBlockEntity;
import com.github.suninvr.virtualadditions.block.entity.WarpTetherBlockEntity;
import com.github.suninvr.virtualadditions.block.entity.SpotlightBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class VABlockEntities {
    public static BlockEntityType<SpotlightBlockEntity> SPOTLIGHT_BLOCK_ENTITY;
    public static BlockEntityType<WarpTetherBlockEntity> IOLITE_TETHER_BLOCK_ENTITY;
    public static BlockEntityType<WarpAnchorBlockEntity> IOLITE_ANCHOR_BLOCK_ENTITY;
    public static BlockEntityType<EntanglementDriveBlockEntity> ENTANGLEMENT_DRIVE_BLOCK_ENTITY;

    public static void init(){
        SPOTLIGHT_BLOCK_ENTITY = register("spotlight", SpotlightBlockEntity::new, VABlocks.SPOTLIGHT);
        IOLITE_TETHER_BLOCK_ENTITY = register("iolite_tether", WarpTetherBlockEntity::new, VABlocks.WARP_TETHER);
        IOLITE_ANCHOR_BLOCK_ENTITY = register("iolite_anchor", WarpAnchorBlockEntity::new, VABlocks.WARP_ANCHOR);
        ENTANGLEMENT_DRIVE_BLOCK_ENTITY = register("entanglement_drive", EntanglementDriveBlockEntity::new, VABlocks.ENTANGLEMENT_DRIVE);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, VirtualAdditions.idOf(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }
}
