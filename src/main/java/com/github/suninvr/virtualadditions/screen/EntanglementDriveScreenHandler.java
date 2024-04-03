package com.github.suninvr.virtualadditions.screen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.EntanglementDriveBlockEntity;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VAScreenHandler;
import com.mojang.datafixers.util.Pair;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.MathHelper;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
public class EntanglementDriveScreenHandler extends ScreenHandler {

    public static final Identifier ENTANGLEMENT_DRIVE_ACTIVE_SLOT_SYNC_ID = VirtualAdditions.idOf("entanglement_drive_active_slot_sync");
    public static final Identifier ENTANGLEMENT_DRIVE_SELECTED_SLOT_SYNC_ID = VirtualAdditions.idOf("entanglement_drive_selected_slot_sync");
    public static final Identifier EMPTY_IOLITE_SLOT = VirtualAdditions.idOf("item/empty_slot_iolite_dark");
    static final Identifier[] EMPTY_ARMOR_SLOT_TEXTURES;
    private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER;
    private final Inventory inventory;
    private final Slot paymentSlot;
    private int selectedSlotIndex;
    private final ScreenHandlerContext context;
    private final PropertyDelegate propertyDelegate;
    private final Property isSamePlayer;
    private final UUID playerId;
    private static final UUID nullId = UUID.fromString("0-0-0-0-0");

    public EntanglementDriveScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, new ArrayPropertyDelegate(5));
    }
    public EntanglementDriveScreenHandler(int syncid, PlayerInventory inventory, ScreenHandlerContext context, PropertyDelegate delegate) {
        super(VAScreenHandler.ENTANGLEMENT_DRIVE, syncid);
        this.context = context;
        this.selectedSlotIndex = -1;
        this.propertyDelegate = delegate;
        this.addProperties(propertyDelegate);
        this.isSamePlayer = Property.create();
        this.addProperty(this.isSamePlayer);
        this.playerId = inventory.player.getUuid();
        this.isSamePlayer.set( getActivePlayerId().equals(this.playerId) ? 1 : 0 );
        this.inventory = new SimpleInventory(1) {
            @Override
            public void markDirty() {
                EntanglementDriveScreenHandler.this.onContentChanged(this);
                super.markDirty();
            }
            @Override
            public boolean isValid(int slot, ItemStack stack) {
                return stack.isOf(VAItems.IOLITE);
            }

            @Override
            public int getMaxCountPerStack() {
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

                public int getMaxItemCount() {
                    return 1;
                }

                public boolean canInsert(ItemStack stack) {
                    return equipmentSlot == MobEntity.getPreferredEquipmentSlot(stack);
                }

                public boolean canTakeItems(PlayerEntity playerEntity) {
                    ItemStack itemStack = this.getStack();
                    return (itemStack.isEmpty() || playerEntity.isCreative() || !EnchantmentHelper.hasBindingCurse(itemStack)) && super.canTakeItems(playerEntity);
                }

                public Pair<Identifier, Identifier> getBackgroundSprite() {
                    return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
                }
            });
        } // Equipment slots ( 36 - 39 )

        this.addSlot(new Slot(inventory, 40, 77, 62) {
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT);
            }
        }); // Offhand Slot ( 40 )

        this.paymentSlot = this.addSlot(new Slot(this.inventory, 0, 91, 30) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(VAItems.IOLITE);
            }

            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EMPTY_IOLITE_SLOT);
            }
        }); // Payment Slot ( 42 )
    }

    public Optional<EntanglementDriveBlockEntity> getEntity() {
        return this.context.get((world, pos) -> world.getBlockEntity(pos) instanceof EntanglementDriveBlockEntity e ? e : null);
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
        return this.paymentSlot.getStack().isOf(VAItems.IOLITE);
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
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        int i = MathHelper.clamp(slotIndex, 0, 40);
        if (!this.isSelectingSlot() || !this.getCursorStack().isEmpty() || i != slotIndex) {
            super.onSlotClick(slotIndex, button, actionType, player);
            return;
        }
        if (button != 0) return;
        this.setSelectedSlotIndex(slotIndex);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slotFrom = this.getSlot(slot);
        if (slotFrom != null && slotFrom.hasStack()) {
            ItemStack itemStack2 = slotFrom.getStack();
            itemStack = itemStack2.copy();

            if (!this.paymentSlot.hasStack() && this.paymentSlot.canInsert(itemStack2) ) {
                if (this.insertItem(itemStack2, 41, 42, false)) { // To payment
                    return ItemStack.EMPTY;
                }
            }

            if (slot == 42) { // From Payment
                if (!this.insertItem(itemStack2, 4, 40, true)) { // To Hotbar + Inventory
                    return ItemStack.EMPTY;
                }

                slotFrom.onQuickTransfer(itemStack2, itemStack);
            } else if (slot >= 4 && slot < 31) { // From Inventory
                if (!this.insertItem(itemStack2, 31, 40, false)) { // To Hotbar
                    return ItemStack.EMPTY;
                }
            } else if (slot >= 32 && slot < 40) { // From Hotbar
                if (!this.insertItem(itemStack2, 4, 31, false)) { // To Inventory
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 4, 40, false)) { // To Hotbar + Inventory
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slotFrom.setStackNoCallbacks(ItemStack.EMPTY);
            } else {
                slotFrom.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slotFrom.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.inventory));
    }

    static {
        EMPTY_ARMOR_SLOT_TEXTURES = new Identifier[]{PlayerScreenHandler.EMPTY_BOOTS_SLOT_TEXTURE, PlayerScreenHandler.EMPTY_LEGGINGS_SLOT_TEXTURE, PlayerScreenHandler.EMPTY_CHESTPLATE_SLOT_TEXTURE, PlayerScreenHandler.EMPTY_HELMET_SLOT_TEXTURE};
        EQUIPMENT_SLOT_ORDER = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    }

    public UUID getActivePlayerId() {
        return Uuids.toUuid(new int[]{this.propertyDelegate.get(1), this.propertyDelegate.get(2), this.propertyDelegate.get(3), this.propertyDelegate.get(4)});
    }

    public void setActivePlayerId(UUID uuid) {
        int[] idArray = Uuids.toIntArray(uuid);
        this.propertyDelegate.set(1, idArray[0]);
        this.propertyDelegate.set(2, idArray[1]);
        this.propertyDelegate.set(3, idArray[2]);
        this.propertyDelegate.set(4, idArray[3]);
        this.isSamePlayer.set( uuid.equals(this.playerId) ? 1 : 0 );
    }

    public void decrementPaymentSlot() {
        this.paymentSlot.getStack().decrement(1);
    }
}
