package net.lucent.formation_arrays.api.formations.node;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public record AvailablePort(Component name, Component description, BlockPos coreLocation, UUID formationId, String portId, String type) {

    public static AvailablePort fromIFormationPort(IFormationPort<?> formationPort,BlockPos coreLocation,UUID formationId,String portId){
        return new AvailablePort(formationPort.getName(),formationPort.getDescription(),coreLocation,formationId,portId,formationPort.getPortType());
    }
}
