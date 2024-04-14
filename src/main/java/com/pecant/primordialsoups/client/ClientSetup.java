package com.pecant.primordialsoups.client;


import com.pecant.primordialsoups.Registration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.pecant.primordialsoups.PrimordialSoups.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void initClient(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(Registration.CROCK_BLOCK_ENTITY.get(), CrockBlockRenderer::new);
    }
}
