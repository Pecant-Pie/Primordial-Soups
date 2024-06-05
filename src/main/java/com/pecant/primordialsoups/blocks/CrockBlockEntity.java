package com.pecant.primordialsoups.blocks;

import com.pecant.primordialsoups.menu.CrockBlockMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import static com.pecant.primordialsoups.Registration.CROCK_BLOCK;
import static com.pecant.primordialsoups.Registration.CROCK_BLOCK_ENTITY;

public class CrockBlockEntity extends BlockEntity implements MenuProvider {

    private static Component TITLE = Component.translatable("block.primordialsoups.crock");
    public static final String ITEMS_TAG = "Inventory";

    public static int SLOT_COUNT = 4;
    public static int SLOT = 0;

    private final ItemStackHandler items = createItemHandler();
    private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> items);
    private final FluidTank inputFluidTank = new FluidTank(16000) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            CrockBlockEntity.this.sendUpdate();
        }
    };

    private final LazyOptional<FluidTank> inputFluidOptional = LazyOptional.of(() -> this.inputFluidTank);

    private final FluidTank outputFluidTank = new FluidTank(16000) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            CrockBlockEntity.this.sendUpdate();
        }
    };

    private final LazyOptional<FluidTank> outputFluidOptional = LazyOptional.of(() -> this.outputFluidTank);

    public CrockBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CROCK_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
        inputFluidOptional.invalidate();
        outputFluidOptional.invalidate();
    }

    public void tickServer() {
        if (level.getGameTime() % 20 == 0) {
            ItemStack stack = items.getStackInSlot(SLOT);
            if (!stack.isEmpty()) {
                if (stack.isDamageableItem()) {
                    int value = stack.getDamageValue();
                    if (value > 0 && inputFluidTank.drain(100, IFluidHandler.FluidAction.SIMULATE).getAmount() == 100) {
                        inputFluidTank.drain(100, IFluidHandler.FluidAction.EXECUTE);
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
        tag.put("InputFluidTank", this.inputFluidTank.writeToNBT(new CompoundTag()));
        tag.put("OutputFluidTank", this.outputFluidTank.writeToNBT(new CompoundTag()));
        saveClientData(tag);
    }

    private void saveClientData(CompoundTag tag) {
        tag.put(ITEMS_TAG, items.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.inputFluidTank.readFromNBT(tag.getCompound("InputFluidTank"));
        this.outputFluidTank.readFromNBT(tag.getCompound("OutputFluidTank"));
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
            @Override
            protected void onContentsChanged(int slot) {
                sendUpdate();
            }
        };
    }

    public void sendUpdate() {
        setChanged();
        if (this.level != null)
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), CrockBlock.UPDATE_ALL);
    }

    @NonNull
    @Override
    public <T> LazyOptional<T> getCapability(@NonNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandler.cast();
        } else if (cap == ForgeCapabilities.FLUID_HANDLER) {

//            if (this.getBlockState().getValue(CrockBlock.FACING) == side)
//                return this.outputFluidOptional.cast();
//            else
                return this.inputFluidOptional.cast();
        }
        else {
            return super.getCapability(cap, side);
        }
    }

    public FluidTank getInputFluidTank() {
        return this.inputFluidTank;
    }

    public LazyOptional<FluidTank> getInputFluidOptional() {
        return this.inputFluidOptional;
    }

    public FluidTank getOutputFluidTank() {
        return this.outputFluidTank;
    }

    public LazyOptional<FluidTank> getOutputFluidOptional() {
        return this.outputFluidOptional;
    }


    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CrockBlockMenu(i, inventory, this);
    }


}
