package com.pecant.primordialsoups.datagen;

import com.pecant.primordialsoups.PrimordialSoups;
import com.pecant.primordialsoups.Registration;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class PsLanguageProvider extends LanguageProvider {

    public PsLanguageProvider(PackOutput output, String locale) {
        super(output, PrimordialSoups.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(Registration.CROCK_BLOCK.get(), "Iron Crock");
    }
}