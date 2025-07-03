package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public class SheepEntityMixin extends AnimalEntity {
    @Shadow @Final private static TrackedData<Byte> COLOR;

    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getColor", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$getAnySheepColor(CallbackInfoReturnable<DyeColor> cir) {
        cir.setReturnValue(DyeColor.byIndex(this.dataTracker.get(COLOR) & 31));
    }

    @Inject(method = "setColor", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$setAnySheepColor(DyeColor color, CallbackInfo ci) {
        if ((24 > color.getIndex() && color.getIndex() > 15) || (this.dataTracker.get(COLOR) > 15)) {
            byte b = this.dataTracker.get(COLOR);
            this.dataTracker.set(COLOR, (byte) (b & 224 | color.getIndex() & 31));
            ci.cancel();
        };
    }

    @Inject(method = "isSheared", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$isSheared(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((this.dataTracker.get(COLOR) & 32) != 0);
    }

    @Inject(method = "setSheared", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$setSheared(boolean sheared, CallbackInfo ci) {
        byte b = this.dataTracker.get(COLOR);
        if (sheared) {
            this.dataTracker.set(COLOR, (byte)(b | 32));
        } else {
            this.dataTracker.set(COLOR, (byte)(b & -33));
        }
        ci.cancel();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
