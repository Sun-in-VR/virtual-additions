package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.DamageSourcesInterface;
import com.github.suninvr.virtualadditions.interfaces.EntityInterface;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VAFluids;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
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
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput, EntityInterface {

    @Shadow protected boolean firstUpdate;

    @Shadow protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;

    @Shadow public abstract World getWorld();

    @Shadow public abstract boolean updateMovementInFluid(TagKey<Fluid> tag, double speed);

    @Shadow public abstract DamageSources getDamageSources();

    @Shadow public abstract BlockPos getBlockPos();
    
    @Unique private Vec3d windVelocity;
    
    @Unique private boolean isInWindCurrent;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Shadow public Optional<BlockPos> supportingBlockPos;

    @Shadow public abstract void addVelocity(Vec3d velocity);

    @Shadow public abstract boolean damage(ServerWorld world, DamageSource source, float amount);

    @Shadow private World world;
    private int ticksInAcid;

    @Inject(method = "getPosWithYOffset", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    void virtualAdditions$getPosWithYOffsetForHedge(float offset, CallbackInfoReturnable<BlockPos> cir) {
        if (this.supportingBlockPos.isPresent()) {
            BlockPos blockPos = this.supportingBlockPos.get();
            BlockState blockState = this.getWorld().getBlockState(blockPos);
            if (blockState.isIn(VABlockTags.HEDGES)) {
                cir.setReturnValue( blockPos );
            }
        }
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V", shift = At.Shift.BEFORE))
    void virtualAdditions$baseTickInAcid(CallbackInfo ci) {
        if(this.world instanceof ServerWorld serverWorld && this.virtualAdditions$isInAcid() && (!( (Entity)(Object)this instanceof ItemEntity itemEntity) || !itemEntity.getStack().isIn(VAItemTags.ACID_RESISTANT))) {
            if (this.ticksInAcid >= 20) this.damage(serverWorld, ((DamageSourcesInterface)this.getDamageSources()).virtualAdditions$acid() , 4.0F);
            this.ticksInAcid = Math.min(this.ticksInAcid + 1, 20);
        } else {
            this.ticksInAcid = Math.max(this.ticksInAcid - 1, 0);
        }
        
        if (this.isInWindCurrent) {
            this.isInWindCurrent = false;
        }
    }

    @Inject(method = "updateWaterState", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$updateAcidState(CallbackInfoReturnable<Boolean> cir) {
        boolean bl = this.updateMovementInFluid(VAFluids.ACID_TAG, 0);
        if (bl) cir.setReturnValue(true);
    }

    public boolean virtualAdditions$isInAcid() {
        return !this.firstUpdate && this.fluidHeight.getDouble(VAFluids.ACID_TAG) > 0.0;
    }

    @Override
    public void virtualAdditions$setInWind(boolean bl) {
        this.isInWindCurrent = bl;
    }

    @Override
    public void virtualAdditions$setWindVelocity(Vec3d vel) {
        this.windVelocity = vel;
    }
}
