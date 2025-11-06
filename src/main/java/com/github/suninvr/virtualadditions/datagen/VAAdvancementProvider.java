package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAGildTypes;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VARegistries;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAAdvancementProvider {

    public static FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider> base() {
        return Base::new;
    }

    public static class Base extends Provider {
        protected Base(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generateAdvancement(HolderLookup.Provider wrapperLookup, Consumer<AdvancementHolder> consumer) {
            ItemStack stack = Items.DIAMOND_PICKAXE.getDefaultInstance();
            stack.set(VADataComponentTypes.GILD_TYPE, VAGildTypes.SCULK);
            Advancement.Builder gildAllToolsBuilder = Advancement.Builder.recipeAdvancement()
                    .parent(Advancement.Builder.advancement().build(idOf("story/gild_tool")))
                    .display(
                            stack,
                            Component.translatable("advancements.virtual_additions.story.gild_all_tools.title"),
                            Component.translatable("advancements.virtual_additions.story.gild_all_tools.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    );
            gildAllToolsBuilder.requirements(AdvancementRequirements.Strategy.AND);
            HolderGetter<Item> lookup = wrapperLookup.lookupOrThrow(Registries.ITEM);
            VARegistries.GILD_TYPE.stream().forEach(type -> {
                Consumer<Item> itemWithGildTypeConsumer = item -> gildAllToolsBuilder.addCriterion(
                        BuiltInRegistries.ITEM.getKey(item).withPrefix(VARegistries.GILD_TYPE.getKey(type).getPath() + "_").getPath(),
                        InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
                                .of(lookup, item)
                                .withComponents(
                                        DataComponentMatchers.Builder.components().exact(
                                                DataComponentExactPredicate.expect(VADataComponentTypes.GILD_TYPE, type)
                                        ).build()
                                )
                        )
                );
                VAItems.COPPER_TOOL_SET.forEach(itemWithGildTypeConsumer);
                VAItems.IRON_TOOL_SET.forEach(itemWithGildTypeConsumer);
                VAItems.GOLDEN_TOOL_SET.forEach(itemWithGildTypeConsumer);
                VAItems.STEEL_TOOL_SET.forEach(itemWithGildTypeConsumer);
                VAItems.DIAMOND_TOOL_SET.forEach(itemWithGildTypeConsumer);
                VAItems.NETHERITE_TOOL_SET.forEach(itemWithGildTypeConsumer);
            });
            gildAllToolsBuilder.save(consumer, idOf("gild_all_tools").toString());
        }
    }

    protected static abstract class Provider extends FabricAdvancementProvider {

        protected Provider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup);
        }
    }
}
