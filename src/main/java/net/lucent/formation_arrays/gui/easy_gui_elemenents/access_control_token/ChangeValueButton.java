package net.lucent.formation_arrays.gui.easy_gui_elemenents.access_control_token;


import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.elements.controls.buttons.AbstractButton;
import net.lucent.easygui.elements.other.Image;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.network.server_bound.UpdateAccessLevel;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

public class ChangeValueButton extends AbstractButton {

    public int valueChange;

    public ITextureData defaultTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/access_token/change_access_level.png"),
            256,256,192,0,
            224,32
    );
    public ITextureData hoverTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/access_token/change_access_level.png"),
            256,256,224,0,
            256,32
    );    public ITextureData pressTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/access_token/change_access_level.png"),
            256,256,224,32,
            256,64
    );
    public ITextureData arrowTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/access_token/change_access_level.png"),
            256,256,167,0,
            185,23
    );
    public ChangeValueButton(IEasyGuiScreen screen,int x,int y,int rotation,int valueChange){
        super(screen,x,y,32,32);
        this.valueChange = valueChange;
        Image renderable = new Image(screen,arrowTexture,7,4){
            @Override
            public boolean isMouseOver(double mouseX, double mouseY) {
                return false;
            }
        };
        addChild(renderable);
        renderable.setRotation(0,0,rotation);
        PRESSED_TIME = 5;
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
        super.onClick(mouseX, mouseY, button, clicked);
        if(clicked && button == InputConstants.MOUSE_BUTTON_LEFT) PacketDistributor.sendToServer(new UpdateAccessLevel(valueChange));
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(isPressed()) pressTexture.renderTexture(guiGraphics);
        else if (isHovered()) hoverTexture.renderTexture(guiGraphics);
        else defaultTexture.renderTexture(guiGraphics);
    }
}
