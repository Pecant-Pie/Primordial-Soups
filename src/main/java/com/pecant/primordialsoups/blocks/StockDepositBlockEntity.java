package com.pecant.primordialsoups.blocks;

import com.pecant.primordialsoups.Registration;
import com.pecant.primordialsoups.fluid.ModFluidTypes;
import com.pecant.primordialsoups.fluid.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StockDepositBlockEntity extends BlockEntity {

    private final FluidTank outputFluidTank = new FluidTank(16000) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            StockDepositBlockEntity.this.sendUpdate();
        }
    };

    private final LazyOptional<FluidTank> outputFluidOptional = LazyOptional.of(() -> this.outputFluidTank);

    public StockDepositBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(Registration.STOCK_DEPOSIT_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public FluidTank getOutputFluidTank() {
        return this.outputFluidTank;
    }

    public LazyOptional<FluidTank> getOutputFluidOptional() {
        return this.outputFluidOptional;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.outputFluidTank.readFromNBT(tag.getCompound("OutputFluidTank"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("OutputFluidTank", this.outputFluidTank.writeToNBT(new CompoundTag()));
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        outputFluidOptional.invalidate();
    }


    @NonNull
    @Override
    public <T> LazyOptional<T> getCapability(@NonNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return this.outputFluidOptional.cast();
        }
        else {
            return super.getCapability(cap, side);
        }
    }

    public void sendUpdate() {
        setChanged();
        if (this.level != null)
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), CrockBlock.UPDATE_ALL);
    }


    public void tickServer() {
        if (level.getGameTime() % 20 == 0) {
            IFluidHandler tank = getOutputFluidTank();
            if (tank.fill(new FluidStack(ModFluids.SOURCE_STOCK.get().defaultFluidState().getType(), 1000), IFluidHandler.FluidAction.SIMULATE) == 1000) {
                tank.fill(new FluidStack(ModFluids.SOURCE_STOCK.get().defaultFluidState().getType(), 1000), IFluidHandler.FluidAction.EXECUTE);
                sendUpdate();
            }
        }
    }
}
