package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.entity.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Map;
import java.util.function.Supplier;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEntityType {

    public static final EntityType<ClimbingRopeEntity> CLIMBING_ROPE;
    public static final EntityType<SteelBombEntity> STEEL_BOMB;
    public static final EntityType<TomatoEntity> TOMATO;
    public static final EntityType<AcidSpitEntity> ACID_SPIT;
    public static final EntityType<SpectralBoltEntity> SPECTRAL_BOLT;
    public static final EntityType<SlungItemProjectile> SLUNG_ITEM;
    public static final EntityType<LumwaspEntity> LUMWASP;
    public static final EntityType<SalineEntity> SALINE;
    public static final EntityType<SpectreEntity> SPECTRE;
    public static final EntityType<PlayerProjectionEntity> PLAYER_PROJECTION;
    public static final EntityType<Boat> SOULBLOOM_BOAT, ZEBRANO_BOAT;
    public static final EntityType<ChestBoat> SOULBLOOM_CHEST_BOAT, ZEBRANO_CHEST_BOAT;
    public static final EntityType<LightningBottleEntity> LIGHTNING_BOTTLE;

    public static final Map<EntityType<? extends LivingEntity>, AttributeSupplier> ENTITY_ATTRIBUTES = new java.util.HashMap<>();

    static {
        CLIMBING_ROPE = Registry.register(BuiltInRegistries.ENTITY_TYPE, VirtualAdditions.idOf("climbing_rope"), EntityType.Builder.<ClimbingRopeEntity>of(ClimbingRopeEntity::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(4).updateInterval(10)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, VirtualAdditions.idOf("climbing_rope"))));

        STEEL_BOMB = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("steel_bomb"), EntityType.Builder.<SteelBombEntity>of(SteelBombEntity::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(4).updateInterval(10)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, VirtualAdditions.idOf("steel_bomb"))));

        TOMATO = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("tomato"), EntityType.Builder.<TomatoEntity>of(TomatoEntity::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(4).updateInterval(10)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, VirtualAdditions.idOf("tomato"))));

        ACID_SPIT = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("acid_spit"), EntityType.Builder.<AcidSpitEntity>of(AcidSpitEntity::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(4).updateInterval(10)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, VirtualAdditions.idOf("acid_spit"))));

        SLUNG_ITEM = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("slung_item"), EntityType.Builder.<SlungItemProjectile>of(SlungItemProjectile::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(4).updateInterval(10)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, VirtualAdditions.idOf("slung_item"))));

        SPECTRAL_BOLT = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("spectral_bolt"), EntityType.Builder.<SpectralBoltEntity>of(SpectralBoltEntity::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(4).updateInterval(10)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, VirtualAdditions.idOf("spectral_bolt"))));

        LUMWASP = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("lumwasp"), EntityType.Builder.of(LumwaspEntity::new, MobCategory.MONSTER)
                .sized(1.5F, 0.75F)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, VirtualAdditions.idOf("lumwasp"))));

        SALINE = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("saline"), EntityType.Builder.of(SalineEntity::new, MobCategory.MONSTER)
                .sized(0.6F, 1.95F).clientTrackingRange(8)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, VirtualAdditions.idOf("saline"))));

        SPECTRE = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("spectre"), EntityType.Builder.of(SpectreEntity::new, MobCategory.MONSTER)
                .sized(0.5F, 0.5F).eyeHeight(0.25F).fireImmune()
                .build(ResourceKey.create(Registries.ENTITY_TYPE, VirtualAdditions.idOf("spectre"))));

        PLAYER_PROJECTION = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("player_projection"), EntityType.Builder.of(PlayerProjectionEntity::new, MobCategory.MISC)
                .sized(0.5F, 0.5F).eyeHeight(0.25F).noLootTable().noSave().noSummon()
                .build(ResourceKey.create(Registries.ENTITY_TYPE, VirtualAdditions.idOf("spectre"))));

        LIGHTNING_BOTTLE = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("lightning_bottle"), EntityType.Builder.<LightningBottleEntity>of(LightningBottleEntity::new, MobCategory.MISC)
                .noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, idOf("lightning_bottle"))));

        SOULBLOOM_BOAT = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("soulbloom_boat"), EntityType.Builder.of(getBoatFactory(() -> VAItems.SOULBLOOM_BOAT), MobCategory.MISC)
                .noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, idOf("soulbloom_boat"))));

        SOULBLOOM_CHEST_BOAT = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("soulbloom_chest_boat"), EntityType.Builder.of(getChestBoatFactory(() -> VAItems.SOULBLOOM_CHEST_BOAT), MobCategory.MISC)
                .noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, idOf("soulbloom_chest_boat"))));

        ZEBRANO_BOAT = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("zebrano_boat"), EntityType.Builder.of(getBoatFactory(() -> VAItems.ZEBRANO_BOAT), MobCategory.MISC)
                .noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, idOf("zebrano_boat"))));

        ZEBRANO_CHEST_BOAT = Registry.register(BuiltInRegistries.ENTITY_TYPE, idOf("zebrano_chest_boat"), EntityType.Builder.of(getChestBoatFactory(() -> VAItems.ZEBRANO_CHEST_BOAT), MobCategory.MISC)
                .noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, idOf("zebrano_chest_boat"))));

        ENTITY_ATTRIBUTES.put(LUMWASP, LumwaspEntity.createLumwaspAttributes());
        ENTITY_ATTRIBUTES.put(SALINE, SalineEntity.createSalineAttributes());
        ENTITY_ATTRIBUTES.put(SPECTRE, SpectreEntity.createSpectreAttributes());
        ENTITY_ATTRIBUTES.put(PLAYER_PROJECTION, PlayerProjectionEntity.createAttributes());
    }

    public static void init(){
        SpawnPlacements.register(SALINE, SpawnPlacements.getPlacementType(SALINE), Heightmap.Types.MOTION_BLOCKING, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(LUMWASP, SpawnPlacements.getPlacementType(LUMWASP), Heightmap.Types.MOTION_BLOCKING, LumwaspEntity::canSpawnLumwasp);
        SpawnPlacements.register(SPECTRE, SpawnPlacements.getPlacementType(SPECTRE), Heightmap.Types.MOTION_BLOCKING, SpectreEntity::canSpawnSpectre);
    }


    private static EntityType.EntityFactory<Boat> getBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new Boat(type, world, itemSupplier);
    }

    private static EntityType.EntityFactory<ChestBoat> getChestBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new ChestBoat(type, world, itemSupplier);
    }
}
