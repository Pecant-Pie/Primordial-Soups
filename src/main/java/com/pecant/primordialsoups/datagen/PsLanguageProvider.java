package com.pecant.primordialsoups.datagen;

import com.pecant.primordialsoups.PrimordialSoups;
import com.pecant.primordialsoups.Registration;
import com.pecant.primordialsoups.fluid.ModFluidTypes;
import com.pecant.primordialsoups.fluid.ModFluids;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;

public class PsLanguageProvider extends LanguageProvider {

    public PsLanguageProvider(PackOutput output, String locale) {
        super(output, PrimordialSoups.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(Registration.CROCK_BLOCK.get(), "Primordial Crock");
        add(Registration.IRON_SOUP_BUCKET.get(), "Bucket of Iron Soup");
        add(Registration.STOCK_BUCKET.get(), "Bucket of Stock");
        addFluidType("stock_fluid", "Stock"); // idk how to do this better

    }

    private void addFluidType(String fluidKey, String name) {
        add("fluid_type.primordialsoups." + fluidKey, name);
    }
}
