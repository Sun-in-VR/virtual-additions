package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.DamageSourcesInterface;
import com.github.suninvr.virtualadditions.interfaces.EntityInterface;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VAFluids;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput, EntityInterface {

    @Shadow public World world;
    @Shadow public abstract boolean damage(DamageSource source, float amount);
    @Shadow protected boolean firstUpdate;
    @Shadow protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;

    @Shadow public abstract World getWorld();

    @Shadow private Vec3d pos;

    @Shadow public abstract boolean updateMovementInFluid(TagKey<Fluid> tag, double speed);

    @Shadow public abstract DamageSources getDamageSources();

    @Shadow public abstract boolean isInvulnerable();

    private int ticksInAcid;

    @Inject(method = "getPosWithYOffset", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    void virtualAdditions$getPosWithYOffsetForHedge(float offset, CallbackInfoReturnable<BlockPos> cir, int i, int j, int k, BlockPos blockPos) {
        if (this.world.getBlockState(blockPos).isAir()) {
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState = this.world.getBlockState(blockPos2);
            if (blockState.isIn(VABlockTags.HEDGES)) {
                cir.setReturnValue(blockPos2);
            }
        }
    }

    @Inject(method = "baseTick", at = @At("TAIL"))
    void virtualAdditions$baseTickInAcid(CallbackInfo ci) {
        if(this.isInAcid()) {
            if (this.ticksInAcid >= 20) this.damage( ((DamageSourcesInterface)(this.getDamageSources())).acid() , 4.0F);
            this.ticksInAcid = Math.min(this.ticksInAcid + 1, 20);
        } else {
            this.ticksInAcid = Math.max(this.ticksInAcid - 1, 0);
        }
    }

    @Inject(method = "updateWaterState", at = @At("RETURN"), cancellable = true)
    void virtualAdditions$updateAcidState(CallbackInfoReturnable<Boolean> cir) {
        boolean bl = this.updateMovementInFluid(VAFluids.ACID_TAG, 0);
        if (bl) cir.setReturnValue(true);
    }

    public boolean isInAcid() {
        return !this.firstUpdate && this.fluidHeight.getDouble(VAFluids.ACID_TAG) > 0.0;
    }
}
