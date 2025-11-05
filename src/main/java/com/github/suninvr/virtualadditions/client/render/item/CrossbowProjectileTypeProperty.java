package com.github.suninvr.virtualadditions.client.render.item;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;
import org.jetbrains.annotations.Nullable;

public class CrossbowProjectileTypeProperty implements RangeSelectItemModelProperty {
    public static final MapCodec<CrossbowProjectileTypeProperty> CODEC = MapCodec.unit(new CrossbowProjectileTypeProperty());

    @Override
    public float get(ItemStack stack, @Nullable ClientLevel world, @Nullable ItemOwner context, int seed) {
        if (!stack.has(DataComponents.CHARGED_PROJECTILES)) return 0.0F;
        ChargedProjectiles chargedProjectilesComponent = stack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
        if (chargedProjectilesComponent.isEmpty()) return 0.0F;
        ItemStack projectile = chargedProjectilesComponent.getItems().getFirst();
        float f = 0.0F;
        if (projectile.is(VAItems.CLIMBING_ROPE) || projectile.is(VAItems.WAXED_CLIMBING_ROPE)) f = 0.25F;
        else if (projectile.is(VAItems.EXPOSED_CLIMBING_ROPE) || projectile.is(VAItems.WAXED_EXPOSED_CLIMBING_ROPE)) f = 0.5F;
        else if (projectile.is(VAItems.WEATHERED_CLIMBING_ROPE) || projectile.is(VAItems.WAXED_WEATHERED_CLIMBING_ROPE)) f = 0.75F;
        else if (projectile.is(VAItems.OXIDIZED_CLIMBING_ROPE) || projectile.is(VAItems.WAXED_OXIDIZED_CLIMBING_ROPE)) f = 1.0F;
        return f;
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return CODEC;
    }
}
