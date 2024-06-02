package com.pecant.primordialsoups.blocks;

import com.pecant.primordialsoups.PrimordialSoups;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class CrockBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING;

    private static final int SIDE_THICKNESS = 2;
    protected static final int FLOOR_LEVEL = 4;

    private static final VoxelShape INSIDE = box(2.0, 14.0, 2.0, 14.0, 16.0, 14.0);
    private static final VoxelShape SPOUT_NORTH = box(7.0, 14.0, 0.0, 9.0, 16.0, 2.0);
    private static final VoxelShape SPOUT_SOUTH = box(7.0, 14.0, 14.0, 9.0, 16.0, 16.0);
    private static final VoxelShape SPOUT_WEST = box(0.0, 14.0, 7.0, 2.0, 16.0, 9.0);
    private static final VoxelShape SPOUT_EAST = box(14.0, 14.0, 7.0, 16.0, 16.0, 9.0);

    private static final VoxelShape SHAPE_NORTH;
    private static final VoxelShape SHAPE_SOUTH;
    private static final VoxelShape SHAPE_WEST;
    private static final VoxelShape SHAPE_EAST;




    public CrockBlock() {
        super(Properties.of()
                .strength(10F) // TODO: Make this as tough as reinforced deepslate
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL));
//        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));

    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
            default -> SHAPE_NORTH;
        };
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

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

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        VoxelShape base = Shapes.join(Shapes.block(), INSIDE, BooleanOp.ONLY_FIRST);
        SHAPE_NORTH = Shapes.join(base, SPOUT_NORTH, BooleanOp.ONLY_FIRST);
        SHAPE_SOUTH = Shapes.join(base, SPOUT_SOUTH, BooleanOp.ONLY_FIRST);
        SHAPE_WEST = Shapes.join(base, SPOUT_WEST, BooleanOp.ONLY_FIRST);
        SHAPE_EAST = Shapes.join(base, SPOUT_EAST, BooleanOp.ONLY_FIRST);
    }
}
