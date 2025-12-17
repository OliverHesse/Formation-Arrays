package net.lucent.formation_arrays.gui.menus;

import net.lucent.formation_arrays.blocks.ModBlocks;
import net.lucent.formation_arrays.gui.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class Tier1FormationCoreMenu extends AbstractFormationCoreMenu{
    private final Level level;
    private final BlockEntity blockEntity;
    private final ContainerData data;


    public Tier1FormationCoreMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId,inventory,inventory.player.level().getBlockEntity(extraData.readBlockPos()),new SimpleContainerData(1));
    }
    public Tier1FormationCoreMenu(int containerId, Inventory inventory, BlockEntity blockEntity, ContainerData dataSlot) {
        super(ModMenuTypes.TIER_1_FORMATION_CORE_MENU.get(),containerId);

        this.level = blockEntity.getLevel();
        this.blockEntity = blockEntity;
        this.data = dataSlot;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.TIER_1_FORMATION_CORE.get());
    }
}
