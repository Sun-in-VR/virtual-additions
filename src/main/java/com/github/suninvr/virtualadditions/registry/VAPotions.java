package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAPotions {
    public static final Holder<Potion> FRAILTY;
    public static final Holder<Potion> LONG_FRAILTY;
    public static final Holder<Potion> STRONG_FRAILTY;
    public static final Holder<Potion> LOVE;
    public static final Holder<Potion> LONG_LOVE;
    public static final Holder<Potion> SILENCE;
    public static final Holder<Potion> LOQUACITY;
    public static final Holder<Potion> AURA;
    public static final Holder<Potion> LONG_AURA;

    public static void init(){
        initBrewingRecipes();
    }

    static {
        FRAILTY = register("frailty", new Potion( "virtual_additions.frailty", new MobEffectInstance(VAStatusEffects.FRAILTY, 800)));
        LONG_FRAILTY = register("long_frailty", new Potion("virtual_additions.frailty", new MobEffectInstance(VAStatusEffects.FRAILTY, 1800)));
        STRONG_FRAILTY = register("strong_frailty", new Potion("virtual_additions.frailty", new MobEffectInstance(VAStatusEffects.FRAILTY, 400, 2)));
        LOVE = register("love", new Potion( "virtual_additions.love", new MobEffectInstance(VAStatusEffects.LOVE, 1800)));
        LONG_LOVE = register("long_love", new Potion( "virtual_additions.love", new MobEffectInstance(VAStatusEffects.LOVE, 4800)));
        SILENCE = register("silence", new Potion( "virtual_additions.silence", new MobEffectInstance(VAStatusEffects.SILENCE, 1)));
        LOQUACITY = register("loquacity", new Potion( "virtual_additions.loquacity", new MobEffectInstance(VAStatusEffects.LOQUACITY, 1)));
        AURA = register("aura", new Potion("virtual_additions.aura", new MobEffectInstance(VAStatusEffects.AURA, 800)));
        LONG_AURA = register("long_aura", new Potion("virtual_additions.aura", new MobEffectInstance(VAStatusEffects.AURA, 1800)));
    }

    private static Holder<Potion> register(String id, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, idOf(id), potion);
    }

    protected static void initBrewingRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {

            VirtualAdditions.skipBrewingRecipeAssert = true;

            builder.addContainer(VAItems.APPLICABLE_POTION);
            builder.addContainerRecipe(Items.POTION, VAItems.LUMWASP_MANDIBLE, VAItems.APPLICABLE_POTION);

            builder.addContainerRecipe(Items.POTION, VAItems.WISDOM_BERRY, Items.EXPERIENCE_BOTTLE);

            builder.addStartMix(VAItems.ROCK_SALT, FRAILTY);
            builder.addMix(FRAILTY, Items.REDSTONE, LONG_FRAILTY);
            builder.addMix(FRAILTY, Items.GLOWSTONE_DUST, STRONG_FRAILTY);

            builder.addStartMix(Items.ROSE_BUSH, LOVE);
            builder.addMix(LOVE, Items.REDSTONE, LONG_LOVE);

            builder.addStartMix(Items.ECHO_SHARD, SILENCE);
            builder.addMix(SILENCE, Items.FERMENTED_SPIDER_EYE, LOQUACITY);
            builder.addMix(LOQUACITY, Items.FERMENTED_SPIDER_EYE, SILENCE);

            builder.addStartMix(VAItems.SPECTRAL_POWDER, AURA);
            builder.addMix(AURA, Items.REDSTONE, LONG_AURA);

            VirtualAdditions.skipBrewingRecipeAssert = false;
        });
    }
}
