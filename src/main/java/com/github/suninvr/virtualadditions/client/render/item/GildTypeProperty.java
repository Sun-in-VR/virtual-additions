package com.github.suninvr.virtualadditions.client.render.item;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VARegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class GildTypeProperty implements SelectItemModelProperty<ResourceKey<GildType>> {
    public static final Codec<ResourceKey<GildType>> VALUE_CODEC = ResourceKey.codec(VARegistries.GILD_TYPE_REGISTRY_KEY);
    public static final SelectItemModelProperty.Type<GildTypeProperty, ResourceKey<GildType>> TYPE = SelectItemModelProperty.Type.create(
            MapCodec.unit(new GildTypeProperty()), VALUE_CODEC
    );

    @Override
    @Nullable
    public ResourceKey<GildType> get(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        Holder<GildType> type = VARegistries.GILD_TYPE.wrapAsHolder(stack.get(VADataComponentTypes.GILD_TYPE));
        if (Objects.isNull(type) || type.unwrapKey().isEmpty()) return null;
        return type.unwrapKey().get();
    }

    @Override
    public Codec valueCodec() {
        return VALUE_CODEC;
    }

    @Override
    public Type type() {
        return TYPE;
    }
}
