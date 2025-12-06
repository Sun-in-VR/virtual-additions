package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;

public class VABlockEntityType {
    public static final BlockEntityType<SpotlightBlockEntity> SPOTLIGHT;
    public static final BlockEntityType<SpotlightLightBlockEntity> SPOTLIGHT_LIGHT;
    public static final BlockEntityType<EntanglementDriveBlockEntity> ENTANGLEMENT_DRIVE;
    public static final BlockEntityType<RemoteNotifierBlockEntity> REMOTE_NOTIFIER;
    public static final BlockEntityType<DestructiveSculkBlockEntity> DESTRUCTIVE_SCULK;
    public static final BlockEntityType<ColoringStationBlockEntity> COLORING_STATION;
    public static final BlockEntityType<SignBlockEntity> CUSTOM_SIGN;
    public static final BlockEntityType<SignBlockEntity> CUSTOM_HANGING_SIGN;
    public static final BlockEntityType<CustomBedBlockEntity> CUSTOM_BED;
    public static final BlockEntityType<ShulkerBoxBlockEntity> CUSTOM_SHULKER_BOX;
    public static final BlockEntityType<BannerBlockEntity> CUSTOM_BANNER;
    public static final BlockEntityType<MiniPortalBlockEntity> MINI_PORTAL;
    public static final BlockEntityType<IncenseBlockEntity> INCENSE;

    static {
        SPOTLIGHT = register("spotlight", SpotlightBlockEntity::new, VABlocks.SPOTLIGHT);
        SPOTLIGHT_LIGHT = register("spotlight_light", SpotlightLightBlockEntity::new, VABlocks.SPOTLIGHT_LIGHT);
        ENTANGLEMENT_DRIVE = register("entanglement_drive", EntanglementDriveBlockEntity::new, VABlocks.ENTANGLEMENT_DRIVE);
        REMOTE_NOTIFIER = register("remote_notifier", RemoteNotifierBlockEntity::new, VABlocks.REMOTE_NOTIFIER);
        DESTRUCTIVE_SCULK = register("destructive_sculk", DestructiveSculkBlockEntity::new, VABlocks.DESTRUCTIVE_SCULK);
        COLORING_STATION = register("coloring_station", ColoringStationBlockEntity::new, VABlocks.COLORING_STATION);
        MINI_PORTAL = register("mini_portal", MiniPortalBlockEntity::new, VABlocks.MINI_PORTAL);
        INCENSE = register("incense", IncenseBlockEntity::new, VABlocks.INCENSE);
        CUSTOM_SIGN = register("custom_sign", CustomSignBlockEntity::new, VABlocks.SOULBLOOM_SIGN, VABlocks.SOULBLOOM_WALL_SIGN, VABlocks.WITHERED_SIGN, VABlocks.WITHERED_WALL_SIGN, VABlocks.ZEBRANO_SIGN, VABlocks.ZEBRANO_WALL_SIGN);
        CUSTOM_HANGING_SIGN = register("custom_hanging_sign", CustomHangingSignBlockEntity::new, VABlocks.SOULBLOOM_HANGING_SIGN, VABlocks.SOULBLOOM_WALL_HANGING_SIGN, VABlocks.WITHERED_HANGING_SIGN, VABlocks.WITHERED_WALL_HANGING_SIGN, VABlocks.ZEBRANO_HANGING_SIGN, VABlocks.ZEBRANO_WALL_HANGING_SIGN);
        CUSTOM_BED = register("custom_bed", CustomBedBlockEntity::new, VABlocks.CHARTREUSE_BED, VABlocks.MAROON_BED, VABlocks.INDIGO_BED, VABlocks.PLUM_BED, VABlocks.VIRIDIAN_BED, VABlocks.TAN_BED, VABlocks.SINOPIA_BED, VABlocks.LILAC_BED);
        CUSTOM_SHULKER_BOX = register("shulker_box", CustomShulkerBoxBlockEntity::new, VABlocks.CHARTREUSE_SHULKER_BOX, VABlocks.MAROON_SHULKER_BOX, VABlocks.INDIGO_SHULKER_BOX, VABlocks.PLUM_SHULKER_BOX, VABlocks.VIRIDIAN_SHULKER_BOX, VABlocks.TAN_SHULKER_BOX, VABlocks.SINOPIA_SHULKER_BOX, VABlocks.LILAC_SHULKER_BOX);
        CUSTOM_BANNER = register("banner", CustomBannerBlockEntity::new, VABlocks.CHARTREUSE_BANNER, VABlocks.CHARTREUSE_WALL_BANNER, VABlocks.MAROON_BANNER, VABlocks.MAROON_WALL_BANNER, VABlocks.INDIGO_BANNER, VABlocks.INDIGO_WALL_BANNER, VABlocks.PLUM_BANNER, VABlocks.PLUM_WALL_BANNER, VABlocks.VIRIDIAN_BANNER, VABlocks.VIRIDIAN_WALL_BANNER, VABlocks.TAN_BANNER, VABlocks.TAN_WALL_BANNER, VABlocks.SINOPIA_BANNER, VABlocks.SINOPIA_WALL_BANNER, VABlocks.LILAC_BANNER, VABlocks.LILAC_WALL_BANNER);
        BlockEntityType.BED.addSupportedBlock(VABlocks.CHARTREUSE_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.MAROON_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.INDIGO_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.PLUM_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.VIRIDIAN_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.TAN_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.SINOPIA_BED);
        BlockEntityType.BED.addSupportedBlock(VABlocks.LILAC_BED);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.CHARTREUSE_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.MAROON_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.INDIGO_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.PLUM_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.VIRIDIAN_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.TAN_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.SINOPIA_SHULKER_BOX);
        BlockEntityType.SHULKER_BOX.addSupportedBlock(VABlocks.LILAC_SHULKER_BOX);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.CHARTREUSE_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.MAROON_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.INDIGO_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.PLUM_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.VIRIDIAN_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.TAN_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.SINOPIA_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.LILAC_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.CHARTREUSE_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.MAROON_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.INDIGO_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.PLUM_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.VIRIDIAN_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.TAN_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.SINOPIA_WALL_BANNER);
        BlockEntityType.BANNER.addSupportedBlock(VABlocks.LILAC_WALL_BANNER);
        BlockEntityType.SIGN.addSupportedBlock(VABlocks.SOULBLOOM_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(VABlocks.SOULBLOOM_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(VABlocks.SOULBLOOM_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(VABlocks.SOULBLOOM_WALL_HANGING_SIGN);
        BlockEntityType.SHELF.addSupportedBlock(VABlocks.SOULBLOOM_SHELF);
        BlockEntityType.SIGN.addSupportedBlock(VABlocks.WITHERED_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(VABlocks.WITHERED_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(VABlocks.WITHERED_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(VABlocks.WITHERED_WALL_HANGING_SIGN);
        BlockEntityType.SHELF.addSupportedBlock(VABlocks.WITHERED_SHELF);
        BlockEntityType.SIGN.addSupportedBlock(VABlocks.ZEBRANO_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(VABlocks.ZEBRANO_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(VABlocks.ZEBRANO_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(VABlocks.ZEBRANO_WALL_HANGING_SIGN);
        BlockEntityType.SHELF.addSupportedBlock(VABlocks.ZEBRANO_SHELF);
    }

    public static void init(){}

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, VirtualAdditions.idOf(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }
}
