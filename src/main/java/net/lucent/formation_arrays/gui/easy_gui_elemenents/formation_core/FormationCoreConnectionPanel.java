package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.elements.containers.EmptyContainer;
import net.lucent.easygui.elements.containers.scroll_boxes.DynamicScrollBox;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.properties.Positioning;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.formations.node.AvailablePort;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.connection_elements.ConnectionDataPanel;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.connection_elements.PortDataPanel;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.connection_elements.buttons.ConnectionButton;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.connection_elements.buttons.PortButton;
import net.lucent.formation_arrays.gui.easy_gui_screens.ConnectionScreen;
import net.lucent.formation_arrays.gui.easy_gui_screens.Tier1FormationCoreScreen;
import net.lucent.formation_arrays.network.server_bound.UpdateFormationConnectionStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class FormationCoreConnectionPanel extends EmptyContainer {
    public final ITextureData background = new TextureDataSubSection(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/connection_screen.png"),
            256,256,
            0,0,176,166);
    public IFormationNode formationNode;
    public IFormationConnection<?> formationConnection;
    public AvailablePort availablePort;


    public DynamicScrollBox connectionScrollBox;
    public DynamicScrollBox portScrollBox;
    public ConnectionDataPanel connectionDataScrollBox;
    public PortDataPanel portDataScrollBox;
    public FormationCoreConnectionPanel(IEasyGuiScreen screen){
        super(screen,0,0,176,166);
        setXPositioning(Positioning.CENTER);
        setYPositioning(Positioning.CENTER);
        setX(-176/2);
        setY(-176/2);
        connectionScrollBox = contentArea(3,5,46,156);
        addChild(connectionScrollBox);
        portScrollBox = contentArea(127,5,46,156);
        addChild(portScrollBox);

        connectionDataScrollBox = new ConnectionDataPanel(screen,51,7,74,75);
        addChild(connectionDataScrollBox);

        portDataScrollBox = new PortDataPanel(screen,51,85,74,75);
        addChild(portDataScrollBox);
    }
    public DynamicScrollBox contentArea(int x,int y, int width,int height){
        DynamicScrollBox textArea = new DynamicScrollBox(getScreen(),x,y,width,height){
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
        };
        textArea.useBackgroundColor = false;
        textArea.setBorderVisible(false);

        return textArea;
    }



    public void selectConnection(IFormationConnection<?> connection){
        this.formationConnection = connection;
        List<AvailablePort> availablePorts =   FormationArrays.getClientFormationHolder().getAvailablePorts(
                ((ConnectionScreen) getScreen()).getCoreBlockEntity().getLevel(),
                ((ConnectionScreen) getScreen()).getCoreBlockEntity().getBlockPos(),
                ((ConnectionScreen) getScreen()).getCoreBlockEntity().getDetectionRadius(),
                formationNode,
                connection
                );
        int y = 0;
        portScrollBox.removeChildren();
        portDataScrollBox.clear();
        connectionDataScrollBox.setConnection(connection);
        for(AvailablePort port : availablePorts){
            y = createPort(port,y);
        }
    }
    public void selectPort(IFormationPort<?> port){}


    //TODO
    public void tryConnect(){
        UpdateFormationConnectionStatus.ConnectionData  connectionData= new UpdateFormationConnectionStatus.ConnectionData(
                ((ConnectionScreen) getScreen()).getCoreBlockEntity().getBlockPos(),
                ((ConnectionScreen) getScreen()).formationSlot,
                formationConnection.getConnectionId()
        );
        UpdateFormationConnectionStatus.PortData portData = new UpdateFormationConnectionStatus.PortData(
                availablePort.coreLocation(),
                availablePort.formationId().toString(),
                availablePort.portId()
        );
        PacketDistributor.sendToServer(new UpdateFormationConnectionStatus(connectionData,portData,portDataScrollBox.mode));
        Minecraft.getInstance().setScreen(null);

    }

    public void setFormationNode(IFormationNode node){
        this.formationNode = node;
        connectionScrollBox.removeChildren();
        portScrollBox.removeChildren();
        portDataScrollBox.clear();
        connectionDataScrollBox.clear();
        int y = 0;

        for( IFormationConnection<?> connection : formationNode.getFormationConnections()){

            y = createConnectionBtn(connection,y);
        }
    }


    public void setAvailablePort(AvailablePort availablePort){
        this.availablePort = availablePort;
        portDataScrollBox.setPort(availablePort);
        //TODO refresh data container
    }

    public int createConnectionBtn(IFormationConnection<?> connection,int y){
        ConnectionButton btn = new ConnectionButton(screen,0,y,connection){
            @Override
            public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
                super.onClick(mouseX, mouseY, button, clicked);
                if(clicked && button == InputConstants.MOUSE_BUTTON_LEFT) selectConnection(connection);
            }
        };
        connectionScrollBox.addChild(btn);
        return y+btn.getHeight();
    }
    public int createPort(AvailablePort port,int y){
        PortButton btn = new PortButton(screen,0,y,port){
            @Override
            public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
                super.onClick(mouseX, mouseY, button, clicked);
                if(clicked && button == InputConstants.MOUSE_BUTTON_LEFT) setAvailablePort(port);
            }
        };
        portScrollBox.addChild(btn);
        return y+btn.getHeight();
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderSelf(guiGraphics, mouseX, mouseY, partialTick);
        background.renderTexture(guiGraphics);
    }
}
