package com.pecant.primordialsoups.datagen;

import com.pecant.primordialsoups.PrimordialSoups;
import com.pecant.primordialsoups.Registration;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

import java.util.Map;
import java.util.stream.Collectors;

public class PsLootTables extends VanillaBlockLoot {


    @Override
    protected void generate() {
        dropSelf(Registration.CROCK_BLOCK.get());// TODO: this only exists for testing, will be removed later

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(PrimordialSoups.MODID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
