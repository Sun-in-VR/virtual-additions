package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.IncenseBlock;
import com.github.suninvr.virtualadditions.component.IncenseEffectsComponent;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

public class IncenseBlockEntity extends BlockEntity {
    private PotionContents effects;
    private int burnTicks;

    public IncenseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(VABlockEntityType.INCENSE, blockPos, blockState);
        this.effects = null;
        this.burnTicks = 0;
    }

    public IncenseBlockEntity(BlockPos blockPos, BlockState blockState, PotionContents effectInstance, int burnTicks) {
        super(VABlockEntityType.INCENSE, blockPos, blockState);
        this.effects = effectInstance;
        this.burnTicks = burnTicks;
    }

    public boolean hasEffects() {
        return this.effects != null;
    }

    public ParticleOptions getFirstEffectParticle() {
        return this.effects.getAllEffects().iterator().next().getParticleOptions();
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putInt("burn_ticks", this.burnTicks);
        valueOutput.storeNullable("effect", PotionContents.CODEC, effects);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        valueInput.getIntOr("burn_ticks", 0);
        valueInput.read("effect", PotionContents.CODEC).ifPresentOrElse(instance -> this.effects = instance, () -> this.effects = null);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter dataComponentGetter) {
        IncenseEffectsComponent component = dataComponentGetter.get(VADataComponentTypes.INCENSE_EFFECTS);
        if (component != null) {
            this.effects = component.potionContents().isPresent() ? component.potionContents().get() : null;
            this.burnTicks = component.burnTicks().orElse(0);
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, IncenseBlockEntity incenseBlockEntity) {
        if (!state.getValue(IncenseBlock.LIT)) return;
        if (incenseBlockEntity.hasEffects() && level.getGameTime() % 40 == 0) {
            level.getEntities(null, AABB.ofSize(pos.getCenter(), 16, 8, 16)).stream().filter(entity -> entity instanceof LivingEntity).forEach(entity -> {
                incenseBlockEntity.effects.applyToLivingEntity((LivingEntity) entity, 1.0F);
            });
        }
        if (incenseBlockEntity.burnTicks > 0) {
            incenseBlockEntity.burnTicks -= 1;
            incenseBlockEntity.setChanged();
            if (incenseBlockEntity.burnTicks == 0) incenseBlockEntity.effects = null;
        }
    }
}
