package com.github.suninvr.virtualadditions.client.render.item;

import com.github.suninvr.virtualadditions.component.GildTypeComponent;
import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VARegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class GildTypeProperty implements SelectProperty<RegistryKey<GildType>> {
    public static final Codec<RegistryKey<GildType>> VALUE_CODEC = RegistryKey.createCodec(VARegistries.GILD_TYPE_REGISTRY_KEY);
    public static final SelectProperty.Type<GildTypeProperty, RegistryKey<GildType>> TYPE = SelectProperty.Type.create(
            MapCodec.unit(new GildTypeProperty()), VALUE_CODEC
    );

    @Override
    @Nullable
    public RegistryKey<GildType> getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        GildTypeComponent type = stack.get(VADataComponentTypes.GILD_TYPE_COMPONENT);
        if (Objects.isNull(type)) return null;
        Optional<RegistryKey<GildType>> gildType = VARegistries.GILD_TYPE.getKey(type.type().value());
        return gildType.get();
    }

    @Override
    public Codec valueCodec() {
        return VALUE_CODEC;
    }

    @Override
    public Type getType() {
        return TYPE;
    }
}
