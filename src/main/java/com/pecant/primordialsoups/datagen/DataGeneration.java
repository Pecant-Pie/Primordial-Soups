package com.pecant.primordialsoups.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DataGeneration {

    public static void generate(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new PsBlockStates(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new PsItemModels(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new PsLanguageProvider(packOutput, "en_us"));

        PsBlockTags blockTags = new PsBlockTags(packOutput, lookupProvider, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new PsItemTags(packOutput, lookupProvider, blockTags, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new PsRecipes(packOutput));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(PsLootTables::new, LootContextParamSets.BLOCK))));



    }
}
