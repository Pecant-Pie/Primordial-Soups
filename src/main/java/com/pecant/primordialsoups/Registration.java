package com.pecant.primordialsoups;

import com.pecant.primordialsoups.blocks.CrockBlock;
import com.pecant.primordialsoups.blocks.CrockBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.pecant.primordialsoups.PrimordialSoups.MODID;

public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<Block> CROCK_BLOCK = BLOCKS.register("iron_crock", () -> new CrockBlock());
    public static final RegistryObject<Item> CROCK_BLOCK_ITEM = ITEMS.register("iron_crock", () -> new BlockItem(CROCK_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<CrockBlockEntity>> CROCK_BLOCK_ENTITY = BLOCK_ENTITIES.register("iron_crock",
            () -> BlockEntityType.Builder.of(CrockBlockEntity::new, CROCK_BLOCK.get()).build(null));

    public static void init(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(CROCK_BLOCK_ITEM);
    }
}
