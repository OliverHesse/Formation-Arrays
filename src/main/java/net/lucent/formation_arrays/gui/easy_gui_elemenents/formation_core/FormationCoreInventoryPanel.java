package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core;

import net.lucent.easygui.elements.containers.EmptyContainer;
import net.lucent.easygui.elements.inventory.DisplayItemHandlerSlot;
import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.properties.Positioning;
import net.lucent.formation_arrays.formations.FormationCoreItemStackHandler;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.inventory_menu.EnergyProgressBar;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.inventory_menu.FormationSlotHolder;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.inventory_menu.ToggleActiveStateButton;
import net.lucent.formation_arrays.gui.easy_gui_screens.VariedSlotCountFormationCoreScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FormationCoreInventoryPanel extends EmptyContainer {

    private final ITextureData inventoryTexture;
    public FormationCoreInventoryPanel(IEasyGuiScreen easyGuiScreen, ITextureData inventoryTexture){
        super(easyGuiScreen,0,0,0,0);
        this.inventoryTexture = inventoryTexture;
        setXPositioning(Positioning.CENTER);
        setYPositioning(Positioning.CENTER);
        setX(-inventoryTexture.getWidth()/2);
        setY(-inventoryTexture.getHeight()/2);
        setWidth(inventoryTexture.getWidth());
        setHeight(inventoryTexture.getHeight());
        addChild(new EnergyProgressBar(screen,8,9,16,62));
        addChild(new ToggleActiveStateButton(screen,136,5));
        addPlayerInventory();
        addPlayerHotBar();

        addChild(new DisplayItemHandlerSlot(screen,155,5,16,16,"formation_slot_"+0));//access
        addChild(new DisplayItemHandlerSlot(screen,8,76,16,16,"formation_slot_"+1));//fuel

        //disable connections for now, not very useful to players
        //addChild(new OpenConnectionButton(screen,82,5,0));
        FormationCoreItemStackHandler itemStackHandler = ((VariedSlotCountFormationCoreScreen) easyGuiScreen).getMenu().getItemStackHandler();
        //only works for a max of 5, can do 6 but looks wrong af
        for(int i =0;i<itemStackHandler.MAX_FORMATIONS;i++){
            FormationSlotHolder holder = new FormationSlotHolder(easyGuiScreen,43+18*i,17);
            addChild(holder);
            holder.addChild(new DisplayItemHandlerSlot(screen,1,0,16,16,"formation_slot_"+itemStackHandler.FORMATION_SLOTS.get(i)));//plate

            holder.addChild(new DisplayItemHandlerSlot(screen,1,22,16,16,"formation_slot_"+(itemStackHandler.FORMATION_SLOTS.get(i)+1)));//jade
            holder.addChild(new DisplayItemHandlerSlot(screen,1,40,16,16,"formation_slot_"+(itemStackHandler.FORMATION_SLOTS.get(i)+2)));//jade
            holder.addChild(new DisplayItemHandlerSlot(screen,1,58,16,16,"formation_slot_"+(itemStackHandler.FORMATION_SLOTS.get(i)+3)));//jade
        }
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return super.isMouseOver(mouseX, mouseY);
    }

    public void addPlayerInventory() {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                addChild(new DisplaySlot(getScreen(),8 + l * 18, 101 + i * 18,16,16,"p" + (l + i * 9 + 9)));
            }
        }
    }

    public void addPlayerHotBar() {
        for (int i = 0; i < 9; ++i) {
            addChild(new DisplaySlot(getScreen(),8 + i * 18, 159,16,16,"p"+i));

        }
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderSelf(guiGraphics, mouseX, mouseY, partialTick);
        inventoryTexture.renderTexture(guiGraphics);
    }
}
