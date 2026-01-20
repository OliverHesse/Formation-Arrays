package net.lucent.formation_arrays.formations.connections;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.lucent.formation_arrays.util.LoggerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;


import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class DefaultFormationConnection<T> implements IFormationConnection<T> {

    private  BlockPos coreLocation; //location of core connected to
    private  UUID formationId; // id of formation connected to
    private  String port;
    private  final String type;
    private final Component name;
    private final Component description;
    public final String connectionId;
    public final T defaultValue;
    public DefaultFormationConnection(String id, String type, Component name, Component description, T defaultValue) {
        this.connectionId = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getConnectionId() {
        return connectionId;
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
    public T getConnectionData(Level level) {
        if(level.isClientSide()) return defaultValue;
        if(getConnectionPort() == null) return defaultValue;
        if(coreLocation == null) return defaultValue;
        if(formationId == null) return defaultValue;
        BlockEntity potentialCore = level.getBlockEntity(coreLocation);
        if(!(potentialCore instanceof IFormationCore formationCore)) return null;

        IFormationNode formationNode = formationCore.getFormationNode(formationId);
        if(!formationNode.getFormationPort(getConnectionPort()).getPortType().equals(getConnectionType())){
            LoggerUtils.portTypeMismatch(port,name.getString(),coreLocation);
            return defaultValue;
        }


        Optional<?> data =  formationNode.runPort(getConnectionPort(),level);
        if(data.isEmpty()) return defaultValue;

        return  data.map(d -> (T) d).orElseGet(() -> {
                    System.out.println("unable to cast");
                    LoggerUtils.sameTypeCastingError(
                            getConnectionPort(),
                            getConnectionId(),
                            coreLocation
                    );
                    return defaultValue;
                });


    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        if(coreLocation == null) {
            buf.writeBoolean(false);
            return;
        }
        buf.writeBoolean(true);
        buf.writeBlockPos(coreLocation);
        buf.writeUUID(formationId);
        buf.writeInt(port.length());
        buf.writeCharSequence(port,Charset.defaultCharset());
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        if(!buf.readBoolean()) return;
        coreLocation = buf.readBlockPos();
        formationId = buf.readUUID();
        port = (String) buf.readCharSequence(buf.readInt(),Charset.defaultCharset());
    }
}
