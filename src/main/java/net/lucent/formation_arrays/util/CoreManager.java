package net.lucent.formation_arrays.util;

import net.lucent.formation_arrays.api.cores.ICoreManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import org.checkerframework.checker.units.qual.C;

import java.util.HashSet;
import java.util.Set;

public class CoreManager extends SavedData implements ICoreManager {

    private static final String FILE_NAME = "formation_array_core_manager";

    private final HashSet<BlockPos> cores = new HashSet<>();


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
