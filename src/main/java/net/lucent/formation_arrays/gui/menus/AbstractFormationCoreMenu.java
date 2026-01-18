package net.lucent.formation_arrays.gui.menus;

import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFormationCoreMenu extends AbstractContainerMenu {


    protected AbstractFormationCoreMenu(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);

    }
    public void addPlayerInventory(Container playerInventory){
        //player inventory
        /*
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory,l + i * 9 + 9,l* 18,i * 18));
            }
        }

         */
    }
    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }


}
