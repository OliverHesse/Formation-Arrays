package net.lucent.formation_arrays.gui.easy_gui_screens;

import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.screens.EasyGuiScreen;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.FormationCoreConnectionPanel;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConnectionScreen extends EasyGuiScreen {

    public AbstractFormationCoreBlockEntity blockEntity;
    public int formationSlot;
    public ConnectionScreen(Component title,AbstractFormationCoreBlockEntity blockEntity, int slot) {
        super(title);
        View view = new View(this);
        view.setUseMinecraftScale(false);
        view.useCustomScaling = true;
        view.setCustomScale(3);
        addView(view);
        this.blockEntity = blockEntity;
        this.formationSlot = slot;
        IFormationNode node = blockEntity.formationNodeSlots[slot].getFormationNode();
        FormationCoreConnectionPanel renderable = new FormationCoreConnectionPanel(this);
        renderable.setFormationNode(node);
        view.addChild(renderable);

        System.out.println("trying to create screen");

    }
    public AbstractFormationCoreBlockEntity getCoreBlockEntity(){return this.blockEntity;}
}
