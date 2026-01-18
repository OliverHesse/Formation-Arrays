package net.lucent.formation_arrays.gui.easy_gui_elemenents.formation_core.connection_elements.buttons;

import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;

public class ConnectionButton extends ConnectionMenuButton {
    public final IFormationConnection<?> connection;
    public ConnectionButton(IEasyGuiScreen screen, int x, int y, IFormationConnection<?> connection) {
        super(screen, x, y);
        this.connection = connection;
        this.textLabel.text = connection.getName();
    }


}
