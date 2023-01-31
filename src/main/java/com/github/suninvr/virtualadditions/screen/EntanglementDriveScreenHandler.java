package com.github.suninvr.virtualadditions.screen;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.entity.EntanglementDriveBlockEntity;
import com.github.suninvr.virtualadditions.registry.VAScreenHandler;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class EntanglementDriveScreenHandler extends ScreenHandler {

    public static final Identifier EMPTY_HELMET_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_helmet");
    public static final Identifier EMPTY_CHESTPLATE_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_chestplate");
    public static final Identifier EMPTY_LEGGINGS_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_leggings");
    public static final Identifier EMPTY_BOOTS_SLOT_TEXTURE = new Identifier("item/empty_armor_slot_boots");
    public static final Identifier EMPTY_OFFHAND_ARMOR_SLOT = new Identifier("item/empty_armor_slot_shield");
    static final Identifier[] EMPTY_ARMOR_SLOT_TEXTURES;
    private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER;
    private final ScreenHandlerContext context;
    private int slotId;
    private UUID playerId;
    private static final UUID nullId = UUID.fromString("0-0-0-0-0");
    public static Identifier ENTANGLEMENT_DRIVE_SCREEN_SYNC_ID = VirtualAdditions.idOf("entanglement_drive_screen_updater");

    public EntanglementDriveScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
        this.slotId = buf.readInt();
        this.playerId = buf.readOptional((buf1 -> buf.readUuid())).orElse( nullId );
    }
    public EntanglementDriveScreenHandler(int syncid, PlayerInventory inventory, ScreenHandlerContext context) {
        super(VAScreenHandler.ENTANGLEMENT_DRIVE_SCREEN_HANDLER, syncid);
        this.context = context;
        this.slotId = 0;
        this.playerId = nullId;

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
        }

        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }

        this.addSlot(new Slot(inventory, 40, 77, 62) {
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT);
            }
        });
    }

    public int getSlotId() {
        return this.slotId;
    }

    public Slot getSlot() {
        AtomicReference<Slot> slot = new AtomicReference<>(this.slots.get(0));
        this.slots.forEach( (slot1 -> {if (slot1.getIndex() == this.slotId) slot.set(slot1);}) );
        return slot.get();
    }

    public UUID getPlayerId() {
        return playerId;
    }

    @Nullable
    BlockEntity getEntity() {
        return this.context.get(World::getBlockEntity).orElse(null);
    }

    public void setSlotId(int readInt) {
        this.slotId = readInt;
    }

    public void setPlayerId(UUID readId) {
        this.playerId = readId;
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        int testIndex = MathHelper.clamp(slotIndex, 0, 41);
        if (testIndex != slotIndex) return;
        if (this.getEntity() instanceof EntanglementDriveBlockEntity entanglementDriveBlockEntity && !(entanglementDriveBlockEntity.getWorld() == null) && !entanglementDriveBlockEntity.getWorld().isClient() ) {
            int i = slots.get(slotIndex).getIndex();
            entanglementDriveBlockEntity.setPlayerSlot(player, i);

            PacketByteBuf buf = PacketByteBufs.create();
            entanglementDriveBlockEntity.writeScreenData(buf);
            ServerPlayNetworking.send((ServerPlayerEntity) player, ENTANGLEMENT_DRIVE_SCREEN_SYNC_ID, buf);
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    static {
        EMPTY_ARMOR_SLOT_TEXTURES = new Identifier[]{EMPTY_BOOTS_SLOT_TEXTURE, EMPTY_LEGGINGS_SLOT_TEXTURE, EMPTY_CHESTPLATE_SLOT_TEXTURE, EMPTY_HELMET_SLOT_TEXTURE};
        EQUIPMENT_SLOT_ORDER = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    }
}
