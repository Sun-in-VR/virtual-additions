package com.github.suninvr.virtualadditions;

import com.github.suninvr.virtualadditions.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class VirtualAdditions implements ModInitializer {

	public static final String MODID = "virtual-additions";
	public static final String NAMESPACE = "virtual_additions";
	public static final String MODNAME = "Virtual Additions";
	public static FeatureFlag PREVIEW;

	public static Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize() {

		FabricLoader.getInstance().getModContainer(MODID).ifPresent(
				modContainer -> {
					ResourceManagerHelper.registerBuiltinResourcePack(idOf("preview"), modContainer, ResourcePackActivationType.NORMAL);
					ResourceManagerHelper.registerBuiltinResourcePack(idOf("worldgen"), modContainer, ResourcePackActivationType.NORMAL);
				}
		);

		VAAdvancementCriteria.init();
		VABlocks.init();
		VABlockEntities.init();
		VABlockTags.init();
		VACallbacks.init();
		VADamageTypes.init();
		VAEntityType.init();
		VAEntityTypeTags.init();
		VAFeatures.init();
		VAFluids.init();
		VAGameRules.init();
		VAItems.init();
		VAItemTags.init();
		VAPackets.init();
		VAParticleTypes.init();
		VAScreenHandler.init();
		VASoundEvents.init();
		VAStatusEffects.init();
	}

	/**
	 * Returns a new ID for this mod.
	 * This will appear in game as "{@code NAMESPACE:id}"
	 *
	 * @param id The identifier's path.
	 * **/
	public static Identifier idOf(String id) {
		return new Identifier(NAMESPACE, id);
	}
}
