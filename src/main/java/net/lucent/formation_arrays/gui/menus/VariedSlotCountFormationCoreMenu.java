package net.lucent.formation_arrays.gui.menus;

import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.lucent.formation_arrays.gui.ModMenuTypes;
import net.lucent.formation_arrays.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.Block;

public class VariedSlotCountFormationCoreMenu extends AbstractFormationCoreMenu{



    public VariedSlotCountFormationCoreMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId,inventory, (AbstractFormationCoreBlockEntity) inventory.player.level().getBlockEntity(extraData.readBlockPos()),new SimpleContainerData(1));
    }
    public VariedSlotCountFormationCoreMenu(int containerId, Inventory inventory, AbstractFormationCoreBlockEntity blockEntity, ContainerData dataSlot) {
        super(ModMenuTypes.VARIED_SLOT_COUNT_FORMATION_CORE_MENU.get(),containerId,inventory,blockEntity,dataSlot);

    }

    public static boolean stillValid(ContainerLevelAccess access, Player player, TagKey<Block> tag){
        return (Boolean) access.evaluate((level,pos)->level.getBlockState(pos).is(tag) && player.canInteractWithBlock(pos, 4.0),true);
    }
    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(getLevel(), getCoreBlockEntity().getBlockPos()),
                player, ModTags.Blocks.FORMATION_CORE);
    }

}
