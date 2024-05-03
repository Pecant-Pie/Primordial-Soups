package com.pecant.primordialsoups.menu;

import com.pecant.primordialsoups.Registration;
import com.pecant.primordialsoups.blocks.CrockBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class CrockBlockMenu extends AbstractContainerMenu {
    private final CrockBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;
    private final BlockPos pos;


    // Client Constructor
    public CrockBlockMenu(int containerId, Inventory playerInv, FriendlyByteBuf additionalData) {
        this(containerId, playerInv, playerInv.player.level().getBlockEntity(additionalData.readBlockPos()));
    }

    public CrockBlockMenu(int containerId, Player player, BlockPos pos) {
        this(containerId, player.getInventory(), player.level().getBlockEntity(pos));
    }

    // Server Constructor
    public CrockBlockMenu(int containerId, Inventory playerInv, BlockEntity blockEntity) {
        super(Registration.CROCK_BLOCK_MENU.get(), containerId);
        if (blockEntity instanceof CrockBlockEntity be) {
            this.blockEntity = be;
        } else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into CrockBlockMenu!"
                    .formatted(blockEntity.getClass().getCanonicalName()));
        }
        pos = blockEntity.getBlockPos();
        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), pos);

        createPlayerHotbar(playerInv);
        createPlayerInventory(playerInv);
        createBlockEntityInventory(be);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, Registration.CROCK_BLOCK.get());
    }

    private void createBlockEntityInventory(CrockBlockEntity be) {
        be.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent( inventory -> {
            addSlot(new SlotItemHandler(inventory, 0, 55, 26));
            addSlot(new SlotItemHandler(inventory, 1, 73, 26));
            addSlot(new SlotItemHandler(inventory, 2, 55, 44));
            addSlot(new SlotItemHandler(inventory, 3, 73, 44));
        });
    }

    private void createPlayerInventory(Inventory playerInv) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInv,
                        9 + column + (row * 9),
                        8 + (column * 18),
                        92 + (row * 18)));
            }
        }
    }

    private void createPlayerHotbar(Inventory playerInv) {
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv,
                    column,
                    8 + (column * 18),
                    150));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        Slot fromSlot = getSlot(pIndex);
        ItemStack fromStack = fromSlot.getItem();

        if (fromStack.getCount() <= 0)
            fromSlot.set(ItemStack.EMPTY);

        if (!fromSlot.hasItem())
            return ItemStack.EMPTY;

        ItemStack copyFromStack = fromStack.copy();

        if (pIndex < 36) {
            // We are inside of the player's inventory
            if (!moveItemStackTo(fromStack, 36, 37, false))
                return ItemStack.EMPTY;
        } else if (pIndex < 37) {
            // We are inside of the block entity inventory
            if (!moveItemStackTo(fromStack, 0, 36, false))
                return ItemStack.EMPTY;
        } else {
            System.err.println("Invalid slot index: " + pIndex);
            return ItemStack.EMPTY;
        }

        fromSlot.setChanged();
        fromSlot.onTake(pPlayer, fromStack);

        return copyFromStack;
    }

    public CrockBlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}
