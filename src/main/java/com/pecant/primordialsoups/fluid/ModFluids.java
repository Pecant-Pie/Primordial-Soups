package com.pecant.primordialsoups.fluid;

import com.pecant.primordialsoups.PrimordialSoups;
import com.pecant.primordialsoups.Registration;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.Flow;
import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, PrimordialSoups.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_IRON_SOUP = FLUIDS.register("iron_soup_fluid",
            () -> new SoupFlowingFluid.Source(ModFluids.IRON_SOUP_FLUID_PROPERTIES, () -> Blocks.IRON_ORE));
    public static final RegistryObject<FlowingFluid> FLOWING_IRON_SOUP = FLUIDS.register("flowing_iron_soup",
            () -> new SoupFlowingFluid.Flowing(ModFluids.IRON_SOUP_FLUID_PROPERTIES, () -> Blocks.IRON_ORE));

    public static final RegistryObject<FlowingFluid> SOURCE_STOCK = FLUIDS.register("brine_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.STOCK_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_STOCK = FLUIDS.register("flowing_brine",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.STOCK_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties IRON_SOUP_FLUID_PROPERTIES =
            soupHelper(ModFluidTypes.IRON_SOUP_FLUID_TYPE, SOURCE_IRON_SOUP, FLOWING_IRON_SOUP,
                    Registration.IRON_SOUP_BLOCK, Registration.IRON_SOUP_BUCKET);

    public static final ForgeFlowingFluid.Properties STOCK_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.STOCK_FLUID_TYPE, SOURCE_STOCK, FLOWING_STOCK).slopeFindDistance(7).levelDecreasePerBlock(1)
            .block(Registration.STOCK_BLOCK).bucket(Registration.STOCK_BUCKET);

    private static ForgeFlowingFluid.Properties soupHelper(RegistryObject<FluidType> fluidType,
       RegistryObject<FlowingFluid> source, RegistryObject<FlowingFluid> flowing, RegistryObject<? extends LiquidBlock> block,
       RegistryObject<? extends Item> bucket) {

        return new ForgeFlowingFluid.Properties(fluidType, source, flowing).slopeFindDistance(1)
                .levelDecreasePerBlock(3).block(block).bucket(bucket);
    }

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}