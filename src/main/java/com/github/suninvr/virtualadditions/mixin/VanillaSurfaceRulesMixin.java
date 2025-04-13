package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABiomeKeys;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;
import org.spongepowered.asm.mixin.Final;
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

    @Shadow @Final private static MaterialRules.MaterialRule LAVA;
    @Shadow @Final private static MaterialRules.MaterialRule WARPED_WART_BLOCK;
    @Shadow @Final private static MaterialRules.MaterialRule WARPED_NYLIUM;
    @Shadow @Final private static MaterialRules.MaterialRule BEDROCK;
    @Shadow @Final private static MaterialRules.MaterialRule NETHERRACK;
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
    @Unique
    private static final MaterialRules.MaterialRule NECROTIC_NYLIUM = block(VABlocks.NECROTIC_NYLIUM);

    @Inject(method = "createNetherSurfaceRule", at = @At("RETURN"), cancellable = true)
    private static void virtualAdditions$modifyNetherSurfaceRule(
            CallbackInfoReturnable<MaterialRules.MaterialRule> cir,
            @Local(ordinal = 0) MaterialRules.MaterialCondition above31,
            @Local(ordinal = 1) MaterialRules.MaterialCondition above32,
            @Local(ordinal = 2) MaterialRules.MaterialCondition above30StoneDepth,
            @Local(ordinal = 3) MaterialRules.MaterialCondition below35StoneDepth,
            @Local(ordinal = 4) MaterialRules.MaterialCondition belowTop5,
            @Local(ordinal = 5) MaterialRules.MaterialCondition hole,
            @Local(ordinal = 6) MaterialRules.MaterialCondition soulSandNoise,
            @Local(ordinal = 7) MaterialRules.MaterialCondition gravelNoise,
            @Local(ordinal = 8) MaterialRules.MaterialCondition patchNoise,
            @Local(ordinal = 9) MaterialRules.MaterialCondition netherrackNoise,
            @Local(ordinal = 10) MaterialRules.MaterialCondition netherWartNoise,
            @Local(ordinal = 11) MaterialRules.MaterialCondition netherStateSelectorNoise,
            @Local MaterialRules.MaterialRule gravelRule
    ) {

        MaterialRules.MaterialCondition notBedrockFloorCondition = MaterialRules.not(MaterialRules.verticalGradient("bedrock_floor", YOffset.getBottom(), YOffset.aboveBottom(5)));
        MaterialRules.MaterialCondition notBedrockRoofCondition = MaterialRules.verticalGradient("bedrock_roof", YOffset.belowTop(5), YOffset.getTop());

        MaterialRules.MaterialRule necroticNyliumRule = MaterialRules.condition(
                MaterialRules.STONE_DEPTH_FLOOR,
                MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.not(above30StoneDepth), MaterialRules.condition(hole, LAVA)),
                        MaterialRules.condition(
                                MaterialRules.biome(VABiomeKeys.WITHERED_WOODS),
                                MaterialRules.condition(
                                        MaterialRules.not(netherrackNoise),
                                        MaterialRules.condition(above31, NECROTIC_NYLIUM)
                                )
                        )
                )
        );

        necroticNyliumRule = MaterialRules.condition(
                notBedrockFloorCondition, MaterialRules.condition(notBedrockRoofCondition, necroticNyliumRule));

        cir.setReturnValue(
                MaterialRules.sequence(
                        necroticNyliumRule,
                        cir.getReturnValue()
                )
        );
    }

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
