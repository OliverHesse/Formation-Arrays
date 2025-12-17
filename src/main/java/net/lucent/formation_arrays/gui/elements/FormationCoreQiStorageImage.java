package net.lucent.formation_arrays.gui.elements;

import net.lucent.easygui.elements.containers.scroll_boxes.DynamicScrollBox;
import net.lucent.easygui.elements.other.Image;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.util.textures.TextureData;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class FormationCoreQiStorageImage extends Image {
    public AbstractFormationCoreBlockEntity formationCoreBlock;
    public FormationCoreQiStorageImage(IEasyGuiScreen easyGuiScreen, int x, int y, AbstractFormationCoreBlockEntity formationCoreBlock) {
        super(easyGuiScreen,
                new TextureData(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,
                        "textures/gui/formation_cores/basic_cores/core_qi_view.png"),
                        176,
                        166),
                x,y);
        this.formationCoreBlock = formationCoreBlock;

        DynamicScrollBox dynamicScrollBox = new DynamicScrollBox(easyGuiScreen,10,13,156,61);
        dynamicScrollBox.setID("energy_scroll_box");
        addChild(dynamicScrollBox);

        InventoryPanel inventoryPanel = new InventoryPanel(easyGuiScreen,7,83, Minecraft.getInstance().player.getInventory());
        addChild(inventoryPanel);

        //TODO add back button
    }

    public void addEnergyBar(String energyId){
        //TODO
    }

    public void removeEnergyBar(String energyId){
        //TODO
    }

}
