package com.github.suninvr.virtualadditions.item;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.github.suninvr.virtualadditions.registry.VAItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class KeyItem extends Item {
    public KeyItem(Settings settings) {
        super(settings);
    }

    public static UUID getOrCreateKeyId (ItemStack stack) {
        NbtCompound stackNbt = stack.getOrCreateNbt();
        if (hasKeyID(stack)) {
            return stackNbt.getUuid("UUID");
        } else {
            return setRandomKeyID(stack.getOrCreateNbt());
        }
    }

    @Nullable
    public UUID getKeyID (NbtCompound nbt) {
        return nbt.getUuid("UUID");
    }

    public static UUID setRandomKeyID(NbtCompound nbt) {
        UUID randomId = UUID.randomUUID();
        setKeyID(nbt, randomId);
        return randomId;
    }

    public static void setKeyID(NbtCompound nbt, UUID uuid) {
        nbt.putUuid("UUID", uuid);
    }

    public static boolean hasKeyID(ItemStack stack) {
        if (!stack.hasNbt()) return false;
        return stack.getNbt().contains("UUID");
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (!hasKeyID(stack) && slot.getStack().isOf(VAItems.KEY)) {
            setKeyID(stack.getOrCreateNbt(), getOrCreateKeyId(slot.getStack()));

            return true;
        }

        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Block block = world.getBlockState(pos).getBlock();

        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (context.isAdvanced() && hasKeyID(stack)) tooltip.add(Text.of("ID: " + getKeyID(stack.getOrCreateNbt())));
    }
}
