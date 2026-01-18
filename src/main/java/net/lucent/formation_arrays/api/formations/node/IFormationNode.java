package net.lucent.formation_arrays.api.formations.node;

import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
//TODO go through and group functions
public interface IFormationNode {

    IFormationConnection<?> getFormationConnection(String id);
    void putFormationConnection(String id,IFormationConnection<?> connection);

    IFormationPort<?> getFormationPort(String port);
    boolean hasPort(String port);
    void putFormationPort(String portId, IFormationPort<?> port);

   default void addFormationConnection(String id,IFormationConnection<?> connection){
        putFormationConnection(id,connection);
    }
    default void linkFormationConnection(String id,BlockPos core, UUID formation,String port){
        IFormationConnection<?> connection = getFormationConnection(id);
        connection.setCoreLocation(core);
        connection.setFormationId(formation);
        connection.setConnectionPort(port);
    }

    Collection<IFormationConnection<?>> getFormationConnections();
    Collection<IFormationPort<?>> getFormationPorts();
    String getPortId(IFormationPort<?> port);

    default Optional<?> runPort(String port,Level level){
        if(!hasPort(port)) return Optional.empty();
        return Optional.of(getFormationPort(port).run(level));
    };
    /**
     *  origin -> the core
     *  yes they could probably get core from level.getBlockEntity but i feel this is better
     */
    void run(IFormationCore formationCore, Level level, BlockPos origin);
    UUID getFormationId();
    int getEnergyCost();

    /**
     * used when a formation needs to burn extra energy during operation
     * @param core the core to burn energy from
     * @param energy how much energy
     * @return true if able to burn false otherwise
     */
    default boolean tryBurnEnergy(IFormationCore core,int energy){
        return core.tryBurnEnergy(energy);
    }
}
