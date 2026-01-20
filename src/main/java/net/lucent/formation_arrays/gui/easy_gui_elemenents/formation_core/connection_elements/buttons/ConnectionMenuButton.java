package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.connection_elements.buttons;

import net.lucent.easygui.elements.controls.buttons.AbstractButton;
import net.lucent.easygui.elements.other.Label;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.lucent.formation_arrays.FormationArrays;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConnectionMenuButton extends AbstractButton {
    private final ITextureData defaultTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/connection_screen.png"),
            256,256,
            179,1,225,13);
    private final ITextureData hoverTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/connection_screen.png"),
            256,256,
            179,14,225,26);
    private final ITextureData pressTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/connection_screen.png"),
            256,256,
            179,27,225,39);

    public Label textLabel;
    public ConnectionMenuButton(IEasyGuiScreen screen,int x,int y){
        super(screen,x,y,46,12);
        textLabel = new Label(screen,23,6,Component.empty());
        textLabel.centered = true;
        textLabel.useCustomScaling = true;
        textLabel.setCustomScale(0.5);
        addChild(textLabel);
        PRESSED_TIME = 5;
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

           if(isPressed()) pressTexture.renderTexture(guiGraphics);
           else   if(isHovered()) hoverTexture.renderTexture(guiGraphics);
            else defaultTexture.renderTexture(guiGraphics);


    }
}
