package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core;

import net.lucent.easygui.elements.inventory.DisplayItemHandlerSlot;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.inventory_menu.OpenConnectionButton;

public class Tier1FormationCoreInventoryPanel extends FormationCoreInventoryPanel{
    public Tier1FormationCoreInventoryPanel(IEasyGuiScreen easyGuiScreen, ITextureData inventoryTexture) {
        super(easyGuiScreen, inventoryTexture);
        addPlayerInventory();
        addPlayerHotBar();
        addChild(new DisplayItemHandlerSlot(screen,155,5,16,16,"c"+0));//access
        addChild(new DisplayItemHandlerSlot(screen,8,76,16,16,"c"+1));//fuel
        addChild(new OpenConnectionButton(screen,82,5,0));
        addChild(new DisplayItemHandlerSlot(screen,80,18,16,16,"c"+2));//plate

        addChild(new DisplayItemHandlerSlot(screen,80,40,16,16,"c"+3));//jade
        addChild(new DisplayItemHandlerSlot(screen,80,58,16,16,"c"+4));//jade
        addChild(new DisplayItemHandlerSlot(screen,80,76,16,16,"c"+5));//jade
    }

}
