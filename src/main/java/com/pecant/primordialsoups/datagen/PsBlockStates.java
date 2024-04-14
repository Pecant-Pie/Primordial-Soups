package com.pecant.primordialsoups.datagen;

import com.pecant.primordialsoups.PrimordialSoups;
import com.pecant.primordialsoups.Registration;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PsBlockStates extends BlockStateProvider {

    public PsBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PrimordialSoups.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(Registration.CROCK_BLOCK.get());
    }
}
