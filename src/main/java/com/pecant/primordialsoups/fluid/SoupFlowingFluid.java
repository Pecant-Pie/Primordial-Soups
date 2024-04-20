package com.pecant.primordialsoups.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.Supplier;

public abstract class SoupFlowingFluid extends ForgeFlowingFluid {
    protected Supplier<Block> ore;

    protected SoupFlowingFluid(Properties properties, Supplier<Block> ore) {
        super(properties);
        this.ore = ore;
    }

    public BlockState getOreBlockState() {
        return ore.get().defaultBlockState();
    }

    @Override
    public void randomTick(Level pLevel, BlockPos pPos, FluidState pState, RandomSource pRandom) {
//        PrimordialSoups.LOGGER.info("ticked soup at " + pPos.toShortString());
        if (pLevel != null && pState != null && pState.isSource()) {
//            PrimordialSoups.LOGGER.info("attempted setting blockstate");
            pLevel.setBlockAndUpdate(pPos, getOreBlockState());
        }

    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    public static class Source extends SoupFlowingFluid {
        public Source(Properties properties, Supplier<Block> ore) {
            super(properties, ore);
        }

        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends SoupFlowingFluid {
        public Flowing(Properties properties, Supplier<Block> ore) {
            super(properties, ore);
            this.registerDefaultState((FluidState)((FluidState)this.getStateDefinition().any()).setValue(LEVEL, 7));
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(new Property[]{LEVEL});
        }

        public int getAmount(FluidState state) {
            return (Integer)state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }
}
