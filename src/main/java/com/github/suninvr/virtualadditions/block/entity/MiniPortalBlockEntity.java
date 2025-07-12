package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.MiniPortalBlock;
import com.github.suninvr.virtualadditions.particle.ColorfulPowerParticleEffect;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MiniPortalBlockEntity extends BlockEntity {
    private BlockPos destination;
    private DyeColor dyeColor;

    public MiniPortalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MiniPortalBlockEntity(BlockPos pos, BlockState state) {
        super(VABlockEntityType.MINI_PORTAL, pos, state);
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        super.onBlockReplaced(pos, oldState);
        if (this.destination != null && this.world.getBlockState(this.destination).isOf(VABlocks.MINI_PORTAL)) this.world.breakBlock(this.destination, false);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return this.createNbt(registries);
    }

    public void ifOther(Consumer<MiniPortalBlockEntity> consumer) {
        if (this.destination != null && this.world != null && this.world.getBlockEntity(this.destination) instanceof MiniPortalBlockEntity blockEntity) consumer.accept(blockEntity);
    }

    public static void setDestination(World world, BlockPos pos, BlockPos destination) {
        if(world.getBlockEntity(pos) instanceof MiniPortalBlockEntity entity) {
            entity.setDestination(destination);
        }
    }

    public void setDestination(BlockPos destination) {
        this.destination = destination;
        this.markDirty();
    }

    public ParticleEffect getParticleParameter() {
        if (this.isDyed()) return new ColorfulPowerParticleEffect(ColorHelper.toVector(this.getDyeColor()));
        else return VAParticleTypes.INTERFERENCE;
    }

    public boolean setDyeColor(DyeColor color) {
        if (color == this.dyeColor) return false;
        this.dyeColor = color;
        this.markDirty();
        if (this.world instanceof ServerWorld serverWorld) serverWorld.getChunkManager().markForUpdate(this.pos);
        this.ifOther(blockEntity -> {
            blockEntity.setDyeColor(color);
            blockEntity.markDirty();
            if (this.world instanceof ServerWorld serverWorld) serverWorld.getChunkManager().markForUpdate(blockEntity.pos);
        });
        return true;
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if (this.destination != null) view.put("destination", BlockPos.CODEC, this.destination);
        if (this.dyeColor != null) view.put("dye_color", DyeColor.CODEC, this.dyeColor);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        view.read("destination", BlockPos.CODEC).ifPresent(blockPos -> this.destination = blockPos);
        view.read("dye_color", DyeColor.CODEC).ifPresent(dyeColor -> this.dyeColor = dyeColor);
    }

    public BlockPos getDestination() {
        return this.destination;
    }

    public boolean isBlocked() {
        return this.getCachedState().isOf(VABlocks.MINI_PORTAL) && !this.getCachedState().get(MiniPortalBlock.STATE).canDepart;
    }

    public boolean isDyed() {
        return this.dyeColor != null;
    }

    public int getDyeColor() {
        return this.isDyed() ? this.dyeColor.getEntityColor() : -1;
    }


}
