package com.github.suninvr.virtualadditions.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAPotions {
    public static final RegistryEntry<Potion> FRAILTY;
    public static final RegistryEntry<Potion> LONG_FRAILTY;
    public static final RegistryEntry<Potion> STRONG_FRAILTY;

    public static void init(){
        initBrewingRecipes();
    }

    static {
        FRAILTY = register("frailty", new Potion( "virtual_additions.frailty", new StatusEffectInstance(VAStatusEffects.FRAILTY, 800)));
        LONG_FRAILTY = register("long_frailty", new Potion("virtual_additions.frailty", new StatusEffectInstance(VAStatusEffects.FRAILTY, 1800)));
        STRONG_FRAILTY = register("strong_frailty", new Potion("virtual_additions.frailty", new StatusEffectInstance(VAStatusEffects.FRAILTY, 400, 1)));
    }

    private static RegistryEntry<Potion> register(String id, Potion potion) {
        return Registry.registerReference(Registries.POTION, idOf(id), potion);
    }

    protected static void initBrewingRecipes() {
        BrewingRecipeRegistry.registerPotionType(VAItems.APPLICABLE_POTION);
        BrewingRecipeRegistry.registerItemRecipe(Items.POTION, VAItems.LUMWASP_MANDIBLE, VAItems.APPLICABLE_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, VAItems.ROCK_SALT, FRAILTY);
        BrewingRecipeRegistry.registerPotionRecipe(FRAILTY, Items.REDSTONE, LONG_FRAILTY);
        BrewingRecipeRegistry.registerPotionRecipe(FRAILTY, Items.GLOWSTONE_DUST, STRONG_FRAILTY);
    }
}