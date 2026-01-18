package net.lucent.formation_arrays.api.cores;

import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.formations.node.AvailablePort;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.*;

public interface ICoreManager {

    List<AvailablePort> getAvailablePorts(Level level, BlockPos origin, int radius, IFormationNode formation, IFormationConnection<?> connection);
    Set<BlockPos> getCores();
    void removeCore(BlockPos blockPos);
    void addCore(BlockPos blockPos);

    default Set<BlockPos> getNearbyCores(Level level, BlockPos origin, int radius){
        System.out.println("trying to get nearby cores");
        //some basic checks to stop crashes
        if(level.getBlockEntity(origin) == null ||!(level.getBlockEntity(origin) instanceof IFormationCore originCore)) return Set.of();

        HashSet<BlockPos> nearbyCores = new HashSet<>();
        Set<BlockPos> cores = getCores();
        Iterator<BlockPos> iterator = cores.iterator();

        while (iterator.hasNext()) {
            System.out.println("getting core");
            BlockPos corePos = iterator.next();
            if (level.getBlockEntity(corePos) == null || !(level.getBlockEntity(corePos) instanceof IFormationCore core)) {
                //either no block entity or entity is not a core
                System.out.println("core does not exist");
                iterator.remove(); // safe remove
                continue;
            }
            System.out.println("matching core ignore");
            if(corePos.equals(origin)) continue;

            System.out.println("outside range ignore");
            //check distance. no roots so should be more efficient
            if(corePos.distSqr(origin) > radius*radius) continue;

            System.out.println("different owners ignore");
            //now ensure origin core has same owner
            if(originCore.getOwnerId() == null || core.getOwnerId() == null ||!originCore.getOwnerId().equals(core.getOwnerId())) continue;

            System.out.println("wrong permission level ignore");
            //check core has permission level
            if(originCore.getPermissionLevel() <= core.getPermissionLevel()) continue;

            nearbyCores.add(corePos);
        }
        return nearbyCores;
    }
}
