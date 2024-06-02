package com.pecant.primordialsoups;

import com.pecant.primordialsoups.blocks.CrockBlock;
import com.pecant.primordialsoups.blocks.CrockBlockEntity;
import com.pecant.primordialsoups.fluid.ModFluidTypes;
import com.pecant.primordialsoups.fluid.ModFluids;
import com.pecant.primordialsoups.menu.CrockBlockMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.pecant.primordialsoups.PrimordialSoups.MODID;

public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, PrimordialSoups.MODID);


    public static final RegistryObject<CrockBlock> CROCK_BLOCK = BLOCKS.register("crock", () -> new CrockBlock());
    public static final RegistryObject<Item> CROCK_BLOCK_ITEM = ITEMS.register("crock", () -> new BlockItem(CROCK_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<CrockBlockEntity>> CROCK_BLOCK_ENTITY = BLOCK_ENTITIES.register("crock",
            () -> BlockEntityType.Builder.of(CrockBlockEntity::new, CROCK_BLOCK.get()).build(null));


    public static final RegistryObject<LiquidBlock> IRON_SOUP_BLOCK = BLOCKS.register("iron_soup",
            () -> new LiquidBlock(ModFluids.SOURCE_IRON_SOUP, BlockBehaviour.Properties.copy(Blocks.LAVA).lightLevel(bs -> 0)));
    public static final RegistryObject<BucketItem> IRON_SOUP_BUCKET = ITEMS.register("iron_soup_bucket",
            () -> new BucketItem(ModFluids.SOURCE_IRON_SOUP,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<LiquidBlock> STOCK_BLOCK = BLOCKS.register("stock",
            () -> new LiquidBlock(ModFluids.SOURCE_STOCK, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<BucketItem> STOCK_BUCKET = ITEMS.register("stock_bucket",
            () -> new BucketItem(ModFluids.SOURCE_STOCK,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));


    public static final RegistryObject<MenuType<CrockBlockMenu>> CROCK_BLOCK_MENU = MENU_TYPES.register("crock_menu",
            () -> IForgeMenuType.create((window, inv, data) -> new CrockBlockMenu(window, inv.player, data.readBlockPos())));


    public static void init(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        MENU_TYPES.register(modEventBus);
        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(CROCK_BLOCK_ITEM);
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(IRON_SOUP_BUCKET);
            event.accept(STOCK_BUCKET);
        }
    }

    public static void addDispenserBehavior(FMLCommonSetupEvent e) {
        DispenserBlock.registerBehavior(STOCK_BUCKET.get(), bucketBehavior);
        DispenserBlock.registerBehavior(IRON_SOUP_BUCKET.get(), bucketBehavior);
    }


    private static DispenseItemBehavior bucketBehavior = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        public ItemStack execute(BlockSource blockPos, ItemStack stack) {
            DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem)stack.getItem();
            BlockPos blockpos = blockPos.getPos().relative((Direction)blockPos.getBlockState().getValue(DispenserBlock.FACING));
            Level level = blockPos.getLevel();
            if (dispensiblecontaineritem.emptyContents((Player)null, level, blockpos, (BlockHitResult)null, stack)) {
                dispensiblecontaineritem.checkExtraContent((Player)null, level, stack, blockpos);
                return new ItemStack(Items.BUCKET);
            } else {
                return this.defaultDispenseItemBehavior.dispense(blockPos, stack);
            }
        }
    };
}
