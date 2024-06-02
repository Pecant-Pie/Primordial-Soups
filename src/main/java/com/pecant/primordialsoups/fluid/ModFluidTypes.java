package com.pecant.primordialsoups.fluid;



import com.pecant.primordialsoups.PrimordialSoups;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {

    public static final ResourceLocation OVERLAY = new ResourceLocation(PrimordialSoups.MODID, "misc/in_soup");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PrimordialSoups.MODID);

    public static final RegistryObject<FluidType> IRON_SOUP_FLUID_TYPE = registerSoup("iron_soup", 0xF0601100);


    public static final RegistryObject<FluidType> STOCK_FLUID_TYPE = register("stock_fluid",
            new ResourceLocation("block/water_still"),
            new ResourceLocation("block/water_flow"), OVERLAY, 0x80AAAA00);


    public static RegistryObject<FluidType> registerSoup(String name, int tintColor) {
        return register(name + "_fluid", new ResourceLocation("block/lava_still"),
                new ResourceLocation("block/lava_flow"),
                OVERLAY,
                tintColor);
    }


    private static RegistryObject<FluidType> register(String name, ResourceLocation still, ResourceLocation flow,
                                                      ResourceLocation overlay, int tintColor) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(still, flow, overlay, tintColor,
                new Vector3f((tintColor >> 16 & 0xFF) / 255f, (tintColor >> 8 & 0xFF) / 255f, (tintColor & 0xFF) / 255f),
                FluidType.Properties.create().lightLevel(1).density(15).viscosity(10)));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}