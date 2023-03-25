package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.LivingEntityInterface;
import com.github.suninvr.virtualadditions.item.enums.GildType;
import com.github.suninvr.virtualadditions.item.interfaces.GildedToolItem;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityInterface {

    @Shadow protected boolean dead;

    private float experienceMultiplier = 1.0F;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    void virtualAdditions$setExperienceMultiplier(DamageSource source, CallbackInfo ci) {
        if (!this.isRemoved() && !this.dead && !((LivingEntity)(Object)(this) instanceof PlayerEntity)) {
            Entity entity = source.getAttacker();
            if (entity instanceof PlayerEntity playerEntity) {
                if (GildedToolItem.getGildType(playerEntity.getMainHandStack()).equals(GildType.EMERALD)) {
                    this.experienceMultiplier = 1.6F;
                }
            }
        }
    }

    @Inject(method = "isClimbing", at = @At("HEAD"), cancellable = true)
    void virtualAdditions$isClimbingRope(CallbackInfoReturnable<Boolean> cir) {
        if (!this.isSpectator() && !(this.world == null)) {
            if (this.getBlockStateAtPos().getCollisionShape(this.world, this.getBlockPos()).equals(VoxelShapes.empty())) {
                BlockPos blockPos = this.getBlockPos().down();
                BlockState state = this.world.getBlockState(blockPos);
                if (state.isOf(VABlocks.CLIMBING_ROPE_ANCHOR)) cir.setReturnValue(true);
            }
        }
    }

    @Override
    public float getXpModifier() {
        return this.experienceMultiplier;
    }
}
