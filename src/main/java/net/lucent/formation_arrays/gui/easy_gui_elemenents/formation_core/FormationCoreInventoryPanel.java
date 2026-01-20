package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core;

import net.lucent.easygui.elements.containers.EmptyContainer;
import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.properties.Positioning;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.inventory_menu.EnergyProgressBar;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.inventory_menu.ToggleActiveStateButton;
import net.minecraft.client.gui.GuiGraphics;

public class FormationCoreInventoryPanel extends EmptyContainer {

    private final ITextureData inventoryTexture;
    public FormationCoreInventoryPanel(IEasyGuiScreen easyGuiScreen, ITextureData inventoryTexture){
        super(easyGuiScreen,0,0,0,0);
        this.inventoryTexture = inventoryTexture;
        setXPositioning(Positioning.CENTER);
        setYPositioning(Positioning.CENTER);
        setX(-inventoryTexture.getWidth()/2);
        setY(-inventoryTexture.getHeight()/2);
        setWidth(inventoryTexture.getWidth());
        setHeight(inventoryTexture.getHeight());
        addChild(new EnergyProgressBar(screen,8,9,16,62));
        addChild(new ToggleActiveStateButton(screen,136,5));

    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return super.isMouseOver(mouseX, mouseY);
    }

    public void addPlayerInventory() {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                addChild(new DisplaySlot(getScreen(),8 + l * 18, 101 + i * 18,16,16,"p" + (l + i * 9 + 9)));
            }
        }
    }

    public void addPlayerHotBar() {
        for (int i = 0; i < 9; ++i) {
            addChild(new DisplaySlot(getScreen(),8 + i * 18, 159,16,16,"p"+i));

        }
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderSelf(guiGraphics, mouseX, mouseY, partialTick);
        inventoryTexture.renderTexture(guiGraphics);
    }
}
