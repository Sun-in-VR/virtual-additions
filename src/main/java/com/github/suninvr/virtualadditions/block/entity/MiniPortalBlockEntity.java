package com.github.suninvr.virtualadditions.block.entity;

import com.github.suninvr.virtualadditions.block.MiniPortalBlock;
import com.github.suninvr.virtualadditions.particle.ColorfulPowerParticleEffect;
import com.github.suninvr.virtualadditions.registry.VABlockEntityType;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VAParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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
    public void preRemoveSideEffects(BlockPos pos, BlockState oldState) {
        super.preRemoveSideEffects(pos, oldState);
        if (this.destination != null && this.level.getBlockState(this.destination).is(VABlocks.MINI_PORTAL)) this.level.destroyBlock(this.destination, false);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    public void ifOther(Consumer<MiniPortalBlockEntity> consumer) {
        if (this.destination != null && this.level != null && this.level.getBlockEntity(this.destination) instanceof MiniPortalBlockEntity blockEntity) consumer.accept(blockEntity);
    }

    public static void setDestination(Level world, BlockPos pos, BlockPos destination) {
        if(world.getBlockEntity(pos) instanceof MiniPortalBlockEntity entity) {
            entity.setDestination(destination);
        }
    }

    public void setDestination(BlockPos destination) {
        this.destination = destination;
        this.setChanged();
    }

    public ParticleOptions getParticleParameter() {
        if (this.isDyed()) return new ColorfulPowerParticleEffect(ARGB.vector3fFromRGB24(this.getDyeColor().getTextureDiffuseColor()));
        else return VAParticleTypes.INTERFERENCE;
    }

    public boolean setDyeColor(DyeColor color) {
        if (color == this.dyeColor) return false;
        this.dyeColor = color;
        this.setChanged();
        if (this.level instanceof ServerLevel serverWorld) serverWorld.getChunkSource().blockChanged(this.worldPosition);
        this.ifOther(blockEntity -> {
            blockEntity.setDyeColor(color);
            blockEntity.setChanged();
            if (this.level instanceof ServerLevel serverWorld) serverWorld.getChunkSource().blockChanged(blockEntity.worldPosition);
        });
        return true;
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if (this.destination != null) view.store("destination", BlockPos.CODEC, this.destination);
        if (this.dyeColor != null) view.store("dye_color", DyeColor.CODEC, this.dyeColor);
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        view.read("destination", BlockPos.CODEC).ifPresent(blockPos -> this.destination = blockPos);
        view.read("dye_color", DyeColor.CODEC).ifPresent(dyeColor -> this.dyeColor = dyeColor);
    }

    public BlockPos getDestination() {
        return this.destination;
    }

    public boolean isBlocked() {
        return this.getBlockState().is(VABlocks.MINI_PORTAL) && !this.getBlockState().getValue(MiniPortalBlock.STATE).canDepart;
    }

    public boolean isDyed() {
        return this.dyeColor != null;
    }

    public DyeColor getDyeColor() {
        return this.dyeColor;
    }


}
