package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.registry.VABiomeKeys;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SurfaceRuleData.class)
public abstract class VanillaSurfaceRulesMixin {

    @Shadow @Final private static SurfaceRules.RuleSource LAVA;
    @Shadow @Final private static SurfaceRules.RuleSource WARPED_WART_BLOCK;
    @Shadow @Final private static SurfaceRules.RuleSource WARPED_NYLIUM;
    @Shadow @Final private static SurfaceRules.RuleSource BEDROCK;
    @Shadow @Final private static SurfaceRules.RuleSource NETHERRACK;

    @Shadow
    protected static SurfaceRules.RuleSource makeStateRule(Block block) {
        return null;
    }

    @Unique
    private static final SurfaceRules.RuleSource ANDESITE = makeStateRule(Blocks.ANDESITE);
    @Unique
    private static final SurfaceRules.RuleSource DIORITE = makeStateRule(Blocks.DIORITE);
    @Unique
    private static final SurfaceRules.RuleSource GRANITE = makeStateRule(Blocks.GRANITE);
    @Unique
    private static final SurfaceRules.RuleSource HORNFELS = makeStateRule(VABlocks.HORNFELS);
    @Unique
    private static final SurfaceRules.RuleSource BLUESCHIST = makeStateRule(VABlocks.BLUESCHIST);
    @Unique
    private static final SurfaceRules.RuleSource SYENITE = makeStateRule(VABlocks.SYENITE);
    @Unique
    private static final SurfaceRules.RuleSource NECROTIC_NYLIUM = makeStateRule(VABlocks.NECROTIC_NYLIUM);

    @Inject(method = "nether", at = @At("RETURN"), cancellable = true)
    private static void virtualAdditions$modifyNetherSurfaceRule(
            CallbackInfoReturnable<SurfaceRules.RuleSource> cir,
            @Local(ordinal = 0) SurfaceRules.ConditionSource above31,
            @Local(ordinal = 1) SurfaceRules.ConditionSource above32,
            @Local(ordinal = 2) SurfaceRules.ConditionSource above30StoneDepth,
            @Local(ordinal = 3) SurfaceRules.ConditionSource below35StoneDepth,
            @Local(ordinal = 4) SurfaceRules.ConditionSource belowTop5,
            @Local(ordinal = 5) SurfaceRules.ConditionSource hole,
            @Local(ordinal = 6) SurfaceRules.ConditionSource soulSandNoise,
            @Local(ordinal = 7) SurfaceRules.ConditionSource gravelNoise,
            @Local(ordinal = 8) SurfaceRules.ConditionSource patchNoise,
            @Local(ordinal = 9) SurfaceRules.ConditionSource netherrackNoise,
            @Local(ordinal = 10) SurfaceRules.ConditionSource netherWartNoise,
            @Local(ordinal = 11) SurfaceRules.ConditionSource netherStateSelectorNoise,
            @Local SurfaceRules.RuleSource gravelRule
    ) {

        SurfaceRules.ConditionSource notBedrockFloorCondition = SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)));
        SurfaceRules.ConditionSource notBedrockRoofCondition = SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top());

        SurfaceRules.RuleSource necroticNyliumRule = SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR,
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.not(above30StoneDepth), SurfaceRules.ifTrue(hole, LAVA)),
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(VABiomeKeys.WITHERED_WOODS),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.not(netherrackNoise),
                                        SurfaceRules.ifTrue(above31, NECROTIC_NYLIUM)
                                )
                        )
                )
        );

        necroticNyliumRule = SurfaceRules.ifTrue(
                notBedrockFloorCondition, SurfaceRules.ifTrue(notBedrockRoofCondition, necroticNyliumRule));

        cir.setReturnValue(
                SurfaceRules.sequence(
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
    private static SurfaceRules.RuleSource createAlternateStoneRule(SurfaceRules.RuleSource upperStateRule, SurfaceRules.RuleSource lowerStateRule, ResourceKey<NormalNoise.NoiseParameters> noiseParameters) {
        SurfaceRules.RuleSource rule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)),
                        lowerStateRule
                ),
                upperStateRule
        );
        return SurfaceRules.ifTrue(SurfaceRules.noiseCondition(noiseParameters, 0.25, 0.9), rule);
    }
}
