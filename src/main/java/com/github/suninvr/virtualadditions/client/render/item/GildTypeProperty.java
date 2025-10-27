package com.github.suninvr.virtualadditions.client.render.item;

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
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class GildTypeProperty implements SelectProperty<RegistryKey<GildType>> {
    public static final Codec<RegistryKey<GildType>> VALUE_CODEC = RegistryKey.createCodec(VARegistries.GILD_TYPE_REGISTRY_KEY);
    public static final SelectProperty.Type<GildTypeProperty, RegistryKey<GildType>> TYPE = SelectProperty.Type.create(
            MapCodec.unit(new GildTypeProperty()), VALUE_CODEC
    );

    @Override
    @Nullable
    public RegistryKey<GildType> getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        RegistryEntry<GildType> type = VARegistries.GILD_TYPE.getEntry(stack.get(VADataComponentTypes.GILD_TYPE));
        if (Objects.isNull(type) || type.getKey().isEmpty()) return null;
        return type.getKey().get();
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
