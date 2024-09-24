package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class VABlockEntityType {
    public static final BlockEntityType<SpotlightBlockEntity> SPOTLIGHT;
    public static final BlockEntityType<SpotlightLightBlockEntity> SPOTLIGHT_LIGHT;
    public static final BlockEntityType<WarpTetherBlockEntity> WARP_TETHER;
    public static final BlockEntityType<WarpAnchorBlockEntity> WARP_ANCHOR;
    public static final BlockEntityType<EntanglementDriveBlockEntity> ENTANGLEMENT_DRIVE;
    public static final BlockEntityType<DestructiveSculkBlockEntity> DESTRUCTIVE_SCULK;
    public static final BlockEntityType<WindBlockEntity> WIND;
    public static final BlockEntityType<ColoringStationBlockEntity> COLORING_STATION;
    public static final BlockEntityType<SignBlockEntity> CUSTOM_SIGN;
    public static final BlockEntityType<SignBlockEntity> CUSTOM_HANGING_SIGN;
    public static final BlockEntityType<CustomBedBlockEntity> CUSTOM_BED;
    public static final BlockEntityType<ShulkerBoxBlockEntity> CUSTOM_SHULKER_BOX;
    public static final BlockEntityType<BannerBlockEntity> CUSTOM_BANNER;

    static {
        SPOTLIGHT = register("spotlight", SpotlightBlockEntity::new, VABlocks.SPOTLIGHT);
        SPOTLIGHT_LIGHT = register("spotlight_light", SpotlightLightBlockEntity::new, VABlocks.SPOTLIGHT_LIGHT);
        WARP_TETHER = register("iolite_tether", WarpTetherBlockEntity::new, VABlocks.WARP_TETHER);
        WARP_ANCHOR = register("iolite_anchor", WarpAnchorBlockEntity::new, VABlocks.WARP_ANCHOR);
        ENTANGLEMENT_DRIVE = register("entanglement_drive", EntanglementDriveBlockEntity::new, VABlocks.ENTANGLEMENT_DRIVE);
        DESTRUCTIVE_SCULK = register("destructive_sculk", DestructiveSculkBlockEntity::new, VABlocks.DESTRUCTIVE_SCULK);
        WIND = register("wind", WindBlockEntity::new, VABlocks.WIND);
        COLORING_STATION = register("coloring_station", ColoringStationBlockEntity::new, VABlocks.COLORING_STATION);
        CUSTOM_SIGN = register("custom_sign", CustomSignBlockEntity::new, VABlocks.AEROBLOOM_SIGN, VABlocks.AEROBLOOM_WALL_SIGN);
        CUSTOM_HANGING_SIGN = register("custom_hanging_sign", CustomHangingSignBlockEntity::new, VABlocks.AEROBLOOM_HANGING_SIGN, VABlocks.AEROBLOOM_WALL_HANGING_SIGN);
        CUSTOM_BED = register("custom_bed", CustomBedBlockEntity::new, VABlocks.CHARTREUSE_BED, VABlocks.MAROON_BED, VABlocks.INDIGO_BED, VABlocks.PLUM_BED, VABlocks.VIRIDIAN_BED, VABlocks.TAN_BED, VABlocks.SINOPIA_BED, VABlocks.LILAC_BED);
        CUSTOM_SHULKER_BOX = register("shulker_box", CustomShulkerBoxBlockEntity::new, VABlocks.CHARTREUSE_SHULKER_BOX, VABlocks.MAROON_SHULKER_BOX, VABlocks.INDIGO_SHULKER_BOX, VABlocks.PLUM_SHULKER_BOX, VABlocks.VIRIDIAN_SHULKER_BOX, VABlocks.TAN_SHULKER_BOX, VABlocks.SINOPIA_SHULKER_BOX, VABlocks.LILAC_SHULKER_BOX);
        CUSTOM_BANNER = register("banner", CustomBannerBlockEntity::new,
                VABlocks.CHARTREUSE_BANNER,
                VABlocks.CHARTREUSE_WALL_BANNER,
                VABlocks.MAROON_BANNER,
                VABlocks.MAROON_WALL_BANNER,
                VABlocks.INDIGO_BANNER,
                VABlocks.INDIGO_WALL_BANNER,
                VABlocks.PLUM_BANNER,
                VABlocks.PLUM_WALL_BANNER,
                VABlocks.VIRIDIAN_BANNER,
                VABlocks.VIRIDIAN_WALL_BANNER,
                VABlocks.TAN_BANNER,
                VABlocks.TAN_WALL_BANNER,
                VABlocks.SINOPIA_BANNER,
                VABlocks.SINOPIA_WALL_BANNER,
                VABlocks.LILAC_BANNER,
                VABlocks.LILAC_WALL_BANNER);
        BlockEntityType.BED.addSupportedBlock(VABlocks.CHARTREUSE_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.MAROON_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.INDIGO_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.PLUM_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.VIRIDIAN_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.TAN_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.SINOPIA_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.LILAC_BED);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.CHARTREUSE_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.CHARTREUSE_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.MAROON_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.MAROON_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.INDIGO_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.INDIGO_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.PLUM_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.PLUM_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.VIRIDIAN_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.VIRIDIAN_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.TAN_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.TAN_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.SINOPIA_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.SINOPIA_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.LILAC_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.LILAC_WALL_BANNER);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.CHARTREUSE_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.MAROON_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.INDIGO_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.PLUM_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.VIRIDIAN_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.TAN_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.SINOPIA_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.LILAC_SHULKER_BOX);
        BlockEntityType.SIGN.addSupportedBlock(VABlocks.AEROBLOOM_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(VABlocks.AEROBLOOM_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(VABlocks.AEROBLOOM_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(VABlocks.AEROBLOOM_WALL_HANGING_SIGN);
    }

    public static void init(){}

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, VirtualAdditions.idOf(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }
}
