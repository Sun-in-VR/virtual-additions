package com.github.suninvr.virtualadditions.screen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.RemoteNotifierBlockEntity;
import com.github.suninvr.virtualadditions.network.SetRemoteNotifierMessagePayload;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VAMenus;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class RemoteNotifierMenu extends AbstractContainerMenu {
    public static final Identifier EMPTY_IOLITE_SLOT = VirtualAdditions.idOf("container/slot/iolite");
    private final Inventory playerInventory;
    private final Container container;
    private final RemoteNotifierBlockEntity blockEntity;
    private final Slot displaySlot;
    private final Slot paymentSlot;

    public RemoteNotifierMenu(int i, Inventory inventory) {
        this(i, inventory, null);
    }

    public RemoteNotifierMenu(int i, Inventory inventory, RemoteNotifierBlockEntity blockEntity) {
        super(VAMenus.REMOTE_NOTIFIER, i);
        this.playerInventory = inventory;
        this.container = new SimpleContainer(2);
        this.blockEntity = blockEntity;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }

        this.paymentSlot = this.addSlot(new Slot(this.container, 0, 80, 60) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(VAItems.IOLITE);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public void setByPlayer(ItemStack itemStack, ItemStack itemStack2) {
                super.setByPlayer(itemStack, itemStack2);
                if (RemoteNotifierMenu.this.blockEntity != null) {
                    if (itemStack.isEmpty()) {
                        RemoteNotifierMenu.this.blockEntity.unsubscribe(RemoteNotifierMenu.this.playerInventory.player);
                    } else {
                        RemoteNotifierMenu.this.blockEntity.subscribe(RemoteNotifierMenu.this.playerInventory.player, itemStack);
                    }
                }
            }

            public Identifier getNoItemIcon() {
                return EMPTY_IOLITE_SLOT;
            }
        });

        this.displaySlot = this.addSlot(new Slot(this.container, 1, 18, 26) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player player) {
                return false;
            }

            @Override
            public void set(ItemStack itemStack) {
                super.set(itemStack);
                if (RemoteNotifierMenu.this.blockEntity == null) return;
                if (!RemoteNotifierMenu.this.blockEntity.getStack().equals(itemStack)) {
                    RemoteNotifierMenu.this.blockEntity.setStack(itemStack);
                }
            }

            @Override
            public boolean isFake() {
                return true;
            }
        });

        if (this.blockEntity != null) {
            this.displaySlot.set(this.blockEntity.getStack());
            if (this.blockEntity.isSubscribed(this.playerInventory.player)) {
                this.paymentSlot.set(this.blockEntity.getSubscriberStack(this.playerInventory.player));
            }
            ServerPlayNetworking.send((ServerPlayer) this.playerInventory.player, new SetRemoteNotifierMessagePayload(this.blockEntity.getMessage()));
        }

    }

    @Override
    public void clicked(int i, int j, ClickType clickType, Player player) {
        if (i == 37) {
            this.displaySlot.set(this.getCarried().copyWithCount(1));
        }
        else super.clicked(i, j, clickType, player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public void setText(String text) {
        if (this.blockEntity != null) this.blockEntity.setString(text);
    }
}
