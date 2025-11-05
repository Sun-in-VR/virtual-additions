package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ClimbingRopeItem extends BlockItem implements ProjectileItem {
    public ClimbingRopeItem(Block block, net.minecraft.world.item.Item.Properties settings) {
        super(block, settings);
    }

    @Override
    public Projectile asProjectile(Level world, Position position, ItemStack itemStack, Direction direction) {
        return new ClimbingRopeEntity(position.x(), position.y(), position.z(), world, itemStack, null);
    }
}