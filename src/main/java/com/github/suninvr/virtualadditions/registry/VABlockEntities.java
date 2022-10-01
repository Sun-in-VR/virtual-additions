package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import com.github.suninvr.virtualadditions.VirtualAdditions;

public class VABlockEntities {
    public static BlockEntityType<IoliteTetherBlockEntity> IOLITE_TETHER_BLOCK_ENTITY;
    public static BlockEntityType<IoliteAnchorBlockEntity> IOLITE_ANCHOR_BLOCK_ENTITY;
    public static BlockEntityType<SpotlightBlockEntity> SPOTLIGHT_BLOCK_ENTITY;
    public static BlockEntityType<EntanglementDriveBlockEntity> ENTANGLEMENT_DRIVE_BLOCK_ENTITY;
    public static BlockEntityType<VenomousBoilBlockEntity> VENOMOUS_BOIL;

    public static void init(){

        IOLITE_TETHER_BLOCK_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                VirtualAdditions.idOf("iolite_tether"),
                FabricBlockEntityTypeBuilder.create(IoliteTetherBlockEntity::new, VABlocks.IOLITE_TETHER).build(null));

        IOLITE_ANCHOR_BLOCK_ENTITY = register("iolite_anchor", IoliteAnchorBlockEntity::new, VABlocks.IOLITE_ANCHOR);

        SPOTLIGHT_BLOCK_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                VirtualAdditions.idOf("spotlight"),
                FabricBlockEntityTypeBuilder.create(SpotlightBlockEntity::new, VABlocks.SPOTLIGHT).build(null));

        ENTANGLEMENT_DRIVE_BLOCK_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                VirtualAdditions.idOf("entanglement_drive"),
                FabricBlockEntityTypeBuilder.create(EntanglementDriveBlockEntity::new, VABlocks.ENTANGLEMENT_DRIVE).build(null));

        VENOMOUS_BOIL = register("venomous_boil", VenomousBoilBlockEntity::new, VABlocks.VENOMOUS_BOIL);
    }


    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, VirtualAdditions.idOf(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }
}
