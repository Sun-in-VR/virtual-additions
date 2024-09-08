package com.github.suninvr.virtualadditions.item.materials;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.github.suninvr.virtualadditions.registry.VAItems;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public class SteelToolMaterial {
    public static final ToolMaterial INSTANCE = new ToolMaterial(VABlockTags.INCORRECT_FOR_STEEL_TOOL, 896, 7.0F, 2.5F, 12, VAItemTags.CLIMBING_ROPES);
}
