package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.connection_elements.buttons;

import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.formation_arrays.api.formations.node.AvailablePort;

public class PortButton extends ConnectionMenuButton {
    public final AvailablePort port;
    public PortButton(IEasyGuiScreen screen, int x, int y, AvailablePort port) {
        super(screen, x, y);
        this.port = port;
        this.textLabel.text = port.name();
    }


}