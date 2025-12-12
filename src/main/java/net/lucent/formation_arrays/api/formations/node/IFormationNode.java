package net.lucent.formation_arrays.api.formations.node;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.Optional;
import java.util.UUID;

public interface IFormationNode {

    IFormationConnection<?> getFormationConnection(String id);
    void putFormationConnection(String id,IFormationConnection<?> connection);

    FormationPort<?> getFormationPort(String port);
    boolean hasPort(String port);
    void putFormationPort(String portId,FormationPort<?> port);

    default void addFormationPort(String port, String type, IFormationPortFunction<?> runnable, Component name, Component description){
        putFormationPort(port,new FormationPort<>(type,runnable,name,description));
    }
    default void addFormationConnection(String id,IFormationConnection<?> connection){
        putFormationConnection(id,connection);
    }
    default void linkFormationConnection(String id,BlockPos core, UUID formation,String port){
        IFormationConnection<?> connection = getFormationConnection(id);
        connection.setCoreLocation(core);
        connection.setFormationId(formation);
        connection.setConnectionPort(port);
    }

    default Optional<?> runPort(String port){
        if(!hasPort(port)) return Optional.empty();
        return Optional.of(getFormationPort(port).run());
    };

    void run();
    double getEnergyCost();
}
