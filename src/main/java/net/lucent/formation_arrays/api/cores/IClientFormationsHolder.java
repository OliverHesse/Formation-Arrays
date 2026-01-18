package net.lucent.formation_arrays.api.cores;

import net.lucent.formation_arrays.api.formations.node.AvailableFormation;
import net.lucent.formation_arrays.api.formations.node.AvailablePort;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;


public interface IClientFormationsHolder {


    List<AvailablePort> getAvailablePorts(Level level, BlockPos origin, int radius, IFormationNode formation, IFormationConnection<?> connection);

    List<AvailableFormation> getNearbyFormations(Level level, BlockPos origin);
}
