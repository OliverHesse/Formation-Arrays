package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.inventory_menu;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.elements.controls.buttons.AbstractButton;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.blocks.blocks.BaseFormationCoreEntityBlock;
import net.lucent.formation_arrays.gui.easy_gui_screens.VariedSlotCountFormationCoreScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ToggleActiveStateButton extends AbstractButton {

    private final ITextureData defaultTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/generic_storage_screen.png"),
            256,256,
            180,0,195,16);
    private final ITextureData defaultHoverTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/generic_storage_screen.png"),
            256,256,
            180,17,195,33);
    private final ITextureData pressTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/generic_storage_screen.png"),
            256,256,
            180,34,195,50);
    private final ITextureData pressHoverTexture = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/generic_storage_screen.png"),
            256,256,
            180,51,195,67);

    public ToggleActiveStateButton(IEasyGuiScreen screen,int x,int y){
        super(screen,x,y,15,16);
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(((VariedSlotCountFormationCoreScreen) getScreen()).getCoreBlockEntity().getBlockState().getValue(BaseFormationCoreEntityBlock.FORMATION_CORE_STATE)){
            if(isHovered()) defaultHoverTexture.renderTexture(guiGraphics);
            else defaultTexture.renderTexture(guiGraphics);
        }else {
            if(isHovered()) pressHoverTexture.renderTexture(guiGraphics);
            else pressTexture.renderTexture(guiGraphics);
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
        super.onClick(mouseX, mouseY, button, clicked);
        if(clicked && button == InputConstants.MOUSE_BUTTON_LEFT){
            Minecraft.getInstance().gameMode.handleInventoryButtonClick(((VariedSlotCountFormationCoreScreen) getScreen()).getMenu().containerId, 0);
        }
    }
}
