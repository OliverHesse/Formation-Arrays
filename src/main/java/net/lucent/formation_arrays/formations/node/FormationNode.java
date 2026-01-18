package net.lucent.formation_arrays.formations.node;


import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.lucent.formation_arrays.api.formations.node.IFormationPortFunction;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import java.util.*;

public class FormationNode implements IFormationNode {

    private final HashMap<String, IFormationConnection<?>> formationConnection = new HashMap<>();
    private final HashMap<String, IFormationPort<?>> formationPorts = new HashMap<>();

    private UUID formationUUID;
    @Override
    public IFormationConnection<?> getFormationConnection(String id) {
        return formationConnection.get(id);
    }

    @Override
    public void putFormationConnection(String id, IFormationConnection<?> connection) {
        formationConnection.put(id,connection);
    }

    @Override
    public IFormationPort<?> getFormationPort(String port) {
        return formationPorts.get(port);
    }

    @Override
    public boolean hasPort(String port) {
        return formationPorts.containsKey(port);
    }

    @Override
    public void putFormationPort(String portId, IFormationPort<?> port) {
        formationPorts.put(portId,port);
    }



    @Override
    public Collection<IFormationConnection<?>> getFormationConnections() {
        return formationConnection.values();
    }

    @Override
    public Collection<IFormationPort<?>> getFormationPorts() {
        return formationPorts.values();
    }

    @Override
    public String getPortId(IFormationPort<?> port) {
        for(Map.Entry<String,IFormationPort<?>> entry : formationPorts.entrySet()){
            if(entry.getValue() == port) return entry.getKey();
        }
        return null; //make it clear it does not exist
    }

    @Override
    public void run(IFormationCore formationCore, Level level, BlockPos origin) {

    }

    @Override
    public UUID getFormationId() {
        return formationUUID;
    }

    @Override
    public void setFormationId(UUID id) {
        this.formationUUID=id;
    }

    @Override
    public int getEnergyCost() {
        return 0;
    }
}
