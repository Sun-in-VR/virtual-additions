package com.github.suninvr.virtualadditions.item;

import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class ClimbingRopeItem extends AliasedBlockItem implements ProjectileItem {
    public ClimbingRopeItem(Block block, net.minecraft.item.Item.Settings settings) {
        super(block, settings);
    }

    @Override
    public ProjectileEntity createEntity(World world, Position position, ItemStack itemStack, Direction direction) {
        return new ClimbingRopeEntity(position.getX(), position.getY(), position.getZ(), world, itemStack);
    }
}