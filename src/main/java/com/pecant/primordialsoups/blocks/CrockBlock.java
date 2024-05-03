package com.pecant.primordialsoups.blocks;

import com.pecant.primordialsoups.PrimordialSoups;
import com.pecant.primordialsoups.Registration;
import com.pecant.primordialsoups.fluid.ModFluidTypes;
import com.pecant.primordialsoups.fluid.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class CrockBlock extends Block implements EntityBlock {
//    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public CrockBlock() {
        super(Properties.of()
                .strength(10F) // TODO: Make this as tough as reinforced deepslate
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL));
//        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));

    }



    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {



        if (!pLevel.isClientSide()) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (!(be instanceof CrockBlockEntity blockEntity))
                return InteractionResult.PASS;
            // open screen
            NetworkHooks.openScreen((ServerPlayer) pPlayer, blockEntity, blockEntity.getBlockPos());
            PrimordialSoups.LOGGER.debug("Open screen!");
        }
        return InteractionResult.SUCCESS;


//
//        ItemStack item = pPlayer.getMainHandItem();
//        if (item.isEmpty())
//            item = pPlayer.getOffhandItem();
//
//        LazyOptional<IFluidHandlerItem> fluidHandler = item.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
//
//        final boolean succ[] = {false};
//
//        fluidHandler.ifPresent(iFluidHandlerItem -> {
//            if (be instanceof CrockBlockEntity crockBlockEntity) {
//                FluidTank input = crockBlockEntity.getInputFluidTank();
//                FluidTank output = crockBlockEntity.getOutputFluidTank();
//                FluidTank tank = null;
//                boolean drainItem = false;
//                if (iFluidHandlerItem.getFluidInTank(0).isFluidEqual(Registration.STOCK_BUCKET.get().getDefaultInstance())) {
//                    tank = crockBlockEntity.getInputFluidTank();
//                    drainItem = true;
//                } else if (false) {
//                    // TODO: implement draining output fluid if not empty
//                }
//
//                if (drainItem) {
//                    int amountToDrain = tank.getSpace();
//                    int drained = iFluidHandlerItem.drain(amountToDrain, IFluidHandler.FluidAction.SIMULATE).getAmount();
//                    if (drained > 0 && drained == amountToDrain && tank.isFluidValid(iFluidHandlerItem.getFluidInTank(0))) {
//                        tank.fill(iFluidHandlerItem.drain(drained, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
//
//                        if (drained <= amountToDrain) {
//                            pPlayer.setItemSlot(EquipmentSlot.MAINHAND, iFluidHandlerItem.getContainer());
//                            PrimordialSoups.LOGGER.info("drained fluid: ", iFluidHandlerItem.getFluidInTank(0).getFluid());
//                            succ[0] = true;
//                        }
//                    }
//                }
//                else {
//                    // TODO: implement functionality for taking output fluid out!
//                }
//            }
//        });
//
//        if (succ[0])
//            return InteractionResult.sidedSuccess(pLevel.isClientSide);
//        else {

//        }

    }

//    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
//        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
//    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CrockBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity>BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        } else {
            return (lvl, pos, st, blockEntity) -> {
                if (blockEntity instanceof CrockBlockEntity be) {
                    be.tickServer();
                }
            };
        }
    }
}
