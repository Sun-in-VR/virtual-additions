package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.dispenser.*;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VADispenserBehavior {
    protected static void init() {
        DispenserBlock.registerBehavior(VAItems.STEEL_BOMB, new ProjectileDispenserBehavior(VAItems.STEEL_BOMB));
        DispenserBlock.registerBehavior(VAItems.LIGHTNING_BOTTLE, new ProjectileDispenserBehavior(VAItems.LIGHTNING_BOTTLE));
        DispenserBlock.registerBehavior(VAItems.TOMATO, new ProjectileDispenserBehavior(VAItems.TOMATO));

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
