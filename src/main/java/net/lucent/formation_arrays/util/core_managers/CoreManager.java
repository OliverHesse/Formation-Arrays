package net.lucent.formation_arrays.util.core_managers;

import net.lucent.formation_arrays.api.cores.ICoreManager;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.formations.node.AvailablePort;
import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.lucent.formation_arrays.util.NBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class CoreManager extends SavedData implements ICoreManager {

    private static final String FILE_NAME = "formation_array_core_manager";

    private final HashSet<BlockPos> cores = new HashSet<>();


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

    public static CoreManager get(MinecraftServer server){
        return server.overworld().getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(CoreManager::new, CoreManager::load), FILE_NAME);
    }

    public static CoreManager load(CompoundTag tag, HolderLookup.Provider registries){
        CoreManager coreManager = new CoreManager();
        if(!tag.contains("cores")) return coreManager;

        for(Tag uncastTag : tag.getList("cores",10)){
            if(uncastTag instanceof CompoundTag coreTag){
                coreManager.addCore(NBTUtil.fromCompound(coreTag));
            }
        }
        return coreManager;
    }



    // Add this method to manually trigger saving
    public void save() {
        this.setDirty();
    }
    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        ListTag tag = new ListTag();
        for(BlockPos core: getCores()){
            CompoundTag coreTag = new CompoundTag();
            coreTag.putInt("x",core.getX());
            coreTag.putInt("y",core.getY());
            coreTag.putInt("z",core.getZ());
            tag.add(coreTag);
        }
        compoundTag.put("cores",tag);
        return compoundTag;
    }
}
