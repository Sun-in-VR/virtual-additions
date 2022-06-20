package com.github.suninvr.virtualadditions.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TomeItem extends Item {

    public TomeItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return getLevel(stack) > 1;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return hasGlint(stack) ? Rarity.RARE : Rarity.UNCOMMON;
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        stack.getOrCreateNbt().putInt("Level", 1);
        return stack;
    }

    public static int getLevel(ItemStack stack) {
        if (stack.hasNbt() && stack.getItem() instanceof TomeItem) {
            return stack.getNbt().getInt("Level");
        }
        return 0;
    }

    public static void setLevel(ItemStack stack, int level) {
        if (stack.getItem() instanceof TomeItem) {
            stack.getOrCreateNbt().putInt("Level", level);
        }
    }

    public static TomeType getType(ItemStack stack) {
        if (stack.hasNbt()) {
            return TomeType.fromString(stack.getNbt().getString("Type"));
        }
        return TomeType.NONE;
    }

    public static void setType(ItemStack stack, TomeType type) {
        stack.getOrCreateNbt().putString("Type", type.asString());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            if (!getType(stack).equals(TomeType.NONE)) {
                tooltip.add(Text.of(""));

                tooltip.add(Text.translatable("item.modifiers.offhand").formatted(Formatting.GRAY));
                tooltip.add(Text.translatable("item.virtual_additions." + getType(stack).asString() + "_tome.tooltip", (int)(getType(stack).getModifierAmount(getLevel(stack)) * 100)).formatted(Formatting.DARK_GREEN));
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            for (TomeType type : TomeType.tomeTypes) {
                int i = 1;
                do {
                    ItemStack stack = new ItemStack(this);
                    setType(stack, type);
                    setLevel(stack, i);
                    stacks.add(stack);
                    i++;
                } while (i <= 2);
            }
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return "item.virtual_additions.tome.type_" + getType(stack).asString();
    }

    public enum TomeType implements StringIdentifiable {
        NONE,
        INTELLIGENCE,
        HASTE,
        SOUL_MENDING;

        public static final TomeType[] tomeTypes = {INTELLIGENCE, HASTE, SOUL_MENDING};

        public float getModifierAmount(int level) {
            return switch (this) {
                case HASTE -> level * 0.32F;
                case INTELLIGENCE -> level * 0.5F;
                case SOUL_MENDING -> level * 0.2F;
                default -> 0;
            };
        }

        @Override
        public String asString() {
            return switch (this) {
                case HASTE -> "haste";
                case INTELLIGENCE -> "intelligence";
                case SOUL_MENDING -> "soul_mending";
                default -> "none";
            };
        }

        public static TomeType fromString(String string) {
            return switch (string) {
                case "INTELLIGENCE", "intelligence" -> TomeType.INTELLIGENCE;
                case "HASTE", "haste" -> TomeType.HASTE;
                case "SOUL_MENDING", "soul_mending" -> TomeType.SOUL_MENDING;
                default -> TomeType.NONE;
            };
        }

        public int getColor() {
            return switch (this) {
                case HASTE -> 16777040;
                case INTELLIGENCE -> 5308240;
                case SOUL_MENDING -> 16732240;
                default -> -1;
            };
        }
    }
}
