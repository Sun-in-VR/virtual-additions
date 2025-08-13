package com.github.suninvr.virtualadditions.client.render.item;

import com.github.suninvr.virtualadditions.registry.VAItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.numeric.NumericProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HeldItemContext;
import org.jetbrains.annotations.Nullable;

public class CrossbowProjectileTypeProperty implements NumericProperty {
    public static final MapCodec<CrossbowProjectileTypeProperty> CODEC = MapCodec.unit(new CrossbowProjectileTypeProperty());

    @Override
    public float getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable HeldItemContext context, int seed) {
        if (!stack.contains(DataComponentTypes.CHARGED_PROJECTILES)) return 0.0F;
        ChargedProjectilesComponent chargedProjectilesComponent = stack.getOrDefault(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT);
        if (chargedProjectilesComponent.isEmpty()) return 0.0F;
        ItemStack projectile = chargedProjectilesComponent.getProjectiles().getFirst();
        float f = 0.0F;
        if (projectile.isOf(VAItems.CLIMBING_ROPE) || projectile.isOf(VAItems.WAXED_CLIMBING_ROPE)) f = 0.25F;
        else if (projectile.isOf(VAItems.EXPOSED_CLIMBING_ROPE) || projectile.isOf(VAItems.WAXED_EXPOSED_CLIMBING_ROPE)) f = 0.5F;
        else if (projectile.isOf(VAItems.WEATHERED_CLIMBING_ROPE) || projectile.isOf(VAItems.WAXED_WEATHERED_CLIMBING_ROPE)) f = 0.75F;
        else if (projectile.isOf(VAItems.OXIDIZED_CLIMBING_ROPE) || projectile.isOf(VAItems.WAXED_OXIDIZED_CLIMBING_ROPE)) f = 1.0F;
        return f;
    }

    @Override
    public MapCodec<? extends NumericProperty> getCodec() {
        return CODEC;
    }
}
