package net.lucent.formation_arrays.gui.easy_gui_elemenents;

import net.lucent.easygui.elements.containers.EmptyContainer;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.properties.Positioning;
import net.lucent.easygui.util.textures.TextureDataSubSection;
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
    }


    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderSelf(guiGraphics, mouseX, mouseY, partialTick);
        inventoryTexture.renderTexture(guiGraphics);
    }
}
