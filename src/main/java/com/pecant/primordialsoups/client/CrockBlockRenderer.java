package com.pecant.primordialsoups.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pecant.primordialsoups.blocks.CrockBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class CrockBlockRenderer implements BlockEntityRenderer<CrockBlockEntity> {

    public CrockBlockRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CrockBlockEntity be, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        be.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
            ItemStack stack = h.getStackInSlot(CrockBlockEntity.SLOT);
            if (!stack.isEmpty()) {
                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                long millis = System.currentTimeMillis();

                poseStack.pushPose();
                poseStack.scale(.5f,.5f,.5f);
                poseStack.translate(1f, 2.8f, 1f);
                float angle = ((millis/45) % 360);
                poseStack.mulPose(Axis.YP.rotationDegrees(angle));
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT,
                        combinedOverlay, poseStack, bufferSource, Minecraft.getInstance().level, 0);
                poseStack.popPose();
            }
        });
    }
}
