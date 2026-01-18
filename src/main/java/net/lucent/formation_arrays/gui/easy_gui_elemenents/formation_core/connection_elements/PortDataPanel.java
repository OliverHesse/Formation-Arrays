package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.connection_elements;

import net.lucent.easygui.elements.containers.scroll_boxes.DynamicScrollBox;
import net.lucent.easygui.elements.other.Label;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.properties.Positioning;
import net.lucent.formation_arrays.api.formations.node.AvailablePort;
import net.minecraft.network.chat.Component;

public class PortDataPanel extends DynamicScrollBox {

    private AvailablePort port;
    public Label titleLabel;
    public Label descriptionLabel;
    public PortDataPanel(IEasyGuiScreen screen, int x, int y, int width, int height){
        super(screen,x,y,width,height);
        useBackgroundColor = false;
        setBorderVisible(false);
        this.titleLabel = new Label(screen,0,6,Component.empty());
        titleLabel.centered = true;
        titleLabel.setCustomScale(0.5);
        titleLabel.useCustomScaling = true;
        titleLabel.setXPositioning(Positioning.CENTER);
        addChild(titleLabel);

        descriptionLabel = new Label(screen,0,15,
                Component.empty()
        );
        descriptionLabel.setCustomScale(0.5);
        descriptionLabel.useCustomScaling = true;
        descriptionLabel.setWidth(getWidth()*2);
        descriptionLabel.setWrap(true);
        addChild(descriptionLabel);
    }
    public void clear(){
        titleLabel.text = Component.empty();
        descriptionLabel.text = Component.empty();
    }//TODO
    public void setPort(AvailablePort port){
        this.port = port;
        titleLabel.text = port.name();
        descriptionLabel.text =  Component.empty()
                .append(port.description())
                .append("\n Type: "+port.type());
    }
    public AvailablePort getPort(){return this.port;}

    @Override
    public int getMaxXOffset() {
        return 0;
    }

    @Override
    public int getMaxYOffset() {
        int maxYOffset = 0;
        for(ContainerRenderable containerRenderable : getChildren()){
            int yOffset = (int) (containerRenderable.getY()+containerRenderable.getHeight()*containerRenderable.getCustomScale());
            if(yOffset > maxYOffset) maxYOffset = yOffset;

        }
        return maxYOffset;
    }
}