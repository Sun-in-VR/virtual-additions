package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.network.RemoteNotifierBroadcastPayload;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.screen.RemoteNotifierMenu;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class RemoteNotifierBlockEntity extends BlockEntity implements MenuProvider {
    private static final Component TITLE = Component.translatable("container.virtual_additions.remote_notifier");
    private Map<UUID, ItemStack> subscribersWithStacks = new HashMap<>();
    private String message = "";
    private ItemStack stack = ItemStack.EMPTY;

    public RemoteNotifierBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(VABlockEntityType.REMOTE_NOTIFIER, blockPos, blockState);
    }

    public void setString(String string) {
        this.message = string;
        this.setChanged();
    }

    public void setStack(ItemStack itemStack) {
        this.stack = itemStack;
        this.setChanged();
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public void broadcast() {
        if (this.getLevel() instanceof ServerLevel serverLevel) {
            this.subscribersWithStacks.forEach((uuid, itemStack) -> {
                Player player = serverLevel.getPlayerByUUID(uuid);
                if (player instanceof ServerPlayer serverPlayer) ServerPlayNetworking.send(serverPlayer, new RemoteNotifierBroadcastPayload(this.message, this.stack));
            });
        }
    }

    public boolean subscribe(Player player, ItemStack stack) {
        if (this.getLevel() instanceof ServerLevel && !this.subscribersWithStacks.containsKey(player.getUUID())) {
            this.subscribersWithStacks.put(player.getUUID(), stack);
            this.setChanged();
            return true;
        }
        return false;
    }

    public boolean unsubscribe(Player player) {
        if (this.getLevel() instanceof ServerLevel && this.subscribersWithStacks.containsKey(player.getUUID())) {
            this.subscribersWithStacks.remove(player.getUUID());
            this.setChanged();
            return true;
        }
        return false;
    }

    public boolean isSubscribed(Player player) {
        return this.subscribersWithStacks.containsKey(player.getUUID());
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        valueInput.list("subscribers", UUIDUtil.CODEC).ifPresent(uuids -> {
            valueInput.list("subscriber_stacks", ItemStack.CODEC).ifPresent(stacks -> {
                if (uuids.stream().count() == stacks.stream().count()) {
                    this.subscribersWithStacks.clear();
                    Iterator<UUID> subscribers = uuids.stream().iterator();
                    Iterator<ItemStack> subscriberStacks = stacks.stream().iterator();
                    while (subscribers.hasNext()) {
                        this.subscribersWithStacks.put(subscribers.next(), subscriberStacks.next());
                    }
                }
            });
        });
        valueInput.read("stack", ItemStack.CODEC).ifPresent(stack -> this.stack = stack);
        valueInput.read("message", Codec.STRING).ifPresent(string -> this.message = string);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        ValueOutput.TypedOutputList<UUID> subscribers1 = valueOutput.list("subscribers", UUIDUtil.CODEC);
        ValueOutput.TypedOutputList<ItemStack> subscriberStacks = valueOutput.list("subscriber_stacks", ItemStack.CODEC);
        this.subscribersWithStacks.forEach( (subscriber, stack) -> {
            subscribers1.add(subscriber);
            subscriberStacks.add(stack);
        });
        valueOutput.storeNullable("stack", ItemStack.CODEC, this.stack);
        valueOutput.storeNullable("message", Codec.STRING, this.message);
    }

    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new RemoteNotifierMenu(i, inventory, this);
    }

    public ItemStack getSubscriberStack(Player player) {
        return this.subscribersWithStacks.getOrDefault(player.getUUID(), ItemStack.EMPTY);
    }

    public String getMessage() {
        return this.message;
    }
}
