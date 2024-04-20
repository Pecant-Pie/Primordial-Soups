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
    // If I want to add a custom fluid texture, th
    public static final ResourceLocation SOUP_STILL_RL = new ResourceLocation("block/lava_still");
    public static final ResourceLocation SOUP_FLOWING_RL = new ResourceLocation("block/lava_flow");
    public static final ResourceLocation IRON_SOUP_OVERLAY_RL = new ResourceLocation(PrimordialSoups.MODID, "misc/in_iron_soup");

    public static final ResourceLocation STOCK_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation STOCK_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation STOCK_OVERLAY_RL = new ResourceLocation(PrimordialSoups.MODID, "misc/in_iron_soup");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PrimordialSoups.MODID);

    public static final RegistryObject<FluidType> IRON_SOUP_FLUID_TYPE = register("iron_soup_fluid", SOUP_STILL_RL,
            SOUP_FLOWING_RL, IRON_SOUP_OVERLAY_RL, 0xF0601100);

    public static final RegistryObject<FluidType> STOCK_FLUID_TYPE = register("stock_soup_fluid", STOCK_STILL_RL,
            STOCK_FLOWING_RL, STOCK_OVERLAY_RL, 0x80AAAA00);



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