package com.pecant.primordialsoups.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrockBlock extends Block implements EntityBlock {
    public CrockBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(10F) // TODO: Make this as tough as reinforced deepslate
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL));


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
}
