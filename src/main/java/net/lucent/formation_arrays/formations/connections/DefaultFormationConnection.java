package net.lucent.formation_arrays.formations.connections;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.node.FormationPort;
import net.lucent.formation_arrays.api.formations.node.IFormationConnection;
import net.lucent.formation_arrays.util.LoggerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;


import java.util.UUID;

public class DefaultFormationConnection<T> implements IFormationConnection<T> {

    private  BlockPos coreLocation;
    private  UUID formationId;
    private  String port;
    private  final String type;
    private final Component name;
    private final Component description;
    public final T defaultValue;
    public DefaultFormationConnection(BlockPos coreLocation, UUID formationId, String port, String type, Component name, Component description, T defaultValue) {
        this.coreLocation = coreLocation;
        this.formationId = formationId;
        this.port = port;
        this.type = type;
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
    }


    @Override
    public BlockPos getCoreLocation() {
        return coreLocation;
    }

    @Override
    public void setCoreLocation(BlockPos pos) {
        this.coreLocation = pos;
    }

    @Override
    public UUID getFormationId() {
        return formationId;
    }

    @Override
    public void setFormationId(UUID formationId) {
        this.formationId = formationId;
    }

    @Override
    public String getConnectionPort() {
        return port;
    }

    @Override
    public void setConnectionPort(String port) {
        this.port = port;
    }

    @Override
    public String getConnectionType() {
        return type;
    }


    @Override
    public Component getDescription() {
        return description;
    }

    @Override
    public Component getName() {
        return name;
    }
    @Override
    public T getConnectionData() {
        return null;
    }
    public T getConnectionData(Level level) {
        if(level.isClientSide()) return null;
        BlockEntity potentialCore = level.getBlockEntity(coreLocation);
        if(!(potentialCore instanceof IFormationCore formationCore)) return null;

        FormationPort<?> formationPort = formationCore.getFormationPort(formationId,port,type);
        if(!formationPort.portType().equals(type)) LoggerUtils.portTypeMismatch(port,name.getString(),coreLocation);

        T data = (T) formationPort.run(); // i want this to crash if something goes wrong, although potentially add logging
        if(data == null) return defaultValue;
        return data;
    }
}
