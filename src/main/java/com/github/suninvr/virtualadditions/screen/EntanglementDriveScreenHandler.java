package com.github.suninvr.virtualadditions.screen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.EntanglementDriveBlockEntity;
import com.github.suninvr.virtualadditions.registry.VAAdvancementCriteria;
import com.github.suninvr.virtualadditions.registry.VAItems;
import com.github.suninvr.virtualadditions.registry.VAScreenHandler;
import com.github.suninvr.virtualadditions.registry.VASoundEvents;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unused")
public class EntanglementDriveScreenHandler extends ScreenHandler {

    public static final Identifier ENTANGLEMENT_DRIVE_ACTIVE_SLOT_SYNC_ID = VirtualAdditions.idOf("entanglement_drive_active_slot_sync");
    public static final Identifier ENTANGLEMENT_DRIVE_SELECTED_SLOT_SYNC_ID = VirtualAdditions.idOf("entanglement_drive_selected_slot_sync");
    public static final Identifier EMPTY_IOLITE_SLOT = VirtualAdditions.idOf("item/empty_iolite_slot");
    static final Identifier[] EMPTY_ARMOR_SLOT_TEXTURES;
    private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER;
    private final EntanglementDriveBlockEntity entity;
    private final Inventory inventory;
    private final Slot paymentSlot;
    private boolean selectingSlot;
    private boolean slotSelected;
    private final ScreenHandlerContext context;
    private Slot selectedSlot;
    private int activeSlotId;
    private int activeSlotIndex;
    private UUID playerId;
    private static final UUID nullId = UUID.fromString("0-0-0-0-0");

    public EntanglementDriveScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
        this.activeSlotId = buf.readInt();
        this.activeSlotIndex = buf.readInt();
        this.playerId = buf.readOptional((buf1 -> buf.readUuid())).orElse( nullId );
    }
    public EntanglementDriveScreenHandler(int syncid, PlayerInventory inventory, ScreenHandlerContext context) {
        super(VAScreenHandler.ENTANGLEMENT_DRIVE_SCREEN_HANDLER, syncid);
        this.context = context;
        this.activeSlotId = 0;
        this.activeSlotIndex = 0;
        this.playerId = nullId;
        this.slotSelected = false;
        if (!(context.equals(ScreenHandlerContext.EMPTY))) this.entity = context.get(World::getBlockEntity, null) instanceof EntanglementDriveBlockEntity blockEntity ? blockEntity : null;
        else entity = null;
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

        for(i = 0; i < 4; ++i) {
            final EquipmentSlot equipmentSlot = EQUIPMENT_SLOT_ORDER[i];
            this.addSlot(new Slot(inventory, 39 - i, 8, 8 + i * 18) {

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
        } // Equipment slots ( 0 - 3 )

        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        } // Inventory Slots ( 4 - 31 )

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        } // Hotbar Slots ( 32 - 40 )

        this.addSlot(new Slot(inventory, 40, 77, 62) {
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT);
            }
        }); // Offhand Slot ( 41 )

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

    public Slot getActiveSlot() {
        AtomicReference<Slot> slot = new AtomicReference<>(this.slots.get(0));
        this.slots.forEach( (slot1 -> {if (slot1.id == this.activeSlotId) slot.set(slot1);}) );
        return slot.get();
    }

    public Slot getSelectedSlot() {
        return this.selectedSlot;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    @Nullable
    BlockEntity getEntity() {
        return this.entity;
    }

    public void setActiveSlotId(int readInt) {
        this.activeSlotId = readInt;
    }

    public void setActiveSlotIndex(int readInt) {
        this.activeSlotIndex = readInt;
    }

    public void setPlayerId(UUID readId) {
        this.playerId = readId;
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory.equals(this.inventory)) {
            this.selectingSlot = this.inventory.getStack(0).isOf(VAItems.IOLITE);
        }
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        int i = MathHelper.clamp(slotIndex, 0, 40);
        if (!this.selectingSlot || !this.getCursorStack().isEmpty() || i != slotIndex) {
            super.onSlotClick(slotIndex, button, actionType, player);
            return;
        }
        this.selectedSlot = slots.get(slotIndex);
        this.slotSelected = true;
        if (player instanceof ServerPlayerEntity serverPlayerEntity) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(selectedSlot.x);
            buf.writeInt(selectedSlot.y);
            ServerPlayNetworking.send(serverPlayerEntity, ENTANGLEMENT_DRIVE_SELECTED_SLOT_SYNC_ID, buf);
        }
    }

    public void setPlayerSlot(PlayerEntity player) {

        if (this.selectedSlot == null) return;
        int slotIndex = this.selectedSlot.getIndex();

        if (this.getEntity() instanceof EntanglementDriveBlockEntity blockEntity && !(blockEntity.getWorld() == null) && !blockEntity.getWorld().isClient() ) {
            this.slotSelected = false;
            blockEntity.setPlayerSlot(player, slotIndex, this.selectedSlot.id);
            PacketByteBuf buf = PacketByteBufs.create();
            blockEntity.writeScreenData(buf);
            ServerPlayNetworking.send((ServerPlayerEntity) player, ENTANGLEMENT_DRIVE_ACTIVE_SLOT_SYNC_ID, buf);
            blockEntity.getWorld().playSound(null, blockEntity.getPos(), VASoundEvents.BLOCK_ENTANGLEMENT_DRIVE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            VAAdvancementCriteria.USE_ENTANGLEMENT_DRIVE.trigger((ServerPlayerEntity) player);

            this.inventory.clear();
        }
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

    public boolean isSelectingSlot() {
        return selectingSlot;
    }

    public boolean isSlotSelected() {
        return this.slotSelected;
    }
}
