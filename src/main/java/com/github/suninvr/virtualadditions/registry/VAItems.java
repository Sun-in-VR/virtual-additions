package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import com.github.suninvr.virtualadditions.entity.SteelBombEntity;
import com.github.suninvr.virtualadditions.item.*;
import com.github.suninvr.virtualadditions.item.enums.GildType;
import com.github.suninvr.virtualadditions.item.materials.SteelToolMaterial;
import com.github.suninvr.virtualadditions.mixin.ComposterBlockAccessor;
import com.github.suninvr.virtualadditions.registry.constructors.item.CustomArmorMaterials;
import com.github.suninvr.virtualadditions.registry.constructors.item.CustomAxeItem;
import com.github.suninvr.virtualadditions.registry.constructors.item.CustomHoeItem;
import com.github.suninvr.virtualadditions.registry.constructors.item.CustomPickaxeItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.ExplosionDecayLootFunction;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Locale;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAItems {
    protected record ToolSet(Item SWORD, Item SHOVEL, Item PICKAXE, Item AXE, Item HOE, String NAME){}
    protected record ItemGroupLocation(ItemGroup GROUP, Item AFTER){}
    public static final ToolSet DIAMOND_TOOL_SET;
    public static final ToolSet GOLDEN_TOOL_SET;
    public static final ToolSet IRON_TOOL_SET;
    public static final ToolSet NETHERITE_TOOL_SET;
    public static final ToolSet STEEL_TOOL_SET;
    public static final Item CLIMBING_ROPE;
    public static final Item HORNFELS;
    public static final Item COBBLED_HORNFELS;
    public static final Item COBBLED_HORNFELS_STAIRS;
    public static final Item COBBLED_HORNFELS_SLAB;
    public static final Item COBBLED_HORNFELS_WALL;
    public static final Item POLISHED_HORNFELS;
    public static final Item POLISHED_HORNFELS_STAIRS;
    public static final Item POLISHED_HORNFELS_SLAB;
    public static final Item HORNFELS_TILES;
    public static final Item CRACKED_HORNFELS_TILES;
    public static final Item HORNFELS_TILE_STAIRS;
    public static final Item HORNFELS_TILE_SLAB;
    public static final Item BLUESCHIST;
    public static final Item COBBLED_BLUESCHIST;
    public static final Item COBBLED_BLUESCHIST_STAIRS;
    public static final Item COBBLED_BLUESCHIST_SLAB;
    public static final Item COBBLED_BLUESCHIST_WALL;
    public static final Item POLISHED_BLUESCHIST;
    public static final Item POLISHED_BLUESCHIST_STAIRS;
    public static final Item POLISHED_BLUESCHIST_SLAB;
    public static final Item BLUESCHIST_BRICKS;
    public static final Item CRACKED_BLUESCHIST_BRICKS;
    public static final Item BLUESCHIST_BRICK_STAIRS;
    public static final Item BLUESCHIST_BRICK_SLAB;
    public static final Item BLUESCHIST_BRICK_WALL;
    public static final Item SYENITE;
    public static final Item COBBLED_SYENITE;
    public static final Item COBBLED_SYENITE_STAIRS;
    public static final Item COBBLED_SYENITE_SLAB;
    public static final Item COBBLED_SYENITE_WALL;
    public static final Item POLISHED_SYENITE;
    public static final Item POLISHED_SYENITE_STAIRS;
    public static final Item POLISHED_SYENITE_SLAB;
    public static final Item SYENITE_BRICKS;
    public static final Item CRACKED_SYENITE_BRICKS;
    public static final Item SYENITE_BRICK_STAIRS;
    public static final Item SYENITE_BRICK_SLAB;
    public static final Item SYENITE_BRICK_WALL;
    public static final Item RED_GLIMMER_CRYSTAL;
    public static final Item GREEN_GLIMMER_CRYSTAL;
    public static final Item BLUE_GLIMMER_CRYSTAL;
    public static final Item CRYSTAL_DUST;
    public static final Item SPOTLIGHT;
    public static final Item RAW_STEEL;
    public static final Item RAW_STEEL_BLOCK;
    public static final Item STEEL_INGOT;
    public static final Item STEEL_BLOCK;
    public static final Item STEEL_BOMB;
    public static final Item STEEL_SWORD;
    public static final Item STEEL_SHOVEL;
    public static final Item STEEL_PICKAXE;
    public static final Item STEEL_AXE;
    public static final Item STEEL_HOE;
    public static final Item STEEL_HELMET;
    public static final Item STEEL_CHESTPLATE;
    public static final Item STEEL_LEGGINGS;
    public static final Item STEEL_BOOTS;
    public static final Item COTTON_SEEDS;
    public static final Item COTTON;
    public static final Item FRIED_EGG;
    public static final Item APPLICABLE_POTION;

    public static final FoodComponent FRIED_EGG_FOOD = (new FoodComponent.Builder().hunger(3).saturationModifier(0.4F).snack().build());

    public static final ToolSet AMETHYST_DIAMOND_TOOL_SET;
    public static final ToolSet COPPER_DIAMOND_TOOL_SET;
    public static final ToolSet EMERALD_DIAMOND_TOOL_SET;
    public static final ToolSet QUARTZ_DIAMOND_TOOL_SET;
    public static final ToolSet AMETHYST_IRON_TOOL_SET;
    public static final ToolSet COPPER_IRON_TOOL_SET;
    public static final ToolSet EMERALD_IRON_TOOL_SET ;
    public static final ToolSet QUARTZ_IRON_TOOL_SET;
    public static final ToolSet AMETHYST_GOLDEN_TOOL_SET;
    public static final ToolSet COPPER_GOLDEN_TOOL_SET;
    public static final ToolSet EMERALD_GOLDEN_TOOL_SET;
    public static final ToolSet QUARTZ_GOLDEN_TOOL_SET;
    public static final ToolSet AMETHYST_NETHERITE_TOOL_SET;
    public static final ToolSet COPPER_NETHERITE_TOOL_SET;
    public static final ToolSet EMERALD_NETHERITE_TOOL_SET;
    public static final ToolSet QUARTZ_NETHERITE_TOOL_SET;
    public static final ToolSet AMETHYST_STEEL_TOOL_SET;
    public static final ToolSet COPPER_STEEL_TOOL_SET;
    public static final ToolSet EMERALD_STEEL_TOOL_SET;
    public static final ToolSet QUARTZ_STEEL_TOOL_SET;

    static {
        CLIMBING_ROPE = register("climbing_rope", new AliasedBlockItem(VABlocks.CLIMBING_ROPE_ANCHOR, new FabricItemSettings().maxCount(16)), ItemGroups.TOOLS, Items.LEAD);

        HORNFELS = registerBlockItem("hornfels", VABlocks.HORNFELS, new ItemGroupLocation(ItemGroups.NATURAL, Items.DEEPSLATE), new ItemGroupLocation(ItemGroups.BUILDING_BLOCKS, Items.DEEPSLATE_TILE_WALL));
        COBBLED_HORNFELS = registerBlockItem("cobbled_hornfels", VABlocks.COBBLED_HORNFELS, ItemGroups.BUILDING_BLOCKS, HORNFELS);
        COBBLED_HORNFELS_STAIRS = registerBlockItem("cobbled_hornfels_stairs", VABlocks.COBBLED_HORNFELS_STAIRS, ItemGroups.BUILDING_BLOCKS, COBBLED_HORNFELS);
        COBBLED_HORNFELS_SLAB = registerBlockItem("cobbled_hornfels_slab", VABlocks.COBBLED_HORNFELS_SLAB, ItemGroups.BUILDING_BLOCKS, COBBLED_HORNFELS_STAIRS);
        COBBLED_HORNFELS_WALL = registerBlockItem("cobbled_hornfels_wall", VABlocks.COBBLED_HORNFELS_WALL, ItemGroups.BUILDING_BLOCKS, COBBLED_HORNFELS_SLAB);
        POLISHED_HORNFELS = registerBlockItem("polished_hornfels", VABlocks.POLISHED_HORNFELS, ItemGroups.BUILDING_BLOCKS, COBBLED_HORNFELS_WALL);
        POLISHED_HORNFELS_STAIRS = registerBlockItem("polished_hornfels_stairs", VABlocks.POLISHED_HORNFELS_STAIRS, ItemGroups.BUILDING_BLOCKS, POLISHED_HORNFELS);
        POLISHED_HORNFELS_SLAB = registerBlockItem("polished_hornfels_slab", VABlocks.POLISHED_HORNFELS_SLAB, ItemGroups.BUILDING_BLOCKS, POLISHED_HORNFELS_STAIRS);
        HORNFELS_TILES = registerBlockItem("hornfels_tiles", VABlocks.HORNFELS_TILES, ItemGroups.BUILDING_BLOCKS, POLISHED_HORNFELS_SLAB);
        CRACKED_HORNFELS_TILES = registerBlockItem("cracked_hornfels_tiles", VABlocks.CRACKED_HORNFELS_TILES, ItemGroups.BUILDING_BLOCKS, HORNFELS_TILES);
        HORNFELS_TILE_STAIRS = registerBlockItem("hornfels_tile_stairs", VABlocks.HORNFELS_TILE_STAIRS, ItemGroups.BUILDING_BLOCKS, CRACKED_HORNFELS_TILES);
        HORNFELS_TILE_SLAB = registerBlockItem("hornfels_tile_slab", VABlocks.HORNFELS_TILE_SLAB, ItemGroups.BUILDING_BLOCKS, HORNFELS_TILE_STAIRS);

        BLUESCHIST = registerBlockItem("blueschist", VABlocks.BLUESCHIST, new ItemGroupLocation(ItemGroups.NATURAL, HORNFELS), new ItemGroupLocation(ItemGroups.BUILDING_BLOCKS, HORNFELS_TILE_SLAB));
        COBBLED_BLUESCHIST = registerBlockItem("cobbled_blueschist", VABlocks.COBBLED_BLUESCHIST, ItemGroups.BUILDING_BLOCKS, BLUESCHIST);
        COBBLED_BLUESCHIST_STAIRS = registerBlockItem("cobbled_blueschist_stairs", VABlocks.COBBLED_BLUESCHIST_STAIRS, ItemGroups.BUILDING_BLOCKS, COBBLED_BLUESCHIST);
        COBBLED_BLUESCHIST_SLAB = registerBlockItem("cobbled_blueschist_slab", VABlocks.COBBLED_BLUESCHIST_SLAB, ItemGroups.BUILDING_BLOCKS, COBBLED_BLUESCHIST_STAIRS);
        COBBLED_BLUESCHIST_WALL = registerBlockItem("cobbled_blueschist_wall", VABlocks.COBBLED_BLUESCHIST_WALL, ItemGroups.BUILDING_BLOCKS, COBBLED_BLUESCHIST_SLAB);
        POLISHED_BLUESCHIST = registerBlockItem("polished_blueschist", VABlocks.POLISHED_BLUESCHIST, ItemGroups.BUILDING_BLOCKS, COBBLED_BLUESCHIST_WALL);
        POLISHED_BLUESCHIST_STAIRS = registerBlockItem("polished_blueschist_stairs", VABlocks.POLISHED_BLUESCHIST_STAIRS, ItemGroups.BUILDING_BLOCKS, POLISHED_BLUESCHIST);
        POLISHED_BLUESCHIST_SLAB = registerBlockItem("polished_blueschist_slab", VABlocks.POLISHED_BLUESCHIST_SLAB, ItemGroups.BUILDING_BLOCKS, POLISHED_BLUESCHIST_STAIRS);
        BLUESCHIST_BRICKS = registerBlockItem("blueschist_bricks", VABlocks.BLUESCHIST_BRICKS, ItemGroups.BUILDING_BLOCKS, POLISHED_BLUESCHIST_SLAB);
        CRACKED_BLUESCHIST_BRICKS = registerBlockItem("cracked_blueschist_bricks", VABlocks.CRACKED_BLUESCHIST_BRICKS, ItemGroups.BUILDING_BLOCKS, BLUESCHIST_BRICKS);
        BLUESCHIST_BRICK_STAIRS = registerBlockItem("blueschist_brick_stairs", VABlocks.BLUESCHIST_BRICK_STAIRS, ItemGroups.BUILDING_BLOCKS, CRACKED_BLUESCHIST_BRICKS);
        BLUESCHIST_BRICK_SLAB = registerBlockItem("blueschist_brick_slab", VABlocks.BLUESCHIST_BRICK_SLAB, ItemGroups.BUILDING_BLOCKS, BLUESCHIST_BRICK_STAIRS);
        BLUESCHIST_BRICK_WALL = registerBlockItem("blueschist_brick_wall", VABlocks.BLUESCHIST_BRICK_WALL, ItemGroups.BUILDING_BLOCKS, BLUESCHIST_BRICK_SLAB);

        SYENITE = registerBlockItem("syenite", VABlocks.SYENITE, new ItemGroupLocation(ItemGroups.NATURAL, BLUESCHIST), new ItemGroupLocation(ItemGroups.BUILDING_BLOCKS, BLUESCHIST_BRICK_WALL));
        COBBLED_SYENITE = registerBlockItem("cobbled_syenite", VABlocks.COBBLED_SYENITE, ItemGroups.BUILDING_BLOCKS, SYENITE);
        COBBLED_SYENITE_STAIRS = registerBlockItem("cobbled_syenite_stairs", VABlocks.COBBLED_SYENITE_STAIRS, ItemGroups.BUILDING_BLOCKS, COBBLED_SYENITE);
        COBBLED_SYENITE_SLAB = registerBlockItem("cobbled_syenite_slab", VABlocks.COBBLED_SYENITE_SLAB, ItemGroups.BUILDING_BLOCKS, COBBLED_SYENITE_STAIRS);
        COBBLED_SYENITE_WALL = registerBlockItem("cobbled_syenite_wall", VABlocks.COBBLED_SYENITE_WALL, ItemGroups.BUILDING_BLOCKS, COBBLED_SYENITE_SLAB);
        POLISHED_SYENITE = registerBlockItem("polished_syenite", VABlocks.POLISHED_SYENITE, ItemGroups.BUILDING_BLOCKS, COBBLED_SYENITE_WALL);
        POLISHED_SYENITE_STAIRS = registerBlockItem("polished_syenite_stairs", VABlocks.POLISHED_SYENITE_STAIRS, ItemGroups.BUILDING_BLOCKS, POLISHED_SYENITE);
        POLISHED_SYENITE_SLAB = registerBlockItem("polished_syenite_slab", VABlocks.POLISHED_SYENITE_SLAB, ItemGroups.BUILDING_BLOCKS, POLISHED_SYENITE_STAIRS);
        SYENITE_BRICKS = registerBlockItem("syenite_bricks", VABlocks.SYENITE_BRICKS, ItemGroups.BUILDING_BLOCKS, POLISHED_SYENITE_SLAB);
        CRACKED_SYENITE_BRICKS = registerBlockItem("cracked_syenite_bricks", VABlocks.CRACKED_SYENITE_BRICKS, ItemGroups.BUILDING_BLOCKS, SYENITE_BRICKS);
        SYENITE_BRICK_STAIRS = registerBlockItem("syenite_brick_stairs", VABlocks.SYENITE_BRICK_STAIRS, ItemGroups.BUILDING_BLOCKS, CRACKED_SYENITE_BRICKS);
        SYENITE_BRICK_SLAB = registerBlockItem("syenite_brick_slab", VABlocks.SYENITE_BRICK_SLAB, ItemGroups.BUILDING_BLOCKS, SYENITE_BRICK_STAIRS);
        SYENITE_BRICK_WALL = registerBlockItem("syenite_brick_wall", VABlocks.SYENITE_BRICK_WALL, ItemGroups.BUILDING_BLOCKS, SYENITE_BRICK_SLAB);

        RED_GLIMMER_CRYSTAL = registerBlockItem("red_glimmer_crystal", VABlocks.RED_GLIMMER_CRYSTAL, ItemGroups.NATURAL, Items.POINTED_DRIPSTONE);
        GREEN_GLIMMER_CRYSTAL = registerBlockItem("green_glimmer_crystal", VABlocks.GREEN_GLIMMER_CRYSTAL, ItemGroups.NATURAL, RED_GLIMMER_CRYSTAL);
        BLUE_GLIMMER_CRYSTAL = registerBlockItem("blue_glimmer_crystal", VABlocks.BLUE_GLIMMER_CRYSTAL, ItemGroups.NATURAL, GREEN_GLIMMER_CRYSTAL);
        CRYSTAL_DUST = register("crystal_dust", ItemGroups.CRAFTING, Items.GUNPOWDER);
        SPOTLIGHT = registerBlockItem("spotlight", VABlocks.SPOTLIGHT, ItemGroups.REDSTONE, Items.REDSTONE_LAMP);

        RAW_STEEL_BLOCK = registerBlockItem("raw_steel_block", VABlocks.RAW_STEEL_BLOCK, ItemGroups.NATURAL, Items.RAW_GOLD_BLOCK);
        STEEL_BLOCK = registerBlockItem("steel_block", VABlocks.STEEL_BLOCK, ItemGroups.BUILDING_BLOCKS, Items.GOLD_BLOCK);
        RAW_STEEL = register("raw_steel", ItemGroups.CRAFTING, Items.RAW_GOLD);
        STEEL_INGOT = register("steel_ingot", ItemGroups.CRAFTING, Items.GOLD_INGOT);
        STEEL_BOMB = register("steel_bomb", new SteelBombItem(new FabricItemSettings().maxCount(16)), new ItemGroupLocation(ItemGroups.COMBAT, Items.SNOWBALL), new ItemGroupLocation(ItemGroups.TOOLS, CLIMBING_ROPE));
        STEEL_SWORD = register("steel_sword", new SwordItem(SteelToolMaterial.INSTANCE, 3, -2.4F, new FabricItemSettings()), ItemGroups.COMBAT, Items.GOLDEN_SWORD);
        STEEL_SHOVEL = register("steel_shovel", new ShovelItem(SteelToolMaterial.INSTANCE, 1.5F, -3.0F, new FabricItemSettings()), ItemGroups.TOOLS, Items.GOLDEN_HOE);
        STEEL_PICKAXE = register("steel_pickaxe", new CustomPickaxeItem(SteelToolMaterial.INSTANCE, 1, -2.8F, new FabricItemSettings()), ItemGroups.TOOLS, STEEL_SHOVEL);
        STEEL_AXE = register("steel_axe", new CustomAxeItem(SteelToolMaterial.INSTANCE, 6.0F, -3.1F, new FabricItemSettings()), ItemGroups.TOOLS, STEEL_PICKAXE);
        STEEL_HOE = register("steel_hoe", new CustomHoeItem(SteelToolMaterial.INSTANCE, -2, -1.0F, new FabricItemSettings()), ItemGroups.TOOLS, STEEL_AXE);
        STEEL_HELMET = register("steel_helmet", new ArmorItem(CustomArmorMaterials.STEEL, EquipmentSlot.HEAD, new Item.Settings()), ItemGroups.COMBAT, Items.GOLDEN_BOOTS);
        STEEL_CHESTPLATE = register("steel_chestplate", new ArmorItem(CustomArmorMaterials.STEEL, EquipmentSlot.CHEST, new Item.Settings()), ItemGroups.COMBAT, STEEL_HELMET);
        STEEL_LEGGINGS = register("steel_leggings", new ArmorItem(CustomArmorMaterials.STEEL, EquipmentSlot.LEGS, new Item.Settings()), ItemGroups.COMBAT, STEEL_CHESTPLATE);
        STEEL_BOOTS = register("steel_boots", new ArmorItem(CustomArmorMaterials.STEEL, EquipmentSlot.FEET, new Item.Settings()), ItemGroups.COMBAT, STEEL_LEGGINGS);

        COTTON_SEEDS = register("cotton_seeds", new AliasedBlockItem(VABlocks.COTTON, new FabricItemSettings()), ItemGroups.NATURAL, Items.BEETROOT_SEEDS);
        COTTON = register("cotton", ItemGroups.CRAFTING, Items.WHEAT);
        FRIED_EGG = register("fried_egg", new Item(new FabricItemSettings().food(FRIED_EGG_FOOD)), ItemGroups.CONSUMABLES, Items.PUMPKIN_PIE);

        APPLICABLE_POTION = register("applicable_potion", new ApplicablePotionItem(new FabricItemSettings()));

        DIAMOND_TOOL_SET = new ToolSet(Items.DIAMOND_SWORD, Items.DIAMOND_SHOVEL, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_HOE, "diamond");
        GOLDEN_TOOL_SET = new ToolSet(Items.GOLDEN_SWORD, Items.GOLDEN_SHOVEL, Items.GOLDEN_PICKAXE, Items.GOLDEN_AXE, Items.GOLDEN_HOE, "golden");
        IRON_TOOL_SET = new ToolSet(Items.IRON_SWORD, Items.IRON_SHOVEL, Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_HOE, "iron");
        NETHERITE_TOOL_SET = new ToolSet(Items.NETHERITE_SWORD, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_HOE, "netherite");
        STEEL_TOOL_SET = new ToolSet(STEEL_SWORD, STEEL_SHOVEL, STEEL_PICKAXE, STEEL_AXE, STEEL_HOE, "steel");

        AMETHYST_DIAMOND_TOOL_SET = registerGildedToolSet(DIAMOND_TOOL_SET, GildType.AMETHYST);
        COPPER_DIAMOND_TOOL_SET = registerGildedToolSet(DIAMOND_TOOL_SET, GildType.COPPER);
        EMERALD_DIAMOND_TOOL_SET = registerGildedToolSet(DIAMOND_TOOL_SET, GildType.EMERALD);
        QUARTZ_DIAMOND_TOOL_SET = registerGildedToolSet(DIAMOND_TOOL_SET, GildType.QUARTZ);
        AMETHYST_IRON_TOOL_SET = registerGildedToolSet(IRON_TOOL_SET, GildType.AMETHYST);
        COPPER_IRON_TOOL_SET = registerGildedToolSet(IRON_TOOL_SET, GildType.COPPER);
        EMERALD_IRON_TOOL_SET = registerGildedToolSet(IRON_TOOL_SET, GildType.EMERALD);
        QUARTZ_IRON_TOOL_SET = registerGildedToolSet(IRON_TOOL_SET, GildType.QUARTZ);
        AMETHYST_GOLDEN_TOOL_SET = registerGildedToolSet(GOLDEN_TOOL_SET, GildType.AMETHYST);
        COPPER_GOLDEN_TOOL_SET = registerGildedToolSet(GOLDEN_TOOL_SET, GildType.COPPER);
        EMERALD_GOLDEN_TOOL_SET = registerGildedToolSet(GOLDEN_TOOL_SET, GildType.EMERALD);
        QUARTZ_GOLDEN_TOOL_SET = registerGildedToolSet(GOLDEN_TOOL_SET, GildType.QUARTZ);
        AMETHYST_NETHERITE_TOOL_SET = registerGildedToolSet(NETHERITE_TOOL_SET, GildType.AMETHYST);
        COPPER_NETHERITE_TOOL_SET = registerGildedToolSet(NETHERITE_TOOL_SET, GildType.COPPER);
        EMERALD_NETHERITE_TOOL_SET = registerGildedToolSet(NETHERITE_TOOL_SET, GildType.EMERALD);
        QUARTZ_NETHERITE_TOOL_SET = registerGildedToolSet(NETHERITE_TOOL_SET, GildType.QUARTZ);
        AMETHYST_STEEL_TOOL_SET = registerGildedToolSet(STEEL_TOOL_SET, GildType.AMETHYST);
        COPPER_STEEL_TOOL_SET = registerGildedToolSet(STEEL_TOOL_SET, GildType.COPPER);
        EMERALD_STEEL_TOOL_SET = registerGildedToolSet(STEEL_TOOL_SET, GildType.EMERALD);
        QUARTZ_STEEL_TOOL_SET = registerGildedToolSet(STEEL_TOOL_SET, GildType.QUARTZ);
    }

    public static void init(){
        //Dispenser Behaviors
        registerDispenserBehavior(STEEL_BOMB, new ProjectileDispenserBehavior() {
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new SteelBombEntity(world, position.getX(), position.getY(), position.getZ()), (steelBombEntity) -> steelBombEntity.setItem(stack));
            }
        });

        registerDispenserBehavior(CLIMBING_ROPE, new ProjectileDispenserBehavior() {
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                ClimbingRopeEntity climbingRopeEntity = new ClimbingRopeEntity( position.getX(), position.getY(), position.getZ(), world);
                climbingRopeEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
                return climbingRopeEntity;
            }
        });

        //Compostable Items
        ComposterBlockAccessor.virtualAdditions$registerCompostableItem(0.3F, COTTON_SEEDS);
        ComposterBlockAccessor.virtualAdditions$registerCompostableItem(0.3F, COTTON);

        //Modified Loot Tables
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && Blocks.GRASS.getLootTableId().equals(id)) {
                LootPool.Builder builder = LootPool.builder()
                        .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE, 2))
                        .apply(ExplosionDecayLootFunction.builder())
                        .conditionally(RandomChanceLootCondition.builder(0.125F))
                        .conditionally(InvertedLootCondition.builder(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS))))
                        .with(ItemEntry.builder(COTTON_SEEDS));
                tableBuilder.pool(builder);
            }
        }));

        //Brewing Recipes
        BrewingRecipeRegistry.registerPotionType(APPLICABLE_POTION);
        BrewingRecipeRegistry.registerItemRecipe(Items.POTION, Items.SLIME_BALL, APPLICABLE_POTION);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.CONSUMABLES).register( (content) -> {
            for (Potion potion : Registry.POTION) {
                if (!(potion.equals(Potions.EMPTY))) content.add(PotionUtil.setPotion(new ItemStack(APPLICABLE_POTION), potion));
            }
        } );
    }

    //Register an Item
    protected static <T extends Item> Item register(String id, T item) { // Register a given item
        return Registry.register(Registry.ITEM, idOf(id), item);
    }
    protected static <T extends Item> Item register(String id, T item, ItemGroup itemGroup) { // Register an item, add to a group
        Item item1 = register(id, item);
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register( (content) -> content.add(item1));
        return item1;
    }
    protected static <T extends Item> Item register(String id, T item, ItemGroup itemGroup, Item itemAfter) { // Register an item, add to a specific location in a group
        Item item1 = register(id, item);
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register( (content) -> content.addAfter(itemAfter, item1));
        return item1;
    }

    protected static <T extends Item> Item register(String id, T item, ItemGroupLocation... locations) { // Register an item, add to several locations
        Item item1 = register(id, item);
        for (ItemGroupLocation location : locations) {
            ItemGroupEvents.modifyEntriesEvent(location.GROUP).register( (content) -> content.addAfter(location.AFTER, item1));
        }
        return item1;
    }

    protected static Item register(String id) { // Create and register an item
        FabricItemSettings settings = new FabricItemSettings();
        return register(id, new Item(settings));
    }

    protected static Item register(String id, ItemGroup itemGroup) { // Create and register an item, give a group
        FabricItemSettings settings = new FabricItemSettings();
        return register(id, new Item(settings), itemGroup);
    }

    protected static Item register(String id, ItemGroup itemGroup, Item itemAfter) { // Create and register an item, give a location in a group
        FabricItemSettings settings = new FabricItemSettings();
        return register(id, new Item(settings), itemGroup, itemAfter);
    }

    protected static Item register(String id, ItemGroup itemGroup, ItemGroupLocation... locations) { // Create and register an item, give several locations
        FabricItemSettings settings = new FabricItemSettings();
        Item item = register(id, new Item(settings));
        for (ItemGroupLocation location : locations) {
            ItemGroupEvents.modifyEntriesEvent(location.GROUP).register( (content) -> content.addAfter(location.AFTER, item));
        }
        return item;
    }

    protected static Item registerBlockItem(String id, Block block) { // Create and register a block item
        return register(id, new BlockItem(block, new FabricItemSettings()));
    }

    protected static Item registerBlockItem(String id, Block block, ItemGroup itemGroup) { // Create and register a block item, give a group
        return register(id, new BlockItem(block, new FabricItemSettings()), itemGroup);
    }

    protected static Item registerBlockItem(String id, Block block, ItemGroup itemGroup, Item itemAfter) { // Create and register a block item, give a location in a group
        return register(id, new BlockItem(block, new FabricItemSettings()), itemGroup, itemAfter);
    }

    protected static Item registerBlockItem(String id, Block block, ItemGroupLocation... locations) { // Create and register a block item, give several locations
        Item item = register(id, new BlockItem(block, new FabricItemSettings()));
        for (ItemGroupLocation location : locations) {
            ItemGroupEvents.modifyEntriesEvent(location.GROUP).register( (content) -> content.addAfter(location.AFTER, item));
        }
        return item;
    }

    protected static ToolSet registerGildedToolSet(ToolSet baseSet, GildType gildedToolMaterial) {
        String newName = gildedToolMaterial.name().toLowerCase(Locale.ROOT) +"_"+ baseSet.NAME;
        return new ToolSet(
                register(newName +"_sword", new GildedSwordItem(gildedToolMaterial, (SwordItem) baseSet.SWORD, GildedToolUtil.settingsOf(baseSet.SWORD))),
                register(newName +"_shovel", new GildedShovelItem(gildedToolMaterial, (ShovelItem) baseSet.SHOVEL, GildedToolUtil.settingsOf(baseSet.SHOVEL))),
                register(newName +"_pickaxe", new GildedPickaxeItem(gildedToolMaterial, (PickaxeItem) baseSet.PICKAXE, GildedToolUtil.settingsOf(baseSet.PICKAXE))),
                register(newName +"_axe", new GildedAxeItem(gildedToolMaterial, (AxeItem) baseSet.AXE, GildedToolUtil.settingsOf(baseSet.AXE))),
                register(newName +"_hoe", new GildedHoeItem(gildedToolMaterial, (HoeItem) baseSet.HOE, GildedToolUtil.settingsOf(baseSet.HOE))),
                newName
        );
    }

    //Register a dispenser behavior
    protected static void registerDispenserBehavior(ItemConvertible item, DispenserBehavior behavior) {
        DispenserBlock.registerBehavior(item, behavior);
    }

}