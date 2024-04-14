package com.pecant.primordialsoups;

import com.mojang.blaze3d.audio.Channel;
import com.mojang.logging.LogUtils;
import com.pecant.primordialsoups.datagen.DataGeneration;
import mcjty.theoneprobe.ForgeEventHandlers;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PrimordialSoups.MODID)
public class PrimordialSoups
{
    public static final String MODID = "primordialsoups";
    private static final Logger LOGGER = LogUtils.getLogger();

    public PrimordialSoups()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Registration.init(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(Registration::addCreative);
        modEventBus.addListener(DataGeneration::generate);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

    }
}
