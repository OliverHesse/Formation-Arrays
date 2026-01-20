package net.lucent.formation_arrays.gui.easy_gui_screens;

import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.screens.EasyGuiContainerScreen;
import net.lucent.easygui.util.inventory.IEasySlot;
import net.lucent.easygui.util.math.BoundChecker;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.FormationCoreConnectionPanel;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.Tier1FormationCoreInventoryPanel;
import net.lucent.formation_arrays.gui.menus.Tier1FormationCoreMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Tier1FormationCoreScreen extends EasyGuiContainerScreen<Tier1FormationCoreMenu> {
    public final View view;
    public Tier1FormationCoreScreen(Tier1FormationCoreMenu menu, Inventory playerInventory,Component title) {
        super(menu, playerInventory, title);
        View view = new View(this);
        view.setUseMinecraftScale(false);
        view.useCustomScaling = true;
        view.setCustomScale(3);
        addView(view);
        view.addChild(new Tier1FormationCoreInventoryPanel(this,new TextureDataSubSection(
                ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"textures/gui/formation_cores/basic_cores/tier_1_formation_core_storage_screen.png"),
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
