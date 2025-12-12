package net.lucent.formation_arrays.api.formations.node;


import java.util.HashMap;

public abstract class AbstractFormationNode implements IFormationNode {

    private final HashMap<String, IFormationConnection<?>> formationConnection = new HashMap<>();
    private final HashMap<String,FormationPort<?>> formationPorts = new HashMap<>();


    @Override
    public IFormationConnection<?> getFormationConnection(String id) {
        return formationConnection.get(id);
    }

    @Override
    public void putFormationConnection(String id, IFormationConnection<?> connection) {
        formationConnection.put(id,connection);
    }

    @Override
    public FormationPort<?> getFormationPort(String port) {
        return formationPorts.get(port);
    }

    @Override
    public boolean hasPort(String port) {
        return formationPorts.containsKey(port);
    }

    @Override
    public void putFormationPort(String portId, FormationPort<?> port) {
        formationPorts.put(portId,port);
    }
}
