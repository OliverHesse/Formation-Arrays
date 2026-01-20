package net.lucent.formation_arrays.util.formation_holder;

import net.lucent.formation_arrays.api.cores.IClientFormationsHolder;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.IFormation;
import net.lucent.formation_arrays.api.formations.node.AvailableFormation;
import net.lucent.formation_arrays.api.formations.node.AvailablePort;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.lucent.formation_arrays.network.client_bound.UpdateNearbyFormations;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.*;

public class ClientFormationHolder implements IClientFormationsHolder {
    Map<BlockPos,Map<String,AvailableFormation>> cores = new HashMap<>();
    final HashSet<BlockPos> dirtyCores = new HashSet<>();
    public void addFormations(List<UpdateNearbyFormations.PacketDataInstance> packetDataInstances){
        for(UpdateNearbyFormations.PacketDataInstance instance : packetDataInstances){
            IFormation formationHolder = FormationRegistry.FORMATION_REGISTRY.get(ResourceLocation.bySeparator(instance.formationKey(),':'));
            System.out.println("trying to create formation");
            System.out.println(instance.formationKey());
            System.out.println(instance.formationUUID());

            IFormationNode newNode = formationHolder.getNewFormationNode(instance.coreLocation(), UUID.fromString(instance.formationUUID()));
            if(!dirtyCores.contains(instance.coreLocation())){
                cores.put(instance.coreLocation(),new HashMap<>());
                dirtyCores.add(instance.coreLocation());
            }
            System.out.println(newNode.getFormationId());

            cores.get(instance.coreLocation()).put(instance.formationUUID(),new AvailableFormation(instance.ownerId(), instance.permissionLevel(), newNode,instance.coreLocation()));
        }
        dirtyCores.clear();
    }

    @Override
    public List<AvailablePort> getAvailablePorts(Level level, BlockPos origin, int radius, IFormationNode formation, IFormationConnection<?> connection) {
        List<AvailableFormation> formations = getNearbyFormations(level,origin);
        List<AvailablePort> ports = new ArrayList<>();
        for(AvailableFormation availableFormation : formations){
            for(IFormationPort<?> port : availableFormation.node().getFormationPorts()){
                if(port.getPortType().equals(connection.getConnectionType())) ports.add(AvailablePort.fromIFormationPort(
                        port,
                        availableFormation.corePos(),
                        availableFormation.node().getFormationId(),
                        availableFormation.node().getPortId(port)
                ));
            }
        }

        return ports;
    }

    public boolean hasPermission(AvailableFormation formation,IFormationCore formationCore){

        return formationCore.getOwnerId() != null &&
                formationCore.getOwnerId().equals(formation.ownerId()) &&
                formationCore.getPermissionLevel() > formation.permissionLevel();
    }

    @Override
    public List<AvailableFormation> getNearbyFormations(Level level, BlockPos origin) {
        //some basic checks to stop crashes
        if(level.getBlockEntity(origin) == null ||!(level.getBlockEntity(origin) instanceof IFormationCore originCore)) return List.of();

        List<AvailableFormation> formations = new ArrayList<>();

        for (BlockPos core : cores.keySet()){

            if(core == origin) continue;
            if(core.distSqr(origin) > originCore.getDetectionRadius()*originCore.getDetectionRadius() ) continue;
            for (String formationId : cores.get(core).keySet()){
                if(hasPermission(cores.get(core).get(formationId),originCore)) formations.add(cores.get(core).get(formationId));
            }
        }
        return formations;
    }
}
