package com.pecant.primordialsoups.datagen;

import com.pecant.primordialsoups.PrimordialSoups;
import com.pecant.primordialsoups.Registration;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PsItemModels extends ItemModelProvider {

    public PsItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PrimordialSoups.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(Registration.CROCK_BLOCK.getId().getPath(), modLoc("block/crock"));
        withExistingParent(Registration.STOCK_DEPOSIT_BLOCK.getId().getPath(), modLoc("block/stock_deposit"));
        basicItem(Registration.IRON_SOUP_BUCKET.getId());
        basicItem(Registration.STOCK_BUCKET.getId());
    }
}
