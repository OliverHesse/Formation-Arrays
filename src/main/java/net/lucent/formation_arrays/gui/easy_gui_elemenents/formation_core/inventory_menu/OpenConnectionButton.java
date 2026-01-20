package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.inventory_menu;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.elements.controls.buttons.AbstractButton;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.gui.easy_gui_screens.Tier1FormationCoreScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;



public class OpenConnectionButton extends AbstractButton {
    private final ITextureData defaultTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/tier_1_formation_core_storage_screen.png"),
            256,256,
            197,5,208,16);
    private final ITextureData hoverTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/tier_1_formation_core_storage_screen.png"),
            256,256,
            197,17,225,28);
    private final ITextureData pressTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/tier_1_formation_core_storage_screen.png"),
            256,256,
            197,30,225,40);
    public final int slot;
    public OpenConnectionButton(IEasyGuiScreen screen,int x, int y,int slot){
        super(screen,x,y,11,11);
        this.slot = slot;
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
        super.onClick(mouseX, mouseY, button, clicked);
        if(clicked &&button == InputConstants.MOUSE_BUTTON_LEFT) ((Tier1FormationCoreScreen) getScreen()).openConnectionPanel(slot);
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(isHovered()) hoverTexture.renderTexture(guiGraphics);
        else if(isPressed()) pressTexture.renderTexture(guiGraphics);
        else defaultTexture.renderTexture(guiGraphics);


    }
}
