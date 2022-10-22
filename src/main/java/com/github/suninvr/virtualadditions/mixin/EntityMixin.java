package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.EntityInterface;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import net.minecraft.entity.Entity;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput, EntityInterface {

    @Shadow private Vec3d pos;

    @Shadow public World world;

    @Inject(method = "getVelocityAffectingPos", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$getInsideVelocetyAffectingPos(CallbackInfoReturnable<BlockPos> cir) {
        BlockPos insidePos = new BlockPos(this.pos);
        if(this.world.getBlockState(insidePos).isIn(VABlockTags.INSIDE_VELOCITY_AFFECTING)) cir.setReturnValue(insidePos);
    }
}
