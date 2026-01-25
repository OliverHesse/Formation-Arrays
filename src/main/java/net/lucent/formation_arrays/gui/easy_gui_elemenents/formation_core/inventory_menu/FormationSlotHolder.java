package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.inventory_menu;

import net.lucent.easygui.elements.containers.EmptyContainer;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.lucent.formation_arrays.FormationArrays;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class FormationSlotHolder extends EmptyContainer {
    public final ITextureData textureData =new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/generic_storage_screen.png"),
            256,256,
            180,70,198,146);
    public FormationSlotHolder(IEasyGuiScreen screen,int x,int y){
        super(screen,x,y,18,76);

    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //textureData.renderTexture(guiGraphics);
    }
}
