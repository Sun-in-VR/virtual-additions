package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.dispenser.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class VADispenserBehavior {
    private static ItemDispenserBehavior spawnEggBehavior = new ItemDispenserBehavior() {
        @Override
        public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            Direction direction = pointer.state().get(DispenserBlock.FACING);
            EntityType<?> entityType = ((SpawnEggItem)stack.getItem()).getEntityType(pointer.world().getRegistryManager(), stack);

            try {
                entityType.spawnFromItemStack(pointer.world(), stack, null, pointer.pos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
            } catch (Exception var6) {
                LOGGER.error("Error while dispensing spawn egg from dispenser at {}", pointer.pos(), var6);
                return ItemStack.EMPTY;
            }

            stack.decrement(1);
            pointer.world().emitGameEvent(null, GameEvent.ENTITY_PLACE, pointer.pos());
            return stack;
        }
    };

    protected static void init() {
        DispenserBlock.registerProjectileBehavior(VAItems.STEEL_BOMB);
        DispenserBlock.registerProjectileBehavior(VAItems.LIGHTNING_BOTTLE);
        DispenserBlock.registerProjectileBehavior(VAItems.TOMATO);
        DispenserBlock.registerProjectileBehavior(VAItems.PURPLE_EGG);

        DispenserBlock.registerBehavior(VAItems.SALINE_SPAWN_EGG, spawnEggBehavior);
        DispenserBlock.registerBehavior(VAItems.LUMWASP_SPAWN_EGG, spawnEggBehavior);
        DispenserBlock.registerBehavior(VAItems.SPECTRE_SPAWN_EGG, spawnEggBehavior);

        Item[] climbingRopes = {VAItems.CLIMBING_ROPE, VAItems.WAXED_CLIMBING_ROPE, VAItems.EXPOSED_CLIMBING_ROPE, VAItems.WAXED_EXPOSED_CLIMBING_ROPE, VAItems.WEATHERED_CLIMBING_ROPE, VAItems.WAXED_WEATHERED_CLIMBING_ROPE, VAItems.OXIDIZED_CLIMBING_ROPE, VAItems.WAXED_OXIDIZED_CLIMBING_ROPE};

        for (Item item : climbingRopes) {
            DispenserBehavior climbingRopeBehavior = new ProjectileDispenserBehavior(item);
            DispenserBlock.registerBehavior(item, climbingRopeBehavior);
        }

        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.CHARTREUSE).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.MAROON).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.INDIGO).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.PLUM).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.VIRIDIAN).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.TAN).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.SINOPIA).asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.get(VADyeColors.LILAC).asItem(), new BlockPlacementDispenserBehavior());

        DispenserBlock.registerBehavior(VAItems.SOULBLOOM_BOAT, new BoatDispenserBehavior(VAEntityType.SOULBLOOM_BOAT));
        DispenserBlock.registerBehavior(VAItems.SOULBLOOM_CHEST_BOAT, new BoatDispenserBehavior(VAEntityType.SOULBLOOM_CHEST_BOAT));

        DispenserBlock.registerBehavior(VAItems.ACID_BUCKET, new ItemDispenserBehavior() {
            private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                FluidModificationItem fluidModificationItem = (FluidModificationItem) stack.getItem();
                BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
                World world = pointer.world();
                if (fluidModificationItem.placeFluid(null, world, blockPos, null)) {
                    fluidModificationItem.onEmptied(null, world, stack, blockPos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return this.fallbackBehavior.dispense(pointer, stack);
                }
            }
        });
    }
}
