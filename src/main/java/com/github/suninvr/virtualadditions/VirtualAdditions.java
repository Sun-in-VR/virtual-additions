package com.github.suninvr.virtualadditions;

import com.github.suninvr.virtualadditions.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
		VAFluids.init();
		VAItems.init();
		VAParticleTypes.init();
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
