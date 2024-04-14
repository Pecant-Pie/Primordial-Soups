package com.pecant.primordialsoups.datagen;

import com.pecant.primordialsoups.PrimordialSoups;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class PsItemTags extends ItemTagsProvider {

    public PsItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blocktags, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, blocktags.contentsGetter(), PrimordialSoups.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}
