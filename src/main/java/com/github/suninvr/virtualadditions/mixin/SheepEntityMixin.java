package com.github.suninvr.virtualadditions.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheep.class)
public class SheepEntityMixin extends Animal {
    @Shadow @Final private static EntityDataAccessor<Byte> DATA_WOOL_ID;

    protected SheepEntityMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "getColor", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$getAnySheepColor(CallbackInfoReturnable<DyeColor> cir) {
        cir.setReturnValue(DyeColor.byId(this.entityData.get(DATA_WOOL_ID) & 31));
    }

    @Inject(method = "setColor", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$setAnySheepColor(DyeColor color, CallbackInfo ci) {
        if ((24 > color.getId() && color.getId() > 15) || (this.entityData.get(DATA_WOOL_ID) > 15)) {
            byte b = this.entityData.get(DATA_WOOL_ID);
            this.entityData.set(DATA_WOOL_ID, (byte) (b & 224 | color.getId() & 31));
            ci.cancel();
        };
    }

    @Inject(method = "isSheared", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$isSheared(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((this.entityData.get(DATA_WOOL_ID) & 32) != 0);
    }

    @Inject(method = "setSheared", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$setSheared(boolean sheared, CallbackInfo ci) {
        byte b = this.entityData.get(DATA_WOOL_ID);
        if (sheared) {
            this.entityData.set(DATA_WOOL_ID, (byte)(b | 32));
        } else {
            this.entityData.set(DATA_WOOL_ID, (byte)(b & -33));
        }
        ci.cancel();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return null;
    }
}
