package com.github.suninvr.virtualadditions;

import com.github.suninvr.virtualadditions.command.GildTypeArgumentType;
import com.github.suninvr.virtualadditions.item.GildedToolUtil;
import com.github.suninvr.virtualadditions.item.enums.GildType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import com.github.suninvr.virtualadditions.registry.*;
import net.minecraft.util.StringIdentifiable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class VirtualAdditions implements ModInitializer {

	public static final String MODID = "virtual_additions";
	public static final String MODNAME = "Virtual Additions";

	public static Logger LOGGER = LogManager.getLogger();


	@Override
	public void onInitialize() {
		VABlocks.init();
		VABlockEntities.init();
		VABlockTags.init();
		VACallbacks.init();
		VAEntityType.init();
		VAFeatures.init();
		VAFluids.init();
		VAFluidTags.init();
		VAItems.init();
		VAParticleTypes.init();
		VARecipeTypes.init();
		VAScreenHandlers.init();
		VASoundEvents.init();
	}

	/**
	 * Returns a new ID for this mod.
	 * This will appear in game as "MODID:id",
	 * where MODID = the value defined in MODID
	 *
	 * @param id The identifier's path.
	 * **/
	public static Identifier idOf(String id) {
		return new Identifier(MODID, id);
	}
}
