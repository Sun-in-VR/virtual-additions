package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.*;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public class VADispenserBehavior {
    private static DefaultDispenseItemBehavior spawnEggBehavior = new DefaultDispenseItemBehavior() {
        @Override
        public ItemStack execute(BlockSource pointer, ItemStack stack) {
            //Direction direction = pointer.state().get(DispenserBlock.FACING);
            //EntityType<?> entityType = ((SpawnEggItem)stack.getItem()).getEntityType(pointer.world().getRegistryManager(), stack);

            //try {
            //    entityType.spawnFromItemStack(pointer.world(), stack, null, pointer.pos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
            //} catch (Exception var6) {
            //    LOGGER.error("Error while dispensing spawn egg from dispenser at {}", pointer.pos(), var6);
            //    return ItemStack.EMPTY;
            //}

            //stack.decrement(1);
            //pointer.world().emitGameEvent(null, GameEvent.ENTITY_PLACE, pointer.pos());
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
            DispenseItemBehavior climbingRopeBehavior = new ProjectileDispenseBehavior(item);
            DispenserBlock.registerBehavior(item, climbingRopeBehavior);
        }

        DispenserBlock.registerBehavior(ShulkerBoxBlock.getBlockByColor(VADyeColors.CHARTREUSE).asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.getBlockByColor(VADyeColors.MAROON).asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.getBlockByColor(VADyeColors.INDIGO).asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.getBlockByColor(VADyeColors.PLUM).asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.getBlockByColor(VADyeColors.VIRIDIAN).asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.getBlockByColor(VADyeColors.TAN).asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.getBlockByColor(VADyeColors.SINOPIA).asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(ShulkerBoxBlock.getBlockByColor(VADyeColors.LILAC).asItem(), new ShulkerBoxDispenseBehavior());

        DispenserBlock.registerBehavior(VAItems.SOULBLOOM_BOAT, new BoatDispenseItemBehavior(VAEntityType.SOULBLOOM_BOAT));
        DispenserBlock.registerBehavior(VAItems.SOULBLOOM_CHEST_BOAT, new BoatDispenseItemBehavior(VAEntityType.SOULBLOOM_CHEST_BOAT));
        DispenserBlock.registerBehavior(VAItems.ZEBRANO_BOAT, new BoatDispenseItemBehavior(VAEntityType.ZEBRANO_BOAT));
        DispenserBlock.registerBehavior(VAItems.ZEBRANO_CHEST_BOAT, new BoatDispenseItemBehavior(VAEntityType.ZEBRANO_CHEST_BOAT));

        DispenserBlock.registerBehavior(VAItems.ACID_BUCKET, new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior fallbackBehavior = new DefaultDispenseItemBehavior();

            public ItemStack execute(BlockSource pointer, ItemStack stack) {
                DispensibleContainerItem fluidModificationItem = (DispensibleContainerItem) stack.getItem();
                BlockPos blockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
                Level world = pointer.level();
                if (fluidModificationItem.emptyContents(null, world, blockPos, null)) {
                    fluidModificationItem.checkExtraContent(null, world, stack, blockPos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return this.fallbackBehavior.dispense(pointer, stack);
                }
            }
        });
    }
}
