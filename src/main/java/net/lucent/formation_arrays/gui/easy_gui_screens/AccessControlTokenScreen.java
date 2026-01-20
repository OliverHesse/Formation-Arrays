package net.lucent.formation_arrays.gui.easy_gui_screens;

import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.screens.EasyGuiScreen;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.access_control_token.ControlTokenPanel;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AccessControlTokenScreen extends EasyGuiScreen {
    public AccessControlTokenScreen(Component title) {
        super(title);
        System.out.println("creating screen for token");
        View view = new View(this);
        view.setUseMinecraftScale(false);
        view.useCustomScaling = true;
        view.setCustomScale(3);
        addView(view);

        view.addChild(new ControlTokenPanel(this));
    }
}
