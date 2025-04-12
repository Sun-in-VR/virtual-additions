package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.Heightmap;

import java.util.Map;
import java.util.function.Supplier;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEntityType {

    public static final EntityType<ClimbingRopeEntity> CLIMBING_ROPE;
    public static final EntityType<SteelBombEntity> STEEL_BOMB;
    public static final EntityType<TomatoEntity> TOMATO;
    public static final EntityType<AcidSpitEntity> ACID_SPIT;
    public static final EntityType<LumwaspEntity> LUMWASP;
    public static final EntityType<SalineEntity> SALINE;
    public static final EntityType<BoatEntity> SOULBLOOM_BOAT;
    public static final EntityType<ChestBoatEntity> SOULBLOOM_CHEST_BOAT;
    public static final EntityType<LightningBottleEntity> LIGHTNING_BOTTLE;

    public static final Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> ENTITY_ATTRIBUTES = new java.util.HashMap<>();

    static {
        CLIMBING_ROPE = Registry.register(Registries.ENTITY_TYPE, VirtualAdditions.idOf("climbing_rope"), EntityType.Builder.<ClimbingRopeEntity>create(ClimbingRopeEntity::new, SpawnGroup.MISC)
                .dimensions(0.25F, 0.25F)
                .maxTrackingRange(4).trackingTickInterval(10)
                .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, VirtualAdditions.idOf("climbing_rope"))));

        STEEL_BOMB = Registry.register(Registries.ENTITY_TYPE, idOf("steel_bomb"), EntityType.Builder.<SteelBombEntity>create(SteelBombEntity::new, SpawnGroup.MISC)
                .dimensions(0.25F, 0.25F)
                .maxTrackingRange(4).trackingTickInterval(10)
                .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, VirtualAdditions.idOf("steel_bomb"))));

        TOMATO = Registry.register(Registries.ENTITY_TYPE, idOf("tomato"), EntityType.Builder.<TomatoEntity>create(TomatoEntity::new, SpawnGroup.MISC)
                .dimensions(0.25F, 0.25F)
                .maxTrackingRange(4).trackingTickInterval(10)
                .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, VirtualAdditions.idOf("tomato"))));

        ACID_SPIT = Registry.register(Registries.ENTITY_TYPE, idOf("acid_spit"), EntityType.Builder.<AcidSpitEntity>create(AcidSpitEntity::new, SpawnGroup.MISC)
                .dimensions(0.25F, 0.25F)
                .maxTrackingRange(4).trackingTickInterval(10)
                .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, VirtualAdditions.idOf("acid_spit"))));

        LUMWASP = Registry.register(Registries.ENTITY_TYPE, idOf("lumwasp"), EntityType.Builder.create(LumwaspEntity::new, SpawnGroup.MONSTER)
                .dimensions(1.5F, 0.75F)
                .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, VirtualAdditions.idOf("lumwasp"))));

        SALINE = Registry.register(Registries.ENTITY_TYPE, idOf("saline"), EntityType.Builder.create(SalineEntity::new, SpawnGroup.MONSTER)
                .dimensions(0.6F, 1.95F).maxTrackingRange(8)
                .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, VirtualAdditions.idOf("saline"))));

        LIGHTNING_BOTTLE = Registry.register(Registries.ENTITY_TYPE, idOf("lightning_bottle"), EntityType.Builder.<LightningBottleEntity>create(LightningBottleEntity::new, SpawnGroup.MISC)
                .dropsNothing().dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10)
                .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, idOf("lightning_bottle"))));

        SOULBLOOM_BOAT = Registry.register(Registries.ENTITY_TYPE, idOf("soulbloom_boat"), EntityType.Builder.create(getBoatFactory(() -> VAItems.SOULBLOOM_BOAT), SpawnGroup.MISC)
                .dropsNothing().dimensions(1.375F, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10)
                .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, idOf("soulbloom_boat"))));

        SOULBLOOM_CHEST_BOAT = Registry.register(Registries.ENTITY_TYPE, idOf("soulbloom_chest_boat"), EntityType.Builder.create(getChestBoatFactory(() -> VAItems.SOULBLOOM_CHEST_BOAT), SpawnGroup.MISC)
                .dropsNothing().dimensions(1.375F, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10)
                .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, idOf("soulbloom_chest_boat"))));

        ENTITY_ATTRIBUTES.put(LUMWASP, LumwaspEntity.createLumwaspAttributes());
        ENTITY_ATTRIBUTES.put(SALINE, SalineEntity.createSalineAttributes());
    }

    public static void init(){
        SpawnRestriction.register(SALINE, SpawnRestriction.getLocation(SALINE), Heightmap.Type.MOTION_BLOCKING, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(LUMWASP, SpawnRestriction.getLocation(LUMWASP), Heightmap.Type.MOTION_BLOCKING, LumwaspEntity::canSpawnInDark);
    }


    private static EntityType.EntityFactory<BoatEntity> getBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new BoatEntity(type, world, itemSupplier);
    }

    private static EntityType.EntityFactory<ChestBoatEntity> getChestBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new ChestBoatEntity(type, world, itemSupplier);
    }
}
