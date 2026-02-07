package net.lucent.formation_arrays.gui.easy_gui_screens;

import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.screens.EasyGuiContainerScreen;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.FormationCoreConnectionPanel;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.FormationCoreInventoryPanel;
import net.lucent.formation_arrays.gui.menus.VariedSlotCountFormationCoreMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VariedSlotCountFormationCoreScreen extends EasyGuiContainerScreen<VariedSlotCountFormationCoreMenu> {
    public final View view;

    public VariedSlotCountFormationCoreScreen(VariedSlotCountFormationCoreMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        View view = new View(this);
        view.setUseMinecraftScale(true);

        addView(view);
        view.addChild(new FormationCoreInventoryPanel(this,new TextureDataSubSection(
                ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/generic_storage_screen.png"),
                256,256,
                0,0,
                175,182
        )));
        ContainerRenderable renderable = new FormationCoreConnectionPanel(this);
        renderable.setActive(false);
        view.addChild(renderable);
        this.view = view;

    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {

        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public Level getLevel(){return getMenu().getLevel();}
    public AbstractFormationCoreBlockEntity getCoreBlockEntity(){return getMenu().getCoreBlockEntity();}
    public void openConnectionPanel(int slot){

        if(getCoreBlockEntity().formationNodeSlots[slot].getFormationNode() == null) return;
        view.getChildren().getFirst().setActive(false);
        view.getChildren().get(1).setActive(true);


        Minecraft.getInstance().setScreen(new ConnectionScreen(Component.empty(),getCoreBlockEntity(),slot));

    }

}
