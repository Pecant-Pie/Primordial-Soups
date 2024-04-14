package com.pecant.primordialsoups.datagen;

import com.pecant.primordialsoups.PrimordialSoups;
import com.pecant.primordialsoups.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.CompletableFuture;

public class PsBlockTags extends BlockTagsProvider {

    public PsBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PrimordialSoups.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.WITHER_IMMUNE).add(Registration.CROCK_BLOCK.get());
        tag(BlockTags.FEATURES_CANNOT_REPLACE).add(Registration.CROCK_BLOCK.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(Registration.CROCK_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(Registration.CROCK_BLOCK.get());
    }

}
