package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.DamageSourcesInterface;
import com.github.suninvr.virtualadditions.interfaces.EntityInterface;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VAFluids;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.commands.CommandSource;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Nameable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityAccess, CommandSource, EntityInterface {

    @Shadow protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;
    @Shadow public abstract BlockPos blockPosition();
    @Shadow public Optional<BlockPos> mainSupportingBlockPos;
    @Shadow public abstract Level level();
    @Shadow private Level level;
    @Shadow public abstract boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f);
    @Shadow public abstract DamageSources damageSources();
    @Shadow public abstract boolean updateFluidHeightAndDoFluidPushing(TagKey<Fluid> tagKey, double d);
    @Shadow protected boolean firstTick;
    @Unique private int ticksInAcid;
    @Unique private long lastUsedMiniPortal;

    @Inject(method = "getOnPos(F)Lnet/minecraft/core/BlockPos;", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    void virtualAdditions$getPosWithYOffsetForHedge(float offset, CallbackInfoReturnable<BlockPos> cir) {
        if (this.mainSupportingBlockPos.isPresent()) {
            BlockPos blockPos = this.mainSupportingBlockPos.get();
            BlockState blockState = this.level().getBlockState(blockPos);
            if (blockState.is(VABlockTags.HEDGES)) {
                cir.setReturnValue( blockPos );
            }
        }
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", shift = At.Shift.BEFORE))
    void virtualAdditions$baseTickInAcid(CallbackInfo ci) {
        if(this.level instanceof ServerLevel serverWorld && this.virtualAdditions$isInAcid() && (!( (Entity)(Object)this instanceof ItemEntity itemEntity) || !itemEntity.getItem().is(VAItemTags.ACID_RESISTANT))) {
            if (this.ticksInAcid >= 20) this.hurtServer(serverWorld, ((DamageSourcesInterface)this.damageSources()).virtualAdditions$acid() , 4.0F);
            this.ticksInAcid = Math.min(this.ticksInAcid + 1, 20);
        } else {
            this.ticksInAcid = Math.max(this.ticksInAcid - 1, 0);
        }
    }

    @Inject(method = "updateInWaterStateAndDoFluidPushing", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$updateAcidState(CallbackInfoReturnable<Boolean> cir) {
        boolean bl = this.updateFluidHeightAndDoFluidPushing(VAFluids.ACID_TAG, 0);
        if (bl) cir.setReturnValue(true);
    }

    public boolean virtualAdditions$isInAcid() {
        return !this.firstTick && this.fluidHeight.getDouble(VAFluids.ACID_TAG) > 0.0;
    }

    public boolean virtualAdditions$hasUsedMiniPortalThisTick() {
        return this.level.getGameTime() == this.lastUsedMiniPortal;
    }

    public void virtualAdditions$setUsedMiniPortal() {
        this.lastUsedMiniPortal = this.level.getGameTime();
    }
}
