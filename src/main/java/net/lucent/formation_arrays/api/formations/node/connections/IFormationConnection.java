package net.lucent.formation_arrays.api.formations.node.connections;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;


import java.util.UUID;

public interface IFormationConnection<T> {

    BlockPos getCoreLocation();
    void setCoreLocation(BlockPos pos);
    UUID getFormationId();
    void setFormationId(UUID formationId);
    String getConnectionPort();
    void setConnectionPort(String port);
    String getConnectionType();
    T getConnectionData(Level level);
    Component getDescription();
    Component getName();
}
