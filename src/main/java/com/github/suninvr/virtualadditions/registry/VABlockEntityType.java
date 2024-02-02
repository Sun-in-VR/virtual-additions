package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class VABlockEntityType {
    public static final BlockEntityType<SpotlightBlockEntity> SPOTLIGHT;
    public static final BlockEntityType<WarpTetherBlockEntity> WARP_TETHER;
    public static final BlockEntityType<WarpAnchorBlockEntity> WARP_ANCHOR;
    public static final BlockEntityType<EntanglementDriveBlockEntity> ENTANGLEMENT_DRIVE;
    public static final BlockEntityType<DestructiveSculkBlockEntity> DESTRUCTIVE_SCULK;
    public static final BlockEntityType<WindBlockEntity> WIND;
    public static final BlockEntityType<ColoringStationBlockEntity> COLORING_STATION;
    public static final BlockEntityType<SignBlockEntity> CUSTOM_SIGN;
    public static final BlockEntityType<SignBlockEntity> CUSTOM_HANGING_SIGN;

    static {
        SPOTLIGHT = register("spotlight", SpotlightBlockEntity::new, VABlocks.SPOTLIGHT);
        WARP_TETHER = register("iolite_tether", WarpTetherBlockEntity::new, VABlocks.WARP_TETHER);
        WARP_ANCHOR = register("iolite_anchor", WarpAnchorBlockEntity::new, VABlocks.WARP_ANCHOR);
        ENTANGLEMENT_DRIVE = register("entanglement_drive", EntanglementDriveBlockEntity::new, VABlocks.ENTANGLEMENT_DRIVE);
        DESTRUCTIVE_SCULK = register("destructive_sculk", DestructiveSculkBlockEntity::new, VABlocks.DESTRUCTIVE_SCULK);
        WIND = register("wind", WindBlockEntity::new, VABlocks.WIND);
        COLORING_STATION = register("coloring_station", ColoringStationBlockEntity::new);
        CUSTOM_SIGN = register("custom_sign", CustomSignBlockEntity::new, VABlocks.AEROBLOOM_SIGN, VABlocks.AEROBLOOM_WALL_SIGN);
        CUSTOM_HANGING_SIGN = register("custom_hanging_sign", CustomHangingSignBlockEntity::new, VABlocks.AEROBLOOM_HANGING_SIGN, VABlocks.AEROBLOOM_WALL_HANGING_SIGN);
    }

    public static void init(){}

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, VirtualAdditions.idOf(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }
}