package net.lucent.formation_arrays.gui.easy_gui_elemenents.access_control_token;

import net.lucent.easygui.elements.containers.EmptyContainer;
import net.lucent.easygui.elements.other.Label;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.properties.Positioning;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.capability.IAccessControlToken;
import net.lucent.formation_arrays.api.capability.Capabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ControlTokenPanel extends EmptyContainer {
    public ITextureData background = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/access_token/change_access_level.png"),
            256,256,0,0,
            137,88
    );
    Label accessLevelLabel;
    IAccessControlToken controlToken;

    public ControlTokenPanel(IEasyGuiScreen screen){
        super(screen,-68,-44,137,88);

        setXPositioning(Positioning.CENTER);
        setYPositioning(Positioning.CENTER);

        addChild(new ChangeValueButton(screen,9,29,180,-1));
        addChild(new ChangeValueButton(screen,95,29,0,1));

        accessLevelLabel = new Label(screen,0,0, Component.empty());
        accessLevelLabel.centered = true;
        accessLevelLabel.setXPositioning(Positioning.CENTER);
        accessLevelLabel.setYPositioning(Positioning.CENTER);
        addChild(accessLevelLabel);


    }


    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderSelf(guiGraphics, mouseX, mouseY, partialTick);
        background.renderTexture(guiGraphics);
        ItemStack itemStack = Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND);
        IAccessControlToken capability = itemStack.getCapability(Capabilities.ACCESS_TOKEN_CAPABILITY);
        if(capability != null) accessLevelLabel.text = Component.literal(String.valueOf(
                capability.getPermissionLevel(itemStack)
        ));
    }
}
