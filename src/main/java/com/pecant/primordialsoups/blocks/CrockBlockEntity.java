package com.pecant.primordialsoups.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import static com.pecant.primordialsoups.Registration.CROCK_BLOCK_ENTITY;

public class CrockBlockEntity extends BlockEntity {

    public static final String ITEMS_TAG = "Inventory";

    public static int SLOT_COUNT = 1;
    public static int SLOT = 0;

    private final ItemStackHandler items = createItemHandler();
    private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> items);

    public CrockBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CROCK_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    public void tickServer() {
        if (level.getGameTime() % 20 == 0) {
            ItemStack stack = items.getStackInSlot(SLOT);
            if (!stack.isEmpty()) {
                if (stack.isDamageableItem()) {
                    int value = stack.getDamageValue();
                    if (value > 0) {
                        stack.setDamageValue(value - 1);
                    } else {
                        ejectItem();
                    }
                } else {
                    ejectItem();
                }
            }
        }
    }

    private void ejectItem() {
        BlockPos pos = worldPosition.relative(Direction.UP);
        Block.popResource(level, pos, items.extractItem(SLOT, 1, false));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag =  super.getUpdateTag();
        saveClientData(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            loadClientData(tag);
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        // this is called on the client
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            handleUpdateTag(tag);
        }
    }



    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        saveClientData(tag);
    }

    private void saveClientData(CompoundTag tag) {
        tag.put(ITEMS_TAG, items.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        loadClientData(tag);
    }

    private void loadClientData(CompoundTag tag) {
        if (tag.contains(ITEMS_TAG)) {
            items.deserializeNBT(tag.getCompound(ITEMS_TAG));
        }
    }


    @NonNull
    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(SLOT_COUNT) {
            // THIS WILL BE NEEDED ONCE WE HAVE CLIENT-SIDE RENDERING BASED ON CONTENTS
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), CrockBlock.UPDATE_ALL);
            }
        };
    }

    @NonNull
    @Override
    public <T> LazyOptional<T> getCapability(@NonNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandler.cast();
        } else {
            return super.getCapability(cap, side);
        }
    }
}
