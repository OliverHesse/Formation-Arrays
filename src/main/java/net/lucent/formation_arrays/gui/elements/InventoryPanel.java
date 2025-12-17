package net.lucent.formation_arrays.gui.elements;

import net.lucent.easygui.elements.containers.panels.Panel;
import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.util.math.BoundChecker;
import net.minecraft.world.Container;

public class InventoryPanel extends Panel {
    public BoundChecker.Vec2 hotBarStartPos = new BoundChecker.Vec2(0,58);
    public BoundChecker.Vec2 inventoryStartPos = new BoundChecker.Vec2(0,0);

    public InventoryPanel(IEasyGuiScreen screen, int x, int y, Container container){
        super(screen,x,y,162,76);
        generateSlots(container);


    }

    public void generateSlots(Container container){

        int slotsPerRow = 9;
        //generate hotBar
        for(int i = 0; i<9;i++){
            DisplaySlot slot = new DisplaySlot(getScreen(),hotBarStartPos.x+i*18+1,hotBarStartPos.y+1,16,16,i, container,false);
            addChild(slot);
        }
        //generate inventory
        for(int i=0;i<27;i++){
            int row = i/9;
            int column = i%9;
            DisplaySlot slot = new DisplaySlot(getScreen(),inventoryStartPos.x+column*18+1,inventoryStartPos.y+row*18+1,16,16,i+9, container,false);
            addChild(slot);
        }

    }
}
