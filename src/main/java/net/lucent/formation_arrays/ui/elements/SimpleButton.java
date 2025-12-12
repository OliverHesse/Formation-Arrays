package net.lucent.formation_arrays.ui.elements;

import net.lucent.easygui.elements.controls.buttons.AbstractButton;
import net.minecraft.client.gui.GuiGraphics;

public class SimpleButton extends AbstractButton {

    private final int border1Color = -9079435;
    private final int border2Color = -1;
    private final int cornerColor = -4539718;
    private final int backgroundColor = -4539718;
    private final int pressedBackgroundColor = -9079435;
    private final int hoveredBackgroundColor = -7631989;

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int topColor = border2Color;
        int bottomColor = border1Color;

        int bgColor = backgroundColor;

        if(isHovered()) bgColor = hoveredBackgroundColor;

        if(isPressed()){
            topColor = border1Color;
            bottomColor = border2Color;
            bgColor = pressedBackgroundColor;
        }
        guiGraphics.fill(0,0,1,getHeight(),topColor);
        guiGraphics.fill(0,0,getWidth(),1,topColor);

        guiGraphics.fill(0,getHeight()-1,getWidth(),getHeight()+1,bottomColor);
        guiGraphics.fill(getWidth()-1,0,getWidth(),getHeight(),bottomColor);

        guiGraphics.fill(0,getHeight()-1,1,getHeight(),cornerColor);
        guiGraphics.fill(getWidth()-1,0,getWidth(),1,cornerColor);



        guiGraphics.fill(1,1,getWidth()-1,getHeight()-1,bgColor);
    }
}
