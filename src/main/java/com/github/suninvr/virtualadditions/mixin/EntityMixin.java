package com.github.suninvr.virtualadditions.mixin;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.Fluid;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import com.github.suninvr.virtualadditions.interfaces.EntityInterface;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VADamageSource;
import com.github.suninvr.virtualadditions.registry.VAFluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput, EntityInterface {

    @Shadow private Vec3d pos;

    @Shadow public World world;

    @Shadow protected boolean firstUpdate;

    @Shadow protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;

    @Shadow public float fallDistance;

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract boolean updateMovementInFluid(TagKey<Fluid> tag, double speed);

    @Inject(method = "getVelocityAffectingPos", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$getInsideVelocetyAffectingPos(CallbackInfoReturnable<BlockPos> cir) {
        BlockPos insidePos = new BlockPos(this.pos);
        if(this.world.getBlockState(insidePos).isIn(VABlockTags.INSIDE_VELOCITY_AFFECTING)) cir.setReturnValue(insidePos);
    }

    @Inject(method = "baseTick", at = @At("TAIL"))
    void virtualAdditions$baseTick(CallbackInfo ci) {

        if(this.isInAcid() && !(((Object)(this)) instanceof ItemEntity)) {
            this.damage(VADamageSource.ACID_BURN, 1F);
            this.fallDistance *= 0.5F;
        }
    }

    @Inject(method = "updateWaterState", at = @At("TAIL"), cancellable = true)
    void virtualAdditions$updateWaterState(CallbackInfoReturnable<Boolean> cir) {
        boolean bl = this.updateMovementInFluid(VAFluidTags.ACID, 0.0012);
        if(bl) {
            //System.out.println("moving in acid");
            cir.setReturnValue(true);
        };
    }


    public boolean isInAcid() {
        return !this.firstUpdate && this.fluidHeight.getDouble(VAFluidTags.ACID) > 0;
    }
}
