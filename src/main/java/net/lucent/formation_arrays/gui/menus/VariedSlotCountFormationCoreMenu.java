package net.lucent.formation_arrays.gui.menus;

import net.lucent.formation_arrays.blocks.ModBlocks;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.lucent.formation_arrays.gui.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;

public class VariedSlotCountFormationCoreMenu extends AbstractFormationCoreMenu{



    public VariedSlotCountFormationCoreMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId,inventory, (AbstractFormationCoreBlockEntity) inventory.player.level().getBlockEntity(extraData.readBlockPos()),new SimpleContainerData(1));
    }
    public VariedSlotCountFormationCoreMenu(int containerId, Inventory inventory, AbstractFormationCoreBlockEntity blockEntity, ContainerData dataSlot) {
        super(ModMenuTypes.TIER_1_FORMATION_CORE_MENU.get(),containerId,inventory,blockEntity,dataSlot);

    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(getLevel(), getCoreBlockEntity().getBlockPos()),
                player, ModBlocks.TIER_1_FORMATION_CORE.get());
    }

}
