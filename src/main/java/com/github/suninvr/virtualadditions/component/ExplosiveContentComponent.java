package com.github.suninvr.virtualadditions.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public record ExplosiveContentComponent(Optional<Integer> explosionStrength, Optional<Integer> fuseLength, Optional<Boolean> destroysBlocks) {
    public static final ExplosiveContentComponent DEFAULT = new ExplosiveContentComponent(Optional.of(2), Optional.of(200), Optional.of(true));
    public static final ExplosiveContentComponent KEEP_BLOCKS = new ExplosiveContentComponent(Optional.of(2), Optional.of(200), Optional.of(false));
    public static final Codec<ExplosiveContentComponent> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, ExplosiveContentComponent> PACKET_CODEC;

    public int getExplosionStrength(){
        return this.explosionStrength.orElse(2);
    }

    public int getFuseLength(){
        return this.fuseLength.orElse(200);
    }

    public boolean shouldDestroyBlocks(){
        return this.destroysBlocks.orElse(true);
    }

    public static DataComponentType.Builder<ExplosiveContentComponent> setCodecs(DataComponentType.Builder<ExplosiveContentComponent> builder) {
        return builder.persistent(CODEC).networkSynchronized(PACKET_CODEC);
    }

    static {
        CODEC = RecordCodecBuilder.create(instance -> {
            return instance.group(
                    ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("explosion_strength").forGetter(ExplosiveContentComponent::explosionStrength),
                    ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("fuse_length").forGetter(ExplosiveContentComponent::fuseLength),
                    Codec.BOOL.optionalFieldOf("destroys_blocks").forGetter(c -> c.destroysBlocks)
            ).apply(instance, ExplosiveContentComponent::new);
        });
        PACKET_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT.apply(ByteBufCodecs::optional), ExplosiveContentComponent::explosionStrength,
                ByteBufCodecs.INT.apply(ByteBufCodecs::optional), ExplosiveContentComponent::fuseLength,
                ByteBufCodecs.BOOL.apply(ByteBufCodecs::optional), c -> c.destroysBlocks,
                ExplosiveContentComponent::new
        );
    }
}
