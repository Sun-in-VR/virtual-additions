package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.entity.ClimbingRopeEntity;
import com.github.suninvr.virtualadditions.entity.SteelBombEntity;
import com.github.suninvr.virtualadditions.item.*;
import com.github.suninvr.virtualadditions.item.enums.GildType;
import com.github.suninvr.virtualadditions.mixin.ComposterBlockAccessor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.enchantment.Enchantments;
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
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Locale;
import java.util.Optional;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAItems {
    protected record ToolSet(Item SWORD, Item SHOVEL, Item PICKAXE, Item AXE, Item HOE, String NAME){}
    public static final ToolSet DIAMOND_TOOL_SET = new ToolSet(Items.DIAMOND_SWORD, Items.DIAMOND_SHOVEL, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_HOE, "diamond");
    public static final ToolSet GOLDEN_TOOL_SET = new ToolSet(Items.GOLDEN_SWORD, Items.GOLDEN_SHOVEL, Items.GOLDEN_PICKAXE, Items.GOLDEN_AXE, Items.GOLDEN_HOE, "golden");
    public static final ToolSet IRON_TOOL_SET = new ToolSet(Items.IRON_SWORD, Items.IRON_SHOVEL, Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_HOE, "iron");
    public static final ToolSet NETHERITE_TOOL_SET = new ToolSet(Items.NETHERITE_SWORD, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_HOE, "netherite");
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
    public static final Item COTTON_SEEDS;
    public static final Item COTTON;
    //public static final Item PROJECTION_SPYGLASS;


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

    static {
        CLIMBING_ROPE = register("climbing_rope", new AliasedBlockItem(VABlocks.CLIMBING_ROPE_ANCHOR, new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(16)));
        HORNFELS = registerBlockItem("hornfels", VABlocks.HORNFELS, ItemGroup.BUILDING_BLOCKS);
        COBBLED_HORNFELS = registerBlockItem("cobbled_hornfels", VABlocks.COBBLED_HORNFELS, ItemGroup.BUILDING_BLOCKS);
        COBBLED_HORNFELS_STAIRS = registerBlockItem("cobbled_hornfels_stairs", VABlocks.COBBLED_HORNFELS_STAIRS, ItemGroup.BUILDING_BLOCKS);
        COBBLED_HORNFELS_SLAB = registerBlockItem("cobbled_hornfels_slab", VABlocks.COBBLED_HORNFELS_SLAB, ItemGroup.BUILDING_BLOCKS);
        COBBLED_HORNFELS_WALL = registerBlockItem("cobbled_hornfels_wall", VABlocks.COBBLED_HORNFELS_WALL, ItemGroup.BUILDING_BLOCKS);
        POLISHED_HORNFELS = registerBlockItem("polished_hornfels", VABlocks.POLISHED_HORNFELS, ItemGroup.BUILDING_BLOCKS);
        POLISHED_HORNFELS_STAIRS = registerBlockItem("polished_hornfels_stairs", VABlocks.POLISHED_HORNFELS_STAIRS, ItemGroup.BUILDING_BLOCKS);
        POLISHED_HORNFELS_SLAB = registerBlockItem("polished_hornfels_slab", VABlocks.POLISHED_HORNFELS_SLAB, ItemGroup.BUILDING_BLOCKS);
        HORNFELS_TILES = registerBlockItem("hornfels_tiles", VABlocks.HORNFELS_TILES, ItemGroup.BUILDING_BLOCKS);
        CRACKED_HORNFELS_TILES = registerBlockItem("cracked_hornfels_tiles", VABlocks.CRACKED_HORNFELS_TILES, ItemGroup.BUILDING_BLOCKS);
        HORNFELS_TILE_STAIRS = registerBlockItem("hornfels_tile_stairs", VABlocks.HORNFELS_TILE_STAIRS, ItemGroup.BUILDING_BLOCKS);
        HORNFELS_TILE_SLAB = registerBlockItem("hornfels_tile_slab", VABlocks.HORNFELS_TILE_SLAB, ItemGroup.BUILDING_BLOCKS);
        BLUESCHIST = registerBlockItem("polished_blueschist", VABlocks.POLISHED_BLUESCHIST, ItemGroup.BUILDING_BLOCKS);
        COBBLED_BLUESCHIST = registerBlockItem("cobbled_blueschist", VABlocks.COBBLED_BLUESCHIST, ItemGroup.BUILDING_BLOCKS);
        COBBLED_BLUESCHIST_STAIRS = registerBlockItem("cobbled_blueschist_stairs", VABlocks.COBBLED_BLUESCHIST_STAIRS, ItemGroup.BUILDING_BLOCKS);
        COBBLED_BLUESCHIST_SLAB = registerBlockItem("cobbled_blueschist_slab", VABlocks.COBBLED_BLUESCHIST_SLAB, ItemGroup.BUILDING_BLOCKS);
        COBBLED_BLUESCHIST_WALL = registerBlockItem("cobbled_blueschist_wall", VABlocks.COBBLED_BLUESCHIST_WALL, ItemGroup.BUILDING_BLOCKS);
        POLISHED_BLUESCHIST = registerBlockItem("blueschist", VABlocks.BLUESCHIST, ItemGroup.BUILDING_BLOCKS);
        POLISHED_BLUESCHIST_STAIRS = registerBlockItem("polished_blueschist_stairs", VABlocks.POLISHED_BLUESCHIST_STAIRS, ItemGroup.BUILDING_BLOCKS);
        POLISHED_BLUESCHIST_SLAB = registerBlockItem("polished_blueschist_slab", VABlocks.POLISHED_BLUESCHIST_SLAB, ItemGroup.BUILDING_BLOCKS);
        BLUESCHIST_BRICKS = registerBlockItem("blueschist_bricks", VABlocks.BLUESCHIST_BRICKS, ItemGroup.BUILDING_BLOCKS);
        CRACKED_BLUESCHIST_BRICKS = registerBlockItem("cracked_blueschist_bricks", VABlocks.CRACKED_BLUESCHIST_BRICKS, ItemGroup.BUILDING_BLOCKS);
        BLUESCHIST_BRICK_STAIRS = registerBlockItem("blueschist_brick_stairs", VABlocks.BLUESCHIST_BRICK_STAIRS, ItemGroup.BUILDING_BLOCKS);
        BLUESCHIST_BRICK_SLAB = registerBlockItem("blueschist_brick_slab", VABlocks.BLUESCHIST_BRICK_SLAB, ItemGroup.BUILDING_BLOCKS);
        BLUESCHIST_BRICK_WALL = registerBlockItem("blueschist_brick_wall", VABlocks.BLUESCHIST_BRICK_WALL, ItemGroup.BUILDING_BLOCKS);
        SYENITE = registerBlockItem("syenite", VABlocks.SYENITE, ItemGroup.BUILDING_BLOCKS);
        COBBLED_SYENITE = registerBlockItem("cobbled_syenite", VABlocks.COBBLED_SYENITE, ItemGroup.BUILDING_BLOCKS);
        COBBLED_SYENITE_STAIRS = registerBlockItem("cobbled_syenite_stairs", VABlocks.COBBLED_SYENITE_STAIRS, ItemGroup.BUILDING_BLOCKS);
        COBBLED_SYENITE_SLAB = registerBlockItem("cobbled_syenite_slab", VABlocks.COBBLED_SYENITE_SLAB, ItemGroup.BUILDING_BLOCKS);
        COBBLED_SYENITE_WALL = registerBlockItem("cobbled_syenite_wall", VABlocks.COBBLED_SYENITE_WALL, ItemGroup.BUILDING_BLOCKS);
        POLISHED_SYENITE = registerBlockItem("polished_syenite", VABlocks.POLISHED_SYENITE, ItemGroup.BUILDING_BLOCKS);
        POLISHED_SYENITE_STAIRS = registerBlockItem("polished_syenite_stairs", VABlocks.POLISHED_SYENITE_STAIRS, ItemGroup.BUILDING_BLOCKS);
        POLISHED_SYENITE_SLAB = registerBlockItem("polished_syenite_slab", VABlocks.POLISHED_SYENITE_SLAB, ItemGroup.BUILDING_BLOCKS);
        SYENITE_BRICKS = registerBlockItem("syenite_bricks", VABlocks.SYENITE_BRICKS, ItemGroup.BUILDING_BLOCKS);
        CRACKED_SYENITE_BRICKS = registerBlockItem("cracked_syenite_bricks", VABlocks.CRACKED_SYENITE_BRICKS, ItemGroup.BUILDING_BLOCKS);
        SYENITE_BRICK_STAIRS = registerBlockItem("syenite_brick_stairs", VABlocks.SYENITE_BRICK_STAIRS, ItemGroup.BUILDING_BLOCKS);
        SYENITE_BRICK_SLAB = registerBlockItem("syenite_brick_slab", VABlocks.SYENITE_BRICK_SLAB, ItemGroup.BUILDING_BLOCKS);
        SYENITE_BRICK_WALL = registerBlockItem("syenite_brick_wall", VABlocks.SYENITE_BRICK_WALL, ItemGroup.BUILDING_BLOCKS);
        RED_GLIMMER_CRYSTAL = registerBlockItem("red_glimmer_crystal", VABlocks.RED_GLIMMER_CRYSTAL, ItemGroup.DECORATIONS);
        GREEN_GLIMMER_CRYSTAL = registerBlockItem("green_glimmer_crystal", VABlocks.GREEN_GLIMMER_CRYSTAL, ItemGroup.DECORATIONS);
        BLUE_GLIMMER_CRYSTAL = registerBlockItem("blue_glimmer_crystal", VABlocks.BLUE_GLIMMER_CRYSTAL, ItemGroup.DECORATIONS);
        CRYSTAL_DUST = register("crystal_dust", ItemGroup.MISC);
        SPOTLIGHT = registerBlockItem("spotlight", VABlocks.SPOTLIGHT, ItemGroup.REDSTONE);
        RAW_STEEL_BLOCK = registerBlockItem("raw_steel_block", VABlocks.RAW_STEEL_BLOCK, ItemGroup.BUILDING_BLOCKS);
        STEEL_BLOCK = registerBlockItem("steel_block", VABlocks.STEEL_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RAW_STEEL = register("raw_steel", ItemGroup.MISC);
        STEEL_INGOT = register("steel_ingot", ItemGroup.MISC);
        STEEL_BOMB = register("steel_bomb", new SteelBombItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(16)));
        COTTON_SEEDS = register("cotton_seeds", new AliasedBlockItem(VABlocks.COTTON, new FabricItemSettings().group(ItemGroup.MISC)));
        COTTON = register("cotton", ItemGroup.MISC);
        //PROJECTION_SPYGLASS = register("projection_spyglass", new ProjectionSpyglassItem(new FabricItemSettings().group(ItemGroup.MISC)));
        //FRIED_EGG = register("fried_egg", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.APPLE)));


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
    }

    //Register an Item
    protected static <T extends Item> Item register(String id, T item) {
        return Registry.register(Registry.ITEM, idOf(id), item);
    }

    protected static Item register(String id, ItemGroup itemGroup) {
        Optional<ItemGroup> optionalItemGroup = Optional.of(itemGroup);
        FabricItemSettings settings = new FabricItemSettings();
        optionalItemGroup.ifPresent(settings::group);
        return register(id, new Item(settings));
    }

    protected static Item registerBlockItem(String id, Block block, ItemGroup itemGroup) {
        return register(id, new BlockItem(block, new FabricItemSettings().group(itemGroup)));
    }

    protected static Item registerBlockItem(String id, Block block) {
        return register(id, new BlockItem(block, new FabricItemSettings()));
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