package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VANoiseParameters;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VanillaSurfaceRules.class)
public abstract class VanillaSurfaceRulesMixin {
    @Shadow
    private static MaterialRules.MaterialRule block(Block block) {
        return null;
    }

    @Unique
    private static final MaterialRules.MaterialRule ANDESITE = block(Blocks.ANDESITE);
    @Unique
    private static final MaterialRules.MaterialRule DIORITE = block(Blocks.DIORITE);
    @Unique
    private static final MaterialRules.MaterialRule GRANITE = block(Blocks.GRANITE);
    @Unique
    private static final MaterialRules.MaterialRule HORNFELS = block(VABlocks.HORNFELS);
    @Unique
    private static final MaterialRules.MaterialRule BLUESCHIST = block(VABlocks.BLUESCHIST);
    @Unique
    private static final MaterialRules.MaterialRule SYENITE = block(VABlocks.SYENITE);

    //@Inject(method = "createDefaultRule", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList$Builder;add(Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList$Builder;", ordinal = 2, shift = At.Shift.AFTER))
    //private static void virtualAdditons$createAlternateStonesRule(boolean surface, boolean bedrockRoof, boolean bedrockFloor, CallbackInfoReturnable<MaterialRules.MaterialRule> cir, @Local() LocalRef<ImmutableList.Builder<MaterialRules.MaterialRule>> builder) {
    //    MaterialRules.MaterialRule rule = MaterialRules.condition(
    //            MaterialRules.verticalGradient("virtual_additions:alternate_stone", YOffset.fixed(40), YOffset.fixed(48)),
    //            MaterialRules.sequence(
    //                    createAlternateStoneRule(ANDESITE, HORNFELS, VANoiseParameters.ANDESITE),
    //                    createAlternateStoneRule(DIORITE, BLUESCHIST, VANoiseParameters.DIORITE),
    //                    createAlternateStoneRule(GRANITE, SYENITE, VANoiseParameters.GRANITE)

    //            )
    //    );
    //    builder.set(builder.get().add(rule));
    //}

    @Unique
    private static MaterialRules.MaterialRule createAlternateStoneRule(MaterialRules.MaterialRule upperStateRule, MaterialRules.MaterialRule lowerStateRule, RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        MaterialRules.MaterialRule rule = MaterialRules.sequence(
                MaterialRules.condition(
                        MaterialRules.verticalGradient("deepslate", YOffset.fixed(0), YOffset.fixed(8)),
                        lowerStateRule
                ),
                upperStateRule
        );
        return MaterialRules.condition(MaterialRules.noiseThreshold(noiseParameters, 0.25, 0.9), rule);
    }
}
