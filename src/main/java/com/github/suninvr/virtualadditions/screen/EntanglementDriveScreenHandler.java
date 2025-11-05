package com.github.suninvr.virtualadditions.screen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.EntanglementDriveBlockEntity;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VAScreenHandler;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
public class EntanglementDriveScreenHandler extends AbstractContainerMenu {

    public static final ResourceLocation ENTANGLEMENT_DRIVE_ACTIVE_SLOT_SYNC_ID = VirtualAdditions.idOf("entanglement_drive_active_slot_sync");
    public static final ResourceLocation ENTANGLEMENT_DRIVE_SELECTED_SLOT_SYNC_ID = VirtualAdditions.idOf("entanglement_drive_selected_slot_sync");
    public static final ResourceLocation EMPTY_IOLITE_SLOT = VirtualAdditions.idOf("container/slot/iolite_dark");
    static final ResourceLocation[] EMPTY_ARMOR_SLOT_TEXTURES;
    private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER;
    private final Container inventory;
    private final Inventory playerInventory;
    private final Slot paymentSlot;
    private int selectedSlotIndex;
    private final ContainerLevelAccess context;
    private final ContainerData propertyDelegate;
    private final DataSlot isSamePlayer;
    private final UUID playerId;
    private static final UUID nullId = UUID.fromString("0-0-0-0-0");

    public EntanglementDriveScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL, new SimpleContainerData(5));
    }
    public EntanglementDriveScreenHandler(int syncid, Inventory inventory, ContainerLevelAccess context, ContainerData delegate) {
        super(VAScreenHandler.ENTANGLEMENT_DRIVE, syncid);
        this.context = context;
        this.selectedSlotIndex = -1;
        this.propertyDelegate = delegate;
        this.addDataSlots(propertyDelegate);
        this.isSamePlayer = DataSlot.standalone();
        this.addDataSlot(this.isSamePlayer);
        this.playerId = inventory.player.getUUID();
        if (!inventory.player.isSpectator()) {
            this.isSamePlayer.set(getActivePlayerId().equals(this.playerId) ? 1 : 0);
        } else {
            this.isSamePlayer.set(0);
        }
        this.playerInventory = inventory;
        this.inventory = new SimpleContainer(1) {
            @Override
            public void setChanged() {
                EntanglementDriveScreenHandler.this.slotsChanged(this);
                super.setChanged();
            }
            @Override
            public boolean canPlaceItem(int slot, ItemStack stack) {
                return stack.is(VAItems.IOLITE);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };

        int i;
        int j;

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        } // Hotbar Slots ( 0 - 8 )

        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        } // Inventory Slots ( 9 - 35 )

        for(i = 0; i < 4; ++i) {
            final EquipmentSlot equipmentSlot = EQUIPMENT_SLOT_ORDER[3 - i];
            this.addSlot(new Slot(inventory, 36 + i, 8, 62 - i * 18) {

                public int getMaxStackSize() {
                    return 1;
                }

                public boolean mayPlace(ItemStack stack) {
                    return equipmentSlot == EntanglementDriveScreenHandler.this.playerInventory.player.getEquipmentSlotForItem(stack);
                }

                public boolean mayPickup(Player playerEntity) {
                    ItemStack itemStack = this.getItem();
                    return (itemStack.isEmpty() || playerEntity.isCreative() || !EnchantmentHelper.has(itemStack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) && super.mayPickup(playerEntity);
                }

                public ResourceLocation getNoItemIcon() {
                    return EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getIndex()];
                }
            });
        } // Equipment slots ( 36 - 39 )

        this.addSlot(new Slot(inventory, 40, 77, 62) {
            public ResourceLocation getNoItemIcon() {
                return InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD;
            }
        }); // Offhand Slot ( 40 )

        this.paymentSlot = this.addSlot(new Slot(this.inventory, 0, 91, 30) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(VAItems.IOLITE);
            }

            public ResourceLocation getNoItemIcon() {
                return EMPTY_IOLITE_SLOT;
            }
        }); // Payment Slot ( 42 )
    }

    public Optional<EntanglementDriveBlockEntity> getEntity() {
        return this.context.evaluate((world, pos) -> world.getBlockEntity(pos) instanceof EntanglementDriveBlockEntity e ? e : null);
    }

    public Slot getActiveSlot() {
        return this.slots.get(this.getActiveSlotIndex());
    }

    public Slot getSelectedSlot() {
        return this.slots.get(this.getSelectedSlotIndex());
    }

    public void setSelectedSlotIndex(int index) {
        this.selectedSlotIndex = index;
    }

    public int getSelectedSlotIndex() {
        return this.selectedSlotIndex;
    }

    public void setActiveSlotIndex(int index) {
        this.propertyDelegate.set(0, index);
    }

    public int getActiveSlotIndex() {
        return this.propertyDelegate.get(0);
    }

    public boolean isSelectingSlot() {
        return this.paymentSlot.getItem().is(VAItems.IOLITE);
    }

    public boolean isSlotSelected() {
        return this.selectedSlotIndex > -1;
    }

    public boolean isActive() {
        return this.propertyDelegate.get(0) > -1;
    }

    public boolean isSamePlayer() {
        return this.isSamePlayer.get() == 1;
    }

    @Override
    public void clicked(int slotIndex, int button, ClickType actionType, Player player) {
        int i = Mth.clamp(slotIndex, 0, 40);
        if (!this.isSelectingSlot() || !this.getCarried().isEmpty() || i != slotIndex) {
            super.clicked(slotIndex, button, actionType, player);
            return;
        }
        if (button != 0) return;
        this.setSelectedSlotIndex(slotIndex);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slotFrom = this.getSlot(slot);
        if (slotFrom != null && slotFrom.hasItem()) {
            ItemStack itemStack2 = slotFrom.getItem();
            itemStack = itemStack2.copy();

            if (!this.paymentSlot.hasItem() && this.paymentSlot.mayPlace(itemStack2) ) {
                if (this.moveItemStackTo(itemStack2, 41, 42, false)) { // To payment
                    return ItemStack.EMPTY;
                }
            }

            if (slot == 42) { // From Payment
                if (!this.moveItemStackTo(itemStack2, 4, 40, true)) { // To Hotbar + Inventory
                    return ItemStack.EMPTY;
                }

                slotFrom.onQuickCraft(itemStack2, itemStack);
            } else if (slot >= 4 && slot < 31) { // From Inventory
                if (!this.moveItemStackTo(itemStack2, 31, 40, false)) { // To Hotbar
                    return ItemStack.EMPTY;
                }
            } else if (slot >= 32 && slot < 40) { // From Hotbar
                if (!this.moveItemStackTo(itemStack2, 4, 31, false)) { // To Inventory
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 4, 40, false)) { // To Hotbar + Inventory
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slotFrom.set(ItemStack.EMPTY);
            } else {
                slotFrom.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slotFrom.onTake(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.context.execute((world, pos) -> this.clearContainer(player, this.inventory));
    }

    static {
        EMPTY_ARMOR_SLOT_TEXTURES = new ResourceLocation[]{InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS, InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET};
        EQUIPMENT_SLOT_ORDER = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    }

    public UUID getActivePlayerId() {
        return UUIDUtil.uuidFromIntArray(new int[]{this.propertyDelegate.get(1), this.propertyDelegate.get(2), this.propertyDelegate.get(3), this.propertyDelegate.get(4)});
    }

    public void setActivePlayerId(UUID uuid) {
        this.getEntity().ifPresent(entity -> {
            entity.setPlayerId(uuid);
        });
        int[] idArray = UUIDUtil.uuidToIntArray(uuid);
        this.propertyDelegate.set(1, idArray[0]);
        this.propertyDelegate.set(2, idArray[1]);
        this.propertyDelegate.set(3, idArray[2]);
        this.propertyDelegate.set(4, idArray[3]);
        this.isSamePlayer.set( uuid.equals(this.playerId) ? 1 : 0 );
    }

    public void decrementPaymentSlot() {
        this.paymentSlot.getItem().shrink(1);
    }
}
