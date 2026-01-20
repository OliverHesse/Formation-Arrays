package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.inventory_menu;

import net.lucent.easygui.elements.containers.EmptyContainer;
import net.lucent.easygui.elements.tooltips.EasyTooltip;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.formation_arrays.gui.easy_gui_screens.Tier1FormationCoreScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

public class EnergyProgressBar extends EmptyContainer {
    boolean hovered;
    int MAX_QI;
    public EnergyProgressBar(IEasyGuiScreen screen, int x, int y, int width, int height){
        super(screen,x,y,width,height);
        MAX_QI = ((Tier1FormationCoreScreen) getScreen()).getCoreBlockEntity().getEnergyContainer().getMaxEnergy();
    }

    public double getPercentage(){
        return (double) ((Tier1FormationCoreScreen) getScreen()).getMenu().data.get(0) /MAX_QI;
    }



    @Override
    public void onMouseOver(boolean state) {
        super.onMouseOver(state);
        hovered = state;
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderSelf(guiGraphics, mouseX, mouseY, partialTick);
        //guiGraphics.fill(0,0,getWidth(), (int) (getHeight()*getPercentage()),-12728833);

        guiGraphics.fill(0,getHeight(),getWidth(),getHeight()- (int) (getHeight()*getPercentage()),-12728833);

        if(hovered){

            getScreen().setTooltip(new EasyTooltip(mouseX,mouseY, List.of(Component.literal(String.valueOf(((Tier1FormationCoreScreen) getScreen()).getMenu().data.get(0))))));
        }
    }
}
