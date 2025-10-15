package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.component.GildTypeComponent;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAGildTypes;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VARegistries;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.DataProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.component.ComponentMapPredicate;
import net.minecraft.predicate.component.ComponentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAAdvancementProvider {

    public static FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider> base() {
        return Base::new;
    }

    public static class Base extends Provider {
        protected Base(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
            ItemStack stack = Items.DIAMOND_PICKAXE.getDefaultStack();
            stack.set(VADataComponentTypes.GILD_TYPE_COMPONENT, new GildTypeComponent(VARegistries.GILD_TYPE.getEntry(VAGildTypes.SCULK)));
            Advancement.Builder gildAllToolsBuilder = Advancement.Builder.createUntelemetered()
                    .parent(Advancement.Builder.create().build(idOf("story/gild_tool")))
                    .display(
                            stack,
                            Text.translatable("advancements.virtual_additions.story.gild_all_tools.title"),
                            Text.translatable("advancements.virtual_additions.story.gild_all_tools.description"),
                            null,
                            AdvancementFrame.CHALLENGE,
                            true,
                            true,
                            false
                    );
            gildAllToolsBuilder.criteriaMerger(AdvancementRequirements.CriterionMerger.AND);
            RegistryEntryLookup<Item> lookup = wrapperLookup.getOrThrow(RegistryKeys.ITEM);
            VARegistries.GILD_TYPE.stream().forEach(type -> {
                Consumer<Item> itemWithGildTypeConsumer = item -> gildAllToolsBuilder.criterion(
                        Registries.ITEM.getId(item).withPrefixedPath(VARegistries.GILD_TYPE.getId(type).getPath() + "_").getPath(),
                        InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create()
                                .items(lookup, item)
                                .components(
                                        ComponentsPredicate.Builder.create().exact(
                                                ComponentMapPredicate.of(VADataComponentTypes.GILD_TYPE_COMPONENT, new GildTypeComponent(VARegistries.GILD_TYPE.getEntry(type)))
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
            gildAllToolsBuilder.build(consumer, idOf("gild_all_tools").toString());
        }
    }

    protected static abstract class Provider extends FabricAdvancementProvider {

        protected Provider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }
    }
}
