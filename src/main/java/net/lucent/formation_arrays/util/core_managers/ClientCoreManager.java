package net.lucent.formation_arrays.util.core_managers;

import net.lucent.formation_arrays.api.cores.ICoreManager;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.node.AvailablePort;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.*;

public class ClientCoreManager implements ICoreManager {

    private final HashSet<BlockPos> cores = new HashSet<>();

    public void loadNearbyCores(BlockPos origin){

    }
    public void updateCores(Set<BlockPos> cores){
        this.cores.addAll(cores);
    }

    @Override
    public List<AvailablePort> getAvailablePorts(Level level, BlockPos origin, int radius, IFormationNode formation, IFormationConnection<?> connection) {
        Set<BlockPos> cores = getNearbyCores(level,origin,radius);
        List<AvailablePort> ports = new ArrayList<>();
        for(BlockPos pos : cores){
            if(level.getBlockEntity(pos) instanceof IFormationCore formationCore){
                for(UUID formationId : formationCore.getFormationNodeIDs()){
                    for(IFormationPort<?> port : formationCore.getFormationNode(formationId).getFormationPorts()){
                        if(port.getPortType().equals(connection.getConnectionType())) ports.add(AvailablePort.fromIFormationPort(
                                port,
                                pos,
                                formationId,
                                formationCore.getFormationNode(formationId).getPortId(port)
                        ));

                    }
                }
            }
        }
        return ports;
    }

    @Override
    public Set<BlockPos> getCores() {
        return cores;
    }

    @Override
    public void removeCore(BlockPos blockPos) {
        cores.remove(blockPos);
    }

    @Override
    public void addCore(BlockPos blockPos) {
        cores.add(blockPos);
    }
}
