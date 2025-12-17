package net.lucent.formation_arrays.gui.easy_gui_screens;

import net.lucent.easygui.screens.EasyGuiContainerScreen;
import net.lucent.formation_arrays.gui.menus.AbstractFormationCoreMenu;
import net.lucent.formation_arrays.gui.menus.Tier1FormationCoreMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Tier1FormationCoreScreen extends EasyGuiContainerScreen<Tier1FormationCoreMenu> {
    public Tier1FormationCoreScreen(Tier1FormationCoreMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}
