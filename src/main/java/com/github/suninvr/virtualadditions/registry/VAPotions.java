package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAPotions {
    public static final RegistryEntry<Potion> FRAILTY;
    public static final RegistryEntry<Potion> LONG_FRAILTY;
    public static final RegistryEntry<Potion> STRONG_FRAILTY;
    public static final RegistryEntry<Potion> LOVE;
    public static final RegistryEntry<Potion> LONG_LOVE;
    public static final RegistryEntry<Potion> SILENCE;
    public static final RegistryEntry<Potion> LOQUACITY;
    public static final RegistryEntry<Potion> AURA;
    public static final RegistryEntry<Potion> LONG_AURA;

    public static void init(){
        initBrewingRecipes();
    }

    static {
        FRAILTY = register("frailty", new Potion( "virtual_additions.frailty", new StatusEffectInstance(VAStatusEffects.FRAILTY, 800)));
        LONG_FRAILTY = register("long_frailty", new Potion("virtual_additions.frailty", new StatusEffectInstance(VAStatusEffects.FRAILTY, 1800)));
        STRONG_FRAILTY = register("strong_frailty", new Potion("virtual_additions.frailty", new StatusEffectInstance(VAStatusEffects.FRAILTY, 400, 2)));
        LOVE = register("love", new Potion( "virtual_additions.love", new StatusEffectInstance(VAStatusEffects.LOVE, 1800)));
        LONG_LOVE = register("long_love", new Potion( "virtual_additions.love", new StatusEffectInstance(VAStatusEffects.LOVE, 4800)));
        SILENCE = register("silence", new Potion( "virtual_additions.silence", new StatusEffectInstance(VAStatusEffects.SILENCE, 1)));
        LOQUACITY = register("loquacity", new Potion( "virtual_additions.loquacity", new StatusEffectInstance(VAStatusEffects.LOQUACITY, 1)));
        AURA = register("aura", new Potion("virtual_additions.aura", new StatusEffectInstance(VAStatusEffects.AURA, 800)));
        LONG_AURA = register("long_aura", new Potion("virtual_additions.aura", new StatusEffectInstance(VAStatusEffects.AURA, 1800)));
    }

    private static RegistryEntry<Potion> register(String id, Potion potion) {
        return Registry.registerReference(Registries.POTION, idOf(id), potion);
    }

    protected static void initBrewingRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {

            VirtualAdditions.skipBrewingRecipeAssert = true;

            builder.registerPotionType(VAItems.APPLICABLE_POTION);
            builder.registerItemRecipe(Items.POTION, VAItems.LUMWASP_MANDIBLE, VAItems.APPLICABLE_POTION);

            builder.registerItemRecipe(Items.POTION, VAItems.WISDOM_BERRY, Items.EXPERIENCE_BOTTLE);

            builder.registerRecipes(VAItems.ROCK_SALT, FRAILTY);
            builder.registerPotionRecipe(FRAILTY, Items.REDSTONE, LONG_FRAILTY);
            builder.registerPotionRecipe(FRAILTY, Items.GLOWSTONE_DUST, STRONG_FRAILTY);

            builder.registerRecipes(Items.ROSE_BUSH, LOVE);
            builder.registerPotionRecipe(LOVE, Items.REDSTONE, LONG_LOVE);

            builder.registerRecipes(Items.ECHO_SHARD, SILENCE);
            builder.registerPotionRecipe(SILENCE, Items.FERMENTED_SPIDER_EYE, LOQUACITY);
            builder.registerPotionRecipe(LOQUACITY, Items.FERMENTED_SPIDER_EYE, SILENCE);

            builder.registerRecipes(VAItems.SPECTRAL_POWDER, AURA);
            builder.registerPotionRecipe(AURA, Items.REDSTONE, LONG_AURA);

            VirtualAdditions.skipBrewingRecipeAssert = false;
        });
    }
}
