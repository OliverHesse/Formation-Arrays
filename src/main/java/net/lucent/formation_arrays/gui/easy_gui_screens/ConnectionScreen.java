package net.lucent.formation_arrays.gui.easy_gui_screens;

import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.screens.EasyGuiScreen;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.FormationCoreConnectionPanel;
import net.minecraft.network.chat.Component;

public class ConnectionScreen extends EasyGuiScreen {
    public IFormationNode node;
    public AbstractFormationCoreBlockEntity blockEntity;
    public ConnectionScreen(Component title, IFormationNode node, AbstractFormationCoreBlockEntity blockEntity) {
        super(title);
        this.node = node;
        View view = new View(this);
        view.setUseMinecraftScale(true);
        addView(view);
        FormationCoreConnectionPanel renderable = new FormationCoreConnectionPanel(this);
        renderable.setFormationNode(node);
        view.addChild(renderable);
        this.blockEntity = blockEntity;
        System.out.println("trying to create screen");
    }
    public AbstractFormationCoreBlockEntity getCoreBlockEntity(){return this.blockEntity;}
}
