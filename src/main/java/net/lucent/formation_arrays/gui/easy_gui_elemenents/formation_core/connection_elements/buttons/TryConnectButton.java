package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.connection_elements.buttons;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.FormationCoreConnectionPanel;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TryConnectButton extends ConnectionMenuButton{
    public TryConnectButton(IEasyGuiScreen screen, int x, int y) {
        super(screen, x, y);
        textLabel.text = Component.literal("CONNECT").withStyle(ChatFormatting.BOLD);
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
        super.onClick(mouseX, mouseY, button, clicked);
        if(clicked && button == InputConstants.MOUSE_BUTTON_LEFT){
            ((FormationCoreConnectionPanel)getParent().getParent()).tryConnect();
        }
    }
}
