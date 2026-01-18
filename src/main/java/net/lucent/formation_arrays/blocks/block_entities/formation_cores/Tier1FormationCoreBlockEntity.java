package net.lucent.formation_arrays.blocks.block_entities.formation_cores;

import net.lucent.formation_arrays.api.cores.ICoreEnergyContainer;
import net.lucent.formation_arrays.blocks.block_entities.ModBlockEntities;
import net.lucent.formation_arrays.formations.FormationCoreItemStackHandler;
import net.lucent.formation_arrays.gui.menus.Tier1FormationCoreMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class Tier1FormationCoreBlockEntity extends AbstractFormationCoreBlockEntity  {
    public Tier1FormationCoreBlockEntity(BlockPos pos, BlockState blockState, ICoreEnergyContainer energyContainer, FormationCoreItemStackHandler itemStackHandler) {
        super(ModBlockEntities.TIER_1_FORMATION_CORE_BE.get(), pos, blockState, energyContainer, itemStackHandler);
        System.out.println("created formation core be");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {

        return new Tier1FormationCoreMenu(containerId,playerInventory,this,dataSlot);
    }

    @Override
    public int getDetectionRadius() {
        return 32;
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }
}
