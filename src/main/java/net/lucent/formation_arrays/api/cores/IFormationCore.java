package net.lucent.formation_arrays.api.cores;

import net.lucent.formation_arrays.api.formations.node.FormationPort;

import java.util.UUID;

public interface IFormationCore {


    FormationPort<?> getFormationPort(UUID formationId,String portId,String portType);
}
