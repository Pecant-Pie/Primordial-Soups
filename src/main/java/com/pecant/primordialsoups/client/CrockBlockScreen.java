package com.pecant.primordialsoups.client;

import com.pecant.primordialsoups.PrimordialSoups;
import com.pecant.primordialsoups.menu.CrockBlockMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import static com.pecant.primordialsoups.PrimordialSoups.LOGGER;

public class CrockBlockScreen extends AbstractContainerScreen<CrockBlockMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(PrimordialSoups.MODID,
            "textures/gui/crock_screen.png");

    public CrockBlockScreen(CrockBlockMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 174;
    }

    @Override
    public void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = inventoryLabelY + 8;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

//        pGuiGraphics.fill(this.leftPos + 99, this.topPos + 14, this.leftPos + 117, this.topPos + 64, 0xFF404040);

        FluidTank tank = this.menu.getBlockEntity().getInputFluidOptional().orElse(null);
        FluidStack fluidStack = tank.getFluid();
        if(fluidStack.isEmpty())
            return;

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);
        if(stillTexture == null)
            return;

        TextureAtlasSprite sprite =
                this.minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = fluidTypeExtensions.getTintColor(fluidStack);

        float alpha = ((tintColor >> 24) & 0xFF) / 255f;
        float red = ((tintColor >> 16) & 0xFF) / 255f;
        float green = ((tintColor >> 8) & 0xFF) / 255f;
        float blue = (tintColor & 0xFF) / 255f;

        pGuiGraphics.setColor(red, green, blue, alpha);

        int fluidHeight = getFluidHeight(tank);
        pGuiGraphics.blit(
                this.leftPos + 26,
                getFluidY(fluidHeight),
                0,
                16,
                fluidHeight,
                sprite);

        pGuiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        FluidTank tank = this.menu.getBlockEntity().getInputFluidTank();
        FluidStack fluidStack = tank.getFluid();
        if(fluidStack.isEmpty())
            return;

        int fluidHeight = getFluidHeight(tank);
        if(!isHovering(26, getFluidY(fluidHeight) - this.topPos, 16, fluidHeight, pMouseX, pMouseY))
            return;

        Component component = MutableComponent.create(fluidStack.getDisplayName().getContents())
                .append(" (%s/%s mB)".formatted(tank.getFluidAmount(), tank.getCapacity()));
        pGuiGraphics.renderTooltip(this.font, component, pMouseX, pMouseY);
        LOGGER.info("Rendered fluid tooltip!");
    }

    private static int getFluidHeight(IFluidTank tank) {
        return (int) (64 * ((float) tank.getFluidAmount() / tank.getCapacity()));
    }

    private int getFluidY(int fluidHeight) {
        return this.topPos + 11 + (64 - fluidHeight);
    }
}
